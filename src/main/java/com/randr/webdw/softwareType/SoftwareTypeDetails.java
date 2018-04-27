package com.randr.webdw.softwareType;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class SoftwareTypeDetails extends AbstractDetails {
    protected BigDecimal softwareTypeId;
    protected String softwareType;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("software_type_id", "softwareTypeId");
        addMapping("software_type", "softwareType");
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
     * Method getSoftwareType.
     * @return String
     */
    public String getSoftwareType() {
        return softwareType;
    }

    /**
     * Method setSoftwareType.
     * @param softwareType String
     */
    public void setSoftwareType(String softwareType) {
        this.softwareType = softwareType;
    }
}
