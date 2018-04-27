package com.randr.webdw.emailTemplate;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 */
public class EmailTemplateView extends AbstractView {
    /**
     * Constructor for NoteView.
     */
    public EmailTemplateView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.emailTemplate.EmailTemplateBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.emailTemplate.EmailTemplateDetails";
    }
}
