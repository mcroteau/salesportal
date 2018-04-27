package com.randr.webdw.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import com.randr.webdw.AppSettings;
import com.randr.webdw.company.CompanyDetails;
import com.randr.webdw.company.CompanyView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.util.EmailUtilities;
import com.randr.webdw.util.URLHandle;
import com.randr.webdw.util.Utilities;

public class ApplicationStartupWizardServlet extends HttpServlet implements ApplicationConstants{
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private String formAction = null;
 
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)  {
		//see ant folder in WEB-INF
		this.request = req;
		this.response = resp;
		this.formAction = request.getParameter("formAction");
		
		if (formAction.equals("DISPLAY_APPLICATION_CONTEXT")) {
			displayApplicationContext();
		}else if (formAction.equals("SAVE_APPLICATION_CONTEXT")) {
			saveApplicationContext();
		}else if (formAction.equals("DISPLAY_DATABASE_SETTINGS")) {
			displayDatabaseSettings();
		} else if(formAction.equals("SAVE_DATABASE_SETTINGS")) {
			saveDatabaseSettings();
		} else if(formAction.equals("SAVE_COMPANY_INFO")) {
			saveCompanyInfo();
		} else if(formAction.equals("SAVE_EMAIL_SETTINGS")) {
			saveEmailSettings();		
		} else if(formAction.equals("REGISTER")) {			
			register();
		}else if(formAction.equals("CONTACT_SUPPORT")){
			displayContactSupport();
		}else if(formAction.equals("EMAIL_DETAIL_INFO")){
			displayEmailDetailInfo();
		}else if(formAction.equals("LOAD_APP_LOGIN")){
			loadApplicationLogin();
		}else if(formAction.equals("RESET_INSTALL")){
			resetInstall();
		}
	}

	private void loadApplicationLogin() {
		try{
			request.getSession().getServletContext().removeAttribute(MESSAGE_NAME);
			AppSettings.setParm("INITIAL_STARTUP_PERFORMED", "YES");
//			AppSettings.outputConfigDocumentToXMLFileBackup();
			AppSettings.outputConfigDocumentToXMLFile();
			forward("/jsp/public/user/login.jsp");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void resetInstall(){
		System.out.println("RESET INSTALL");
		try{
			request.getSession().getServletContext().setAttribute("MESSAGE_NAME", MESSAGE_NAME);
			AppSettings.setParm("INITIAL_STARTUP_PERFORMED", "NO");
			AppSettings.outputConfigDocumentToXMLFile();
			forward("/jsp/startup/welcome.jsp");
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	private void displayApplicationContext() {
		try {
			forward("/jsp/startup/displayApplicationContext.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void saveApplicationContext() {
		String successMessage = "";
		String errorMessage = "";
		String stackTrace = "";
		try {
			String applicationContext = request.getParameter("dfApplicationContext");
			String portNumber = request.getParameter("dfPortNumber");
			
			File buildFile = new File(AppSettings.getAppRealPath()+"/WEB-INF/ant/configuration.xml");
			Project p = new Project();
			p.setUserProperty("ant.file", buildFile.getAbsolutePath());		
			DefaultLogger consoleLogger = new DefaultLogger();
			consoleLogger.setErrorPrintStream(System.err);
			consoleLogger.setOutputPrintStream(System.out);
			consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
			p.addBuildListener(consoleLogger);

			try {
//				p.fireBuildStarted();
				AppSettings.setParm("APPLICATION_CONTEXT", applicationContext);
				AppSettings.setParm("PORT_NUMBER", portNumber);
				AppSettings.outputConfigDocumentToXMLFile();
				
				p.setProperty("application_context", applicationContext);
				p.setProperty("port_number", portNumber);
				
				p.init();
				ProjectHelper helper = ProjectHelper.getProjectHelper();
				p.addReference("ant.projectHelper", helper);
				helper.parse(p, buildFile);
				p.executeTarget("save_application_context");
				
				successMessage = "Successfully set Application Context";
				
			} catch (BuildException e) {
            	e.printStackTrace();
                
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                
                errorMessage= errorMessage + "\nProblem with saving new Application Context.  Please try again";
                stackTrace = sw.toString();

			}
	
	
			request.setAttribute("successMessage", successMessage);
			request.setAttribute("errorMessage", errorMessage);
			request.setAttribute("stackTrace", stackTrace);

			AbstractController.reloadAppSettings(getServletContext(), request);
			System.out.println("LOCALURL="+AppSettings.getParm("LOCAL_COMPLETE_URL"));
			
			if(stackTrace.equals("")){
//				forward("/jsp/startup/displayApplicationContext.jsp");
				forward("/jsp/startup/displayDatabaseSettings.jsp");
			}else{
				forward("/jsp/startup/displayApplicationContext.jsp");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void displayEmailDetailInfo() {
		try {
			forward("/jsp/startup/emailDetailInfo.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}


	private void displayContactSupport() {
		try {
			forward("/jsp/startup/support.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}


	private void validateEmailConnection() throws MessagingException {
	
        String from = request.getParameter("dfEmailSender");
        String relayUser = request.getParameter("dfUser");
        String relayPassword = request.getParameter("dfPassword");
        String relayHost = request.getParameter("dfHost");
        String subject = "Sale Portal Setup Confimation";
        String body = "<style>body, table {font-family:arial,verdana,tahoma;font-size:10pt} h1{font-size:12pt;}</style>";
        body += "<h1>Sales portal Setup Confirmation Email</h1>";
        
//        boolean mailSent = false;
        if(request.getParameter("cmbAuthentication").equals("true")){
            EmailUtilities.sendMailWithLogging(relayHost, relayUser, relayPassword,
            		from, request.getParameter("dfTestEmail"), 
            		subject, body, EmailUtilities.TYPE_HTML);   
//            mailSent = EmailUtilities.sendMail(relayHost, relayUser, relayPassword,
//            		from, request.getParameter("dfTestEmail"), 
//            		subject, body, EmailUtilities.TYPE_HTML);       	
        }else{
        	EmailUtilities.sendMailWithLogging(from, request.getParameter("dfTestEmail"), 
        			relayHost, subject, body.toString());  
//        	mailSent = EmailUtilities.sendMail(from, request.getParameter("dfTestEmail"), 
//        			relayHost, subject, body.toString());   
        }
//        if(!mailSent){
//        	request.setAttribute("errorMessage", "Email could not be sent. " + "\nPlease Examine your email and your test email address.");
//        }
//        return mailSent;
	}


	private void displayFinish() throws Exception{
		forward("/jsp/startup/displayFinish.jsp");
	}


	private void register() {
		String errorMessage = "";
		String stackTrace = "";	
		
		try {	

			if(request.getParameter("skip").equals("TRUE")){
				System.out.println("skipped registration");
				request.setAttribute("successMessage", "You have successfully completed the installation.  Click Continue to Proceed to your Sales Portal application to Log In.");
				displayFinish();
			}else{
				String urlPath1 = "http://www.randrinc.com:8180/datamigrationportal/receive_message";		 
				String urlParms1 = "formAction=REGISTRATION";	
				
				Enumeration en = request.getParameterNames();
				while (en.hasMoreElements()) {
				  String paramName = (String) en.nextElement();
				  String paramValue = request.getParameter(paramName);
				  
				  if(!paramName.equals("formAction")){
					  urlParms1 = urlParms1 + "&" + paramName + "="
					           + URLEncoder.encode(paramValue);
	
				  }
				}
				urlParms1 = urlParms1 + "&portalRegistered="+URLEncoder.encode("2D Sales Portal");
				URLHandle.submitURL(urlPath1, URLHandle.HTTP, URLHandle.GET, urlParms1);
				
				request.setAttribute("successMessage", "Thank you for Registering with us! <br/>You have successfully completed the installation.  Click Continue to Proceed to your Sales Portal application to Log In.");
				
				displayFinish();
				
			}
		}catch(Exception e){
        	e.printStackTrace();
            
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));

            errorMessage= errorMessage + "\nSettings are incorrect.  Please check your settings and try again.  Below are more details on the error.";
            stackTrace = sw.toString();
            
			request.setAttribute("errorMessage", errorMessage);
			request.setAttribute("stackTrace", stackTrace);
			displayRegistration();
		}
		
	}


	private void displayRegistration() {
		try {
			forward("/jsp/startup/displayRegistration.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}


	private void saveEmailSettings() {
		String errorMessage = "";
		String stackTrace = "";
	

		try{
			if(request.getParameter("skip").equals("TRUE")){
				displayRegistration();
			}else{
				validateEmailConnection();
				saveEmailConfigSettings();

				request.setAttribute("successMessage", "Successfully sent test email.  Settings are correct and saved.");			

				displayRegistration();
			}

		}catch(Exception e){
        	e.printStackTrace();
            
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));

            errorMessage= errorMessage + "\nSettings are incorrect.  Please check your setting and try again.  Below are more details on the error.";
            stackTrace = sw.toString();
            
			request.setAttribute("errorMessage", errorMessage);
			request.setAttribute("stackTrace", stackTrace);

			displayEmailSettings();

		}

	}


	private void saveEmailConfigSettings() {
		AppSettings.setParm("SMTP_RELAY_HOST", request.getParameter("dfHost"));
		AppSettings.setParm("SMTP_USER", request.getParameter("dfUser"));
		AppSettings.setParm("SMTP_PASSWORD", request.getParameter("dfPassword"));
		AppSettings.setParm("AUTOMATED_EMAIL_SENDER", request.getParameter("dfEmailSender"));
		AppSettings.setParm("SALES_ACTION_EMAIL_ENABLED", request.getParameter("cmbEmailEnabled"));
		AppSettings.setParm("SALES_ACTION_EMAIL_SCHEDULE_MINUTES", request.getParameter("cmbCheckInterval"));
		AppSettings.setParm("SALES_ACTION_EMAIL_BEFORE_DAYS", request.getParameter("cmbDueDate"));
		AppSettings.setParm("SMTP_AUTHENTICATION", request.getParameter("cmbAuthentication"));
		AppSettings.outputConfigDocumentToXMLFile();
	}

	private void displayEmailSettings() {
		if(Utilities.nullToBlank((String)request.getAttribute("errorMessage")).equals("")) {
			request.setAttribute("successMessage", "Company Info successfully updated.");
		}	
		try {
			forward("/jsp/startup/displayEmailSettings.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}


	private void saveCompanyInfo() {
		String successMessage = "";
		String errorMessage = "";
		String stackTrace = "";
		try{
			AppSettings.setParm("COMPANY_NAME", request.getParameter("dfCompanyName"));
			AppSettings.setParm("COMPANY_ADDRESS", request.getParameter("dfAddress"));
			AppSettings.setParm("COMPANY_CITY", request.getParameter("dfCity"));
			AppSettings.setParm("COMPANY_STATE", request.getParameter("dfState"));
			AppSettings.setParm("COMPANY_STATE_ABREV", request.getParameter("dfStateAbb"));
			AppSettings.setParm("COMPANY_ZIP", request.getParameter("dfZip"));
			AppSettings.setParm("COMPANY_PHONE", request.getParameter("dfPhone"));
			AppSettings.setParm("COMPANY_FAX", request.getParameter("dfFax"));
			AppSettings.setParm("CONTACT_EMAIL", request.getParameter("dfEmail"));
			AppSettings.outputConfigDocumentToXMLFile();
			successMessage = "Successfully saved Company Information";

			request.setAttribute("successMessage", successMessage);
			displayEmailSettings();
			
		} catch (Exception e) {
	    	e.printStackTrace();
	        
	        StringWriter sw = new StringWriter();
	        e.printStackTrace(new PrintWriter(sw));
	        
	        errorMessage= errorMessage + "\nProblem saving Company Information.  Please try again";
	        stackTrace = sw.toString();

			request.setAttribute("errorMessage", errorMessage);
			request.setAttribute("stackTrace", stackTrace);
			displayCompanyInfo();
	
		}
		
	}

	private void displayDatabaseSettings() {
		try {
			forward("/jsp/startup/displayDatabaseSettings.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void saveDatabaseSettings() {
		boolean errors = false;
		String errorMessage = "";
		String stackTrace = "";
		Connection connectionTemplate1 = null;
		Connection connectionNewDatabase = null;
		try{
			connectionTemplate1 = getConnection("template1");
			createDatabase(connectionTemplate1);
			connectionTemplate1.close();


			connectionNewDatabase = getConnection(request.getParameter("dfDatabaseName"));
			createDatabaseSchema(connectionNewDatabase);
			connectionNewDatabase.close();
			
			
			saveDatabaseConfigSettings();

			checkCloseDatabaseConnection(connectionTemplate1);
			checkCloseDatabaseConnection(connectionNewDatabase);
			
			request.setAttribute("successMessage", "Successfully created Sales Portal database : " + request.getParameter("dfDatabaseName"));
			
		}catch (Exception e) {
			try {
				checkCloseDatabaseConnection(connectionTemplate1);
				checkCloseDatabaseConnection(connectionNewDatabase);
				Connection connectionDropDb = getConnection("template1");
				dropDatabase(connectionDropDb);	
			} catch (Exception e1) {
				System.out.println("catching e1");
				log("install wizard : database was never created, drop database failed");
			}
			errors = true;
			errorCatchIt(e, stackTrace, errorMessage);		
		}
		
		if(errors){	
			displayDatabaseSettings();		
		}else{
			displayCompanyInfo();
		}
		
	}
	
	private void createDatabaseSchema(Connection connection) throws Exception {
		createTableScript(connection);
		createAlterScript(connection);
		createIndexScript(connection);
		createInitialDataScript(connection);
	}
	
	
	private Connection getConnection(String databaseName) throws ClassNotFoundException, SQLException {
		
		Class.forName(request.getParameter("cmbDatabaseDriver"));
		Connection connection = DriverManager.getConnection( "jdbc:postgresql://"
                +  request.getParameter("dfIPAddress") + ":5432/"
                + databaseName, request.getParameter("dfUserName"), 
                request.getParameter("dfPassword"));

		return connection;
	}
		
	private void checkCloseDatabaseConnection(Connection connection) throws SQLException {
		if(connection !=null && !connection.isClosed()){
			connection.close();
		}
		
	}
	
	private void errorCatchIt(Exception e, String stackTrace, String errorMessage) {
		e.printStackTrace();
        
		if(e.getMessage().indexOf("Stack Trace:")!=-1){
			errorMessage = e.getMessage().substring(0, e.getMessage().indexOf("Stack Trace:"));
		}else{
			errorMessage = e.getMessage();
		}
        
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        
        stackTrace = sw.toString();
        
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("stackTrace", stackTrace);
	}
	
	private void dropDatabase(Connection connection) throws Exception {
		String deleteDatabase = "DROP DATABASE " + request.getParameter("dfDatabaseName") + ";";
		Statement statement = connection.createStatement();
		statement.execute(deleteDatabase);
		connection.close();
	}
	
	

	private void saveDatabaseConfigSettings() {
		AppSettings.setParm("DB_USER", request.getParameter("dfUserName"));
		AppSettings.setParm("DB_PASSWORD", request.getParameter("dfPassword"));
		AppSettings.setParm("DB_TYPE", request.getParameter("cmbDatabaseType"));
		AppSettings.setParm("DB_DRIVER", request.getParameter("cmbDatabaseDriver"));
		AppSettings.setParm("DB_NAME", request.getParameter("dfDatabaseName"));
		AppSettings.setParm("DB_SYSTEM_IP", request.getParameter("dfIPAddress"));
		AppSettings.outputConfigDocumentToXMLFile();
	}

	private void createDatabase(Connection connection) throws SQLException {
		Statement statement;

		statement = connection.createStatement();
		String sql = "CREATE DATABASE " + request.getParameter("dfDatabaseName") + ";";
		statement.execute(sql);
	
	}

	
	private void createDatabaseSchema() {
		Connection connection = null;
		String creationStep = TABLE_SCRIPT;
		
		try{
			Class.forName(request.getParameter("cmbDatabaseDriver"));

			connection = DriverManager.getConnection( "jdbc:postgresql://"
	                +  request.getParameter("dfIPAddress") + ":5432/"
	                + request.getParameter("dfDatabaseName"), request.getParameter("dfUserName"), 
	                request.getParameter("dfPassword"));
			createTableScript(connection);
			creationStep = ALTER_SCRIPT;
			createAlterScript(connection);
			creationStep = INDEX_SCRIPT;
			createIndexScript(connection);
			creationStep = INITIAL_DATA_SCRIPT;
			createInitialDataScript(connection);
			connection.close();
			
		}catch (Exception e){
			
			String errorMessage = "";
			if(e.getMessage().indexOf("Stack Trace:")!=-1){
				errorMessage = e.getMessage().substring(0, e.getMessage().indexOf("Stack Trace:"));
			}else{
				errorMessage = e.getMessage();
			}			
			request.setAttribute("errorMessage", "Database script incorrect or corrupt.  Please check file " + creationStep + " for : " + errorMessage);

			try {
				if(connection !=null && !connection.isClosed()){
					connection.close();
				}			
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			dropDatabase();
			displayDatabaseSettings();
		}	
	}


	private void dropDatabase() {
		Connection connection = null;
		try {
			Class.forName(request.getParameter("cmbDatabaseDriver"));
			connection = DriverManager.getConnection( "jdbc:postgresql://"
	                +  request.getParameter("dfIPAddress") + ":5432/"
	                + "template1", request.getParameter("dfUserName"), 
	                request.getParameter("dfPassword"));
			String deleteDatabase = "DROP DATABASE " + request.getParameter("dfDatabaseName") + ";";
			Statement statement = connection.createStatement();
			statement.execute(deleteDatabase);
			connection.close();
		} catch (Exception e) {
			try {
				if(connection !=null && !connection.isClosed()){
					connection.close();
				}				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}		
	}


	private void createInitialDataScript(Connection connection) throws IOException, SQLException {
		Statement statement;
		String tableScript = AppSettings.getAppRealPath() + DATABASE_CREATION_FOLDER + "/" + INITIAL_DATA_SCRIPT;

        FileInputStream fileReader;
		fileReader = new java.io.FileInputStream(tableScript);

        byte[] fileBytes = new byte[fileReader.available()];
        fileReader.read(fileBytes);
        fileReader.close();
        String fileContent = new String(fileBytes);
        
        statement = connection.createStatement();
		statement.execute(fileContent);
		
	}


	private void createIndexScript(Connection connection) throws IOException, SQLException {
		Statement statement;
		String tableScript = AppSettings.getAppRealPath() + DATABASE_CREATION_FOLDER + "/" + INDEX_SCRIPT;

        FileInputStream fileReader;
		fileReader = new java.io.FileInputStream(tableScript);

        byte[] fileBytes = new byte[fileReader.available()];
        fileReader.read(fileBytes);
        fileReader.close();
        String fileContent = new String(fileBytes);
        
        statement = connection.createStatement();
		statement.execute(fileContent);
		
	}
	


	private void createAlterScript(Connection connection) throws IOException, SQLException {
		Statement statement;
		String tableScript = AppSettings.getAppRealPath() + DATABASE_CREATION_FOLDER + "/" + ALTER_SCRIPT;

        FileInputStream fileReader;
		fileReader = new java.io.FileInputStream(tableScript);

        byte[] fileBytes = new byte[fileReader.available()];
        fileReader.read(fileBytes);
        fileReader.close();
        String fileContent = new String(fileBytes);
        
        statement = connection.createStatement();
		statement.execute(fileContent);
		
	}


	private void createTableScript(Connection connection) throws IOException, SQLException {
		Statement statement;
		String tableScript = AppSettings.getAppRealPath() + DATABASE_CREATION_FOLDER + "/" + TABLE_SCRIPT;

        FileInputStream fileReader;
		fileReader = new java.io.FileInputStream(tableScript);

        byte[] fileBytes = new byte[fileReader.available()];
        fileReader.read(fileBytes);
        fileReader.close();
        String fileContent = new String(fileBytes);
        
        statement = connection.createStatement();
		statement.execute(fileContent);
	}	
	
	
	private void displayCompanyInfo() {
		try {
			forward("/jsp/startup/displayCompanyInfo.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {
		doGet(req, resp);
	}
	
    private void forward(String path) throws Exception {
    	request.getRequestDispatcher(path).forward(request, response);
    }

}
