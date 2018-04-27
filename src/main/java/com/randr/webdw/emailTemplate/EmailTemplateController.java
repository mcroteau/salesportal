package com.randr.webdw.emailTemplate;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.util.DateUtilities;


/**
 */
public class EmailTemplateController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	if (isAdmin()){
    		//redirectIfNotValidAdminUserProfile();
    	}
        if ((formAction == null) || (formAction.equals("DISPLAY"))) {
            displayEmailTemplates();
        } else if (formAction.equals("INSERT")) {
        	insertEmailTemplate();
        } else if (formAction.equals("UPDATE")) {
            updateEmailTemplate();
        } else if (formAction.equals("DELETE")) {
            deleteEmailTemplate();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertEmailTemplate();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateEmailTemplate();
        } else if (formAction.equals("DISPLAY_DELETE")) {
            displayDeleteEmailTemplate();
        }
    }

    /**
     * Method displayInsertEmailTemplate.
     */
    private void displayInsertEmailTemplate() {
        try {
            request.getRequestDispatcher("/jsp/shared/emailTemplate/displayInsertUpdateEmailTemplate.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateEmailTemplate.
     */
    private void displayUpdateEmailTemplate() {
        try {
            openConnection();
            EmailTemplateDetails emailTemplateDetails = new EmailTemplateDetails();
            emailTemplateDetails.setEmailTemplateId(new BigDecimal(request.getParameter("dfEmailTemplateId")));
            EmailTemplateView emailTemplateView = new EmailTemplateView();
            emailTemplateView.fillWithElements(connection, EmailTemplateDetails.FILL_TYPE_ALL, emailTemplateDetails);
            if (emailTemplateView.getElements().size() == 1) {
                emailTemplateDetails = (EmailTemplateDetails) emailTemplateView.getElements().elementAt(0);
                request.setAttribute("emailTemplateDetails", emailTemplateDetails);
            }
            commit();
            request.getRequestDispatcher("/jsp/shared/emailTemplate/displayInsertUpdateEmailTemplate.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayDeleteEmailTemplate.
     */
    private void displayDeleteEmailTemplate() {
        try {
            openConnection();
            EmailTemplateDetails emailTemplateDetails = new EmailTemplateDetails();
            emailTemplateDetails.setEmailTemplateId(new BigDecimal(request.getParameter("dfEmailTemplateId")));
            EmailTemplateView emailTemplateView = new EmailTemplateView();
            emailTemplateView.fillWithElements(connection, EmailTemplateDetails.FILL_TYPE_ALL, emailTemplateDetails);
            if (emailTemplateView.getElements().size() == 1) {
                emailTemplateDetails = (EmailTemplateDetails) emailTemplateView.getElements().elementAt(0);
                request.setAttribute("emailTemplateDetails", emailTemplateDetails);
            }
            commit();
            request.getRequestDispatcher("/jsp/shared/emailTemplate/displayInsertUpdateEmailTemplate.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }


    /**
     * Method insertEmailTemplate.
     */
    private void insertEmailTemplate() {
        try {
            openConnection();

            EmailTemplateDetails emailTemplateDetails = new EmailTemplateDetails();
            EmailTemplateView emailTemplateView = new EmailTemplateView();
            try {
                // have to add user offset to teh time
                emailTemplateDetails.setEmailTemplateDate(DateUtilities.getCurrentSQLTimestamp());
                if(isAdmin()){
                	emailTemplateDetails.setUserName("admin");
                }else{
                	emailTemplateDetails.setUserName(this.userProfile.getUserName());
                }
                emailTemplateDetails.setDescription(request.getParameter("dfDescription"));
                emailTemplateDetails.setText(request.getParameter("dfText"));

                emailTemplateView.setConnection(connection);
                emailTemplateView.setAction("INSERT");
                emailTemplateView.setFillType(EmailTemplateDetails.FILL_TYPE_ALL);
                emailTemplateView.setCurrent(emailTemplateDetails);
                emailTemplateView.doAction();

                //also, change the prospect Update Date and User
                           commit();
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            forward("/jsp/shared/emailTemplate/emailTemplateActionResult.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateEmailTemplate.
     */
    private void updateEmailTemplate() {
        try {
            openConnection();

            EmailTemplateDetails emailTemplateDetails = new EmailTemplateDetails();
            EmailTemplateView emailTemplateView = new EmailTemplateView();

            emailTemplateDetails.setEmailTemplateId(new BigDecimal(request.getParameter("dfEmailTemplateId")));
            emailTemplateDetails.setEmailTemplateDate(DateUtilities.getCurrentSQLTimestamp());
            emailTemplateDetails.setUserName(this.userProfile.getUserName());
            emailTemplateDetails.setDescription(request.getParameter("dfDescription"));
            emailTemplateDetails.setText(request.getParameter("dfText"));

            emailTemplateView.setConnection(connection);
            emailTemplateView.setAction("UPDATE");
            emailTemplateView.setFillType(EmailTemplateDetails.FILL_TYPE_ALL);
            emailTemplateView.setCurrent(emailTemplateDetails);
            emailTemplateView.doAction();
            
            commit();
            forward("/jsp/shared/emailTemplate/emailTemplateActionResult.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteEmailTemplate.
     */
    private void deleteEmailTemplate() {
        try {
            openConnection();

            EmailTemplateDetails emailTemplateDetails = new EmailTemplateDetails();
            EmailTemplateView emailTemplateView = new EmailTemplateView();

            emailTemplateDetails.setEmailTemplateId(new BigDecimal(request.getParameter("dfEmailTemplateId")));

            emailTemplateView.setConnection(connection);
            emailTemplateView.setAction("DELETE");
            emailTemplateView.setFillType(EmailTemplateDetails.FILL_TYPE_ALL);
            emailTemplateView.setCurrent(emailTemplateDetails);
            emailTemplateView.doAction();

            commit();
            forward("/jsp/shared/emailTemplate/emailTemplateActionResult.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayEmailTemplates.
     */
    private void displayEmailTemplates() {
        try {
            openConnection();
            try {
              
                EmailTemplateView emailTemplateView = new EmailTemplateView();
                EmailTemplateDetails emailTemplateDetails = new EmailTemplateDetails();

                emailTemplateView.fillWithElements(connection, EmailTemplateDetails.FILL_TYPE_ALL, emailTemplateDetails);
                if (emailTemplateView.getElements().size() < 1) {
                    throw new ModelException("No records found.", ModelException.RECORD_NOT_FOUND);
                }
                request.setAttribute("emailTemplateView", emailTemplateView);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            commit();
            
            if (isAdmin()){
            	forward("/jsp/shared/emailTemplate/displayEmailTemplates.jsp");//pop up
            }else{
            	//forward("/jsp/shared/emailTemplate/displayEmailTemplates.jsp");//pop up
            	forward("/jsp/shared/emailTemplate/displayEmailTemplatesFromMenu.jsp");//within menu structure
            }
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }


}
