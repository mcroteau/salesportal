package com.randr.webdw.emailTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class EmailTemplateDetails extends AbstractDetails {

    protected BigDecimal emailTemplateId;
    protected String userName;
    protected Timestamp emailTemplateDate;
    protected String description;
    protected String text;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Constructor for NoteDetails.
     */
    public EmailTemplateDetails() {
    }

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("email_template_id", "emailTemplateId");
        addMapping("user_name", "userName");
        addMapping("email_template_date", "emailTemplateDate");
        addMapping("description", "description");
        addMapping("text", "text");
    }
    
 
    /**
     * Method getUserAndDate.
     * @return String
     */
    public String getUserAndDate() {
        return this.userName.toString() + "#" + String.valueOf(this.emailTemplateDate.getTime());
    }

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the emailTemplateDate
	 */
	public Timestamp getEmailTemplateDate() {
		return emailTemplateDate;
	}

	/**
	 * @param emailTemplateDate the emailTemplateDate to set
	 */
	public void setEmailTemplateDate(Timestamp emailTemplateDate) {
		this.emailTemplateDate = emailTemplateDate;
	}

	/**
	 * @return the emailTemplateId
	 */
	public BigDecimal getEmailTemplateId() {
		return emailTemplateId;
	}

	/**
	 * @param emailTemplateId the emailTemplateId to set
	 */
	public void setEmailTemplateId(BigDecimal emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

  
}
