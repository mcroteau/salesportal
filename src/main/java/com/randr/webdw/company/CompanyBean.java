package com.randr.webdw.company;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.mvcAbstract.AbstractDBBean;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.util.Utilities;


/**
 */
public class CompanyBean extends AbstractDBBean {
    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "company";
        this.tableAlias = "c";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param companyDetails CompanyDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, CompanyDetails companyDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(companyDetails);
        // establish the list of properties to be populated
        addColumnList("c.company_id, c.company, c.date_format_id, c.time_offset_id");
        if (companyDetails.getDateFormatId() != null) {
        	addColumnList("df.date_format");
        	addOtherTables(" inner join date_format df on c.date_format_id = df.date_format_id");
        }
        if (companyDetails.getTimeOffsetId() != null) {
        	addColumnList("tof.time_offset");
        	addOtherTables(" inner join time_offset tof on c.time_offset_id = tof.time_offset_id");
        }
        setOrderBy("c.company");

        return doSelect(companyDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param companyDetails CompanyDetails
     */
    private void establishSearchConditions(CompanyDetails companyDetails) {
        if (companyDetails.getCompanyId() != null) {
            addCondition("c.company_id= ?");
            addConditionParam(companyDetails.getCompanyId());
        }

        if (companyDetails.getCompany() != null) {
            if (isCaseSensitiveSearch()) {
                addCondition("c.company = ?");
                addConditionParam(companyDetails.getCompany());
            } else {
                addCondition("upper(c.company) = upper('" + Utilities.replaceString(companyDetails.getCompany(), "'", "''") + "')");
            }
        }
        
        if (companyDetails.getDateFormatId() != null) {
            addCondition("c.date_format_id= ?");
            addConditionParam(companyDetails.getDateFormatId());
        }
        
        if (companyDetails.getTimeOffsetId() != null) {
            addCondition("c.time_offset_id= ?");
            addConditionParam(companyDetails.getTimeOffsetId());
        }
        
    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param companyDetails CompanyDetails
     * @return CompanyDetails
     * @throws Exception
     */
    public CompanyDetails insert(BigDecimal fillType, CompanyDetails companyDetails) throws Exception {
        companyDetails.setCompanyId(getAvailableID("company_id", "company"));
        addColumnList("company_id, company, date_format_id, time_offset_id");

        return (CompanyDetails) doInsert(companyDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param companyDetails CompanyDetails
     * @return CompanyDetails
     * @throws Exception
     */
    public CompanyDetails update(BigDecimal fillType, CompanyDetails companyDetails) throws Exception {
        addCondition("company_id= ?");
        addConditionParam(companyDetails.getCompanyId());

        addColumnList("company");

        if (companyDetails.getDateFormatId() != null) {
        	addColumnList("date_format_id");
        }
        
        if (companyDetails.getTimeOffsetId() != null) {
        	addColumnList("time_offset_id");
        }
        return (CompanyDetails) doUpdate(companyDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param companyDetails CompanyDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, CompanyDetails companyDetails) throws Exception {
    	
    	if(companyDetails.getCompanyId() != null){
            addCondition("company_id= ?");
            addConditionParam(companyDetails.getCompanyId());

            doDelete(companyDetails);
            return true;
    	}else{
    		return false;
    	}

    }
}
