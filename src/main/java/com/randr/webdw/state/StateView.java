package com.randr.webdw.state;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractView;
import com.randr.webdw.util.Utilities;


/**
 */
public class StateView extends AbstractView {
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.state.StateBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.state.StateDetails";
    }

    /**
     * Method getStateArrays.
     * @return String
     */
    public String getStateArrays() {
        StringBuffer statesArrays = new StringBuffer();
        statesArrays.append("var arrState=new Array('','');");

        BigDecimal tmpCountryId = new BigDecimal(0);
        Integer countCountries = new Integer(0);

        for (int i = 0; i < this.theCollection.size(); i++) {
            StateDetails stateDetails = (StateDetails) this.theCollection.elementAt(i);

            if (stateDetails.getCountryId() != null) {
                if (stateDetails.getCountryId().equals(tmpCountryId)) {
                    //same country
                    statesArrays.append(",'" + stateDetails.getStateId() + "','" + stateDetails.getState() + " '");
                } else {
                    //new country
                    if (i > 0) {
                        statesArrays.append(");");
                    }
                    countCountries = new Integer(countCountries.intValue() + 1);
                    if (stateDetails.getState() != null) {
                        statesArrays.append("   var arrState" + stateDetails.getCountryId().toString() + " = new Array('',' ',");
                        statesArrays.append("'" + Utilities.nullToBlank(stateDetails.getStateId()) + "','");
                        statesArrays.append(Utilities.nullToBlank(stateDetails.getState()) + " '");
                    } else {
                        statesArrays.append("   var arrState" + stateDetails.getCountryId().toString() + " = new Array('','No states/provinces available '");
                    }
                    tmpCountryId = stateDetails.getCountryId();
                }
            }

        }
        if (this.theCollection.size() > 0) {
            statesArrays.append(");");
        }

        return statesArrays.toString();
    }

}
