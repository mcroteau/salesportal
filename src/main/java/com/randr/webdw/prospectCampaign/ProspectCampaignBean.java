package com.randr.webdw.prospectCampaign;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.calendarSchedule.CalendarScheduleDetails;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.prospect.ProspectView;

/**
 * @author randr
 * @version $Revision: 1.3 $
 */
public class ProspectCampaignBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    public void setTableNameAndAlias() {
        this.table = "prospect_campaign";
        this.tableAlias = "pc";
    }

    public Vector loadElements(BigDecimal fillType, ProspectCampaignDetails prospectCampaignDetails) 
    	throws Exception {
        establishSearchConditions(prospectCampaignDetails, fillType);

        // set the detail fields
        addColumnList("pc.prospect_campaign_id");
        addColumnList("pc.campaign_id"); 
        addColumnList("pc.prospect_id");
        addColumnList("pc.creation_date"); 
        
        addOtherTables(" left outer join " +
                AppSettings.getParm("LIB") + "campaign cam on cam.campaign_id = pc.campaign_id");
        
        addColumnList("cam.campaign"); 
        addColumnList("cam.description"); 
        
      
        
        return doSelect(prospectCampaignDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param prospectCampaignDetails ProspectCampaignDetails
     */
    private void establishSearchConditions(ProspectCampaignDetails prospectCampaignDetails, BigDecimal fillType) {
        if (prospectCampaignDetails.getProspectCampaignId() != null) {
            addCondition("prospect_campaign_id = ?");
            addConditionParam(prospectCampaignDetails.getProspectCampaignId());
        }
        
        if (prospectCampaignDetails.getProspectId() != null) {
            addCondition("prospect_id = ?");
            addConditionParam(prospectCampaignDetails.getProspectId());
        }
        
        if (prospectCampaignDetails.getCampaignId() != null) {
            addCondition("pc.campaign_id = ?");
            addConditionParam(prospectCampaignDetails.getCampaignId());
        }
      
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param prospectCampaignDetails ProspectCampaignDetails
     * @return ProspectCampaignDetails
     * @throws Exception
     */
    public ProspectCampaignDetails insert(BigDecimal fillType, ProspectCampaignDetails prospectCampaignDetails) throws Exception {
        prospectCampaignDetails.setProspectCampaignId(getAvailableID("prospect_campaign_id", "prospect_campaign"));

        // set the detail fields
        addColumnList("prospect_campaign_id");
        addColumnList("campaign_id");
        addColumnList("prospect_id");
        addColumnList("creation_date");          


        return (ProspectCampaignDetails) doInsert(prospectCampaignDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param prospectCampaignDetails ProspectCampaignDetails
     * @return ProspectCampaignDetails
     * @throws Exception
     */
    public ProspectCampaignDetails update(BigDecimal fillType, ProspectCampaignDetails prospectCampaignDetails) throws Exception {
        if (prospectCampaignDetails.getProspectCampaignId() != null) {
            addCondition("prospect_campaign_id = ?");
            addConditionParam(prospectCampaignDetails.getProspectCampaignId());

            // set the detail fields
            addColumnList("campaign_id"); 
            addColumnList("prospect_id"); 
            addColumnList("creation_date"); 

            return (ProspectCampaignDetails) doUpdate(prospectCampaignDetails);
        } else {
            return new ProspectCampaignDetails();
        }
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param prospectCampaignDetails ProspectCampaignDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, ProspectCampaignDetails prospectCampaignDetails) throws Exception {
        if (prospectCampaignDetails.getProspectCampaignId() != null) {
            addCondition("prospect_campaign_id= ?");
            addConditionParam(prospectCampaignDetails.getProspectCampaignId());
        }
        
        if (prospectCampaignDetails.getProspectId() != null) {
            addCondition("prospect_id=?");
            addConditionParam(prospectCampaignDetails.getProspectId());
        }

        if(prospectCampaignDetails.getProspectId() != null || prospectCampaignDetails.getProspectCampaignId() != null){
            doDelete(prospectCampaignDetails);
            return true;	
        }else{
        	return false;
        }

    }
}
