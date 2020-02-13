/* see ../../../../../LICENSE for release details */
package ws.nzen.tracking.splaintime;

/**  */
public final class NetworkPort
{
	private int number;

	/** Represents a networking endpoint. Zero to max int.
	 * @throws ArrayIndexOutOfBoundsException otherwise */
	public NetworkPort( int nonnegativeNumber )
	{
		if ( nonnegativeNumber <= 0
				|| nonnegativeNumber > 65_535 )
		{
			String problem = Integer.toString( nonnegativeNumber ) +
					(( nonnegativeNumber <= 0 ) ? " is negative" : " too large");
			throw new ArrayIndexOutOfBoundsException( nonnegativeNumber + problem );	
		}
		number = nonnegativeNumber;
	}



	public boolean equals( NetworkPort candidate )
	{
		return number == candidate.number;
	}
	
	public boolean equals( int candidate )
	{
		return number == candidate;
	}


	@Override
	public String toString()
	{
		return Integer.toString( number );
	}


	public int getPort()
	{
		return number;
	}

	public void setPort( int number )
	{
		this.number = number;
	}

}


















