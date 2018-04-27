package com.randr.webdw.emailSalesAction.emailSalesActionTerritories;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class EmailSalesActionTerritoriesBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "email_sales_action_territories";
        this.tableAlias = "eter";
    }
   

    public Vector loadElements(BigDecimal fillType, EmailSalesActionTerritoriesDetails emailSalesActionTerritoriesDetails) 
    	throws Exception {
        establishSearchConditions(emailSalesActionTerritoriesDetails, fillType);

        // set the detail fields
        addColumnList("eter.email_territory_id");
        addColumnList("eter.email_action_id");
        addColumnList("eter.email_campaign_id");
        addColumnList("eter.territory_id");       


        return doSelect(emailSalesActionTerritoriesDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param userTerritoriesDetails UserTerritoriesDetails
     */
    private void establishSearchConditions(EmailSalesActionTerritoriesDetails emailSalesActionTerritoriesDetails, BigDecimal fillType) {
        if (emailSalesActionTerritoriesDetails.getEmailTerritoryId() != null) {
            addCondition("eter.email_territory_id = ?");
            addConditionParam(emailSalesActionTerritoriesDetails.getEmailTerritoryId());
        }

        if (emailSalesActionTerritoriesDetails.getEmailActionId() != null) {
            addCondition("eter.email_action_id = ?");
            addConditionParam(emailSalesActionTerritoriesDetails.getEmailActionId());
        }
        if (emailSalesActionTerritoriesDetails.getEmailCampaignId() != null) {
            addCondition("eter.email_campaign_id = ?");
            addConditionParam(emailSalesActionTerritoriesDetails.getEmailCampaignId());
        }
        // this is the get the list of sales actions that need emails sent
        if (emailSalesActionTerritoriesDetails.getTerritoryId() != null) {
            addCondition("eter.territory_id = ?");
            addConditionParam(emailSalesActionTerritoriesDetails.getTerritoryId());
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param userTerritoriesDetails UserTerritoriesDetails
     * @return UserTerritoriesDetails
     * @throws Exception
     */
    public EmailSalesActionTerritoriesDetails insert(BigDecimal fillType, EmailSalesActionTerritoriesDetails emailSalesActionTerritoriesDetails) throws Exception {
    	emailSalesActionTerritoriesDetails.setEmailTerritoryId(getAvailableID("email_territory_id", "email_sales_action_territories"));

        addColumnList("email_territory_id");
        addColumnList("email_action_id");
        addColumnList("email_campaign_id");
        addColumnList("territory_id");       

        return (EmailSalesActionTerritoriesDetails) doInsert(emailSalesActionTerritoriesDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param userTerritoriesDetails UserTerritoriesDetails
     * @return UserTerritoriesDetails
     * @throws Exception
     */
    public EmailSalesActionTerritoriesDetails update(BigDecimal fillType, EmailSalesActionTerritoriesDetails emailSalesActionTerritoriesDetails) throws Exception {
//        if (userTerritoriesDetails.getUserId() != null) {
            addCondition("email_action_id = ?");
            addConditionParam(emailSalesActionTerritoriesDetails.getEmailActionId());

//            addColumnList("user_id");
            
            addColumnList("territory_id");       

            return (EmailSalesActionTerritoriesDetails) doUpdate(emailSalesActionTerritoriesDetails);
            
//        } else {
//            return new UserTerritoriesDetails();
//        }
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param userTerritoriesDetails UserTerritoriesDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, EmailSalesActionTerritoriesDetails emailSalesActionTerritoriesDetails) throws Exception {
        
    	if (emailSalesActionTerritoriesDetails.getEmailTerritoryId() != null) {
            addCondition("email_territory_id= ?");
            addConditionParam(emailSalesActionTerritoriesDetails.getEmailTerritoryId());
    	}
    	
    	if (emailSalesActionTerritoriesDetails.getEmailTerritoryId() != null) {  
            doDelete(emailSalesActionTerritoriesDetails);
            return true;
            
        }else{
    		return false;
    	}

    }
}
