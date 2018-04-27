package com.randr.webdw.background.backendProspectImport;

import java.util.Timer;

import com.randr.webdw.AppSettings;
import com.randr.webdw.background.AbstractImportBackgroundProcessor;


/**
 * @author randr
 * 
 */
public class ProspectImportBackgroundProcessor  extends AbstractImportBackgroundProcessor{
	
	private static final int START_AFTER_MILLISECONDS = 2 * 60 * 1000;
	private static final int MINUTES_PER_HOUR = 60;
	private static final int MILLISECONDS_PER_MINUTE = 1000;

	//private IAppScmportalSettings appSettings;
	
	
	public ProspectImportBackgroundProcessor(){
		
	}

    /**
     * Method init.
     */
    public void init() {
    	if(isEnabled("PROSPECT_IMPORT_ENABLED")){
    		int repeatEveryMilliseconds = getInterval("PROSPECT_IMPORT_SCHEDULE_MINUTES");
    		System.out.println("SP - Prospect Import is Enabled, runs every " +  AppSettings.getParm("PROSPECT_IMPORT_SCHEDULE_MINUTES") + " minutes");
            Timer timer = new Timer(false);
            ProspectImportTimerTask task = new ProspectImportTimerTask();

            int startTimeInMilliseconds = 0;
            if(hasStartTime("PROSPECT_IMPORT_SCHEDULE_START_TIME")) {
            	startTimeInMilliseconds = getStartTimeInMillisecond("PROSPECT_IMPORT_SCHEDULE_START_TIME");
            } else {
            	startTimeInMilliseconds = START_AFTER_MILLISECONDS;
            }
            timer.scheduleAtFixedRate(task, startTimeInMilliseconds, repeatEveryMilliseconds);
        }
    }
}


