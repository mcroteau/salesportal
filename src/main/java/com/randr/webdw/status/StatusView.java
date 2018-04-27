package com.randr.webdw.status;

import java.util.ArrayList;
import java.util.List;

import com.randr.webdw.mvcAbstract.AbstractView;
import com.randr.webdw.territory.TerritoryDetails;


/**
 */
public class StatusView extends AbstractView {
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.status.StatusBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.status.StatusDetails";
    }
    
    public List getStatusIdsList(){
    	List idsVector = new ArrayList();
    	StatusDetails statusDetails;
    	
    	for(int i = 0; i < this.theCollection.size(); i++){
    		statusDetails = (StatusDetails)this.theCollection.get(i);
    		idsVector.add(statusDetails.getStatusId());
    	}	
    	return idsVector;
    }
}
