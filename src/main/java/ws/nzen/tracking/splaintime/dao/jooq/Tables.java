/*
 * This file is generated by jOOQ.
 */
package ws.nzen.tracking.splaintime.dao.jooq;


import javax.annotation.Generated;

import ws.nzen.tracking.splaintime.dao.jooq.tables.StCategory;
import ws.nzen.tracking.splaintime.dao.jooq.tables.StConfiguration;
import ws.nzen.tracking.splaintime.dao.jooq.tables.StHashingAlgorithm;
import ws.nzen.tracking.splaintime.dao.jooq.tables.StPerson;
import ws.nzen.tracking.splaintime.dao.jooq.tables.StRecordingDevice;
import ws.nzen.tracking.splaintime.dao.jooq.tables.StRecordingDeviceType;
import ws.nzen.tracking.splaintime.dao.jooq.tables.StTag;
import ws.nzen.tracking.splaintime.dao.jooq.tables.StTagCategory;
import ws.nzen.tracking.splaintime.dao.jooq.tables.StType;


/**
 * Convenience access to all tables in PUBLIC
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * Characterizaton of tag, ex break or cooking
     */
    public static final StCategory ST_CATEGORY = StCategory.ST_CATEGORY;

    /**
     * Arbitrary attributes for the runtime
     */
    public static final StConfiguration ST_CONFIGURATION = StConfiguration.ST_CONFIGURATION;

    /**
     * How we hashed the person password
     */
    public static final StHashingAlgorithm ST_HASHING_ALGORITHM = StHashingAlgorithm.ST_HASHING_ALGORITHM;

    /**
     * ST only expects one, but thats fine
     */
    public static final StPerson ST_PERSON = StPerson.ST_PERSON;

    /**
     * For representing the pc, phone that recorded tags
     */
    public static final StRecordingDevice ST_RECORDING_DEVICE = StRecordingDevice.ST_RECORDING_DEVICE;

    /**
     * Computer; phone
     */
    public static final StRecordingDeviceType ST_RECORDING_DEVICE_TYPE = StRecordingDeviceType.ST_RECORDING_DEVICE_TYPE;

    /**
     * The user entered text about what happened during that time
     */
    public static final StTag ST_TAG = StTag.ST_TAG;

    /**
     * Categories of a tag
     */
    public static final StTagCategory ST_TAG_CATEGORY = StTagCategory.ST_TAG_CATEGORY;

    /**
     * Descriptions that dont need type checking
     */
    public static final StType ST_TYPE = StType.ST_TYPE;
}
