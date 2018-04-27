package com.randr.webdw.company;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 */
public class CompanyView extends AbstractView {
	public static final BigDecimal FILL_TYPE_BASIC = new BigDecimal(1);
	
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.company.CompanyBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.company.CompanyDetails";
    }
}
