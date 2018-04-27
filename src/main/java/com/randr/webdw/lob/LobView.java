package com.randr.webdw.lob;

import java.util.ArrayList;
import java.util.List;

import com.randr.webdw.mvcAbstract.AbstractView;
import com.randr.webdw.status.StatusDetails;


/**
 */
public class LobView extends AbstractView {
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.lob.LobBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.lob.LobDetails";
    }
    
    public List getLobIdsList(){
    	List idsVector = new ArrayList();
    	LobDetails lobDetails;
    	
    	for(int i = 0; i < this.theCollection.size(); i++){
    		lobDetails = (LobDetails)this.theCollection.get(i);
    		idsVector.add(lobDetails.getLobId());
    	}	
    	return idsVector;
    }
}
