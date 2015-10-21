/*
    &copy; Nicholas Prado; License: ../../readme.md

  Next:
Decide if this TagStore should eat this
*/

package nzen;

public class GuiModel {

	private String timeDiff;
	private String prevSummary;
	// int flubs ?
	private MainGuiModelListener theView;

	public GuiModel() {
		timeDiff = "0";
		prevSummary = "Started up";
		theView = null;
	}

	public GuiModel( String diff, String summary ) {
		timeDiff = diff;
		prevSummary = summary;
		theView = null;
	}

	// --

	public void setGmListener( MainGuiModelListener sgml ) {
		theView = sgml;
	}

	// perhaps get() too ?

	public void removeGmListener() {
		theView = null;
	}

	// --

	public String getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(String timeDiff) {
		this.timeDiff = timeDiff;
		theView.diffChanged(timeDiff);
	}

	public String getPrevSummary() {
		return prevSummary;
	}

	public void setPrevSummary(String prevSummary) {
		this.prevSummary = prevSummary;
		theView.tagChanged(prevSummary);
	}

}
