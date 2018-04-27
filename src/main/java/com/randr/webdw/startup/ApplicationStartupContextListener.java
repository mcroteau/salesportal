package com.randr.webdw.startup;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.randr.webdw.AppSettings;
import com.randr.webdw.background.AbstractImportBackgroundProcessor;
import com.randr.webdw.background.backendAutomatedCampaignCreate.AutomatedCampaignCreateBackgroundProcessor;
import com.randr.webdw.background.backendEmailOptOutImport.EmailOptOutImportBackgroundProcessor;
import com.randr.webdw.background.backendEmailSalesActionSend.EmailSalesActionSendBackgroundProcessor;
import com.randr.webdw.background.backendProspectImport.ProspectImportBackgroundProcessor;
import com.randr.webdw.background.backendTerritoryChange.TerritoryChangeBackgroundProcessor;
import com.randr.webdw.label.LabelDetails;
import com.randr.webdw.label.LabelView;
import com.randr.webdw.util.ConnectionPool;
import com.randr.webdw.util.Utilities;

public class ApplicationStartupContextListener implements ServletContextListener, ApplicationConstants {
	
	private Log log = LogFactory.getLog(this.getClass().getName());
	private ProspectImportBackgroundProcessor prospectImportBackgroundProcessor = null;
	private EmailOptOutImportBackgroundProcessor emailOptOutImportBackgroundProcessor = null;
	private TerritoryChangeBackgroundProcessor territoryChangeBackgroundProcessor = null;
	private EmailSalesActionSendBackgroundProcessor emailSalesActionSendBackgroundProcessor = null;
	private AutomatedCampaignCreateBackgroundProcessor automatedCampaignCreateBackgroundProcessor = null;
	
	public void contextInitialized(ServletContextEvent event) {
		log.info("contextInitialized event");
		ServletContext context = event.getServletContext();
		if(!initialStartup(context)) {
			loadUserDefinedLabel(context);	
		}
		loadProspectImportBackgroundProcessor(context); //Import prospects through data migration portal
		loadEmailOptOutImportBackgroundProcessor(context); //Import email Opt Outs from Order Portal through data migration portal
		loadTerritoryChangeBackgroundProcessor(context) ; //Change Territories if territory needs to be changed
		loadEmailSalesActionSendBackgroundProcessor(context) ; //Change Territories if territory needs to be changed
		loadAutomatedCampaignCreateBackgroundProcessor(context) ; //Change Territories if territory needs to be changed
	}

	private boolean initialStartup(ServletContext context) {
		AppSettings.load(context);
		if(!Utilities.nullToBlank(AppSettings.getParm("INITIAL_STARTUP_PERFORMED")).equals("") &&
				!Utilities.nullToBlank(AppSettings.getParm("INITIAL_STARTUP_PERFORMED")).equals("NO")) {
			return false;
		} else {
			context.setAttribute(MESSAGE_NAME, MESSAGE_INITIAL_START_UP);
			return true;
		}		
	}

	private void loadUserDefinedLabel(ServletContext context) {
		AppSettings.load(context);
		Connection conn = null;
		try {
			ConnectionPool.initConnectionPool();
			conn = ConnectionPool.getNewConnection();
			LabelView labelView = new LabelView();
			labelView.fillWithElements(conn, LabelDetails.FILL_TYPE_ALL, new LabelDetails());
			context.setAttribute("labelView", labelView);
			conn.close();
		} catch(Exception e) {
			try {
				if(conn != null || !conn.isClosed()) {
					conn.close();
				}
			} catch(Exception e2) {
				catchDatabaseError(context);
			}
		}		
	}
	
	/**
	 * @param context
	 * Import prospects through data migration portal
	 * these are formated to csv for import into 2D
	 */
	private void loadProspectImportBackgroundProcessor(ServletContext context) {
		System.out.println("SP- in loadProspectImportBackgroundProcessor");
		if(isParmEnabled("PROSPECT_IMPORT_ENABLED")){
			System.out.println("SP - loading prospectImportBackgroundProcessor");
			prospectImportBackgroundProcessor = new ProspectImportBackgroundProcessor();
			prospectImportBackgroundProcessor.init();
		}
	}
	
	/**
	 * @param context
	 * Import email opt out from Order POrtal through data migration portal
	 * updated the opt out date in prospect
	 */
	private void loadEmailOptOutImportBackgroundProcessor(ServletContext context) {
		System.out.println("SP - in loadEmailOptOutImportBackgroundProcessor");
		if(isParmEnabled("EMAIL_OPTOUT_IMPORT_ENABLED")){
			System.out.println("SP - loading emailOptOutImportBackgroundProcessor");
			emailOptOutImportBackgroundProcessor = new EmailOptOutImportBackgroundProcessor();
			emailOptOutImportBackgroundProcessor.init();
		}
	}

	/**
	 * @param context
	 * Import prospects through data migration portal
	 * these are formated to csv for import into 2D
	 */
	private void loadTerritoryChangeBackgroundProcessor(ServletContext context) {
		System.out.println("\n\nin loadTerritoryChangeBackgroundProcessor");
		if(isParmEnabled("PROSPECT_TERRITORY_CHANGE_ENABLED")){
			System.out.println("SP - loading prospectTerritoryChangeBackgroundProcessor");
			territoryChangeBackgroundProcessor = new TerritoryChangeBackgroundProcessor();
			territoryChangeBackgroundProcessor.init();
		}
	}
	
	/**
	 * @param context
	 * Send emails through data migration portal
	 * these are formated to csv for import into OP
	 * where they are send out automatically based on OP parm
	 */
	private void loadEmailSalesActionSendBackgroundProcessor(ServletContext context) {
		System.out.println("\n\nin loadEmailSalesActionSendBackgroundProcessor");
		if(isParmEnabled("EMAIL_SALES_ACTION_SEND_ENABLED")){
			System.out.println("SP - loading emailSalesActionSendBackgroundProcessor");
			emailSalesActionSendBackgroundProcessor = new EmailSalesActionSendBackgroundProcessor();
			emailSalesActionSendBackgroundProcessor.init();
		}
	}
	
	/**
	 * @param context
	 * This creates campaigns automatically for prospects that meet the selection
	 * criteria in the automated Campaigns. Checks first to make sure they didn't
	 * already get this campaign.
	 */
	private void loadAutomatedCampaignCreateBackgroundProcessor(ServletContext context) {
		System.out.println("\n\nin loadAutomatedCampaignCreateBackgroundProcessor");
		if(isParmEnabled("AUTOMATED_CAMPAIGN_CREATE_ENABLED")){
			System.out.println("SP - loading automatedCampaignCreateBackgroundProcessor");
			automatedCampaignCreateBackgroundProcessor = new AutomatedCampaignCreateBackgroundProcessor();
			automatedCampaignCreateBackgroundProcessor.init();
		}
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		context.removeAttribute("labelView");
		System.out.println("destroying all BackgroundProcessors");
		cancel(prospectImportBackgroundProcessor);
		cancel(emailOptOutImportBackgroundProcessor);
		cancel(territoryChangeBackgroundProcessor);
		cancel(emailSalesActionSendBackgroundProcessor);
		cancel(automatedCampaignCreateBackgroundProcessor);
		ConnectionPool.closeConnectionPool();
		
	}
	
	private void catchDatabaseError(ServletContext context) {
		context.setAttribute(MESSAGE_NAME, MESSAGE_DB_CONNECTION_PROBLEM);
	}
	
	private void cancel(AbstractImportBackgroundProcessor processor) {
		if(processor != null) {
			processor.cancelTimerTask();
			processor = null;
		}
	}
	
	private boolean isParmEnabled(String parmName) {
		if(AppSettings.getParm(parmName).equalsIgnoreCase("TRUE")||
				AppSettings.getParm(parmName).equalsIgnoreCase("YES")){
			return true;
		} else {
			return false;
		}
	}

}
