package com.randr.webdw.customQuery;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CustomQueryView extends AbstractView {

    public static final BigDecimal FILL_TYPE_BASIC = new BigDecimal(1);

    /**
     * Constructor for CustomQueryView.
     */
    public CustomQueryView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.customQuery.CustomQueryBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.customQuery.CustomQueryDetails";
    }

}