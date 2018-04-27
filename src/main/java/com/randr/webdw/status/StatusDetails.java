package com.randr.webdw.status;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class StatusDetails extends AbstractDetails {
    protected BigDecimal statusId;
    protected String status;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("status_id", "statusId");
        addMapping("status", "status");
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
     * Method getStatus.
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Method setStatus.
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
