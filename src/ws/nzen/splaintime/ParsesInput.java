package ws.nzen.splaintime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParsesInput
{
    /*private java.text.SimpleDateFormat hourMinText
    		= new java.text.SimpleDateFormat( "h:mm a" );*/
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
		if ( input.isEmpty() )
		{
			return ! worked;
		}
		/*
		extract directives
		extract text
		regex?
		( ( j8x )? | ( [+-]\d+(:\d+) )? | {? | c4t? | ca7? )
		um, except the subtask, and time parsing, there isn't much 'parsing',
		most of these directives aren't made to stack
		*/
		return false;//TODO
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


	public List<Flag> getDirectives()
	{
		if ( awaitingParse )
		{
			parse();
		}
		return directives;
	}


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


	public String getOriginalText()
	{
		return input;
	}


	public void setWhen( LocalDateTime originally )
	{
		when = originally;
	}


	public void setDirectiveDefaults( StPreference directiveDefaults )
	{
		this.directiveDefaults = directiveDefaults;
	}



}




















