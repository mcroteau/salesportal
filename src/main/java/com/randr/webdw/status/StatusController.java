package com.randr.webdw.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.userStatuses.UserStatusesDetails;
import com.randr.webdw.userStatuses.UserStatusesView;


/**
 */
public class StatusController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
        if ((formAction == null) || (formAction.equals("DISPLAY"))) {
            displayStatuses();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertStatus();
        } else if (formAction.equals("INSERT")) {
            insertStatus();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateStatus();
        } else if (formAction.equals("UPDATE")) {
            updateStatus();
        } else if (formAction.equals("DELETE")) {
            deleteStatus();
        }
    }

    /**
     * Method displayStatuses.
     */
    private void displayStatuses() {
        try {

            openConnection();

            StatusView statusView = new StatusView();
            statusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, new StatusDetails());
            try {
                if (statusView.getElements().size() > 0) {
                    request.setAttribute("statusView", statusView);
                } else {
                    throw new ModelException("No statuses found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/status/displayStatuses.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertStatus.
     */
    private void insertStatus() {
        try {
            openConnection();

            // check if the status already exist
            StatusDetails statusDetails = new StatusDetails();
            statusDetails.setStatus(request.getParameter("dfStatus"));

            StatusView statusView = new StatusView();
            statusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, statusDetails);

            try {
                if (statusView.getElements().size() > 0) {
                    throw new ModelException("A status with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert status
                statusView.doAction(connection, "INSERT", StatusDetails.FILL_TYPE_ALL, statusDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/status/statusActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateStatus.
     */
    private void updateStatus() {
        try {
            openConnection();

            // check if the status already exist
            StatusDetails statusDetails = new StatusDetails();
            statusDetails.setStatus(request.getParameter("dfStatus"));

            StatusView statusView = new StatusView();
            statusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, statusDetails);

            try {
                if (statusView.getElements().size() > 0) {
                    throw new ModelException("A status with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert status
                statusDetails.setStatusId(new BigDecimal(request.getParameter("dfStatusId")));
                statusView.doAction(connection, "UPDATE", StatusDetails.FILL_TYPE_ALL, statusDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/status/statusActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateStatus.
     */
    private void displayUpdateStatus() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the status already exist
            StatusDetails statusDetails = new StatusDetails();
            statusDetails.setStatusId(new BigDecimal(request.getParameter("dfStatusId")));

            StatusView statusView = new StatusView();
            statusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, statusDetails);

            try {
                if (statusView.getElements().size() == 1) {
                    statusDetails = (StatusDetails) statusView.getElements().elementAt(0);
                    request.setAttribute("statusDetails", statusDetails);
                } else {
                    throw new ModelException("Status cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/status/displayInsertUpdateStatus.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertStatus.
     */
    private void displayInsertStatus() {
        try {
            request.setAttribute("displayInsert", new Boolean(true));
            // forward request
            request.getRequestDispatcher("/jsp/admin/status/displayInsertUpdateStatus.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteStatus.
     */
    private void deleteStatus() {
        try {
            openConnection();

            // check if status exist
            StatusDetails statusDetails = new StatusDetails();
            statusDetails.setStatusId(new BigDecimal(request.getParameter("dfStatusId")));

            StatusView statusView = new StatusView();
            statusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, statusDetails);

            try {
                if (statusView.getElements().size() == 0) {
                    throw new ModelException("The status you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete status

                try {
                    statusView.doAction(connection, "DELETE", null, statusDetails);
                    
                    UserStatusesView userStatusesView = new UserStatusesView();
                    UserStatusesDetails userStatusesDetails = new UserStatusesDetails();
                    
                    userStatusesDetails.setStatusId(statusDetails.getStatusId());
                    userStatusesView.fillWithElements(connection, UserStatusesDetails.FILL_TYPE_ALL, userStatusesDetails);
                    
                    for(int i = 0; i < userStatusesView.getElements().size(); i++){
                    	userStatusesDetails = (UserStatusesDetails) userStatusesView.getElement(i);
                    	userStatusesView.doAction(connection, "DELETE", null, userStatusesDetails);
                    }
                    
                } catch (SQLException sqlE) {
                    throw new ModelException("The status code cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/status/statusActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
