package com.randr.webdw.importData.importProcessor;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.importData.ImportDataConstants;
import com.randr.webdw.importData.importProcessorAbstract.CsvLine;
import com.randr.webdw.importData.importProcessorAbstract.ImportAbstractProcessor;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.util.DateUtilities;

/**
 */
public class EmailOptOutImportProcessor
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
     */
    public EmailOptOutImportProcessor(String csvFileName,
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

    		//System.out.println("prospId - " + prospectDetailsForUpdate.getProspectId());
    		ProspectDetails prospectDetails = new ProspectDetails();
    		actionPerformed = UPDATE_EXISTING_PROSPECT;
    		getProspectDetails(line.getLineElements(), prospectDetails);
            ProspectView prospectView = new ProspectView();
            ProspectDetails updateProspectDetails = new ProspectDetails();
            updateProspectDetails.setEmail(prospectDetails.getEmail());
            prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, updateProspectDetails);
            if(prospectView.getElements().size()>0){
            	for(int i= 0; i<prospectView.getElements().size(); i++){
            	updateProspectDetails = (ProspectDetails)prospectView.getElement(i);
            	updateProspectDetails.setOptOutDate(prospectDetails.getOptOutDate());
            	prospectView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, updateProspectDetails);
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

        if(getString(lineElements, 0, 80) != null){
        	prospectDetails.setEmail(getString(lineElements, 0, 80).trim());//0 = email
        	//System.out.println("getting elements, email= " + prospectDetails.getEmail());
        }else{
        	prospectDetails.setEmail("error, no email");
        }
        
        if(getString(lineElements, 1, 20) != null){
        	prospectDetails.setOptOutDate(DateUtilities.getCurrentSQLTimestamp());
        	//System.out.println("getting elements, optoutdate= " + prospectDetails.getOptOutDate());
        }
               
        return prospectDetails;
    }
    
  
}


