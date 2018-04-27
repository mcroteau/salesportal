package com.randr.webdw.contact;

import com.randr.webdw.mvcAbstract.AbstractView;

/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class ContactView extends AbstractView {
    /**
     * Constructor for ContactView.
     */
    public ContactView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.contact.ContactBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.contact.ContactDetails";
    }
}
