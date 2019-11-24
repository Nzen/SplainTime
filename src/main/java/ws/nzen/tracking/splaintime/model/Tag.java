/** see ../../../../../../../LICENSE for release rights */
package ws.nzen.tracking.splaintime.model;

import java.time.LocalDateTime;

/** 
 */
public class Tag
{
	private String userText;
	private LocalDateTime when;
	private LocalDateTime userWhen = null;
	private int minuteAdjustment;
	private boolean isSubTag;
	private String tagText;
	@Deprecated
	/** Solely for legacy compatibility.
	 * I'll toss this in a bit, in favor of LocalDateTime */
	public java.util.Date utilDate;


	public Tag( String input )
	{
		when = LocalDateTime.now();
		adopt( input );
	}


	public Tag( String input, LocalDateTime actualTime )
	{
		when = actualTime;
		adopt( input );
	}


	public void adopt( String input )
	{
		if ( input == null )
			input = "";
		userText = input;
		tagText = userText; // FIX only until ParsesInput is handling it
		isSubTag = userText.contains( "{" );
		/*
		parse for minute / time adjustment
			for subtask flag
		adjust when
			prep adjustment text
		populate the tagText
		*/
	}


	@Override
	public String toString()
	{
		return "t-"+ tagText +" @"+ when;
	}


	public String getUserText()
	{
		return userText;
	}
	public void setUserText( String userText )
	{
		this.userText = userText;
		// and then readopt the whole thing
	}

	public LocalDateTime getWhen()
	{
		return when;
	}
	public void setWhen( LocalDateTime when )
	{
		this.when = when;
		// and then reinterpret based on the adjustment minutes
	}

	public boolean isSubTag()
	{
		return isSubTag;
	}
	public void setSubTag( boolean isSubTag )
	{
		this.isSubTag = isSubTag;
		// change the clean text
	}

	public String getTagText()
	{
		/*if ( userWhen != null )
		{
			System.out.println( 
			"[adj: "+ adjFlag +" @"+ hourMinText.format(
        			now ) +"]  "+ input.getTagText().substring(separator +1) );
        			// oh, they'll need to share (or instantiate, but it'll be share) a date formatter
			 );
		}*/
		return tagText;
	}

	@Deprecated
	/** evolutionary necessity to start using Tags instead of straight strings.
	 * In a bit, I'll incorporate the external parsing or move it to ParsesInput */
	public void hackSetTagText( String externallyMassaged )
	{
		tagText = externallyMassaged;
	}


	public LocalDateTime getUserWhen()
	{
		return userWhen;
	}
	public void setUserWhen( LocalDateTime userWhen )
	{
		this.userWhen = userWhen;
	}

}




















