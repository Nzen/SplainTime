package ws.nzen.splaintime;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ws.nzen.splaintime.model.Flag;
import ws.nzen.splaintime.model.Tag;

public class ParsesInput
{
    private java.text.SimpleDateFormat hourMinText
    		= new java.text.SimpleDateFormat( "h:mm" ); // 12 hour, will guess meridian, eventually
	private StPreference directiveDefaults;
	private String input;
	private LocalDateTime when;
	private Tag result;
	private List<Flag> directives;
	private String directivelessText;
	private boolean awaitingParse = false;


	public ParsesInput()
	{
		input = "";
	}


	public ParsesInput( LocalDateTime when )
	{
		this();
		this.when = when;
	}


	public ParsesInput( String userWords )
	{
		if ( userWords == null )
		{
			input = "";
		}
		else
		{
			input = userWords;
		}
		when = LocalDateTime.now();
		awaitingParse = true;
	}


	public boolean parse( String input )
	{
		this.input = input;
		return parse();
	}


	//public boolean parse(  ) combinations of input and when


	public boolean parse()
	{
		final boolean worked = true;
		ensureSubComponentsReady();
		if ( input == null || input.isEmpty() )
		{
			return ! worked;
		}
		String[] tokens = input.split( " " );
		boolean exhaustedDirectives = false;
		for ( String nibble : tokens )
		{
			if ( nibble.isEmpty() )
			{
				continue;
			}
			else if ( exhaustedDirectives )
			{
				directivelessText += " "+ nibble;
			}
			else if ( nibble.equals( directiveDefaults.getUndoFlag() ) )
			{
				directives.add( Flag.undo );
			}
			else if ( nibble.equals( directiveDefaults.getSubtaskStartFlag() ) )
			{
				directives.add( Flag.toggleSubtask );
			}
			else if ( nibble.equals( directiveDefaults.getRelabelFlag() ) )
			{
				directives.add( Flag.changeActiveText );
			}
			else if ( nibble.charAt( 0 ) == '-' || nibble.charAt( 0 ) == '+' )
			{
				if ( directives.contains( Flag.changeActiveTime ) )
				{
					// NOTE only interpret the first time adjustment
					directivelessText = nibble;
					exhaustedDirectives = true;
					continue;
				}
				else if ( nibble.contains( ":" ) )
				{
					try
					{
						Date userWhen = hourMinText.parse( nibble.substring( 1 ) ); // consider adding ymd? so it's today, not 700101
						// FIx handle + and - ; which is to say meridian
						directives.add( Flag.changeActiveTime );
						when = userWhen.toInstant().atZone(
			        			ZoneId.systemDefault() ).toLocalDateTime();
						continue;
					}
					catch ( ParseException e )
					{
						// nothing, it wasn't certain to be an adjustment; it's normal text
					}
					directivelessText = nibble;
					exhaustedDirectives = true;
					continue;
				}
				else
				{
					try
					{
						int minAdjust = Integer.parseInt( nibble );
						when = when.plus( minAdjust, ChronoUnit.MINUTES );
						directives.add( Flag.changeActiveTime );
						// is it the "active" time? as in the tag becoming or pre-parsing active? that sounds like a higher level concern
						continue;
					}
					catch ( NumberFormatException nfe )
					{
						// nothing, it wasn't certain to be an adjustment
					}
					directivelessText = nibble;
					exhaustedDirectives = true;
					continue;
				}
			}
			else
			{
				directivelessText = nibble;
				exhaustedDirectives = true;
			}
		}
		awaitingParse = false;
		return worked;
	}


	private void ensureSubComponentsReady()
	{
		if ( directiveDefaults == null )
		{
			directiveDefaults = new StPreference();
		}
		if ( directives == null )
		{
			directives = new ArrayList<>();
		}
		else
		{
			directives.clear();
		}
	}


	/** Parsed tag of input. Caller should check for directives that aren't Tag attributes */
	public Tag getResult()
	{
		if ( awaitingParse )
		{
			if ( input == null || input.isEmpty() || ! parse() )
			{
				return null; // le sigh
			}
		}
		Tag fromInput = new Tag( directivelessText );
		if ( directives.contains( Flag.changeActiveTime ) )
		{
			fromInput.setWhen( LocalDateTime.now() );
			fromInput.setUserWhen( when );
		}
		else
		{
			fromInput.setWhen( when );
		}
		if ( directives.contains( Flag.toggleSubtask ) )
		{
			fromInput.setSubTag( true ); // Improve
		}
		fromInput.setUserText( input );
		return fromInput;
	}


	public List<Flag> getDirectives()
	{
		if ( awaitingParse )
		{
			parse();
		}
		return directives;
	}


	/** text stripped of flags and directives */
	public String getJustText()
	{
		if ( awaitingParse )
		{
			parse();
		}
		return directivelessText;
	}


	public Tag getTag()
	{
		return new Tag( input );//TODO
	}


	/** entire original text */
	public String getOriginalText()
	{
		return input;
	}


	public void setWhen( LocalDateTime originally )
	{
		when = originally;
		// recalc time based on flag?
	}


	public void setDirectiveDefaults( StPreference directiveDefaults )
	{
		this.directiveDefaults = directiveDefaults;
	}



}




















