package com.randr.webdw.salesAction;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class SalesActionDetails extends AbstractDetails {
	public static BigDecimal REQUIRES_MANDATORY_DATE = new BigDecimal(1);
	public static BigDecimal DOES_NOT_REQUIRE_DATE = new BigDecimal(0);

	public static BigDecimal HASNT_BEEN_NOTIFIED = new BigDecimal(0);
	public static BigDecimal NOTIFY = new BigDecimal(1);
	
	public static BigDecimal TOP_PRIORITY = new BigDecimal(1);
	public static BigDecimal SECOND_PRIORITY = new BigDecimal(2);
	public static BigDecimal THIRD_PRIORITY = new BigDecimal(3);
	public static BigDecimal FOURTH_PRIORITY = new BigDecimal(4);

	public static BigDecimal ACTIVE = new BigDecimal(0);
	public static BigDecimal COMPLETED = new BigDecimal(1);
	
    protected BigDecimal actionId;
    protected String action;
    protected BigDecimal mandatoryDate;
    protected String color;
    protected BigDecimal emailDraftToUse;//which email draft # to use (see OP for the number)
    // if a sales action has an email draft # then this is an email sales action
    protected BigDecimal sendEmailDays;//used in automated campaigns
    protected BigDecimal displayForSalesmen;// controls if this is displayed in the select box

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
    
    public static final BigDecimal NO_DONOT_DISPLAY_FOR_SALESMEN = new BigDecimal(0);//this controls the options in the prospect screen
    public static final BigDecimal YES_DISPLAY_FOR_SALESMEN = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("action_id", "actionId");
        addMapping("action", "action");
        addMapping("mandatory_date", "mandatoryDate");
        addMapping("color", "color");
        addMapping("email_draft_to_use", "emailDraftToUse");
        addMapping("display_for_salesmen", "displayForSalesmen");
    }

    /**
     * Method getActionId.
     * @return BigDecimal
     */
    public BigDecimal getActionId() {
        return actionId;
    }

    /**
     * Method setActionId.
     * @param actionId BigDecimal
     */
    public void setActionId(BigDecimal actionId) {
        this.actionId = actionId;
    }

    /**
     * Method getAction.
     * @return String
     */
    public String getAction() {
        return action;
    }

    /**
     * Method setAction.
     * @param action String
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Method getMandatoryDate.
     * @return BigDecimal
     */
    public BigDecimal getMandatoryDate() {
        return mandatoryDate;
    }

    /**
     * Method setMandatoryDate.
     * @param mandatoryDate BigDecimal
     */
    public void setMandatoryDate(BigDecimal mandatoryDate) {
        this.mandatoryDate = mandatoryDate;
    }
     
    /**
     * Method getColor.
     * @return String
     */
    public String getColor() {
        return color;
    }

    /**
     * Method setColor.
     * @param action String
     */
    public void setColor(String color) {
        this.color = color;
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

	/**
	 * @return the displayForSalesmen
	 */
	public BigDecimal getDisplayForSalesmen() {
		return displayForSalesmen;
	}

	/**
	 * @param displayForSalesmen the displayForSalesmen to set
	 */
	public void setDisplayForSalesmen(BigDecimal displayForSalesmen) {
		this.displayForSalesmen = displayForSalesmen;
	}

}
