package com.randr.webdw.prospect;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.note.NoteDetails;
import com.randr.webdw.note.NoteView;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionView;


/**
 */
public class ProspectPrintController extends ProspectBaseController {

    /**
     * Constructor for ProspectPrintController.
     */
    public ProspectPrintController() {
    }

    /**
     * Method doAction.
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    public void doAction() throws java.io.IOException, javax.servlet.ServletException {
        if (formAction.equals("DISPLAY_PRINT_FRAMESET")) {
            displayPrintFrameset();
        } else if (formAction.equals("DISPLAY_PRINT_TOP_FRAME")) {
            displayPrintTopFrame();
        } else if (formAction.equals("DISPLAY_PRINT_PROSPECT_DETAILS")) {
            displayPrintProspectDetails();
        }
    }


    /**
     * Method displayPrintFrameset.
     */
    private void displayPrintFrameset() {
        try {
            request.getRequestDispatcher("/jsp/public/prospect/displayPrintFrames.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        }
    }

    /**
     * Method displayPrintProspectDetails.
     */
    private void displayPrintProspectDetails() {
         try {             
             openConnection();
             try {
                 ProspectDetails prospectDetails = new ProspectDetails();
                 prospectDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));

                 ProspectView prospectView = new ProspectView();
                 prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_PRINT, prospectDetails);

                 if (prospectView.getElements().size() != 1) {
                     throw new ModelException("Prospect not found.", ModelException.RECORD_NOT_FOUND);
                 }
                 prospectDetails = (ProspectDetails) prospectView.getElements().elementAt(0);
                 request.setAttribute("prospectDetails", prospectDetails);

                 NoteView noteView = new NoteView();
                 NoteDetails noteDetails = new NoteDetails();

                 noteDetails.setProspectId(prospectDetails.getProspectId());
                 noteView.fillWithElements(connection, NoteDetails.FILL_TYPE_ALL, noteDetails);
                 request.setAttribute("noteView", noteView);
                 
                 ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
                 ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();

                 prospectSalesActionDetails.setProspectId(prospectDetails.getProspectId());
                 prospectSalesActionView.fillWithElements(connection, NoteDetails.FILL_TYPE_ALL, prospectSalesActionDetails);
                 request.setAttribute("prospectSalesActionView", prospectSalesActionView);
                 
                 request.setAttribute("userProfile", userProfile);
                 
                 commit();

             } catch (ModelException modelException) {
                 request.setAttribute("modelException", modelException);
             }

             commit();
             request.getRequestDispatcher("/jsp/public/prospect/displayPrintProspectDetails.jsp").forward(request, response);
         } catch (Exception e) {
             doCatch(e);
         } finally {
             finallyMethod();
         }
     }

     /**
      * Method displayPrintTopFrame.
      */
     private void displayPrintTopFrame() {
         try {
             request.getRequestDispatcher("/jsp/public/prospect/displayPrintTopFrame.jsp").forward(request, response);
         } catch (Exception e) {
             doCatch(e);
         }
     }

}
