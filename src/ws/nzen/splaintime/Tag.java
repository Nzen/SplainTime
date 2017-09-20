/** see ../../../../../../../LICENSE for release rights */
package ws.nzen.splaintime;

import java.time.LocalDateTime;

/** 
 */
public class Tag
{
	private String userText;
	private LocalDateTime when;
	private int minuteAdjustment;
	private boolean isSubTag;
	private String tagText;


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
		/*
		parse for minute / time adjustment
			for subtask flag
		adjust when
			prep adjustment text
		populate the tagText
		*/
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
		return tagText;
	}

}




















