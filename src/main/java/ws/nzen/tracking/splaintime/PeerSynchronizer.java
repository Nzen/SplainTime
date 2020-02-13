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
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import ws.nzen.tracking.splaintime.dao.jooq.tables.StRecordingDevice;

/** Exchange records with another splaintime recording device */
public class PeerSynchronizer
{
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
		Map<Integer, Integer> devicePigdin = exchangeDevicesAsClient(
				in, out, mapper );
		if ( devicePigdin == null )
		{
			wrapSession( in, out, mouth );
			return;
		}

		//
		String userInput;
		while ( (userInput = in.readLine()) != null )
		{
			System.out.println( "echo: " + in.readLine() );
		}
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


	/** return map of combined device ids, theirs:ours ; maybe -1*ours:theirs
	order is
	<ol>
	<li>
	client tells guid, then own id
	</li>
	<li>
	server replies entire or continue
	</li>
	<li>
	if entire, client sends entire device record for own device
	</li>
	<li>
	server sends own guid, then own id
	</li>
	<li>
	client replies entire or continue
	</li>
	<li>
	...
	</li>
	</ol>
	*/
	private Map<Integer, Integer> exchangeDevicesAsClient(
			BufferedReader in,
			PrintWriter out,
			Gson mapper )
			throws IOException
	{
		// N- client speaks first
		out.println( jqPool
				.select( StRecordingDevice.ST_RECORDING_DEVICE.HOME_DIR_GUID )
				.from( StRecordingDevice.ST_RECORDING_DEVICE )
				.where( StRecordingDevice.ST_RECORDING_DEVICE.RECORDING_DEVICE_ID.eq( DSL.val( ownDeviceId ) ) )
				.fetchOne( StRecordingDevice.ST_RECORDING_DEVICE.HOME_DIR_GUID ) );
		out.println( ownDeviceId );
		String requestType = in.readLine();
		if ( requestType == null )
		{
			return null;
		}
		else if ( requestType.equals( RT_ENTIRE_RECORD ) )
		{
			ws.nzen.tracking.splaintime.dao.jooq.tables
			.pojos.StRecordingDevice ownDeviceR = jqPool
			.select()
			.from( StRecordingDevice.ST_RECORDING_DEVICE )
			.where( StRecordingDevice.ST_RECORDING_DEVICE.RECORDING_DEVICE_ID.eq( DSL.val( ownDeviceId ) ) )
			.fetchInto( ws.nzen.tracking.splaintime.dao.jooq.tables.pojos.StRecordingDevice.class ).get( 0 );
			out.println( mapper.toJson( ownDeviceR ) );
		}
		else if ( ! requestType.equals( RT_CONTINUE ) )
		{
			return null;
		}
		// N- else, peer already knows about our device
		Map<Integer, Integer> combinedDeviceIds = new TreeMap<>();
		// N- client listens
		String peerDeviceGuid = in.readLine();
		if ( peerDeviceGuid == null )
		{
			return null;
		}
		String peerDeviceId = in.readLine();
		if ( peerDeviceId == null )
		{
			return null;
		}
		Integer peerRdIdOwn = Integer.parseInt( peerDeviceId );
		//  N- resolve that id or ask for the entire record
		List<ws.nzen.tracking.splaintime.dao.jooq.tables
		.pojos.StRecordingDevice> peerDeviceDb = jqPool
				.select()
				.from( StRecordingDevice.ST_RECORDING_DEVICE )
				.where( StRecordingDevice.ST_RECORDING_DEVICE.HOME_DIR_GUID.eq( DSL.val( peerDeviceGuid ) ) )
				.fetchInto( ws.nzen.tracking.splaintime.dao.jooq.tables.pojos.StRecordingDevice.class );
		if ( peerDeviceDb.isEmpty() )
		{
			out.println( RT_ENTIRE_RECORD );
			ws.nzen.tracking.splaintime.dao.jooq.tables
			.pojos.StRecordingDevice peerDeviceR = mapper.fromJson(
					in.readLine(),
					ws.nzen.tracking.splaintime.dao.jooq.tables
							.pojos.StRecordingDevice.class );
			Integer peerRdIdMine = jqPool.insertInto(
    				StRecordingDevice.ST_RECORDING_DEVICE,
    				StRecordingDevice.ST_RECORDING_DEVICE.HOME_DIR_GUID,
    				StRecordingDevice.ST_RECORDING_DEVICE.RECORDING_DEVICE_TYPE_ID,
    				StRecordingDevice.ST_RECORDING_DEVICE.IPV4_ADDRESS,
    				StRecordingDevice.ST_RECORDING_DEVICE.RECORDING_DEVICE_DESC )
    				.values(
    						peerDeviceR.getHomeDirGuid(),
    						peerDeviceR.getRecordingDeviceTypeId(),
    						peerDeviceR.getIpv4Address(),
    						peerDeviceR.getRecordingDeviceDesc() )
    				.returning().fetchOne().getRecordingDeviceId();
			combinedDeviceIds.put( peerRdIdOwn, peerRdIdMine );
		}
		else
		{
			combinedDeviceIds.put(
					peerRdIdOwn, peerDeviceDb.get( 0 ).getRecordingDeviceId() );
		}
		/*
		FIX
		send all my guids
		expect a list of unrecognized guids to send the entire records for or empty list
		send those
		request peer's list of all guids, preferably without mine, but that's fine too

		rather than rely on these lines, should I drive this with an fsm ?
		*/  

		Map<Integer, ws.nzen.tracking.splaintime.dao.jooq.tables
				.pojos.StRecordingDevice> devicePigdin = new TreeMap<>();
		// parse the string or list or whatever. I should bring in gson or somebody
		return null;
	}
	private static final String RT_ENTIRE_RECORD = "gimmieme",
			RT_CONTINUE = "continue", RT_UNRECOGNIZED = "huh?";


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

		//
		wrapSession( in, out, mouth );
		ear.close();
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



























































