/*
    &copy Nicholas Prado; License: ../../readme.md

Next:
 */

package ws.nzen.tracking.splaintime;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random; // for self testing

import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import ws.nzen.tracking.splaintime.model.Tag;

/** @author Nzen
 */
public class TagStore implements Store {

    String userFile;
    String tempFile;
    final static boolean amSubTask = true;
    final static boolean forClient = true;
    private final Logger outChannel = LoggerFactory.getLogger( TagStore.class );
    private LinkedList<WhenTag> tags;
    private SimpleDateFormat toHourMs;
    private long msSinceRestartTag = 0L;
    private String restartTag = "";
    private List<Tag> recorded = new LinkedList<>();
    private JdbcConnectionPool cPool;


    /** Setup the store's output, guarantee an initial task */
    public TagStore( String introText, StPreference config ) {
        commonInit( introText, config );
    }

    protected void commonInit( String introText, StPreference config ) {
        tags = new LinkedList<>();
		String hourFormat = ( config.isHourFormatIs12Not24() )
				? "hh:mm.ss a" : "kk:mm.ss   ";
        toHourMs = new SimpleDateFormat( hourFormat );
        GregorianCalendar willBeName = new GregorianCalendar();

        userFile = itoa(willBeName.get( Calendar.YEAR ))
                +" "+ ensureTwoDigits( willBeName.get( Calendar.MONTH ) +1 ) // NOTE zero indexed
                +" "+ ensureTwoDigits( willBeName.get( Calendar.DAY_OF_MONTH ) );
        tempFile = userFile + " tmp.txt";
        userFile += " splained.txt";
        prepDb( config );
		insertFirst( introText );
    }

	protected void prepDb( StPreference config )
	{
		// if ( ! System.getProperty( "os.name" ).equals( "Linux" ) ) // possibly for user home folder
    	cPool = JdbcConnectionPool.create(
    			"jdbc:h2:file:~/.splaintime",
    			"sp_admin_user",
    			"alot1ofcomputerizedneeds" );
    	// NOTE migrate db, if necessary
    	String expectedChangeLogFile = "_st_changesets.json";
    	JdbcConnection liquibasePipe = null;
    	try
    	(
    			Connection pipe = cPool.getConnection();
    	)
		{
    		ResourceAccessor fileProbe = new FileSystemResourceAccessor();
     		liquibasePipe = new JdbcConnection( pipe );
			Liquibase migrator = new Liquibase(
					expectedChangeLogFile,
					fileProbe,
					DatabaseFactory.getInstance()
						.findCorrectDatabaseImplementation( liquibasePipe ) );
			migrator.update( new Contexts() );
			// 4TESTS check if it went in
			Statement executor = liquibasePipe.createStatement();
			ResultSet rows = executor.executeQuery( "SHOW TABLES;" );
			if ( rows.next() )
			{
				outChannel.info( "tables exist :" );
				do
				{
					
					outChannel.info( rows.getString( 1 ) );
				}
				while ( rows.next() );
			}
			rows.close();
			executor.close();
		}
		catch ( SQLException | LiquibaseException se )
		{
			outChannel.error( se.toString() );
			se.printStackTrace();
		}
    	finally
    	{
    		if ( liquibasePipe != null )
				try
				{
					liquibasePipe.close();
				}
				catch ( DatabaseException de )
				{
					outChannel.error( de.toString() );
				}
    	}
	}

    /** Ensure a task is ready on startup */
	private void insertFirst( String basicStartup ) {
        // IMPROVE delete temp files from previous run
        String tempSays = gTempSavedTag();
        if ( tempSays.isEmpty() ) {
            pr( "ts.if() no temp file" ); // 4TESTS
            writeToDisk( forClient, " SplainTime output format v 2.0\r\n" );
            Tag primingPump = new Tag( basicStartup );
            primingPump.utilDate = new Date();
            add( primingPump );
        } else {
            pr( "ts.if() found temp: "+ tempSays ); // 4TESTS
            WhenTag fromPreviousRun = parseTempTag( tempSays );
            if ( fromPreviousRun != null ) {
                tags.add( fromPreviousRun );
                Tag alsoAs = new Tag( fromPreviousRun.didWhat,
                		fromPreviousRun.tagTime.toInstant()
    					.atZone( java.time.ZoneId.systemDefault() )
    					.toLocalDateTime() );
                recorded.add( alsoAs );
            }
            else {
                Tag primingPump = new Tag( basicStartup );
                primingPump.utilDate = new Date();
                add( primingPump );
            }
        }
	}

    /** Add a tag, when started, whether ends previous tag */
    public void add( Tag fromGui ) {
        tags.add( new WhenTag( fromGui ) );
        // pr( "ts.a() got "+ when.toString() +" _ "+ what ); // 4TESTS
        if ( ! restartTag.isEmpty() )
        {
        	restartTag = "";
        	msSinceRestartTag = 0L;
        }
        recorded.add( fromGui );
    }


    public void replaceActiveWith( Tag fromGui )
    {
    	tags.pop();
    	tags.add( new WhenTag( fromGui ) );
    }


    /** writes all but the latest to disk; callee checked there's x&gt;1 */ // UNREADY
    private void flushExtra() {
        String outStr = "";
        WhenTag temp;
        Date later;
        for ( int ind = 0; ind < tags.size() -1; ind++ ) {
            temp = tags.get( ind );
            later = tags.get( ind +1 ).tagTime;
            outStr += toHourMs.format( temp.tagTime ) +"\t"
                + prettyDiff( temp.tagTime, later, temp.subT ) +"\t"
                + temp.didWhat + "\r\n";
        }
        while( tags.size() > 1 ) {
            tags.removeFirst();
        }
        // writeToDisk( userFile, outStr ); // 4TESTS
		writeToDisk( forClient, outStr ); // 4REAL
    }

    /** Flush &amp; write current to the cache file */
    public void quickSave() {
    	boolean holdingMultipleTags = tags.size() > 1;
        if ( holdingMultipleTags ) {
            flushExtra();
        }
        if ( holdingMultipleTags
        		|| ( ! restartTag.isEmpty() && aMinuteSince( msSinceRestartTag ) )
        		|| aMinuteSince( tags.peek().tagTime.getTime() ) ) {
            // pr("ts.qs "); writeToDisk( tempFile, tempFileFormat(tags.peek()) ); // 4TESTS
            if ( ! restartTag.isEmpty() )
            {
            	writeToDisk( ! forClient, tempFileFormat() );
            }
            else
            {
            	writeToDisk( ! forClient, tempFileFormat(tags.peek()) );
            }
        }
    }

    private boolean aMinuteSince( long prevTagStamp ) {
        return (System.currentTimeMillis() - prevTagStamp) < 85000; // roughly over a minute
    }

    /** Formats the start time and diff for the user */
    private String prettyDiff( Date start, Date end, boolean subT ) {
        long diffSeconds = ( end.getTime() - start.getTime() ) / 1000;
        // pr( "ts.pd() diff is " + Long.toString(diffSeconds) +" seconds" ); // 4TESTS
        int hours = 0, hourSeconds = 3600; // 60m * 60s
        while ( diffSeconds >= hourSeconds ) {
            diffSeconds -= hourSeconds;
            hours++;
        }
        String hrs = ( hours > 0 ) ? itoa( hours )+"h " : "";
        int  min = 0, minuteSeconds = 60;
        while ( diffSeconds >= minuteSeconds ) {
            diffSeconds -= minuteSeconds;
            min++;
        }
        String mins = ( min >= 0 ) ? itoa( min )+"m" : "";
        return (subT) ? "("+ hrs + mins+") "
        		: hrs + mins+" "; // NOTE remainder tossed
    }

    /** does pd show correct diff? */
	String[] problemsWithPrettyDiff( Random oracle ) {
		/*
		no difference
		arbitrary difference
		*/
		return new String[]{""};
	}

    /** to test prettyDiff quickly, by hand */
    void interactiveUTF() {
        Date now = new Date();
        Date later;
        long nowMs = now.getTime();
        pr( "ts.iuff() interactive mode begins\n" );
        String[] tagsToSay = new String[] { "nn uu cc DD","bla bla",
                "FileForm", "interactiveUFF", "Integer.toString" }; // aesthetic touch
        java.util.Scanner cli = new java.util.Scanner( System.in );
        for ( int times = 5; times > 0; times-- ) {
            pr( "\n\t\tminute difference ?" );
            int nowPlusMin = cli.nextInt();
            long laterMs = nowMs + ( nowPlusMin * 60000 );
            later = new Date( laterMs );
            pr( toHourMs.format( later ) +"\t"+ prettyDiff(
            		now, later, (times % 3 == 0) ) +"\t"+ tagsToSay[times -1] );
        }
        cli.close();
        pr( "interactive mode over\n" );
    }

    /** restartTag and time -1 to signal that it's 'write when seen' */
    private String tempFileFormat() {
        return "-1\t"+ restartTag +"\t"+ restartTag;
    }

    /** just toStr of inMem */
    private String tempFileFormat( WhenTag inMem ) {
        // String sub = ( inMem.subT ) ? "s" : "m"; // IMPROVE this is for later
        return Long.toString( inMem.tagTime.getTime() ) +"\t"+ inMem.didWhat +"\t"+ inMem.originallySaid;
    }

    /** turns datetag into a whentag, not subtask aware for now */
    private WhenTag parseTempTag( String fromFile ) {
        String[] date_tag = fromFile.split( "\t" );
        int time = 0, tag = time +1, original = tag +1;
        try {
        	long savedMilliseconds = Long.parseLong(date_tag[ time ]);
        	if ( savedMilliseconds < 0 )
        	{
        		savedMilliseconds = System.currentTimeMillis();
        	}
            Date then = new Date( savedMilliseconds );
            Tag deserialized = new Tag( date_tag[ original ] );
            deserialized.hackSetTagText( date_tag[tag] );
            deserialized.utilDate = then;
            return new WhenTag( deserialized );
        } catch ( NumberFormatException nfe ) {
            pr( "ts.ptt couldn't parse |"+ date_tag[time] +"| as millisec for the date" );
            return null;
        }
    }

    String[] problemsWithParseTempTag() {
        int tests = 2;
        int currProb = 0;
        String[] problems = new String[ tests ];
        problems[ 0 ] = "";
        Date wasNow = new Date();
        Tag input = new Tag( "basic tag" );
        input.utilDate = wasNow;
        String basic = tempFileFormat(new WhenTag( input ));
        WhenTag basicWt = parseTempTag( basic );
        if ( basicWt == null ) {
            problems[ currProb ] = "ts.pwptt basic parse is null";
            return problems;
        }
        if ( ! wasNow.equals(basicWt.tagTime) ){
            problems[ currProb ] = "ts.pwptt dates didn't match: \n\ttest "
                    + wasNow.toString() +"\tbecame "+ basicWt.tagTime.toString();
            currProb++;
        }
        return problems;
    }

    private String gTempSavedTag( boolean testing ) {
        return tempFile;
    }

    /** Gets blank or tag in a temp file */
    private String gTempSavedTag() {
        String maybePrevious = "";
        Path relPath = Paths.get( tempFile );
        try {
            if ( Files.exists(relPath) ) {
                try (java.io.BufferedReader fileOpener = Files.newBufferedReader(
                        relPath, StandardCharsets.UTF_8 )
                    ) {
                    maybePrevious = fileOpener.readLine(); // IMPROVE assumes no subtasks
                    if ( maybePrevious == null )
                        maybePrevious = "";
            }   }
        } catch ( IOException ioe ) {
            System.err.println( "LF.rsf() had some I/O problem."
                    + " there's like five options\n "+ ioe.toString() );
        }
        return maybePrevious;
    }

    /* 4TESTS, for visual feedback */
    private void writeToDisk( String whichFile, String outStr ) {
        if (whichFile.equals( userFile )) {
            pr( "\t"+ whichFile +"\n"+ outStr );
        } else {
            tempFile = outStr;
            pr( "\t temp\t"+ outStr );
        }
    }

    /** Appends outStr userFile or truncates temp with outStr */
    private void writeToDisk( boolean isUser, String outStr ) {
        // IMPROVE use imports
        String whichFile;
        StandardOpenOption howTreat;
        if ( isUser ) {
            whichFile = userFile;
            howTreat = StandardOpenOption.APPEND;
        } else {
            whichFile = tempFile;
            howTreat = StandardOpenOption.TRUNCATE_EXISTING;
        }
        Path relPath = Paths.get(whichFile);
        try {
            if ( Files.notExists(relPath) ) {
                Files.createFile(relPath);
            }
            try (java.io.BufferedWriter paper = Files.newBufferedWriter(
                    relPath, StandardCharsets.UTF_8, howTreat )
                ) {
                paper.append( outStr );
            }
        } catch ( java.io.IOException ioe ) {
            System.err.println( "LF.rsf() had some I/O problem."
                    + " there's like five options\n "+ ioe.toString() );
        }
    }

    /** open the archived tags */
    public OpenResult showStoredTags() {
        if ( Desktop.isDesktopSupported() ) {
            try {
                File archivedTags = new File( userFile );
                if ( archivedTags.exists() )
                {
	                Desktop os = Desktop.getDesktop();
	                if ( os.isSupported( Desktop.Action.EDIT ) )
	                {
	                	os.edit( archivedTags );
	                	return OpenResult.WORKED;
	                }
	                else if ( os.isSupported( Desktop.Action.OPEN ) )
	                {
	                	os.open( archivedTags );
	                	return OpenResult.WORKED;
	                }
	                else
	                {
	                    System.out.println( "neither opening nor editing supported; sorry" );
	                    return OpenResult.NO_OPEN;
	                }
                }
                else
                {
                    System.out.println( "haven't written yet" );
                	return OpenResult.NO_FILE;
                }
            } catch ( IOException | UnsupportedOperationException ioe ) {
                System.err.println( "couldn't open, sorry\n\t"+ ioe.toString() );
            	return OpenResult.IOE;
            }
        } else {
            System.out.println( "can't open: Desktop unsupported; sorry" );
        	return OpenResult.NO_DESKTOP;
        }
    }

    public boolean whetherCategory( String someInput, StPreference config )
	{
		if ( someInput == null || someInput.isEmpty()
				|| config.isDoesntNeedSum() )
		{
			return false;
		}
		try
		{
			Path whereToday = Paths.get( userFile );
			Path whereCategories = Paths.get( config.getPathToCategoryFile() );
			List<String> userLines = Files.readAllLines( whereToday );
			List<String> categories = Files.readAllLines( whereCategories );
			Accountant tabulator = new Accountant( config );
			// IMPROVE avoid calculating the sums just to parse the files
			userLines = tabulator.withSums( userLines, categories );
			categories = tabulator.getCategories();
			final int catInd = 0;
			for ( String catPlusDate : categories )
			{
				String[] catSplit = catPlusDate.split( "\t" );
				if ( catSplit[ catInd ].equals( someInput ) )
				{
					return true;
				}
			}
			return false;
		}
		catch ( IOException ie )
		{
			System.err.println( "unable to prettify user file because "+ ie );
			return false;
		}
	}

	/** Uses in-memory only, not the file stored tags; returns
	 * a Duration of 0 seconds if the tag is not found. */
    public Duration timeSince( String someInput,
			LocalDateTime reference, StPreference config )
	{
		if ( someInput == null || someInput.isEmpty() )
		{
			return Duration.ofSeconds( 0L );
		}
		Tag latestRelevant = null;
		for ( int ind = recorded.size() -1; ind >= 0; ind-- )
		{
			Tag candidate = recorded.get( ind );
			if ( candidate.getUserText().contains( someInput ) )
			{
				// NOTE calc from end, if we aren't the active tag
				if ( ind < recorded.size() -1 )
					latestRelevant = recorded.get( ind +1 );
				else
					
					latestRelevant = candidate;
				break;
			}
		}
		if ( latestRelevant == null )
		{
			return Duration.ofSeconds( 0L );
		}
		else
		{
			return Duration.between( latestRelevant.getWhen(), reference );
		}
	}

    /** delete the temp file ; write the last one */ // UNREADY
    public void wrapUp( StPreference config ) {
        flushExtra();
        WhenTag ultimate = tags.getLast();
        String outStr = toHourMs.format( ultimate.tagTime ) +"\t"
                + prettyDiff( ultimate.tagTime,
                		new Date(), ultimate.subT ) +"\t"
                + ultimate.didWhat + "\r\n";
        // writeToDisk( userFile, outStr ); // 4TESTS
		writeToDisk( forClient, outStr ); // 4REAL
        deleteTempFile();
        cPool.dispose();
        prettifyFile( config );
	}

	private void prettifyFile( StPreference config )
	{
		if ( config.isDoesntNeedSum() )
		{
			return;
		}
		try
		{
			Path whereToday = Paths.get( userFile );
			Path whereCategories = Paths.get( config.getPathToCategoryFile() );
			List<String> userLines = Files.readAllLines( whereToday );
			List<String> categories = Files.readAllLines( whereCategories );
			Accountant tabulator = new Accountant( config );
			userLines = tabulator.withSums( userLines, categories );
			categories = tabulator.getCategories();
			Files.write( whereToday, userLines, StandardOpenOption.TRUNCATE_EXISTING );
			Files.write( whereCategories, categories, StandardOpenOption.TRUNCATE_EXISTING );
		}
		catch ( IOException ie )
		{
			System.err.println( "ts.pf unable to prettify user file because "+ ie );
		}
	}

    private void deleteTempFile() {
        Path relPath = Paths.get( tempFile );
        try {
            Files.deleteIfExists( relPath );
        } catch ( IOException ioe ) {
            System.err.println( "LF.dtf() I/O problem.  While deleting "
                    + tempFile +" "+ ioe.toString() );
        }
    }

    /** Gets current tag, there will always be one */
    public Tag gPreviousTag() {
		return tags.peekLast().getExternallyViableVersion();
    }

    /** Gets current tag's start time */
    public Date gPreviousTime() {
        return tags.peekLast().tagTime;
    }

    public boolean canRemoveOne()
    {
    	return tags.size() > 1; // since I always want at least one
    }

    public void removePrevious()
    {
    	tags.removeLast();
    }

    public void setRestartTag( String restartTag )
	{
		if ( restartTag != null && ! restartTag.isEmpty() )
		{
			this.restartTag = restartTag;
			msSinceRestartTag = System.currentTimeMillis();
		}
	}

    /** Still rolling my own ad hoc suite, string focused this time */
    public void runTests() {
		Random oracle = new Random();
        boolean passed = true;
        if(showsResults( problemsWithPrettyDiff(oracle) )) {
			pr( "--" );
            passed = false;
        }
        if(showsResults( problemsWithParseTempTag() )) {
			pr( "--" );
            passed = false;
        }
        if(showsResults( problemsWithEnsureTwoDigits() )) {
			pr( "--" );
            passed = false;
        }
        if ( passed )
            pr( "  yay, no test failed" );
    }

    /** Print and indicate whether theseTests had problems */
	private boolean showsResults( String[] theseTests ) {
		boolean failed = true;
		int first = 0, prob = 0;
        if ( ! theseTests[first].equals("") ) {
            for ( String problem : theseTests ) {
                if ( problem != null ) {
                    pr( problem );
                    prob++;
            }   }
            pr(" * "+ itoa( prob )+ " problems *");
			return failed; 
        } else
			return ! failed;
	}

    /** So much typing just for println. No. */
    void pr( String out ) {
        outChannel.info( out );
    }

	/** More laziness/terseness */
	String itoa( int nn ) {
		return Integer.toString( nn );
	}

    /** Probably replicates a printf feature. Yes. Yes it does: %02d; */
    String ensureTwoDigits( int num ) {
        if ( num < 10 )
            return "0"+ itoa( num );
        else
            return itoa( num );
    }

    /** Check 1 & 2 digit ints. Oracle would be overkill here */
    private String[] problemsWithEnsureTwoDigits() {
        int tests = 2;
        int currProb = 0;
        String[] problems = new String[ tests ];
        problems[ 0 ] = "";

        String oo = ensureTwoDigits( 0 );
        if ( ! oo.equals("00") ) {
            problems[ currProb ] = "ts.pwetd instead of 00 got "+ oo;
            currProb++;
        }
        String sixNine = ensureTwoDigits( 69 );
        if ( ! sixNine.equals("69") ) {
            problems[ currProb ] = "ts.pwetd instead of 69 got |"+ sixNine +"|";
            currProb++;
        }
        return problems;
    }

    /** Struct for Date : String : if_subtask */
    private class WhenTag {
        public Date tagTime;
        public String didWhat;
        public String originallySaid;
        public boolean subT;
        /** Provisional, given that WhenTag was the original form, but I kept it solely in here */
        public WhenTag( Tag fromGui ) {
        	tagTime = fromGui.utilDate;
        	didWhat = fromGui.getTagText().replace( '\t', ' ' );
        	originallySaid = fromGui.getUserText();
        	subT = fromGui.isSubTag();
        }
        public Tag getExternallyViableVersion()
        {
        	Tag alt = new Tag( originallySaid );
        	alt.hackSetTagText( didWhat );
        	alt.utilDate = tagTime;
        	return alt;
        }
    }
}


























