package com.randr.webdw.territory;

import java.math.BigDecimal;
import java.util.List;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class TerritoryDetails extends AbstractDetails {
    protected BigDecimal territoryId;
    protected String territory;
    
    //territory that the territory returns to
    protected BigDecimal returnTerritoryId;
    protected String returnTerritoryName;
    
    //time that the prospect remains in the territory before being 
    //sent to the returnTerritory
    protected BigDecimal returnTime;
    
    //will determine whether to display the prospects in this.territory in random sequence
    protected BigDecimal randomSequence;
    
    //will determine whether to include this.territory in the round robin logic set for ushud
    protected BigDecimal includeInRoundRobin;
    
    //used for the round robin logic.  Will determine which Territory will be next
    //to receive a prospect
    protected BigDecimal roundRobinTerritorySelected;
    
    //settings for maximum prospects to be displayed on one screen
    protected BigDecimal maxProspectDisplay;
    
    protected BigDecimal ownerUserId;
    protected BigDecimal salesManagerUserId;
    protected BigDecimal serviceManagerUserId;

    protected String ownerUserName;
    protected String salesManagerUserName;
    protected String serviceManagerUserName;
    
    protected List territoryIdList;
    
    protected BigDecimal includeInConversionReport;
    
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
    
    public static final BigDecimal NOT_RANDOM = new BigDecimal(0);
    public static final BigDecimal RANDOM = new BigDecimal(1);
    
    public static final BigDecimal NOT_INCLUDED_IN_CONVERSION_REPORT = new BigDecimal(0);
    public static final BigDecimal INCLUDED_IN_CONVERSION_REPORT = new BigDecimal(1);
    
    public static final BigDecimal NOT_INCLUDED_IN_ROUND_ROBIN = new BigDecimal(0);
    public static final BigDecimal INCLUDED_IN_ROUND_ROBIN = new BigDecimal(1);
    
    public static final BigDecimal NOT_SELECTED_ROUND_ROBIN = new BigDecimal(0);
    public static final BigDecimal SELECTED_ROUND_ROBIN = new BigDecimal(1);
    
    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("territory_id", "territoryId");
        addMapping("territory", "territory");
        addMapping("owner_user_id", "ownerUserId");
        addMapping("sales_manager_user_id", "salesManagerUserId");
        addMapping("service_manager_user_id", "serviceManagerUserId");
        addMapping("u1.user_name", "ownerUserName");
        addMapping("u2.user_name", "salesManagerUserName");
        addMapping("u3.user_name", "serviceManagerUserName");
        addMapping("return_territory_id", "returnTerritoryId");
        addMapping("return_time", "returnTime");
        addMapping("random_sequence", "randomSequence");
        addMapping("max_prospect_display", "maxProspectDisplay");
        addMapping("include_in_conversion_report", "includeInConversionReport");
        addMapping("include_in_round_robin", "includeInRoundRobin");
        addMapping("round_robin_selected", "roundRobinTerritorySelected");
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
     * Method getTerritory.
     * @return String
     */
    public String getTerritory() {
        return territory;
    }

    /**
     * Method setTerritory.
     * @param territory String
     */
    public void setTerritory(String territory) {
        this.territory = territory;
    }

    /**
     * Method getOwnerUserId.
     * @return BigDecimal
     */
    public BigDecimal getOwnerUserId() {
        return ownerUserId;
    }

    /**
     * Method setOwnerUserId.
     * @param ownerUserId BigDecimal
     */
    public void setOwnerUserId(BigDecimal ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * Method getSalesManagerUserId.
     * @return BigDecimal
     */
    public BigDecimal getSalesManagerUserId() {
        return salesManagerUserId;
    }

    /**
     * Method setSalesManagerUserId.
     * @param salesManagerUserId BigDecimal
     */
    public void setSalesManagerUserId(BigDecimal salesManagerUserId) {
        this.salesManagerUserId = salesManagerUserId;
    }

    /**
     * Method getServiceManagerUserId.
     * @return BigDecimal
     */
    public BigDecimal getServiceManagerUserId() {
        return serviceManagerUserId;
    }

    /**
     * Method setServiceManagerUserId.
     * @param serviceManagerUserId BigDecimal
     */
    public void setServiceManagerUserId(BigDecimal serviceManagerUserId) {
        this.serviceManagerUserId = serviceManagerUserId;
    }

    /**
     * Method getOwnerUserName.
     * @return String
     */
    public String getOwnerUserName() {
        return ownerUserName;
    }

    /**
     * Method setOwnerUserName.
     * @param ownerUserName String
     */
    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    /**
     * Method getSalesManagerUserName.
     * @return String
     */
    public String getSalesManagerUserName() {
        return salesManagerUserName;
    }

    /**
     * Method setSalesManagerUserName.
     * @param salesManagerUserName String
     */
    public void setSalesManagerUserName(String salesManagerUserName) {
        this.salesManagerUserName = salesManagerUserName;
    }

    /**
     * Method getServiceManagerUserName.
     * @return String
     */
    public String getServiceManagerUserName() {
        return serviceManagerUserName;
    }

    /**
     * Method setServiceManagerUserName.
     * @param serviceManagerUserName String
     */
    public void setServiceManagerUserName(String serviceManagerUserName) {
        this.serviceManagerUserName = serviceManagerUserName;
    }

	public BigDecimal getReturnTerritoryId() {
		return returnTerritoryId;
	}

	public void setReturnTerritoryId(BigDecimal returnTerritoryId) {
		this.returnTerritoryId = returnTerritoryId;
	}

	public BigDecimal getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(BigDecimal returnTime) {
		this.returnTime = returnTime;
	}

	public BigDecimal getMaxProspectDisplay() {
		return maxProspectDisplay;
	}

	public void setMaxProspectDisplay(BigDecimal maxProspectDisplay) {
		this.maxProspectDisplay = maxProspectDisplay;
	}

	public BigDecimal getRandomSequence() {
		return randomSequence;
	}

	public void setRandomSequence(BigDecimal randomSequence) {
		this.randomSequence = randomSequence;
	}

	public List getTerritoryIdList() {
		return territoryIdList;
	}

	public void setTerritoryIdList(List territoryIdList) {
		this.territoryIdList = territoryIdList;
	}

	public BigDecimal getIncludeInConversionReport() {
		return includeInConversionReport;
	}

	public void setIncludeInConversionReport(BigDecimal includeInConversionReport) {
		this.includeInConversionReport = includeInConversionReport;
	}

	public String getReturnTerritoryName() {
		return returnTerritoryName;
	}

	public void setReturnTerritoryName(String returnTerritoryName) {
		this.returnTerritoryName = returnTerritoryName;
	}
	
	public String toString(){
		return "territoryId = " + territoryId + "\n" +
			"territoryName =" + territory + "\n" +
			"ownerUserId =" + ownerUserId+ "\n" +
			"salesManagerId =" + salesManagerUserId + "\n" +
			"serviceManagerId = " + serviceManagerUserId + "\n" +
			"territoryIdList = " + territoryIdList + "\n" +
			"returnTerritoryName = " + returnTerritoryName + "\n" +
			"includeInConversionReport = " + includeInConversionReport + "\n" +
			"maxProspectDisplay = " + maxProspectDisplay + "\n" +
			"includeInRoundRobin = " + includeInRoundRobin;
	}

	public BigDecimal getRoundRobinTerritorySelected() {
		return roundRobinTerritorySelected;
	}

	public void setRoundRobinTerritorySelected(
			BigDecimal roundRobinTerritorySelected) {
		this.roundRobinTerritorySelected = roundRobinTerritorySelected;
	}

	public BigDecimal getIncludeInRoundRobin() {
		return includeInRoundRobin;
	}

	public void setIncludeInRoundRobin(BigDecimal includeInRoundRobin) {
		this.includeInRoundRobin = includeInRoundRobin;
	}
}
