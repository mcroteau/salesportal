package com.randr.webdw.emailSalesAction.emailCampaign;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class EmailCampaignBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "email_campaign";
        this.tableAlias = "ecam";
    }
  
    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param salesActionDetails SalesActionDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, EmailCampaignDetails emailCampaignDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(emailCampaignDetails);

        // establish the list of properties to be populated
        addColumnList("email_campaign_id, ecam.campaign_id, email_campaign_description, ecam.creation_date, verified_yes_no, " +
        		"use_salesman_from_email, start_email_date, end_email_date, use_campaign_start_date ");
        addColumnList("cam.campaign");
        addOtherTables(" left outer join campaign cam on ecam.campaign_id = cam.campaign_id");
        setOrderBy("end_email_date, start_email_date, email_campaign_description");

        return doSelect(emailCampaignDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param emailCampaignDetails EmailCampaignDetails
     */
    private void establishSearchConditions(EmailCampaignDetails emailCampaignDetails) {
        if (emailCampaignDetails.getEmailCampaignId() != null) {
            addCondition("email_campaign_id= ?");
            addConditionParam(emailCampaignDetails.getEmailCampaignId());
        }
        
        if (emailCampaignDetails.getCampaignId() != null) {
            addCondition("ecam.campaign_id= ?");
            addConditionParam(emailCampaignDetails.getCampaignId());
        }
        
        if (emailCampaignDetails.getEmailCampaignDescription() != null) {
            addCondition("email_campaign_description= ?");
            addConditionParam(emailCampaignDetails.getEmailCampaignDescription());
        }
       
        if (emailCampaignDetails.getVerifiedYesNo() != null) {
            addCondition("verified_yes_no= ?");
            addConditionParam(emailCampaignDetails.getVerifiedYesNo());
        }
        if (emailCampaignDetails.getUseSalesmanFromEmail() != null) {
            addCondition("ecam.use_salesman_from_email= ?");
            addConditionParam(emailCampaignDetails.getUseSalesmanFromEmail());
        }
//        if (emailCampaignDetails.isOpenEmailCampaign()) {//think this is from email sales action
//            addCondition("end_email_date is null ");
//        }

        if (emailCampaignDetails.getCheckStartEmailDate() != null) {
    		addCondition("date(start_email_date) <= date(?)");
    		addConditionParam(emailCampaignDetails.getCheckStartEmailDate());
    	}
        
        if (emailCampaignDetails.getCheckEndEmailDate() != null) {
    		addCondition("date(end_email_date) >= date(?)");
    		addConditionParam(emailCampaignDetails.getCheckEndEmailDate());
    	}
       
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param emailCampaignDetails EmailCampaignDetails
     * @return EmailCampaignDetails
     * @throws Exception
     */
    public EmailCampaignDetails insert(BigDecimal fillType,
                                     EmailCampaignDetails emailCampaignDetails) throws Exception {
        emailCampaignDetails.setEmailCampaignId(getAvailableID("email_campaign_id", "email_campaign"));
        addColumnList("email_campaign_id, campaign_id, email_campaign_description, creation_date, verified_yes_no, " +
        		"use_salesman_from_email, start_email_date, end_email_date , use_campaign_start_date");

        return (EmailCampaignDetails) doInsert(emailCampaignDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param campaignDetails EmailCampaignDetails
     * @return EmailCampaignDetails
     * @throws Exception
     */
    public EmailCampaignDetails update(BigDecimal fillType,
                                     EmailCampaignDetails campaignDetails) throws Exception {
        addCondition("email_campaign_id= ?");
        addConditionParam(campaignDetails.getEmailCampaignId());

        addColumnList("campaign_id, email_campaign_description, creation_date, verified_yes_no, " +
        		"use_salesman_from_email, start_email_date, end_email_date, use_campaign_start_date ");

        return (EmailCampaignDetails) doUpdate(campaignDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param emailCampaignDetails EmailCampaignDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, EmailCampaignDetails emailCampaignDetails) throws Exception {
    	
    	if(emailCampaignDetails.getEmailCampaignId() != null){
            addCondition("email_campaign_id= ?");
            addConditionParam(emailCampaignDetails.getEmailCampaignId());

            doDelete(emailCampaignDetails);
            return true; 		
    	}else{
    		return false;
    	}

    }
}
