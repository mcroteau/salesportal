package com.randr.webdw.emailSalesAction;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 */
public class EmailSalesActionView extends AbstractView {
	
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
	
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.emailSalesAction.EmailSalesActionBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.emailSalesAction.EmailSalesActionDetails";
    }

 
}
