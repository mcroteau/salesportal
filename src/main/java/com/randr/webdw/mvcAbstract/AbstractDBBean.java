package com.randr.webdw.mvcAbstract;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.SqlParser;
import com.randr.webdw.util.Utilities;


/**
 */
public abstract class AbstractDBBean extends AbstractConnection {

    protected String table = null;
    protected String tableAlias = "";
    protected boolean doTransaction = false;
    protected int startRow = 0;
    protected int maxRows = 0;
    protected boolean hasMoreRows = false;
    protected PreparedStatement statement;
    protected int updateCount = 0;
    private String orderBy = "";
    private String colsList = "";
    private String otherTables = "";
    private String where = "";
    private Vector conditionsParams = new Vector();
    private String groupBy = "";
    private String additionalUpdateString = "";
    private String distinctStr = "";
    private boolean caseSensitiveSearch = false;

    /**
     * Constructor for AbstractDBBean.
     */
    public AbstractDBBean() {
        setTableNameAndAlias();
    }

    /**
     * Method setTableNameAndAlias.
     */
    protected abstract void setTableNameAndAlias();

    /**
     * Method setStartRow.
     * @param startRow int
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * Method setMaxRows.
     * @param maxRows int
     */
    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    /**
     * Method declaration
     *
     * @return boolean
     * @see
     */
    public boolean hasMoreRows() {
        return this.hasMoreRows;
    }

    /**
     * Method declaration
     *
     * @see
     */
    protected void closeConnection() {
        try {
            this.connection.commit();
        } catch (SQLException sql) {
        }

        try {
            this.connection.close();
        } catch (SQLException sql) {
        }
    }

    /**
     * Method getUpdateCount.
     * @return int
     */
    public int getUpdateCount() {
        return this.updateCount;
    }

    /**
     * Method declaration
     *
     * @param idColumn
     * @param table
     * @return BigDecimal
     * @throws Exception
     * @see
     */
    protected BigDecimal getAvailableID(String idColumn, String table) throws Exception {
        return getAvailableID(idColumn, table, "", new Vector());
    }

    /**
     * Method declaration
     *
     * @param idColumn
     * @param table
     * @param where
     * @param vStatementParams
     * @return BigDecimal
     * @throws Exception
     * @see
     */
    protected BigDecimal getAvailableID(String idColumn, String table, String where, Vector vStatementParams) throws Exception {
        int nID = 0;
        String statementString = "select max(" + idColumn + ") from " + AppSettings.getParm("LIB") + table + " " + where;

        statementString = SqlParser.getParsedSql(statementString, AppSettings.getParm("DB_TYPE"));
        if (AppSettings.getParm("SQL_TRACE").equals("YES")) {
            System.out.println(statementString);
        }
        statement = connection.prepareStatement(statementString);

        for (int i = 0; i < vStatementParams.size(); i++) {
            if (vStatementParams.get(i).getClass().getName().equals("java.math.BigDecimal")) {
                statement.setBigDecimal(i + 1, (BigDecimal) vStatementParams.get(i));
            } else {
                statement.setObject(i + 1, vStatementParams.get(i));
            }
        }
        if (AppSettings.getParm("SQL_TRACE").equals("YES")) {
            System.out.println(vStatementParams);
        }
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            nID = resultSet.getInt(1);
        }

        nID++;
        statement.close();

        return new BigDecimal(nID);
    }

    /**
     * Method declaration
     *
     * @param statementString
     * @return Boolean
     * @throws Exception
     * @see
     */
    protected Boolean execUpdate(String statementString) throws Exception {
        return execUpdate(statementString, new Vector());
    }

    /**
     * Method declaration
     *
     * @param statementString
     * @param paramObj
     * @return Boolean
     * @throws Exception
     * @see
     */
    protected Boolean execUpdate(String statementString, Object paramObj) throws Exception {
        Vector vStatementParams = new Vector();

        vStatementParams.add(0, paramObj);

        return execUpdate(statementString, vStatementParams);
    }

    /**
     * Method declaration
     *
     * @param statementString
     * @param details
     * @param useInheritedFields boolean
     * @return Object
     * @throws Exception
     * @see
     */
    protected Object execUpdate(String statementString, AbstractDetails details, boolean useInheritedFields) throws Exception {
        return execUpdate(statementString, details, "", useInheritedFields);
    }

    /**
     * Method declaration
     *
     * @param statementString
     * @param details
     * @param detailsPropList
     * @param useInheritedFields boolean
     * @return Object
     * @throws Exception
     * @see
     */
    protected Object execUpdate(String statementString, AbstractDetails details, String detailsPropList, boolean useInheritedFields) throws Exception {
        Object objDetails = null;
        Vector vDetailsProps = null;

        if (detailsPropList == null) {
            vDetailsProps = new Vector();
        } else if (!detailsPropList.equals("")) {
            vDetailsProps = details.getProperties(detailsPropList, useInheritedFields);
        } else {
            vDetailsProps = details.getProperties(useInheritedFields);
        }

        execUpdate(statementString, vDetailsProps);

        objDetails = details.clone();

        return objDetails;
    }

    /**
     * Method declaration
     *
     * @param statementString
     * @param vStatementParams
     * @return Boolean
     * @throws Exception
     * @see
     */
    protected Boolean execUpdate(String statementString, Vector vStatementParams) throws Exception {
        try {
            statementString = SqlParser.getParsedSql(statementString, AppSettings.getParm("DB_TYPE"));

            doTransaction = false;

            if (this.connection == null) {
                doTransaction = true;

                openConnection();
            }

            statement = connection.prepareStatement(statementString);

            for (int i = 0; i < vStatementParams.size(); i++) {
                if (vStatementParams.get(i).getClass().getName().equals("java.math.BigDecimal")) {
                    statement.setBigDecimal(i + 1, (BigDecimal) vStatementParams.get(i));
                } else {
                    statement.setObject(i + 1, vStatementParams.get(i));
                }
            }

            if (AppSettings.getParm("SQL_TRACE").equals("YES")) {
                System.out.println(statementString);
                System.out.println(vStatementParams);
            }

            statement.executeUpdate();

            updateCount = statement.getUpdateCount();

            statement.close();

            if (doTransaction) {
                commit();
            }
            return new Boolean(true);
        } catch (SQLException sql) {
            System.out.println(statementString);
            System.out.println(vStatementParams);

            sql.printStackTrace();
            finallyMethod();

            throw sql;
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method execQuery.
     * @param statementString String
     * @param details AbstractDetails
     * @param useInheritedFields boolean
     * @return Vector
     * @throws Exception
     */
    protected Vector execQuery(String statementString, AbstractDetails details, boolean useInheritedFields) throws Exception {
        return execQuery(statementString, details, "", useInheritedFields);
    }

    /**
     * Method declaration
     *
     * @param statementString
     * @param details
     * @param detailsPropList
     * @param useInheritedFields boolean
     * @return Vector
     * @throws Exception
     * @see
     */
    protected Vector execQuery(String statementString, AbstractDetails details, String detailsPropList, boolean useInheritedFields) throws Exception {
        Vector vStatementParams = new Vector();

        return execQuery(statementString, details, vStatementParams, detailsPropList, useInheritedFields);
    }

    /**
     * Method declaration
     *
     * @param statementString
     * @param details
     * @param objParam
     * @param useInheritedFields boolean
     * @return Vector
     * @throws Exception
     * @see
     */
    protected Vector execQuery(String statementString, AbstractDetails details, Object objParam, boolean useInheritedFields) throws Exception {
        return execQuery(statementString, details, objParam, "", useInheritedFields);
    }

    /**
     * Method declaration
     *
     * @param statementString
     * @param details
     * @param objParam
     * @param detailsPropList
     * @param useInheritedFields boolean
     * @return Vector
     * @throws Exception
     * @see
     */
    protected Vector execQuery(String statementString, AbstractDetails details, Object objParam, String detailsPropList, boolean useInheritedFields) throws Exception {
        Vector vStatementParams = new Vector(1);

        vStatementParams.add(objParam);

        return execQuery(statementString, details, vStatementParams, detailsPropList, useInheritedFields);
    }

    /**
     * Method declaration
     *
     * @param statementString
     * @param details
     * @param vStatementParams
     * @param useInheritedFields boolean
     * @return Vector
     * @throws Exception
     * @see
     */
    protected Vector execQuery(String statementString, AbstractDetails details, Vector vStatementParams, boolean useInheritedFields) throws Exception {
        return execQuery(statementString, details, vStatementParams, "", useInheritedFields);
    }

    /**
     * Method declaration
     *
     * @param statementString
     * @param details
     * @param vStatementParams
     * @param detailsPropList
     * @param useInheritedFields boolean
     * @return Vector
     * @throws Exception
     * @see
     */
    protected Vector execQuery(String statementString, AbstractDetails details, Vector vStatementParams, String detailsPropList, boolean useInheritedFields) throws Exception {
        try {
            statementString = SqlParser.getParsedSql(statementString, AppSettings.getParm("DB_TYPE"));

            doTransaction = false;

            AbstractDetails objDetails = null;
            Vector allRows = new Vector();
            int index = 0;

            if (this.connection == null) {
                doTransaction = true;

                openConnection();
            }

            // statement = connection.prepareStatement(statementString);
            statement = connection.prepareStatement(statementString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            for (int i = 0; i < vStatementParams.size(); i++) {
                if (vStatementParams.get(i).getClass().getName().equals("java.math.BigDecimal")) {
                    statement.setBigDecimal(i + 1, (BigDecimal) vStatementParams.get(i));
                } else {
                    statement.setObject(i + 1, vStatementParams.get(i));
                }
            }

            if (AppSettings.getParm("SQL_TRACE").equals("YES")) {
                System.out.println(statementString);
                System.out.println(vStatementParams);
            }

            ResultSet resultSet = statement.executeQuery();

            if (startRow > 1) {
                resultSet.absolute(startRow - 1);
            }

            // resultSet.absolute(3);
            if (maxRows == 0) {
                maxRows = Integer.MAX_VALUE;
            }

            while ((resultSet.next()) && (index < maxRows)) {
                index++;
                objDetails = (AbstractDetails) details.clone();

                if (!detailsPropList.equals("")) {
                    objDetails.setProperties(resultSet, detailsPropList, useInheritedFields);
                } else {
                    objDetails.setProperties(resultSet, useInheritedFields);
                }

                allRows.add(objDetails);
            }

            if (index == maxRows) {

                // if (resultSet.next()) {
                if (resultSet.absolute(startRow + maxRows)) {
                    hasMoreRows = true;
                } else {
                    hasMoreRows = false;
                }
            } else {
                hasMoreRows = false;
            }

            // resultSet.close();
            statement.close();

            if (doTransaction) {
                commit();
            }

            return allRows;
        } catch (SQLException sql) {
            System.out.println(statementString);
            System.out.println(vStatementParams);

            sql.printStackTrace();
            finallyMethod();

            throw sql;
        } finally {
            finallyMethod();
        }
    }

    /**
     * This method is used to get the equivalent detailed object for this bean
     * object.
     */

    // public abstract Object getDetails();

    /**
     * Thiss method Release the connection asociated with the current object.It is used in
     * conjunction with the ConnectionPool class.
     *
     * @see ConnectionPool
     */
    protected void finallyMethod() {

        /*
        * try {
        * this.statement.close();
        * } catch (Exception sql) {}
        */
        if (doTransaction) {
            try {
                this.connection.rollback();
            } catch (Exception sql) {
            }

            try {
                this.connection.close();
            } catch (Exception sql) {
            }
        }
    }

    /**
     * This method is used to get an exception message that may be showe to the
     * user. If any other message other rhan those recognised by the method is
     * encountered, the message of the exception is returned.
     *
     * @param sql the sql caught exception, whose message has to be converted to a
     *            end user frendly message.
     * @return the end user frendly message.
     * @see SQLException
     */
    protected String getSQLMessage(SQLException sql) {
        String message = sql.getMessage();

        if (message.indexOf("Cannot insert duplicate key row in object") != -1) {
            if (message.indexOf("with unique index") != -1) {
                return "The name of the record is already used by another record.Please use another name.";
            }
        }

        return message;
    }

    /**
     * Method setOrderBy.
     * @param orderByCols String
     */
    public void setOrderBy(String orderByCols) {
        if (orderByCols != null) {
            this.orderBy = orderByCols;
        }
    }

    /**
     * Method declaration
     *
     * @return String
     * @see
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Method addColumnList.
     * @param columnList String
     */
    protected void addColumnList(String columnList) {
        if (!colsList.equals("")) {
            colsList = colsList + ",";
        }

        colsList = colsList + columnList;
    }

    /**
     * Method getColumnList.
     * @return String
     */
    protected String getColumnList() {
        return colsList;
    }

    /**
     * Method declaration
     *
     * @param otherTables
     * @see
     */
    protected void setOtherTables(String otherTables) {
        this.otherTables = otherTables;
    }

    /**
     * Method declaration
     *
     * @param condition
     * @see
     */
    protected void addCondition(String condition) {
        if (!condition.equals("")) {
            if (where.equals("")) {
                where = " where " + condition + " ";
            } else {
                where = where + "and " + condition + " ";
            }
        }
    }

    /**
     * Method declaration
     *
     * @param conditionParam
     * @see
     */
    protected void addConditionParam(Object conditionParam) {
        conditionsParams.add(conditionParam);
    }

    /**
     * Method declaration
     *
     * @param groupByCols
     * @see
     */
    protected void setGroupBy(String groupByCols) {
        groupBy = " group by " + groupByCols;
    }

    /**
     * Method declaration
     *
     * @param additionalUpdateString
     * @see
     */
    protected void addAdditionalUpdateString(String additionalUpdateString) {
        if (!this.additionalUpdateString.equals("")) {
            this.additionalUpdateString = this.additionalUpdateString + ",";
        }

        this.additionalUpdateString = this.additionalUpdateString + additionalUpdateString;
    }

    /**
     * Method declaration
     *
     * @param details
     * @return AbstractDetails
     * @throws Exception
     * @see
     */
    protected AbstractDetails doInsert(AbstractDetails details) throws Exception {
        Vector properties = details.getPropertiesFromDBColumns(colsList);
        Vector statementParams = new Vector();
        String insertVals = "";

        for (int i = 0; i < properties.size(); i++) {
            if (properties.elementAt(i) != null) {
                statementParams.add(properties.elementAt(i));

                insertVals = insertVals + "?,";
            } else {
                insertVals = insertVals + "null,";
            }
        }

        insertVals = insertVals.substring(0, insertVals.length() - 1);

        String statementString = "insert into " + AppSettings.getParm("LIB") + table + "(" + colsList + ") values (" + insertVals + ")";

        try {
            execUpdate(statementString, statementParams);
        } catch (Exception e) {
            System.out.println(statementString);
            System.out.println(statementParams);
            throw e;
        }


        return (AbstractDetails) details.clone();
    }

    /**
     * Method declaration
     *
     * @param details
     * @return AbstractDetails
     * @throws Exception
     * @see
     */
    protected AbstractDetails doUpdate(AbstractDetails details) throws Exception {
        Vector statementParams = new Vector();
        String updateVals = "";

        if (!colsList.equals("")) {
            Vector properties = details.getPropertiesFromDBColumns(colsList);
            Vector columns = Utilities.tokenize(colsList, ",");

            for (int i = 0; i < properties.size(); i++) {
                updateVals = updateVals + (String) columns.elementAt(i) + " = ";

                if (properties.elementAt(i) != null) {
                    statementParams.add(properties.elementAt(i));

                    updateVals = updateVals + " ?,";
                } else {
                    updateVals = updateVals + " null,";
                }
            }

            updateVals = updateVals.substring(0, updateVals.length() - 1);
        }

        if (!this.additionalUpdateString.equals("")) {
            if (!updateVals.equals("")) {
                updateVals = updateVals + ",";
            }

            updateVals = updateVals + additionalUpdateString + " ";
        }

        for (int i = 0; i < conditionsParams.size(); i++) {
            statementParams.add(conditionsParams.elementAt(i));
        }

        String statementString = "update " + AppSettings.getParm("LIB") + table + " set " + updateVals + where;

        try {
            if (AppSettings.getParm("SQL_TRACE").equals("YES")) {
                System.out.println(statementString);
                System.out.println(updateVals);
            }

            execUpdate(statementString, statementParams);
        } catch (Exception e) {
            System.out.println(statementString);
            System.out.println(statementParams);
            throw e;
        }


        return (AbstractDetails) details.clone();
    }

    /**
     * Method declaration
     *
     * @param details
     * @return Boolean
     * @throws Exception
     * @see
     */
    protected Boolean doDelete(AbstractDetails details) throws Exception {
        Vector statementParams = new Vector();

        for (int i = 0; i < conditionsParams.size(); i++) {
            statementParams.add(conditionsParams.elementAt(i));
        }

        String statementString = "delete from " + AppSettings.getParm("LIB") + table + where;

        if (AppSettings.getParm("SQL_TRACE").equals("YES")) {
            System.out.println(statementString);
            System.out.println(statementParams);
        }
        return this.execUpdate(statementString, statementParams);
    }

    /**
     * Method declaration
     *
     * @param details
     * @return Vector
     * @throws Exception
     * @see
     */
    protected Vector doSelect(AbstractDetails details) throws Exception {
        String transformedColsList = colsList;
        int numberOfColsParams = 0;

        if (transformedColsList.indexOf("?") > -1) {
            numberOfColsParams = Utilities.countOccurences(transformedColsList, "?");

            int paramIndex = 0;

            for (int i = 0; i < numberOfColsParams; i++) {
                paramIndex = transformedColsList.indexOf("?");
                transformedColsList = transformedColsList.substring(0, paramIndex) + getSqlLiteralFromParam(conditionsParams.elementAt(i)) + transformedColsList.substring(paramIndex + 1);
            }
        }

        Vector statementParams = new Vector();

        for (int i = numberOfColsParams; i < conditionsParams.size(); i++) {
            statementParams.add(conditionsParams.elementAt(i));
        }

        String orderByStr = "";

        if (!this.orderBy.equals("")) {
            orderByStr = " order by " + orderBy + " ";
        }

        String statementString = " select " + distinctStr + transformedColsList + " from " + AppSettings.getParm("LIB") + table + " " + tableAlias + otherTables + where + groupBy + orderByStr;
        String detailsPropList = details.getPropertyNamesFromDBColumns(colsList);


        try {
            if (AppSettings.getParm("SQL_TRACE").equals("YES")) {
                System.out.println(statementString);
                System.out.println(statementParams);
            }

            return this.execQuery(statementString, details, statementParams, detailsPropList, true);

        } catch (Exception e) {
            System.out.println(statementString);
            System.out.println(detailsPropList);
            for (int i = 0; i < statementParams.size(); i++) {
                System.out.println(statementParams.elementAt(i));
            }

            throw e;
        }
    }

    /**
     * Method declaration
     *
     * @param param
     * @return String
     * @throws Exception
     * @see
     */
    private String getSqlLiteralFromParam(Object param) throws Exception {
        if (param.getClass().getName().equals("java.lang.String")) {
            return "'" + Utilities.replaceString((String) param, "'", "''") + "'";
        } else if (param.getClass().getName().equals("java.math.BigDecimal")) {
            return param.toString();
        } else if (param.getClass().getName().equals("java.sql.Date")) {
            return "'" + DateUtilities.formatDate((java.util.Date) param, "yyyy-MM-dd") + "'";
        } else {
            throw new Exception("Literal transform from " + param.getClass().getName() + " currently not supported");
        }
    }

    /**
     * Method declaration
     *
     * @param useDistinct
     * @see
     */
    protected void useDistinct(boolean useDistinct) {
        if (useDistinct) {
            distinctStr = " distinct ";
        } else {
            distinctStr = "";
        }
    }

    /**
     * Method declaration
     *
     * @param otherTablesList
     * @see
     */
    protected void addOtherTables(String otherTablesList) {
        this.otherTables = this.otherTables + otherTablesList;
    }

    /**
     * Method addOrderBy.
     * @param orderByCols String
     */
    protected void addOrderBy(String orderByCols) {
        if (!this.orderBy.equals("")) {
            this.orderBy = this.orderBy + ",";
        }

        this.orderBy = this.orderBy + orderByCols;
    }

    /**
     * Method isCaseSensitiveSearch.
     * @return boolean
     */
    protected boolean isCaseSensitiveSearch() {
        return caseSensitiveSearch;
    }

    /**
     * Method setCaseSensitiveSearch.
     * @param caseSensitiveSearch boolean
     */
    protected void setCaseSensitiveSearch(boolean caseSensitiveSearch) {
        this.caseSensitiveSearch = caseSensitiveSearch;
    }
}
