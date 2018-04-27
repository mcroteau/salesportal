package com.randr.webdw.customQuery;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import com.oreilly.servlet.MultipartRequest;
import com.randr.webdw.AppSettings;
import com.randr.webdw.customQueryAuthorization.CustomQueryAuthorizationDetails;
import com.randr.webdw.customQueryAuthorization.CustomQueryAuthorizationView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserView;
import com.randr.webdw.util.FileHandle;
import com.randr.webdw.util.Utilities;


/**
 * @author randr
 * @version $Revision: 1.3 $
 */
public class CustomQueryController extends AbstractController {

    DatabaseMetaData dmd = null;
    PrintWriter out = null;
    String tableName = null;
    ResultSet columns = null;
    ResultSet primary_keys = null;

    public static String SQL_CUSTOM_QUERIES_FOLDER = "sql_files/custom_queries";

    /**
     * Constructor for CustomQueryController.
     */
    public CustomQueryController() {
    }

    /**
     * Method doAction.
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    public void doAction() throws java.io.IOException, javax.servlet.ServletException {

        createUploadProcessor(request);
        if (this.uploadProcessor == null) {
            if (this.formAction == null
                || this.formAction.equals("DISPLAY_QUERIES")
                || this.formAction.equals("DISPLAY")) {
                displayQueries();
            } else if (this.formAction.equals("DISPLAY_UPLOAD")) {
                request.getRequestDispatcher("/jsp/admin/customQuery/displayUploadCustomQueries.jsp").forward(request, response);
            } else if (this.formAction.equals("DISPLAY_UPDATE_QUERY")) {
                displayUpdateQuery();
            } else if (this.formAction.equals("UPDATE_QUERY")) {
                updateQuery();
            } else if (this.formAction.equals("EXECUTE_QUERY")) {
                executeSelectedQuery();
            } else if (this.formAction.equals("DISPLAY_AUTHORIZE_USERS")) {
                displayAuthorizeUsers();
            } else if (this.formAction.equals("AUTHORIZE_USERS")) {
                authorizeUsers();
            } else if (this.formAction.equals("DELETE_QUERY")) {
                deleteQuery();
            }
        } else {
            MultipartRequest multi = uploadProcessor.getMultipartRequest();
            if (multi.getParameter("formAction").equals("PROCESS_UPLOAD")) {
                processUploadQuery();
            }
        }
    }

    /**
     * Method processUploadQuery.
     */
    private void processUploadQuery() {
        try {
            openConnection();
            Vector vAccept = new Vector(1);

            vAccept.add("sql");

            if (uploadProcessor.isValidUpload(vAccept, new Vector())) {
                if (!FileHandle.isDir(AppSettings.getAppRealPath() + SQL_CUSTOM_QUERIES_FOLDER)) {
                    FileHandle.makeDir(AppSettings.getAppRealPath() + SQL_CUSTOM_QUERIES_FOLDER);
                }

                HashMap uploadedFilesHashMap = uploadProcessor.getUploadedFiles(AppSettings.getAppRealPath() + SQL_CUSTOM_QUERIES_FOLDER);
                Vector filesUploaded = new Vector(uploadedFilesHashMap.values());
                CustomQueryDetails customQueryDetails = new CustomQueryDetails();
                CustomQueryView customQueryView = new CustomQueryView();

                if (filesUploaded.size() > 0) {
                    customQueryDetails.setCustomQueryDescription(uploadProcessor.getMultipartRequest().getParameter("dfFileDescription"));
                    customQueryDetails.setCustomQueryName(uploadProcessor.getMultipartRequest().getParameter("dfQueryName"));
                    customQueryDetails.setCustomQueryFileName(filesUploaded.elementAt(0).toString());

                    customQueryView.setConnection(connection);
                    customQueryView.setFillType(CustomQueryView.FILL_TYPE_BASIC);
                    customQueryView.setAction("INSERT");
                    customQueryView.setCurrent(customQueryDetails);
                    customQueryView.doAction();

                }
                request.setAttribute("customQueryDetails", customQueryDetails);
            } else {
                request.setAttribute("dbErrorMsg", "INVALID_UPLOAD");
            }
            commit();
            request.getRequestDispatcher("/jsp/admin/customQuery/processUploadCustomQueries.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteQuery.
     */
    private void deleteQuery() {
        try {
            openConnection();
            CustomQueryDetails customQueryDetails = new CustomQueryDetails();
            CustomQueryView customQueryView = new CustomQueryView();
            customQueryDetails.setCustomQueryNo(new BigDecimal(request.getParameter("dfQueryNo")));
            customQueryView.setConnection(connection);
            customQueryView.fillWithElements(CustomQueryView.FILL_TYPE_BASIC, customQueryDetails);
            if (customQueryView.getElements().size() == 1) {
                customQueryDetails = (CustomQueryDetails) customQueryView.getElements().elementAt(0);

                customQueryView = new CustomQueryView();
                customQueryView.setConnection(connection);
                customQueryView.setFillType(CustomQueryView.FILL_TYPE_BASIC);
                customQueryView.setAction("DELETE");
                customQueryView.setCurrent(customQueryDetails);
                customQueryView.doAction();

                String fileCompletePath = AppSettings.getAppRealPath() + SQL_CUSTOM_QUERIES_FOLDER + "/" + customQueryDetails.getCustomQueryFileName();
                FileHandle.deleteFile(fileCompletePath);
            }
            customQueryView = new CustomQueryView();
            customQueryView.fillWithElements(connection, CustomQueryView.FILL_TYPE_BASIC, new CustomQueryDetails());
            Vector customQueryVector = customQueryView.getElements();

            request.setAttribute("customQueryVector", customQueryVector);
            request.setAttribute("adminSite", new Boolean(this.isAdmin()));
            commit();
            request.getRequestDispatcher("/jsp/admin/customQuery/displayCustomQueries.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateQuery.
     */
    private void updateQuery() {
        try {
            openConnection();
            CustomQueryDetails customQueryDetails = new CustomQueryDetails();
            CustomQueryView customQueryView = new CustomQueryView();
            customQueryDetails.setCustomQueryNo(new BigDecimal(request.getParameter("dfQueryNo")));
            customQueryView.setConnection(connection);
            customQueryView.fillWithElements(CustomQueryView.FILL_TYPE_BASIC, customQueryDetails);
            if (customQueryView.getElements().size() == 1) {
                customQueryDetails = (CustomQueryDetails) customQueryView.getElements().elementAt(0);
                customQueryDetails.setCustomQueryContent(request.getParameter("dfQueryContent"));
                customQueryDetails.setCustomQueryName(request.getParameter("dfQueryName"));
                customQueryDetails.setCustomQueryDescription(request.getParameter("dfQueryDescription"));

                customQueryView = new CustomQueryView();
                customQueryView.setConnection(connection);
                customQueryView.setFillType(CustomQueryView.FILL_TYPE_BASIC);
                customQueryView.setAction("UPDATE");
                customQueryView.setCurrent(customQueryDetails);
                customQueryView.doAction();

                updateFileContent(customQueryDetails.getCustomQueryFileName(), customQueryDetails.getCustomQueryContent());
            } else {
                request.setAttribute("dbErrorMsg", "ERROR");
            }
            request.setAttribute("customQueryDetails", customQueryDetails);
            request.setAttribute("adminSite", new Boolean(this.isAdmin()));
            commit();
            request.getRequestDispatcher("/jsp/admin/customQuery/updateCustomQuery.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayAuthorizeUsers.
     */
    private void displayAuthorizeUsers() {
        try {
            openConnection();

            BigDecimal queryId = new BigDecimal(request.getParameter("dfQueryNo"));
            CustomQueryDetails customQueryDetails = new CustomQueryDetails();
            customQueryDetails.setCustomQueryNo(queryId);
            CustomQueryView customQueryView = new CustomQueryView();
            customQueryView.fillWithElements(connection, CustomQueryView.FILL_TYPE_BASIC, customQueryDetails);
            if (customQueryView.getElements().size() > 0) {
                customQueryDetails = (CustomQueryDetails) customQueryView.getElements().elementAt(0);
            }


            UserDetails userDetails = new UserDetails();
            userDetails.setCustomQueryNo(customQueryDetails.getCustomQueryNo());
            UserView userView = new UserView();

            userView.fillWithElements(connection, UserView.FILL_TYPE_QUERY_AUTHORIZED_USERS, userDetails);

            request.setAttribute("userView", userView);
            request.setAttribute("customQueryDetails", customQueryDetails);
            commit();
            request.getRequestDispatcher("/jsp/admin/customQuery/displayAuthorizeUsers.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }


    /**
     * Method executeSelectedQuery.
     */
    private void executeSelectedQuery() {
        try {
            openConnection();
            // find the file name for the selected query id
            BigDecimal queryId = new BigDecimal(request.getParameter("dfQueryNo"));
            CustomQueryDetails customQueryDetails = new CustomQueryDetails();
            customQueryDetails.setCustomQueryNo(queryId);
            CustomQueryView customQueryView = new CustomQueryView();
            customQueryView.fillWithElements(connection, CustomQueryView.FILL_TYPE_BASIC, customQueryDetails);
            if (customQueryView.getElements().size() > 0) {
                customQueryDetails = (CustomQueryDetails) customQueryView.getElements().elementAt(0);
            }
            Vector resultVector = processFile(customQueryDetails.getCustomQueryFileName());

            request.setAttribute("resultVector", resultVector);
            request.getSession().setAttribute("resultVector", resultVector);
            request.setAttribute("customQueryDetails", customQueryDetails);

            commit();

            if (isAdmin()) {
                request.getRequestDispatcher("/jsp/admin/customQuery/displayCustomQueryResultSet.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/jsp/public/customQuery/displayCustomQueryResultSet.jsp").forward(request, response);
            }
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method processFile.
     * @param fileName String
     * @return Vector
     * @throws Exception
     */
    private Vector processFile(String fileName) throws Exception {
        Vector resultVector = new Vector();
        int i = 0;
        String fileCompletePath = AppSettings.getAppRealPath() + SQL_CUSTOM_QUERIES_FOLDER + "/" + fileName;

        FileInputStream fileReader = new java.io.FileInputStream(fileCompletePath);
        byte[] fileBytes = new byte[fileReader.available()];
        fileReader.read(fileBytes);
        fileReader.close();
        String fileContent = new String(fileBytes);
        Vector fileLines = Utilities.tokenize(fileContent, ";");
        String fileLine = null;
        for (i = 0; i < fileLines.size(); i++) {
            fileLine = (String) fileLines.elementAt(i);

            if (!fileLine.trim().equals("")) {
                //execute line
                CustomQueryParser queryParser = new CustomQueryParser();
                queryParser.parseSqlStatement(fileLine, AppSettings.getParm("LIB"), AppSettings.getParm("DB_TYPE"));
                fileLine = queryParser.getParsedSqlStatement();

                String statementString = fileLine;
                PreparedStatement statement = null;
                if (!AppSettings.getParm("SCROLLABLE_CURSOR").equals("YES")) {
                    statement = connection.prepareStatement(statementString);
                } else {
                    statement = connection.prepareStatement(statementString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                }

                ResultSet resultSet = statement.executeQuery();

                int nColumns = resultSet.getMetaData().getColumnCount();
                Vector rowData = new Vector();
                for (int col = 1; col <= nColumns; col++) {
                    rowData.add(resultSet.getMetaData().getColumnLabel(col));
                }
                resultVector.add(rowData);

                while (resultSet.next()) {
                    rowData = new Vector();
                    for (int col = 1; col <= nColumns; col++) {
                        rowData.add(resultSet.getString(col));
                    }
                    resultVector.add(rowData);
                }
            }
        }

        return resultVector;
    }

    /**
     * Method updateFileContent.
     * @param fileName String
     * @param fileNewContent String
     * @throws Exception
     */
    private void updateFileContent(String fileName, String fileNewContent) throws Exception {
        String fileCompletePath = AppSettings.getAppRealPath() + SQL_CUSTOM_QUERIES_FOLDER + "/" + fileName;
        FileOutputStream fileWriter = new java.io.FileOutputStream(fileCompletePath);
        fileWriter.write(fileNewContent.getBytes());
        fileWriter.close();
    }

    /**
     * Method readFileContent.
     * @param fileName String
     * @return String
     * @throws Exception
     */
    private String readFileContent(String fileName) throws Exception {
        String fileCompletePath = AppSettings.getAppRealPath() + SQL_CUSTOM_QUERIES_FOLDER + "/" + fileName;
        FileInputStream fileReader = new java.io.FileInputStream(fileCompletePath);
        byte[] fileBytes = new byte[fileReader.available()];
        fileReader.read(fileBytes);
        fileReader.close();

        return new String(fileBytes);
    }

    /**
     * Method displayQueries.
     */
    private void displayQueries() {
        try {
            Vector customQueryVector = new Vector();
            openConnection();

            if (!isAdmin()) {
                CustomQueryAuthorizationDetails customAuthorizationDetails = new CustomQueryAuthorizationDetails();
                customAuthorizationDetails.setAuthorizedUserId(this.userProfile.getUserId());
                CustomQueryAuthorizationView customQueryAuthorizationView = new CustomQueryAuthorizationView();
                customQueryAuthorizationView.fillWithElements(connection, CustomQueryView.FILL_TYPE_BASIC, customAuthorizationDetails);
                customQueryVector = customQueryAuthorizationView.getElements();
            } else {
                CustomQueryView customQueryView = new CustomQueryView();
                customQueryView.fillWithElements(connection, CustomQueryView.FILL_TYPE_BASIC, new CustomQueryDetails());
                customQueryVector = customQueryView.getElements();
            }
            request.setAttribute("customQueryVector", customQueryVector);
            request.setAttribute("adminSite", new Boolean(isAdmin()));
            commit();
            if (isAdmin()) {
                request.getRequestDispatcher("/jsp/admin/customQuery/displayCustomQueries.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/jsp/public/customQuery/displayCustomQueries.jsp").forward(request, response);
            }
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateQuery.
     */
    private void displayUpdateQuery() {
        try {
            openConnection();
            CustomQueryDetails customQueryDetails = new CustomQueryDetails();
            customQueryDetails.setCustomQueryNo(new BigDecimal(request.getParameter("dfQueryNo")));
            CustomQueryView customQueryView = new CustomQueryView();
            customQueryView.fillWithElements(connection, CustomQueryView.FILL_TYPE_BASIC, customQueryDetails);
            if (customQueryView.getElements().size() == 1) {
                customQueryDetails = (CustomQueryDetails) customQueryView.getElements().elementAt(0);
                customQueryDetails.setCustomQueryContent(readFileContent(customQueryDetails.getCustomQueryFileName()));
                request.setAttribute("customQueryDetails", customQueryDetails);
            } else {
                request.setAttribute("dbErrorMsg", "NOT_FOUND");
            }
            request.setAttribute("adminSite", new Boolean(isAdmin()));

            commit();
            request.getRequestDispatcher("/jsp/admin/customQuery/displayUpdateCustomQuery.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method authorizeUsers.
     */
    private void authorizeUsers() {
        try {
            String dbErrorMsg = "";
            openConnection();

            CustomQueryAuthorizationDetails customQueryAuthorizationDetails = new CustomQueryAuthorizationDetails();
            CustomQueryAuthorizationView customQueryAuthorizationView = new CustomQueryAuthorizationView();
            customQueryAuthorizationDetails.setCustomQueryNo(new BigDecimal(request.getParameter("dfQueryNo")));
            customQueryAuthorizationView.setConnection(connection);
            customQueryAuthorizationView.setFillType(CustomQueryAuthorizationView.FILL_TYPE_BASIC);
            customQueryAuthorizationView.setAction("DELETE");
            customQueryAuthorizationView.setCurrent(customQueryAuthorizationDetails);
            customQueryAuthorizationView.doAction();

            customQueryAuthorizationDetails = null;
            customQueryAuthorizationView = new CustomQueryAuthorizationView();

            customQueryAuthorizationView.setAction("INSERT");
            customQueryAuthorizationView.setFillType(CustomQueryAuthorizationView.FILL_TYPE_BASIC);
            customQueryAuthorizationView.setConnection(connection);

            int maxRows = 0;
            if (request.getParameter("dfRows") != null && !request.getParameter("dfRows").trim().equals("")) {
                maxRows = new BigDecimal(request.getParameter("dfRows")).intValue();
            }
            int authorizedUsersCount = 0;
            for (int i = 0; i < maxRows; i++) {
                customQueryAuthorizationDetails = new CustomQueryAuthorizationDetails();
                if (request.getParameter("dfCheckBox" + String.valueOf(i + 1)) != null) {
                    try {
                        customQueryAuthorizationDetails.setCustomQueryNo(new BigDecimal(request.getParameter("dfQueryNo")));
                        customQueryAuthorizationDetails.setAuthorizedUserId(new BigDecimal(request.getParameter("dfCustomerNo" + String.valueOf(i + 1))));
                        customQueryAuthorizationView.setCurrent(customQueryAuthorizationDetails);
                        customQueryAuthorizationView.doAction();
                        authorizedUsersCount++;
                    } catch (Exception e) {
                        dbErrorMsg = "ERROR";
                        e.printStackTrace();
                    }
                } else {
                    
                }
            }
            request.setAttribute("dbErrorMsg", dbErrorMsg);
            request.setAttribute("countUsers", new BigDecimal(authorizedUsersCount));

            commit();
            request.getRequestDispatcher("/jsp/admin/customQuery/authorizeUsers.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

}