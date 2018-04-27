package com.randr.webdw.prospect;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDetails;
import com.randr.webdw.util.Utilities;

/**
 * Maps the PROSPECT table
 * User: Cami
 * Date: Sep 25, 2003
 * Time: 12:37:24 PM
 * @author randr
 * @version $Revision: 1.9 $
 */
public class ProspectDetails extends AbstractDetails {
	
    
    protected BigDecimal prospectId;

    protected String customerCompany;

    protected String address;
    protected String address2;
    protected String city;
    protected String county;
    protected String zip;
    protected BigDecimal stateId;
    protected BigDecimal countryId;

    protected String contactName;
    protected String contactTitle;
    protected String phone;
    protected String phoneExt;
    protected String cellPhone;
    protected String email;
    protected String fax;
    protected String website;

//    protected String size;
    protected String systemNo;
    protected String ssa;
    protected String sic;
    protected String hardwareMaintenance;


    protected BigDecimal companyId;
    protected BigDecimal systemTypeId;
    protected BigDecimal territoryId;
    protected BigDecimal lobId;
    protected BigDecimal softwareTypeId;
    protected BigDecimal statusId;
    protected BigDecimal verifiedId;
    protected BigDecimal sizeId;

    protected Timestamp creationDate;
    protected Timestamp changeDate;

    protected BigDecimal creationUserId;
    protected BigDecimal changeUserId;

    protected Timestamp territoryChangeDate;
    
    protected List territoryIdList;
    protected List statusIdList;
    protected List lobIdList;
    
    protected BigDecimal searchLimit;
    protected Timestamp activityDate;
    protected Timestamp optOutDate;// this will come from Order Portal
    protected boolean notOptedOut; //true = opt out date is null
    protected boolean territoryIsNull; //true = prospect has no territory
    protected boolean lobIsNull; //true = prospect has no lob
    protected boolean statusIsNull; //true = prospect has no status
    
    protected String emailMailToString;//this gets build in the controller
    
    protected BigDecimal territoryAssignedOnProspectCreation;
    protected boolean excludeNIAndDNC;//used in the automated email campaigns
    
    //fields used for email roundtrip/autoresponder
    protected BigDecimal emailDraftToUse;//which email draft # to use (see OP for the number)
    protected String fromEmailToUse;//if blank use email in OP webprofile
    protected String emailDescription;//description of email
    protected String salesmanName;//territory owner name
    
    protected String genericSearchText;//use for searching mulitple fields
    protected BigDecimal genericSearchNumeric;//use for searching mulitple fields
    protected Date genericSearchDate;//use for searching mulitple fields
    
    //performance Data
    protected BigDecimal openOrdersNumber;
    protected BigDecimal openOrdersValue;
    protected BigDecimal openQuotesNumber;
    protected BigDecimal openQuotesValue;
    protected BigDecimal openAccountsReceivable;
    protected BigDecimal invoicesNumberLtd; //life to date
    protected BigDecimal invoicesValueLtd;  // life to date 
    protected BigDecimal invoicesNumberYtd; // year to date
    protected BigDecimal invoicesValueYtd; //year to date
    protected BigDecimal forecast;
    protected BigDecimal orderportalUserId;
    protected BigDecimal ebsUserId;

    //fields used in JOINS
    protected String companyName;
    protected String systemTypeName;
    protected String territoryName;
    protected String lobName;
    protected String softwareTypeName;
    protected String statusName;
    protected String verifiedName;
    protected String sizeName;

    protected String stateName;
    protected String countryName;

    protected String creationUserName;
    protected String changeUserName;
    
    protected String externalId;

    protected Integer countNotes;

    protected Date searchNextSalesActionDate;
    protected Date searchNextSalesActionDateEnd;
    protected BigDecimal searchNextSalesActionId;
    protected Date searchChangeDate;
    protected Date searchExactChangeDate;

    protected BigDecimal territoryOwnerUserId;
    protected String territoryOwnerUserName;
    protected String territoryOwnerEmail;
    protected BigDecimal territoryOwnerActive;

    protected BigDecimal territorySalesManagerUserId;
    protected String territorySalesManagerUserName;
    protected String territorySalesManagerEmail;
    protected BigDecimal territorySalesManagerActive;

    protected BigDecimal territoryServiceManagerUserId;
    protected String territoryServiceManagerUserName;
    protected String territoryServiceManagerEmail;
    protected BigDecimal territoryServiceManagerActive;

    public static final String countNotesField = "count(n.note_id)";
    public String orderByFieldName;

    protected Vector idsVector;
    
    //New for Current Sales Actions
    //Join prospect_sales_action table
    protected BigDecimal currentSalesActionId;
    protected BigDecimal currentSalesActionPriority;
    protected String currentSalesActionNote;
    protected Timestamp currentSalesActionDate;
    protected BigDecimal currentSalesActionStatus;
     
	/**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("prospect_id", "prospectId");

        addMapping("customer_company", "customerCompany");
        addMapping("address", "address");
        addMapping("address2", "address2");
        addMapping("city", "city");
        addMapping("zip", "zip");
        addMapping("county", "county");
        addMapping("state_id", "stateId");
        addMapping("country_id", "countryId");

        addMapping("contact_name", "contactName");
        addMapping("contact_title", "contactTitle");

        addMapping("phone", "phone");
        addMapping("phone_ext", "phoneExt");
        addMapping("cell_phone", "cellPhone");
        addMapping("fax", "fax");
        addMapping("email", "email");
        addMapping("website", "website");

        addMapping("size", "size");
        addMapping("system_no", "systemNo");
        addMapping("ssa", "ssa");
        addMapping("sic", "sic");
        addMapping("hardmnt", "hardwareMaintenance");

        addMapping("company_id", "companyId");
        addMapping("system_type_id", "systemTypeId");
        addMapping("territory_id", "territoryId");
        addMapping("lob_id", "lobId");
        addMapping("software_type_id", "softwareTypeId");
        addMapping("status_id", "statusId");
        addMapping("size_id", "sizeId");
        addMapping("verified_id", "verifiedId");

        addMapping("creation_date", "creationDate");
        addMapping("change_date", "changeDate");
        addMapping("creation_user_id", "creationUserId");
        addMapping("change_user_id", "changeUserId");
        
        addMapping("territory_changed_date", "territoryChangeDate");
        addMapping("external_id", "externalId");
        addMapping("activity_date", "activityDate");
        addMapping("optout_date", "optOutDate");
        
        addMapping("territory_assigned_on_creation", "territoryAssignedOnProspectCreation");

        addMapping("open_orders_number", "openOrdersNumber");
        addMapping("open_orders_value", "openOrdersValue");
        addMapping("open_quotes_number", "openQuotesNumber");
        addMapping("open_quotes_value", "openQuotesValue");
        addMapping("open_accounts_receivable", "openAccountsReceivable");
        addMapping("orders_invoices_number_ltd", "invoicesNumberLtd");
        addMapping("orders_invoices_value_ltd", "invoicesValueLtd");
        addMapping("orders_invoices_number_ytd", "invoicesNumberYtd");
        addMapping("orders_invoices_value_ytd", "invoicesValueYtd");
        addMapping("forecast", "forecast");
        addMapping("orderportal_user_id", "orderportalUserId");
        addMapping("ebs_user_id", "ebsUserId");

        //join
        addMapping("cn.country", "countryName");
        addMapping("sta.state", "stateName");
        addMapping("c.company", "companyName");
        addMapping("t.territory", "territoryName");
        addMapping("l.lob", "lobName");
        addMapping("sy.system_type", "systemTypeName");
        addMapping("sf.software_type", "softwareTypeName");
        addMapping("st.status", "statusName");
        addMapping("si.size", "sizeName");
        addMapping("v.verified", "verifiedName");
        

        addMapping(countNotesField, "countNotes");

        addMapping("t.owner_user_id", "territoryOwnerUserId");
        addMapping("u1.user_name", "territoryOwnerUserName");
        addMapping("u1.email", "territoryOwnerEmail");
        addMapping("u1.active", "territoryOwnerActive");

        addMapping("t.sales_manager_user_id", "territorySalesManagerUserId");
        addMapping("u2.user_name", "territorySalesManagerUserName");
        addMapping("u2.email", "territorySalesManagerEmail");
        addMapping("u2.active", "territorySalesManagerActive");

        addMapping("t.service_manager_user_id", "territoryServiceManagerUserId");
        addMapping("u3.user_name", "territoryServiceManagerUserName");
        addMapping("u3.email", "territoryServiceManagerEmail");
        addMapping("u3.active", "territoryServiceManagerActive");
        
        //
        addMapping("psa.sales_action_id", "currentSalesActionId");
        addMapping("psa.action_note", "currentSalesActionNote");
        addMapping("psa.action_date", "currentSalesActionDate");
        addMapping("psa.action_priority", "currentSalesActionPriority");
        addMapping("psa.action_status", "currentSalesActionStatus");
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
     * Method getCustomerCompany.
     * @return String
     */
    public String getCustomerCompany() {
        return customerCompany;
    }

    /**
     * Method setCustomerCompany.
     * @param customerCompany String
     */
    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    /**
     * Method getAddress.
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Method setAddress.
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Method getAddress2.
     * @return String
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Method setAddress2.
     * @param address2 String
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Method getCity.
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * Method setCity.
     * @param city String
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Method getCounty.
     * @return String
     */
    public String getCounty() {
        return county;
    }

    /**
     * Method setCounty.
     * @param county String
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * Method getZip.
     * @return String
     */
    public String getZip() {
        return zip;
    }

    /**
     * Method setZip.
     * @param zip String
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Method getStateId.
     * @return BigDecimal
     */
    public BigDecimal getStateId() {
        return stateId;
    }

    /**
     * Method setStateId.
     * @param stateId BigDecimal
     */
    public void setStateId(BigDecimal stateId) {
        this.stateId = stateId;
    }

    /**
     * Method getCountryId.
     * @return BigDecimal
     */
    public BigDecimal getCountryId() {
        return countryId;
    }

    /**
     * Method setCountryId.
     * @param countryId BigDecimal
     */
    public void setCountryId(BigDecimal countryId) {
        this.countryId = countryId;
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

    /**
     * Method getWebsite.
     * @return String
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Method setWebsite.
     * @param website String
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Method getSizeId.
     * @return BigDecimal
     */
    public BigDecimal getSizeId() {
        return sizeId;
    }

    /**
     * Method setSizeId.
     * @param sizeId BigDecimal
     */
    public void setSizeId(BigDecimal sizeId) {
        this.sizeId = sizeId;
    }

    /**
     * Method getSizeName.
     * @return String
     */
    public String getSizeName() {
        return sizeName;
    }

    /**
     * Method setSizeName.
     * @param sizeName String
     */
    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    /**
     * Method getSystemNo.
     * @return String
     */
    public String getSystemNo() {
        return systemNo;
    }

    /**
     * Method setSystemNo.
     * @param systemNo String
     */
    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
    }

    /**
     * Method getSsa.
     * @return String
     */
    public String getSsa() {
        return ssa;
    }

    /**
     * Method setSsa.
     * @param ssa String
     */
    public void setSsa(String ssa) {
        this.ssa = ssa;
    }

    /**
     * Method getCompanyId.
     * @return BigDecimal
     */
    public BigDecimal getCompanyId() {
        return companyId;
    }

    /**
     * Method setCompanyId.
     * @param companyId BigDecimal
     */
    public void setCompanyId(BigDecimal companyId) {
        this.companyId = companyId;
    }

    /**
     * Method getSystemTypeId.
     * @return BigDecimal
     */
    public BigDecimal getSystemTypeId() {
        return systemTypeId;
    }

    /**
     * Method setSystemTypeId.
     * @param systemTypeId BigDecimal
     */
    public void setSystemTypeId(BigDecimal systemTypeId) {
        this.systemTypeId = systemTypeId;
    }

    /**
     * Method getTerritoryId.
     * @return BigDecimal
     */
    public BigDecimal getTerritoryId() {
        return territoryId;
    }

    /**
     * Method setTerritoryId.
     * @param territoryId BigDecimal
     */
    public void setTerritoryId(BigDecimal territoryId) {
        this.territoryId = territoryId;
    }

    /**
     * Method getLobId.
     * @return BigDecimal
     */
    public BigDecimal getLobId() {
        return lobId;
    }

    /**
     * Method setLobId.
     * @param lobId BigDecimal
     */
    public void setLobId(BigDecimal lobId) {
        this.lobId = lobId;
    }

    /**
     * Method getSoftwareTypeId.
     * @return BigDecimal
     */
    public BigDecimal getSoftwareTypeId() {
        return softwareTypeId;
    }

    /**
     * Method setSoftwareTypeId.
     * @param softwareTypeId BigDecimal
     */
    public void setSoftwareTypeId(BigDecimal softwareTypeId) {
        this.softwareTypeId = softwareTypeId;
    }

    /**
     * Method getStatusId.
     * @return BigDecimal
     */
    public BigDecimal getStatusId() {
        return statusId;
    }

    /**
     * Method setStatusId.
     * @param statusId BigDecimal
     */
    public void setStatusId(BigDecimal statusId) {
        this.statusId = statusId;
    }

    /**
     * Method getVerifiedId.
     * @return BigDecimal
     */
    public BigDecimal getVerifiedId() {
        return verifiedId;
    }

    /**
     * Method setVerifiedId.
     * @param verifiedId BigDecimal
     */
    public void setVerifiedId(BigDecimal verifiedId) {
        this.verifiedId = verifiedId;
    }

    /**
     * Method getCreationDate.
     * @return Timestamp
     */
    public Timestamp getCreationDate() {
        return creationDate;
    }

    /**
     * Method setCreationDate.
     * @param creationDate Timestamp
     */
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Method getChangeDate.
     * @return Timestamp
     */
    public Timestamp getChangeDate() {
        return changeDate;
    }

    /**
     * Method setChangeDate.
     * @param changeDate Timestamp
     */
    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    /**
     * Method getCreationUserId.
     * @return BigDecimal
     */
    public BigDecimal getCreationUserId() {
        return creationUserId;
    }

    /**
     * Method setCreationUserId.
     * @param creationUserId BigDecimal
     */
    public void setCreationUserId(BigDecimal creationUserId) {
        this.creationUserId = creationUserId;
    }

    /**
     * Method getChangeUserId.
     * @return BigDecimal
     */
    public BigDecimal getChangeUserId() {
        return changeUserId;
    }

    /**
     * Method setChangeUserId.
     * @param changeUserId BigDecimal
     */
    public void setChangeUserId(BigDecimal changeUserId) {
        this.changeUserId = changeUserId;
    }

    /**
     * Method getCompanyName.
     * @return String
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Method setCompanyName.
     * @param companyName String
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Method getSystemTypeName.
     * @return String
     */
    public String getSystemTypeName() {
        return systemTypeName;
    }

    /**
     * Method setSystemTypeName.
     * @param systemTypeName String
     */
    public void setSystemTypeName(String systemTypeName) {
        this.systemTypeName = systemTypeName;
    }

    /**
     * Method getTerritoryName.
     * @return String
     */
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * Method setTerritoryName.
     * @param territoryName String
     */
    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    /**
     * Method getLobName.
     * @return String
     */
    public String getLobName() {
        return lobName;
    }

    /**
     * Method setLobName.
     * @param lobName String
     */
    public void setLobName(String lobName) {
        this.lobName = lobName;
    }

    /**
     * Method getSoftwareTypeName.
     * @return String
     */
    public String getSoftwareTypeName() {
        return softwareTypeName;
    }

    /**
     * Method setSoftwareTypeName.
     * @param softwareTypeName String
     */
    public void setSoftwareTypeName(String softwareTypeName) {
        this.softwareTypeName = softwareTypeName;
    }

    /**
     * Method getStatusName.
     * @return String
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * Method setStatusName.
     * @param statusName String
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * Method getVerifiedName.
     * @return String
     */
    public String getVerifiedName() {
        return verifiedName;
    }

    /**
     * Method setVerifiedName.
     * @param verifiedName String
     */
    public void setVerifiedName(String verifiedName) {
        this.verifiedName = verifiedName;
    }

    /**
     * Method getStateName.
     * @return String
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * Method setStateName.
     * @param stateName String
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * Method getCountryName.
     * @return String
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Method setCountryName.
     * @param countryName String
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Method getCreationUserName.
     * @return String
     */
    public String getCreationUserName() {
        return creationUserName;
    }

    /**
     * Method setCreationUserName.
     * @param creationUserName String
     */
    public void setCreationUserName(String creationUserName) {
        this.creationUserName = creationUserName;
    }

    /**
     * Method getChangeUserName.
     * @return String
     */
    public String getChangeUserName() {
        return changeUserName;
    }

    /**
     * Method setChangeUserName.
     * @param changeUserName String
     */
    public void setChangeUserName(String changeUserName) {
        this.changeUserName = changeUserName;
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
     * Method getSic.
     * @return String
     */
    public String getSic() {
        return sic;
    }

    /**
     * Method setSic.
     * @param sic String
     */
    public void setSic(String sic) {
        this.sic = sic;
    }

    /**
     * Method getHardwareMaintenance.
     * @return String
     */
    public String getHardwareMaintenance() {
        return hardwareMaintenance;
    }

    /**
     * Method setHardwareMaintenance.
     * @param hardwareMaintenance String
     */
    public void setHardwareMaintenance(String hardwareMaintenance) {
        this.hardwareMaintenance = hardwareMaintenance;
    }

    /**
     * Method getCountNotes.
     * @return Integer
     */
    public Integer getCountNotes() {
        return countNotes;
    }

    /**
     * Method setCountNotes.
     * @param countNotes Integer
     */
    public void setCountNotes(Integer countNotes) {
        this.countNotes = countNotes;
    }

    /**
     * Method getSearchNextSalesActionDate.
     * @return Date
     */
    public Date getSearchNextSalesActionDate() {
        return searchNextSalesActionDate;
    }

    /**
     * Method setSearchNextSalesActionDate.
     * @param searchNextSalesActionDate Date
     */
    public void setSearchNextSalesActionDate(Date searchNextSalesActionDate) {
        this.searchNextSalesActionDate = searchNextSalesActionDate;
    }

    /**
     * Method getTerritoryOwnerUserName.
     * @return String
     */
    public String getTerritoryOwnerUserName() {
        return territoryOwnerUserName;
    }

    /**
     * Method setTerritoryOwnerUserName.
     * @param territoryOwnerUserName String
     */
    public void setTerritoryOwnerUserName(String territoryOwnerUserName) {
        this.territoryOwnerUserName = territoryOwnerUserName;
    }

    /**
     * Method getTerritoryOwnerEmail.
     * @return String
     */
    public String getTerritoryOwnerEmail() {
        return territoryOwnerEmail;
    }

    /**
     * Method setTerritoryOwnerEmail.
     * @param territoryOwnerEmail String
     */
    public void setTerritoryOwnerEmail(String territoryOwnerEmail) {
        this.territoryOwnerEmail = territoryOwnerEmail;
    }

    /**
     * Method getTerritoryOwnerActive.
     * @return BigDecimal
     */
    public BigDecimal getTerritoryOwnerActive() {
        return territoryOwnerActive;
    }

    /**
     * Method setTerritoryOwnerActive.
     * @param territoryOwnerActive BigDecimal
     */
    public void setTerritoryOwnerActive(BigDecimal territoryOwnerActive) {
        this.territoryOwnerActive = territoryOwnerActive;
    }

    /**
     * Method getTerritorySalesManagerUserName.
     * @return String
     */
    public String getTerritorySalesManagerUserName() {
        return territorySalesManagerUserName;
    }

    /**
     * Method setTerritorySalesManagerUserName.
     * @param territorySalesManagerUserName String
     */
    public void setTerritorySalesManagerUserName(String territorySalesManagerUserName) {
        this.territorySalesManagerUserName = territorySalesManagerUserName;
    }

    /**
     * Method getTerritorySalesManagerEmail.
     * @return String
     */
    public String getTerritorySalesManagerEmail() {
        return territorySalesManagerEmail;
    }

    /**
     * Method setTerritorySalesManagerEmail.
     * @param territorySalesManagerEmail String
     */
    public void setTerritorySalesManagerEmail(String territorySalesManagerEmail) {
        this.territorySalesManagerEmail = territorySalesManagerEmail;
    }

    /**
     * Method getTerritorySalesManagerActive.
     * @return BigDecimal
     */
    public BigDecimal getTerritorySalesManagerActive() {
        return territorySalesManagerActive;
    }

    /**
     * Method setTerritorySalesManagerActive.
     * @param territorySalesManagerActive BigDecimal
     */
    public void setTerritorySalesManagerActive(BigDecimal territorySalesManagerActive) {
        this.territorySalesManagerActive = territorySalesManagerActive;
    }

    /**
     * Method getTerritoryServiceManagerUserName.
     * @return String
     */
    public String getTerritoryServiceManagerUserName() {
        return territoryServiceManagerUserName;
    }

    /**
     * Method setTerritoryServiceManagerUserName.
     * @param territoryServiceManagerUserName String
     */
    public void setTerritoryServiceManagerUserName(String territoryServiceManagerUserName) {
        this.territoryServiceManagerUserName = territoryServiceManagerUserName;
    }

    /**
     * Method getTerritoryServiceManagerEmail.
     * @return String
     */
    public String getTerritoryServiceManagerEmail() {
        return territoryServiceManagerEmail;
    }

    /**
     * Method setTerritoryServiceManagerEmail.
     * @param territoryServiceManagerEmail String
     */
    public void setTerritoryServiceManagerEmail(String territoryServiceManagerEmail) {
        this.territoryServiceManagerEmail = territoryServiceManagerEmail;
    }

    /**
     * Method getTerritoryServiceManagerActive.
     * @return BigDecimal
     */
    public BigDecimal getTerritoryServiceManagerActive() {
        return territoryServiceManagerActive;
    }

    /**
     * Method setTerritoryServiceManagerActive.
     * @param territoryServiceManagerActive BigDecimal
     */
    public void setTerritoryServiceManagerActive(BigDecimal territoryServiceManagerActive) {
        this.territoryServiceManagerActive = territoryServiceManagerActive;
    }

    /**
     * Method getTerritoryOwnerUserId.
     * @return BigDecimal
     */
    public BigDecimal getTerritoryOwnerUserId() {
        return territoryOwnerUserId;
    }

    /**
     * Method setTerritoryOwnerUserId.
     * @param territoryOwnerUserId BigDecimal
     */
    public void setTerritoryOwnerUserId(BigDecimal territoryOwnerUserId) {
        this.territoryOwnerUserId = territoryOwnerUserId;
    }

    /**
     * Method getTerritorySalesManagerUserId.
     * @return BigDecimal
     */
    public BigDecimal getTerritorySalesManagerUserId() {
        return territorySalesManagerUserId;
    }

    /**
     * Method setTerritorySalesManagerUserId.
     * @param territorySalesManagerUserId BigDecimal
     */
    public void setTerritorySalesManagerUserId(BigDecimal territorySalesManagerUserId) {
        this.territorySalesManagerUserId = territorySalesManagerUserId;
    }

    /**
     * Method getTerritoryServiceManagerUserId.
     * @return BigDecimal
     */
    public BigDecimal getTerritoryServiceManagerUserId() {
        return territoryServiceManagerUserId;
    }

    /**
     * Method setTerritoryServiceManagerUserId.
     * @param territoryServiceManagerUserId BigDecimal
     */
    public void setTerritoryServiceManagerUserId(BigDecimal territoryServiceManagerUserId) {
        this.territoryServiceManagerUserId = territoryServiceManagerUserId;
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
     * Method getOrderByFieldName.
     * @return String
     */
    public String getOrderByFieldName() {
        return orderByFieldName;
    }

    /**
     * Method setOrderByFieldName.
     * @param orderByFieldName String
     */
    public void setOrderByFieldName(String orderByFieldName) {
        this.orderByFieldName = orderByFieldName;
    }

    /**
     * Method getIdsVector.
     * @return Vector
     */
    public Vector getIdsVector() {
        return idsVector;
    }

    /**
     * Method setIdsVector.
     * @param idsVector Vector
     */
    public void setIdsVector(Vector idsVector) {
        this.idsVector = idsVector;
    }

    /**
     * Method addIdToIdsVector.
     * @param id BigDecimal
     */
    public void addIdToIdsVector(BigDecimal id) {
        if (this.idsVector == null) {
            this.idsVector = new Vector();
        }
        this.idsVector.add(id);
    }

    /**
     * Method getCountryAndStateSortValue.
     * @return String
     */
    public String getCountryAndStateSortValue() {
        return Utilities.nullToBlank(this.countryName) + ":" + Utilities.nullToBlank(this.stateName);
    }


    /**
     * Method toString.
     * @return String
     */
    public String toString() {
        return "ProspectDetails{" +
               "prospectId=" + prospectId +
               ", customerCompany='" + customerCompany + "'" +
               ", address='" + address + "'" +
               ", address2='" + address2 + "'" +
               ", city='" + city + "'" +
               ", county='" + county + "'" +
               ", zip='" + zip + "'" +
               ", stateId=" + stateId +
               ", countryId=" + countryId +
               ", contactName='" + contactName + "'" +
               ", contactTitle='" + contactTitle + "'" +
               ", phone='" + phone + "'" +
               ", phoneExt='" + phoneExt + "'" +
               ", cellPhone='" + cellPhone + "'" +
               ", email='" + email + "'" +
               ", fax='" + fax + "'" +
               ", website='" + website + "'" +
               ", systemNo='" + systemNo + "'" +
               ", ssa='" + ssa + "'" +
               ", sic='" + sic + "'" +
               ", hardwareMaintenance='" + hardwareMaintenance + "'" +
               ", companyId=" + companyId +
               ", systemTypeId=" + systemTypeId +
               ", territoryId=" + territoryId +
               ", lobId=" + lobId +
               ", softwareTypeId=" + softwareTypeId +
               ", statusId=" + statusId +
               ", verifiedId=" + verifiedId +
               ", sizeId='" + sizeId + "'" +
               ", creationDate=" + creationDate +
               ", changeDate=" + changeDate +
               ", creationUserId=" + creationUserId +
               ", changeUserId=" + changeUserId +
               ", creationUserName='" + creationUserName + "'" +
               ", changeUserName='" + changeUserName + "'" +
               ", territoryChangeDate='" + territoryChangeDate + "'" +
               ", territoryIdList='" + territoryIdList + "'" +
               ", statusIdList='" + statusIdList + "'" +
               ", currentSalesActionId='" + currentSalesActionId + "'" +
               ", currentSalesActionDate='" + currentSalesActionDate + "'" +
               ", currentSalesActionNote='" + currentSalesActionNote + "'" +
               ", currentSalesActionPriority='" + currentSalesActionPriority + "'" +
               "}";
    }

	public BigDecimal getSearchNextSalesActionId() {
		return searchNextSalesActionId;
	}

	public void setSearchNextSalesActionId(BigDecimal searchNextSalesActionId) {
		this.searchNextSalesActionId = searchNextSalesActionId;
	}
	
    public Timestamp getTerritoryChangeDate() {
		return territoryChangeDate;
	}

	public void setTerritoryChangeDate(Timestamp territoryChangeDate) {
		this.territoryChangeDate = territoryChangeDate;
	}

	/**
	 * @return the externalId
	 */
	public String getExternalId() {
		return externalId;
	}

	/**
	 * @param externalId the externalId to set
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	

	public List getTerritoryIdList() {
		return territoryIdList;
	}

	public void setTerritoryIdList(List territoryIdList) {
		this.territoryIdList = territoryIdList;
	}

	public List getStatusIdList() {
		return statusIdList;
	}

	public void setStatusIdList(List statusIdList) {
		this.statusIdList = statusIdList;
	}

	public BigDecimal getSearchLimit() {
		return searchLimit;
	}

	public void setSearchLimit(BigDecimal searchLimit) {
		this.searchLimit = searchLimit;
	}

	public Timestamp getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Timestamp activityDate) {
		this.activityDate = activityDate;
	}

	


	public Timestamp getCurrentSalesActionDate() {
		return currentSalesActionDate;
	}

	public void setCurrentSalesActionDate(Timestamp currentSalesActionDate) {
		this.currentSalesActionDate = currentSalesActionDate;
	}

	public BigDecimal getCurrentSalesActionId() {
		return currentSalesActionId;
	}

	public void setCurrentSalesActionId(BigDecimal currentSalesActionId) {
		this.currentSalesActionId = currentSalesActionId;
	}



	public BigDecimal getCurrentSalesActionPriority() {
		return currentSalesActionPriority;
	}

	public void setCurrentSalesActionPriority(BigDecimal currentSalesActionPriority) {
		this.currentSalesActionPriority = currentSalesActionPriority;
	}

	public String getCurrentSalesActionNote() {
		return currentSalesActionNote;
	}

	public void setCurrentSalesActionNote(String currentSalesActionNote) {
		this.currentSalesActionNote = currentSalesActionNote;
	}

	public BigDecimal getCurrentSalesActionStatus() {
		return currentSalesActionStatus;
	}

	public void setCurrentSalesActionStatus(BigDecimal currentSalesActionStatus) {
		this.currentSalesActionStatus = currentSalesActionStatus;
	}

	public List getLobIdList() {
		return lobIdList;
	}

	public void setLobIdList(List lobIdList) {
		this.lobIdList = lobIdList;
	}

	public BigDecimal getTerritoryAssignedOnProspectCreation() {
		return territoryAssignedOnProspectCreation;
	}

	public void setTerritoryAssignedOnProspectCreation(
			BigDecimal territoryAssignedOnProspectCreation) {
		this.territoryAssignedOnProspectCreation = territoryAssignedOnProspectCreation;
	}

	/**
	 * @return the searchChangeDate
	 */
	public Date getSearchChangeDate() {
		return searchChangeDate;
	}

	/**
	 * @param searchChangeDate the searchChangeDate to set
	 */
	public void setSearchChangeDate(Date searchChangeDate) {
		this.searchChangeDate = searchChangeDate;
	}

	/**
	 * @return the searchExactChangeDate
	 */
	public Date getSearchExactChangeDate() {
		return searchExactChangeDate;
	}

	/**
	 * @param searchExactChangeDate the searchExactChangeDate to set
	 */
	public void setSearchExactChangeDate(Date searchExactChangeDate) {
		this.searchExactChangeDate = searchExactChangeDate;
	}

	/**
	 * @return the emailMailToString
	 */
	public String getEmailMailToString() {
		return emailMailToString;
	}

	/**
	 * @param emailMailToString the emailMailToString to set
	 */
	public void setEmailMailToString(String emailMailToString) {
		this.emailMailToString = emailMailToString;
	}

	/**
	 * @return the emailDescription
	 */
	public String getEmailDescription() {
		return emailDescription;
	}

	/**
	 * @param emailDescription the emailDescription to set
	 */
	public void setEmailDescription(String emailDescription) {
		this.emailDescription = emailDescription;
	}

	/**
	 * @return the emailDraftToUse
	 */
	public BigDecimal getEmailDraftToUse() {
		return emailDraftToUse;
	}

	/**
	 * @param emailDraftToUse the emailDraftToUse to set
	 */
	public void setEmailDraftToUse(BigDecimal emailDraftToUse) {
		this.emailDraftToUse = emailDraftToUse;
	}

	/**
	 * @return the fromEmailToUse
	 */
	public String getFromEmailToUse() {
		return fromEmailToUse;
	}

	/**
	 * @param fromEmailToUse the fromEmailToUse to set
	 */
	public void setFromEmailToUse(String fromEmailToUse) {
		this.fromEmailToUse = fromEmailToUse;
	}

	/**
	 * @return the optOutDate
	 */
	public Timestamp getOptOutDate() {
		return optOutDate;
	}

	/**
	 * @param optOutDate the optOutDate to set
	 */
	public void setOptOutDate(Timestamp optOutDate) {
		this.optOutDate = optOutDate;
	}

	/**
	 * @return the notOptedOut
	 */
	public boolean isNotOptedOut() {
		return notOptedOut;
	}

	/**
	 * @param notOptedOut the notOptedOut to set
	 */
	public void setNotOptedOut(boolean notOptedOut) {
		this.notOptedOut = notOptedOut;
	}

	/**
	 * @return the excludeNIAndDNC
	 */
	public boolean isExcludeNIAndDNC() {
		return excludeNIAndDNC;
	}

	/**
	 * @param excludeNIAndDNC the excludeNIAndDNC to set
	 */
	public void setExcludeNIAndDNC(boolean excludeNIAndDNC) {
		this.excludeNIAndDNC = excludeNIAndDNC;
	}

	/**
	 * @return the salesmanName
	 */
	public String getSalesmanName() {
		return salesmanName;
	}

	/**
	 * @param salesmanName the salesmanName to set
	 */
	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	/**
	 * @return the lobIsNull
	 */
	public boolean isLobIsNull() {
		return lobIsNull;
	}

	/**
	 * @param lobIsNull the lobIsNull to set
	 */
	public void setLobIsNull(boolean lobIsNull) {
		this.lobIsNull = lobIsNull;
	}

	

	/**
	 * @return the territoryIsNull
	 */
	public boolean isTerritoryIsNull() {
		return territoryIsNull;
	}

	/**
	 * @param territoryIsNull the territoryIsNull to set
	 */
	public void setTerritoryIsNull(boolean territoryIsNull) {
		this.territoryIsNull = territoryIsNull;
	}

	/**
	 * @return the statusIsNull
	 */
	public boolean isStatusIsNull() {
		return statusIsNull;
	}

	/**
	 * @param statusIsNull the statusIsNull to set
	 */
	public void setStatusIsNull(boolean statusIsNull) {
		this.statusIsNull = statusIsNull;
	}

	/**
	 * @return the searchNextSalesActionDateEnd
	 */
	public Date getSearchNextSalesActionDateEnd() {
		return searchNextSalesActionDateEnd;
	}

	/**
	 * @param searchNextSalesActionDateEnd the searchNextSalesActionDateEnd to set
	 */
	public void setSearchNextSalesActionDateEnd(Date searchNextSalesActionDateEnd) {
		this.searchNextSalesActionDateEnd = searchNextSalesActionDateEnd;
	}

	/**
	 * @return the genericSearchDate
	 */
	public Date getGenericSearchDate() {
		return genericSearchDate;
	}

	/**
	 * @param genericSearchDate the genericSearchDate to set
	 */
	public void setGenericSearchDate(Date genericSearchDate) {
		this.genericSearchDate = genericSearchDate;
	}

	/**
	 * @return the genericSearchNumeric
	 */
	public BigDecimal getGenericSearchNumeric() {
		return genericSearchNumeric;
	}

	/**
	 * @param genericSearchNumeric the genericSearchNumeric to set
	 */
	public void setGenericSearchNumeric(BigDecimal genericSearchNumeric) {
		this.genericSearchNumeric = genericSearchNumeric;
	}

	/**
	 * @return the genericSearchText
	 */
	public String getGenericSearchText() {
		return genericSearchText;
	}

	/**
	 * @param genericSearchText the genericSearchText to set
	 */
	public void setGenericSearchText(String genericSearchText) {
		this.genericSearchText = genericSearchText;
	}

	public BigDecimal getOpenOrdersNumber() {
		return openOrdersNumber;
	}

	public void setOpenOrdersNumber(BigDecimal openOrdersNumber) {
		this.openOrdersNumber = openOrdersNumber;
	}

	public BigDecimal getOpenOrdersValue() {
		return openOrdersValue;
	}

	public void setOpenOrdersValue(BigDecimal openOrdersValue) {
		this.openOrdersValue = openOrdersValue;
	}

	public BigDecimal getOpenQuotesNumber() {
		return openQuotesNumber;
	}

	public void setOpenQuotesNumber(BigDecimal openQuotesNumber) {
		this.openQuotesNumber = openQuotesNumber;
	}

	public BigDecimal getOpenQuotesValue() {
		return openQuotesValue;
	}

	public void setOpenQuotesValue(BigDecimal openQuotesValue) {
		this.openQuotesValue = openQuotesValue;
	}

	public BigDecimal getOpenAccountsReceivable() {
		return openAccountsReceivable;
	}

	public void setOpenAccountsReceivable(BigDecimal openAccountsReceivable) {
		this.openAccountsReceivable = openAccountsReceivable;
	}

	public BigDecimal getInvoicesNumberLtd() {
		return invoicesNumberLtd;
	}

	public void setInvoicesNumberLtd(BigDecimal invoicesNumberLtd) {
		this.invoicesNumberLtd = invoicesNumberLtd;
	}

	public BigDecimal getInvoicesValueLtd() {
		return invoicesValueLtd;
	}

	public void setInvoicesValueLtd(BigDecimal invoicesValueLtd) {
		this.invoicesValueLtd = invoicesValueLtd;
	}

	public BigDecimal getInvoicesNumberYtd() {
		return invoicesNumberYtd;
	}

	public void setInvoicesNumberYtd(BigDecimal invoicesNumberYtd) {
		this.invoicesNumberYtd = invoicesNumberYtd;
	}

	public BigDecimal getInvoicesValueYtd() {
		return invoicesValueYtd;
	}

	public void setInvoicesValueYtd(BigDecimal invoicesValueYtd) {
		this.invoicesValueYtd = invoicesValueYtd;
	}

	public BigDecimal getForecast() {
		return forecast;
	}

	public void setForecast(BigDecimal forecast) {
		this.forecast = forecast;
	}

	public BigDecimal getOrderportalUserId() {
		return orderportalUserId;
	}

	public void setOrderportalUserId(BigDecimal orderportalUserId) {
		this.orderportalUserId = orderportalUserId;
	}

	public BigDecimal getEbsUserId() {
		return ebsUserId;
	}

	public void setEbsUserId(BigDecimal ebsUserId) {
		this.ebsUserId = ebsUserId;
	}

	//USHUD
//	/**
//	 * @return the ushudDate
//	 */
//	public Timestamp getUshudDate() {
//		return ushudDate;
//	}
//
//	/**
//	 * @param ushudDate the ushudDate to set
//	 */
//	public void setUshudDate(Timestamp ushudDate) {
//		this.ushudDate = ushudDate;
//	}
}
