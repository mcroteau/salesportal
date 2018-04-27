package com.randr.webdw.background.backendTerritoryChange;

import java.util.Timer;

import com.randr.webdw.AppSettings;
import com.randr.webdw.background.AbstractImportBackgroundProcessor;


/**
 * @author randr
 * 
 */
public class TerritoryChangeBackgroundProcessor  extends AbstractImportBackgroundProcessor{
	
	private static final int START_AFTER_MILLISECONDS = 2 * 60 * 1000;
	private static final int MINUTES_PER_HOUR = 60;
	private static final int MILLISECONDS_PER_MINUTE = 1000;

	//private IAppScmportalSettings appSettings;
	
	
	public TerritoryChangeBackgroundProcessor(){
		
	}

    /**
     * Method init.
     */
    public void init() {
    	if(isEnabled("PROSPECT_TERRITORY_CHANGE_ENABLED")){
    		int repeatEveryMilliseconds = getInterval("PROSPECT_TERRITORY_CHANGE_SCHEDULE_MINUTES");
    		System.out.println("SP - Territory Change is Enabled, runs every " +  AppSettings.getParm("PROSPECT_TERRITORY_CHANGE_SCHEDULE_MINUTES") + " minutes\n");
            Timer timer = new Timer(false);
            TerritoryChangeTimerTask task = new TerritoryChangeTimerTask();

            int startTimeInMilliseconds = 0;
            if(hasStartTime("PROSPECT_TERRITORY_CHANGE_SCHEDULE_START_TIME")) {
            	startTimeInMilliseconds = getStartTimeInMillisecond("PROSPECT_TERRITORY_CHANGE_SCHEDULE_START_TIME");
            } else {
            	startTimeInMilliseconds = START_AFTER_MILLISECONDS;
            }
            timer.scheduleAtFixedRate(task, startTimeInMilliseconds, repeatEveryMilliseconds);
        }
    }
}


