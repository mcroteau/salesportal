package com.randr.webdw.document;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;

/**
 * Date: Oct 8, 2004
 * Time: 10:58:26 AM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class DocumentBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "document";
        this.tableAlias = "doc";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param documentDetails DocumentDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, DocumentDetails documentDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(documentDetails);
        
        if (fillType.equals(DocumentDetails.FILL_TYPE_ALL_AUTHORIZED)) {
            useDistinct(true);
            addOtherTables(" left join " + AppSettings.getParm("LIB") + "prospect_document pd on pd.document_id=doc.document_id");
            addOtherTables(" left join " + AppSettings.getParm("LIB") + "prospect p on pd.prospect_id=p.prospect_id");

            if (documentDetails.getCompanyId() != null) {
                addCondition("(p.company_id = ? or pd.prospect_document_id is null)");
                addConditionParam(documentDetails.getCompanyId());
            }

            if (documentDetails.getCountryId() != null) {
                addCondition("(p.country_id = ?  or pd.prospect_document_id is null)");
                addConditionParam(documentDetails.getCountryId());
            }

            if (documentDetails.getStateId() != null) {
                addCondition("(p.state_id = ? or   pd.prospect_document_id is null)");
                addConditionParam(documentDetails.getStateId());
            }

            if (documentDetails.getSystemTypeId() != null) {
                addCondition("(p.system_type_id= ? or  pd.prospect_document_id is null)");
                addConditionParam(documentDetails.getSystemTypeId());
            }

            if (documentDetails.getTerritoryId() != null) {
                addCondition("(p.territory_id= ? or pd.prospect_document_id is null)");
                addConditionParam(documentDetails.getTerritoryId());
            }

            if (documentDetails.getLobId() != null) {
                addCondition("(p.lob_id= ? or pd.prospect_document_id is null)");
                addConditionParam(documentDetails.getLobId());
            }

            if (documentDetails.getSoftwareTypeId() != null) {
                addCondition("(p.software_type_id= ? or pd.prospect_document_id is null)");
                addConditionParam(documentDetails.getSoftwareTypeId());
            }

            if (documentDetails.getStatusId() != null) {
                addCondition("(p.status_id= ? or pd.prospect_document_id is null)");
                addConditionParam(documentDetails.getStatusId());
            }

            if (documentDetails.getVerifiedId() != null) {
                addCondition("(p.verified_id= ? or pd.prospect_document_id is null)");
                addConditionParam(documentDetails.getVerifiedId());
            }

        }
        // establish the list of properties to be populated
        addColumnList("doc.document_id, doc.document_type, doc.file_name, doc.file_type, doc.description,doc.creation_date, doc.creation_user_id, upper(doc.file_name), doc.external_id");
        setOrderBy("doc.document_type, upper(doc.file_name)");
        return doSelect(documentDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param documentDetails DocumentDetails
     */
    private void establishSearchConditions(DocumentDetails documentDetails) {
        if (documentDetails.getDocumentId() != null) {
            addCondition("doc.document_id= ?");
            addConditionParam(documentDetails.getDocumentId());
        }

        if (documentDetails.getDocumentType() != null) {
            addCondition("document_type= ?");
            addConditionParam(documentDetails.getDocumentType());
        }
        
        if (documentDetails.getExternalId() != null) {
            addCondition("external_id= ?");
            addConditionParam(documentDetails.getExternalId());
        }
        
        if (documentDetails.getCreationDate() != null) {
            addCondition("creation_date= ?");
            addConditionParam(documentDetails.getExternalId());
        }
        
        if (documentDetails.getCreationUserId() != null) {
            addCondition("creation_user_id= ?");
            addConditionParam(documentDetails.getCreationDate());
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param documentDetails DocumentDetails
     * @return DocumentDetails
     * @throws Exception
     */
    public DocumentDetails insert(BigDecimal fillType, DocumentDetails documentDetails) throws Exception {
        documentDetails.setDocumentId(getAvailableID("document_id", "document"));
        addColumnList("document_id, document_type, file_name, file_type, description, external_id, creation_date, creation_user_id");

        return (DocumentDetails) doInsert(documentDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param documentDetails DocumentDetails
     * @return DocumentDetails
     * @throws Exception
     */
    public DocumentDetails update(BigDecimal fillType, DocumentDetails documentDetails) throws Exception {
        addCondition("doc.document_id= ?");
        addConditionParam(documentDetails.getDocumentId());
        addColumnList("description");

        if (documentDetails.getFileName() != null) {
            addColumnList("file_name, file_type");
        }
        if (documentDetails.getDocumentType() != null) {
            addColumnList("document_type");
        }
        if (documentDetails.getExternalId() != null) {
            addColumnList("external_id");
        }

        return (DocumentDetails) doUpdate(documentDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param documentDetails DocumentDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, DocumentDetails documentDetails) throws Exception {
    	
    	if(documentDetails.getDocumentId() != null){
            addCondition("document_id= ?");
            addConditionParam(documentDetails.getDocumentId());

            doDelete(documentDetails);
            return true;	
    	}else{
    		return false;
    	}
    }

}
