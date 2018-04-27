package com.randr.webdw.verified;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class VerifiedBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "verified";
        this.tableAlias = "c";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param verifiedDetails VerifiedDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, VerifiedDetails verifiedDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(verifiedDetails);

        // establish the list of properties to be populated
        addColumnList("verified_id, verified");
        setOrderBy("verified");

        return doSelect(verifiedDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param verifiedDetails VerifiedDetails
     */
    private void establishSearchConditions(VerifiedDetails verifiedDetails) {
        if (verifiedDetails.getVerifiedId() != null) {
            addCondition("verified_id= ?");
            addConditionParam(verifiedDetails.getVerifiedId());
        }

        if (verifiedDetails.getVerified() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("verified = ?");
                addConditionParam(verifiedDetails.getVerified());
            } else {
                addCondition("upper(verified) = upper('" + Utilities.replaceString(verifiedDetails.getVerified(), "'", "''") + "')");
            }
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param verifiedDetails VerifiedDetails
     * @return VerifiedDetails
     * @throws Exception
     */
    public VerifiedDetails insert(BigDecimal fillType, VerifiedDetails verifiedDetails) throws Exception {
        verifiedDetails.setVerifiedId(getAvailableID("verified_id", "verified"));
        addColumnList("verified_id, verified");

        return (VerifiedDetails) doInsert(verifiedDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param verifiedDetails VerifiedDetails
     * @return VerifiedDetails
     * @throws Exception
     */
    public VerifiedDetails update(BigDecimal fillType, VerifiedDetails verifiedDetails) throws Exception {
        addCondition("verified_id= ?");
        addConditionParam(verifiedDetails.getVerifiedId());

        addColumnList("verified");

        return (VerifiedDetails) doUpdate(verifiedDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param verifiedDetails VerifiedDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, VerifiedDetails verifiedDetails) throws Exception {
        
    	if(verifiedDetails.getVerifiedId() != null){
        	addCondition("verified_id= ?");
            addConditionParam(verifiedDetails.getVerifiedId());

            doDelete(verifiedDetails);
            return true;
    	}else{
    		return false;
    	}

    }
}
