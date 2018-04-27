package com.randr.webdw.emailQueueAudit;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;

/**
 * Date: Oct 8, 2004
 * Time: 10:58:26 AM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class EmailQueueAuditBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "email_queue_audit";
        this.tableAlias = "eqa";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param emailQueueAuditDetails EmailQueueDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, EmailQueueAuditDetails emailQueueAuditDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(emailQueueAuditDetails);

        // establish the list of properties to be populated
        addColumnList("email_queue_audit_id, export_date, return_date, status, email_draft_id");
        addColumnList("prospect_id, webprofile_id, user_id, file_name");
        
        setOrderBy("email_queue_audit_type, upper(doc.file_name)");
        
        return doSelect(emailQueueAuditDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param emailQueueAuditDetails EmailQueueAuditDetails
     */
    private void establishSearchConditions(EmailQueueAuditDetails emailQueueAuditDetails) {

    	if (emailQueueAuditDetails.getEmailQueueAuditId() != null) {
            addCondition("email_queue_audit_id= ?");
            addConditionParam(emailQueueAuditDetails.getEmailQueueAuditId());
        }

        if (emailQueueAuditDetails.getExportDate() != null) {
            addCondition("export_date= ?");
            addConditionParam(emailQueueAuditDetails.getExportDate());
        }
        
        if (emailQueueAuditDetails.getReturnDate() != null) {
            addCondition("return_date= ?");
            addConditionParam(emailQueueAuditDetails.getReturnDate());
        }
        
        if (emailQueueAuditDetails.getStatus() != null) {
            addCondition("status= ?");
            addConditionParam(emailQueueAuditDetails.getStatus());
        }
        
        if (emailQueueAuditDetails.getEmailDraftId() != null) {
            addCondition("email_draft_id= ?");
            addConditionParam(emailQueueAuditDetails.getEmailDraftId());
        }
        
        if (emailQueueAuditDetails.getProspectId() != null) {
            addCondition("prospect_id= ?");
            addConditionParam(emailQueueAuditDetails.getProspectId());
        }
        
        if (emailQueueAuditDetails.getWebProfileId() != null) {
            addCondition("webprofile_id= ?");
            addConditionParam(emailQueueAuditDetails.getWebProfileId());
        }
        
        if (emailQueueAuditDetails.getUserId() != null) {
            addCondition("user_id= ?");
            addConditionParam(emailQueueAuditDetails.getUserId());
        }
        
        if (emailQueueAuditDetails.getFileName() != null) {
            addCondition("file_name= ?");
            addConditionParam(emailQueueAuditDetails.getFileName());
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param emailQueueAuditDetails EmailQueueAuditDetails
     * @return EmailQueueAuditDetails
     * @throws Exception
     */
    public EmailQueueAuditDetails insert(BigDecimal fillType, EmailQueueAuditDetails emailQueueAuditDetails) throws Exception {
        emailQueueAuditDetails.setEmailQueueAuditId(getAvailableID("email_queue_audit_id", "email_queue_audit"));
        System.out.println("just set " + emailQueueAuditDetails.getEmailQueueAuditId());
        addColumnList("email_queue_audit_id, export_date, status, email_draft_id");
        addColumnList("prospect_id, webprofile_id, user_id, file_name");
        
        System.out.println("bean - " + emailQueueAuditDetails.getUserId());
        return (EmailQueueAuditDetails) doInsert(emailQueueAuditDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param emailQueueAuditDetails EmailQueueAuditDetails
     * @return EmailQueueAuditDetails
     * @throws Exception
     */
    public EmailQueueAuditDetails update(BigDecimal fillType, EmailQueueAuditDetails emailQueueAuditDetails) throws Exception {
        addCondition("email_queue_audit_id= ?");
        addConditionParam(emailQueueAuditDetails.getEmailQueueAuditId());


        
        if (emailQueueAuditDetails.getExportDate() != null) {
            addColumnList("export_date");
        }
        if (emailQueueAuditDetails.getReturnDate() != null) {
            addColumnList("return_date");
        }
        if (emailQueueAuditDetails.getStatus() != null) {
            addColumnList("status");
        }
        if (emailQueueAuditDetails.getEmailDraftId() != null) {
            addColumnList("email_draft_id");
        }
        if (emailQueueAuditDetails.getProspectId() != null) {
            addColumnList("prospect_id");
        }
        if (emailQueueAuditDetails.getWebProfileId() != null) {
            addColumnList("webprofile_id");
        }
        if (emailQueueAuditDetails.getUserId() != null) {
            addColumnList("user_id");
        }
        if (emailQueueAuditDetails.getFileName() != null) {
            addCondition("file_name");
        }
        
        return (EmailQueueAuditDetails) doUpdate(emailQueueAuditDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param emailQueueAuditDetails EmailQueueAuditDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, EmailQueueAuditDetails emailQueueAuditDetails) throws Exception {
    	
    	if(emailQueueAuditDetails.getEmailQueueAuditId() != null){
            addCondition("email_queue_audit_id= ?");
            addConditionParam(emailQueueAuditDetails.getEmailQueueAuditId());

            doDelete(emailQueueAuditDetails);
            
            return true;	
    	}else{
    		return false;
    	}
    }

}
