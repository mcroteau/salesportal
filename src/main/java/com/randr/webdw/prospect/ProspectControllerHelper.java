package com.randr.webdw.prospect;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;

public class ProspectControllerHelper {
	
    /**
     * Method getProspectDataFromRequest.
     * @param prospectDetails ProspectDetails
     */
    public static void getProspectDataFromRequest(HttpServletRequest request, ProspectDetails prospectDetails, UserDetails salesmanDetails) {
    	
        if (request.getParameter("dfCompanyId") != null
                && !request.getParameter("dfCompanyId").equals("")) {
            prospectDetails.setCompanyId(new BigDecimal(request.getParameter("dfCompanyId")));
        } else {
            prospectDetails.setCompanyId(null);
        }

        if (request.getParameter("dfContactName") != null
                && !request.getParameter("dfContactName").equals("")) {
            prospectDetails.setContactName(request.getParameter("dfContactName"));
        } else {
            prospectDetails.setContactName(null);
        }

        if (request.getParameter("dfContactTitle") != null
                && !request.getParameter("dfContactTitle").equals("")) {
            prospectDetails.setContactTitle(request.getParameter("dfContactTitle"));
        } else {
            prospectDetails.setContactTitle(null);
        }

        if (request.getParameter("dfTerritoryId") != null
                && !request.getParameter("dfTerritoryId").equals("")) {
            prospectDetails.setTerritoryId(new BigDecimal(request.getParameter("dfTerritoryId")));
            prospectDetails.setTerritoryChangeDate(DateUtilities.getCurrentSQLTimestamp());
            
        } else {
            prospectDetails.setTerritoryId(null);
        }

        if (request.getParameter("dfLobId") != null
                && !request.getParameter("dfLobId").equals("")) {
            prospectDetails.setLobId(new BigDecimal(request.getParameter("dfLobId")));
        } else {
            prospectDetails.setLobId(null);
        }

        if (request.getParameter("dfStatusId") != null
                && !request.getParameter("dfStatusId").equals("")) {
            prospectDetails.setStatusId(new BigDecimal(request.getParameter("dfStatusId")));
        } else {
            prospectDetails.setStatusId(null);
        }

        if (request.getParameter("dfSoftwareTypeId") != null
                && request.getParameter("dfSoftwareTypeId") != null
                && !request.getParameter("dfSoftwareTypeId").equals("")) {
            prospectDetails.setSoftwareTypeId(new BigDecimal(request.getParameter("dfSoftwareTypeId")));
        } else {
            prospectDetails.setSoftwareTypeId(null);
        }

        if (request.getParameter("dfSystemTypeId") != null
        		&& !request.getParameter("dfSystemTypeId").equals("0")
                && !request.getParameter("dfSystemTypeId").equals("")) {
            prospectDetails.setSystemTypeId(new BigDecimal(request.getParameter("dfSystemTypeId")));
        } else {
            prospectDetails.setSystemTypeId(null);
        }

        if (request.getParameter("dfVerifiedId") != null
                && !request.getParameter("dfVerifiedId").equals("")) {
            prospectDetails.setVerifiedId(new BigDecimal(request.getParameter("dfVerifiedId")));
        } else {
            prospectDetails.setVerifiedId(null);
        }

        if (request.getParameter("dfCustomerCompany") != null
                && !request.getParameter("dfCustomerCompany").equals("")) {
            prospectDetails.setCustomerCompany(request.getParameter("dfCustomerCompany"));
        } else {
            prospectDetails.setCustomerCompany(null);
        }

        if (request.getParameter("dfAddress") != null
                && !request.getParameter("dfAddress").equals("")) {
            prospectDetails.setAddress(request.getParameter("dfAddress"));
        } else {
            prospectDetails.setAddress(null);
        }

        if (request.getParameter("dfAddress2") != null
                && !request.getParameter("dfAddress2").equals("")) {
            prospectDetails.setAddress2(request.getParameter("dfAddress2"));
        } else {
            prospectDetails.setAddress2(null);
        }

        if (request.getParameter("dfCity") != null
                && !request.getParameter("dfCity").equals("")) {
            prospectDetails.setCity(request.getParameter("dfCity"));
        } else {
            prospectDetails.setCity(null);
        }

        if (request.getParameter("dfCounty") != null
                && !request.getParameter("dfCounty").equals("")) {
            prospectDetails.setCounty(request.getParameter("dfCounty"));
        } else {
            prospectDetails.setCounty(null);
        }

        if (request.getParameter("dfZip") != null
                && !request.getParameter("dfZip").equals("")) {
            prospectDetails.setZip(request.getParameter("dfZip"));
        } else {
            prospectDetails.setZip(null);
        }

        if (request.getParameter("dfPhone") != null
                && !request.getParameter("dfPhone").equals("")) {
            prospectDetails.setPhone(request.getParameter("dfPhone"));
        } else {
            prospectDetails.setPhone(null);
        }

        if (request.getParameter("dfPhoneExt") != null
                && !request.getParameter("dfPhoneExt").equals("")) {
            prospectDetails.setPhoneExt(request.getParameter("dfPhoneExt"));
        } else {
            prospectDetails.setPhoneExt(null);
        }

        if (request.getParameter("dfCellPhone") != null
                && !request.getParameter("dfCellPhone").equals("")) {
            prospectDetails.setCellPhone(request.getParameter("dfCellPhone"));
        } else {
            prospectDetails.setCellPhone(null);
        }

        if (request.getParameter("dfEmail") != null
                && !request.getParameter("dfEmail").equals("")) {
            prospectDetails.setEmail(request.getParameter("dfEmail"));
        } else {
            prospectDetails.setEmail(null);
        }

        if (request.getParameter("dfFax") != null
                && !request.getParameter("dfFax").equals("")) {
            prospectDetails.setFax(request.getParameter("dfFax"));
        } else {
            prospectDetails.setFax(null);
        }

        if (request.getParameter("dfWebsite") != null
                && !request.getParameter("dfWebsite").equals("")) {
            prospectDetails.setWebsite(request.getParameter("dfWebsite"));
        } else {
            prospectDetails.setWebsite(null);
        }

        if (request.getParameter("dfCountryId") != null
                && !request.getParameter("dfCountryId").equals("")) {
            prospectDetails.setCountryId(new BigDecimal(request.getParameter("dfCountryId")));
        } else {
            prospectDetails.setCountryId(null);
        }

        if (request.getParameter("dfStateId") != null
                && !request.getParameter("dfStateId").equals("")) {
            prospectDetails.setStateId(new BigDecimal(request.getParameter("dfStateId")));
        } else {
            prospectDetails.setStateId(null);
        }

        if (request.getParameter("dfSizeId") != null
                && !request.getParameter("dfSizeId").equals("")) {
            prospectDetails.setSizeId(new BigDecimal(request.getParameter("dfSizeId")));
        } else {
            prospectDetails.setSizeId(null);
        }

        if (request.getParameter("dfSystemNo") != null
                && !request.getParameter("dfSystemNo").equals("")) {
            prospectDetails.setSystemNo(request.getParameter("dfSystemNo"));
        } else {
            prospectDetails.setSystemNo(null);
        }

        if (request.getParameter("dfSsa") != null
                && !request.getParameter("dfSsa").equals("")) {
            prospectDetails.setSsa(request.getParameter("dfSsa"));
        } else {
            prospectDetails.setSsa(null);
        }

        if (request.getParameter("dfSic") != null
                && !request.getParameter("dfSic").equals("")) {
            prospectDetails.setSic(request.getParameter("dfSic"));
        } else {
            prospectDetails.setSic(null);
        }

        if (request.getParameter("dfHardmnt") != null
                && !request.getParameter("dfHardmnt").equals("")) {
            prospectDetails.setHardwareMaintenance(request.getParameter("dfHardmnt"));
        } else {
            prospectDetails.setHardwareMaintenance(null);
        }

        if (request.getParameter("dfActivityDate") != null
                && !request.getParameter("dfActivityDate").equals("")) {

            prospectDetails.setActivityDate(DateUtilities.getSQLTimestamp(request.getParameter("dfActivityDate") + " 00:00:00", salesmanDetails.getDateFormat()));
        } else {
            prospectDetails.setActivityDate(null);
        }
        //Performance Data
        if (request.getParameter("dfOpenOrdersNumber") != null
                && !request.getParameter("dfOpenOrdersNumber").equals("")) {//open orders #
            prospectDetails.setOpenOrdersNumber(new BigDecimal(request.getParameter("dfOpenOrdersNumber")));
        } else {
            prospectDetails.setOpenOrdersNumber(null);
        }
        if (request.getParameter("dfOpenOrdersValue") != null
                && !request.getParameter("dfOpenOrdersValue").equals("")) {// open orders value
            prospectDetails.setOpenOrdersValue(new BigDecimal(request.getParameter("dfOpenOrdersValue")));
        } else {
            prospectDetails.setOpenOrdersValue(null);
        }
        if (request.getParameter("dfOpenQuotesNumber") != null
                && !request.getParameter("dfOpenQuotesNumber").equals("")) {// open quotes #
            prospectDetails.setOpenQuotesNumber(new BigDecimal(request.getParameter("dfOpenQuotesNumber")));
        } else {
            prospectDetails.setOpenQuotesNumber(null);
        }
        if (request.getParameter("dfOpenQuotesValue") != null
                && !request.getParameter("dfOpenQuotesValue").equals("")) {//open quotes value
            prospectDetails.setOpenQuotesValue(new BigDecimal(request.getParameter("dfOpenQuotesValue")));
        } else {
            prospectDetails.setOpenQuotesValue(null);
        }
        if (request.getParameter("dfOpenAccountsReceivable") != null
                && !request.getParameter("dfOpenAccountsReceivable").equals("")) {// open AR
            prospectDetails.setOpenAccountsReceivable(new BigDecimal(request.getParameter("dfOpenAccountsReceivable")));
        } else {
            prospectDetails.setOpenAccountsReceivable(null);
        }
        if (request.getParameter("dfInvoicesNumberLtd") != null
                && !request.getParameter("dfInvoicesNumberLtd").equals("")) { //ltd invoices #
            prospectDetails.setInvoicesNumberLtd(new BigDecimal(request.getParameter("dfInvoicesNumberLtd")));
        } else {
            prospectDetails.setInvoicesNumberLtd(null);
        }
        if (request.getParameter("dfInvoicesValueLtd") != null
                && !request.getParameter("dfInvoicesValueLtd").equals("")) {//ltd invoices value
            prospectDetails.setInvoicesValueLtd(new BigDecimal(request.getParameter("dfInvoicesValueLtd")));
        } else {
            prospectDetails.setInvoicesValueLtd(null);
        }
        if (request.getParameter("dfInvoicesNumberYtd") != null
                && !request.getParameter("dfInvoicesNumberYtd").equals("")) {//ytd invoices #
            prospectDetails.setInvoicesNumberYtd(new BigDecimal(request.getParameter("dfInvoicesNumberYtd")));
        } else {
            prospectDetails.setInvoicesNumberYtd(null);
        }
        if (request.getParameter("dfInvoicesValueYtd") != null
                && !request.getParameter("dfInvoicesValueYtd").equals("")) {//ytd invoices value
            prospectDetails.setInvoicesValueYtd(new BigDecimal(request.getParameter("dfInvoicesValueYtd")));
        } else {
            prospectDetails.setInvoicesValueYtd(null);
        }
        if (request.getParameter("dfForecast") != null
                && !request.getParameter("dfForecast").equals("")) {// forecast
            prospectDetails.setForecast(new BigDecimal(request.getParameter("dfForecast")));
        } else {
            prospectDetails.setForecast(null);
        }
        if (request.getParameter("dfOrderportalUserId") != null
                && !request.getParameter("dfOrderportalUserId").equals("")) {//orderportal user id
            prospectDetails.setOrderportalUserId(new BigDecimal(request.getParameter("dfOrderportalUserId")));
        } else {
            prospectDetails.setOrderportalUserId(null);
        }
        if (request.getParameter("dfEbsUserId") != null
                && !request.getParameter("dfEbsUserId").equals("")) {//ebs user id
            prospectDetails.setEbsUserId(new BigDecimal(request.getParameter("dfEbsUserId")));
        } else {
            prospectDetails.setEbsUserId(null);
        }

    }

	public static  void getProspectSalesActionFromRequest(HttpServletRequest request, ProspectSalesActionDetails updateProspectSalesActionsDetails) {
        //added Sales Action
        if (request.getParameter("dfSalesActionDate") != null
                && !request.getParameter("dfSalesActionDate").equals("")) {
        	Timestamp timestamp=null;

        	if(!Utilities.nullToBlank(request.getParameter("dfMinute")).equals("")){
        		timestamp = DateUtilities.getSqlTimestamp(request.getParameter("dfSalesActionDate"),
        			request.getParameter("dfHour"), request.getParameter("dfMinute"),
        			request.getParameter("time"));
        	}else{
        		timestamp = DateUtilities.getSqlTimestamp(request.getParameter("dfSalesActionDate"),
        			request.getParameter("dfHour"), "00",
        			request.getParameter("time"));
        	}
        	updateProspectSalesActionsDetails.setActionDate(timestamp);
        } else {
        	updateProspectSalesActionsDetails.setActionDate(null);
        }
        
        if (request.getParameter("cmbSalesAction") != null
                && !request.getParameter("cmbSalesAction").equals("")) {
        	updateProspectSalesActionsDetails.setSalesActionId(new BigDecimal(request.getParameter("cmbSalesAction")));
        } else {
        	updateProspectSalesActionsDetails.setSalesActionId(null);
        }
        
        if (request.getParameter("cmbPriority") != null
                && !request.getParameter("cmbPriority").equals("")) {
        	updateProspectSalesActionsDetails.setActionPriority(new BigDecimal(request.getParameter("cmbPriority")));
        } else {
        	updateProspectSalesActionsDetails.setActionPriority(new BigDecimal(1));;
        }
        
        if (request.getParameter("dfActionNote") != null
                && !request.getParameter("dfActionNote").equals("")) {
        	updateProspectSalesActionsDetails.setActionNote(request.getParameter("dfActionNote"));
        } else {
        	updateProspectSalesActionsDetails.setActionNote(null);
        }
        
        updateProspectSalesActionsDetails.setActionNotificationflag(new BigDecimal(0));
        updateProspectSalesActionsDetails.setActionStatus(ProspectSalesActionDetails.STATUS_ACTIVE);
	}
}
