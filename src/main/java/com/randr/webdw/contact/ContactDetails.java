package com.randr.webdw.contact;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;

/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class ContactDetails extends AbstractDetails {
    protected BigDecimal contactId;
    protected BigDecimal prospectId;

    protected String contactName;
    protected String contactTitle;
    protected String phone;
    protected String phoneExt;
    protected String cellPhone;
    protected String email;
    protected String fax;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("contact_id", "contactId");
        addMapping("prospect_id", "prospectId");

        addMapping("contact_name", "contactName");
        addMapping("contact_title", "contactTitle");
        addMapping("phone", "phone");
        addMapping("phone_ext", "phoneExt");
        addMapping("cell_phone", "cellPhone");
        addMapping("fax", "fax");
        addMapping("email", "email");
    }


    /**
     * Method getContactId.
     * @return BigDecimal
     */
    public BigDecimal getContactId() {
        return contactId;
    }

    /**
     * Method setContactId.
     * @param contactId BigDecimal
     */
    public void setContactId(BigDecimal contactId) {
        this.contactId = contactId;
    }

    /**
     * Method getProspectId.
     * @return BigDecimal
     */
    public BigDecimal getProspectId() {
        return prospectId;
    }

    /**
     * Method setProspectId.
     * @param prospectId BigDecimal
     */
    public void setProspectId(BigDecimal prospectId) {
        this.prospectId = prospectId;
    }

    /**
     * Method getContactName.
     * @return String
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Method setContactName.
     * @param contactName String
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Method getContactTitle.
     * @return String
     */
    public String getContactTitle() {
        return contactTitle;
    }

    /**
     * Method setContactTitle.
     * @param contactTitle String
     */
    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    /**
     * Method getPhone.
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Method setPhone.
     * @param phone String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Method getPhoneExt.
     * @return String
     */
    public String getPhoneExt() {
        return phoneExt;
    }

    /**
     * Method setPhoneExt.
     * @param phoneExt String
     */
    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    /**
     * Method getCellPhone.
     * @return String
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * Method setCellPhone.
     * @param cellPhone String
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /**
     * Method getEmail.
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method setEmail.
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method getFax.
     * @return String
     */
    public String getFax() {
        return fax;
    }

    /**
     * Method setFax.
     * @param fax String
     */
    public void setFax(String fax) {
        this.fax = fax;
    }
}
