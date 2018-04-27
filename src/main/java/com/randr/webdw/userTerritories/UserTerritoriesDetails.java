package com.randr.webdw.userTerritories;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class UserTerritoriesDetails extends AbstractDetails {
   
	protected BigDecimal userTerritoryId;
    protected BigDecimal userId;
    protected BigDecimal territoryId;
    
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("user_territory_id", "userTerritoryId");
        addMapping("user_id", "userId");
        addMapping("territory_id", "territoryId");       
    }
    
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(super.toString());
    	
    	sb.append("[ ");
    	sb.append("userTerritoryId=" + userTerritoryId + ", ");
    	sb.append("userId=" + userId + ", ");
    	sb.append("territoryId=" + territoryId + ", ");
    	sb.append(" ]");  	
    	
    	return sb.toString();
    }

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public BigDecimal getUserTerritoryId() {
		return userTerritoryId;
	}

	public void setUserTerritoryId(BigDecimal userTerritoryId) {
		this.userTerritoryId = userTerritoryId;
	}

	public BigDecimal getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(BigDecimal territoryId) {
		this.territoryId = territoryId;
	}

}
