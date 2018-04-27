package com.randr.webdw.softwareType;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 */
public class SoftwareTypeController extends AbstractController {
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
            displaySoftwareTypes();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertSoftwareType();
        } else if (formAction.equals("INSERT")) {
            insertSoftwareType();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateSoftwareType();
        } else if (formAction.equals("UPDATE")) {
            updateSoftwareType();
        } else if (formAction.equals("DELETE")) {
            deleteSoftwareType();
        }
    }

    /**
     * Method displaySoftwareTypes.
     */
    private void displaySoftwareTypes() {
        try {

            openConnection();

            SoftwareTypeView softwareTypeView = new SoftwareTypeView();
            softwareTypeView.fillWithElements(connection, SoftwareTypeDetails.FILL_TYPE_ALL, new SoftwareTypeDetails());
            try {
                if (softwareTypeView.getElements().size() > 0) {
                    request.setAttribute("softwareTypeView", softwareTypeView);
                } else {
                    throw new ModelException("No software types found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/softwareType/displaySoftwareTypes.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertSoftwareType.
     */
    private void insertSoftwareType() {
        try {
            openConnection();

            // check if the softwareType already exist
            SoftwareTypeDetails softwareTypeDetails = new SoftwareTypeDetails();
            softwareTypeDetails.setSoftwareType(request.getParameter("dfSoftwareType"));

            SoftwareTypeView softwareTypeView = new SoftwareTypeView();
            softwareTypeView.fillWithElements(connection, SoftwareTypeDetails.FILL_TYPE_ALL, softwareTypeDetails);

            try {
                if (softwareTypeView.getElements().size() > 0) {
                    throw new ModelException("A software type with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert softwareType
                softwareTypeView.doAction(connection, "INSERT", SoftwareTypeDetails.FILL_TYPE_ALL, softwareTypeDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/softwareType/softwareTypeActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateSoftwareType.
     */
    private void updateSoftwareType() {
        try {
            openConnection();

            // check if the softwareType already exist
            SoftwareTypeDetails softwareTypeDetails = new SoftwareTypeDetails();
            softwareTypeDetails.setSoftwareType(request.getParameter("dfSoftwareType"));

            SoftwareTypeView softwareTypeView = new SoftwareTypeView();
            softwareTypeView.fillWithElements(connection, SoftwareTypeDetails.FILL_TYPE_ALL, softwareTypeDetails);

            try {
                if (softwareTypeView.getElements().size() > 0) {
                    throw new ModelException("A software type with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert softwareType
                softwareTypeDetails.setSoftwareTypeId(new BigDecimal(request.getParameter("dfSoftwareTypeId")));
                softwareTypeView.doAction(connection, "UPDATE", SoftwareTypeDetails.FILL_TYPE_ALL, softwareTypeDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/softwareType/softwareTypeActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateSoftwareType.
     */
    private void displayUpdateSoftwareType() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the softwareType already exist
            SoftwareTypeDetails softwareTypeDetails = new SoftwareTypeDetails();
            softwareTypeDetails.setSoftwareTypeId(new BigDecimal(request.getParameter("dfSoftwareTypeId")));

            SoftwareTypeView softwareTypeView = new SoftwareTypeView();
            softwareTypeView.fillWithElements(connection, SoftwareTypeDetails.FILL_TYPE_ALL, softwareTypeDetails);

            try {
                if (softwareTypeView.getElements().size() == 1) {
                    softwareTypeDetails = (SoftwareTypeDetails) softwareTypeView.getElements().elementAt(0);
                    request.setAttribute("softwareTypeDetails", softwareTypeDetails);
                } else {
                    throw new ModelException("Software type cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/softwareType/displayInsertUpdateSoftwareType.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertSoftwareType.
     */
    private void displayInsertSoftwareType() {
        try {
            request.setAttribute("displayInsert", new Boolean(true));
            // forward request
            request.getRequestDispatcher("/jsp/admin/softwareType/displayInsertUpdateSoftwareType.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteSoftwareType.
     */
    private void deleteSoftwareType() {
        try {
            openConnection();

            // check if softwareType exist
            SoftwareTypeDetails softwareTypeDetails = new SoftwareTypeDetails();
            softwareTypeDetails.setSoftwareTypeId(new BigDecimal(request.getParameter("dfSoftwareTypeId")));

            SoftwareTypeView softwareTypeView = new SoftwareTypeView();
            softwareTypeView.fillWithElements(connection, SoftwareTypeDetails.FILL_TYPE_ALL, softwareTypeDetails);

            try {
                if (softwareTypeView.getElements().size() == 0) {
                    throw new ModelException("The software type you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete softwareType

                try {
                    softwareTypeView.doAction(connection, "DELETE", null, softwareTypeDetails);
                } catch (SQLException sqlE) {
                    throw new ModelException("The software type cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/softwareType/softwareTypeActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
