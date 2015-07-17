
// &copy; Nicholas Prado; License: ../../readme.md

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
import java.util.Date;

/** @author Nzen
    The gui and main class of SplainTime. Handles user events
 */
public class SplainTime extends javax.swing.JFrame {

    private TagStore tagHandler;
	private int exitFlubsLeft;
        final int delayms = 60000; // 60 * 1000

    /** Starts gui, starts tagStore */
    public SplainTime() {
        tagHandler = new TagStore();
		exitFlubsLeft = 3;
        initComponents();
        ActionListener taskPerformer = new ActionListener() {

            public void actionPerformed( ActionEvent evt ) {
                Date temp = new Date();
                updateTimeDiffLabel( temp );
                resetExit();
            }
        };
        javax.swing.Timer cron =
			new javax.swing.Timer( delayms, taskPerformer );
        cron.start();
    }

    /** 4TESTS version */
    public SplainTime( boolean testMode ) {
        tagHandler = new TagStore();
        exitFlubsLeft = 0;
        runTests();
        tagHandler.runTests();
        tagHandler.interactiveUFF();
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

	/** This one will mean save & close, I will change the label */
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

    /**  */
    private void pushedOpen(ActionEvent evt) {//GEN-FIRST:event_pushedOpen
        tagHandler.showStoredTags();
    }//GEN-LAST:event_pushedOpen

    /**  */
    private void pushedEnter(ActionEvent evt) {//GEN-FIRST:event_pushedEnter
        String newestDid = jtfForTag.getText();
		// interpret flag, if present
		storeTag( newestDid );
        updateLatestTaskLabel( newestDid );
        jtfForTag.setText(""); // blank the text entry
        jlShowsRoughTime.setText( "0 min" );
    }//GEN-LAST:event_pushedEnter

    /**  */
	private void storeTag( String says ) {
        Date newT = new Date();
        tagHandler.add(newT, says, TagStore.amSubTask);
	}

    /**  */
    private void updateTimeDiffLabel( Date newestT ) {
        long diff = (newestT.getTime() - tagHandler.gPreviousTime().getTime()) / delayms;
        // IMPROVE if ( diff > 36000 )
        jlShowsRoughTime.setText( Long.toString(diff) +" min" );
    }

    /**  */
    private void updateLatestTaskLabel( String whatDid ) {
        // fits 12 capital Ws, 30+ commas
        int strMax = ( whatDid.length() < 20 ) ? whatDid.length() : 20;
        jlSaysPrevious.setText("since "+ whatDid.substring(0, strMax));
    }

    /** Change exit counter, reset button text */
	private void resetExit() {
		exitFlubsLeft = 3;
		if ( ! btnFinish.getText().equals("Finish") )
			btnFinish.setText( "Finish" );
	}

    /**  */
    public void runTests() {
        // check that ultl gives the right number of minutes
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
