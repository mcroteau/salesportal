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
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.randr.webdw.AppSettings;
import com.randr.webdw.startup.ApplicationConstants;
import com.randr.webdw.upload.UploadProcessor;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserProfile;
import com.randr.webdw.util.ConnectionPool;
// import com.sun.corba.ee.connection.Connection;


/**
 */
public abstract class AbstractController extends HttpServlet 
	implements SingleThreadModel, ApplicationConstants {

    public static final int UPLOAD_PRODUCT = 1;
    public static final int UPLOAD_DOCUMENT = 2;
    public static final int MAX_SELECT_IN_LIST_SIZE = 50;
    
    private static final String JSP_ERROR = "/jsp/error/error.jsp";
    private static final String JSP_STARTUP = "/jsp/startup/welcome.jsp";
    
    protected String formAction = null;
    protected Connection connection = null;
    protected UserProfile userProfile = null;
    protected UploadProcessor uploadProcessor = null;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    /**
     * Constructor for AbstractController.
     */
    public AbstractController() {
    }

    /**
     * Method getConnection.
     * @return Connection
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Method setConnection.
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
            AbstractController.loadAppSettings(getServletContext(), request);
            
            if(request.getSession().getServletContext().getAttribute(MESSAGE_NAME) != null) {
            	Integer messageValue = (Integer)request.getSession().getServletContext().getAttribute(MESSAGE_NAME);
            	switch(messageValue.intValue()) {
            		case 1 :
            			forward(JSP_ERROR);
            			break;
            		case 2 : 
            			forward(JSP_STARTUP);
            			break;
            		default :
            			break;
            	}
            } else {
	
	            formAction = request.getParameter("formAction");
	            request.setAttribute("formAction", formAction);
	
	            if (!request.getServletPath().startsWith("/admin")) {
	                if (!(request.getServletPath().equals("/user")
	                      && (formAction == null || formAction.equals("LOGIN")))) {
	                    loadUserProfile();
	
	                    if (!this.userProfile.isValidProfile()) {
	
	                        redirect(response, AppSettings.getAppCompleteWebPath(request) + "user");
	                        return;
	                    }
	                }
	
	                request.setAttribute("isAdmin", Boolean.FALSE);
	            } else {
	                request.setAttribute("isAdmin", Boolean.TRUE);
	            }
	            doAction();
            }
        } catch (Exception ex) {
            doCatch(ex);
        }
    }

    /**
     * Method isAdmin.
     * @return boolean
     */
    protected boolean isAdmin() {
        return request.getServletPath().startsWith("/admin");
    }

    /**
     * Method loadUserProfile.
     */
    private void loadUserProfile() {
        this.userProfile = (UserProfile) request.getSession().getAttribute("userProfile");

        if (this.userProfile == null || !this.userProfile.isValidProfile()) {
            this.userProfile = new UserProfile();

            if (request.getParameter("userName") != null) {
                this.userProfile.setUserName(request.getParameter("userName"));
                this.userProfile.setPassword(request.getParameter("password"));
            }

            this.userProfile.loadProfile();
            request.getSession().setAttribute("userProfile", this.userProfile);
        }
    }

    /**
     * Method sessionExpired.
     * @return boolean
     */
    private boolean sessionExpired() {
        if ((request.getAttribute("sessionExpired") != null)
                || (request.getSession().getAttribute("sessionExpired") != null)) {
            request.getSession().setAttribute("sessionExpired", new Boolean(true));
            return true;
        }

        if (request.getSession().getAttribute("sessionTimeoutNotifier") == null) {
            request.getSession().setAttribute("sessionTimeoutNotifier", new SessionTimeoutNotifier(request));
        }

        return false;
    }

    /**
     * Method doPost.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public abstract void doAction() throws ServletException, IOException;

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
                this.connection.close();
            } catch (Exception sql) {
            }
        }
    }

    /**
     * Method doCatch.
     * @param e Exception
     */
    protected void doCatch(Exception e) {
        doCatch(e, JSP_ERROR);
    }

    /**
     * Method doCatch.
     * @param e Exception
     * @param errorURL String
     */
    protected void doCatch(Exception e, String errorURL) {
        try {
            e.printStackTrace();
            finallyMethod();
            request.setAttribute("exception", e);

            if (!errorURL.equals("")) {
                request.getRequestDispatcher(errorURL).forward(request, response);
            }
        } catch (Exception _e) {
        }
    }

    /**
     * Method createUploadProcessor.
     * @param request HttpServletRequest
     */
    protected void createUploadProcessor(HttpServletRequest request) {
        createUploadProcessor(request, UPLOAD_PRODUCT);
    }

    /**
     * Method createUploadProcessor.
     * @param request HttpServletRequest
     * @param uploadType int
     */
    protected void createUploadProcessor(HttpServletRequest request, int uploadType) {
        try {
            if (uploadType == UPLOAD_PRODUCT) {
                uploadProcessor = new UploadProcessor(request, AppSettings.getGraphicsRealPath() + "temp");
            } else {
                uploadProcessor = new UploadProcessor(request, AppSettings.getAppRealPath() + "temp");
            }
        } catch (Exception m) {
            uploadProcessor = null;
//            m.printStackTrace();
        } finally {
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
     * Method loadAppSettings.
     * @param app ServletContext
     * @param request HttpServletRequest
     */
    public static synchronized void reloadAppSettings(ServletContext app, HttpServletRequest request) {

    	AppSettings.load(app);
        AppSettings.establishAppWebPath(request);

        // corect the graphics paths (only after the initial install)
        AppSettings.correctGraphicsPaths();

    }

    /**
     * Method redirect.
     * @param resp HttpServletResponse
     * @param sRedirURL String
     */
    private static void redirect(HttpServletResponse resp, String sRedirURL) {
        try {
            resp.sendRedirect(sRedirURL);
        } catch (java.io.IOException io) {
        } finally {
        }
    }
    
    public void forward(String path) throws Exception, IOException, ServletException {
    	request.getRequestDispatcher(path).forward(request, response);
    }
    
    

    /**
     * Method destroy.
     */
    public void destroy() {
        ConnectionPool.closeConnectionPool();
    }

    /**
     */
    class SessionTimeoutNotifier implements HttpSessionBindingListener {
        private HttpServletRequest request;

        /**
         * Constructor for SessionTimeoutNotifier.
         * @param request HttpServletRequest
         */
        SessionTimeoutNotifier(HttpServletRequest request) {
            this.request = request;
        }

        /**
         * Method valueBound.
         * @param event HttpSessionBindingEvent
         */
        public void valueBound(HttpSessionBindingEvent event) {
            //System.out.println("The session has started : " + event.getSession().getId());
        }

        /**
         * Method valueUnbound.
         * @param event HttpSessionBindingEvent
         */
        public void valueUnbound(HttpSessionBindingEvent event) {
            event.getSession().getId();
            request.setAttribute("sessionExpired", Boolean.TRUE);
        }
    }
    
	protected boolean isEnabled(String parm) {
		if (AppSettings.getParm(parm).equalsIgnoreCase("TRUE")||
        		AppSettings.getParm(parm).equalsIgnoreCase("YES")) {
			return true;
		} else {
			return false;
		}
	}
	
	protected void redirectIfNotValidAdminUserProfile() throws IOException, ServletException {
		loadUserProfile();
		//System.out.println("redirect, userProfile=" + userProfile );
		//System.out.println("redirect,  userType=" + userProfile.getUserType());
    	if(this.userProfile==null || !this.userProfile.isValidProfile() || !userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN)){
    		request.getRequestDispatcher("/jsp/public/user/login.jsp").forward(request, response);
    	}
 	}
	
}
