package com.randr.webdw.systemType;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class SystemTypeBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "system_type";
        this.tableAlias = "sy";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param systemTypeDetails SystemTypeDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, SystemTypeDetails systemTypeDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(systemTypeDetails);

        // establish the list of properties to be populated
        addColumnList("system_type_id, system_type");
        setOrderBy("system_type");

        return doSelect(systemTypeDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param systemTypeDetails SystemTypeDetails
     */
    private void establishSearchConditions(SystemTypeDetails systemTypeDetails) {
        if (systemTypeDetails.getSystemTypeId() != null) {
            addCondition("system_type_id= ?");
            addConditionParam(systemTypeDetails.getSystemTypeId());
        }

        if (systemTypeDetails.getSystemType() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("system_type = ?");
                addConditionParam(systemTypeDetails.getSystemType());
            } else {
                addCondition("upper(system_type) = upper('" + Utilities.replaceString(systemTypeDetails.getSystemType(), "'", "''") + "')");
            }
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param systemTypeDetails SystemTypeDetails
     * @return SystemTypeDetails
     * @throws Exception
     */
    public SystemTypeDetails insert(BigDecimal fillType, SystemTypeDetails systemTypeDetails) throws Exception {
        systemTypeDetails.setSystemTypeId(getAvailableID("system_type_id", "system_type"));
        addColumnList("system_type_id, system_type");

        return (SystemTypeDetails) doInsert(systemTypeDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param systemTypeDetails SystemTypeDetails
     * @return SystemTypeDetails
     * @throws Exception
     */
    public SystemTypeDetails update(BigDecimal fillType, SystemTypeDetails systemTypeDetails) throws Exception {
        addCondition("system_type_id= ?");
        addConditionParam(systemTypeDetails.getSystemTypeId());

        addColumnList("system_type");

        return (SystemTypeDetails) doUpdate(systemTypeDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param systemTypeDetails SystemTypeDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, SystemTypeDetails systemTypeDetails) throws Exception {
        
    	if(systemTypeDetails.getSystemTypeId() != null){
        	addCondition("system_type_id= ?");
            addConditionParam(systemTypeDetails.getSystemTypeId());

            doDelete(systemTypeDetails);
            return true;  		
    	}else{
    		return false;
    	}

    }
}
