package com.randr.webdw.label;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 * @author randr
 * @version $Revision$
 */
public class LabelController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	if (isAdmin()){
    		redirectIfNotValidAdminUserProfile();
    	}
    	if(formAction.equals("DISPLAY")) {
    		displayLabels();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateLabel();
        } else if (formAction.equals("UPDATE")) {
            updateLabel();
        } 

    }

	private void updateLabel() {
	       try {
	            openConnection();

	            // check if the label already exist
	            LabelDetails labelDetails = new LabelDetails();
	            labelDetails.setLabelDefault(request.getParameter("dfLabelDefault"));
	            labelDetails.setLabelActual(request.getParameter("dfLabelActual"));
	            labelDetails.setLabelId(new BigDecimal(request.getParameter("dfLabelId")));


	            LabelView labelView = new LabelView();
	            try {
	                labelView.doAction(connection, "UPDATE", LabelDetails.FILL_TYPE_ALL, labelDetails);
	            } catch (ModelException modelException) {
	                request.setAttribute("modelException", modelException);
	            }

	            commit();
	            updateLabelViewInApplicationContext();
	            // forward request
	            forward("/jsp/admin/label/labelActionResult.jsp");
	        } catch (Exception e) {
	            doCatch(e);
	        } finally {
	            finallyMethod();
	        }
		
	}

	private void updateLabelViewInApplicationContext() throws Exception {
		ServletContext context = request.getSession().getServletContext();
		context.removeAttribute("labelView");
		LabelView labelView = new LabelView();
		labelView.fillWithElements(connection, LabelDetails.FILL_TYPE_ALL, new LabelDetails());
		context.setAttribute("labelView", labelView);	
	}

	private void displayUpdateLabel() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the country already exist
            LabelDetails labelDetails = new LabelDetails();
            labelDetails.setLabelId(new BigDecimal(request.getParameter("dfLabelId")));

            LabelView labelView = new LabelView();
            labelView.fillWithElements(connection, LabelDetails.FILL_TYPE_ALL, labelDetails);

            try {
                if (labelView.getElements().size() == 1) {
                    labelDetails = (LabelDetails) labelView.getElements().elementAt(0);
                    request.setAttribute("labelDetails", labelDetails);
                } else {
                    throw new ModelException("Country cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/label/displayInsertUpdateLabel.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}

	private void displayLabels() {
        try {

            openConnection();

            LabelView labelView = new LabelView();
            labelView.fillWithElements(connection, LabelDetails.FILL_TYPE_ALL, new LabelDetails());
            try {
                if (labelView.getElements().size() > 0) {
                    request.setAttribute("labelView", labelView);
                } else {
                    throw new ModelException("No labels found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/label/displayLabels.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
