package com.randr.webdw.userTerritories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractView;
import com.randr.webdw.userStatuses.UserStatusesDetails;

/**
 * @author randr
 * @version $Revision: 1.7 $
 */
public class UserTerritoriesView extends AbstractView {
	
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Constructor for ContactView.
     */
    public UserTerritoriesView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.userTerritories.UserTerritoriesBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.userTerritories.UserTerritoriesDetails";
    }
   
    public Vector getUserTerritoriesIdsVector(){
    	Vector idsVector = new Vector();
    	UserTerritoriesDetails userTerritoriesDetails;
    	
    	for(int i = 0; i < this.theCollection.size(); i++){
    		userTerritoriesDetails = (UserTerritoriesDetails)this.theCollection.get(i);
    		idsVector.add(userTerritoriesDetails.getTerritoryId());
    	}
    	return idsVector;
    }
}
