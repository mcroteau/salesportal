package com.randr.webdw.startup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.randr.webdw.AppSettings;

public class ApplicationStartupUpdateWizardServlet  extends HttpServlet implements ApplicationConstants{
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private String formAction = null;
 
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)  {
		this.request = req;
		this.response = resp;
		this.formAction = request.getParameter("formAction");
		
		if (formAction.equals("DISPLAY_WIZARD_1_UPDATE_DATABASE")) {
			displayUpdateDatabase();
		} else if(formAction.equals("CHECK_DATABASE_SETTINGS")) {
			if(validDatabaseConnection()){
				request.setAttribute("databaseConnectionSuccess", "SUCCESS");
			}
			displayUpdateDatabase();
		}  else if(formAction.equals("UPDATE_DATABASE")) {
			if(validDatabaseConnection()){
				updateDatabase();
			}
			displayUpdateDatabase();
//			displayUpdateCompanyInfo();
		} 

	}


	private void displayUpdateCompanyInfo() {
		// TODO Auto-generated method stub
		
	}


	private void updateDatabase() {
		Connection connection = null;
		try{
		connection = DriverManager.getConnection( "jdbc:postgresql://"
                +  request.getParameter("dfIPAddress") + ":5432/"
                + request.getParameter("dfDatabaseName"), request.getParameter("dfUserName"), 
                request.getParameter("dfPassword"));
		
		connection.close();
		String updateScript = getUpdateScript();
		System.out.println("updateScript = " + updateScript.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	private String getUpdateScript() throws IOException {
		String updateScript;
        int release = Integer.parseInt(request.getParameter("release"));
        switch (release) {
            case 1: updateScript = UPDATE_FROM_1_0; break;
            case 2: updateScript = UPDATE_FROM_1_1; break;
            case 3: updateScript = UPDATE_FROM_1_1_1; break;
            case 4: updateScript = UPDATE_FROM_1_1_2; break;
            case 5: updateScript = UPDATE_FROM_1_1_3; break;
            case 6: updateScript = UPDATE_FROM_1_1_4; break;
            case 7: updateScript = UPDATE_FROM_1_2_0; break;
            case 8: updateScript = UPDATE_FROM_1_2_3; break;
            case 9: updateScript = UPDATE_FROM_1_2_5; break;
            default: updateScript = ""; break;
        }
		String tableScript = AppSettings.getAppRealPath() + DATABASE_UPDATE_FOLDER + "/" + updateScript;

        FileInputStream fileReader;
		fileReader = new java.io.FileInputStream(tableScript);

        byte[] fileBytes = new byte[fileReader.available()];
        fileReader.read(fileBytes);
        fileReader.close();
        String fileContent = new String(fileBytes);
		return fileContent;
	}


	private void displayUpdateDatabase() {
		try{	
			forward("/jsp/startup/displayWizardOneUpdateDatabase.jsp");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private boolean validDatabaseConnection() {
		Connection connection = null;
		try{
			Class.forName(request.getParameter("cmbDatabaseDriver"));
			connection = DriverManager.getConnection( "jdbc:postgresql://"
	                +  request.getParameter("dfIPAddress") + ":5432/"
	                + request.getParameter("dfDatabaseName"), request.getParameter("dfUserName"), 
	                request.getParameter("dfPassword"));

			connection.close();
			return true;
		}catch (Exception e) {
			String error = "";
			if(e.getMessage().indexOf("Stack Trace:")!=-1){
				error = e.getMessage().substring(0, e.getMessage().indexOf("Stack Trace:"));
			}else{
				error = e.getMessage();
			}			
			request.setAttribute("databaseConnectionError", error);
			try {
				if(connection !=null && !connection.isClosed()){
					connection.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}
	
    private void forward(String path) throws Exception {
    	request.getRequestDispatcher(path).forward(request, response);
    }
}
