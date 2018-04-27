package com.randr.webdw.document;

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
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class DocumentController extends AbstractController {
    public static final String UPLOAD_FOLDER = "doc";

    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	//System.out.println("document formAction= " + formAction);
        createUploadProcessor(request, UPLOAD_DOCUMENT);
        if (this.uploadProcessor == null) {
        	if (formAction.equals("DISPLAY_INSERT")) {
        		displayInsertDocument();
        	} else if (formAction.equals("DISPLAY_UPDATE")) {
        		displayUpdateDocument();
        	} else if (formAction.equals("DISPLAY_DELETE")) {
        		displayDeleteDocument();
        	} else if (formAction.equals("DELETE")) {
                deleteDocument();	
        	}else if (formAction == null
                || formAction.equals("DISPLAY")
                || formAction.startsWith("DISPLAY_")) {
                BigDecimal documentType = null;

                if (formAction.equals("DISPLAY_DOCUMENTATION")) {
                    documentType = DocumentDetails.TYPE_DOCUMENTATION;
                } else if (formAction.equals("DISPLAY_UML")) {
                    documentType = DocumentDetails.TYPE_PICTURES;
                }else if (formAction.equals("DISPLAY_PICTURES")) {
                    documentType = DocumentDetails.TYPE_PICTURES;
                }else if (formAction.equals("DISPLAY_OTHER")) {
                    documentType = DocumentDetails.TYPE_OTHER;
                }else if (formAction.equals("DISPLAY_CONTRACTS")) {
                    documentType = DocumentDetails.TYPE_CONTRACTS;
                }
                displayDocuments(documentType);
            }
        } else {
            MultipartRequest multipartRequest = uploadProcessor.getMultipartRequest();
            if (multipartRequest.getParameter("formAction").equals("INSERT")) {
                insertDocument();
            } else if (multipartRequest.getParameter("formAction").equals("UPDATE")) {
                updateDocument();
            }
        }

    }

    /**
     * Method displayInsertDocument.
     */
    private void displayInsertDocument() {
        try {
            request.getRequestDispatcher("/jsp/public/document/displayInsertUpdateDocument.jsp").forward(request, response);
        } catch (Exception e) {

        }
    }

    /**
     * Method displayUpdateDocument.
     */
    private void displayUpdateDocument() {
        try {
            openConnection();
            
            DocumentDetails documentDetails = new DocumentDetails();
            documentDetails.setDocumentId(new BigDecimal(request.getParameter("dfDocumentId")));
            DocumentView documentView = new DocumentView();
            documentView.fillWithElements(connection, DocumentDetails.FILL_TYPE_ALL, documentDetails);
            if (documentView.getElements().size() == 1) {
                documentDetails = (DocumentDetails) documentView.getElements().elementAt(0);
                request.setAttribute("documentDetails", documentDetails);
            }
            commit();
            
            forward("/jsp/public/document/displayInsertUpdateDocument.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayDeleteDocument.
     */
    private void displayDeleteDocument() {
        try {
            openConnection();
            DocumentDetails documentDetails = new DocumentDetails();
            documentDetails.setDocumentId(new BigDecimal(request.getParameter("dfDocumentId")));
            DocumentView documentView = new DocumentView();
            documentView.fillWithElements(connection, DocumentDetails.FILL_TYPE_ALL, documentDetails);

            if (documentView.getElements().size() == 1) {
                documentDetails = (DocumentDetails) documentView.getElements().elementAt(0);
                request.setAttribute("documentDetails", documentDetails);
                ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();
                prospectDocumentDetails.setDocumentId(documentDetails.getDocumentId());
                ProspectDocumentView prospectDocumentView = new ProspectDocumentView();
                prospectDocumentView.fillWithElements(connection, ProspectDocumentDetails.FILL_TYPE_ALL, prospectDocumentDetails);
                if (prospectDocumentView.getElements().size() > 0) {
                    request.setAttribute("dbErrorMsg", "The document cannot be deleted because there are prospects linked to it.");
                }

            } else {
                request.setAttribute("dbErrorMsg", "The document has just been deleted by someone else.");
            }

            commit();
            request.getRequestDispatcher("/jsp/public/document/displayInsertUpdateDocument.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }


    /**
     * Method insertDocument.
     */
    private void insertDocument() {
        try {
            openConnection();
            MultipartRequest multipartRequest = uploadProcessor.getMultipartRequest();
            request.setAttribute("multipartRequest", multipartRequest);

            try {
                HashMap uploadedFilesMap = uploadProcessor.getUploadedFiles(AppSettings.getAppRealPath() + UPLOAD_FOLDER);
                Enumeration filesEnumeration = multipartRequest.getFileNames();
                if (filesEnumeration.hasMoreElements()) {
                    //a new file has been uploaded, create a DOCUMENT record
                    String name = (String) filesEnumeration.nextElement();
                    String filename = (String) uploadedFilesMap.get(name);
                    DocumentView documentView = new DocumentView();
                    DocumentDetails documentDetails = new DocumentDetails();

                    documentDetails.setDocumentType(new BigDecimal(multipartRequest.getParameter("dfDocumentType")));
                    documentDetails.setFileName(filename);
                    int extOffset = documentDetails.getFileName().lastIndexOf(".");
                    if (extOffset > -1) {
                        documentDetails.setFileType(documentDetails.getFileName().substring(extOffset + 1).toLowerCase());
                    }
                    documentDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
                    documentDetails.setCreationUserId(userProfile.getUserId());
                    documentDetails.setDescription(multipartRequest.getParameter("dfDescription"));
                    documentView.setConnection(connection);
                    documentView.setAction("INSERT");
                    documentView.setFillType(DocumentDetails.FILL_TYPE_ALL);
                    documentView.setCurrent(documentDetails);
                    documentView.doAction();
                    commit();
                } else {
                    throw new ModelException("Invalid upload.", ModelException.UNKNOWN_ERROR);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            request.getRequestDispatcher("/jsp/public/document/insertUpdateDocument.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateDocument.
     */
    private void updateDocument() {
        try {
            openConnection();

            MultipartRequest multipartRequest = uploadProcessor.getMultipartRequest();
            request.setAttribute("multipartRequest", multipartRequest);
            HashMap uploadedFilesMap = uploadProcessor.getUploadedFiles(AppSettings.getAppRealPath() + UPLOAD_FOLDER);
            Enumeration files = multipartRequest.getFileNames();

            DocumentDetails documentDetails = new DocumentDetails();
            DocumentView documentView = new DocumentView();

            documentDetails.setDocumentId(new BigDecimal(multipartRequest.getParameter("dfDocumentId")));
            documentDetails.setDescription(multipartRequest.getParameter("dfDescription"));
            documentDetails.setDocumentType(new BigDecimal(multipartRequest.getParameter("dfDocumentType")));

            if (files.hasMoreElements()) {
                String name = (String) files.nextElement();
                String filename = (String) uploadedFilesMap.get(name);

                if (!Utilities.nullToBlank(filename).equals("")) {
                    documentDetails.setFileName(filename);
                    int extOffset = documentDetails.getFileName().lastIndexOf(".");

                    if (extOffset > -1) {
                        documentDetails.setFileType(documentDetails.getFileName().substring(extOffset + 1).toLowerCase());
                    }
                }
            }

            documentView.setConnection(connection);
            documentView.setAction("UPDATE");
            documentView.setFillType(DocumentDetails.FILL_TYPE_ALL);
            documentView.setCurrent(documentDetails);
            documentView.doAction();

            commit();
            request.getRequestDispatcher("/jsp/public/document/insertUpdateDocument.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteDocument.
     */
    private void deleteDocument() {
        try {
            openConnection();

            ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();
            prospectDocumentDetails.setDocumentId(new BigDecimal(request.getParameter("dfDocumentId")));
            ProspectDocumentView prospectDocumentView = new ProspectDocumentView();
            prospectDocumentView.fillWithElements(connection, ProspectDocumentDetails.FILL_TYPE_ALL, prospectDocumentDetails);
            if (prospectDocumentView.getElements().size() > 0) {
                request.setAttribute("dbErrorMsg", "There are prospects linked to this document.");
            } else {
                DocumentDetails documentDetails = new DocumentDetails();
                DocumentView documentView = new DocumentView();

                documentDetails.setDocumentId(new BigDecimal(request.getParameter("dfDocumentId")));

                documentView.setConnection(connection);
                documentView.setAction("DELETE");
                documentView.setFillType(DocumentDetails.FILL_TYPE_ALL);
                documentView.setCurrent(documentDetails);
                documentView.doAction();

                commit();
            }
            request.getRequestDispatcher("/jsp/public/document/insertUpdateDocument.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayDocuments.
     * @param documentType BigDecimal
     */
    private void displayDocuments(BigDecimal documentType) {
        try {
            openConnection();
            try {
                DocumentView documentView = new DocumentView();
                DocumentDetails documentDetails = new DocumentDetails();
                getAuthorizationLevelSearchInformation(documentDetails, getCurrentSalesmanDetails());
                documentDetails.setDocumentType(documentType);
                documentView.fillWithElements(connection, DocumentDetails.FILL_TYPE_ALL_AUTHORIZED, documentDetails);
                
                if (documentView.getElements().size() < 1) {
                    throw new ModelException("No records found.", ModelException.RECORD_NOT_FOUND);
                }
                setCreationUserName(documentView);
                request.setAttribute("documentView", documentView);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            commit();
            request.getRequestDispatcher("/jsp/public/document/displayDocuments.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    private void setCreationUserName(DocumentView documentView) throws Exception{
		UserView userView = new UserView();
		for(int i = 0;i<documentView.getElements().size();i++){
			DocumentDetails documentDetails = new DocumentDetails();
			documentDetails = (DocumentDetails)documentView.getElement(i);
			if(documentDetails.getCreationUserId()!=null){
				UserDetails userDetails= new UserDetails();
				userDetails.setUserId(documentDetails.getCreationUserId());
				//System.out.println("userDetails.getUserUserId()=" + userDetails.getUserId());
				userView.fillWithElements(connection, UserView.FILL_TYPE_LOGIN, userDetails);
				//System.out.println(" userView.getElements().size()= " + userView.getElements().size());
				if(userView.getElements().size()>0){
					userDetails = (UserDetails)userView.getElement(0);
					documentDetails.setCreationUserName(userDetails.getUserName());
				}else{
					documentDetails.setCreationUserName("Admin");
				}
			}else{
				documentDetails.setCreationUserName("Admin");
			}//end if if prospectDocumentDetails, creation User Id is not null
		}//end of prospectDocumentView for loop
		
	}

    /**
     * Method getCurrentSalesmanDetails.
     * @return UserDetails
     * @throws Exception
     */
    protected UserDetails getCurrentSalesmanDetails() throws Exception {
        UserView userView = new UserView();
        UserDetails currentSalesmanProspectDetails = new UserDetails();
        currentSalesmanProspectDetails.setUserId(userProfile.getUserId());
        userView.fillWithElements(connection, UserView.FILL_TYPE_SALESMAN, currentSalesmanProspectDetails);
        if (userView.getElements().size() == 1) {
            // load current salesman prospect profile
            currentSalesmanProspectDetails = (UserDetails) userView.getElements().elementAt(0);
        } else {
            currentSalesmanProspectDetails = null;
        }

        return currentSalesmanProspectDetails;
    }

    /**
     * Method getAuthorizationLevelSearchInformation.
     * @param documentDetails DocumentDetails
     * @param salesmanDetails UserDetails
     */
    public void getAuthorizationLevelSearchInformation(DocumentDetails documentDetails,
                                             UserDetails salesmanDetails) {
            if (salesmanDetails != null) {
                documentDetails.setCompanyId(salesmanDetails.getCompanyId());
                documentDetails.setCountryId(salesmanDetails.getCountryId());
                documentDetails.setCountryId(salesmanDetails.getStateId());
                documentDetails.setTerritoryId(salesmanDetails.getTerritoryId());
                documentDetails.setLobId(salesmanDetails.getLobId());
                documentDetails.setStatusId(salesmanDetails.getStateId());
                documentDetails.setSoftwareTypeId(salesmanDetails.getSoftwareTypeId());
                documentDetails.setSystemTypeId(salesmanDetails.getSystemTypeId());
                documentDetails.setVerifiedId(salesmanDetails.getVerifiedId());
            }
    }

}
