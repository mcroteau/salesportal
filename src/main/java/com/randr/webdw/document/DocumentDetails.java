package com.randr.webdw.document;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * Date: Oct 8, 2004
 * Time: 10:48:36 AM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class DocumentDetails extends AbstractDetails {
    protected BigDecimal documentId;
    protected BigDecimal documentType;
    protected String fileName;
    protected String fileType;
    protected String description;
    protected String externalId;
    
    protected Timestamp creationDate;
    protected BigDecimal creationUserId;
    protected String creationUserName;

    //join to user auth
    protected BigDecimal companyId;
    protected BigDecimal systemTypeId;
    protected BigDecimal territoryId;
    protected BigDecimal lobId;
    protected BigDecimal softwareTypeId;
    protected BigDecimal statusId;
    protected BigDecimal verifiedId;
    protected BigDecimal stateId;
    protected BigDecimal countryId;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
    public static final BigDecimal FILL_TYPE_ALL_AUTHORIZED = new BigDecimal(2);

    public static final BigDecimal TYPE_DOCUMENTATION = new BigDecimal(1);
    public static final BigDecimal TYPE_PICTURES = new BigDecimal(2);
    public static final BigDecimal TYPE_CONTRACTS = new BigDecimal(3);
    public static final BigDecimal TYPE_OTHER = new BigDecimal(4);

    public static final String[] typeDescription = { "Documentation", "Pictures", "Contracts" , "Other"};

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("document_id", "documentId");
        addMapping("document_type", "documentType");
        addMapping("file_name", "fileName");
        addMapping("file_type", "fileType");
        addMapping("description", "description");
        addMapping("external_id", "externalId");
        addMapping("creation_date", "creationDate");
        addMapping("creation_user_id", "creationUserId");
    }

    /**
     * Method getDocumentId.
     * @return BigDecimal
     */
    public BigDecimal getDocumentId() {
        return documentId;
    }

    /**
	 * @return the externalId
	 */
	public String getExternalId() {
		return externalId;
	}

	/**
	 * @param externalId the externalId to set
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	/**
     * Method setDocumentId.
     * @param documentId BigDecimal
     */
    public void setDocumentId(BigDecimal documentId) {
        this.documentId = documentId;
    }

    /**
     * Method getDocumentType.
     * @return BigDecimal
     */
    public BigDecimal getDocumentType() {
        return documentType;
    }

    /**
     * Method setDocumentType.
     * @param documentType BigDecimal
     */
    public void setDocumentType(BigDecimal documentType) {
        this.documentType = documentType;
    }

    /**
     * Method getFileName.
     * @return String
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Method setFileName.
     * @param fileName String
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Method getFileType.
     * @return String
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Method setFileType.
     * @param fileType String
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * Method getDescription.
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method setDescription.
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method getDocumentTypeDescription.
     * @param documentType BigDecimal
     * @return String
     */
    public static String getDocumentTypeDescription(BigDecimal documentType) {
        return typeDescription[documentType.intValue() - 1];
    }

    /**
     * Method getCompanyId.
     * @return BigDecimal
     */
    public BigDecimal getCompanyId() {
        return companyId;
    }

    /**
     * Method setCompanyId.
     * @param companyId BigDecimal
     */
    public void setCompanyId(BigDecimal companyId) {
        this.companyId = companyId;
    }

    /**
     * Method getSystemTypeId.
     * @return BigDecimal
     */
    public BigDecimal getSystemTypeId() {
        return systemTypeId;
    }

    /**
     * Method setSystemTypeId.
     * @param systemTypeId BigDecimal
     */
    public void setSystemTypeId(BigDecimal systemTypeId) {
        this.systemTypeId = systemTypeId;
    }

    /**
     * Method getTerritoryId.
     * @return BigDecimal
     */
    public BigDecimal getTerritoryId() {
        return territoryId;
    }

    /**
     * Method setTerritoryId.
     * @param territoryId BigDecimal
     */
    public void setTerritoryId(BigDecimal territoryId) {
        this.territoryId = territoryId;
    }

    /**
     * Method getLobId.
     * @return BigDecimal
     */
    public BigDecimal getLobId() {
        return lobId;
    }

    /**
     * Method setLobId.
     * @param lobId BigDecimal
     */
    public void setLobId(BigDecimal lobId) {
        this.lobId = lobId;
    }

    /**
     * Method getSoftwareTypeId.
     * @return BigDecimal
     */
    public BigDecimal getSoftwareTypeId() {
        return softwareTypeId;
    }

    /**
     * Method setSoftwareTypeId.
     * @param softwareTypeId BigDecimal
     */
    public void setSoftwareTypeId(BigDecimal softwareTypeId) {
        this.softwareTypeId = softwareTypeId;
    }

    /**
     * Method getStatusId.
     * @return BigDecimal
     */
    public BigDecimal getStatusId() {
        return statusId;
    }

    /**
     * Method setStatusId.
     * @param statusId BigDecimal
     */
    public void setStatusId(BigDecimal statusId) {
        this.statusId = statusId;
    }

    /**
     * Method getVerifiedId.
     * @return BigDecimal
     */
    public BigDecimal getVerifiedId() {
        return verifiedId;
    }

    /**
     * Method setVerifiedId.
     * @param verifiedId BigDecimal
     */
    public void setVerifiedId(BigDecimal verifiedId) {
        this.verifiedId = verifiedId;
    }

    /**
     * Method getStateId.
     * @return BigDecimal
     */
    public BigDecimal getStateId() {
        return stateId;
    }

    /**
     * Method setStateId.
     * @param stateId BigDecimal
     */
    public void setStateId(BigDecimal stateId) {
        this.stateId = stateId;
    }

    /**
     * Method getCountryId.
     * @return BigDecimal
     */
    public BigDecimal getCountryId() {
        return countryId;
    }

    /**
     * Method setCountryId.
     * @param countryId BigDecimal
     */
    public void setCountryId(BigDecimal countryId) {
        this.countryId = countryId;
    }

	/**
	 * @return the creationDate
	 */
	public Timestamp getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the creationUserId
	 */
	public BigDecimal getCreationUserId() {
		return creationUserId;
	}

	/**
	 * @param creationUserId the creationUserId to set
	 */
	public void setCreationUserId(BigDecimal creationUserId) {
		this.creationUserId = creationUserId;
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
