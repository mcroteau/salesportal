package com.randr.webdw.campaign;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class CampaignDetails extends AbstractDetails {
    protected BigDecimal campaignId;
    protected String campaign;
    private Timestamp creationDate;
    private String creationUser;
    private Timestamp updateDate;
    private String updateUser;
    private String description;
    protected BigDecimal displayForSalesmen;// controls if this is displayed in the select box

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
    
    public static final BigDecimal NO_DONOT_DISPLAY_FOR_SALESMEN = new BigDecimal(0);//this controls the options in the prospect screen
    public static final BigDecimal YES_DISPLAY_FOR_SALESMEN = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("campaign_id", "campaignId");
        addMapping("campaign", "campaign");
        addMapping("creation_date", "creationDate");
        addMapping("created_by_user", "creationUser");
        addMapping("update_date", "updateDate");
        addMapping("updated_by_user", "updateUser");
        addMapping("description", "description");
        addMapping("display_for_salesmen", "displayForSalesmen");
    }

    /**
     * Method getCampaignId.
     * @return BigDecimal
     */
    public BigDecimal getCampaignId() {
        return campaignId;
    }

    /**
     * Method setCampaignId.
     * @param campaignId BigDecimal
     */
    public void setCampaignId(BigDecimal campaignId) {
        this.campaignId = campaignId;
    }

    /**
     * Method getCampaign.
     * @return String
     */
    public String getCampaign() {
        return campaign;
    }

    /**
     * Method setCampaign.
     * @param campaign String
     */
    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the displayForSalesmen
	 */
	public BigDecimal getDisplayForSalesmen() {
		return displayForSalesmen;
	}

	/**
	 * @param displayForSalesmen the displayForSalesmen to set
	 */
	public void setDisplayForSalesmen(BigDecimal displayForSalesmen) {
		this.displayForSalesmen = displayForSalesmen;
	}
}
