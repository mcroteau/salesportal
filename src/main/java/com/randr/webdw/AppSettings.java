package com.randr.webdw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.randr.webdw.user.UserProfile;
import com.randr.webdw.util.URLHandle;
import com.randr.webdw.util.Utilities;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class AppSettings {

    private static boolean loaded = false;
    private static ServletContext application = null;
    private static Element configDocRoot = null;
    private static String appWebPath = null;
    private static XMLOutputter outputter = new XMLOutputter("", false);
    private static String appRealPath = null;
    private static String graphicsDir = "graphics";
    private static String graphicsPath = null;
    private static String graphicsRealPath = null;
    private static StringBuffer usStatesCombo;

    // private static StringBuffer   scommonHiddenForms = null;

    /**
     * Method isLoaded.
     * @return boolean
     */
    public static boolean isLoaded() {
        return loaded;
    }

    /**
     * Method load.
     * @param _app ServletContext
     */
    public static synchronized void load(ServletContext _app) {
        System.gc();

        application = _app;

        setAppRealPath();
        setGraphicsRealPath();
        createConfigDocument();

        loaded = true;
    }


    /**
     * Method correctGraphicsPaths.
     */
    public static void correctGraphicsPaths() {
        if (getParm("GRAPHICS_CORRECTED").equals("NO")) {

            // correct backgroud image path
            if (getParm("SITE_BACKGROUND_IMAGE").toLowerCase().startsWith("/graphics/")) {
                try {
                    setParm("SITE_BACKGROUND_IMAGE", getGraphicsPath() + getParm("SITE_BACKGROUND_IMAGE").substring(10));
                } catch (Exception e1) {
                } finally {
                }
            }

            // correct image paths in page headers and footers
            setParm("COMMON_PAGE_HEADER_RETAIL", Utilities.replaceString(getParm("COMMON_PAGE_HEADER_RETAIL"), "src=\"/graphics/", "src=\"" + getGraphicsPath()));
            setParm("COMMON_PAGE_FOOTER_RETAIL", Utilities.replaceString(getParm("COMMON_PAGE_FOOTER_RETAIL"), "src=\"/graphics/", "src=\"" + getGraphicsPath()));
            setParm("COMMON_PAGE_HEADER_WHOLESALE", Utilities.replaceString(getParm("COMMON_PAGE_HEADER_WHOLESALE"), "src=\"/graphics/", "src=\"" + getGraphicsPath()));
            setParm("COMMON_PAGE_FOOTER_WHOLESALE", Utilities.replaceString(getParm("COMMON_PAGE_FOOTER_WHOLESALE"), "src=\"/graphics/", "src=\"" + getGraphicsPath()));

            // update the flag that says that the image paths are corrected
            setParm("GRAPHICS_CORRECTED", "YES");

            // put the changes in appConfig.xml
            outputConfigDocumentToXMLFile();
        }
    }

    /**
     * Method declaration
     *
     * @see
     */
    public static void createConfigDocument() {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new File(appRealPath + "webapp/xml_config/appConfig.xml"));

            configDocRoot = doc.getRootElement();
        } catch (org.jdom.JDOMException j) {
            j.printStackTrace();
        }
    }

    /**
     * Method createConfigDocument.
     * @param appRealPath String
     */
    public static void createConfigDocument(String appRealPath) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new File(appRealPath + "xml_config/appConfig.xml"));

            configDocRoot = doc.getRootElement();
        } catch (org.jdom.JDOMException j) {
            j.printStackTrace();
        }
    }

    /**
     * Method main.
     * @param args String[]
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //    try {
        //	    SAXBuilder builder = new SAXBuilder();
        //	    Document   doc =
        //		builder.build(new File("d:\\appConfig.xml"));
        //
        //	    configDocRoot = doc.getRootElement();
        //            System.out.println(configDocRoot);
        //	} catch (org.jdom.JDOMException j) {j.printStackTrace();}

    }

    /**
     * Method declaration
     *
     * @param parm
     * @return String
     * @see
     */
    public static String getParm(String parm) {
        try {
            return Utilities.nullToBlank(configDocRoot.getChild(parm).getText());
        } catch (Exception e) {
            //e.printStackTrace();

            return "";
        }
    }

    /**
     * Method declaration
     *
     * @param parm
     * @return Element
     * @see
     */
    public static Element getElement(String parm) {
        try {
            return configDocRoot.getChild(parm);
        } catch (Exception e) {
            e.printStackTrace();

            return new Element(parm);
        }
    }

    /**
     * Method parseCompanyInfo.
     * @param parmVal String
     * @return String
     */
    public static String parseCompanyInfo(String parmVal) {
        String sParsedVal = parmVal;

        sParsedVal = Utilities.replaceString(sParsedVal, "COMPANY_NAME", getParm("COMPANY_NAME"));
        sParsedVal = Utilities.replaceString(sParsedVal, "COMPANY_PHONE", getParm("COMPANY_PHONE"));
        sParsedVal = Utilities.replaceString(sParsedVal, "COMPANY_ADDRESS", getParm("COMPANY_ADDRESS"));
        sParsedVal = Utilities.replaceString(sParsedVal, "COMPANY_STATE_ABREV", getParm("COMPANY_STATE_ABREV"));
        sParsedVal = Utilities.replaceString(sParsedVal, "COMPANY_STATE", getParm("COMPANY_STATE"));
        sParsedVal = Utilities.replaceString(sParsedVal, "COMPANY_CITY", getParm("COMPANY_CITY"));
        sParsedVal = Utilities.replaceString(sParsedVal, "COMPANY_ZIP", getParm("COMPANY_ZIP"));
        sParsedVal = Utilities.replaceString(sParsedVal, "CONTACT_EMAIL", getParm("CONTACT_EMAIL"));
        sParsedVal = Utilities.replaceString(sParsedVal, "COMPANY_FAX", getParm("COMPANY_FAX"));

        return sParsedVal;
    }

    /**
     * Method getParmTxt.
     * @param parm String
     * @return String
     */
    public static String getParmTxt(String parm) {

        // String sReturn=(String)application.getAttribute(parm);
        try {

            // return Util.nullToBlank(Util.getTagContent(outputter.outputString(configDocRoot.getChild(parm))));
            return Utilities.nullToBlank(configDocRoot.getChild(parm).getText());
        } catch (Exception e) {
            e.printStackTrace();

            return "";
        }
    }

    /**
     * Method getHeader.
     * @param req HttpServletRequest
     * @param userProfile UserProfile
     * @param sTitle String
     * @param sAdditionalScript String
     * @param sHiddenFormStr String
     * @param sOnLoad String
     * @return String
     * @throws Exception
     */
    public static String getHeader(HttpServletRequest req, UserProfile userProfile, String sTitle, String sAdditionalScript, String sHiddenFormStr, String sOnLoad) throws Exception {
        return getHeader(req, userProfile, sTitle, sAdditionalScript, sHiddenFormStr, sOnLoad, "");
    }

    /**
     * Method getHeader.
     * @param req HttpServletRequest
     * @param userProfile UserProfile
     * @param sTitle String
     * @param sAdditionalScript String
     * @param sHiddenFormStr String
     * @param sOnLoad String
     * @param additionalBodyAttributes String
     * @return String
     * @throws Exception
     */
    public static String getHeader(HttpServletRequest req,
                                   UserProfile userProfile,
                                   String sTitle,
                                   String sAdditionalScript,
                                   String sHiddenFormStr,
                                   String sOnLoad,
                                   String additionalBodyAttributes) throws Exception {
        try {

            // getAppWebPath(req);
            String sURLBegin = null;

            String sProtocol = null;

            if (getParm("SSL_ENABLED").toUpperCase().equals("YES")) {
                sProtocol = "https";
            } else {
                sProtocol = "http";
            }

            sURLBegin = sProtocol + "://" + req.getHeader("host") + appWebPath;

            StringBuffer sHeader = new StringBuffer("");

            sHeader.append("<html>\n");

            sHeader.append("<head><title>" + "SALES PORTAL - " + sTitle + "</title>\n" + "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + AppSettings.getAppWebPath() + "css/" + AppSettings.getParm("PUBLIC_SITE_CSS") + "?Timestamp=" + String.valueOf((new java.util.Date()).getTime())+ "\">" + "<meta http-equiv=\"expires\" content=\"0\">\n" + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n" + "<META name=\"resource-type\" content=document>\n" + "<META name=distribution content=global>\n");

            if (!getParm("META_DESCRIPTION").equals("")) {
                sHeader.append("<META name=\"description\" content=\"" + parseCompanyInfo(getParm("META_DESCRIPTION")) + "\">" + "\n");
            }

            if (!getParm("META_KEYWORDS").equals("")) {
                sHeader.append("<META name=\"keywords\" content=\"" + parseCompanyInfo(getParm("META_KEYWORDS")) + "\">" + "\n");
            }

            sHeader.append(parseCompanyInfo(getParm("COMMON_PAGE_HEAD")) + "\n");
            addHeaderScript(sHeader, sAdditionalScript, sOnLoad);
            sHeader.append("</head>\n");

            String sBkgImgURL = "";

            sHeader.append("<body" + " onload=\"onLoadPage()\"" + " " + additionalBodyAttributes + ">\n");
            appendGoogleAnalyticsCall(sHeader);
            sHeader.append("<script type='text/javascript'> function Go(){return} </script>\n");
            sHeader.append("<script type='text/javascript' src=\"" + sURLBegin + "menu\"></script>\n");
            //            sHeader.append("<script type='text/javascript' src=\""
            //                    + sURLBegin
            //                    + "scripts/menu_com.js\"></script>\n");
            sHeader.append("<script type='text/javascript' src=\"" + sURLBegin + "scripts/menu_com.js?Timestamp=" + String.valueOf((new java.util.Date()).getTime()) + "\"></script>\n");
            sHeader.append("<noscript>Your browser does not support script</noscript>\n");
            sHeader.append(sHiddenFormStr + "\n");
            sHeader.append(getCommonHiddenForms(sURLBegin) + "\n");
            String commonPageHeader = getParm("COMMON_PAGE_HEADER");
            commonPageHeader = Utilities.replaceString(commonPageHeader, "[USER_PROFILE]", userProfile.display());
            sHeader.append(parseCompanyInfo(commonPageHeader) + "\n");

            return sHeader.toString();
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method getFooter.
     * @param userProfile UserProfile
     * @return String
     */
    public static String getFooter(UserProfile userProfile) {

        StringBuffer sFooter = new StringBuffer("");
        String commonPageFooter = getParm("COMMON_PAGE_FOOTER");
        commonPageFooter = Utilities.replaceString(commonPageFooter, "[USER_PROFILE]", userProfile.display());
        sFooter.append(parseCompanyInfo(commonPageFooter) + "\n");
        sFooter.append("</body></html>\n");

        return sFooter.toString();
    }

    /**
     * Method establishAppWebPath.
     * @param req HttpServletRequest
     */
    public static synchronized void establishAppWebPath(HttpServletRequest req) {
        if (appWebPath == null) {
            String sReqURI = req.getRequestURI();
            String sServlPath = req.getServletPath();

            appWebPath = sReqURI.substring(0, sReqURI.length() - sServlPath.length() + 1);
        }
    }

    /**
     * Method getAppWebPath.
     * @return String
     */
    public static String getAppWebPath() {
        return appWebPath;
    }

    /**
     * Method getAppCompleteWebPath.
     * @param req HttpServletRequest
     * @return String
     */
    public static String getAppCompleteWebPath(HttpServletRequest req) {
        String sProtocol = null;

        if (getParm("SSL_ENABLED").toUpperCase().equals("YES")) {
            sProtocol = "https";
        } else {
            sProtocol = "http";
        }

        return sProtocol + "://" + req.getHeader("host") + getAppWebPath();
    }

    /**
     * Method declaration
     *
     * @param req
     * @return String
     * @see
     */
    public static String getGraphicsCompleteWebPath(HttpServletRequest req) {
        String sProtocol = null;

        if (getParm("SSL_ENABLED").toUpperCase().equals("YES")) {
            sProtocol = "https";
        } else {
            sProtocol = "http";
        }

        return sProtocol + "://" + req.getHeader("host") + getGraphicsPath();
    }

    /**
     * Method getGraphicsPath.
     * @return String
     */
    public static String getGraphicsPath() {
        if (graphicsPath == null) {
            graphicsPath = appWebPath + graphicsDir + "/";
        }

        return graphicsPath;

        //return "/tmp_graphics/";
    }

    /**
     * Method setParm.
     * @param parm String
     * @param parmValue String
     */
    public static void setParm(String parm, String parmValue) {
        String parmVal = Utilities.formatXMLString(parmValue);
        Element child = configDocRoot.getChild(parm);

        if (child != null) {
            child.setContent(parmVal);
        } else {
            child = new Element(parm);

            child.addContent(parmVal);
            configDocRoot.addContent(child);
        }
    }

    /**
     * Method outputConfigDocumentToXMLFile.
     */
    public static void outputConfigDocumentToXMLFile() {

        // setup this like outputDocument
        try {
            FileWriter writer = new FileWriter(appRealPath + "xml_config/appConfig.xml");

            outputter.output(configDocRoot.getDocument(), writer);

            writer.flush();
            writer.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Method outputConfigDocumentToXMLFile.
     */
    public static void outputConfigDocumentToXMLFileBackup() {

        // setup this like outputDocument
        try {
            FileWriter writer = new FileWriter(appRealPath + "xml_config/backup/appConfig.xml");

            outputter.output(configDocRoot.getDocument(), writer);

            writer.flush();
            writer.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method setAppRealPath.
     */
    private static void setAppRealPath() {
        String sWebRealPath = application.getRealPath("/");
        String sPathDelimiter = null;

        if (sWebRealPath.indexOf("/") > -1) {
            sPathDelimiter = "/";
        } else {
            sPathDelimiter = "\\";
        }

        appRealPath = sWebRealPath.substring(0, sWebRealPath.lastIndexOf(sPathDelimiter) + 1);
    }

    /**
     * Method getAppRealPath.
     * @return String
     */
    public static String getAppRealPath() {
        return appRealPath;
    }

    /**
     * Method setGraphicsRealPath.
     */
    private static void setGraphicsRealPath() {
        graphicsRealPath = application.getRealPath(graphicsDir);

        if (graphicsRealPath.indexOf("/") > -1) {
            graphicsRealPath = graphicsRealPath + "/";
        } else {
            graphicsRealPath = graphicsRealPath + "\\";
        }
    }

    /**
     * Method getGraphicsRealPath.
     * @return String
     */
    public static String getGraphicsRealPath() {
        return graphicsRealPath;

        //return "/ProdWeb/ugw/tmp_graphics/";
    }

    /**
     * Method addHeaderScript.
     * @param sHeader StringBuffer
     * @param sAdditionalScript String
     * @param sOnLoadStr String
     */
    private static void addHeaderScript(StringBuffer sHeader, String sAdditionalScript, String sOnLoadStr) {
        sHeader.append("<script language=\"JavaScript\">\n" + "var sApostrof=\"'\";" + "function onLoadPage() {\n" + sOnLoadStr + "\n" + "};" + "</script>");

        if (!sAdditionalScript.equals("")) {
            sHeader.append("<script language=\"JavaScript\">\n");
            sHeader.append(sAdditionalScript + "\n");
            sHeader.append("</script>\n");
        }

        sHeader.append("<SCRIPT LANGUAGE=\"JavaScript\">\n" + "function fOpenPage(sAction) {\n" + "fOpenPage(sAction,'');};\n" + "function fOpenPage(sAction, sFormAction) {\n" + "document.frmPage.action=sAction;\n" + "document.frmPage.formAction.value=sFormAction;\n" + "document.frmPage.submit();\n" + "};\n");
        sHeader.append("</SCRIPT>\n");
        sHeader.append("<SCRIPT LANGUAGE=\"JavaScript\">\n" + "function fOpenPageAdmin(sAction) {\n" + "fOpenPageAdmin(sAction,'');};\n" + "function fOpenPageAdmin(sAction, sFormAction) {\n" + "document.frmPage.action=sAction;\n" + "document.frmPage.formAction.value=sFormAction;\n" + "document.frmPage.target=\"_blank\";\n" + "document.frmPage.submit();\n" + "};\n");
        
        if ((AppSettings.getParm("CUSTOM_PAGES_ENABLED").equals("YES")) && (!AppSettings.getParm("CUSTOM_PAGES").equals(""))) {
            sHeader.append("function fOpenCustomPage(page) {document.frmCustomPage.PAGE.value=page;document.frmCustomPage.submit();};\n");
        }

        sHeader.append("</SCRIPT>\n");
    }

    /**
     * Method getCommonHiddenForms.
     * @param sURLBegin String
     * @return StringBuffer
     */
    private static StringBuffer getCommonHiddenForms(String sURLBegin) {

        // if (scommonHiddenForms == null) {
        StringBuffer scommonHiddenForms = new StringBuffer("");

        scommonHiddenForms.append("<form name=\"frmPage\" method=post action=\"\">\n");
        scommonHiddenForms.append("<input type=hidden name=\"formAction\" value=\"0\">\n");
        scommonHiddenForms.append("</form>\n");

        if ((AppSettings.getParm("CUSTOM_PAGES_ENABLED").equals("YES")) && (!AppSettings.getParm("CUSTOM_PAGES").equals(""))) {
            scommonHiddenForms.append("<form name=\"frmCustomPage\" method=post action=\"" + sURLBegin + "custom_page\">\n");
            scommonHiddenForms.append("<input type=hidden name=\"formAction\" value=\"DISPLAY\">\n");
            scommonHiddenForms.append("<input type=hidden name=\"PAGE\" value=\"\">\n");
            scommonHiddenForms.append("</form>\n");
        }

        scommonHiddenForms.append("<form name=\"frmPayroll\" method=post action=\"" + sURLBegin + "payroll_report\">\n");
        scommonHiddenForms.append("<input type=hidden name=\"formAction\" value=\"DISPLAY_SEARCH\">\n");
        scommonHiddenForms.append("<input type=hidden name=\"userType\" value=\"\">\n");
        scommonHiddenForms.append("</form>\n");
        // }
        return scommonHiddenForms;
    }
    
    /**
     * Method getUSStatesCombo.
     * @return String
     */
    public static String getUSStatesCombo() {
        if ( usStatesCombo == null ) {
            usStatesCombo = new StringBuffer();

            usStatesCombo.append("<option>Alabama" + "<option>Alaska"
                                 + "<option>Arizona" + "<option>Arkansas"
                                 + "<option>California" + "<option>Colorado"
                                 + "<option>Connecticut" + "<option>Delaware"
                                 + "<option>District of Columbia"
                                 + "<option>Florida" + "<option>Georgia"
                                 + "<option>Hawaii" + "<option>Idaho"
                                 + "<option>Illinois" + "<option>Indiana"
                                 + "<option>Iowa" + "<option>Kansas"
                                 + "<option>Kentucky" + "<option>Louisiana"
                                 + "<option>Maine" + "<option>Maryland"
                                 + "<option>Massachusetts"
                                 + "<option>Michigan" + "<option>Minnesota"
                                 + "<option>Mississippi" + "<option>Missouri"
                                 + "<option>Montana" + "<option>Nebraska"
                                 + "<option>Nevada" + "<option>New Hampshire"
                                 + "<option>New Jersey"
                                 + "<option>New Mexico" + "<option>New York"
                                 + "<option>North Carolina"
                                 + "<option>North Dakota" + "<option>Ohio"
                                 + "<option>Oklahoma" + "<option>Oregon"
                                 + "<option>Pennsylvania"
                                 + "<option>Rhode Island"
                                 + "<option>South Carolina"
                                 + "<option>South Dakota"
                                 + "<option>Tennessee" + "<option>Texas"
                                 + "<option>Utah" + "<option>Vermont"
                                 + "<option>Virgin Islands"
                                 + "<option>Virginia" + "<option>Washington"
                                 + "<option>West Virginia"
                                 + "<option>Wisconsin" + "<option>Wyoming");
        }

        return usStatesCombo.toString();
    }
    /**
     * Method elementExists.
     * @param name String
     * @return boolean
     */
    public static boolean elementExists(String name) {
    	Element docRoot = configDocRoot; 
    	Element elem = docRoot.getChild(name);
        if ( elem != null ) {
            return true;
        }
        return false;
    }
    
    private static void appendGoogleAnalyticsCall(StringBuffer sb) {
    	if(!Utilities.nullToBlank(getParm("GOOGLE_ANALYTICS_ACCT_NO")).equals("")){
        	sb.append("\n\n<!-- BEGIN GOOGLE ANALYTICS CODE -->\n");
        	sb.append("<script type=\"text/javascript\">\n");
        	sb.append("var gaJsHost = ((\"https:\" == document.location.protocol) ? \"https://ssl.\" : \"http://www.\");\n");
        	sb.append("document.write(unescape(\"%3Cscript src='\" + gaJsHost + \"google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E\"));\n");
        	sb.append("</script>\n");
        	sb.append("<script type=\"text/javascript\">\n");
        	sb.append("var pageTracker = _gat._getTracker(\"" + getParm("GOOGLE_ANALYTICS_ACCT_NO") + "\");\n");
        	sb.append("pageTracker._initData();\n");
        	sb.append("pageTracker._trackPageview();\n");
        	sb.append("</script>\n");
        	sb.append("<!-- END GOOGLE ANALYTICS CODE -->\n\n");
    	}
    }

     public static String getGoogleAnalyticsCode() {
    	    	StringBuffer sb = new StringBuffer();
    	    	sb.append("\n\n<!-- BEGIN GOOGLE ANALYTICS CODE -->\n");
    	    	sb.append("<script type=\"text/javascript\">\n");
    	    	sb.append("var gaJsHost = ((\"https:\" == document.location.protocol) ? \"https://ssl.\" : \"http://www.\");\n");
    	    	sb.append("document.write(unescape(\"%3Cscript src='\" + gaJsHost + \"google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E\"));\n");
    	    	sb.append("</script>\n");
    	    	sb.append("<script type=\"text/javascript\">\n");
    	    	sb.append("var pageTracker = _gat._getTracker(\"" + getParm("GOOGLE_ANALYTICS_ACCT_NO") + "\");\n");
    	    	sb.append("pageTracker._initData();\n");
    	    	sb.append("pageTracker._trackPageview();\n");
    	    	sb.append("</script>\n");
    	    	sb.append("<!-- END GOOGLE ANALYTICS CODE -->\n\n");
    	    	return sb.toString();
    	    }

}


