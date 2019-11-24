/** see ../../../../../../../LICENSE for release rights */
package ws.nzen.tracking.splaintime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import ws.nzen.tracking.splaintime.ParsesInput;
import ws.nzen.tracking.splaintime.StPreference;
import ws.nzen.tracking.splaintime.model.Flag;

/** 
 */
public class TestsParsesInput
{

	/**
	 * Test method for {@link ws.nzen.tracking.splaintime.ParsesInput#parse(java.lang.String)}.
	 */
	@Test
	public void testParseString()
	{
		ParsesInput tagFactory = new ParsesInput();
		StPreference config = new StPreference();
		tagFactory.setDirectiveDefaults( config );
		String nothing  = null;
		assertFalse( tagFactory.parse( nothing ) );
		nothing = "";
		assertFalse( tagFactory.parse( nothing ) );
		assertEquals( tagFactory.getResult().getTagText(), nothing );
		//
		String directiveless = "bla";
		assertTrue( tagFactory.parse( directiveless ) );
		assertTrue( tagFactory.getDirectives().isEmpty() );
		assertEquals( directiveless, tagFactory.getJustText() );
		//
		tagFactory.parse( config.getUndoFlag() );
		assertEquals( Flag.undo, tagFactory.getDirectives().get( 0 ) );
		//
		/*
		-1 malomar
		+2 fodorol
		-8:55 jurgen
		[[ relabel ]] fafbla
		{ bivi
		deactivates { -5 parsing
		-1 -5 -6 avoid time adjust stacking
		*/
		// fail( "Not yet implemented" );
	}

	/**
	 * Test method for {@link ws.nzen.tracking.splaintime.ParsesInput#parse()}.
	 */
	@Test
	public void testParse()
	{
		// fail( "Not yet implemented" );
	}

	/**
	 * Test method for {@link ws.nzen.tracking.splaintime.ParsesInput#getResult()}.
	 */
	@Test
	public void testGetResult()
	{
		// fail( "Not yet implemented" );
	}

}




















