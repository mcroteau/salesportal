package com.randr.webdw.size;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 */
public class SizeController extends AbstractController {
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
            displaySizes();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertSize();
        } else if (formAction.equals("INSERT")) {
            insertSize();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateSize();
        } else if (formAction.equals("UPDATE")) {
            updateSize();
        } else if (formAction.equals("DELETE")) {
            deleteSize();
        }
    }

    /**
     * Method displaySizes.
     */
    private void displaySizes() {
        try {

            openConnection();

            SizeView sizeView = new SizeView();
            sizeView.fillWithElements(connection, SizeDetails.FILL_TYPE_ALL, new SizeDetails());
            try {
                if (sizeView.getElements().size() > 0) {
                    request.setAttribute("sizeView", sizeView);
                } else {
                    throw new ModelException("No sizes found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/size/displaySizes.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertSize.
     */
    private void insertSize() {
        try {
            openConnection();

            // check if the size already exist
            SizeDetails sizeDetails = new SizeDetails();
            sizeDetails.setSize(request.getParameter("dfSize"));

            SizeView sizeView = new SizeView();
            sizeView.fillWithElements(connection, SizeDetails.FILL_TYPE_ALL, sizeDetails);

            try {
                if (sizeView.getElements().size() > 0) {
                    throw new ModelException("A size with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert size
                sizeView.doAction(connection, "INSERT", SizeDetails.FILL_TYPE_ALL, sizeDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/size/sizeActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateSize.
     */
    private void updateSize() {
        try {
            openConnection();

            // check if the size already exist
            SizeDetails sizeDetails = new SizeDetails();
            sizeDetails.setSize(request.getParameter("dfSize"));

            SizeView sizeView = new SizeView();
            sizeView.fillWithElements(connection, SizeDetails.FILL_TYPE_ALL, sizeDetails);

            try {
                if (sizeView.getElements().size() > 0) {
                    throw new ModelException("A size with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert size
                sizeDetails.setSizeId(new BigDecimal(request.getParameter("dfSizeId")));
                sizeView.doAction(connection, "UPDATE", SizeDetails.FILL_TYPE_ALL, sizeDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/size/sizeActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateSize.
     */
    private void displayUpdateSize() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the size already exist
            SizeDetails sizeDetails = new SizeDetails();
            sizeDetails.setSizeId(new BigDecimal(request.getParameter("dfSizeId")));

            SizeView sizeView = new SizeView();
            sizeView.fillWithElements(connection, SizeDetails.FILL_TYPE_ALL, sizeDetails);

            try {
                if (sizeView.getElements().size() == 1) {
                    sizeDetails = (SizeDetails) sizeView.getElements().elementAt(0);
                    request.setAttribute("sizeDetails", sizeDetails);
                } else {
                    throw new ModelException("Size cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/size/displayInsertUpdateSize.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertSize.
     */
    private void displayInsertSize() {
        try {
            request.setAttribute("displayInsert", new Boolean(true));
            // forward request
            request.getRequestDispatcher("/jsp/admin/size/displayInsertUpdateSize.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteSize.
     */
    private void deleteSize() {
        try {
            openConnection();

            // check if size exist
            SizeDetails sizeDetails = new SizeDetails();
            sizeDetails.setSizeId(new BigDecimal(request.getParameter("dfSizeId")));

            SizeView sizeView = new SizeView();
            sizeView.fillWithElements(connection, SizeDetails.FILL_TYPE_ALL, sizeDetails);

            try {
                if (sizeView.getElements().size() == 0) {
                    throw new ModelException("The size you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete size

                try {
                    sizeView.doAction(connection, "DELETE", null, sizeDetails);
                } catch (SQLException sqlE) {
                    throw new ModelException("The size code cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/size/sizeActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
