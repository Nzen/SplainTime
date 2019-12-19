/*
 * This file is generated by jOOQ.
 */
package ws.nzen.tracking.splaintime.dao.jooq.tables;


import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import ws.nzen.tracking.splaintime.dao.jooq.Indexes;
import ws.nzen.tracking.splaintime.dao.jooq.Keys;
import ws.nzen.tracking.splaintime.dao.jooq.Public;
import ws.nzen.tracking.splaintime.dao.jooq.tables.records.StCategoryRecord;


/**
 * Characterizaton of tag, ex break or cooking
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StCategory extends TableImpl<StCategoryRecord> {

    private static final long serialVersionUID = -1054716422;

    /**
     * The reference instance of <code>PUBLIC.ST_CATEGORY</code>
     */
    public static final StCategory ST_CATEGORY = new StCategory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StCategoryRecord> getRecordType() {
        return StCategoryRecord.class;
    }

    /**
     * The column <code>PUBLIC.ST_CATEGORY.CATEGORY_ID</code>.
     */
    public final TableField<StCategoryRecord, Integer> CATEGORY_ID = createField(DSL.name("CATEGORY_ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>PUBLIC.ST_CATEGORY.CATEGORY_DESC</code>.
     */
    public final TableField<StCategoryRecord, String> CATEGORY_DESC = createField(DSL.name("CATEGORY_DESC"), org.jooq.impl.SQLDataType.VARCHAR(30), this, "");

    /**
     * The column <code>PUBLIC.ST_CATEGORY.EXPIRES_WHEN</code>.
     */
    public final TableField<StCategoryRecord, OffsetDateTime> EXPIRES_WHEN = createField(DSL.name("EXPIRES_WHEN"), org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE.precision(6), this, "");

    /**
     * The column <code>PUBLIC.ST_CATEGORY.TYPE_ID</code>. fk
     */
    public final TableField<StCategoryRecord, Integer> TYPE_ID = createField(DSL.name("TYPE_ID"), org.jooq.impl.SQLDataType.INTEGER, this, "fk");

    /**
     * The column <code>PUBLIC.ST_CATEGORY.PARENT_ID</code>. fk
     */
    public final TableField<StCategoryRecord, Integer> PARENT_ID = createField(DSL.name("PARENT_ID"), org.jooq.impl.SQLDataType.INTEGER, this, "fk");

    /**
     * Create a <code>PUBLIC.ST_CATEGORY</code> table reference
     */
    public StCategory() {
        this(DSL.name("ST_CATEGORY"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.ST_CATEGORY</code> table reference
     */
    public StCategory(String alias) {
        this(DSL.name(alias), ST_CATEGORY);
    }

    /**
     * Create an aliased <code>PUBLIC.ST_CATEGORY</code> table reference
     */
    public StCategory(Name alias) {
        this(alias, ST_CATEGORY);
    }

    private StCategory(Name alias, Table<StCategoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private StCategory(Name alias, Table<StCategoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("Characterizaton of tag, ex break or cooking"));
    }

    public <O extends Record> StCategory(Table<O> child, ForeignKey<O, StCategoryRecord> key) {
        super(child, key, ST_CATEGORY);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.CATEGORY_FK_CATEGORY_INDEX_A, Indexes.CATEGORY_FK_TYPE_INDEX_A, Indexes.PRIMARY_KEY_A);
    }

    @Override
    public Identity<StCategoryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ST_CATEGORY;
    }

    @Override
    public UniqueKey<StCategoryRecord> getPrimaryKey() {
        return Keys.PK_ST_CATEGORY;
    }

    @Override
    public List<UniqueKey<StCategoryRecord>> getKeys() {
        return Arrays.<UniqueKey<StCategoryRecord>>asList(Keys.PK_ST_CATEGORY);
    }

    @Override
    public List<ForeignKey<StCategoryRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<StCategoryRecord, ?>>asList(Keys.CATEGORY_FK_TYPE, Keys.CATEGORY_FK_CATEGORY);
    }

    public StType stType() {
        return new StType(this, Keys.CATEGORY_FK_TYPE);
    }

    public ws.nzen.tracking.splaintime.dao.jooq.tables.StCategory stCategory() {
        return new ws.nzen.tracking.splaintime.dao.jooq.tables.StCategory(this, Keys.CATEGORY_FK_CATEGORY);
    }

    @Override
    public StCategory as(String alias) {
        return new StCategory(DSL.name(alias), this);
    }

    @Override
    public StCategory as(Name alias) {
        return new StCategory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StCategory rename(String name) {
        return new StCategory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public StCategory rename(Name name) {
        return new StCategory(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, String, OffsetDateTime, Integer, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}