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
	// update sec
	// adjustment verbosity
	// try to cleanup leftover files

	
	public void parseConfig( String wholeFile )
	{
		if ( wholeFile == null || wholeFile.isEmpty() )
		{
			return;
		}
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
	
}
