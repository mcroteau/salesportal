package com.randr.webdw.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.util.GenericDataSource;

import com.randr.webdw.AppSettings;
// import com.sun.corba.ee.connection.Connection;


/**
 */
public final class ConnectionPool {
    private static GenericDataSource dataSource = null;
    private static boolean connectionPoolCreated = false;

    /**
     * Method initConnectionPool.
     * @throws Exception
     */
    public static synchronized void initConnectionPool() throws Exception {
        if (!ConnectionPool.connectionPoolCreated) {
            try {
                ConnectionPool.dataSource = new GenericDataSource();

                dataSource.setAutoCommit(false);
                dataSource.setPingCommand("rollback");
                // dataSource.setDriverClass("com.ibm.db2.jdbc.net.DB2Driver");
                dataSource.setDriverClass(AppSettings.getParm("DB_DRIVER"));
                dataSource.setMaxCount(Integer.parseInt(AppSettings.getParm("DB_CONNECTION_POOL_MAX")));
                dataSource.setMinCount(Integer.parseInt(AppSettings.getParm("DB_CONNECTION_POOL_MIN")));

                dataSource.setUser(AppSettings.getParm("DB_USER"));
                dataSource.setPassword(AppSettings.getParm("DB_PASSWORD"));

                if (AppSettings.getParm("DB_DRIVER").equals("COM.ibm.db2.jdbc.net.DB2Driver")) {
                    dataSource.setUrl("jdbc:db2://" + AppSettings.getParm("DB_SYSTEM_IP")
                                      + "/" + AppSettings.getParm("DB_NAME"));
                } else if (AppSettings.getParm("DB_DRIVER").equals("com.ibm.as400.access.AS400JDBCDriver")) {
                    if (AppSettings.getParm("DB_SYSTEM_IP").equals("")) {
                        dataSource.setUrl("jdbc:as400://localhost");
                    } else {
                        dataSource.setUrl("jdbc:as400://" + AppSettings.getParm("DB_SYSTEM_IP"));
                    }
                } else if (AppSettings.getParm("DB_DRIVER").equals("org.postgresql.Driver")) {
                	System.out.println("ConnectionPool, we are in Postgres");
                    dataSource.setUrl("jdbc:postgresql://"
                                      + AppSettings.getParm("DB_SYSTEM_IP")
                                      + ":5432/"
                                      + AppSettings.getParm("DB_NAME"));
                } else if (AppSettings.getParm("DB_DRIVER").equals("com.mysql.jdbc.Driver")) {
                	System.out.println("ConnectionPool, we are in MySql");
                    dataSource.setUrl("jdbc:mysql://"
                            + AppSettings.getParm("DB_SYSTEM_IP")
                            + ":3306/"
                            + AppSettings.getParm("DB_NAME"));
                }
                dataSource.open();

                ConnectionPool.connectionPoolCreated = true;
            } catch (Exception sql) {
            	sql.printStackTrace();
                throw new Exception("msgConnectionPoolNotOpened : " + sql.getMessage());
            }
        }
    }

    /**
     * Method getNewConnection.
     * @return Connection
     * @throws SQLException
     */
    public static Connection getNewConnection() throws SQLException {
        if (!ConnectionPool.connectionPoolCreated) {
            try {
                ConnectionPool.initConnectionPool();
            } catch (Exception ex) {
            	ex.printStackTrace();
            }
        }

        return dataSource.getConnection();
    }

    /**
     * Method closeConnectionPool.
     */
    public static void closeConnectionPool() {
        try {
            if (ConnectionPool.connectionPoolCreated) {
                dataSource.close();

                dataSource = null;
            }
        } catch (SQLException sql) {
        }
    }

    /**
     * Method finalize.
     */
    protected void finalize() {
        try {
            closeConnectionPool();
        } catch (Exception exc) {
        }
    }

}

