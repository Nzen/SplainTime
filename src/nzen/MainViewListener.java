/*
    &copy; Nicholas Prado; License: ../../readme.md

*/

package nzen;

public interface MainViewListener {

	/** Time changed, so the text is invalid */
	public void timeChanged( java.util.Date fireTime );

	/** User supplied text: interpret */
	public void textEntered( String userText, java.util.Date when );

	/** User wants to see the rendered output of the day's tags */
	public void requestsOutput();

	/** User is bored but not finished: save state */
	public void closing();

	/** Clean up & shut down */
	public void finished();

}
