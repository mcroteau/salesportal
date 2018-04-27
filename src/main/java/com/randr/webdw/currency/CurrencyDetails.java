package com.randr.webdw.currency;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class CurrencyDetails extends AbstractDetails {
	 // table fields mappings
    private BigDecimal currencyId;
    private String currencyCode;
    private String currencyDescription;
    private Timestamp creationDate;
    private String creationUser;
    private Timestamp updateDate;
    private String updateUser;

    // public constants
    public static final String DEFAULT_SORT_KEY = "getCurrencyCode";

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("currency_id", "currencyId");
        addMapping("currency_code", "currencyCode");
        addMapping("currency_description", "currencyDescription");
        addMapping("creation_date", "creationDate");
        addMapping("created_by_user", "creationUser");
        addMapping("update_date", "updateDate");
        addMapping("updated_by_user", "updateUser");
    }

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyDescription() {
		return currencyDescription;
	}

	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

 
}
