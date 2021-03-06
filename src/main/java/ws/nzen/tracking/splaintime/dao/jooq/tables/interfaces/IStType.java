/*
 * This file is generated by jOOQ.
 */
package ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces;


import java.io.Serializable;

import javax.annotation.Generated;


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
public interface IStType extends Serializable {

    /**
     * Setter for <code>PUBLIC.ST_TYPE.TYPE_ID</code>.
     */
    public void setTypeId(Integer value);

    /**
     * Getter for <code>PUBLIC.ST_TYPE.TYPE_ID</code>.
     */
    public Integer getTypeId();

    /**
     * Setter for <code>PUBLIC.ST_TYPE.TYPE_DESC</code>.
     */
    public void setTypeDesc(String value);

    /**
     * Getter for <code>PUBLIC.ST_TYPE.TYPE_DESC</code>.
     */
    public String getTypeDesc();

    /**
     * Setter for <code>PUBLIC.ST_TYPE.OWN_TYPE</code>. fk; enabling simple heirarchy
     */
    public void setOwnType(Integer value);

    /**
     * Getter for <code>PUBLIC.ST_TYPE.OWN_TYPE</code>. fk; enabling simple heirarchy
     */
    public Integer getOwnType();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common interface IStType
     */
    public void from(ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces.IStType from);

    /**
     * Copy data into another generated Record/POJO implementing the common interface IStType
     */
    public <E extends ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces.IStType> E into(E into);
}
