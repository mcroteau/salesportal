package com.randr.webdw.audit.auditRoundRobin;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;


/**
 */
public class AuditRoundRobinBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "audit_round_robin";
        this.tableAlias = "arr";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param auditRoundRobinDetails AuditRoundRobinDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, AuditRoundRobinDetails auditRoundRobinDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(auditRoundRobinDetails);
        // establish the list of properties to be populated
        addColumnList("arr.audit_rr_id, arr.prosect_id, arr.territory_id, arr.creation_date, arr.user_id_making_change, external_id");
        
        setOrderBy("arr.creation_cate");

        return doSelect(auditRoundRobinDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param auditRoundRobinDetails AuditRoundRobinDetails
     */
    private void establishSearchConditions(AuditRoundRobinDetails auditRoundRobinDetails) {
        if (auditRoundRobinDetails.getAuditRRId() != null) {
            addCondition("arr.audit_rr_id= ?");
            addConditionParam(auditRoundRobinDetails.getAuditRRId());
        }

       
        
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param auditRoundRobinDetails AuditRoundRobinDetails
     * @return AuditRoundRobinDetails
     * @throws Exception
     */
    public AuditRoundRobinDetails insert(BigDecimal fillType, AuditRoundRobinDetails auditRoundRobinDetails) throws Exception {
        auditRoundRobinDetails.setAuditRRId(getAvailableID("audit_rr_id", "audit_round_robin"));
        addColumnList("audit_rr_id, prosect_id, territory_id, creation_date, user_id_making_change, external_id");

        return (AuditRoundRobinDetails) doInsert(auditRoundRobinDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param auditRoundRobinDetails AuditRoundRobinDetails
     * @return AuditRoundRobinDetails
     * @throws Exception
     */
    public AuditRoundRobinDetails update(BigDecimal fillType, AuditRoundRobinDetails auditRoundRobinDetails) throws Exception {
        addCondition("audit_rr_id= ?");
        addConditionParam(auditRoundRobinDetails.getAuditRRId());

        addColumnList("prosect_id, territory_id, creation_date, user_id_making_change, external_id");

       
        return (AuditRoundRobinDetails) doUpdate(auditRoundRobinDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param auditRoundRobinDetails AuditRoundRobinDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, AuditRoundRobinDetails auditRoundRobinDetails) throws Exception {
    	
    	if(auditRoundRobinDetails.getAuditRRId() != null){
            addCondition("audit_rr_id= ?");
            addConditionParam(auditRoundRobinDetails.getAuditRRId());

            doDelete(auditRoundRobinDetails);
            return true;
    	}else{
    		return false;
    	}

    }
}
