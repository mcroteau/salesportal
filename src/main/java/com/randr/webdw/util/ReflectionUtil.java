package com.randr.webdw.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 */
public class ReflectionUtil {
    /**
     * Method propertyGetterExist.
     * @param cls Class
     * @param propertyName String
     * @return boolean
     */
    public static boolean propertyGetterExist(Class cls, String propertyName) {
        if (propertyName.indexOf('.') > -1) {
            String parentPropertyName = propertyName.substring(0, propertyName.indexOf('.'));
            String parentPropertyGetterName = "get" + parentPropertyName.substring(0, 1).toUpperCase() + parentPropertyName.substring(1);

            try {
                Method parentPropertyMethod = cls.getDeclaredMethod(parentPropertyGetterName, new Class[0]);

                if (Modifier.isStatic(parentPropertyMethod.getModifiers())) {
                    return false;
                }

                return propertyGetterExist(parentPropertyMethod.getReturnType(),
                        propertyName.substring(propertyName.indexOf('.') + 1));
            } catch (NoSuchMethodException e) {
                return false;
            }

        } else {
            String getterName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

            try {
                Method getterMethod = cls.getDeclaredMethod(getterName, new Class[0]);

                if (Modifier.isStatic(getterMethod.getModifiers())) {
                    return false;
                }

                return true;
            } catch (NoSuchMethodException e) {
                return false;
            }
        }
    }

    /**
     * Method propertySetterExist.
     * @param cls Class
     * @param propertyName String
     * @param propertyType Class
     * @return boolean
     */
    public static boolean propertySetterExist(Class cls, String propertyName, Class propertyType) {
        if (propertyName.indexOf('.') > -1) {
            String parentPropertyName = propertyName.substring(0, propertyName.indexOf('.'));
            String parentPropertyGetterName = "get" + parentPropertyName.substring(0, 1).toUpperCase() + parentPropertyName.substring(1);

            try {
                Method parentPropertyMethod = cls.getDeclaredMethod(parentPropertyGetterName, new Class[0]);

                return propertySetterExist(parentPropertyMethod.getReturnType(),
                        propertyName.substring(propertyName.indexOf('.') + 1), propertyType);
            } catch (NoSuchMethodException e) {
                return false;
            }

        } else {
            String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

            try {
                cls.getDeclaredMethod(setterName, new Class[]{propertyType});

                return true;
            } catch (NoSuchMethodException e) {
                return false;
            }
        }
    }

    /**
     * Method getPropertyValue.
     * @param instance Object
     * @param propertyName String
     * @return Object
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object getPropertyValue(Object instance, String propertyName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (propertyName.indexOf('.') > -1) {
            String parentPropertyName = propertyName.substring(0, propertyName.indexOf('.'));
            Object parentPropertyValue = getPropertyValue(instance, parentPropertyName);

            return getPropertyValue(parentPropertyValue, propertyName.substring(propertyName.indexOf('.') + 1));
        } else {
            String getterName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            Method getterMethod = instance.getClass().getDeclaredMethod(getterName, new Class[0]);

            return getterMethod.invoke(instance, new Object[0]);
        }
    }

    /**
     * Method setPropertyValue.
     * @param instance Object
     * @param propertyName String
     * @param propertyValue Object
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void setPropertyValue(Object instance, String propertyName, Object propertyValue) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (propertyName.indexOf('.') > -1) {
            String parentPropertyName = propertyName.substring(0, propertyName.indexOf('.'));
            Object parentPropertyValue = getPropertyValue(instance, parentPropertyName);

            setPropertyValue(parentPropertyValue, propertyName.substring(propertyName.indexOf('.') + 1), propertyValue);
        } else {
            String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            if (propertyValue != null) {
                Method setterMethod = instance.getClass().getDeclaredMethod(setterName, new Class[]{propertyValue.getClass()});
                setterMethod.invoke(instance, new Object[]{propertyValue});
            }
        }
    }

    /**
     * Method getPropertyType.
     * @param instance Object
     * @param propertyName String
     * @return Class
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Class getPropertyType(Object instance, String propertyName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (propertyName.indexOf('.') > -1) {
            String parentPropertyName = propertyName.substring(0, propertyName.indexOf('.'));
            Object parentPropertyValue = getPropertyValue(instance, parentPropertyName);

            return getPropertyType(parentPropertyValue, propertyName.substring(propertyName.indexOf('.') + 1));
        } else {
            String getterName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            Method getterMethod = instance.getClass().getDeclaredMethod(getterName, new Class[0]);

            return getterMethod.getReturnType();
        }
    }

    /**
     * Method copyPropertyValues.
     * @param objectTo Object
     * @param objectFrom Object
     */
    public static void copyPropertyValues(Object objectTo, Object objectFrom) {
        copyPropertyValues(objectTo, objectFrom, false);
    }

    /**
     * Method copyPropertyValues.
     * @param objectTo Object
     * @param objectFrom Object
     * @param emptyStringToNull boolean
     */
    public static void copyPropertyValues(Object objectTo, Object objectFrom, boolean emptyStringToNull) {
        Method[] methods = objectTo.getClass().getMethods();

        for (int i = 0; i < methods.length; i++) {

            if (methods[i].getName().startsWith("set")) {

                try {
                    Method setterMethod = methods[i];
                    String getterName = "get" + methods[i].getName().substring(3);
                    Method getterMethod = objectFrom.getClass().getMethod(getterName, new Class[0]);
                    Object propertyValue = getterMethod.invoke(objectFrom, new Object[0]);

                    if (propertyValue != null) {
                        Class getterReturnType = getterMethod.getReturnType();
                        Class setterParamType = (setterMethod.getParameterTypes())[0];

                        if (getterReturnType.equals(setterParamType)) {
                            if (setterParamType.equals(String.class)) {
                                if (!emptyStringToNull || ((String) propertyValue).length() > 0) {
                                    setterMethod.invoke(objectTo, new Object[]{propertyValue});
                                }
                            } else {
                                setterMethod.invoke(objectTo, new Object[]{propertyValue});
                            }
                        } else {
                            //try type conversions
                            if (setterParamType.equals(String.class)) {
                                String strValue = propertyValue.toString();
                                if (!emptyStringToNull || strValue.length() > 0) {
                                    setterMethod.invoke(objectTo, new Object[]{strValue});
                                }
                            } else if (getterReturnType.equals(String.class)) {
                                Class[] constructorParamTypes = {String.class};
                                Object[] constructorParams = {propertyValue};
                                Constructor constructor = setterParamType.getConstructor(constructorParamTypes);
                                Object setterRequiredPropertyValue = constructor.newInstance(constructorParams);
                                setterMethod.invoke(objectTo, new Object[]{setterRequiredPropertyValue});
                            }

                        }
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method main.
     * @param args String[]
     */
    public static void main(String[] args) {
/*
        ProjectDetails projectDetails = new ProjectDetails();
        projectDetails.setProjectId(new BigDecimal(1));

        projectDetails.setName("");
        projectDetails.setModuleNextNumber(new BigDecimal(800));
        projectDetails.setEstimatedEndDate(DateUtilities.getCurrentSQLDate());

        ProjectForm projectForm = new ProjectForm();
        copyPropertyValues(projectForm, projectDetails);
        
*/

    }
}
