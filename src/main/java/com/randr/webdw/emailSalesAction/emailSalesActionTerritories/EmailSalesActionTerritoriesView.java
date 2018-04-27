package com.randr.webdw.emailSalesAction.emailSalesActionTerritories;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractView;

/**
 * @author randr
 * @version $Revision: 1.7 $
 */
public class EmailSalesActionTerritoriesView extends AbstractView {
	
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Constructor for ContactView.
     */
    public EmailSalesActionTerritoriesView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.emailSalesAction.emailSalesActionTerritories.EmailSalesActionTerritoriesBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.emailSalesAction.emailSalesActionTerritories.EmailSalesActionTerritoriesDetails";
    }
    
    public Vector getEmailTerritoriesIdsVector(){
    	Vector idsVector = new Vector();
    	EmailSalesActionTerritoriesDetails emailSalesActionTerritoriesDetails;
    	
    	for(int i = 0; i < this.theCollection.size(); i++){
    		emailSalesActionTerritoriesDetails = (EmailSalesActionTerritoriesDetails)this.theCollection.get(i);
    		idsVector.add(emailSalesActionTerritoriesDetails.getTerritoryId());
    	}
    	
    	return idsVector;
    }
   
}
