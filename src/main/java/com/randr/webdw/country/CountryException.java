package com.randr.webdw.country;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.exception.ModelException;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CountryException extends ModelException {
    /**
     * Constructor for CountryException.
     */
    public CountryException() {
    }

    /**
     * Constructor for CountryException.
     * @param errorId BigDecimal
     */
    public CountryException(BigDecimal errorId) {
        super(errorId);
    }

    /**
     * Constructor for CountryException.
     * @param s String
     * @param errorId BigDecimal
     */
    public CountryException(String s, BigDecimal errorId) {
        super(s, errorId);
    }
}
