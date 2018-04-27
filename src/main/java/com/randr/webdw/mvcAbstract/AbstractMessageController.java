package com.randr.webdw.mvcAbstract;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.randr.webdw.AppSettings;
import com.randr.webdw.util.ConnectionPool;

public abstract class AbstractMessageController extends HttpServlet implements SingleThreadModel{
    
	protected String formAction = null;
    protected Connection connection = null;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Log log = LogFactory.getLog("MESSAGE_CONTROLLER");

    
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Method setConnection.
     *
     * @param connection Connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method openConnection.
     * @throws SQLException
     */
    protected void openConnection() throws SQLException {
        this.connection = ConnectionPool.getNewConnection();
    }
    
    /**
     * Method commit.
     *
     * @throws SQLException
     */
    protected void commit() throws SQLException {
        this.connection.commit();
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
            this.connection.close();
        } catch (SQLException sql) {
        	sql.printStackTrace();
        }
    }

    /**
     * Method doGet.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.request = req;
            this.response = resp;
            loadAppSettings(getServletContext(), request);
            
            formAction = request.getParameter("formAction");
	        request.setAttribute("formAction", formAction);
	
	        doAction();
            
        } catch (Exception ex) {
            doCatch(ex);
        }
    }

    /**
     * Method doPost.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        doGet(request, response);
    }


    /**
     * Method doAction.
     *
     * @throws ServletException
     * @throws IOException
     */
    public abstract void doAction() throws ServletException,
                                           IOException;

    /**
     * Method finallyMethod.
     */
    protected void finallyMethod() {
        if (this.connection != null) {
            try {
                this.connection.rollback();
            } catch (Exception sql) {
            	sql.printStackTrace();
            }

            try {
                this.connection.close();
            } catch (Exception sql) {
            	sql.printStackTrace();
            }
        }
    }

    /**
     * Method doCatch.
     *
     * @param e Exception
     */
    protected void doCatch(Exception e) {
        doCatch(e, "/jsp/error/error.jsp");
    }

    /**
     * Method doCatch.
     *
     * @param e        Exception
     * @param errorURL String
     */
    protected void doCatch(Exception e, String errorURL) {
        try {
            e.printStackTrace();
            finallyMethod();
            request.setAttribute("exception", e);

            if (!errorURL.equals("")) {
                request.getRequestDispatcher(errorURL).forward(request,
                                                               response);
            }
        } catch (Exception _e) {
        	_e.printStackTrace();
        }
    }


    /**
     * Method loadAppSettings.
     * @param app ServletContext
     * @param request HttpServletRequest
     */
    private static synchronized void loadAppSettings(ServletContext app, HttpServletRequest request) {
        if (!AppSettings.isLoaded()) {
            AppSettings.load(app);
            AppSettings.establishAppWebPath(request);

            // corect the graphics paths (only after the initial install)
            AppSettings.correctGraphicsPaths();
        } else {
        	AppSettings.establishAppWebPath(request);
        }
    }


    /**
     * Method destroy.
     */
    public void destroy() {
        ConnectionPool.closeConnectionPool();
    }
    
    public void forward(String path) throws Exception {
    	request.getRequestDispatcher(path).forward(request, response);
    }

}
