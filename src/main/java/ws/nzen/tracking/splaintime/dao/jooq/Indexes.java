/*
 * This file is generated by jOOQ.
 */
package ws.nzen.tracking.splaintime.dao.jooq;


import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;

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
 * A class modelling indexes of tables of the <code>PUBLIC</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index CATEGORY_FK_CATEGORY_INDEX_A = Indexes0.CATEGORY_FK_CATEGORY_INDEX_A;
    public static final Index CATEGORY_FK_TYPE_INDEX_A = Indexes0.CATEGORY_FK_TYPE_INDEX_A;
    public static final Index PRIMARY_KEY_A = Indexes0.PRIMARY_KEY_A;
    public static final Index CONFIGURATION_FK_RECDEVICE_INDEX_9 = Indexes0.CONFIGURATION_FK_RECDEVICE_INDEX_9;
    public static final Index CONFIGURATION_FK_TYPE_INDEX_9 = Indexes0.CONFIGURATION_FK_TYPE_INDEX_9;
    public static final Index PRIMARY_KEY_9C = Indexes0.PRIMARY_KEY_9C;
    public static final Index PRIMARY_KEY_2 = Indexes0.PRIMARY_KEY_2;
    public static final Index PERSON_FK_HASH_INDEX_5 = Indexes0.PERSON_FK_HASH_INDEX_5;
    public static final Index PRIMARY_KEY_5 = Indexes0.PRIMARY_KEY_5;
    public static final Index PRIMARY_KEY_E = Indexes0.PRIMARY_KEY_E;
    public static final Index RECDEVICE_FK_RECDEVICE_TYPE_INDEX_E = Indexes0.RECDEVICE_FK_RECDEVICE_TYPE_INDEX_E;
    public static final Index PRIMARY_KEY_4 = Indexes0.PRIMARY_KEY_4;
    public static final Index PRIMARY_KEY_EC = Indexes0.PRIMARY_KEY_EC;
    public static final Index TAG_FK_PERSON_INDEX_E = Indexes0.TAG_FK_PERSON_INDEX_E;
    public static final Index TAG_FK_RECDEVICE_INDEX_E = Indexes0.TAG_FK_RECDEVICE_INDEX_E;
    public static final Index PRIMARY_KEY_D5 = Indexes0.PRIMARY_KEY_D5;
    public static final Index TAGCATEGORY_FK_CATEGORY_INDEX_D = Indexes0.TAGCATEGORY_FK_CATEGORY_INDEX_D;
    public static final Index TAGCATEGORY_FK_TAG_INDEX_D = Indexes0.TAGCATEGORY_FK_TAG_INDEX_D;
    public static final Index PRIMARY_KEY_B = Indexes0.PRIMARY_KEY_B;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index CATEGORY_FK_CATEGORY_INDEX_A = Internal.createIndex("CATEGORY_FK_CATEGORY_INDEX_A", StCategory.ST_CATEGORY, new OrderField[] { StCategory.ST_CATEGORY.PARENT_ID }, false);
        public static Index CATEGORY_FK_TYPE_INDEX_A = Internal.createIndex("CATEGORY_FK_TYPE_INDEX_A", StCategory.ST_CATEGORY, new OrderField[] { StCategory.ST_CATEGORY.TYPE_ID }, false);
        public static Index PRIMARY_KEY_A = Internal.createIndex("PRIMARY_KEY_A", StCategory.ST_CATEGORY, new OrderField[] { StCategory.ST_CATEGORY.CATEGORY_ID }, true);
        public static Index CONFIGURATION_FK_RECDEVICE_INDEX_9 = Internal.createIndex("CONFIGURATION_FK_RECDEVICE_INDEX_9", StConfiguration.ST_CONFIGURATION, new OrderField[] { StConfiguration.ST_CONFIGURATION.RECORDING_DEVICE_ID }, false);
        public static Index CONFIGURATION_FK_TYPE_INDEX_9 = Internal.createIndex("CONFIGURATION_FK_TYPE_INDEX_9", StConfiguration.ST_CONFIGURATION, new OrderField[] { StConfiguration.ST_CONFIGURATION.TYPE_ID }, false);
        public static Index PRIMARY_KEY_9C = Internal.createIndex("PRIMARY_KEY_9C", StConfiguration.ST_CONFIGURATION, new OrderField[] { StConfiguration.ST_CONFIGURATION.CONFIGURATION_ID }, true);
        public static Index PRIMARY_KEY_2 = Internal.createIndex("PRIMARY_KEY_2", StHashingAlgorithm.ST_HASHING_ALGORITHM, new OrderField[] { StHashingAlgorithm.ST_HASHING_ALGORITHM.HASHING_ALGORITHM_ID }, true);
        public static Index PERSON_FK_HASH_INDEX_5 = Internal.createIndex("PERSON_FK_HASH_INDEX_5", StPerson.ST_PERSON, new OrderField[] { StPerson.ST_PERSON.HASHING_ALGORITHM_ID }, false);
        public static Index PRIMARY_KEY_5 = Internal.createIndex("PRIMARY_KEY_5", StPerson.ST_PERSON, new OrderField[] { StPerson.ST_PERSON.PERSON_ID }, true);
        public static Index PRIMARY_KEY_E = Internal.createIndex("PRIMARY_KEY_E", StRecordingDevice.ST_RECORDING_DEVICE, new OrderField[] { StRecordingDevice.ST_RECORDING_DEVICE.RECORDING_DEVICE_ID }, true);
        public static Index RECDEVICE_FK_RECDEVICE_TYPE_INDEX_E = Internal.createIndex("RECDEVICE_FK_RECDEVICE_TYPE_INDEX_E", StRecordingDevice.ST_RECORDING_DEVICE, new OrderField[] { StRecordingDevice.ST_RECORDING_DEVICE.RECORDING_DEVICE_TYPE_ID }, false);
        public static Index PRIMARY_KEY_4 = Internal.createIndex("PRIMARY_KEY_4", StRecordingDeviceType.ST_RECORDING_DEVICE_TYPE, new OrderField[] { StRecordingDeviceType.ST_RECORDING_DEVICE_TYPE.RECORDING_DEVICE_TYPE_ID }, true);
        public static Index PRIMARY_KEY_EC = Internal.createIndex("PRIMARY_KEY_EC", StTag.ST_TAG, new OrderField[] { StTag.ST_TAG.TAG_ID }, true);
        public static Index TAG_FK_PERSON_INDEX_E = Internal.createIndex("TAG_FK_PERSON_INDEX_E", StTag.ST_TAG, new OrderField[] { StTag.ST_TAG.PERSON_ID }, false);
        public static Index TAG_FK_RECDEVICE_INDEX_E = Internal.createIndex("TAG_FK_RECDEVICE_INDEX_E", StTag.ST_TAG, new OrderField[] { StTag.ST_TAG.RECORDING_DEVICE_ID }, false);
        public static Index PRIMARY_KEY_D5 = Internal.createIndex("PRIMARY_KEY_D5", StTagCategory.ST_TAG_CATEGORY, new OrderField[] { StTagCategory.ST_TAG_CATEGORY.TAG_ID, StTagCategory.ST_TAG_CATEGORY.CATEGORY_ID }, true);
        public static Index TAGCATEGORY_FK_CATEGORY_INDEX_D = Internal.createIndex("TAGCATEGORY_FK_CATEGORY_INDEX_D", StTagCategory.ST_TAG_CATEGORY, new OrderField[] { StTagCategory.ST_TAG_CATEGORY.CATEGORY_ID }, false);
        public static Index TAGCATEGORY_FK_TAG_INDEX_D = Internal.createIndex("TAGCATEGORY_FK_TAG_INDEX_D", StTagCategory.ST_TAG_CATEGORY, new OrderField[] { StTagCategory.ST_TAG_CATEGORY.TAG_ID }, false);
        public static Index PRIMARY_KEY_B = Internal.createIndex("PRIMARY_KEY_B", StType.ST_TYPE, new OrderField[] { StType.ST_TYPE.TYPE_ID }, true);
    }
}
