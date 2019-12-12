/*
 * This file is generated by jOOQ.
 */
package ws.nzen.tracking.splaintime.dao.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import ws.nzen.tracking.splaintime.dao.jooq.Indexes;
import ws.nzen.tracking.splaintime.dao.jooq.Keys;
import ws.nzen.tracking.splaintime.dao.jooq.Public;
import ws.nzen.tracking.splaintime.dao.jooq.tables.records.StRecordingDeviceTypeRecord;


/**
 * Computer; phone
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StRecordingDeviceType extends TableImpl<StRecordingDeviceTypeRecord> {

    private static final long serialVersionUID = 1116180774;

    /**
     * The reference instance of <code>PUBLIC.ST_RECORDING_DEVICE_TYPE</code>
     */
    public static final StRecordingDeviceType ST_RECORDING_DEVICE_TYPE = new StRecordingDeviceType();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StRecordingDeviceTypeRecord> getRecordType() {
        return StRecordingDeviceTypeRecord.class;
    }

    /**
     * The column <code>PUBLIC.ST_RECORDING_DEVICE_TYPE.RECORDING_DEVICE_TYPE_ID</code>.
     */
    public final TableField<StRecordingDeviceTypeRecord, Integer> RECORDING_DEVICE_TYPE_ID = createField(DSL.name("RECORDING_DEVICE_TYPE_ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>PUBLIC.ST_RECORDING_DEVICE_TYPE.RECORDING_DEVICE_TYPE_DESC</code>.
     */
    public final TableField<StRecordingDeviceTypeRecord, String> RECORDING_DEVICE_TYPE_DESC = createField(DSL.name("RECORDING_DEVICE_TYPE_DESC"), org.jooq.impl.SQLDataType.VARCHAR(30), this, "");

    /**
     * Create a <code>PUBLIC.ST_RECORDING_DEVICE_TYPE</code> table reference
     */
    public StRecordingDeviceType() {
        this(DSL.name("ST_RECORDING_DEVICE_TYPE"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.ST_RECORDING_DEVICE_TYPE</code> table reference
     */
    public StRecordingDeviceType(String alias) {
        this(DSL.name(alias), ST_RECORDING_DEVICE_TYPE);
    }

    /**
     * Create an aliased <code>PUBLIC.ST_RECORDING_DEVICE_TYPE</code> table reference
     */
    public StRecordingDeviceType(Name alias) {
        this(alias, ST_RECORDING_DEVICE_TYPE);
    }

    private StRecordingDeviceType(Name alias, Table<StRecordingDeviceTypeRecord> aliased) {
        this(alias, aliased, null);
    }

    private StRecordingDeviceType(Name alias, Table<StRecordingDeviceTypeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("Computer; phone"));
    }

    public <O extends Record> StRecordingDeviceType(Table<O> child, ForeignKey<O, StRecordingDeviceTypeRecord> key) {
        super(child, key, ST_RECORDING_DEVICE_TYPE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_4);
    }

    @Override
    public Identity<StRecordingDeviceTypeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ST_RECORDING_DEVICE_TYPE;
    }

    @Override
    public UniqueKey<StRecordingDeviceTypeRecord> getPrimaryKey() {
        return Keys.PK_ST_RECORDING_DEVICE_TYPE;
    }

    @Override
    public List<UniqueKey<StRecordingDeviceTypeRecord>> getKeys() {
        return Arrays.<UniqueKey<StRecordingDeviceTypeRecord>>asList(Keys.PK_ST_RECORDING_DEVICE_TYPE);
    }

    @Override
    public StRecordingDeviceType as(String alias) {
        return new StRecordingDeviceType(DSL.name(alias), this);
    }

    @Override
    public StRecordingDeviceType as(Name alias) {
        return new StRecordingDeviceType(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StRecordingDeviceType rename(String name) {
        return new StRecordingDeviceType(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public StRecordingDeviceType rename(Name name) {
        return new StRecordingDeviceType(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
