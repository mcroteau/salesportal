package com.randr.webdw.migrationLog;

import com.randr.webdw.mvcAbstract.AbstractView;



/**
 * @author Randr
 * 
 */
public class MigrationLogView extends AbstractView {
	
	public MigrationLogView(){
		
	}
		
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.migrationLog.MigrationLogBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.migrationLog.MigrationLogDetails";
    }
}
