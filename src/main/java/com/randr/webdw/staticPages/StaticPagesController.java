package com.randr.webdw.staticPages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.user.UserDetails;


/**
 */
public class StaticPagesController extends AbstractController {

	private static String mobile = "";
	private boolean mobility = false;
    /**
     * Constructor for StaticPagesController.
     */
    public StaticPagesController() {
    }

    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
   		try {
	    	determineWebBrowserType(request);
	    	if(isMobility()){
				forward("/jsp/public/mobility/mainMenuMobility.jsp");
	    	}else{
		    	if (request.getServletPath().equals("/home")) {
		            if (request.getParameter("initialProspectId") != null) {
		                request.getRequestDispatcher("/prospect_search?formAction=DISPLAY&dfSearchProspectId=" + request.getParameter("initialProspectId")).forward(request, response);
		            } else {
		                request.getRequestDispatcher("/jsp/public/home.jsp").forward(request, response);
		            }
		        } else if (request.getServletPath().equals("/menu")) {
//		        	need to validate this is admin user first else display sign on screen
		        	if(this.userProfile.isValidProfile()){//check if this is necessary
		        		displayMenu();
		        	}else{
		        		request.getRequestDispatcher("/jsp/public/home.jsp").forward(request, response);
		        	}
		        } else if (request.getServletPath().equals("/admin")) {
		        	//need to validate this is admin user first else display sign on screen
		        	if(this.userProfile!=null && this.userProfile.isValidProfile() && userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN)){
		        		request.getRequestDispatcher("/jsp/admin/home.jsp").forward(request, response);
		        	}else{
		        		request.getRequestDispatcher("/jsp/public/user/login.jsp").forward(request, response);
		        	}
		        }
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private void determineWebBrowserType(HttpServletRequest request) {

    	String s = request.getHeader("user-agent");
    	
    	if(s.indexOf("Blazer") > -1 || s.indexOf("Palm") > -1 || s.indexOf("Handspring") > -1 
    			|| s.indexOf("Nokia") > -1 || s.indexOf("Kyocera") > -1 
    			|| s.indexOf("Samsung") > -1 || s.indexOf("Motorola") > -1 
    			|| s.indexOf("Smartphone") > -1 || s.indexOf("Windows CE") > -1
    			|| s.indexOf("Blackberry") > -1 || s.indexOf("WAP") > -1 
    			|| s.indexOf("SonyEricsson") > -1 || s.indexOf("iPhone") > -1 
    			|| s.indexOf("Droid") > -1
 //   			|| s.indexOf("Mozilla") > -1
    			)
    	{
    		
    		this.setMobility(true);
    	}else{
    		this.setMobility(false);
    	}

//	       'Blazer'
//	       'Palm'
//	       'Handspring'
//	       'Nokia'
//	       'Kyocera'
//	       'Samsung'
//	       'Motorola'
//	       'Smartphone'
//	       'Windows CE'
//	       'Blackberry'
//	       'WAP'
//	       'SonyEricsson' 
//         'iPhone'    	
  	
//	   // etc ...	
	}

		

	/**
     * Method displayMenu.
     */
    private void displayMenu() {
        try {
            if (MenuView.getMenu(userProfile.getUserType(), request) == null) {
                MenuView.refreshMenu(userProfile.getUserType(), request);
            }

            request.getRequestDispatcher("/jsp/public/menu.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

	public boolean isMobility() {
		return mobility;
	}

	public void setMobility(boolean mobility) {
		this.mobility = mobility;
	}
}
