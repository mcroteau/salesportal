package com.randr.webdw.company;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class CompanyDetails extends AbstractDetails {
    protected BigDecimal companyId;
    protected String company;
    protected BigDecimal dateFormatId;
    protected BigDecimal timeOffsetId;
    
    protected String dateFormat;
    protected BigDecimal timeOffset;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("company_id", "companyId");
        addMapping("company", "company");
        addMapping("date_format_id", "dateFormatId");
        addMapping("time_offset_id", "timeOffsetId");
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
     * Method getCompany.
     * @return String
     */
    public String getCompany() {
        return company;
    }

    /**
     * Method setCompany.
     * @param company String
     */
    public void setCompany(String company) {
        this.company = company;
    }

	public BigDecimal getDateFormatId() {
		return dateFormatId;
	}

	public void setDateFormatId(BigDecimal dateFormatId) {
		this.dateFormatId = dateFormatId;
	}

	public BigDecimal getTimeOffsetId() {
		return timeOffsetId;
	}

	public void setTimeOffsetId(BigDecimal timeOffsetId) {
		this.timeOffsetId = timeOffsetId;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public BigDecimal getTimeOffset() {
		return timeOffset;
	}

	public void setTimeOffset(BigDecimal timeOffset) {
		this.timeOffset = timeOffset;
	}
}
