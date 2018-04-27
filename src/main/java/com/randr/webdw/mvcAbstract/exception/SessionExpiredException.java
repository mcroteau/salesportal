package com.randr.webdw.mvcAbstract.exception;

// import org.apache.xpath.operations.String;


/**
 */
public class SessionExpiredException extends Exception {
    private String message;

    /**
     * Constructor for SessionExpiredException.
     */
    public SessionExpiredException() {
    }

    /**
     * Constructor for SessionExpiredException.
     * @param message String
     */
    public SessionExpiredException(String message) {
        this.message = message;
    }

    /**
     * Method getMessage.
     * @return String
     */
    public String getMessage() {
        return this.message;
    }
}
