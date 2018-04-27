package com.randr.webdw.userTerritories;

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
public class UserTerritoriesController extends AbstractController {
    /**
     * Constructor for ContactController.
     */
    public UserTerritoriesController() {
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
            insertUserTerritories();
        } else if (formAction.equals("UPDATE")) {
            updateUserTerritories();
        } else if (formAction.equals("DELETE")) {
            deleteUserTerritories();
        } else if (formAction.equals("DISPLAY")) {
            displayUserTerritories();
        }
    }

	private void displayUserTerritories() {
		System.out.println("display user territories");
		
	}

	private void deleteUserTerritories() {
		System.out.println("delete user territories");
		
	}

	private void updateUserTerritories() {
		System.out.println("update user territories");
		
	}

	private void insertUserTerritories() {
		System.out.println("insert user territories");
		
	}


}
