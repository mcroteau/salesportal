package com.randr.webdw.util;

import java.sql.Timestamp;

// import org.apache.xpath.operations.String;

/**
 */
public class SqlParser {
    /**
     * Method getParsedSql.
     * @param sqlString String
     * @param dbType String
     * @return String
     */
    public static String getParsedSql(String sqlString, String dbType) {
        if ((dbType.equals("")) || (dbType.equals("DB2"))) {
            return sqlString;
        }

        if (dbType.equals("POSTGRES")) {
            return getParsedSqlPostgres(sqlString);
        }

        return sqlString;
    }

    /**
     * Method getParsedSqlPostgres.
     * @param sqlString String
     * @return String
     */
    private static String getParsedSqlPostgres(String sqlString) {
        String parsedSql = sqlString;
        //parsedSql = Utilities.replaceWholeWord(parsedSql, "USER", "\"USER\"");
        parsedSql = Utilities.replaceString(parsedSql, "current timestamp", "current_timestamp");
        parsedSql = Utilities.replaceString(parsedSql, "current date", "current_date");
        parsedSql = Utilities.replaceString(parsedSql, "ucase(", "upper(");
        parsedSql = Utilities.replaceString(parsedSql, "lcase(", "lower(");
        parsedSql = Utilities.replaceString(parsedSql, "value(", "coalesce(");
        return parsedSql;
    }

    /**
     * Method getPostgresTimestamp.
     * @param postgresTimestampString String
     * @return Timestamp
     */
    public static Timestamp getPostgresTimestamp(String postgresTimestampString) {

        if (postgresTimestampString == null) {
            return null;
        }
        int year = Integer.parseInt(postgresTimestampString.substring(0, 4)) - 1900;
        int month = Integer.parseInt(postgresTimestampString.substring(5, 7)) - 1;
        int day = Integer.parseInt(postgresTimestampString.substring(8, 10));
        int hour = Integer.parseInt(postgresTimestampString.substring(11, 13));
        int minute = Integer.parseInt(postgresTimestampString.substring(14, 16));
        int second = Integer.parseInt(postgresTimestampString.substring(17, 19));
        // returns a timestamp in the follwoing format 2003-02-21 19:47:12.47688+02
        return new Timestamp(year, month, day, hour, minute, second, 0);
    }
}
