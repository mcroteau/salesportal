package com.randr.webdw.verified;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class VerifiedDetails extends AbstractDetails {
    protected BigDecimal verifiedId;
    protected String verified;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("verified_id", "verifiedId");
        addMapping("verified", "verified");
    }

    /**
     * Method getVerifiedId.
     * @return BigDecimal
     */
    public BigDecimal getVerifiedId() {
        return verifiedId;
    }

    /**
     * Method setVerifiedId.
     * @param verifiedId BigDecimal
     */
    public void setVerifiedId(BigDecimal verifiedId) {
        this.verifiedId = verifiedId;
    }

    /**
     * Method getVerified.
     * @return String
     */
    public String getVerified() {
        return verified;
    }

    /**
     * Method setVerified.
     * @param verified String
     */
    public void setVerified(String verified) {
        this.verified = verified;
    }
}
