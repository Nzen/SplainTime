/* see ../../../../../LICENSE for release details */
package ws.nzen.tracking.splaintime;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;

import ws.nzen.tracking.splaintime.SplainTime;

/**  */
class TestSplainTime
{
	final LocalDate yesterday = LocalDate.now().minusDays( 1L ),
			today = LocalDate.now(),
			tomorrow = LocalDate.now().plusDays( 1L );
	final boolean pastways = true;

	String userTime = "";

	/**
	 * Test method for {@link ws.nzen.splaintime.SplainTime#adjustToHhmmFormat(java.lang.String,java.util.Date,java.lang.boolean)}.
	 */
	@Test
	void testAdjustToHhmmFormat()
	{
		SplainTime nonGui = new SplainTime();
		tathfMidnightMinusToYesterday( nonGui );
		tathfMidnightMinusToMidnight( nonGui );
		tathfMidnightMinusToYesterNoon( nonGui );
		tathfMidnightPlusToNoon( nonGui );
		tathfMidnightPlusToMidnight( nonGui );
		tathfMidnightPlusToMorning( nonGui );
		tathfMorningMinusToYesterday( nonGui );
		tathfMorningMinusToMidnight( nonGui );
		tathfMorningMinusToMorning( nonGui );
		tathfMorningPlusToNoon( nonGui );
		tathfMorningPlusToMorning( nonGui );
		tathfMorningPlusToAfternoon( nonGui );
		tathfNoonMinusToMidnight( nonGui );
		tathfNoonMinusToMorning( nonGui );
		tathfNoonMinusToNoon( nonGui );
		tathfNoonPlusToNoon( nonGui );
		tathfNoonPlusToAfternoon( nonGui );
		tathfNoonPlusToMidnight( nonGui );
		tathfAfternoonMinusToMorning( nonGui );
		tathfAfternoonMinusToNoon( nonGui );
		tathfAfternoonMinusToAfternoon( nonGui );
		tathfAfternoonPlusToAfternoon( nonGui );
		tathfAfternoonPlusToTomorrowMidnight( nonGui );
		tathfAfternoonPlusToTomorrowMorning( nonGui );
	}


	private void tathfMidnightMinusToYesterday( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 0, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "5:01";
		expectedTime = LocalTime.of( 17, 1 );
		expectedLdt = LocalDateTime.of( yesterday, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "00:41 -5:01" );
	}


	private void tathfMidnightMinusToMidnight( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 0, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:01";
		expectedTime = LocalTime.of( 0, 1 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "00:41 -12:01" );
	}


	private void tathfMidnightMinusToYesterNoon( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 0, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:55";
		expectedTime = LocalTime.of( 12, 55 );
		expectedLdt = LocalDateTime.of( yesterday, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "00:41 -12:55" );
	}


	private void tathfMidnightPlusToNoon( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 0, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:05";
		expectedTime = LocalTime.of( 12, 05 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "00:41 +12:05" );
	}


	private void tathfMidnightPlusToMidnight( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 0, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:55";
		expectedTime = LocalTime.of( 0, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "00:41 +12:55" );
	}


	private void tathfMidnightPlusToMorning( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 0, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "5:55";
		expectedTime = LocalTime.of( 5, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "00:41 +5:55" );
	}


	private void tathfMorningMinusToYesterday( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 3, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "5:55";
		expectedTime = LocalTime.of( 17, 55 );
		expectedLdt = LocalDateTime.of( yesterday, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "03:41 -5:55" );

		userTime = "3:55";
		expectedTime = LocalTime.of( 15, 55 );
		expectedLdt = LocalDateTime.of( yesterday, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "03:41 -3:55" );
	}


	private void tathfMorningMinusToMidnight( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 3, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:55";
		expectedTime = LocalTime.of( 0, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "03:41 -12:55" );
	}


	private void tathfMorningMinusToMorning( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 3, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "2:55";
		expectedTime = LocalTime.of( 2, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "03:41 -2:55" );

		currTime = LocalTime.of( 3, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "3:35";
		expectedTime = LocalTime.of( 3, 35 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "03:41 -3:35" );
	}


	private void tathfMorningPlusToNoon( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 3, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:55";
		expectedTime = LocalTime.of( 12, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "03:41 +12:55" );
	}


	private void tathfMorningPlusToMorning( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 3, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "3:55";
		expectedTime = LocalTime.of( 3, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "03:41 +3:55" );

		currTime = LocalTime.of( 3, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "5:55";
		expectedTime = LocalTime.of( 5, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "03:41 +5:55" );
	}


	private void tathfMorningPlusToAfternoon( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 3, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "2:55";
		expectedTime = LocalTime.of( 14, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "03:41 +2:55" );
	}


	private void tathfNoonMinusToMidnight( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 12, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:55";
		expectedTime = LocalTime.of( 0, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "12:41 -12:55" );
	}


	private void tathfNoonMinusToMorning( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 12, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "2:55";
		expectedTime = LocalTime.of( 2, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "12:41 -2:55" );
	}


	private void tathfNoonMinusToNoon( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 12, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:35";
		expectedTime = LocalTime.of( 12, 35 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "12:41 -12:35" );
	}


	private void tathfNoonPlusToNoon( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 12, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:55";
		expectedTime = LocalTime.of( 12, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "12:41 +12:55" );
	}


	private void tathfNoonPlusToAfternoon( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 12, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "2:55";
		expectedTime = LocalTime.of( 14, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "12:41 +2:55" );
	}


	private void tathfNoonPlusToMidnight( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 12, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:35";
		expectedTime = LocalTime.of( 0, 35 );
		expectedLdt = LocalDateTime.of( tomorrow, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "12:41 +12:35" );
	}

	private void tathfAfternoonMinusToMorning( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 15, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "5:55";
		expectedTime = LocalTime.of( 5, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "15:41 -5:55" );
	}

	private void tathfAfternoonMinusToNoon( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 15, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:55";
		expectedTime = LocalTime.of( 12, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "15:41 -12:55" );
	}

	private void tathfAfternoonMinusToAfternoon( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 15, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "2:55";
		expectedTime = LocalTime.of( 14, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "15:41 -2:55" );

		currTime = LocalTime.of( 15, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "3:35";
		expectedTime = LocalTime.of( 15, 35 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, pastways ) );
		match( expectedLdt, actualLdt, "15:41 -3:35" );
	}

	private void tathfAfternoonPlusToAfternoon( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 15, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "5:55";
		expectedTime = LocalTime.of( 17, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "15:41 +5:55" );

		currTime = LocalTime.of( 15, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "3:55";
		expectedTime = LocalTime.of( 15, 55 );
		expectedLdt = LocalDateTime.of( today, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "15:41 +3:55" );
	}

	private void tathfAfternoonPlusToTomorrowMidnight( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 15, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "12:55";
		expectedTime = LocalTime.of( 0, 55 );
		expectedLdt = LocalDateTime.of( tomorrow, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "15:41 +12:55" );
	}

	private void tathfAfternoonPlusToTomorrowMorning( SplainTime nonGui )
	{
		Date currDate;
		LocalDateTime expectedLdt, actualLdt, currLdt;
		LocalTime currTime, expectedTime;

		currTime = LocalTime.of( 23, 41 );
		currLdt = LocalDateTime.of( today, currTime );
		userTime = "2:55";
		expectedTime = LocalTime.of( 2, 55 );
		expectedLdt = LocalDateTime.of( tomorrow, expectedTime );
		currDate = adapted( currLdt );
		actualLdt = adapted( nonGui.adjustToHhmmFormat( userTime, currDate, ! pastways ) );
		match( expectedLdt, actualLdt, "23:41 +2:55" );
	}


	private void match(
			LocalDateTime expectedLdt, LocalDateTime actualLdt, String message )
	{
		assertEquals(
				expectedLdt.getDayOfMonth(),
				actualLdt.getDayOfMonth(),
				message +" day" );
		assertEquals(
				expectedLdt.getHour(),
				actualLdt.getHour(),
				message +" hour" );
		assertEquals(
				expectedLdt.getMinute(),
				actualLdt.getMinute(),
				message +" minute" );
	}


	private Date adapted( LocalDateTime desired )
	{
		return Date.from( desired.atZone( ZoneId
				.systemDefault() ).toInstant() );
	}


	private LocalDateTime adapted( Date provided )
	{
		return provided.toInstant().atZone(
    			ZoneId.systemDefault() ).toLocalDateTime();
	}

}





































