package com.randr.webdw.international.timeOffset;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class TimeOffsetException extends ModelException {
    /**
     * Constructor for CountryException.
     */
    public TimeOffsetException() {
    }

    /**
     * Constructor for CountryException.
     * @param errorId BigDecimal
     */
    public TimeOffsetException(BigDecimal errorId) {
        super(errorId);
    }

    /**
     * Constructor for CountryException.
     * @param s String
     * @param errorId BigDecimal
     */
    public TimeOffsetException(String s, BigDecimal errorId) {
        super(s, errorId);
    }
}
