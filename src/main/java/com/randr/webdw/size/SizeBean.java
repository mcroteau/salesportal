package com.randr.webdw.size;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class SizeBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "size";
        this.tableAlias = "s";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param sizeDetails SizeDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, SizeDetails sizeDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(sizeDetails);

        // establish the list of properties to be populated
        addColumnList("size_id, size");
        setOrderBy("size");

        return doSelect(sizeDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param sizeDetails SizeDetails
     */
    private void establishSearchConditions(SizeDetails sizeDetails) {
        if (sizeDetails.getSizeId() != null) {
            addCondition("size_id= ?");
            addConditionParam(sizeDetails.getSizeId());
        }

        if (sizeDetails.getSize() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("size = ?");
                addConditionParam(sizeDetails.getSize());
            } else {
                addCondition("upper(size) = upper('" + Utilities.replaceString(sizeDetails.getSize(), "'", "''") + "')");
            }
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param sizeDetails SizeDetails
     * @return SizeDetails
     * @throws Exception
     */
    public SizeDetails insert(BigDecimal fillType, SizeDetails sizeDetails) throws Exception {
        sizeDetails.setSizeId(getAvailableID("size_id", "size"));
        addColumnList("size_id, size");

        return (SizeDetails) doInsert(sizeDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param sizeDetails SizeDetails
     * @return SizeDetails
     * @throws Exception
     */
    public SizeDetails update(BigDecimal fillType, SizeDetails sizeDetails) throws Exception {
        addCondition("size_id= ?");
        addConditionParam(sizeDetails.getSizeId());

        addColumnList("size");

        return (SizeDetails) doUpdate(sizeDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param sizeDetails SizeDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, SizeDetails sizeDetails) throws Exception {
    	
    	if(sizeDetails.getSizeId() != null){
            addCondition("size_id= ?");
            addConditionParam(sizeDetails.getSizeId());

            doDelete(sizeDetails);
            return true;   		
    	}else{
    		return false;
    	}

    }
}
