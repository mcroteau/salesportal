package com.randr.webdw.prospect;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.TimerTask;

import com.randr.webdw.AppSettings;
import com.randr.webdw.company.CompanyDetails;
import com.randr.webdw.company.CompanyView;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserView;
import com.randr.webdw.util.ConnectionPool;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.EmailUtilities;
import com.randr.webdw.util.Utilities;


/**
 */
public class ProspectTimerTask extends TimerTask {
    protected Connection connection = null;

    /**
     * Method run.
     */
    public void run() {
        sendNextSalesActionEmailNotification();
    }

    /**
     * Method openConnection.
     * @throws SQLException
     */
    protected void openConnection() throws SQLException {
        this.connection = ConnectionPool.getNewConnection();
    }

    /**
     * Method commit.
     * @throws SQLException
     */
    protected void commit() throws SQLException {
        this.connection.commit();
    }

    /**
     * Method finallyMethod.
     */
    protected void finallyMethod() {
        if (this.connection != null) {
            try {
                this.connection.rollback();
            } catch (Exception sql) {
            }

            try {
                this.connection.close();
            } catch (Exception sql) {
            }
        }
    }

    /**
     * Method doCatch.
     * @param e Exception
     */
    protected void doCatch(Exception e) {
        e.printStackTrace();
    }

//    /**
//     * Method sendNextSalesActionEmailNotification.
//     */
//    protected void sendNextSalesActionEmailNotificationOld() {
//        try {
//            openConnection();
//            GregorianCalendar gregorianCalendar = new GregorianCalendar();
//
//            String scheduledDayStr = AppSettings.getParm("SALES_ACTION_EMAIL_BEFORE_DAYS");
//            gregorianCalendar.add(GregorianCalendar.DAY_OF_MONTH, new BigDecimal(scheduledDayStr).intValue());
//
//            ProspectView prospectView = new ProspectView();
//            ProspectDetails prospectDetails = new ProspectDetails();
//            prospectDetails.setSearchNextSalesActionDate(DateUtilities.getSqlDate(gregorianCalendar));
//            prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_NEXT_SALES_ACTION_NOTIFY, prospectDetails);
//
//            for (int i = 0; i < prospectView.getElements().size(); i++) {
//                prospectDetails = (ProspectDetails) prospectView.getElements().elementAt(i);
//                if (prospectDetails.getNextSalesActionNotificationFlag().equals(new BigDecimal(0))) {
//                    if (sendNextSalesActionEmailNotificationToUsers(prospectDetails)) {
//                        ProspectView updateProspectView = new ProspectView();
//                        ProspectDetails updateProspectDetails = new ProspectDetails();
//                        updateProspectDetails.setProspectId(prospectDetails.getProspectId());
//                        updateProspectDetails.setNextSalesActionNotificationFlag(new BigDecimal(1));
//                        updateProspectView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_NEXT_SALES_ACTION_NOTIFY, updateProspectDetails);
//                        commit();
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            doCatch(e);
//        } finally {
//            finallyMethod();
//        }
//    }
    
    /**
     * Method sendNextSalesActionEmailNotification.
     */
    protected void sendNextSalesActionEmailNotification() {
        try {
            openConnection();
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            String scheduledDayStr = AppSettings.getParm("SALES_ACTION_EMAIL_BEFORE_DAYS");
            
            //1 - get prospectSalesActionView where NextSalesActionNotificationFlag = 0 and order by  salesman and actionDate 
            //     will need to add the salesman join based on prospect_id
            //2- loop by through the nextSales Actions when the salesman is not the same as the prior salesman get time offset, then calc the date by 
            //        using getGregorianOffset(int offset)  then add the scheduled DayStr 
            //3 - 2nd loop - for each action where the date is less than or equal to the DateUtilities.getSqlDate(gregorianCalendar
            //    and send email passing in prospect details and dateformat
            //	 update setNextSalesActionNotificationFlag(new BigDecimal(1)); 
                  
            ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
            ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
            prospectSalesActionDetails.setActionNotificationflag(new BigDecimal(0));
            prospectSalesActionView.fillWithElements(connection, ProspectSalesActionView.FILL_TYPE_NEXT_SALES_ACTION_NOTIFY, prospectSalesActionDetails);
            BigDecimal compareSalesman= new BigDecimal(0);	
            
            //getGregorianOffset
           
            for (int i = 0; i < prospectSalesActionView.getElements().size(); i++) {
            	prospectSalesActionDetails = (ProspectSalesActionDetails) prospectSalesActionView.getElements().elementAt(i);
            	if(prospectSalesActionDetails.getOwnerUserId().compareTo(compareSalesman)!=0){
            		//get the dateformat and get the time format and 
            		//GregorianCalendar gregorianCalendar = new GregorianCalendar();
            		gregorianCalendar = getGregorianCalendar(gregorianCalendar, scheduledDayStr, prospectSalesActionDetails.getOwnerUserId());
            		compareSalesman = prospectSalesActionDetails.getOwnerUserId();
            	}
//            	 next compare the dates and send the email 
            	//todo first add the number of dayes to ActionDate, then do the compare and decide if we send email yet.
            	//System.out.println("==Email date comparison, actionDate= " + DateUtilities.getSqlDate(prospectSalesActionDetails.getActionDate()) + "compareDate= "  + DateUtilities.getSqlDate(gregorianCalendar));
            	if( prospectSalesActionDetails.getActionDate()!= null && DateUtilities.getSqlDate(prospectSalesActionDetails.getActionDate()).compareTo(DateUtilities.getSqlDate(gregorianCalendar))<=0 ){
//        			get date format
        			String dateFormat = getDateFormat(prospectSalesActionDetails.getOwnerUserId());
        			ProspectView prospectView = new ProspectView();
        			ProspectDetails prospectDetails = new ProspectDetails();
        			prospectDetails.setProspectId(prospectSalesActionDetails.getProspectId());
        			prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_NEXT_SALES_ACTION_NOTIFY, prospectDetails);
        			if(prospectView.getElements().size()>0){
        				prospectDetails = (ProspectDetails)prospectView.getElement(0);
                         if (sendNextSalesActionEmailNotificationToUsers(prospectDetails, prospectSalesActionDetails, dateFormat)) {
                        	 ProspectSalesActionView updateSalesActionView = new ProspectSalesActionView();
                        	 ProspectSalesActionDetails updateSalesActionDetails = new ProspectSalesActionDetails();
                        	 updateSalesActionDetails.setProspectSalesActionId(prospectSalesActionDetails.getProspectSalesActionId());
                        	 //get view
                        	 updateSalesActionView.fillWithElements(connection, ProspectView.FILL_TYPE_NEXT_SALES_ACTION_NOTIFY, updateSalesActionDetails);
                        	 if(updateSalesActionView.getElements().size()>0){
                        		 updateSalesActionDetails = (ProspectSalesActionDetails)updateSalesActionView.getElement(0);
                        		 updateSalesActionDetails.setActionNotificationflag(new BigDecimal(1));
                        		 updateSalesActionView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_NEXT_SALES_ACTION_NOTIFY, updateSalesActionDetails);
                        		 commit();
                        	 }
                         }else{
//                        	 System.out.println("email notification was NOT sent");
                         }
                     }
        		}
            }
 
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

//    private GregorianCalendar getCompanyGregorianCalendar (GregorianCalendar companyGregorianCalendar, 
//    		String scheduledDayStr, BigDecimal companyId) throws Exception{
//		CompanyView companyView = new CompanyView();
//		CompanyDetails companyDetails = new CompanyDetails();
//		companyDetails.setCompanyId(companyId);
//		companyView.fillWithElements(connection, new BigDecimal(1), companyDetails);
//		companyDetails= (CompanyDetails)companyView.getElement(0);
//		if(Utilities.nullToZero(companyDetails.getTimeOffset()).compareTo(new BigDecimal(0))==0){
//			companyGregorianCalendar.add(GregorianCalendar.DAY_OF_MONTH, new BigDecimal(scheduledDayStr).intValue());
//			return companyGregorianCalendar;
//		}
//		companyGregorianCalendar=DateUtilities.getGregorianOffset(companyDetails.getTimeOffset().intValue());
//		companyGregorianCalendar.add(GregorianCalendar.DAY_OF_MONTH, new BigDecimal(scheduledDayStr).intValue());
//		return companyGregorianCalendar;
//	}

    
    private GregorianCalendar getGregorianCalendar (GregorianCalendar gregorianCalendar, 
    		String scheduledDayStr, BigDecimal salesmanId) throws Exception{
		UserView userView = new UserView();
		UserDetails userDetails = new UserDetails();
		userDetails.setCompanyId(salesmanId);
		userView.fillWithElements(connection, new BigDecimal(1), userDetails);
		BigDecimal timeOffset= new BigDecimal(0);
		if(userView.getElements().size()!=0){
			userDetails= (UserDetails)userView.getElement(0);
			timeOffset= userDetails.getTimeOffset();
		}
		if(timeOffset==null){
        	timeOffset=getTimeDefaultOffset(userDetails);
        }
		
		if(Utilities.nullToZero(timeOffset).compareTo(new BigDecimal(0))==0){
			gregorianCalendar.add(GregorianCalendar.DAY_OF_MONTH, new BigDecimal(scheduledDayStr).intValue());
			return gregorianCalendar;
		}
		gregorianCalendar=DateUtilities.getGregorianOffset(timeOffset.intValue());
		gregorianCalendar.add(GregorianCalendar.DAY_OF_MONTH, new BigDecimal(scheduledDayStr).intValue());
		return gregorianCalendar;
	}
    
    /**
     * @param userDetails
     * @return
     * @throws Exception
     */
    private BigDecimal getTimeDefaultOffset(UserDetails userDetails) throws Exception{
		CompanyDetails companyDetails = new CompanyDetails();
		CompanyView companyView = new CompanyView();
		companyDetails.setCompanyId(userDetails.getCompanyId());
		companyView.fillWithElements(connection,CompanyView.FILL_TYPE_BASIC , companyDetails);
		if(companyView.getElements().size()!=0){
			companyDetails = (CompanyDetails)companyView.getElement(0);
	    	if(companyDetails.getTimeOffset() ==null){
				return new BigDecimal(0);
			}else {
				return companyDetails.getTimeOffset();
			}
		}
		return new BigDecimal(0);
	}
    
    private String getDateFormat(BigDecimal salesmanId) throws Exception{
		UserView userView = new UserView();
		UserDetails userDetails = new UserDetails();
		userDetails.setCompanyId(salesmanId);
		userView.fillWithElements(connection, new BigDecimal(1), userDetails);
		if(userView.getElements().size()!=0){
			userDetails= (UserDetails)userView.getElement(0);
			String dateFormat = userDetails.getDateFormat();
			if(userDetails.getDateFormat()==null){
	        	dateFormat=getDateDefaultFormat(userDetails);
	        	return dateFormat;
	        }else{
	        	return userDetails.getDateFormat();
	        }
		}
		return "MM/dd/yyyy";
	}

    
    /**
     * @param userDetails
     * @return
     * @throws Exception
     */
    private String getDateDefaultFormat(UserDetails userDetails) throws Exception{
		CompanyDetails companyDetails = new CompanyDetails();
		CompanyView companyView = new CompanyView();
		companyDetails.setCompanyId(userDetails.getCompanyId());
		companyView.fillWithElements(connection,CompanyView.FILL_TYPE_BASIC , companyDetails);
    	companyDetails = (CompanyDetails)companyView.getElement(0);
    	if(companyDetails.getDateFormat() ==null){
			return "MM/dd/yyyy";
		}else {
			return companyDetails.getDateFormat();
		}
	}

	/**
     * Method sendNextSalesActionEmailNotificationToUsers.
     * @param prospectDetails ProspectDetails
     * @return boolean
     */
    protected boolean sendNextSalesActionEmailNotificationToUsers(ProspectDetails prospectDetails, 
    		ProspectSalesActionDetails salesActionDetail, String dateFormat) {
    	boolean sentAtLeastToOneUser = false;
        String from = AppSettings.getParm("AUTOMATED_EMAIL_SENDER");
        String relayUser = AppSettings.getParm("SMTP_USER");
        String relayPassword = AppSettings.getParm("SMTP_PASSWORD");
        String relayHost = AppSettings.getParm("SMTP_RELAY_HOST");
        String subject = "";
        if(salesActionDetail.getCampaignName() != null) {
        	subject = "Campaign " + salesActionDetail.getCampaignName() + 
        	" Step " + (salesActionDetail.getProspectCampaignSequence().intValue() +1) + " Sales Action: " + 
        	salesActionDetail.getAction().toUpperCase() + " for " + prospectDetails.getCustomerCompany();
        } else {
        	subject = "Next Sales Action: " + Utilities.nullToBlank(salesActionDetail.getAction()).toUpperCase() + " for " + Utilities.nullToBlank(prospectDetails.getCustomerCompany());
        }
        String body = "<style>body, table {font-family:arial,verdana,tahoma;font-size:10pt} h1{font-size:12pt;}</style>";
        body += "<h1>Next Sales Action: <font color=red>" + salesActionDetail.getAction() + "</font> on <font color=red>" + DateUtilities.formatDate(salesActionDetail.getActionDate(), dateFormat) + "</font></h1>";
        body += "<h2>Time: " + DateUtilities.formatDate(salesActionDetail.getActionDate(), "hh:mm aa")+ "</h2>";
        body += "<table border=0>";
        body += "<tr><th align=right>Company: " + "</th><td>"
                + "<a href=\"" + AppSettings.getAppRealPath() + "/user?initialProspectId=" + prospectDetails.getProspectId() + "\">"
                + Utilities.nullToBlank(prospectDetails.getCustomerCompany())
                + "</a>" 
                + "</td></tr>";
        body += "<tr><th align=right>Territory: " + "</th><td>" + Utilities.nullToBlank(prospectDetails.getTerritoryName()) + "</td></tr>";
        body += "<tr><th align=right>Contact: " + "</th><td>"
                + Utilities.nullToBlank(prospectDetails.getContactName());
        if (prospectDetails.getContactTitle() != null) {
            body += " (" + prospectDetails.getContactTitle() + ")";
        }
        body += "</td></tr>";
        body += "<tr><th align=right>Phone: " + "</th><td>" + Utilities.nullToBlank(prospectDetails.getPhone()) + " ext: " + Utilities.nullToBlank(prospectDetails.getPhoneExt()) + "</td></tr>";
        body += "<tr><th align=right>Fax: " + "</th><td>" + Utilities.nullToBlank(prospectDetails.getFax()) + "</td></tr>";
        body += "<tr><th align=right>E-mail: " + "</th><td>" + Utilities.nullToBlank(prospectDetails.getEmail()) + "</td></tr>";
        body += "<tr><th align=right>Action Note: " + "</th><td>" + Utilities.nullToBlank(salesActionDetail.getActionNote()) + "</td></tr>";
        body += "<tr><th align=right>Link to Sales Portal: " + "</th><td><a href=\" " +AppSettings.getParm("SALESPORTAL_URL")+ "\">" + AppSettings.getParm("SALESPORTAL_URL") +"</a></td></tr>";
        body += "</table>";
        body += AppSettings.getParm("SALES_ACTION_BODY_TEXT");

//        System.out.println("relayHost=" + relayHost);
//        System.out.println("relayUser=" + relayUser);
//        System.out.println("relayPassword=" + relayPassword);
//        System.out.println("from=" + from);
//        System.out.println("AppSettings.getParm(SALES_ACTION_TEST_EMAIL_ADDRESS)=" + AppSettings.getParm("SALES_ACTION_TEST_EMAIL_ADDRESS"));
//        System.out.println("subject=" + subject);
       // System.out.println("body=" + body);
//        System.out.println("EmailUtilities.TYPE_HTML=" + EmailUtilities.TYPE_HTML);
//        System.out.println("prospectDetails.getTerritoryOwnerActive()"+ prospectDetails.getTerritoryOwnerActive());
//        System.out.println("\n\nSENDING EMAIL NOTIFICATION\n\n");
//        territory owner
        if (prospectDetails.getTerritoryOwnerActive() != null
            && prospectDetails.getTerritoryOwnerActive().equals(new BigDecimal(1))
            && !Utilities.nullToBlank(prospectDetails.getTerritoryOwnerEmail()).equals("")) {
            //System.out.println("TERRITORY OWNER SENT??");
        	//String relayHost, String relayUser, String relayPassword,
//            String from, String to,
//            String subject, String body,
//            String emailType
            EmailUtilities.sendMail(relayHost, relayUser, relayPassword,
                                    from, prospectDetails.getTerritoryOwnerEmail(), subject, body,
                                    EmailUtilities.TYPE_HTML);
            sentAtLeastToOneUser = true;      
        }
        
//        territory sales manager
        if (prospectDetails.getTerritorySalesManagerActive() != null
            && prospectDetails.getTerritorySalesManagerActive().equals(new BigDecimal(1))
            && !Utilities.nullToBlank(prospectDetails.getTerritorySalesManagerEmail()).equals("")) {
        	//System.out.println("TERRITORY SALESMANAGER");
            EmailUtilities.sendMail(relayHost, relayUser, relayPassword,
                                    from, prospectDetails.getTerritorySalesManagerEmail(), subject, body,
                                    EmailUtilities.TYPE_HTML);
            sentAtLeastToOneUser = true;
        }
//        territory service manager
        if (prospectDetails.getTerritoryServiceManagerActive() != null
            && prospectDetails.getTerritoryServiceManagerActive().equals(new BigDecimal(1))
            && !Utilities.nullToBlank(prospectDetails.getTerritoryServiceManagerEmail()).equals("")) {
        	//System.out.println("TERRITORY SERVICE MANAGER");
            EmailUtilities.sendMail(relayHost, relayUser, relayPassword,
                                    from, prospectDetails.getTerritoryServiceManagerEmail(), subject, body,
                                    EmailUtilities.TYPE_HTML);
            sentAtLeastToOneUser = true;
        }

//        debug

        if (!AppSettings.getParm("SALES_ACTION_TEST_EMAIL_ADDRESS").equals("")) {
            try {
            	EmailUtilities.sendMail(from, AppSettings.getParm("SALES_ACTION_TEST_EMAIL_ADDRESS"), relayHost, subject, body);
//                EmailUtilities.sendMail(relayHost, relayUser, relayPassword,
//                                        from, AppSettings.getParm("SALES_ACTION_TEST_EMAIL_ADDRESS"), subject, body,
//                                        EmailUtilities.TYPE_HTML);
            } catch (Exception e) {
            	e.printStackTrace();
            }
        
        }
        return sentAtLeastToOneUser;
        
    }
    
}
