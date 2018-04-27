package com.randr.webdw.customQuery;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.util.SqlParser;
import com.randr.webdw.util.Utilities;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CustomQueryParser {
    private String sqlStatement = null;
    private String parsedSqlStatement = null;
    private String errorMessage = null;
    private String library = null;
    private String dbType = null;

    /**
     * Method parseSqlStatement.
     */
    public void parseSqlStatement() {
        formatSqlStatement();
        if (!errorOccured() && library != null && !library.equals("")) {
            int endFrom = -1;
            int offsetFrom = getOffsetIgnoreStrings("FROM");
            int offsetWhere = getOffsetIgnoreStrings("WHERE");
            if (offsetWhere > -1) {
                endFrom = offsetWhere;
            }
            int offsetGroupBy = getOffsetIgnoreStrings("GROUP BY");
            if (offsetGroupBy > -1 && endFrom == -1) {
                endFrom = offsetGroupBy;
            }

            int offsetHaving = getOffsetIgnoreStrings("HAVING");
            if (offsetHaving > -1 && endFrom == -1) {
                endFrom = offsetHaving;
            }

            int offsetOrderBy = getOffsetIgnoreStrings("ORDER BY");
            if (offsetOrderBy > -1 && endFrom == -1) {
                endFrom = offsetOrderBy;
            }

            String fromClause = "";
            if (endFrom > -1) {
                fromClause = parsedSqlStatement.substring(offsetFrom, endFrom);
            } else {
                fromClause = parsedSqlStatement.substring(offsetFrom);
            }
            fromClause = FillWithLibrary(fromClause, ",");
            fromClause = FillWithLibrary(fromClause, "JOIN");

            String tmpSqlStatement = parsedSqlStatement.substring(0, offsetFrom) + fromClause;
            if (endFrom > -1) {
                tmpSqlStatement += parsedSqlStatement.substring(endFrom);
            }
            parsedSqlStatement = tmpSqlStatement;
        }
        parsedSqlStatement = SqlParser.getParsedSql(parsedSqlStatement, dbType);
    }

    /**
     * Method parseSqlStatement.
     * @param originalSqlStatement String
     * @param library String
     * @param dbType String
     */
    public void parseSqlStatement(String originalSqlStatement, String library, String dbType) {
        setSqlStatement(originalSqlStatement);
        setLibrary(library);
        setDbType(dbType);
        parseSqlStatement();
    }

    /**
     * Method FillWithLibrary.
     * @param fromClause String
     * @param tokenizer String
     * @return String
     */
    private String FillWithLibrary(String fromClause, String tokenizer) {
        Vector fromTokens = Utilities.tokenize(fromClause, tokenizer);
        int i = 0;
        fromClause = "";
        for (i = 0; i < fromTokens.size(); i++) {
            String fromToken = (String) fromTokens.elementAt(i);
            if (!fromToken.startsWith("FROM " + library) && !fromToken.startsWith(library)) {
                if (fromToken.startsWith("FROM")) {
                    fromClause += "FROM " + library + fromToken.substring(5);
                } else {
                    if (fromToken.startsWith(" ")) {
                        fromToken = fromToken.substring(1);
                    }
                    fromClause += tokenizer + " " + library + fromToken;
                }
            } else {
                fromClause += fromToken;
            }
        }
        return fromClause;
    }

    /**
     * Method getSqlStatement.
     * @return String
     */
    public String getSqlStatement() {
        return sqlStatement;
    }

    /**
     * Method setSqlStatement.
     * @param sqlStatement String
     */
    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    /**
     * Method getParsedSqlStatement.
     * @return String
     */
    public String getParsedSqlStatement() {
        return parsedSqlStatement;
    }

    /**
     * Method getErrorMessage.
     * @return String
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Method errorOccured.
     * @return boolean
     */
    public boolean errorOccured() {
        return (getErrorMessage() != null);
    }

    /**
     * Method setLibrary.
     * @param library String
     */
    public void setLibrary(String library) {
        this.library = library;
    }

    /**
     * Method setDbType.
     * @param dbType String
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    /**
     * Method formatSqlStatement.
     */
    private void formatSqlStatement() {
        Vector occ = Utilities.getOccurences(sqlStatement, "'");
        if (occ.size() % 2 != 0) {
            errorMessage = "Extra ' found";
        } else {
            parsedSqlStatement = "";
            int from = 0;
            int to = 0;
            for (int i = 0; i < occ.size(); i++) {
                BigDecimal offset = (BigDecimal) occ.elementAt(i);
                if (i % 2 != 0) {   //even index - outside ''
                    to = offset.intValue();
                    String tmpStr = sqlStatement.substring(from, to);
                    parsedSqlStatement += tmpStr;
                } else {  //odd index
                    from = offset.intValue() + 1;
                    String tmpStr = sqlStatement.substring(to, from);
                    parsedSqlStatement += Utilities.removeExtraBlanks(Utilities.replace(Utilities.replaceString(tmpStr, "\n", " "), "\"", "")).toUpperCase();
                }
            }
            parsedSqlStatement += Utilities.removeExtraBlanks(Utilities.replace(Utilities.replaceString(sqlStatement.substring(to), "\n", " "), "\"", "")).toUpperCase();
        }
    }

    /**
     * Method getOffsetIgnoreStrings.
     * @param string String
     * @return int
     */
    private int getOffsetIgnoreStrings(String string) {
        int offset = -1;
        int startFrom = 0;
        int foundPosition = -1;
        boolean searchFinished = false;
        while (!searchFinished) {
            offset = parsedSqlStatement.substring(startFrom).indexOf(string);
            if (offset != -1) {
                int countCommas = Utilities.countOccurences(parsedSqlStatement.substring(0, startFrom + offset), "'");

                if (countCommas % 2 == 0) {
                    foundPosition = startFrom + offset;
                    searchFinished = true;
                } else {
                    startFrom += (offset + string.length());
                }

            } else {
                searchFinished = true;
            }
        }
        return foundPosition;
    }
}

