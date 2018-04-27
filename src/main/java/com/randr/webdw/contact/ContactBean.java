package com.randr.webdw.contact;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;

/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class ContactBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "contact";
        this.tableAlias = "c";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param contactDetails ContactDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, ContactDetails contactDetails) throws Exception {
        establishSearchConditions(contactDetails);

        // set the detail fields
        addColumnList("c.contact_id, c.prospect_id");
        addColumnList("c.contact_name, c.contact_title");
        addColumnList("c.phone, c.phone_ext, c.cell_phone, c.fax, c.email");

        return doSelect(contactDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param contactDetails ContactDetails
     */
    private void establishSearchConditions(ContactDetails contactDetails) {
        if (contactDetails.getContactId() != null) {
            addCondition("c.contact_id = ?");
            addConditionParam(contactDetails.getContactId());
        }

        if (contactDetails.getProspectId() != null) {
            addCondition("c.prospect_id = ?");
            addConditionParam(contactDetails.getProspectId());
        }
        
        if (contactDetails.getContactName() != null) {
        	addCondition("upper(c.contact_name) like ?");
            addConditionParam("%" + contactDetails.getContactName().toUpperCase() + "%");
    		
    	}
        if (contactDetails.getPhone() != null) {
        	addCondition("c.phone like ?");
    		addConditionParam("%" + contactDetails.getPhone() + "%");
    	}
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param contactDetails ContactDetails
     * @return ContactDetails
     * @throws Exception
     */
    public ContactDetails insert(BigDecimal fillType, ContactDetails contactDetails) throws Exception {
        contactDetails.setContactId(getAvailableID("contact_id", "contact"));

        addColumnList("contact_id, prospect_id, contact_name, contact_title");
        addColumnList("phone, phone_ext, cell_phone, fax, email");

        return (ContactDetails) doInsert(contactDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param contactDetails ContactDetails
     * @return ContactDetails
     * @throws Exception
     */
    public ContactDetails update(BigDecimal fillType, ContactDetails contactDetails) throws Exception {
        if (contactDetails.getContactId() != null) {
            addCondition("contact_id = ?");
            addConditionParam(contactDetails.getContactId());

            addColumnList("prospect_id, contact_name, contact_title");
            addColumnList("phone, phone_ext, cell_phone, fax, email");

            return (ContactDetails) doUpdate(contactDetails);
        } else {
            return new ContactDetails();
        }
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param contactDetails ContactDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, ContactDetails contactDetails) throws Exception {
        if (contactDetails.getContactId() != null) {
            addCondition("contact_id= ?");
            addConditionParam(contactDetails.getContactId());
        }

        if (contactDetails.getProspectId() != null) {
            addCondition("prospect_id=?");
            addConditionParam(contactDetails.getProspectId());
        }
        
        if(contactDetails.getProspectId() != null || contactDetails.getContactId() != null){
            doDelete(contactDetails);
            return true;
        }else{
        	return false;
        }

    }
}
