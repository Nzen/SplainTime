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

    private TagDb tagHandler;
    boolean terseAdj = true;
    int exitFlubs = 2;
	private int exitFlubsLeft;
    final int delayms = 60001; // 60 * 1000

    /**  */
    public static void main(String args[]) {
        boolean testing = false; //true;
        if ( testing ) {
            new MainGui_Swing( testing );
            new TagDb("just testing").runTests();
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
		String basicStart = "started up"; // just so it is in one place, rather than two
        tagHandler = new TagDb( basicStart );
		exitFlubsLeft = exitFlubs;
		viewInfo = new GuiModel();
    }

    /** 4TESTS version */
    public StFrame( boolean testMode ) {
        tagHandler = new TagDb( "whatever" );
        exitFlubsLeft = 0; // NOTE irrelevant for testing, probably :p
        tagHandler.runTests();
        // tagHandler.interactiveUTF();
    }

    public void setGmListener( MainGuiModelListener mainGui ) {
    	viewInfo.setGmListener(mainGui);
    }

	// FIX
	public void timeChanged( java.util.Date fireTime ) {
		System.out.println( "I don;t know how to change time yet" );
		viewInfo.setTimeDiff( tagHandler.getDiffStr(fireTime) );
	}

	// FIX
	public void textEntered( String userText, Date then ) {
		System.out.println( "I don;t know how to read text yet" );
        tagHandler.add(then, userText, ! TagDb.amSubTask);
        timeChanged(then);
        viewInfo.setPrevSummary( tagHandler.getTextOfPrevious() );
        tagHandler.quickSave();
	}

	// FIX
	public void requestsOutput() {
		tagHandler.showStoredTags();
	}

	// FIX
	public void closing() {
        tagHandler.quickSave();
        // NOTE not wrapUp() so it won't delete the temp file
	}

	// FIX
	public void finished() {
		tagHandler.add(new Date(), "Shutting down", TagDb.amSubTask);
		tagHandler.wrapUp();
		System.exit(0);
	}

}