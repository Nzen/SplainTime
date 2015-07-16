
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

/**
 * @author Nzen
 */
public class SplainOld extends javax.swing.JFrame {

    final static int delayms = 60000; // milliseconds
    private String outFile;
    private String[] recentTags;
    final int maxTags = 3; // more than that and I may lose it.
    private int latestTag; // you want a stack?
    private Date latestCheckin;

    public SplainOld() {
        GregorianCalendar willBeName = new GregorianCalendar();
        outFile = Integer.toString(willBeName.get( Calendar.YEAR ))
                +" "+ Integer.toString(willBeName.get( Calendar.MONTH ))
                +" "+ Integer.toString(willBeName.get( Calendar.DAY_OF_MONTH ))
                + " splained.txt";
        initComponents();
        // IMPROVE make a close listener, and flushTags() in response
        latestCheckin = new Date();

        // IMPROVE what if I had to restart? check if that file is there
        // else that diff will be wrong

        ActionListener taskPerformer = (ActionEvent evt) -> {
            Date temp = new Date();
            updateTimeDiffLabel( temp );
            if ( latestTag > 0 ) {
                flushTags();
            }
        };
        recentTags = new String[ maxTags ];
        latestTag = 0;
        javax.swing.Timer cron = new javax.swing.Timer( delayms, taskPerformer );
        cron.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfForTag = new JTextField();
        btnSave = new JButton();
        jlSaysPrevious = new JLabel();
        btnOpensFile = new JButton();
        jlShowsRoughTime = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("What are you doing?");
        setResizable(false);

        jtfForTag.setFont(new Font("Times New Roman", 0, 18)); // NOI18N
        jtfForTag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pushedEnter(evt);
            }
        });

        btnSave.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pushedSave(evt);
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
                        .addComponent(btnSave))
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
                    .addComponent(btnSave)
                    .addComponent(btnOpensFile))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pushedSave(ActionEvent evt) {//GEN-FIRST:event_pushedSave
        // IMPROVE, leave a date difference in the tag
        Date newT = new Date();
        updateTimeDiffLabel( newT );
        latestCheckin = newT;
        if ( latestTag >= maxTags ) {
            flushTags();
        } // IMPROVE the date output format
        String newestDid = jtfForTag.getText(); // NOTE below indicies are hand counted
        recentTags[ latestTag ] = latestCheckin.toString().substring(11, 19) +"\t"
                + jlShowsRoughTime.getText() +"\t"+ newestDid +"\r\n";
        updateLatestTaskLabel( newestDid );
        latestTag++;
        jtfForTag.setText(""); // blank the text entry
        jlShowsRoughTime.setText( "0 min" );
    }//GEN-LAST:event_pushedSave

    private void pushedOpen(ActionEvent evt) {//GEN-FIRST:event_pushedOpen
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                File myFile = new File( outFile );
                java.awt.Desktop.getDesktop().edit( myFile );
            } catch ( java.io.IOException ioe ) {
                System.out.println( "couldn't open, sorry\n"+ ioe.toString() );
            }
        } else {
            System.out.println( "couldn't open, sorry" );
        }
        /*
        ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "myfile.txt");
        pb.start();
        ----------
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("C:\\Windows\\notepad.exe C:\\test.txt");
        ------
        Runtime.getRuntime().exec(new String[]{"cmd.exe","/c","text.txt"});
        -------
        I love that I've gotten here again. (Recently moved to mature IPC in python)
        But, I am after mvp, not a robust solution.
        Feature Tabled.
        */
    }//GEN-LAST:event_pushedOpen

    private void pushedEnter(ActionEvent evt) {//GEN-FIRST:event_pushedEnter
        pushedSave( evt );
    }//GEN-LAST:event_pushedEnter

    private void updateTimeDiffLabel( Date newestT ) {
        long diff = (newestT.getTime() - latestCheckin.getTime()) / delayms;
        // IMPROVE if ( diff > 36000 )
        jlShowsRoughTime.setText( Long.toString(diff) +" min" );
    }

    private void updateLatestTaskLabel( String whatDid ) {
        // fits 12 capital Ws, 30+ commas
        int strMax = ( whatDid.length() < 20 ) ? whatDid.length() : 20;
        jlSaysPrevious.setText("since "+ whatDid.substring(0, strMax));
    }

    /** Saves the tags to file and zeros the buffer index */
    private void flushTags() {
        String outStr = "";
        for ( int ind = 0; ind < latestTag; ind++ ) {
            outStr += recentTags[ ind ];
        }

        // improve with import
        java.nio.file.Path relPath = java.nio.file.Paths.get(outFile);
        try {
            if ( java.nio.file.Files.notExists(relPath) ) {
                java.nio.file.Files.createFile(relPath);
            }
            try (java.io.BufferedWriter paper = java.nio.file.Files.newBufferedWriter(
                    relPath,
                    java.nio.charset.StandardCharsets.UTF_8,
                    java.nio.file.StandardOpenOption.APPEND )
            )   {
                paper.append( outStr );
            }
        } catch ( java.io.IOException ioe ) {
            System.err.println( "LF.rsf() had some I/O problem."
                    + " there's like five options\n "+ ioe.toString() );
        }
        latestTag = 0;
    }

    public static void main(String args[]) {
        /* woo, lambda form instead of Runnable anon class */
        java.awt.EventQueue.invokeLater(() -> {
            new SplainOld().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnOpensFile;
    private JButton btnSave;
    private JLabel jlSaysPrevious;
    private JLabel jlShowsRoughTime;
    private JTextField jtfForTag;
    // End of variables declaration//GEN-END:variables
}
