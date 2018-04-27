package com.randr.webdw.campaignSalesAction;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class CampaignSalesActionBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "campaign_sales_action";
        this.tableAlias = "csa";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param campaignSalesActionDetails CampaignSalesActionDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, CampaignSalesActionDetails campaignSalesActionDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(campaignSalesActionDetails);

        // establish the list of properties to be populated
        addColumnList("campaign_sales_action_id,campaign_id, sales_action_id, display_sequence,creation_date, created_by_user, " +
        		"update_date, updated_by_user, send_email_days");
       // addColumnList("sa.action, sa.mandatory_date, sa.email_draft_to_use");
        addColumnList("sa.action, sa.mandatory_date");
        addOtherTables(" left outer join " +
                       AppSettings.getParm("LIB") + "action sa on sales_action_id = sa.action_id");
        
        setOrderBy("display_sequence");

        return doSelect(campaignSalesActionDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param campaignSalesActionDetails CampaignSalesActionDetails
     */
    private void establishSearchConditions(CampaignSalesActionDetails campaignSalesActionDetails) {
        if (campaignSalesActionDetails.getCampaignId() != null) {
            addCondition("campaign_id= ?");
            addConditionParam(campaignSalesActionDetails.getCampaignId());
        }

        if (campaignSalesActionDetails.getCampaignSalesActionId() != null) {
            addCondition("campaign_sales_action_id= ?");
            addConditionParam(campaignSalesActionDetails.getCampaignSalesActionId());
        }
        if (campaignSalesActionDetails.getSalesActionId() != null) {
            addCondition("sales_action_id= ?");
            addConditionParam(campaignSalesActionDetails.getSalesActionId());
        }
        
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param campaignSalesActionDetails CampaignSalesActionDetails
     * @return CampaignSalesActionDetails
     * @throws Exception
     */
    public CampaignSalesActionDetails insert(BigDecimal fillType, CampaignSalesActionDetails campaignSalesActionDetails) throws Exception {
        campaignSalesActionDetails.setCampaignSalesActionId(getAvailableID("campaign_sales_action_id", "campaign_sales_action"));
        addColumnList("campaign_sales_action_id, campaign_id, sales_action_id, display_sequence, creation_date, created_by_user, " +
        		"update_date, updated_by_user, send_email_days");

        return (CampaignSalesActionDetails) doInsert(campaignSalesActionDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param campaignSalesActionDetails CampaignSalesActionDetails
     * @return CampaignSalesActionDetails
     * @throws Exception
     */
    public CampaignSalesActionDetails update(BigDecimal fillType, CampaignSalesActionDetails campaignSalesActionDetails) throws Exception {
        addCondition("campaign_id= ?");
        addConditionParam(campaignSalesActionDetails.getCampaignId());

        addColumnList("campaign_id, sales_action_id, display_sequence, created_by_user, update_date, updated_by_user, send_email_days");

        return (CampaignSalesActionDetails) doUpdate(campaignSalesActionDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param campaignSalesActionDetails CampaignSalesActionDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, CampaignSalesActionDetails campaignSalesActionDetails) throws Exception {
        
    	if(campaignSalesActionDetails.getCampaignId() != null){
	    	addCondition("campaign_id= ?");
	        addConditionParam(campaignSalesActionDetails.getCampaignId());
	
	        doDelete(campaignSalesActionDetails);
	        return true;
    	}else{
    		return false;
    	}
    }
}
