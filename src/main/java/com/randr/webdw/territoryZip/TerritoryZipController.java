package com.randr.webdw.territoryZip;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.calendarSchedule.CalendarScheduleDetails;
import com.randr.webdw.calendarSchedule.CalendarScheduleView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.territoryZip.TerritoryZipDetails;
import com.randr.webdw.territoryZip.TerritoryZipView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserView;
import com.randr.webdw.util.Utilities;


/**
 */
public class TerritoryZipController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	if (isAdmin()){
    		redirectIfNotValidAdminUserProfile();
    	}
        if ((formAction == null) || formAction.equals("DISPLAY")) {
            displayInsertUpdateZip();
        } else if (formAction.equals("DELETE")){
        	deleteTerritoryZip();
        } else if (formAction.equals("INSERT")){
        	insertTerritoryZip();
        }
    }


	private void displayInsertUpdateZip() {
		try {

            openConnection();
            
            TerritoryView view = new TerritoryView();
            TerritoryDetails details = new TerritoryDetails();
            details.setTerritoryId(new BigDecimal(request.getParameter("dfTerritoryId")));
            view.fillWithElements(connection, details.FILL_TYPE_ALL, details);
            
            TerritoryZipView territoryZipView = new TerritoryZipView();
            TerritoryZipDetails territoryZipDetails = new TerritoryZipDetails();
                        
            territoryZipDetails.setTerritoryId(new BigDecimal(request.getParameter("dfTerritoryId")));
            
            territoryZipView.fillWithElements(connection, territoryZipDetails.FILL_TYPE_ALL,  territoryZipDetails);
            
            request.setAttribute("territoryDetails", view.getElement(0));
			request.setAttribute("territoryZipView", territoryZipView);
			forward("/jsp/admin/territoryZip/displayInsertUpdateZip.jsp");
            
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertTerritoryZip.
     */
    private void insertTerritoryZip() {
        try {
            openConnection();

            // check if the territory already exist
            TerritoryZipDetails details = new TerritoryZipDetails();
            details.setZip(request.getParameter("dfZip"));
    
            TerritoryZipView view = new TerritoryZipView();
            view.fillWithElements(connection, TerritoryZipDetails.FILL_TYPE_ALL, details);

            if(view.getElements().size() > 0) {
            	details=(TerritoryZipDetails)view.getElement(0);
            	TerritoryView territoryView = new TerritoryView();
                TerritoryDetails territoryDetails = new TerritoryDetails();
                territoryDetails.setTerritoryId(details.getTerritoryId());
                territoryView.fillWithElements(connection, details.FILL_TYPE_ALL, territoryDetails);
                territoryDetails = (TerritoryDetails)territoryView.getElement(0);
            	request.setAttribute("message", "Zip " + details.getZip() + 
            			" already associated with Territory " + territoryDetails.getTerritory() + ".");
            } else {
            	details.setTerritoryId(new BigDecimal(request.getParameter("dfTerritoryId")));
            	view.doAction(connection, "INSERT", TerritoryZipDetails.FILL_TYPE_ALL, details);
            	request.setAttribute("message", "Zip successfully added.");
            }

            commit();
            // forward request
           
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
        displayInsertUpdateZip();
    }


    /**
     * Method deleteTerritory.
     */
    private void deleteTerritoryZip() {
        try {
            openConnection();

            // check if territory exist
            TerritoryZipDetails territoryZipDetails = new TerritoryZipDetails();
            territoryZipDetails.setTerritoryZipId(new BigDecimal(request.getParameter("dfTerritoryZipId")));

            TerritoryZipView territoryZipView = new TerritoryZipView();
            territoryZipView.fillWithElements(connection, TerritoryZipDetails.FILL_TYPE_ALL, territoryZipDetails);
            
            
            if(territoryZipView.getElements().size() == 0) {
            	request.setAttribute("message", "The territory zip cannot be deleted.");
            } else {
            	TerritoryZipDetails messageDetails = (TerritoryZipDetails)territoryZipView.getElement(0);
            	territoryZipView.doAction(connection, "DELETE", null, territoryZipDetails);
            	request.setAttribute("message", "Zip " + messageDetails.getZip()  + " deleted.");
            }

            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
        displayInsertUpdateZip();
    }
}
