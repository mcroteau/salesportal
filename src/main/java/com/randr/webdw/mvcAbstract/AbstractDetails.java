/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package com.randr.webdw.mvcAbstract;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import com.randr.webdw.util.Utilities;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 *
 * @version 1.0
 * @author randr
 */
public abstract class AbstractDetails implements Cloneable {
    protected Hashtable mappingHashtable = null;
    private String actionFlag = "UPDATE";
    private boolean isChanged = false;

    // public constants for action flag
    //    public static String ACTION_UPDATE = "UPDATE";
    //    public static String ACTION_INSERT = "INSERT";
    //    public static String ACTION_DELETE = "DELETE";

    /**
     * Constructor declaration
     *
     * @see
     */
    public AbstractDetails() {
        try {
            constructMapping();
        } catch (Exception e) {
        }
    }

    /**
     * Method declaration
     *
     * @throws Exception
     * @see
     */
    protected void constructMapping() throws Exception {
        Class cls = this.getClass();
        Hashtable previousMappingHashtable = MappingManager.getDetailClassMapping(cls);

        if (previousMappingHashtable == null) {
            if ((cls.getSuperclass() != null) && (!cls.getSuperclass().getName().endsWith("AbstractDetails"))) {
                Hashtable enheritedMappingHashtable = MappingManager.getDetailClassMapping(cls.getSuperclass());

                if (enheritedMappingHashtable == null) {
                    Class[] clsBase = new Class[0];
                    Object[] objBase = new Object[0];
                    AbstractDetails baseDetails = (AbstractDetails) Class.forName(cls.getSuperclass().getName()).getConstructor(clsBase).newInstance(objBase);

                    enheritedMappingHashtable = MappingManager.getDetailClassMapping(cls.getSuperclass());
                }

                this.mappingHashtable = new Hashtable(enheritedMappingHashtable);
            } else {
                this.mappingHashtable = new Hashtable();
            }

            loadMapping();
            MappingManager.addDetailClassMapping(this.getClass(), this.mappingHashtable);
        } else {
            this.mappingHashtable = previousMappingHashtable;
        }
    }

    /**
     * Method declaration
     *
     * @see
     */
    protected abstract void loadMapping();

    /**
     * Method declaration
     *
     *
     * @param dbColumn String
     * @param propertyName String
     * @see
     */

    /**
     * Method declaration
     *
     * @param dbColumn
     * @param propertyName
     * @see
     */
    protected void addMapping(String dbColumn, String propertyName) {
        mappingHashtable.put(dbColumn.toLowerCase().trim(), propertyName);
    }

    /**
     * Method declaration
     *
     * @param dbColumnsList
     * @return Vector
     * @throws Exception
     * @see
     */
    public Vector getPropertiesFromDBColumns(String dbColumnsList) throws Exception {
        if ((dbColumnsList == null) || (dbColumnsList.equals(""))) {
            return new Vector();
        }

        String propList = this.getPropertyNamesFromDBColumns(dbColumnsList);

        return getProperties(propList, true);
    }

    /**
     * Method declaration
     *
     * @param resultSet
     * @param dbColumnsList
     * @throws Exception
     * @see
     */
    public void setPropertiesFromDBColumns(ResultSet resultSet, String dbColumnsList) throws Exception {
        String propList = this.getPropertyNamesFromDBColumns(dbColumnsList);

        setProperties(resultSet, propList, true);
    }

    /**
     * Method declaration
     *
     * @return Object
     * @throws CloneNotSupportedException
     * @see
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Method declaration
     *
     * @param useInheritedFields
     * @return Vector
     * @throws Exception
     * @see
     */
    public Vector getProperties(boolean useInheritedFields) throws Exception {
        return getProperties(this.getClass(), useInheritedFields);
    }

    /**
     * Method declaration
     *
     * @param cls
     * @param useInheritedFields
     * @return Vector
     * @throws Exception
     * @see
     */
    private Vector getProperties(Class cls, boolean useInheritedFields) throws Exception {
        Vector vProperties = null;
        Field[] field = cls.getDeclaredFields();

        if ((useInheritedFields) && (cls.getSuperclass() != null) && (!cls.getSuperclass().getClass().getName().endsWith("AbstractDetails"))) {
            vProperties = getProperties(cls.getSuperclass(), true);
        } else {
            vProperties = new Vector(field.length);
        }

        for (int i = 0; i < field.length; i++) {
            if (!field[i].isAccessible()) {
                field[i].setAccessible(true);
            }

            vProperties.add(field[i].get(this));
        }

        return vProperties;
    }

    /**
     * Method declaration
     *
     * @param propList
     * @param useInheritedFields
     * @return Vector
     * @throws Exception
     * @see
     */
    public Vector getProperties(String propList, boolean useInheritedFields) throws Exception {
        if ((propList == null) || (propList.equals(""))) {
            return new Vector();
        }

        Vector vProperties = null;
        Class cls = null;
        boolean fieldFound = false;
        Vector vPropToken = Utilities.tokenize(propList, ",");

        vProperties = new Vector(vPropToken.size());

        Field field = null;

        for (int i = 0; i < vPropToken.size(); i++) {
            cls = this.getClass();

            if (!useInheritedFields) {
                field = cls.getDeclaredField(Utilities.trim((String) vPropToken.get(i)));
            } else {
                fieldFound = false;

                while ((!fieldFound) && (cls != null) && (!cls.getName().endsWith("AbstractDetails"))) {
                    try {
                        field = cls.getDeclaredField(Utilities.trim((String) vPropToken.get(i)));
                        fieldFound = true;
                    } catch (NoSuchFieldException n) {
                        cls = cls.getSuperclass();
                    } finally {
                    }
                }

                if (!fieldFound) {
                    throw new NoSuchFieldException("field: " + vPropToken.get(i));
                }
            }

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            vProperties.add(field.get(this));
        }

        return vProperties;
    }

    /**
     * Method declaration
     *
     * @param resultSet
     * @param useInheritedFields
     * @throws Exception
     * @see
     */
    public void setProperties(ResultSet resultSet, boolean useInheritedFields) throws Exception {
        setProperties(resultSet, this.getClass(), useInheritedFields);
    }

    /**
     * Method declaration
     *
     * @param resultSet
     * @param cls
     * @param useInheritedFields
     * @throws Exception
     * @see
     */
    private void setProperties(ResultSet resultSet, Class cls, boolean useInheritedFields) throws Exception {
        try {
            if ((useInheritedFields) && (cls.getSuperclass() != null) && (!cls.getSuperclass().getName().endsWith("AbstractDetails"))) {
                setProperties(resultSet, cls.getSuperclass(), true);
            }

            Field[] field = cls.getDeclaredFields();

            for (int i = 0; i < field.length; i++) {
                if (!field[i].isAccessible()) {
                    field[i].setAccessible(true);
                }

                field[i].set(this, resultSet.getObject(i + 1));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method declaration
     *
     * @param resultSet
     * @param propList
     * @param useInheritedFields
     * @throws Exception
     * @see
     */
    public void setProperties(ResultSet resultSet, String propList, boolean useInheritedFields) throws Exception {
        Class cls = null;
        Field field = null;
        boolean fieldFound = false;
        Vector vPropToken = Utilities.tokenize(propList, ",");

        for (int i = 0; i < vPropToken.size(); i++) {
            cls = this.getClass();

            if (!useInheritedFields) {
                field = cls.getDeclaredField(Utilities.trim((String) vPropToken.get(i)));
            } else {
                fieldFound = false;

                while ((!fieldFound) && (cls != null) && (!cls.getName().endsWith("AbstractDetails"))) {
                    try {
                        field = cls.getDeclaredField(Utilities.trim((String) vPropToken.get(i)));
                        fieldFound = true;
                    } catch (NoSuchFieldException n) {
                        cls = cls.getSuperclass();
                    } finally {
                    }
                }

                if (!fieldFound) {
                    throw new NoSuchFieldException("field: " + vPropToken.get(i));
                }
            }

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            try {
                field.set(this, resultSet.getObject(i + 1));
            } catch (IllegalArgumentException il) {
                throw new IllegalArgumentException("field: " + vPropToken.get(i));
            }
        }
    }

    /**
     * Method declaration
     *
     * @param dbColumnsList
     * @return String
     * @see
     */
    protected String getPropertyNamesFromDBColumns(String dbColumnsList) {
        Vector dbColumns = tokenizeDBCols(dbColumnsList);
        String propList = "";
        String property = null;
        String columnName = null;
        int dotOffset = 0;

        for (int i = 0; i < dbColumns.size(); i++) {
            columnName = (String) dbColumns.elementAt(i);
            columnName = columnName.trim().toLowerCase();
            property = (String) mappingHashtable.get(columnName);
            dotOffset = columnName.indexOf(".");

            if ((property == null) && (dotOffset > -1)) {
                columnName = columnName.substring(dotOffset + 1);
                property = (String) mappingHashtable.get(columnName);
            }

            if (property != null) {
                propList = propList + property + ",";
            } else {
                if (!columnName.endsWith(")")) {
                    //System.out.println("Null property for: " + columnName);
                }
            }
        }

        propList = propList.substring(0, propList.length() - 1);

        return propList;
    }

    /**
     * Method declaration
     *
     * @param colsList
     * @return Vector
     * @see
     */
    private static Vector tokenizeDBCols(String colsList) {
        Vector vToken = new Vector();
        int nOffset = 0;
        int nOffset1 = 0;
        int lastCommaOffset = 0;
        int countOpenParanthesys = 0;
        int countClosedParanthesys = 0;

        nOffset1 = colsList.indexOf(",", nOffset);

        while (nOffset1 > -1) {
            countOpenParanthesys = countOpenParanthesys + Utilities.countOccurences(colsList.substring(lastCommaOffset, nOffset1), "(");
            countClosedParanthesys = countClosedParanthesys + Utilities.countOccurences(colsList.substring(lastCommaOffset, nOffset1), ")");

            if (countOpenParanthesys == countClosedParanthesys) {
                vToken.add(colsList.substring(nOffset, nOffset1));

                countOpenParanthesys = 0;
                countClosedParanthesys = 0;
                nOffset = nOffset1 + 1;
            }

            lastCommaOffset = nOffset1;
            nOffset1 = colsList.indexOf(",", nOffset1 + 1);
        }

        vToken.add(colsList.substring(nOffset, colsList.length()));

        return vToken;
    }

    /**
     * Method getActionFlag.
     * @return String
     */
    public String getActionFlag() {
        return actionFlag;
    }

    /**
     * Method setActionFlag.
     * @param actionFlag String
     */
    public void setActionFlag(String actionFlag) {
        this.actionFlag = actionFlag;
    }

    /**
     * Method isChanged.
     * @return boolean
     */
    public boolean isChanged() {
        return isChanged;
    }

    /**
     * Method setChanged.
     * @param changed boolean
     */
    public void setChanged(boolean changed) {
        isChanged = changed;
    }
}


/*--- formatting done in "Sun Java Convention" style on 02-10-2003 ---*/

