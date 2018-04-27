package com.randr.webdw.userLobs;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class UserLobsBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "user_lobs";
        this.tableAlias = "ulobs";
    }

    public Vector loadElements(BigDecimal fillType, UserLobsDetails userLobsDetails) 
    	throws Exception {
        establishSearchConditions(userLobsDetails, fillType);

        // set the detail fields
        addColumnList("ulobs.user_lob_id");
        addColumnList("ulobs.user_id");
        addColumnList("ulobs.lob_id");       


        return doSelect(userLobsDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param userLobsDetails UserLobsDetails
     */
    private void establishSearchConditions(UserLobsDetails userLobsDetails, BigDecimal fillType) {
        if (userLobsDetails.getUserLobId() != null) {
            addCondition("ulobs.user_lob_id = ?");
            addConditionParam(userLobsDetails.getUserLobId());
        }

        if (userLobsDetails.getUserId() != null) {
            addCondition("ulobs.user_id = ?");
            addConditionParam(userLobsDetails.getUserId());
        }
        // this is the get the list of sales actions that need emails sent
        if (userLobsDetails.getLobId() != null) {
            addCondition("ulobs.lob_id = ?");
            addConditionParam(userLobsDetails.getLobId());
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param userLobsDetails UserLobsDetails
     * @return UserLobsDetails
     * @throws Exception
     */
    public UserLobsDetails insert(BigDecimal fillType, UserLobsDetails userLobsDetails) throws Exception {
        userLobsDetails.setUserLobId(getAvailableID("user_lob_id", "user_lobs"));

        addColumnList("user_lob_id");
        addColumnList("user_id");
        addColumnList("lob_id");       

        return (UserLobsDetails) doInsert(userLobsDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param userLobsDetails UserLobsDetails
     * @return UserLobsDetails
     * @throws Exception
     */
    public UserLobsDetails update(BigDecimal fillType, UserLobsDetails userLobsDetails) throws Exception {
//        if (userLobsDetails.getUserId() != null) {
            addCondition("user_id = ?");
            addConditionParam(userLobsDetails.getUserId());

//            addColumnList("user_id");
            
            addColumnList("lob_id");       

            return (UserLobsDetails) doUpdate(userLobsDetails);
            
//        } else {
//            return new UserLobsDetails();
//        }
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param userLobsDetails UserLobsDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, UserLobsDetails userLobsDetails) throws Exception {
        
    	if (userLobsDetails.getUserId() != null) {
            addCondition("user_id= ?");
            addConditionParam(userLobsDetails.getUserId());
    	}
         
    	if (userLobsDetails.getLobId() != null) {
            addCondition("lob_id= ?");
            addConditionParam(userLobsDetails.getLobId());
    	}
    	if (userLobsDetails.getUserId() != null || userLobsDetails.getLobId() != null) {
            doDelete(userLobsDetails);
            return true;
        }else{
    		return false;
    	}

    }
}
