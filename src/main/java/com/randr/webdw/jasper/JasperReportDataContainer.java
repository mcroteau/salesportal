package com.randr.webdw.jasper;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import com.randr.webdw.util.ReflectionUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;



/**
 * @author randr
 * 
 */
public class JasperReportDataContainer implements Serializable {
    static final long serialVersionUID = 1444155097057767693L;

    private JasperReport jasperReport;
    private HashMap reportParametersMap;
    private transient Vector beanList;
    private JRDataSource jrDataSource;

    /**
     * Constructor for JasperReportDataContainer.
     * @param beanList Vector
     * @param jasperReport JasperReport
     * @param reportParametersMap HashMap
     * @throws JRException
     */
    public JasperReportDataContainer(Vector beanList, JasperReport jasperReport, HashMap reportParametersMap) throws JRException {
        this.beanList = beanList;
        this.jasperReport = jasperReport;
        this.reportParametersMap = reportParametersMap;
        this.jrDataSource = new JasperBeanListDataSource();
    }

    /**
     * Method getJasperReport.
     * @return JasperReport
     */
    public JasperReport getJasperReport() {
        return jasperReport;
    }

    /**
     * Method getReportParametersMap.
     * @return HashMap
     */
    public HashMap getReportParametersMap() {
        return reportParametersMap;
    }

    /**
     * Method getJRDataSource.
     * @return JRDataSource
     */
    public JRDataSource getJRDataSource() {
        return jrDataSource;
    }

    /**
     * @author randr
     */
    private class JasperBeanListDataSource implements JRDataSource, Serializable {
		static final long serialVersionUID =  -6256981644448914998L;
        private HashMap fieldsHashMap;
        private int index = -1;
        private int collectionSize;

        /**
         * Constructor for JasperBeanListDataSource.
         * @throws JRException
         */
        public JasperBeanListDataSource() throws JRException {
            collectionSize = beanList.size();
            createFieldsHashMap();
        }

        /**
         * Method next.
         * @return boolean
         * @throws JRException
         * @see net.sf.jasperreports.engine.JRDataSource#next()
         */
        public boolean next() throws JRException {
            index++;
            return index < collectionSize;
        }

        /**
         * Method getFieldValue.
         * @param jrField JRField
         * @return Object
         * @throws JRException
         * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(JRField)
         */
        public Object getFieldValue(JRField jrField) throws JRException {
            return ((Vector) fieldsHashMap.get(jrField.getName())).elementAt(index);
        }

        /**
         * Method createFieldsHashMap.
         * @throws JRException
         */
        private void createFieldsHashMap() throws JRException {
            JRField[] jrFields = jasperReport.getFields();
            fieldsHashMap = new HashMap(jrFields.length);
            Vector fieldValues = null;
            Object bean = null;
            String mappedPropertyName = null;

            for (int i = 0; i < jrFields.length; i++) {
                fieldValues = new Vector(beanList.size());

                for (int j = 0; j < beanList.size(); j++) {
                    bean = beanList.elementAt(j);

                    try {
                        mappedPropertyName = jrFields[i].getName().replace('_', '.');
                        fieldValues.add(ReflectionUtil.getPropertyValue(bean, mappedPropertyName));
                    } catch (Exception e) {
                        throw new JRException("property " + mappedPropertyName + " not valid", e);
                    }
                }

                fieldsHashMap.put(jrFields[i].getName(), fieldValues);
            }
        }
    }
}
