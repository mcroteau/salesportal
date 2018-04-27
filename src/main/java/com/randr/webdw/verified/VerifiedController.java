package com.randr.webdw.verified;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 */
public class VerifiedController extends AbstractController {
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
            displayVerifications();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertVerified();
        } else if (formAction.equals("INSERT")) {
            insertVerified();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateVerified();
        } else if (formAction.equals("UPDATE")) {
            updateVerified();
        } else if (formAction.equals("DELETE")) {
            deleteVerified();
        }
    }

    /**
     * Method displayVerifications.
     */
    private void displayVerifications() {
        try {

            openConnection();

            VerifiedView verifiedView = new VerifiedView();
            verifiedView.fillWithElements(connection, VerifiedDetails.FILL_TYPE_ALL, new VerifiedDetails());
            try {
                if (verifiedView.getElements().size() > 0) {
                    request.setAttribute("verifiedView", verifiedView);
                } else {
                    throw new ModelException("No verifications found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/verified/displayVerifications.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertVerified.
     */
    private void insertVerified() {
        try {
            openConnection();

            // check if the verified already exist
            VerifiedDetails verifiedDetails = new VerifiedDetails();
            verifiedDetails.setVerified(request.getParameter("dfVerified"));

            VerifiedView verifiedView = new VerifiedView();
            verifiedView.fillWithElements(connection, VerifiedDetails.FILL_TYPE_ALL, verifiedDetails);

            try {
                if (verifiedView.getElements().size() > 0) {
                    throw new ModelException("A verified with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert verified
                verifiedView.doAction(connection, "INSERT", VerifiedDetails.FILL_TYPE_ALL, verifiedDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/verified/verifiedActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateVerified.
     */
    private void updateVerified() {
        try {
            openConnection();

            // check if the verified already exist
            VerifiedDetails verifiedDetails = new VerifiedDetails();
            verifiedDetails.setVerified(request.getParameter("dfVerified"));

            VerifiedView verifiedView = new VerifiedView();
            verifiedView.fillWithElements(connection, VerifiedDetails.FILL_TYPE_ALL, verifiedDetails);

            try {
                if (verifiedView.getElements().size() > 0) {
                    throw new ModelException("A verified with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert verified
                verifiedDetails.setVerifiedId(new BigDecimal(request.getParameter("dfVerifiedId")));
                verifiedView.doAction(connection, "UPDATE", VerifiedDetails.FILL_TYPE_ALL, verifiedDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/verified/verifiedActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateVerified.
     */
    private void displayUpdateVerified() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the verified already exist
            VerifiedDetails verifiedDetails = new VerifiedDetails();
            verifiedDetails.setVerifiedId(new BigDecimal(request.getParameter("dfVerifiedId")));

            VerifiedView verifiedView = new VerifiedView();
            verifiedView.fillWithElements(connection, VerifiedDetails.FILL_TYPE_ALL, verifiedDetails);

            try {
                if (verifiedView.getElements().size() == 1) {
                    verifiedDetails = (VerifiedDetails) verifiedView.getElements().elementAt(0);
                    request.setAttribute("verifiedDetails", verifiedDetails);
                } else {
                    throw new ModelException("Verified cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/verified/displayInsertUpdateVerified.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertVerified.
     */
    private void displayInsertVerified() {
        try {
            request.setAttribute("displayInsert", new Boolean(true));
            // forward request
            request.getRequestDispatcher("/jsp/admin/verified/displayInsertUpdateVerified.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteVerified.
     */
    private void deleteVerified() {
        try {
            openConnection();

            // check if verified exist
            VerifiedDetails verifiedDetails = new VerifiedDetails();
            verifiedDetails.setVerifiedId(new BigDecimal(request.getParameter("dfVerifiedId")));

            VerifiedView verifiedView = new VerifiedView();
            verifiedView.fillWithElements(connection, VerifiedDetails.FILL_TYPE_ALL, verifiedDetails);

            try {
                if (verifiedView.getElements().size() == 0) {
                    throw new ModelException("The verify status you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete verified

                try {
                    verifiedView.doAction(connection, "DELETE", null, verifiedDetails);
                } catch (SQLException sqlE) {
                    throw new ModelException("The verify status cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/verified/verifiedActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
