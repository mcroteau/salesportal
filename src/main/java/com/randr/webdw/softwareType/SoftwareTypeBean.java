package com.randr.webdw.softwareType;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class SoftwareTypeBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "software_type";
        this.tableAlias = "sf";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param softwareTypeDetails SoftwareTypeDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, SoftwareTypeDetails softwareTypeDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(softwareTypeDetails);

        // establish the list of properties to be populated
        addColumnList("software_type_id, software_type");
        setOrderBy("software_type");

        return doSelect(softwareTypeDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param softwareTypeDetails SoftwareTypeDetails
     */
    private void establishSearchConditions(SoftwareTypeDetails softwareTypeDetails) {
        if (softwareTypeDetails.getSoftwareTypeId() != null) {
            addCondition("software_type_id= ?");
            addConditionParam(softwareTypeDetails.getSoftwareTypeId());
        }

        if (softwareTypeDetails.getSoftwareType() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("software_type = ?");
                addConditionParam(softwareTypeDetails.getSoftwareType());
            } else {
                addCondition("upper(software_type) = upper('" + Utilities.replaceString(softwareTypeDetails.getSoftwareType(), "'", "''") + "')");
            }
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param softwareTypeDetails SoftwareTypeDetails
     * @return SoftwareTypeDetails
     * @throws Exception
     */
    public SoftwareTypeDetails insert(BigDecimal fillType, SoftwareTypeDetails softwareTypeDetails) throws Exception {
        softwareTypeDetails.setSoftwareTypeId(getAvailableID("software_type_id", "software_type"));
        addColumnList("software_type_id, software_type");

        return (SoftwareTypeDetails) doInsert(softwareTypeDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param softwareTypeDetails SoftwareTypeDetails
     * @return SoftwareTypeDetails
     * @throws Exception
     */
    public SoftwareTypeDetails update(BigDecimal fillType, SoftwareTypeDetails softwareTypeDetails) throws Exception {
        addCondition("software_type_id= ?");
        addConditionParam(softwareTypeDetails.getSoftwareTypeId());

        addColumnList("software_type");

        return (SoftwareTypeDetails) doUpdate(softwareTypeDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param softwareTypeDetails SoftwareTypeDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, SoftwareTypeDetails softwareTypeDetails) throws Exception {
    	
    	if(softwareTypeDetails.getSoftwareTypeId() != null){
        	addCondition("software_type_id= ?");
            addConditionParam(softwareTypeDetails.getSoftwareTypeId());

            doDelete(softwareTypeDetails);
            return true;    		
    	}else{
    		return false;
    	}

    }
}
