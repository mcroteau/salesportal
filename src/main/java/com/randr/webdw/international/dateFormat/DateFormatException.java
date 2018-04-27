package com.randr.webdw.international.dateFormat;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class DateFormatException extends ModelException {
    /**
     * Constructor for CountryException.
     */
    public DateFormatException() {
    }

    /**
     * Constructor for CountryException.
     * @param errorId BigDecimal
     */
    public DateFormatException(BigDecimal errorId) {
        super(errorId);
    }

    /**
     * Constructor for CountryException.
     * @param s String
     * @param errorId BigDecimal
     */
    public DateFormatException(String s, BigDecimal errorId) {
        super(s, errorId);
    }
}
