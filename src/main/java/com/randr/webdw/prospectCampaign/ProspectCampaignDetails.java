package com.randr.webdw.prospectCampaign;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * @author randr
 * @version $Revision: 1.3 $
 */
public class ProspectCampaignDetails extends AbstractDetails {
   
	protected BigDecimal prospectCampaignId;
	protected BigDecimal prospectId;
    protected BigDecimal campaignId;
    protected Timestamp creationDate;
    
    
    //next 3 fields are not db fields but will 
    //show the user how many actions remain on the
    //campaign, for user to determine if campaign 
    //has been completed
    protected int actionsRemaining;
    protected int totalActions;   
    protected int status;
    
    // joined
    protected String campaign;
    protected String description;


    public static int CAMPAIGN_NOT_COMPLETED = 0;
    public static int CAMPAIGN_COMPLETED = 1;
    
    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("prospect_campaign_id", "prospectCampaignId");
        addMapping("campaign_id", "campaignId");
        addMapping("prospect_id", "prospectId");
        addMapping("creation_date", "creationDate");
        addMapping("cam.campaign", "campaign");
        addMapping("cam.description", "description");
    }


	public BigDecimal getCampaignId() {
		return campaignId;
	}


	public void setCampaignId(BigDecimal campaignId) {
		this.campaignId = campaignId;
	}


	public Timestamp getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}


	public BigDecimal getProspectCampaignId() {
		return prospectCampaignId;
	}


	public void setProspectCampaignId(BigDecimal prospectCampaignId) {
		this.prospectCampaignId = prospectCampaignId;
	}


	public String getCampaign() {
		return campaign;
	}


	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}


	public BigDecimal getProspectId() {
		return prospectId;
	}


	public void setProspectId(BigDecimal prospectId) {
		this.prospectId = prospectId;
	}
    

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
  
	public String toString(){
		return "ProspectCampaignDetails = { \n       campaignId = " + campaignId + "\n" +
										"       campaign = " + campaign + "\n" +
										"       prospectId = " + prospectId + "\n   }";
	}


	public int getActionsRemaining() {
		return actionsRemaining;
	}


	public void setActionsRemaining(int actionsRemaining) {
		this.actionsRemaining = actionsRemaining;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getTotalActions() {
		return totalActions;
	}


	public void setTotalActions(int totalActions) {
		this.totalActions = totalActions;
	}

}
