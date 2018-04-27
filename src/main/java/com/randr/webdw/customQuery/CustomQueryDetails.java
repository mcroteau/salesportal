package com.randr.webdw.customQuery;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CustomQueryDetails extends AbstractDetails {

    private BigDecimal customQueryNo;
    private String customQueryName;
    private String customQueryFileName;
    private String customQueryDescription;
    private String customQueryContent; //content from the .sql file

    /**
     * Constructor for CustomQueryDetails.
     */
    public CustomQueryDetails() {
    }

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("custom_query_no", "customQueryNo");
        addMapping("custom_query_name", "customQueryName");
        addMapping("custom_query_file_name", "customQueryFileName");
        addMapping("custom_query_description", "customerQueryDescription");
    }

    /**
     * Method getCustomQueryNo.
     * @return BigDecimal
     */
    public BigDecimal getCustomQueryNo() {
        return customQueryNo;
    }

    /**
     * Method setCustomQueryNo.
     * @param customQueryNo BigDecimal
     */
    public void setCustomQueryNo(BigDecimal customQueryNo) {
        this.customQueryNo = customQueryNo;
    }

    /**
     * Method setCustomQueryDescription.
     * @param customQueryDescription String
     */
    public void setCustomQueryDescription(String customQueryDescription) {
        this.customQueryDescription = customQueryDescription;
    }

    /**
     * Method getCustomQueryDescription.
     * @return String
     */
    public String getCustomQueryDescription() {
        return customQueryDescription;
    }

    /**
     * Method getCustomQueryFileName.
     * @return String
     */
    public String getCustomQueryFileName() {
        return customQueryFileName;
    }

    /**
     * Method setCustomQueryFileName.
     * @param customQueryFileName String
     */
    public void setCustomQueryFileName(String customQueryFileName) {
        this.customQueryFileName = customQueryFileName;
    }

    /**
     * Method getCustomQueryName.
     * @return String
     */
    public String getCustomQueryName() {
        return customQueryName;
    }

    /**
     * Method setCustomQueryName.
     * @param customQueryName String
     */
    public void setCustomQueryName(String customQueryName) {
        this.customQueryName = customQueryName;
    }

    /**
     * Method getCustomQueryContent.
     * @return String
     */
    public String getCustomQueryContent() {
        return customQueryContent;
    }

    /**
     * Method setCustomQueryContent.
     * @param customQueryContent String
     */
    public void setCustomQueryContent(String customQueryContent) {
        this.customQueryContent = customQueryContent;
    }
}