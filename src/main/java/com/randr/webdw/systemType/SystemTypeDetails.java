package com.randr.webdw.systemType;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class SystemTypeDetails extends AbstractDetails {
    protected BigDecimal systemTypeId;
    protected String systemType;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("system_type_id", "systemTypeId");
        addMapping("system_type", "systemType");
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
     * Method getSystemType.
     * @return String
     */
    public String getSystemType() {
        return systemType;
    }

    /**
     * Method setSystemType.
     * @param systemType String
     */
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }
}
