/* see ../../../../../LICENSE for release details */
package ws.nzen.tracking.splaintime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import ws.nzen.tracking.splaintime.model.Tag;

/**  */
public interface Store
{
	public void add( Tag fromGui );

	public void replaceActiveWith( Tag fromGui );

	public void quickSave();

	public boolean whetherCategory( String someInput, StPreference config );

	public Duration timeSince( String someInput,
			LocalDateTime reference, StPreference config );

	public void wrapUp( StPreference config );

	public OpenResult showStoredTags();

    /** Gets current tag, there will always be one */
    public Tag gPreviousTag();

    /** Gets current tag's start time */
    public Date gPreviousTime();

    public boolean canRemoveOne();

    public void removePrevious();

    public void setRestartTag( String restartTag );

}


















