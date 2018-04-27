package com.randr.webdw.emailSalesAction.emailSalesActionLobs;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractView;

/**
 * @author randr
 * @version $Revision: 1.7 $
 */
public class EmailSalesActionLobsView extends AbstractView {
	
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Constructor for ContactView.
     */
    public EmailSalesActionLobsView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.emailSalesAction.emailSalesActionLobs.EmailSalesActionLobsBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.emailSalesAction.emailSalesActionLobs.EmailSalesActionLobsDetails";
    }
    
    public Vector getEmailLobsIdsVector(){
    	Vector idsVector = new Vector();
    	EmailSalesActionLobsDetails emailSalesActionLobsDetails;
    	
    	for(int i = 0; i < this.theCollection.size(); i++){
    		emailSalesActionLobsDetails = (EmailSalesActionLobsDetails)this.theCollection.get(i);
    		idsVector.add(emailSalesActionLobsDetails.getLobId());
    	}
    	
    	return idsVector;
    }
   
}
