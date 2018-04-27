package com.randr.webdw.prospectImportLog;

import com.randr.webdw.mvcAbstract.AbstractView;



/**
 * @author Randr
 * 
 */
public class ProspectImportLogView extends AbstractView {
	
	public ProspectImportLogView(){
		
	}
		
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.prospectImportLog.ProspectImportLogBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.prospectImportLog.ProspectImportLogDetails";
    }
}
