package com.randr.webdw.international.dateFormat;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 * @author randr
 * @version $Revision: 1.2 $
 */
public class DateFormatDetails extends AbstractDetails {
    protected BigDecimal dateFormatId;
    protected String dateFormat;
 
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("date_format_id", "dateFormatId");
        addMapping("date_format", "dateFormat");
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

}
