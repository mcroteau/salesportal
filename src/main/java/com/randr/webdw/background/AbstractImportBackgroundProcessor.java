package com.randr.webdw.background;
   
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.GregorianCalendar;


import com.randr.webdw.AppSettings;
import com.randr.webdw.util.Utilities;

public abstract class AbstractImportBackgroundProcessor{
	
	protected static final int MINUTES_PER_HOUR = 60;
	protected static final int MILLISECONDS_PER_MINUTE = 1000;
	protected AbstractImportTimerTask task;
    protected Connection connection = null;

	public abstract void init();
	
	protected boolean isEnabled(String parm) {
		if (AppSettings.getParm(parm).equalsIgnoreCase("TRUE")||
        		AppSettings.getParm(parm).equalsIgnoreCase("YES")) {
			return true;
		} else {
			return false;
		}
	}
	
	protected boolean hasStartTime(String parm) {
		if(!Utilities.nullToBlank(AppSettings.getParm(parm)).equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	protected int getStartTimeInMillisecond(String parm) {
		int startingHour = Integer.parseInt(AppSettings.getParm(parm));
		
		GregorianCalendar currentCalendar = new GregorianCalendar();
		GregorianCalendar workingCalendar 
			= new GregorianCalendar(currentCalendar.get(GregorianCalendar.YEAR),
					currentCalendar.get(GregorianCalendar.MONTH), 
					currentCalendar.get(GregorianCalendar.DATE), 
					startingHour, 0);
		
		return 	getStartTimeInMillisecond(currentCalendar, workingCalendar); 
	}
	
	protected int getStartTimeInMillisecond(GregorianCalendar currentCalendar, GregorianCalendar workingCalendar) {
		if (currentCalendar.before(workingCalendar)) {
			return (int)(workingCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis());
		} else if (currentCalendar.after(workingCalendar)) {
			workingCalendar.add(GregorianCalendar.DATE, 1);
			return (int)(workingCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis());
		} else {
			return 0;
		}
	}
	
	protected int getInterval(String parm) {
		return  new BigDecimal(AppSettings.getParm(parm)).intValue() 
			* MINUTES_PER_HOUR * MILLISECONDS_PER_MINUTE;
	}
	
	public void cancelTimerTask() {
		task.cancel();
	}
}
