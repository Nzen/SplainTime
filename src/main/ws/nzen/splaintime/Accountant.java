/** see ../../../../../../../LICENSE for release rights */
package ws.nzen.splaintime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ws.nzen.splaintime.model.Category;

/** Provides reports about existing tags
 */
public class Accountant
{
	// \d{2}:\d{2}\.\d{2} [AP]M\s\(?(\dh )?(\dm )? ;;; 10:49.37 PM	1h 25m 
	private static final String tagLineRegex = "\\d{2}:\\d{2}\\.\\d{2} [AP]M	\\(?(\\d+h )??(\\d+m)";
	private StPreference settings = null;
	private Map<String, LocalDate> categoryUses = new HashMap<>();
	private Pattern fsmSpec = null;
	private Matcher fsmRuntime = null;


	public Accountant()
	{
		this( new StPreference() );
	}


	public Accountant( StPreference howToThink )
	{
		if ( howToThink == null )
		{
			howToThink = new StPreference();
		}
		settings = howToThink;
	}


	// IMPROVE split apart
	public List<String> withSums( List<String> contentsOfTagFile,
			List<String> contentsOfCategoryFile )
	{
		Category temp = new Category();
		// deserialize the previous categories
		for ( String curr : contentsOfCategoryFile )
		{
			temp.become( curr );
			categoryUses.put( temp.getCategory(), temp.getLastUsed() );
		}
		// find (new) categories in the tag lines
		int lenOfDelim = settings.getSumDelimiter().length();
		for ( String currLine : contentsOfTagFile )
		{
			int indOfMark = -1, tempInd = -1;
			tempInd = currLine.indexOf( settings.getSumDelimiter(), indOfMark );
			while ( tempInd > indOfMark && tempInd >= 0 )
			{
				indOfMark = tempInd + lenOfDelim; // past sumDelim, first ind of potential category
				tempInd = currLine.indexOf( settings.getSumDelimiter(), indOfMark );
				if ( tempInd > indOfMark )
				{
					String currCategory = currLine.substring( indOfMark, tempInd );
					categoryUses.put( currCategory, LocalDate.now() );
					// try again, just in case
					indOfMark = tempInd + lenOfDelim;
					tempInd = currLine.indexOf( settings.getSumDelimiter(), indOfMark );
				}
				else
				{
					// this is an unmatched delimiter, ignore
					break;
				}
			}
		}
		// sum durations of the categories of user lines
		Map<String, Integer> categoryMins = new HashMap<>();
		for ( String currLine : contentsOfTagFile )
		{
			int tagMins = 0;
			for ( String currCategory : categoryUses.keySet() )
			{
				if ( currLine.contains( currCategory ) )
				{
					tagMins = durationOf( currLine );
					Integer prevCatDur = categoryMins.get( currCategory );
					if ( prevCatDur != null )
					{
						tagMins += prevCatDur;
					}
					categoryMins.put( currCategory, tagMins );
				}
			}
		}
		// add sums to bottom of user lines
		if ( ! categoryMins.isEmpty() )
		{
			contentsOfTagFile.add( "" );
			contentsOfTagFile.add( "\t- - - - -\tCATEGORY SUMS\t- - - - -\t" );
			contentsOfTagFile.add( "" );
			for ( String currCategory : categoryMins.keySet()  )
			{
				contentsOfTagFile.add( Integer.toString(
						categoryMins.get( currCategory ) ) +"m\t"+ currCategory );
			}
		}
		// prune the expired categories
		LocalDate expirationDate = LocalDate.now()
				.minusDays( settings.getCategoryDaysToExpiration() );
		List<String> expiredCategories = new LinkedList<>();
		for ( String currCategory : categoryUses.keySet()  )
		{
			LocalDate when = categoryUses.get( currCategory );
			if ( when.isBefore( expirationDate ) )
			{
				expiredCategories.add( currCategory );
			}
		}
		for ( String staleKey : expiredCategories )
		{
			categoryUses.remove( staleKey );
		}
		return contentsOfTagFile;
	}


	private int durationOf( String tagLine )
	{
		int totalMinutes = 0;
		if ( fsmSpec == null )
		{
			fsmSpec = Pattern.compile( tagLineRegex );
		}
		// gr here is an abbreviation for (regex) group
		final int grHour = 1, grMin = grHour +1;
		int frInd = 0;
		fsmRuntime = fsmSpec.matcher( tagLine );
		try
		{
			if ( fsmRuntime.find( frInd ) )
			{
				String hrGroup = fsmRuntime.group( grHour );
				String minGroup = fsmRuntime.group( grMin ) +" "; // guranteed to exist
				totalMinutes = minutesOfRegexGroup( hrGroup, 60 );
				totalMinutes += minutesOfRegexGroup( minGroup, 1 );
			}
		}
		catch ( IllegalArgumentException iae )
		{
			System.err.println( "couldn't interpret tag line "+ tagLine +" because "+ iae );
		}
		return totalMinutes;
	}


	private int minutesOfRegexGroup( String digitsUnitSpace, int modifier )
	{
		int value = 0;
		if ( digitsUnitSpace != null )
		{
			String justDigits = digitsUnitSpace.substring(
					0, digitsUnitSpace.length() -2 ); // avoid space and unit abbreviation
			try
			{
				value = Integer.parseInt( justDigits ) * modifier;
			}
			catch ( NumberFormatException nfe )
			{
				System.err.println( "morg just digits should be an integer" );
			}
		}
		return value;
	}


	public List<String> getCategories()
	{
		if ( categoryUses == null || categoryUses.isEmpty() )
		{
			List<String> nothing = new ArrayList<>();
			return nothing;
		}
		else
		{
			Category temp = new Category();
			List<String> replacement = new ArrayList<>( categoryUses.size() );
			for ( String currCategory : categoryUses.keySet()  )
			{
				temp.setCategory( currCategory );
				temp.setLastUsed( categoryUses.get( currCategory ) );
				replacement.add( temp.toString() );
			}
			return replacement;
		}
	}


}




















