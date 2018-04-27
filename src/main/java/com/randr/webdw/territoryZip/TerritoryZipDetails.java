package com.randr.webdw.territoryZip;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class TerritoryZipDetails extends AbstractDetails {
    
	protected BigDecimal territoryId;
	protected BigDecimal territoryZipId;
	protected String zip;
    
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("territory_id", "territoryId");
        addMapping("territory_zip_id", "territoryZipId");
        addMapping("zip", "zip");
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
     * Method getTerritoryZipId.
     * @param territoryId BigDecimal
     */
	public BigDecimal getTerritoryZipId() {
		return territoryZipId;
	}

    /**
     * Method setTerritoryZipId.
     * @param territoryId BigDecimal
     */
	public void setTerritoryZipId(BigDecimal territoryZipId) {
		this.territoryZipId = territoryZipId;
	}
	
    /**
     * Method getZip.
     * @param territoryId BigDecimal
     */
	public String getZip() {
		return zip;
	}
	
    /**
     * Method setZip.
     * @param territoryId BigDecimal
     */
	public void setZip(String zip) {
		this.zip = zip;
	}
}
