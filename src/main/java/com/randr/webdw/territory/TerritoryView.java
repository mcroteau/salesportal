package com.randr.webdw.territory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractView;
import com.randr.webdw.userStatuses.UserStatusesDetails;


/**
 */
public class TerritoryView extends AbstractView {
	
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
    
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.territory.TerritoryBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.territory.TerritoryDetails";
    }
    
    public List getTerritoryIdsList(){
    	List idsVector = new ArrayList();
    	TerritoryDetails territoryDetails;
    	
    	for(int i = 0; i < this.theCollection.size(); i++){
    		territoryDetails = (TerritoryDetails)this.theCollection.get(i);
    		idsVector.add(territoryDetails.getTerritoryId());
    	}	
    	return idsVector;
    }
    
    public String getTerritoryName(BigDecimal territoryId){
    	String territoryName = "";
    	TerritoryDetails territoryDetails;
    	if(territoryId != null){
	    	for(int i = 0; i < this.theCollection.size(); i++){
	    		territoryDetails = (TerritoryDetails)this.theCollection.get(i);
	    		if(territoryDetails.getTerritoryId().compareTo(territoryId) == 0) {
	    			territoryName = territoryDetails.getTerritory();
	    		}
	    	}	
    	}
    	return territoryName;
    }
}
