package com.randr.webdw.importData.importProcessor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.audit.auditRoundRobin.AuditRoundRobinDetails;
import com.randr.webdw.audit.auditRoundRobin.AuditRoundRobinView;
import com.randr.webdw.campaign.CampaignDetails;
import com.randr.webdw.campaignSalesAction.CampaignSalesActionDetails;
import com.randr.webdw.campaignSalesAction.CampaignSalesActionView;
import com.randr.webdw.country.CountryDetails;
import com.randr.webdw.country.CountryView;
import com.randr.webdw.document.DocumentDetails;
import com.randr.webdw.document.DocumentView;
import com.randr.webdw.importData.ImportDataConstants;
import com.randr.webdw.importData.importProcessorAbstract.CsvLine;
import com.randr.webdw.importData.importProcessorAbstract.ImportAbstractProcessor;
import com.randr.webdw.lob.LobDetails;
import com.randr.webdw.lob.LobView;
import com.randr.webdw.note.NoteDetails;
import com.randr.webdw.note.NoteView;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.prospectCampaign.ProspectCampaignDetails;
import com.randr.webdw.prospectCampaign.ProspectCampaignView;
import com.randr.webdw.prospectDocument.ProspectDocumentDetails;
import com.randr.webdw.prospectDocument.ProspectDocumentView;
import com.randr.webdw.prospectImportLog.ProspectImportLogDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionView;
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
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;
import com.randr.webdw.verified.VerifiedDetails;
import com.randr.webdw.verified.VerifiedView;

/**
 */
public class ProspectImportProcessor
        extends ImportAbstractProcessor
        implements ImportDataConstants {

    /**
     * Constructor for ProspectImportProcessor.
     * @param csvFileName String
     * @param csvFilePath String
     * @param processedCsvFilesPath String
     * @param invalidCsvFilesPath String
     * @param errorLogFilesPath String
     * @param prospectImportParameters ProspectImportParameters
     * 3/18/09 - when doing update, do not replace lead source if it exists in prospectDetails, 
     *           do not replace territory if it exists in prospectDetails
     */
    public ProspectImportProcessor(String csvFileName,
                                   String csvFilePath,
                                   String processedCsvFilesPath,
                                   String invalidCsvFilesPath,
                                   String errorLogFilesPath,
                                   ProspectImportParameters prospectImportParameters) {
        super(csvFileName,
              csvFilePath,
              processedCsvFilesPath,
              invalidCsvFilesPath,
              errorLogFilesPath,
              prospectImportParameters);
    }
    
    //need this for updating the prospect and inserting other
    //information like documents, campaigns, sales actions, notes
    ProspectDetails prospectDetailsForUpdate = null;
    
    BigDecimal actionPerformed = new BigDecimal(0);
    private boolean insertedIntoRR;// if true, then add to audit file
    
    private static final BigDecimal UPDATE_EXISTING_PROSPECT = new BigDecimal(1);
    private static final BigDecimal INSERT_NEW_PROSPECT = new BigDecimal(2);
    
    /**
     * Method processFirstLine.
     * @param csvLine CsvLine
     * @throws Exception
     */
    protected void processFirstLine(CsvLine csvLine) throws Exception {        
    }

    /**
     * Method init.
     * @throws Exception
     */
    protected void init() throws Exception {

    }

    /**
     * Method processLine.
     * @param line CsvLine
     * @throws Exception
     */
    protected void processLine(CsvLine line) throws Exception {
    	if(lineIsData(line.getLineElements())){// checking if line is heading or data
	    	//writeProspectToTable(line.getLineElements(), false, "Starting to process prospect ");// write to audit log
	    	if(prospectDoesNotExist(line.getLineElements())){//true = insert new prospect
	//    		System.out.println("new..");
	    		actionPerformed = INSERT_NEW_PROSPECT;
	    		insertedIntoRR=false;
	    		ProspectDetails prospectDetails = new ProspectDetails();
	            getProspectDetails(line.getLineElements(), prospectDetails);
	            setAssignedTerritoryOnCreation(prospectDetails);
	
	            ProspectView prospectView = new ProspectView();
	            writeProspectToTable(line.getLineElements(), false, "About to insert prospect");// write to audit log
	            prospectView.doAction(connection, "INSERT", ProspectView.FILL_TYPE_BASIC, prospectDetails);
	            if(insertedIntoRR){
	            //insert into auditRoundRobin table
		            AuditRoundRobinDetails auditRRDetails = new AuditRoundRobinDetails();
		            AuditRoundRobinView auditRRView = new AuditRoundRobinView();
		            auditRRDetails.setProspectId(prospectDetails.getProspectId());
		            auditRRDetails.setTerritoryId(prospectDetails.getTerritoryId());
		            auditRRDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
		            auditRRDetails.setExternalId(prospectDetails.getExternalId());
		            auditRRView.doAction(connection, "INSERT", AuditRoundRobinDetails.FILL_TYPE_ALL, auditRRDetails);
	            }
	            //now use inserted prospect ID and add campaign, sales action and note if necessary.
	            insertAdditionalInformation(prospectDetails, line);
		
	    	}else{ //false = prospect exists - update the prospectDetails
	    		actionPerformed = UPDATE_EXISTING_PROSPECT;
	    		getProspectDetails(line.getLineElements(), prospectDetailsForUpdate);
	            ProspectView prospectView = new ProspectView();
	            writeProspectToTable(line.getLineElements(), false, "About to update prospect");// write to audit log
	            prospectView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectDetailsForUpdate);
	            insertAdditionalInformationUpdate(prospectDetailsForUpdate, line);   
	            //System.out.println("prospectDetailsForUpdate.getProspectId(), update=" + prospectDetailsForUpdate.getProspectId());
	    	}
    	}
    }

	private boolean lineIsData(Vector lineElements) {
		String firstField = Utilities.nullToBlank(getString(lineElements, 0, 25));
		if(firstField.trim().compareToIgnoreCase("Company name")==0){
			return false;
		}
		return true;
	}

	private void writeProspectToTable(Vector lineElements, boolean writeToLog, String action) {
		String externalId = Utilities.nullToBlank(getString(lineElements, 27, 25));
		String email = Utilities.nullToBlank(getString(lineElements, 18, 100));
		String phone = Utilities.nullToBlank(getString(lineElements, 8, 30));
		if(writeToLog){
			writeProspectLog(externalId, email, phone, action);
		}
	}

	private void setAssignedTerritoryOnCreation(ProspectDetails prospectDetails) {
		//System.out.println("TERRITORY ID TO BE ASSIGNED = " + prospectDetails.getTerritoryId());
        //setTerritoryAssignedOnCreation after getting territory
        if(prospectDetails.getTerritoryId()!=null){
        	prospectDetails.setTerritoryAssignedOnProspectCreation(prospectDetails.getTerritoryId());
        }
		
	}

	/**
	 * @param prospectDetails
	 * @param line
	 * @throws Exception
	 * for Update only add campaign, note and salesAction
	 */
	private void insertAdditionalInformationUpdate(ProspectDetails prospectDetails, CsvLine line) throws Exception {
        Vector lineElements = line.getLineElements();
        if (getString(lineElements, 23, 9) != null && !Utilities.nullToBlank(getString(lineElements, 23, 9)).equals("")) {//X
        	addProspectCampaign(lineElements, prospectDetails.getProspectId());
        }
        if (getString(lineElements, 24, 9) != null && !Utilities.nullToBlank(getString(lineElements, 24, 9)).equals("")) {//Y (actionid)& //z (note)
        	addProspectSalesAction(lineElements, prospectDetails.getProspectId(), getString(lineElements, 25, 200));
        }
        if (getString(lineElements, 26, 2000) != null && !Utilities.nullToBlank(getString(lineElements, 26, 2000)).equals("")) {//AA note
        	addProspectNote(getString(lineElements, 26, 2000), prospectDetails.getProspectId());
        }
		
	}
	
	private void insertAdditionalInformation(ProspectDetails prospectDetails, CsvLine line) throws Exception {
        Vector lineElements = line.getLineElements();
        if (getString(lineElements, 23, 9) != null && !Utilities.nullToBlank(getString(lineElements, 23, 9)).equals("")) {//X
        	addProspectCampaign(lineElements, prospectDetails.getProspectId());
        }
        if (getString(lineElements, 24, 9) != null && !Utilities.nullToBlank(getString(lineElements, 24, 9)).equals("")) {//Y (actionid)& //z (note)
        	addProspectSalesAction(lineElements, prospectDetails.getProspectId(), getString(lineElements, 25, 200));
        }
        if (getString(lineElements, 26, 2000) != null && !Utilities.nullToBlank(getString(lineElements, 26, 2000)).equals("")) {//AA note
        	addProspectNote(getString(lineElements, 26, 2000), prospectDetails.getProspectId());
        }
        if (prospectDetails.getExternalId() !=null){//AB external id
        	addProspectDocuments(prospectDetails);
        } 
        
        if(importIsUshud(lineElements)){
        	addDefaultSalesAction(prospectDetails.getProspectId());
        }
        
        if(importIsRoundRobinEnabled(lineElements)){
        	addDefaultRoundRobinSalesAction(prospectDetails.getProspectId());
        }
		
	}

	private void addDefaultRoundRobinSalesAction(BigDecimal prospectId) throws Exception {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		//Date date = sdf.parse(DateUtilities.getCurrentSQLTimestamp().toString());

		BigDecimal salesActionId;
		if(roundRobinSalesActionDefined()){
			salesActionId = new BigDecimal(AppSettings.getParm("ROUND_ROBIN_SALES_ACTION_ID"));
		}else{
			salesActionId = new BigDecimal(2);
		}
//		System.out.println("salesActionId = " + salesActionId);
		String salesActionNote;
		if(roundRobinSalesActionNoteDefined()){
			salesActionNote = AppSettings.getParm("ROUND_ROBIN_SALES_ACTION_NOTE");
		}else{
			salesActionNote = "USHUD: Round Robin import.";
		}
		
//		System.out.println("salesActionNote = " + salesActionNote);
		ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
		prospectSalesActionDetails.setProspectId(prospectId);
		prospectSalesActionDetails.setSalesActionId(salesActionId);

//		System.out.println("actionDate = " + actionDate);
		prospectSalesActionDetails.setActionDate(DateUtilities.getCurrentSQLTimestamp());
//		System.out.println("action date = " + prospectSalesActionDetails.getActionDate());
		prospectSalesActionDetails.setActionNotificationflag(new BigDecimal(0));

		prospectSalesActionDetails.setActionNote(salesActionNote);
		prospectSalesActionDetails.setActionStatus(new BigDecimal(0));
		prospectSalesActionDetails.setActionPriority(new BigDecimal(1));
		ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
		prospectSalesActionView.doAction(connection, "INSERT", ProspectSalesActionDetails.FILL_TYPE_ALL, prospectSalesActionDetails);
	
	}
	
	private void addDefaultSalesAction(BigDecimal prospectId) throws Exception {
		////SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		//Date date = sdf.parse(DateUtilities.getCurrentSQLTimestamp().toString());

		BigDecimal salesActionId;
		if(roundRobinSalesActionDefined()){
			salesActionId = new BigDecimal(AppSettings.getParm("USHUD_ROUND_ROBIN_SALES_ACTION_ID"));
		}else{
			salesActionId = new BigDecimal(2);
		}
//		System.out.println("salesActionId = " + salesActionId);
		String salesActionNote;
		if(ushudRoundRobinSalesActionNoteDefined()){
			salesActionNote = AppSettings.getParm("USHUD_ROUND_ROBIN_SALES_ACTION_NOTE");
		}else{
			salesActionNote = "USHUD: Round Robin import.";
		}
		
//		System.out.println("salesActionNote = " + salesActionNote);
		ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
		prospectSalesActionDetails.setProspectId(prospectId);
		prospectSalesActionDetails.setSalesActionId(salesActionId);

//		System.out.println("actionDate = " + actionDate);
		prospectSalesActionDetails.setActionDate(DateUtilities.getCurrentSQLTimestamp());
//		System.out.println("action date = " + prospectSalesActionDetails.getActionDate());
		prospectSalesActionDetails.setActionNotificationflag(new BigDecimal(0));

		prospectSalesActionDetails.setActionNote(salesActionNote);
		prospectSalesActionDetails.setActionStatus(new BigDecimal(0));
		prospectSalesActionDetails.setActionPriority(new BigDecimal(1));
		ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
		prospectSalesActionView.doAction(connection, "INSERT", ProspectSalesActionDetails.FILL_TYPE_ALL, prospectSalesActionDetails);
	
	}

	private boolean ushudRoundRobinSalesActionNoteDefined() {
//		System.out.println("\n\nGET NOTE???  = " + AppSettings.getParm("ROUND_ROBIN_SALES_ACTION_NOTE"));
		if(AppSettings.getParm("USHUD_ROUND_ROBIN_SALES_ACTION_NOTE")!= null &&
				!AppSettings.getParm("USHUD_ROUND_ROBIN_SALES_ACTION_NOTE").equals("")){
			return true;
		}
		return false;
	}

	private boolean roundRobinSalesActionNoteDefined() {
//		System.out.println("\n\nGET NOTE???  = " + AppSettings.getParm("ROUND_ROBIN_SALES_ACTION_NOTE"));
		if(AppSettings.getParm("USHUD_ROUND_ROBIN_SALES_ACTION_NOTE")!= null &&
				!AppSettings.getParm("USHUD_ROUND_ROBIN_SALES_ACTION_NOTE").equals("")){
			return true;
		}
		return false;
	}
	
	private boolean roundRobinSalesActionDefined() {
//		System.out.println("\n\nGET ACTION???  = " + AppSettings.getParm("ROUND_ROBIN_SALES_ACTION_ID"));
		
		if(AppSettings.getParm("ROUND_ROBIN_SALES_ACTION_ID")!= null &&
				!AppSettings.getParm("ROUND_ROBIN_SALES_ACTION_ID").equals("")){
			return true;
		}
		return false;
	}

	/**
	 * @param lineElements
	 * @return
	 * @throws Exception
	 * This checks to see if the prospect already exists, if so , then we do an update, not an insert
	 * check 1)external ID, 2) name and title, 3) email, 4) phone number plus company, 5) email and phone
	 */
	private boolean prospectDoesNotExist(Vector lineElements) throws Exception {		
		if(((ProspectImportParameters) this.importParameters).isEliminateDups()){//yes, we should elminate dups
			if(externalIdExists(lineElements) || contactNameExists(lineElements) || emailExists(lineElements) || phoneNumberExists(lineElements)){
				return false;  // update
			}else{
				//does not exist - do insert
				return true; // do insert 
			}	
		}else{
			//does not exist - do insert
			return true; // do insert cuz elminate dups is false
		}	
	}

	private boolean phoneNumberExists(Vector lineElements) throws Exception { // checking if this is a duplicate based on phone# and customerCompany
		if(Utilities.nullToBlank(getString(lineElements, 8, 30)).equals("")){			
			return false;
		}
		ProspectDetails searchProspectDetails = new ProspectDetails();
		ProspectView searchProspectView = new ProspectView();
		
		//temporary change adding customer company
		searchProspectDetails.setCustomerCompany(getString(lineElements, 0, 100));//getString(lineElements, 0, 100));//A
		searchProspectDetails.setPhone(getString(lineElements, 8, 30));//getString(lineElements, 8, 30);//I
		
		searchProspectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, searchProspectDetails);

		if(searchProspectView.getElements().size() > 0){			
			prospectDetailsForUpdate = (ProspectDetails)searchProspectView.getElement(0);
			return true;// this is a dup			
		}else{	
			return false;
		}
	}
	
	private boolean emailAndPhoneExists(Vector lineElements) throws Exception {// checking if this is a dup based on eamil only
		
		if(Utilities.nullToBlank(getString(lineElements, 18, 100)).equals("")){			
			return false;
		}

		ProspectDetails searchProspectDetails = new ProspectDetails();
		ProspectView searchProspectView = new ProspectView();
		//temporary
		searchProspectDetails.setPhone(getString(lineElements, 8, 30));//getString(lineElements, 8, 30);//I
		searchProspectDetails.setEmail(getString(lineElements, 18, 100));//getString(lineElements, 18, 100));//S	
		
		searchProspectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, searchProspectDetails);	
		
		if(searchProspectView.getElements().size() > 0){			
			prospectDetailsForUpdate = (ProspectDetails)searchProspectView.getElement(0);
			
			return true;// this is a dup			
		}else{	
			return false;
		}
	}

	private boolean emailExists(Vector lineElements) throws Exception {// checking if this is a dup based on eamil only
	
		if(Utilities.nullToBlank(getString(lineElements, 18, 100)).equals("")){			
			return false;
		}

		ProspectDetails searchProspectDetails = new ProspectDetails();
		ProspectView searchProspectView = new ProspectView();
		//temporary
		searchProspectDetails.setCustomerCompany(getString(lineElements, 0, 100));//getString(lineElements, 0, 100));//A
		searchProspectDetails.setEmail(getString(lineElements, 18, 100));//getString(lineElements, 18, 100));//S	
		
		searchProspectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, searchProspectDetails);	
		
		if(searchProspectView.getElements().size() > 0){			
			prospectDetailsForUpdate = (ProspectDetails)searchProspectView.getElement(0);
			
			return true;// this is a dup			
		}else{	
			return false;
		}
	}

	private boolean contactNameExists(Vector lineElements) throws Exception {
		if(Utilities.nullToBlank(getString(lineElements, 1, 30)).equals("") || 
				Utilities.nullToBlank(getString(lineElements, 10,100)).equals("")){			
			return false;
		}
		
		ProspectDetails searchProspectDetails = new ProspectDetails();
		ProspectView searchProspectView = new ProspectView();
		
		searchProspectDetails.setCustomerCompany(getString(lineElements, 0, 100));//getString(lineElements, 0, 100));//A
		searchProspectDetails.setContactName(getString(lineElements, 1, 30));//getString(lineElements, 1, 30));//B
		searchProspectDetails.setContactTitle(getString(lineElements, 10,100));//K);
		
		searchProspectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, searchProspectDetails);
		
		if(searchProspectView.getElements().size() > 0){			
			prospectDetailsForUpdate = (ProspectDetails)searchProspectView.getElement(0);
			return true;//this is a dup			
		}else{			
			return false;
		}

	}
	
	private boolean externalIdExists(Vector lineElements) throws Exception {
		if(Utilities.nullToBlank(getString(lineElements, 27, 25)).equals("") ){			
			return false;
		}
		//System.out.println("== ExternalId" + getString(lineElements, 27, 25));
		ProspectDetails searchProspectDetails = new ProspectDetails();
		ProspectView searchProspectView = new ProspectView();
		searchProspectDetails.setExternalId(getString(lineElements, 27, 25));//AA external ID 
		searchProspectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, searchProspectDetails);
		
		if(searchProspectView.getElements().size() > 0){			
			prospectDetailsForUpdate = (ProspectDetails)searchProspectView.getElement(0);
			return true;//this is a dup			
		}else{			
			return false;
		}

	}

	/**
	 * @param decimal
	 * @param prospectId
	 * if a campaign was included in the import, then add it and all its sales actions
	 * @throws Exception 
	 */
	private void addProspectCampaign(Vector lineElements, BigDecimal prospectId) throws Exception {
		//set ProspectCampaignDetails
		ProspectCampaignDetails prospectCampaignDetails = new ProspectCampaignDetails();
		ProspectCampaignView prospectCampaignView = new ProspectCampaignView();
		prospectCampaignDetails.setCampaignId(new BigDecimal(getString(lineElements, 23, 9)));
		prospectCampaignDetails.setProspectId(prospectId);
    	prospectCampaignDetails.setCreationDate(new Timestamp(System.currentTimeMillis()));

		prospectCampaignView.doAction(connection, "INSERT", new BigDecimal(1), prospectCampaignDetails);
	
    	prospectCampaignDetails = (ProspectCampaignDetails)prospectCampaignView.getCurrent();

    	// insert ProspectSalesActions. inserts dates into prospectSalesActions, 
    	ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
    	
    	CampaignSalesActionView campaignSalesActionView = getCampaignSalesActionView(new BigDecimal(1));
    	for(int i = 0; i < campaignSalesActionView.getElements().size(); i++) {
    		
    		CampaignSalesActionDetails campaignSalesActionDetails = (CampaignSalesActionDetails)campaignSalesActionView.getElement(i);
    		ProspectSalesActionDetails prospectSalesActionDetails = getProspectSalesActionDetails(lineElements, prospectId, prospectCampaignDetails, campaignSalesActionDetails);
//    		System.out.println("prospectSalesActionDetails being inserted - " + prospectSalesActionDetails.toString());
    		prospectSalesActionView.doAction(connection, "INSERT", new BigDecimal(1), prospectSalesActionDetails);
    	}
		
	}

    private CampaignSalesActionView getCampaignSalesActionView(BigDecimal campaignId) throws Exception{
    	CampaignSalesActionView campaignSalesActionView = new CampaignSalesActionView();
    	CampaignSalesActionDetails campaignSalesActionDetails = new CampaignSalesActionDetails();
    	campaignSalesActionDetails.setCampaignId(campaignId);
    	campaignSalesActionView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, campaignSalesActionDetails);
    	return campaignSalesActionView;
    }
    
    private ProspectSalesActionDetails getProspectSalesActionDetails(Vector lineElements, BigDecimal prospectId, ProspectCampaignDetails prospectCampaignDetails, CampaignSalesActionDetails campaignSalesActionDetails) throws Exception {
    	
    	SalesActionDetails searchSalesActionDetails = new SalesActionDetails();
    	SalesActionView salesActionView = new SalesActionView();
    	searchSalesActionDetails.setActionId(campaignSalesActionDetails.getSalesActionId());
    	salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, searchSalesActionDetails);
    	
    	SalesActionDetails salesActionDetails = (SalesActionDetails)salesActionView.getElement(0);
    	
    	ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
    	
    	if(salesActionDetails.getMandatoryDate().equals(SalesActionDetails.REQUIRES_MANDATORY_DATE)){
    		prospectSalesActionDetails.setActionDate(new Timestamp(System.currentTimeMillis()));
    		prospectSalesActionDetails.setActionNote("");
    	}

    	prospectSalesActionDetails.setProspectId(prospectId);
    	prospectSalesActionDetails.setSalesActionId(campaignSalesActionDetails.getSalesActionId());
    	prospectSalesActionDetails.setProspectCampaignSequence(campaignSalesActionDetails.getDisplaySequence());
    	prospectSalesActionDetails.setProspectCampaignId(prospectCampaignDetails.getProspectCampaignId());
    	prospectSalesActionDetails.setActionPriority(SalesActionDetails.TOP_PRIORITY);

    	prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_ACTIVE);
    	prospectSalesActionDetails.setActionNotificationflag(SalesActionDetails.HASNT_BEEN_NOTIFIED);
    	
    	int position = campaignSalesActionDetails.getDisplaySequence().intValue();
        
        prospectSalesActionDetails.setMandatoryDate(campaignSalesActionDetails.getSalesActionMandatoryDate());
    	
    	return prospectSalesActionDetails;  	
    }

	/**
	 * @param salesActionId
	 * @param prospectId
	 * @param salesActionNote
	 * @throws Exception
	 * if a sales action was included in the import, then add it
	 */
	private void addProspectSalesAction(Vector lineElements, BigDecimal prospectId, String salesActionNote) throws Exception{
		ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
		prospectSalesActionDetails.setProspectId(prospectId);
		prospectSalesActionDetails.setSalesActionId((getSalesActionid(getString(lineElements, 24, 9))));
		if (prospectSalesActionDetails.getSalesActionId()!=null){
			Timestamp actionDate = DateUtilities.getCurrentSQLTimestamp();//trap for no sales action date
			if(getString(lineElements, 28, 10)!=null){
				String thisDate = getString(lineElements, 28, 10).trim();
	        	actionDate = (DateUtilities.getDateValue(thisDate));//AC sales action date
				
			}
			prospectSalesActionDetails.setActionDate(actionDate);
	//		System.out.println("action date = " + prospectSalesActionDetails.getActionDate());
			prospectSalesActionDetails.setActionNotificationflag(new BigDecimal(0));
			if(salesActionNote!=null){
				prospectSalesActionDetails.setActionNote(salesActionNote);
			}
			prospectSalesActionDetails.setActionStatus(new BigDecimal(0));
			prospectSalesActionDetails.setActionPriority(new BigDecimal(1));
			ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
			prospectSalesActionView.doAction(connection, "INSERT", ProspectSalesActionDetails.FILL_TYPE_ALL, prospectSalesActionDetails);
		}
	}

	/**
	 * @param string
	 * @return
	 * sales action could be the description or the id
	 */
	private BigDecimal getSalesActionid(String strSalesActionId) throws Exception{
		//System.out.println("SalesActionid from import= "+ strSalesActionId);
		BigDecimal resultId = null;
        if (strSalesActionId != null && strSalesActionId.trim().length() > 0) {
            SalesActionDetails salesActionDetails = new SalesActionDetails();
            salesActionDetails.setAction(strSalesActionId);
            SalesActionView salesActionView = new SalesActionView();

            salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);
            
            if (salesActionView.getElements().size() >= 1) {
            	salesActionDetails = (SalesActionDetails) salesActionView.getElements().elementAt(0);
                resultId = salesActionDetails.getActionId();
            } else if(Utilities.isNumber(strSalesActionId)){
            	
            	salesActionDetails = new SalesActionDetails();
            	salesActionDetails.setActionId(new BigDecimal(strSalesActionId));

            	salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);

                if (salesActionView.getElements().size() >= 1) {
                	salesActionDetails = (SalesActionDetails) salesActionView.getElements().elementAt(0);
                    resultId = salesActionDetails.getActionId();
                }
            }else{// return the first sales action id
            	//we will drop the sales action note if not valid
                
            }
        }
        //System.out.println("SalesActionid being used= "+ resultId);
        return resultId;
	}

	/**
	 * @param note
	 * @param prospectId
	 * @throws Exception
	 * if a note was included in the import, then add it
	 */
	private void addProspectNote(String note, BigDecimal prospectId) throws Exception{
        NoteDetails noteDetails = new NoteDetails();
        noteDetails.setNoteDate(DateUtilities.getCurrentSQLTimestamp());
        noteDetails.setProspectId(prospectId);
        noteDetails.setUserName(UserDetails.DATA_IMPORT);
        noteDetails.setNote(note.trim());//AA
        NoteView noteView = new NoteView();
        noteView.doAction(connection, "INSERT", NoteDetails.FILL_TYPE_ALL, noteDetails);	
	}
	
	/**
	 * @param prospectDetails
	 * @throws Exception
	 * if there are any documents with the same external id as this prospect, then link them
	 */
	private void addProspectDocuments(ProspectDetails prospectDetails) throws Exception{
		DocumentDetails documentDetails = new DocumentDetails();
		documentDetails.setExternalId(prospectDetails.getExternalId().trim());
		DocumentView documentView = new DocumentView();
		documentView.fillWithElements(connection, DocumentDetails.FILL_TYPE_ALL, documentDetails);
		if(documentView.getElements().size()>0){
			for(int i = 0;i<documentView.getElements().size();i++){
				documentDetails = (DocumentDetails)documentView.getElement(i);
				ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();
				prospectDocumentDetails.setProspectId(prospectDetails.getProspectId());
				prospectDocumentDetails.setDocumentId(documentDetails.getDocumentId());
				ProspectDocumentView prospectDocumentView = new ProspectDocumentView();
				prospectDocumentView.doAction(connection, "INSERT", ProspectDocumentDetails.FILL_TYPE_ALL, prospectDocumentDetails);	
			}
		}
	}

	/**
     * Method getProspectDetails.
     * @param lineElements Vector
	 * @param prospectDetailsForUpdate 
     * @return ProspectDetails
     * @throws Exception
     */
    protected ProspectDetails getProspectDetails(Vector lineElements, ProspectDetails prospectDetails) throws Exception {

        if(((ProspectImportParameters) this.importParameters).getCompanyNumber()!= null){//get company from selection on import screen
        	prospectDetails.setCompanyId(((ProspectImportParameters) this.importParameters).getCompanyNumber());
        }else if(getString(lineElements, 22, 5) != null && getString(lineElements, 22, 5).compareToIgnoreCase("")!=0){//get company from import file
        	prospectDetails.setCompanyId(Utilities.getBigDecimal(getString(lineElements, 22, 5)));//W
        }else{
        	prospectDetails.setCompanyId(new BigDecimal(1));//else default company to 1
        }
        
        if(getString(lineElements, 0, 100) != null){
        	prospectDetails.setCustomerCompany(getString(lineElements, 0, 100));//A
        }
        
        if(getString(lineElements, 1, 30) != null){
        	prospectDetails.setContactName(getString(lineElements, 1, 30));//B
        }
        
        if(getString(lineElements, 2, 50) != null){
        	prospectDetails.setAddress(getString(lineElements, 2, 50));//C
        }
        
        if(getString(lineElements, 3, 50) != null){
        	prospectDetails.setAddress2(getString(lineElements, 3, 50));//D
        }
        
        if(getString(lineElements, 4, 30) != null){
        	 prospectDetails.setCity(getString(lineElements, 4, 30));//E
        }
        
        if(getString(lineElements, 21, 250) != null){
        	prospectDetails.setCountryId(getCountryId(getString(lineElements, 21, 250)));//V
        }else{
        	prospectDetails.setCountryId(new BigDecimal(1));
        }
        
        if(getString(lineElements, 5, 30) != null){
        	 prospectDetails.setStateId(getStateId(getString(lineElements, 5, 30), prospectDetails.getCountryId()));//F
        }
        
        if(getString(lineElements, 6, 15) != null){
        	 prospectDetails.setZip(getString(lineElements, 6, 15));//G
        }
        
        if(getString(lineElements, 7, 50) != null){
        	prospectDetails.setStatusId(getStatusId(getString(lineElements, 7, 50)));//H
        }
        
        if(getString(lineElements, 8, 30) != null){
        	prospectDetails.setPhone(getString(lineElements, 8, 30));//I
        }
        
        if(getString(lineElements, 9, 30) != null){
        	prospectDetails.setFax(getString(lineElements, 9, 30));//J
        }
        
        if(getString(lineElements, 10,100) != null){
        	prospectDetails.setContactTitle(getString(lineElements, 10,100));//K
        }
        
        if(getString(lineElements, 11, 80) != null){
        	prospectDetails.setSystemTypeId(getSystemTypeId(getString(lineElements, 11, 80)));//L
        }
        
        if(getString(lineElements, 12, 100) != null){
        	 prospectDetails.setSic(getString(lineElements, 12, 100));//M User 2
        }
        
        if(getString(lineElements, 13, 50) != null){
        	prospectDetails.setSizeId(getSizeId(getString(lineElements, 13, 50)));//N
        }
        
        if(getString(lineElements, 14, 30) != null){
        	prospectDetails.setVerifiedId(getVerifiedId(getString(lineElements, 14, 30)));//O
        }

        if(getString(lineElements, 15, 80) != null){
        	 prospectDetails.setSoftwareTypeId(getSoftwareTypeId(getString(lineElements, 15, 80)));//P
        }
        
        if(prospectDetails.getLobId()== null){//only update if LOB is null
	        if(getString(lineElements, 16, 80) != null){
	        	 prospectDetails.setLobId(getLobId(getString(lineElements, 16, 80)));//Q
	        }
        }
        
        if(getString(lineElements, 17, 200) != null){
        	 prospectDetails.setWebsite(getString(lineElements, 17, 200));//R
        }
        
        if(getString(lineElements, 18, 100) != null){
        	prospectDetails.setEmail(getString(lineElements, 18, 100));//S
        }
       
        if(getString(lineElements, 19, 30) != null){
        	 prospectDetails.setCounty(getString(lineElements, 19, 30));//T
        } 

        if(prospectDetails.getTerritoryId()== null){//only update if territory is null
	        if(roundRobinEnabled() || ushudRoundRobinEnabled()){
	        	//System.out.println("round robin is enabled");
	        	if(importIsUshud(lineElements) || importIsRoundRobinEnabled(lineElements)){
		        	if(actionPerformed.compareTo(INSERT_NEW_PROSPECT) == 0){
		        		assignToRoundRobin(prospectDetails, lineElements);
		        		insertedIntoRR= true;//this controls writing to the audit RR table
		        		//System.out.println("===set insertedIntoRR to true");
		        	}	
	        	}else{
	        		if(getString(lineElements, 20, 250) != null){
	            		prospectDetails.setTerritoryId(getTerritoryId(getString(lineElements, 20, 250)));//U TERRITORY
	            	}
	        	}
	        }else{
	        	if(getString(lineElements, 20, 250) != null){
	        		prospectDetails.setTerritoryId(getTerritoryId(getString(lineElements, 20, 250)));//U TERRITORY
	        	}
	        }
        }
       
        if(actionPerformed.compareTo(INSERT_NEW_PROSPECT) == 0){
        	 prospectDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
        }else{
        	prospectDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
        }
        
        if (getString(lineElements, 27, 25) != null){
        	prospectDetails.setExternalId(Utilities.nullToBlank(getString(lineElements, 27, 25).trim()));//AB External Id
        }
        
        if (getString(lineElements, 29, 100) != null){
        	prospectDetails.setSsa(getString(lineElements, 29, 100).trim());//AD User 1
        }
        
        if (getString(lineElements, 30, 100) != null){
        	prospectDetails.setSystemNo(Utilities.nullToBlank(getString(lineElements, 30, 100)).trim());//AE User 3
        }
        
        if (getString(lineElements, 31, 100) != null){
        	prospectDetails.setHardwareMaintenance(getString(lineElements, 31, 100).trim());//AF User 4
        }
        
        if (getString(lineElements, 32, 30) != null){
        	prospectDetails.setPhoneExt(getString(lineElements, 32, 100).trim());//AG Ext/work phone
        }
        
        if (getString(lineElements, 33, 30) != null){
        	prospectDetails.setCellPhone(getString(lineElements, 33, 100).trim());//AH Cell Phone
        }
        
        return prospectDetails;
    }
    
    private boolean importIsUshud(Vector lineElements) {
//    	System.out.println("" + getString(lineElements, 20, 250));
    	String ushudLabel = "USHUD";
        if (getString(lineElements, 20, 250) != null){
        	if(getString(lineElements, 20, 250).equals(ushudLabel)){
        		
        		
        		return true;
        	}
        }
		return false;
	}

    private boolean importIsRoundRobinEnabled(Vector lineElements) {
    	String roundRobin = "RR";
    	//System.out.println("fullstring = " + "'" + getString(lineElements, 20, 250) + "'");
    	if (getString(lineElements, 20, 250) != null){
        	if(getString(lineElements, 20, 250).equals(roundRobin)){//U TERRITORY
        		
        		//System.out.println("returning true " + getString(lineElements, 20, 250).substring(2));
        		return true;
        	}
        }
       // System.out.println("false");
		return false;
	}

	private void assignToRoundRobin(ProspectDetails prospectDetails, Vector lineElements) throws Exception {

    	TerritoryDetails searchTerritoryDetails = new TerritoryDetails();
		TerritoryView searchRoundTerritoryView = new TerritoryView();// list of all territories that are included in Round Robing
		
		searchTerritoryDetails.setIncludeInRoundRobin(TerritoryDetails.INCLUDED_IN_ROUND_ROBIN);
		searchRoundTerritoryView.setOrderBy("t.territory_id");
		searchRoundTerritoryView.fillWithElements(connection, TerritoryView.FILL_TYPE_ALL, searchTerritoryDetails);
		
		TerritoryDetails territoryDetails;
//		boolean setRound = false;
		TerritoryDetails firstTerritoryToUse = (TerritoryDetails)searchRoundTerritoryView.getElement(0);
		int firstId = firstTerritoryToUse.getTerritoryId().intValue();
		int nextIdToUse = firstTerritoryToUse.getTerritoryId().intValue();
		
		int currentPosition = 0;
		int nextPosition = 0;
		//System.out.println("\n\n=========== SETTING ROUND ROBIN TERRITORY ID ============\n");
//		find which is the territory to begin with
		if(searchRoundTerritoryView.getElements().size() > 0){
			for(int i = 0; i < searchRoundTerritoryView.getElements().size(); i++){//find which is the territory to begin with
				
				territoryDetails = (TerritoryDetails)searchRoundTerritoryView.getElement(i);
				if(territoryDetails.getRoundRobinTerritorySelected()!= null &&
						territoryDetails.getRoundRobinTerritorySelected().compareTo(TerritoryDetails.SELECTED_ROUND_ROBIN)==0){
					
					nextPosition = i + 1;
					//System.out.println("currentPosition = " + currentPosition);
					//System.out.println("nextPosition = " + nextPosition + " " + searchRoundTerritoryView.getElements().size());
					
					if(nextPosition >= searchRoundTerritoryView.getElements().size()){
						nextPosition = 0;
					}
					
					TerritoryDetails nextTerritoryDetails = (TerritoryDetails)searchRoundTerritoryView.getElement(nextPosition);
					nextIdToUse = nextTerritoryDetails.getTerritoryId().intValue();
					
					territoryDetails.setRoundRobinTerritorySelected(TerritoryDetails.NOT_SELECTED_ROUND_ROBIN);//reset to 0 the selected field
					searchRoundTerritoryView.doAction(connection, "UPDATE", TerritoryView.FILL_TYPE_ALL, territoryDetails);
				}
				//System.out.println("territoryDetails " + territoryDetails.getTerritoryId());
			}
			
			//nextIdToUse is the next territory that was get assigned a prospect
			//go thru the territories and until we reach the territory to assign the prospect to.
			for(int j = 0; j < searchRoundTerritoryView.getElements().size(); j++){
				TerritoryDetails territoryDetailsToUse = (TerritoryDetails)searchRoundTerritoryView.getElement(j);
				
				if(nextIdToUse == territoryDetailsToUse.getTerritoryId().intValue()){// or should we be comparing to the nextPostition
					prospectDetails.setTerritoryId(territoryDetailsToUse.getTerritoryId());
					territoryDetailsToUse.setRoundRobinTerritorySelected(TerritoryDetails.SELECTED_ROUND_ROBIN);
				}
				
				searchRoundTerritoryView.doAction(connection, "UPDATE", TerritoryView.FILL_TYPE_ALL, territoryDetailsToUse);	
			}
			
			//System.out.println("nextIdToUse = " + nextIdToUse);
		}else{
			prospectDetails.setTerritoryId(getTerritoryId(getString(lineElements, 20, 250)));//U TERRITORY   -use the imported territory	
		}
		
//		System.out.println("\n\n");
    }


	private boolean ushudRoundRobinEnabled() {
		if(AppSettings.getParm("USHUD_ROUND_ROBIN_ENABLED").equalsIgnoreCase("YES")||
				AppSettings.getParm("USHUD_ROUND_ROBIN_ENABLED").equalsIgnoreCase("TRUE")){
			return true;
		}
		return false;
	}

	private boolean roundRobinEnabled() {
		if(AppSettings.getParm("ROUND_ROBIN_ENABLED").equalsIgnoreCase("YES")||
				AppSettings.getParm("ROUND_ROBIN_ENABLED").equalsIgnoreCase("TRUE")){
			return true;
		}
		return false;
	}
	
	/**
     * Method getSystemTypeId.
     * @param strSystemType String
     * @return BigDecimal
     * @throws java.lang.Exception
     */
    private BigDecimal getSystemTypeId(String strSystemType) throws java.lang.Exception {
        BigDecimal resultId = null;
        if (strSystemType != null && strSystemType.trim().length() > 0) {
            SystemTypeDetails systemTypeDetails = new SystemTypeDetails();
            systemTypeDetails.setSystemType(strSystemType);
            SystemTypeView systemTypeView = new SystemTypeView();

            systemTypeView.fillWithElements(connection, SystemTypeDetails.FILL_TYPE_ALL, systemTypeDetails);

            if (systemTypeView.getElements().size() >= 1) {
                systemTypeDetails = (SystemTypeDetails) systemTypeView.getElements().elementAt(0);
                resultId = systemTypeDetails.getSystemTypeId();
            } else if (((ProspectImportParameters) this.importParameters).isCreateSystemType()) {
                systemTypeView.doAction(connection, "INSERT", VerifiedDetails.FILL_TYPE_ALL, systemTypeDetails);
                return ((SystemTypeDetails) systemTypeView.getCurrent()).getSystemTypeId();
            }
        }
        return resultId;
    }


    /**
     * Method getSoftwareTypeId.
     * @param strSoftwareType String
     * @return BigDecimal
     * @throws java.lang.Exception
     */
    private BigDecimal getSoftwareTypeId(String strSoftwareType) throws java.lang.Exception {
        BigDecimal resultId = null;
        if (strSoftwareType != null && strSoftwareType.trim().length() > 0) {
            SoftwareTypeDetails softwareTypeDetails = new SoftwareTypeDetails();
            softwareTypeDetails.setSoftwareType(strSoftwareType);
            SoftwareTypeView softwareTypeView = new SoftwareTypeView();

            softwareTypeView.fillWithElements(connection, SoftwareTypeDetails.FILL_TYPE_ALL, softwareTypeDetails);
            if (softwareTypeView.getElements().size() >= 1) {
                softwareTypeDetails = (SoftwareTypeDetails) softwareTypeView.getElements().elementAt(0);
                resultId = softwareTypeDetails.getSoftwareTypeId();
            } else if (((ProspectImportParameters) this.importParameters).isCreateSoftwareType()) {
                softwareTypeView.doAction(connection, "INSERT", VerifiedDetails.FILL_TYPE_ALL, softwareTypeDetails);
                return ((SoftwareTypeDetails) softwareTypeView.getCurrent()).getSoftwareTypeId();
            }
        }
        return resultId;
    }

    /**
     * Method getLobId.
     * @param strLob String
     * @return BigDecimal
     * @throws java.lang.Exception
     */
    private BigDecimal getLobId(String strLob) throws java.lang.Exception {
        BigDecimal resultId = null;
        if (strLob != null && strLob.trim().length() > 0) {
            LobDetails lobDetails = new LobDetails();
            lobDetails.setLob(strLob);
            LobView lobView = new LobView();

            lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, lobDetails);
            
            if (lobView.getElements().size() >= 1) {
            	lobDetails = (LobDetails) lobView.getElements().elementAt(0);
                resultId = lobDetails.getLobId();
            } else if(Utilities.isNumber(strLob)){
            	
            	lobDetails = new LobDetails();
            	lobDetails.setLobId(new BigDecimal(strLob));

            	lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, lobDetails);

                if (lobView.getElements().size() >= 1) {
                	lobDetails = (LobDetails) lobView.getElements().elementAt(0);
                    resultId = lobDetails.getLobId();
                } else if (((ProspectImportParameters) this.importParameters).isCreateLob()) {
                	lobDetails.setLob(strLob);
                	lobView.doAction(connection, "INSERT", VerifiedDetails.FILL_TYPE_ALL, lobDetails);
                    return ((LobDetails) lobView.getCurrent()).getLobId();
                }
            }else if(((ProspectImportParameters) this.importParameters).isCreateLob()) {
            	lobDetails.setLob(strLob);
            	lobView.doAction(connection, "INSERT", VerifiedDetails.FILL_TYPE_ALL, lobDetails);
                return ((LobDetails) lobView.getCurrent()).getLobId();
            }
        }
        return resultId;
    }

    /**
     * Method getVerifiedId.
     *
     * @param strVerified
     *
     * @return BigDecimal
     * @throws java.lang.Exception
     */

    private BigDecimal getVerifiedId(String strVerified) throws java.lang.Exception {
        BigDecimal resultId = null;
        if (strVerified != null && strVerified.trim().length() > 0) {
            VerifiedDetails verifiedDetails = new VerifiedDetails();
            verifiedDetails.setVerified(strVerified);

            VerifiedView verifiedView = new VerifiedView();
            verifiedView.fillWithElements(connection, VerifiedDetails.FILL_TYPE_ALL, verifiedDetails);

            if (verifiedView.getElements().size() >= 1) {
                verifiedDetails = (VerifiedDetails) verifiedView.getElements().elementAt(0);
                resultId = verifiedDetails.getVerifiedId();
            } else if (((ProspectImportParameters) this.importParameters).isCreateVerified()) {
                verifiedView.doAction(connection, "INSERT", VerifiedDetails.FILL_TYPE_ALL, verifiedDetails);
                return ((VerifiedDetails) verifiedView.getCurrent()).getVerifiedId();
            }
        }
        return resultId;
    }

    /**
     * Method getSizeId.
     * @param strSize String
     * @return BigDecimal
     * @throws java.lang.Exception
     */
    private BigDecimal getSizeId(String strSize) throws java.lang.Exception {
        BigDecimal resultId = null;
        if (strSize != null && strSize.trim().length() > 0) {
            SizeDetails sizeDetails = new SizeDetails();
            sizeDetails.setSize(strSize);

            SizeView sizeView = new SizeView();
            sizeView.fillWithElements(connection, SizeDetails.FILL_TYPE_ALL, sizeDetails);

            if (sizeView.getElements().size() >= 1) {
                sizeDetails = (SizeDetails) sizeView.getElements().elementAt(0);
                resultId = sizeDetails.getSizeId();
            } else if (((ProspectImportParameters) this.importParameters).isCreateSize()) {
                sizeView.doAction(connection, "INSERT", SizeDetails.FILL_TYPE_ALL, sizeDetails);
                return ((SizeDetails) sizeView.getCurrent()).getSizeId();
            }
        }
        return resultId;
    }

    /**
     * Method getStatusId.
     * @param strStatus String
     * @return BigDecimal
     * @throws java.lang.Exception
     */
    private BigDecimal getStatusId(String strStatus) throws java.lang.Exception {
        BigDecimal resultId = null;
        if (strStatus != null && strStatus.trim().length() > 0) {
            StatusDetails statusDetails = new StatusDetails();
            statusDetails.setStatus(strStatus);
            StatusView statusView = new StatusView();

            statusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, statusDetails);

            if (statusView.getElements().size() >= 1) {
                statusDetails = (StatusDetails) statusView.getElements().elementAt(0);
                resultId = statusDetails.getStatusId();
            } else if (((ProspectImportParameters) this.importParameters).isCreateStatus()) {
                statusView.doAction(connection, "INSERT", VerifiedDetails.FILL_TYPE_ALL, statusDetails);
                return ((StatusDetails) statusView.getCurrent()).getStatusId();
            }else if (Utilities.isNumber(strStatus)){//check if they entered a valid status id 4/25/09 
           	 StatusDetails status2Details = new StatusDetails();
             status2Details.setStatusId(new BigDecimal(strStatus.trim()));
             StatusView status2View = new StatusView();
             status2View.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, status2Details);
             if (status2View.getElements().size() > 0) {
            	 status2Details = (StatusDetails)status2View.getElement(0);
            	 resultId = status2Details.getStatusId();
             }
            }
        }
        return resultId;
    }

    /**
     * Method getStateId.
     * @param strState String
     * @param countryId BigDecimal
     * @return BigDecimal
     * @throws java.lang.Exception
     */
    private BigDecimal getStateId(String strState, BigDecimal countryId) throws java.lang.Exception {
        BigDecimal resultId = null;
        if (strState != null && strState.trim().length() > 0) {
            StateDetails stateDetails = new StateDetails();
            stateDetails.setState(strState);
            stateDetails.setCountryId(countryId);
            StateView stateView = new StateView();

            stateView.fillWithElements(connection, StateDetails.FILL_TYPE_ALL, stateDetails);
            if (stateView.getElements().size() >= 1) {
                stateDetails = (StateDetails) stateView.getElements().elementAt(0);
                resultId = stateDetails.getStateId();
            } else {
                stateDetails = new StateDetails();
                stateDetails.setStateCode(Utilities.truncate(strState, 2));
                stateDetails.setCountryId(countryId);
                stateView.fillWithElements(connection, StateDetails.FILL_TYPE_ALL, stateDetails);

                if (stateView.getElements().size() >= 1) {
                    stateDetails = (StateDetails) stateView.getElements().elementAt(0);
                    resultId = stateDetails.getStateId();
                } else if (((ProspectImportParameters) this.importParameters).isCreateState()) {
                    stateDetails.setState(strState);
                    stateDetails.setStateCode(Utilities.truncate(strState.toUpperCase(), 2));
                    stateDetails.setCountryId(countryId);
                    stateView.doAction(connection, "INSERT", VerifiedDetails.FILL_TYPE_ALL, stateDetails);
                    return ((StateDetails) stateView.getCurrent()).getStateId();
                }
            }
        }
        return resultId;
    }

    /**
     * Method getTerritoryId.
     * @param strTerritory String
     * @return BigDecimal
     * @throws java.lang.Exception
     * will accept either territory id or territory name
     */
    private BigDecimal getTerritoryId(String strTerritory) throws java.lang.Exception {
        BigDecimal resultId = null;
        
        if (strTerritory != null
            && strTerritory.trim().length() > 0) {
            TerritoryDetails territoryDetails = new TerritoryDetails();
            territoryDetails.setTerritory(strTerritory);
            TerritoryView territoryView = new TerritoryView();

            territoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, territoryDetails);
            if (territoryView.getElements().size() >= 1) {//check if they entered a valid territory name
                territoryDetails = (TerritoryDetails) territoryView.getElements().elementAt(0);
                resultId = territoryDetails.getTerritoryId();
            } else if (((ProspectImportParameters) this.importParameters).isCreateTerritory()) {//check if we are supposed to add the territory
                territoryView.doAction(connection, "INSERT", VerifiedDetails.FILL_TYPE_ALL, territoryDetails);
                return ((TerritoryDetails) territoryView.getCurrent()).getTerritoryId();
            }else if(Utilities.isNumber(strTerritory)){//check if they entered a territory id
            	 TerritoryDetails territory2Details = new TerritoryDetails();
                 territory2Details.setTerritoryId(new BigDecimal(strTerritory.trim()));
                 TerritoryView territory2View = new TerritoryView();
                 territory2View.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, territory2Details);
                 if (territory2View.getElements().size() > 0) {
                	 territory2Details = (TerritoryDetails)territory2View.getElement(0);
                	 resultId = territory2Details.getTerritoryId();
                 }
            }
        }
        return resultId;
    }

    /**
     * Method getCountryId.
     * @param strCountry String
     * @return BigDecimal
     * @throws java.lang.Exception
     */
    private BigDecimal getCountryId(String strCountry) throws java.lang.Exception {
        BigDecimal resultId = null;
        if (strCountry != null && strCountry.trim().length() > 0) {
            CountryDetails countryDetails = new CountryDetails();
            countryDetails.setCountry(strCountry);
            CountryView countryView = new CountryView();

            countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, countryDetails);
            if (countryView.getElements().size() >= 1) {
                countryDetails = (CountryDetails) countryView.getElements().elementAt(0);
                resultId = countryDetails.getCountryId();
            } else if (((ProspectImportParameters) this.importParameters).isCreateCountry()) {
                countryDetails.setCountry(strCountry);
                String strCountryCode = Utilities.truncate(strCountry, 2);
                try {
                    int pos = strCountry.indexOf(" ");
                    if (pos != -1) {
                        strCountryCode = strCountryCode.substring(0, 1) + strCountryCode.substring(pos + 1);
                    }
                } catch (Exception e) {
                	e.printStackTrace();
                }
                countryDetails.setCountryCode(strCountryCode.toUpperCase());
                countryView.doAction(connection, "INSERT", VerifiedDetails.FILL_TYPE_ALL, countryDetails);
                return ((CountryDetails) countryView.getCurrent()).getCountryId();
            }
        }
        return resultId;
    }

	/**
	 * @return the insertedIntoRR
	 */
	public boolean isInsertedIntoRR() {
		return insertedIntoRR;
	}

	/**
	 * @param insertedIntoRR the insertedIntoRR to set
	 */
	public void setInsertedIntoRR(boolean insertedIntoRR) {
		this.insertedIntoRR = insertedIntoRR;
	}

	private void writeProspectLog(String externalId, String email, String phone, String action) {
		try{
    	ProspectImportLogDetails prospectImportLogDetails = new ProspectImportLogDetails();
    	prospectImportLogDetails.setActionTaken(action);
		prospectImportLogDetails.setProspectImportTimestamp(new Timestamp(System.currentTimeMillis()));
		prospectImportLogDetails.setExternalId(externalId);
		prospectImportLogDetails.setEmail(email);
		prospectImportLogDetails.setPhone(phone);
		logResult(prospectImportLogDetails);
		}catch(Exception e) {
			e.printStackTrace();
		}

	} 
	
	  private void logResult(ProspectImportLogDetails prospectImportLogDetails) throws SQLException{
		    StringBuffer sb = new StringBuffer();
			Statement statement = connection.createStatement();
			sb.append("insert into prospect_import_log (action_taken, external_id, email," +
					"phone, prospect_import_timestamp) values ( ");
			sb.append("'" + prospectImportLogDetails.getActionTaken() + "', ");
			sb.append("'" + prospectImportLogDetails.getExternalId() + "', ");
			sb.append("'" + prospectImportLogDetails.getEmail() + "', ");
			sb.append("'" + prospectImportLogDetails.getPhone() + "', ");
			sb.append("'" + prospectImportLogDetails.getProspectImportTimestamp() + "'");
			sb.append(")");
			//System.out.println("prospectImport Log statement = " + sb.toString());		
			statement.executeUpdate(sb.toString()); 
			statement.close();		
		}
	
}


