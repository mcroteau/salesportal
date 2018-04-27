package com.randr.webdw.configurations;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.util.EmailUtilities;

/**
 */
public class ConfigurationsController extends AbstractController {

    /**
     * Constructor for ConfigurationsController.
     */
    public ConfigurationsController() {
    }

    /**
     * Method doAction.
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    public void doAction()
            throws java.io.IOException, javax.servlet.ServletException {
    	if (isAdmin()){
    		redirectIfNotValidAdminUserProfile();
    	}
        if ((formAction == null) || (formAction.equals("DISPLAY"))) {
            request.getRequestDispatcher("/jsp/configurations/home.jsp").forward(request, response);
        } else if (formAction.equals("DISPLAY_UPDATE_APPLICATION")) {
        	request.getRequestDispatcher("/jsp/configurations/displayUpdateApplicationOptions.jsp").forward(request, response); 
        } else if ( formAction.equals("DISPLAY_UPDATE_DMP1") ) {
        	request.getRequestDispatcher("/jsp/configurations/displayUpdateDMP1Options.jsp").forward(request, response); 
        } else if ( formAction.equals("DISPLAY_UPDATE_IMPORT1") ) {
        	request.getRequestDispatcher("/jsp/configurations/displayUpdateImport1Options.jsp").forward(request, response); 
        } else if ( formAction.equals("DISPLAY_UPDATE_EMAIL1") ) {
        	request.getRequestDispatcher("/jsp/configurations/displayUpdateEmailRoundTripOptions.jsp").forward(request, response); 	        	
        } else if (formAction.startsWith("UPDATE")) {
            saveConfigurations();
        } else if (formAction.equals("DISPLAY_EDITOR")) {
            request.getRequestDispatcher("/jsp/configurations/displayEditor.jsp").forward(request,
                                                                                          response);
        } else if (formAction.equals("DISPLAY_EKIT_EDITOR")) {
            request.getRequestDispatcher("/jsp/configurations/displayEkitEditor.jsp").forward(request,
                                                                                              response);
        } else if (formAction.equals("SEND_TEST_EMAIL")) {
        	sendTestEmail();
        } else if (formAction.equals("SEND_TEST_COMMUNICATION_DATAMIGRATION")) {
        	sendTestCommunicationDatamigration();
        } 
    }

    /**
     * check that the link to data migration portal is good
     */
    private void sendTestCommunicationDatamigration() {
    	String confirmationStatus;
    	try{
//    		URL url = new URL("http://localhost:8080/dm_seg/request?formAction=TEST_SALESPORTAL_CONNECTION");
    		URL url = new URL(AppSettings.getParm("DATA_MIGRATION_PROSPECT_CSV_LINK")+"?formAction=TEST_SALESPORTAL_CONNECTION");
    				//AppSettings.getParm("DATA_MIGRATION_PROSPECT_CSV_SERVLET")+"?formAction=TEST_SALESPORTAL_CONNECTION");
    		HttpURLConnection con = (HttpURLConnection) url.openConnection();

			if(con.getResponseCode() == HttpURLConnection.HTTP_OK){	
	    		confirmationStatus = "<p id=\"success\">Successful in contacting Datamigration Portal</p>";
    		}else{
    			confirmationStatus = "<p id=\"failed\">Failed to connect to Datamigration Portal</p>";
    		}

    		request.setAttribute("confirmationStatus", confirmationStatus);
    		
    		forward("/jsp/configurations/displayUpdateDMP1Options.jsp");
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		try {
    			confirmationStatus = "<p id=\"failed\">Failed to connect to Datamigration Portal</p>";
    			request.setAttribute("confirmationStatus", confirmationStatus);
        		
				forward("/jsp/configurations/displayUpdateDMP1Options.jsp");
			
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    		
    	}
		
	}

	private void sendTestEmail() {
      
    	//FOR FUTURE RELEASE... NEED TO DEBUG
        String subject = "TEST EMAIL FROM SALES PORTAL";  
        String from = "";
        String to = "";
        String relayUser = "";
        String relayPassword = "";
        String relayHost = "";
        String body = "";
        
        Enumeration paramEnumeration = request.getParameterNames();
        String paramName = null;

        while (paramEnumeration.hasMoreElements()) {
            paramName = (String) paramEnumeration.nextElement();

            if (paramName.equals("OPT_AUTOMATED_EMAIL_SENDER")) {
            	from = request.getParameter("OPT_AUTOMATED_EMAIL_SENDER");
            } 
            if (paramName.equals("OPT_SMTP_USER")) {
            	relayUser = request.getParameter("OPT_SMTP_USER");
            }
            if (paramName.equals("OPT_SMTP_PASSWORD")) {
            	relayPassword = request.getParameter("OPT_SMTP_PASSWORD");
            } 
            if (paramName.equals("OPT_SMTP_RELAY_HOST")) {
            	relayHost = request.getParameter("OPT_SMTP_RELAY_HOST");
            }
            if (paramName.equals("dfEmailAddress")) {
            	to = request.getParameter("dfEmailAddress");
            } 
            if (paramName.equals("OPT_SALES_ACTION_BODY_TEXT")) {
            	body = request.getParameter("OPT_SALES_ACTION_BODY_TEXT");
            }
            
        }
        System.out.println("from - " + from);
        System.out.println("relayUser - " + relayUser);
        System.out.println("relayPassword  - " + relayPassword);
        System.out.println("relayHost - " + relayHost);
        System.out.println("subject - " + subject);
        System.out.println("to - " + to);
        System.out.println("body - " + body);
        
        EmailUtilities.sendMail(from, to, relayHost, subject, body);
	}

	/**
     * Method saveConfigurations.
     */
    private void saveConfigurations() {
        try {
            Enumeration paramEnumeration = request.getParameterNames();
            String paramName = null;

            while (paramEnumeration.hasMoreElements()) {
                paramName = (String) paramEnumeration.nextElement();

                if (paramName.startsWith("OPT_")) {
                    AppSettings.setParm(paramName.substring(4), request.getParameter(paramName));
                } else if (paramName.startsWith("CB_")) {
                    AppSettings.setParm(paramName.substring(3), request.getParameter(paramName));
                }
            }
            AppSettings.outputConfigDocumentToXMLFile();
            request.getRequestDispatcher("/jsp/configurations/updateConfigurations.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
        }
    }

}