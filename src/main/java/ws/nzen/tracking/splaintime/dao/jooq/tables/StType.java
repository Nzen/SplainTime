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
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import ws.nzen.tracking.splaintime.dao.jooq.Indexes;
import ws.nzen.tracking.splaintime.dao.jooq.Keys;
import ws.nzen.tracking.splaintime.dao.jooq.Public;
import ws.nzen.tracking.splaintime.dao.jooq.tables.records.StTypeRecord;


/**
 * Descriptions that dont need type checking
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StType extends TableImpl<StTypeRecord> {

    private static final long serialVersionUID = -969150328;

    /**
     * The reference instance of <code>PUBLIC.ST_TYPE</code>
     */
    public static final StType ST_TYPE = new StType();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StTypeRecord> getRecordType() {
        return StTypeRecord.class;
    }

    /**
     * The column <code>PUBLIC.ST_TYPE.TYPE_ID</code>.
     */
    public final TableField<StTypeRecord, Integer> TYPE_ID = createField(DSL.name("TYPE_ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>PUBLIC.ST_TYPE.TYPE_DESC</code>.
     */
    public final TableField<StTypeRecord, String> TYPE_DESC = createField(DSL.name("TYPE_DESC"), org.jooq.impl.SQLDataType.VARCHAR(60), this, "");

    /**
     * The column <code>PUBLIC.ST_TYPE.OWN_TYPE</code>. fk; enabling simple heirarchy
     */
    public final TableField<StTypeRecord, Integer> OWN_TYPE = createField(DSL.name("OWN_TYPE"), org.jooq.impl.SQLDataType.INTEGER, this, "fk; enabling simple heirarchy");

    /**
     * Create a <code>PUBLIC.ST_TYPE</code> table reference
     */
    public StType() {
        this(DSL.name("ST_TYPE"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.ST_TYPE</code> table reference
     */
    public StType(String alias) {
        this(DSL.name(alias), ST_TYPE);
    }

    /**
     * Create an aliased <code>PUBLIC.ST_TYPE</code> table reference
     */
    public StType(Name alias) {
        this(alias, ST_TYPE);
    }

    private StType(Name alias, Table<StTypeRecord> aliased) {
        this(alias, aliased, null);
    }

    private StType(Name alias, Table<StTypeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("Descriptions that dont need type checking"));
    }

    public <O extends Record> StType(Table<O> child, ForeignKey<O, StTypeRecord> key) {
        super(child, key, ST_TYPE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_B);
    }

    @Override
    public Identity<StTypeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ST_TYPE;
    }

    @Override
    public UniqueKey<StTypeRecord> getPrimaryKey() {
        return Keys.PK_ST_TYPE;
    }

    @Override
    public List<UniqueKey<StTypeRecord>> getKeys() {
        return Arrays.<UniqueKey<StTypeRecord>>asList(Keys.PK_ST_TYPE);
    }

    @Override
    public List<ForeignKey<StTypeRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<StTypeRecord, ?>>asList(Keys.TYPE_FK_TYPE_RECUR);
    }

    public ws.nzen.tracking.splaintime.dao.jooq.tables.StType stType() {
        return new ws.nzen.tracking.splaintime.dao.jooq.tables.StType(this, Keys.TYPE_FK_TYPE_RECUR);
    }

    @Override
    public StType as(String alias) {
        return new StType(DSL.name(alias), this);
    }

    @Override
    public StType as(Name alias) {
        return new StType(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StType rename(String name) {
        return new StType(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public StType rename(Name name) {
        return new StType(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
