/** see ../../../../../../../LICENSE for release rights */
package ws.nzen.tracking.splaintime.model;

import java.time.LocalDate;

public class Category
{
	private String category;
	private LocalDate lastUsed;
	

	// IMPROVE I don't need this
	public Category( )
	{
	}
	

	public Category( String entireLine )
	{
		become( entireLine );
	}
	

	public Category( String type, LocalDate when  )
	{
		category = type;
		lastUsed = when;
	}


	public void become( String entireLine )
	{
		if ( entireLine == null || entireLine.isEmpty()
				|| ! entireLine.contains( "\t" ) )
		{
			throw new UnsupportedOperationException( "given an invalidly formatted category" );
		}
		else // ASK version stuff ?
		{
			String[] cat_last = entireLine.split( "\t" );
			final int catInd = 0, usedInd = catInd +1;
			category = cat_last[ catInd ];
			lastUsed = LocalDate.parse( cat_last[ usedInd ] );
		}
	}


	public String toString()
	{
		return category +"\t"+ lastUsed;
	}


	public String getCategory()
	{
		return category;
	}
	public void setCategory( String category )
	{
		this.category = category;
	}


	public LocalDate getLastUsed()
	{
		return lastUsed;
	}
	public void setLastUsed( LocalDate lastUsed )
	{
		this.lastUsed = lastUsed;
	}



}




















