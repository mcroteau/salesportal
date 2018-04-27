package com.randr.webdw.emailSalesAction.emailSalesActionLobs;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class EmailSalesActionLobsDetails extends AbstractDetails {

	protected BigDecimal emailLobId;
    protected BigDecimal emailActionId;
    protected BigDecimal emailCampaignId;
    protected BigDecimal lobId;
    
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("email_lob_id", "emailLobId");
        addMapping("email_action_id", "emailActionId");
        addMapping("email_campaign_id", "emailCampaignId");
        addMapping("lob_id", "lobId");       
    }
    
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(super.toString());
    	
    	sb.append("[ ");
    	sb.append("emailLobId=" + emailLobId + ", ");
    	sb.append("emailActionId=" + emailActionId + ", ");
    	sb.append("emailCampaignId=" + emailCampaignId + ", ");
    	sb.append("lobId=" + lobId + ", ");
    	sb.append(" ]");  	
    	
    	return sb.toString();
    }

	public BigDecimal getLobId() {
		return lobId;
	}

	public void setLobId(BigDecimal lobId) {
		this.lobId = lobId;
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
	 * @return the emailLobId
	 */
	public BigDecimal getEmailLobId() {
		return emailLobId;
	}

	/**
	 * @param emailLobId the emailLobId to set
	 */
	public void setEmailLobId(BigDecimal emailLobId) {
		this.emailLobId = emailLobId;
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
