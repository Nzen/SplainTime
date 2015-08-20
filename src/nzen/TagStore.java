/*
    &copy Nicholas Prado; License: ../../readme.md
 */

package nzen;

import java.io.File;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Random; // for self testing

/** @author Nzen
 */
public class TagStore {

    String userFile;
    String tempFile;
    final static boolean amSubTask = true;
    final static boolean forClient = true;
    LinkedList<WhenTag> tags;
    private SimpleDateFormat toHourMs;

    /** Setup the store's output, guarantee an initial task */
    public TagStore( String introText ) {
        tags = new LinkedList<>();
        toHourMs = new SimpleDateFormat( "hh:mm.ss a" );
		insertFirst( introText );
		// IMPROVE check, maybe restore from cache
        GregorianCalendar willBeName = new GregorianCalendar();

        userFile = itoa(willBeName.get( Calendar.YEAR ))
                +" "+ ensureTwoDigits( willBeName.get( Calendar.MONTH ) +1 ) // NOTE zero indexed
                +" "+ ensureTwoDigits( willBeName.get( Calendar.DAY_OF_MONTH ) );
        // System.out.println( "TD() today is "+ userFile ); // 4TESTS
        tempFile = userFile + " tmp.txt";
        userFile += " splained.txt";
    }

    /** Ensure a task is ready on startup */
	private void insertFirst( String basicStartup ) {
		// IMPROVE check for a restoration file with today's dates
        add( new Date(), basicStartup, ! TagStore.amSubTask );
	}

    /** Add a tag, when started, whether ends previous tag */
    void add( Date when, String what, boolean ifSub ) {
        tags.add( new WhenTag( when, what, ifSub ) );
        // pr( "ts.a() got "+ when.toString() +" _ "+ what ); // 4TESTS
    }

    /** Subtract time from previous: client entered the tag late; premature */
    // ASK will I need this if adjustment is before previous?
    void adjustPrevious( int minutesOff ) { // otherwise, premature
		WhenTag was = tags.removeLast();
		Long wasTime = was.tagTime.getTime();
		int millisecondsOff = minutesOff * 60000;
		was.tagTime = new Date( wasTime - millisecondsOff );
		tags.add( was );
        /*
        adjust the temp file time
        */
    }

    /** does ap() fix previous? */
    public String[] problemsWithAdjustPrevious( Random oracle ) {
        int numTests = 1; int failedTests = 0;
        String[] problems = new String[ numTests ];
        problems[0] = "";
        LinkedList<WhenTag> store = tags; // no matter what it is
        Date whenever = new Date();
        tags = new LinkedList<>();
        tags.add(new WhenTag( whenever, "", ! TagStore.amSubTask ));
        int off = 0;
        adjustPrevious( off );
        if ( whenever.getTime() != tags.peek().tagTime.getTime() ) {
            problems[ failedTests ] = "TS.pwap(0) subtract 0 was different";
            failedTests++;
        }
        while ( off == 0 ) // NOTE already tested zero above
            off = oracle.nextInt( 75 );
        Date wheneverLessRand = new Date();
        /* FIX finish procedural, probably have to use Calendar to adjust it */
        tags = store; // reset
        return problems;
    }

    /** writes all but the latest to disk; callee checked there's x>1 */ // UNREADY
    private void flushExtra() {
        String outStr = "";
        WhenTag temp;
        Date later;
        for ( int ind = 0; ind < tags.size() - 1; ind++ ) {
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
        if ( tags.size() > 1 ) // IMPROVE later for subtasks
            flushExtra();
        // writeToDisk( tempFile, tempFileFormat(tags.peek()) ); // ASK should this be conditional?
        // writeToDisk( ! forClient, tempFileFormat(tags.peek()) ); // 4REAL
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
        return hrs + min+"m "; // NOTE remainder tossed
    }

    /** does pd show correct diff? */
	String[] problemsWithPrettyDiff( Random oracle ) {
		/*
		no difference
		arbitrary difference
		*/
		return new String[]{""};
	}

    /** to test the above quickly, by hand */
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
            pr( toHourMs.format( later ) +"\t"+ prettyDiff(now, later) +"\t"+ tagsToSay[times -1] );
        }
        pr( "interactive mode over\n" );
    }

    /** just toStr of inMem */
    private String tempFileFormat( WhenTag inMem ) {
        // String sub = ( inMem.subT ) ? "s" : "m"; // IMPROVE this is for later
        return inMem.tagTime.toString() +"\t"+ inMem.didWhat; // +"\t"+ sub;
    }

    /* 4TESTS, for visual feedback */
    private void writeToDisk( String whichFile, String outStr ) {
        System.out.println( "\t"+ whichFile +"\n"+ outStr );
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
        java.nio.file.Path relPath = java.nio.file.Paths.get(whichFile);
        try {
            if ( java.nio.file.Files.notExists(relPath) ) {
                java.nio.file.Files.createFile(relPath);
            }
            try (java.io.BufferedWriter paper = java.nio.file.Files.newBufferedWriter(
                    relPath, java.nio.charset.StandardCharsets.UTF_8, howTreat )
            )   {
                paper.append( outStr );
            }
        } catch ( java.io.IOException ioe ) {
            System.err.println( "LF.rsf() had some I/O problem."
                    + " there's like five options\n "+ ioe.toString() );
        }
    }

    /** open the text file */
    void showStoredTags() {
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                File myFile = new File( userFile );
                java.awt.Desktop.getDesktop().edit( myFile );
            } catch ( java.io.IOException ioe ) {
                System.out.println( "couldn't open, sorry\n"+ ioe.toString() );
            }
        } else {
            System.out.println( "couldn't open, sorry" );
        }
    }

    /** delete the temp file ; write the last one */ // UNREADY
    void wrapUp() {
		// IMPROVE delete temp file
        flushExtra();
        WhenTag ultimate = tags.getLast();
        String outStr = toHourMs.format( ultimate.tagTime ) +"\t"
                + prettyDiff( ultimate.tagTime, new Date() ) +"\t"
                + ultimate.didWhat + "\r\n";
        // writeToDisk( userFile, outStr ); // 4TESTS
		writeToDisk( forClient, outStr ); // 4REAL
	}

    /** Gets current tag, there will always be one */
    String gPreviousTag() {
		return tags.peek().didWhat;
    }

    /** Gets current tag's start time */
    Date gPreviousTime() {
        return tags.peekLast().tagTime;
    }

    /** Still rolling my own ad hoc suite, string focused this time */
    public void runTests() {
		Random oracle = new Random();
        if(showsResults( problemsWithPrettyDiff(oracle) ))
			pr( "--" );
        if(showsResults( problemsWithAdjustPrevious(oracle) ))
			pr( "--" );
    }

    /** Print and indicate whether theseTests had problems */
	private boolean showsResults( String[] theseTests ) {
		boolean failed = true;
		int first = 0;
        if ( ! theseTests[first].equals("") ) {
            for ( String problem : theseTests )
                pr( problem );
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

    String ensureTwoDigits( int num ) {
        if ( num < 10 )
            return "0"+ itoa( num );
        else
            return itoa( num );
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
