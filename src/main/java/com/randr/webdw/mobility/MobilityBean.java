package com.randr.webdw.mobility;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.util.Utilities;


/**
 */
public class MobilityBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "prospect";
        this.tableAlias = "p";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, MobilityDetails mobilityDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(mobilityDetails);

        // establish the list of properties to be populated

        return doSelect(mobilityDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param lobDetails LobDetails
     */
    private void establishSearchConditions(MobilityDetails mobilityDetails) {

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return LobDetails
     * @throws Exception
     */
    public MobilityDetails insert(BigDecimal fillType, MobilityDetails mobilityDetails) throws Exception {


        return (MobilityDetails) doInsert(mobilityDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return LobDetails
     * @throws Exception
     */
    public MobilityDetails update(BigDecimal fillType, MobilityDetails mobilityDetails) throws Exception {


        return (MobilityDetails) doUpdate(mobilityDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param lobDetails LobDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, MobilityDetails mobilityDetails) throws Exception {

//        doDelete(mobilityDetails);
        return true;
    }
}
