package com.randr.webdw.prospectSalesAction;

import java.math.BigDecimal;
import java.util.HashMap;

import com.randr.webdw.mvcAbstract.AbstractView;

/**
 * @author randr
 * @version $Revision: 1.7 $
 */
public class ProspectSalesActionView extends AbstractView {
	
	public static final BigDecimal FILL_TYPE_NEXT_SALES_ACTION_NOTIFY = new BigDecimal(4);
	public static final BigDecimal FILL_TYPE_NEXT_SALES_ACTION_SENT = new BigDecimal(5);
	public static final BigDecimal FILL_TYPE_CAMPAIGN_OR_COMPLETED = new BigDecimal(6);
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(7);
	
    /**
     * Constructor for ContactView.
     */
    public ProspectSalesActionView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.prospectSalesAction.ProspectSalesActionBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails";
    }
    
    public static String getActionPriorityDropDown(ProspectSalesActionDetails details) {
    	
    	StringBuffer sb = new StringBuffer();
    	for(int i = 0; i < 10 ; i++) {
    		sb.append("<option value=\"" + (i+1) + "\"");
    		
    		if(details != null && details.getActionPriority() != null  &&
    				details.getActionPriority().compareTo(new BigDecimal(i+1)) == 0) {
    			sb.append(" selected>");
    		} else {
    			sb.append(">");
    		}
    		sb.append((i + 1) + "</option>\n");
    	}	
    	return sb.toString();
    }
   
}
