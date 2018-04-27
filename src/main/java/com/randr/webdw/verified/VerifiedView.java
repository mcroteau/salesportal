package com.randr.webdw.verified;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 */
public class VerifiedView extends AbstractView {
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.verified.VerifiedBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.verified.VerifiedDetails";
    }
}
