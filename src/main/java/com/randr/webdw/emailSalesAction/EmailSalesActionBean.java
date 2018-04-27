package com.randr.webdw.emailSalesAction;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class EmailSalesActionBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "email_sales_action";
        this.tableAlias = "esac";
    }
  
    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param salesActionDetails SalesActionDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, EmailSalesActionDetails emailSalesActionDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(emailSalesActionDetails);

        // establish the list of properties to be populated
        addColumnList("email_action_id, email_action_description, creation_date, email_draft_to_use, verified_yes_no, " +
        		"use_salesman_from_email, send_email_date, actual_email_date ");
        setOrderBy("actual_email_date, send_email_date, email_action_description");

        return doSelect(emailSalesActionDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param emailSalesActionDetails EmailSalesActionDetails
     */
    private void establishSearchConditions(EmailSalesActionDetails emailSalesActionDetails) {
        if (emailSalesActionDetails.getEmailActionId() != null) {
            addCondition("email_action_id= ?");
            addConditionParam(emailSalesActionDetails.getEmailActionId());
        }
        
        if (emailSalesActionDetails.getEmailActionDescription() != null) {
            addCondition("email_action_description= ?");
            addConditionParam(emailSalesActionDetails.getEmailActionDescription());
        }
        if (emailSalesActionDetails.getEmailDraftToUse() != null) {
            addCondition("esac.email_draft_to_use= ?");
            addConditionParam(emailSalesActionDetails.getEmailDraftToUse());
        }
        if (emailSalesActionDetails.getVerifiedYesNo() != null) {
            addCondition("verified_yes_no= ?");
            addConditionParam(emailSalesActionDetails.getVerifiedYesNo());
        }
        if (emailSalesActionDetails.getUseSalesmanFromEmail() != null) {
            addCondition("esac.use_salesman_from_email= ?");
            addConditionParam(emailSalesActionDetails.getUseSalesmanFromEmail());
        }
        if (emailSalesActionDetails.isOpenEmailSalesAction()) {
            addCondition("actual_email_date is null ");
        }

        if (emailSalesActionDetails.getCheckSendEmailDate() != null) {
    		addCondition("date(send_email_date) <= date(?)");
    		addConditionParam(emailSalesActionDetails.getCheckSendEmailDate());
    	}
       
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param emailSalesActionDetails EmailSalesActionDetails
     * @return EmailSalesActionDetails
     * @throws Exception
     */
    public EmailSalesActionDetails insert(BigDecimal fillType,
                                     EmailSalesActionDetails emailSalesActionDetails) throws Exception {
        emailSalesActionDetails.setEmailActionId(getAvailableID("email_action_id", "email_sales_action"));
        addColumnList("email_action_id, email_action_description, creation_date, email_draft_to_use, verified_yes_no, " +
        		"use_salesman_from_email, send_email_date, actual_email_date ");

        return (EmailSalesActionDetails) doInsert(emailSalesActionDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param actionDetails EmailSalesActionDetails
     * @return EmailSalesActionDetails
     * @throws Exception
     */
    public EmailSalesActionDetails update(BigDecimal fillType,
                                     EmailSalesActionDetails actionDetails) throws Exception {
        addCondition("email_action_id= ?");
        addConditionParam(actionDetails.getEmailActionId());

        addColumnList("email_action_description, creation_date, email_draft_to_use, verified_yes_no, " +
        		"use_salesman_from_email, send_email_date, actual_email_date ");

        return (EmailSalesActionDetails) doUpdate(actionDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param emailSalesActionDetails EmailSalesActionDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, EmailSalesActionDetails emailSalesActionDetails) throws Exception {
    	
    	if(emailSalesActionDetails.getEmailActionId() != null){
            addCondition("email_action_id= ?");
            addConditionParam(emailSalesActionDetails.getEmailActionId());

            doDelete(emailSalesActionDetails);
            return true; 		
    	}else{
    		return false;
    	}

    }
}
