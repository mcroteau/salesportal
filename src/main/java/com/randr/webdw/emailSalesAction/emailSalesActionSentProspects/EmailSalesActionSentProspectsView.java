package com.randr.webdw.emailSalesAction.emailSalesActionSentProspects;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractView;

/**
 * @author randr
 * @version $Revision: 1.7 $
 */
public class EmailSalesActionSentProspectsView extends AbstractView {
	
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Constructor for ContactView.
     */
    public EmailSalesActionSentProspectsView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.emailSalesAction.emailSalesActionSentProspects.EmailSalesActionSentProspectsBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.emailSalesAction.emailSalesActionSentProspects.EmailSalesActionSentProspectsDetails";
    }
    
   
   
}
