package com.randr.webdw.contact;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.util.DateUtilities;

/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class ContactController extends AbstractController {
    /**
     * Constructor for ContactController.
     */
    public ContactController() {
    }

    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
        if ((formAction == null) || (formAction.equals("INSERT"))) {
            insertContact();
        } else if (formAction.equals("UPDATE")) {
            updateContact();
        } else if (formAction.equals("DELETE")) {
            deleteContact();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertContact();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateContact();
        } else if (formAction.equals("DISPLAY_DELETE")) {
            displayDeleteContact();
        }
    }

    /**
     * Method insertContact.
     */
    private void insertContact() {
        try {
            openConnection();

            ContactDetails contactDetails = new ContactDetails();
            ContactView contactView = new ContactView();
            try {
                ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
                if (prospectDetails == null) {
                    throw new ModelException("Session expired", ModelException.UNKNOWN_ERROR);
                }
                contactDetails.setProspectId(prospectDetails.getProspectId());
                getContactInformation(contactDetails);
                contactView.doAction(connection, "INSERT", ContactDetails.FILL_TYPE_ALL, contactDetails);

                //also, change the prospect Update Date and User
                ProspectDetails prospectUpdateDetails = new ProspectDetails();
                ProspectView prospectUpdateView = new ProspectView();
                prospectUpdateDetails.setProspectId(prospectDetails.getProspectId());
                prospectUpdateView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
                if(prospectUpdateView.getElements().size()>0){
                	prospectUpdateDetails = (ProspectDetails)prospectUpdateView.getElement(0);
                	prospectUpdateDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
                	prospectUpdateDetails.setChangeUserId(userProfile.getUserId());
                	prospectUpdateView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
                }

                commit();
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            request.getRequestDispatcher("/jsp/shared/contact/insertUpdateContact.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateContact.
     */
    private void updateContact() {
        try {
            openConnection();

            ContactDetails contactDetails = new ContactDetails();
            ContactView contactView = new ContactView();

            contactDetails.setContactId(new BigDecimal(request.getParameter("dfContactId")));
            contactDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
            getContactInformation(contactDetails);

            contactView.doAction(connection, "UPDATE", ContactDetails.FILL_TYPE_ALL, contactDetails);

            //also, change the prospect Update Date and User
            ProspectDetails prospectUpdateDetails = new ProspectDetails();
            ProspectView prospectUpdateView = new ProspectView();
            prospectUpdateDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
            prospectUpdateView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
            if(prospectUpdateView.getElements().size()>0){
            	prospectUpdateDetails = (ProspectDetails)prospectUpdateView.getElement(0);
            	prospectUpdateDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
            	prospectUpdateDetails.setChangeUserId(userProfile.getUserId());
            	prospectUpdateView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
            }

            commit();
            request.getRequestDispatcher("/jsp/shared/contact/insertUpdateContact.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteContact.
     */
    private void deleteContact() {
        try {
            openConnection();

            ContactDetails contactDetails = new ContactDetails();
            ContactView contactView = new ContactView();

            contactDetails.setContactId(new BigDecimal(request.getParameter("dfContactId")));
            contactView.doAction(connection, "DELETE", ContactDetails.FILL_TYPE_ALL, contactDetails);

            commit();
            request.getRequestDispatcher("/jsp/shared/contact/insertUpdateContact.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method getContactInformation.
     * @param contactDetails ContactDetails
     */
    private void getContactInformation(ContactDetails contactDetails) {
        if (request.getParameter("dfContactName") != null
                && !request.getParameter("dfContactName").equals("")) {
            contactDetails.setContactName(request.getParameter("dfContactName"));
        } else {
            contactDetails.setContactName(null);
        }

        if (request.getParameter("dfContactTitle") != null
                && !request.getParameter("dfContactTitle").equals("")) {
            contactDetails.setContactTitle(request.getParameter("dfContactTitle"));
        } else {
            contactDetails.setContactTitle(null);
        }

        if (request.getParameter("dfPhone") != null
                && !request.getParameter("dfPhone").equals("")) {
            contactDetails.setPhone(request.getParameter("dfPhone"));
        } else {
            contactDetails.setPhone(null);
        }

        if (request.getParameter("dfPhoneExt") != null
                && !request.getParameter("dfPhoneExt").equals("")) {
            contactDetails.setPhoneExt(request.getParameter("dfPhoneExt"));
        } else {
            contactDetails.setPhoneExt(null);
        }

        if (request.getParameter("dfCellPhone") != null
                && !request.getParameter("dfCellPhone").equals("")) {
            contactDetails.setCellPhone(request.getParameter("dfCellPhone"));
        } else {
            contactDetails.setCellPhone(null);
        }

        if (request.getParameter("dfFax") != null
                && !request.getParameter("dfFax").equals("")) {
            contactDetails.setFax(request.getParameter("dfFax"));
        } else {
            contactDetails.setFax(null);
        }

        if (request.getParameter("dfEmail") != null
                && !request.getParameter("dfEmail").equals("")) {
            contactDetails.setEmail(request.getParameter("dfEmail"));
        } else {
            contactDetails.setEmail(null);
        }
    }

    /**
     * Method displayInsertContact.
     */
    private void displayInsertContact() {
        try {
            request.getRequestDispatcher("/jsp/shared/contact/displayInsertUpdateContact.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateContact.
     */
    private void displayUpdateContact() {
        try {
            openConnection();
            ContactDetails contactDetails = new ContactDetails();
            contactDetails.setContactId(new BigDecimal(request.getParameter("dfContactId")));
            ContactView contactView = new ContactView();
            contactView.fillWithElements(connection, ContactDetails.FILL_TYPE_ALL, contactDetails);
            if (contactView.getElements().size() == 1) {
                contactDetails = (ContactDetails) contactView.getElements().elementAt(0);
                request.setAttribute("contactDetails", contactDetails);
            }
            commit();
            request.getRequestDispatcher("/jsp/shared/contact/displayInsertUpdateContact.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayDeleteContact.
     */
    private void displayDeleteContact() {
        try {
            openConnection();
            ContactDetails contactDetails = new ContactDetails();
            contactDetails.setContactId(new BigDecimal(request.getParameter("dfContactId")));
            ContactView contactView = new ContactView();
            contactView.fillWithElements(connection, ContactDetails.FILL_TYPE_ALL, contactDetails);
            if (contactView.getElements().size() == 1) {
                contactDetails = (ContactDetails) contactView.getElements().elementAt(0);
                request.setAttribute("contactDetails", contactDetails);
            }
            commit();
            request.getRequestDispatcher("/jsp/shared/contact/displayInsertUpdateContact.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
