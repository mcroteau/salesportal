package com.randr.webdw.country;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 * @author randr
 * @version $Revision: 1.1 $
 */
public class CountryView extends AbstractView {
    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.country.CountryBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.country.CountryDetails";
    }

    /**
     * Method getCountriesArray.
     * @return String
     */
    public String getCountriesArray() {
        StringBuffer countriesArray = new StringBuffer();
        countriesArray.append("var arrCountries = new Array('Please select...'");
        for (int i = 0; i < this.theCollection.size(); i++) {
            CountryDetails countryDetails = (CountryDetails) this.theCollection.elementAt(i);
            countriesArray.append(",'" + countryDetails.getCountry().toString() + "'");
        }
        countriesArray.append(");");
        return countriesArray.toString();
    }

}
