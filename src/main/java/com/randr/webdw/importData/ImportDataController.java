package com.randr.webdw.importData;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import com.oreilly.servlet.MultipartRequest;
import com.randr.webdw.AppSettings;
import com.randr.webdw.company.CompanyDetails;
import com.randr.webdw.company.CompanyView;
import com.randr.webdw.importData.importProcessor.ProspectImportParameters;
import com.randr.webdw.importData.importProcessor.ProspectImportProcessor;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.FileHandle;
import com.randr.webdw.util.Utilities;

/**
 */
public class ImportDataController extends AbstractController implements ImportDataConstants {
    /**
     * Constructor for ImportDataController.
     */
    public ImportDataController() {
    }

    /**
     * Method doAction.
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    public void doAction() throws java.io.IOException, javax.servlet.ServletException {
        createUploadProcessor(request);
        if (this.uploadProcessor == null) {
            if (formAction == null
                || formAction.equals("DISPLAY_UPLOAD")) {
                displayUploadFile();
            } else if (formAction.equals("DISPLAY_DELETE")) {
                request.getRequestDispatcher("/jsp/admin/importData/displayDelete.jsp").forward(request, response);
            } else if (formAction.equals("DELETE")) {
                deleteProspects();
            }
        } else {
            MultipartRequest multi = uploadProcessor.getMultipartRequest();
            if (multi.getParameter("formAction").equals("PROCESS_UPLOAD")) {
                processUpload();
            }
        }
    }

    /**
     * Method displayUploadFile.
     */
    private void displayUploadFile() {
        try {
            openConnection();
            CompanyView companyView = new CompanyView();
            companyView.fillWithElements(connection, CompanyDetails.FILL_TYPE_ALL, new CompanyDetails());
            request.setAttribute("companyView", companyView);
            commit();
            request.getRequestDispatcher("/jsp/admin/importData/displayUploadFile.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method processUpload.
     */
    protected void processUpload() {
        try {
            String uploadedFilesDir = AppSettings.getAppRealPath() + UPLOAD_PENDING_FOLDER;
            String invalidFilesDir = AppSettings.getAppRealPath() + CSV_INVALID_FORMAT_FOLDER;
            String errorLogsDir = AppSettings.getAppRealPath() + CSV_ERROR_LOGS_FOLDER;
            String processedFilesDir = AppSettings.getAppRealPath() + CSV_PROCESSED_FOLDER;
            MultipartRequest multipartRequest = uploadProcessor.getMultipartRequest();
            ProspectImportParameters prospectImportParameters = new ProspectImportParameters();
            prospectImportParameters.setCompanyNumber(new BigDecimal(multipartRequest.getParameter("dfCompanyId")));
            prospectImportParameters.setEliminateDups(multipartRequest.getParameter("dfEliminateDups") != null);

            prospectImportParameters.setCreateState(multipartRequest.getParameter("dfCreateState") != null);
            prospectImportParameters.setCreateCountry(multipartRequest.getParameter("dfCreateCountry") != null);
            prospectImportParameters.setCreateState(multipartRequest.getParameter("dfCreateState") != null);
            prospectImportParameters.setCreateSoftwareType(multipartRequest.getParameter("dfCreateSoftwareType") != null);
            prospectImportParameters.setCreateSystemType(multipartRequest.getParameter("dfCreateSystemType") != null);
            prospectImportParameters.setCreateLob(multipartRequest.getParameter("dfCreateLob") != null);
            prospectImportParameters.setCreateVerified(multipartRequest.getParameter("dfCreateVerified") != null);
            prospectImportParameters.setCreateTerritory(multipartRequest.getParameter("dfCreateTerritory") != null);
            
            Vector vAccept = new Vector(1);
            vAccept.add("csv");

            if (uploadProcessor.isValidUpload(vAccept, new Vector())) {

                if (!FileHandle.isDir(uploadedFilesDir)) {
                    FileHandle.makeDir(uploadedFilesDir);
                }
                HashMap uploadedFilesMap
                        = uploadProcessor.getUploadedFiles(uploadedFilesDir);
                Enumeration files = multipartRequest.getFileNames();

                if (files.hasMoreElements()) {
                    String name = (String) files.nextElement();
                    String filename = (String) uploadedFilesMap.get(name);

                    ProspectImportProcessor processor = new ProspectImportProcessor(filename,
                                                                                    uploadedFilesDir, processedFilesDir,
                                                                                    invalidFilesDir, errorLogsDir,
                                                                                    prospectImportParameters);
                    processor.setSpecialFirstLine(multipartRequest.getParameter("dfIgnoreFirstLine") != null);
                    processor.run();
                    request.setAttribute("importProcessorResult", processor.getImportProcessorResult());
                }
            } else {
                request.setAttribute("dbErrorMsg", "Invalid file for upload, please use only .csv files.");
            }
            request.getRequestDispatcher("/jsp/admin/importData/importDataActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
        }
    }

    /**
     * Method deleteProspects.
     */
    private void deleteProspects() {
        try {
            openConnection();

            String date = request.getParameter("dfDate1");
            String time = Utilities.blankToString(request.getParameter("dfTime1"), "00:00");
            String timestamp = date + " " + time;

            ProspectDetails prospectDetails = new ProspectDetails();
            prospectDetails.setCreationDate(DateUtilities.getSQLTimestamp(timestamp));

            ProspectView prospectView = new ProspectView();
            try {
                prospectView.doAction(connection, "DELETE", null, prospectDetails);
            } catch (SQLException sqlE) {
                throw new ModelException("The prospects cannot be deleted. <br>" +
                                         "The most probable reason for this is that there are notes " +
                                         "or other data attached to them.",
                                         ModelException.UNKNOWN_ERROR);
            }

            commit();
            request.getRequestDispatcher("/jsp/admin/importData/importDataActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
