package com.randr.webdw.emailQue;

import java.io.IOException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.prospect.ProspectView;

public class EmailQueController extends AbstractController{

	public void doAction() throws ServletException, IOException {
		if (isAdmin()){
    		redirectIfNotValidAdminUserProfile();
    	}
		if (formAction.equals("DISPLAY_UPLOAD_EMAIL_QUE")) {
			displayUploadEmailQue();
	    } 
		
	}


	/**
     * Method displayNotes.
     */
    private void displayUploadEmailQue() {
        try {
            openConnection();
            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
            
            request.setAttribute("prospectView", sessionProspectView);

            commit();
            forward("/jsp/public/emailQue/displayUploadEmailQue.jsp");
            
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
