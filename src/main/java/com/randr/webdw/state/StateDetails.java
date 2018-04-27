package com.randr.webdw.state;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class StateDetails extends AbstractDetails {
    protected BigDecimal stateId;
    protected String state;
    protected String stateCode;
    protected BigDecimal countryId;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
    public static final BigDecimal FILL_TYPE_JOIN_COUNTRY = new BigDecimal(2);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("state_id", "stateId");
        addMapping("state", "state");
        addMapping("state_code", "stateCode");
        addMapping("country_id", "countryId");
    }

    /**
     * Method getStateId.
     * @return BigDecimal
     */
    public BigDecimal getStateId() {
        return stateId;
    }

    /**
     * Method setStateId.
     * @param stateId BigDecimal
     */
    public void setStateId(BigDecimal stateId) {
        this.stateId = stateId;
    }

    /**
     * Method getState.
     * @return String
     */
    public String getState() {
        return state;
    }

    /**
     * Method setState.
     * @param state String
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Method getCountryId.
     * @return BigDecimal
     */
    public BigDecimal getCountryId() {
        return countryId;
    }

    /**
     * Method setCountryId.
     * @param countryId BigDecimal
     */
    public void setCountryId(BigDecimal countryId) {
        this.countryId = countryId;
    }

    /**
     * Method getStateCode.
     * @return String
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * Method setStateCode.
     * @param stateCode String
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}
