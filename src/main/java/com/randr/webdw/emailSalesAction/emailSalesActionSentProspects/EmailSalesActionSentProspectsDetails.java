package com.randr.webdw.emailSalesAction.emailSalesActionSentProspects;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class EmailSalesActionSentProspectsDetails extends AbstractDetails {


	protected BigDecimal sentEmailActionId;
    protected BigDecimal emailActionId;
    protected BigDecimal prospectId;
    protected Timestamp sentDate;
    
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("sent_email_action_id", "sentEmailActionId");
        addMapping("email_action_id", "emailActionId");
        addMapping("prospect_id", "prospectId");
        addMapping("sent_date", "sentDate");
    }
    
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(super.toString());
    	
    	sb.append("[ ");
    	sb.append("emailLobId=" + sentEmailActionId + ", ");
    	sb.append("emailActionId=" + emailActionId + ", ");
    	sb.append("prospectId=" + prospectId + ", ");
    	sb.append("sentDate=" + sentDate + ", ");
    	sb.append(" ]");  	
    	
    	return sb.toString();
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
	 * @return the prospectId
	 */
	public BigDecimal getProspectId() {
		return prospectId;
	}

	/**
	 * @param prospectId the prospectId to set
	 */
	public void setProspectId(BigDecimal prospectId) {
		this.prospectId = prospectId;
	}

	/**
	 * @return the sentDate
	 */
	public Timestamp getSentDate() {
		return sentDate;
	}

	/**
	 * @param sentDate the sentDate to set
	 */
	public void setSentDate(Timestamp sentDate) {
		this.sentDate = sentDate;
	}

	/**
	 * @return the sentEmailActionId
	 */
	public BigDecimal getSentEmailActionId() {
		return sentEmailActionId;
	}

	/**
	 * @param sentEmailActionId the sentEmailActionId to set
	 */
	public void setSentEmailActionId(BigDecimal sentEmailActionId) {
		this.sentEmailActionId = sentEmailActionId;
	}

}
