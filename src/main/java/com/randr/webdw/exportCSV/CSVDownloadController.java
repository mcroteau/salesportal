package com.randr.webdw.exportCSV;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;

import com.Ostermiller.util.CSVPrinter;
import com.randr.webdw.util.Utilities;
import com.randr.webdw.mvcAbstract.AbstractController;


/**
 * @author randr
 * 
 */
public class CSVDownloadController extends AbstractController {

    /**
     * Constructor for CSVDownloadController.
     */
    public CSVDownloadController() {
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
		request.setAttribute("formAction", "DISPLAY_QUERIES");
    }

    /**
     * Method downloadCSVFile.
     */
    private void downloadCSVFile() {
        try {
            Vector resultVector = (Vector) request.getSession().getAttribute("resultVector");
            if (resultVector != null) {
                writeCsvFile(resultVector);
            } else {
                throw new Exception("Session expired. Please close the current browser window and reopen the application.");
            }
        } catch (Exception e) {
            doCatch(e);
        }
    }

    /**
     * Method writeCsvFile.
     * @param resultVector Vector
     */
    private void writeCsvFile(Vector resultVector) {
        try {
            PrintWriter out = response.getWriter();
            CSVPrinter csvPrinter = new CSVPrinter(out);
            Iterator iterator = resultVector.iterator();
			StringBuffer csvFileContent = new StringBuffer();

            while (iterator.hasNext()) {
                //csvPrinter.writeln(getCsvFileDetail((Vector) iterator.next()));
                csvFileContent.append(getCsvDetail((Vector) iterator.next()));
            }

            response.setContentType("application/x-unknown-content-type-Excel.CSV");
			out.println(csvFileContent.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method getCsvFileDetail.
     * @param resultLine Vector
     * @return String[]
     */
    private String[] getCsvFileDetail(Vector resultLine) {
		String[] lineElements = new String[resultLine.size()];
    	
    	for (int i = 0; i < resultLine.size(); i++ ) {
    		lineElements[i] = Utilities.nullToBlank((String) resultLine.elementAt(i));
    	}

        return lineElements;
    }

	/**
	 * Method getCsvDetail.
	 * @param resultLine Vector
	 * @return String
	 */
	private String getCsvDetail(Vector resultLine) {
		StringBuffer lineElements = new StringBuffer();
    	
		for (int i = 0; i < resultLine.size(); i++ ) {
			lineElements.append(Utilities.nullToBlank((String) resultLine.elementAt(i)) + ",");
		}
		lineElements.append("\r\n");

		return lineElements.toString();
	}
}
