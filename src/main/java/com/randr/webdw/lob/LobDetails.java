package com.randr.webdw.lob;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class LobDetails extends AbstractDetails {
    protected BigDecimal lobId;
    protected String lob;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("lob_id", "lobId");
        addMapping("lob", "lob");
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
     * Method getLob.
     * @return String
     */
    public String getLob() {
        return lob;
    }

    /**
     * Method setLob.
     * @param lob String
     */
    public void setLob(String lob) {
        this.lob = lob;
    }
}
