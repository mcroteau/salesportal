package com.randr.webdw.emailSalesAction.emailSalesActionLobs;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class EmailSalesActionLobsBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "email_sales_action_lobs";
        this.tableAlias = "elob";
    }
   

    public Vector loadElements(BigDecimal fillType, EmailSalesActionLobsDetails emailSalesActionLobsDetails) 
    	throws Exception {
        establishSearchConditions(emailSalesActionLobsDetails, fillType);

        // set the detail fields
        addColumnList("elob.email_lob_id");
        addColumnList("elob.email_action_id");
        addColumnList("elob.email_campaign_id");
        addColumnList("elob.lob_id");       


        return doSelect(emailSalesActionLobsDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param userLobsDetails UserLobsDetails
     */
    private void establishSearchConditions(EmailSalesActionLobsDetails emailSalesActionLobsDetails, BigDecimal fillType) {
        if (emailSalesActionLobsDetails.getEmailLobId() != null) {
            addCondition("elob.email_lob_id = ?");
            addConditionParam(emailSalesActionLobsDetails.getEmailLobId());
        }

        if (emailSalesActionLobsDetails.getEmailActionId() != null) {
            addCondition("elob.email_action_id = ?");
            addConditionParam(emailSalesActionLobsDetails.getEmailActionId());
        }
        if (emailSalesActionLobsDetails.getEmailCampaignId() != null) {
            addCondition("elob.email_campaign_id = ?");
            addConditionParam(emailSalesActionLobsDetails.getEmailCampaignId());
        }
        // this is the get the list of sales actions that need emails sent
        if (emailSalesActionLobsDetails.getLobId() != null) {
            addCondition("elob.lob_id = ?");
            addConditionParam(emailSalesActionLobsDetails.getLobId());
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param userLobsDetails UserLobsDetails
     * @return UserLobsDetails
     * @throws Exception
     */
    public EmailSalesActionLobsDetails insert(BigDecimal fillType, EmailSalesActionLobsDetails emailSalesActionLobsDetails) throws Exception {
    	emailSalesActionLobsDetails.setEmailLobId(getAvailableID("email_lob_id", "email_sales_action_lobs"));

        addColumnList("email_lob_id");
        addColumnList("email_action_id");
        addColumnList("email_campaign_id");
        addColumnList("lob_id");       

        return (EmailSalesActionLobsDetails) doInsert(emailSalesActionLobsDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param userLobsDetails UserLobsDetails
     * @return UserLobsDetails
     * @throws Exception
     */
    public EmailSalesActionLobsDetails update(BigDecimal fillType, EmailSalesActionLobsDetails emailSalesActionLobsDetails) throws Exception {
//        if (userLobsDetails.getUserId() != null) {
            addCondition("email_action_id = ?");
            addConditionParam(emailSalesActionLobsDetails.getEmailActionId());

//            addColumnList("user_id");
            
            addColumnList("lob_id");       

            return (EmailSalesActionLobsDetails) doUpdate(emailSalesActionLobsDetails);
            
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
    public boolean delete(BigDecimal fillType, EmailSalesActionLobsDetails emailSalesActionLobsDetails) throws Exception {
        
    	if (emailSalesActionLobsDetails.getEmailLobId() != null) {
            addCondition("email_lob_id= ?");
            addConditionParam(emailSalesActionLobsDetails.getEmailLobId());
    	}
    	
    	if (emailSalesActionLobsDetails.getEmailActionId() != null) {  
            doDelete(emailSalesActionLobsDetails);
            return true;
            
        }else{
    		return false;
    	}

    }
}
