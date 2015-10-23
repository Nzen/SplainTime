/*
    &copy; Nicholas Prado; License: ../../readme.md

  Next:
Decide whether the exitFlubsAllowed/left goes in the model
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
import java.util.Date;

/** @author Nzen
    The gui and main class of SplainTime. Handles user events
 */
public class MainGui_Swing extends javax.swing.JFrame
			implements MainGuiModelListener {

	private static final long serialVersionUID = 1L;

	private MainViewListener stRoot;

    private javax.swing.Timer cron;
    boolean terseAdj = true;
    private int exitFlubsAllowed = 2;
	private int exitFlubsLeft = exitFlubsAllowed;
    final int delayms = 60001; // 60 * 1000

    /** 4TESTS version */
    public MainGui_Swing( boolean testMode ) {
        exitFlubsLeft = 0; // NOTE irrelevant for testing, probably :p
        cron = null;
        // tagHandler.interactiveUTF();
    }
    //--

    public MainGui_Swing( MainViewListener stFrame ) {
    	stRoot = stFrame;
    	initComponents();
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent evt ) {
                stRoot.timeChanged( new Date() );
                resetExit();
            }
        };
        cron = new javax.swing.Timer( delayms, taskPerformer );
    }

    public void launch() {
    	cron.start();
    	stRoot.timeChanged( new Date() );
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
        setTitle("SplainTime");
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
		if ( exitFlubsLeft < 1 ) { // IMPROVE move to the guiModel
			stRoot.finished();
		} else {
			exitFlubsLeft--;
			btnFinish.setText(Integer.toString( exitFlubsLeft ));
		}
    }//GEN-LAST:event_pushedFinish

    /** Open a list of closed tags for the user */
    private void pushedOpen(ActionEvent evt) {//GEN-FIRST:event_pushedOpen
        stRoot.requestsOutput();
    }//GEN-LAST:event_pushedOpen

    /** Store (interpret?) this tag, reset gui time & tag summary */
    private void pushedEnter(ActionEvent evt) {//GEN-FIRST:event_pushedEnter
    	stRoot.textEntered( jtfForTag.getText(), new Date() );
        cron.restart(); // so it doesn't fire midway into newest tag's first minute
        jtfForTag.setText(""); // blank the text entry
    }//GEN-LAST:event_pushedEnter

    /** update the time diff portion */
    public void diffChanged( String newDiff ) {
    	jlShowsRoughTime.setText(newDiff);
    }

    /** Show portion of the task that fits on the label */
    public void tagChanged( String newText ) {
		int charsFit = Math.min( 25, newText.length() );
        jlSaysPrevious.setText(
        		newText.substring( 0, charsFit ));
    }

    /** Save, but not final save */
    private void closingFrame(WindowEvent evt) {//GEN-FIRST:event_closingFrame
    	stRoot.closing();
    }//GEN-LAST:event_closingFrame

    /** Received new config values */
    private void openConfig(ActionEvent evt) {//GEN-FIRST:event_openConfig
        boolean modal = true;
        new ConfigDialog( new SplainTime(), modal ); // FIX
    }//GEN-LAST:event_openConfig

    /** Change exit counter, reset Finish button text */
	private void resetExit() {
		exitFlubsLeft = exitFlubsAllowed;
		if ( ! btnFinish.getText().equals("Finish") )
			btnFinish.setText( "Finish" );
	}

    /**  */ // IMPROVE extract to config or config model
    void applyConfig( boolean terseAdjOutput, int timesToClose ) {
        sAdjustOutput( terseAdjOutput );
        sClicksFinish( timesToClose );
    }
    void sAdjustOutput( boolean terseAdjOutput ) {
        terseAdj = terseAdjOutput;
    }
    void sClicksFinish( int newClickCount ) {
        exitFlubsAllowed = newClickCount;
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
