package com.randr.webdw.background.backendEmailOptOutImport;

import java.io.File;

import com.randr.webdw.AppSettings;
import com.randr.webdw.background.AbstractImportTimerTask;
import com.randr.webdw.background.ImportBackgroundResult;
import com.randr.webdw.importData.importProcessor.EmailOptOutImportProcessor;
import com.randr.webdw.importData.importProcessor.ProspectImportParameters;
import com.randr.webdw.importData.importProcessorAbstract.ImportProcessorResult;
import com.randr.webdw.util.DateUtilities;

/**
 * @author randr
 * 
 */
public class EmailOptOutImportTimerTask extends AbstractImportTimerTask {

    /**
     * Method run.
     */
    public void run() {
//    	System.out.println("performing import");
        performEmailOptOutImport();
    }

	private void performEmailOptOutImport() {
		
		importAndSaveCsvFiles();
		ImportBackgroundResult result = importCsvFilesAsContracts();
		
		// Only send out email if we did an import
		if(result != null) {
			//sendOutImportBackgroundResultEmail(result);
		}
		
	}
	
	private ImportBackgroundResult importCsvFilesAsContracts() {
		ImportBackgroundResult result = new ImportBackgroundResult();
		result.setImportType(ImportBackgroundResult.IMPORT_TYPE_EMAIL_OPTOUT);
		result.setImportProcessDate(DateUtilities.getCurrentSQLTimestamp());
		
		File unprocessedCsvDirectory = new File(getUnprocessedCsvDirectoryName());
		File processedCsvDirectory = new File(getProcessedCsvDirectoryName());
		
		File[] files = unprocessedCsvDirectory.listFiles();
		File oldestFile = getOldestFile(files);
		
		// if no files to import return with null
		if(oldestFile == null) {
			return null;
		}
		System.out.println("SP - In email opt out import, retrieving files");
		
		ProspectImportParameters prospectImportParameters = new ProspectImportParameters();
		ImportProcessorResult importProcessorResult;
		while(oldestFile != null) {
			EmailOptOutImportProcessor processor =
				new EmailOptOutImportProcessor(oldestFile.getName(), getUnprocessedCsvDirectoryName(), 
						getProcessedCsvDirectoryName(), getInvalidCsvDirectoryName(),
						getLogCsvDirectoryName(), prospectImportParameters);
			//ignores first line when set to true, we are sending in heading line
			processor.setSpecialFirstLine(true);
			processor.run();
			importProcessorResult = processor.getImportProcessorResult();
			importProcessorResult.setFileName(oldestFile.getName());
			result.addImportProcessorResult(processor.getImportProcessorResult());
			files = unprocessedCsvDirectory.listFiles();
			oldestFile = getOldestFile(files);
		}
		return result;
	}

	/**
	 * Send Request to DataMigration Portal to see if there are any CSV files to import
	 * If yes, then import all the CSV files into a directory
	 */
	private void importAndSaveCsvFiles() {
		createRequiredDirectories();
		importAndSaveCsvFiles("REQUEST_SALESPORTAL_EMAIL_OPTOUT_CSV",getCsvFileName());
	}

	private void createRequiredDirectories() {
		File dir = new File(AppSettings.getAppRealPath() + AppSettings.getParm("EMAIL_OPTOUT_IMPORT_UNPROCESSED_STORAGE"));
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
	}
	
	private String getUnprocessedCsvDirectoryName() {
		return AppSettings.getAppRealPath() 
			+ AppSettings.getParm("EMAIL_OPTOUT_IMPORT_UNPROCESSED_STORAGE");
	}
	
	private String getProcessedCsvDirectoryName() {
		return AppSettings.getAppRealPath() 
			+ AppSettings.getParm("EMAIL_OPTOUT_IMPORT_PROCESSED_STORAGE");
	}
	
	private String getInvalidCsvDirectoryName() {
		return AppSettings.getAppRealPath() 
			+ AppSettings.getParm("EMAIL_OPTOUT_IMPORT_INVALID_STORAGE");
	}
	
	private String getLogCsvDirectoryName() {
		return AppSettings.getAppRealPath() 
			+ AppSettings.getParm("EMAIL_OPTOUT_IMPORT_LOG");
	}
	
	private String getCsvFileName() {
		return AppSettings.getAppRealPath() 
			+ AppSettings.getParm("EMAIL_OPTOUT_IMPORT_UNPROCESSED_STORAGE") 
			+ "/" 
			+ AppSettings.getParm("EMAIL_OPTOUT_IMPORT_FILE_PREFIX") 
			+ "_" + DateUtilities.getCurrentSQLDate() 
			+ "_" + System.currentTimeMillis(); 
			//+ ".csv";
	}

}
