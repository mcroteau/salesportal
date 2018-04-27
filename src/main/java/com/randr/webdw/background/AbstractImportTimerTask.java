package com.randr.webdw.background;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.NoRouteToHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimerTask;



import com.randr.webdw.AppSettings;
import com.randr.webdw.importData.importProcessorAbstract.CsvLine;
import com.randr.webdw.importData.importProcessorAbstract.ImportProcessorResult;
import com.randr.webdw.migrationLog.MigrationLogDetails;
import com.randr.webdw.util.ConnectionPool;
import com.randr.webdw.util.EmailUtilities;
import com.randr.webdw.util.URLHandle;
import com.randr.webdw.util.Utilities;

public class AbstractImportTimerTask extends TimerTask {
    protected Connection connection = null; 
	
	protected File getOldestFile(File[] files) {
		File oldestFile = null;
		if(files!=null){
			for(int i = 0; i < files.length; i++) {
				if(!files[i].isDirectory()) {
					if(oldestFile == null) {
						oldestFile = files[i];
					} else if(files[i].lastModified() < oldestFile.lastModified()) {
						oldestFile = files[i];
					}
				} 
			}
		}
		return oldestFile;
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	protected void sendOutImportBackgroundResultEmail(ImportBackgroundResult result) {
		//System.out.println("Salesportal-about to send mail, parm = " + AppSettings.getParm("IMPORT_EMAIL_ENABLED"));
		if(AppSettings.getParm("IMPORT_EMAIL_ENABLED").equalsIgnoreCase("TRUE") ||
				AppSettings.getParm("IMPORT_EMAIL_ENABLED").equalsIgnoreCase("YES")) {
			
			String from = AppSettings.getParm("IMPORT_AUTOMATED_EMAIL_FROM_ADDRESS");
			String username = AppSettings.getParm("SMTP_USER");
			String password = AppSettings.getParm("SMTP_PASSWORD");
			String host = AppSettings.getParm("SMTP_RELAY_HOST");
			String subject = getImportBackgroundResultEmailSubject(result);
			String body = getImportBackgroundResultEmailBody(result);
			
//			System.out.println("AppSettings.getParm(IMPORT_AUTOMATED_EMAIL_RECEIVER)=" + 
//					AppSettings.getParm("IMPORT_AUTOMATED_EMAIL_RECEIVER"));
			List emailAddresses = Utilities.tokenize(AppSettings.getParm("IMPORT_AUTOMATED_EMAIL_RECEIVER"), ",");
//			System.out.println("emailAddresses.size()=" + emailAddresses.size());
			for(int i = 0; i < emailAddresses.size(); i++) {
				if(!emailAddresses.get(i).toString().trim().equals("")) {
//					System.out.println("SP-Sending out email to '" + emailAddresses.get(i).toString().trim() + "'");
					EmailUtilities.sendMail(host, password, username, from, emailAddresses.get(i).toString().trim(), subject, body, "text/html");
				}
			}
			

		}
		
	}

	private String getImportBackgroundResultEmailSubject(ImportBackgroundResult result) {
		StringBuffer sb = new StringBuffer();
		sb.append(AppSettings.getParm("IMPORT_EMAIL_SUBJECT"));
		sb.append(" - ");
		sb.append(result.getImportTypeDescription(result.getImportType()));
		sb.append("  ");
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		sb.append(format.format(result.getImportProcessDate()));
		return sb.toString();
	}

	private String getImportBackgroundResultEmailBody(ImportBackgroundResult result) {
		StringBuffer sb = new StringBuffer();
		DateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy hh:mm");
		DateFormat formatTime = new SimpleDateFormat("hh:mm aa");
		 sb.append("<html><head>");
	        sb.append("<style type=\"\">");
	        sb.append("#webpage { width: 650px; }");
	        sb.append("</style>");
	        sb.append("</head><body>");
	        sb.append("<table>");
		sb.append("</tr> Automated Import Report<br/>");
		
		sb.append("Import type: " + result.getImportTypeDescription(result.getImportType()) + "<br/>");
		sb.append("Import date: " + formatDate.format(result.getImportProcessDate()) + "<br/>");
		sb.append("Import time: " + formatTime.format(result.getImportProcessDate()) + "<br/>");		
		sb.append("Number of files imported: " + result.getImportProcessorResultList().size() + "<br/>");
		
		sb.append("------------- DETAILS --------------- <br/>");
    
		for(int i = 0; i < result.getImportProcessorResultList().size(); i++) {
			ImportProcessorResult importProcessorResult = (ImportProcessorResult)result.getImportProcessorResultList().get(i);
			sb.append("Import File: " + importProcessorResult.getFileName() + "<br/>");
			
			if (importProcessorResult.isImportSuccessful()) {
				sb.append("Operation successful<br/>");
			} else if (importProcessorResult.getInvalidLines() != null) {
				sb.append("Error<br/>");
				sb.append(importProcessorResult.getInvalidLines().size() + " lines could not be processed:<br/>");
				for (int j = 0; j < importProcessorResult.getInvalidLines().size(); j++){
					CsvLine csvLine = (CsvLine) importProcessorResult.getInvalidLines().elementAt(i);
					sb.append("Line# " + csvLine.getLineNumber().intValue() + " - Error: " + csvLine.getErrorMessage() + "<br/>");
				}
			} else {
				sb.append("Errors occured while processing the file.<br/>");
				sb.append(Utilities.nullToBlank(importProcessorResult.getErrorMessage()) + "<br/>");
			}
			
			if (importProcessorResult.getWarningLines() != null) {
				CsvLine csvLine = (CsvLine) importProcessorResult.getWarningLines().elementAt(i);
				sb.append("Line# " + csvLine.getLineNumber().intValue() + " - Error: " + csvLine.getErrorMessage() + "<br/>");
			}
			
		}
		sb.append("</tr></table>");
		sb.append("</body></html>");	
		return sb.toString();
	}
	
	/**
	 * Send Request to DataMigration Portal to see if there are any CSV files to import
	 * If yes, then import all the CSV files into a directory
	 */
	public void importAndSaveCsvFiles(String formAction, String csvFileName) {
		
		try {
			openConnection();
			//log #1 - about to import files from DMP + file name
			logResultRequestFiles(formAction, csvFileName);
			boolean doneImporting = false;
			int count = 1;
			while(!doneImporting) {
				String content = URLHandle.getURLContent(
						AppSettings.getParm("DATA_MIGRATION_PROSPECT_CSV_LINK"), 
						URLHandle.HTTP, URLHandle.GET, "formAction=" + formAction );
				//System.out.println("SP - importAbstract getting Prospect CSV from DMP, content='" + content + "'");
				
				if(content.equals("")) {
					doneImporting = true;
				} else {
					//System.out.println("SP - importAbstract, about to write out the file= " + csvFileName);
					BufferedWriter out = new BufferedWriter(new FileWriter(csvFileName + "_" + count +".csv"));
			        out.write(content);
			        out.close();
			        count++;
				}
			} 
			//log #2 - done importing prospects, number of files imported = count + file name
			logResultRequestFilesComplete(formAction, csvFileName, count);
			commit();
		}catch (NoRouteToHostException nrthe) {
			String timestamp = new Timestamp(System.currentTimeMillis()).toString();
//			log # 3 - error - no rout to host
			try{
				logResultRequestFilesError(formAction, csvFileName);
			}catch (Exception e){
				e.printStackTrace();
			}	
			System.err.println( timestamp + " - No Route to Host - check Data Migration Portal");
		}catch (Exception e){
			e.printStackTrace();
		} finally {
			finallyMethod();
		}
	}

    /**
     * Method openConnection.
     * @throws SQLException
     */
    protected void openConnection() throws SQLException {
        this.connection = ConnectionPool.getNewConnection();
    }

    /**
     * Method commit.
     * @throws SQLException
     */
    protected void commit() throws SQLException {
        this.connection.commit();
    }

    /**
     * Method finallyMethod.
     */
    protected void finallyMethod() {
        if (this.connection != null) {
            try {
                this.connection.rollback();
            } catch (Exception sql) {
            }

            try {
                this.connection.close();
            } catch (Exception sql) {
            }
        }
    }

    /**
     * Method doCatch.
     * @param e Exception
     */
    protected void doCatch(Exception e) {
        e.printStackTrace();
    }



	private void logResultRequestFiles(String formAction, String csvFileName) throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
		migrationLogDetails.setMigrationId("");
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_IMPORT_FROM_DMP);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_IMPORT_FROM_DMP);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUBTYPE_IMPORT_FROM_DMP_REQUEST_FILES);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_NOTIFY);//succes or failure
		migrationLogDetails.setMigrationResult("Requesting CSV files from DMP.  File name = " + csvFileName + ". FormAction = " + formAction);
		logResult(migrationLogDetails);

	}

	private void logResultRequestFilesComplete(String formAction, String csvFileName, int count) throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
		migrationLogDetails.setMigrationId("");
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_IMPORT_FROM_DMP);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_IMPORT_FROM_DMP);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUBTYPE_IMPORT_FROM_DMP_REQUEST_FILES_COMPLETE);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_SUCCESS);//succes or failure
		count = count-1;
		if(count>0){
			migrationLogDetails.setMigrationResult("Requesting CSV files from DMP Complete. Total files = " + count + ". File name = " + 
				csvFileName + ". FormAction = " + formAction);
		}else{
			migrationLogDetails.setMigrationResult("Requesting CSV files from DMP Complete. Total files = " + count + ". FormAction = " + formAction);
		}
		logResult(migrationLogDetails);

	}

	private void logResultRequestFilesError(String formAction, String csvFileName) throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
		migrationLogDetails.setMigrationId("");
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_IMPORT_FROM_DMP);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_IMPORT_FROM_DMP);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUBTYPE_IMPORT_FROM_DMP_REQUEST_FILES_ERROR);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_FAILURE);//succes or failure
		migrationLogDetails.setMigrationResult("Requesting CSV files from DMP had communications error.  File name = " + csvFileName + ". FormAction = " + formAction);
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
