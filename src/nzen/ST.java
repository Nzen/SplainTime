
package nzen;

/** @author Nzen
    The main class of SplainTime. Routes info and requests
 */
public class ST {

    private TagStore tagHandler;
    private javax.swing.Timer cron;
    boolean terseAdj = true;
    int exitFlubs = 2;
	private int exitFlubsLeft;
    final int delayms = 60001; // 60 * 1000

    /** Starts gui, starts tagStore */
    public ST() {
		String basicStart = "started up"; // just so it is in one place, rather than two
        tagHandler = new TagStore( basicStart );
		exitFlubsLeft = exitFlubs;
        initComponents();
		updateLatestTaskLabel( tagHandler.gPreviousTag() );
    }

    /** 4TESTS version */
    public ST( boolean testMode ) {
        tagHandler = new TagStore( "whatever" );
        exitFlubsLeft = 0; // NOTE irrelevant for testing, probably :p
        cron = null;
        runTests();
        tagHandler.runTests();
        // tagHandler.interactiveUTF();
    }

    /**  */
    public static void main(String args[]) {
        boolean testing = false; //true;
        if ( testing ) {
            SplainTime nn = new SplainTime( testing );
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new SplainTime().setVisible(true);
            }
        });
    }


}