package com.randr.webdw.campaignSalesAction;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class CampaignSalesActionDetails extends AbstractDetails {
	protected BigDecimal campaignSalesActionId;
	protected BigDecimal campaignId;
    protected BigDecimal salesActionId;
    protected BigDecimal displaySequence;
    private Timestamp creationDate;
    private String creationUser;
    private Timestamp updateDate;
    private String updateUser;
    protected BigDecimal sendEmailDays;//number of days since the last sales action to send email
    
    //join
    protected String salesActionDescription;
    protected BigDecimal salesActionMandatoryDate;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
    	addMapping("campaign_sales_action_id", "campaignSalesActionId");
        addMapping("campaign_id", "campaignId");
        addMapping("sales_action_id", "salesActionId");
        addMapping("display_sequence", "displaySequence");
        addMapping("creation_date", "creationDate");
        addMapping("created_by_user", "creationUser");
        addMapping("update_date", "updateDate");
        addMapping("updated_by_user", "updateUser");
        addMapping("send_email_days", "sendEmailDays");
        
        //join 
        addMapping("sa.action", "salesActionDescription");
        addMapping("sa.mandatory_date", "salesActionMandatoryDate");
        addMapping("sa.email_draft_to_use", "emailDraftToUse");
    }

    /**
     * Method getCampaignId.
     * @return BigDecimal
     */
    public BigDecimal getCampaignId() {
        return campaignId;
    }

    /**
     * Method setCampaignId.
     * @param campaignId BigDecimal
     */
    public void setCampaignId(BigDecimal campaignId) {
        this.campaignId = campaignId;
    }

    public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public BigDecimal getCampaignSalesActionId() {
		return campaignSalesActionId;
	}

	public void setCampaignSalesActionId(BigDecimal campaignSalesActionId) {
		this.campaignSalesActionId = campaignSalesActionId;
	}

	public BigDecimal getDisplaySequence() {
		return displaySequence;
	}

	public void setDisplaySequence(BigDecimal displaySequence) {
		this.displaySequence = displaySequence;
	}

	public BigDecimal getSalesActionId() {
		return salesActionId;
	}

	public void setSalesActionId(BigDecimal salesActionId) {
		this.salesActionId = salesActionId;
	}

	public String getSalesActionDescription() {
		return salesActionDescription;
	}

	public void setSalesActionDescription(String salesActionDescription) {
		this.salesActionDescription = salesActionDescription;
	}

	public BigDecimal getSalesActionMandatoryDate() {
		return salesActionMandatoryDate;
	}

	public void setSalesActionMandatoryDate(BigDecimal salesActionMandatoryDate) {
		this.salesActionMandatoryDate = salesActionMandatoryDate;
	}

	/**
	 * @return the sendEmailDays
	 */
	public BigDecimal getSendEmailDays() {
		return sendEmailDays;
	}

	/**
	 * @param sendEmailDays the sendEmailDays to set
	 */
	public void setSendEmailDays(BigDecimal sendEmailDays) {
		this.sendEmailDays = sendEmailDays;
	}
}
