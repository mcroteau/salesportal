package com.randr.webdw.userStatuses;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class UserStatusesBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "user_statuses";
        this.tableAlias = "ustat";
    }

    public Vector loadElements(BigDecimal fillType, UserStatusesDetails userStatusesDetails) 
    	throws Exception {
        establishSearchConditions(userStatusesDetails, fillType);

        // set the detail fields
        addColumnList("ustat.user_status_id");
        addColumnList("ustat.user_id");
        addColumnList("ustat.status_id");       


        return doSelect(userStatusesDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param userStatusesDetails UserStatusesDetails
     */
    private void establishSearchConditions(UserStatusesDetails userStatusesDetails, BigDecimal fillType) {
        if (userStatusesDetails.getUserStatusId() != null) {
            addCondition("ustat.user_status_id = ?");
            addConditionParam(userStatusesDetails.getUserStatusId());
        }

        if (userStatusesDetails.getUserId() != null) {
            addCondition("ustat.user_id = ?");
            addConditionParam(userStatusesDetails.getUserId());
        }
        // this is the get the list of sales actions that need emails sent
        if (userStatusesDetails.getStatusId() != null) {
            addCondition("ustat.status_id = ?");
            addConditionParam(userStatusesDetails.getStatusId());
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param userStatusesDetails UserStatusesDetails
     * @return UserStatusesDetails
     * @throws Exception
     */
    public UserStatusesDetails insert(BigDecimal fillType, UserStatusesDetails userStatusesDetails) throws Exception {
        userStatusesDetails.setUserStatusId(getAvailableID("user_status_id", "user_statuses"));

        addColumnList("user_status_id");
        addColumnList("user_id");
        addColumnList("status_id");       

        return (UserStatusesDetails) doInsert(userStatusesDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param userStatusesDetails UserStatusesDetails
     * @return UserStatusesDetails
     * @throws Exception
     */
    public UserStatusesDetails update(BigDecimal fillType, UserStatusesDetails userStatusesDetails) throws Exception {
//        if (userStatusesDetails.getUserId() != null) {
            addCondition("user_id = ?");
            addConditionParam(userStatusesDetails.getUserId());

//            addColumnList("user_id");
            
            addColumnList("status_id");       

            return (UserStatusesDetails) doUpdate(userStatusesDetails);
            
//        } else {
//            return new UserStatusesDetails();
//        }
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param userStatusesDetails UserStatusesDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, UserStatusesDetails userStatusesDetails) throws Exception {
        
    	if (userStatusesDetails.getUserId() != null) {
            addCondition("user_id= ?");
            addConditionParam(userStatusesDetails.getUserId());
    	}
    	if (userStatusesDetails.getStatusId() != null) {
            addCondition("status_id= ?");
            addConditionParam(userStatusesDetails.getStatusId());
    	}
    	if (userStatusesDetails.getUserId() != null || userStatusesDetails.getStatusId() != null) {  
            doDelete(userStatusesDetails);
            return true;
            
        }else{
    		return false;
    	}

    }
}
