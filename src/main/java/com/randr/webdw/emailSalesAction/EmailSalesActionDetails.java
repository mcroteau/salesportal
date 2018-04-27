package com.randr.webdw.emailSalesAction;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class EmailSalesActionDetails extends AbstractDetails {
	
	public static BigDecimal IGNORE_VERIFIED_CODE = new BigDecimal(0);
	public static BigDecimal USE_VERIFED_YES_ONLY = new BigDecimal(1);

	public static BigDecimal DO_NOT_USE_SALESMAN_EMAIL = new BigDecimal(0);
	public static BigDecimal USE_SALESMAN_EMAIL = new BigDecimal(1);
	
    protected BigDecimal emailActionId;// id
    protected String emailActionDescription;// description
    protected BigDecimal emailDraftToUse;//which email draft # to use (see OP for the number)
    protected BigDecimal verifiedYesNo;//yes = only select verified = yes
    protected BigDecimal useSalesmanFromEmail;//yes = use the salesmans email as the from email
    protected Timestamp creationDate; //creation date of the email sales action
    protected Timestamp sendEmailDate; // date the email should be sent
    protected Timestamp actualEmailDate; //actual date the email was sent
    protected Date checkSendEmailDate; //used for getting email sales actions to send
    
    protected boolean openEmailSalesAction;//true = actualEmailDate is null, fales= email is sent and actual email date is not null

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("email_action_id", "emailActionId");
        addMapping("email_action_description", "emailActionDescription");
        addMapping("email_draft_to_use", "emailDraftToUse");
        addMapping("verified_yes_no", "verifiedYesNo");
        addMapping("use_salesman_from_email", "useSalesmanFromEmail");
        addMapping("creation_date", "creationDate");
        addMapping("send_email_date", "sendEmailDate");
        addMapping("actual_email_date", "actualEmailDate");
    }

	/**
	 * @return the actualEmailDate
	 */
	public Timestamp getActualEmailDate() {
		return actualEmailDate;
	}

	/**
	 * @param actualEmailDate the actualEmailDate to set
	 */
	public void setActualEmailDate(Timestamp actualEmailDate) {
		this.actualEmailDate = actualEmailDate;
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
	 * @return the emailActionDescription
	 */
	public String getEmailActionDescription() {
		return emailActionDescription;
	}

	/**
	 * @param emailActionDescription the emailActionDescription to set
	 */
	public void setEmailActionDescription(String emailActionDescription) {
		this.emailActionDescription = emailActionDescription;
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
	 * @return the sendEmailDate
	 */
	public Timestamp getSendEmailDate() {
		return sendEmailDate;
	}

	/**
	 * @param sendEmailDate the sendEmailDate to set
	 */
	public void setSendEmailDate(Timestamp sendEmailDate) {
		this.sendEmailDate = sendEmailDate;
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
	 * @return the openEmailSalesAction
	 */
	public boolean isOpenEmailSalesAction() {
		return openEmailSalesAction;
	}

	/**
	 * @param openEmailSalesAction the openEmailSalesAction to set
	 */
	public void setOpenEmailSalesAction(boolean openEmailSalesAction) {
		this.openEmailSalesAction = openEmailSalesAction;
	}

	/**
	 * @return the checkSendEmailDate
	 */
	public Date getCheckSendEmailDate() {
		return checkSendEmailDate;
	}

	/**
	 * @param checkSendEmailDate the checkSendEmailDate to set
	 */
	public void setCheckSendEmailDate(Date checkSendEmailDate) {
		this.checkSendEmailDate = checkSendEmailDate;
	}



}
