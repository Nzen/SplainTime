/**
 * 
 */
package ws.nzen.splaintime;

/**
 * Struct for preferences
 */
public class StPreference
{
	private String undoFlag = "j8x";
	private int finishFuse = 2;
	private String subtaskStartFlag = "{";
	/** Means of changing the active text. No time interpretation */
	private String relabelFlag = "f4l";
	//private boolean showSeconds = false;
	// adjustment verbosity
	// try to cleanup leftover files

	
	public void parseConfig( String wholeFile )
	{
		if ( wholeFile == null || wholeFile.isEmpty() )
		{
			return;
		}
		// throw unsupported operation ;; json? xml? Config/yaml?
	}


	public String getUndoFlag()
	{
		return undoFlag;
	}
	public void setUndoFlag( String undoFlag )
	{
		this.undoFlag = undoFlag;
	}

	public int getFinishFuse()
	{
		return finishFuse;
	}
	public void setFinishFuse( int finishFuse )
	{
		this.finishFuse = finishFuse;
	}


	public String getRelabelFlag()
	{
		return relabelFlag;
	}
	public void setRelabelFlag( String relabelFlag )
	{
		this.relabelFlag = relabelFlag;
	}


	public String getSubtaskStartFlag()
	{
		return subtaskStartFlag;
	}
	public void setSubtaskStartFlag( String subtaskStartFlag )
	{
		this.subtaskStartFlag = subtaskStartFlag;
	}
	
	
}
