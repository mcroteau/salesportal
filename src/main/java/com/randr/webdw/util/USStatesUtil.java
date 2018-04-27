package com.randr.webdw.util;

import java.util.Hashtable;

// import org.apache.xpath.operations.String;


/**
 */
public class USStatesUtil {

    private static StringBuffer usStatesCombo = null;
    private static Hashtable abrevsHashtable = null;

    /**
     * Method declaration
     *
     * @return String
     * @see
     */
    public static String getUSStatesCombo() {
        if (usStatesCombo == null) {
            usStatesCombo = new StringBuffer();

            usStatesCombo.append("<option>Alabama" + "<option>Alaska" + "<option>Arizona" + "<option>Arkansas" + "<option>California" + "<option>Colorado" + "<option>Connecticut" + "<option>Delaware" + "<option>District of Columbia" + "<option>Florida" + "<option>Georgia" + "<option>Hawaii" + "<option>Idaho" + "<option>Illinois" + "<option>Indiana" + "<option>Iowa" + "<option>Kansas" + "<option>Kentucky" + "<option>Louisiana" + "<option>Maine" + "<option>Maryland" + "<option>Massachusetts" + "<option>Michigan" + "<option>Minnesota" + "<option>Mississippi" + "<option>Missouri" + "<option>Montana" + "<option>Nebraska" + "<option>Nevada" + "<option>New Hampshire" + "<option>New Jersey" + "<option>New Mexico" + "<option>New York" + "<option>North Carolina" + "<option>North Dakota" + "<option>Ohio" + "<option>Oklahoma" + "<option>Oregon" + "<option>Pennsylvania" + "<option>Rhode Island" + "<option>South Carolina" + "<option>South Dakota" + "<option>Tennessee" + "<option>Texas" + "<option>Utah" + "<option>Vermont" + "<option>Virgin Islands" + "<option>Virginia" + "<option>Washington" + "<option>West Virginia" + "<option>Wisconsin" + "<option>Wyoming");
        }

        return usStatesCombo.toString();
    }

    /**
     * Method getUSStateFromAbreviation.
     * @param abreviation String
     * @return String
     */
    public static String getUSStateFromAbreviation(String abreviation) {
        if (abrevsHashtable == null) {
            loadAbrevsHashtable();
        }

        return (String) abrevsHashtable.get(abreviation);
    }

    /**
     * Method loadAbrevsHashtable.
     */
    private static void loadAbrevsHashtable() {
        abrevsHashtable = new Hashtable();

        abrevsHashtable.put("AL", "Alabama");
        abrevsHashtable.put("AK", "Alaska");
        abrevsHashtable.put("AZ", "Arizona");
        abrevsHashtable.put("AR", "Arkansas");
        abrevsHashtable.put("CA", "California");
        abrevsHashtable.put("CO", "Colorado");
        abrevsHashtable.put("CT", "Connecticut");
        abrevsHashtable.put("DE", "Delaware");
        abrevsHashtable.put("DC", "District of Columbia");
        abrevsHashtable.put("FL", "Florida");
        abrevsHashtable.put("GA", "Georgia");
        abrevsHashtable.put("HI", "Hawaii");
        abrevsHashtable.put("ID", "Idaho");
        abrevsHashtable.put("IL", "Illinois");
        abrevsHashtable.put("IN", "Indiana");
        abrevsHashtable.put("IA", "Iowa");
        abrevsHashtable.put("KS", "Kansas");
        abrevsHashtable.put("KY", "Kentucky");
        abrevsHashtable.put("LA", "Louisiana");
        abrevsHashtable.put("ME", "Maine");
        abrevsHashtable.put("MD", "Maryland");
        abrevsHashtable.put("MA", "Massachesetts");
        abrevsHashtable.put("MI", "Michigan");
        abrevsHashtable.put("MN", "Minnesota");
        abrevsHashtable.put("MS", "Mississippi");
        abrevsHashtable.put("MO", "Missouri");
        abrevsHashtable.put("MT", "Montana");
        abrevsHashtable.put("NE", "Nebraska");
        abrevsHashtable.put("NV", "Nevada");
        abrevsHashtable.put("NH", "New Hampshire");
        abrevsHashtable.put("NJ", "New Jersey");
        abrevsHashtable.put("NM", "New Mexico");
        abrevsHashtable.put("NY", "New York");
        abrevsHashtable.put("NC", "North Carolina");
        abrevsHashtable.put("ND", "North Dakota");
        abrevsHashtable.put("OH", "Ohio");
        abrevsHashtable.put("OK", "Oklahoma");
        abrevsHashtable.put("OR", "Oregon");
        abrevsHashtable.put("PA", "Pennsylvania");
        abrevsHashtable.put("RI", "Rhode Island");
        abrevsHashtable.put("SC", "South Carolina");
        abrevsHashtable.put("SD", "South Dakota");
        abrevsHashtable.put("TN", "Tennessee");
        abrevsHashtable.put("TX", "Texas");
        abrevsHashtable.put("UT", "Utah");
        abrevsHashtable.put("VT", "Vermont");
        abrevsHashtable.put("VA", "Virginia");
        abrevsHashtable.put("VI", "Virgin Islands");
        abrevsHashtable.put("WA", "Washington");
        abrevsHashtable.put("WV", "West Virginia");
        abrevsHashtable.put("WI", "Wisconsin");
        abrevsHashtable.put("WY", "Wyoming");
    }
}
