/*
    &copy; Nicholas Prado; License: ../../readme.md

*/

package nzen;

public interface MainGuiModelListener {

	/** Finished processing the diff string, adopt it */
	public void diffChanged( String newDifference );

	/** Finished processing the tag, adopt it */
	public void tagChanged( String newSummary );

	// not sure, it centralizes it, but I'm not going to process besides default times
	// closeAttemptsChanged
}
