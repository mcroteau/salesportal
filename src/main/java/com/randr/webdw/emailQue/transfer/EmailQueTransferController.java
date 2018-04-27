package com.randr.webdw.emailQue.transfer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.NoRouteToHostException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.ServletException;

import com.Ostermiller.util.CSVPrinter;
import com.randr.webdw.user.UserProfile;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.AppSettings;
import com.randr.webdw.emailQueueAudit.EmailQueueAuditDetails;
import com.randr.webdw.emailQueueAudit.EmailQueueAuditView;
import com.randr.webdw.migrationLog.MigrationLogDetails;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.AbstractMessageController;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.util.URLHandle;
import com.randr.webdw.util.Utilities;


public class EmailQueTransferController extends AbstractMessageController {

	public void doAction() throws ServletException, IOException {
		//System.out.println("formAction SP =" + formAction);
		if(formAction.equals("NOTIFY_EMAIL_QUE_UPLOAD")) {
			startEmailQueTransfer(formAction);
		} else if (formAction.equals("TRANSFER_EMAIL_QUE_SALESPORTAL")) {
			//System.out.println("SP-IN CORRECT SALES PORTAL CALL - TRANSFER_EMAIL_QUE_SALESPORTAL");
			transferEmailQueCsvFile(formAction);
	    }

	}


	private void transferEmailQueCsvFile(String formAction) {
		String id = "";
		String fileName = "";
		try {
			id = (String)request.getParameter("id");
			fileName = (String)request.getParameter("fileName");
			//System.out.println("SP-email, fileName= " + fileName);
			createRequiredEmailQueDirectories();
			logResultPassFilesStarted(formAction);
			requestCsv(getUnprocessedCsvEmailQueDirectoryName(), getProcessedCsvEmailQueDirectoryName(), fileName);
			logResultPassFilesEnded(formAction);
			logResult(fileName, id, "SUCCESS");
		} catch(Exception e) {
			logResultPassFilesError();
			logResult(fileName, id, "FAILURE");
			e.printStackTrace();
		}
		
	}

	private String getUnprocessedCsvEmailQueDirectoryName() {
		return AppSettings.getAppRealPath()+ "csv/emailQue/";
	}


	private String getProcessedCsvEmailQueDirectoryName() {
		return AppSettings.getAppRealPath()+ "csv/emailQue/processed/";
	}


	private void createRequiredEmailQueDirectories() {
		createRequiredDirectory(getUnprocessedCsvEmailQueDirectoryName());
		createRequiredDirectory(getProcessedCsvEmailQueDirectoryName());	
	}


	private void requestCsv(String unprocessedDir, String processedDir, String fileName) {
		//System.out.println("SP, requestCSV, step 1");
		File file = new File(unprocessedDir + "/" + fileName);
		StringBuffer sb = new StringBuffer();
		try {
			//System.out.println("SP, requestCSV, step 2");
	        BufferedReader in = new BufferedReader(new FileReader(file));
	        String str;
	        //System.out.println("SP, requestCSV, step 2");
	        while ((str = in.readLine()) != null) {
	        	 //System.out.println("SP, requestCSV, step 3");
	            sb.append(str + "\r\n");
	        }
	        in.close();

		    File dir = new File(processedDir);
		    boolean success = file.renameTo(new File(dir, file.getName()));
		    //System.out.println("SP, requestCSV, step 4");
		    // the above file move was successful delete unprocessed file.
		    if (success) {
		    	 //System.out.println("SP, requestCSV, step 5");
		    	file.delete();
		    }
		    //System.out.println("SP, requestCSV, step 6");
		    PrintWriter out = response.getWriter();
		    out.print(sb.toString());
		    out.close();
		    
	    } catch (IOException ioe) {
	    	ioe.printStackTrace();
	    }	
		
	}
	
	private void startEmailQueTransfer(String formAction) {
        try {
        	String datePrefix = DateUtilities.formatDate((new java.util.Date()), "yyyyMMdd");
            
        	String fileName = datePrefix + "_emailque_" + System.currentTimeMillis()+ ".csv";
        	//System.out.println("SP-startEmailQueTransfer, step 1");
			createEmailQueCsvFile(fileName);
			//System.out.println("SP-startEmailQueTransfer, step 2");
			notifyEmailQueCsvReady(fileName);
			//System.out.println("SP-startEmailQueTransfer, step 3");
			saveEmailQueueAudit(fileName);
			//System.out.println("SP-startEmailQueTransfer, step 4");
			
			forward("/jsp/public/emailQue/uploadResult.jsp");
        } catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveEmailQueueAudit(String fileName) {
		try{
			openConnection();
			
			UserProfile userProfile = (UserProfile)request.getSession().getAttribute("userProfile");
			//System.out.println("SP, userProfile - " + userProfile.getUserId());
			
			ProspectView sessionProspectView = (ProspectView)request.getSession().getAttribute("prospectView");
			ProspectDetails prospectDetails;
			for(int i = 0; i < sessionProspectView.getElements().size(); i++){
				prospectDetails = (ProspectDetails)sessionProspectView.getElement(i);
				EmailQueueAuditDetails emailQueueAuditDetails = new EmailQueueAuditDetails();
				EmailQueueAuditView emailQueueAuditView = new EmailQueueAuditView();
				
				emailQueueAuditDetails.setProspectId(prospectDetails.getProspectId());
				emailQueueAuditDetails.setExportDate(DateUtilities.getCurrentSQLTimestamp());
				emailQueueAuditDetails.setEmailDraftId(new BigDecimal(request.getParameter("dfEmailDraftId")));
				emailQueueAuditDetails.setUserId(userProfile.getUserId());
				emailQueueAuditDetails.setWebProfileId(new BigDecimal(1));// no longer required field
				emailQueueAuditDetails.setFileName(fileName);
				emailQueueAuditDetails.setStatus(EmailQueueAuditDetails.EXPORTED_TO_ORDERPORTAL);
			
				//System.out.println("emailQueueAuditDetails " + emailQueueAuditDetails.toString());
				
				emailQueueAuditView.doAction(connection, "INSERT", EmailQueueAuditDetails.FILL_TYPE_ALL, emailQueueAuditDetails);
				
			}
			

			

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			finallyMethod();
		}
		
	}


	private void createEmailQueCsvFile(String fileName) throws Exception {
		//System.out.println("do we get here 1, createEmailQueCsvFile");
        ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
        if (sessionProspectView != null) {
            writeCsvFile(sessionProspectView, fileName);            
        } else {
    		throw new Exception("Session expired. Please close the current browser window and reopen the application.");
        }
	}

	private void notifyEmailQueCsvReady(String fileName) {
		String id = "";
		String content = "";
		//System.out.println("do we get here 3, createEmailQueCsvFile");
		try {
			logResultnotifiedRetrieveCsvStarted(formAction);
			content = URLHandle.getURLContent(AppSettings.getParm("SALESPORTAL_AUTOMATED_EMAIL_LINK"),
						URLHandle.HTTP, URLHandle.GET, "formAction=NOTIFY_EMAIL_QUE_CSV_READY&id=" + id +"&fileName=" + fileName);
			//System.out.println("content=" + content);
			logResultnotifiedRetrieveCsvEnded(formAction);
			logResult(content, id, "SUCCESS");
		} catch(Exception e) {
			logResultnotifiedRetrieveCsvError();
			logResult(content, id, "FAILURE");
			e.printStackTrace();
		} 
	}

    private void logResult(String content, String id, String tag) {
    	try {
    		//System.out.println("LOG RESULTS   ID: " + id + "  CONTENT: "+ content + "  TAG: " + tag);
		} catch(Exception e) {
			e.getStackTrace();
//		} finally {
//            finallyMethod();
        }
	}
    
    
    /**
     * Method writeCsvFile.
     * @param prospectView ProspectView
     */
    private void writeCsvFile(ProspectView sessionProspectView, String fileName) {
        try {
        	//System.out.println("do we get here 2, createEmailQueCsvFile");
            String csvContent = getEmailQueCSVContent(sessionProspectView);
            String dirName = getUnprocessedCsvEmailQueDirectoryName();
            //System.out.println("do we get here 2.1, createEmailQueCsvFile");
            createRequiredDirectory(dirName);
            //System.out.println("do we get here 2.2, createEmailQueCsvFile");
            String prospectFileName = dirName + fileName;
            //System.out.println("value of csvContent= " + csvContent);
            //System.out.println("do we get here 2.3, createEmailQueCsvFile");
    		if(!csvContent.equals("")){
    			//System.out.println("do we get here 2.4, createEmailQueCsvFile");
                BufferedWriter out = new BufferedWriter(new FileWriter(prospectFileName));
                //System.out.println("do we get here 2.5, createEmailQueCsvFile");
                out.write(csvContent);
                out.close();
            }
    		
            //System.out.println("SAVED");
        } catch (Exception e) {
        	e.printStackTrace();
        	//System.out.println("DID NOT SAVE");
            doCatch(e);
//        } finally {
//            finallyMethod();
        }
    }
    
	private String getEmailQueCSVContent(ProspectView sessionProspectView) throws Exception {
    	StringBuffer csvContent = new StringBuffer();
        ProspectDetails details = null;
        //System.out.println(" in getEmailQueCSVContent, size of sessionProspectView= " + sessionProspectView.getElements().size());
    	for (int i = 0; i < sessionProspectView.getElements().size(); i++) {
        	details = (ProspectDetails) sessionProspectView.getElements().elementAt(i);
            if(details.getEmail() != null && !details.getEmail().equals("")){
            	csvContent.append(details.getEmail() + ",");//A = email
            	csvContent.append("Salesportal" + ",");//B = Category
            	csvContent.append(details.getCustomerCompany() + ",");//C= company
            	csvContent.append(details.getContactName() + ",");//D = Name
            	csvContent.append(details.getContactName() + ",");//E = Title
            	csvContent.append((String)request.getParameter("dfFromAddress") + ",");//F = from email
            	csvContent.append((String)request.getParameter("dfEmailDraftId"));//G = from email
            	csvContent.append("\n");
            	//System.out.println("csv content= " + csvContent.toString());
            }
        }
		return csvContent.toString();
	}

	
//	protected String getEmailQueFilename() {
//
//		createRequiredDirectory("C:" + "/" + "Documents and Settings" + "/" + "Michael Croteau" + "/"+ "Desktop" + "/mike123/");
//		return "C:" + "/" + "Documents and Settings" + "/" + "Michael Croteau" + "/"+ "Desktop" + "/emailque" + System.currentTimeMillis() + ".csv";
////		return  appOrderportalSettings.getParm("ORDER_IMPORT_FILE_PREFIX", webProfileFile) + "_" + System.currentTimeMillis() + ".csv";
//	}


    
	protected void createRequiredDirectory(String directory) {
		File dir = new File(directory);
		//System.out.println("dirname = " + directory);
		//System.out.println("dir = " + dir.isDirectory());
		if(!dir.isDirectory()) {
			//System.out.println("making dirs");
			dir.mkdirs();
		}
		//System.out.println("made dir");
	}
	
	private void logResultnotifiedRetrieveCsvStarted(String formAction) throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
		migrationLogDetails.setMigrationId(MigrationLogDetails.MIGRATION_ID_EMAIL_QUEUE_TRANSFER_CONTROLLER);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_NOTIFY);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_EMAIL_QUE_TRANSFER_FORMACTION + formAction);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_NOTIFY_STARTED);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_NOTIFY);//succes or failure
		migrationLogDetails.setMigrationResult("Timer started");
		logResult(migrationLogDetails);

	} 
	

	private void logResultnotifiedRetrieveCsvEnded(String formAction) throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
    	migrationLogDetails.setMigrationId(MigrationLogDetails.MIGRATION_ID_EMAIL_QUEUE_TRANSFER_CONTROLLER);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_NOTIFY);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_EMAIL_QUE_TRANSFER_FORMACTION + formAction);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_NOTIFY_ENDED);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_SUCCESS);//succes or failure
		migrationLogDetails.setMigrationResult("Timer process is completed");
		logResult(migrationLogDetails);

	} 
	private void logResultnotifiedRetrieveCsvError() {
		try{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
    	migrationLogDetails.setMigrationId(MigrationLogDetails.MIGRATION_ID_EMAIL_QUEUE_TRANSFER_CONTROLLER);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_NOTIFY);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_EMAIL_QUE_TRANSFER_FORMACTION);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_NOTIFY_ERROR);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_FAILURE);//succes or failure
		migrationLogDetails.setMigrationResult("There was an error in this timer job");
		logResult(migrationLogDetails);
		}catch(Exception e) {
			e.printStackTrace();
		}

	} 
	
/*	private void logResultGetFilesCsvStarted(String formAction) throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
		migrationLogDetails.setMigrationId(MigrationLogDetails.MIGRATION_ID_EMAIL_QUEUE_TRANSFER_CONTROLLER);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_GET_FILES);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_EMAIL_QUE_TRANSFER_FORMACTION + formAction);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_GET_FILES_STARTED);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_NOTIFY);//succes or failure
		migrationLogDetails.setMigrationResult("Timer started");
		logResult(migrationLogDetails);

	} 
	

	private void logResultGetFilesCsvEnded(String formAction) throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
    	migrationLogDetails.setMigrationId(MigrationLogDetails.MIGRATION_ID_EMAIL_QUEUE_TRANSFER_CONTROLLER);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_GET_FILES);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_EMAIL_QUE_TRANSFER_FORMACTION + formAction);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_GET_FILES_ENDED);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_SUCCESS);//succes or failure
		migrationLogDetails.setMigrationResult("Timer process is completed");
		logResult(migrationLogDetails);

	} 
	private void logResultGetFilesCsvError() {
		try{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
    	migrationLogDetails.setMigrationId(MigrationLogDetails.MIGRATION_ID_EMAIL_QUEUE_TRANSFER_CONTROLLER);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_GET_FILES);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_EMAIL_QUE_TRANSFER_FORMACTION);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_GET_FILES_ERROR);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_FAILURE);//succes or failure
		migrationLogDetails.setMigrationResult("There was an error in this timer job");
		logResult(migrationLogDetails);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}*/ 
	
	private void logResultPassFilesStarted(String formAction) throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
		migrationLogDetails.setMigrationId(MigrationLogDetails.MIGRATION_ID_EMAIL_QUEUE_TRANSFER_CONTROLLER);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_PASSES_FILES);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_EMAIL_QUE_TRANSFER_FORMACTION + formAction);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_PASSES_FILES_STARTED);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_NOTIFY);//succes or failure
		migrationLogDetails.setMigrationResult("Timer started");
		logResult(migrationLogDetails);

	} 
	

	private void logResultPassFilesEnded(String formAction) throws Exception{
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
    	migrationLogDetails.setMigrationId(MigrationLogDetails.MIGRATION_ID_EMAIL_QUEUE_TRANSFER_CONTROLLER);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_PASSES_FILES);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_EMAIL_QUE_TRANSFER_FORMACTION + formAction);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_PASSES_FILES_ENDED);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_SUCCESS);//succes or failure
		migrationLogDetails.setMigrationResult("Timer process is completed");
		logResult(migrationLogDetails);

	} 
	private void logResultPassFilesError() {
		try{
		
    	MigrationLogDetails migrationLogDetails = new MigrationLogDetails();
    	migrationLogDetails.setMigrationId(MigrationLogDetails.MIGRATION_ID_EMAIL_QUEUE_TRANSFER_CONTROLLER);
		migrationLogDetails.setMigrationTimestamp(new Timestamp(System.currentTimeMillis()));
		migrationLogDetails.setApplication(MigrationLogDetails.APPLICATION_EMAIL_QUE_TRANSFER_CONTROLLER_PASSES_FILES);
		migrationLogDetails.setMigrationType(MigrationLogDetails.TYPE_EMAIL_QUE_TRANSFER_FORMACTION);
		migrationLogDetails.setMigrationSubType(MigrationLogDetails.SUB_TYPE_EMAIL_QUE_TRANSFER_CONTROLLER_PASSES_FILES_ERROR);
		migrationLogDetails.setMigrationTag(MigrationLogDetails.TAG_FAILURE);//succes or failure
		migrationLogDetails.setMigrationResult("There was an error in this timer job");
		logResult(migrationLogDetails);
		}catch(Exception e) {
			e.printStackTrace();
		}

	} 
	
	 private void logResult(MigrationLogDetails migrationLogDetails) throws SQLException{
		 try{
			 
			openConnection();
		    StringBuffer sb = new StringBuffer();
			Statement statement = connection.createStatement();
			System.out.println("before write to migration_sp log");
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
			System.out.println("migration Log statement = " + sb.toString());		
			statement.executeUpdate(sb.toString()); 
			statement.close();		
		 }catch(Exception e){
		  		e.printStackTrace();
		}finally{
			finallyMethod();
		}
		}
	 
}
