package com.randr.webdw.commision;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.randr.webdw.mvcAbstract.AbstractDetails;
import com.randr.webdw.util.Utilities;


/**
 */
public class CommisionDetails extends AbstractDetails {
    protected BigDecimal commisionId;
    protected BigDecimal userId;// salesman
    protected String description;
    protected BigDecimal amount;
    protected Date dateSold;// order date from OEP
    protected Date dateBilled; // invoice date from OEP
    protected Date datePaid;
    protected String checkNumber;
    protected BigDecimal checkAmount;
    protected BigDecimal prospectId;
    protected String prospectName;
    protected BigDecimal currencyId;  

    protected Timestamp creationDate;
    protected String internalDescription;

    //added for revenue tracking:
    protected Timestamp expectedCloseDate;
    protected Timestamp originalExpectedCloseDate;
    protected Timestamp cancelDate;
    protected BigDecimal revenue;
    protected BigDecimal commissionTypeId;

    
    //additional informatin fields imported from OEP
    protected Timestamp date1;
    protected Timestamp date2;
    protected Timestamp date3;
    protected Timestamp date4;
    protected Timestamp date5;
    protected String yesNo1;//0=no, 1=yes
    protected BigDecimal numeric1;
    //protected BigDecimal dropDown1Id;//key to dropDown
    //used in contract export
    protected String dropDown1Name; //we will import the value from OEP
    protected BigDecimal oepTransactionid;//unique commisison record from OEP = userId, prospectId, oepTransactionid
    protected BigDecimal totalUnits;
    protected BigDecimal totalUnitsClosed;
    protected BigDecimal totalUnitsOpen;
    //used in joins:
    protected String userFirstName;
    protected String userLastName;
    protected String currencyCode;
    protected String poNumber;

    //used in searches:
    protected BigDecimal status;
    protected Date dateSold1;
    protected Date dateSold2;
    protected Date datePaid1;
    protected Date datePaid2;

    //constants:
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
    public static final BigDecimal FILL_TYPE_JOIN = new BigDecimal(2);

    public static final BigDecimal STATUS_OPEN = new BigDecimal(1);
    public static final BigDecimal STATUS_PAID = new BigDecimal(2);

    private static final String statusDescription[] = {"Open", "Paid"};

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("commision_id", "commisionId");
        addMapping("user_id", "userId");
        addMapping("description", "description");
        addMapping("amount", "amount");
        addMapping("date_sold", "dateSold");
        addMapping("date_billed", "dateBilled");
        addMapping("date_paid", "datePaid");
        addMapping("check_number", "checkNumber");
        addMapping("check_amount", "checkAmount");
        addMapping("creation_date", "creationDate");
        addMapping("internal_description", "internalDescription");
        addMapping("commission_prospect_id", "prospectId");
        addMapping("prospect_name", "prospectName");
        addMapping("commission_currency_id", "currencyId");
        addMapping("po_number", "poNumber");
        addMapping("date1", "date1");
		addMapping("date2", "date2");
		addMapping("date3", "date3");
		addMapping("date4", "date4");
		addMapping("date5", "date5");
		addMapping("yes_no1", "yesNo1");
		addMapping("numeric1", "numeric1");
		addMapping("drop_down1_name", "dropDown1Name");
		addMapping("oep_transactionid", "oepTransactionid");
		addMapping("total_units", "totalUnits");
		addMapping("total_units_closed", "totalUnitsClosed");
		addMapping("expected_close_date", "expectedCloseDate");
		addMapping("original_expected_close_date", "originalExpectedCloseDate");
		addMapping("cancel_date", "cancelDate");
		addMapping("revenue", "revenue");
		addMapping("commission_type_id", "commissionTypeId");
		
        addMapping("u.first_name", "userFirstName");
        addMapping("u.last_name", "userLastName");
        
        addMapping("c.currency_code", "currencyCode");
        
        addMapping("p.customer_company", "prospectName");
    }

    /**
     * Method getCommisionId.
     * @return BigDecimal
     */
    public BigDecimal getCommisionId() {
        return commisionId;
    }

    /**
     * Method setCommisionId.
     * @param commisionId BigDecimal
     */
    public void setCommisionId(BigDecimal commisionId) {
        this.commisionId = commisionId;
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
     * Method getDescription.
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method setDescription.
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method getAmount.
     * @return BigDecimal
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Method setAmount.
     * @param amount BigDecimal
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Method getDateSold.
     * @return Date
     */
    public Date getDateSold() {
        return dateSold;
    }

    /**
     * Method setDateSold.
     * @param dateSold Date
     */
    public void setDateSold(Date dateSold) {
        this.dateSold = dateSold;
    }

    /**
     * Method getDateBilled.
     * @return Date
     */
    public Date getDateBilled() {
        return dateBilled;
    }

    /**
     * Method setDateBilled.
     * @param dateBilled Date
     */
    public void setDateBilled(Date dateBilled) {
        this.dateBilled = dateBilled;
    }

    /**
     * Method getDatePaid.
     * @return Date
     */
    public Date getDatePaid() {
        return datePaid;
    }

    /**
     * Method setDatePaid.
     * @param datePaid Date
     */
    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    /**
     * Method getCheckNumber.
     * @return String
     */
    public String getCheckNumber() {
        return checkNumber;
    }

    /**
     * Method setCheckNumber.
     * @param checkNumber String
     */
    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    /**
     * Method getCheckAmount.
     * @return BigDecimal
     */
    public BigDecimal getCheckAmount() {
        return checkAmount;
    }

    /**
     * Method setCheckAmount.
     * @param checkAmount BigDecimal
     */
    public void setCheckAmount(BigDecimal checkAmount) {
        this.checkAmount = checkAmount;
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
     * Method getInternalDescription.
     * @return String
     */
    public String getInternalDescription() {
        return internalDescription;
    }

    /**
     * Method setInternalDescription.
     * @param internalDescription String
     */
    public void setInternalDescription(String internalDescription) {
        this.internalDescription = internalDescription;
    }

    /**
     * Method getUserFirstName.
     * @return String
     */
    public String getUserFirstName() {
        return userFirstName;
    }

    /**
     * Method setUserFirstName.
     * @param userFirstName String
     */
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    /**
     * Method getUserLastName.
     * @return String
     */
    public String getUserLastName() {
        return userLastName;
    }

    /**
     * Method setUserLastName.
     * @param userLastName String
     */
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    /**
     * Method getStatus.
     * @return BigDecimal
     */
    public BigDecimal getStatus() {
        return status;
    }

    /**
     * Method setStatus.
     * @param status BigDecimal
     */
    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    /**
     * Method getDateSold1.
     * @return Date
     */
    public Date getDateSold1() {
        return dateSold1;
    }

    /**
     * Method setDateSold1.
     * @param dateSold1 Date
     */
    public void setDateSold1(Date dateSold1) {
        this.dateSold1 = dateSold1;
    }

    /**
     * Method getDateSold2.
     * @return Date
     */
    public Date getDateSold2() {
        return dateSold2;
    }

    /**
     * Method setDateSold2.
     * @param dateSold2 Date
     */
    public void setDateSold2(Date dateSold2) {
        this.dateSold2 = dateSold2;
    }

    /**
     * Method getDatePaid1.
     * @return Date
     */
    public Date getDatePaid1() {
        return datePaid1;
    }

    /**
     * Method setDatePaid1.
     * @param datePaid1 Date
     */
    public void setDatePaid1(Date datePaid1) {
        this.datePaid1 = datePaid1;
    }

    /**
     * Method getDatePaid2.
     * @return Date
     */
    public Date getDatePaid2() {
        return datePaid2;
    }

    /**
     * Method setDatePaid2.
     * @param datePaid2 Date
     */
    public void setDatePaid2(Date datePaid2) {
        this.datePaid2 = datePaid2;
    }

    /**
     * Method getStatusDescription.
     * @param status BigDecimal
     * @return String
     */
    public static String getStatusDescription(BigDecimal status) {
        if (status == null) {
            return "";
        }
        return statusDescription[status.intValue() - 1];
    }

    /**
     * Method getStatusDescription.
     * @param status int
     * @return String
     */
    public static String getStatusDescription(int status) {
        return statusDescription[status - 1];
    }

    /**
     * Method getStatusDescriptionLength.
     * @return int
     */
    public static int getStatusDescriptionLength() {
        return statusDescription.length;
    }

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public BigDecimal getProspectId() {
		return prospectId;
	}

	public void setProspectId(BigDecimal prospectId) {
		this.prospectId = prospectId;
	}

	public String getProspectName() {
		return prospectName;
	}

	public void setProspectName(String prospectName) {
		this.prospectName = prospectName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the date1
	 */
	public Timestamp getDate1() {
		return date1;
	}

	/**
	 * @param date1 the date1 to set
	 */
	public void setDate1(Timestamp date1) {
		this.date1 = date1;
	}

	/**
	 * @return the date2
	 */
	public Timestamp getDate2() {
		return date2;
	}

	/**
	 * @param date2 the date2 to set
	 */
	public void setDate2(Timestamp date2) {
		this.date2 = date2;
	}

	/**
	 * @return the date3
	 */
	public Timestamp getDate3() {
		return date3;
	}

	/**
	 * @param date3 the date3 to set
	 */
	public void setDate3(Timestamp date3) {
		this.date3 = date3;
	}

	/**
	 * @return the date4
	 */
	public Timestamp getDate4() {
		return date4;
	}

	/**
	 * @param date4 the date4 to set
	 */
	public void setDate4(Timestamp date4) {
		this.date4 = date4;
	}

	/**
	 * @return the date5
	 */
	public Timestamp getDate5() {
		return date5;
	}

	/**
	 * @param date5 the date5 to set
	 */
	public void setDate5(Timestamp date5) {
		this.date5 = date5;
	}

	/**
	 * @return the dropDown1Name
	 */
	public String getDropDown1Name() {
		return dropDown1Name;
	}

	/**
	 * @param dropDown1Name the dropDown1Name to set
	 */
	public void setDropDown1Name(String dropDown1Name) {
		this.dropDown1Name = dropDown1Name;
	}

	/**
	 * @return the numeric1
	 */
	public BigDecimal getNumeric1() {
		return numeric1;
	}

	/**
	 * @param numeric1 the numeric1 to set
	 */
	public void setNumeric1(BigDecimal numeric1) {
		this.numeric1 = numeric1;
	}

	/**
	 * @return the yesNo1
	 */
	public String getYesNo1() {
		return yesNo1;
	}

	/**
	 * @param yesNo1 the yesNo1 to set
	 */
	public void setYesNo1(String yesNo1) {
		this.yesNo1 = yesNo1;
	}

	/**
	 * @return the oepTransactionid
	 */
	public BigDecimal getOepTransactionid() {
		return oepTransactionid;
	}

	/**
	 * @param oepTransactionid the oepTransactionid to set
	 */
	public void setOepTransactionid(BigDecimal oepTransactionid) {
		this.oepTransactionid = oepTransactionid;
	}

	/**
	 * @return the totalUnits
	 */
	public BigDecimal getTotalUnits() {
		return totalUnits;
	}

	/**
	 * @param totalUnits the totalUnits to set
	 */
	public void setTotalUnits(BigDecimal totalUnits) {
		this.totalUnits = totalUnits;
	}

	/**
	 * @return the totalUnitsClosed
	 */
	public BigDecimal getTotalUnitsClosed() {
		return totalUnitsClosed;
	}

	/**
	 * @param totalUnitsClosed the totalUnitsClosed to set
	 */
	public void setTotalUnitsClosed(BigDecimal totalUnitsClosed) {
		this.totalUnitsClosed = totalUnitsClosed;
	}

	/**
	 * @return the totalUnitsOpen
	 */
	public BigDecimal getTotalUnitsOpen() {
		return Utilities.nullToZero(totalUnits).subtract(Utilities.nullToZero(totalUnitsClosed));
	}

	/**
	 * @param totalUnitsOpen the totalUnitsOpen to set
	 */
	public void setTotalUnitsOpen(BigDecimal totalUnitsOpen) {
		this.totalUnitsOpen = totalUnitsOpen;
	}

	public Timestamp getExpectedCloseDate() {
		return expectedCloseDate;
	}

	public void setExpectedCloseDate(Timestamp expectedCloseDate) {
		this.expectedCloseDate = expectedCloseDate;
	}

	public Timestamp getOriginalExpectedCloseDate() {
		return originalExpectedCloseDate;
	}

	public void setOriginalExpectedCloseDate(Timestamp originalExpectedCloseDate) {
		this.originalExpectedCloseDate = originalExpectedCloseDate;
	}

	public Timestamp getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Timestamp cancelDate) {
		this.cancelDate = cancelDate;
	}

	public BigDecimal getRevenue() {
		return revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	public BigDecimal getCommissionTypeId() {
		return commissionTypeId;
	}

	public void setCommissionTypeId(BigDecimal commissionTypeId) {
		this.commissionTypeId = commissionTypeId;
	}

}
