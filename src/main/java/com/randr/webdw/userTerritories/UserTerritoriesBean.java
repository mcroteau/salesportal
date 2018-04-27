package com.randr.webdw.userTerritories;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class UserTerritoriesBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "user_territories";
        this.tableAlias = "uterr";
    }

    public Vector loadElements(BigDecimal fillType, UserTerritoriesDetails userTerritoriesDetails) 
    	throws Exception {
        establishSearchConditions(userTerritoriesDetails, fillType);

        // set the detail fields
        addColumnList("uterr.user_territory_id");
        addColumnList("uterr.user_id");
        addColumnList("uterr.territory_id");       


        return doSelect(userTerritoriesDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param userTerritoriesDetails UserTerritoriesDetails
     */
    private void establishSearchConditions(UserTerritoriesDetails userTerritoriesDetails, BigDecimal fillType) {
        if (userTerritoriesDetails.getUserId() != null) {
            addCondition("uterr.user_id = ?");
            addConditionParam(userTerritoriesDetails.getUserId());
        }

        // this is the get the list of sales actions that need emails sent
        if (userTerritoriesDetails.getTerritoryId() != null) {
            addCondition("uterr.territory_id = ?");
            addConditionParam(userTerritoriesDetails.getTerritoryId());
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param userTerritoriesDetails UserTerritoriesDetails
     * @return UserTerritoriesDetails
     * @throws Exception
     */
    public UserTerritoriesDetails insert(BigDecimal fillType, UserTerritoriesDetails userTerritoriesDetails) throws Exception {
        userTerritoriesDetails.setUserTerritoryId(getAvailableID("user_territory_id", "user_territories"));

        addColumnList("user_territory_id");
        addColumnList("user_id");
        addColumnList("territory_id");       

        return (UserTerritoriesDetails) doInsert(userTerritoriesDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param userTerritoriesDetails UserTerritoriesDetails
     * @return UserTerritoriesDetails
     * @throws Exception
     */
    public UserTerritoriesDetails update(BigDecimal fillType, UserTerritoriesDetails userTerritoriesDetails) throws Exception {
        
            addCondition("user_id = ?");
            addConditionParam(userTerritoriesDetails.getUserId());

            addColumnList("user_id");
            addColumnList("territory_id");       

            return (UserTerritoriesDetails) doUpdate(userTerritoriesDetails);
            
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param userTerritoriesDetails UserTerritoriesDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, UserTerritoriesDetails userTerritoriesDetails) throws Exception {
        
    	if (userTerritoriesDetails.getUserId() != null) {
            addCondition("user_id= ?");
            addConditionParam(userTerritoriesDetails.getUserId());
    	}
    	if (userTerritoriesDetails.getTerritoryId() != null) {
            addCondition("territory_id= ?");
            addConditionParam(userTerritoriesDetails.getTerritoryId());
    	}
    	if (userTerritoriesDetails.getUserId() != null ||userTerritoriesDetails.getTerritoryId() != null) {
            doDelete(userTerritoriesDetails);
            return true;
        }else{
    		return false;
    	}
    }
}
