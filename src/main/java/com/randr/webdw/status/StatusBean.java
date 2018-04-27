package com.randr.webdw.status;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class StatusBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "status";
        this.tableAlias = "s";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param statusDetails StatusDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, StatusDetails statusDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(statusDetails);

        // establish the list of properties to be populated
        addColumnList("status_id, status");
        setOrderBy("status");

        return doSelect(statusDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param statusDetails StatusDetails
     */
    private void establishSearchConditions(StatusDetails statusDetails) {
        if (statusDetails.getStatusId() != null) {
            addCondition("status_id= ?");
            addConditionParam(statusDetails.getStatusId());
        }

        if (statusDetails.getStatus() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("status = ?");
                addConditionParam(statusDetails.getStatus());
            } else {
                addCondition("upper(status) = upper('" + Utilities.replaceString(statusDetails.getStatus(), "'", "''") + "')");
            }
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param statusDetails StatusDetails
     * @return StatusDetails
     * @throws Exception
     */
    public StatusDetails insert(BigDecimal fillType, StatusDetails statusDetails) throws Exception {
        statusDetails.setStatusId(getAvailableID("status_id", "status"));
        addColumnList("status_id, status");

        return (StatusDetails) doInsert(statusDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param statusDetails StatusDetails
     * @return StatusDetails
     * @throws Exception
     */
    public StatusDetails update(BigDecimal fillType, StatusDetails statusDetails) throws Exception {
        addCondition("status_id= ?");
        addConditionParam(statusDetails.getStatusId());

        addColumnList("status");

        return (StatusDetails) doUpdate(statusDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param statusDetails StatusDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, StatusDetails statusDetails) throws Exception {
    	
    	if(statusDetails.getStatusId() != null){
        	addCondition("status_id= ?");
            addConditionParam(statusDetails.getStatusId());

            doDelete(statusDetails);
            return true;   		
    	}else{
    		return false;
    	}
    }
}
