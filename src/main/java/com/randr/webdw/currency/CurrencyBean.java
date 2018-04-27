package com.randr.webdw.currency;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class CurrencyBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "currency";
        this.tableAlias = "c";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, CurrencyDetails currencyDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(currencyDetails);

        // establish the list of properties to be populated
        addColumnList("currency_id, currency_code, currency_description, creation_date, created_by_user, update_date, updated_by_user");
        setOrderBy("currency_description");

        return doSelect(currencyDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param lobDetails LobDetails
     */
    private void establishSearchConditions(CurrencyDetails currencyDetails) {
        if (currencyDetails.getCurrencyId() != null) {
            addCondition("currency_id= ?");
            addConditionParam(currencyDetails.getCurrencyId());
        }

        if (currencyDetails.getCurrencyDescription() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("currency_description = ?");
                addConditionParam(currencyDetails.getCurrencyDescription());
            } else {
                addCondition("upper(currency_description) = upper('" + Utilities.replaceString(currencyDetails.getCurrencyDescription(), "'", "''") + "')");
            }
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return LobDetails
     * @throws Exception
     */
    public CurrencyDetails insert(BigDecimal fillType, CurrencyDetails currencyDetails) throws Exception {
    	currencyDetails.setCurrencyId(getAvailableID("currency_id", "currency"));
        addColumnList("currency_id, currency_code");
        addColumnList("currency_description, creation_date, created_by_user, update_date, updated_by_user");
        return (CurrencyDetails) doInsert(currencyDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return LobDetails
     * @throws Exception
     */
    public CurrencyDetails update(BigDecimal fillType, CurrencyDetails currencyDetails) throws Exception {
        addCondition("currency_id= ?");
        addConditionParam(currencyDetails.getCurrencyId());

        addColumnList("currency_code");
        addColumnList("currency_description, update_date, updated_by_user");
        return (CurrencyDetails) doUpdate(currencyDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, CurrencyDetails currencyDetails) throws Exception {
    	
    	if(currencyDetails.getCurrencyId() != null){
            addCondition("currency_id= ?");
            addConditionParam(currencyDetails.getCurrencyId());

            doDelete(currencyDetails);
            return true;    		
    	}else{
    		return false;
    	}

    }
}
