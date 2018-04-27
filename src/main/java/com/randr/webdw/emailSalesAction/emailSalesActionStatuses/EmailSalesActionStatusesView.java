package com.randr.webdw.emailSalesAction.emailSalesActionStatuses;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractView;

/**
 * @author randr
 * @version $Revision: 1.7 $
 */
public class EmailSalesActionStatusesView extends AbstractView {
	
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Constructor for ContactView.
     */
    public EmailSalesActionStatusesView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.emailSalesAction.emailSalesActionStatuses.EmailSalesActionStatusesBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.emailSalesAction.emailSalesActionStatuses.EmailSalesActionStatusesDetails";
    }
    
    public Vector getEmailStatusesIdsVector(){
    	Vector idsVector = new Vector();
    	EmailSalesActionStatusesDetails emailSalesActionStatusesDetails;
    	
    	for(int i = 0; i < this.theCollection.size(); i++){
    		emailSalesActionStatusesDetails = (EmailSalesActionStatusesDetails)this.theCollection.get(i);
    		idsVector.add(emailSalesActionStatusesDetails.getStatusId());
    	}
    	
    	return idsVector;
    }
   
}
