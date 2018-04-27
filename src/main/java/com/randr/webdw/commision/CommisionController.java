package com.randr.webdw.commision;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;

import com.randr.webdw.currency.CurrencyDetails;
import com.randr.webdw.currency.CurrencyView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserView;
import com.randr.webdw.util.DateUtilities;


/**
 */
public class CommisionController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	//System.out.println("CommCont, formAction= " + formAction);
        if ((formAction == null) || (formAction.equals("DISPLAY_SEARCH"))) {
            displaySearchCommisions();
        } else if (formAction.equals("DISPLAY")) {
            displayCommisions();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertCommision();
        } else if (formAction.equals("INSERT")) {
            insertCommision();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateCommision();
        } else if (formAction.equals("UPDATE")) {
            updateCommision();
        } else if (formAction.equals("DELETE")) {
            deleteCommision();
        }
    }

    /**
     * Method displaySearchCommisions.
     */
    private void displaySearchCommisions() {
        try {
            // forward request
            if (isAdmin()) {
                request.getRequestDispatcher("/jsp/admin/commision/displaySearchCommisions.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/jsp/public/commision/displaySearchCommisions.jsp").forward(request, response);
            }
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayCommisions.
     */
    private void displayCommisions() {
        try {

            openConnection();
           
            CommisionDetails commisionDetails = new CommisionDetails();
            if (!isAdmin()) {
                commisionDetails.setUserId(this.userProfile.getUserId());
            }else{
            	if(request.getParameter("dfSearchUserId") != null
                        && !request.getParameter("dfSearchUserId").equals("")) {
            		commisionDetails.setUserId(new BigDecimal(request.getParameter("dfSearchUserId")));
            	}
            }
            if (formAction.equals("DISPLAY_PROSPECT")){
            //add the search on prospect id for click when you are on the prospect screen
            	//enclose the other search selection in else statement
            }
            if (request.getParameter("dfStatus") != null
                && !request.getParameter("dfStatus").equals("")) {
                commisionDetails.setStatus(new BigDecimal(request.getParameter("dfStatus")));
            }
            if (request.getParameter("dfDateSold1") != null
                && !request.getParameter("dfDateSold1").equals("")) {
                try {
                    commisionDetails.setDateSold1(DateUtilities.getSQLDate(request.getParameter("dfDateSold1")));
                } catch (java.lang.ArrayIndexOutOfBoundsException ex) {

                }
            }
            if (request.getParameter("dfDateSold2") != null
                && !request.getParameter("dfDateSold2").equals("")) {
                try {
                    commisionDetails.setDateSold2(DateUtilities.getSQLDate(request.getParameter("dfDateSold2")));
                } catch (java.lang.ArrayIndexOutOfBoundsException ex) {

                }
            }
            if (request.getParameter("dfDatePaid1") != null
                && !request.getParameter("dfDatePaid1").equals("")) {
                try {
                    commisionDetails.setDatePaid1(DateUtilities.getSQLDate(request.getParameter("dfDatePaid1")));
                } catch (java.lang.ArrayIndexOutOfBoundsException ex) {

                }
            }
            if (request.getParameter("dfDatePaid2") != null
                && !request.getParameter("dfDatePaid2").equals("")) {
                try {
                    commisionDetails.setDatePaid2(DateUtilities.getSQLDate(request.getParameter("dfDatePaid2")));
                } catch (java.lang.ArrayIndexOutOfBoundsException ex) {

                }
            }

            CommisionView commisionView = new CommisionView();
            commisionView.fillWithElements(connection, CommisionDetails.FILL_TYPE_JOIN, commisionDetails);
            try {
                if (commisionView.getElements().size() > 0) {
                    request.setAttribute("commisionView", commisionView);
                } else {
                    throw new ModelException("No commissions found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            if (isAdmin()) {
                request.getRequestDispatcher("/jsp/admin/commision/displayCommisions.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/jsp/public/commision/displayCommisions.jsp").forward(request, response);
            }
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertCommision.
     */
    private void insertCommision() {
        try {
            openConnection();

            // check if the commision already exist
            CommisionDetails commisionDetails = new CommisionDetails();
            getCommonFields(commisionDetails);

            // insert commision
            CommisionView commisionView = new CommisionView();
            commisionView.doAction(connection, "INSERT", CommisionDetails.FILL_TYPE_ALL, commisionDetails);
            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/commision/commisionActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method getCommonFields.
     * @param commisionDetails CommisionDetails
     */
    private void getCommonFields(CommisionDetails commisionDetails) {
        if (request.getParameter("dfCommisionId") != null
            && !request.getParameter("dfCommisionId").equals("")) {
            commisionDetails.setCommisionId(new BigDecimal(request.getParameter("dfCommisionId")));
        }
        commisionDetails.setUserId(new BigDecimal(request.getParameter("dfUserId")));
        commisionDetails.setDescription(request.getParameter("dfDescription"));
        
        if(request.getParameter("dfAmount") != null
                && !request.getParameter("dfAmount").equals("")) {
        	commisionDetails.setAmount(new BigDecimal(request.getParameter("dfAmount")));
        }else{
        	commisionDetails.setAmount(new BigDecimal(0));
        }
        
        if(request.getParameter("dfRevenue") != null
                && !request.getParameter("dfRevenue").equals("")) {
        	commisionDetails.setRevenue(new BigDecimal(request.getParameter("dfRevenue")));
        }
        if (request.getParameter("dfDateSold") != null
            && !request.getParameter("dfDateSold").equals("")) {
            try {
                commisionDetails.setDateSold(DateUtilities.getSQLDate(request.getParameter("dfDateSold")));
            } catch (java.lang.ArrayIndexOutOfBoundsException ex) {

            }
        }
        if (request.getParameter("dfDateBilled") != null
            && !request.getParameter("dfDateBilled").equals("")) {
            try {
                commisionDetails.setDateBilled(DateUtilities.getSQLDate(request.getParameter("dfDateBilled")));
            } catch (java.lang.ArrayIndexOutOfBoundsException ex) {

            }

        }
        if (request.getParameter("dfDatePaid") != null
            && !request.getParameter("dfDatePaid").equals("")) {
            try {

                commisionDetails.setDatePaid(DateUtilities.getSQLDate(request.getParameter("dfDatePaid")));
            } catch (java.lang.ArrayIndexOutOfBoundsException ex) {

            }

        }
        String dateFormat= "MM/dd/yyyy";
        if(userProfile!=null){
    		dateFormat = userProfile.getDateFormat();
    	}
       
        if (request.getParameter("dfCloseDate") != null
                && !request.getParameter("dfCloseDate").equals("")) {
                try {
                    commisionDetails.setExpectedCloseDate(DateUtilities.getSQLTimestamp(request.getParameter("dfCloseDate")  + " 00:00", dateFormat ));
                } catch (java.lang.ArrayIndexOutOfBoundsException ex) {

                }
                if(commisionDetails.getExpectedCloseDate()!=null){
                	if(commisionDetails.getOriginalExpectedCloseDate()==null){
                		commisionDetails.setOriginalExpectedCloseDate(commisionDetails.getExpectedCloseDate());
                	}
                }   
         }
        
        if (request.getParameter("dfCancelDate") != null
                && !request.getParameter("dfCancelDate").equals("")) {
                try {
                    commisionDetails.setCancelDate(DateUtilities.getSQLTimestamp(request.getParameter("dfCancelDate") + " 00:00", dateFormat ));
                } catch (java.lang.ArrayIndexOutOfBoundsException ex) {

                }
         }
        

        commisionDetails.setInternalDescription(request.getParameter("dfInternalDescription"));
        commisionDetails.setCheckNumber(request.getParameter("dfCheckNumber"));
        if (request.getParameter("dfCheckAmount") != null && !request.getParameter("dfCheckAmount").equals("")) {
            commisionDetails.setCheckAmount(new BigDecimal(request.getParameter("dfCheckAmount")));
        }
        if (request.getParameter("dfInternalDescription") != null
            && !request.getParameter("dfInternalDescription").equals("")) {
            commisionDetails.setInternalDescription(request.getParameter("dfInternalDescription"));
        }

        if (request.getParameter("dfProspectId") != null
                && !request.getParameter("dfProspectId").equals("")) {
                commisionDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
        }
              
        if (request.getParameter("dfCurrencyId") != null
                && !request.getParameter("dfCurrencyId").equals("")) {
                commisionDetails.setCurrencyId(new BigDecimal(request.getParameter("dfCurrencyId")));
        }
        
        if (request.getParameter("dfPONumber") != null
                && !request.getParameter("dfPONumber").equals("")) {
                commisionDetails.setPoNumber(request.getParameter("dfPONumber"));
        }
        
    }

    /**
     * Method updateCommision.
     */
    private void updateCommision() {
        try {
            openConnection();

            // check if the commision already exist
            CommisionDetails commisionDetails = new CommisionDetails();
            CommisionView commisionView = new CommisionView();
            if (request.getParameter("dfCommisionId") != null
                    && !request.getParameter("dfCommisionId").equals("")) {
                    commisionDetails.setCommisionId(new BigDecimal(request.getParameter("dfCommisionId")));
                    commisionView.fillWithElements(connection, CommisionDetails.FILL_TYPE_ALL, commisionDetails);
                    if(commisionView.getElements().size()>0){
                    	commisionDetails = (CommisionDetails)commisionView.getElement(0);
                    }
            }
            getCommonFields(commisionDetails);
            // insert commision
            commisionView.doAction(connection, "UPDATE", CommisionDetails.FILL_TYPE_ALL, commisionDetails);
            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/commision/commisionActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateCommision.
     */
    private void displayUpdateCommision() {
        try {
            openConnection();

            request.setAttribute("displayInsert", new Boolean(false));
            UserView userView = new UserView();
            UserDetails userDetails = new UserDetails();
            userDetails.setUserType(UserDetails.USER_TYPE_SALESMAN);
            userView.fillWithElements(connection, UserView.FILL_TYPE_SALESMAN, userDetails);
            request.setAttribute("userView", userView);
            
            CurrencyView currencyView = new CurrencyView();
            CurrencyDetails currencyDetails = new CurrencyDetails();
            currencyView.fillWithElements(connection, CurrencyDetails.FILL_TYPE_ALL, currencyDetails);
            request.setAttribute("currencyView", currencyView);

            // check if the commision already exist
            CommisionDetails commisionDetails = new CommisionDetails();
            commisionDetails.setCommisionId(new BigDecimal(request.getParameter("dfCommisionId")));

            CommisionView commisionView = new CommisionView();
            commisionView.fillWithElements(connection, CommisionDetails.FILL_TYPE_ALL, commisionDetails);
            
            try {
                if (commisionView.getElements().size() == 1) {
                    commisionDetails = (CommisionDetails) commisionView.getElements().elementAt(0);
                    request.setAttribute("commisionDetails", commisionDetails);
                    //request.setAttribute("commisionView", commisionView);
                } else {
                    throw new ModelException("Commision cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/commision/displayInsertUpdateCommision.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertCommision.
     */
    private void displayInsertCommision() {
        try {
            openConnection();
            UserView userView = new UserView();
            UserDetails userDetails = new UserDetails();
            userDetails.setUserType(UserDetails.USER_TYPE_SALESMAN);
            userView.fillWithElements(connection, UserView.FILL_TYPE_SALESMAN, userDetails);
            request.setAttribute("userView", userView);
            request.setAttribute("displayInsert", new Boolean(true));
            
            CurrencyView currencyView = new CurrencyView();
            CurrencyDetails currencyDetails = new CurrencyDetails();
            currencyView.fillWithElements(connection, CurrencyDetails.FILL_TYPE_ALL, currencyDetails);
            request.setAttribute("currencyView", currencyView);
            
            CommisionDetails commisionDetails = new CommisionDetails();
            if (request.getParameter("dfProspectId") != null
                    && !request.getParameter("dfProspectId").equals("")) {
                    commisionDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
            }
            if (request.getParameter("dfCustomerCompany") != null
                    && !request.getParameter("dfCustomerCompany").equals("")) {
                    commisionDetails.setProspectName(request.getParameter("dfCustomerCompany"));
            }
            request.setAttribute("commisionDetails", commisionDetails);
            
            // forward request
            commit();
            request.getRequestDispatcher("/jsp/admin/commision/displayInsertUpdateCommision.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteCommision.
     */
    private void deleteCommision() {
        try {
            openConnection();

            // check if commision exist
            CommisionDetails commisionDetails = new CommisionDetails();
            commisionDetails.setCommisionId(new BigDecimal(request.getParameter("dfCommisionId")));

            CommisionView commisionView = new CommisionView();
            commisionView.fillWithElements(connection, CommisionDetails.FILL_TYPE_ALL, commisionDetails);

            try {
                if (commisionView.getElements().size() == 0) {
                    throw new ModelException("The commission you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete commision
                commisionView.doAction(connection, "DELETE", null, commisionDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/commision/commisionActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
