
// &copy; Nicholas Prado; License: ../../readme.md
/* FOR ADJUST BRANCH
test adjust with clock time, properly. huh, a time-of-day sensitive test.
pull in master, it has a nice config
integrate config
remove tagstore adj previous
*/

package ws.nzen.tracking.splaintime;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ws.nzen.tracking.splaintime.model.Tag;

/** @author Nzen
    The gui and main class of SplainTime. Handles user events
 */
public class SplainTime extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private static final String configFile = "Splaintime.properties";
    private final Logger outChannel = LoggerFactory.getLogger( SplainTime.class );
	private Store tagHandler;
    private javax.swing.Timer cron;
    private SimpleDateFormat hourFormat;
    boolean terseAdj = true;
	private int exitFlubsLeft;
    private final int delayms = 60_001; // 60 * 1000
    private StPreference config;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnConfig;
    private JButton btnFinish;
    private JButton btnOpensFile;
    private JLabel jlSaysPrevious;
    private JLabel jlShowsRoughTime;
    private JTextField jtfForTag;
    // End of variables declaration//GEN-END:variables


	public static void main( String args[] )
	{
		EventQueue.invokeLater( new Runnable()
		{
			public void run()
			{
				SplainTime gui = new SplainTime();
				gui.setVisible( true );
			}
		} );
	}


	/** Starts gui, starts tagStore */
    public SplainTime() {
		config = new StPreference();
		config.parseConfig( configFile );
        tagHandler = new TagStore( config.getInitialTagText(), config );
		exitFlubsLeft = config.getFinishFuse();
		hourFormat = ( config.isHourFormatIs12Not24() )
				? new SimpleDateFormat( "h:mm a" )
				: new SimpleDateFormat( "k:mm" );
        initComponents( config );
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
    private void initComponents( StPreference config ) {
    	if ( ! config.isLightLookAndFeel() )
    	{
    		try
			{
				UIManager.setLookAndFeel( "com.bulenkov.darcula.DarculaLaf" );
				// UIManager.setLookAndFeel( "com.jtattoo.plaf.hifi.HiFiLookAndFeel" );
			}
			catch ( ClassNotFoundException
					| InstantiationException
					| IllegalAccessException
					| UnsupportedLookAndFeelException cnfe )
			{
				outChannel.error( "st.ic unable to find Darcula L-F because"+ cnfe.toString() );
			}
    	}

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
        btnConfig.setText("About");
        btnConfig.setToolTipText("Acknowledgements");
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
        	ParsesInput tagGen = new ParsesInput( newestDid );
        	// tagGen.parse(); // if, else kick back
        	saveNewTag( tagGen.getTag() );
        }
    }//GEN-LAST:event_pushedEnter

    private void closingFrame(WindowEvent evt) {//GEN-FIRST:event_closingFrame
        tagHandler.quickSave();
        // NOTE not wrapUp() so it won't delete the temp file
    }//GEN-LAST:event_closingFrame

    private void openConfig(ActionEvent evt) {//GEN-FIRST:event_openConfig
        JOptionPane.showMessageDialog(
        		this,
        		"Splaintime is released as Fair licence"
        		+ "\nSt relies on Jooq, Liquibase, snakeyaml,"
        		+ "\nslf4j, javax.annotation, and darcula." );
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
	public Date adjustToHhmmFormat(
			String adjFlag, Date now, boolean pastward )
	{
		String strHours = adjFlag.substring( 0, adjFlag.indexOf( ':' ) );
		String strMins = adjFlag.substring( adjFlag.indexOf( ':' ) + 1,
				adjFlag.length() );
		System.out.println(
				"st.athm() " + adjFlag + " found " + strHours + " : " + strMins
						+ "\tand got " + now.toString() ); // 4TESTS
		int adjHour = Integer.parseInt( strHours );
		int adjMinute = Integer.parseInt( strMins );
		/*
		interpret as a time
		if + find one in the future
		else find one in the past
		if hour is the same, use the minute
		*/
		LocalDateTime nowIs = now.toInstant().atZone(
				ZoneId.systemDefault() ).toLocalDateTime();
		int nowHour = nowIs.getHour();
		boolean nowIsAm = nowHour < 12;
		int absoluteNowHour;
		if ( nowHour == 0 )
		{
			absoluteNowHour = 12;
		}
		else if ( nowHour > 0 && nowHour <= 12 )
		{
			absoluteNowHour = nowHour; 
		}
		else
		{
			absoluteNowHour = nowHour -12;
		}
		LocalDate adjDate = nowIs.toLocalDate();
		LocalTime adjTime = nowIs.toLocalTime();
		if ( absoluteNowHour == adjHour )
		{
			int nowMin = nowIs.getMinute();
			if ( pastward && adjMinute < nowMin )
			{
				/*
				00:30 said -12:10
					0 today
				5:30 said -5:29
					5 today
				12:30 said -12:01
					12 today
				17:30 said -5:24
					17 today
				*/
				adjTime = LocalTime.of( nowHour, adjMinute );
			}
			else if ( pastward && adjMinute >= nowMin )
			{
				/*
				00:30 said -12:37
					12 yesterday
				5:30 said -5:55
					17 yesterday
				12:30 said -12:37
					0 today
				17:30 said -5:55
					5 today
				*/
				if ( nowIsAm )
				{
					adjDate = adjDate.minusDays( 1L );
					if ( adjHour != 12 )
						adjHour += 12;
					adjTime = LocalTime.of( adjHour, adjMinute );
				}
				else
				{
					adjHour -= 12;
					adjTime = LocalTime.of( adjHour, adjMinute );
				}
			}
			else if ( ! pastward && adjMinute <= nowMin )
			{
				/*
				00:30 said +12:27
					12 today
				5:30 said +5:25
					17 today
				12:30 said +12:27
					0 next day
				17:30 said +5:25
					5 next day
				*/
				if ( nowIsAm )
				{
					adjTime = LocalTime.of( nowHour +12, adjMinute );
				}
				else
				{
					adjDate = adjDate.plusDays( 1L );
					if ( adjHour == 12 )
						adjHour = 0;
					adjTime = LocalTime.of( adjHour, adjMinute );
				}
			}
			else // if ( ! pastward && adjMinutes > nowMin )
			{
				/*
				00:30 said +12:37
					0 today
				5:30 said +5:55
					5 today
				12:30 said +12:37
					12 today
				17:30 said +5:55
					17 today
				*/
				adjTime = LocalTime.of( nowHour, adjMinute );
			}
		}
		else if ( pastward && adjHour < absoluteNowHour )
		{
			/*
			00:30 said -11:10
				23 yesterday
			4:30 said -3:29
				3 today
			12:30 said -11:01
				11 today
			17:30 said -4:24
				16 today
			*/
			if ( nowHour == 0 )
			{
				adjDate = adjDate.minusDays( 1L );
				adjTime = LocalTime.of( adjHour +12, adjMinute );
			}
			else if ( nowIsAm || nowHour == 12 )
			{
				adjTime = LocalTime.of( adjHour, adjMinute );
			}
			else
			{
				adjTime = LocalTime.of( adjHour +12, adjMinute );
			}
		}
		else if ( pastward && adjHour > absoluteNowHour )
		{
			/*
			1:30 said -11:10
				23 yesterday
			2:30 said -12:10
				0 today
			5:30 said -3:55
				3 today
			12:30 said -5:37
				5 today
			17:30 said -1:55
				13 today
			*/
			if ( nowIsAm && adjHour == 12 )
			{
				adjTime = LocalTime.of( 0, adjMinute );
			}
			else if ( nowIsAm )
			{
				adjDate = adjDate.minusDays( 1L );
				adjTime = LocalTime.of( adjHour +12, adjMinute );
			}
			else
			{
				adjTime = LocalTime.of( adjHour, adjMinute );
			}
		}
		else if ( ! pastward && adjHour < absoluteNowHour )
		{
			/*
			00:30 said +8:10
				8 today
			6:30 said +5:25
				17 today
			12:59 said +11:49
				23 today
			17:30 said +4:25
				4 next day
			*/
			if ( nowHour == 0 )
			{
				adjTime = LocalTime.of( adjHour, adjMinute );
			}
			else if ( nowIsAm || nowHour == 12 )
			{
				adjTime = LocalTime.of( adjHour +12, adjMinute );
			}
			else
			{
				adjDate = adjDate.plusDays( 1L );
				adjTime = LocalTime.of( adjHour, adjMinute );
			}
			
		}
		else // if ( ! pastward && adjHours > absoluteNowHour )
		{
			/*
			01:30 said +12:37
				12 today
			5:30 said +9:55
				9 today
			11:30 said +12:27
				12 today
			14:30 said +10:37
				22 today
			14:30 said +1:37
				1 next day
			21:30 said +12:55
				0 next day
			*/
			if ( nowIsAm )
			{
				adjTime = LocalTime.of( adjHour, adjMinute );
			}
			else if ( adjHour == 12 )
			{
				adjDate = adjDate.plusDays( 1L );
				adjTime = LocalTime.of( 0, adjMinute );
			}
			else
			{
				adjTime = LocalTime.of( adjHour +12, adjMinute );
			}
		}
		return Date.from( LocalDateTime.of( adjDate, adjTime )
				.atZone( ZoneId.systemDefault() ).toInstant() );
	}

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
}
