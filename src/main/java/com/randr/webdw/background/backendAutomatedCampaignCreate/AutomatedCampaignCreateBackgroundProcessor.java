package com.randr.webdw.background.backendAutomatedCampaignCreate;

import java.util.Timer;

import com.randr.webdw.AppSettings;
import com.randr.webdw.background.AbstractImportBackgroundProcessor;


/**
 * @author randr
 * 
 */
public class AutomatedCampaignCreateBackgroundProcessor  extends AbstractImportBackgroundProcessor{
	
	private static final int START_AFTER_MILLISECONDS = 4 * 60 * 1000;
	private static final int MINUTES_PER_HOUR = 60;
	private static final int MILLISECONDS_PER_MINUTE = 1000;

	//private IAppScmportalSettings appSettings;
	
	
	public AutomatedCampaignCreateBackgroundProcessor(){
		
	}

    /**
     * Method init.
     */
    public void init() {
    	if(isEnabled("AUTOMATED_CAMPAIGN_CREATE_ENABLED")){
    		int repeatEveryMilliseconds = getInterval("AUTOMATED_CAMPAIGN_CREATE_SCHEDULE_MINUTES");
    		System.out.println("SP - Email Sales Action Send is Enabled, runs every " +  AppSettings.getParm("AUTOMATED_CAMPAIGN_CREATE_SCHEDULE_MINUTES") + " minutes");
            Timer timer = new Timer(false);
            AutomatedCampaignCreateTimerTask task = new AutomatedCampaignCreateTimerTask();

            int startTimeInMilliseconds = 0;
            if(hasStartTime("AUTOMATED_CAMPAIGN_CREATE_SCHEDULE_START_TIME")) {
            	startTimeInMilliseconds = getStartTimeInMillisecond("AUTOMATED_CAMPAIGN_CREATE_SCHEDULE_START_TIME");
            } else {
            	startTimeInMilliseconds = START_AFTER_MILLISECONDS;
            }
            timer.scheduleAtFixedRate(task, startTimeInMilliseconds, repeatEveryMilliseconds);
        }
    }
}


