package com.randr.webdw.prospect;

import java.io.PrintWriter;

import com.Ostermiller.util.CSVPrinter;
import com.randr.webdw.util.Utilities;


/**
 */
public class ProspectDownloadController extends ProspectBaseController {

    /**
     * Constructor for ProspectDownloadController.
     */
    public ProspectDownloadController() {
    }

    /**
     * Method doAction.
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    public void doAction() throws java.io.IOException, javax.servlet.ServletException {
        if (formAction == null || formAction.equals("DOWNLOAD")) {
            downloadCSVFile();
        }
    }

    /**
     * Method downloadCSVFile.
     */
    private void downloadCSVFile() {
        try {
            ProspectView prospectView = (ProspectView) request.getSession().getAttribute("prospectView");
            if (prospectView != null) {
                writeCsvFile(prospectView);
            } else {
                throw new Exception("Session expired. Please close the current browser window and reopen the application.");
            }
        } catch (Exception e) {
            doCatch(e);
        }
    }

    /**
     * Method writeCsvFile.
     * @param prospectView ProspectView
     */
    private void writeCsvFile(ProspectView prospectView) {
        try {
            PrintWriter out = response.getWriter();
            CSVPrinter csvPrinter = new CSVPrinter(out);
            csvPrinter.writeln(getCsvFileHeader());

            ProspectDetails ProspectDetails = null;

            for (int i = 0; i < prospectView.getElements().size(); i++) {
                ProspectDetails = (ProspectDetails) prospectView.getElements().elementAt(i);
                csvPrinter.writeln(getCsvFileDetail(ProspectDetails));
            }

            response.setContentType("application/x-unknown-content-type-Excel.CSV");
            out.flush();
            out.close();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method getCsvFileHeader.
     * @return String[]
     */
    private String[] getCsvFileHeader() {
        String[] lineElements = new String[23];
        lineElements[0] = "Company Name";
        lineElements[1] = "Contact Name";
        lineElements[2] = "Address";
        lineElements[3] = "Address 2";
        lineElements[4] = "City";
        lineElements[5] = "State";
        lineElements[6] = "Zip";
        lineElements[7] = "Prospect Status";
        lineElements[8] = "Phone #";
        lineElements[9] = "Fax #";
        lineElements[10] = "Contact Title";
        lineElements[11] = "System Type";
        lineElements[12] = "SIC";
        lineElements[13] = "Size";
        lineElements[14] = "Verified";
        lineElements[15] = "Software Type";
        lineElements[16] = "L.O.B.";
        lineElements[17] = "Web Site URL";
        lineElements[18] = "E-mail address";
        lineElements[19] = "County";
        lineElements[20] = "Territory";
        lineElements[21] = "Country";
        lineElements[22] = "ID";

        return lineElements;
    }

    /**
     * Method getCsvFileDetail.
     * @param prospectDetails ProspectDetails
     * @return String[]
     */
    private String[] getCsvFileDetail(ProspectDetails prospectDetails) {
        String[] lineElements = new String[23];
        lineElements[0] = Utilities.nullToBlank(prospectDetails.getCustomerCompany());
        lineElements[1] = Utilities.nullToBlank(prospectDetails.getContactName());
        lineElements[2] = Utilities.nullToBlank(prospectDetails.getAddress());
        lineElements[3] = Utilities.nullToBlank(prospectDetails.getAddress2());
        lineElements[4] = Utilities.nullToBlank(prospectDetails.getCity());
        lineElements[5] = Utilities.nullToBlank(prospectDetails.getStateName());
        lineElements[6] = Utilities.nullToBlank(prospectDetails.getZip());
        lineElements[7] = Utilities.nullToBlank(prospectDetails.getStatusName());
        lineElements[8] = Utilities.nullToBlank(prospectDetails.getPhone());
        lineElements[9] = Utilities.nullToBlank(prospectDetails.getFax());
        lineElements[10] = Utilities.nullToBlank(prospectDetails.getContactTitle());
        lineElements[11] = Utilities.nullToBlank(prospectDetails.getSystemTypeName());
        lineElements[12] = Utilities.nullToBlank(prospectDetails.getSic());
        lineElements[13] = Utilities.nullToBlank(prospectDetails.getSizeName());
        lineElements[14] = Utilities.nullToBlank(prospectDetails.getVerifiedName());
        lineElements[15] = Utilities.nullToBlank(prospectDetails.getSoftwareTypeName());
        lineElements[16] = Utilities.nullToBlank(prospectDetails.getLobName());
        lineElements[17] = Utilities.nullToBlank(prospectDetails.getWebsite());
        lineElements[18] = Utilities.nullToBlank(prospectDetails.getEmail());
        lineElements[19] = Utilities.nullToBlank(prospectDetails.getCounty());
        lineElements[20] = Utilities.nullToBlank(prospectDetails.getTerritoryName());
        lineElements[21] = Utilities.nullToBlank(prospectDetails.getCountryName());
        lineElements[22] = Utilities.nullToBlank(prospectDetails.getProspectId());

        return lineElements;
    }
}
