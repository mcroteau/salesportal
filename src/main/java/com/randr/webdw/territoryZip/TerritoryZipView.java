package com.randr.webdw.territoryZip;

import com.randr.webdw.mvcAbstract.AbstractView;

public class TerritoryZipView extends AbstractView{
	
	/**
	 * No-Arg Constructor
	 *
	 */
	public TerritoryZipView(){
	}

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.territoryZip.TerritoryZipBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.territoryZip.TerritoryZipDetails";
    }

}
