package com.randr.webdw.customQueryAuthorization;

import java.math.BigDecimal;

import com.randr.webdw.customQuery.CustomQueryDetails;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CustomQueryAuthorizationDetails extends CustomQueryDetails {

    private BigDecimal authorizedUserId;


    /**
     * Constructor for CustomQueryAuthorizationDetails.
     */
    public CustomQueryAuthorizationDetails() {
    }

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("a.user_id", "authorizedUserId");
    }

    /**
     * Method getAuthorizedUserId.
     * @return BigDecimal
     */
    public BigDecimal getAuthorizedUserId() {
        return authorizedUserId;
    }

    /**
     * Method setAuthorizedUserId.
     * @param authorizedUserId BigDecimal
     */
    public void setAuthorizedUserId(BigDecimal authorizedUserId) {
        this.authorizedUserId = authorizedUserId;
    }


}