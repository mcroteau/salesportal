package com.randr.webdw.importData.importProcessorAbstract;

import java.math.BigDecimal;
import java.util.Vector;

// import org.apache.xpath.operations.String;

/**
 * Date: Oct 29, 2004
 * Time: 11:32:16 AM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CsvLine {
    BigDecimal lineNumber;
    Vector lineElements;
    String errorMessage;

    /**
     * Constructor for CsvLine.
     * @param lineNumber BigDecimal
     * @param lineElements Vector
     * @param errorMessage String
     */
    public CsvLine(BigDecimal lineNumber, Vector lineElements, String errorMessage) {
        this.lineNumber = lineNumber;
        this.lineElements = lineElements;
        this.errorMessage = errorMessage;
    }

    /**
     * Method getLineNumber.
     * @return BigDecimal
     */
    public BigDecimal getLineNumber() {
        return lineNumber;
    }

    /**
     * Method setLineNumber.
     * @param lineNumber BigDecimal
     */
    public void setLineNumber(BigDecimal lineNumber) {
        this.lineNumber = lineNumber;
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

    /**
     * Method getLineElements.
     * @return Vector
     */
    public Vector getLineElements() {
        return lineElements;
    }

    /**
     * Method setLineElements.
     * @param lineElements Vector
     */
    public void setLineElements(Vector lineElements) {
        this.lineElements = lineElements;
    }

    /**
     * Method toString.
     * @return String
     */
    public String toString() {
        return "CsvLine{" +
               "lineNumber=" + lineNumber +
               ", lineElements=" + lineElements +
               ", errorMessage='" + errorMessage + "'" +
               "}";
    }
}
