package com.randr.webdw.prospectSalesAction;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class ProspectSalesActionDetails extends AbstractDetails {
   
	protected BigDecimal prospectSalesActionId;
    protected BigDecimal prospectId;
    protected BigDecimal salesActionId;
    protected BigDecimal actionPriority;
    protected Timestamp actionDate;
    protected BigDecimal actionNotificationflag;
    protected BigDecimal actionHour;
    protected BigDecimal actionMinute;
    protected BigDecimal actionAmPm;
    protected String actionNote;
    protected BigDecimal actionStatus; //0 = active, 1 = completed
    protected BigDecimal prospectCampaignId;
    protected BigDecimal prospectCampaignSequence;
       
    protected List prospectIdList;
    
    protected Timestamp changeDate;
    protected Timestamp creationDate;
    protected BigDecimal changedByUserId;
    protected String changedByUserName;
    protected String changedByUserFirstName;
    protected String changedByUserLastName;
    
    //join fields
    protected String action;
    protected BigDecimal mandatoryDate;
    protected BigDecimal ownerUserId;
    protected BigDecimal companyId;
    protected String salesActionName;
    protected String campaignName;
    protected BigDecimal territoryId;
    
    //fields for email round trip/autoresponder
    protected BigDecimal emailDraftToUse;//which email draft # to use (see OP for the number)
    // if a sales action has an email draft # then this is an email sales action
    protected Date checkActionDate; //used for getting prospect sales actions to send emails to
    protected boolean emailProspectSalesAction;//true = emailDraftToUse is not null and >0 , false= not an email sales action
    protected boolean emailNotSent;//used in emailSalesActionSendTimer to flag when prospect has opt out.
    
    //misc fields
    
    protected boolean excludeCampaignSalesActions;

    public static final BigDecimal STATUS_ACTIVE = new BigDecimal(0);
    public static final BigDecimal STATUS_COMPLETE = new BigDecimal(1);
    public static final BigDecimal STATUS_DELETED = new BigDecimal(2);
    
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
    public static final BigDecimal FILL_TYPE_CAMPAIGN = new BigDecimal(2);
    
    public static BigDecimal REQUIRES_MANDATORY_DATE = new BigDecimal(1);
	public static BigDecimal DOES_NOT_REQUIRE_DATE = new BigDecimal(0);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("prospect_sales_action_id", "prospectSalesActionId");
        addMapping("prospect_id", "prospectId");
        addMapping("sales_action_id", "salesActionId");       
        addMapping("action_priority", "actionPriority");
        addMapping("action_date", "actionDate");
        addMapping("action_notification_flag", "actionNotificationflag");
        addMapping("action_hour", "actionHour");
        addMapping("action_minute", "actionMinute");
        addMapping("action_am_pm", "actionAmPm");
        addMapping("action_note", "actionNote");
        addMapping("action_status", "actionStatus");
        
        addMapping("prospect_campaign_id", "prospectCampaignId");
        addMapping("prospect_campaign_sequence", "prospectCampaignSequence");
        
        addMapping("change_date", "changeDate");
        addMapping("creation_date", "creationDate");
        addMapping("changed_by_user_id", "changedByUserId");
        addMapping("email_draft_to_use", "emailDraftToUse");
        
        addMapping("sa.action", "action");
        addMapping("sa.mandatory_date", "mandatoryDate");
        
        addMapping("t.owner_user_id", "ownerUserId");
        addMapping("p.company_id", "companyId");
        addMapping("w.first_name", "changedByUserFirstName");
        addMapping("w.last_name", "changedByUserLastName");
        
        addMapping("cam.campaign", "campaignName");
    }
    
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(super.toString());
    	sb.append("[ ");
    	sb.append("prospectSalesActionId=" + prospectSalesActionId + ", ");
    	sb.append("prospectId=" + prospectId + ", ");
    	sb.append("salesActionId=" + salesActionId + ", ");
    	sb.append("actionPriority=" + actionPriority + ", ");
    	sb.append("actionDate=" + actionDate +", ");
    	sb.append("actionNotificationflag=" + actionNotificationflag);   	
    	sb.append(" ]");  	
    	return sb.toString();
    }

	public BigDecimal getActionAmPm() {
		return actionAmPm;
	}

	public void setActionAmPm(BigDecimal actionAmPm) {
		this.actionAmPm = actionAmPm;
	}

	public Timestamp getActionDate() {
		return actionDate;
	}

	public void setActionDate(Timestamp actionDate) {
		this.actionDate = actionDate;
	}

	public BigDecimal getActionHour() {
		return actionHour;
	}

	public void setActionHour(BigDecimal actionHour) {
		this.actionHour = actionHour;
	}

	public BigDecimal getActionMinute() {
		return actionMinute;
	}

	public void setActionMinute(BigDecimal actionMinute) {
		this.actionMinute = actionMinute;
	}

	public BigDecimal getActionNotificationflag() {
		return actionNotificationflag;
	}

	public void setActionNotificationflag(BigDecimal actionNotificationflag) {
		this.actionNotificationflag = actionNotificationflag;
	}

	public BigDecimal getActionPriority() {
		return actionPriority;
	}

	public void setActionPriority(BigDecimal actionPriority) {
		this.actionPriority = actionPriority;
	}

	public BigDecimal getProspectId() {
		return prospectId;
	}

	public void setProspectId(BigDecimal prospectId) {
		this.prospectId = prospectId;
	}

	public BigDecimal getProspectSalesActionId() {
		return prospectSalesActionId;
	}

	public void setProspectSalesActionId(BigDecimal prospectSalesActionId) {
		this.prospectSalesActionId = prospectSalesActionId;
	}

	public BigDecimal getSalesActionId() {
		return salesActionId;
	}

	public void setSalesActionId(BigDecimal salesActionId) {
		this.salesActionId = salesActionId;
	}

	public String getAction() {
		return action;
	}

	public void setSalesActionName(String action) {
		this.action = action;
	}

	public BigDecimal getMandatoryDate() {
		return mandatoryDate;
	}

	public void setMandatoryDate(BigDecimal mandatoryDate) {
		this.mandatoryDate = mandatoryDate;
	}

	public BigDecimal getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(BigDecimal ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public String getActionNote() {
		return actionNote;
	}

	public void setActionNote(String actionNote) {
		this.actionNote = actionNote;
	}

	public BigDecimal getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(BigDecimal actionStatus) {
		this.actionStatus = actionStatus;
	}

	public BigDecimal getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(BigDecimal territoryId) {
		this.territoryId = territoryId;
	}
	
	public BigDecimal getProspectCampaignId() {
		return prospectCampaignId;
	}

	public void setProspectCampaignId(BigDecimal prospectCampaignId) {
		this.prospectCampaignId = prospectCampaignId;
	}

	public BigDecimal getProspectCampaignSequence() {
		return prospectCampaignSequence;
	}

	public void setProspectCampaignSequence(BigDecimal prospectCampaignSequence) {
		this.prospectCampaignSequence = prospectCampaignSequence;
	}

	public boolean isExcludeCampaignSalesActions() {
		return excludeCampaignSalesActions;
	}

	public void setExcludeCampaignSalesActions(boolean excludeCampaignSalesActions) {
		this.excludeCampaignSalesActions = excludeCampaignSalesActions;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public List getProspectIdList() {
		return prospectIdList;
	}

	public void setProspectIdList(List prospectIdList) {
		this.prospectIdList = prospectIdList;
	}

	public Timestamp getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Timestamp changeDate) {
		this.changeDate = changeDate;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public BigDecimal getChangedByUserId() {
		return changedByUserId;
	}

	public void setChangedByUserId(BigDecimal changedByUserId) {
		this.changedByUserId = changedByUserId;
	}

	/**
	 * @return the changedByUserName
	 */
	public String getChangedByUserName() {
		return getChangedByUserFirstName() + " " + getChangedByUserLastName();
	}

	/**
	 * @param changedByUserName the changedByUserName to set
	 */
	public void setChangedByUserName(String changedByUserName) {
		this.changedByUserName = changedByUserName;
	}

	/**
	 * @return the changedByUserFirstName
	 */
	public String getChangedByUserFirstName() {
		return changedByUserFirstName;
	}

	/**
	 * @param changedByUserFirstName the changedByUserFirstName to set
	 */
	public void setChangedByUserFirstName(String changedByUserFirstName) {
		this.changedByUserFirstName = changedByUserFirstName;
	}

	/**
	 * @return the changedByUserLastName
	 */
	public String getChangedByUserLastName() {
		return changedByUserLastName;
	}

	/**
	 * @param changedByUserLastName the changedByUserLastName to set
	 */
	public void setChangedByUserLastName(String changedByUserLastName) {
		this.changedByUserLastName = changedByUserLastName;
	}
	
	/**
	 * @return the emailDraftToUse
	 */
	public BigDecimal getEmailDraftToUse() {
		return emailDraftToUse;
	}

	/**
	 * @param emailDraftToUse the emailDraftToUse to set
	 */
	public void setEmailDraftToUse(BigDecimal emailDraftToUse) {
		this.emailDraftToUse = emailDraftToUse;
	}

	/**
	 * @return the checkActionDate
	 */
	public Date getCheckActionDate() {
		return checkActionDate;
	}

	/**
	 * @param checkActionDate the checkActionDate to set
	 */
	public void setCheckActionDate(Date checkActionDate) {
		this.checkActionDate = checkActionDate;
	}

	/**
	 * @return the emailProspectSalesAction
	 */
	public boolean isEmailProspectSalesAction() {
		return emailProspectSalesAction;
	}

	/**
	 * @param emailProspectSalesAction the emailProspectSalesAction to set
	 */
	public void setEmailProspectSalesAction(boolean emailProspectSalesAction) {
		this.emailProspectSalesAction = emailProspectSalesAction;
	}

	/**
	 * @return the emailNotSent
	 */
	public boolean isEmailNotSent() {
		return emailNotSent;
	}

	/**
	 * @param emailNotSent the emailNotSent to set
	 */
	public void setEmailNotSent(boolean emailNotSent) {
		this.emailNotSent = emailNotSent;
	}

}
