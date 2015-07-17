/*
 * &copy Nicholas Prado, aka Nzen. But, ISC license.
 */

package nzen;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.nio.file.StandardOpenOption;
/**
 * @author Nzen
 */
public class TagStore {

    String userFile;
    String tempFile;
    final static boolean amSubTask = true;
    final static boolean forClient = true;
    LinkedList<WhenTag> tags;

    public TagStore() {
        /*
        check for temp file
        send up the current time diff, if appropriate
        */
        tags = new LinkedList<>();
        GregorianCalendar willBeName = new GregorianCalendar();
        userFile = Integer.toString(willBeName.get( Calendar.YEAR ))
                +" "+ Integer.toString(willBeName.get( Calendar.MONTH ))
                +" "+ Integer.toString(willBeName.get( Calendar.DAY_OF_MONTH ));
        System.out.println( "TD() today is "+ userFile ); // 4TESTS
        tempFile = userFile + " tmp.txt";
        userFile += " splained.txt";
    }

    /** Add a tag, when started, whether ends previous tag */
    void add( Date when, String what, boolean ifSub ) {
        tags.add( new WhenTag( when, what, ifSub ) );
    }

    /** Subtract time from previous: client entered the tag late */
    void adjustPrevious( int minutesOff ) {
        if ( tags.size() > 0 ) {
            WhenTag was = tags.removeLast();
            Long wasTime = was.tagTime.getTime();
            int millisecondsOff = minutesOff * 60000;
            was.tagTime = new Date( wasTime - millisecondsOff );
            tags.add( was );
        }
        /*
        adjust the temp file or
        just forget it
        */
    }

    public String[] problemsWithAdjustPrevious() {
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
        java.util.Random oracle = new java.util.Random();
        while ( off == 0 )
            off = oracle.nextInt( 75 );
        Date wheneverLessRand = new Date();
        /* FIX finish procedural, probably have to use Calendar to adjust it */
        return problems;
    }

    /** writes all but the latest to disk */
    private void flushExtra() {
        String outStr = "";
        for (Iterator<WhenTag> it = tags.iterator(); it.hasNext();) {
            WhenTag temp = it.next();
            if ( it.hasNext() ) {
                // FIX put the difference
                // long diff = (temp.getTime() - latestCheckin.getTime()) / 1000;
 // NOTE below indicies are hand counted ;; from SplainOld
        outStr = temp.tagTime.toString().substring(11, 19) +"\t"
                + jlShowsRoughTime.getText() +"\t"+ newestDid +"\r\n";

                outStr += temp.tagTime.toString() +"\t"+ temp.didWhat;
                it.remove();
            } // else, keep the last one
        }
        writeToDisk( userFile, outStr );
    }

    /** Flush & write current to the cache file */
    void quickSave() {
        if ( tags.size() > 1 )
            flushExtra();
        writeToDisk( tempFile, tempFileFormat(tags.peek()) );
        // writeToDisk( forClient, tempFileFormat(tags.peek()) );
    }

    private String tempFileFormat( WhenTag inMem ) {
        String sub = ( inMem.subT ) ? "s" : "m";
        return inMem.tagTime.toString() +"\t"+ inMem.didWhat +"\t"+ sub;
    }

    /* for while I'm testing, so I don't need to open files etc */
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

	void wrapUp() {
		// delete the temp file ? Can't undo that. I made them press several times
	}

    /** Gets current tag */
    String gPreviousTag() {
        if ( tags.size() > 0 )
            return tags.peek().didWhat;
        else
            return "Started";
    }

    /** Gets current start time, or null */
    Date gPreviousTime() {
        if ( tags.size() > 0 ) // IMPROVE just insert one at the start
            return tags.peek().tagTime;
        else
            return null;
    }

    /** Struct for Date : String */
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
