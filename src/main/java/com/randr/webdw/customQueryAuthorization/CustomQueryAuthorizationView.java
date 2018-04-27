package com.randr.webdw.customQueryAuthorization;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractView;

/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CustomQueryAuthorizationView extends AbstractView {
    public static final BigDecimal FILL_TYPE_BASIC = new BigDecimal(1);

    /**
     * Constructor for CustomQueryAuthorizationView.
     */
    public CustomQueryAuthorizationView() {
    }


    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.customQueryAuthorization.CustomQueryAuthorizationBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.customQueryAuthorization.CustomQueryAuthorizationDetails";
    }


}