package com.randr.webdw.campaign;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 */
public class CampaignView extends AbstractView {
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.campaign.CampaignBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.campaign.CampaignDetails";
    }
}
