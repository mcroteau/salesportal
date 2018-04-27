package com.randr.webdw.userLobs;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class UserLobsDetails extends AbstractDetails {
   
	protected BigDecimal userLobId;
    protected BigDecimal userId;
    protected BigDecimal lobId;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
    
    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("user_lob_id", "userLobId");
        addMapping("user_id", "userId");
        addMapping("lob_id", "lobId");       
    }
    
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(super.toString());
    	
    	sb.append("[ ");
    	sb.append("userLobId=" + userLobId + ", ");
    	sb.append("userId=" + userId + ", ");
    	sb.append("lobId=" + lobId + ", ");
    	sb.append(" ]");  	
    	
    	return sb.toString();
    }

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public BigDecimal getLobId() {
		return lobId;
	}

	public void setLobId(BigDecimal lobId) {
		this.lobId = lobId;
	}

	public BigDecimal getUserLobId() {
		return userLobId;
	}

	public void setUserLobId(BigDecimal userLobId) {
		this.userLobId = userLobId;
	}

}
