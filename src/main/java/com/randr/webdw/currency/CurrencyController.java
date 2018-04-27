package com.randr.webdw.currency;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;


/**
 */
public class CurrencyController extends AbstractController {
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
            displayCurrencies();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertCurrency();
        } else if (formAction.equals("INSERT")) {
            insertCurrency();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateCurrency();
        } else if (formAction.equals("UPDATE")) {
            updateCurrency();
        } else if (formAction.equals("DELETE")) {
            deleteCurrency();
        }
    }

    /**
     * Method displayCurrencys
     */
    private void displayCurrencies() {
        try {

            openConnection();

            CurrencyView currencyView = new CurrencyView();
            currencyView.fillWithElements(connection, CurrencyDetails.FILL_TYPE_ALL, new CurrencyDetails());
            try {
                if (currencyView.getElements().size() > 0) {
                    request.setAttribute("currencyView", currencyView);
                } else {
                    throw new ModelException("No currency codes found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/currency/displayCurrencies.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertCurrency.
     */
    private void insertCurrency() {
        try {
            openConnection();

            // check if the currency already exist
            CurrencyDetails currencyDetails = new CurrencyDetails();
            currencyDetails.setCurrencyCode(request.getParameter("dfCurrencyCode"));
            currencyDetails.setCurrencyDescription(request.getParameter("dfCurrencyDescription"));
            currencyDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
//            if(Utilities.nullToZero(userProfile.getUserId())!=null){
//            	currencyDetails.setCreationUser(userProfile.getUserId().toString());
//            }
            
            CurrencyView currencyView = new CurrencyView();
            currencyView.fillWithElements(connection, CurrencyDetails.FILL_TYPE_ALL, currencyDetails);

            try {
                if (currencyView.getElements().size() > 0) {
                    throw new ModelException("A currency code with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert currency
                currencyView.doAction(connection, "INSERT", CurrencyDetails.FILL_TYPE_ALL, currencyDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/currency/currencyActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateCurrency.
     */
    private void updateCurrency() {
        try {
            openConnection();

            // check if the currency already exist
            CurrencyDetails currencyDetails = new CurrencyDetails();
            currencyDetails.setCurrencyId(new BigDecimal(request.getParameter("dfCurrencyId")));

            CurrencyView currencyView = new CurrencyView();
            currencyView.fillWithElements(connection, CurrencyDetails.FILL_TYPE_ALL, currencyDetails);
            try {
                if (currencyView.getElements().size() == 0) {
                    throw new ModelException("This currency code does not exist in the Database.", ModelException.DUPLICATE_RECORD);
                }
                currencyDetails = (CurrencyDetails)currencyView.getElement(0);
                // insert currency
                currencyDetails.setCurrencyCode(request.getParameter("dfCurrencyCode"));
                currencyDetails.setCurrencyDescription(request.getParameter("dfCurrencyDescription"));
                currencyDetails.setUpdateDate(DateUtilities.getCurrentSQLTimestamp());
                //currencyDetails.setUpdateUser(userProfile.getUserId().toString());
                currencyView.doAction(connection, "UPDATE", CurrencyDetails.FILL_TYPE_ALL, currencyDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/currency/currencyActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateCurrency.
     */
    private void displayUpdateCurrency() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the currency already exist
            CurrencyDetails currencyDetails = new CurrencyDetails();
            currencyDetails.setCurrencyId(new BigDecimal(request.getParameter("dfCurrencyId")));

            CurrencyView currencyView = new CurrencyView();
            currencyView.fillWithElements(connection, CurrencyDetails.FILL_TYPE_ALL, currencyDetails);

            try {
                if (currencyView.getElements().size() == 1) {
                    currencyDetails = (CurrencyDetails) currencyView.getElements().elementAt(0);
                    request.setAttribute("currencyDetails", currencyDetails);
                } else {
                    throw new ModelException("Currency code cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/currency/displayInsertUpdateCurrency.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertCurrency.
     */
    private void displayInsertCurrency() {
        try {
            request.setAttribute("displayInsert", new Boolean(true));
            // forward request
            request.getRequestDispatcher("/jsp/admin/currency/displayInsertUpdateCurrency.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteCurrency.
     */
    private void deleteCurrency() {
        try {
            openConnection();

            // check if currency exist
            CurrencyDetails currencyDetails = new CurrencyDetails();
            currencyDetails.setCurrencyId(new BigDecimal(request.getParameter("dfCurrencyId")));

            CurrencyView currencyView = new CurrencyView();
            currencyView.fillWithElements(connection, CurrencyDetails.FILL_TYPE_ALL, currencyDetails);

            try {
                if (currencyView.getElements().size() == 0) {
                    throw new ModelException("The currency code you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete currency

                try {
                    currencyView.doAction(connection, "DELETE", null, currencyDetails);
                } catch (SQLException sqlE) {
                    throw new ModelException("The currency code cannot be deleted. <br>It is currently in use.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/currency/currencyActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
