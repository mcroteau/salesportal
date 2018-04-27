package com.randr.webdw.prospectSalesAction;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.calendarSchedule.CalendarScheduleDetails;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.util.DateUtilities;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class ProspectSalesActionBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    public void setTableNameAndAlias() {
        this.table = "prospect_sales_action";
        this.tableAlias = "psa";
    }

    public Vector loadElements(BigDecimal fillType, ProspectSalesActionDetails prospectSalesActionDetails) 
    	throws Exception {
        establishSearchConditions(prospectSalesActionDetails, fillType);

        // set the detail fields
        addColumnList("psa.prospect_sales_action_id");
        addColumnList("psa.prospect_id");
        addColumnList("psa.sales_action_id");       
        addColumnList("psa.action_priority");
        addColumnList("psa.action_date");
        addColumnList("psa.action_notification_flag");
        addColumnList("psa.action_note");
        addColumnList("psa.action_status");
        addColumnList("psa.prospect_campaign_id");
        addColumnList("psa.prospect_campaign_sequence");
        addColumnList("psa.change_date");
        addColumnList("psa.creation_date");
        addColumnList("psa.changed_by_user_id");
        addColumnList("psa.email_draft_to_use");
        
        addColumnList("sa.action");
        addColumnList("sa.mandatory_date");
        addColumnList("cam.campaign");
        addOtherTables(" left outer join " +
                AppSettings.getParm("LIB") + "action sa on sa.action_id = psa.sales_action_id");
        addOtherTables(" left outer join " +
                AppSettings.getParm("LIB") + "prospect_campaign pc on psa.prospect_campaign_id=pc.prospect_campaign_id");
        addOtherTables(" left outer join " +
                AppSettings.getParm("LIB") + "campaign cam on cam.campaign_id=pc.campaign_id");
        if(fillType.equals(ProspectSalesActionView.FILL_TYPE_NEXT_SALES_ACTION_NOTIFY)) {
            addColumnList("p.territory_id");
            addColumnList("p.country_id");
            addOtherTables(" inner join prospect p on p.prospect_id = psa.prospect_id");
            addColumnList("t.owner_user_id");
            addOtherTables(" inner join territory t on t.territory_id = p.territory_id");
            setOrderBy("t.owner_user_id , action_date ");
        }else if(fillType.equals(ProspectSalesActionView.FILL_TYPE_CAMPAIGN_OR_COMPLETED)) {
        	addColumnList("w.first_name, w.last_name");
            addOtherTables(" left outer join webuser w on  psa.changed_by_user_id = w.user_id");
            setOrderBy("psa.prospect_campaign_sequence");
        } else {
        	setOrderBy("action_priority, action_date");
        }
        

        
        return doSelect(prospectSalesActionDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param prospectSalesActionDetails ProspectSalesActionDetails
     */
    private void establishSearchConditions(ProspectSalesActionDetails prospectSalesActionDetails, BigDecimal fillType) {
        if (prospectSalesActionDetails.getProspectSalesActionId() != null) {
            addCondition("psa.prospect_sales_action_id = ?");
            addConditionParam(prospectSalesActionDetails.getProspectSalesActionId());
        }

        if (prospectSalesActionDetails.getProspectId() != null) {
            addCondition("psa.prospect_id = ?");
            addConditionParam(prospectSalesActionDetails.getProspectId());
        }
        
        // this is the get the list of sales actions that need emails sent
        if (prospectSalesActionDetails.getActionNotificationflag() != null) {
            addCondition("psa.action_notification_flag = ?");
            addConditionParam(prospectSalesActionDetails.getActionNotificationflag());
        }
        
        if (prospectSalesActionDetails.getProspectCampaignId() != null) {
            addCondition("psa.prospect_campaign_id = ?");
            addConditionParam(prospectSalesActionDetails.getProspectCampaignId());
        }
        
        if (prospectSalesActionDetails.getActionStatus() != null) {
            addCondition("psa.action_status = ?");
            addConditionParam(prospectSalesActionDetails.getActionStatus());
        }else if(!fillType.equals(ProspectSalesActionView.FILL_TYPE_CAMPAIGN_OR_COMPLETED)){
        	addCondition("psa.action_status = 0");
        }
        
        if(prospectSalesActionDetails.isExcludeCampaignSalesActions()) {
            addCondition("psa.prospect_campaign_id is null");
        }
        
        if(prospectSalesActionDetails.getActionDate()!= null) {
            addCondition("date(psa.action_date) = date('?')");
            addConditionParam(prospectSalesActionDetails.getActionDate());
        }
        
        if(prospectSalesActionDetails.getSalesActionId()!= null) {
            addCondition("psa.sales_action_id = ? ");
            addConditionParam(prospectSalesActionDetails.getSalesActionId());
        }
        
        if(prospectSalesActionDetails.getEmailDraftToUse()!= null) {
            addCondition("psa.email_draft_to_use = ? ");
            addConditionParam(prospectSalesActionDetails.getEmailDraftToUse());
        }
        
        if (prospectSalesActionDetails.getCheckActionDate() != null) {
    		addCondition("date(action_date) <= date(?)");
    		addConditionParam(prospectSalesActionDetails.getCheckActionDate());
    	}
        
        if (prospectSalesActionDetails.isEmailProspectSalesAction()) {
            addCondition("psa.email_draft_to_use is not null and psa.email_draft_to_use>0 ");
        }
        
        if (prospectSalesActionDetails.getProspectIdList() != null && prospectSalesActionDetails.getProspectIdList().size() > 0) {
            if (prospectSalesActionDetails.getProspectIdList().size() == 1) {
                addCondition("psa.prospect_id = ?");
                addConditionParam((BigDecimal) prospectSalesActionDetails.getProspectIdList().get(0));
            } else {
                String condition = "psa.prospect_id IN (";
                for (int i = 0; i < prospectSalesActionDetails.getProspectIdList().size(); i++) {
                    if (i > 0) {
                        condition += ",";
                    }
                    condition += ((BigDecimal) prospectSalesActionDetails.getProspectIdList().get(i)).toString();
                }
                condition += ")";
                addCondition(condition);
            }
        }  
      
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param prospectSalesActionDetails ProspectSalesActionDetails
     * @return ProspectSalesActionDetails
     * @throws Exception
     */
    public ProspectSalesActionDetails insert(BigDecimal fillType, ProspectSalesActionDetails prospectSalesActionDetails) throws Exception {
        prospectSalesActionDetails.setProspectSalesActionId(getAvailableID("prospect_sales_action_id", "prospect_sales_action"));

        addColumnList("prospect_sales_action_id");
        addColumnList("prospect_id");
        addColumnList("sales_action_id");       
        addColumnList("action_priority");
        addColumnList("action_date");
        addColumnList("action_notification_flag");
        addColumnList("action_note");
        addColumnList("action_status");
        addColumnList("prospect_campaign_id");
        addColumnList("prospect_campaign_sequence");
        addColumnList("creation_date");
        addColumnList("change_date");
        addColumnList("changed_by_user_id");
        addColumnList("email_draft_to_use");
        
        return (ProspectSalesActionDetails) doInsert(prospectSalesActionDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param prospectSalesActionDetails ProspectSalesActionDetails
     * @return ProspectSalesActionDetails
     * @throws Exception
     */
    public ProspectSalesActionDetails update(BigDecimal fillType, ProspectSalesActionDetails prospectSalesActionDetails) throws Exception {
        if (prospectSalesActionDetails.getProspectSalesActionId() != null) {
            addCondition("prospect_sales_action_id = ?");
            addConditionParam(prospectSalesActionDetails.getProspectSalesActionId());

            addColumnList("prospect_id");
            addColumnList("sales_action_id");       
            addColumnList("action_priority");
            addColumnList("action_date");
            addColumnList("action_notification_flag");
            addColumnList("action_note");
            addColumnList("action_status");
            addColumnList("change_date");
            addColumnList("changed_by_user_id");
            addColumnList("email_draft_to_use");//updating from sales action
          
            if(prospectSalesActionDetails.getProspectCampaignId() != null) {
            	addColumnList("prospect_campaign_id");
            }
            
            if(prospectSalesActionDetails.getProspectCampaignSequence() != null) {
            	addColumnList("prospect_campaign_sequence");
            }
            

            return (ProspectSalesActionDetails) doUpdate(prospectSalesActionDetails);
        } else {
            return new ProspectSalesActionDetails();
        }
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param prospectSalesActionDetails ProspectSalesActionDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, ProspectSalesActionDetails prospectSalesActionDetails) throws Exception {
        if (prospectSalesActionDetails.getProspectSalesActionId() != null) {
            addCondition("prospect_sales_action_id= ?");
            addConditionParam(prospectSalesActionDetails.getProspectSalesActionId());
        }  
    	
        if (prospectSalesActionDetails.getProspectId() != null) {
            addCondition("prospect_id=?");
            addConditionParam(prospectSalesActionDetails.getProspectId());
        }
    	
        if(prospectSalesActionDetails.getProspectId() != null || prospectSalesActionDetails.getProspectSalesActionId() != null){
            doDelete(prospectSalesActionDetails);
            return true;
        }else{
        	return false;
        }

    }
}
