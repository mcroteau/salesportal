package com.randr.webdw.territoryZip;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.calendarSchedule.CalendarScheduleDetails;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.util.Utilities;


/**
 */
public class TerritoryZipBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "territory_zips";
        this.tableAlias = "tz";
    }

    public Vector loadElements(BigDecimal fillType, TerritoryZipDetails territoryZipDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(territoryZipDetails);

        // establish the list of properties to be populated
        addColumnList("tz.territory_id");
        addColumnList("tz.territory_zip_id");
        addColumnList("tz.zip");

        
        return doSelect(territoryZipDetails);
    }


    private void establishSearchConditions(TerritoryZipDetails territoryZipDetails) {
    	
    	if(territoryZipDetails.getTerritoryId()!=null){
    		addCondition("tz.territory_id=?");
    		addConditionParam(territoryZipDetails.getTerritoryId());
    	}
    	if(territoryZipDetails.getZip()!=null){
    		addCondition("tz.zip=?");
    		addConditionParam(territoryZipDetails.getZip());
    	}
    }
    
    public TerritoryZipDetails insert(BigDecimal fillType, TerritoryZipDetails territoryZipDetails) throws Exception {
        territoryZipDetails.setTerritoryZipId(getAvailableID("territory_zip_id", "territory_zips"));
        addColumnList("territory_zip_id, zip, territory_id");

        return (TerritoryZipDetails) doInsert(territoryZipDetails);
    }
    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param territoryZipDetails TerritoryZipDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, TerritoryZipDetails territoryZipDetails) throws Exception {
        
    	if(territoryZipDetails.getTerritoryZipId() != null){
        	addCondition("territory_zip_id= ?");
            addConditionParam(territoryZipDetails.getTerritoryZipId());

            doDelete(territoryZipDetails);
            return true;   		
    	}else{
    		return false;
    	}

    }
}
