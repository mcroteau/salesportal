package com.randr.webdw.salesAction;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class SalesActionBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "action";
        this.tableAlias = "ac";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param salesActionDetails SalesActionDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, SalesActionDetails salesActionDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(salesActionDetails);

        
        // establish the list of properties to be populated
        addColumnList("action_id, action, mandatory_date, color, email_draft_to_use, display_for_salesmen");
        setOrderBy("action");

        return doSelect(salesActionDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param salesActionDetails SalesActionDetails
     */
    private void establishSearchConditions(SalesActionDetails salesActionDetails) {
        if (salesActionDetails.getActionId() != null) {
            addCondition("action_id= ?");
            addConditionParam(salesActionDetails.getActionId());
        }

        if (salesActionDetails.getEmailDraftToUse() != null) {
            addCondition("email_draft_to_use= ?");
            addConditionParam(salesActionDetails.getEmailDraftToUse());
        }
        
        if (salesActionDetails.getDisplayForSalesmen() != null) {
            addCondition("display_for_salesmen= ?");
            addConditionParam(salesActionDetails.getDisplayForSalesmen());
        }
        
        if (salesActionDetails.getAction() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("action = ?");
                addConditionParam(salesActionDetails.getAction());
            } else {
                addCondition("upper(action) = upper('"
                             + Utilities.replaceString(salesActionDetails.getAction(), "'", "''")
                             + "')");
            }
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param salesActionDetails SalesActionDetails
     * @return SalesActionDetails
     * @throws Exception
     */
    public SalesActionDetails insert(BigDecimal fillType,
                                     SalesActionDetails salesActionDetails) throws Exception {
        salesActionDetails.setActionId(getAvailableID("action_id", "action"));
        addColumnList("action_id, action, mandatory_date, color, email_draft_to_use, display_for_salesmen");

        return (SalesActionDetails) doInsert(salesActionDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param actionDetails SalesActionDetails
     * @return SalesActionDetails
     * @throws Exception
     */
    public SalesActionDetails update(BigDecimal fillType,
                                     SalesActionDetails actionDetails) throws Exception {
        addCondition("action_id= ?");
        addConditionParam(actionDetails.getActionId());

        addColumnList("action, mandatory_date, color, email_draft_to_use, display_for_salesmen");

        return (SalesActionDetails) doUpdate(actionDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param salesActionDetails SalesActionDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, SalesActionDetails salesActionDetails) throws Exception {
    	
    	if(salesActionDetails.getActionId() != null){
            addCondition("action_id= ?");
            addConditionParam(salesActionDetails.getActionId());

            doDelete(salesActionDetails);
            return true; 		
    	}else{
    		return false;
    	}

    }
}
