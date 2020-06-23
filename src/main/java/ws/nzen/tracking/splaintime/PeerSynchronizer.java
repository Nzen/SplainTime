/* see ../../../../../LICENSE for release details */
package ws.nzen.tracking.splaintime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import ws.nzen.tracking.splaintime.dao.jooq.tables.StRecordingDevice;

/** Exchange records with another splaintime recording device */
public class PeerSynchronizer
{
	private static final String RT_ENTIRE_RECORD = "gimmieme",
			RT_CONTINUE = "continue", RT_UNRECOGNIZED = "huh?",
			RT_END_OF_INPUT = "RT_END_OF_INPUT";
	private final Logger outChannel = LoggerFactory
			.getLogger( PeerSynchronizer.class );
	private DSLContext jqPool;
	private int ownDeviceId;

	/**  */
	public PeerSynchronizer( DSLContext jooq, int currentDevice )
	{
		if ( jooq == null )
			throw new NullPointerException( "jooq Dsl must not be null" );
		jqPool = jooq;
		ownDeviceId = currentDevice;
	}


	/** Start the client socket */
	public void send(
			InetAddress host, NetworkPort peerPort )
			throws IOException
	{
		if ( host == null )
			throw new NullPointerException( "peer address must not be null" );
		if ( peerPort == null )
			throw new NullPointerException( "peer port must not be null" );
		Socket mouth = new Socket( host, peerPort.getPort() );
		BufferedReader in = new BufferedReader(
				new InputStreamReader( mouth.getInputStream() ) );
		PrintWriter out = new PrintWriter( mouth.getOutputStream(), true );
		Gson mapper = new Gson();
		// todo exchange device types
		Map<Integer, Integer> devicePigdin = exchangeDevicesAsClient(
				in, out, mapper );
		if ( devicePigdin == null )
		{
			wrapSession( in, out, mouth );
			return;
		}

		//
		//
		wrapSession( in, out, mouth );
		/*
		determine the recording device I'm exchanging with
		if it's new, exchange the whole record
		check for other unknown devices
		reply with my own device list
		
		https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
		https://bitbucket.org/Nzen/380-dhcp/src/master/Server.java
		https://docs.oracle.com/javase/8/docs/technotes/guides/serialization/examples/sockets/Client.java
		
		strictly speaking, the device type ids could also need mapping, but I can handle that in a far future
		*/
	}


	/** return map of combined device ids, theirs:ours ; maybe -1*ours:theirs.
	 * Currently assumes that device type id match.
	order is
	<ol>
	<li>
	client announces the topic
	</li>
	<li>
	client each recording device it's aware of
	</li>
	<li>
	client indicates end of input
	</li>
	<li>
	server each recording device it's aware of
	</li>
	<li>
	server indicates end of input
	</li>
	</ol>
	*/
	private Map<Integer, Integer> exchangeDevicesAsClient(
			BufferedReader in,
			PrintWriter out,
			Gson mapper )
			throws IOException
	{
		out.println( StRecordingDevice.ST_RECORDING_DEVICE
				.getClass().getSimpleName() );
		String reply = in.readLine();
		if ( ! reply.equals( RT_CONTINUE ) )
		{
			return null; // N- whatever, server is out of sequence
		}
		Map<Integer, Integer> combinedDeviceIds = new TreeMap<>();
		List<ws.nzen.tracking.splaintime.dao.jooq.tables
		.pojos.StRecordingDevice> knownDevices = jqPool
				.select()
				.from( StRecordingDevice.ST_RECORDING_DEVICE )
				.where()
				.fetchInto( ws.nzen.tracking.splaintime.dao.jooq.tables.pojos.StRecordingDevice.class );
		assert ! knownDevices.isEmpty();
		Map<String, ws.nzen.tracking.splaintime.dao.jooq.tables
		.pojos.StRecordingDevice> uniqueDevices = new TreeMap<>();
		for ( ws.nzen.tracking.splaintime.dao.jooq.tables
				.pojos.StRecordingDevice currDevice : knownDevices )
		{
			uniqueDevices.put( currDevice.getHomeDirGuid(), currDevice );
		}
		// N- offer known devices
		for ( ws.nzen.tracking.splaintime.dao.jooq.tables
				.pojos.StRecordingDevice currDevice : uniqueDevices.values() )
		{
			out.println( mapper.toJson( currDevice ) );
		}
		out.println( RT_END_OF_INPUT );
		// N- listen to peer's known devices
		ws.nzen.tracking.splaintime.dao.jooq.tables
		.pojos.StRecordingDevice peerDevice, ownDevice;
		reply = in.readLine();
		while ( ! reply.equals( RT_END_OF_INPUT ) )
		{
			peerDevice = mapper.fromJson(
					reply,
					ws.nzen.tracking.splaintime.dao.jooq.tables
							.pojos.StRecordingDevice.class );
			ownDevice = uniqueDevices.get( peerDevice.getHomeDirGuid() );
			if ( ownDevice == null )
			{
				Integer peerRdIdMine = jqPool.insertInto(
	    				StRecordingDevice.ST_RECORDING_DEVICE,
	    				StRecordingDevice.ST_RECORDING_DEVICE.HOME_DIR_GUID,
	    				StRecordingDevice.ST_RECORDING_DEVICE.RECORDING_DEVICE_TYPE_ID,
	    				StRecordingDevice.ST_RECORDING_DEVICE.IPV4_ADDRESS,
	    				StRecordingDevice.ST_RECORDING_DEVICE.RECORDING_DEVICE_DESC )
	    				.values(
	    						peerDevice.getHomeDirGuid(),
	    						peerDevice.getRecordingDeviceTypeId(),
	    						peerDevice.getIpv4Address(),
	    						peerDevice.getRecordingDeviceDesc() )
	    				.returning().fetchOne().getRecordingDeviceId();
				combinedDeviceIds.put(
						peerDevice.getRecordingDeviceId(), peerRdIdMine );
			}
			else
			{
				combinedDeviceIds.put(
						peerDevice.getRecordingDeviceId(),
						-1 * ownDevice.getRecordingDeviceId() );
			}
			reply = in.readLine();
		}
		
		return combinedDeviceIds;
	}


	/** Start the server socket */
	public void listen( NetworkPort peerPort )
			throws IOException
	{
		if ( peerPort == null )
			throw new NullPointerException( "peer port must not be null" );
		ServerSocket ear = new ServerSocket( peerPort.getPort() );
		Socket mouth = ear.accept();
		BufferedReader in = new BufferedReader(
				new InputStreamReader( mouth.getInputStream() ) );
		PrintWriter out = new PrintWriter( mouth.getOutputStream(), true );
		Gson mapper = new Gson();
		Map<Integer, Integer> devicePigdin = exchangeDevicesAsServer(
				in, out, mapper );
		if ( devicePigdin == null )
		{
			wrapSession( in, out, mouth );
			ear.close();
			return;
		}

		//
		wrapSession( in, out, mouth );
		ear.close();
	}


	/** return map of combined device ids, theirs:ours ; maybe -1*ours:theirs.
	 * Currently assumes that device type id match.
	order is
	<ol>
	<li>
	client announces the topic
	</li>
	<li>
	client each recording device it's aware of
	</li>
	<li>
	client indicates end of input
	</li>
	<li>
	server each recording device it's aware of
	</li>
	<li>
	server indicates end of input
	</li>
	</ol>
	*/
	private Map<Integer, Integer> exchangeDevicesAsServer(
			BufferedReader in,
			PrintWriter out,
			Gson mapper )
			throws IOException
	{
		String reply = in.readLine();
		if ( ! reply.equals( StRecordingDevice.ST_RECORDING_DEVICE.getClass().getSimpleName() ) )
		{
			out.println( RT_UNRECOGNIZED );
			return null; // N- whatever, maybe the server thinks we did it too recently
		}
		else
			out.println( RT_CONTINUE );
		Map<Integer, Integer> combinedDeviceIds = new TreeMap<>();
		List<ws.nzen.tracking.splaintime.dao.jooq.tables
		.pojos.StRecordingDevice> knownDevices = jqPool
				.select()
				.from( StRecordingDevice.ST_RECORDING_DEVICE )
				.where()
				.fetchInto( ws.nzen.tracking.splaintime.dao.jooq.tables.pojos.StRecordingDevice.class );
		assert ! knownDevices.isEmpty();
		Map<String, ws.nzen.tracking.splaintime.dao.jooq.tables
		.pojos.StRecordingDevice> uniqueDevices = new TreeMap<>();
		for ( ws.nzen.tracking.splaintime.dao.jooq.tables
				.pojos.StRecordingDevice currDevice : knownDevices )
		{
			uniqueDevices.put( currDevice.getHomeDirGuid(), currDevice );
		}
		// N- listen to peer's known devices
		ws.nzen.tracking.splaintime.dao.jooq.tables
		.pojos.StRecordingDevice peerDevice, ownDevice;
		reply = in.readLine();
		while ( ! reply.equals( RT_END_OF_INPUT ) )
		{
			peerDevice = mapper.fromJson(
					reply,
					ws.nzen.tracking.splaintime.dao.jooq.tables
							.pojos.StRecordingDevice.class );
			ownDevice = uniqueDevices.get( peerDevice.getHomeDirGuid() );
			if ( ownDevice == null )
			{
				Integer peerRdIdMine = jqPool.insertInto(
	    				StRecordingDevice.ST_RECORDING_DEVICE,
	    				StRecordingDevice.ST_RECORDING_DEVICE.HOME_DIR_GUID,
	    				StRecordingDevice.ST_RECORDING_DEVICE.RECORDING_DEVICE_TYPE_ID,
	    				StRecordingDevice.ST_RECORDING_DEVICE.IPV4_ADDRESS,
	    				StRecordingDevice.ST_RECORDING_DEVICE.RECORDING_DEVICE_DESC )
	    				.values(
	    						peerDevice.getHomeDirGuid(),
	    						peerDevice.getRecordingDeviceTypeId(),
	    						peerDevice.getIpv4Address(),
	    						peerDevice.getRecordingDeviceDesc() )
	    				.returning().fetchOne().getRecordingDeviceId();
				combinedDeviceIds.put(
						peerDevice.getRecordingDeviceId(), peerRdIdMine );
			}
			else
			{
				combinedDeviceIds.put(
						peerDevice.getRecordingDeviceId(),
						-1 * ownDevice.getRecordingDeviceId() );
			}
			reply = in.readLine();
		}
		// N- offer known devices
		for ( ws.nzen.tracking.splaintime.dao.jooq.tables
				.pojos.StRecordingDevice currDevice : uniqueDevices.values() )
		{
			out.println( mapper.toJson( currDevice ) );
		}
		out.println( RT_END_OF_INPUT );
		
		return combinedDeviceIds;
	}


	private void wrapSession(
			BufferedReader in,
			PrintWriter out,
			Socket ear )
			throws IOException
	{
		in.close();
		out.close();
		ear.close();
	}

}



























































