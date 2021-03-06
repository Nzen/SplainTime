/**
 * 
 */
package ws.nzen.tracking.splaintime;

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
	private String checkCategoryFlag = "??cat";
	private String nextTimeFlag = "!!nt";
	private String timeSinceFlag = "??ts";
	private String pathToCategoryFile = "st_categories.tsv";
	private boolean hourFormatIs12Not24 = true;
	private String databaseFilename = "st_data";
	private boolean lightLookAndFeel = true;
	private String syncFlag = "<>sync";
	private String bulkEnterFlag = "!!enter";
	@Deprecated
	/** Means of changing the active text. No time interpretation */
	private String relabelFlag = "f4l";
	private static final String FC_UNDO = "undo_phrase", FC_FUSE = "press_finish",
			FC_INITIAL = "initial_tag", FC_VERSION = "version", FC_SUM = "sum_delimiter",
			FC_WANT_SUM = "want_final_sum", FC_CATEGORY_DAYS = "category_grace_days",
			FC_12_HOUR = "12_hour_format", FC_CHECK_CATEGORY = "vet_category",
			FC_TIME_SINCE = "time_since", FC_DB_FILE = "database_filename",
			FC_LOOKFEEL = "theme", FC_BULK_ENTER = "bulk_insert_dialog";
	private float wayFutureVersion = 54F;
	//private boolean showSeconds = false; // or just the format
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
			hourFormatIs12Not24 = temp == null
					|| temp.toLowerCase().equals( "yes" )
					|| temp.toLowerCase().equals( "true" );
 			temp = fileConfig.getProperty( FC_CATEGORY_DAYS,
 					Integer.valueOf( categoryDaysToExpiration ).toString() );
			try
			{
				categoryDaysToExpiration = Integer.parseInt( temp );
			}
			catch ( NumberFormatException nfe )
			{
				System.err.println( cl +"pc config category days should be an integer" );
			}
		}
		if ( configVersion > 2F && configVersion < wayFutureVersion )
		{
			checkCategoryFlag = fileConfig.getProperty( FC_CHECK_CATEGORY, checkCategoryFlag );
			timeSinceFlag = fileConfig.getProperty( FC_TIME_SINCE, timeSinceFlag );
			databaseFilename = fileConfig.getProperty( FC_DB_FILE, databaseFilename );
			lightLookAndFeel = ! fileConfig.getProperty( FC_LOOKFEEL, "light" )
					.equals( "dark" );
			bulkEnterFlag = fileConfig.getProperty( FC_BULK_ENTER, bulkEnterFlag );
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



	public String getCheckCategoryFlag()
	{
		return checkCategoryFlag;
	}
	public void setCheckCategoryFlag( String checkCategoryFlag )
	{
		this.checkCategoryFlag = checkCategoryFlag;
	}


	public String getNextTimeFlag()
	{
		return nextTimeFlag;
	}
	public void setNextTimeFlag( String nextTimeFlag )
	{
		this.nextTimeFlag = nextTimeFlag;
	}



	public String getTimeSinceFlag()
	{
		return timeSinceFlag;
	}
	public void setTimeSinceFlag( String timeSinceFlag )
	{
		this.timeSinceFlag = timeSinceFlag;
	}


	public String getPathToCategoryFile()
	{
		return pathToCategoryFile;
	}


	public String getDatabaseFilename()
	{
		return databaseFilename;
	}


	public boolean isLightLookAndFeel()
	{
		return lightLookAndFeel;
	}
	public void setLightLookAndFeel( boolean lightLookAndFeel )
	{
		this.lightLookAndFeel = lightLookAndFeel;
	}


	public String getSyncFlag()
	{
		return syncFlag;
	}
	public void setSyncFlag( String syncFlag )
	{
		this.syncFlag = syncFlag;
	}


	public String getBulkEnterFlag()
	{
		return bulkEnterFlag;
	}

	public void setBulkEnterFlag( String bulkEnterFlag )
	{
		this.bulkEnterFlag = bulkEnterFlag;
	}


	
	
}
