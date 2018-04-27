package com.randr.webdw.state;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.country.CountryDetails;
import com.randr.webdw.country.CountryException;
import com.randr.webdw.country.CountryView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 */
public class StateController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
        if ((formAction == null) || (formAction.equals("DISPLAY_SEARCH"))) {
            displaySearchStates();
        } else if (formAction.equals("DISPLAY")) {
            displayStates();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertState();
        } else if (formAction.equals("INSERT")) {
            insertState();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateState();
        } else if (formAction.equals("UPDATE")) {
            updateState();
        } else if (formAction.equals("DELETE")) {
            deleteState();
        }
    }

    /**
     * Method displaySearchStates.
     */
    private void displaySearchStates() {
        try {
            openConnection();

            CountryView countryView = new CountryView();
            countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, new CountryDetails());
            try {
                if (countryView.getElements().size() > 0) {
                    request.setAttribute("countryView", countryView);
                } else {
                    throw new ModelException("No countries found in the database.", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/state/displaySearchStates.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayStates.
     */
    private void displayStates() {
        try {

            openConnection();

            StateDetails stateDetails = new StateDetails();
            if (request.getParameter("dfCountryId") != null && (!request.getParameter("dfCountryId").equals(""))) {
                stateDetails.setCountryId(new BigDecimal(request.getParameter("dfCountryId")));
            }

            StateView stateView = new StateView();
            stateView.fillWithElements(connection, StateDetails.FILL_TYPE_ALL, stateDetails);

            CountryDetails countryDetails = new CountryDetails();
            countryDetails.setCountryId(stateDetails.getCountryId());
            CountryView countryView = new CountryView();
            countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, countryDetails);
            try {
                if (countryView.getElements().size() == 1) {
                    countryDetails = (CountryDetails) countryView.getElements().elementAt(0);
                    request.setAttribute("countryDetails", countryDetails);

                    if (stateView.getElements().size() > 0) {
                        request.setAttribute("stateView", stateView);
                    } else {
                        throw new ModelException("No states found in the database for the selected country", ModelException.RECORD_NOT_FOUND);
                    }
                } else {
                    throw new CountryException("The country you selected has probably just been deleted by another user.", ModelException.RECORD_NOT_FOUND);
                }
            } catch (CountryException countryException) {
                request.setAttribute("countryException", countryException);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/state/displayStates.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertState.
     */
    private void insertState() {
        try {
            openConnection();

            // check if the state already exist
            StateDetails stateDetails = new StateDetails();
            stateDetails.setState(request.getParameter("dfState"));
            stateDetails.setStateCode(request.getParameter("dfStateCode"));
            stateDetails.setCountryId(new BigDecimal(request.getParameter("dfCountryId")));

            StateView stateView = new StateView();
            stateView.fillWithElements(connection, StateDetails.FILL_TYPE_ALL, stateDetails);

            try {
                if (stateView.getElements().size() > 0) {
                    throw new ModelException("A state with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert state
                stateView.doAction(connection, "INSERT", StateDetails.FILL_TYPE_ALL, stateDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/state/stateActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateState.
     */
    private void updateState() {
        try {
            openConnection();

            // check if the state already exist
            StateDetails stateDetails = new StateDetails();
            stateDetails.setState(request.getParameter("dfState"));
            stateDetails.setStateCode(request.getParameter("dfStateCode"));
            stateDetails.setCountryId(new BigDecimal(request.getParameter("dfCountryId")));

            StateView stateView = new StateView();
            stateView.fillWithElements(connection, StateDetails.FILL_TYPE_ALL, stateDetails);

            try {
                if (stateView.getElements().size() > 0) {
                    throw new ModelException("A state with this name and code has already been created or you did not make any changes.", ModelException.DUPLICATE_RECORD);
                }

                // insert state
                stateDetails.setStateId(new BigDecimal(request.getParameter("dfStateId")));
                stateView.doAction(connection, "UPDATE", StateDetails.FILL_TYPE_ALL, stateDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/state/stateActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateState.
     */
    private void displayUpdateState() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            CountryView countryView = new CountryView();
            countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, new CountryDetails());
            try {
                if (countryView.getElements().size() > 0) {
                    request.setAttribute("countryView", countryView);

                    // check if the state exists
                    StateDetails stateDetails = new StateDetails();
                    stateDetails.setStateId(new BigDecimal(request.getParameter("dfStateId")));

                    StateView stateView = new StateView();
                    stateView.fillWithElements(connection, StateDetails.FILL_TYPE_ALL, stateDetails);

                    try {
                        if (stateView.getElements().size() == 1) {
                            stateDetails = (StateDetails) stateView.getElements().elementAt(0);
                            request.setAttribute("stateDetails", stateDetails);
                        } else {
                            throw new ModelException("State cannot be found", ModelException.RECORD_NOT_FOUND);
                        }
                    } catch (ModelException modelException) {
                        request.setAttribute("modelException", modelException);
                    }

                } else {
                    throw new ModelException("No countries found in the database.", ModelException.RECORD_NOT_FOUND);
                }
            } catch (CountryException countryException) {
                request.setAttribute("countryException", countryException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/state/displayInsertUpdateState.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertState.
     */
    private void displayInsertState() {
        try {
            request.setAttribute("displayInsert", new Boolean(true));
            openConnection();
            CountryView countryView = new CountryView();
            countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, new CountryDetails());
            try {
                if (countryView.getElements().size() > 0) {
                    request.setAttribute("countryView", countryView);
                } else {
                    throw new ModelException("No countries found in the database.", ModelException.RECORD_NOT_FOUND);

                }
            } catch (CountryException countryException) {
                request.setAttribute("countryException", countryException);
            }
            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/state/displayInsertUpdateState.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteState.
     */
    private void deleteState() {
        try {
            openConnection();

            // check if state exist
            StateDetails stateDetails = new StateDetails();
            stateDetails.setStateId(new BigDecimal(request.getParameter("dfStateId")));

            StateView stateView = new StateView();
            stateView.fillWithElements(connection, StateDetails.FILL_TYPE_ALL, stateDetails);

            try {
                if (stateView.getElements().size() == 0) {
                    throw new ModelException("The state you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete state

                try {
                    stateView.doAction(connection, "DELETE", null, stateDetails);
                } catch (SQLException sqlE) {
                    throw new ModelException("The state cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/state/stateActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
