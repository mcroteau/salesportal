package com.randr.webdw.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;


/**
 */
public class URLHandle {
    public static final int GET = 1;
    public static final int POST = 2;
    public static final int HTTP = 1;
    public static final int HTTPS = 2;

    /**
     * Method submitURL.
     * @param urlPath String
     * @param protocol int
     * @param method int
     * @param urlParms String
     * @return BufferedReader
     * @throws Exception
     */
    public static BufferedReader submitURL(String urlPath, int protocol, int method, String urlParms) throws Exception {
        return submitURL(urlPath, protocol, method, urlParms, "text/html");
    }

    /**
     * Method submitURL.
     * @param urlPath String
     * @param protocol int
     * @param method int
     * @param urlParms String
     * @param contentType String
     * @return BufferedReader
     * @throws Exception
     */
    public static BufferedReader submitURL(String urlPath, int protocol, int method, String urlParms, String contentType) throws Exception {
        if (protocol == HTTPS) {
            if (System.getProperty("java.protocol.handler.pkgs") == null) {
                System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
            }

            if (Security.getProvider("SunJSSE") == null) {
                Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            }
        }

        URL url = null;

        if (urlParms != null) {
            url = new URL(urlPath + "?" + urlParms);
        }

        HttpURLConnection uc = (HttpURLConnection) url.openConnection();

        uc.setRequestMethod((method == GET) ? "GET" : "POST");
        uc.setRequestProperty("Content-Type", contentType);
        uc.setDoOutput(true);
        uc.setDoInput(true);

        return new BufferedReader(new InputStreamReader(uc.getInputStream()));
    }

    /**
     * Method getURLContent.
     * @param urlPath String
     * @param protocol int
     * @param method int
     * @param urlParms String
     * @return String
     * @throws Exception
     */
    public static String getURLContent(String urlPath, int protocol, int method, String urlParms) throws Exception {
        return getURLContent(urlPath, protocol, method, urlParms, "text/html");
    }

    /**
     * Method getURLContent.
     * @param urlPath String
     * @param protocol int
     * @param method int
     * @param urlParms String
     * @param contentType String
     * @return String
     * @throws Exception
     */
    public static String getURLContent(String urlPath, int protocol, int method, String urlParms, String contentType) throws Exception {
        StringBuffer pageContent = new StringBuffer("");
        BufferedReader pageContentReader = submitURL(urlPath, protocol, method, urlParms, contentType);
        String inputLine;

        while ((inputLine = pageContentReader.readLine()) != null) {
            pageContent.append(inputLine + "\n");
        }

        pageContentReader.close();

        return pageContent.toString();
    }

    /**
     * Method main.
     * @param args String[]
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // observation. for web apps, the jnet, jcert and jsse jar's have
        // to be placed either in java_home/jre/lib/ext (for websphere this is the only choice)
        // or or in the /lib folder of the application

        // this submits to an url but does not get the html source

        // URLHandle.submitURL("https://payflowlink.verisign.com/payflowlink.cfm",
        // URLHandle.HTTPS,
        // URLHandle.POST,
        // "LOGIN=randrwebdwtest1&PARTNER=VeriSign&AMOUNT=8.02&TYPE=A&ORDERFORM=False&SHOWCONFIRM=False&METHOD=CC&NAME=razvan+dani&NAMETOSHIP=razvan+dani&EXPDATE=1202&USER7=80965219308082002034448929&USER8=2&USER9=5105105105105100&USER10=1202&dfBillFirstName=razvan&dfBillLastName=dani&ADDRESS=test&CITY=test&STATE=California&ZIP=1111&PHONE=11111&FAX=11111&USER6=arobs&EMAIL=dr@arobs.com&cbCopyBill=on&dfShipFirstName=razvan&dfShipLastName=dani&ADDRESSTOSHIP=test&CITYTOSHIP=test&STATETOSHIP=California&ZIPTOSHIP=1111&PHONETOSHIP=11111&USER5=arobs&cmbPaymentType=Credit+Card&CARDNUM=5105105105105100&dfExpMonth=12&dfExpYear=02&cmbCardType=2&dfCheckNum=");

        // this submits to an url and gets the html source
//        System.out.println(URLHandle.getURLContent("https://payflowlink.verisign.com/payflowlink.cfm", URLHandle.HTTPS, URLHandle.POST, "LOGIN=randrwebdwtest1&PARTNER=VeriSign&AMOUNT=8.02&TYPE=A&ORDERFORM=False&SHOWCONFIRM=False&METHOD=CC&NAME=razvan+dani&NAMETOSHIP=razvan+dani&EXPDATE=1202&USER7=80965219308082002034448929&USER8=2&USER9=5105105105105100&USER10=1202&dfBillFirstName=razvan&dfBillLastName=dani&ADDRESS=test&CITY=test&STATE=California&ZIP=1111&PHONE=11111&FAX=11111&USER6=arobs&EMAIL=dr@arobs.com&cbCopyBill=on&dfShipFirstName=razvan&dfShipLastName=dani&ADDRESSTOSHIP=test&CITYTOSHIP=test&STATETOSHIP=California&ZIPTOSHIP=1111&PHONETOSHIP=11111&USER5=arobs&cmbPaymentType=Credit+Card&CARDNUM=5105105105105100&dfExpMonth=12&dfExpYear=02&cmbCardType=2&dfCheckNum="));
    }

}