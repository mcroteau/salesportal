package com.randr.webdw.territoryChange;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 */
public class TerritoryChangeView extends AbstractView {
	
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
    
	/**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.territoryChange.TerritoryChangeBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.territoryChange.TerritoryChangeDetails";
    }
}
