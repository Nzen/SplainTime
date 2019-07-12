
// &copy; Nicholas Prado; License: ../../readme.md
/* FOR ADJUST BRANCH
test adjust with clock time, properly. huh, a time-of-day sensitive test.
pull in master, it has a nice config
integrate config
remove tagstore adj previous
*/

package ws.nzen.splaintime;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import ws.nzen.splaintime.model.Tag;

/** @author Nzen
    The gui and main class of SplainTime. Handles user events
 */
public class SplainTime extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private static final String configFile = "Splaintime.properties";
	private Store tagHandler;
    private javax.swing.Timer cron;
    private SimpleDateFormat hourFormat;
    boolean terseAdj = true;
	private int exitFlubsLeft;
    private final int delayms = 60_001; // 60 * 1000
    private StPreference config;

    /** Starts gui, starts tagStore */
    public SplainTime() {
		config = new StPreference();
		config.parseConfig( configFile );
        tagHandler = new TagStore( config.getInitialTagText(), config );
		exitFlubsLeft = config.getFinishFuse();
		hourFormat = ( config.isHourFormatIs12Not24() )
				? new SimpleDateFormat( "h:mm a" )
				: new SimpleDateFormat( "k:mm" );
        initComponents();
		updateLatestTaskLabel( tagHandler.gPreviousTag() );
		updateTimeDiffLabel( new Date() );
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent evt ) {
                updateTimeDiffLabel( new Date() );
                tagHandler.quickSave();
                resetExit();
            }
        };
        cron = new javax.swing.Timer( delayms, taskPerformer );
        cron.start();
    }

    /**
    Netbeans generated gui initialization
     */
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
        btnConfig.setEnabled( false );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jtfForTag)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnOpensFile)
                        .addGap(60, 60, 60)
                        .addComponent(btnConfig)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(btnFinish))
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
    }//GEN-END:initComponents

	/** Strengthen exit signal OR end all tasks & delete today's cache file */
    private void pushedFinish(ActionEvent evt) {//GEN-FIRST:event_pushedFinish
		if ( exitFlubsLeft < 1 ) {
			storeTag( new ParsesInput( "Shutting down" ).getTag() );
			tagHandler.wrapUp( config );
            System.exit( 0 );
		} else {
			exitFlubsLeft--;
			btnFinish.setText(Integer.toString( exitFlubsLeft ));
		}
    }//GEN-LAST:event_pushedFinish

    /** Open a list of closed tags for the user */
    private void pushedOpen(ActionEvent evt) {//GEN-FIRST:event_pushedOpen
        switch ( tagHandler.showStoredTags() )
        {
        	case NO_FILE :
        	{
        		if ( jtfForTag.getText().isEmpty() )
        		{
        			jtfForTag.setText( "Haven't written file yet" );
        		}
        		break;
        	}
        	case IOE :
        	case NO_DESKTOP :
        	case NO_OPEN :
        	{
        		if ( jtfForTag.getText().isEmpty() )
        		{
        			jtfForTag.setText( "Editing unsupported, sorry" );
        		}
        		break;
        	}
        	default : {} // nothing
        }
    }//GEN-LAST:event_pushedOpen

    /** Store (interpret?) this tag, reset gui time & tag summary */
    private void pushedEnter(ActionEvent evt) {//GEN-FIRST:event_pushedEnter
        String newestDid = jtfForTag.getText();
        if ( newestDid.isEmpty() )
        {
        	return;
        }
        else if (newestDid.equals( config.getUndoFlag() ))
        {
        	tryToRemovePreviousTag();
        }
        else if ( newestDid.startsWith( config.getCheckCategoryFlag() ) )
        {
        	checkIfTagIsCategory( newestDid );
        }
        else if ( newestDid.startsWith( config.getNextTimeFlag() ) )
        {
        	saveForRestart( newestDid );
        }
        else if ( newestDid.startsWith( config.getTimeSinceFlag() +" " ) )
        {
        	replyTimeSince( newestDid );
        }
        /* relabelActiveTag() is broken
        else if ( newestDid.startsWith( config.getRelabelFlag() ) )
        {
        	relabelActiveTag( newestDid.substring(
        			config.getRelabelFlag().length() ).trim() );
        } */
        else
        {
        	saveNewTag( new ParsesInput( newestDid ).getTag() );
        }
    }//GEN-LAST:event_pushedEnter

    private void closingFrame(WindowEvent evt) {//GEN-FIRST:event_closingFrame
        tagHandler.quickSave();
        // NOTE not wrapUp() so it won't delete the temp file
    }//GEN-LAST:event_closingFrame

    @Deprecated
    private void openConfig(ActionEvent evt) {//GEN-FIRST:event_openConfig
        System.out.println( "st.oc no longer implemented" );
    }//GEN-LAST:event_openConfig


    private void validateConfig()
    {
    	exitFlubsLeft = config.getFinishFuse();
    }


    /** drops in memory tag if it isn't the only one */
    private void tryToRemovePreviousTag()
    {
    	if ( tagHandler.canRemoveOne() )
    	{
            jtfForTag.setText( tagHandler.gPreviousTag().getUserText() );
            	// replace with current, in case I just want to edit it
    		tagHandler.removePrevious();
    		updateLatestTaskLabel( tagHandler.gPreviousTag() );
    		updateTimeDiffLabel( new Date() );
    	}
    	else
    	{
    		jtfForTag.setText("Sorry, already saved");
    	}
    }


    private void checkIfTagIsCategory( String text )
	{
		final String yes = " y", no = " n";
		if ( config.getCheckCategoryFlag().length() +1 >= text.length() )
		{
			jtfForTag.setText( text +" {check which category?}" );
			return;
		}
		String someInput = text.substring(
     			config.getCheckCategoryFlag().length() +1 );
		if ( config.isDoesntNeedSum() )
		{
			jtfForTag.setText( no +": categories off" );
		}
		else if (  tagHandler.whetherCategory( someInput, config ) )
		{
			jtfForTag.setText( someInput + yes );
		}
		else
		{
			jtfForTag.setText( config.getSumDelimiter() + someInput
					+ config.getSumDelimiter() + no );
		}
	}


	private void saveForRestart( String userEntered )
	{
		tagHandler.setRestartTag( userEntered
    			.substring( config.getNextTimeFlag().length() ) );
		jtfForTag.setText( "" );
	}


	private void replyTimeSince( String userEntered )
	{
		// assert userEntered contains command and isn't blank
		String textToFind = userEntered.substring(
				userEntered.indexOf( " " ) +1, userEntered.length() );
		Duration timeSince = tagHandler.timeSince(
				textToFind, LocalDateTime.now(), config );
		if ( timeSince.getSeconds() == 0L )
		{
			jtfForTag.setText( "No record of "+ textToFind );
		}
		else
		{
			long minutes = timeSince.toMinutes();
			long minPerHour = 60;
			if ( minutes > minPerHour )
			{
				long hours = minutes / minPerHour;
				minutes = minutes % minPerHour;
				jtfForTag.setText( Long.toString( hours ) +"h "
						+ Long.toString( minutes ) +"m" );
			}
			else
			{
				jtfForTag.setText( Long.toString( minutes ) +"m" );
			}
		}
	}


	private void saveNewTag( Tag userEntered )
    {
		// interpret flag, if present
		storeTag( userEntered );
        updateLatestTaskLabel( userEntered );
        cron.restart(); // so it doesn't fire midway into newest tag's first minute
        jtfForTag.setText(""); // blank the text entry
        updateTimeDiffLabel( new Date() );
        resetExit();
    }


	/** Store the tag with the current time */
	private void storeTag( Tag input ) {
        Date now = new Date();
        Date newT = adjustedTime( input, now );
        if ( ! now.equals(newT) ) { // IMPROVE && config.adjOut == bla
        	int separator = input.getTagText().indexOf(' ');
            String adjFlag = input.getTagText().substring( 0, separator );
        	input.hackSetTagText( "[adj: "+ adjFlag +" @"+ hourFormat.format(
        			now ) +"]  "+ input.getTagText().substring(separator +1) );
        	input.setUserWhen( newT.toInstant().atZone(
        			ZoneId.systemDefault() ).toLocalDateTime() );
        }
    	input.setWhen( now.toInstant().atZone(
    			ZoneId.systemDefault() ).toLocalDateTime() );
        input.utilDate = newT;
        if ( input.isSubTag() )
        {
        	input = stripSubtaskFlag( input );
        }
        tagHandler.add( input );
	}


	@Deprecated
	/** Changes active text, <em>not</em> reinterpreting time (use undo). */
	private void relabelActiveTag( String replacementText )
	{
		/*
		change labellabel
		send with previous adj text to tag
		*/
		Tag nameSlate = tagHandler.gPreviousTag();
		nameSlate.hackSetTagText( replacementText );
		// FIX this is 'changing history' territory, but updateLTLabel uses user text
		nameSlate.setUserText( replacementText );
		tagHandler.replaceActiveWith( nameSlate );
		updateLatestTaskLabel( nameSlate );
		jtfForTag.setText( "" );
	}


	/** now, or subtracted by adjust flag */
    private Date adjustedTime( Tag input, Date now ) {
    	String fullTag = input.getUserText();
        if ( ! ( fullTag.startsWith( "-" )
        		|| fullTag.startsWith( "+" ) ) )
        {
        	return now; // normal case
        }
        boolean pastward = fullTag.charAt( 0 ) == '-';
        String adjFlag = fullTag.substring( 1, fullTag.indexOf(' ') );
        // NOTE intentionally ignoring substr error, so user can fix the entry
        if ( adjFlag.contains(":") ) { // ex 8:00
            // System.out.println( "st.at( ::: ) starts with "+ fullTag ); // 4TESTS
            return adjustToHhmmFormat( adjFlag, now, pastward );
        }
		// else is simple adjust. ex -13
        int adjMinutes = Integer.parseInt( adjFlag );
        if ( pastward )
        {
        	adjMinutes *= -1;
        }
        int adjMillis = adjMinutes * 60000; // 60sec * 1000ms
        return new Date( now.getTime() + adjMillis );
    }

    /** parse time from hh:mm and round down to before 'now' */
    private Date adjustToHhmmFormat( String adjFlag,
    		Date now, boolean pastward ) {
        String strHours = adjFlag.substring( 0, adjFlag.indexOf(':') );
        String strMins = adjFlag.substring( adjFlag.indexOf(':') +1, adjFlag.length() );
        System.out.println( "st.athm() "+adjFlag+" found "+ strHours +" : "+ strMins
            +"\tand got "+ now.toString() ); // 4TESTS
        int adjHours = Integer.parseInt( strHours );
        int adjMinutes = Integer.parseInt( strMins );
        GregorianCalendar timeKnob = new GregorianCalendar();
        timeKnob.set( Calendar.HOUR, adjHours );
        timeKnob.set( Calendar.MINUTE, adjMinutes );
        // System.out.println("st.athm() initial calc is "+ timeKnob.getTime().toString()); // 4TESTS

        long tempAdjMilli = timeKnob.getTimeInMillis();
        long nowMilli = now.getTime();
        // FIX actually analyze this math
        if ( nowMilli < tempAdjMilli && pastward) { // adjusted doesn't match current AM/PM
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
        GregorianCalendar timeKnob = new GregorianCalendar();
        timeKnob.set( Calendar.HOUR, initHr );
        timeKnob.set( Calendar.MINUTE, initMin );
        // System.out.println( here +"initial calc is "
                // + initChars +" is "+ timeKnob.getTime().toString() ); // 4TESTS
        int makeItLess = 66666; // NOTE milliseconds
        boolean lessIsThePast = true;
        Date aTime = timeKnob.getTime();
        Date receivedTime = adjustToHhmmFormat( initChars,
        		new Date( aTime.getTime() - makeItLess ), lessIsThePast );
        if ( ! aTime.after(receivedTime) ) {
            problems[ pInd ] = here +"aT "+ aTime.toString() +" }} rT "
                    + receivedTime.toString() +" }} sT "
                    + new Date( aTime.getTime() - makeItLess ).toString()
                    +"\tdiff is "+ Long.toString(aTime.getTime() - receivedTime.getTime());
            pInd++;
        }
        return problems;
    }

    /* Tests for adjustedTime() *
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
        GregorianCalendar afternoon // IMPROVE use oracle
                = new GregorianCalendar( 1999, 10, 12, 16, 20 ); // arbitrary date
        Date theAfternoon = afternoon.getTime();
        String woopsEnteredLate = "-8:00 hung over all morning";
        aTime = adjustedTime( woopsEnteredLate, theAfternoon );
        GregorianCalendar morning
                = new GregorianCalendar( 1999, 10, 12, 8, 00 );
        Date whenIShouldHaveEntered = morning.getTime();
        if ( ! aTime.equals(whenIShouldHaveEntered) ) {
            long became = aTime.getTime();
            problems[ pInd ] = here +"wrong clock adjusted time: off by "
                    + Long.toString( whenIShouldHaveEntered.getTime() - became ) +"ms\n"
                    + "  should be "+ whenIShouldHaveEntered.toString()
                    + " - became "+ aTime.toString();
            pInd++;
        }
        /
        Above is a bad test since aT() isn't actually functional.
        it relies on using the current time.
        *
        return problems;
    }*/

    /** remove { and one of the spaces around it */
    private Tag stripSubtaskFlag( Tag tag )
    {
    	int stTagInd = tag.getTagText().indexOf( '{' );
    	int len = tag.getTagText().length();
    	if ( stTagInd == 0 ) {
    		tag.hackSetTagText( tag.getTagText().substring( stTagInd +2 ) );
    	}
    	else if ( stTagInd == len -1 ) {
    		tag.hackSetTagText( tag.getTagText().substring(  0, len -2  ) );
    	}
    	else {
    		tag.hackSetTagText( tag.getTagText().substring( 0, stTagInd )
    				+ tag.getTagText().substring( stTagInd +2 ) );
    	}
    	return tag;
    }

    /** Show minutes elapsed for the current task */
    private void updateTimeDiffLabel( Date now ) {
        // ASK should I have ts calculate it this way or fanciest way?
        long diff = (now.getTime() - tagHandler.gPreviousTime().getTime()) / delayms;
        // IMPROVE if ( diff > 36000 )
        jlShowsRoughTime.setText( Long.toString(diff) +" min" );
    }

    /** Show portion of the task that fits on the label */
    private void updateLatestTaskLabel( Tag whatDidStruct ) {
    	String whatDid = whatDidStruct.getUserText();
        if (whatDid.startsWith( "-" )) {
            int startsAfter = whatDid.indexOf( " " );
            whatDid = whatDid.substring( startsAfter );
        } // simple to add {} above, but what if tag is " } -8:00 bla " ?
        // fits between 12 capital Ws || 30+ commas
		int charsFit = 25;
		String truncation = "...";
		if ( whatDid.length() >= charsFit )
		{
			jlSaysPrevious.setToolTipText( whatDid );
			whatDid = whatDid.substring( 0, charsFit - truncation
					.length() ) + truncation;
        }
		else
		{
			jlSaysPrevious.setToolTipText( "" ); // NOTE so it's not keeping old tooltips
		}
        jlSaysPrevious.setText( whatDid );
    }

    /** Change exit counter, reset Finish button text */
	private void resetExit() {
		exitFlubsLeft = config.getFinishFuse();
		if ( ! btnFinish.getText().equals("Finish") )
			btnFinish.setText( "Finish" );
	}

    /*  *
    public void runTests() {
        Random oracle = new Random();
        if (showsResults( problemsWithAdjustedTime(oracle) ))
            System.out.println( "--" );
        if (showsResults( problemsWithAdjustHhMm(oracle) ))
            System.out.println( "--" );
        System.out.println( "- Tests over -" );
    }

    /* Indicate whether theseTests had problems ; hmm if only I could extract this ... *
	private boolean showsResults( String[] theseTests ) {
		boolean failed = true;
		int first = 0;
        if ( ! theseTests[first].equals("") ) {
            for ( String problem : theseTests )
                System.out.println( problem );
			return failed; 
        } else
			return ! failed;
	}*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnConfig;
    private JButton btnFinish;
    private JButton btnOpensFile;
    private JLabel jlSaysPrevious;
    private JLabel jlShowsRoughTime;
    private JTextField jtfForTag;
    // End of variables declaration//GEN-END:variables
}
