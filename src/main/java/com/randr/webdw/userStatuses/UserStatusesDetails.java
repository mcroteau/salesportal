package com.randr.webdw.userStatuses;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * @author randr
 * @version $Revision: 1.12 $
 */
public class UserStatusesDetails extends AbstractDetails {
   
	protected BigDecimal userStatusId;
    protected BigDecimal userId;
    protected BigDecimal statusId;
    
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("user_status_id", "userStatusId");
        addMapping("user_id", "userId");
        addMapping("status_id", "statusId");       
    }
    
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append(super.toString());
    	
    	sb.append("[ ");
    	sb.append("userStatusId=" + userStatusId + ", ");
    	sb.append("userId=" + userId + ", ");
    	sb.append("statusId=" + statusId + ", ");
    	sb.append(" ]");  	
    	
    	return sb.toString();
    }

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public BigDecimal getUserStatusId() {
		return userStatusId;
	}

	public void setUserStatusId(BigDecimal userStatusId) {
		this.userStatusId = userStatusId;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

}
