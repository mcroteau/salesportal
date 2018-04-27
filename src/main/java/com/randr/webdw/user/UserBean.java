package com.randr.webdw.user;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class UserBean extends AbstractDBBean {

    /**
     * Constructor for UserBean.
     */
    public UserBean() {
    }

    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "webuser";
        this.tableAlias = "u";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param userDetails UserDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, UserDetails userDetails) throws Exception {
        establishSearchConditions(userDetails);
        // set the detail fields
        addColumnList(tableAlias + ".user_id");

        if (fillType.equals(UserView.FILL_TYPE_LOGIN)
            || fillType.equals(UserView.FILL_TYPE_SALESMAN)
            || fillType.equals(UserView.FILL_TYPE_SALESMAN_REPORT)
            || fillType.equals(UserView.FILL_TYPE_QUERY_AUTHORIZED_USERS)) {

//            login:
            addColumnList("user_type, active, user_name, password");
            addColumnList("first_name, last_name, email");
            addColumnList("u.company_id, u.system_type_id, u.territory_id, u.lob_id, u.software_type_id, u.status_id, u.verified_id, u.size_id, u.limit_company");
            addColumnList("u.country_id, u.state_id, u.allow_cal_of_all_territories, u.date_format_id, u.time_offset_id, u.specific_territories, u.specific_statuses, u.limit_view, u.specific_lobs");
            
            //if(userDetails.getDateFormatId()  != null){
        	addColumnList("df.date_format");
        	addOtherTables(" left outer join " +
       				  		AppSettings.getParm("LIB") + "date_format df on df.date_format_id = u.date_format_id");
        //}
        
       	//if(userDetails.getTimeOffsetId() != null){
       		addColumnList("tof.time_offset");
       		addOtherTables(" left outer join " +
       				  		AppSettings.getParm("LIB") + "time_offset tof on tof.time_offset_id = u.time_offset_id");
       	//}
//            custom queries:
            if (fillType.equals(UserView.FILL_TYPE_QUERY_AUTHORIZED_USERS)) {
                addColumnList("qu.custom_query_no");
                addOtherTables(" left join " +
                               AppSettings.getParm("LIB")
                               + "custom_query_users qu on u.user_id=qu.user_id ");
                if (userDetails.getCustomQueryNo() != null) {
                    addOtherTables(" AND qu.custom_query_no = " + userDetails.getCustomQueryNo() + " ");
                }

            } else if (fillType.equals(UserView.FILL_TYPE_SALESMAN)
                       || fillType.equals(UserView.FILL_TYPE_SALESMAN_REPORT)) {
//              salesmen:
                addColumnList("creation_date");
                addColumnList("change_date");

                if (fillType.equals(UserView.FILL_TYPE_SALESMAN_REPORT)) {
                    addColumnList("c.company");
                    addOtherTables(" inner join " +
                                   AppSettings.getParm("LIB") + "company c on c.company_id = u.company_id");

                    addColumnList("sy.system_type");
                    addOtherTables(" left outer join " +
                                   AppSettings.getParm("LIB") + "system_type sy on sy.system_type_id = u.system_type_id");

                    addColumnList("sf.software_type");
                    addOtherTables(" left outer join " +
                                   AppSettings.getParm("LIB") + "software_type sf on sf.software_type_id = u.software_type_id");

                    addColumnList("t.territory");
                    addOtherTables(" left outer join " +
                                   AppSettings.getParm("LIB") + "territory t on t.territory_id = u.territory_id");

                    addColumnList("l.lob");
                    addOtherTables(" left outer join " +
                                   AppSettings.getParm("LIB") + "lob l on l.lob_id = u.lob_id");

                    addColumnList("st.status");
                    addOtherTables(" left outer join " +
                                   AppSettings.getParm("LIB") + "status st on st.status_id = u.status_id");

                    addColumnList("si.size");
                    addOtherTables(" left outer join " +
                                   AppSettings.getParm("LIB") + "size si on si.size_id = u.size_id");

                    addColumnList("v.verified");
                    addOtherTables(" left outer join " +
                                   AppSettings.getParm("LIB") + "verified v on v.verified_id = u.verified_id");

                    addColumnList("cn.country");
                    addOtherTables(" left outer join " +
                                   AppSettings.getParm("LIB") + "country cn on cn.country_id = u.country_id");

                    addColumnList("sta.state");
                    addOtherTables(" left outer join " +
                                   AppSettings.getParm("LIB") + "state sta on sta.state_id = u.state_id");

                }
            }

            if (fillType.equals(UserView.FILL_TYPE_QUERY_AUTHORIZED_USERS)) {
                addOrderBy("u.user_type, u.user_name");
            } else if (getOrderBy().equals("")) {
                addOrderBy("u.user_id");
            } else {
                addColumnList(Utilities.replaceString(Utilities.replaceString(getOrderBy(), "asc", ""), "desc", ""));
            }
        }
        return doSelect(userDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param userDetails UserDetails
     */
    private void establishSearchConditions(UserDetails userDetails) {
    	
        if (userDetails.getUserId() != null) {
            addCondition("u.user_id = ?");
            addConditionParam(userDetails.getUserId());
        }

        if (userDetails.getUserName() != null) {
            addCondition("upper(user_name) = upper('" + Utilities.replaceString(userDetails.getUserName(), "'", "''") + "')");
        }

        if (userDetails.getPassword() != null) {
            addCondition("password = ?");
            addConditionParam(userDetails.getPassword());
        }

        if (userDetails.getActive() != null) {
            addCondition("active = ?");
            addConditionParam(userDetails.getActive());
        }
        if (userDetails.getCompanyId() != null) {
            addCondition("u.company_id = ?");
            addConditionParam(userDetails.getCompanyId());
        }

        if (userDetails.getFirstName() != null) {
            addCondition("first_name = ?");
            addConditionParam(userDetails.getFirstName());
        }

        if (userDetails.getLastName() != null) {
            addCondition("last_name = ?");
            addConditionParam(userDetails.getLastName());
        }

        if (userDetails.getUserType() != null) {
            addCondition("user_type= ?");
            addConditionParam(userDetails.getUserType());
        }

        if (userDetails.getSystemTypeId() != null) {
            addCondition("u.system_type_id= ?");
            addConditionParam(userDetails.getSystemTypeId());
        }

        if (userDetails.getTerritoryId() != null) {
            addCondition("u.territory_id= ?");
            addConditionParam(userDetails.getTerritoryId());
        }

        if (userDetails.getLobId() != null) {
            addCondition("u.lob_id= ?");
            addConditionParam(userDetails.getLobId());
        }

        if (userDetails.getSoftwareTypeId() != null) {
            addCondition("u.software_type_id= ?");
            addConditionParam(userDetails.getSoftwareTypeId());
        }

        if (userDetails.getStatusId() != null) {
            addCondition("u.status_id= ?");
            addConditionParam(userDetails.getStatusId());
        }
        if (userDetails.getSizeId() != null) {
            addCondition("u.size_id= ?");
            addConditionParam(userDetails.getSizeId());
        }
        if (userDetails.getVerifiedId() != null) {
            addCondition("u.verified_id= ?");
            addConditionParam(userDetails.getVerifiedId());
        }

        if (userDetails.getTerritoryIdOrNull() != null) {
            addCondition("( u.territory_id=? or value(u.territory_id, 0)=0 )");
            addConditionParam(userDetails.getTerritoryIdOrNull());
        }
        
        if (userDetails.getDateFormatId() != null) {
            addCondition("u.date_format_id= ?");
            addConditionParam(userDetails.getDateFormatId());
        }
        
        if (userDetails.getTimeOffsetId() != null) {
            addCondition("u.time_offset_id= ?");
            addConditionParam(userDetails.getTimeOffsetId());
        }
 
        if (userDetails.getSpecificStatuses() != null) {
            addCondition("u.specific_statuses= ?");
            addConditionParam(userDetails.getSpecificStatuses());
        }
        
        if (userDetails.getSpecificTerritories() != null) {
            addCondition("u.specific_territories= ?");
            addConditionParam(userDetails.getSpecificTerritories());
        }
        
        if (userDetails.getSpecificLobs() != null) {
            addCondition("u.specific_lobs= ?");
            addConditionParam(userDetails.getSpecificLobs());
        }
        
        if (userDetails.getLimitProspectSearchView() != null) {
            addCondition("u.limit_view= ?");
            addConditionParam(userDetails.getLimitProspectSearchView());
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param userDetails UserDetails
     * @return UserDetails
     * @throws Exception
     */
    public UserDetails insert(BigDecimal fillType, UserDetails userDetails) throws Exception {
        
    	userDetails.setUserId(getAvailableID("user_id", "webuser"));

        addColumnList("company_id, user_id, user_type, creation_date, first_name, last_name, email");
        addColumnList("system_type_id, territory_id, lob_id, software_type_id, " +
                      "verified_id, size_id,country_id, state_id");
        addColumnList("active, user_name, password, allow_cal_of_all_territories, date_format_id, time_offset_id, " +
        			  "specific_territories, specific_statuses, limit_view, specific_lobs, limit_company");
        
        return (UserDetails) doInsert(userDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param userDetails UserDetails
     * @return UserDetails
     * @throws Exception
     */
    public UserDetails update(BigDecimal fillType, UserDetails userDetails) throws Exception {
        if (userDetails.getUserId() != null) {
            addCondition("user_id = ?");
            addConditionParam(userDetails.getUserId());

            if (fillType.equals(UserView.FILL_TYPE_ACCOUNT)
                || fillType.equals(UserView.FILL_TYPE_ALL)) {
                addColumnList("change_date, first_name, last_name,email");

                if (userDetails.getUserName() != null) {
                    addColumnList("user_name");
                }

                if (userDetails.getPassword() != null) {
                    addColumnList("password");
                }
                
                if (userDetails.getAllowCalendarViewOfAllTerritories() != null) {
                    addColumnList("allow_cal_of_all_territories");
                }

                if (fillType.equals(UserView.FILL_TYPE_ALL)) {
                    addColumnList("company_id, system_type_id, territory_id, lob_id, software_type_id, " +
                                  "verified_id,  size_id, status_id, country_id, state_id, time_offset_id, date_format_id, " +
                                  "specific_territories, specific_statuses, limit_view, specific_lobs, limit_company");
                }
            }
            return (UserDetails) doUpdate(userDetails);
        } else {
            return new UserDetails();
        }
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param userDetails UserDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, UserDetails userDetails) throws Exception {
        
    	if(userDetails.getUserId()!= null){
        	addCondition("user_id= ?");
            addConditionParam(userDetails.getUserId());

            doDelete(userDetails);
            return true;	
    	}else{
    		return false;
    	}

    }

}