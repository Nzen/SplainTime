
// &copy; Nicholas Prado; License: ../../readme.md
/* FOR ADJUST BRANCH
add a hour formatted adjust flag : -8:00 walked in
*/

package nzen;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Date;

/** @author Nzen
    The gui and main class of SplainTime. Handles user events
 */
public class SplainTime extends javax.swing.JFrame {

    private TagStore tagHandler;
    private javax.swing.Timer cron;
	private int exitFlubsLeft;
    final int delayms = 60001; // 60 * 1000

    /** Starts gui, starts tagStore */
    public SplainTime() {
		String basicStart = "started up"; // just so it is in one place, rather than two
        tagHandler = new TagStore( basicStart );
		exitFlubsLeft = 3;
        initComponents();
		updateLatestTaskLabel( basicStart );
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
        tagHandler = new TagStore( "whatever" );
        exitFlubsLeft = 0; // NOTE irrelevant for testing, probably :p
        cron = null;
        runTests();
        tagHandler.runTests();
        tagHandler.interactiveUTF();
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

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("What now ?");
        setResizable(false);

        jtfForTag.setFont(new Font("Times New Roman", 0, 18)); // NOI18N
        jtfForTag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pushedEnter(evt);
            }
        });

        btnFinish.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        btnFinish.setText("Finish");
        btnFinish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pushedFinish(evt);
            }
        });

        jlSaysPrevious.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        jlSaysPrevious.setText("since started up");

        btnOpensFile.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        btnOpensFile.setText("Open");
        btnOpensFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pushedOpen(evt);
            }
        });

        jlShowsRoughTime.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        jlShowsRoughTime.setText("0 min");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnOpensFile)
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
                    .addComponent(btnOpensFile))
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
        jlShowsRoughTime.setText( "0 min" );
    }//GEN-LAST:event_pushedEnter

    /** Store the tag with the current time */
	private void storeTag( String says ) {
        Date newT = adjustedTime( says, new Date() );
        tagHandler.add(newT, says, ! TagStore.amSubTask); // IMPROVE subTask awareness
	}

    /** now, or subtracted by adjust flag */
    private Date adjustedTime( String fullTag, Date now ) {
        if ( ! fullTag.startsWith( "-" ) )
            return now; // normal case
        String adjFlag = fullTag.substring( 1, fullTag.indexOf(' ') );
        if ( adjFlag.contains(":") ) { // ex 8:00
            return new Date(); // IMPROVE I'll handle this later
            /* how to AM/PM ?
            well, definitely in the past. If I'm past 12pm, time is definitely am
            or adopt an am/pm between previous and now.
            */
        } // else is simple adjust
        int adjMinutes = Integer.parseInt( adjFlag );
        int adjMillis = adjMinutes * 60000; // 60sec * 1000ms
        return new Date( now.getTime() - adjMillis );
    }

    String[] problemsWithAdjustedTime( Random oracle ) {
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
            problems[ pInd ] = here +"wrong adjusted time: off by "
                    + Long.toString( shouldBe.getTime() - became ) +"ms";
            pInd++;
        }
        // deliberately distinct means of calculating the new time
        // java.time.Instant iNow = aTime.toInstant(); // goddamn. java8 only

        // IMPROVE clock flag adjust
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
		exitFlubsLeft = 3;
		if ( ! btnFinish.getText().equals("Finish") )
			btnFinish.setText( "Finish" );
	}

    /**  */
    public void runTests() {
        Random oracle = new Random();
        if (showsResults( problemsWithAdjustedTime(oracle) ))
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
        boolean testing = false; // true;
        if ( testing ) {
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
    private JButton btnFinish;
    private JButton btnOpensFile;
    private JLabel jlSaysPrevious;
    private JLabel jlShowsRoughTime;
    private JTextField jtfForTag;
    // End of variables declaration//GEN-END:variables
}
