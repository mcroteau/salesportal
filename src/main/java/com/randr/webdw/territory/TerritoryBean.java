package com.randr.webdw.territory;

import java.math.BigDecimal;
import java.util.Vector;
import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class TerritoryBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "territory";
        this.tableAlias = "t";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param territoryDetails TerritoryDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, TerritoryDetails territoryDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(territoryDetails);

        // establish the list of properties to be populated
        addColumnList("t.territory_id, t.territory, t.owner_user_id, t.sales_manager_user_id, t.service_manager_user_id");
       	addColumnList("t.return_territory_id, t.return_time, t.random_sequence, t.max_prospect_display, t.include_in_conversion_report");
       	addColumnList("t.include_in_round_robin, t.round_robin_selected");
        
        addColumnList("u1.user_name");
        addOtherTables(" left outer join " +
                       AppSettings.getParm("LIB") + "webuser u1 on t.owner_user_id = u1.user_id");

        addColumnList("u2.user_name");
        addOtherTables(" left outer join " +
                       AppSettings.getParm("LIB") + "webuser u2 on t.sales_manager_user_id = u2.user_id");

        addColumnList("u3.user_name");
        addOtherTables(" left outer join " +
                       AppSettings.getParm("LIB") + "webuser u3 on t.service_manager_user_id = u3.user_id");

        addColumnList("upper(t.territory)");
        setOrderBy("upper(t.territory)");

        return doSelect(territoryDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param territoryDetails TerritoryDetails
     */
    private void establishSearchConditions(TerritoryDetails territoryDetails) {
    	
    	if (territoryDetails.getTerritoryIdList() != null && territoryDetails.getTerritoryIdList().size() > 0) {
            if (territoryDetails.getTerritoryIdList().size() == 1) {
                addCondition("t.territory_id = ?");
                addConditionParam((BigDecimal) territoryDetails.getTerritoryIdList().get(0));
            } else {
                String condition = "t.territory_id IN (";
                for (int i = 0; i < territoryDetails.getTerritoryIdList().size(); i++) {
                    if (i > 0) {
                        condition += ",";
                    }
                    condition += ((BigDecimal) territoryDetails.getTerritoryIdList().get(i)).toString();
                }
                condition += ")";
                addCondition(condition);
            }
        }
    	
        if (territoryDetails.getTerritoryId() != null) {
            addCondition("t.territory_id= ?");
            addConditionParam(territoryDetails.getTerritoryId());
        }
        if (territoryDetails.getOwnerUserId() != null){
        	addCondition("t.owner_user_id= ?");
        	addConditionParam(territoryDetails.getOwnerUserId());
        }
        
        if (territoryDetails.getSalesManagerUserId() != null){
        	addCondition("t.sales_manager_user_id= ?");
        	addConditionParam(territoryDetails.getSalesManagerUserId());
        }
        
        if (territoryDetails.getServiceManagerUserId() != null){
        	addCondition("t.service_manager_user_id= ?");
        	addConditionParam(territoryDetails.getServiceManagerUserId());
        }
        
        if (territoryDetails.getTerritory() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("t.territory = ?");
                addConditionParam(territoryDetails.getTerritory());
            } else {
                addCondition("upper(t.territory) = upper('" + Utilities.replaceString(territoryDetails.getTerritory(), "'", "''") + "')");
            }
        }
        
        if (territoryDetails.getReturnTerritoryId() != null){
        	addCondition("t.return_territory_id= ?");
        	addConditionParam(territoryDetails.getReturnTerritoryId());
        }
        
        if (territoryDetails.getReturnTime() != null){
        	addCondition("t.return_time= ?");
        	addConditionParam(territoryDetails.getReturnTime());
        }
        
        if (territoryDetails.getMaxProspectDisplay() != null){
        	addCondition("t.max_prospect_display= ?");
        	addConditionParam(territoryDetails.getMaxProspectDisplay());
        }
        
        if (territoryDetails.getRandomSequence() != null){
        	addCondition("t.random_sequence= ?");
        	addConditionParam(territoryDetails.getRandomSequence());
        }
        
        if (territoryDetails.getIncludeInConversionReport() != null){
        	addCondition("t.include_in_conversion_report= ?");
        	addConditionParam(territoryDetails.getIncludeInConversionReport());
        }
        
//        System.out.println("bean - getInclude = " + territoryDetails.getIncludeInRoundRobin());
        if (territoryDetails.getIncludeInRoundRobin() != null){
        	addCondition("t.include_in_round_robin= ?");
        	addConditionParam(territoryDetails.getIncludeInRoundRobin());
        }
        
        if (territoryDetails.getRoundRobinTerritorySelected() != null){
        	addCondition("t.round_robin_selected= ?");
        	addConditionParam(territoryDetails.getRoundRobinTerritorySelected());
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param territoryDetails TerritoryDetails
     * @return TerritoryDetails
     * @throws Exception
     */
    public TerritoryDetails insert(BigDecimal fillType, TerritoryDetails territoryDetails) throws Exception {
        territoryDetails.setTerritoryId(getAvailableID("territory_id", "territory"));
        
        addColumnList("territory_id, territory, owner_user_id, sales_manager_user_id, service_manager_user_id");
        addColumnList("return_territory_id, return_time, max_prospect_display, random_sequence, include_in_conversion_report");
      	addColumnList("include_in_round_robin");
      	
        return (TerritoryDetails) doInsert(territoryDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param territoryDetails TerritoryDetails
     * @return TerritoryDetails
     * @throws Exception
     */
    public TerritoryDetails update(BigDecimal fillType, TerritoryDetails territoryDetails) throws Exception {

        addCondition("territory_id= ?");
        addConditionParam(territoryDetails.getTerritoryId());

        addColumnList("territory, owner_user_id, sales_manager_user_id, service_manager_user_id");
        addColumnList("return_territory_id, return_time, max_prospect_display, random_sequence, include_in_conversion_report");
      	addColumnList("include_in_round_robin, round_robin_selected");
      	
        return (TerritoryDetails) doUpdate(territoryDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param territoryDetails TerritoryDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, TerritoryDetails territoryDetails) throws Exception {
        
    	if(territoryDetails.getTerritoryId() != null){
        	addCondition("territory_id= ?");
            addConditionParam(territoryDetails.getTerritoryId());

            doDelete(territoryDetails);
            return true;   		
    	}else{
    		return false;
    	}

    }
}
