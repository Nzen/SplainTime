package nzen;

import java.util.Date;

public class ParsedTagInput {

	private String input;
	private char startSubFlag;
	private char endSubFlag;

	private int minuteAdjustment;
	private Date timeAdjustment;
	private boolean hasStartSubFlag;
	private boolean hasEndSubFlag;
	private String tag;

	public ParsedTagInput( String tag ) {
		this( tag, '{', '}' );
	}

	public ParsedTagInput( String tag, char startSub, char endSub ) {
		startSubFlag = startSub;
		endSubFlag = endSub;
		input = tag;
		minuteAdjustment = 0;
		timeAdjustment = null;
		hasStartSubFlag = false;
		hasEndSubFlag = false;
		tag = null;
		parse( tag );
	}

	/** FSM; Find and save valid flags: } { -4 -8:00 */
	protected void parse( String userInput ) {
		if ( userInput.length() < 2 ) {
			return;
		}
		char first = userInput.charAt(0);
		char second = userInput.charAt(1);
		if ( first == startSubFlag || first == endSubFlag ) {
			if ( second == ' ' ) {
				hasStartSubFlag = (first == startSubFlag);
				hasEndSubFlag = (first == endSubFlag);
				checkAdjustAfterFoundSubFlag( userInput.substring(2) );
			} else { // ambiguous; ignore the rest
				tag = userInput;
			}
		} else if ( first == '-' ) {
			if ( Character.isDigit(second) ) {
				checkInitialAdjust( userInput.substring(0) );
			} else { // ambiguous; ignore the rest
				tag = userInput;
			}
		} else { // no flags; ignore the rest
			tag = userInput;
		}
	}

	protected void checkAdjustAfterFoundSubFlag( String inputMinusSub ) {
        throw new UnsupportedOperationException( "Not yet implemented" );
	}

	protected void checkInitialAdjust( String inputMinusHyphen ) {
        throw new UnsupportedOperationException( "Not yet implemented" );
	}

	public boolean hasMinAdjust() {
		return minuteAdjustment > 0;
	}

	public int getMinuteAdjustment() {
		return minuteAdjustment;
	}

	public boolean hasTimeAdjust() {
		return timeAdjustment != null;
	}

	public Date getTimeAdjustment() {
		return timeAdjustment;
	}

	public boolean hasStartSubFlag() {
		return hasStartSubFlag;
	}

	public boolean hasEndSubFlag() {
		return hasEndSubFlag;
	}

	public String getTag() {
		return tag;
	}

	public String getInput() {
		return input;
	}

}
