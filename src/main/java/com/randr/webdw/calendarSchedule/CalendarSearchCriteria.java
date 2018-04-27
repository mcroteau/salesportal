package com.randr.webdw.calendarSchedule;

import java.util.List;

public class CalendarSearchCriteria {
	
	public String calendarType;
	public String territoryId;
	public List territoryIdList;
	public List statusIdList;
	public List lobIdList;
	public String statusId;
	public String selectDate;
	
	
	public String getCalendarType() {
		return calendarType;
	}
	public void setCalendarType(String calendarType) {
		this.calendarType = calendarType;
	}
	public String getSelectDate() {
		return selectDate;
	}
	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getTerritoryId() {
		return territoryId;
	}
	public void setTerritoryId(String territoryId) {
		this.territoryId = territoryId;
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
	
}
