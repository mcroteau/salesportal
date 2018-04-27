package com.randr.webdw.importData.importProcessor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.randr.webdw.commision.CommisionDetails;
import com.randr.webdw.commision.CommisionView;
import com.randr.webdw.importData.ImportDataConstants;
import com.randr.webdw.importData.importProcessorAbstract.CsvLine;
import com.randr.webdw.importData.importProcessorAbstract.ImportAbstractProcessor;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.util.DateUtilities;

/**
 */
public class OepInvoiceImportProcessor
        extends ImportAbstractProcessor
        implements ImportDataConstants {

    /**
     * Constructor for OepInvoiceImportProcessor.
     * @param csvFileName String
     * @param csvFilePath String
     * @param processedCsvFilesPath String
     * @param invalidCsvFilesPath String
     * @param errorLogFilesPath String
     * @param prospectImportParameters ProspectImportParameters
     * This is importing orders and invoices from OEP(2D) and creating and updating commission records.
     */
    public OepInvoiceImportProcessor(String csvFileName,
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
    CommisionDetails commisionDetailsForUpdate = null;
   
    
    BigDecimal actionPerformed = new BigDecimal(0);
    
    
    private static final BigDecimal UPDATE_EXISTING_COMMISSION = new BigDecimal(1);
    private static final BigDecimal INSERT_NEW_COMMISSION = new BigDecimal(2);
    
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
    	//System.out.println("processing Invoice line - 1");
    	//we have to do this for 7 salesmen
    	Set salesmenList = getSalesmen(line.getLineElements());
    	Iterator iterator = salesmenList.iterator();
    	String salesman = "";
    	String commission = "";
    	List salesmanCommission = new ArrayList();
    	//System.out.println("set = " + salesmenList);
    	while(iterator.hasNext()){
    		salesmanCommission = (List)iterator.next();// proces for all salesman linked to OEP transactions
    		salesman = (String)salesmanCommission.get(0);
    		commission = (String)salesmanCommission.get(1);
    		//System.out.println("in while loop, salesman = " + salesman + " size of list" + salesmenList.size() + " commission= " + commission);
	    	if(commissionDoesNotExist(line.getLineElements(), salesman)){//true = insert new prospect
	    		//System.out.println("processing Invoice line - 2 new commission record ");
	    		actionPerformed = INSERT_NEW_COMMISSION;
	    		CommisionDetails commisionDetails = new CommisionDetails();
	    		//System.out.println("processing Invoice line - 2 new commission record, commissionDetails= " + commisionDetails);
	            getCommissionDetails(line.getLineElements(), commisionDetails, salesman, commission);
	            CommisionView commisionView = new CommisionView();
	            commisionView.doAction(connection, "INSERT", ProspectView.FILL_TYPE_BASIC, commisionDetails);
	    	}else{ //false = prospect exists - update the prospectDetails
	    		//System.out.println("processing Invoice line - 3 update commission record ");
	    		actionPerformed = UPDATE_EXISTING_COMMISSION;
	    		//System.out.println("processing Invoice line - 3 update commission record,commisionDetailsForUpdate= " + commisionDetailsForUpdate);
	    		getCommissionDetails(line.getLineElements(), commisionDetailsForUpdate, salesman, commission);
	    		CommisionView commisionView = new CommisionView();
	    		commisionView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, commisionDetailsForUpdate);
	    	}
    	}
    }

	private Set getSalesmen(Vector lineElements) {
		Set set= new HashSet();
		List salesmanCommission = new ArrayList();
		if(getString(lineElements, 112, 10)!=null && getString(lineElements, 112, 10).trim().compareToIgnoreCase("")!=0){//salesman 1
			//System.out.println("salesman 1 = " + getString(lineElements, 112, 10) + " size of list" + set.size());
			salesmanCommission = new ArrayList(); 
			salesmanCommission.add(getString(lineElements, 112, 10).trim());
			if(getString(lineElements, 113, 10)!=null && getString(lineElements, 113, 10).trim().compareToIgnoreCase("")!=0){
				salesmanCommission.add(getString(lineElements, 113, 10).trim());
			}else{
				salesmanCommission.add("0");
			}
			set.add(salesmanCommission);
		}
		if(getString(lineElements, 117, 10)!=null && getString(lineElements, 117, 10).trim().compareToIgnoreCase("")!=0){//salesman 2
			//System.out.println("salesman 2 = " + getString(lineElements, 117, 10) + " size of list" + set.size());
			salesmanCommission = new ArrayList(); 
			salesmanCommission.add(getString(lineElements, 117, 10).trim());
			if(getString(lineElements, 118, 10)!=null && getString(lineElements, 118, 10).trim().compareToIgnoreCase("")!=0){
				salesmanCommission.add(getString(lineElements, 118, 10).trim());
			}else{
				salesmanCommission.add("0");
			}
			set.add(salesmanCommission);
		}
		if(getString(lineElements, 122, 10)!=null && getString(lineElements, 122, 10).trim().compareToIgnoreCase("")!=0){//salesman 3
			//System.out.println("salesman 3 = " + getString(lineElements, 122, 10) + " size of list" + set.size());
			salesmanCommission = new ArrayList(); 
			salesmanCommission.add(getString(lineElements, 122, 10).trim());
			if(getString(lineElements, 123, 10)!=null && getString(lineElements, 123, 10).trim().compareToIgnoreCase("")!=0){
				salesmanCommission.add(getString(lineElements, 123, 10).trim());
			}else{
				salesmanCommission.add("0");
			}
			set.add(salesmanCommission);
		}
		if(getString(lineElements, 127, 10)!=null && getString(lineElements, 127, 10).trim().compareToIgnoreCase("")!=0){//salesman 4
			//System.out.println("salesman 4 = " + getString(lineElements, 127, 10) + " size of list" + set.size());
			salesmanCommission = new ArrayList(); 
			salesmanCommission.add(getString(lineElements, 127, 10).trim());
			if(getString(lineElements, 128, 10)!=null && getString(lineElements, 128, 10).trim().compareToIgnoreCase("")!=0){
				salesmanCommission.add(getString(lineElements, 128, 10).trim());
			}else{
				salesmanCommission.add("0");
			}
			set.add(salesmanCommission);
		}
		if(getString(lineElements, 132, 10)!=null && getString(lineElements, 132, 10).trim().compareToIgnoreCase("")!=0){//salesman 5
			//System.out.println("salesman 5 = " + getString(lineElements, 132, 10));
			set.add(getString(lineElements, 132, 10).trim());
		}
		if(getString(lineElements, 137, 10)!=null && getString(lineElements, 137, 10).trim().compareToIgnoreCase("")!=0){//salesman 6
			//System.out.println("salesman 6 = " + getString(lineElements, 137, 10));
			set.add(getString(lineElements, 137, 10).trim());
		}
		if(getString(lineElements, 142, 10)!=null && getString(lineElements, 142, 10).trim().compareToIgnoreCase("")!=0){//salesman 7
			//System.out.println("salesman 7 = " + getString(lineElements, 142, 10));
			set.add(getString(lineElements, 142, 10).trim());
		}
		
		return set;
	}

	/**
	 * @param lineElements
	 * @return
	 * @throws Exception
	 * This checks to see if the commission transaction exists. use OEP transaction id,
	 * salesman id and prospect id
	 */
	private boolean commissionDoesNotExist(Vector lineElements, String salesman) throws Exception {		
		//check if commission exists - link by OEP transactionID, salesman and prospect
		commisionDetailsForUpdate = new CommisionDetails();
		CommisionView searchCommisionView = new CommisionView();
		
		
		if(getString(lineElements, 50, 10)!= null && getString(lineElements, 50, 10).trim().compareToIgnoreCase("")!=0){
			commisionDetailsForUpdate.setProspectId(new BigDecimal(getString(lineElements, 50, 10).trim()));////prospect id
		}
		if(getString(lineElements, 0, 12)!= null && getString(lineElements, 0, 12).trim().compareToIgnoreCase("")!=0){
			commisionDetailsForUpdate.setOepTransactionid(new BigDecimal(getString(lineElements, 0, 12).trim()));//OEP transaction id
		}
		if(salesman.trim().compareToIgnoreCase("")!=0){
			commisionDetailsForUpdate.setUserId(new BigDecimal((salesman).trim()));//salesman - up to 7 salesmen per transactions
		}
		
		searchCommisionView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, commisionDetailsForUpdate);

		if(searchCommisionView.getElements().size() > 0){			
			commisionDetailsForUpdate = (CommisionDetails)searchCommisionView.getElement(0);
			return false;// this is a dup, do update			
		}else{	
			return true;//does not exist, do insert
		}
	}

	


	/**
     * Method getProspectDetails.
     * @param lineElements Vector
	 * @param prospectDetailsForUpdate 
     * @return ProspectDetails
     * @throws Exception
     */
    protected CommisionDetails getCommissionDetails(Vector lineElements, CommisionDetails commisionDetails, String salesman, String commission) throws Exception {
        if(actionPerformed.compareTo(INSERT_NEW_COMMISSION) == 0){//new records
        	//System.out.println("getCommissionDetails, insert");
        	commisionDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
        	if(salesman.trim().compareToIgnoreCase("")!=0){
        		commisionDetails.setUserId(new BigDecimal((salesman).trim()));//salesman - up to 7 salesmen per transactions
    		}
	        if (getString(lineElements, 50, 10) != null && getString(lineElements, 50, 10).trim().compareToIgnoreCase("")!=0 ){
	        	commisionDetails.setProspectId(new BigDecimal(getString(lineElements, 50, 10).trim()));//sp ProspectId
	        }
	        if (getString(lineElements, 0, 12) != null && getString(lineElements, 0, 12).trim().compareToIgnoreCase("")!=0 ){
	        	commisionDetails.setDescription("Commission for Transaction #: " +getString(lineElements, 0, 12).trim());//OEP transaction id
	        }
	        if(getString(lineElements, 0, 12).trim().compareToIgnoreCase("")!=0){
	        	commisionDetails.setOepTransactionid(new BigDecimal(getString(lineElements, 0, 12).trim()));//OEP transaction id
			}
	        if (getString(lineElements, 0, 12) != null && getString(lineElements, 0, 12).trim().compareToIgnoreCase("")!=0 ){
	        	commisionDetails.setInternalDescription("Commission for Transaction #: " +getString(lineElements, 0, 12).trim());//OEP transaction id
	        }
	        commisionDetails.setProspectName(getProspectName(commisionDetails.getProspectId()));
	        commisionDetails.setCurrencyId(getCurrencyId());
        }
        //System.out.println("getCommissionDetails, other fields, commisionDetails = " + commisionDetails);
        if (commission.trim().compareToIgnoreCase("")!=0){
        	commisionDetails.setAmount(new BigDecimal(commission));//salesman commission
        }else{
        	commisionDetails.setAmount(new BigDecimal(0));
        }
       
        if (getString(lineElements, 5, 10) != null && getString(lineElements, 5, 10).trim().compareToIgnoreCase("")!=0 ){
        	//System.out.println("order date 10  = '" + getString(lineElements, 5, 10) + "' order date 21  = " + getString(lineElements, 5, 21));
        	//date sold is date not timestamp
        	//commisionDetails.setDateSold(DateUtilities.getDateValue(getString(lineElements, 5, 10).trim()));//order date
        }
        if (getString(lineElements, 8, 10) != null && getString(lineElements, 8, 10).trim().compareToIgnoreCase("")!=0 ){
        	//invoice date is date not timestamp
        	//commisionDetails.setDateBilled(DateUtilities.getDateValue(getString(lineElements, 8, 10).trim()));//invoice date
        }
        if (getString(lineElements, 144, 10) != null && getString(lineElements, 144, 10).trim().compareToIgnoreCase("")!=0 ){
        	//System.out.println("date 1  = '" + getString(lineElements, 144, 10) + "' ");
        	commisionDetails.setDate1(DateUtilities.getDateValue(getString(lineElements, 144, 10).trim()));//date1
        	//commisionDetails.setDate1(DateUtilities.getCurrentSQLTimestamp());
        }
        if (getString(lineElements, 145, 10) != null && getString(lineElements, 145, 10).trim().compareToIgnoreCase("")!=0 ){
        	//System.out.println("date 2  = '" + getString(lineElements, 145, 10) + "' ");
        	commisionDetails.setDate3(DateUtilities.getDateValue(getString(lineElements, 145, 10).trim()));//date2
        }
        if (getString(lineElements, 146, 10) != null && getString(lineElements, 146, 10).trim().compareToIgnoreCase("")!=0 ){
        	//System.out.println("date 3  = '" + getString(lineElements, 146, 10) + "' ");
        	commisionDetails.setDate3(DateUtilities.getDateValue(getString(lineElements, 146, 10).trim()));//date3
        }
        if (getString(lineElements, 147, 10) != null && getString(lineElements, 147, 10).trim().compareToIgnoreCase("")!=0 ){
        	//System.out.println("date 4  = '" + getString(lineElements, 147, 10) + "' ");
        	commisionDetails.setDate4(DateUtilities.getDateValue(getString(lineElements, 147, 10).trim()));//date4
        }
        if (getString(lineElements, 148, 10) != null && getString(lineElements, 148, 10).trim().compareToIgnoreCase("")!=0 ){
        	//System.out.println("date 5  = '" + getString(lineElements, 148, 10) + "' ");
        	commisionDetails.setDate5(DateUtilities.getDateValue(getString(lineElements, 148, 10).trim()));//date5
        }
        if (getString(lineElements, 149, 10) != null && getString(lineElements, 149, 10).trim().compareToIgnoreCase("")!=0 ){
        	//System.out.println("yes no 1  = '" + getString(lineElements, 149, 10) + "' ");
        	commisionDetails.setYesNo1(getString(lineElements, 149, 10).trim());//Yes No 1
        }
        if (getString(lineElements, 151, 50) != null && getString(lineElements, 151, 50).trim().compareToIgnoreCase("")!=0 ){
        	//System.out.println("ddname  = '" + getString(lineElements, 151, 50) + "' ");
        	commisionDetails.setDropDown1Name(getString(lineElements, 151, 50).trim());//Drop Down 1 Name
        }
        if(getString(lineElements, 150, 10)!=null && getString(lineElements, 150, 10).trim().compareToIgnoreCase("")!=0){
        	//System.out.println("numeric 1  = '" + getString(lineElements, 150, 10) + "' ");
        	commisionDetails.setNumeric1(new BigDecimal(getString(lineElements, 150, 10).trim()));//Numeric1
		}
        if(getString(lineElements, 152, 10)!=null && getString(lineElements, 152, 10).trim().compareToIgnoreCase("")!=0){
        	//System.out.println("total units  = '" + getString(lineElements, 152, 10) + "' ");
        	commisionDetails.setTotalUnits(new BigDecimal(getString(lineElements, 152, 10).trim()));//Total Units
		}
        if(getString(lineElements, 153, 10)!=null && getString(lineElements, 153, 10).trim().compareToIgnoreCase("")!=0){
        	//System.out.println("total units closed = '" + getString(lineElements, 153, 10) + "' ");
        	commisionDetails.setTotalUnitsClosed(new BigDecimal(getString(lineElements, 153, 10).trim()));//Total Units Closed
		}
        return commisionDetails;
    }

	private BigDecimal getCurrencyId() {
		// TODO Auto-generated method stub
		return new BigDecimal(1);
	}

	private String getProspectName(BigDecimal prospectId) throws Exception{
		ProspectDetails prospectDetails = new ProspectDetails();
		ProspectView prospectView = new ProspectView();
		prospectDetails.setProspectId(prospectId);
		prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);
		if(prospectView.getElements().size()>0){
			prospectDetails = (ProspectDetails)prospectView.getElement(0);
			return prospectDetails.getCustomerCompany();
		}
		
		return "no prospect";
	}



}


