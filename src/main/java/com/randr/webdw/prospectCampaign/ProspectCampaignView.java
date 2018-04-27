package com.randr.webdw.prospectCampaign;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractView;

/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class ProspectCampaignView extends AbstractView {
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
	
    /**
     * Constructor for ContactView.
     */
    public ProspectCampaignView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.prospectCampaign.ProspectCampaignBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.prospectCampaign.ProspectCampaignDetails";
    }
   
}
