package com.randr.webdw.background.backendTerritoryChange;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.territoryChange.TerritoryChangeDetails;
import com.randr.webdw.territoryChange.TerritoryChangeView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserView;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;
import com.randr.webdw.AppSettings;
import com.randr.webdw.background.AbstractImportTimerTask;
import com.randr.webdw.background.ImportBackgroundResult;
import com.randr.webdw.calendarSchedule.CalendarScheduleDetails;
import com.randr.webdw.calendarSchedule.CalendarScheduleView;
import com.randr.webdw.importData.importProcessor.ProspectImportParameters;
import com.randr.webdw.importData.importProcessor.ProspectImportProcessor;
import com.randr.webdw.importData.importProcessorAbstract.ImportProcessorResult;

/**
 * @author randr
 * 
 */
public class TerritoryChangeTimerTask extends AbstractImportTimerTask {
    
    /**
     * Method run.
     */
    public void run() {
        performTerritoryChange();
    }

	private void performTerritoryChange() {
		try {
			openConnection();
			
			TerritoryDetails searchTerritoryDetails = new TerritoryDetails();
			TerritoryView searchTerritoryView = new TerritoryView();
			searchTerritoryView.fillWithElements(connection, TerritoryView.FILL_TYPE_ALL, searchTerritoryDetails);
			
			TerritoryDetails territoryDetails;
			
			System.out.println("performing territory change.....");
			for(int i = 0; i < searchTerritoryView.getElements().size(); i++){
				
				territoryDetails = (TerritoryDetails)searchTerritoryView.getElement(i);
			
				if(territoryDetails.getReturnTerritoryId() != null){
					
					ProspectDetails searchProspectDetails = new ProspectDetails();
					ProspectView prospectView = new ProspectView();
					
					searchProspectDetails.setTerritoryId(territoryDetails.getTerritoryId());
					prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, searchProspectDetails);

					for(int j = 0; j < prospectView.getElements().size(); j++){
						ProspectDetails prospectDetails = (ProspectDetails)prospectView.getElement(j);
						GregorianCalendar scheduledReturnDate = new GregorianCalendar();
						if (prospectDetails.getTerritoryChangeDate()!=null){
							Timestamp dateChanged = prospectDetails.getTerritoryChangeDate() ;
							//System.out.println("===== dateChanged= " +prospectDetails.getTerritoryChangeDate() + " prospect= " + prospectDetails.getProspectId());
							scheduledReturnDate = new GregorianCalendar(dateChanged.getYear() + 1900, 
									dateChanged.getMonth(), dateChanged.getDate() + territoryDetails.getReturnTime().intValue());
							
							
							GregorianCalendar todaysDate = new GregorianCalendar();
							
							if(DateUtilities.getSqlDate(scheduledReturnDate).compareTo(DateUtilities.getSqlDate(todaysDate)) <= 0 ){
								
								changeTerritoryId(prospectDetails, territoryDetails);
								performTerritoryChangeAudit(prospectDetails, territoryDetails);
								//System.out.println("tId - " + territoryDetails.getTerritoryId() + ",  pId - " + prospectDetails.getProspectId() 
								//		+ ", " + DateUtilities.getSqlDate(scheduledReturnDate) + " - " + DateUtilities.getSqlDate(todaysDate));
								
							}
						}	
					}

				}
				
			}
			commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
	
	}
	
    private void performTerritoryChangeAudit(ProspectDetails prospectDetails, TerritoryDetails territoryDetails) throws Exception {
		TerritoryChangeDetails territoryChangeDetails = new TerritoryChangeDetails();
		TerritoryChangeView territoryChangeView = new TerritoryChangeView();
		territoryChangeDetails.setUserId(UserDetails.USER_TYPE_ADMIN);
		territoryChangeDetails.setOriginalTerritoryId(territoryDetails.getTerritoryId());
		territoryChangeDetails.setNewTerritoryId(territoryDetails.getReturnTerritoryId());
		territoryChangeDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
		territoryChangeDetails.setAutomatedTerritoryChange(TerritoryChangeDetails.AUTOMATED_TERRITORY_CHANGE);
		territoryChangeView.doAction(connection, "INSERT", TerritoryChangeView.FILL_TYPE_ALL, territoryChangeDetails);
		
	}

	private void changeTerritoryId(ProspectDetails prospectDetails, TerritoryDetails territoryDetails) throws Exception {
		prospectDetails.setTerritoryId(territoryDetails.getReturnTerritoryId());
		ProspectView prospectView = new ProspectView();
		prospectView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC,prospectDetails);
	}

	private GregorianCalendar getGregorianCalendar (GregorianCalendar gregorianCalendar, 
    		String scheduledDayStr) throws Exception{

		gregorianCalendar.add(GregorianCalendar.DAY_OF_MONTH, new BigDecimal(scheduledDayStr).intValue());
		
		return gregorianCalendar;
	}

}
