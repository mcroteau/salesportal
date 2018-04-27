package com.randr.webdw.emailSalesAction.emailSalesActionStatuses;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class EmailSalesActionStatusesBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "email_sales_action_statuses";
        this.tableAlias = "estat";
    }
   

    public Vector loadElements(BigDecimal fillType, EmailSalesActionStatusesDetails emailSalesActionStatusesDetails) 
    	throws Exception {
        establishSearchConditions(emailSalesActionStatusesDetails, fillType);

        // set the detail fields
        addColumnList("estat.email_status_id");
        addColumnList("estat.email_action_id");
        addColumnList("estat.email_campaign_id");
        addColumnList("estat.status_id");       


        return doSelect(emailSalesActionStatusesDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param userStatusesDetails UserStatusesDetails
     */
    private void establishSearchConditions(EmailSalesActionStatusesDetails emailSalesActionStatusesDetails, BigDecimal fillType) {
        if (emailSalesActionStatusesDetails.getEmailStatusId() != null) {
            addCondition("estat.email_status_id = ?");
            addConditionParam(emailSalesActionStatusesDetails.getEmailStatusId());
        }

        if (emailSalesActionStatusesDetails.getEmailActionId() != null) {
            addCondition("estat.email_action_id = ?");
            addConditionParam(emailSalesActionStatusesDetails.getEmailActionId());
        }
        if (emailSalesActionStatusesDetails.getEmailCampaignId() != null) {
            addCondition("estat.email_campaign_id = ?");
            addConditionParam(emailSalesActionStatusesDetails.getEmailCampaignId());
        }
        // this is the get the list of sales actions that need emails sent
        if (emailSalesActionStatusesDetails.getStatusId() != null) {
            addCondition("estat.status_id = ?");
            addConditionParam(emailSalesActionStatusesDetails.getStatusId());
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param userStatusesDetails UserStatusesDetails
     * @return UserStatusesDetails
     * @throws Exception
     */
    public EmailSalesActionStatusesDetails insert(BigDecimal fillType, EmailSalesActionStatusesDetails emailSalesActionStatusesDetails) throws Exception {
    	emailSalesActionStatusesDetails.setEmailStatusId(getAvailableID("email_status_id", "email_sales_action_statuses"));

        addColumnList("email_status_id");
        addColumnList("email_action_id");
        addColumnList("email_campaign_id");
        addColumnList("status_id");       

        return (EmailSalesActionStatusesDetails) doInsert(emailSalesActionStatusesDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param userStatusesDetails UserStatusesDetails
     * @return UserStatusesDetails
     * @throws Exception
     */
    public EmailSalesActionStatusesDetails update(BigDecimal fillType, EmailSalesActionStatusesDetails emailSalesActionStatusesDetails) throws Exception {
//        if (userStatusesDetails.getUserId() != null) {
            addCondition("email_action_id = ?");
            addConditionParam(emailSalesActionStatusesDetails.getEmailActionId());

//            addColumnList("user_id");
            
            addColumnList("status_id");       

            return (EmailSalesActionStatusesDetails) doUpdate(emailSalesActionStatusesDetails);
            
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
    public boolean delete(BigDecimal fillType, EmailSalesActionStatusesDetails emailSalesActionStatusesDetails) throws Exception {
        
    	if (emailSalesActionStatusesDetails.getEmailStatusId() != null) {
            addCondition("email_status_id= ?");
            addConditionParam(emailSalesActionStatusesDetails.getEmailStatusId());
    	}
    	
    	if (emailSalesActionStatusesDetails.getEmailStatusId() != null) {  
            doDelete(emailSalesActionStatusesDetails);
            return true;
            
        }else{
    		return false;
    	}

    }
}
