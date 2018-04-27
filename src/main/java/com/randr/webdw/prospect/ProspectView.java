package com.randr.webdw.prospect;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 */
public class ProspectView extends AbstractView {
    public static final BigDecimal FILL_TYPE_BASIC = new BigDecimal(1);
    public static final BigDecimal FILL_TYPE_REPORT = new BigDecimal(2);
    public static final BigDecimal FILL_TYPE_PRINT = new BigDecimal(3);
    public static final BigDecimal FILL_TYPE_NEXT_SALES_ACTION_NOTIFY = new BigDecimal(4);
    public static final BigDecimal FILL_TYPE_UPDATE_COLLECTION = new BigDecimal(5);
    public static final BigDecimal FILL_TYPE_SALES_ACTION_SEARCH = new BigDecimal(6);
    public static final BigDecimal FILL_TYPE_RANDOM = new BigDecimal(7);   
    public static final BigDecimal FILL_TYPE_ELIMINATE_DUPLICATES_EMAIL = new BigDecimal(8);
    public static final BigDecimal FILL_TYPE_ELIMINATE_DUPLICATES_COMPANY_NAME = new BigDecimal(9);   
    public static final BigDecimal FILL_TYPE_ELIMINATE_DUPLICATES_CONTACT_NAME = new BigDecimal(10);   
    public static final BigDecimal FILL_TYPE_ELIMINATE_DUPLICATES_PHONE_NUMBER = new BigDecimal(11); 
    public static final BigDecimal FILL_TYPE_INCLUDE_CONTACT_SEARCH = new BigDecimal(12);
    
    /**
     * Constructor for ProspectView.
     */
    public ProspectView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.prospect.ProspectBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.prospect.ProspectDetails";
    }

}


