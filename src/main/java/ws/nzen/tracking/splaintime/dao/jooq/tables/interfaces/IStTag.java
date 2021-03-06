/*
 * This file is generated by jOOQ.
 */
package ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces;


import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.annotation.Generated;


/**
 * The user entered text about what happened during that time
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public interface IStTag extends Serializable {

    /**
     * Setter for <code>PUBLIC.ST_TAG.TAG_ID</code>.
     */
    public void setTagId(Integer value);

    /**
     * Getter for <code>PUBLIC.ST_TAG.TAG_ID</code>.
     */
    public Integer getTagId();

    /**
     * Setter for <code>PUBLIC.ST_TAG.HAPPENED_WHEN</code>. ASK is this the resolved date or only when entered
     */
    public void setHappenedWhen(OffsetDateTime value);

    /**
     * Getter for <code>PUBLIC.ST_TAG.HAPPENED_WHEN</code>. ASK is this the resolved date or only when entered
     */
    public OffsetDateTime getHappenedWhen();

    /**
     * Setter for <code>PUBLIC.ST_TAG.ADJUSTED_WHEN</code>. ASK is this null when not adjusted
     */
    public void setAdjustedWhen(OffsetDateTime value);

    /**
     * Getter for <code>PUBLIC.ST_TAG.ADJUSTED_WHEN</code>. ASK is this null when not adjusted
     */
    public OffsetDateTime getAdjustedWhen();

    /**
     * Setter for <code>PUBLIC.ST_TAG.RECORDING_DEVICE_ID</code>. fk
     */
    public void setRecordingDeviceId(Integer value);

    /**
     * Getter for <code>PUBLIC.ST_TAG.RECORDING_DEVICE_ID</code>. fk
     */
    public Integer getRecordingDeviceId();

    /**
     * Setter for <code>PUBLIC.ST_TAG.PERSON_ID</code>. fk
     */
    public void setPersonId(Integer value);

    /**
     * Getter for <code>PUBLIC.ST_TAG.PERSON_ID</code>. fk
     */
    public Integer getPersonId();

    /**
     * Setter for <code>PUBLIC.ST_TAG.TAG_VALUE</code>.
     */
    public void setTagValue(String value);

    /**
     * Getter for <code>PUBLIC.ST_TAG.TAG_VALUE</code>.
     */
    public String getTagValue();

    /**
     * Setter for <code>PUBLIC.ST_TAG.ADJUSTED_WITH_HHMM</code>. Whether used -4 or -1:30
     */
    public void setAdjustedWithHhmm(Boolean value);

    /**
     * Getter for <code>PUBLIC.ST_TAG.ADJUSTED_WITH_HHMM</code>. Whether used -4 or -1:30
     */
    public Boolean getAdjustedWithHhmm();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common interface IStTag
     */
    public void from(ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces.IStTag from);

    /**
     * Copy data into another generated Record/POJO implementing the common interface IStTag
     */
    public <E extends ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces.IStTag> E into(E into);
}
