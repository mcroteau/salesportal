package com.randr.webdw.prospectSalesAction;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.salesAction.SalesActionDetails;
import com.randr.webdw.salesAction.SalesActionView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;

/**
 * @author randr
 * @version $Revision: 1.7 $
 */
public class ProspectSalesActionController extends AbstractController {
    /**
     * Constructor for ContactController.
     */
    public ProspectSalesActionController() {
    }

    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
        if ((formAction == null) || (formAction.equals("INSERT"))) {
            insertProspectSalesAction();
        } else if (formAction.equals("UPDATE")) {
            updateProspectSalesAction();
        } else if (formAction.equals("DELETE")) {
            deleteProspectSalesAction();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertProspectSalesAction();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateProspectSalesAction();
        } else if (formAction.equals("DISPLAY_DELETE")) {
            displayDeleteProspectSalesAction();
        }
    }

    /**
     * Method insertProspectSalesAction.
     */
    private void insertProspectSalesAction() {
        try {
        	openConnection();

            ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
            ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
            try {
                ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
                if (prospectDetails == null) {
                    throw new ModelException("Session expired", ModelException.UNKNOWN_ERROR);
                }
                prospectSalesActionDetails.setProspectId(prospectDetails.getProspectId());
                getProspectSalesActionInformation(prospectSalesActionDetails);
                prospectSalesActionDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
                //stop null sales action id from getting written to the db
                if(prospectSalesActionDetails.getSalesActionId()!=null){
                	prospectSalesActionView.doAction(connection, "INSERT", ProspectSalesActionDetails.FILL_TYPE_ALL, prospectSalesActionDetails);
                }
                //also, change the prospect Update Date and User
                ProspectDetails prospectUpdateDetails = new ProspectDetails();
                ProspectView prospectUpdateView = new ProspectView();
                prospectUpdateDetails.setProspectId(prospectDetails.getProspectId());
                prospectUpdateView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
                if(prospectUpdateView.getElements().size()>0){
                	prospectUpdateDetails = (ProspectDetails)prospectUpdateView.getElement(0);
                	prospectUpdateDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
                	prospectUpdateDetails.setChangeUserId(userProfile.getUserId());
                	prospectUpdateView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
                }
                
                commit();
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            request.getRequestDispatcher("/jsp/public/prospectSalesAction/insertUpdateProspectSalesAction.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateProspectSalesAction.
     */
    private void updateProspectSalesAction() {
        try {
            openConnection();

            ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
            ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();

            prospectSalesActionDetails.setProspectSalesActionId(new BigDecimal(request.getParameter("dfProspectSalesActionId")));
            prospectSalesActionDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
            getProspectSalesActionInformation(prospectSalesActionDetails);
            prospectSalesActionDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
            
            prospectSalesActionView.doAction(connection, "UPDATE", ProspectSalesActionDetails.FILL_TYPE_ALL, prospectSalesActionDetails);

            //also, change the prospect Update Date and User
            ProspectDetails prospectUpdateDetails = new ProspectDetails();
            ProspectView prospectUpdateView = new ProspectView();
            prospectUpdateDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
            prospectUpdateView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
            if(prospectUpdateView.getElements().size()>0){
            	prospectUpdateDetails = (ProspectDetails)prospectUpdateView.getElement(0);
            	prospectUpdateDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
            	prospectUpdateDetails.setChangeUserId(userProfile.getUserId());
            	prospectUpdateView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
            }

            commit();
            request.getRequestDispatcher("/jsp/public/prospectSalesAction/insertUpdateProspectSalesAction.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteProspectSalesAction.
     */
    private void deleteProspectSalesAction() {
        try {
            openConnection();

            ProspectSalesActionDetails searchProspectSalesActionDetails = new ProspectSalesActionDetails();
            ProspectSalesActionView searchProspectSalesActionView = new ProspectSalesActionView();

            searchProspectSalesActionDetails.setProspectSalesActionId(new BigDecimal(request.getParameter("dfProspectSalesActionId")));
            searchProspectSalesActionView.fillWithElements(connection, ProspectSalesActionDetails.FILL_TYPE_ALL, searchProspectSalesActionDetails);
            
            ProspectSalesActionDetails prospectSalesActionDetails = (ProspectSalesActionDetails)searchProspectSalesActionView.getElement(0);
            ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
            
            prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_DELETED);
            prospectSalesActionDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
            

            BigDecimal changedByUserId;
            if(userProfile != null){
            	changedByUserId = userProfile.getUserId();
            }else{
            	changedByUserId = UserDetails.APPLICATION_USER;
            }
            prospectSalesActionDetails.setChangedByUserId(changedByUserId);
            
            prospectSalesActionView.doAction(connection, "UPDATE", ProspectSalesActionDetails.FILL_TYPE_ALL, prospectSalesActionDetails);
            
            commit();
            request.getRequestDispatcher("/jsp/public/prospectSalesAction/insertUpdateProspectSalesAction.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method getProspectSalesActionInformation.
     * @param prospectSalesActionDetails ProspectSalesActionDetails
     */
    private void getProspectSalesActionInformation(ProspectSalesActionDetails prospectSalesActionDetails) throws Exception{
        if (!Utilities.nullToBlank(request.getParameter("cmbSalesAction")).equals("")) {
            prospectSalesActionDetails.setSalesActionId(new BigDecimal(request.getParameter("cmbSalesAction")));
        } else {
            prospectSalesActionDetails.setSalesActionId(null);
        }
        
        if (!Utilities.nullToBlank(request.getParameter("cmbPriority")).equals("")) {
            prospectSalesActionDetails.setActionPriority(new BigDecimal(request.getParameter("cmbPriority")));
        } else {
            prospectSalesActionDetails.setActionPriority(null);
        }
        
        if (Utilities.nullToBlank(request.getParameter("dfEmailDraftToUse")).equals("")) {
            prospectSalesActionDetails.setEmailDraftToUse(getEmailDraftFromSalesAction(prospectSalesActionDetails.getSalesActionId()));
        }else{
        	prospectSalesActionDetails.setEmailDraftToUse(new BigDecimal(request.getParameter("dfEmailDraftToUse")));
        }
        
        if (!Utilities.nullToBlank(request.getParameter("dfProspectId")).equals("")) {
            prospectSalesActionDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
        } else {
            prospectSalesActionDetails.setProspectId(null);
        }
        
        if (!Utilities.nullToBlank(request.getParameter("dfActionNote")).equals("")) {
            prospectSalesActionDetails.setActionNote((request.getParameter("dfActionNote")));
        } else {
            prospectSalesActionDetails.setActionNote(null);
        }
         
    	Timestamp timestamp=null;	
    	String hour = "00";
    	String minute = "00";
    	String time = "";
    	
        if(!Utilities.nullToBlank(request.getParameter("dfSalesActionDate")).equals("")){
        	if(!Utilities.nullToBlank(request.getParameter("dfHour")).equals("")){
        		hour = request.getParameter("dfHour");
        	}else{
        		//defaults to 12:00 PM when no hour, minute is entered
        		time = "PM";
        	}
        	
        	if(!Utilities.nullToBlank(request.getParameter("time")).equals("")){
        		time = request.getParameter("time");
        	
        	}

        	if(!Utilities.nullToBlank(request.getParameter("dfMinute")).equals("")){
        		minute = request.getParameter("dfMinute");
        	}

        	timestamp = DateUtilities.getSqlTimestamp(request.getParameter("dfSalesActionDate"), hour, minute, time);

        }

        prospectSalesActionDetails.setActionDate(timestamp);
        
        //OLD LOGIC - 15 Dec 07 
        //MIKE C 
//        if (!Utilities.nullToBlank(request.getParameter("dfHour")).equals("") &&
//        	!Utilities.nullToBlank(request.getParameter("time")).equals("") &&
//        	!Utilities.nullToBlank(request.getParameter("dfSalesActionDate")).equals("")) {
//
//        	if(!Utilities.nullToBlank(request.getParameter("dfMinute")).equals("")){
//        		timestamp = DateUtilities.getSqlTimestamp(request.getParameter("dfSalesActionDate"),
//        			request.getParameter("dfHour"), request.getParameter("dfMinute"),
//        			request.getParameter("time"));
//        	}else{
//        		timestamp = DateUtilities.getSqlTimestamp(request.getParameter("dfSalesActionDate"),
//        			request.getParameter("dfHour"), "00",
//        			request.getParameter("time"));
//        	}
//            prospectSalesActionDetails.setActionDate(timestamp);
//        }
        
        prospectSalesActionDetails.setActionNotificationflag(new BigDecimal(0));
        
        if (Utilities.nullToBlank(request.getParameter("cmbActionStatus")).equals("Completed")) {
            prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_COMPLETE);
            // when action is set to completed, set notification flag to 1 so no email goes out.
            prospectSalesActionDetails.setActionNotificationflag(new BigDecimal(1));
        } else {
        	prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_ACTIVE);
        }

        BigDecimal changedByUserId;
        if(userProfile != null){
        	changedByUserId = userProfile.getUserId();
        }else{
        	changedByUserId = UserDetails.APPLICATION_USER;
        }
        
        prospectSalesActionDetails.setChangedByUserId(changedByUserId);
    }

    private BigDecimal getEmailDraftFromSalesAction(BigDecimal salesActionId) throws Exception{
		SalesActionDetails salesActionDetails = new SalesActionDetails();
		SalesActionView salesActionView = new SalesActionView();
		salesActionDetails.setActionId(salesActionId);
		salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);
		if(salesActionView.getElements().size()>0){
			salesActionDetails = (SalesActionDetails)salesActionView.getElement(0);
			return salesActionDetails.getEmailDraftToUse();
		}
		return null;
	}

	/**
     * Method displayInsertProspectSalesAction.
     */
    private void displayInsertProspectSalesAction() {
        try {
            request.getRequestDispatcher("/jsp/public/prospectSalesAction/displayInsertUpdateProspectSalesAction.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateProspectSalesAction.
     */
    private void displayUpdateProspectSalesAction() {
        try {
            openConnection();
            ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
            prospectSalesActionDetails.setProspectSalesActionId(new BigDecimal(request.getParameter("dfProspectSalesActionId")));
            ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
            prospectSalesActionView.fillWithElements(connection, ProspectSalesActionDetails.FILL_TYPE_ALL, prospectSalesActionDetails);
            if (prospectSalesActionView.getElements().size() == 1) {
                prospectSalesActionDetails = (ProspectSalesActionDetails) prospectSalesActionView.getElements().elementAt(0);
                request.setAttribute("prospectSalesActionDetails", prospectSalesActionDetails);
            }
            commit();
            request.getRequestDispatcher("/jsp/public/prospectSalesAction/displayInsertUpdateProspectSalesAction.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayDeleteProspectSalesAction.
     */
    private void displayDeleteProspectSalesAction() {
        try {
            openConnection();
            ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
            prospectSalesActionDetails.setProspectSalesActionId(new BigDecimal(request.getParameter("dfProspectSalesActionId")));
            ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
            prospectSalesActionView.fillWithElements(connection, ProspectSalesActionDetails.FILL_TYPE_ALL, prospectSalesActionDetails);
            if (prospectSalesActionView.getElements().size() == 1) {
                prospectSalesActionDetails = (ProspectSalesActionDetails) prospectSalesActionView.getElements().elementAt(0);
                request.setAttribute("prospectSalesActionDetails", prospectSalesActionDetails);
            }
            commit();
            request.getRequestDispatcher("/jsp/public/prospectSalesAction/displayInsertUpdateProspectSalesAction.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
