package com.randr.webdw.emailQueueAudit;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;

import com.oreilly.servlet.MultipartRequest;
import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospectDocument.ProspectDocumentDetails;
import com.randr.webdw.prospectDocument.ProspectDocumentView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserView;
import com.randr.webdw.util.Utilities;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class EmailQueueAuditController extends AbstractController {

    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	//might not even need these
    	if(formAction.equals("EXPORTED_TO_ORDERPORTAL")){
    		
    	}else if(formAction.equals("ORDERPORTAL_RECEIVED_FAILED_SEND")){
    		
    	}
    	
    }

  
}
