package com.randr.webdw.calendarSchedule;

import com.randr.webdw.mvcAbstract.AbstractView;

public class CalendarScheduleView extends AbstractView{
	
	/**
	 * No-Arg Constructor
	 *
	 */
	public CalendarScheduleView(){
	}

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.calendarSchedule.CalendarScheduleBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.calendarSchedule.CalendarScheduleDetails";
    }

}
