package com.randr.webdw.customQueryAuthorization;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CustomQueryAuthorizationBean extends AbstractDBBean {

    /**
     * Constructor for CustomQueryAuthorizationBean.
     */
    public CustomQueryAuthorizationBean() {
    }

    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "custom_query_users";
        this.tableAlias = "a";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param customQueryAuthorizationDetails CustomQueryAuthorizationDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, CustomQueryAuthorizationDetails customQueryAuthorizationDetails) throws Exception {
        Vector returnDetails = null;
        Vector vConditions = new Vector();
        String sSelectFields = "a.custom_query_no, a.user_id, q.custom_query_name, q.custom_query_file_name, q.custom_query_description";
        String sDetailFields = "customQueryNo, authorizedUserId, customQueryName, customQueryFileName, customQueryDescription";
        String sWhere = "";
        String sOrderBy = " order by a.custom_query_no ";


        if (customQueryAuthorizationDetails.getCustomQueryNo() != null) {
            sWhere = " where a.custom_query_no= ? ";
            vConditions.add(customQueryAuthorizationDetails.getCustomQueryNo());
        }

        if (customQueryAuthorizationDetails.getAuthorizedUserId() != null) {
            sWhere = " where a.user_id= ? ";
            vConditions.add(customQueryAuthorizationDetails.getAuthorizedUserId());
        }

        returnDetails = execQuery("SELECT " + sSelectFields + " FROM "
                                  + AppSettings.getParm("LIB") + "custom_query_users a LEFT JOIN "
                                  + AppSettings.getParm("LIB") + "custom_query q ON a.custom_query_no=q.custom_query_no "
                                  + sWhere + sOrderBy,
                                  new CustomQueryAuthorizationDetails(), vConditions, sDetailFields, true);
        return returnDetails;
    }


    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param customQueryDetails CustomQueryAuthorizationDetails
     * @return CustomQueryAuthorizationDetails
     * @throws Exception
     */
    public CustomQueryAuthorizationDetails insert(BigDecimal fillType, CustomQueryAuthorizationDetails customQueryDetails)
            throws Exception {
        CustomQueryAuthorizationDetails returnDetails = null;
        String sInsertFields = "custom_query_no, user_id";
        String sInsertVals = "?,?";
        String sDetailFields = "customQueryNo, authorizedUserId";

        returnDetails = (CustomQueryAuthorizationDetails) execUpdate("INSERT INTO "
                                                                     + AppSettings.getParm("LIB") + "custom_query_users  (" + sInsertFields
                                                                     + ") VALUES(" + sInsertVals + ")", customQueryDetails, sDetailFields, true);

        return returnDetails;
    }


    /**
     * Method update.
     * @param fillType BigDecimal
     * @param customQueryDetails CustomQueryAuthorizationDetails
     * @return CustomQueryAuthorizationDetails
     * @throws Exception
     */
    public CustomQueryAuthorizationDetails update(BigDecimal fillType, CustomQueryAuthorizationDetails customQueryDetails) throws Exception {
        CustomQueryAuthorizationDetails returnDetails = null;
        String sUpdateFields = null;
        String sDetailFields = null;
        String sWhere = "";

        // for the moment the only fill type is ALL...
        sUpdateFields = " custom_query_no, user_id";
        sDetailFields = "customQueryNo, authorizedUserId";

        // set conditions
        sWhere = "WHERE custom_query_no = ? and authorizedUserId = ? ";
        sDetailFields = sDetailFields + ",customQueryNo, authorizedUserId";

        // do the update
        returnDetails = (CustomQueryAuthorizationDetails) execUpdate("UPDATE " + AppSettings.getParm("LIB") + "custom_query_users SET " + sUpdateFields + sWhere, customQueryDetails, sDetailFields, true);

        return returnDetails;
    }


    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param customQueryDetails CustomQueryAuthorizationDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, CustomQueryAuthorizationDetails customQueryDetails) throws Exception {
        Vector vConditions = new Vector();
        String sDetailFields = "";
        String sWhere = "";
        if (customQueryDetails.getCustomQueryNo() != null) {
            if (!sWhere.equals("")) {
                sWhere += " and custom_query_no= ? ";
                sDetailFields += ", customQueryNo";
            } else {
                sWhere = " where custom_query_no= ? ";
                sDetailFields = "customQueryNo";
            }
        }

        if (customQueryDetails.getAuthorizedUserId() != null) {
            if (!sWhere.equals("")) {
                sWhere += " and user_id= ? ";
                sDetailFields += ", authorizedUserId";
            } else {
                sWhere = " where user_id= ? ";
                sDetailFields = "authorizedUserId";
            }
        }
        execUpdate("DELETE FROM " + AppSettings.getParm("LIB")
                   + "custom_query_users "
                   + sWhere, customQueryDetails, sDetailFields, true);
        return true;
    }

}