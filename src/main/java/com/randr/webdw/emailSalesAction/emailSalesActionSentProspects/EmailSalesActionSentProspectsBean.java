package com.randr.webdw.emailSalesAction.emailSalesActionSentProspects;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class EmailSalesActionSentProspectsBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "email_sales_action_sent_prospects";
        this.tableAlias = "esentp";
    }
   
    public Vector loadElements(BigDecimal fillType, EmailSalesActionSentProspectsDetails emailSalesActionSentProspectsDetails) 
    	throws Exception {
        establishSearchConditions(emailSalesActionSentProspectsDetails, fillType);

        // set the detail fields
        addColumnList("esentp.sent_email_action_id");
        addColumnList("esentp.email_action_id");
        addColumnList("esentp.prospect_id"); 
        addColumnList("esentp.sent_date"); 


        return doSelect(emailSalesActionSentProspectsDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param userSentProspectsDetails UserSentProspectsDetails
     */
    private void establishSearchConditions(EmailSalesActionSentProspectsDetails emailSalesActionSentProspectsDetails, BigDecimal fillType) {
        if (emailSalesActionSentProspectsDetails.getSentEmailActionId() != null) {
            addCondition("esentp.sent_email_action_id = ?");
            addConditionParam(emailSalesActionSentProspectsDetails.getSentEmailActionId());
        }

        if (emailSalesActionSentProspectsDetails.getEmailActionId() != null) {
            addCondition("esentp.email_action_id = ?");
            addConditionParam(emailSalesActionSentProspectsDetails.getEmailActionId());
        }
        // this is the get the list of sales actions that need emails sent
        if (emailSalesActionSentProspectsDetails.getProspectId() != null) {
            addCondition("esentp.prospect_id = ?");
            addConditionParam(emailSalesActionSentProspectsDetails.getProspectId());
        }
        if (emailSalesActionSentProspectsDetails.getSentDate() != null) {
            addCondition("esentp.sent_date = ?");
            addConditionParam(emailSalesActionSentProspectsDetails.getSentDate());
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param userSentProspectsDetails UserSentProspectsDetails
     * @return UserSentProspectsDetails
     * @throws Exception
     */
    public EmailSalesActionSentProspectsDetails insert(BigDecimal fillType, EmailSalesActionSentProspectsDetails emailSalesActionSentProspectsDetails) throws Exception {
    	emailSalesActionSentProspectsDetails.setSentEmailActionId(getAvailableID("sent_email_action_id", "email_sales_action_sent_prospects"));

        addColumnList("sent_email_action_id");
        addColumnList("email_action_id");
        addColumnList("prospect_id");  
        addColumnList("sent_date"); 

        return (EmailSalesActionSentProspectsDetails) doInsert(emailSalesActionSentProspectsDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param userSentProspectsDetails UserSentProspectsDetails
     * @return UserSentProspectsDetails
     * @throws Exception
     */
    public EmailSalesActionSentProspectsDetails update(BigDecimal fillType, EmailSalesActionSentProspectsDetails emailSalesActionSentProspectsDetails) throws Exception {
//        if (userSentProspectsDetails.getUserId() != null) {
            addCondition("sent_email_action_id = ?");
            addConditionParam(emailSalesActionSentProspectsDetails.getSentEmailActionId());

//            addColumnList("user_id");
            
            addColumnList("prospect_id");       

            return (EmailSalesActionSentProspectsDetails) doUpdate(emailSalesActionSentProspectsDetails);
            
//        } else {
//            return new UserSentProspectsDetails();
//        }
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param userSentProspectsDetails UserSentProspectsDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, EmailSalesActionSentProspectsDetails emailSalesActionSentProspectsDetails) throws Exception {
        
    	if (emailSalesActionSentProspectsDetails.getSentEmailActionId() != null) {
            addCondition("sent_email_action_id= ?");
            addConditionParam(emailSalesActionSentProspectsDetails.getSentEmailActionId());
    	}
    	if (emailSalesActionSentProspectsDetails.getProspectId() != null) {
            addCondition("prospect_id= ?");
            addConditionParam(emailSalesActionSentProspectsDetails.getProspectId());
    	}
    	if (emailSalesActionSentProspectsDetails.getSentEmailActionId() != null || emailSalesActionSentProspectsDetails.getProspectId() != null) {  
            doDelete(emailSalesActionSentProspectsDetails);
            return true;
            
        }else{
    		return false;
    	}

    }
}
