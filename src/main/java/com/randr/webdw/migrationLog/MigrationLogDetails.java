package com.randr.webdw.migrationLog;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;



/**
 * @author randr
 * @version $Revision: 1.0 $
 */
public class MigrationLogDetails extends AbstractDetails {
    // table fields mappings
    private BigDecimal migrationLogId;  //serial data type, will auto populate
    private String migrationId;//example - http return code
    private String application;  // example: MigrationLogDetails.UPLOAD_ORDER_FOR_EXPORT_TO_OP
    private String migrationType; // example OVERSTOCK UPLOAD
    private String migrationSubType; // example OVERSTOCK TRANSFER
    private String migrationResult;// example "Transfered " + content
    private String migrationTag; // example succes or failure
    private Timestamp migrationTimestamp;


    // public constants
    public static final String DEFAULT_SORT_KEY = "getMigrationLogTimestamp";
    
    // id
    public static final String ID_IMPORT_FROM_DMP_PROSPECT = "ProspectImportTimerTask";
    public static final String MIGRATION_ID_EMAIL_QUEUE_TRANSFER_CONTROLLER = "EmailQueTransferController";
    
    //Applications
    public static final String APPLICATION_IMPORT_FROM_DMP = "SP Importing files from DMP";
    public static final String APPLICATION_PROCESS_DMP_IMPORTS = "SP Processing files imported from DMP";
    public static final String APPLICATION_PROCESS_EMAIL_QUEUE = "SP Email Queue process, import and export";
    public static final String APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_NOTIFY = "DMP-Email being passed from SP to OP - SP Notifies DMP";
    public static final String APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_GET_FILES = "DMP-Email being passed from SP to OP - DMP gets files from SP";
    public static final String APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_PASSES_FILES = "DMP-Email being passed from SP to OP - DMP passes files to OP";

   
    //Migration Type
    public static final String TYPE_IMPORT_FROM_DMP = "SP Importing files from DMP";
    public static final String TYPE_PROCESS_DMP_IMPORTS = "SP Processing files imported from DMP";
    public static final String TYPE_EMAIL_QUE_TRANSFER_FORMACTION= "DMP-Email Que formAction= ";
    
    //Migration sub type
    public static final String SUBTYPE_IMPORT_FROM_DMP_REQUEST_FILES = "SP Importing files from DMP-Requesting files ";
    public static final String SUBTYPE_IMPORT_FROM_DMP_REQUEST_FILES_COMPLETE = "SP Importing files from DMP-Completed receiving files ";
    public static final String SUBTYPE_IMPORT_FROM_DMP_REQUEST_FILES_ERROR = "SP Importing files from DMP-Requesting files, connection error ";
    public static final String SUBTYPE_PROCESS_DMP_IMPORTS_NO_FILES = "SP Processing files imported from DMP - no files to import";
    public static final String SUBTYPE_PROCESS_DMP_IMPORTS_YES_FILES = "SP Processing files imported from DMP - yes there are files to import";	
    public static final String SUBTYPE_PROCESS_DMP_IMPORTS_ERROR = "SP Processing files imported from DMP - yes there are files to import, but we got an error processing the files";
    public static final String SUBTYPE_PROCESS_DMP_IMPORTS_PROSPECTS = "SP Processing files imported from DMP - this file contains prospects";
    public static final String SUBTYPE_PROCESS_DMP_IMPORTS_OEPORDERS = "SP Processing files imported from DMP - this file contains OEP orders/invoices";
    public static final String SUBTYPE_PROCESS_DMP_IMPORTS_COMPLETE = "SP Processing files imported from DMP - completed processing all files";
    public static final String SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_NOTIFY_STARTED = "Email Q Notify Started";
    public static final String SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_NOTIFY_ENDED = "Email Q Notify Ended";
    public static final String SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_NOTIFY_ERROR = "Email Q Notify has error";
    public static final String SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_GET_FILES_STARTED = "Email Q Get Files Started";
    public static final String SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_GET_FILES_ENDED = "Email Q Get Files Ended";
    public static final String SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_GET_FILES_ERROR = "Email Q Get Files has error";
    public static final String SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_PASSES_FILES_STARTED = "Email Q Passes Files Started";
    public static final String SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_PASSES_FILES_ENDED = "Email Q Passes Files Ended";
    public static final String SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_PASSES_FILES_ERROR = "Email Q Passes Files has error";

    // migration Tag
    public static final String TAG_SUCCESS = "SUCCESS";
    public static final String TAG_FAILURE = "FAILURE";
    public static final String TAG_DB_WARNING = "DATABASE WARNING";
    public static final String TAG_WARNING = "WARNING";
    public static final String TAG_NOTIFY = "NOTIFY";
    

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("migration_log_id","migrationLogId");
        addMapping("migration_id","migrationId");
        addMapping("application","application");
        addMapping("migration_type","migrationType");
        addMapping("migration_sub_type","migrationSubType");
        addMapping("migration_result","migrationResult");
        addMapping("migration_tag","migrationTag");
        addMapping("migration_timestamp","migrationTimestamp");
    }

    
	public BigDecimal getMigrationLogId() {
		return migrationLogId;
	}


	public void setMigrationLogId(BigDecimal migrationLogId) {
		this.migrationLogId = migrationLogId;
	}


	public String getMigrationResult() {
		return migrationResult;
	}


	public void setMigrationResult(String migrationResult) {
		this.migrationResult = migrationResult;
	}


	public Timestamp getMigrationTimestamp() {
		return migrationTimestamp;
	}


	public void setMigrationTimestamp(Timestamp migrationTimestamp) {
		this.migrationTimestamp = migrationTimestamp;
	}


	public String getMigrationType() {
		return migrationType;
	}


	public void setMigrationType(String migrationType) {
		this.migrationType = migrationType;
	}


	public String getMigrationId() {
		return migrationId;
	}


	public void setMigrationId(String migrationId) {
		this.migrationId = migrationId;
	}


	public String getMigrationTag() {
		return migrationTag;
	}


	public void setMigrationTag(String migrationTag) {
		this.migrationTag = migrationTag;
	}


	/**
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}


	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}


	/**
	 * @return the migrationSubType
	 */
	public String getMigrationSubType() {
		return migrationSubType;
	}


	/**
	 * @param migrationSubType the migrationSubType to set
	 */
	public void setMigrationSubType(String migrationSubType) {
		this.migrationSubType = migrationSubType;
	}

 
}
