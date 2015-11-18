/*
    &copy; Nicholas Prado; License: ../../readme.md

  Next:
Parse the input string to determine whether sub, adj, & remove flags
*/

package nzen;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TimeTag implements Serializable {
	private final String cl = "tt.";
	private static final long serialVersionUID = 2L;
	final int aMinuteMilli = 60 * 1000;
    private SimpleDateFormat toHourMs;

	protected Date tagTime;
	protected String didWhat;
	protected boolean subT;
	protected boolean retainsFlags; // ASK whether I need this

    // FIX this is the main constructor; parse input & set fields
    public TimeTag( Date tt, String dw ) {
    	this( tt, dw, false );
    }

    public TimeTag( Date tt, String dw, boolean explicitAdj ) {
        tagTime = tt;
        didWhat = dw; // NOTE defaults
        subT = false;
        retainsFlags = explicitAdj;
        toHourMs = new SimpleDateFormat( "hh:mm.ss a" );
        applyParsedInput( tt, dw );
    }

    protected void applyParsedInput( Date tt, String dw ) {
    	ParsedTagInput args = new ParsedTagInput( dw );
    	didWhat = args.getTag();
    	subT = args.hasStartSubFlag();
    	if ( args.hasMinAdjust() ) {
    		tagTime = new Date( tt.getTime() - args.getMinuteAdjustment() );
    	} else if ( args.hasTimeAdjust() ) {
    		// FIX check if it is the right am/pm
    		tagTime = args.getTimeAdjustment();
    	}
        throw new UnsupportedOperationException( "Incomplete implementation" );
    }

    /** calculates difference from stored time and replies as text */
    public String getDiffAsText( Date elseWhen ) {
    	throw new UnsupportedOperationException( "Not yet implemented" );
    }

    public long getDiffAsMilliseconds( Date elseWhen ) {
    	return tagTime.getTime() - elseWhen.getTime();
    }

    public String getFullDescription( Date elseWhen ) {
    	return toHourMs.format( tagTime ) +"\t"
                + prettyDiff( tagTime, elseWhen ) +"\t"
                + didWhat;
    }

    public boolean overOneMinuteElapsed() {
    	return (System.currentTimeMillis() - this.tagTime.getTime()) > aMinuteMilli;
    }

    public String getTag() {
    	return didWhat;
    }

    public boolean isSubTag() {
    	return subT;
    }

    /* -- following is a straight copy from tagstore -- */

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

    /** Formats the start time and diff for the user */
    private String prettyDiff( Date start, Date end ) {
        long diffSeconds = ( end.getTime() - start.getTime() ) / 1000;
        // pr( "ts.pd() diff is " + Long.toString(diffSeconds) +" seconds" ); // 4TESTS
        int hours = 0, hourSeconds = 3600; // 60m * 60s
        while ( diffSeconds >= hourSeconds ) {
            diffSeconds -= hourSeconds;
            hours++;
        }
        String hrs = ( hours > 0 ) ? itoa( hours )+"h " : "";
        int  min = 0, minuteSeconds = 60;
        while ( diffSeconds >= minuteSeconds ) {
            diffSeconds -= minuteSeconds;
            min++;
        }
        String mins = ( min >= 0 ) ? itoa( min )+"m " : "";
        return hrs + mins; // NOTE remainder tossed
    }

    /** so much typing just for println. No. */
    void pr( String out ) {
        System.out.println( out );
    }

	/** More laziness/terseness */
	String itoa( int nn ) {
		return Integer.toString( nn );
	}

}
