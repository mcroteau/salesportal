package com.randr.webdw.prospectDocument;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;

import com.oreilly.servlet.MultipartRequest;
import com.randr.webdw.AppSettings;
import com.randr.webdw.document.DocumentDetails;
import com.randr.webdw.document.DocumentView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.util.DateUtilities;

/**
 * Date: Oct 12, 2004
 * Time: 5:14:12 PM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class ProspectDocumentController extends AbstractController {
    public static final String UPLOAD_FOLDER = "doc";

    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	createUploadProcessor(request, UPLOAD_DOCUMENT);
        if (this.uploadProcessor == null) {
            if ((formAction == null) || (formAction.equals("DISPLAY"))) {
//                displayDocuments();
            } else if (formAction.equals("DELETE")) {
                deleteDocument();
            } else if (formAction.equals("DISPLAY_INSERT")) {
//                displayInsertDocument();
            } else if (formAction.equals("DISPLAY_UPDATE")) {
//                displayUpdateDocument();
            } else if (formAction.equals("DISPLAY_DELETE")) {
//                displayDeleteDocument();
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
     * Method insertDocument.
     */
    private void insertDocument() {
        try {
            openConnection();
            MultipartRequest multipartRequest = uploadProcessor.getMultipartRequest();
            request.setAttribute("multipartRequest", multipartRequest);
            try {
                BigDecimal documentId = null;
                HashMap uploadedFilesMap = uploadProcessor.getUploadedFiles(AppSettings.getAppRealPath() + UPLOAD_FOLDER);
                Enumeration filesEnumeration = multipartRequest.getFileNames();

                if (multipartRequest.getParameter("dfDocumentId") == null ||
                    multipartRequest.getParameter("dfDocumentId").equals("")
                    && filesEnumeration.hasMoreElements()) {
                    //a new file has been uploaded, create a DOCUMENT record
                    String name = (String) filesEnumeration.nextElement();
                    String filename = (String)uploadedFilesMap.get(name);

                    DocumentView documentView = new DocumentView();
                    DocumentDetails documentDetails = new DocumentDetails();
                    documentDetails.setDocumentType(new BigDecimal(multipartRequest.getParameter("dfDocumentType")));
                    documentDetails.setFileName(filename);
                    documentDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
                    documentDetails.setCreationUserId(userProfile.getUserId());
                    int extOffset = documentDetails.getFileName().lastIndexOf(".");
                    if (extOffset > -1) {
                        documentDetails.setFileType(documentDetails.getFileName().substring(extOffset + 1).toLowerCase());
                    }
                    documentDetails.setDescription(multipartRequest.getParameter("dfDescription"));

                    documentView.setConnection(connection);
                    documentView.setAction("INSERT");
                    documentView.setFillType(DocumentDetails.FILL_TYPE_ALL);
                    documentView.setCurrent(documentDetails);
                    documentView.doAction();
                    documentDetails = (DocumentDetails) documentView.getCurrent();
                    documentId = documentDetails.getDocumentId();
                } else {
                    documentId = new BigDecimal(multipartRequest.getParameter("dfDocumentId"));
                }

                ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();
                prospectDocumentDetails.setDocumentId(documentId);
                prospectDocumentDetails.setProspectId(new BigDecimal(multipartRequest.getParameter("dfProspectId")));

                ProspectDocumentView prospectDocumentView = new ProspectDocumentView();
                prospectDocumentView.setConnection(connection);
                prospectDocumentView.setAction("INSERT");
                prospectDocumentView.setFillType(ProspectDocumentDetails.FILL_TYPE_ALL);
                prospectDocumentView.setCurrent(prospectDocumentDetails);
                prospectDocumentView.doAction();

                commit();
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            request.getRequestDispatcher("/jsp/shared/prospectDocument/insertUpdateProspectDocument.jsp").forward(request, response);
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
            try {
                BigDecimal documentId = null;
                HashMap uploadedFilesMap = uploadProcessor.getUploadedFiles(AppSettings.getAppRealPath() + UPLOAD_FOLDER);
                Enumeration filesEnumeration = multipartRequest.getFileNames();

                if (multipartRequest.getParameter("dfDocumentId") == null
                    || multipartRequest.getParameter("dfDocumentId").equals("")
                    && filesEnumeration.hasMoreElements()) {
                    //a new file has been uploaded, create a DOCUMENT record
                    String name = (String) filesEnumeration.nextElement();
                    String filename = (String)uploadedFilesMap.get(name);

                    DocumentView documentView = new DocumentView();
                    DocumentDetails documentDetails = new DocumentDetails();
                    documentDetails.setDocumentType(new BigDecimal(multipartRequest.getParameter("dfDocumentType")));
                    documentDetails.setFileName(filename);
                    int extOffset = documentDetails.getFileName().lastIndexOf(".");
                    if (extOffset > -1) {
                        documentDetails.setFileType(documentDetails.getFileName().substring(extOffset + 1).toLowerCase());
                    }
                    documentDetails.setDescription(multipartRequest.getParameter("dfDescription"));

                    documentView.setConnection(connection);
                    documentView.setAction("INSERT");
                    documentView.setFillType(DocumentDetails.FILL_TYPE_ALL);
                    documentView.setCurrent(documentDetails);
                    documentView.doAction();
                    documentDetails = (DocumentDetails) documentView.getCurrent();
                    documentId = documentDetails.getDocumentId();
                } else {
                    documentId = new BigDecimal(multipartRequest.getParameter("dfDocumentId"));
                }

                ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();
                prospectDocumentDetails.setProspectDocumentId(new BigDecimal(multipartRequest.getParameter("dfProspectDocumentId")));
                prospectDocumentDetails.setDocumentId(documentId);
                prospectDocumentDetails.setProspectId(new BigDecimal(multipartRequest.getParameter("dfProspectId")));

                ProspectDocumentView prospectDocumentView = new ProspectDocumentView();
                prospectDocumentView.setConnection(connection);
                prospectDocumentView.setAction("UPDATE");
                prospectDocumentView.setFillType(DocumentDetails.FILL_TYPE_ALL);
                prospectDocumentView.setCurrent(prospectDocumentDetails);
                prospectDocumentView.doAction();
                
                DocumentView documentView = new DocumentView();
                DocumentDetails documentDetails = new DocumentDetails();
                documentDetails.setDocumentId(documentId);
                documentView.fillWithElements(connection, DocumentDetails.FILL_TYPE_ALL_AUTHORIZED, documentDetails);
                System.out.println("pros doc update 4");
                if(documentView.getElements().size()>0){
                	System.out.println("pros doc update 5");
                	documentDetails = (DocumentDetails)documentView.getElement(0);
                	documentDetails.setDocumentType(new BigDecimal(multipartRequest.getParameter("dfDocumentType")));
                	System.out.println("new desc = " + multipartRequest.getParameter("dfDescription"));
                    documentDetails.setDescription(multipartRequest.getParameter("dfDescription"));
                    documentView.doAction(connection, "UPDATE", DocumentDetails.FILL_TYPE_ALL, documentDetails);
                }
        
                commit();
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            request.getRequestDispatcher("/jsp/shared/prospectDocument/insertUpdateProspectDocument.jsp").forward(request, response);
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
            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
            if (sessionProspectView == null) {
                throw new ModelException("Session expired", ModelException.UNKNOWN_ERROR);
            }
            ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();
            prospectDocumentDetails.setProspectDocumentId(new BigDecimal(request.getParameter("dfProspectDocumentId")));

            ProspectDocumentView prospectDocumentView = new ProspectDocumentView();
            prospectDocumentView.setConnection(connection);
            prospectDocumentView.setAction("DELETE");
            prospectDocumentView.setFillType(DocumentDetails.FILL_TYPE_ALL);
            prospectDocumentView.setCurrent(prospectDocumentDetails);
            prospectDocumentView.doAction();

            commit();

            request.getRequestDispatcher("/jsp/shared/prospectDocument/deleteProspectDocument.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

}
