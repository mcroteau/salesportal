package com.randr.webdw.emailTemplate;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;


/**
 */
public class EmailTemplateBean extends AbstractDBBean {
    /**
     * Constructor for NoteBean.
     */
    public EmailTemplateBean() {
    }

    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "email_template";
        this.tableAlias = "et";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param emailTemplateDetails EmailTemplateDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, EmailTemplateDetails emailTemplateDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(emailTemplateDetails);

        // establish the list of properties to be populated
        addColumnList("email_template_id, user_name, email_template_date, description, text");
        setOrderBy("description, email_template_date");
        return doSelect(emailTemplateDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param emailTemplateDetails EmailTemplateDetails
     */
    private void establishSearchConditions(EmailTemplateDetails emailTemplateDetails) {

        if (emailTemplateDetails.getUserName() != null) {
            addCondition("user_name = ?");
            addConditionParam(emailTemplateDetails.getUserName());
        }

        if (emailTemplateDetails.getEmailTemplateId() != null) {
            addCondition("email_template_id = ?");
            addConditionParam(emailTemplateDetails.getEmailTemplateId());
        }

       
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param emailTemplateDetails EmailTemplateDetails
     * @return EmailTemplateDetails
     * @throws Exception
     */
    public EmailTemplateDetails insert(BigDecimal fillType, EmailTemplateDetails emailTemplateDetails) throws Exception {
        emailTemplateDetails.setEmailTemplateId(getAvailableID("email_template_id", "email_template"));
        addColumnList("email_template_id, user_name, email_template_date, description, text");

        return (EmailTemplateDetails) doInsert(emailTemplateDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param emailTemplateDetails EmailTemplateDetails
     * @return EmailTemplateDetails
     * @throws Exception
     */
    public EmailTemplateDetails update(BigDecimal fillType, EmailTemplateDetails emailTemplateDetails) throws Exception {
        addCondition("email_template_id = ? ");
        addConditionParam(emailTemplateDetails.getEmailTemplateId());

        addColumnList("user_name, email_template_date, description, text");

        return (EmailTemplateDetails) doUpdate(emailTemplateDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param emailTemplateDetails EmailTemplateDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, EmailTemplateDetails emailTemplateDetails) throws Exception {
        
        if (emailTemplateDetails.getEmailTemplateId() != null) {
            addCondition("email_template_id = ? ");
            addConditionParam(emailTemplateDetails.getEmailTemplateId());
        }
        
        if(emailTemplateDetails.getEmailTemplateId() != null ){
            doDelete(emailTemplateDetails);
            return true;
        }else{
        	return false;
        }
    }

}
