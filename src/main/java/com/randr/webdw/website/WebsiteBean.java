package com.randr.webdw.website;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;

/**
 * Date: Oct 8, 2004
 * Time: 10:58:26 AM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class WebsiteBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "website";
        this.tableAlias = "web";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param websiteDetails WebsiteDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, WebsiteDetails websiteDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(websiteDetails);

        // establish the list of properties to be populated
        addColumnList("website_id, prospect_id, url, description");

        return doSelect(websiteDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param websiteDetails WebsiteDetails
     */
    private void establishSearchConditions(WebsiteDetails websiteDetails) {
        if (websiteDetails.getProspectId() != null) {
            addCondition("prospect_id= ?");
            addConditionParam(websiteDetails.getProspectId());
        }
        if (websiteDetails.getWebsiteId() != null) {
            addCondition("website_id= ?");
            addConditionParam(websiteDetails.getWebsiteId());
        }
        if (websiteDetails.getProspectIdsVector() != null
                && websiteDetails.getProspectIdsVector().size() > 0) {
            String condition = " prospect_id in (";
            for (int i = 0; i < websiteDetails.getProspectIdsVector().size(); i++) {
                BigDecimal prospectId = (BigDecimal) websiteDetails.getProspectIdsVector().elementAt(i);
                if (i > 0) {
                    condition += ",";
                }
                condition += prospectId.toString();
            }
            condition += ")";
            addCondition(condition);
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param websiteDetails WebsiteDetails
     * @return WebsiteDetails
     * @throws Exception
     */
    public WebsiteDetails insert(BigDecimal fillType, WebsiteDetails websiteDetails) throws Exception {
        websiteDetails.setWebsiteId(getAvailableID("website_id", "website"));
        addColumnList("website_id, prospect_id, url, description ");

        return (WebsiteDetails) doInsert(websiteDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param websiteDetails WebsiteDetails
     * @return WebsiteDetails
     * @throws Exception
     */
    public WebsiteDetails update(BigDecimal fillType, WebsiteDetails websiteDetails) throws Exception {
        addCondition("website_id= ?");
        addConditionParam(websiteDetails.getWebsiteId());

        addColumnList("url, description");

        return (WebsiteDetails) doUpdate(websiteDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param websiteDetails WebsiteDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, WebsiteDetails websiteDetails) throws Exception {
        if (websiteDetails.getWebsiteId() != null) {
            addCondition("website_id= ?");
            addConditionParam(websiteDetails.getWebsiteId());
        }
        if (websiteDetails.getProspectId() != null) {
            addCondition("prospect_id=?");
            addConditionParam(websiteDetails.getProspectId());
        }
        
        if(websiteDetails.getProspectId() != null || websiteDetails.getWebsiteId() != null){
            doDelete(websiteDetails);
            return true;	
        }else{
        	return false;
        }

    }

}
