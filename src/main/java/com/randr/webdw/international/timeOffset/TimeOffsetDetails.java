package com.randr.webdw.international.timeOffset;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 * @author randr
 * @version $Revision: 1.2 $
 */
public class TimeOffsetDetails extends AbstractDetails {
    protected BigDecimal timeOffsetId;
    protected BigDecimal timeOffset;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("time_offset_id", "timeOffsetId");
        addMapping("time_offset", "timeOffset");
    }

	public BigDecimal getTimeOffset() {
		return timeOffset;
	}

	public void setTimeOffset(BigDecimal timeOffset) {
		this.timeOffset = timeOffset;
	}

	public BigDecimal getTimeOffsetId() {
		return timeOffsetId;
	}

	public void setTimeOffsetId(BigDecimal timeOffsetId) {
		this.timeOffsetId = timeOffsetId;
	}

}
