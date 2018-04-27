package com.randr.webdw.importData.importProcessor;

import java.math.BigDecimal;

import com.randr.webdw.importData.importProcessorAbstract.ImportParameters;

/**
 * User: cami
 * Date: Jul 19, 2005
 * @author randr
 * @version $Revision: 1.1 $
 */
public class ProspectImportParameters extends ImportParameters{
    BigDecimal companyNumber;

    boolean createStatus = false;
    boolean createCountry = false;
    boolean createState = false;
    boolean createSystemType = false;
    boolean createSoftwareType = false;
    boolean createLob = false;
    boolean createTerritory = false;
    boolean createVerified = false;
    boolean createSize = false;
    boolean eliminateDups = false;

    /**
     * Method getCompanyNumber.
     * @return BigDecimal
     */
    public BigDecimal getCompanyNumber() {
        return companyNumber;
    }

    /**
     * Method setCompanyNumber.
     * @param companyNumber BigDecimal
     */
    public void setCompanyNumber(BigDecimal companyNumber) {
        this.companyNumber = companyNumber;
    }

    /**
     * Method isCreateStatus.
     * @return boolean
     */
    public boolean isCreateStatus() {
        return createStatus;
    }

    /**
     * Method setCreateStatus.
     * @param createStatus boolean
     */
    public void setCreateStatus(boolean createStatus) {
        this.createStatus = createStatus;
    }

    /**
     * Method isCreateCountry.
     * @return boolean
     */
    public boolean isCreateCountry() {
        return createCountry;
    }

    /**
     * Method setCreateCountry.
     * @param createCountry boolean
     */
    public void setCreateCountry(boolean createCountry) {
        this.createCountry = createCountry;
    }

    /**
     * Method isCreateState.
     * @return boolean
     */
    public boolean isCreateState() {
        return createState;
    }

    /**
     * Method setCreateState.
     * @param createState boolean
     */
    public void setCreateState(boolean createState) {
        this.createState = createState;
    }

    /**
     * Method isCreateSystemType.
     * @return boolean
     */
    public boolean isCreateSystemType() {
        return createSystemType;
    }

    /**
     * Method setCreateSystemType.
     * @param createSystemType boolean
     */
    public void setCreateSystemType(boolean createSystemType) {
        this.createSystemType = createSystemType;
    }

    /**
     * Method isCreateSoftwareType.
     * @return boolean
     */
    public boolean isCreateSoftwareType() {
        return createSoftwareType;
    }

    /**
     * Method setCreateSoftwareType.
     * @param createSoftwareType boolean
     */
    public void setCreateSoftwareType(boolean createSoftwareType) {
        this.createSoftwareType = createSoftwareType;
    }

    /**
     * Method isCreateLob.
     * @return boolean
     */
    public boolean isCreateLob() {
        return createLob;
    }

    /**
     * Method setCreateLob.
     * @param createLob boolean
     */
    public void setCreateLob(boolean createLob) {
        this.createLob = createLob;
    }

    /**
     * Method isCreateTerritory.
     * @return boolean
     */
    public boolean isCreateTerritory() {
        return createTerritory;
    }

    /**
     * Method setCreateTerritory.
     * @param createTerritory boolean
     */
    public void setCreateTerritory(boolean createTerritory) {
        this.createTerritory = createTerritory;
    }

    /**
     * Method isCreateVerified.
     * @return boolean
     */
    public boolean isCreateVerified() {
        return createVerified;
    }

    /**
     * Method setCreateVerified.
     * @param createVerified boolean
     */
    public void setCreateVerified(boolean createVerified) {
        this.createVerified = createVerified;
    }

    /**
     * Method isCreateSize.
     * @return boolean
     */
    public boolean isCreateSize() {
        return createSize;
    }

    /**
     * Method setCreateSize.
     * @param createSize boolean
     */
    public void setCreateSize(boolean createSize) {
        this.createSize = createSize;
    }

    /**
     * Method toString.
     * @return String
     */
    public String toString() {
        return "ProspectImportParameters{" +
               "companyNumber=" + companyNumber +
               ", createStatus=" + createStatus +
               ", createCountry=" + createCountry +
               ", createState=" + createState +
               ", createSystemType=" + createSystemType +
               ", createSoftwareType=" + createSoftwareType +
               ", createLob=" + createLob +
               ", createTerritory=" + createTerritory +
               ", createVerified=" + createVerified +
               ", createSize=" + createSize +
               "}";
    }

	/**
	 * @return the eliminateDups
	 */
	public boolean isEliminateDups() {
		return eliminateDups;
	}

	/**
	 * @param eliminateDups the eliminateDups to set
	 */
	public void setEliminateDups(boolean eliminateDups) {
		this.eliminateDups = eliminateDups;
	}
}
