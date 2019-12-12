/*
 * This file is generated by jOOQ.
 */
package ws.nzen.tracking.splaintime.dao.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
import ws.nzen.tracking.splaintime.dao.jooq.tables.records.StTagCategoryRecord;


/**
 * Categories of a tag
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StTagCategory extends TableImpl<StTagCategoryRecord> {

    private static final long serialVersionUID = 313303830;

    /**
     * The reference instance of <code>PUBLIC.ST_TAG_CATEGORY</code>
     */
    public static final StTagCategory ST_TAG_CATEGORY = new StTagCategory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StTagCategoryRecord> getRecordType() {
        return StTagCategoryRecord.class;
    }

    /**
     * The column <code>PUBLIC.ST_TAG_CATEGORY.TAG_ID</code>. fk
     */
    public final TableField<StTagCategoryRecord, Integer> TAG_ID = createField(DSL.name("TAG_ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "fk");

    /**
     * The column <code>PUBLIC.ST_TAG_CATEGORY.CATEGORY_ID</code>. fk
     */
    public final TableField<StTagCategoryRecord, Integer> CATEGORY_ID = createField(DSL.name("CATEGORY_ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "fk");

    /**
     * Create a <code>PUBLIC.ST_TAG_CATEGORY</code> table reference
     */
    public StTagCategory() {
        this(DSL.name("ST_TAG_CATEGORY"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.ST_TAG_CATEGORY</code> table reference
     */
    public StTagCategory(String alias) {
        this(DSL.name(alias), ST_TAG_CATEGORY);
    }

    /**
     * Create an aliased <code>PUBLIC.ST_TAG_CATEGORY</code> table reference
     */
    public StTagCategory(Name alias) {
        this(alias, ST_TAG_CATEGORY);
    }

    private StTagCategory(Name alias, Table<StTagCategoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private StTagCategory(Name alias, Table<StTagCategoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("Categories of a tag"));
    }

    public <O extends Record> StTagCategory(Table<O> child, ForeignKey<O, StTagCategoryRecord> key) {
        super(child, key, ST_TAG_CATEGORY);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_D5, Indexes.TAGCATEGORY_FK_CATEGORY_INDEX_D, Indexes.TAGCATEGORY_FK_TAG_INDEX_D);
    }

    @Override
    public UniqueKey<StTagCategoryRecord> getPrimaryKey() {
        return Keys.TAGCATEGORY_PK;
    }

    @Override
    public List<UniqueKey<StTagCategoryRecord>> getKeys() {
        return Arrays.<UniqueKey<StTagCategoryRecord>>asList(Keys.TAGCATEGORY_PK);
    }

    @Override
    public List<ForeignKey<StTagCategoryRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<StTagCategoryRecord, ?>>asList(Keys.TAGCATEGORY_FK_TAG, Keys.TAGCATEGORY_FK_CATEGORY);
    }

    public StTag stTag() {
        return new StTag(this, Keys.TAGCATEGORY_FK_TAG);
    }

    public StCategory stCategory() {
        return new StCategory(this, Keys.TAGCATEGORY_FK_CATEGORY);
    }

    @Override
    public StTagCategory as(String alias) {
        return new StTagCategory(DSL.name(alias), this);
    }

    @Override
    public StTagCategory as(Name alias) {
        return new StTagCategory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StTagCategory rename(String name) {
        return new StTagCategory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public StTagCategory rename(Name name) {
        return new StTagCategory(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
