/*
    &copy; Nicholas Prado; License: ../../readme.md

  Next:
listen to the tagDb
	if I'm going to keep it, rename it tagStore again
initialize the gui
make a model and listeners for the config
*/

package nzen;

import java.util.Date;

/** @author Nzen
    The main class of SplainTime. Routes info and requests
 */
public class StFrame implements MainViewListener {

	private GuiModel viewInfo;

    private TagStore tagHandler;
    boolean terseAdj = true;
    int exitFlubs = 2;
	private int exitFlubsLeft;
    final int delayms = 60001; // 60 * 1000

    /** launches ourself and the gui */
    public static void main(String args[]) {
        boolean testing = false; //true;
        if ( testing ) {
            new MainGui_Swing( testing );
            new TagStore("just testing").runTests();
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	        	// ASK is there some way to ferret sf in here ?
	        	StFrame pictureNail = new StFrame();
	        	MainGui_Swing splainTime = new MainGui_Swing( pictureNail );
	        	pictureNail.setGmListener(splainTime); // le sigh, bootstrapping
	        	splainTime.setVisible(true);
	        	splainTime.launch();
	        }	});
        }
    }

    /** Starts gui, starts tagStore */ // IMPROVE when tagHandler changes
    public StFrame() {
		String basicStart = "started up"; // generic restart text
        tagHandler = new TagStore( basicStart );
		exitFlubsLeft = exitFlubs;
		viewInfo = new GuiModel();
    }

    /** 4TESTS version */
    public StFrame( boolean testMode ) {
        tagHandler = new TagStore( "whatever" );
        exitFlubsLeft = 0; // NOTE irrelevant for testing, probably :p
        tagHandler.runTests();
        // tagHandler.interactiveUTF();
    }

    public void setGmListener( MainGuiModelListener mainGui ) {
    	viewInfo.setGmListener(mainGui);
    }

    /** Update gui model about the time elapsed */
	public void timeChanged( Date fireTime ) {
		viewInfo.setTimeDiff( tagHandler.gElapsedAsText(fireTime) );
	}

	/** remove last or add the tag & remind tagStore to save */
	public void textEntered( String userText, Date then ) {
		// NOTE for when I store my password accidently :\
		if ( userText.startsWith("jmki") ){
			tagHandler.removePrevious();
			// ASK time changed ?
		} else {
			tagHandler.addTag( then, userText );
	        timeChanged(then);
	        viewInfo.setPrevSummary( tagHandler.gTextOfActiveTag() );
	        tagHandler.considerQuickSaving();
		}
	}

	// FIX
	public void requestsOutput() {
		tagHandler.showStoredTags();
	}

	/** Demand tags get saved, likely to restore later */
	public void closing() {
        tagHandler.quickSaveBecauseWeAreClosing();
        // NOTE not wrapUp() so it won't delete the temp file
	}

	/** final tag & clean up our space */
	public void finished() {
		tagHandler.addTag( new Date(), "Shutting down" );
		tagHandler.wrapUp();
		System.exit(0);
	}

}
