package com.randr.webdw.state;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class StateBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "state";
        this.tableAlias = "sta";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param stateDetails StateDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, StateDetails stateDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(stateDetails);

        // establish the list of properties to be populated
        addColumnList("state_id, state, state_code");
        if (fillType.equals(StateDetails.FILL_TYPE_JOIN_COUNTRY)) {
            addColumnList("cn.country_id");
            addOtherTables(" right outer join " +
                           AppSettings.getParm("LIB") + "country cn on cn.country_id = sta.country_id");

        }
        if (fillType.equals(StateDetails.FILL_TYPE_ALL)) {
            addColumnList("country_id");
        }
        setOrderBy("country_id, state");
        return doSelect(stateDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param stateDetails StateDetails
     */
    private void establishSearchConditions(StateDetails stateDetails) {
        if (stateDetails.getStateId() != null) {
            addCondition("state_id= ?");
            addConditionParam(stateDetails.getStateId());
        }

        if (stateDetails.getStateCode() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("state_code = ?");
                addConditionParam(stateDetails.getStateCode());
            } else {
                addCondition("upper(state_code) = upper('" + Utilities.replaceString(stateDetails.getStateCode(), "'", "''") + "')");
            }
        }

        if (stateDetails.getCountryId() != null) {
            addCondition("country_id= ?");
            addConditionParam(stateDetails.getCountryId());
        }

        if (stateDetails.getState() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("state = ?");
                addConditionParam(stateDetails.getState());
            } else {
                addCondition("upper(state) = upper('" + Utilities.replaceString(stateDetails.getState(), "'", "''") + "')");
            }
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param stateDetails StateDetails
     * @return StateDetails
     * @throws Exception
     */
    public StateDetails insert(BigDecimal fillType, StateDetails stateDetails) throws Exception {
        stateDetails.setStateId(getAvailableID("state_id", "state"));
        addColumnList("state_id, state, state_code, country_id");

        return (StateDetails) doInsert(stateDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param stateDetails StateDetails
     * @return StateDetails
     * @throws Exception
     */
    public StateDetails update(BigDecimal fillType, StateDetails stateDetails) throws Exception {
        addCondition("state_id= ?");
        addConditionParam(stateDetails.getStateId());

        addColumnList("state");
        addColumnList("state_code");
        addColumnList("country_id");

        return (StateDetails) doUpdate(stateDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param stateDetails StateDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, StateDetails stateDetails) throws Exception {
    	
    	if(stateDetails.getStateId() != null){
        	addCondition("state_id= ?");
            addConditionParam(stateDetails.getStateId());

            doDelete(stateDetails);
            return true;    		
    	}else{
    		return false;
    	}

    }
}
