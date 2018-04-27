package com.randr.webdw.request;

import java.io.IOException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;

public class RequestController extends AbstractController  {
	

	// no silent interface
	private static final String FORM_ACTION_TEST_DATAMIGRATIONPORTAL_CONNECTION = "TEST_DATAMIGRATIONPORTAL_CONNECTION";//
	private static final String FORM_ACTION_TEST_ORDERPORTAL_CONNECTION = "TEST_ORDERPORTAL_CONNECTION";//
	private static final String FORM_ACTION_TEST_EBSPORTAL_CONNECTION = "TEST_EBSPORTAL_CONNECTION";//
															

	/* (non-Javadoc)
	 * @see com.randr.datamigrationportal.mvcAbstract.message.AbstractMessageController#doAction()
	 * This process handles the requests from 2D 
	 * pushes the CSV files to 2D 
	 * 2D always initiates the process
	 */
	public void doAction() throws ServletException, IOException {
		System.out.println("SP - ReqController formaction= '" + formAction + "'");
		//logResultRequestControllerStarted(formAction);
		if(formAction.equalsIgnoreCase(FORM_ACTION_TEST_DATAMIGRATIONPORTAL_CONNECTION)) {
			//this is a dummy formAction to test connection between Sales Portal
			//and Datamigration.  Checks response code to make sure its 
			//a response code of 200.  Just by getting here will return 200.
			//System.out.println("TEST FROM Data Migration Portal);
		} 
		
	}
	
}
