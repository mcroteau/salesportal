package com.randr.webdw.importData;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.exception.ModelException;

/**
 */
public class ImportDataException extends ModelException {
    public static final BigDecimal IMPORT_INVALID_FORMAT = new BigDecimal(10);
    public static final BigDecimal IMPORT_ERROR_IO = new BigDecimal(11);
    public static final BigDecimal IMPORT_ERROR_READ = new BigDecimal(12);

    /**
     * Constructor for ImportDataException.
     */
    public ImportDataException() {
    }

    /**
     * Constructor for ImportDataException.
     * @param errorId BigDecimal
     */
    public ImportDataException(BigDecimal errorId) {
        super(errorId);
    }

    /**
     * Constructor for ImportDataException.
     * @param s String
     * @param errorId BigDecimal
     */
    public ImportDataException(String s, BigDecimal errorId) {
        super(s, errorId);
    }
}
