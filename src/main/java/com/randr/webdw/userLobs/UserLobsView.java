package com.randr.webdw.userLobs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractView;

/**
 * @author randr
 * @version $Revision: 1.7 $
 */
public class UserLobsView extends AbstractView {
	
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Constructor for ContactView.
     */
    public UserLobsView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.userLobs.UserLobsBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.userLobs.UserLobsDetails";
    }
    
    public Vector getUserLobsIdsVector(){
    	Vector idsVector = new Vector();
    	UserLobsDetails userLobsDetails;
    	
    	for(int i = 0; i < this.theCollection.size(); i++){
    		userLobsDetails = (UserLobsDetails)this.theCollection.get(i);
    		idsVector.add(userLobsDetails.getLobId());
    	}
    	
    	return idsVector;
    }
   
}
