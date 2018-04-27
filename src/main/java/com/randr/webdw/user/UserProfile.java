package com.randr.webdw.user;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import com.randr.webdw.company.CompanyDetails;
import com.randr.webdw.company.CompanyView;
import com.randr.webdw.util.ConnectionPool;
import com.randr.webdw.util.ReflectionUtil;
// import com.sun.corba.ee.connection.Connection;


/**
 */
public class UserProfile extends UserDetails {
    protected Connection connection = null;
    private boolean validProfile = false;
    private int errorCode = UserProfile.ERROR_NONE;

    // public constants
    public static final int ERROR_NONE = 0;
    public static final int ERROR_INVALID_USER_NAME = 1;
    public static final int ERROR_INVALID_PASSWORD = 2;
    public static final int ERROR_ACCOUNT_INACTIVE = 3;
    public static final int ERROR_USER_NOT_SPECIFIED = 4;

    /**
     * Constructor for UserProfile.
     */
    public UserProfile() {
        this.userName = "";
        this.password = "";
    }

    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
    }

    /**
     * Method isValidProfile.
     * @return boolean
     */
    public boolean isValidProfile() {
        return this.validProfile;
    }

    /**
     * Method loadProfile.
     */
    public synchronized void loadProfile() {
        try {
            this.validProfile = false;

            if (this.userName == null || this.userName.equals("")) {
                this.errorCode = UserProfile.ERROR_USER_NOT_SPECIFIED;
                return;
            }

            openConnection();

            UserDetails userDetails = new UserDetails();
            userDetails.setUserName(this.userName);

            UserView userView = new UserView();
            userView.fillWithElements(connection, UserView.FILL_TYPE_LOGIN, userDetails);

            if (userView.getElements().size() == 0) {
                this.errorCode = ERROR_INVALID_USER_NAME;
            } else {
                userDetails = (UserDetails) userView.getElements().elementAt(0);

                if (this.password == null || !this.password.equals(userDetails.getPassword())) {
                    this.errorCode = ERROR_INVALID_PASSWORD;
                } else if (userDetails.getActive().equals(new BigDecimal(0))) {
                    this.errorCode = ERROR_ACCOUNT_INACTIVE;
                } else {
                    this.validProfile = true;
                    this.errorCode = 0;
                    loadProfileFromUserDetails(userDetails);
                    if(userDetails.getDateFormat()==null){
                    	this.dateFormat=getDateDefaultFormat(userDetails);
                    }
                    if(userDetails.getTimeOffset()==null){
                    	this.timeOffset=getTimeDefaultOffset(userDetails);
                    }

                }
            }
            commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finallyMethod();
        }
    }

    /**
     * @param userDetails
     * @return
     * @throws Exception
     */
    private String getDateDefaultFormat(UserDetails userDetails) throws Exception{
		CompanyDetails companyDetails = new CompanyDetails();
		CompanyView companyView = new CompanyView();
		companyDetails.setCompanyId(userDetails.getCompanyId());
		companyView.fillWithElements(connection,CompanyView.FILL_TYPE_BASIC , companyDetails);
    	companyDetails = (CompanyDetails)companyView.getElement(0);
    	if(companyDetails.getDateFormat() ==null){
			return "MM/dd/yyyy";
		}else {
			return companyDetails.getDateFormat();
		}
	}

    /**
     * @param userDetails
     * @return
     * @throws Exception
     */
    private BigDecimal getTimeDefaultOffset(UserDetails userDetails) throws Exception{
		CompanyDetails companyDetails = new CompanyDetails();
		CompanyView companyView = new CompanyView();
		companyDetails.setCompanyId(userDetails.getCompanyId());
		companyView.fillWithElements(connection,CompanyView.FILL_TYPE_BASIC , companyDetails);
    	companyDetails = (CompanyDetails)companyView.getElement(0);
    	if(companyDetails.getTimeOffset() ==null){
			return new BigDecimal(0);
		}else {
			return companyDetails.getTimeOffset();
		}
	}
	/**
     * Method loadProfileFromUserDetails.
     * @param userDetails UserDetails
     */
    private void loadProfileFromUserDetails(UserDetails userDetails) {
/*
        this.userType = userDetails.getUserType();
        this.userId = userDetails.getUserId();
        this.active = userDetails.getActive();
        this.firstName = userDetails.getFirstName();
        this.lastName = userDetails.getLastName();
        this.userName = userDetails.getUserName();
*/
        ReflectionUtil.copyPropertyValues(this, userDetails, false);
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
     * Method getErrorCode.
     * @return int
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Method display.
     * @return String
     */
    public String display() {
        String resStr = this.userName + "<br>";
        resStr += "[" + this.firstName + " " + this.lastName + "]";
        return resStr;
    }

    /**
     * Method toString.
     * @return String
     */
    public String toString() {
        return "UserProfile{" +
                "connection=" + connection +
                ", validProfile=" + validProfile +
                ", errorCode=" + errorCode +
                ", " + super.toString() +
                "}";
    }
}

