package com.randr.webdw.prospectImportLog;

import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;

/**
 * @author Randr
 * 
 */
public class ProspectImportLogBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "prospect_import_log";
        this.tableAlias = "pill";
    }

    /**
     * Method loadElements.
     * @param fillType String
     * @param prospectImportLogDetails ProspectImportLogDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(String fillType, ProspectImportLogDetails prospectImportLogDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(prospectImportLogDetails);

        // establish the list of properties to be populated
        addColumnList("prospect_import_log_id , action_taken ,email, phone, external_id, prospect_import_timestamp");
        setOrderBy("prospect_import_timestamp desc");

        return doSelect(prospectImportLogDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param prospectImportLogDetails ProspectImportLogDetails
     */
    private void establishSearchConditions(ProspectImportLogDetails prospectImportLogDetails) {
        if (prospectImportLogDetails.getProspectImportTimestamp() != null) {
            addCondition("prospect_import_timestamp= ?");
            addConditionParam(prospectImportLogDetails.getProspectImportTimestamp());
        }

        
        
    }

    /**
     * Method insert.
     * @param fillType String
     * @param prospectImportLogDetails ProspectImportLogDetails
     * @return ProspectImportLogDetails
     * @throws Exception
     */
    public ProspectImportLogDetails insert(String fillType, ProspectImportLogDetails prospectImportLogDetails) throws Exception {
        prospectImportLogDetails.setProspectImportLogId(getAvailableID("prospect_import_log_id", "prospect_import_log"));
        addColumnList("prospect_import_log_id , action_taken ,email, phone, external_id, prospect_import_timestamp");

        return (ProspectImportLogDetails) doInsert(prospectImportLogDetails);
    }

    /**
     * Method update.
     * @param fillType String
     * @param prospectImportLogDetails ProspectImportLogDetails
     * @return ProspectImportLogDetails
     * @throws Exception
     */
    public ProspectImportLogDetails update(String fillType, ProspectImportLogDetails prospectImportLogDetails) throws Exception {
        addCondition("prospect_import_log_id= ?");
        addConditionParam(prospectImportLogDetails.getProspectImportLogId());

       

        return (ProspectImportLogDetails) doUpdate(prospectImportLogDetails);
    }

    /**
     * Method delete.
     * @param fillType String
     * @param prospectImportLogDetails ProspectImportLogDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(String fillType, ProspectImportLogDetails prospectImportLogDetails) throws Exception {
        addCondition("prospect_import_log_id= ?");
        addConditionParam(prospectImportLogDetails.getProspectImportLogId());

        doDelete(prospectImportLogDetails);
        return true;
    }
}
