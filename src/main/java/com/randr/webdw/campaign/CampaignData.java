package com.randr.webdw.campaign;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.randr.webdw.campaignSalesAction.CampaignSalesActionDetails;
import com.randr.webdw.salesAction.SalesActionDetails;
import com.randr.webdw.salesAction.SalesActionView;

public class CampaignData {
	
	// Campaign Info
	private CampaignDetails campaignDetails;
	private List campaignSalesActionList;
	
	// Combobox Info
	private SalesActionView salesActionView;
	
	public CampaignData() {
		campaignDetails = new CampaignDetails();
		campaignSalesActionList = new ArrayList();
	}
	
	public void moveDownList(int index){
		List listOne  = campaignSalesActionList.subList(0, index);
		List listTwo  = campaignSalesActionList.subList(index, index + 1);
		List listThree  = campaignSalesActionList.subList(index +1, index + 2);
		List listFour = campaignSalesActionList.subList(index +2, campaignSalesActionList.size());
		Vector newList = new Vector();
		newList.addAll(listOne);
		newList.addAll(listThree);
		newList.addAll(listTwo);
		newList.addAll(listFour);
		campaignSalesActionList = newList;
	}
	
	public void moveUpList(int index){
		List listOne  = campaignSalesActionList.subList(0, index-1);
		List listTwo  = campaignSalesActionList.subList(index-1, index);
		List listThree  = campaignSalesActionList.subList(index, index + 1);
		List listFour = campaignSalesActionList.subList(index +1, campaignSalesActionList.size());
		Vector newList = new Vector();
		newList.addAll(listOne);
		newList.addAll(listThree);
		newList.addAll(listTwo);
		newList.addAll(listFour);
		campaignSalesActionList = newList;
	}
	
	public void removeFromList(int index){
		campaignSalesActionList.remove(index);
	}
	
	public void addToList(SalesActionDetails salesActionDetails){
		campaignSalesActionList.add(getCampaignSalesActionDetailsFromSalesActionDetails(salesActionDetails));
	}

	public CampaignDetails getCampaignDetails() {
		return campaignDetails;
	}
	public void setCampaignDetails(CampaignDetails campaignDetails) {
		this.campaignDetails = campaignDetails;
	}
	public List getCampaignSalesActionList() {
		return campaignSalesActionList;
	}
	public void setCampaignSalesActionList(List campaignSalesActionList) {
		this.campaignSalesActionList = campaignSalesActionList;
	}

	public SalesActionView getSalesActionView() {
		return salesActionView;
	}

	public void setSalesActionView(SalesActionView salesActionView) {
		this.salesActionView = salesActionView;
	}
	
	private CampaignSalesActionDetails getCampaignSalesActionDetailsFromSalesActionDetails(
			SalesActionDetails salesActionDetails) {
		CampaignSalesActionDetails campaignSalesActionDetails = new CampaignSalesActionDetails();
		campaignSalesActionDetails.setCampaignSalesActionId(null);
		campaignSalesActionDetails.setSalesActionId(salesActionDetails.getActionId());
		campaignSalesActionDetails.setSalesActionDescription(salesActionDetails.getAction());
		campaignSalesActionDetails.setSalesActionMandatoryDate(salesActionDetails.getMandatoryDate());
		campaignSalesActionDetails.setSendEmailDays(salesActionDetails.getSendEmailDays());
		return campaignSalesActionDetails;
	}
	
	
}
