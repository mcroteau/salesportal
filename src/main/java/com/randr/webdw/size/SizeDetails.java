package com.randr.webdw.size;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class SizeDetails extends AbstractDetails {
    protected BigDecimal sizeId;
    protected String size;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("size_id", "sizeId");
        addMapping("size", "size");
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
     * Method getSize.
     * @return String
     */
    public String getSize() {
        return size;
    }

    /**
     * Method setSize.
     * @param size String
     */
    public void setSize(String size) {
        this.size = size;
    }
}
