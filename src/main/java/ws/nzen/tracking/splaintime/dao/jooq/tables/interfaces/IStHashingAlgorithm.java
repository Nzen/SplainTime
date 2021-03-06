/*
 * This file is generated by jOOQ.
 */
package ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * How we hashed the person password
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public interface IStHashingAlgorithm extends Serializable {

    /**
     * Setter for <code>PUBLIC.ST_HASHING_ALGORITHM.HASHING_ALGORITHM_ID</code>.
     */
    public void setHashingAlgorithmId(Integer value);

    /**
     * Getter for <code>PUBLIC.ST_HASHING_ALGORITHM.HASHING_ALGORITHM_ID</code>.
     */
    public Integer getHashingAlgorithmId();

    /**
     * Setter for <code>PUBLIC.ST_HASHING_ALGORITHM.HASHING_ALGORITHM_DESC</code>.
     */
    public void setHashingAlgorithmDesc(String value);

    /**
     * Getter for <code>PUBLIC.ST_HASHING_ALGORITHM.HASHING_ALGORITHM_DESC</code>.
     */
    public String getHashingAlgorithmDesc();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common interface IStHashingAlgorithm
     */
    public void from(ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces.IStHashingAlgorithm from);

    /**
     * Copy data into another generated Record/POJO implementing the common interface IStHashingAlgorithm
     */
    public <E extends ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces.IStHashingAlgorithm> E into(E into);
}
