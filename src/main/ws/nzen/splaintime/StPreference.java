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
	private String sumDelimiter = "^^";
	private boolean doesntNeedSum = true;
	private int categoryDaysToExpiration = 29;
	private String pathToCategoryFile = "st_categories.tsv";
	private boolean hourFormatIs12Not24 = true;
	/** Means of changing the active text. No time interpretation */
	@Deprecated
	private String relabelFlag = "f4l";
	private static final String FC_UNDO = "undo_phrase", FC_FUSE = "press_finish",
			FC_INITIAL = "initial_tag", FC_VERSION = "version", FC_SUM = "sum_delimiter",
			FC_WANT_SUM = "want_final_sum", FC_CATEGORY_DAYS = "category_grace_days",
			FC_12_HOUR = "12_hour_format";
	private float wayFutureVersion = 54F;
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
		if ( configVersion > 0F && configVersion < wayFutureVersion )
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
		if ( configVersion > 1F && configVersion < wayFutureVersion )
		{
			sumDelimiter = fileConfig.getProperty( FC_SUM, sumDelimiter );
			temp = fileConfig.getProperty( FC_WANT_SUM );
			doesntNeedSum = temp != null && ! temp.toLowerCase().equals( "yes" )
					 && ! temp.toLowerCase().equals( "true" );
			temp = fileConfig.getProperty( FC_12_HOUR );
			hourFormatIs12Not24 = temp != null && ( temp.toLowerCase().equals( "yes" )
					 && temp.toLowerCase().equals( "true" ) );
 			temp = fileConfig.getProperty( FC_CATEGORY_DAYS, "29" );
			try
			{
				categoryDaysToExpiration = Integer.parseInt( temp );
			}
			catch ( NumberFormatException nfe )
			{
				System.err.println( cl +"pc config category days should be an integer" );
			}
		}
		System.out.println( cl +"pc 4TESTS config made undo : "+ undoFlag
				+" ;; initail "+ initialTagText +" ;; sum "+ sumDelimiter );
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


	public String getSumDelimiter()
	{
		return sumDelimiter;
	}
	public void setSumDelimiter( String sumDelimiter )
	{
		this.sumDelimiter = sumDelimiter;
	}


	public boolean isDoesntNeedSum()
	{
		return doesntNeedSum;
	}
	public void setDoesntNeedSum( boolean doesntNeedSum )
	{
		this.doesntNeedSum = doesntNeedSum;
	}


	public boolean isHourFormatIs12Not24()
	{
		return hourFormatIs12Not24;
	}
	public void setHourFormatIs12Not24( boolean hourFormatIs12Not24 )
	{
		this.hourFormatIs12Not24 = hourFormatIs12Not24;
	}


	public int getCategoryDaysToExpiration()
	{
		return categoryDaysToExpiration;
	}
	public void setCategoryDaysToExpiration( int categoryDaysToExpiration )
	{
		this.categoryDaysToExpiration = categoryDaysToExpiration;
	}


	public String getPathToCategoryFile()
	{
		return pathToCategoryFile;
	}

	
	
}
