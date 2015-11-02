/*
    &copy; Nicholas Prado; License: ../../readme.md

  Next:
Parse the input string to determine whether sub, adj, & remove flags
*/

package nzen;

import java.util.Date;

public class TimeTag {
	private final String cl = "tt.";

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

    public String getTag() {
    	return didWhat;
    }

    public boolean isSubTag() {
    	return subT;
    }

    /* -- following is a straight copy from tagstore -- */

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
