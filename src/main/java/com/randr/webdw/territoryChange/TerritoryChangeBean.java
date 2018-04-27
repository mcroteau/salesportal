package com.randr.webdw.territoryChange;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;


/**
 */
public class TerritoryChangeBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "territory_change";
        this.tableAlias = "tch";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param territoryChangeDetails TerritoryChangeDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, TerritoryChangeDetails territoryChangeDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(territoryChangeDetails);

        // establish the list of properties to be populated
        addColumnList("tch.territory_change_id, tch.original_territory_id");
        addColumnList(" tch.new_territory_id, tch.user_id, tch.change_date, tch.auto_territory_change");

        return doSelect(territoryChangeDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param territoryChangeDetails TerritoryChangeDetails
     */
    private void establishSearchConditions(TerritoryChangeDetails territoryChangeDetails) {
        if (territoryChangeDetails.getTerritoryChangeId() != null) {
            addCondition("tch.territory_change_id= ?");
            addConditionParam(territoryChangeDetails.getTerritoryChangeId());
        }
        
        if (territoryChangeDetails.getNewTerritoryId() != null){
        	addCondition("tch.new_territory_id= ?");
        	addConditionParam(territoryChangeDetails.getNewTerritoryId());
        }
        
        if (territoryChangeDetails.getOriginalTerritoryId() != null){
        	addCondition("tch.original_territory_id= ?");
        	addConditionParam(territoryChangeDetails.getOriginalTerritoryId());
        }
        
        if (territoryChangeDetails.getChangeDate() != null){
        	addCondition("tch.change_date= ?");
        	addConditionParam(territoryChangeDetails.getChangeDate());
        }
        
        if (territoryChangeDetails.getUserId() != null){
        	addCondition("tch.user_id= ?");
        	addConditionParam(territoryChangeDetails.getUserId());
        }
        
        if (territoryChangeDetails.getAutomatedTerritoryChange() != null){
        	addCondition("tch.auto_territory_change= ?");
        	addConditionParam(territoryChangeDetails.getAutomatedTerritoryChange());
        }
    }

    /**
     * Method insertch.
     * @param fillType BigDecimal
     * @param territoryChangeDetails TerritoryChangeDetails
     * @return TerritoryChangeDetails
     * @throws Exception
     */
    public TerritoryChangeDetails insert(BigDecimal fillType, TerritoryChangeDetails territoryChangeDetails) throws Exception {
    	
        territoryChangeDetails.setTerritoryChangeId(getAvailableID("territory_change_id", "territory_change"));
        
        addColumnList("territory_change_id, new_territory_id, original_territory_id, change_date, user_id, auto_territory_change");

        return (TerritoryChangeDetails) doInsert(territoryChangeDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param territoryChangeDetails TerritoryChangeDetails
     * @return TerritoryChangeDetails
     * @throws Exception
     */
    public TerritoryChangeDetails update(BigDecimal fillType, TerritoryChangeDetails territoryChangeDetails) throws Exception {

        addCondition("territory_change_id= ?");
        addConditionParam(territoryChangeDetails.getTerritoryChangeId());

        addColumnList("territory_change_id, new_territory_id, original_territory_id, change_date, user_id, auto_territory_change");

        return (TerritoryChangeDetails) doUpdate(territoryChangeDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param territoryChangeDetails TerritoryChangeDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, TerritoryChangeDetails territoryChangeDetails) throws Exception {
        
    	if(territoryChangeDetails.getTerritoryChangeId() != null){
        	addCondition("territory_change_id= ?");
            addConditionParam(territoryChangeDetails.getTerritoryChangeId());

            doDelete(territoryChangeDetails);
            return true;	
    	}else{
    		return false;
    	}

    }
}
