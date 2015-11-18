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
    final static boolean forClient = true;
    LinkedList<TimeTag> activeTags;

    /** Setup the store's output, guarantee one initial task */
    public TagStore( String introText ) {
        activeTags = new LinkedList<>();
        GregorianCalendar willBeName = new GregorianCalendar();

        userFile = itoa(willBeName.get( Calendar.YEAR ))
                +" "+ ensureTwoDigits( willBeName.get( Calendar.MONTH ) +1 )
                // NOTE cal.month is zero indexed
                // IMPROVE maybe str.format %2d ?
                +" "+ ensureTwoDigits( willBeName.get( Calendar.DAY_OF_MONTH ) );
        // pr( "TD() today is "+ userFile ); // 4TESTS
        userFile += " splained.txt";
        tempFile = userFile.replace(' ', '_') + "_tmp.ser";

        initTags( introText );
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
    	/*
    	if there's more than two main tags
			accumulate the rest as text
			cut those
			write those for user
			stfst
    	*/
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

    private void saveTagsForUser( boolean all ) {
    	// IMPROVE write to file
    	String toWrite = "";
    	if ( all ) {
    		for ( TimeTag tt : activeTags ) {
    			toWrite += tt.getFullDescription(elseWhen); // IMPROVE I'm sending different calculations each time
    		}
    	} else {
    		
    	}
    }

    /*    private void flushExtra() {
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
    }*/

    /* 4TESTS, for visual feedback */
    private void writeToDisk( String whichFile, String outStr ) {
		final String here = cl +"wtd ";
        if (whichFile.equals( userFile )) {
            pr( here +"\t"+ whichFile +"\n"+ outStr );
        } else {
            tempFile = outStr;
            pr( here +"\ttemp\t"+ outStr );
        }
    }

    /** Appends outStr userFile or truncates temp with outStr */
    private void writeToDisk( String outStr ) {
        // IMPROVE use imports
		final String here = cl +"wtd ";
        StandardOpenOption howTreat
        	= StandardOpenOption.APPEND;
        Path relPath = Paths.get(userFile);
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
            System.err.println( here +"had some I/O problem."
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
            } catch ( IOException ioe ) {
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
		writeToDisk( outStr ); // 4REAL
        deleteTempFile();
	}

    private void deleteTempFile() {
		final String here = cl +"dtf ";
        Path relPath = Paths.get( tempFile );
        try {
            Files.deleteIfExists( relPath );
        } catch ( IOException ioe ) {
            System.err.println( here +" I/O problem.  While deleting "
                    + tempFile +" "+ ioe.toString() );
        }
    }

    /** Still rolling my own ad hoc suite, string focused this time */
    public void runTests() {
		Random oracle = new Random();
        boolean passed = true;
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
}
