package com.randr.webdw.international.dateFormat;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 * @author randr
 * @version $Revision: 1.2 $
 */
public class DateFormatBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "date_format";
        this.tableAlias = "df";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param dateFormatDetails DateFormatDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, DateFormatDetails dateFormatDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(dateFormatDetails);

        // establish the list of properties to be populated
        addColumnList("date_format_id, date_format");
        setOrderBy("date_format_id");

        return doSelect(dateFormatDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param dateFormatDetails DateFormatDetails
     */
    private void establishSearchConditions(DateFormatDetails dateFormatDetails) {
        if (dateFormatDetails.getDateFormatId() != null) {
            addCondition("date_format_id = ?");
            addConditionParam(dateFormatDetails.getDateFormatId());
        }

        if (dateFormatDetails.getDateFormat() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("date_format = ?");
                addConditionParam(dateFormatDetails.getDateFormat());
            } else {
                addCondition("upper(date_format) = upper('" + Utilities.replaceString(dateFormatDetails.getDateFormat(), "'", "''") + "')");
            }
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param dateFormatDetails DateFormatDetails
     * @return DateFormatDetails
     * @throws Exception
     */
    public DateFormatDetails insert(BigDecimal fillType, DateFormatDetails dateFormatDetails) throws Exception {
        dateFormatDetails.setDateFormatId(getAvailableID("date_format_id", "date_format"));
        addColumnList("date_format_id, date_format");

        return (DateFormatDetails) doInsert(dateFormatDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param dateFormatDetails DateFormatDetails
     * @return DateFormatDetails
     * @throws Exception
     */
    public DateFormatDetails update(BigDecimal fillType, DateFormatDetails dateFormatDetails) throws Exception {
        addCondition("date_format_id= ?");
        addConditionParam(dateFormatDetails.getDateFormatId());

        addColumnList("date_format");

        return (DateFormatDetails) doUpdate(dateFormatDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param dateFormatDetails DateFormatDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, DateFormatDetails dateFormatDetails) throws Exception {
    	
    	if(dateFormatDetails.getDateFormatId() != null){
            addCondition("date_format_id= ?");
            addConditionParam(dateFormatDetails.getDateFormatId());

            doDelete(dateFormatDetails);
            return true;
    	}else{
    		return false;
    	}
    }
}
