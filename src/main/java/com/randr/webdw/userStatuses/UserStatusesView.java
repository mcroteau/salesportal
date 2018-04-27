package com.randr.webdw.userStatuses;

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
public class UserStatusesView extends AbstractView {
	
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Constructor for ContactView.
     */
    public UserStatusesView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.userStatuses.UserStatusesBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.userStatuses.UserStatusesDetails";
    }
    
    public Vector getUserStatusesIdsVector(){
    	Vector idsVector = new Vector();
    	UserStatusesDetails userStatusesDetails;
    	
    	for(int i = 0; i < this.theCollection.size(); i++){
    		userStatusesDetails = (UserStatusesDetails)this.theCollection.get(i);
    		idsVector.add(userStatusesDetails.getStatusId());
    	}
    	
    	return idsVector;
    }
   
}
