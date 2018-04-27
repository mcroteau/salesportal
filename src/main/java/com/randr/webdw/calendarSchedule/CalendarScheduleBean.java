package com.randr.webdw.calendarSchedule;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;

public class CalendarScheduleBean extends AbstractDBBean{

	protected void setTableNameAndAlias() {
        this.table = "prospect_sales_action";
        this.tableAlias = "psa";
	}
	
	 public Vector loadElements(BigDecimal fillType, CalendarScheduleDetails calendarScheduleDetails) throws Exception {
		 	establishSearchConditions(calendarScheduleDetails);
            
		 	addColumnList("psa.prospect_id");
	        addColumnList("psa.prospect_sales_action_id");
	        addColumnList("psa.sales_action_id");       
	        addColumnList("psa.action_priority");
	        addColumnList("psa.action_date");
	        addColumnList("psa.action_notification_flag");
	        addColumnList("psa.action_note");
	        addColumnList("psa.action_status");
	        addColumnList("psa.prospect_campaign_id");
	        addColumnList("psa.prospect_campaign_sequence");
          
            addColumnList("p.customer_company");
            addColumnList("p.contact_name, p.contact_title");
            addColumnList("p.address, p.address2, p.city, p.county, p.country_id, p.state_id, p.zip");
            addColumnList("p.phone, p.phone_ext, p.email");
            addColumnList("p.territory_id, p.status_id");        
            
		 	//join prospect table
            addOtherTables(" left outer join " +
                    AppSettings.getParm("LIB") + "prospect p on p.prospect_id = psa.prospect_id");
            
            //join state table
            addColumnList("sta.state");
            addOtherTables(" left outer join " +
                    AppSettings.getParm("LIB") + "state sta on sta.state_id = p.state_id");
            
            //join action table
            addColumnList("ac.action");
            addColumnList("ac.color");
            addOtherTables(" left outer join " +
                    AppSettings.getParm("LIB") + "action ac on ac.action_id = psa.sales_action_id");
            
            //join country table
            addColumnList("cn.country");
            addOtherTables(" left outer join " +
                    AppSettings.getParm("LIB") + "country cn on cn.country_id = p.country_id");
            
            //join territory table
            addColumnList("t.territory");
            addOtherTables(" left outer join " +
                    AppSettings.getParm("LIB") + "territory t on t.territory_id = p.territory_id");
            
            //join status table
            addColumnList("s.status");
            addOtherTables(" left outer join " +
                    AppSettings.getParm("LIB") + "status s on s.status_id = p.status_id");
            
            //join campaign and prospect_campaign tables
            addColumnList("cam.campaign");
            addOtherTables(" left outer join " +
                    AppSettings.getParm("LIB") + "prospect_campaign pc on psa.prospect_campaign_id=pc.prospect_campaign_id");
            addOtherTables(" left outer join " +
                    AppSettings.getParm("LIB") + "campaign cam on cam.campaign_id=pc.campaign_id");

            if(fillType.equals(CalendarScheduleDetails.FILL_TYPE_DAY)){
            	addCondition("date(action_date)=date(?)");
            	addConditionParam(calendarScheduleDetails.getNextSalesActionDate());
            	
            }else if (fillType.equals(CalendarScheduleDetails.FILL_TYPE_WEEK)){
            	addCondition("date(action_date)>=date(?) and date(action_date)<=date(?)");
            	addConditionParam(calendarScheduleDetails.getStartDate());
            	addConditionParam(calendarScheduleDetails.getEndDate());	
            	
            }else if (fillType.equals(CalendarScheduleDetails.FILL_TYPE_MONTH)){
            	
            }
            setOrderBy("psa.action_date");
            return doSelect(calendarScheduleDetails);

	 }
	 
	private void establishSearchConditions(CalendarScheduleDetails calendarScheduleDetails) {
        
        if (calendarScheduleDetails.getStatusIdList() != null && calendarScheduleDetails.getStatusIdList().size() > 0) {
            if (calendarScheduleDetails.getStatusIdList().size() == 1) {
                addCondition("p.status_id = ?");
                addConditionParam((BigDecimal) calendarScheduleDetails.getStatusIdList().get(0));
            } else {
                String condition = "p.status_id IN (";
                for (int i = 0; i < calendarScheduleDetails.getStatusIdList().size(); i++) {
                    if (i > 0) {
                        condition += ",";
                    }
                    condition += ((BigDecimal) calendarScheduleDetails.getStatusIdList().get(i)).toString();
                }
                condition += ")";
                addCondition(condition);
            }
        } 
        
        if (calendarScheduleDetails.getLobIdList() != null && calendarScheduleDetails.getLobIdList().size() > 0) {
        	if (calendarScheduleDetails.getLobIdList().size() == 1) {
                addCondition("p.lob_id = ?");
                addConditionParam((BigDecimal) calendarScheduleDetails.getLobIdList().get(0));
            } else {
                String condition = "p.lob_id IN (";
                for (int i = 0; i < calendarScheduleDetails.getLobIdList().size(); i++) {
                    if (i > 0) {
                        condition += ",";
                    }
                    condition += ((BigDecimal) calendarScheduleDetails.getLobIdList().get(i)).toString();
                }
                condition += ")";
                addCondition(condition);
            }
        } 	
        
		if(calendarScheduleDetails.getTerritoryId() != null) {
			addCondition("p.territory_id=?");
			addConditionParam(calendarScheduleDetails.getTerritoryId());
		}

				
		addCondition("psa.action_status=0");
					
	}
	
}
