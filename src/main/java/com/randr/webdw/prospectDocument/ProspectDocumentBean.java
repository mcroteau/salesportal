package com.randr.webdw.prospectDocument;

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
public class ProspectDocumentBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "prospect_document";
        this.tableAlias = "pd";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param prospectDocumentDetails ProspectDocumentDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, ProspectDocumentDetails prospectDocumentDetails) throws Exception {
        establishSearchConditions(prospectDocumentDetails);
        addColumnList("pd.prospect_document_id, pd.document_id, pd.prospect_id ");

        addOtherTables(" inner join " + AppSettings.getParm("LIB")
                + "document doc on pd.document_id=doc.document_id");
        addColumnList("doc.document_id, doc.document_type, doc.file_name, doc.file_type, doc.description, doc.creation_date, doc.creation_user_id");
        setOrderBy("doc.document_type, doc.file_name");
        return doSelect(prospectDocumentDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param prospectDocumentDetails ProspectDocumentDetails
     */
    private void establishSearchConditions(ProspectDocumentDetails prospectDocumentDetails) {
        if (prospectDocumentDetails.getProspectDocumentId() != null) {
            addCondition("pd.prospect_document_id= ?");
            addConditionParam(prospectDocumentDetails.getProspectDocumentId());
        }
        if (prospectDocumentDetails.getProspectId() != null) {
            addCondition("pd.prospect_id= ?");
            addConditionParam(prospectDocumentDetails.getProspectId());
        }
        if (prospectDocumentDetails.getDocumentId() != null) {
            addCondition("pd.document_id= ?");
            addConditionParam(prospectDocumentDetails.getDocumentId());
        }
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param prospectDocumentDetails ProspectDocumentDetails
     * @return ProspectDocumentDetails
     * @throws Exception
     */
    public ProspectDocumentDetails insert(BigDecimal fillType, ProspectDocumentDetails prospectDocumentDetails) throws Exception {
        prospectDocumentDetails.setProspectDocumentId(getAvailableID("prospect_document_id", "prospect_document"));
        addColumnList("prospect_document_id, document_id, prospect_id");
        return (ProspectDocumentDetails) doInsert(prospectDocumentDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param prospectDocumentDetails ProspectDocumentDetails
     * @return ProspectDocumentDetails
     * @throws Exception
     */
    public ProspectDocumentDetails update(BigDecimal fillType, ProspectDocumentDetails prospectDocumentDetails) throws Exception {
        addCondition("prospect_document_id=?");
        addConditionParam(prospectDocumentDetails.getProspectDocumentId());
        addColumnList("document_id, prospect_id");

        return (ProspectDocumentDetails) doUpdate(prospectDocumentDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param prospectDocumentDetails ProspectDocumentDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, ProspectDocumentDetails prospectDocumentDetails) throws Exception {
        if (prospectDocumentDetails.getProspectDocumentId() != null) {
            addCondition("prospect_document_id= ?");
            addConditionParam(prospectDocumentDetails.getProspectDocumentId());
        }

        if (prospectDocumentDetails.getProspectId() != null) {
            addCondition("prospect_id=?");
            addConditionParam(prospectDocumentDetails.getProspectId());
        }
        
        if(prospectDocumentDetails.getProspectId() != null || prospectDocumentDetails.getProspectDocumentId() != null){
            doDelete(prospectDocumentDetails);
            return true;
        }else{
        	return false;
        }

    }

}
