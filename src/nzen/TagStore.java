/*
    &copy; Nicholas Prado; License: ../../readme.md

  Next:
Subtask awareness in clearing tags ?
	depends on TimeTag subtask awareness
*/

package nzen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.nio.file.StandardOpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Random; // for self testing

/** @author Nzen
 */
public class TagStore {
	private final String cl = "ts.";

    String userFile;
    String tempFile;
    final static boolean amSubTask = true;
    final static boolean forClient = true;
    /** @deprecated in favor of the LL of timeTags */
    LinkedList<WhenTag> tags;
    LinkedList<TimeTag> activeTags;
    private SimpleDateFormat toHourMs;

    /** Setup the store's output, guarantee one initial task */
    public TagStore( String introText ) {
        tags = new LinkedList<>();
        activeTags = new LinkedList<>();
        toHourMs = new SimpleDateFormat( "hh:mm.ss a" );
        GregorianCalendar willBeName = new GregorianCalendar();

        userFile = itoa(willBeName.get( Calendar.YEAR ))
                +" "+ ensureTwoDigits( willBeName.get( Calendar.MONTH ) +1 )
                // NOTE cal.month is zero indexed
                // IMPROVE maybe str.format %2d ?
                +" "+ ensureTwoDigits( willBeName.get( Calendar.DAY_OF_MONTH ) );
        // pr( "TD() today is "+ userFile ); // 4TESTS
        userFile += " splained.txt";
        tempFile = userFile.replace(' ', '_') + "_tmp.ser";

		insertFirst( introText );
    }

    /** Ensure a task is ready on startup */
    private void initTags( String basicStartup ) {
    	final String here = cl +"if ";
    	TimeTag fromQuickSave = gQuickSaved();
    	if ( fromQuickSave == null ) {
            pr( here +" no saved tags" ); // 4TESTS
            activeTags.add(new TimeTag( new Date(), basicStartup ));
    	}
    }

    /** deserialize LL and provide last if there is more than one; null otherwise */
    private TimeTag gQuickSaved() {
    	LinkedList<TimeTag> maybeRestored = retrieveTags();
    	if ( maybeRestored == null ) {
    		return null;
    	} else {
    		activeTags = maybeRestored;
    		return activeTags.getLast();
    	}
    }

    /** Saves a new tag with its time (which may adjust on interpretation) */
    public void addTag( Date when, String input ) {
    	activeTags.add( new TimeTag( when, input ) );
    }

    /** Provides the processed text of the latest tag */
    public String gTextOfActiveTag() {
    	return activeTags.peekLast().getTag();
    }

    /** Provides string of time elapsed since the latest tag began */
    public String gElapsedAsText( Date now ) {
    	return activeTags.peekLast().getDiffAsText( now );
    }

    /** Removes a tag, generally when user saves something wrong */
    void removePrevious() {
		activeTags.removeLast();
    	if ( activeTags.size() < 1 ) {
    		// FIX currently unsupported, as I clear them all quickly
    		addTag( new Date(), "Cleared all active tags" );
    		// NOTE there must nonetheless be at least one until we quit
    	}
    }

    /** an opportunity to save tags may or may not save it */
    public void considerQuickSaving() {
    	if ( activeTags.peekLast().overOneMinuteElapsed() ) {
    		// IMPROVE wait until there are 3 requests or something ?
    		saveTagsForSplainTime();
    	}
    	// else, ignore
    }

    /** save tags in anticipation of closing, not wrapping up */
    public void quickSaveBecauseWeAreClosing() {
    	saveTagsForSplainTime();
    }

    /** serialize the tag list */
    private void saveTagsForSplainTime() {
    	final String here = cl +"stfst ";
    	boolean append = true;
    	try {
            Path relPath = Paths.get( tempFile );
            if ( Files.notExists(relPath) ) {
                Files.createFile(relPath);
            }
    		ObjectOutputStream byteEmitter = new ObjectOutputStream(
    				new FileOutputStream( relPath.toFile(), ! append ) );
    		byteEmitter.writeObject( activeTags );
    		byteEmitter.close();
    	} catch( IOException ioe ) {
    		System.err.println( here
    				+"couldn't quick save tags because"
    				+ ioe.toString()  );
    	}
    }

    /** deserialize our LL or return null */
    private LinkedList<TimeTag> retrieveTags() {
    	final String here = cl +"rt ";
    	boolean append = true;
    	try {
            Path relPath = Paths.get( tempFile );
            if ( Files.notExists(relPath) ) {
            	System.out.println( here +"no quicksave to restore from" );
            	return null;
            } else {
            	ObjectInputStream byteReceiver = new ObjectInputStream(
            			new FileInputStream(relPath.toFile()) );
            	@SuppressWarnings("unchecked")
            	// NOTE assumes nothing corrupts the file
				LinkedList<TimeTag> maybeOurs = (LinkedList<TimeTag>)byteReceiver.readObject();
            	byteReceiver.close();
            	return maybeOurs;
            }
    	} catch( IOException | ClassNotFoundException ioORcnfE ) {
    		System.err.println( here
    				+"couldn't restore quick saved tags because"
    				+ ioORcnfE.toString() );
    		return null;
    	}
    }

    private void saveTagsForUser() {
    	// IMPROVE write to file
    }

    // --- below is deprecated

    /** Ensure a task is ready on startup */
	private void insertFirst( String basicStartup ) {
		final String here = cl +"if ";
        String tempSays = gTempSavedTag();
        if ( tempSays.isEmpty() ) {
            pr( here +" no temp file" ); // 4TESTS
            add( new Date(), basicStartup, ! TagStore.amSubTask );
        } else {
            pr( here +"found temp: "+ tempSays ); // 4TESTS
            WhenTag fromPreviousRun = parseTempTag( tempSays );
            if ( fromPreviousRun != null )
                tags.add( fromPreviousRun );
            else
                add( new Date(), basicStartup, ! TagStore.amSubTask );
        }
	}

    /** Add a tag, when started, whether ends previous tag */ // IMPROVE subTask awareness
    void add( Date when, String what, boolean ifSub ) {
        tags.add( new WhenTag( when, what, ifSub ) );
        // pr( "ts.a() got "+ when.toString() +" _ "+ what ); // 4TESTS
    }

    // IMPROVE perhaps by processing or handing that to another
    public String getTextOfPrevious() {
    	return tags.getLast().didWhat;
    }

    /** writes all but the latest to disk; callee checked there's x>1 */ // UNREADY
    private void flushExtra() {
        String outStr = "";
        WhenTag temp;
        Date later;
        for ( int ind = 0; ind < tags.size() -1; ind++ ) {
            temp = tags.get( ind );
            later = tags.get( ind +1 ).tagTime;
            outStr += toHourMs.format( temp.tagTime ) +"\t"
                + prettyDiff( temp.tagTime, later ) +"\t"
                + temp.didWhat + "\r\n";
        }
        while( tags.size() > 1 ) {
            tags.removeFirst();
        }
        // writeToDisk( userFile, outStr ); // 4TESTS
		writeToDisk( forClient, outStr ); // 4REAL
    }

    /** Flush & write current to the cache file */
    void quickSave() {
        if ( tags.size() > 1 ) { // IMPROVE for subtask awareness
            flushExtra();
            // pr("ts.qs "); writeToDisk( tempFile, tempFileFormat(tags.peek()) ); // 4TESTS
            writeToDisk( ! forClient, tempFileFormat(tags.peek()) ); // 4REAL
        } else if (aMinuteSince( tags.peek().tagTime.getTime() )) {
            writeToDisk( ! forClient, tempFileFormat(tags.peek()) );
        }
    }

    private boolean aMinuteSince( long prevTagStamp ) {
        return (System.currentTimeMillis() - prevTagStamp) < 85000; // roughly over a minute
    }

    // IMPROVE
    public String getDiffStr( Date callee ) {
    	return prettyDiff( gPreviousTime(), callee );
    }

    /** Formats the start time and diff for the user */
    private String prettyDiff( Date start, Date end ) {
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
        String mins = ( min >= 0 ) ? itoa( min )+"m " : "";
        return hrs + mins; // NOTE remainder tossed
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
		final String here = cl +"iu ";
        Date now = new Date();
        Date later;
        long nowMs = now.getTime();
        pr( here +" interactive mode begins\n" );
        String[] tagsToSay = new String[] { "nn uu cc DD","bla bla",
                "FileForm", "interactiveUFF", "Integer.toString" }; // aesthetic touch
        java.util.Scanner cli = new java.util.Scanner( System.in );
        for ( int times = 5; times > 0; times-- ) {
            pr( "\n\t\tminute difference ?" );
            int nowPlusMin = cli.nextInt();
            long laterMs = nowMs + ( nowPlusMin * 60000 );
            later = new Date( laterMs );
            pr( toHourMs.format( later ) +"\t"
            		+ prettyDiff(now, later) +"\t"+ tagsToSay[times -1] );
        }
        cli.close();
        pr( here +"interactive mode over\n" );
    }

    /** just toStr of inMem */
    private String tempFileFormat( WhenTag inMem ) {
        // String sub = ( inMem.subT ) ? "s" : "m"; // IMPROVE this is for later
        return Long.toString( inMem.tagTime.getTime() ) +"\t"+ inMem.didWhat; // +"\t"+ sub;
    }

    /** turns date\ttag into a whentag, not subtask aware for now */
    private WhenTag parseTempTag( String fromFile ) {
		final String here = cl +"ptt ";
        String[] date_tag = fromFile.split( "\t" );
        int time = 0, tag = time +1; //, subT = tag +1;
        try {
            Date then = new Date( Long.parseLong(date_tag[ time ]) );
            return new WhenTag( then, date_tag[tag], !amSubTask );
        } catch ( NumberFormatException nfe ) {
            pr( here +" couldn't parse |"+ date_tag[time] +"| as millisec for the date" );
            return null;
        }
    }

    String[] problemsWithParseTempTag() {
		final String here = cl +"pwptt ";
        int tests = 2;
        int currProb = 0;
        String[] problems = new String[ tests ];
        problems[ 0 ] = "";
        Date wasNow = new Date();
        String basic = tempFileFormat(new WhenTag( wasNow, "basic tag", ! amSubTask ));
        WhenTag basicWt = parseTempTag( basic );
        if ( basicWt == null ) {
            problems[ currProb ] = here +" basic parse is null";
            return problems;
        }
        if ( ! wasNow.equals(basicWt.tagTime) ){
            problems[ currProb ] = here +" dates didn't match: \n\ttest "
                    + wasNow.toString() +"\tbecame "+ basicWt.tagTime.toString();
            currProb++;
        }
        return problems;
    }

    // not sure what this was supposed to test
    private String gTempSavedTag( boolean testing ) {
        return tempFile;
    }

    /** Gets blank or tag in a temp file */
    private String gTempSavedTag() {
		final String here = cl +"gtst ";
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
        } catch ( java.io.IOException ioe ) {
            System.err.println( here +" had some I/O problem."
                    + " there's like five options\n "+ ioe.toString() );
        }
        return maybePrevious;
    }

    /* 4TESTS, for visual feedback */
    private void writeToDisk( String whichFile, String outStr ) {
		final String here = cl +"wtd ";
        if (whichFile.equals( userFile )) {
            pr( here +"\t"+ whichFile +"\n"+ outStr );
        } else {
            tempFile = outStr;
            pr( here +"\t temp\t"+ outStr );
        }
    }

    /** Appends outStr userFile or truncates temp with outStr */
    private void writeToDisk( boolean isUser, String outStr ) {
        // IMPROVE use imports
		final String here = cl +"wtd ";
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
        } catch ( IOException ioe ) {
            System.err.println( here +" ad some I/O problem."
                    + " there's like five options\n "+ ioe.toString() );
        }
    }

    /** open the text file */
    void showStoredTags() {
		final String here = cl +"sst ";
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                File myFile = new File( userFile );
                java.awt.Desktop.getDesktop().edit( myFile );
            } catch ( java.io.IOException ioe ) {
                pr( here +"couldn't open, sorry\n"+ ioe.toString() );
            }
        } else {
            pr( here +"couldn't open, sorry" );
        }
    }

    /** delete the temp file ; write the last one */ // UNREADY
    void wrapUp() {
        flushExtra();
        WhenTag ultimate = tags.getLast();
        String outStr = toHourMs.format( ultimate.tagTime ) +"\t"
                + prettyDiff( ultimate.tagTime, new Date() ) +"\t"
                + ultimate.didWhat + "\r\n";
        // writeToDisk( userFile, outStr ); // 4TESTS
		writeToDisk( forClient, outStr ); // 4REAL
        deleteTempFile();
	}

    private void deleteTempFile() {
		final String here = cl +"dtf ";
        Path relPath = Paths.get( tempFile );
        try {
            java.nio.file.Files.deleteIfExists( relPath );
        } catch ( java.io.IOException ioe ) {
            System.err.println( here +" I/O problem.  While deleting "
                    + tempFile +" "+ ioe.toString() );
        }
    }

    /** Gets current tag, there will always be one */
    String gPreviousTag() {
		return tags.peekLast().didWhat;
    }

    /** Gets current tag's start time */
    Date gPreviousTime() {
        return tags.peekLast().tagTime;
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
		final String here = cl +"sr ";
		boolean failed = true;
		int first = 0, prob = 0;
        if ( ! theseTests[first].equals("") ) {
            for ( String problem : theseTests ) {
                if ( problem != null ) {
                    pr( problem );
                    prob++;
            }   }
            pr( here +" * "+ itoa( prob )+ " problems *" );
			return failed; 
        } else
			return ! failed;
	}

    /** so much typing just for println. No. */
    void pr( String out ) {
        System.out.println( out );
    }

	/** More laziness/terseness */
	String itoa( int nn ) {
		return Integer.toString( nn );
	}

    /** Probably replicates a printf feature */
    String ensureTwoDigits( int num ) {
        if ( num < 10 )
            return "0"+ itoa( num );
        else
            return itoa( num );
    }

    /** Check 1 & 2 digit ints. Oracle would be overkill here */
    private String[] problemsWithEnsureTwoDigits() {
		final String here = cl +"pwetd ";
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
            problems[ currProb ] = here +" instead of 69 got |"+ sixNine +"|";
            currProb++;
        }
        return problems;
    }

    /** Struct for Date : String : if_subtask */
    private class WhenTag {
        public Date tagTime;
        public String didWhat;
        public boolean subT;
        public WhenTag( Date tt, String dw, boolean notMainTask ) {
            tagTime = tt;
            didWhat = dw;
            subT = notMainTask;
        }
    }
}
