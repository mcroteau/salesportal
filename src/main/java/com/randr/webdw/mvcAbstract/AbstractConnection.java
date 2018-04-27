/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package com.randr.webdw.mvcAbstract;

import java.sql.Connection;
import java.sql.SQLException;

import com.randr.webdw.util.ConnectionPool;
// import com.sun.corba.ee.connection.Connection;


/**
 */
public abstract class AbstractConnection implements Cloneable {
    protected Connection connection;

    /**
     * Method declaration
     *
     * @return Connection
     * @see
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Method declaration
     *
     * @param connection
     * @see
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method declaration
     *
     * @throws SQLException
     * @see
     */
    protected void openConnection() throws SQLException {
        this.connection = ConnectionPool.getNewConnection();
    }

    /**
     * Method declaration
     *
     * @throws SQLException
     * @see
     */
    protected void commit() throws SQLException {
        this.connection.commit();
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
     * Method clone.
     * @return Object
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}


/*--- formatting done in "Sun Java Convention" style on 04-01-2000 ---*/

