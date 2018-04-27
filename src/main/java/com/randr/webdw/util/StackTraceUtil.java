package com.randr.webdw.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

// import org.apache.xpath.operations.String;

/**
 */
public class StackTraceUtil {
    /**
     * Method getStackTrace.
     * @param aThrowable Throwable
     * @return String
     */
    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

}
