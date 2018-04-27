package com.randr.webdw.commision;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.DateUtilities;


/**
 * @author randr
 * @version $Revision: 1.3 $
 */
public class CommisionBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "commision";
        this.tableAlias = "com";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param commisionDetails CommisionDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, CommisionDetails commisionDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(commisionDetails);

        // establish the list of properties to be populated
        addColumnList("commision_id, description, amount, date_sold, date_billed, date_paid");
        addColumnList("check_number, check_amount, com.creation_date, internal_description");
        addColumnList("commission_prospect_id, commission_currency_id, po_number");
        addColumnList("com.user_id, oep_transactionid");
        addColumnList("expected_close_date, original_expected_close_date, cancel_date, revenue, commission_type_id");
        addColumnList("c.currency_code, date1, date2, date3, date4, date5,yes_no1, drop_down1_name, numeric1, total_units, total_units_closed");
        addOtherTables(" left outer join " +
                       AppSettings.getParm("LIB") + "currency c on commission_currency_id = c.currency_id");
        addColumnList("p.customer_company");
        addOtherTables(" left outer join " +
                       AppSettings.getParm("LIB") + "prospect p on commission_prospect_id = p.prospect_id");
        if (fillType.equals(CommisionDetails.FILL_TYPE_JOIN)) {
            addColumnList("u.first_name, u.last_name");
            addOtherTables(" inner join " +
                           AppSettings.getParm("LIB") + "webuser u on u.user_id = com.user_id");
         }

        setOrderBy("commission_currency_id, date_sold");

        return doSelect(commisionDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param commisionDetails CommisionDetails
     */
    private void establishSearchConditions(CommisionDetails commisionDetails) {
        if (commisionDetails.getCommisionId() != null) {
            addCondition("commision_id= ?");
            addConditionParam(commisionDetails.getCommisionId());
        }
        if (commisionDetails.getUserId() != null) {
            addCondition("com.user_id= ?");
            addConditionParam(commisionDetails.getUserId());
        }
        
        if (commisionDetails.getProspectId() != null) {
            addCondition("com.commission_prospect_id= ?");
            addConditionParam(commisionDetails.getProspectId());
        }
        if (commisionDetails.getStatus() != null) {
            if (commisionDetails.getStatus().equals(CommisionDetails.STATUS_OPEN)) {
                addCondition("(com.check_amount < com.amount or com.check_amount is null)");
            }
            if (commisionDetails.getStatus().equals(CommisionDetails.STATUS_PAID)) {
                addCondition("com.check_amount >= com.amount");
            }
        }

        if (commisionDetails.getDateSold1() != null) {
            addCondition("com.date_sold >= ?");
            addConditionParam(commisionDetails.getDateSold1());
        }
        if (commisionDetails.getDateSold2() != null) {
            addCondition("com.date_sold <= ?");
            addConditionParam(commisionDetails.getDateSold2());
        }

        if (commisionDetails.getDatePaid1() != null) {
            addCondition("com.date_paid >= ?");
            addConditionParam(commisionDetails.getDatePaid1());
        }
        if (commisionDetails.getDatePaid2() != null) {
            addCondition("com.date_paid <= ?");
            addConditionParam(commisionDetails.getDatePaid2());
        }
        if (commisionDetails.getOepTransactionid() != null) {
            addCondition("oep_transactionid = ?");
            addConditionParam(commisionDetails.getOepTransactionid());
        }
        
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param commisionDetails CommisionDetails
     * @return CommisionDetails
     * @throws Exception
     */
    public CommisionDetails insert(BigDecimal fillType, CommisionDetails commisionDetails) throws Exception {
        commisionDetails.setCommisionId(getAvailableID("commision_id", "commision"));
        commisionDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
        addColumnList("commision_id, user_id, description, amount, date_sold, date_billed, date_paid, check_number, check_amount, creation_date, internal_description");
        addColumnList("commission_prospect_id, commission_currency_id, po_number, date1, date2, date3, date4, date5,yes_no1, drop_down1_name, " +
        		"numeric1, oep_transactionid, total_units, total_units_closed");
        addColumnList("expected_close_date, original_expected_close_date, cancel_date, revenue, commission_type_id");
        return (CommisionDetails) doInsert(commisionDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param commisionDetails CommisionDetails
     * @return CommisionDetails
     * @throws Exception
     */
    public CommisionDetails update(BigDecimal fillType, CommisionDetails commisionDetails) throws Exception {
        addCondition("commision_id= ?");
        addConditionParam(commisionDetails.getCommisionId());

        addColumnList("commision_id, user_id, description, amount, date_sold, date_billed, date_paid, check_number, check_amount, internal_description");
        addColumnList("commission_prospect_id, commission_currency_id, po_number, date1, date2, date3, date4, date5,yes_no1, drop_down1_name, " +
        		"numeric1, oep_transactionid, total_units, total_units_closed");
        addColumnList("expected_close_date, original_expected_close_date, cancel_date, revenue, commission_type_id");
        return (CommisionDetails) doUpdate(commisionDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param commisionDetails CommisionDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, CommisionDetails commisionDetails) throws Exception {
        addCondition("commision_id= ?");
        addConditionParam(commisionDetails.getCommisionId());

        if(commisionDetails.getCommisionId() != null){
        	addCondition("commision_id= ?");
            addConditionParam(commisionDetails.getCommisionId());

            doDelete(commisionDetails);
            return true;    		
    	}else{
    		return false;
    	}

    }
}
