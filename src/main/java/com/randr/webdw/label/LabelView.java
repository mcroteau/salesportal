package com.randr.webdw.label;

import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 * @author randr
 * @version $Revision$
 */
public class LabelView extends AbstractView {
	
	public static final String USER_1 = "USER_1";
	public static final String USER_2 = "USER_2";
	public static final String USER_3 = "USER_3";
	public static final String USER_4 = "USER_4";
	
	public static final String USER_DROPDOWN_1 = "USER_DROPDOWN_1";
	public static final String USER_DROPDOWN_2 = "USER_DROPDOWN_2";
	public static final String LOB = "LOB";
	public static final String VERIFIED = "Verified";
	public static final String TEST = "TEST";
	
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.label.LabelBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.label.LabelDetails";
    }
    
    public String getLabel(String genericLabel) {
    	String label = genericLabel;
    	try {
    		Vector vector = this.getElements();
    		LabelDetails details;
    		for(int i =0; i < vector.size(); i++) {
    			details = (LabelDetails)vector.get(i);
    			if(details.getLabelDefault().equals(genericLabel)) {
    				return details.getLabelActual();
    			}   			
    		}   		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}   		
    	return label;
    }
    
    public String getLabelShort(String genericLabel) {
    	String label = genericLabel;
    	try {
    		Vector vector = this.getElements();
    		LabelDetails details;
    		for(int i =0; i < vector.size(); i++) {
    			details = (LabelDetails)vector.get(i);
    			if(details.getLabelDefault().equals(genericLabel)) {
    				return details.getLabelShort();
    			}   
    			
    		}   		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}   		
    	return label;
    }


}
