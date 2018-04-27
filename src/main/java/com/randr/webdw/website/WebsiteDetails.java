package com.randr.webdw.website;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * Date: Oct 8, 2004
 * Time: 10:48:36 AM
 * @author randr
 * @version $Revision: 1.1 $
 */
public class WebsiteDetails extends AbstractDetails {
    protected BigDecimal websiteId;
    protected BigDecimal prospectId;
    protected String url;
    protected String description;

    protected Vector prospectIdsVector;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("website_id", "websiteId");
        addMapping("prospect_id", "prospectId");
        addMapping("url", "url");
        addMapping("description", "description");
    }

    /**
     * Method getWebsiteId.
     * @return BigDecimal
     */
    public BigDecimal getWebsiteId() {
        return websiteId;
    }

    /**
     * Method setWebsiteId.
     * @param websiteId BigDecimal
     */
    public void setWebsiteId(BigDecimal websiteId) {
        this.websiteId = websiteId;
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
     * Method getUrl.
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Method setUrl.
     * @param url String
     */
    public void setUrl(String url) {
        this.url = url;
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
     * Method getProspectIdsVector.
     * @return Vector
     */
    public Vector getProspectIdsVector() {
        return prospectIdsVector;
    }

    /**
     * Method setProspectIdsVector.
     * @param prospectIdsVector Vector
     */
    public void setProspectIdsVector(Vector prospectIdsVector) {
        this.prospectIdsVector = prospectIdsVector;
    }


}
