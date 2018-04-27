package com.randr.webdw.userStatuses;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 * @author randr
 * @version $Revision: 1.7 $
 */
public class UserStatusesController extends AbstractController {
    /**
     * Constructor for ContactController.
     */
    public UserStatusesController() {
    }

    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	if (isAdmin()){
    		redirectIfNotValidAdminUserProfile();
    	}
        if ((formAction == null) || (formAction.equals("INSERT"))) {
            insertUserStatuses();
        } else if (formAction.equals("UPDATE")) {
            updateUserStatuses();
        } else if (formAction.equals("DELETE")) {
            deleteUserStatuses();
        } else if (formAction.equals("DISPLAY")) {
            displayUserStatuses();
        }
    }

	private void displayUserStatuses() {
		System.out.println("display user statuses");
		
	}

	private void deleteUserStatuses() {
		System.out.println("delete user statuses");
		
	}

	private void updateUserStatuses() {
		System.out.println("update user statuses");
		
	}

	private void insertUserStatuses() {
		System.out.println("insert user statuses");
		
	}


}
