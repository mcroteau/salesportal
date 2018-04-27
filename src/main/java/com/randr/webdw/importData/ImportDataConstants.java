package com.randr.webdw.importData;

// import org.apache.xpath.operations.String;



/**
 * Date: Oct 27, 2004
 * Time: 12:53:59 PM
 */
public interface ImportDataConstants {
    public static final int ERROR_INVALID_FORMAT = 1;
    public static final int ERROR_IO = 2;
    public static final int ERROR_READ_FILE = 3;
    public static final int ERROR_UNKNOWN = 4;

//    public static final BigDecimal IMPORT_ITEM_BALANCE_FILE = new BigDecimal(1);
//    public static final BigDecimal IMPORT_ITEM_USER_DEFINED_FILE = new BigDecimal(2);

    public static final String CSV_PROCESSED_FOLDER = "upload_files/processed";
    public static final String CSV_INVALID_FORMAT_FOLDER = "upload_files/invalid_formats";
    public static final String CSV_ERROR_LOGS_FOLDER = "upload_files/error_logs";
    public static final String UPLOAD_PENDING_FOLDER = "upload_files/pending";
    
    
}
