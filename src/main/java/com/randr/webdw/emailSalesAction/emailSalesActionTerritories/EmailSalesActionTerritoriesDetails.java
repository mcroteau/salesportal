package com.randr.webdw.emailSalesAction.emailSalesActionTerritories;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class EmailSalesActionTerritoriesDetails extends AbstractDetails {

	protected BigDecimal emailTerritoryId;
    protected BigDecimal emailActionId;
    protected BigDecimal emailCampaignId;
    protected BigDecimal territoryId;
    
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("email_territory_id", "emailTerritoryId");
        addMapping("email_action_id", "emailActionId");
        addMapping("email_campaign_id", "emailCampaignId");
        addMapping("territory_id", "territoryId");       
    }
    
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(super.toString());
    	
    	sb.append("[ ");
    	sb.append("emailTerritoryId=" + emailTerritoryId + ", ");
    	sb.append("emailActionId=" + emailActionId + ", ");
    	sb.append("emailCampaignId=" + emailCampaignId + ", ");
    	sb.append("territoryId=" + territoryId + ", ");
    	sb.append(" ]");  	
    	
    	return sb.toString();
    }

	public BigDecimal getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(BigDecimal territoryId) {
		this.territoryId = territoryId;
	}

	/**
	 * @return the emailActionId
	 */
	public BigDecimal getEmailActionId() {
		return emailActionId;
	}

	/**
	 * @param emailActionId the emailActionId to set
	 */
	public void setEmailActionId(BigDecimal emailActionId) {
		this.emailActionId = emailActionId;
	}

	/**
	 * @return the emailTerritoryId
	 */
	public BigDecimal getEmailTerritoryId() {
		return emailTerritoryId;
	}

	/**
	 * @param emailTerritoryId the emailTerritoryId to set
	 */
	public void setEmailTerritoryId(BigDecimal emailTerritoryId) {
		this.emailTerritoryId = emailTerritoryId;
	}

	/**
	 * @return the emailCampaignId
	 */
	public BigDecimal getEmailCampaignId() {
		return emailCampaignId;
	}

	/**
	 * @param emailCampaignId the emailCampaignId to set
	 */
	public void setEmailCampaignId(BigDecimal emailCampaignId) {
		this.emailCampaignId = emailCampaignId;
	}
}
