package com.randr.webdw.systemType;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 */
public class SystemTypeController extends AbstractController {
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
            displaySystemTypes();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertSystemType();
        } else if (formAction.equals("INSERT")) {
            insertSystemType();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateSystemType();
        } else if (formAction.equals("UPDATE")) {
            updateSystemType();
        } else if (formAction.equals("DELETE")) {
            deleteSystemType();
        }
    }

    /**
     * Method displaySystemTypes.
     */
    private void displaySystemTypes() {
        try {

            openConnection();

            SystemTypeView systemTypeView = new SystemTypeView();
            systemTypeView.fillWithElements(connection, SystemTypeDetails.FILL_TYPE_ALL, new SystemTypeDetails());
            try {
                if (systemTypeView.getElements().size() > 0) {
                    request.setAttribute("systemTypeView", systemTypeView);
                } else {
                    throw new ModelException("No system types found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/systemType/displaySystemTypes.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertSystemType.
     */
    private void insertSystemType() {
        try {
            openConnection();

            // check if the systemType already exist
            SystemTypeDetails systemTypeDetails = new SystemTypeDetails();
            systemTypeDetails.setSystemType(request.getParameter("dfSystemType"));

            SystemTypeView systemTypeView = new SystemTypeView();
            systemTypeView.fillWithElements(connection, SystemTypeDetails.FILL_TYPE_ALL, systemTypeDetails);

            try {
                if (systemTypeView.getElements().size() > 0) {
                    throw new ModelException("A system type with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert systemType
                systemTypeView.doAction(connection, "INSERT", SystemTypeDetails.FILL_TYPE_ALL, systemTypeDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/systemType/systemTypeActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateSystemType.
     */
    private void updateSystemType() {
        try {
            openConnection();

            // check if the systemType already exist
            SystemTypeDetails systemTypeDetails = new SystemTypeDetails();
            systemTypeDetails.setSystemType(request.getParameter("dfSystemType"));

            SystemTypeView systemTypeView = new SystemTypeView();
            systemTypeView.fillWithElements(connection, SystemTypeDetails.FILL_TYPE_ALL, systemTypeDetails);

            try {
                if (systemTypeView.getElements().size() > 0) {
                    throw new ModelException("A system type with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert systemType
                systemTypeDetails.setSystemTypeId(new BigDecimal(request.getParameter("dfSystemTypeId")));
                systemTypeView.doAction(connection, "UPDATE", SystemTypeDetails.FILL_TYPE_ALL, systemTypeDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/systemType/systemTypeActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateSystemType.
     */
    private void displayUpdateSystemType() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the systemType already exist
            SystemTypeDetails systemTypeDetails = new SystemTypeDetails();
            systemTypeDetails.setSystemTypeId(new BigDecimal(request.getParameter("dfSystemTypeId")));

            SystemTypeView systemTypeView = new SystemTypeView();
            systemTypeView.fillWithElements(connection, SystemTypeDetails.FILL_TYPE_ALL, systemTypeDetails);

            try {
                if (systemTypeView.getElements().size() == 1) {
                    systemTypeDetails = (SystemTypeDetails) systemTypeView.getElements().elementAt(0);
                    request.setAttribute("systemTypeDetails", systemTypeDetails);
                } else {
                    throw new ModelException("System type cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/systemType/displayInsertUpdateSystemType.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertSystemType.
     */
    private void displayInsertSystemType() {
        try {
            request.setAttribute("displayInsert", new Boolean(true));
            // forward request
            request.getRequestDispatcher("/jsp/admin/systemType/displayInsertUpdateSystemType.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteSystemType.
     */
    private void deleteSystemType() {
        try {
            openConnection();

            // check if systemType exist
            SystemTypeDetails systemTypeDetails = new SystemTypeDetails();
            systemTypeDetails.setSystemTypeId(new BigDecimal(request.getParameter("dfSystemTypeId")));

            SystemTypeView systemTypeView = new SystemTypeView();
            systemTypeView.fillWithElements(connection, SystemTypeDetails.FILL_TYPE_ALL, systemTypeDetails);

            try {
                if (systemTypeView.getElements().size() == 0) {
                    throw new ModelException("The system type you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete systemType

                try {
                    systemTypeView.doAction(connection, "DELETE", null, systemTypeDetails);
                } catch (SQLException sqlE) {
                    throw new ModelException("The system type cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/systemType/systemTypeActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
