package com.randr.webdw.currency;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 */
public class CurrencyView extends AbstractView {
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.currency.CurrencyBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.currency.CurrencyDetails";
    }
}
