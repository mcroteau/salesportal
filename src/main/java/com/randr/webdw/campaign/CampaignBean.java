package com.randr.webdw.campaign;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class CampaignBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "campaign";
        this.tableAlias = "cam";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param campaignDetails CampaignDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, CampaignDetails campaignDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(campaignDetails);

        // establish the list of properties to be populated
        addColumnList("campaign_id, campaign, creation_date, created_by_user, update_date, updated_by_user, description, display_for_salesmen");
        setOrderBy("campaign");

        return doSelect(campaignDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param campaignDetails CampaignDetails
     */
    private void establishSearchConditions(CampaignDetails campaignDetails) {
        if (campaignDetails.getCampaignId() != null) {
            addCondition("campaign_id= ?");
            addConditionParam(campaignDetails.getCampaignId());
        }

        if (campaignDetails.getDisplayForSalesmen() != null) {
            addCondition("display_for_salesmen= ?");
            addConditionParam(campaignDetails.getDisplayForSalesmen());
        }
        
        if (campaignDetails.getCampaign() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("campaign = ?");
                addConditionParam(campaignDetails.getCampaign());
            } else {
                addCondition("upper(campaign) = upper('" + Utilities.replaceString(campaignDetails.getCampaign(), "'", "''") + "')");
            }
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param campaignDetails CampaignDetails
     * @return CampaignDetails
     * @throws Exception
     */
    public CampaignDetails insert(BigDecimal fillType, CampaignDetails campaignDetails) throws Exception {
        campaignDetails.setCampaignId(getAvailableID("campaign_id", "campaign"));
        addColumnList("campaign_id, campaign, creation_date, created_by_user, update_date, updated_by_user, description, display_for_salesmen");

        return (CampaignDetails) doInsert(campaignDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param campaignDetails CampaignDetails
     * @return CampaignDetails
     * @throws Exception
     */
    public CampaignDetails update(BigDecimal fillType, CampaignDetails campaignDetails) throws Exception {
        addCondition("campaign_id= ?");
        addConditionParam(campaignDetails.getCampaignId());

        addColumnList("campaign, creation_date, created_by_user, update_date, updated_by_user, description, display_for_salesmen");

        return (CampaignDetails) doUpdate(campaignDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param campaignDetails CampaignDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, CampaignDetails campaignDetails) throws Exception {
    	
    	if(campaignDetails.getCampaignId()!= null){
    		
            addCondition("campaign_id= ?");
            addConditionParam(campaignDetails.getCampaignId());
            
    	    doDelete(campaignDetails);
    	    return true;
    	    
    	}else{
    		return false;
    	}   
    }
}
