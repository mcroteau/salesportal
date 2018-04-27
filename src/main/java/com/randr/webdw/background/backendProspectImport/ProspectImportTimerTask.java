package com.randr.webdw.background.backendProspectImport;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.randr.webdw.AppSettings;
import com.randr.webdw.background.AbstractImportTimerTask;
import com.randr.webdw.background.ImportBackgroundResult;
import com.randr.webdw.importData.importProcessor.OepInvoiceImportProcessor;
import com.randr.webdw.importData.importProcessor.ProspectImportParameters;
import com.randr.webdw.importData.importProcessor.ProspectImportProcessor;
import com.randr.webdw.importData.importProcessorAbstract.ImportAbstractProcessor;
import com.randr.webdw.importData.importProcessorAbstract.ImportProcessorResult;
import com.randr.webdw.migrationLog.MigrationLogDetails;
import com.randr.webdw.util.DateUtilities;

/**
 * @author randr
 * 
 */
public class ProspectImportTimerTask extends AbstractImportTimerTask {

    /**
     * Method run.
     */
    public void run() {
    	System.out.println("SP - performing import");
        performProspectImport();
    }

	private void performProspectImport() {
		importAndSaveCsvFilesProspects();
		importAndSaveCsvFilesOepInvoices();
		ImportBackgroundResult result = importCsvFilesAsContracts();
		
		// Only send out email if we did an import
		if(result != null) {
			sendOutImportBackgroundResultEmail(result);//do we do this?
		}
		
	}
	
	private ImportBackgroundResult importCsvFilesAsContracts() {
		try{	
			openConnection();
			//System.out.println("SP - in importCsvFilesAsContracts 1"); 
			ImportBackgroundResult result = new ImportBackgroundResult();
			result.setImportProcessDate(DateUtilities.getCurrentSQLTimestamp());
			
			File unprocessedCsvDirectory = new File(getUnprocessedCsvDirectoryName());
			File processedCsvDirectory = new File(getProcessedCsvDirectoryName());
			
			File[] files = unprocessedCsvDirectory.listFiles();
			File oldestFile = getOldestFile(files);
			//System.out.println("SP - in importCsvFilesAsContracts 2 files =" + files);
			// if no files to import return with null
			if(oldestFile == null) {
				//System.out.println("SP - in importCsvFilesAsContracts 3 - no files"); 
				//log #1A - no files to import
				logResultProcessCSVFilesNoFiles();
				return null;
			}else {
				//System.out.println("SP - in importCsvFilesAsContracts 4 - yes files"); 
				//log #1B - files to import
				logResultProcessCSVFilesYesFiles();
			}
			//System.out.println("SP - In prospect import 5, retrieving files");
			//what if we checked the first x characters of the file name and based on what it was executed a different importProcssor
			
			ProspectImportParameters prospectImportParameters = new ProspectImportParameters();
			if(AppSettings.getParm("ELIMINATE_DUPS_ON_AUTOMATED_IMPORTS").compareToIgnoreCase("TRUE")==0 ||
					AppSettings.getParm("ELIMINATE_DUPS_ON_AUTOMATED_IMPORTS").compareToIgnoreCase("YES")==0){
				// app setting determines if we eliminate dups on the automated imports.	
				prospectImportParameters.setEliminateDups(true);
			}	
			ImportProcessorResult importProcessorResult;
			ImportAbstractProcessor processor = null;
			while(oldestFile != null) {
				String importFileType = getImportType(oldestFile.getName());
				
				
				if(importFileType.compareToIgnoreCase("PROSPECTS")==0){
					result.setImportType(ImportBackgroundResult.IMPORT_TYPE_PROSPECT);
					//log about to import prospects
					logResultProcessCSVFilesYesFilesProspects();
					processor = new ProspectImportProcessor(oldestFile.getName(), getUnprocessedCsvDirectoryName(), 
							getProcessedCsvDirectoryName(), getInvalidCsvDirectoryName(),
							getLogCsvDirectoryName(), prospectImportParameters);
				//ignores first line when set to true
				// 8/2/09 this is moved into the Import processing where we check the elemen 0 to determine if header or data
					//   due to this coming in automated from multiple sources
				//	processor.setSpecialFirstLine(true); 
				}else if(importFileType.compareToIgnoreCase("OEPINVOICE")==0){
					System.out.println("in SP ProspectImportTimerTask = about to import OEP Invoice");
					//log about to import OEP orders
					logResultProcessCSVFilesYesFilesOEPOrders();
					result.setImportType(ImportBackgroundResult.IMPORT_TYPE_OEPINVOICE);
					processor = new OepInvoiceImportProcessor(oldestFile.getName(), getUnprocessedCsvDirectoryName(), 
								getProcessedCsvDirectoryName(), getInvalidCsvDirectoryName(),
								getLogCsvDirectoryName(), prospectImportParameters);
					//ignores first line when set to true
					processor.setSpecialFirstLine(true);
				}
				processor.run();
				importProcessorResult = processor.getImportProcessorResult();
				importProcessorResult.setFileName(oldestFile.getName());
				result.addImportProcessorResult(processor.getImportProcessorResult());
				files = unprocessedCsvDirectory.listFiles();
				oldestFile = getOldestFile(files);
			}
			logResultProcessCSVFilesYesFilesComplete();
			commit();
			return result;
		}catch (Exception e) {
			String timestamp = new Timestamp(System.currentTimeMillis()).toString();
			
//			log # 3 - error - no rout to host
			try{
				logResultProcessCSVFilesError();
			}catch (Exception e1){
				
			}
			return null;
		} finally {
			finallyMethod();
		}
	}

	
	private String getImportType(String filename) {
//		 First occurrence of a c
       int index = filename.indexOf('_');    
       String importType = filename.substring(0, index);
       //System.out.println(" in ProspectImportTimerTask, importType = " + importType + " fileName = " + filename);
       
       if(importType.compareToIgnoreCase("invoices")==0){
       	return "OEPINVOICE";
       }else if(importType.compareToIgnoreCase("prospects")==0){
       	return "PROSPECTS";
       }else if(importType.compareToIgnoreCase("orderssp")==0){
          	return "OPORDERS";
       }else {
    	   return "PROSPECTS";
       }
	}
	/**
	 * Send Request to DataMigration Portal to see if there are any CSV files to import
	 * If yes, then import all the CSV files into a directory
	 */
	private void importAndSaveCsvFilesProspects() {
		createRequiredDirectories();
		importAndSaveCsvFiles("REQUEST_SALESPORTAL_PROSPECT_CSV",getCsvFileNameProspects());
	}

	private void importAndSaveCsvFilesOepInvoices() {
		createRequiredDirectories();
		importAndSaveCsvFiles("REQUEST_SALESPORTAL_OEPINVOICES_CSV",getCsvFileNameOepInvoices());
	}
	
	private void createRequiredDirectories() {
		File dir = new File(AppSettings.getAppRealPath() + AppSettings.getParm("PROSPECT_IMPORT_UNPROCESSED_STORAGE"));
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
	}
	
	private String getUnprocessedCsvDirectoryName() {
		return AppSettings.getAppRealPath() 
			+ AppSettings.getParm("PROSPECT_IMPORT_UNPROCESSED_STORAGE");
	}
	
	private String getProcessedCsvDirectoryName() {
		return AppSettings.getAppRealPath() 
			+ AppSettings.getParm("PROSPECT_IMPORT_PROCESSED_STORAGE");
	}
	
	private String getInvalidCsvDirectoryName() {
		return AppSettings.getAppRealPath() 
			+ AppSettings.getParm("PROSPECT_IMPORT_INVALID_STORAGE");
	}
	
	private String getLogCsvDirectoryName() {
		return AppSettings.getAppRealPath() 
			+ AppSettings.getParm("PROSPECT_IMPORT_LOG");
	}
	
	/*private String getCsvFileName() {
		//hum, this may be a problem, renaming the file here
		return AppSettings.getAppRealPath() 
			+ AppSettings.getParm("PROSPECT_IMPORT_UNPROCESSED_STORAGE") 
			+ "/" 
			+ AppSettings.getParm("PROSPECT_IMPORT_FILE_PREFIX") 
			+ "_" + DateUtilities.getCurrentSQLDate() 
			+ "_" + System.currentTimeMillis(); 
			//+ ".csv";
	}*/
	private String getCsvFileNameProspects() {
		String fileName = AppSettings.getAppRealPath() 
		+ AppSettings.getParm("PROSPECT_IMPORT_UNPROCESSED_STORAGE") 
		+ "/" 
		+ AppSettings.getParm("PROSPECT_IMPORT_FILE_PREFIX") 
		+ "_" + DateUtilities.getCurrentSQLDate() 
		+ "_" + System.currentTimeMillis(); 
//		+ ".csv";
		//System.out.println("In getCsvFileNameProspects, file name = " + fileName);
		return fileName;
			
	}
	private String getCsvFileNameOepInvoices() {
		String fileName = AppSettings.getAppRealPath() 
		+ AppSettings.getParm("PROSPECT_IMPORT_UNPROCESSED_STORAGE") 
		+ "/" 
		+ AppSettings.getParm("INVOICE_IMPORT_FILE_PREFIX") 
		+ "_" + DateUtilities.getCurrentSQLDate() 
		+ "_" + System.currentTimeMillis(); 
		//+ ".csv";
		//System.out.println("In getCsvFileNameOepInvoices, file name = " + fileName);
		return fileName;
	}

	private void logResultProcessCSVFilesNoFiles() throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
		migrationLogDetails.setMigrationId(MigrationLogDetails.ID_IMPORT_FROM_DMP_PROSPECT);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUBTYPE_PROCESS_DMP_IMPORTS_NO_FILES);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_NOTIFY);//succes or failure
		migrationLogDetails.setMigrationResult("No files were received from DMP");
		logResult(migrationLogDetails);

	}

	private void logResultProcessCSVFilesYesFiles() throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
    	migrationLogDetails.setMigrationId(MigrationLogDetails.ID_IMPORT_FROM_DMP_PROSPECT);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUBTYPE_PROCESS_DMP_IMPORTS_YES_FILES);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_NOTIFY);//succes or failure
		migrationLogDetails.setMigrationResult("Yes, files were received from DMP");
		logResult(migrationLogDetails);

	}
	
	private void logResultProcessCSVFilesError() throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
    	migrationLogDetails.setMigrationId(MigrationLogDetails.ID_IMPORT_FROM_DMP_PROSPECT);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUBTYPE_PROCESS_DMP_IMPORTS_ERROR);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_FAILURE);//succes or failure
		migrationLogDetails.setMigrationResult("Error during processing ofr files received from DMP");
		logResult(migrationLogDetails);

	}
	
	private void logResultProcessCSVFilesYesFilesProspects() throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
    	migrationLogDetails.setMigrationId(MigrationLogDetails.ID_IMPORT_FROM_DMP_PROSPECT);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUBTYPE_PROCESS_DMP_IMPORTS_PROSPECTS);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_NOTIFY);//succes or failure
		migrationLogDetails.setMigrationResult("Starting to process Prospects into SP");
		logResult(migrationLogDetails);

	}
	
	private void logResultProcessCSVFilesYesFilesOEPOrders() throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
    	migrationLogDetails.setMigrationId(MigrationLogDetails.ID_IMPORT_FROM_DMP_PROSPECT);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUBTYPE_PROCESS_DMP_IMPORTS_OEPORDERS);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_NOTIFY);//succes or failure
		migrationLogDetails.setMigrationResult("Starting to process OEP Orders into SP as commissions");
		logResult(migrationLogDetails);

	} 
	
	private void logResultProcessCSVFilesYesFilesComplete() throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
    	migrationLogDetails.setMigrationId(MigrationLogDetails.ID_IMPORT_FROM_DMP_PROSPECT);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_PROCESS_DMP_IMPORTS);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUBTYPE_PROCESS_DMP_IMPORTS_OEPORDERS);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_SUCCESS);//succes or failure
		migrationLogDetails.setMigrationResult("Completed processing all files into SP");
		logResult(migrationLogDetails);

	} 
	
	 private void logResult(MigrationLogDetails migrationLogDetails) throws SQLException{
		    StringBuffer sb = new StringBuffer();
			Statement statement = connection.createStatement();
			sb.append("insert into migration_log_sp ( migration_id ,application, migration_type, migration_sub_type," +
					"migration_result, migration_tag, migration_timestamp) values ( ");
			sb.append("'" + migrationLogDetails.getMigrationId() + "', ");
			sb.append("'" + migrationLogDetails.getApplication() + "', ");
			sb.append("'" + migrationLogDetails.getMigrationType() + "', ");
			sb.append("'" + migrationLogDetails.getMigrationSubType() + "', ");
			sb.append("'" + migrationLogDetails.getMigrationResult() + "', ");
			sb.append("'" + migrationLogDetails.getMigrationTag() + "', ");
			sb.append("'" + migrationLogDetails.getMigrationTimestamp() + "'");
			sb.append(")");
			//System.out.println("migration Log statement = " + sb.toString());		
			statement.executeUpdate(sb.toString()); 
			statement.close();		
		}
	 

	
}
