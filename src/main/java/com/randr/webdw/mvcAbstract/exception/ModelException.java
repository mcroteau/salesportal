package com.randr.webdw.mvcAbstract.exception;

import java.math.BigDecimal;

// import org.apache.xpath.operations.String;


/**
 */
public class ModelException extends Exception {
    private BigDecimal errorId = UNKNOWN_ERROR;

    public static final BigDecimal UNKNOWN_ERROR = new BigDecimal(1);
    public static final BigDecimal DUPLICATE_RECORD = new BigDecimal(2);
    public static final BigDecimal RECORD_NOT_FOUND = new BigDecimal(3);
    public static final BigDecimal EMAIL_ALREADY_SENT = new BigDecimal(4);

    /**
     * Constructor for ModelException.
     */
    public ModelException() {

    }

    /**
     * Constructor for ModelException.
     * @param errorId BigDecimal
     */
    public ModelException(BigDecimal errorId) {
        this.errorId = errorId;
    }

    /**
     * Constructor for ModelException.
     * @param s String
     * @param errorId BigDecimal
     */
    public ModelException(String s, BigDecimal errorId) {
        super(s);
        this.errorId = errorId;
    }

    /**
     * Method getErrorId.
     * @return BigDecimal
     */
    public BigDecimal getErrorId() {
        return errorId;
    }

    /**
     * Method setErrorId.
     * @param errorId BigDecimal
     */
    public void setErrorId(BigDecimal errorId) {
        this.errorId = errorId;
    }
}
