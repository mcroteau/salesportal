package com.randr.webdw.prospectDocument;

import java.math.BigDecimal;

import com.randr.webdw.document.DocumentDetails;

/**
 * Date: Oct 11, 2004
 * Time: 3:04:20 PM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class ProspectDocumentDetails extends DocumentDetails {
    protected BigDecimal prospectDocumentId;
    protected BigDecimal documentId;
    protected BigDecimal prospectId;
    protected String creationUserName;

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("prospect_document_id", "prospectDocumentId");
        addMapping("document_id", "documentId");
        addMapping("prospect_id", "prospectId");

        addMapping("doc.document_type", "documentType");
        addMapping("doc.file_name", "fileName");
        addMapping("doc.file_type", "fileType");
        addMapping("doc.description", "description");

    }

    /**
     * Method getProspectDocumentId.
     * @return BigDecimal
     */
    public BigDecimal getProspectDocumentId() {
        return prospectDocumentId;
    }

    /**
     * Method setProspectDocumentId.
     * @param prospectDocumentId BigDecimal
     */
    public void setProspectDocumentId(BigDecimal prospectDocumentId) {
        this.prospectDocumentId = prospectDocumentId;
    }

    /**
     * Method getDocumentId.
     * @return BigDecimal
     */
    public BigDecimal getDocumentId() {
        return documentId;
    }

    /**
     * Method setDocumentId.
     * @param documentId BigDecimal
     */
    public void setDocumentId(BigDecimal documentId) {
        this.documentId = documentId;
    }

    /**
     * Method getProspectId.
     * @return BigDecimal
     */
    public BigDecimal getProspectId() {
        return prospectId;
    }

    /**
     * Method setProspectId.
     * @param prospectId BigDecimal
     */
    public void setProspectId(BigDecimal prospectId) {
        this.prospectId = prospectId;
    }

	/**
	 * @return the creationUserName
	 */
	public String getCreationUserName() {
		return creationUserName;
	}

	/**
	 * @param creationUserName the creationUserName to set
	 */
	public void setCreationUserName(String creationUserName) {
		this.creationUserName = creationUserName;
	}
}
