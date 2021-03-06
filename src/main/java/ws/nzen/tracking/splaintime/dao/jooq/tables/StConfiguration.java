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
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import ws.nzen.tracking.splaintime.dao.jooq.Indexes;
import ws.nzen.tracking.splaintime.dao.jooq.Keys;
import ws.nzen.tracking.splaintime.dao.jooq.Public;
import ws.nzen.tracking.splaintime.dao.jooq.tables.records.StConfigurationRecord;


/**
 * Arbitrary attributes for the runtime
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StConfiguration extends TableImpl<StConfigurationRecord> {

    private static final long serialVersionUID = 348367206;

    /**
     * The reference instance of <code>PUBLIC.ST_CONFIGURATION</code>
     */
    public static final StConfiguration ST_CONFIGURATION = new StConfiguration();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StConfigurationRecord> getRecordType() {
        return StConfigurationRecord.class;
    }

    /**
     * The column <code>PUBLIC.ST_CONFIGURATION.CONFIGURATION_ID</code>.
     */
    public final TableField<StConfigurationRecord, Integer> CONFIGURATION_ID = createField(DSL.name("CONFIGURATION_ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>PUBLIC.ST_CONFIGURATION.CONFIGURATION_DESC</code>.
     */
    public final TableField<StConfigurationRecord, String> CONFIGURATION_DESC = createField(DSL.name("CONFIGURATION_DESC"), org.jooq.impl.SQLDataType.VARCHAR(35), this, "");

    /**
     * The column <code>PUBLIC.ST_CONFIGURATION.TYPE_ID</code>. fk; redundant
     */
    public final TableField<StConfigurationRecord, Integer> TYPE_ID = createField(DSL.name("TYPE_ID"), org.jooq.impl.SQLDataType.INTEGER, this, "fk; redundant");

    /**
     * The column <code>PUBLIC.ST_CONFIGURATION.RECORDING_DEVICE_ID</code>. fk; ex to enable an easier undo string on a phone
     */
    public final TableField<StConfigurationRecord, Integer> RECORDING_DEVICE_ID = createField(DSL.name("RECORDING_DEVICE_ID"), org.jooq.impl.SQLDataType.INTEGER, this, "fk; ex to enable an easier undo string on a phone");

    /**
     * The column <code>PUBLIC.ST_CONFIGURATION.TEXTUAL_VALUE</code>. Large, in case ST allows custom datetime format
     */
    public final TableField<StConfigurationRecord, String> TEXTUAL_VALUE = createField(DSL.name("TEXTUAL_VALUE"), org.jooq.impl.SQLDataType.VARCHAR(30), this, "Large, in case ST allows custom datetime format");

    /**
     * The column <code>PUBLIC.ST_CONFIGURATION.INTEGRAL_VALUE</code>.
     */
    public final TableField<StConfigurationRecord, Integer> INTEGRAL_VALUE = createField(DSL.name("INTEGRAL_VALUE"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>PUBLIC.ST_CONFIGURATION.BINARY_VALUE</code>.
     */
    public final TableField<StConfigurationRecord, Boolean> BINARY_VALUE = createField(DSL.name("BINARY_VALUE"), org.jooq.impl.SQLDataType.BOOLEAN, this, "");

    /**
     * Create a <code>PUBLIC.ST_CONFIGURATION</code> table reference
     */
    public StConfiguration() {
        this(DSL.name("ST_CONFIGURATION"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.ST_CONFIGURATION</code> table reference
     */
    public StConfiguration(String alias) {
        this(DSL.name(alias), ST_CONFIGURATION);
    }

    /**
     * Create an aliased <code>PUBLIC.ST_CONFIGURATION</code> table reference
     */
    public StConfiguration(Name alias) {
        this(alias, ST_CONFIGURATION);
    }

    private StConfiguration(Name alias, Table<StConfigurationRecord> aliased) {
        this(alias, aliased, null);
    }

    private StConfiguration(Name alias, Table<StConfigurationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("Arbitrary attributes for the runtime"));
    }

    public <O extends Record> StConfiguration(Table<O> child, ForeignKey<O, StConfigurationRecord> key) {
        super(child, key, ST_CONFIGURATION);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.CONFIGURATION_FK_RECDEVICE_INDEX_9, Indexes.CONFIGURATION_FK_TYPE_INDEX_9, Indexes.PRIMARY_KEY_9C);
    }

    @Override
    public Identity<StConfigurationRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ST_CONFIGURATION;
    }

    @Override
    public UniqueKey<StConfigurationRecord> getPrimaryKey() {
        return Keys.PK_ST_CONFIGURATION;
    }

    @Override
    public List<UniqueKey<StConfigurationRecord>> getKeys() {
        return Arrays.<UniqueKey<StConfigurationRecord>>asList(Keys.PK_ST_CONFIGURATION);
    }

    @Override
    public List<ForeignKey<StConfigurationRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<StConfigurationRecord, ?>>asList(Keys.CONFIGURATION_FK_TYPE, Keys.CONFIGURATION_FK_RECDEVICE);
    }

    public StType stType() {
        return new StType(this, Keys.CONFIGURATION_FK_TYPE);
    }

    public StRecordingDevice stRecordingDevice() {
        return new StRecordingDevice(this, Keys.CONFIGURATION_FK_RECDEVICE);
    }

    @Override
    public StConfiguration as(String alias) {
        return new StConfiguration(DSL.name(alias), this);
    }

    @Override
    public StConfiguration as(Name alias) {
        return new StConfiguration(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StConfiguration rename(String name) {
        return new StConfiguration(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public StConfiguration rename(Name name) {
        return new StConfiguration(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, String, Integer, Integer, String, Integer, Boolean> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
