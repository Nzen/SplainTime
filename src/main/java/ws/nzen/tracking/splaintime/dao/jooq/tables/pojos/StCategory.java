/*
 * This file is generated by jOOQ.
 */
package ws.nzen.tracking.splaintime.dao.jooq.tables.pojos;


import java.time.OffsetDateTime;

import javax.annotation.Generated;

import ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces.IStCategory;


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
public class StCategory implements IStCategory {

    private static final long serialVersionUID = 963932224;

    private Integer        categoryId;
    private String         categoryDesc;
    private OffsetDateTime expiresWhen;
    private Integer        typeId;
    private Integer        parentId;

    public StCategory() {}

    public StCategory(IStCategory value) {
        this.categoryId = value.getCategoryId();
        this.categoryDesc = value.getCategoryDesc();
        this.expiresWhen = value.getExpiresWhen();
        this.typeId = value.getTypeId();
        this.parentId = value.getParentId();
    }

    public StCategory(
        Integer        categoryId,
        String         categoryDesc,
        OffsetDateTime expiresWhen,
        Integer        typeId,
        Integer        parentId
    ) {
        this.categoryId = categoryId;
        this.categoryDesc = categoryDesc;
        this.expiresWhen = expiresWhen;
        this.typeId = typeId;
        this.parentId = parentId;
    }

    @Override
    public Integer getCategoryId() {
        return this.categoryId;
    }

    @Override
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String getCategoryDesc() {
        return this.categoryDesc;
    }

    @Override
    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @Override
    public OffsetDateTime getExpiresWhen() {
        return this.expiresWhen;
    }

    @Override
    public void setExpiresWhen(OffsetDateTime expiresWhen) {
        this.expiresWhen = expiresWhen;
    }

    @Override
    public Integer getTypeId() {
        return this.typeId;
    }

    @Override
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public Integer getParentId() {
        return this.parentId;
    }

    @Override
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("StCategory (");

        sb.append(categoryId);
        sb.append(", ").append(categoryDesc);
        sb.append(", ").append(expiresWhen);
        sb.append(", ").append(typeId);
        sb.append(", ").append(parentId);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IStCategory from) {
        setCategoryId(from.getCategoryId());
        setCategoryDesc(from.getCategoryDesc());
        setExpiresWhen(from.getExpiresWhen());
        setTypeId(from.getTypeId());
        setParentId(from.getParentId());
    }

    @Override
    public <E extends IStCategory> E into(E into) {
        into.from(this);
        return into;
    }
}
