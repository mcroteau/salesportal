package com.randr.webdw.prospectImportLog;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;



/**
 * @author randr
 * @version $Revision: 1.0 $
 */
public class ProspectImportLogDetails extends AbstractDetails {
    // table fields mappings
    private BigDecimal prospectImportLogId;  //serial data type, will auto populate
    private String externalId;
    private String email;
    private String phone;
    private String actionTaken;
    private Timestamp prospectImportTimestamp;


    
    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("prospect_import_log_id","prospectImportLogId");
        addMapping("external_id","externalId");
        addMapping("email","email");
        addMapping("phone","phone");
        addMapping("action_taken","actionTaken");
        addMapping("prospect_import_timestamp","prospectImportTimestamp");
    }



	/**
	 * @return the actionTaken
	 */
	public String getActionTaken() {
		return actionTaken;
	}



	/**
	 * @param actionTaken the actionTaken to set
	 */
	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the externalId
	 */
	public String getExternalId() {
		return externalId;
	}



	/**
	 * @param externalId the externalId to set
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}



	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}



	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}



	/**
	 * @return the prospectImportLogId
	 */
	public BigDecimal getProspectImportLogId() {
		return prospectImportLogId;
	}



	/**
	 * @param prospectImportLogId the prospectImportLogId to set
	 */
	public void setProspectImportLogId(BigDecimal prospectImportLogId) {
		this.prospectImportLogId = prospectImportLogId;
	}



	/**
	 * @return the prospectImportTimestamp
	 */
	public Timestamp getProspectImportTimestamp() {
		return prospectImportTimestamp;
	}



	/**
	 * @param prospectImportTimestamp the prospectImportTimestamp to set
	 */
	public void setProspectImportTimestamp(Timestamp prospectImportTimestamp) {
		this.prospectImportTimestamp = prospectImportTimestamp;
	}

   
}
