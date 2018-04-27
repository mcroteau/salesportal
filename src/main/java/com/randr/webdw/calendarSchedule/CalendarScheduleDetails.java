package com.randr.webdw.calendarSchedule;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import com.randr.webdw.mvcAbstract.AbstractDetails;
import com.randr.webdw.util.Utilities;

public class CalendarScheduleDetails extends AbstractDetails{
	
	public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);
	public static final BigDecimal FILL_TYPE_DAY = new BigDecimal(2);
	public static final BigDecimal FILL_TYPE_WEEK = new BigDecimal(3);
	public static final BigDecimal FILL_TYPE_MONTH = new BigDecimal(4);
	
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
    SimpleDateFormat hourFormat = new SimpleDateFormat("hh" + ":");
    SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
    SimpleDateFormat amPmFormat = new SimpleDateFormat("aa");
	
	protected BigDecimal prospectSalesActionId;
    protected BigDecimal prospectId;
    protected Timestamp nextSalesActionDate;
	protected BigDecimal nextSalesActionId;
    protected BigDecimal actionPriority;
    protected BigDecimal actionNotificationflag;
    protected String actionNote;
    protected BigDecimal actionStatus; //0 = active, 1 = completed
    
	protected String hour;
	protected String minute;
	protected String amPm;
	protected String time;
	
	//join
	protected String customerCompany;
	
	protected String nextSalesActionColor;
	
    protected String address;
    protected String address2;
    protected String city;
    protected String county;
    protected String zip;
    protected BigDecimal stateId;
    protected BigDecimal countryId;

    protected String contactName;
    protected String contactTitle;
    
    
    //replacing territoryId salesman are now allowed multiple territories
    protected List territoryIdList;
//  replacing territoryId salesman are now allowed multiple statuses
    protected List statusIdList;
    
    //for stone equity
    protected List lobIdList;
    
    protected BigDecimal territoryId;
    protected BigDecimal statusId;
    protected BigDecimal lobId;
    
    
    protected String phone;
    protected String phoneExt;
    
    protected String email;
	
    protected String territoryName;
    protected String statusName;
    protected String nextSalesActionName;
    protected String stateName;
    protected Integer countNotes;
    public static final String countNotesField = "count(n.note_id)";
    public String orderByFieldName;
    protected String countryName;
    
    protected BigDecimal prospectCampaignId;
    protected BigDecimal prospectCampaignSequence;
    protected String campaignName;
    
    protected Vector idsVector;
	
    //New String to hold Note
    protected String note;
    
    
    
	//this field is for searching only
	protected Timestamp dailyDate;
	protected Timestamp startDate;
	protected Timestamp endDate;

	protected void loadMapping() {
        addMapping("prospect_id", "prospectId");
        addMapping("prospect_sales_action_id", "prospectSalesActionId");
        addMapping("action_priority", "actionPriority");
        addMapping("sales_action_id", "nextSalesActionId");
        addMapping("action_date", "nextSalesActionDate");
        addMapping("action_notification_flag", "actionNotificationflag");
        addMapping("action_note", "actionNote");
        addMapping("action_status", "actionStatus");
        addMapping("prospect_campaign_id", "prospectCampaignId");
        addMapping("prospect_campaign_sequence", "prospectCampaignSequence");; 
        addMapping("cam.campaign", "campaignName");
        
        //join
        addMapping("p.address", "address");
		addMapping("p.customer_company", "customerCompany");
        addMapping("p.address2", "address2");
        addMapping("p.city", "city");
        addMapping("p.zip", "zip");
        addMapping("p.county", "county");
        addMapping("p.state_id", "stateId");
        addMapping("p.country_id", "countryId");
        addMapping("p.territory_id", "territoryId");
        addMapping("p.status_id", "statusId");
        addMapping("p.email", "email");

        addMapping("p.contact_name", "contactName");
        addMapping("p.contact_title", "contactTitle");

        addMapping("p.phone", "phone");
        addMapping("p.phone_ext", "phoneExt");
        
        addMapping("p.lob_id", "lobId");
        
        addMapping("ac.action", "nextSalesActionName");
        addMapping("ac.color", "nextSalesActionColor");
        addMapping("cn.country", "countryName");
        addMapping("sta.state", "stateName");
        addMapping("t.territory", "territoryName");
        addMapping("s.status", "statusName");
	}
	
	public String getNextSalesActionColor() {
		return nextSalesActionColor;
	}

	public void setNextSalesActionColor(String nextSalesActionColor) {
		this.nextSalesActionColor = nextSalesActionColor;
	}
	


	public BigDecimal getNextSalesActionId() {
		return nextSalesActionId;
	}


	public void setNextSalesActionId(BigDecimal nextSalesActionId) {
		this.nextSalesActionId = nextSalesActionId;
	}


	public String getCustomerCompany() {
		return customerCompany;
	}


	public void setCustomerCompany(String customerCompany) {
		this.customerCompany = customerCompany;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public Timestamp getNextSalesActionDate() {
		return nextSalesActionDate;
	}

	public void setNextSalesActionDate(Timestamp nextSalesActionDate) {
		this.nextSalesActionDate = nextSalesActionDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneExt() {
		return phoneExt;
	}

	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}

	public BigDecimal getProspectId() {
		return prospectId;
	}

	public void setProspectId(BigDecimal prospectId) {
		this.prospectId = prospectId;
	}

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Integer getCountNotes() {
		return countNotes;
	}

	public void setCountNotes(Integer countNotes) {
		this.countNotes = countNotes;
	}

	public Vector getIdsVector() {
		return idsVector;
	}

	public void setIdsVector(Vector idsVector) {
		this.idsVector = idsVector;
	}
    /**
     * Method getCountryAndStateSortValue.
     * @return String
     */
    public String getCountryAndStateSortValue() {
        return Utilities.nullToBlank(this.countryName) + ":" + Utilities.nullToBlank(this.stateName);
    }

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getTerritoryName() {
		return territoryName;
	}

	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}

	public String getNextSalesActionName() {
		return nextSalesActionName;
	}

	public void setNextSalesActionName(String nextSalesActionName) {
		this.nextSalesActionName = nextSalesActionName;
	}

	public String getOrderByFieldName() {
		return orderByFieldName;
	}

	public void setOrderByFieldName(String orderByFieldName) {
		this.orderByFieldName = orderByFieldName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public BigDecimal getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(BigDecimal territoryId) {
		this.territoryId = territoryId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAmPm() {
		this.amPm = amPmFormat.format(this.getNextSalesActionDate());
		return amPm;
	}

	public void setAmPm(String amPm) {
		this.amPm = amPm;
	}

	public String getHour() {
		this.hour = hourFormat.format(this.getNextSalesActionDate());
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		this.minute = minuteFormat.format(this.getNextSalesActionDate());
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

//	public BigDecimal getStatusId() {
//		return statusId;
//	}
//
//	public void setStatusId(BigDecimal statusId) {
//		this.statusId = statusId;
//	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public BigDecimal getActionPriority() {
		return actionPriority;
	}

	public void setActionPriority(BigDecimal actionPriority) {
		this.actionPriority = actionPriority;
	}

	public BigDecimal getProspectSalesActionId() {
		return prospectSalesActionId;
	}

	public void setProspectSalesActionId(BigDecimal prospectSalesActionId) {
		this.prospectSalesActionId = prospectSalesActionId;
	}
	
	public Timestamp getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(Timestamp dailyDate) {
		this.dailyDate = dailyDate;
	}

	public BigDecimal getActionNotificationflag() {
		return actionNotificationflag;
	}

	public void setActionNotificationflag(BigDecimal actionNotificationflag) {
		this.actionNotificationflag = actionNotificationflag;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getActionNote() {
		return actionNote;
	}

	public void setActionNote(String actionNote) {
		this.actionNote = actionNote;
	}

	public BigDecimal getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(BigDecimal actionStatus) {
		this.actionStatus = actionStatus;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public BigDecimal getProspectCampaignId() {
		return prospectCampaignId;
	}

	public void setProspectCampaignId(BigDecimal prospectCampaignId) {
		this.prospectCampaignId = prospectCampaignId;
	}

	public BigDecimal getProspectCampaignSequence() {
		return prospectCampaignSequence;
	}

	public void setProspectCampaignSequence(BigDecimal prospectCampaignSequence) {
		this.prospectCampaignSequence = prospectCampaignSequence;
	}

	public List getStatusIdList() {
		return statusIdList;
	}

	public void setStatusIdList(List statusIdList) {
		this.statusIdList = statusIdList;
	}

	public List getTerritoryIdList() {
		return territoryIdList;
	}

	public void setTerritoryIdList(List territoryIdList) {
		this.territoryIdList = territoryIdList;
	}

	public List getLobIdList() {
		return lobIdList;
	}

	public void setLobIdList(List lobIdList) {
		this.lobIdList = lobIdList;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
