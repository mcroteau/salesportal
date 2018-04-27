package com.randr.webdw.audit.auditRoundRobin;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class AuditRoundRobinDetails extends AbstractDetails {
    protected BigDecimal auditRRId;
    protected BigDecimal prospectId;
    protected BigDecimal territoryId;
    protected Timestamp creationDate;
    protected BigDecimal userIdMakingChange;
    protected String externalId;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("audit_rr_id", "auditRRId");
        addMapping("prosect_id", "prospectId");
        addMapping("territory_id", "territoryId");
        addMapping("creation_date", "creationDate");
        addMapping("user_id_making_change", "userIdMakingChange");
        addMapping("external_id", "externalId");
        
    }

	/**
	 * @return the auditRRId
	 */
	public BigDecimal getAuditRRId() {
		return auditRRId;
	}

	/**
	 * @param auditRRId the auditRRId to set
	 */
	public void setAuditRRId(BigDecimal auditRRId) {
		this.auditRRId = auditRRId;
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
	 * @return the prospectId
	 */
	public BigDecimal getProspectId() {
		return prospectId;
	}

	/**
	 * @param prospectId the prospectId to set
	 */
	public void setProspectId(BigDecimal prospectId) {
		this.prospectId = prospectId;
	}

	/**
	 * @return the territoryId
	 */
	public BigDecimal getTerritoryId() {
		return territoryId;
	}

	/**
	 * @param territoryId the territoryId to set
	 */
	public void setTerritoryId(BigDecimal territoryId) {
		this.territoryId = territoryId;
	}

	/**
	 * @return the userIdMakingChange
	 */
	public BigDecimal getUserIdMakingChange() {
		return userIdMakingChange;
	}

	/**
	 * @param userIdMakingChange the userIdMakingChange to set
	 */
	public void setUserIdMakingChange(BigDecimal userIdMakingChange) {
		this.userIdMakingChange = userIdMakingChange;
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

   
}
