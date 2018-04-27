package com.randr.webdw.website;

import com.randr.webdw.mvcAbstract.AbstractView;

/**
 * Date: Oct 8, 2004
 * Time: 10:57:59 AM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class WebsiteView extends AbstractView {
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.website.WebsiteBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.website.WebsiteDetails";
    }
}
