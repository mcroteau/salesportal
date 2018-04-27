package com.randr.webdw.lob;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.userLobs.UserLobsDetails;
import com.randr.webdw.userLobs.UserLobsView;


/**
 */
public class LobController extends AbstractController {
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
            displayLobs();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertLob();
        } else if (formAction.equals("INSERT")) {
            insertLob();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateLob();
        } else if (formAction.equals("UPDATE")) {
            updateLob();
        } else if (formAction.equals("DELETE")) {
            deleteLob();
        }
    }

    /**
     * Method displayLobs.
     */
    private void displayLobs() {
        try {

            openConnection();

            LobView lobView = new LobView();
            lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, new LobDetails());
            try {
                if (lobView.getElements().size() > 0) {
                    request.setAttribute("lobView", lobView);
                } else {
                    throw new ModelException("No lines of business found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/lob/displayLobs.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertLob.
     */
    private void insertLob() {
        try {
            openConnection();

            // check if the lob already exist
            LobDetails lobDetails = new LobDetails();
            lobDetails.setLob(request.getParameter("dfLob"));

            LobView lobView = new LobView();
            lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, lobDetails);

            try {
                if (lobView.getElements().size() > 0) {
                    throw new ModelException("A line of business with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert lob
                lobView.doAction(connection, "INSERT", LobDetails.FILL_TYPE_ALL, lobDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/lob/lobActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateLob.
     */
    private void updateLob() {
        try {
            openConnection();

            // check if the lob already exist
            LobDetails lobDetails = new LobDetails();
            lobDetails.setLob(request.getParameter("dfLob"));

            LobView lobView = new LobView();
            lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, lobDetails);

            try {
                if (lobView.getElements().size() > 0) {
                    throw new ModelException("A line of business with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert lob
                lobDetails.setLobId(new BigDecimal(request.getParameter("dfLobId")));
                lobView.doAction(connection, "UPDATE", LobDetails.FILL_TYPE_ALL, lobDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/lob/lobActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateLob.
     */
    private void displayUpdateLob() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the lob already exist
            LobDetails lobDetails = new LobDetails();
            lobDetails.setLobId(new BigDecimal(request.getParameter("dfLobId")));

            LobView lobView = new LobView();
            lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, lobDetails);

            try {
                if (lobView.getElements().size() == 1) {
                    lobDetails = (LobDetails) lobView.getElements().elementAt(0);
                    request.setAttribute("lobDetails", lobDetails);
                } else {
                    throw new ModelException("Line of business cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/lob/displayInsertUpdateLob.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertLob.
     */
    private void displayInsertLob() {
        try {
            request.setAttribute("displayInsert", new Boolean(true));
            // forward request
            request.getRequestDispatcher("/jsp/admin/lob/displayInsertUpdateLob.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteLob.
     */
    private void deleteLob() {
        try {
            openConnection();

            // check if lob exist
            LobDetails lobDetails = new LobDetails();
            lobDetails.setLobId(new BigDecimal(request.getParameter("dfLobId")));

            LobView lobView = new LobView();
            lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, lobDetails);

            try {
                if (lobView.getElements().size() == 0) {
                    throw new ModelException("The line of business you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete lob

                try {
                    lobView.doAction(connection, "DELETE", null, lobDetails);
                    
                    UserLobsView userLobsView = new UserLobsView();
                    UserLobsDetails userLobsDetails = new UserLobsDetails();
                    
                    userLobsDetails.setLobId(lobDetails.getLobId());
                    userLobsView.fillWithElements(connection, UserLobsDetails.FILL_TYPE_ALL, userLobsDetails);
                    
                    for(int i = 0; i < userLobsView.getElements().size(); i++){
                    	userLobsDetails = (UserLobsDetails) userLobsView.getElement(i);
                    	userLobsView.doAction(connection, "DELETE", null, userLobsDetails);
                    }
                    
                } catch (SQLException sqlE) {
                    throw new ModelException("The line of bussinnes cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/lob/lobActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
