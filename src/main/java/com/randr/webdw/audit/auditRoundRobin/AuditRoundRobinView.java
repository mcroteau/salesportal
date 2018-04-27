package com.randr.webdw.audit.auditRoundRobin;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 */
public class AuditRoundRobinView extends AbstractView {
	public static final BigDecimal FILL_TYPE_BASIC = new BigDecimal(1);
	
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.audit.auditRoundRobin.AuditRoundRobinBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.audit.auditRoundRobin.AuditRoundRobinDetails";
    }
}
