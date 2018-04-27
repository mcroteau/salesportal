package com.randr.webdw.background.backendEmailSalesActionSend;

import java.util.Timer;

import com.randr.webdw.AppSettings;
import com.randr.webdw.background.AbstractImportBackgroundProcessor;


/**
 * @author randr
 * 
 */
public class EmailSalesActionSendBackgroundProcessor  extends AbstractImportBackgroundProcessor{
	
	private static final int START_AFTER_MILLISECONDS = 3 * 60 * 1000;
	private static final int MINUTES_PER_HOUR = 60;
	private static final int MILLISECONDS_PER_MINUTE = 1000;

	//private IAppScmportalSettings appSettings;
	
	
	public EmailSalesActionSendBackgroundProcessor(){
		
	}

    /**
     * Method init.
     */
    public void init() {
    	if(isEnabled("EMAIL_SALES_ACTION_SEND_ENABLED")){
    		int repeatEveryMilliseconds = getInterval("EMAIL_SALES_ACTION_SEND_SCHEDULE_MINUTES");
    		System.out.println("SP - Email Sales Action Send is Enabled, runs every " +  AppSettings.getParm("EMAIL_SALES_ACTION_SEND_SCHEDULE_MINUTES") + " minutes");
            Timer timer = new Timer(false);
            EmailSalesActionSendTimerTask task = new EmailSalesActionSendTimerTask();

            int startTimeInMilliseconds = 0;
            if(hasStartTime("EMAIL_SALES_ACTION_SEND_SCHEDULE_START_TIME")) {
            	startTimeInMilliseconds = getStartTimeInMillisecond("EMAIL_SALES_ACTION_SEND_SCHEDULE_START_TIME");
            } else {
            	startTimeInMilliseconds = START_AFTER_MILLISECONDS;
            }
            timer.scheduleAtFixedRate(task, startTimeInMilliseconds, repeatEveryMilliseconds);
        }
    }
}


