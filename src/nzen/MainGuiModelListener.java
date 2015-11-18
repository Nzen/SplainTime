/*
    &copy; Nicholas Prado; License: ../../readme.md

*/

package nzen;

public interface MainGuiModelListener {

	/** Finished processing the diff string, adopt it */
	public void diffChanged( String newDifference );

	/** Finished processing the tag, adopt it */
	public void tagChanged( String newSummary );

	// not sure, it centralizes close attempts, but no processing
	// public void closeAttemptsChanged( int newAttemptsLeft );
}
