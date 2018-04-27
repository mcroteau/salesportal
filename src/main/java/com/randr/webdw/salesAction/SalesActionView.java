package com.randr.webdw.salesAction;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractView;
import com.randr.webdw.util.Utilities;


/**
 */
public class SalesActionView extends AbstractView {
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.salesAction.SalesActionBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.salesAction.SalesActionDetails";
    }

    /**
     * Method getDateMandatoryArray.
     * @return String
     */
    public String getDateMandatoryArray() {
        StringBuffer array = new StringBuffer();
        array.append("var arrMandatoryDateFlags=new Array(");
        for (int i = 0; i < this.theCollection.size(); i++) {
            SalesActionDetails salesActionDetails = (SalesActionDetails) this.theCollection.elementAt(i);
            if (i > 0) {
                array.append(",");
            }
            array.append(salesActionDetails.getMandatoryDate().toString());
        }
        array.append(");");
        return array.toString();
    }
    
    public String getEmailDraftArray() {
        StringBuffer array = new StringBuffer();
        array.append("var arrEmailDraft=new Array(");
        for (int i = 0; i < this.theCollection.size(); i++) {
            SalesActionDetails salesActionDetails = (SalesActionDetails) this.theCollection.elementAt(i);
            if (i > 0) {
                array.append(",");
            }
            array.append(Utilities.nullToZero(salesActionDetails.getEmailDraftToUse()).toString());
        }
        array.append(");");
        //System.out.println("emailArray= " + array.toString() );
        return array.toString();
    }

    public String getSalesActionDescription(BigDecimal salesActionId){
    	String description = "";
    	if(salesActionId!= null){
	        for (int i = 0; i < this.theCollection.size(); i++) {
	        	SalesActionDetails salesActionDetails = (SalesActionDetails)this.theCollection.get(i);
	        	
	        	if (salesActionDetails.getActionId().compareTo(salesActionId)==0) {
	        		description = salesActionDetails.getAction();
	            }
	        }
    	}
    	return description;
    }
}
