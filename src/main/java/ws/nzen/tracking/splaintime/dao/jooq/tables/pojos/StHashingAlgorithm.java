/*
 * This file is generated by jOOQ.
 */
package ws.nzen.tracking.splaintime.dao.jooq.tables.pojos;


import javax.annotation.Generated;

import ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces.IStHashingAlgorithm;


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
public class StHashingAlgorithm implements IStHashingAlgorithm {

    private static final long serialVersionUID = 1343617269;

    private Integer hashingAlgorithmId;
    private String  hashingAlgorithmDesc;

    public StHashingAlgorithm() {}

    public StHashingAlgorithm(IStHashingAlgorithm value) {
        this.hashingAlgorithmId = value.getHashingAlgorithmId();
        this.hashingAlgorithmDesc = value.getHashingAlgorithmDesc();
    }

    public StHashingAlgorithm(
        Integer hashingAlgorithmId,
        String  hashingAlgorithmDesc
    ) {
        this.hashingAlgorithmId = hashingAlgorithmId;
        this.hashingAlgorithmDesc = hashingAlgorithmDesc;
    }

    @Override
    public Integer getHashingAlgorithmId() {
        return this.hashingAlgorithmId;
    }

    @Override
    public void setHashingAlgorithmId(Integer hashingAlgorithmId) {
        this.hashingAlgorithmId = hashingAlgorithmId;
    }

    @Override
    public String getHashingAlgorithmDesc() {
        return this.hashingAlgorithmDesc;
    }

    @Override
    public void setHashingAlgorithmDesc(String hashingAlgorithmDesc) {
        this.hashingAlgorithmDesc = hashingAlgorithmDesc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("StHashingAlgorithm (");

        sb.append(hashingAlgorithmId);
        sb.append(", ").append(hashingAlgorithmDesc);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IStHashingAlgorithm from) {
        setHashingAlgorithmId(from.getHashingAlgorithmId());
        setHashingAlgorithmDesc(from.getHashingAlgorithmDesc());
    }

    @Override
    public <E extends IStHashingAlgorithm> E into(E into) {
        into.from(this);
        return into;
    }
}
