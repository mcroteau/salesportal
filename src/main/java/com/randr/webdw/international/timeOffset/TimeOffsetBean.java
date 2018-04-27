package com.randr.webdw.international.timeOffset;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 * @author randr
 * @version $Revision: 1.2 $
 */
public class TimeOffsetBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "time_offset";
        this.tableAlias = "tof";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param timeOffsetDetails TimeOffsetDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, TimeOffsetDetails timeOffsetDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(timeOffsetDetails);

        // establish the list of properties to be populated
        addColumnList("time_offset_id, time_offset");
        setOrderBy("time_offset_id");

        return doSelect(timeOffsetDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param timeOffsetDetails TimeOffsetDetails
     */
    private void establishSearchConditions(TimeOffsetDetails timeOffsetDetails) {
        if (timeOffsetDetails.getTimeOffsetId() != null) {
            addCondition("time_offset_id = ?");
            addConditionParam(timeOffsetDetails.getTimeOffsetId());
        }

        if (timeOffsetDetails.getTimeOffset() != null) {
            addCondition("time_offset = ?");
            addConditionParam(timeOffsetDetails.getTimeOffset());
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param timeOffsetDetails TimeOffsetDetails
     * @return TimeOffsetDetails
     * @throws Exception
     */
    public TimeOffsetDetails insert(BigDecimal fillType, TimeOffsetDetails timeOffsetDetails) throws Exception {
        timeOffsetDetails.setTimeOffsetId(getAvailableID("time_offset_id", "time_offset"));
        addColumnList("time_offset_id, time_offset");

        return (TimeOffsetDetails) doInsert(timeOffsetDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param timeOffsetDetails TimeOffsetDetails
     * @return TimeOffsetDetails
     * @throws Exception
     */
    public TimeOffsetDetails update(BigDecimal fillType, TimeOffsetDetails timeOffsetDetails) throws Exception {
        addCondition("time_offset_id= ?");
        addConditionParam(timeOffsetDetails.getTimeOffsetId());

        addColumnList("time_offset");

        return (TimeOffsetDetails) doUpdate(timeOffsetDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param timeOffsetDetails TimeOffsetDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, TimeOffsetDetails timeOffsetDetails) throws Exception {
    	
    	if(timeOffsetDetails.getTimeOffsetId() != null){
        	addCondition("time_offset_id= ?");
            addConditionParam(timeOffsetDetails.getTimeOffsetId());

            doDelete(timeOffsetDetails);
            return true;
    	}else{
    		return false;
    	}

    }
}
