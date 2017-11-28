/*
 * &copy Nicholas Prado, aka Nzen. But, ISC license.
 */
package ws.nzen.splaintime;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

/**

 @author Nzen
 */
public class ConfigDialog extends javax.swing.JDialog {

    private SplainTime parent;
    private StPreference whatsWanted;

    /**
     Creates new form ConfigDialog
     */
    public ConfigDialog( SplainTime callee, boolean modal ) {
        super( callee, modal );
        whatsWanted = new StPreference();
        initComponents();
        parent = callee;
    }


    public ConfigDialog( JFrame whatever,
    		StPreference initialVals )
	{
		super( whatever, true );
		whatsWanted = initialVals;
        initComponents();
	}


    /**
     This method is called from within the constructor to initialize the form.
     WARNING: Do NOT modify this code. The content of this method is always
     regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        bgAdjust = new ButtonGroup();
        jrbTerseAdjust = new JRadioButton();
        jrbVerboseAdjust = new JRadioButton();
        btnSave = new JButton();
        btnCancel = new JButton();
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jspClicksFinish = new JSpinner();
        jPanel2 = new JPanel();
        jlSaysUndoCode = new JLabel();
        jtfUndo = new JTextField();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Config SplainTime");

        bgAdjust.add(jrbTerseAdjust);
        jrbTerseAdjust.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        jrbTerseAdjust.setText("Terse adjust");

        bgAdjust.add(jrbVerboseAdjust);
        jrbVerboseAdjust.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        jrbVerboseAdjust.setText("Verbose adjust");
        jrbVerboseAdjust.setToolTipText("");

        btnSave.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        btnSave.setText("Save");
        btnSave.setToolTipText("Save settings");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pressedSave(evt);
            }
        });

        btnCancel.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setToolTipText("Close config");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pressedCancel(evt);
            }
        });

        jLabel1.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Clicks to Finish");

        jspClicksFinish.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        jspClicksFinish.setModel(new SpinnerNumberModel(2, 1, 4, 1));
        jspClicksFinish.setToolTipText("Clicks to Finish");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspClicksFinish, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(jspClicksFinish, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        jlSaysUndoCode.setText("Undo code");

        jtfUndo.setText("j8x");

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jtfUndo, GroupLayout.Alignment.TRAILING)
            .addComponent(jlSaysUndoCode, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jlSaysUndoCode)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfUndo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jrbTerseAdjust, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jrbVerboseAdjust, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancel)))))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jrbTerseAdjust)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbVerboseAdjust)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }//GEN-END:initComponents

    private void pressedSave(ActionEvent evt) {//GEN-FIRST:event_pressedSave
        boolean wantsTerseAdjOutput = jrbTerseAdjust.isSelected();
        int clicksToClose = (int)jspClicksFinish.getValue();
        whatsWanted.setFinishFuse( clicksToClose );
        setVisible( false );
    }//GEN-LAST:event_pressedSave

    private void pressedCancel(ActionEvent evt) {//GEN-FIRST:event_pressedCancel
        this.dispose();
    }//GEN-LAST:event_pressedCancel


    public StPreference getConfig()
    {
    	return whatsWanted;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ButtonGroup bgAdjust;
    private JButton btnCancel;
    private JButton btnSave;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel jlSaysUndoCode;
    private JRadioButton jrbTerseAdjust;
    private JRadioButton jrbVerboseAdjust;
    private JSpinner jspClicksFinish;
    private JTextField jtfUndo;
    // End of variables declaration//GEN-END:variables
}