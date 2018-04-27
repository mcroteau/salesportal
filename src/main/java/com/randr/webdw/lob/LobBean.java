package com.randr.webdw.lob;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class LobBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "lob";
        this.tableAlias = "l";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, LobDetails lobDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(lobDetails);

        // establish the list of properties to be populated
        addColumnList("lob_id, lob");
        setOrderBy("lob");

        return doSelect(lobDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param lobDetails LobDetails
     */
    private void establishSearchConditions(LobDetails lobDetails) {
        if (lobDetails.getLobId() != null) {
            addCondition("lob_id= ?");
            addConditionParam(lobDetails.getLobId());
        }

        if (lobDetails.getLob() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("lob = ?");
                addConditionParam(lobDetails.getLob());
            } else {
                addCondition("upper(lob) = upper('" + Utilities.replaceString(lobDetails.getLob(), "'", "''") + "')");
            }
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return LobDetails
     * @throws Exception
     */
    public LobDetails insert(BigDecimal fillType, LobDetails lobDetails) throws Exception {
        lobDetails.setLobId(getAvailableID("lob_id", "lob"));
        addColumnList("lob_id, lob");

        return (LobDetails) doInsert(lobDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return LobDetails
     * @throws Exception
     */
    public LobDetails update(BigDecimal fillType, LobDetails lobDetails) throws Exception {
        addCondition("lob_id= ?");
        addConditionParam(lobDetails.getLobId());

        addColumnList("lob");

        return (LobDetails) doUpdate(lobDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, LobDetails lobDetails) throws Exception {
    	
    	if(lobDetails.getLobId() != null){
        	addCondition("lob_id= ?");
            addConditionParam(lobDetails.getLobId());

            doDelete(lobDetails);
            return true;    		
    	}else{
    		return false;
    	}

    }
}
