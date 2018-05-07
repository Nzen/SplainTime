/**
 * 
 */
package ws.nzen.splaintime;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Struct for preferences
 */
public class StPreference
{
	private static final String cl = "sp.";
	private String undoFlag = "j8x";
	private int finishFuse = 2;
	private String initialTagText = "started up";
	private String subtaskStartFlag = "{";
	/** Means of changing the active text. No time interpretation */
	private String relabelFlag = "f4l";
	private static final String FC_UNDO = "undo_phrase", FC_FUSE = "press_finish",
			FC_INITIAL = "initial_tag", FC_VERSION = "version";
	//private boolean showSeconds = false;
	// adjustment verbosity
	// try to cleanup leftover files

	
	public void parseConfig( String wholeFile )
	{
		if ( wholeFile == null || wholeFile.isEmpty() )
		{
			return;
		}
		Properties fileConfig = getConfigFrom( wholeFile );
		if ( fileConfig.isEmpty() )
		{
			return;
		}
		String temp = fileConfig.getProperty( FC_VERSION, "55.0" );
		float configVersion;
		try
		{
			configVersion = Float.parseFloat( temp );
		}
		catch ( NumberFormatException nfe )
		{
			System.err.println( cl +"pc config version should be a number" );
			configVersion = 0;
		}
		if ( configVersion > 0F )
		{
			undoFlag = fileConfig.getProperty( FC_UNDO, undoFlag );
			initialTagText = fileConfig.getProperty( FC_INITIAL, initialTagText );
			temp = fileConfig.getProperty( FC_FUSE, "2" );
			try
			{
				finishFuse = Integer.parseInt( temp );
			}
			catch ( NumberFormatException nfe )
			{
				System.err.println( cl +"pc config fuse presses should be an integer" );
			}
		}
		System.out.println( cl +"pc 4TESTS config made undo : "+ undoFlag +" ;; initail "+ initialTagText );
	}


	/** populate a properties file or return empty */
	private Properties getConfigFrom( String resourceFileName )
	{
		Properties resourceMap = new Properties();
		if ( resourceFileName == null )
		{
			return resourceMap;
		}
		try ( FileReader ioIn = new FileReader( resourceFileName ) )
		{
			resourceMap.load( ioIn );
			if ( resourceMap.size() < 1 )
			{
				System.err.println( cl +"No resources in "+ resourceFileName );
			}
		}
		catch ( FileNotFoundException e )
		{
			System.err.println( cl +"Couldn't find "+ resourceFileName );
		}
		catch ( IOException e )
		{
			System.err.println( cl +"Couldn't read "+ resourceFileName );
		}
		return resourceMap;
	}


	public String getUndoFlag()
	{
		return undoFlag;
	}
	public void setUndoFlag( String undoFlag )
	{
		this.undoFlag = undoFlag;
	}

	public int getFinishFuse()
	{
		return finishFuse;
	}
	public void setFinishFuse( int finishFuse )
	{
		this.finishFuse = finishFuse;
	}


	public String getRelabelFlag()
	{
		return relabelFlag;
	}
	public void setRelabelFlag( String relabelFlag )
	{
		this.relabelFlag = relabelFlag;
	}


	public String getSubtaskStartFlag()
	{
		return subtaskStartFlag;
	}
	public void setSubtaskStartFlag( String subtaskStartFlag )
	{
		this.subtaskStartFlag = subtaskStartFlag;
	}


	public String getInitialTagText()
	{
		return initialTagText;
	}
	public void setInitialTagText( String initialTagText )
	{
		this.initialTagText = initialTagText;
	}
	
	
}
