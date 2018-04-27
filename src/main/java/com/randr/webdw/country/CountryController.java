package com.randr.webdw.country;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CountryController extends AbstractController {
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
            displayCountries();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertCountry();
        } else if (formAction.equals("INSERT")) {
            insertCountry();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateCountry();
        } else if (formAction.equals("UPDATE")) {
            updateCountry();
        } else if (formAction.equals("DELETE")) {
            deleteCountry();
        }
    }

    /**
     * Method displayCountries.
     */
    private void displayCountries() {
        try {

            openConnection();

            CountryView countryView = new CountryView();
            countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, new CountryDetails());
            try {
                if (countryView.getElements().size() > 0) {
                    request.setAttribute("countryView", countryView);
                } else {
                    throw new ModelException("No countries found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/country/displayCountries.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertCountry.
     */
    private void insertCountry() {
        try {
            openConnection();

            // check if the country already exist
            CountryDetails countryDetails = new CountryDetails();
            countryDetails.setCountry(request.getParameter("dfCountry"));
            countryDetails.setCountryCode(request.getParameter("dfCountryCode"));

            CountryView countryView = new CountryView();
            countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, countryDetails);

            try {
                if (countryView.getElements().size() > 0) {
                    throw new ModelException("A country with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert country
                countryView.doAction(connection, "INSERT", CountryDetails.FILL_TYPE_ALL, countryDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/country/countryActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateCountry.
     */
    private void updateCountry() {
        try {
            openConnection();

            // check if the country already exist
            CountryDetails countryDetails = new CountryDetails();
            countryDetails.setCountry(request.getParameter("dfCountry"));
            countryDetails.setCountryCode(request.getParameter("dfCountryCode"));

            CountryView countryView = new CountryView();
            countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, countryDetails);

            try {
                if (countryView.getElements().size() > 0) {
                    throw new ModelException("A country with this name and code has already been created or you did not make any changes.", ModelException.DUPLICATE_RECORD);
                }

                // insert country
                countryDetails.setCountryId(new BigDecimal(request.getParameter("dfCountryId")));
                countryView.doAction(connection, "UPDATE", CountryDetails.FILL_TYPE_ALL, countryDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/country/countryActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateCountry.
     */
    private void displayUpdateCountry() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the country already exist
            CountryDetails countryDetails = new CountryDetails();
            countryDetails.setCountryId(new BigDecimal(request.getParameter("dfCountryId")));

            CountryView countryView = new CountryView();
            countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, countryDetails);

            try {
                if (countryView.getElements().size() == 1) {
                    countryDetails = (CountryDetails) countryView.getElements().elementAt(0);
                    request.setAttribute("countryDetails", countryDetails);
                } else {
                    throw new ModelException("Country cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/country/displayInsertUpdateCountry.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertCountry.
     */
    private void displayInsertCountry() {
        try {
            request.setAttribute("displayInsert", new Boolean(true));
            // forward request
            request.getRequestDispatcher("/jsp/admin/country/displayInsertUpdateCountry.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteCountry.
     */
    private void deleteCountry() {
        try {
            openConnection();

            // check if country exist
            CountryDetails countryDetails = new CountryDetails();
            countryDetails.setCountryId(new BigDecimal(request.getParameter("dfCountryId")));

            CountryView countryView = new CountryView();
            countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, countryDetails);

            try {
                if (countryView.getElements().size() == 0) {
                    throw new ModelException("The country you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete country

                try {
                    countryView.doAction(connection, "DELETE", null, countryDetails);
                } catch (SQLException sqlE) {
                    throw new ModelException("The country cannot be deleted. <br>The most probable reason for this is that there are states, users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/country/countryActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
