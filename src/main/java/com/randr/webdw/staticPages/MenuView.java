package com.randr.webdw.staticPages;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Element;

import com.randr.webdw.AppSettings;
import com.randr.webdw.util.Utilities;


/**
 */
public class MenuView {

    // private boolean      bRefreshMenuRetail = true;
    // private boolean      bRefreshMenuWholesale = true;
    // private boolean      bRefreshMenuHome = true;

    private static Hashtable menuContentHashtable = new Hashtable();
    private static Hashtable menuListHashtable = new Hashtable();
    private static StringBuffer menuVarsStr = null;
    private static String lastServerNameHome = "";
    private static StringBuffer currentMenuContent = null;
    private static Hashtable currentMenuList = null;

    /**
     * Constructor for MenuView.
     */
    public MenuView() {
    }

    /**
     * Method getMenu.
     * @param userType BigDecimal
     * @param req HttpServletRequest
     * @return String
     */
    public static String getMenu(BigDecimal userType, HttpServletRequest req) {
        if (req.getHeader("host").equals(lastServerNameHome)) {
            StringBuffer menuContent = (StringBuffer) menuContentHashtable.get(userType);
            if (menuContent != null) {
                return menuContent.toString();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Method refreshMenu.
     * @param userType BigDecimal
     * @param req HttpServletRequest
     * @throws Exception
     */
    public static void refreshMenu(BigDecimal userType, HttpServletRequest req) throws Exception {
        // identify the begin url...
        String urlBegin = null;

        urlBegin = AppSettings.getAppCompleteWebPath(req);

        // create the menu
        createMenu(userType, urlBegin, req);

        lastServerNameHome = req.getHeader("host");
    }

    /**
     * Method flagRefresh.
     * @param refreshType int
     */
    public static void flagRefresh(int refreshType) {
        menuContentHashtable = new Hashtable();

        if (refreshType == 2) {
            menuListHashtable = new Hashtable();
        }
    }

    /**
     * Method flagRefreshMenuVars.
     */
    public static void flagRefreshMenuVars() {
        menuVarsStr = null;
    }

    /**
     * Method createMenu.
     * @param userType BigDecimal
     * @param urlBegin String
     * @param req HttpServletRequest
     * @throws Exception
     */
    private static synchronized void createMenu(BigDecimal userType, String urlBegin,
                                                HttpServletRequest req) throws Exception {
        Hashtable menuList = getMenuList(userType, urlBegin, req);
        menuContentHashtable.put(userType, new StringBuffer(""));
        currentMenuContent = (StringBuffer) menuContentHashtable.get(userType);

        Vector menuOptions = Utilities.tokenize(AppSettings.getParm("MENU_OPTIONS_LIST"), ",");
        Vector menuEnabledFlags = Utilities.tokenize(AppSettings.getParm("MENU_OPTIONS_ENABLED_" + userType.toString()), ",");
        String menuListOption = null;
        int countMenu = 0;

        for (int i = 0; i < menuOptions.size(); i++) {
            menuListOption = (String) menuList.get(menuOptions.elementAt(i));

            if ((!Utilities.nullToBlank(menuListOption).equals("")) && (menuEnabledFlags.elementAt(i).equals("1"))) {
                countMenu++;

                currentMenuContent.append(Utilities.replaceString(menuListOption, "Menux", "Menu" + String.valueOf(countMenu)));
            }
        }

        currentMenuContent.append("var NoOffFirstLineMenus=" + String.valueOf(countMenu) + ";\n");
        currentMenuContent.append("var GraphicsPath=\"" + AppSettings.getAppWebPath() + "graphics/\";");
        currentMenuContent.append("function CancelLink() {return false;}\n");
    }

    /**
     * Method createMenuVars.
     */
    private static synchronized void createMenuVars() {
        menuVarsStr = new StringBuffer("");

        menuVarsStr.append("var  LowBgColor='" + AppSettings.getParm("MENU_LOW_BACKGROUND_COLOR") + "';\n");
        menuVarsStr.append("var  HighBgColor='" + AppSettings.getParm("MENU_HIGH_BACKGROUND_COLOR") + "';\n");
        menuVarsStr.append("var  FontLowColor='" + AppSettings.getParm("MENU_TEXT_LOW_COLOR") + "';\n");
        menuVarsStr.append("var  FontHighColor='" + AppSettings.getParm("MENU_TEXT_HIGH_COLOR") + "';\n");
        menuVarsStr.append("var  BorderColor='" + AppSettings.getParm("MENU_BORDER_COLOR") + "';\n");
        menuVarsStr.append("var  BorderWidth=1;\n");
        menuVarsStr.append("var  BorderBtwnElmnts=1;\n");
        menuVarsStr.append("var  FontFamily='" + AppSettings.getParm("MENU_TEXT_FONT_FAMILY") + "';\n");
        menuVarsStr.append("var  FontSize=" + AppSettings.getParm("MENU_TEXT_FONT_SIZE") + ";\n");
        menuVarsStr.append("var  FontBold=0;\n");
        menuVarsStr.append("var  FontItalic=0;\n");
        menuVarsStr.append("var  MenuTextCentered=0;\n");
        menuVarsStr.append("var  MenuCentered='left';\n");
        menuVarsStr.append("var  MenuVerticalCentered='top';\n");
        menuVarsStr.append("var  ChildOverlap=.1;\n");
        menuVarsStr.append("var  ChildVerticalOverlap=.1;\n");
        menuVarsStr.append("var  StartTop=" + AppSettings.getParm("MENU_VERTICAL_POSITION") + "; //set vertical offset\n");
        menuVarsStr.append("var  StartLeft=" + AppSettings.getParm("MENU_HORIZONTAL_POSITION") + "; //set horizontal offset\n");
        menuVarsStr.append(" if (document.layers) {StartTop = StartTop + 7; StartLeft = StartLeft -1;}");
        menuVarsStr.append("var  VerCorrect=0;\n");
        menuVarsStr.append("var  HorCorrect=0;\n");
        menuVarsStr.append("var  LeftPaddng=3;\n");
        menuVarsStr.append("var  TopPaddng=2;\n");
        menuVarsStr.append("var  FirstLineHorizontal=" + AppSettings.getParm("MENU_STYLE") + "; //set menu layout (1=horizontal, 0=vertical)\n");
        menuVarsStr.append("var  MenuFramesVertical=1;\n");
        menuVarsStr.append("var  DissapearDelay=500;\n");
        menuVarsStr.append("var  TakeOverBgColor=1;\n");
        menuVarsStr.append("var  FirstLineFrame='self';\n");
        menuVarsStr.append("var  SecLineFrame='self';\n");
        menuVarsStr.append("var  DocTargetFrame='self';\n");
        menuVarsStr.append("var  WebMasterCheck=0;\n");
    }

    /**
     * Method getMenuVars.
     * @return String
     */
    public static String getMenuVars() {
        if (menuVarsStr == null) {
            createMenuVars();
        }

        return menuVarsStr.toString();
    }

    /**
     * Method getMenuList.
     * @param userType BigDecimal
     * @param urlBegin String
     * @param req HttpServletRequest
     * @return Hashtable
     * @throws Exception
     */
    private static synchronized Hashtable getMenuList(BigDecimal userType, String urlBegin,
                                                      HttpServletRequest req) throws Exception {
        if (menuListHashtable.get(userType) != null
            && req.getHeader("host").equals(lastServerNameHome)) {
            return (Hashtable) menuListHashtable.get(userType);
        }
        String workProspectHeading = "Work with Prospects";
        if(AppSettings.getParm("WORK_PROSPECT_HEADING").compareTo("")!=0 &&
        		AppSettings.getParm("WORK_PROSPECT_HEADING")!=null){
        	workProspectHeading = AppSettings.getParm("WORK_PROSPECT_HEADING");
        }
        currentMenuList = new Hashtable();
        menuListHashtable.put(userType, currentMenuList);
        //language = make each menu heading a variable
        currentMenuList.put("DOCUMENTS", getMenuOptionString("Documents", "document", "DISPLAY_DOCUMENTATION", urlBegin));
        currentMenuList.put("EMAIL_TEMPLATES", getMenuOptionString("Email Templates", "email_template", "DISPLAY", urlBegin));
        currentMenuList.put("UML", getMenuOptionString("UML", "document", "DISPLAY_UML", urlBegin));
        currentMenuList.put("MODIFY_ACCOUNT", getMenuOptionString("Your Account", "user", "DISPLAY_UPDATE", urlBegin));
        currentMenuList.put("COMMISIONS", getMenuOptionString("Your Revenue/Comm.", "commision", "DISPLAY_SEARCH", urlBegin));
        currentMenuList.put("NEW_CUSTOMER_ACCOUNT", getMenuOptionString("Create Prospect", "prospect", "DISPLAY_INSERT", urlBegin));
        currentMenuList.put("CUSTOMER_ACCOUNTS", getMenuOptionString(workProspectHeading, "prospect_search", "DISPLAY_SEARCH", urlBegin));
        currentMenuList.put("CUSTOM_QUERIES", getMenuOptionString("Custom Queries", "custom_queries", "DISPLAY_QUERIES", urlBegin));
        currentMenuList.put("CURRENT_ACTIONS" , getMenuOptionString("Todays Actions", "current_actions" , "DISPLAY_CURRENT_ACTIONS" , urlBegin));
        currentMenuList.put("CALENDAR" , getMenuOptionString("Calendar", "territory_calendar" , "DISPLAY_CALENDAR_SELECTION" , urlBegin));
        currentMenuList.put("REPORTING" , getMenuOptionString("Information Portal", "user" , "DISPLAY_REPORTING" , urlBegin));

        
        //currentMenuList.put("CALA" , getMenuOptionString())
        if (AppSettings.getParm("SUPPORT_LINK_ENABLED").equals("YES")) {
            currentMenuList.put("SUPPORT", getMenuOptionString("Support", AppSettings.getAppWebPath() + AppSettings.getParm("SUPPORT_LINK")));
        } else {
            currentMenuList.put("SUPPORT", getMenuOptionString("Support", "home", "DISPLAY", urlBegin));
        }
//        if (AppSettings.getParm("INFOPORTAL_LINK_ENABLED").equals("YES")) {
//            currentMenuList.put("INFOPORTAL", getMenuOptionString("Information Portal", AppSettings.getParm("INFOPORTAL_LINK")));
//        } else {
//        }
        
        currentMenuList.put("ADMIN", getMenuOptionString("Admin", AppSettings.getAppWebPath() + "admin"));
        currentMenuList.put("ADMIN_CONFIG", getMenuOptionString("Configuration", AppSettings.getAppWebPath() + "admin/config"));
        
        currentMenuList.put("LOGIN", getMenuOptionString("Log Out", "user", "LOGOUT", urlBegin));

        if ((AppSettings.getParm("CUSTOM_PAGES_ENABLED").equals("YES")) && (!AppSettings.getParm("CUSTOM_PAGES").equals(""))) {

            // initialize the menu options for the custom pages
            LinkedList customPages = (LinkedList) AppSettings.getElement("CUSTOM_PAGES").getChildren();
            Element customPageElement = null;
            String availForMenu = null;
            String userTypeStr = null;
            String menuID = null;
            String menuText = null;
            String content = null;

            for (int i = 0; i < customPages.size(); i++) {
                customPageElement = (Element) customPages.get(i);
                availForMenu = customPageElement.getChild("AVAILABLE_FOR_MENU").getText();
                userTypeStr = customPageElement.getChild("USER_TYPE").getText();
                content = customPageElement.getChild("CONTENT").getText();

                if ((availForMenu.equals("YES")) && ((userTypeStr.equals(userType.toString())) || (userTypeStr.equals("ALL"))) && (!content.equals(""))) {
                    menuID = customPageElement.getChild("MENU_ID").getText();
                    menuText = customPageElement.getChild("MENU_TEXT").getText();

                    currentMenuList.put(menuID, getMenuOptionString(menuText, "javascript: fOpenCustomPage('" + customPageElement.getName() + "')"));
                }
            }
        }

        return currentMenuList;
    }

    /**
     * Method getMenuOptionString.
     * @param title String
     * @param urlMap String
     * @param formAction String
     * @param urlBegin String
     * @return String
     */
    private static String getMenuOptionString(String title, String urlMap, String formAction, String urlBegin) {
        return "Menux = new Array(\"" + title + "\",\"javascript: fOpenPage('" + urlBegin + urlMap + "','" + formAction + "')\",0,20, " + AppSettings.getParm("MENU_OPTION_WIDTH") + ");\n";
    }

    /**
     * @param title
     * @param urlMap
     * @param formAction
     * @param urlBegin
     * @return
     * Opens up in a new tab
     */
    private static String getMenuOptionStringAdmin(String title, String urlMap, String formAction, String urlBegin) {
        return "Menux = new Array(\"" + title + "\",\"javascript: fOpenPageAdmin('" + urlBegin + urlMap + "','" + formAction + "')\",0,20, " + AppSettings.getParm("MENU_OPTION_WIDTH") + ");\n";
    }

    /**
     * Method getMenuOptionString.
     * @param title String
     * @param href String
     * @return String
     */
    private static String getMenuOptionString(String title, String href) {
        return "Menux = new Array(\"" + title + "\",\"" + href + "\",0, 20, " + AppSettings.getParm("MENU_OPTION_WIDTH") + ");\n";
    }
}
