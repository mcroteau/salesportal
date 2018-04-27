package com.randr.webdw.reports.prospectSearch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletException;

import net.sf.jasperreports.engine.JRException;

import com.randr.webdw.AppSettings;
import com.randr.webdw.country.CountryDetails;
import com.randr.webdw.country.CountryView;
import com.randr.webdw.jasper.JasperPrintUtil;
import com.randr.webdw.label.LabelView;
import com.randr.webdw.lob.LobDetails;
import com.randr.webdw.lob.LobView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.salesAction.SalesActionDetails;
import com.randr.webdw.salesAction.SalesActionView;
import com.randr.webdw.size.SizeDetails;
import com.randr.webdw.size.SizeView;
import com.randr.webdw.softwareType.SoftwareTypeDetails;
import com.randr.webdw.softwareType.SoftwareTypeView;
import com.randr.webdw.state.StateDetails;
import com.randr.webdw.state.StateView;
import com.randr.webdw.status.StatusDetails;
import com.randr.webdw.status.StatusView;
import com.randr.webdw.systemType.SystemTypeDetails;
import com.randr.webdw.systemType.SystemTypeView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.verified.VerifiedDetails;
import com.randr.webdw.verified.VerifiedView;

public class ProspectSearchReportController extends AbstractController {
	
	private static final String REPORT_SOURCE_NAME= "PROSPECT_SEARCH_REPORT_JASPER_SOURCE";

	public void doAction() throws ServletException, IOException {
        if (formAction.equals("DISPLAY")) {
	        try{	
	            displayProspectSearchReport();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
       }

	}


	private void displayProspectSearchReport() throws Exception{
		
		prepareJasperReportComponents();
		
	}


	private void prepareJasperReportComponents() throws Exception{
		
		Vector lines = new Vector();
		ProspectView prospectView = ((ProspectView)request.getSession().getAttribute("prospectView"));
		lines.addAll(prospectView.getElements());
		
		HashMap parametersMap = getReportMap();
		//parametersMap.put("searchResultsSize",prospectView.getElements().size());
		
		try {
			JasperPrintUtil.writePdfReport(request, response, 
					AppSettings.getParm(REPORT_SOURCE_NAME), lines, parametersMap);			
		} catch(JRException e) {
			e.printStackTrace();
		}

	}
	//TODO delete the commented lines
	private HashMap getReportMap() throws Exception {
		
		HashMap parametersMap = new HashMap();
		ProspectDetails prospectDetails = ((ProspectDetails)request.getSession().getAttribute("searchProspectDetails"));
		LabelView labelView = ((LabelView)request.getSession().getServletContext().getAttribute("labelView"));
		parametersMap.put("searchContactName",prospectDetails.getContactName());
		parametersMap.put("searchCustomerCompany",prospectDetails.getCustomerCompany());
		parametersMap.put("searchCity",prospectDetails.getCity());
		parametersMap.put("searchZip",prospectDetails.getZip());
		//parametersMap.put("searchSalesActionDate",prospectDetails.getNextSalesActionDate());
		parametersMap.put("searchCounty",prospectDetails.getCounty());
		parametersMap.put("userDropDown1Label", labelView.getLabel(LabelView.USER_DROPDOWN_1));
		parametersMap.put("userDropDown2Label", labelView.getLabel(LabelView.USER_DROPDOWN_2));
		
		
		//Parameters requiring db lookup
		try {
			openConnection();
			parametersMap.put("searchUserDropDown1", getUserDropDown1(prospectDetails));
			parametersMap.put("searchUserDropDown2", getUserDropDown2(prospectDetails));
			parametersMap.put("searchState", getStateName(prospectDetails));
			parametersMap.put("searchCountry", getCountryName(prospectDetails));
			//parametersMap.put("searchSalesAction", getNextSalesActionId(prospectDetails));
			parametersMap.put("searchTerritory", getTerritory(prospectDetails));
			parametersMap.put("searchStatusCode", getStatusCode(prospectDetails));
			parametersMap.put("searchLob", getLob(prospectDetails));
			parametersMap.put("searchVerified", getVerified(prospectDetails));
			parametersMap.put("searchSize", getSize(prospectDetails));
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			doCatch(e);
		}finally{
			finallyMethod();
		}

		//reportParametersMap.put("companyInvoiceLogoPath", appOrderportalSettings.getGraphicsRealPath() + 
		//		appOrderportalSettings.getParm("INVOICE_REPORT_COMPANY_LOGO"));
		return parametersMap;

	}
	
	private Object getSize(ProspectDetails prospectDetails) {
		if(prospectDetails.getSizeId() == null) {
			return null;
		}
		
		SizeView sizeView = new SizeView();
		SizeDetails sizeDetails = new SizeDetails();

		try {
			sizeDetails.setSizeId(prospectDetails.getSizeId());
			sizeView.fillWithElements(connection, SizeDetails.FILL_TYPE_ALL, sizeDetails);
			sizeDetails = (SizeDetails)sizeView.getElement(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sizeDetails.getSize();
	}
	
	private Object getVerified(ProspectDetails prospectDetails) {
		if(prospectDetails.getVerifiedId() == null) {
			return null;
		}
		VerifiedView verifiedView = new VerifiedView();
		VerifiedDetails verifiedDetails = new VerifiedDetails();

		try {
			verifiedDetails.setVerifiedId(prospectDetails.getVerifiedId());
			verifiedView.fillWithElements(connection, VerifiedDetails.FILL_TYPE_ALL, verifiedDetails);
			verifiedDetails = (VerifiedDetails)verifiedView.getElement(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return verifiedDetails.getVerified();
	}
	
	private Object getLob(ProspectDetails prospectDetails) {
		if(prospectDetails.getLobId() == null) {
			return null;
		}
		LobView lobView = new LobView();
		LobDetails lobDetails = new LobDetails();

		try {
			lobDetails.setLobId(prospectDetails.getLobId());
			lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, lobDetails);
			lobDetails = (LobDetails)lobView.getElement(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return lobDetails.getLob();
	}
	
	private Object getStatusCode(ProspectDetails prospectDetails) {
		if(prospectDetails.getStatusId() == null) {
			return null;
		}
		StatusView statusView = new StatusView();
		StatusDetails statusDetails = new StatusDetails();

		try {
			statusDetails.setStatusId(prospectDetails.getStatusId());
			statusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, statusDetails);
			statusDetails = (StatusDetails)statusView.getElement(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return statusDetails.getStatus();
	}



	private Object getTerritory(ProspectDetails prospectDetails) {
		if(prospectDetails.getTerritoryId() == null) {
			return null;
		}
		TerritoryView territoryView = new TerritoryView();
		TerritoryDetails territoryDetails = new TerritoryDetails();

		try {
			territoryDetails.setTerritoryId(prospectDetails.getTerritoryId());
			territoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, territoryDetails);
			territoryDetails = (TerritoryDetails)territoryView.getElement(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return territoryDetails.getTerritory();
	}


//	private Object getNextSalesActionId(ProspectDetails prospectDetails) {
//		if(prospectDetails.getNextSalesActionId() == null) {
//			return null;
//		}
//		SalesActionView salesActionView = new SalesActionView();
//		SalesActionDetails salesActionDetails = new SalesActionDetails();
//		try {
//			salesActionDetails.setActionId(prospectDetails.getNextSalesActionId());
//			salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);
//			salesActionDetails = (SalesActionDetails)salesActionView.getElement(0);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		return salesActionDetails.getAction();
//
//	}


	private Object getCountryName(ProspectDetails prospectDetails) {
		if(prospectDetails.getCountryId() == null) {
			return null;
		}
		CountryView countryView = new CountryView();
		CountryDetails countryDetails = new CountryDetails();
		try {
			countryDetails.setCountryId(prospectDetails.getCountryId());
			countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, countryDetails);
			countryDetails = (CountryDetails)countryView.getElement(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return countryDetails.getCountry();
	}


	private String getUserDropDown1(ProspectDetails prospectDetails){
		if(prospectDetails.getSystemTypeId() == null) {
			return null;
		}
		SystemTypeView systemTypeView = new SystemTypeView();
		SystemTypeDetails systemTypeDetails = new SystemTypeDetails();
		try {
			systemTypeDetails.setSystemTypeId(prospectDetails.getSystemTypeId());
			systemTypeView.fillWithElements(connection, SystemTypeDetails.FILL_TYPE_ALL, systemTypeDetails);
			systemTypeDetails = (SystemTypeDetails)systemTypeView.getElement(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return systemTypeDetails.getSystemType();
	}
	
	private String getUserDropDown2(ProspectDetails prospectDetails){
		if(prospectDetails.getSoftwareTypeId() == null) {
			return null;
		}
		SoftwareTypeView softwareTypeView = new SoftwareTypeView();
		SoftwareTypeDetails softwareTypeDetails = new SoftwareTypeDetails();
		try{
			softwareTypeDetails.setSoftwareTypeId(prospectDetails.getSoftwareTypeId());
			softwareTypeView.fillWithElements(connection, SoftwareTypeDetails.FILL_TYPE_ALL, softwareTypeDetails);
			softwareTypeDetails = (SoftwareTypeDetails)softwareTypeView.getElement(0);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return softwareTypeDetails.getSoftwareType();
	}

	private String getStateName(ProspectDetails prospectDetails){
		if(prospectDetails.getStateId() == null) {
			return null;
		}
		StateView stateView = new StateView();
		StateDetails stateDetails = new StateDetails();
		try{
			stateDetails.setStateId(prospectDetails.getStateId());
			stateView.fillWithElements(connection, StateDetails.FILL_TYPE_ALL, stateDetails);
			stateDetails = (StateDetails)stateView.getElement(0);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return stateDetails.getState();
	}


}
