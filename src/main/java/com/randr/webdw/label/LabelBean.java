package com.randr.webdw.label;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;


/**
 * @author randr
 * @version 
 */
public class LabelBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "label";
        this.tableAlias = "lb";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param countryDetails CountryDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, LabelDetails labelDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(labelDetails);

        // establish the list of properties to be populated
        addColumnList("label_id, label_default, label_actual, label_short");
        setOrderBy("label_id");

        return doSelect(labelDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param countryDetails CountryDetails
     */
    private void establishSearchConditions(LabelDetails labelDetails) {
        if (labelDetails.getLabelId() != null) {
            addCondition("label_id = ?");
            addConditionParam(labelDetails.getLabelId());
        }
        if (labelDetails.getLabelDefault() != null) {
            addCondition("label_default = ?");
            addConditionParam(labelDetails.getLabelDefault());
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param countryDetails CountryDetails
     * @return CountryDetails
     * @throws Exception
     */
    public LabelDetails insert(BigDecimal fillType, LabelDetails labelDetails) throws Exception {
    	labelDetails.setLabelId(getAvailableID("label_id", "label"));
        addColumnList("label_id, label_default, label_actual, label_short");

        return (LabelDetails) doInsert(labelDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param countryDetails CountryDetails
     * @return CountryDetails
     * @throws Exception
     */
    public LabelDetails update(BigDecimal fillType, LabelDetails labelDetails) throws Exception {
        addCondition("label_id= ?");
        addConditionParam(labelDetails.getLabelId());

        addColumnList("label_actual, label_default, label_short");

        return (LabelDetails) doUpdate(labelDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param countryDetails CountryDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, LabelDetails labelDetails) throws Exception {
    	
    	if(labelDetails.getLabelId() != null){
        	addCondition("label_id= ?");
            addConditionParam(labelDetails.getLabelId());

            doDelete(labelDetails);
            return true;		
    	}else{
    		return false;
    	}
    }
}
