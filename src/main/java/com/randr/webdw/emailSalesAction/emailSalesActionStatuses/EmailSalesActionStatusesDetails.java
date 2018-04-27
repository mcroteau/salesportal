package com.randr.webdw.emailSalesAction.emailSalesActionStatuses;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class EmailSalesActionStatusesDetails extends AbstractDetails {

	protected BigDecimal emailStatusId;
    protected BigDecimal emailActionId;
    protected BigDecimal emailCampaignId;
    protected BigDecimal statusId;
    
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("email_status_id", "emailStatusId");
        addMapping("email_action_id", "emailActionId");
        addMapping("email_campaign_id", "emailCampaignId");
        addMapping("status_id", "statusId");       
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

	public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(super.toString());
    	
    	sb.append("[ ");
    	sb.append("emailStatusId=" + emailStatusId + ", ");
    	sb.append("emailActionId=" + emailActionId + ", ");
    	sb.append("emailCampaignId=" + emailCampaignId + ", ");
    	sb.append("statusId=" + statusId + ", ");
    	sb.append(" ]");  	
    	
    	return sb.toString();
    }

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
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
	 * @return the emailStatusId
	 */
	public BigDecimal getEmailStatusId() {
		return emailStatusId;
	}

	/**
	 * @param emailStatusId the emailStatusId to set
	 */
	public void setEmailStatusId(BigDecimal emailStatusId) {
		this.emailStatusId = emailStatusId;
	}

}
