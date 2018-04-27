package com.randr.webdw.importData.importProcessorAbstract;

import java.util.Vector;

// import org.apache.xpath.operations.String;

/**
 * Date: Oct 29, 2004
 * Time: 11:28:04 AM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class ImportProcessorResult {
	protected String fileName;
    protected Vector invalidLines;
    protected Vector warningLines;
    protected String errorMessage;

    /**
     * Method addInvalidLine.
     * @param csvLine CsvLine
     */
    public void addInvalidLine(CsvLine csvLine) {
        if (this.invalidLines == null) {
            this.invalidLines = new Vector();
        }
        this.invalidLines.add(csvLine);
    }

    /**
     * Method addWarningLine.
     * @param csvLine CsvLine
     */
    public void addWarningLine(CsvLine csvLine) {
        if (this.warningLines == null) {
            this.warningLines = new Vector();
        }
        this.warningLines.add(csvLine);
    }

    /**
     * Method isImportSuccessful.
     * @return boolean
     */
    public boolean isImportSuccessful() {
        if ((this.invalidLines == null || this.invalidLines.size() == 0)
            && errorMessage == null) {
            return true;
        }
        return false;
    }

    /**
     * Method getInvalidLines.
     * @return Vector
     */
    public Vector getInvalidLines() {
        return invalidLines;
    }

    /**
     * Method getWarningLines.
     * @return Vector
     */
    public Vector getWarningLines() {
        return warningLines;
    }

    /**
     * Method getErrorMessage.
     * @return String
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Method setErrorMessage.
     * @param errorMessage String
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
