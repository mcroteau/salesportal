package com.randr.webdw.website;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospect.ProspectDetails;


/**
 */
public class WebsiteController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
        if ((formAction == null) || (formAction.equals("DISPLAY"))) {
            displayWebsites();
        } else if (formAction.equals("INSERT")) {
            insertWebsite();
        } else if (formAction.equals("UPDATE")) {
            updateWebsite();
        } else if (formAction.equals("DELETE")) {
            deleteWebsite();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertWebsite();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateWebsite();
        } else if (formAction.equals("DISPLAY_DELETE")) {
            displayDeleteWebsite();
        }
    }

    /**
     * Method displayInsertWebsite.
     */
    private void displayInsertWebsite() {
        try {
            request.getRequestDispatcher("/jsp/shared/website/displayInsertUpdateWebsite.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateWebsite.
     */
    private void displayUpdateWebsite() {
        try {
            openConnection();
            WebsiteDetails websiteDetails = new WebsiteDetails();
            websiteDetails.setWebsiteId(new BigDecimal(request.getParameter("dfWebsiteId")));
            WebsiteView websiteView = new WebsiteView();
            websiteView.fillWithElements(connection, WebsiteDetails.FILL_TYPE_ALL, websiteDetails);
            if (websiteView.getElements().size() == 1) {
                websiteDetails = (WebsiteDetails) websiteView.getElements().elementAt(0);
                request.setAttribute("websiteDetails", websiteDetails);
            }
            commit();
            request.getRequestDispatcher("/jsp/shared/website/displayInsertUpdateWebsite.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayDeleteWebsite.
     */
    private void displayDeleteWebsite() {
        try {
            openConnection();
            WebsiteDetails websiteDetails = new WebsiteDetails();
            websiteDetails.setWebsiteId(new BigDecimal(request.getParameter("dfWebsiteId")));
            WebsiteView websiteView = new WebsiteView();
            websiteView.fillWithElements(connection, WebsiteDetails.FILL_TYPE_ALL, websiteDetails);
            if (websiteView.getElements().size() == 1) {
                websiteDetails = (WebsiteDetails) websiteView.getElements().elementAt(0);
                request.setAttribute("websiteDetails", websiteDetails);
            }
            commit();
            request.getRequestDispatcher("/jsp/shared/website/displayInsertUpdateWebsite.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }


    /**
     * Method insertWebsite.
     */
    private void insertWebsite() {
        try {
            openConnection();

            WebsiteDetails websiteDetails = new WebsiteDetails();
            WebsiteView websiteView = new WebsiteView();
            try {
                ProspectDetails sessionProspectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");

                if (sessionProspectDetails == null) {
                    throw new ModelException("Session expired", ModelException.UNKNOWN_ERROR);
                }

                websiteDetails.setProspectId(sessionProspectDetails.getProspectId());
                websiteDetails.setUrl(request.getParameter("dfUrl"));
                websiteDetails.setDescription(request.getParameter("dfDescription"));

                websiteView.setConnection(connection);
                websiteView.setAction("INSERT");
                websiteView.setFillType(WebsiteDetails.FILL_TYPE_ALL);
                websiteView.setCurrent(websiteDetails);
                websiteView.doAction();

                commit();
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            request.getRequestDispatcher("/jsp/shared/website/insertUpdateWebsite.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateWebsite.
     */
    private void updateWebsite() {
        try {
            openConnection();

            WebsiteDetails websiteDetails = new WebsiteDetails();
            WebsiteView websiteView = new WebsiteView();

            websiteDetails.setWebsiteId(new BigDecimal(request.getParameter("dfWebsiteId")));
            websiteDetails.setUrl(request.getParameter("dfUrl"));
            websiteDetails.setDescription(request.getParameter("dfDescription"));

            websiteView.setConnection(connection);
            websiteView.setAction("UPDATE");
            websiteView.setFillType(WebsiteDetails.FILL_TYPE_ALL);
            websiteView.setCurrent(websiteDetails);
            websiteView.doAction();

            commit();
            request.getRequestDispatcher("/jsp/shared/website/insertUpdateWebsite.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteWebsite.
     */
    private void deleteWebsite() {
        try {
            openConnection();

            WebsiteDetails websiteDetails = new WebsiteDetails();
            WebsiteView websiteView = new WebsiteView();

            websiteDetails.setWebsiteId(new BigDecimal(request.getParameter("dfWebsiteId")));

            websiteView.setConnection(connection);
            websiteView.setAction("DELETE");
            websiteView.setFillType(WebsiteDetails.FILL_TYPE_ALL);
            websiteView.setCurrent(websiteDetails);
            websiteView.doAction();

            commit();
            request.getRequestDispatcher("/jsp/shared/website/insertUpdateWebsite.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayWebsites.
     */
    private void displayWebsites() {
        try {
            openConnection();
            try {

                ProspectDetails sessionProspectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");

                if (sessionProspectDetails == null) {
                    throw new ModelException("Session expired", ModelException.UNKNOWN_ERROR);
                }

                WebsiteView websiteView = new WebsiteView();
                WebsiteDetails websiteDetails = new WebsiteDetails();

                websiteDetails.setProspectId(sessionProspectDetails.getProspectId());

                websiteView.fillWithElements(connection, WebsiteDetails.FILL_TYPE_ALL, websiteDetails);
                if (websiteView.getElements().size() < 1) {
                    throw new ModelException("No records found.", ModelException.RECORD_NOT_FOUND);
                }
                request.setAttribute("websiteView", websiteView);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            commit();
            request.getRequestDispatcher("/jsp/shared/website/displayWebsites.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

}
