package com.randr.webdw.territory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.territoryZip.TerritoryZipDetails;
import com.randr.webdw.territoryZip.TerritoryZipView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserView;
import com.randr.webdw.userTerritories.UserTerritoriesDetails;
import com.randr.webdw.userTerritories.UserTerritoriesView;
import com.randr.webdw.util.Utilities;


/**
 */
public class TerritoryController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	if (isAdmin()){
    		redirectIfNotValidAdminUserProfile();
    	}
        if ((formAction == null) || (formAction.equals("DISPLAY"))) {
            displayTerritories();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertTerritory();
        } else if (formAction.equals("INSERT")) {
            insertTerritory();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateTerritory();
        } else if (formAction.equals("UPDATE")) {
            updateTerritory();
        } else if (formAction.equals("DELETE")) {
            deleteTerritory();
        }
    }

	/**
     * Method displayTerritories.
     */
    private void displayTerritories() {
        try {

            openConnection();

            TerritoryView territoryView = new TerritoryView();
            territoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, new TerritoryDetails());
            try {
                if (territoryView.getElements().size() > 0) {
                	request.setAttribute("territoryView", territoryView);
                } else {
                    throw new ModelException("No territories found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/territory/displayTerritories.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

	/**
     * Method insertTerritory.
     */
    private void insertTerritory() {
        try {
            openConnection();

            // check if the territory already exist
            TerritoryDetails territoryDetails = new TerritoryDetails();
            territoryDetails.setTerritory(request.getParameter("dfTerritory"));

            if (!Utilities.nullToBlank(request.getParameter("dfOwnerUserId")).equals("")) {
                territoryDetails.setOwnerUserId(new BigDecimal(request.getParameter("dfOwnerUserId")));
            }

            if (!Utilities.nullToBlank(request.getParameter("dfSalesManagerUserId")).equals("")) {
                territoryDetails.setSalesManagerUserId(new BigDecimal(request.getParameter("dfSalesManagerUserId")));
            }
            if (!Utilities.nullToBlank(request.getParameter("dfServiceManagerUserId")).equals("")) {
                territoryDetails.setServiceManagerUserId(new BigDecimal(request.getParameter("dfServiceManagerUserId")));
            }
            
            if (!Utilities.nullToBlank(request.getParameter("dfReturnTerritoryId")).equals("")) {
                territoryDetails.setReturnTerritoryId(new BigDecimal(request.getParameter("dfReturnTerritoryId")));
            }

            if (!Utilities.nullToBlank(request.getParameter("dfReturnTime")).equals("")) {
                territoryDetails.setReturnTime(new BigDecimal(request.getParameter("dfReturnTime")));
            }
            
            if (!Utilities.nullToBlank(request.getParameter("dfMaxProspectDisplay")).equals("")) {
                territoryDetails.setMaxProspectDisplay(new BigDecimal(request.getParameter("dfMaxProspectDisplay")));
            }

            if (!Utilities.nullToBlank(request.getParameter("dfRandomSequence")).equals("")) {
                territoryDetails.setRandomSequence(new BigDecimal(request.getParameter("dfRandomSequence")));
            }
            
            if (!Utilities.nullToBlank(request.getParameter("dfIncludeInReport")).equals("")) {
                territoryDetails.setIncludeInConversionReport(new BigDecimal(request.getParameter("dfIncludeInReport")));
            }
            
            if (!Utilities.nullToBlank(request.getParameter("dfIncludeInRoundRobin")).equals("")) {
                territoryDetails.setIncludeInRoundRobin(new BigDecimal(request.getParameter("dfIncludeInRoundRobin")));
            }
            
            
            TerritoryView territoryView = new TerritoryView();
            territoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, territoryDetails);

            try {
                if (territoryView.getElements().size() > 0) {
                    throw new ModelException("A territory with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert territory
                territoryView.doAction(connection, "INSERT", TerritoryDetails.FILL_TYPE_ALL, territoryDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/territory/territoryActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateTerritory.
     */
    private void updateTerritory() {
        try {
            openConnection();

            TerritoryDetails territoryDetails = new TerritoryDetails();
            territoryDetails.setTerritoryId(new BigDecimal(request.getParameter("dfTerritoryId")));
            territoryDetails.setTerritory(request.getParameter("dfTerritory"));

            if (!Utilities.nullToBlank(request.getParameter("dfOwnerUserId")).equals("")) {
                territoryDetails.setOwnerUserId(new BigDecimal(request.getParameter("dfOwnerUserId")));
            }

            if (!Utilities.nullToBlank(request.getParameter("dfSalesManagerUserId")).equals("")) {
                territoryDetails.setSalesManagerUserId(new BigDecimal(request.getParameter("dfSalesManagerUserId")));
            }
            if (!Utilities.nullToBlank(request.getParameter("dfServiceManagerUserId")).equals("")) {
                territoryDetails.setServiceManagerUserId(new BigDecimal(request.getParameter("dfServiceManagerUserId")));
            }

            if (!Utilities.nullToBlank(request.getParameter("dfReturnTerritoryId")).equals("")) {
                territoryDetails.setReturnTerritoryId(new BigDecimal(request.getParameter("dfReturnTerritoryId")));
            }
            
            if (!Utilities.nullToBlank(request.getParameter("dfReturnTime")).equals("")) {
                territoryDetails.setReturnTime(new BigDecimal(request.getParameter("dfReturnTime")));
            }
            
            if (!Utilities.nullToBlank(request.getParameter("dfMaxProspectDisplay")).equals("")) {
                territoryDetails.setMaxProspectDisplay(new BigDecimal(request.getParameter("dfMaxProspectDisplay")));
            }

            if (!Utilities.nullToBlank(request.getParameter("dfRandomSequence")).equals("")) {
                territoryDetails.setRandomSequence(new BigDecimal(request.getParameter("dfRandomSequence")));
            }
            
            if (!Utilities.nullToBlank(request.getParameter("dfIncludeInReport")).equals("")) {
                territoryDetails.setIncludeInConversionReport(new BigDecimal(request.getParameter("dfIncludeInReport")));
            }

            System.out.println("round robin - " + request.getParameter("dfIncludeInRoundRobin"));
            if (!Utilities.nullToBlank(request.getParameter("dfIncludeInRoundRobin")).equals("")) {
            	System.out.println("not equal '' ");
                territoryDetails.setIncludeInRoundRobin(new BigDecimal(request.getParameter("dfIncludeInRoundRobin")));
            }
            
            System.out.println("" + territoryDetails.getIncludeInRoundRobin());
            TerritoryView territoryView = new TerritoryView();
            territoryView.doAction(connection, "UPDATE", TerritoryDetails.FILL_TYPE_ALL, territoryDetails);

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/territory/territoryActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateTerritory.
     */
    private void displayUpdateTerritory() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the territory already exist
            TerritoryDetails territoryDetails = new TerritoryDetails();
            territoryDetails.setTerritoryId(new BigDecimal(request.getParameter("dfTerritoryId")));

            TerritoryView territoryView = new TerritoryView();
            territoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, territoryDetails);

            try {
                if (territoryView.getElements().size() == 1) {
                    territoryDetails = (TerritoryDetails) territoryView.getElements().elementAt(0);
                    
                    TerritoryView returnTerritoriesView = new TerritoryView();
                    TerritoryDetails returnTerritoryDetails = new TerritoryDetails();
                    returnTerritoriesView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, returnTerritoryDetails);
                    request.setAttribute("returnTerritoryView", returnTerritoriesView);
                    
                    request.setAttribute("territoryDetails", territoryDetails);

                    UserDetails userDetails = new UserDetails();
                    userDetails.setUserType(UserDetails.USER_TYPE_SALESMAN);
                    userDetails.setTerritoryIdOrNull(territoryDetails.getTerritoryId());

                    UserView userView = new UserView();
                    userView.setOrderBy("user_name");
                    userView.fillWithElements(connection, UserView.FILL_TYPE_SALESMAN, userDetails);
                    request.setAttribute("userView", userView);

                } else {
                    throw new ModelException("Territory cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/territory/displayInsertUpdateTerritory.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertTerritory.
     */
    private void displayInsertTerritory() {
        try {
            openConnection();
            request.setAttribute("displayInsert", new Boolean(true));
            UserDetails userDetails = new UserDetails();
            userDetails.setUserType(UserDetails.USER_TYPE_SALESMAN);

            UserView userView = new UserView();
            userView.setOrderBy("user_name");
            userView.fillWithElements(connection, UserView.FILL_TYPE_SALESMAN, userDetails);
            request.setAttribute("userView", userView);
            
            
            TerritoryView returnTerritoriesView = new TerritoryView();
            TerritoryDetails returnTerritoryDetails = new TerritoryDetails();
            returnTerritoriesView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, returnTerritoryDetails);
            request.setAttribute("returnTerritoryView", returnTerritoriesView);
            

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/territory/displayInsertUpdateTerritory.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteTerritory.
     */
    private void deleteTerritory() {
        try {
            openConnection();

            // check if territory exist
            TerritoryDetails territoryDetails = new TerritoryDetails();
            territoryDetails.setTerritoryId(new BigDecimal(request.getParameter("dfTerritoryId")));

            TerritoryView territoryView = new TerritoryView();
            territoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, territoryDetails);

            try {
                if (territoryView.getElements().size() == 0) {
                    throw new ModelException("The territory you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete territory

                try {
                    territoryView.doAction(connection, "DELETE", null, territoryDetails);
                    TerritoryZipView territoryZipView = new TerritoryZipView();
                    TerritoryZipDetails territoryZipDetails = new TerritoryZipDetails();
                    
                    territoryZipDetails.setTerritoryId(territoryDetails.getTerritoryId());
                    territoryZipView.fillWithElements(connection, TerritoryZipDetails.FILL_TYPE_ALL, territoryZipDetails);
                    
                    for(int i = 0; i < territoryZipView.getElements().size(); i++){
                    	territoryZipDetails = (TerritoryZipDetails) territoryZipView.getElement(i);
                    	territoryZipView.doAction(connection, "DELETE", null, territoryZipDetails);
                    }
//                  do the same logic for the user_territories
                    UserTerritoriesView userTerritoriesView = new UserTerritoriesView();
                    UserTerritoriesDetails userTerritoriesDetails = new UserTerritoriesDetails();
                    
                    userTerritoriesDetails.setTerritoryId(territoryDetails.getTerritoryId());
                    userTerritoriesView.fillWithElements(connection, UserTerritoriesDetails.FILL_TYPE_ALL, userTerritoriesDetails);
                    
                    for(int i = 0; i < userTerritoriesView.getElements().size(); i++){
                    	userTerritoriesDetails = (UserTerritoriesDetails) userTerritoriesView.getElement(i);
                    	userTerritoriesView.doAction(connection, "DELETE", null, userTerritoriesDetails);
                    }
                    
                    
                } catch (SQLException sqlE) {
                    throw new ModelException("The territory cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/territory/territoryActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
