package com.randr.webdw.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.randr.webdw.user.UserProfile;




/**
 */
public class DateUtilities {
    private static final String[] monthAbrevDescription = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static final String[] monthDescription = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static final int YEAR_CONVERSION = 1900;
    /**
     * Method formatDate.
     * @param dateIn Date
     * @param sFormat String
     * @return String
     */
    public static String formatDate(Date dateIn, String sFormat) {
        if (dateIn == null) {
            return "";
        }

        try {
            SimpleDateFormat formatterConverted = new SimpleDateFormat(sFormat);

            return formatterConverted.format(dateIn);
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Method formatDate.
     * @param dateIn Date
     * @param userProfile userProfile
     * @return String
     */
    public static String formatDate(Timestamp dateIn, UserProfile userProfile) {
    	String sFormat = userProfile.getDateFormat();
    	BigDecimal timeOffset = userProfile.getTimeOffset();
    	if (dateIn == null) {
            return "";
        }
    	if(timeOffset != null) {
    		dateIn.setHours(dateIn.getHours() + timeOffset.intValue());
    	}
        

        try {
            SimpleDateFormat formatterConverted = new SimpleDateFormat(sFormat);

            return formatterConverted.format(dateIn);
        } catch (Exception e) {
            return "";
        }
    }
    
    public static String formatDate(Timestamp dateIn, UserProfile userProfile, String overrideFormat) {
    	String sFormat = overrideFormat;
    	BigDecimal timeOffset = userProfile.getTimeOffset();
    	if(timeOffset != null) {
    		dateIn.setHours(dateIn.getHours() + timeOffset.intValue());
    	}
        if (dateIn == null) {
            return "";
        }

        try {
            SimpleDateFormat formatterConverted = new SimpleDateFormat(sFormat);

            return formatterConverted.format(dateIn);
        } catch (Exception e) {
            return "";
        }
    }
    
    public static String formatDateAndTime(Timestamp dateIn, UserProfile userProfile) {
    	String sFormat = userProfile.getDateFormat() + " hh:mm a";
    	BigDecimal timeOffset = userProfile.getTimeOffset();
    	if(timeOffset != null) {
    		dateIn.setHours(dateIn.getHours() + timeOffset.intValue());
    	}
        if (dateIn == null) {
            return "";
        }

        try {
            SimpleDateFormat formatterConverted = new SimpleDateFormat(sFormat);

            return formatterConverted.format(dateIn);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param thisDate - date from csv file
     * @return timeStamp 
     * convert incoming date field into the following format mm/DD/yyyy
     * Handles the following format: m/DD/yyyy, yyyy/mm/DD, mm/DD/yy, yyyy-mm-DD
     * Used for editing imported CSV data
     */
    public static Timestamp getDateValue(String thisDate){
    	String dateString = thisDate;
    	//check for null
    	if(dateString==null){
    		return null;
    	}
    	if(dateString.substring(0).compareTo("0")==0){
    		return null;
    	}
    	if(dateString.substring(0).compareTo("?")==0){
    		return null;
    	}
    	//check #1 is date in this format 1/15/2009, if yes, the put the leading 0 there
    	if (dateString.substring(1,2).compareTo("/")==0){// adding leading zeros
    		dateString = "0".concat(thisDate);
    	}
//    	check #2 is date in this format 01/5/2009, if yes, the put the leading 0 in front of the month
    	if (dateString.substring(2,3).compareTo("/")==0 && dateString.substring(4,5).compareTo("/")==0){// adding leading zeros
    		dateString = dateString.substring(0,3).concat("0").concat(dateString.substring(3));
    	}
    	//check #3 is date in this format 2009/01/15, if yes, then change to 01/15/2009
    	if (dateString.substring(4,5).compareTo("/")==0){
    		dateString = dateString.substring(5).concat("/").concat(dateString.substring(0, 4));
    	}
//    	check #4 is date in this format 2009-01-15 
    	if (dateString.substring(4,5).compareTo("-")==0){
    		dateString = dateString.substring(5,7).concat("/").concat(dateString.substring(8)).concat("/").concat(dateString.substring(0, 4));
    	}
    	//check #5 is date in this format 01/15/09, missing the 20/
    	if(dateString.substring(6,7).compareTo("2")!=0){
    		dateString = dateString.substring(0,6).concat("20").concat(dateString.substring(6));
    	}
    	//System.out.println("Date in = " + thisDate + " date out= " + getSQLTimestamp(dateString + " 00:00:00"));
		return getSQLTimestamp(dateString + " 00:00:00");
	}
    /**
     * Method getSqlDate.
     * @param date Date
     * @return java.sql.Date
     */
    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getYear(), date.getMonth(), date.getDate());
    }

    /**
     * Method getSqlDate.
     * @param gregorianCalendar GregorianCalendar
     * @return java.sql.Date
     */
    public static java.sql.Date getSqlDate(GregorianCalendar gregorianCalendar) {
        return new java.sql.Date(gregorianCalendar.get(GregorianCalendar.YEAR) - 1900,
                                 gregorianCalendar.get(GregorianCalendar.MONTH),
                                 gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH));
    }

    /**
     * Method getSqlDate.
     * @param timestamp java.sql.Timestamp
     * @return java.sql.Date
     */
    public static java.sql.Date getSqlDate(java.sql.Timestamp timestamp) {
        return new java.sql.Date(timestamp.getYear(), timestamp.getMonth(), timestamp.getDate());
    }

    /**
     * Method getSQLDate.
     * @param dateStr String
     * @return java.sql.Date
     */
    public static java.sql.Date getSQLDate(String dateStr) {
        if (Utilities.nullToBlank(dateStr).equals("")) {
            return null;
        }

        Vector vDateToken = Utilities.tokenize(dateStr, "/");
        int year = Integer.parseInt((String) vDateToken.elementAt(2));

        if (year < 100) {
            year = 2000 + year;
        }

        return new java.sql.Date(year - 1900, Integer.parseInt((String) vDateToken.elementAt(0)) - 1, Integer.parseInt((String) vDateToken.elementAt(1)));
    }
    
    /**
     * Method getSQLDate.
     * @param dateStr String
     * @return java.sql.Date
     */
    public static java.sql.Date getSQLDate(String dateStr,String inComingFormat) {
    	try {
            if (Utilities.nullToBlank(dateStr).equals("")) {
                return null;
            }

            if(inComingFormat==null){
            	inComingFormat="MM/dd/yyyy";
            }
            
            Vector vDateToken = Utilities.tokenize(dateStr, "/");
            int year = Integer.parseInt((String) vDateToken.elementAt(2));

            if (year < 100) {
                year += 2000;
            }
            if (inComingFormat.equals("dd/MM/yy") || inComingFormat.equals("dd/MM/yyyy")){
            	return new java.sql.Date(year - 1900,
                                     Integer.parseInt((String) vDateToken.elementAt(1)) - 1,
                                     Integer.parseInt((String) vDateToken.elementAt(0)));
            }else {  //default is MM/dd/yyyy or MM/dd/yy 
            	return new java.sql.Date(year - 1900,
                        Integer.parseInt((String) vDateToken.elementAt(0)) - 1,
                        Integer.parseInt((String) vDateToken.elementAt(1)));
            }
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Method getDate.
     * @param dateStr String
     * @return Date
     */
    public static Date getDate(String dateStr) {
        if (Utilities.nullToBlank(dateStr).equals("")) {
            return null;
        }

        Vector vDateToken = Utilities.tokenize(dateStr, "/");
        int year = Integer.parseInt((String) vDateToken.elementAt(2));

        if (year < 100) {
            year = 2000 + year;
        }

        return new Date(year - 1900, Integer.parseInt((String) vDateToken.elementAt(0)) - 1, Integer.parseInt((String) vDateToken.elementAt(1)));
    }

    /**
     * Method getSQLTimestamp.
     * @param timestampStr String
     * @return java.sql.Timestamp
     */
    public static java.sql.Timestamp getSQLTimestamp(String timestampStr) {
        if (Utilities.nullToBlank(timestampStr).equals("")) {
            return null;
        }

        Vector vDateTimeToken = Utilities.tokenize(timestampStr, " ");
        Vector vDateToken = Utilities.tokenize((String) vDateTimeToken.elementAt(0), "/");
        Vector vTimeToken = Utilities.tokenize((String) vDateTimeToken.elementAt(1), ":");
        int year = Integer.parseInt((String) vDateToken.elementAt(2));

        if (year < 100) {
            year = 2000 + year;
        }

        return new java.sql.Timestamp(year - 1900,
                                      Integer.parseInt((String) vDateToken.elementAt(0)) - 1,
                                      Integer.parseInt((String) vDateToken.elementAt(1)),
                                      Integer.parseInt((String) vTimeToken.elementAt(0)),
                                      Integer.parseInt((String) vTimeToken.elementAt(1)),
                                      0,
                                      0);
    }
    
    /**
     * Method getSQLTimestamp.
     * @param timestampStr String
     * @return java.sql.Timestamp
     */
    public static java.sql.Timestamp getSQLTimestamp(String timestampStr, String inComingFormat) {
    	 // inComingFormat will be either MM/dd/yy or dd/MM/yy
    	//timestampStr must be in the MM/dd/yyyy mm:ss format, e.g. 07/20/2004 20:49

        try {
            if (Utilities.nullToBlank(timestampStr).equals("")) {
                return null;
            }
           
            if(inComingFormat==null){
            	inComingFormat="MM/dd/yyyy";
            }
            
        Vector vDateTimeToken = Utilities.tokenize(timestampStr, " ");
        Vector vDateToken = Utilities.tokenize((String) vDateTimeToken.elementAt(0), "/");
        Vector vTimeToken = Utilities.tokenize((String) vDateTimeToken.elementAt(1), ":");
        int year = Integer.parseInt((String) vDateToken.elementAt(2));

        if (year < 100) {
            year = 2000 + year;
        }

        if (inComingFormat.equals("MM/dd/yy") || inComingFormat.equals("MM/dd/yyyy")){
            return new java.sql.Timestamp(year - 1900,
                Integer.parseInt((String) vDateToken.elementAt(0)) - 1,
                Integer.parseInt((String) vDateToken.elementAt(1)),
                Integer.parseInt((String) vTimeToken.elementAt(0)),
                Integer.parseInt((String) vTimeToken.elementAt(1)),
                0, 0);
        }else if(inComingFormat.equals("dd/MM/yy") || inComingFormat.equals("dd/MM/yyyy")){
            return new java.sql.Timestamp(year - 1900,
                Integer.parseInt((String) vDateToken.elementAt(1))- 1,
                Integer.parseInt((String) vDateToken.elementAt(0)),
                Integer.parseInt((String) vTimeToken.elementAt(0)),
                Integer.parseInt((String) vTimeToken.elementAt(1)),
                0, 0);
        }
        return null;
    } catch (Exception e) {
        return null;
    }
}
    /**
     * Method getSQLTime.
     * @param timeStr String
     * @return java.sql.Time
     */
    public static java.sql.Time getSQLTime(String timeStr) {
        if (Utilities.nullToBlank(timeStr).equals("")) {
            return null;
        }

        Vector vTimeToken = Utilities.tokenize(timeStr, ":");

        return new java.sql.Time(Integer.parseInt((String) vTimeToken.elementAt(0)), Integer.parseInt((String) vTimeToken.elementAt(1)), 0);
    }

    /**
     * Method getCurrentSQLDate.
     * @return java.sql.Date
     */
    public static java.sql.Date getCurrentSQLDate() {
        Date currentDate = new Date();
        java.sql.Date currentSQLDate = new java.sql.Date(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate());

        return currentSQLDate;
    }

    /**
     * Method getCurrentSQLTimestamp.
     * @return java.sql.Timestamp
     */
    public static java.sql.Timestamp getCurrentSQLTimestamp() {
        Date currentDate = new Date();
        java.sql.Timestamp currentSQLTimestamp = new java.sql.Timestamp(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate(), currentDate.getHours(), currentDate.getMinutes(), currentDate.getSeconds(), 0);

        return currentSQLTimestamp;
    }
    /**
     * Method getCurrentSQLTimestamp.
     * @return java.sql.Timestamp
     */
    public static java.sql.Timestamp getCurrentSQLTimestamp(int offset) {
       // Date currentDate = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        //calendar.get(Calendar.DATE);
        calendar.add(Calendar.HOUR, offset);
        java.sql.Timestamp currentSQLTimestamp = new java.sql.Timestamp(calendar.get(Calendar.YEAR)-1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 
        		calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), 0);

        return currentSQLTimestamp;
    }

    
    /**
     * Method getCurrentSQLTimestamp.
     * @return java.sql.Timestamp
     */
    public static GregorianCalendar  getGregorianOffset(int offset) {
       // Date currentDate = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        //calendar.get(Calendar.DATE);
        calendar.add(Calendar.HOUR, offset);
        

        return calendar;
    }


    /**
     * Method getMonthBeginDate.
     * @return Date
     */
    public static Date getMonthBeginDate() {
        return getMonthBeginDate(new Date());
    }

    /**
     * Method getMonthBeginDate.
     * @param date Date
     * @return Date
     */
    public static Date getMonthBeginDate(Date date) {
        Date monthBeginDate = (Date) date.clone();
        monthBeginDate.setDate(1);

        if (!(monthBeginDate instanceof java.sql.Date)) {
            monthBeginDate.setHours(0);
            monthBeginDate.setMinutes(0);
            monthBeginDate.setSeconds(0);
        }

        monthBeginDate.toLocaleString();
        return monthBeginDate;
    }

    /**
     * Method getMonthEndDate.
     * @return Date
     */
    public static Date getMonthEndDate() {
        return getMonthEndDate(new Date());
    }

    /**
     * Method getMonthEndDate.
     * @param date Date
     * @return Date
     */
    public static Date getMonthEndDate(Date date) {
        Date monthEndDate = (Date) date.clone();
        monthEndDate.setDate(1);
        monthEndDate.setMonth(monthEndDate.getMonth() + 1);
        monthEndDate.setDate(0);

        if (!(monthEndDate instanceof java.sql.Date)) {
            monthEndDate.setHours(23);
            monthEndDate.setMinutes(59);
            monthEndDate.setSeconds(59);
        }

        monthEndDate.toLocaleString();
        return monthEndDate;
    }

    /**
     * Method getWeekBeginDate.
     * @return Date
     */
    public static Date getWeekBeginDate() {
        return getWeekBeginDate(new Date());
    }

    /**
     * Method getWeekBeginDate.
     * @param date Date
     * @return Date
     */
    public static Date getWeekBeginDate(Date date) {
        Date weekBeginDate = (Date) date.clone();
        if (date.getDay() == 0) { // sunday
            weekBeginDate.setDate(date.getDate() - 6);
        } else {
            weekBeginDate.setDate(date.getDate() - date.getDay() + 1);
        }

        if (!(weekBeginDate instanceof java.sql.Date)) {
            weekBeginDate.setHours(0);
            weekBeginDate.setMinutes(0);
            weekBeginDate.setSeconds(0);
        }

        weekBeginDate.toLocaleString();
        return weekBeginDate;
    }

    /**
     * Method getWeekEndDate.
     * @return Date
     */
    public static Date getWeekEndDate() {
        return getWeekEndDate(new Date());
    }

    /**
     * Method getWeekEndDate.
     * @param date Date
     * @return Date
     */
    public static Date getWeekEndDate(Date date) {
        Date weekEndDate = (Date) date.clone();
        if (date.getDay() != 0) { // !=sunday
            weekEndDate.setDate(date.getDate() + 7 - date.getDay());
        }

        if (!(weekEndDate instanceof java.sql.Date)) {
            weekEndDate.setHours(23);
            weekEndDate.setMinutes(59);
            weekEndDate.setSeconds(59);
        }

        weekEndDate.toLocaleString();
        return weekEndDate;
    }

    /**
     * Method getMinutesFromNumber.
     * @param number BigDecimal
     * @return BigDecimal
     */
    public static BigDecimal getMinutesFromNumber(BigDecimal number) {
        BigDecimal hours = number.setScale(0, BigDecimal.ROUND_FLOOR);
        return number.subtract(hours).multiply(new BigDecimal(60)).setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Method getNumberFromHoursAndMinutes.
     * @param hours BigDecimal
     * @param minutes BigDecimal
     * @return BigDecimal
     */
    public static BigDecimal getNumberFromHoursAndMinutes(BigDecimal hours, BigDecimal minutes) {
        return hours.add(minutes.multiply(new BigDecimal(100)).divide(new BigDecimal(60 * 100), 4, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * Method getNumberFromHoursAndMinutes.
     * @param hours int
     * @param minutes int
     * @return BigDecimal
     */
    public static BigDecimal getNumberFromHoursAndMinutes(int hours, int minutes) {
        return getNumberFromHoursAndMinutes(new BigDecimal(hours), new BigDecimal(minutes));
    }

    /**
     * Method getMonthAbrevDescription.
     * @param month int
     * @return String
     */
    public static String getMonthAbrevDescription(int month) {
        return monthAbrevDescription[month - 1];
    }

    /**
     * Method getMonthAbrevDescription.
     * @param month BigDecimal
     * @return String
     */
    public static String getMonthAbrevDescription(BigDecimal month) {
        return monthAbrevDescription[month.intValue() - 1];
    }

    /**
     * Method getMonthDescription.
     * @param month int
     * @return String
     */
    public static String getMonthDescription(int month) {
        return monthDescription[month - 1];
    }

    /**
     * Method getMonthDescription.
     * @param month BigDecimal
     * @return String
     */
    public static String getMonthDescription(BigDecimal month) {
        return monthDescription[month.intValue() - 1];
    }

    /**
     * Method compareSQLDates.
     * @param date1 java.sql.Date
     * @param date2 java.sql.Date
     * @return int
     */
    public static int compareSQLDates(java.sql.Date date1, java.sql.Date date2) {
        int ymd1 = Integer.parseInt(formatDate(date1, "yyyyMMdd"));
        int ymd2 = Integer.parseInt(formatDate(date2, "yyyyMMdd"));

        if (ymd1 > ymd2) {
            return 1;
        } else if (ymd1 < ymd2) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Method main.
     * @param args String[]
     */
    public static void main(String[] args) {

    }
    
    public static Timestamp getWeekStartTimestamp(Timestamp dayOfWeek){
    	GregorianCalendar calendar = new GregorianCalendar(dayOfWeek.getYear()+ 1900, dayOfWeek.getMonth(), 
    			dayOfWeek.getDate(), dayOfWeek.getHours(), dayOfWeek.getMinutes(),
    			dayOfWeek.getSeconds());

    	Date tempDate = calendar.getTime();
    	
    	int week = calendar.get(GregorianCalendar.WEEK_OF_YEAR);
    	for(int i = 0; i <= 7; i++){
    		if(calendar.get(GregorianCalendar.DAY_OF_WEEK)== calendar.getFirstDayOfWeek()){
    			tempDate = calendar.getTime();
    		} else {
    			calendar.add(GregorianCalendar.DATE, - 1);
    		}
    	}
    	
    	return new Timestamp(tempDate.getYear(), tempDate.getMonth(), tempDate.getDate(),
    			tempDate.getHours(), tempDate.getMinutes(), tempDate.getSeconds(), 0);
    	
    }
    
    public static Timestamp getWeekEndTimestamp(Timestamp dayOfWeek){
    	GregorianCalendar calendar = new GregorianCalendar(dayOfWeek.getYear()+ YEAR_CONVERSION, dayOfWeek.getMonth(), 
    			dayOfWeek.getDate(), dayOfWeek.getHours(), dayOfWeek.getMinutes(),
    			dayOfWeek.getSeconds());

    	Date tempDate = calendar.getTime();
    	
    	int week = calendar.get(GregorianCalendar.WEEK_OF_YEAR);
    	for(int i = 0; i <= 7; i++){
    		if(calendar.get(GregorianCalendar.WEEK_OF_YEAR)== week){
    			tempDate = calendar.getTime();
    		}
    		calendar.add(GregorianCalendar.DATE, + 1);
    	}
    	
    	return new Timestamp(tempDate.getYear(), tempDate.getMonth(), tempDate.getDate(),
    			tempDate.getHours(), tempDate.getMinutes(), tempDate.getSeconds(), 0);
    	
    }
    
    public static Timestamp getSqlTimestamp(String dateString, String hourString, 
    		String minutesString, String amPmString) {
    	Date date = getSQLDate(dateString);
    	int hours = Integer.parseInt(hourString);
    	int minutes = Integer.parseInt(minutesString);
    	
    	if(amPmString.toUpperCase().equals("PM") && hours!=12) {
    		hours = hours + 12;
    	}

    	return new Timestamp(date.getYear(), date.getMonth(), date.getDate(),
    			hours, minutes, 0, 0);
    }
    
    public static long formatDateToTimeInDays(Timestamp date){
    	
    	long dateInDays = date.getTime()/(24*60*60*1000);
    	
    	return dateInDays;
    }
    
    /**
     * @param timestamp
     * @param daysToAdd
     * @return
     * send in a time stamp and number of days to add,
     * return the new timestamp
     */
    public static Timestamp addDaysToSQLTimestamp(Timestamp timestamp, BigDecimal daysToAdd) {
		//step 1 - convert the timestamp to Gregorian Calendar
		GregorianCalendar calendar = new GregorianCalendar(timestamp.getYear()+ 1900, timestamp.getMonth(), 
				timestamp.getDate(), timestamp.getHours(), timestamp.getMinutes(),
				timestamp.getSeconds());
		
		//step 2 - add the daysToAdd to get the new date
		if(daysToAdd!=null){
			calendar.add(calendar.DATE, daysToAdd.intValue());
		}
		//step 3 - convert the result back to timestamp
		java.sql.Timestamp newSQLTimestamp = new java.sql.Timestamp(calendar.get(Calendar.YEAR)-1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 
        		calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), 0);
		
		return newSQLTimestamp;
	}
}
