package com.randr.webdw.emailQueueAudit;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * Date: Oct 8, 2004
 * Time: 10:48:36 AM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class EmailQueueAuditDetails extends AbstractDetails {
    protected BigDecimal emailQueueAuditId;
    protected Timestamp exportDate;
    protected Timestamp returnDate;
    protected BigDecimal status;
    protected BigDecimal emailDraftId;
    protected BigDecimal prospectId;
    protected BigDecimal webProfileId;
    protected BigDecimal userId;
    protected String fileName;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    public static final BigDecimal SUCCESSFULLY_SENT_EMAIL = new BigDecimal(1);
    public static final BigDecimal EXPORTED_TO_ORDERPORTAL = new BigDecimal(2);
    public static final BigDecimal FAILED_TO_EXPORT = new BigDecimal(3);
    public static final BigDecimal EXPORTED_BUT_FAILED_TO_SEND_EMAIL = new BigDecimal(4);
    
	/**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("email_queue_audit_id", "emailQueueAuditId");
        addMapping("export_date", "exportDate");
        addMapping("return_date", "returnDate");
        addMapping("status", "status");
        addMapping("email_draft_id", "emailDraftId");
        addMapping("prospect_id", "prospectId");
        addMapping("webprofile_id", "webProfileId");
        addMapping("user_id", "userId");
        addMapping("file_name", "fileName");
    }
    
    


	public BigDecimal getEmailDraftId() {
		return emailDraftId;
	}

	public void setEmailDraftId(BigDecimal emailDraftId) {
		this.emailDraftId = emailDraftId;
	}

	public BigDecimal getEmailQueueAuditId() {
		return emailQueueAuditId;
	}

	public void setEmailQueueAuditId(BigDecimal emailQueueAuditId) {
		this.emailQueueAuditId = emailQueueAuditId;
	}

	public Timestamp getExportDate() {
		return exportDate;
	}

	public void setExportDate(Timestamp exportDate) {
		this.exportDate = exportDate;
	}

	public BigDecimal getProspectId() {
		return prospectId;
	}

	public void setProspectId(BigDecimal prospectId) {
		this.prospectId = prospectId;
	}

	public Timestamp getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Timestamp returnDate) {
		this.returnDate = returnDate;
	}

	public BigDecimal getStatus() {
		return status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public BigDecimal getWebProfileId() {
		return webProfileId;
	}

	public void setWebProfileId(BigDecimal webProfileId) {
		this.webProfileId = webProfileId;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String toString(){
		return "EmailQueueAuditDetails: " +
				"emailQueueAuditId " + emailQueueAuditId + "\n" +
				"emailDraftId " + emailDraftId + "\n" +
				"webProfileId " + webProfileId + "\n" +
				"prospectId " + prospectId + "\n" +
				"status " + status + "\n" +
				"fileName " + fileName + "\n" +
				"exportDate " + exportDate + "\n" +
				"returnDate " + returnDate + "\n" +
				"userId " + userId;
				
	}
}
