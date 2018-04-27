package com.randr.webdw.emailSalesAction.emailCampaign;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class EmailCampaignDetails extends AbstractDetails {
	
	public static BigDecimal IGNORE_VERIFIED_CODE = new BigDecimal(0);
	public static BigDecimal USE_VERIFED_YES_ONLY = new BigDecimal(1);

	public static BigDecimal DO_NOT_USE_SALESMAN_EMAIL = new BigDecimal(0);
	public static BigDecimal USE_SALESMAN_EMAIL = new BigDecimal(1);
	
	public static BigDecimal DO_NOT_USE_CAMPAIGN_START_DATE = new BigDecimal(0);
	public static BigDecimal YES_USE_CAMPAIGN_START_DATE = new BigDecimal(1);
	
    protected BigDecimal emailCampaignId;// id
    protected BigDecimal campaignId; //the id of the selected campagin
    protected String emailCampaignDescription;// description
    protected BigDecimal verifiedYesNo;//yes = only select verified = yes
    protected BigDecimal useSalesmanFromEmail;//yes = use the salesmans email as the from email
    protected Timestamp creationDate; //creation date of the email campaign
    protected Timestamp startEmailDate; //start date of the campaign
    protected Timestamp endEmailDate; // end of the campaign date
    protected Date checkStartEmailDate; //used for getting email sales actions to send
    protected Date checkEndEmailDate;// used for checking the end of the campaign
    protected BigDecimal useCampaignStartDate; //use campaign start date, to select prospects (prospect create date>= campaign start date
    
    protected String campaignName;
    protected boolean openEmailCampaign;// not used

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("email_campaign_id", "emailCampaignId");
        addMapping("campaign_id", "campaignId");
        addMapping("email_campaign_description", "emailCampaignDescription");
        addMapping("verified_yes_no", "verifiedYesNo");
        addMapping("use_salesman_from_email", "useSalesmanFromEmail");
        addMapping("creation_date", "creationDate");
        addMapping("start_email_date", "startEmailDate");
        addMapping("end_email_date", "endEmailDate");
        addMapping("use_campaign_start_date", "useCampaignStartDate");
        addMapping("cam.campaign","campaignName");
    }

	/**
	 * @return the checkEndEmailDate
	 */
	public Date getCheckEndEmailDate() {
		return checkEndEmailDate;
	}

	/**
	 * @param checkEndEmailDate the checkEndEmailDate to set
	 */
	public void setCheckEndEmailDate(Date checkEndEmailDate) {
		this.checkEndEmailDate = checkEndEmailDate;
	}

	/**
	 * @return the checkStartEmailDate
	 */
	public Date getCheckStartEmailDate() {
		return checkStartEmailDate;
	}

	/**
	 * @param checkStartEmailDate the checkStartEmailDate to set
	 */
	public void setCheckStartEmailDate(Date checkStartEmailDate) {
		this.checkStartEmailDate = checkStartEmailDate;
	}

	/**
	 * @return the creationDate
	 */
	public Timestamp getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the emailCampaignDescription
	 */
	public String getEmailCampaignDescription() {
		return emailCampaignDescription;
	}

	/**
	 * @param emailCampaignDescription the emailCampaignDescription to set
	 */
	public void setEmailCampaignDescription(String emailCampaignDescription) {
		this.emailCampaignDescription = emailCampaignDescription;
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

	/**
	 * @return the endEmailDate
	 */
	public Timestamp getEndEmailDate() {
		return endEmailDate;
	}

	/**
	 * @param endEmailDate the endEmailDate to set
	 */
	public void setEndEmailDate(Timestamp endEmailDate) {
		this.endEmailDate = endEmailDate;
	}

	/**
	 * @return the openEmailCampaign
	 */
	public boolean isOpenEmailCampaign() {
		return openEmailCampaign;
	}

	/**
	 * @param openEmailCampaign the openEmailCampaign to set
	 */
	public void setOpenEmailCampaign(boolean openEmailCampaign) {
		this.openEmailCampaign = openEmailCampaign;
	}

	/**
	 * @return the startEmailDate
	 */
	public Timestamp getStartEmailDate() {
		return startEmailDate;
	}

	/**
	 * @param startEmailDate the startEmailDate to set
	 */
	public void setStartEmailDate(Timestamp startEmailDate) {
		this.startEmailDate = startEmailDate;
	}

	/**
	 * @return the useSalesmanFromEmail
	 */
	public BigDecimal getUseSalesmanFromEmail() {
		return useSalesmanFromEmail;
	}

	/**
	 * @param useSalesmanFromEmail the useSalesmanFromEmail to set
	 */
	public void setUseSalesmanFromEmail(BigDecimal useSalesmanFromEmail) {
		this.useSalesmanFromEmail = useSalesmanFromEmail;
	}

	/**
	 * @return the verifiedYesNo
	 */
	public BigDecimal getVerifiedYesNo() {
		return verifiedYesNo;
	}

	/**
	 * @param verifiedYesNo the verifiedYesNo to set
	 */
	public void setVerifiedYesNo(BigDecimal verifiedYesNo) {
		this.verifiedYesNo = verifiedYesNo;
	}

	/**
	 * @return the campaignId
	 */
	public BigDecimal getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId the campaignId to set
	 */
	public void setCampaignId(BigDecimal campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * @return the campaignName
	 */
	public String getCampaignName() {
		return campaignName;
	}

	/**
	 * @param campaignName the campaignName to set
	 */
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	/**
	 * @return the useCampaignStartDate
	 */
	public BigDecimal getUseCampaignStartDate() {
		return useCampaignStartDate;
	}

	/**
	 * @param useCampaignStartDate the useCampaignStartDate to set
	 */
	public void setUseCampaignStartDate(BigDecimal useCampaignStartDate) {
		this.useCampaignStartDate = useCampaignStartDate;
	}


}
