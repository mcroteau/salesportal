package com.randr.webdw.prospectDocument;

import com.randr.webdw.mvcAbstract.AbstractView;

/**
 * Date: Oct 8, 2004
 * Time: 10:57:59 AM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class ProspectDocumentView extends AbstractView {
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.prospectDocument.ProspectDocumentBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.prospectDocument.ProspectDocumentDetails";
    }
}
