package com.randr.webdw.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


/**
 */
public class Utilities {

    /**
     * Method toTimestamp.
     * @param value String
     * @return Timestamp
     */
    public static Timestamp toTimestamp(String value) {
        try {
            final long time = Timestamp.valueOf(value).getTime();

            return new Timestamp(time);
        } catch (Exception ex) {
        }

        try {
            final long time = Timestamp.valueOf(value + " 00:00:00").getTime();

            return new Timestamp(time);
        } catch (Exception ex) {
        }

        return null;
    }

    /**
     * Method declaration
     *
     * @param trimString
     * @return String
     * @see
     */
    public static String trim(String trimString) {
        String trimed = null;

        if (trimString != null) {
            trimed = trimString.trim();
        }

        return trimed;
    }

    /**
     * Method declaration
     *
     * @param desiredLength
     * @param current
     * @return String
     * @see
     */
    public static String fillWithChars(int desiredLength, String current) {
        String returnValue = "";

        if (desiredLength == 0) {
            return current;
        }

        if (current == null) {
            returnValue = Utilities.fillWithChars(desiredLength - 1, " ");
        } else if (current.length() >= desiredLength) {
            return current;
        } else {
            returnValue = " " + Utilities.fillWithChars(desiredLength - 1, current);
        }

        return returnValue;
    }

    /**
     * Method declaration
     *
     * @param value
     * @return Short
     * @see
     */
    public static final Short toShort(Object value) {
        Short returnValue = null;
        String temp = Utilities.toString(value);

        try {
            returnValue = Short.decode(temp);
        } catch (Exception ex) {
        }

        return returnValue;
    }

    /**
     * Method declaration
     *
     * @param value
     * @return Integer
     * @see
     */
    public static final Integer toInteger(Object value) {
        Integer returnValue = null;
        String temp = Utilities.toString(value);

        try {
            returnValue = Integer.decode(temp);
        } catch (Exception ex) {
        }

        return returnValue;
    }

    /**
     * Method declaration
     *
     * @param value
     * @return BigDecimal
     * @see
     */
    public static final BigDecimal toBigDecimal(Object value) {
        BigDecimal returnValue = null;
        String temp = Utilities.toString(value);

        try {
            returnValue = new BigDecimal(temp);
        } catch (Exception ex) {
        }

        return returnValue;
    }
    
    /**
     * Method declaration
     *
     * @param thisValue -string from CSV import
     * @return BigDecimal, if null returns new BigDecimal(0)
     * @see
     * developed for import csv to remove extraneous characters
     */
    public static final BigDecimal getBigDecimal(String thisValue) {
    	if(thisValue == null){
    		return new BigDecimal(0);
    	}
    	if(thisValue.compareTo("?")==0){
    		return new BigDecimal(0);
    	}
    	thisValue = thisValue.replace(",", "");
    	thisValue = thisValue.replace("$", "");
        BigDecimal returnValue = null;
        String temp = Utilities.toString(thisValue);

        try {
            returnValue = new BigDecimal(temp);
        } catch (Exception ex) {
        }

        return returnValue;
    }

    /**Method getYesNo. Returns a 1 for "Y" or "YES" and a 0 for "N" or "NO".
     * if null sends back 0 for No
	 * @param string
	 * @return BigDecimal
	 */
    public static final BigDecimal getYesNoFromString(String string) {
		BigDecimal yesNo;
		if(string==null){
			string="0";
		}
		if(string.toUpperCase().startsWith("Y")) {
			yesNo = new BigDecimal(1);
		} else {
			yesNo = new BigDecimal(0);
		}
		return yesNo;
	}
	
    /**Method getYesNoOpposite. Returns a 0 for "Y" or "YES" and a 1 for "N" or "NO".
     * if null sends back 0 for Yes
	 * @param string
	 * @return BigDecimal
	 */
	 public static final BigDecimal getYesNoFromStringOpposite(String string) {
		BigDecimal yesNo;
		if(string==null){
			string="0";
		}
		if(string.toUpperCase().startsWith("N")) {
			yesNo = new BigDecimal(1);
		} else {
			yesNo = new BigDecimal(0);
		}
		return yesNo;
	}

    /**
     * Method declaration
     *
     * @param value
     * @return Byte
     * @see
     */
    public static final Byte toByte(Object value) {
        Byte returnValue = null;
        String temp = Utilities.toString(value);

        try {
            returnValue = new Byte(temp);
        } catch (Exception ex) {
        }

        return returnValue;
    }

    /**
     * Method declaration
     *
     * @param value
     * @return String
     * @see
     */
    public static final String toShortTimestamp(Object value) {
        String returnValue = null;

        try {
            returnValue = value.toString();

            if (returnValue.indexOf(' ') >= 0) {
                returnValue = returnValue.substring(0, returnValue.indexOf(' '));
            }
        } catch (Exception ex) {
        }

        return returnValue;
    }

    /**
     * Method declaration
     *
     * @param value
     * @return String
     * @see
     */
    public static final String toString(Object value) {
        String returnValue = null;

        try {
            returnValue = value.toString();
        } catch (Exception ex) {
        }

        return returnValue;
    }

    /**
     * Method declaration
     *
     * @param value
     * @return String
     * @see
     */
    public static final String toNotNullString(Object value) {
        String returnValue = "";

        try {
            returnValue = value.toString();
        } catch (Exception ex) {
        }

        return returnValue;
    }

    /**
     * Method declaration
     *
     * @param value
     * @return String
     * @see
     */
    public static final String emptyStringToNullString(String value) {
        if (value == null) {
            return null;
        }

        if (value.length() == 0) {
            return null;
        }

        return value;
    }

    /**
     * Method declaration
     *
     * @param aValue
     * @return String
     * @see
     */
    public static final String getBooleanFromYesNo(String aValue) {
        if (aValue.equalsIgnoreCase("yes")) {
            return "true";
        }

        return "false";
    }

    /**
     * Method declaration
     *
     * @param value
     * @return boolean
     * @see
     */
    public static final boolean toBoolean(String value) {
        boolean returnValue = false;

        try {
            returnValue = Boolean.valueOf(value).booleanValue();
        } catch (Exception ex) {
        }

        return returnValue;
    }

    /**
     * Method declaration
     *
     * @param startFrom
     * @param upTo
     * @return ArrayList
     * @see
     */
    public static ArrayList getIntsAsStrings(int startFrom, int upTo) {
        ArrayList anArray = new ArrayList();
        int index = startFrom;

        while (index < upTo) {
            anArray.add(String.valueOf(index++));
        }

        return anArray;
    }

    /**
     * Method declaration
     *
     * @param list
     * @param value
     * @return boolean
     * @see
     */
    public static boolean containsValue(Hashtable list, Object value) {
        return list.contains(value);
    }

    /**
     * Method declaration
     *
     * @param list
     * @param value
     * @return boolean
     * @see
     */
    public static boolean containsValue(Enumeration list, Object value) {
        while (list.hasMoreElements()) {
            if (value.equals(list.nextElement())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method declaration
     *
     * @param where =  in coming string
     * @param replaced = char you want to replace
     * @param replacement = what to replace it with
     * @return String
     * @see
     */
    public static String replace(String where, String replaced, String replacement) {
        StringBuffer ret = new StringBuffer();
        String tmp = where;

        while (tmp.indexOf(replaced) > -1) {
            ret.append(tmp.substring(0, tmp.indexOf(replaced)) + replacement);

            tmp = tmp.substring(tmp.indexOf(replaced) + replaced.length());
        }

        ret.append(tmp);

        return ret.toString();
    }

    /**
     * Method truncate.
     * @param strIn String
     * @param length int
     * @return String
     */
    public static String truncate(String strIn, int length) {
        String strOut = strIn;
        if (strIn != null &&
            strIn.length() > length) {
            strOut = strIn.substring(0, length);
        }
        return strOut;
    }

    /**
     * Method convertStringDateFormat.
     * @param sDateIn String
     * @param sInitialFormat String
     * @param sConvertedFormat String
     * @return String
     */
    public static String convertStringDateFormat(String sDateIn, String sInitialFormat, String sConvertedFormat) {
        if ((sDateIn == null) || (sDateIn.equals(""))) {
            return "";
        }

        try {
            SimpleDateFormat formatterInitial = new SimpleDateFormat(sInitialFormat);
            SimpleDateFormat formatterConverted = new SimpleDateFormat(sConvertedFormat);
            Date dtDate = formatterInitial.parse(sDateIn);

            return formatterConverted.format(dtDate);
        } catch (java.text.ParseException parseExcept) {
            return "";
        }
    }

    /**
     * Method declaration
     *
     * @param sIn
     * @return String
     * @see
     */
    public static String nullToBlank(String sIn) {
        if (sIn == null) {
            return "";
        } else {
            return sIn;
        }
    }

    /**
     * Method declaration
     *
     * @param number
     * @return String
     * @see
     */
    public static String nullToBlank(BigDecimal number) {
        if (number == null) {
            return "";
        } else {
            return number.toString();
        }
    }

    /**
     * Method declaration
     *
     * @param number
     * @return String
     * @see
     */
    public static String nullToBlank(Integer number) {
        if (number == null) {
            return "";
        } else {
            return number.toString();
        }
    }

    
    /**
     * Method declaration
     *
     * @param sIn
     * @return String
     * @see
     */
    public static String nullToDash(String sIn) {
        if (sIn == null || sIn.equals("")) {
            return "-";
        } else {
            return sIn;
        }
    }

    /**
     * Method declaration
     *
     * @param number
     * @return String
     * @see
     */
    public static String nullToDash(BigDecimal number) {
        if (number == null) {
            return "-";
        } else {
            return number.toString();
        }
    }

    /**
     * Method declaration
     *
     * @param number
     * @return String
     * @see
     */
    public static String nullToDash(Integer number) {
        if (number == null) {
            return "-";
        } else {
            return number.toString();
        }
    }
    

    
    
    /**
     * Method declaration
     *
     * @param number
     * @return BigDecimal
     * @see
     */
    public static BigDecimal nullToZero(BigDecimal number) {
        if (number == null) {
            return new BigDecimal("0");
        } else {
            return number;
        }
    }
    public static BigDecimal nullToZero(String number) {
        if (number == null) {
            return new BigDecimal("0");
        } else {
            return new BigDecimal(number);
        }
    }

    /**
     * Method nullToValue.
     * @param number BigDecimal
     * @param defaultValue BigDecimal
     * @return BigDecimal
     */
    public static BigDecimal nullToValue(BigDecimal number, BigDecimal defaultValue) {
        if (number == null) {
            return defaultValue;
        } else {
            return number;
        }
    }

    /**
     * Method declaration
     *
     * @param number
     * @return Integer
     * @see
     */
    public static Integer nullToZero(Integer number) {
        if (number == null) {
            return new Integer("0");
        } else {
            return number;
        }
    }

    /**
     * Method declaration
     *
     * @param string
     * @param outString
     * @return String
     * @see
     */
    public static String nullToString(String string, String outString) {
        if (string == null) {
            return outString;
        } else {
            return string;
        }
    }

    /**
     * Method declaration
     *
     * @param string
     * @param outString
     * @return String
     * @see
     */
    public static String blankToString(String string, String outString) {
        if ((string == null) || (string.equals(""))) {
            return outString;
        } else {
            return string;
        }
    }

    /**
     * Method blankToNull.
     * @param string String
     * @return String
     */
    public static String blankToNull(String string) {
        if ((string == null) || (string.trim().length() == 0)) {
            return null;
        } else {
            return string;
        }
    }

    /*
     * public static String getTagContent(String sIn) {
     * int nOffset1=sIn.indexOf(">");
     * int nOffset2=sIn.lastIndexOf("</");
     * return sIn.substring(nOffset1+1,nOffset2);
     * }
     */

    /**
     * Method declaration
     *
     * @param sIni
     * @param sFrom
     * @param sTo
     * @return String
     * @see
     */
    public static String replaceString(String sIni, String sFrom, String sTo) {
        return replaceString(sIni, sFrom, sTo, true);
    }

    /**
     * Method declaration
     *
     * @param sIni
     * @param sFrom
     * @param sTo
     * @param caseSensitiveSearch
     * @return String
     * @see
     */
    public static String replaceString(String sIni, String sFrom, String sTo, boolean caseSensitiveSearch) {
        int i = 0;
        String s = new String(sIni);
        StringBuffer rez = new StringBuffer();

        if (caseSensitiveSearch) {
            i = s.indexOf(sFrom);
        } else {
            i = s.toLowerCase().indexOf(sFrom.toLowerCase());
        }

        while (i != -1) {
            rez.append(s.substring(0, i));
            rez.append(sTo);

            s = s.substring(i + sFrom.length());

            if (caseSensitiveSearch) {
                i = s.indexOf(sFrom);
            } else {
                i = s.toLowerCase().indexOf(sFrom.toLowerCase());
            }
        }

        rez.append(s);

        return rez.toString();
    }

    /**
     * Method declaration
     *
     * @param sIn
     * @param sTokenizer
     * @return Vector
     * @see
     */
    public static Vector tokenize(String sIn, String sTokenizer) {
        Vector vToken = new Vector();
        int nOffset = 0;
        int nOffset1 = 0;

        nOffset1 = sIn.indexOf(sTokenizer, nOffset);

        while (nOffset1 > -1) {
            if (nOffset1 > nOffset) {
                vToken.add(sIn.substring(nOffset, nOffset1));
            } else {
                vToken.add("");
            }

            nOffset = nOffset1 + sTokenizer.length();
            nOffset1 = sIn.indexOf(sTokenizer, nOffset);
        }

        if (nOffset < sIn.length()) {
            vToken.add(sIn.substring(nOffset, sIn.length()));
        } else {
            vToken.add("");
        }

        return vToken;
    }

    /**
     * Method tokenizeToArray.
     * @param sIn String
     * @param sTokenizer String
     * @return String[]
     */
    public static String[] tokenizeToArray(String sIn, String sTokenizer) {
        Vector vToken = tokenize(sIn, sTokenizer);
        String[] tokenArray = new String[vToken.size()];

        for (int i = 0; i < vToken.size(); i++) {
            tokenArray[i] = (String) vToken.elementAt(i);
        }

        return tokenArray;
    }

    /**
     * Method declaration
     *
     * @param sVal
     * @return String
     * @see
     */
    public static String formatXMLString(String sVal) {
        String sRetVal = sVal;

        if (sVal == null) {
            return "";
        }

        //Vector vXMLReplacements = tokenize("&iexcl;�;&cent;�;&pound;�;&curren;�;&yen;�;&brvbar;�;&sect;�;" + "&uml;�;&copy;�;&ordf;�;&laquo;�;&not;�;&shy;�;&reg;�;&macr;�;&deg;�;&plusmn;�;&sup2;�;&sup3;�;" + "&acute;�;&micro;�;&para;�;&middot;�;&cedil;�;&sup1;�;&ordm;�;&raquo;�;&frac14;�;&frac12;�;" + "&frac34;�;&iquest;�;&Agrave;�;&Aacute;�;&Acirc;�;&Atilde;�;&Auml;�;&Aring;�;&AElig;�;&Ccedil;�;" + "&Egrave;�;&Eacute;�;&Ecirc;�;&Euml;�;&Igrave;�;&Iacute;�;&Icirc;�;&Iuml;�;&ETH;�;&Ntilde;�;" + "&Ograve;�;&Oacute;�;&Ocirc;�;&Otilde;�;&Ouml;�;&times;�;&Oslash;�;&Ugrave;�;&Uacute;�;&Ucirc;�;" + "&Uuml;�;&Yacute;�;&THORN;�;&szlig;�;&agrave;�;&aacute;�;&acirc;�;&atilde;�;&auml;�;&aring;�;" + "&aelig;�;&ccedil;�;&egrave;�;&eacute;�;&ecirc;�;&euml;�;&igrave;�;&iacute;�;&icirc;�;&iuml;�;" + "&eth;�;&ntilde;�;&ograve;�;&oacute;�;&ocirc;�;&otilde;�;&ouml;�;&divide;�;&oslash;�;&ugrave;�;" + "&uacute;�;&ucirc;�;&uuml;�;&yacute;�;&thorn;�;&yuml;�", ";");
        Vector vXMLReplacements = null;//TODO:
        int i = 0;

        for (i = 0; i < vXMLReplacements.size(); i = i + 2) {
            sRetVal = replaceString(sRetVal, (String) vXMLReplacements.elementAt(i + 1), (String) vXMLReplacements.elementAt(i));
        }

        char[] wrongChar = {'�'};
        String wrongString = new String(wrongChar);

        sRetVal = replaceString(sRetVal, wrongString, " ");

        return sRetVal;
    }

    /**
     * Method declaration
     *
     * @param number
     * @param decimals
     * @return String
     * @see
     */
    public static String formatNumber(BigDecimal number, int decimals) {
        if (number == null) {
            return "";
        }
        return number.setScale(decimals, BigDecimal.ROUND_FLOOR).toString();
    }

    /**
     * Method formatNumber.
     * @param number BigDecimal
     * @param pattern String
     * @return String
     */
    public static String formatNumber(BigDecimal number, String pattern) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(number);
    }
    /**
     * Method repeatStr.
     * @param str String
     * @param nrRepeat int
     * @return String
     */
    public static String repeatStr(String str, int nrRepeat) {
        String sReturn = "";

        for (int i = 1; i <= nrRepeat; i++) {
            sReturn = sReturn + str;
        }

        return sReturn;
    }

    /**
     * Method encrypt.
     * @param string String
     * @param encoding String
     * @return String
     */
    public static String encrypt(String string, String encoding) {
        String outStr = "";
        String strTmp = permute2(permute1(string));
        int nL = strTmp.length();

        for (int i = 1; i <= nL; i++) {
            try {
                outStr = outStr + (new BigInteger(1, strTmp.substring(i - 1, i).getBytes(encoding))).toString(16);
            } catch (Exception e) {
            }
        }

        outStr = permute1(outStr);

        return outStr;
    }

    /**
     * Method encrypt.
     * @param string String
     * @return String
     */
    public static String encrypt(String string) {
        return encrypt(string, "US-ASCII");
    }

    /**
     * Method declaration
     *
     * @param string
     * @return String
     * @see
     */

    public static String decrypt(String string) {
        return decrypt(string, "US-ASCII");
    }

    /**
     * Method decrypt.
     * @param string String
     * @param encoding String
     * @return String
     */
    public static String decrypt(String string, String encoding) {
        String strOut = "";
        String strTmp = permute1(string);
        int nL = strTmp.length();

        String charStr = null;
        for (int i = 1; i <= nL; i = i + 2) {

            // strTmp.substring(i-1,i+1);
            try {
                charStr = new String((new BigInteger(strTmp.substring(i - 1, i + 1), 16)).toByteArray(), encoding);
                if (charStr.length() == 2) {
                    charStr = charStr.substring(1);
                }
                //strOut = strOut + new String((new BigInteger(strTmp.substring(i - 1, i + 1), 16)).toByteArray(), encoding);
                strOut = strOut + charStr;
            } catch (Exception e) {
            }

            // strOut = strOut +
        }

        strOut = permute1(permute2(strOut));

        return strOut;
    }

    /**
     * Method declaration
     *
     * @param string
     * @return String
     * @throws Exception
     * @see
     */
    public static String hexToZecimal(String string) throws Exception {
        double num = 0;
        double pow = 0;
        byte aCode = "a".getBytes()[0];
        byte fCode = "f".getBytes()[0];
        byte zeroCode = "0".getBytes()[0];
        byte nineCode = "9".getBytes()[0];
        byte charCode;
        String character = null;

        for (int i = 0; i < string.length(); i++) {
            pow = Math.pow(16, string.length() - i - 1);
            character = string.substring(i, i + 1);

            if ((character.getBytes()[0] >= zeroCode) && (character.getBytes()[0] <= nineCode)) {
                num = num + Integer.parseInt(character) * pow;
            } else if ((character.toLowerCase().getBytes()[0] >= aCode) && (character.toLowerCase().getBytes()[0] <= fCode)) {
                charCode = character.toLowerCase().getBytes()[0];
                num = num + (10 + (charCode - aCode)) * pow;
            } else {
                throw new Exception("Invalid hex string");
            }
        }

        return String.valueOf((new BigDecimal(num)).intValue());
    }

    /**
     * Method declaration
     *
     * @param string
     * @return String
     * @see
     */
    private static String permute1(String string) {
        String outString = string;
        String tmpString = "";
        String tmp1String = "";
        int nL = outString.length();

        for (int i = 1; i <= nL / 2; i++) {
            tmp1String = "";

            try {
                tmpString = outString.substring(0, i - 1);
            } catch (Exception e1) {
                tmpString = "";
            } finally {
                tmp1String = tmp1String + tmpString;
            }

            try {
                tmpString = outString.substring(nL - i, nL - i + 1);
            } catch (Exception e2) {
                tmpString = "";
            } finally {
                tmp1String = tmp1String + tmpString;
            }

            try {
                tmpString = outString.substring(i, i + (nL - 2 * i));
            } catch (Exception e3) {
                tmpString = "";
            } finally {
                tmp1String = tmp1String + tmpString;
            }

            try {
                tmpString = outString.substring(i - 1, i);
            } catch (Exception e4) {
                tmpString = "";
            } finally {
                tmp1String = tmp1String + tmpString;
            }

            try {
                tmpString = outString.substring(nL - i + 1);
            } catch (Exception e5) {
                tmpString = "";
            } finally {
                tmp1String = tmp1String + tmpString;
            }

            outString = tmp1String;
        }

        return outString;
    }

    /**
     * Method declaration
     *
     * @param string
     * @return String
     * @see
     */
    private static String permute2(String string) {
        String outString = string;
        int nL = outString.length();

        for (int i = 1; i <= nL; i = i + 4) {
            if (i + 3 <= nL) {
                outString = outString.substring(0, i - 1) + outString.substring(i + 1, i + 2) + outString.substring(i + 2, i + 3) + outString.substring(i - 1, i) + outString.substring(i, i + 1) + outString.substring(i + 3);
            } else {
                if (i + 2 == nL) {
                    outString = outString.substring(0, i - 1) + outString.substring(i + 1, i + 2) + outString.substring(i, i + 1) + outString.substring(i - 1, i);
                }
            }
        }

        return outString;
    }

    /**
     * Method declaration
     *
     * @param stringArray
     * @param delimiter
     * @return String
     * @see
     */
    public static String getStringList(String[] stringArray, String delimiter) {
        String stringList = "";

        for (int i = 0; i < stringArray.length; i++) {
            stringList = stringList + delimiter + replaceString(stringArray[i], "'", "''") + delimiter + ",";
        }

        if (stringList.length() > 0) {
            stringList = stringList.substring(0, stringList.length() - 1);
        }

        return stringList;
    }

    /**
     * Method booleanToString.
     * @param boolVal boolean
     * @return String
     */
    public static String booleanToString(boolean boolVal) {
        if (boolVal) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * Method isNumber.
     * @param strIn String
     * @return boolean
     */
    public static boolean isNumber(String strIn) {
        boolean res = false;
        if (strIn != null && strIn.length() > 0) {
            for (int i = 0; i < strIn.length(); i++) {
                if (strIn.charAt(i) < '0' || strIn.charAt(i) > '9') {
                    return false;
                }
            }
            return true;
        }
        return res;
    }

    /**
     * Method formatPhoneNumber.
     * @param strIn String
     * @return String
     */
    public static String formatPhoneNumber(String strIn) {
        String strOut = strIn;
        if (strIn != null && isNumber(strIn)) {
            if (strIn.length() == 7) {
                strOut = strIn.substring(0, 3) + "-" + strIn.substring(3, 7);
            } else if (strIn.length() == 10) {
                strOut = strIn.substring(0, 3) + "-" + strIn.substring(3, 6) + "-" + strIn.substring(6, 10);
            }
        }
        return strOut;
    }

    /**
     * Method main.
     * @param args String[]
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        System.out.println(formatPhoneNumber("1234567"));
//        System.out.println(formatPhoneNumber("1234567890"));
    }

    /**
     * Method declaration
     *
     * @param string
     * @param word
     * @param replaceWord
     * @return String
     * @see
     */
    public static String replaceWholeWord(String string, String word, String replaceWord) {
        if (word.equals(replaceWord)) {
            return string;
        }

        String replacedString = string;
        String tmpString = null;
        String upperWord = word.toUpperCase();
        int offset = replacedString.toUpperCase().indexOf(upperWord);

        while (offset > -1) {
            if (((offset == 0) || (isWordDelimiter(replacedString.charAt(offset - 1)))) && ((offset + word.length() == replacedString.length()) || (isWordDelimiter(replacedString.charAt(offset + word.length()))))) {
                tmpString = "";

                if (offset > 0) {
                    tmpString = replacedString.substring(0, offset);
                }

                tmpString = tmpString + replaceWord;

                if (offset + word.length() < replacedString.length()) {
                    tmpString = tmpString + replacedString.substring(offset + word.length());
                }

                replacedString = tmpString;
            }

            offset = replacedString.toUpperCase().indexOf(upperWord, offset + 1);
        }

        return replacedString;
    }

    /**
     * Method declaration
     *
     * @param c
     * @return boolean
     * @see
     */
    public static boolean isWordDelimiter(char c) {
        if ((c == ' ') || (c == ',') || (c == ';') || (c == '(') || (c == ')') || (c == '\n') || (c == '.')) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method replaceIlegalFileChars.
     * @param fileStr String
     * @return String
     */
    public static String replaceIlegalFileChars(String fileStr) {
        String replacedFileStr = fileStr;

        replacedFileStr = Utilities.replaceString(replacedFileStr, " ", "_");
        replacedFileStr = Utilities.replaceString(replacedFileStr, "&", "");
        replacedFileStr = Utilities.replaceString(replacedFileStr, "%", "");
        replacedFileStr = Utilities.replaceString(replacedFileStr, ",", "");
        replacedFileStr = Utilities.replaceString(replacedFileStr, ";", "");
        replacedFileStr = Utilities.replaceString(replacedFileStr, "/", "_");

        return replacedFileStr;
    }

    /**
     * Method validWebSiteAddress.
     * @param websiteStr String
     * @return String
     */
    public static String validWebSiteAddress(String websiteStr) {
        String resWebsiteStr = "";
        if (websiteStr != null) {
            if (websiteStr.startsWith("http://") || websiteStr.startsWith("https://")) {
                resWebsiteStr = websiteStr;
            } else {
                resWebsiteStr = "http://" + websiteStr;
            }
        }
        return resWebsiteStr;
    }

    /**
     * Method countOccurences.
     * @param string String
     * @param searchString String
     * @return int
     */
    public static int countOccurences(String string, String searchString) {
        int count = 0;
        int prevOffset = -1;
        int offset = -1;

        do {
            offset = string.substring(prevOffset + 1).indexOf(searchString);

            if (offset > -1) {
                count++;
                prevOffset = prevOffset + offset + 1;
            }
        } while (offset > -1);

        return count;
    }

    /**
     * Method getOccurences.
     * @param string String
     * @param searchString String
     * @return Vector
     */
    public static Vector getOccurences(String string, String searchString) {
//        int count = 0;
        int prevOffset = -1;
        int offset = -1;
        Vector occurences = new Vector();

        do {
            offset = string.substring(prevOffset + 1).indexOf(searchString);
            if (offset > -1) {
//                count++;
                prevOffset = prevOffset + offset + 1;
                occurences.addElement(new BigDecimal(prevOffset));
            }
        } while (offset > -1);

        return occurences;
    }

    /**
     * Method removeExtraBlanks.
     * @param string String
     * @return String
     */
    public static String removeExtraBlanks(String string) {
        String resultString = "";
        Vector tokens = tokenize(string, " ");
        for (int i = 0; i < tokens.size(); i++) {
            if (!tokens.elementAt(i).equals("")) {
                resultString += (String) tokens.elementAt(i) + " ";
            }
        }
        return resultString.trim();
    }
    
    /**
     * Method escapeProblemCharacters. Replaces characters that can cause an html 
     * or javascript break if found in the string.
     * 
     * @param string - dirty input string
     * @param secondaryReplacementString - if string above is blank or null use this string
     * 
     * @return
     */
    public static String escapeProblemCharacters(String string, String secondaryReplacementString) {
    	//Make sure ampersand is first or method will break
    	String[] charactersToReplace = {"&", "\"", "<", ">", "'", "\n"};
    	String[] replacementCharacters = {"&amp;", "&quot;", "&lt;", "&gt;", "\\'", "&lt;br&gt;"};
    	
    	string = nullToBlank(string);
    	secondaryReplacementString = nullToBlank(secondaryReplacementString);
    	string = blankToString(string, secondaryReplacementString);
    	
    	for(int i = 0; i < charactersToReplace.length; i++) {
    		string = replace(string, charactersToReplace[i], replacementCharacters[i]);
    	}
    	
    	return string;
    }

	public static Object nullToBlank(Timestamp date) {
		if (date == null) {
            return "";
        } else {
            return date.toString();
        }
	}


}


