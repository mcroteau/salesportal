package com.randr.webdw.user;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;
import com.randr.webdw.util.Utilities;


/**
 */
public class UserDetails extends AbstractDetails {
    // user type, defaulted to user customer
    protected BigDecimal userType;
    protected BigDecimal userId;

    protected BigDecimal active;

    protected String userName;
    protected String password;

    protected String firstName;
    protected String lastName;

    protected BigDecimal companyId;
    protected BigDecimal systemTypeId;
    protected BigDecimal territoryId;
    protected BigDecimal lobId;
    protected BigDecimal softwareTypeId;
    protected BigDecimal statusId;
    protected BigDecimal verifiedId;
    protected BigDecimal sizeId;
    protected BigDecimal stateId;
    protected BigDecimal countryId;

    protected String companyName;
    protected BigDecimal companyDateFormatId;
    protected BigDecimal companyTimeOffsetId;
    protected String companyDateFormat;
    protected BigDecimal companyTimeOffset;
    protected String systemTypeName;
    protected String territoryName;
    protected String lobName;
    protected String softwareTypeName;
    protected String statusName;
    protected String verifiedName;
    protected String sizeName;

    protected String countryName;
    protected String stateName;
    
    protected BigDecimal dateFormatId;
    protected BigDecimal timeOffsetId;
    
    protected String dateFormat;
    protected BigDecimal timeOffset;

    protected String email;
    protected Timestamp creationDate;
    protected Timestamp changeDate;

    protected BigDecimal customQueryNo;

    protected BigDecimal territoryIdOrNull;
    
    protected BigDecimal allowCalendarViewOfAllTerritories;
    
    protected BigDecimal specificTerritories;
    protected BigDecimal specificStatuses;
    protected BigDecimal specificLobs;
    
    protected BigDecimal limitProspectSearchView;
    protected BigDecimal limitCompanyView;

    
    
    // public constants
    public static final BigDecimal USER_TYPE_SALESMAN = new BigDecimal(1);
    
    // ONLY SALESMEN ARE BEING USED RIGHT NOW. NEED TO ADD 
    public static final BigDecimal USER_TYPE_ADMIN = new BigDecimal(2);
    
 // ONly allowed to work with prospects and not allowed to download or drill thru, admin, etc. 
    public static final BigDecimal USER_TYPE_INQUIRY_ONLY = new BigDecimal(3);

    public static final BigDecimal USER_ACTIVE = new BigDecimal(1);
    public static final BigDecimal USER_NOT_ACTIVE = new BigDecimal(0);
    
    public static final BigDecimal SPECIFIC_TERRITORIES = new BigDecimal(1);
    public static final BigDecimal NO_SPECIFIC_TERRITORIES = new BigDecimal(0);
    
    public static final BigDecimal SPECIFIC_STATUSES = new BigDecimal(1);
    public static final BigDecimal NO_SPECIFIC_STATUSES = new BigDecimal(0);
 
    public static final BigDecimal SPECIFIC_LOBS = new BigDecimal(1);
    public static final BigDecimal NO_SPECIFIC_LOBS = new BigDecimal(0);
    
    public static final BigDecimal LIMIT_VIEW = new BigDecimal(1);
    public static final BigDecimal DO_NOT_LIMIT_VIEW = new BigDecimal(0);

    public static final BigDecimal LIMIT_COMPANY = new BigDecimal(1);
    public static final BigDecimal DO_NOT_LIMIT_COMPANY = new BigDecimal(0);
    
    
    public static final BigDecimal APPLICATION_USER = new BigDecimal(0);
    
    // private constants
    public static final String[] userTypeDescription = {"Salesman", "Admin"};
    public static final String[] userTypeDescriptionPlural = {"Salesmen", "Admins"};
    
    public static final String DATA_IMPORT = "Data Import";


    // private constants
    /**
     * Constructor for UserDetails.
     */
    public UserDetails() {
    }

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("user_id", "userId");
        addMapping("user_type", "userType");
        addMapping("active", "active");

        addMapping("user_name", "userName");
        addMapping("password", "password");

        addMapping("first_name", "firstName");
        addMapping("last_name", "lastName");

        addMapping("company_id", "companyId");
        addMapping("system_type_id", "systemTypeId");
        addMapping("territory_id", "territoryId");
        addMapping("lob_id", "lobId");
        addMapping("software_type_id", "softwareTypeId");
        addMapping("status_id", "statusId");
        addMapping("size_id", "sizeId");
        addMapping("verified_id", "verifiedId");

        addMapping("country_id", "countryId");
        addMapping("state_id", "stateId");

        addMapping("email", "email");
        addMapping("creation_date", "creationDate");
        addMapping("change_date", "changeDate");
        
        addMapping("allow_cal_of_all_territories", "allowCalendarViewOfAllTerritories");
        
        addMapping("time_offset_id", "timeOffsetId");
        addMapping("date_format_id", "dateFormatId");
        
        addMapping("specific_territories", "specificTerritories");
        addMapping("specific_statuses", "specificStatuses");
        addMapping("specific_lobs", "specificLobs");
        
        addMapping("limit_view", "limitProspectSearchView");
        addMapping("limit_company", "limitCompanyView");
        
        //join
        addMapping("c.company", "companyName");
        addMapping("c.time_offset_id", "companyTimeOffsetId");
        addMapping("c.date_format_id", "companyDateFormatId");

        
        addMapping("t.territory", "territoryName");
        addMapping("l.lob", "lobName");
        addMapping("sy.system_type", "systemTypeName");
        addMapping("sf.software_type", "softwareTypeName");
        addMapping("st.status", "statusName");
        addMapping("si.size", "sizeName");
        addMapping("v.verified", "verifiedName");

        addMapping("cn.country", "countryName");
        addMapping("sta.state", "stateName");

        addMapping("qu.custom_query_no", "customQueryNo");
        addMapping("tof.time_offset", "timeOffset");
        addMapping("df.date_format", "dateFormat");
        
    }

    /**
     * Method getUserType.
     * @return BigDecimal
     */
    public BigDecimal getUserType() {
        return userType;
    }

    /**
     * Method setUserType.
     * @param userType BigDecimal
     */
    public void setUserType(BigDecimal userType) {
        this.userType = userType;
    }

    /**
     * Method getUserId.
     * @return BigDecimal
     */
    public BigDecimal getUserId() {
        return userId;
    }

    /**
     * Method setUserId.
     * @param userId BigDecimal
     */
    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    /**
     * Method getActive.
     * @return BigDecimal
     */
    public BigDecimal getActive() {
        return active;
    }

    /**
     * Method setActive.
     * @param active BigDecimal
     */
    public void setActive(BigDecimal active) {
        this.active = active;
    }

    /**
     * Method getUserName.
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method setUserName.
     * @param userName String
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Method getPassword.
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method setPassword.
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method getFirstName.
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method setFirstName.
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method getLastName.
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method setLastName.
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Method getCompanyId.
     * @return BigDecimal
     */
    public BigDecimal getCompanyId() {
        return companyId;
    }

    /**
     * Method setCompanyId.
     * @param companyId BigDecimal
     */
    public void setCompanyId(BigDecimal companyId) {
        this.companyId = companyId;
    }

    /**
     * Method getSystemTypeId.
     * @return BigDecimal
     */
    public BigDecimal getSystemTypeId() {
        return systemTypeId;
    }

    /**
     * Method setSystemTypeId.
     * @param systemTypeId BigDecimal
     */
    public void setSystemTypeId(BigDecimal systemTypeId) {
        this.systemTypeId = systemTypeId;
    }

    /**
     * Method getTerritoryId.
     * @return BigDecimal
     */
    public BigDecimal getTerritoryId() {
        return territoryId;
    }

    /**
     * Method setTerritoryId.
     * @param territoryId BigDecimal
     */
    public void setTerritoryId(BigDecimal territoryId) {
        this.territoryId = territoryId;
    }

    /**
     * Method getLobId.
     * @return BigDecimal
     */
    public BigDecimal getLobId() {
        return lobId;
    }

    /**
     * Method setLobId.
     * @param lobId BigDecimal
     */
    public void setLobId(BigDecimal lobId) {
        this.lobId = lobId;
    }

    /**
     * Method getSoftwareTypeId.
     * @return BigDecimal
     */
    public BigDecimal getSoftwareTypeId() {
        return softwareTypeId;
    }

    /**
     * Method setSoftwareTypeId.
     * @param softwareTypeId BigDecimal
     */
    public void setSoftwareTypeId(BigDecimal softwareTypeId) {
        this.softwareTypeId = softwareTypeId;
    }

    /**
     * Method getStatusId.
     * @return BigDecimal
     */
    public BigDecimal getStatusId() {
        return statusId;
    }

    /**
     * Method setStatusId.
     * @param statusId BigDecimal
     */
    public void setStatusId(BigDecimal statusId) {
        this.statusId = statusId;
    }

    /**
     * Method getVerifiedId.
     * @return BigDecimal
     */
    public BigDecimal getVerifiedId() {
        return verifiedId;
    }

    /**
     * Method setVerifiedId.
     * @param verifiedId BigDecimal
     */
    public void setVerifiedId(BigDecimal verifiedId) {
        this.verifiedId = verifiedId;
    }

    /**
     * Method getCompanyName.
     * @return String
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Method setCompanyName.
     * @param companyName String
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Method getSystemTypeName.
     * @return String
     */
    public String getSystemTypeName() {
        return systemTypeName;
    }

    /**
     * Method setSystemTypeName.
     * @param systemTypeName String
     */
    public void setSystemTypeName(String systemTypeName) {
        this.systemTypeName = systemTypeName;
    }

    /**
     * Method getTerritoryName.
     * @return String
     */
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * Method setTerritoryName.
     * @param territoryName String
     */
    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    /**
     * Method getLobName.
     * @return String
     */
    public String getLobName() {
        return lobName;
    }

    /**
     * Method setLobName.
     * @param lobName String
     */
    public void setLobName(String lobName) {
        this.lobName = lobName;
    }

    /**
     * Method getSoftwareTypeName.
     * @return String
     */
    public String getSoftwareTypeName() {
        return softwareTypeName;
    }

    /**
     * Method setSoftwareTypeName.
     * @param softwareTypeName String
     */
    public void setSoftwareTypeName(String softwareTypeName) {
        this.softwareTypeName = softwareTypeName;
    }

    /**
     * Method getStatusName.
     * @return String
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * Method setStatusName.
     * @param statusName String
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * Method getVerifiedName.
     * @return String
     */
    public String getVerifiedName() {
        return verifiedName;
    }

    /**
     * Method setVerifiedName.
     * @param verifiedName String
     */
    public void setVerifiedName(String verifiedName) {
        this.verifiedName = verifiedName;
    }

    /**
     * Method getEmail.
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method setEmail.
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method getStateId.
     * @return BigDecimal
     */
    public BigDecimal getStateId() {
        return stateId;
    }

    /**
     * Method setStateId.
     * @param stateId BigDecimal
     */
    public void setStateId(BigDecimal stateId) {
        this.stateId = stateId;
    }

    /**
     * Method getCountryId.
     * @return BigDecimal
     */
    public BigDecimal getCountryId() {
        return countryId;
    }

    /**
     * Method setCountryId.
     * @param countryId BigDecimal
     */
    public void setCountryId(BigDecimal countryId) {
        this.countryId = countryId;
    }

    /**
     * Method getCountryName.
     * @return String
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Method setCountryName.
     * @param countryName String
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Method getStateName.
     * @return String
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * Method setStateName.
     * @param stateName String
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * Method getCreationDate.
     * @return Timestamp
     */
    public Timestamp getCreationDate() {
        return creationDate;
    }

    /**
     * Method setCreationDate.
     * @param creationDate Timestamp
     */
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Method getChangeDate.
     * @return Timestamp
     */
    public Timestamp getChangeDate() {
        return changeDate;
    }

    /**
     * Method setChangeDate.
     * @param changeDate Timestamp
     */
    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    /**
     * Method getCustomQueryNo.
     * @return BigDecimal
     */
    public BigDecimal getCustomQueryNo() {
        return customQueryNo;
    }

    /**
     * Method setCustomQueryNo.
     * @param customQueryNo BigDecimal
     */
    public void setCustomQueryNo(BigDecimal customQueryNo) {
        this.customQueryNo = customQueryNo;
    }

    /**
     * Method getUserTypeDescription.
     * @param userType BigDecimal
     * @param plural boolean
     * @return String
     */
    public static String getUserTypeDescription(BigDecimal userType, boolean plural) {
        if (userType == null) {
            return "";
        }

        if (!plural) {
            return userTypeDescription[userType.intValue() - 1];
        } else {
            return userTypeDescriptionPlural[userType.intValue() - 1];
        }
    }

    /**
     * Method getTerritoryIdOrNull.
     * @return BigDecimal
     */
    public BigDecimal getTerritoryIdOrNull() {
        return territoryIdOrNull;
    }

    /**
     * Method setTerritoryIdOrNull.
     * @param territoryIdOrNull BigDecimal
     */
    public void setTerritoryIdOrNull(BigDecimal territoryIdOrNull) {
        this.territoryIdOrNull = territoryIdOrNull;
    }

    /**
     * Method getDropDownText.
     * @return String
     */
    public String getDropDownText() {
        String strResult = this.userName;
        if (this.firstName != null && this.lastName != null) {
            strResult += " [ "
                         + Utilities.nullToBlank(this.firstName)
                         + " "
                         + this.lastName;
            if (this.companyName != null) {
                strResult += " from " + this.companyName;
            }
            strResult += " ]";
        }
        return strResult;
    }

    /**
     * Method getSizeId.
     * @return BigDecimal
     */
    public BigDecimal getSizeId() {
        return sizeId;
    }

    /**
     * Method setSizeId.
     * @param sizeId BigDecimal
     */
    public void setSizeId(BigDecimal sizeId) {
        this.sizeId = sizeId;
    }

    /**
     * Method getSizeName.
     * @return String
     */
    public String getSizeName() {
        return sizeName;
    }

    /**
     * Method setSizeName.
     * @param sizeName String
     */
    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    /**
     * Method toString.
     * @return String
     */
    public String toString() {
        return "UserDetails{" +
               "userType=" + userType +
               ", userId=" + userId +
               ", active=" + active +
               ", userName='" + userName + "'" +
               ", password='" + password + "'" +
               ", firstName='" + firstName + "'" +
               ", lastName='" + lastName + "'" +
               ", companyId=" + companyId +
               ", systemTypeId=" + systemTypeId +
               ", territoryId=" + territoryId +
               ", lobId=" + lobId +
               ", softwareTypeId=" + softwareTypeId +
               ", statusId=" + statusId +
               ", verifiedId=" + verifiedId +
               ", sizeId=" + sizeId +
               ", stateId=" + stateId +
               ", countryId=" + countryId +
               ", companyName='" + companyName + "'" +
               ", systemTypeName='" + systemTypeName + "'" +
               ", territoryName='" + territoryName + "'" +
               ", lobName='" + lobName + "'" +
               ", softwareTypeName='" + softwareTypeName + "'" +
               ", statusName='" + statusName + "'" +
               ", verifiedName='" + verifiedName + "'" +
               ", sizeName='" + sizeName + "'" +
               ", countryName='" + countryName + "'" +
               ", stateName='" + stateName + "'" +
               ", email='" + email + "'" +
               ", creationDate=" + creationDate +
               ", changeDate=" + changeDate +
               ", customQueryNo=" + customQueryNo +
               ", territoryIdOrNull=" + territoryIdOrNull +
               ", dateFormatId=" + dateFormatId +
               ", timeOffsetId=" + timeOffsetId +
               "}";
    }

	public BigDecimal getAllowCalendarViewOfAllTerritories() {
		return allowCalendarViewOfAllTerritories;
	}

	public void setAllowCalendarViewOfAllTerritories(
			BigDecimal allowCalendarViewOfAllTerritories) {
		this.allowCalendarViewOfAllTerritories = allowCalendarViewOfAllTerritories;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public BigDecimal getDateFormatId() {
		return dateFormatId;
	}

	public void setDateFormatId(BigDecimal dateFormatId) {
		this.dateFormatId = dateFormatId;
	}

	public BigDecimal getTimeOffsetId() {
//		if(timeOffsetId ==null && getCompanyTimeOffsetId() ==null){
//			return new BigDecimal(1);
//		}else if(timeOffsetId ==null ){
//			return getCompanyTimeOffsetId();
//		}
		return timeOffsetId;
	}

	public void setTimeOffsetId(BigDecimal timeOffsetId) {
		this.timeOffsetId = timeOffsetId;
	}

	public BigDecimal getTimeOffset() {
		return timeOffset;
	}

	public void setTimeOffset(BigDecimal timeOffset) {
		this.timeOffset = timeOffset;
	}

	public String getCompanyDateFormat() {
		return companyDateFormat;
	}

	public void setCompanyDateFormat(String companyDateFormat) {
		this.companyDateFormat = companyDateFormat;
	}

	public BigDecimal getCompanyDateFormatId() {
		return companyDateFormatId;
	}

	public void setCompanyDateFormatId(BigDecimal companyDateFormatId) {
		this.companyDateFormatId = companyDateFormatId;
	}

	public BigDecimal getCompanyTimeOffset() {
		return companyTimeOffset;
	}

	public void setCompanyTimeOffset(BigDecimal companyTimeOffset) {
		this.companyTimeOffset = companyTimeOffset;
	}

	public BigDecimal getCompanyTimeOffsetId() {
		return companyTimeOffsetId;
	}

	public void setCompanyTimeOffsetId(BigDecimal companyTimeOffsetId) {
		this.companyTimeOffsetId = companyTimeOffsetId;
	}


	public BigDecimal getSpecificStatuses() {
		return specificStatuses;
	}

	public void setSpecificStatuses(BigDecimal specificStatuses) {
		this.specificStatuses = specificStatuses;
	}

	public BigDecimal getSpecificTerritories() {
		return specificTerritories;
	}

	public void setSpecificTerritories(BigDecimal specificTerritories) {
		this.specificTerritories = specificTerritories;
	}
	
	public BigDecimal getSpecificLobs() {
		return specificLobs;
	}

	public void setSpecificLobs(BigDecimal specificLobs) {
		this.specificLobs = specificLobs;
	}
	
	public BigDecimal getLimitProspectSearchView() {
		return limitProspectSearchView;
	}

	public void setLimitProspectSearchView(BigDecimal limitProspectSearchView) {
		this.limitProspectSearchView = limitProspectSearchView;
	}

	public BigDecimal getLimitCompanyView() {
		return limitCompanyView;
	}

	public void setLimitCompanyView(BigDecimal limitCompanyView) {
		this.limitCompanyView = limitCompanyView;
	}


}


