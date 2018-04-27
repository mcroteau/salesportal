package com.randr.webdw.customQuery;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CustomQueryBean extends AbstractDBBean {

    /**
     * Constructor for CustomQueryBean.
     */
    public CustomQueryBean() {
    }

    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "custom_query";
        this.tableAlias = "q";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param customQueryDetails CustomQueryDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, CustomQueryDetails customQueryDetails) throws Exception {
        Vector returnDetails = null;
        Vector vConditions = new Vector();
        String sSelectFields = "q.custom_query_no,q.custom_query_name, q.custom_query_file_name, q.custom_query_description";
        String sDetailFields = "customQueryNo, customQueryName, customQueryFileName, customQueryDescription";
        String sWhere = "";
        String sOrderBy = " order by q.custom_query_no ";

        if (customQueryDetails.getCustomQueryNo() != null) {
            sWhere = " where q.custom_query_no= ? ";
            vConditions.add(customQueryDetails.getCustomQueryNo());
        }

        if (customQueryDetails.getCustomQueryName() != null) {
            sWhere = " where q.custom_query_name= ? ";
            vConditions.add(customQueryDetails.getCustomQueryName());
        }
        if (customQueryDetails.getCustomQueryFileName() != null) {
            sWhere = " where q.custom_query_file_name= ? ";
            vConditions.add(customQueryDetails.getCustomQueryFileName());
        }
        if (customQueryDetails.getCustomQueryDescription() != null) {
            sWhere = " where q.custom_query_file_description= ? ";
            vConditions.add(customQueryDetails.getCustomQueryDescription());
        }

        returnDetails = execQuery("SELECT " + sSelectFields + " FROM " + AppSettings.getParm("LIB") + "custom_query q " + sWhere + sOrderBy, new CustomQueryDetails(), vConditions, sDetailFields, false);
        return returnDetails;
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param customQueryDetails CustomQueryDetails
     * @return CustomQueryDetails
     * @throws Exception
     */
    public CustomQueryDetails insert(BigDecimal fillType, CustomQueryDetails customQueryDetails) throws Exception {
        customQueryDetails.setCustomQueryNo(getAvailableID("custom_query_no", "custom_query"));
        BigDecimal startNumber = new BigDecimal(1);
        if (customQueryDetails.getCustomQueryNo().compareTo(startNumber) < 0) {
            customQueryDetails.setCustomQueryNo(startNumber);
        }
        CustomQueryDetails returnDetails = null;
        String sInsertFields = "custom_query_no, custom_query_name, custom_query_file_name, custom_query_description";
        String sInsertVals = "?,?,?,?";
        String sDetailFields = "customQueryNo, customQueryName, customQueryFileName, customQueryDescription";

        returnDetails = (CustomQueryDetails) execUpdate("INSERT INTO " + AppSettings.getParm("LIB") + "custom_query  (" + sInsertFields + ") VALUES(" + sInsertVals + ")", customQueryDetails, sDetailFields, true);

        return returnDetails;
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param customQueryDetails CustomQueryDetails
     * @return CustomQueryDetails
     * @throws Exception
     */
    public CustomQueryDetails update(BigDecimal fillType, CustomQueryDetails customQueryDetails) throws Exception {
        CustomQueryDetails returnDetails = null;
        String sUpdateFields = null;
        String sDetailFields = null;
        String sWhere = "";

        // for the moment the only fill type is ALL...
        sUpdateFields = " custom_query_description=? , custom_query_name=?";
        sDetailFields = "customQueryDescription, customQueryName";

        // set conditions
        sWhere = "WHERE custom_query_no = ? ";
        sDetailFields = sDetailFields + ",customQueryNo";

        // do the update
        returnDetails = (CustomQueryDetails) execUpdate("UPDATE " + AppSettings.getParm("LIB") + "custom_query SET " + sUpdateFields + sWhere, customQueryDetails, sDetailFields, true);

        return returnDetails;
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param customQueryDetails CustomQueryDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, CustomQueryDetails customQueryDetails) throws Exception {
        execUpdate("DELETE FROM " + AppSettings.getParm("LIB") + "custom_query " + " WHERE custom_query_no=?", customQueryDetails, "customQueryNo", true);
        return true;
    }

}