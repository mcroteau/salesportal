package com.randr.webdw.country;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CountryDetails extends AbstractDetails {
    protected BigDecimal countryId;
    protected String country;
    protected String countryCode;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("country_id", "countryId");
        addMapping("country", "country");
        addMapping("country_code", "countryCode");
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
     * Method getCountry.
     * @return String
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method setCountry.
     * @param country String
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Method getCountryCode.
     * @return String
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Method setCountryCode.
     * @param countryCode String
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
