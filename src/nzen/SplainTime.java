/*
    &copy; Nicholas Prado; License: ../../readme.md

  Next:
Check that all this functionality is safely out
rename the .form
delete this class
*/

package nzen;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import java.util.Calendar;
import java.util.Random;
import java.util.Date;

/** @author Nzen
    The gui and main class of SplainTime. Handles user events
    @deprecated supplant with MainGui_swing
 */
public class SplainTime extends javax.swing.JFrame {

    private TagDb tagHandler;
    private javax.swing.Timer cron;
    boolean terseAdj = true;
    int exitFlubs = 2;
	private int exitFlubsLeft;
    final int delayms = 60001; // 60 * 1000

    /** Starts gui, starts tagStore */
    public SplainTime() {
		String basicStart = "started up"; // just so it is in one place, rather than two
        tagHandler = new TagDb( basicStart );
		exitFlubsLeft = exitFlubs;
        initComponents();
		updateLatestTaskLabel( tagHandler.gPreviousTag() );
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent evt ) {
                Date temp = new Date();
                updateTimeDiffLabel( temp );
                tagHandler.quickSave();
                resetExit();
            }
        };
        cron = new javax.swing.Timer( delayms, taskPerformer );
        cron.start();
    }

    /** 4TESTS version */
    public SplainTime( boolean testMode ) {
        tagHandler = new TagDb( "whatever" );
        exitFlubsLeft = 0; // NOTE irrelevant for testing, probably :p
        cron = null;
        runTests();
        tagHandler.runTests();
        // tagHandler.interactiveUTF();
        // consider doing a text entry version instead / in addition to ?
    }

    /**
    Netbeans generated gui initialization
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfForTag = new JTextField();
        btnFinish = new JButton();
        jlSaysPrevious = new JLabel();
        btnOpensFile = new JButton();
        jlShowsRoughTime = new JLabel();
        btnConfig = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("What now ?");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closingFrame(evt);
            }
        });

        jtfForTag.setFont(new Font("Times New Roman", 0, 18)); // NOI18N
        jtfForTag.setToolTipText("Describe this moment");
        jtfForTag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pushedEnter(evt);
            }
        });

        btnFinish.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        btnFinish.setText("Finish");
        btnFinish.setToolTipText("Press several to close");
        btnFinish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pushedFinish(evt);
            }
        });

        jlSaysPrevious.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        jlSaysPrevious.setText("since started up");

        btnOpensFile.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        btnOpensFile.setText("Open");
        btnOpensFile.setToolTipText("Show saved tags");
        btnOpensFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pushedOpen(evt);
            }
        });

        jlShowsRoughTime.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        jlShowsRoughTime.setText("0 min");

        btnConfig.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        btnConfig.setText("Config");
        btnConfig.setToolTipText("Choose settings");
        btnConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openConfig(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnOpensFile)
                        .addGap(30, 30, 30)
                        .addComponent(btnConfig)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFinish))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfForTag, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlShowsRoughTime, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jlSaysPrevious, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jlSaysPrevious)
                    .addComponent(jlShowsRoughTime))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfForTag, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(btnFinish)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOpensFile)
                        .addComponent(btnConfig)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	/** Strengthen exit signal OR end all tasks & delete today's cache file */
    private void pushedFinish(ActionEvent evt) {//GEN-FIRST:event_pushedFinish
		if ( exitFlubsLeft < 1 ) {
			storeTag( "Shutting down" );
			tagHandler.wrapUp();
            System.exit( 0 );
            // exit(1);
		} else {
			exitFlubsLeft--;
			btnFinish.setText(Integer.toString( exitFlubsLeft ));
		}
    }//GEN-LAST:event_pushedFinish

    /** Open a list of closed tags for the user */
    private void pushedOpen(ActionEvent evt) {//GEN-FIRST:event_pushedOpen
        tagHandler.showStoredTags();
    }//GEN-LAST:event_pushedOpen

    /** Store (interpret?) this tag, reset gui time & tag summary */
    private void pushedEnter(ActionEvent evt) {//GEN-FIRST:event_pushedEnter
        String newestDid = jtfForTag.getText();
		// interpret flag, if present
		storeTag( newestDid );
        updateLatestTaskLabel( newestDid );
        cron.restart(); // so it doesn't fire midway into newest tag's first minute
        jtfForTag.setText(""); // blank the text entry
        updateTimeDiffLabel( new Date() );
        // jlShowsRoughTime.setText( "0 min" );
        resetExit();
    }//GEN-LAST:event_pushedEnter

    /** Save, but not final save */
    private void closingFrame(WindowEvent evt) {//GEN-FIRST:event_closingFrame
        tagHandler.quickSave();
        // NOTE not wrapUp() so it won't delete the temp file
    }//GEN-LAST:event_closingFrame

    /** Received new config values */
    private void openConfig(ActionEvent evt) {//GEN-FIRST:event_openConfig
        boolean modal = true;
        ConfigDialog settings = new ConfigDialog( this, modal );
        settings.setVisible( true );
    }//GEN-LAST:event_openConfig

    /** Store the tag with the current time */
	private void storeTag( String says ) {
        Date now = new Date();
        Date newT = adjustedTime( says, now );
        if ( ! now.equals(newT) ) { // IMPROVE && config.adjOut == bla
            says += " ; adjusted time at "+ now.toString(); // IMPROVE use a date formatter, please
        }
        tagHandler.add(newT, says, ! TagDb.amSubTask); // IMPROVE subTask awareness
	}

    /** now, or subtracted by adjust flag */
    private Date adjustedTime( String fullTag, Date now ) {
        if ( ! fullTag.startsWith( "-" ) )
            return now; // normal case
        String adjFlag = fullTag.substring( 1, fullTag.indexOf(' ') );
        if ( adjFlag.contains(":") ) { // ex 8:00
            // System.out.println( "st.at( ::: ) starts with "+ fullTag ); // 4TESTS
            return adjustToHhmmFormat( adjFlag, now );
        }
		// else is simple adjust. ex -13
        int adjMinutes = Integer.parseInt( adjFlag );
        int adjMillis = adjMinutes * 60000; // 60sec * 1000ms
        return new Date( now.getTime() - adjMillis );
    }

    /** parse time from hh:mm and round down to before 'now' */
    private Date adjustToHhmmFormat( String adjFlag, Date now ) {
        String strHours = adjFlag.substring( 0, adjFlag.indexOf(':') );
        String strMins = adjFlag.substring( adjFlag.indexOf(':') +1, adjFlag.length() );
        System.out.println( "st.athm() "+adjFlag+" found "+ strHours +" : "+ strMins
            +"\tand got "+ now.toString() ); // 4TESTS
        int adjHours = Integer.parseInt( strHours );
        int adjMinutes = Integer.parseInt( strMins );
        java.util.GregorianCalendar timeKnob = new java.util.GregorianCalendar();
        timeKnob.set( Calendar.HOUR, adjHours );
        timeKnob.set( Calendar.MINUTE, adjMinutes );
        // System.out.println("st.athm() initial calc is "+ timeKnob.getTime().toString()); // 4TESTS

        long tempAdjMilli = timeKnob.getTimeInMillis();
        long nowMilli = now.getTime();
        if ( nowMilli < tempAdjMilli ) { // adjusted doesn't match current AM/PM
            System.out.println("st.athm() woo adjusting am/pm"); // 4TESTS
            int apIs = timeKnob.get( Calendar.AM_PM );
            if ( apIs == Calendar.AM )
                apIs = Calendar.PM; // so swap it
            else
                apIs = Calendar.AM;
            timeKnob.set( Calendar.AM_PM, apIs );
        } // or subtract 12h-milli :p
        // System.out.println("st.athm() post amPM calc is "+ timeKnob.getTime().toString()); // 4TESTS
        return timeKnob.getTime();
    }

    /** Tests for adjustToHhmmFormat() */
    String[] problemsWithAdjustHhMm( Random oracle ) {
		// no change, minute flag, time flag
        int tests = 1, pInd = 0;
        String here = "st.pwahm() ";
        String[] problems = new String[ tests ];
        problems[pInd] = ""; // successful case if it stays this way

        int initHr = oracle.nextInt( 11 ); // NOTE zero offset, le sigh
        int initMin = oracle.nextInt( 58 );
        String initChars = Integer.toString( initHr ) +":"
                +(( initMin < 10 )?"0":"") + Integer.toString( initMin );
        java.util.GregorianCalendar timeKnob = new java.util.GregorianCalendar();
        timeKnob.set( Calendar.HOUR, initHr );
        timeKnob.set( Calendar.MINUTE, initMin );
        // System.out.println( here +"initial calc is "
                // + initChars +" is "+ timeKnob.getTime().toString() ); // 4TESTS
        int makeItLess = 66666; // NOTE milliseconds
        Date aTime = timeKnob.getTime();
        Date receivedTime = adjustToHhmmFormat(
                initChars, new Date( aTime.getTime() - makeItLess ));
        if ( ! aTime.after(receivedTime) ) {
            problems[ pInd ] = here +"aT "+ aTime.toString() +" }} rT "
                    + receivedTime.toString() +" }} sT "
                    + new Date( aTime.getTime() - makeItLess ).toString()
                    +"\tdiff is "+ Long.toString(aTime.getTime() - receivedTime.getTime());
            pInd++;
        }
        return problems;
    }

    /** Tests for adjustedTime() */
    String[] problemsWithAdjustedTime( Random oracle ) {
		// no change, minute flag, time flag
        int tests = 2, pInd = 0;
        String here = "st.pwat() ";
        String[] problems = new String[ tests ];
        problems[pInd] = ""; // successful case if it stays this way
        Date aTime = new Date( System.currentTimeMillis() - oracle.nextInt( 100000 ) );
        String noFlag = "";
        if ( ! adjustedTime(noFlag, aTime).equals(aTime) ) {
            long became = adjustedTime(noFlag, aTime).getTime();
            problems[ pInd ] = here +"adjusted time despite no flag by "
                    + Long.toString( aTime.getTime() - became ) +" milliSec";
            pInd++;
        }
        int minutes = oracle.nextInt(60) +1; // to eliminate 0
        String minusMinutes = "-"+ Integer.toString( minutes );
        long minusMillis = minutes * 60000; // 60sec * 1000ms
        Date shouldBe = new Date( aTime.getTime() - minusMillis );
        String withAdjFlag = minusMinutes +" whatever";
        if ( ! adjustedTime(withAdjFlag, aTime).equals(shouldBe) ) {
            long became = adjustedTime(withAdjFlag, aTime).getTime();
            problems[ pInd ] = here +"wrong minute adjusted time: off by "
                    + Long.toString( shouldBe.getTime() - became ) +"ms";
            pInd++;
        }
        java.util.GregorianCalendar afternoon // IMPROVE use oracle
                = new java.util.GregorianCalendar( 1999, 10, 12, 16, 20 ); // arbitrary date
        Date theAfternoon = afternoon.getTime();
        String woopsEnteredLate = "-8:00 hung over all morning";
        aTime = adjustedTime( woopsEnteredLate, theAfternoon );
        java.util.GregorianCalendar morning
                = new java.util.GregorianCalendar( 1999, 10, 12, 8, 00 );
        Date whenIShouldHaveEntered = morning.getTime();
        if ( ! aTime.equals(whenIShouldHaveEntered) ) {
            long became = aTime.getTime();
            problems[ pInd ] = here +"wrong clock adjusted time: off by "
                    + Long.toString( whenIShouldHaveEntered.getTime() - became ) +"ms\n"
                    + "  should be "+ whenIShouldHaveEntered.toString()
                    + " - became "+ aTime.toString();
            pInd++;
        }
        /*
        Above is a bad test since aT() isn't actually functional.
        it relies on using the current time.
        */
        return problems;
    }

    /** Show minutes elapsed for the current task */
    private void updateTimeDiffLabel( Date now ) {
        // ASK should I have ts calculate it this way or fanciest way?
        long diff = (now.getTime() - tagHandler.gPreviousTime().getTime()) / delayms;
        // IMPROVE if ( diff > 36000 )
        jlShowsRoughTime.setText( Long.toString(diff) +" min" );
    }

    /** Show portion of the task that fits on the label */
    private void updateLatestTaskLabel( String whatDid ) {
        if (whatDid.startsWith( "-" )) {
            int startsAfter = whatDid.indexOf( " " );
            whatDid = whatDid.substring( startsAfter );
        } // simple to add {} above, but what if tag is " } -8:00 bla " ?
        // fits between 12 capital Ws || 30+ commas
		int charsFit = 20;
		if ( whatDid.length() >= charsFit )
			whatDid = whatDid.substring( 0, charsFit );
        jlSaysPrevious.setText( "since "+ whatDid );
    }

    /** Change exit counter, reset Finish button text */
	private void resetExit() {
		exitFlubsLeft = exitFlubs;
		if ( ! btnFinish.getText().equals("Finish") )
			btnFinish.setText( "Finish" );
	}

    /**  */
    void applyConfig( boolean terseAdjOutput, int timesToClose ) {
        sAdjustOutput( terseAdjOutput );
        sClicksFinish( timesToClose );
    }

    void sAdjustOutput( boolean terseAdjOutput ) {
        terseAdj = terseAdjOutput;
    }

    void sClicksFinish( int newClickCount ) {
        exitFlubs = newClickCount;
    }

    /**  */
    public void runTests() {
        Random oracle = new Random();
        if (showsResults( problemsWithAdjustedTime(oracle) ))
            System.out.println( "--" );
        if (showsResults( problemsWithAdjustHhMm(oracle) ))
            System.out.println( "--" );
        System.out.println( "- Tests over -" );
    }

    /** Indicate whether theseTests had problems ; hmm if only I could extract this ... */
	private boolean showsResults( String[] theseTests ) {
		boolean failed = true;
		int first = 0;
        if ( ! theseTests[first].equals("") ) {
            for ( String problem : theseTests )
                System.out.println( problem );
			return failed; 
        } else
			return ! failed;
	}

    /**  */
    public static void main(String args[]) {
        boolean testing = true;
        if ( !testing ) {
            SplainTime nn = new SplainTime( testing );
        } else
        /* woo, lambda form instead of Runnable anon class */
            java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new SplainTime().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnConfig;
    private JButton btnFinish;
    private JButton btnOpensFile;
    private JLabel jlSaysPrevious;
    private JLabel jlShowsRoughTime;
    private JTextField jtfForTag;
    // End of variables declaration//GEN-END:variables
}
