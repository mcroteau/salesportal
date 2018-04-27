package com.randr.webdw.country;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 * @author randr
 * @version $Revision: 1.2 $
 */
public class CountryBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "country";
        this.tableAlias = "cn";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param countryDetails CountryDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, CountryDetails countryDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(countryDetails);

        // establish the list of properties to be populated
        addColumnList("country_id, country, country_code");
        setOrderBy("country_id");

        return doSelect(countryDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param countryDetails CountryDetails
     */
    private void establishSearchConditions(CountryDetails countryDetails) {
        if (countryDetails.getCountryId() != null) {
            addCondition("country_id = ?");
            addConditionParam(countryDetails.getCountryId());
        }

        if (countryDetails.getCountry() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("country = ?");
                addConditionParam(countryDetails.getCountry());
            } else {
                addCondition("upper(country) = upper('" + Utilities.replaceString(countryDetails.getCountry(), "'", "''") + "')");
            }
        }

        if (countryDetails.getCountryCode() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("country_code = ?");
                addConditionParam(countryDetails.getCountryCode());
            } else {
                addCondition("upper(country) = upper('" + Utilities.replaceString(countryDetails.getCountryCode(), "'", "''") + "')");
            }
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param countryDetails CountryDetails
     * @return CountryDetails
     * @throws Exception
     */
    public CountryDetails insert(BigDecimal fillType, CountryDetails countryDetails) throws Exception {
        countryDetails.setCountryId(getAvailableID("country_id", "country"));
        addColumnList("country_id, country, country_code");

        return (CountryDetails) doInsert(countryDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param countryDetails CountryDetails
     * @return CountryDetails
     * @throws Exception
     */
    public CountryDetails update(BigDecimal fillType, CountryDetails countryDetails) throws Exception {
        addCondition("country_id= ?");
        addConditionParam(countryDetails.getCountryId());

        addColumnList("country, country_code");

        return (CountryDetails) doUpdate(countryDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param countryDetails CountryDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, CountryDetails countryDetails) throws Exception {
    	
    	if(countryDetails.getCountryId() != null){
            addCondition("country_id= ?");
            addConditionParam(countryDetails.getCountryId());

            doDelete(countryDetails);
            return true;
    	}else{
    		return false;
    	}
    }
}
