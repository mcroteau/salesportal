package com.randr.webdw.prospect;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 * User: Cami
 * Date: Sep 25, 2003
 * Time: 12:43:19 PM
 * @author randr
 * @version $Revision: 1.9 $
 */
public class ProspectBean extends AbstractDBBean {

    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "prospect";
        this.tableAlias = "p";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param prospectDetails ProspectDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, ProspectDetails prospectDetails) throws Exception {
    	
        establishSearchConditions(prospectDetails, fillType);
        
        if(prospectDetails.getGenericSearchText()!=null){
        	addGenericSearchConditionsText(prospectDetails, fillType);
        }
        if(prospectDetails.getGenericSearchNumeric()!=null){
        	addGenericSearchConditionsNumeric(prospectDetails, fillType);
        }
        if(prospectDetails.getGenericSearchDate()!=null){
        	addGenericSearchConditionsDate(prospectDetails, fillType);
        }

        if (fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_EMAIL)
                || fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_COMPANY_NAME)
                || fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_CONTACT_NAME)
                || fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_PHONE_NUMBER)){
        	useDistinct(true);
        }
        
        // set the detail fields
        addColumnList("p.prospect_id");

        if (fillType.equals(ProspectView.FILL_TYPE_BASIC)
                || fillType.equals(ProspectView.FILL_TYPE_REPORT)
                || fillType.equals(ProspectView.FILL_TYPE_RANDOM)
                || fillType.equals(ProspectView.FILL_TYPE_PRINT)
                || fillType.equals(ProspectView.FILL_TYPE_NEXT_SALES_ACTION_NOTIFY)
                || fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_EMAIL)
                || fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_COMPANY_NAME)
                || fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_CONTACT_NAME)
                || fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_PHONE_NUMBER)
                || fillType.equals(ProspectView.FILL_TYPE_SALES_ACTION_SEARCH)) {
            addColumnList("p.company_id, p.system_type_id, p.territory_id, p.lob_id, p.software_type_id, p.size_id, p.status_id, p.verified_id");
            addColumnList("p.contact_name, p.contact_title");
            addColumnList("p.customer_company, p.address, p.address2, p.city, p.county, p.country_id, p.state_id, p.zip");
            addColumnList("p.phone, p.phone_ext, p.cell_phone, p.fax, p.email, p.website");
            addColumnList("p.system_no, p.ssa, p.sic, p.hardmnt, p.external_id");
            addColumnList("p.creation_date, p.change_date, p.territory_changed_date");
            addColumnList("p.activity_date, p.territory_assigned_on_creation, p.optout_date");
            addColumnList("p.open_orders_number, p.open_orders_value, p.open_quotes_number, p.open_quotes_value");
            addColumnList("p.forecast, p.open_accounts_receivable, p.orderportal_user_id, p.ebs_user_id");
            addColumnList("p.orders_invoices_number_ltd, p.orders_invoices_value_ltd, p.orders_invoices_number_ytd, p.orders_invoices_value_ytd");

            if (fillType.equals(ProspectView.FILL_TYPE_REPORT)
            		|| fillType.equals(ProspectView.FILL_TYPE_RANDOM)
                    || fillType.equals(ProspectView.FILL_TYPE_PRINT)
                    || fillType.equals(ProspectView.FILL_TYPE_SALES_ACTION_SEARCH)
                    || fillType.equals(ProspectView.FILL_TYPE_NEXT_SALES_ACTION_NOTIFY)
                    || fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_EMAIL)
                    || fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_COMPANY_NAME)
                    || fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_CONTACT_NAME)
                    || fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_PHONE_NUMBER)) {
            	
                addColumnList("c.company");
                addOtherTables(" inner join " +
                        AppSettings.getParm("LIB") + "company c on c.company_id = p.company_id");

                addColumnList("sy.system_type");
                addOtherTables(" left outer join " +
                        AppSettings.getParm("LIB") + "system_type sy on sy.system_type_id = p.system_type_id");

                addColumnList("sf.software_type");
                addOtherTables(" left outer join " +
                        AppSettings.getParm("LIB") + "software_type sf on sf.software_type_id = p.software_type_id");

                addColumnList("t.territory,  t.owner_user_id, t.sales_manager_user_id, t.service_manager_user_id");
                addOtherTables(" left outer join " +
                        AppSettings.getParm("LIB") + "territory t on t.territory_id = p.territory_id");

                addColumnList("l.lob");
                addOtherTables(" left outer join " +
                        AppSettings.getParm("LIB") + "lob l on l.lob_id = p.lob_id");

                addColumnList("st.status");
                addOtherTables(" left outer join " +
                        AppSettings.getParm("LIB") + "status st on st.status_id = p.status_id");

                addColumnList("si.size");
                addOtherTables(" left outer join " +
                        AppSettings.getParm("LIB") + "size si on si.size_id = p.size_id");

                addColumnList("v.verified");
                addOtherTables(" left outer join " +
                        AppSettings.getParm("LIB") + "verified v on v.verified_id = p.verified_id");

//                addColumnList("ac.action");
//                addOtherTables(" left outer join " +
//                        AppSettings.getParm("LIB") + "action ac on ac.action_id = p.next_action_id");

                addColumnList("cn.country");
                addOtherTables(" left outer join " +
                        AppSettings.getParm("LIB") + "country cn on cn.country_id = p.country_id");

                addColumnList("sta.state");
                addOtherTables(" left outer join " +
                        AppSettings.getParm("LIB") + "state sta on sta.state_id = p.state_id");

                if (fillType.equals(ProspectView.FILL_TYPE_NEXT_SALES_ACTION_NOTIFY)) {
                    addColumnList("u1.user_name,  u1.email, u1.active");
                    addOtherTables(" left outer join " +
                            AppSettings.getParm("LIB") + "webuser u1 on t.owner_user_id = u1.user_id");

                    addColumnList("u2.user_name,  u2.email, u2.active");
                    addOtherTables(" left outer join " +
                            AppSettings.getParm("LIB") + "webuser u2 on t.sales_manager_user_id = u2.user_id");

                    addColumnList("u3.user_name,  u3.email, u3.active");
                    addOtherTables(" left outer join " +
                            AppSettings.getParm("LIB") + "webuser u3 on t.service_manager_user_id = u3.user_id");

                }
                if (fillType.equals(ProspectView.FILL_TYPE_SALES_ACTION_SEARCH)) {
                    addOtherTables(" inner join prospect_sales_action psa on psa.prospect_id = p.prospect_id");
                    addColumnList("psa.action_priority,  psa.action_date, psa.action_note, psa.sales_action_id, psa.action_status");
                }
                
                if (fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_EMAIL)) {
                    addOtherTables(" left outer join prospect p2 on p.email = p2.email");
                    addCondition("p.email is not null and p.email <> ' ' and p.prospect_id <>p2.prospect_id");
                    addOrderBy("p.email");
                }
                if (fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_COMPANY_NAME)) {
                    addOtherTables(" left outer join prospect p2 on p.customer_company = p2.customer_company");
                    addCondition("p.customer_company is not null and p.customer_company <> ' ' and p.prospect_id <>p2.prospect_id");
                    addOrderBy("p.customer_company");
                }
                if (fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_CONTACT_NAME)) {
                    addOtherTables(" left outer join prospect p2 on p.contact_name = p2.contact_name");
                    addCondition("p.contact_name is not null and p.contact_name <> ' ' and p.prospect_id <>p2.prospect_id");
                    addOrderBy("p.contact_name");
                }
                if (fillType.equals(ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_PHONE_NUMBER)) {
                    addOtherTables(" left outer join prospect p2 on p.phone = p2.phone ");
                    addCondition("p.phone is not null and p.phone <> ' ' and p.prospect_id <>p2.prospect_id");
                    addOrderBy("p.phone");
                }
                
                if (fillType.equals(ProspectView.FILL_TYPE_RANDOM )) {
                    setOrderBy(" RANDOM() LIMIT " + prospectDetails.getSearchLimit());
                }
                
                if (fillType.equals(ProspectView.FILL_TYPE_REPORT )) {
                    setOrderBy("p.customer_company");
                }
            }

            if (!getOrderBy().equals("") && !fillType.equals(ProspectView.FILL_TYPE_RANDOM)) {
                addColumnList(Utilities.replaceString(Utilities.replaceString(getOrderBy(), "asc", ""), "desc", ""));
            }
        }

        return doSelect(prospectDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param prospectDetails ProspectDetails
     */
    private void establishSearchConditions(ProspectDetails prospectDetails, BigDecimal fillType) {

    	if (prospectDetails.getProspectId() != null) {
            addCondition("p.prospect_id = ?");
            addConditionParam(prospectDetails.getProspectId());
        }

        if (prospectDetails.getIdsVector() != null && prospectDetails.getIdsVector().size() > 0) {
            if (prospectDetails.getIdsVector().size() == 1) {
                addCondition("p.prospect_id = ?");
                addConditionParam((BigDecimal) prospectDetails.getIdsVector().elementAt(0));
            } else {
                String condition = "p.prospect_id IN (";
                for (int i = 0; i < prospectDetails.getIdsVector().size(); i++) {
                    if (i > 0) {
                        condition += ",";
                    }
                    condition += ((BigDecimal) prospectDetails.getIdsVector().elementAt(i)).toString();
                }
                condition += ")";
                addCondition(condition);
            }
        }

        if (prospectDetails.getTerritoryIdList() != null && prospectDetails.getTerritoryIdList().size() > 0) {
            if (prospectDetails.getTerritoryIdList().size() == 1) {
                addCondition("p.territory_id = ?");
                addConditionParam((BigDecimal) prospectDetails.getTerritoryIdList().get(0));
            } else {
                String condition = "p.territory_id IN (";
                for (int i = 0; i < prospectDetails.getTerritoryIdList().size(); i++) {
                    if (i > 0) {
                        condition += ",";
                    }
                    condition += ((BigDecimal) prospectDetails.getTerritoryIdList().get(i)).toString();
                }
                condition += ")";
                addCondition(condition);
            }
        }

        if (prospectDetails.getStatusIdList() != null && prospectDetails.getStatusIdList().size() > 0) {
            if (prospectDetails.getStatusIdList().size() == 1) {
                addCondition("p.status_id = ?");
                addConditionParam((BigDecimal) prospectDetails.getStatusIdList().get(0));
            } else {
                String condition = "p.status_id IN (";
                for (int i = 0; i < prospectDetails.getStatusIdList().size(); i++) {
                    if (i > 0) {
                        condition += ",";
                    }
                    condition += ((BigDecimal) prospectDetails.getStatusIdList().get(i)).toString();
                }
                condition += ")";
                addCondition(condition);
            }
        }        
//        if (prospectDetails.getLobId() != null) {
//            addCondition("p.lob_id= ?");
//            addConditionParam(prospectDetails.getLobId());
//        }
        if (prospectDetails.getLobIdList() != null && prospectDetails.getLobIdList().size() > 0) {
            if (prospectDetails.getLobIdList().size() == 1) {
                addCondition("p.lob_id = ?");
                addConditionParam((BigDecimal) prospectDetails.getLobIdList().get(0));
            } else {
                String condition = "p.lob_id IN (";
                for (int i = 0; i < prospectDetails.getLobIdList().size(); i++) {
                    if (i > 0) {
                        condition += ",";
                    }
                    condition += ((BigDecimal) prospectDetails.getLobIdList().get(i)).toString();
                }
                condition += ")";
                addCondition(condition);
            }
        }

        if (prospectDetails.getCompanyId() != null) {
            addCondition("p.company_id = ?");
            addConditionParam(prospectDetails.getCompanyId());
        }

        if (prospectDetails.getCountryId() != null) {
            addCondition("p.country_id = ?");
            addConditionParam(prospectDetails.getCountryId());
        }

        if (prospectDetails.getStateId() != null) {
            addCondition("p.state_id = ?");
            addConditionParam(prospectDetails.getStateId());
        }

        if (prospectDetails.getContactName() != null) {
            addCondition("upper(p.contact_name) like ?");
            addConditionParam("%" + prospectDetails.getContactName().toUpperCase() + "%");
        }

        if (prospectDetails.getCustomerCompany() != null) {
            addCondition("upper(p.customer_company) like ?");
            addConditionParam("%" + prospectDetails.getCustomerCompany().toUpperCase() + "%");
        }

        if (prospectDetails.getCity() != null) {
            addCondition("upper(p.city) like ?");
            addConditionParam("%" + prospectDetails.getCity().toUpperCase() + "%");
        }
        if (prospectDetails.getZip() != null) {
            addCondition("upper(p.zip) like ?");
            addConditionParam("%" + prospectDetails.getZip() + "%");
        }

        if (prospectDetails.getSystemTypeId() != null) {
            addCondition("p.system_type_id= ?");
            addConditionParam(prospectDetails.getSystemTypeId());
        }

        if (prospectDetails.getTerritoryId() != null) {
            addCondition("p.territory_id= ?");
            addConditionParam(prospectDetails.getTerritoryId());
        }

        if (prospectDetails.getSoftwareTypeId() != null) {
            addCondition("p.software_type_id= ?");
            addConditionParam(prospectDetails.getSoftwareTypeId());
        }

        if (prospectDetails.getStatusId() != null) {
            addCondition("p.status_id= ?");
            addConditionParam(prospectDetails.getStatusId());
        }
        
        if (prospectDetails.getSizeId() != null) {
            addCondition("p.size_id= ?");
            addConditionParam(prospectDetails.getSizeId());
        }

        if (prospectDetails.getVerifiedId() != null) {
            addCondition("p.verified_id= ?");
            addConditionParam(prospectDetails.getVerifiedId());
        }
        
        if (prospectDetails.getExternalId() != null) {
            addCondition("p.external_id= ?");
            addConditionParam(prospectDetails.getExternalId());
        }

        if (fillType.equals(ProspectView.FILL_TYPE_SALES_ACTION_SEARCH)) {
        	
        	if (prospectDetails.getSearchNextSalesActionId() != null) {
	          addCondition("psa.sales_action_id= ?");
	          addConditionParam(prospectDetails.getSearchNextSalesActionId());
	        }
        	//sales action search is now a range of dates
        	if (prospectDetails.getSearchNextSalesActionDate() != null) {
        		addCondition("date(psa.action_date) >= date(?)");
        		addConditionParam(prospectDetails.getSearchNextSalesActionDate());
        	}
	      
        	if (prospectDetails.getSearchNextSalesActionDateEnd() != null) {
        		addCondition("date(psa.action_date) <= date(?)");
        		addConditionParam(prospectDetails.getSearchNextSalesActionDateEnd());
        	}
        	
        	if (prospectDetails.getCurrentSalesActionStatus() != null) {
        		addCondition("psa.action_status= ?");
        		addConditionParam(prospectDetails.getCurrentSalesActionStatus());
        	}
	      
        }

        if (prospectDetails.getTerritoryChangeDate() != null) {
            addCondition("p.territory_changed_date= ?");
            addConditionParam(prospectDetails.getTerritoryChangeDate());
        }
        
        if (prospectDetails.getActivityDate() != null) {
            addCondition("p.activity_date= ?");
            addConditionParam(prospectDetails.getActivityDate());
        }
        
        if (prospectDetails.getEmail() != null) {
        	addCondition("upper(p.email) like ?");
            addConditionParam("%" + prospectDetails.getEmail().toUpperCase() + "%");
        }
        
        if (prospectDetails.getPhone() != null) {
        	addCondition("p.phone like ?");
            addConditionParam("%" + prospectDetails.getPhone() + "%");
        }
        
        if (prospectDetails.getContactTitle() != null) {
        	addCondition("upper(p.contact_title) like ?");
            addConditionParam("%" + prospectDetails.getContactTitle().toUpperCase() + "%");
        }
        
        if (prospectDetails.getTerritoryAssignedOnProspectCreation() != null) {
        	addCondition("p.territory_assigned_on_creation = ?");
        	addConditionParam(prospectDetails.getTerritoryAssignedOnProspectCreation());
        }
        
        if (prospectDetails.getSearchChangeDate() != null) {
    		addCondition("date(p.change_date) >= date(?)");
    		addConditionParam(prospectDetails.getSearchChangeDate());
    	}
        if (prospectDetails.getSearchExactChangeDate() != null) {
    		addCondition("date(p.change_date)= date(?)");
    		addConditionParam(prospectDetails.getSearchExactChangeDate());
    	}
        if (prospectDetails.isNotOptedOut()) {
    		addCondition("p.optout_date is null");
    	}
        if (prospectDetails.getCreationDate() != null) {
            addCondition("date(creation_date) >= date(?)");
            addConditionParam(prospectDetails.getCreationDate());
        }
        if (prospectDetails.isExcludeNIAndDNC()){//hard coded for stone, need to come up with better way
        	addCondition("(p.status_id <> 2 and p.status_id <> 7 or p.status_id is null)");
        	
        }
        if (prospectDetails.isTerritoryIsNull()) {
    		addCondition("p.territory_id is null");
    	}
        if (prospectDetails.isLobIsNull()) {
    		addCondition("p.lob_id is null");
    	}
        if (prospectDetails.isStatusIsNull()) {
    		addCondition("p.status_id is null");
    	}
    }

    private void addGenericSearchConditionsText(ProspectDetails prospectDetails, BigDecimal fillType) {
    	String genericSearch = prospectDetails.getGenericSearchText().trim();

             addCondition("(" +
             "upper(p.contact_name) like '%" + genericSearch.toUpperCase() +"%' " +
     		 "or upper(p.customer_company) like '%" + genericSearch.toUpperCase() +"%'" +
     		 " or upper(p.address) like '%" + genericSearch.toUpperCase() +"%'" +
     		 " or upper(p.address2) like '%" + genericSearch.toUpperCase() +"%'" +
             " or upper(p.city) like '%" + genericSearch.toUpperCase() +"%'" +
             " or upper(p.county) like '%" + genericSearch.toUpperCase() +"%'" +

             " or upper(sta.state) like '%" + genericSearch.toUpperCase() +"%'" +
             " or upper(cn.country) like '%" + genericSearch.toUpperCase() +"%'" +
       
             " or upper(p.zip) like '%" + genericSearch.toUpperCase() +"%'" +
         
             " or upper(p.ssa) like '%" + genericSearch.toUpperCase() +"%'" +
             " or upper(p.sic) like '%" + genericSearch.toUpperCase() +"%'" +
             " or upper(p.system_no) like '%" + genericSearch.toUpperCase() +"%'" +
             " or upper(p.hardmnt) like '%" + genericSearch.toUpperCase() +"%'" +
             
            
        	" or upper(p.email) like '%" + genericSearch.toUpperCase() +"%'" +
       
        	" or upper(p.phone) like '%" + genericSearch.toUpperCase() +"%'" +
            " or upper(p.phone_ext) like '%" + genericSearch.toUpperCase() +"%'" +
            " or upper(p.cell_phone) like '%" + genericSearch.toUpperCase() +"%'" +
       
            " or Upper(l.lob) like '%" + genericSearch.toUpperCase() +"%'" +
            " or upper(sf.software_type) like '%" + genericSearch.toUpperCase() +"%'" +
            " or upper(sy.system_type) like '%" + genericSearch.toUpperCase() +"%'" +
            " or upper(st.status) like '%" + genericSearch.toUpperCase() +"%'" +
            "or upper(p.contact_title) like '%" + genericSearch.toUpperCase() +"%'" +
            "or upper(p.website) like '%" + genericSearch.toUpperCase() +"%'" +
            " or upper(p.fax) like '%" + genericSearch.toUpperCase() +"%'" +
            " or upper(p.size) like '%" + genericSearch.toUpperCase() +"%'" +
           

   			")");        
	}
    
    private void addGenericSearchConditionsNumeric(ProspectDetails prospectDetails, BigDecimal fillType) {
    	BigDecimal genericSearch = prospectDetails.getGenericSearchNumeric();
    	addCondition("(" +
    	"p.territory_id= " + genericSearch +
    	" or p.open_orders_number= " + genericSearch +
    	" or p.open_orders_value= " + genericSearch +
    	" or p.open_quotes_number= " + genericSearch +
    	" or p.open_quotes_value= " + genericSearch +
    	" or p.forecast= " + genericSearch +
    	" or p.open_accounts_receivable= " + genericSearch +
    	" or p.orders_invoices_number_ltd= " + genericSearch +
    	" or p.orders_invoices_value_ltd= " + genericSearch +
    	" or p.orders_invoices_number_ytd= " + genericSearch +
    	" or p.orders_invoices_value_ytd= " + genericSearch +
    	" or p.orderportal_user_id= " + genericSearch +
    	" or p.ebs_user_id= " + genericSearch +
    	
    	")");      
    }  	
    
    private void addGenericSearchConditionsDate(ProspectDetails prospectDetails, BigDecimal fillType) {
    	Date genericSearch = prospectDetails.getGenericSearchDate();
    
    	addCondition("(" +
    		
    		"    date(p.creation_date) = date('" + genericSearch+ "')" +
            " or date(p.change_date)= date('" + genericSearch+ "')"+	
            " or date(p.territory_changed_date)= date('" + genericSearch+ "')"+
            " or date(p.activity_date)= date('" + genericSearch+ "')"+
            " or date(p.optout_date)= date('" + genericSearch+ "')"+

    	")");   

    }
    
    
    
    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param prospectDetails ProspectDetails
     * @return ProspectDetails
     * @throws Exception
     */
    public ProspectDetails insert(BigDecimal fillType, ProspectDetails prospectDetails) throws Exception {
    	prospectDetails.setProspectId(getAvailableID("prospect_id", "prospect"));

        addColumnList("company_id, prospect_id, creation_date, creation_user_id, change_date, change_user_id,contact_name, contact_title");
        addColumnList("system_type_id, territory_id, lob_id, software_type_id, verified_id, status_id, size_id");
        addColumnList("customer_company, address, address2, city, county, state_id, zip, country_id, phone, phone_ext, cell_phone, fax, email, website");
        addColumnList(" ssa, sic, system_no, hardmnt, external_id, territory_changed_date, activity_date, territory_assigned_on_creation, optout_date");
        addColumnList("open_orders_number, open_orders_value, open_quotes_number, open_quotes_value");
        addColumnList("forecast, open_accounts_receivable, orderportal_user_id, ebs_user_id");
        addColumnList("orders_invoices_number_ltd, orders_invoices_value_ltd, orders_invoices_number_ytd, orders_invoices_value_ytd");

        //addColumnList("next_action_id, next_action_date, next_action_hour, next_action_minute, next_action_am_pm");

        return (ProspectDetails) doInsert(prospectDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param prospectDetails ProspectDetails
     * @return ProspectDetails
     * @throws Exception
     */
    public ProspectDetails update(BigDecimal fillType, ProspectDetails prospectDetails) throws Exception {

        if (prospectDetails.getProspectId() != null) {
            addCondition("prospect_id = ?");
            addConditionParam(prospectDetails.getProspectId());

            if (fillType == null || fillType.equals(ProspectView.FILL_TYPE_BASIC)) {
                addColumnList("company_id, change_date, change_user_id, contact_name, contact_title");
                addColumnList("system_type_id, territory_id, lob_id, software_type_id, verified_id, status_id, size_id");
                addColumnList("customer_company, address, address2, city, county, state_id, zip, country_id, phone, phone_ext, cell_phone, fax, email, website");
                addColumnList("ssa, sic, system_no, hardmnt, external_id, territory_changed_date, activity_date, optout_date");
                addColumnList("open_orders_number, open_orders_value, open_quotes_number, open_quotes_value");
                addColumnList("forecast, open_accounts_receivable, orderportal_user_id, ebs_user_id");
                addColumnList("orders_invoices_number_ltd, orders_invoices_value_ltd, orders_invoices_number_ytd, orders_invoices_value_ytd");


            } else if (fillType.equals(ProspectView.FILL_TYPE_UPDATE_COLLECTION)) {
                addColumnList("change_date, change_user_id");
                if (prospectDetails.getCompanyId() != null) {
                    addColumnList("company_id");
                }
                if (prospectDetails.getSystemTypeId() != null) {
                    addColumnList("system_type_id");
                }
                if (prospectDetails.getTerritoryId() != null) {
                    addColumnList("territory_id");
                }
                if (prospectDetails.getLobId() != null) {
                    addColumnList("lob_id");
                }
                if (prospectDetails.getSoftwareTypeId() != null) {
                    addColumnList("software_type_id");
                }
                if (prospectDetails.getVerifiedId() != null) {
                    addColumnList("verified_id");
                }
                if (prospectDetails.getStatusId() != null) {
                    addColumnList("status_id");
                }
                if (prospectDetails.getSizeId() != null) {
                    addColumnList("size_id");
                }
                if (prospectDetails.getSsa() != null) {
                    addColumnList("ssa");
                }
                if (prospectDetails.getSic() != null) {
                    addColumnList("sic");
                }
                if (prospectDetails.getSystemNo() != null) {
                    addColumnList("system_no");
                }
                if (prospectDetails.getHardwareMaintenance() != null) {
                    addColumnList("hardmnt");
                }
                if (prospectDetails.getCity() != null) {
                    addColumnList("city");
                }
                if (prospectDetails.getCounty() != null) {
                    addColumnList("county");
                }
                if (prospectDetails.getZip() != null) {
                    addColumnList("zip");
                }
                if (prospectDetails.getStateId() != null) {
                    addColumnList("state_id");
                }
                if (prospectDetails.getCountryId() != null) {
                    addColumnList("country_id");
                } 
                if (prospectDetails.getActivityDate() != null) {
                    addColumnList("activity_date");
                } 
                if(prospectDetails.getTerritoryChangeDate()!=null){
                	addColumnList("territory_changed_date");
                }

                
            }

            return (ProspectDetails) doUpdate(prospectDetails);
        } else {
            return new ProspectDetails();
        }
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param prospectDetails ProspectDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, ProspectDetails prospectDetails) throws Exception {
        
    	if (prospectDetails.getCreationDate() != null) {
            addCondition("date(creation_date) >= ?");
            addConditionParam(new java.sql.Date(prospectDetails.getCreationDate().getTime()));

            addCondition("creation_user_id is null");
        } 
    	
    	if(prospectDetails.getProspectId()!= null){
            addCondition("prospect_id= ?");
            addConditionParam(prospectDetails.getProspectId());
        }
    	
    	if(prospectDetails.getProspectId()!= null || prospectDetails.getCreationDate() != null){
    		
            doDelete(prospectDetails);
            return true;
            
    	}else{
    		return false;
    	}

    }

}
