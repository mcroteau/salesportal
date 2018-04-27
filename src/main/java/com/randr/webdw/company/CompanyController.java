package com.randr.webdw.company;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;

import com.randr.webdw.international.dateFormat.DateFormatDetails;
import com.randr.webdw.international.dateFormat.DateFormatView;
import com.randr.webdw.international.timeOffset.TimeOffsetDetails;
import com.randr.webdw.international.timeOffset.TimeOffsetView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 */
public class CompanyController extends AbstractController {
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
            displayCompanies();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertCompany();
        } else if (formAction.equals("INSERT")) {
            insertCompany();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateCompany();
        } else if (formAction.equals("UPDATE")) {
            updateCompany();
        } else if (formAction.equals("DELETE")) {
            deleteCompany();
        }
    }

    /**
     * Method displayCompanies.
     */
    private void displayCompanies() {
        try {

            openConnection();

            CompanyView companyView = new CompanyView();
            companyView.fillWithElements(connection,CompanyDetails.FILL_TYPE_ALL, new CompanyDetails());
            try {
                if (companyView.getElements().size() > 0) {
                    request.setAttribute("companyView", companyView);
                    
                } else {
                    throw new ModelException("No companies found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/company/displayCompanies.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertCompany.
     */
    private void insertCompany() {
        try {
            openConnection();

            // check if the company already exist
            CompanyDetails companyDetails = new CompanyDetails();
            companyDetails.setCompany(request.getParameter("dfCompany"));

            CompanyView companyView = new CompanyView();
            companyView.fillWithElements(connection, CompanyDetails.FILL_TYPE_ALL, companyDetails);

            try {
                if (companyView.getElements().size() > 0) {
                    throw new ModelException("A company with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert company
                companyView.doAction(connection, "INSERT", CompanyDetails.FILL_TYPE_ALL, companyDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/company/companyActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateCompany.
     */
    private void updateCompany() {
        try {
            openConnection();

            // check if the company already exist
            CompanyDetails companyDetails = new CompanyDetails();
            companyDetails.setCompany(request.getParameter("dfCompany"));
            companyDetails.setDateFormatId(new BigDecimal(request.getParameter("cmbDataFormat")));
            companyDetails.setTimeOffsetId(new BigDecimal(request.getParameter("cmbTimeOffset")));

            CompanyView companyView = new CompanyView();
            companyView.fillWithElements(connection, CompanyDetails.FILL_TYPE_ALL, companyDetails);

            try {
                if (companyView.getElements().size() > 0) {
                    throw new ModelException("A company with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert company
                companyDetails.setCompanyId(new BigDecimal(request.getParameter("dfCompanyId")));
                companyView.doAction(connection, "UPDATE", CompanyDetails.FILL_TYPE_ALL, companyDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/company/companyActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateCompany.
     */
    private void displayUpdateCompany() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the company already exist
            CompanyDetails companyDetails = new CompanyDetails();
            companyDetails.setCompanyId(new BigDecimal(request.getParameter("dfCompanyId")));

            CompanyView companyView = new CompanyView();
            companyView.fillWithElements(connection, CompanyDetails.FILL_TYPE_ALL, companyDetails);
            DateFormatView dateFormatView = new DateFormatView();
            dateFormatView.fillWithElements(connection,CompanyDetails.FILL_TYPE_ALL, new DateFormatDetails());
        
            request.setAttribute("dateFormatView", dateFormatView);
            TimeOffsetView timeOffsetView = new TimeOffsetView();
            timeOffsetView.fillWithElements(connection,CompanyDetails.FILL_TYPE_ALL, new TimeOffsetDetails());
            
            request.setAttribute("timeOffsetView", timeOffsetView);
            try {
                if (companyView.getElements().size() == 1) {
                    companyDetails = (CompanyDetails) companyView.getElements().elementAt(0);
                    request.setAttribute("companyDetails", companyDetails);
                } else {
                    throw new ModelException("Company cannot be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/company/displayInsertUpdateCompany.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertCompany.
     */
    private void displayInsertCompany() {
        try {
            request.setAttribute("displayInsert", new Boolean(true));
            openConnection();
            DateFormatView dateFormatView = new DateFormatView();
            dateFormatView.fillWithElements(connection,CompanyDetails.FILL_TYPE_ALL, new DateFormatDetails());
            
            request.setAttribute("dateFormatView", dateFormatView);
            
            TimeOffsetView timeOffsetView = new TimeOffsetView();
            timeOffsetView.fillWithElements(connection,CompanyDetails.FILL_TYPE_ALL, new TimeOffsetDetails());
            
            request.setAttribute("timeOffsetView", timeOffsetView);
            // forward request
            request.getRequestDispatcher("/jsp/admin/company/displayInsertUpdateCompany.jsp").forward(request, response);
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteCompany.
     */
    private void deleteCompany() {
        try {
            openConnection();

            // check if company exist
            CompanyDetails companyDetails = new CompanyDetails();
            companyDetails.setCompanyId(new BigDecimal(request.getParameter("dfCompanyId")));

            CompanyView companyView = new CompanyView();
            companyView.fillWithElements(connection, CompanyDetails.FILL_TYPE_ALL, companyDetails);

            try {
                if (companyView.getElements().size() == 0) {
                    throw new ModelException("The company you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete company
                companyView.doAction(connection, "DELETE", null, companyDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/company/companyActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
