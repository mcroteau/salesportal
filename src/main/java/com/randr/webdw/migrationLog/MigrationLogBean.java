package com.randr.webdw.migrationLog;

import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;

/**
 * @author Randr
 * 
 */
public class MigrationLogBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "migration_log_sp";
        this.tableAlias = "ml";
    }

    /**
     * Method loadElements.
     * @param fillType String
     * @param migrationLogDetails MigrationLogDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(String fillType, MigrationLogDetails migrationLogDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(migrationLogDetails);

        // establish the list of properties to be populated
        addColumnList("migration_log_id , migration_id ,application, migration_type, migration_sub_type, " +
        		"migration_result, migration_tag, migration_timestamp");
        setOrderBy("migration_timestamp desc, application, migration_type");

        return doSelect(migrationLogDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param migrationLogDetails MigrationLogDetails
     */
    private void establishSearchConditions(MigrationLogDetails migrationLogDetails) {
        if (migrationLogDetails.getMigrationTimestamp() != null) {
            addCondition("migration_timestamp= ?");
            addConditionParam(migrationLogDetails.getMigrationTimestamp());
        }

        if (migrationLogDetails.getApplication() != null) {
            addCondition("application= ?");
            addConditionParam(migrationLogDetails.getApplication());
        }
        
        if (migrationLogDetails.getMigrationType() != null) {
            addCondition("migration_type= ?");
            addConditionParam(migrationLogDetails.getMigrationType());
        }

        
    }

    /**
     * Method insert.
     * @param fillType String
     * @param migrationLogDetails MigrationLogDetails
     * @return MigrationLogDetails
     * @throws Exception
     */
    public MigrationLogDetails insert(String fillType, MigrationLogDetails migrationLogDetails) throws Exception {
        migrationLogDetails.setMigrationLogId(getAvailableID("migration_log_id", "migration_log"));
        addColumnList("migration_log_id , migration_id ,application, migration_type, migration_sub_type, migration_result, " +
        		"migration_tag, migration_timestamp");

        return (MigrationLogDetails) doInsert(migrationLogDetails);
    }

    /**
     * Method update.
     * @param fillType String
     * @param migrationLogDetails MigrationLogDetails
     * @return MigrationLogDetails
     * @throws Exception
     */
    public MigrationLogDetails update(String fillType, MigrationLogDetails migrationLogDetails) throws Exception {
        addCondition("migration_log_id= ?");
        addConditionParam(migrationLogDetails.getMigrationLogId());

       

        return (MigrationLogDetails) doUpdate(migrationLogDetails);
    }

    /**
     * Method delete.
     * @param fillType String
     * @param migrationLogDetails MigrationLogDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(String fillType, MigrationLogDetails migrationLogDetails) throws Exception {
        addCondition("migration_log_id= ?");
        addConditionParam(migrationLogDetails.getMigrationLogId());

        doDelete(migrationLogDetails);
        return true;
    }
}
