package com.randr.webdw.importData.importProcessorAbstract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Vector;

import com.Ostermiller.util.CSVParser;
import com.randr.webdw.importData.ImportDataConstants;
import com.randr.webdw.util.CollectionUtilities;
import com.randr.webdw.util.ConnectionPool;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.FileHandle;
import com.randr.webdw.util.StackTraceUtil;
import com.randr.webdw.util.Utilities;

/**
 */
public abstract class ImportAbstractProcessor implements ImportDataConstants {

    protected ImportProcessorResult importProcessorResult;
    protected String csvFileName;
    protected String csvFilePath;
    protected String processedCsvFilesPath;
    protected String invalidCsvFilesPath;
    protected String errorLogFilesPath;

    protected java.sql.Connection connection;
    protected static final boolean performConnectionTracking = true;
    protected BigInteger numberOfOpenedConnections = new BigInteger("0");
    protected BigInteger numberOfClosedConnections = new BigInteger("0");
    protected String lastOpenConnectionCallTrace = "";
    protected boolean connectionCallTraceDumped = false;

    protected ImportParameters importParameters;
    protected boolean specialFirstLine = false;

    /**
     * Constructor for ImportAbstractProcessor.
     * @param csvFileName String
     * @param csvFilePath String
     * @param processedCsvFilesPath String
     * @param invalidCsvFilesPath String
     * @param errorLogFilesPath String
     * @param importParameters ImportParameters
     */
    protected ImportAbstractProcessor(String csvFileName,
                                      String csvFilePath,
                                      String processedCsvFilesPath,
                                      String invalidCsvFilesPath,
                                      String errorLogFilesPath,
                                      ImportParameters importParameters) {
        this.csvFileName = csvFileName;
        this.csvFilePath = csvFilePath;
        this.processedCsvFilesPath = processedCsvFilesPath;
        this.invalidCsvFilesPath = invalidCsvFilesPath;
        this.errorLogFilesPath = errorLogFilesPath;
        this.importParameters = importParameters;
    }

    /**
     * Method run.
     */
    public void run() {
        importProcessorResult = new ImportProcessorResult();
        initFolders();
        processFile();

        if (importProcessorResult.isImportSuccessful()) {
            moveCurrentFileTo(processedCsvFilesPath);
        } else {
            moveCurrentFileTo(invalidCsvFilesPath);
            writeToErrorLog();
        }
    }

    /**
     * Method initFolders.
     */
    protected void initFolders() {
        if (!FileHandle.isDir(processedCsvFilesPath)) {
            FileHandle.makeDir(processedCsvFilesPath);
        }

        if (!FileHandle.isDir(invalidCsvFilesPath)) {
            FileHandle.makeDir(invalidCsvFilesPath);
        }

        if (!FileHandle.isDir(errorLogFilesPath)) {
            FileHandle.makeDir(errorLogFilesPath);
        }
    }


    /**
     * Method processFile.
     */
    protected void processFile() {
        try {
            openConnection();
            init();
            processLines(getLinesFromCsvFile());
        } catch (Exception e) {
            e.printStackTrace();
            importProcessorResult.setErrorMessage(e.getMessage());
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method getLinesFromCsvFile.
     * @return String[][]
     * @throws Exception
     */
    protected String[][] getLinesFromCsvFile() throws Exception {
        FileInputStream fileReader = new FileInputStream(csvFilePath + "/" + csvFileName);
        byte[] fileBytes = new byte[fileReader.available()];
        fileReader.read(fileBytes);
        fileReader.close();

        String fileContent = Utilities.replace(new String(fileBytes), "\"\"", "");
        String[][] values = CSVParser.parse(fileContent);

        return values;
    }

    /**
     * Method processLine.
     * @param csvLine CsvLine
     * @throws Exception
     */
    protected abstract void processLine(CsvLine csvLine) throws Exception;

    /**
     * Method processFirstLine.
     * @param csvLine CsvLine
     * @throws Exception
     */
    protected abstract void processFirstLine(CsvLine csvLine) throws Exception;

    /**
     * Method init.
     * @throws Exception
     */
    protected abstract void init() throws Exception;

    /**
     * Method processLines.
     * @param values String[][]
     * @throws Exception
     */
    protected void processLines(String[][] values) throws Exception {
        int notEmptyLinesIndex = 0;

        for (int i = 0; i < values.length; i++) {
        	// changed from 1000 to 200 lines 
//        	if (i % 75 == 0){
//				System.out.println("Processed " + i + " lines so far. " + DateUtilities.getCurrentSQLTimestamp());
//        	}
            String[] lineValues = values[i];
            //ignore blank lines
            if (lineValues.length > 0) {
                CsvLine csvLine = new CsvLine(new BigDecimal(i + 1),
                                              CollectionUtilities.getVectorFromObjectArray(lineValues),
                                              null);

                if (notEmptyLinesIndex == 0 && this.specialFirstLine) {
                    processFirstLine(csvLine);
                } else {
                    try {
                        processLine(csvLine);
                        commit();
                    } catch (Exception e) {
                        rollback();
                        e.printStackTrace();
                        csvLine.setErrorMessage(e.toString());
                        importProcessorResult.addInvalidLine(csvLine);
                    }
                }
                notEmptyLinesIndex++;
            }
        }
    }

    /**
     * Method writeToErrorLog.
     */
    protected void writeToErrorLog() {
        try {
            if (importProcessorResult != null && !importProcessorResult.isImportSuccessful()) {
                String errorLogFile = errorLogFilesPath + "/" + "error_log_"
                                      + DateUtilities.formatDate(new java.util.Date(), "yyyy_MM_dd")
                                      + ".txt";
                FileWriter errorLogWriter = new FileWriter(errorLogFile, true);
                errorLogWriter.write("Time: " + DateUtilities.formatDate(DateUtilities.getCurrentSQLTimestamp(), "MM/dd/yyyy hh:mm:ss a") + "\n");
                errorLogWriter.write("File: " + this.csvFilePath + "/" + this.csvFileName + "\n");
                if (importProcessorResult.getErrorMessage() != null) {
                    errorLogWriter.write("Error Message: " + importProcessorResult.getErrorMessage() + "\n");
                }
                if (importProcessorResult.getInvalidLines() != null) {
                    for (int i = 0; i < importProcessorResult.getInvalidLines().size(); i++) {
                        CsvLine invalidCsvLine = (CsvLine) importProcessorResult.getInvalidLines().elementAt(i);
                        errorLogWriter.write("Line# " + invalidCsvLine.getLineNumber() + "; Error Message: " + invalidCsvLine.getErrorMessage() + "\n");
                    }
                }
                errorLogWriter.write("\n\n");
                errorLogWriter.flush();
                errorLogWriter.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method moveCurrentFileTo.
     * @param newFilePath String
     */
    protected void moveCurrentFileTo(String newFilePath) {
        try {

            String datePrefix = DateUtilities.formatDate((new java.util.Date()), "yyyyMMdd");
            String extension = csvFileName.substring(csvFileName.indexOf("."));
            String origFileNoExt = csvFileName.substring(0, csvFileName.length() - extension.length());
            origFileNoExt = origFileNoExt.substring(origFileNoExt.lastIndexOf("/") + 1);

            int countRename = 0;
            boolean renamed = false;

            do {
                String underscoreCount = null;

                if (countRename > 0) {
                    underscoreCount = "_" + String.valueOf(countRename);
                } else {
                    underscoreCount = "";
                }

                String newFileName = datePrefix + "_" + origFileNoExt + underscoreCount + extension;
                File newFile = new File(newFilePath + "/" + newFileName);

                if (!newFile.isFile()) {
                    FileHandle.copyFile(csvFilePath + "/" + csvFileName, newFilePath + "/" + newFileName);
                    FileHandle.deleteFile(csvFilePath + "/" + csvFileName);
                    renamed = true;
                }
                countRename++;
            } while (!renamed);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method getCsvFileName.
     * @return String
     */
    public String getCsvFileName() {
        return csvFileName;
    }

    /**
     * Method setCsvFileName.
     * @param csvFileName String
     */
    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    /**
     * Method getCsvFilePath.
     * @return String
     */
    public String getCsvFilePath() {
        return csvFilePath;
    }

    /**
     * Method setCsvFilePath.
     * @param csvFilePath String
     */
    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    /**
     * Method openConnection.
     * @throws SQLException
     */
    protected void openConnection() throws SQLException {
        if (performConnectionTracking) {
            if (numberOfOpenedConnections.equals(numberOfClosedConnections)) {
                lastOpenConnectionCallTrace = StackTraceUtil.getStackTrace(new Exception("Connection Tracking Inconsistancy"));
            } else {
                writeToConnectionTrackingLog();
            }
        }

        this.connection = ConnectionPool.getNewConnection();
        if (performConnectionTracking) {
            numberOfOpenedConnections = numberOfOpenedConnections.add(new BigInteger("1"));
        }
    }

    /**
     * Method writeToConnectionTrackingLog.
     */
    protected void writeToConnectionTrackingLog() {
        String logsFolder = "logs";

        if (!(new File(logsFolder)).isDirectory()) {
            FileHandle.makeDir(logsFolder);
        }

        String logFile = logsFolder + "/db_connection_tracking.log";
        try {
            FileWriter logWriter = new FileWriter(logFile, true);
            String messageContent = (new java.util.Date()).toGMTString() + ": The controller " + this.getClass().getName() +
                                    " with hashcode " + this.hashCode() + " had the following inconsistancy betwen the DB connections " +
                                    "that were opened and connections that were closed:\n" +
                                    "       Opened connections: " + numberOfOpenedConnections + "\n" +
                                    "       Closed connections: " + numberOfClosedConnections + "\n\n";


            if (!connectionCallTraceDumped) {
                messageContent += "Here is the call trace of the openConnection() method that probably caused the inconsistancy:\n" +
                                  this.lastOpenConnectionCallTrace + "\n\n";
                connectionCallTraceDumped = true;

            }

            logWriter.write(messageContent);
            logWriter.flush();
            logWriter.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method commit.
     * @throws SQLException
     */
    protected void commit() throws SQLException {
        this.connection.commit();
    }

    /**
     * Method rollback.
     * @throws SQLException
     */
    protected void rollback() throws SQLException {
        this.connection.rollback();
    }

    /**
     * Method closeConnection.
     */
    protected void closeConnection() {
        try {
            this.connection.commit();
        } catch (SQLException sql) {
        }

        try {
            if (!this.connection.isClosed()) {
                if (performConnectionTracking) {
                    numberOfClosedConnections = numberOfClosedConnections.add(new BigInteger("1"));
                }

                this.connection.close();
            }
        } catch (SQLException sql) {
        }
    }

    /**
     * Method finallyMethod.
     */
    protected void finallyMethod() {
        if (this.connection != null) {
            try {
                this.connection.rollback();
            } catch (Exception sql) {
            }

            try {
                if (!this.connection.isClosed()) {
                    if (performConnectionTracking) {
                        numberOfClosedConnections = numberOfClosedConnections.add(new BigInteger("1"));
                    }

                    this.connection.close();
                }
            } catch (Exception sql) {
            }
        }
    }

    /**
     * Method getImportProcessorResult.
     * @return ImportProcessorResult
     */
    public ImportProcessorResult getImportProcessorResult() {
        return importProcessorResult;
    }

    /**
     * Method getCellContent.
     * @param lineElements Vector
     * @param index int
     * @return String
     */
    public String getCellContent(Vector lineElements, int index) {
        if (index > lineElements.size() - 1) {
            return null;
        }
        String strResult = (String) lineElements.elementAt(index);

        if (strResult.trim().length() == 0) {
            return null;
        }

        return strResult.trim();
    }

    /**
     * Method getString.
     * @param lineElements Vector
     * @param index int
     * @param maxLength int
     * @return String
     */
    protected String getString(Vector lineElements, int index, int maxLength) {
        return Utilities.truncate(getCellContent(lineElements, index), maxLength);
    }

    /**
     * Method getBigDecimal.
     * @param lineElements Vector
     * @param index int
     * @return BigDecimal
     */
    protected BigDecimal getBigDecimal(Vector lineElements, int index) {
        BigDecimal nResult = null;
        String strCellContent = getCellContent(lineElements, index);

        if (strCellContent != null) {
            nResult = new BigDecimal(strCellContent);
        }
        return nResult;
    }

    /**
     * Method isSpecialFirstLine.
     * @return boolean
     */
    public boolean isSpecialFirstLine() {
        return specialFirstLine;
    }

    /**
     * Method setSpecialFirstLine.
     * @param specialFirstLine boolean
     */
    public void setSpecialFirstLine(boolean specialFirstLine) {
        this.specialFirstLine = specialFirstLine;
    }
}
