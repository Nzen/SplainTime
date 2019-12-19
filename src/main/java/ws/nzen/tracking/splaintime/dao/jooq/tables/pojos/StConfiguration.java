/*
 * This file is generated by jOOQ.
 */
package ws.nzen.tracking.splaintime.dao.jooq.tables.pojos;


import javax.annotation.Generated;

import ws.nzen.tracking.splaintime.dao.jooq.tables.interfaces.IStConfiguration;


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
public class StConfiguration implements IStConfiguration {

    private static final long serialVersionUID = 1440230216;

    private Integer configurationId;
    private String  configurationDesc;
    private Integer typeId;
    private Integer recordingDeviceId;
    private String  textualValue;
    private Integer integralValue;
    private Boolean binaryValue;

    public StConfiguration() {}

    public StConfiguration(IStConfiguration value) {
        this.configurationId = value.getConfigurationId();
        this.configurationDesc = value.getConfigurationDesc();
        this.typeId = value.getTypeId();
        this.recordingDeviceId = value.getRecordingDeviceId();
        this.textualValue = value.getTextualValue();
        this.integralValue = value.getIntegralValue();
        this.binaryValue = value.getBinaryValue();
    }

    public StConfiguration(
        Integer configurationId,
        String  configurationDesc,
        Integer typeId,
        Integer recordingDeviceId,
        String  textualValue,
        Integer integralValue,
        Boolean binaryValue
    ) {
        this.configurationId = configurationId;
        this.configurationDesc = configurationDesc;
        this.typeId = typeId;
        this.recordingDeviceId = recordingDeviceId;
        this.textualValue = textualValue;
        this.integralValue = integralValue;
        this.binaryValue = binaryValue;
    }

    @Override
    public Integer getConfigurationId() {
        return this.configurationId;
    }

    @Override
    public void setConfigurationId(Integer configurationId) {
        this.configurationId = configurationId;
    }

    @Override
    public String getConfigurationDesc() {
        return this.configurationDesc;
    }

    @Override
    public void setConfigurationDesc(String configurationDesc) {
        this.configurationDesc = configurationDesc;
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
    public Integer getRecordingDeviceId() {
        return this.recordingDeviceId;
    }

    @Override
    public void setRecordingDeviceId(Integer recordingDeviceId) {
        this.recordingDeviceId = recordingDeviceId;
    }

    @Override
    public String getTextualValue() {
        return this.textualValue;
    }

    @Override
    public void setTextualValue(String textualValue) {
        this.textualValue = textualValue;
    }

    @Override
    public Integer getIntegralValue() {
        return this.integralValue;
    }

    @Override
    public void setIntegralValue(Integer integralValue) {
        this.integralValue = integralValue;
    }

    @Override
    public Boolean getBinaryValue() {
        return this.binaryValue;
    }

    @Override
    public void setBinaryValue(Boolean binaryValue) {
        this.binaryValue = binaryValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("StConfiguration (");

        sb.append(configurationId);
        sb.append(", ").append(configurationDesc);
        sb.append(", ").append(typeId);
        sb.append(", ").append(recordingDeviceId);
        sb.append(", ").append(textualValue);
        sb.append(", ").append(integralValue);
        sb.append(", ").append(binaryValue);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IStConfiguration from) {
        setConfigurationId(from.getConfigurationId());
        setConfigurationDesc(from.getConfigurationDesc());
        setTypeId(from.getTypeId());
        setRecordingDeviceId(from.getRecordingDeviceId());
        setTextualValue(from.getTextualValue());
        setIntegralValue(from.getIntegralValue());
        setBinaryValue(from.getBinaryValue());
    }

    @Override
    public <E extends IStConfiguration> E into(E into) {
        into.from(this);
        return into;
    }
}