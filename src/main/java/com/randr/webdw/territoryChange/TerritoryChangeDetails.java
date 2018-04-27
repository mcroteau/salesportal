package com.randr.webdw.territoryChange;

import java.math.BigDecimal;
import java.sql.Timestamp;
import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class TerritoryChangeDetails extends AbstractDetails {
	
    public static final BigDecimal AUTOMATED_TERRITORY_CHANGE = new BigDecimal(2);
    public static final BigDecimal NON_AUTOMATED_TERRITORY_CHANGE = new BigDecimal(1);

	protected BigDecimal territoryChangeId;
	protected BigDecimal originalTerritoryId;
    protected BigDecimal newTerritoryId;
    protected BigDecimal userId;
    protected Timestamp changeDate;
    protected BigDecimal automatedTerritoryChange;

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
    	addMapping("territory_change_id", "territoryChangeId");
        addMapping("original_territory_id", "originalTerritoryId");
        addMapping("new_territory_id", "newTerritoryId");
        addMapping("user_id", "userId");
        addMapping("change_date", "changeDate");
        addMapping("auto_territory_change", "automatedTerritoryChange");
    }

	public Timestamp getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Timestamp changeDate) {
		this.changeDate = changeDate;
	}

	public BigDecimal getNewTerritoryId() {
		return newTerritoryId;
	}

	public void setNewTerritoryId(BigDecimal newTerritoryId) {
		this.newTerritoryId = newTerritoryId;
	}

	public BigDecimal getOriginalTerritoryId() {
		return originalTerritoryId;
	}

	public void setOriginalTerritoryId(BigDecimal originalTerritoryId) {
		this.originalTerritoryId = originalTerritoryId;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public BigDecimal getTerritoryChangeId() {
		return territoryChangeId;
	}

	public void setTerritoryChangeId(BigDecimal territoryChangeId) {
		this.territoryChangeId = territoryChangeId;
	}

	public BigDecimal getAutomatedTerritoryChange() {
		return automatedTerritoryChange;
	}

	public void setAutomatedTerritoryChange(BigDecimal automatedTerritoryChange) {
		this.automatedTerritoryChange = automatedTerritoryChange;
	}

}
