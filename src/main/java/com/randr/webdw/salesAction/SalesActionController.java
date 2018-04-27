package com.randr.webdw.salesAction;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionView;
import com.randr.webdw.util.Utilities;


/**
 */
public class SalesActionController extends AbstractController {
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
            displaySalesActions();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertSalesAction();
        } else if (formAction.equals("INSERT")) {
            insertSalesAction();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateSalesAction();
        } else if (formAction.equals("UPDATE")) {
            updateSalesAction();
        } else if (formAction.equals("DELETE")) {
            deleteSalesAction();
        }
    }

    /**
     * Method displaySalesActions.
     */
    private void displaySalesActions() {
        try {

            openConnection();

            SalesActionView salesActionView = new SalesActionView();
            salesActionView.fillWithElements(connection,
                                             SalesActionDetails.FILL_TYPE_ALL,
                                             new SalesActionDetails());
            try {
                if (salesActionView.getElements().size() > 0) {
                    request.setAttribute("salesActionView", salesActionView);
                } else {
                    throw new ModelException("No sales actions found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/salesAction/displaySalesActions.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertSalesAction.
     */
    private void insertSalesAction() {
        try {
            openConnection();

            // check if the salesAction already exist
            SalesActionDetails salesActionDetails = new SalesActionDetails();
            salesActionDetails.setAction(request.getParameter("dfSalesAction"));
            salesActionDetails.setColor(request.getParameter("dfColor"));
            if(!Utilities.nullToBlank(request.getParameter("dfEmailDraftToUse")).equals("")) {
            	salesActionDetails.setEmailDraftToUse(new BigDecimal(request.getParameter("dfEmailDraftToUse")));
            }else{
            	salesActionDetails.setEmailDraftToUse(null);
            }
            salesActionDetails.setMandatoryDate(new BigDecimal(request.getParameter("dfMandatoryDate")));
            salesActionDetails.setDisplayForSalesmen(new BigDecimal(request.getParameter("dfDisplayForSalesmen")));

            SalesActionView salesActionView = new SalesActionView();
            salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);

            try {
                if (salesActionView.getElements().size() > 0) {
                    throw new ModelException("A sales action with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert salesAction
                salesActionView.doAction(connection, "INSERT", SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/salesAction/salesActionActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateSalesAction.
     */
    private void updateSalesAction() {
        try {
            openConnection();

            // check if the salesAction already exist
            SalesActionDetails salesActionDetails = new SalesActionDetails();
            salesActionDetails.setAction(request.getParameter("dfSalesAction"));
            salesActionDetails.setColor(request.getParameter("dfColor"));
            salesActionDetails.setMandatoryDate(new BigDecimal(request.getParameter("dfMandatoryDate")));
            salesActionDetails.setDisplayForSalesmen(new BigDecimal(request.getParameter("dfDisplayForSalesmen")));
            if(!Utilities.nullToBlank(request.getParameter("dfEmailDraftToUse")).equals("")) {
            	salesActionDetails.setEmailDraftToUse(new BigDecimal(request.getParameter("dfEmailDraftToUse")));
            }else{
            	salesActionDetails.setEmailDraftToUse(null);
            }

            SalesActionView salesActionView = new SalesActionView();
            salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);

            SalesActionDetails sad = (salesActionView.getElements().size() > 0) ? (SalesActionDetails)salesActionView.getElements().get(0) : null;
                        
            salesActionDetails.setActionId(new BigDecimal(request.getParameter("dfSalesActionId")));
            
            try {
//                if (salesActionView.getElements().size() > 0) {
            	if ( (sad != null) && (!sad.actionId.equals(salesActionDetails.actionId)) ) {
                    throw new ModelException("A sales action with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

//                salesActionDetails.setActionId(new BigDecimal(request.getParameter("dfSalesActionId")));
//              update salesAction
                salesActionView.doAction(connection, "UPDATE", SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/salesAction/salesActionActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateSalesAction.
     */
    private void displayUpdateSalesAction() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the salesAction already exist
            SalesActionDetails salesActionDetails = new SalesActionDetails();
            salesActionDetails.setActionId(new BigDecimal(request.getParameter("dfSalesActionId")));

            SalesActionView salesActionView = new SalesActionView();
            salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);

            try {
                if (salesActionView.getElements().size() == 1) {
                    salesActionDetails = (SalesActionDetails) salesActionView.getElements().elementAt(0);
                    request.setAttribute("salesActionDetails", salesActionDetails);
                } else {
                    throw new ModelException("Line of business cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/salesAction/displayInsertUpdateSalesAction.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertSalesAction.
     */
    private void displayInsertSalesAction() {
        try {
            request.setAttribute("displayInsert", new Boolean(true));
            // forward request
            request.getRequestDispatcher("/jsp/admin/salesAction/displayInsertUpdateSalesAction.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteSalesAction.
     */
    private void deleteSalesAction() {
        try {
            openConnection();

            
            try{
//	            if(salesActionExistsForProspects()){// do we want to allow for deletes?
//	            	throw new ModelException("The sales action you are trying to delete cannot be deleted.  You have prospects with this sales action.", ModelException.RECORD_NOT_FOUND);	
//		            
//	            }else{
	    	        // check if salesAction exist
	                SalesActionDetails salesActionDetails = new SalesActionDetails();
	                salesActionDetails.setActionId(new BigDecimal(request.getParameter("dfSalesActionId")));

            SalesActionView salesActionView = new SalesActionView();
            salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);

//	                try {
	                    if (salesActionView.getElements().size() == 0) {
	                        throw new ModelException("The sales action you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
	                    }
	                    // delete salesAction

	                    try {
	                        salesActionView.doAction(connection, "DELETE", null, salesActionDetails);
	                    } catch (SQLException sqlE) {
	                        throw new ModelException("The Sales Action cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
	                    }

//	                } catch (ModelException modelException) {
//	                    request.setAttribute("modelException", modelException);
//	                }
	                
	           // }

	        } catch (ModelException modelException) {
	            request.setAttribute("modelException", modelException);
	        }
            


            commit();
            // forward request
            forward("/jsp/admin/salesAction/salesActionActionResult.jsp");
            
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

	private boolean salesActionExistsForProspects() throws Exception {
        
		ProspectSalesActionDetails searchProspectSalesActionDetails = new ProspectSalesActionDetails();
        ProspectSalesActionView searchProspectSalesActionView = new ProspectSalesActionView();
        
        searchProspectSalesActionDetails.setSalesActionId(new BigDecimal(request.getParameter("dfSalesActionId")));
        System.out.println("searchProspectSalesActionDetails = " + searchProspectSalesActionDetails.getSalesActionId());
        System.out.println("searchProspectSalesActionDetails = " + searchProspectSalesActionDetails.toString());
        searchProspectSalesActionView.fillWithElements(connection, ProspectSalesActionDetails.FILL_TYPE_ALL, searchProspectSalesActionDetails);
        System.out.println("searchProspectSalesActionView.getElements().size() = " + searchProspectSalesActionView.getElements().size());
        
        
        for(int i = 0; i < searchProspectSalesActionView.getElements().size(); i++){
        	ProspectSalesActionDetails details = (ProspectSalesActionDetails)searchProspectSalesActionView.getElement(i);
        	System.out.println("details " + details.toString());
        	
        }
        if(searchProspectSalesActionView.getElements().size() > 0){
        	return true;
        }
		return false;
	}
}
