/** see ../../../../../../../LICENSE for release rights */
package ws.nzen.splaintime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/** 
 */
public class TestsAccountant
{

	@Test
	public void test()
	{
		StPreference settings = new StPreference();
		List<String> todaysTags = new ArrayList<>();
		todaysTags.add( "08:00.07 PM	3m 	internet" );
		todaysTags.add( "08:00.07 PM	3m 	break" );
		List<String> existingCategories = new ArrayList<>();
		existingCategories.add( "break\t"+ LocalDate.now()
				.minusDays( settings.getCategoryDaysToExpiration() +1 ) );
		Accountant counter = new Accountant();
		List<String> tagsPlusSums = counter.withSums(
				todaysTags, existingCategories );
		List<String> categoriesAfterward = counter.getCategories();
		assertEquals( 1, categoriesAfterward.size(), "should have one category" );
	}

}




















