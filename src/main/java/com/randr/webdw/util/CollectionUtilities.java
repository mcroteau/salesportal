package com.randr.webdw.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;


/**
 */
public class CollectionUtilities {
    public static final int SORT_UP = 1;
    public static final int SORT_DOWN = 2;

    public static final int MAX = 1;
    public static final int MIN = 2;

    /**
     * Method sortVector.
     * @param vector Vector
     * @param getter String
     * @throws Exception
     */
    public static void sortVector(Vector vector, String getter) throws Exception {
        sortVector(vector, getter, SORT_UP);
    }

    /**
     * Method sortVector.
     * @param vector Vector
     * @param getter String
     * @param sortDirection int
     * @throws Exception
     */
    public static void sortVector(Vector vector, String getter, int sortDirection) throws Exception {
        sortVector(vector, getter, sortDirection, true);
    }

    /**
     * Method sortVector.
     * @param vector Vector
     * @param getter String
     * @param sortDirection int
     * @param caseSensitiveSort boolean
     * @throws Exception
     */
    public static void sortVector(Vector vector, String getter, int sortDirection, boolean caseSensitiveSort) throws Exception {
        if (vector.size() > 1) {
            sortVectorRecursive(vector, getter, 0, vector.size() - 1, sortDirection, caseSensitiveSort);
        }
    }

    /**
     * Method sortVectorRecursive.
     * @param vector Vector
     * @param getter String
     * @param low double
     * @param high double
     * @param sortDirection int
     * @param caseSensitiveSort boolean
     * @throws Exception
     */
    private static void sortVectorRecursive(Vector vector, String getter, double low, double high, int sortDirection, boolean caseSensitiveSort) throws Exception {
        Class[] cls = new Class[0];
        Object[] objs = new Object[0];
        Object middleVectorElem = vector.elementAt((int) ((low + high) / 2));
        Method method = null;
        Object middleObject = null;

        if (middleVectorElem != null) {
            method = middleVectorElem.getClass().getMethod(getter, cls);
            middleObject = method.invoke(middleVectorElem, objs);
        }

        Object tmpElement;
        Object compareVectorElem;
        Object compareObject;
        int i = (int) high;
        int j = (int) low;

        Class[] objectClass = new Class[1];
        objectClass[0] = Class.forName("java.lang.Object");
        Class[] stringObjectClass = new Class[1];
        stringObjectClass[0] = Class.forName("java.lang.String");

        Object[] object = new Object[1];
        object[0] = middleObject;
        Object compResult;
        boolean repeatDo;

        do {
            do {
                compareVectorElem = vector.elementAt(j);
                if (compareVectorElem != null) {
                    method = compareVectorElem.getClass().getMethod(getter, cls);
                    compareObject = method.invoke(compareVectorElem, objs);
                    if (compareObject != null) {
                        if (!(caseSensitiveSort) && compareObject instanceof String && object[0] instanceof String) {
                            method = compareObject.getClass().getMethod("compareToIgnoreCase", stringObjectClass);
                        } else {
                            method = compareObject.getClass().getMethod("compareTo", objectClass);
                        }
                        if (object[0] != null) {
                            compResult = method.invoke(compareObject, object);
                        } else {
                            compResult = new Integer(1);
                        }
                    } else {
                        if (object[0] != null) {
                            compResult = new Integer(-1);
                        } else {
                            compResult = new Integer(0);
                        }
                    }
                } else {
                    if (object[0] != null) {
                        compResult = new Integer(-1);
                    } else {
                        compResult = new Integer(0);
                    }
                }

                if (((sortDirection == SORT_UP) && (((Integer) compResult).intValue() < 0)) || ((sortDirection == SORT_DOWN) && (((Integer) compResult).intValue() > 0))) {
                    j++;
                    repeatDo = true;
                } else {
                    repeatDo = false;
                }
            } while (repeatDo);

            do {
                compareVectorElem = vector.elementAt(i);
                if (compareVectorElem != null) {
                    method = compareVectorElem.getClass().getMethod(getter, cls);
                    compareObject = method.invoke(compareVectorElem, objs);
                    if (compareObject != null) {
                        if (!(caseSensitiveSort) && compareObject instanceof String && object[0] instanceof String) {
                            method = compareObject.getClass().getMethod("compareToIgnoreCase", stringObjectClass);
                        } else {
                            method = compareObject.getClass().getMethod("compareTo", objectClass);
                        }

                        if (object[0] != null) {
                            compResult = method.invoke(compareObject, object);
                        } else {
                            compResult = new Integer(1);
                        }
                    } else {
                        if (object[0] != null) {
                            compResult = new Integer(-1);
                        } else {
                            compResult = new Integer(0);
                        }
                    }
                } else {
                    if (object[0] != null) {
                        compResult = new Integer(-1);
                    } else {
                        compResult = new Integer(0);
                    }
                }
                if (((sortDirection == SORT_UP) && (((Integer) compResult).intValue() > 0)) || ((sortDirection == SORT_DOWN) && (((Integer) compResult).intValue() < 0))) {
                    i--;
                    repeatDo = true;
                } else {
                    repeatDo = false;
                }
            } while (repeatDo);

            if (i >= j) {
                if (i != j) {
                    tmpElement = vector.elementAt(i);

                    vector.set(i, vector.elementAt(j));
                    vector.set(j, tmpElement);
                }

                i--;
                j++;
            }
        } while (j <= i);

        if (low < i) {
            sortVectorRecursive(vector, getter, low, (double) i, sortDirection, caseSensitiveSort);
        }

        if (j < high) {
            sortVectorRecursive(vector, getter, (double) j, high, sortDirection, caseSensitiveSort);
        }
    }

    /**
     * Method getDropDownOptions.
     * @param vector Vector
     * @param valueGetter String
     * @param textGetter String
     * @return String
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static String getDropDownOptions(Vector vector, String valueGetter, String textGetter) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return getDropDownOptions(vector, valueGetter, textGetter, "");
    }

    /**
     * Method getDropDownOptions.
     * @param vector Vector
     * @param valueGetter String
     * @param textGetter String
     * @param selectedValue Object
     * @return String
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static String getDropDownOptions(Vector vector, String valueGetter, String textGetter, Object selectedValue) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Vector selectedValues = new Vector(1);
        selectedValues.add(selectedValue);
        return getDropDownOptions(vector, valueGetter, textGetter, selectedValues);
    }

    /**
     * Method getDropDownOptions.
     * @param vector Vector
     * @param valueGetter String
     * @param textGetter String
     * @param selectedValues Vector
     * @return String
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static String getDropDownOptions(Vector vector, String valueGetter, String textGetter, Vector selectedValues) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Hashtable selectedValuesHashtable = new Hashtable(selectedValues.size());

        for (int i = 0; i < selectedValues.size(); i++) {
            if (selectedValues.elementAt(i) != null) {
                selectedValuesHashtable.put(selectedValues.elementAt(i).toString(), "");
            }

        }

        StringBuffer dropDownOptions = new StringBuffer("");
        String optionValue = null;
        String optionText = null;
        Class[] clsParms = new Class[0];
        Object[] objParms = new Object[0];
        Method getterMethod = null;

        Object vectorElement = null;

        for (int i = 0; i < vector.size(); i++) {
            vectorElement = vector.elementAt(i);
            getterMethod = vectorElement.getClass().getMethod(valueGetter, clsParms);
            optionValue = getterMethod.invoke(vectorElement, objParms).toString();
            getterMethod = vectorElement.getClass().getMethod(textGetter, clsParms);
            optionText = getterMethod.invoke(vectorElement, objParms).toString();

            dropDownOptions.append("<option class=\"default\" value=\"" + optionValue + "\"");

            if (selectedValuesHashtable.containsKey(optionValue)) {
                dropDownOptions.append(" selected");
            }

            dropDownOptions.append(">" + optionText);
        }

        return dropDownOptions.toString();
    }


    /**
     * Method getTokenSeparatedStrings.
     * @param vector Vector
     * @param getter String
     * @param tokenizer String
     * @param tokenDelimiter String
     * @return String
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static String getTokenSeparatedStrings(Vector vector, String getter, String tokenizer, String tokenDelimiter) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        StringBuffer tokenSeparatedStrings = new StringBuffer("");
        String stringValue = null;
        Class[] clsParms = new Class[0];
        Object[] objParms = new Object[0];
        Object property;
        Method getterMethod = null;

        Object vectorElement = null;

        for (int i = 0; i < vector.size(); i++) {
            vectorElement = vector.elementAt(i);
            getterMethod = vectorElement.getClass().getMethod(getter, clsParms);
            property = getterMethod.invoke(vectorElement, objParms);

            if (property != null) {
                stringValue = property.toString();
            } else {
                stringValue = "";
            }

            tokenSeparatedStrings.append(tokenDelimiter + stringValue + tokenDelimiter);

            if (i < vector.size() - 1) {
                tokenSeparatedStrings.append(tokenizer);
            }
        }

        return tokenSeparatedStrings.toString();
    }

    /**
     * Method main.
     * @param args String[]
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
       /* Vector v = new Vector();
        v.add("c");
        v.add(null);
        v.add("b");
        v.add(null);
        v.add("a");
        v.add("a");
        v.add("d");
        v.add("b");
        v.add("f");
        v.add("a");
        v.add("b");

        System.out.println(CollectionUtilities.getMax(v, "toString"));*/
    }

    /**
     * Method getMax.
     * @param vector Vector
     * @param getter String
     * @return Object
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object getMax(Vector vector, String getter) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return getLimit(vector, getter, MAX);
    }

    /**
     * Method getMin.
     * @param vector Vector
     * @param getter String
     * @return Object
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object getMin(Vector vector, String getter) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return getLimit(vector, getter, MIN);
    }

    /**
     * Method getLimit.
     * @param vector Vector
     * @param getter String
     * @param limitType int
     * @return Object
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static Object getLimit(Vector vector, String getter, int limitType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object limitObject = null;
        Object currentObject = null;
        Method getterMethod = null;
        Method compareMethod = null;
        Object vectorElement = null;

        Class[] clsParms = new Class[0];
        Object[] objParms = new Object[0];
        Class[] objectClass = new Class[1];
        Object[] limitObjectArray = new Object[1];
        int compareResult = 0;

        try {
            objectClass[0] = Class.forName("java.lang.Object");
        } catch (Exception e) {

        }

        if (vector.size() > 0) {
            vectorElement = vector.elementAt(0);
            getterMethod = vectorElement.getClass().getMethod(getter, clsParms);
            limitObject = getterMethod.invoke(vectorElement, objParms);

            if ((limitObject == null) && (limitType == MIN)) {
                return null;
            }
        }

        for (int i = 1; i < vector.size(); i++) {
            vectorElement = vector.elementAt(i);
            if (vectorElement != null) {
                getterMethod = vectorElement.getClass().getMethod(getter, clsParms);
                currentObject = getterMethod.invoke(vectorElement, objParms);
            } else {
                currentObject = null;
            }

            if (currentObject != null) {
                if (limitObject != null) {
                    compareMethod = currentObject.getClass().getMethod("compareTo", objectClass);
                    limitObjectArray[0] = limitObject;
                    compareResult = ((Integer) compareMethod.invoke(currentObject, limitObjectArray)).intValue();
                } else {
                    compareResult = 1;
                }
            } else {
                if (limitObject != null) {
                    compareResult = -1;
                } else {
                    compareResult = 0;
                }
            }

            if (((compareResult > 0) && (limitType == MAX)) || ((compareResult < 0) && (limitType == MIN))) {
                limitObject = currentObject;
            }
        }

        return limitObject;
    }

    /**
     * Method getHashtableFromVector.
     * @param vector Vector
     * @return Hashtable
     */
    public static Hashtable getHashtableFromVector(Vector vector) {
        Hashtable hashtable = new Hashtable(vector.size());

        for (int i = 0; i < vector.size(); i++) {
            hashtable.put(vector.elementAt(i), vector.elementAt(i));
        }

        return hashtable;
    }

    /**
     * Method getHashtableFromVector.
     * @param vector Vector
     * @param keyGetter String
     * @return Hashtable
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Hashtable getHashtableFromVector(Vector vector, String keyGetter) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Hashtable hashtable = new Hashtable(vector.size());

        Object vectorElement = null;
        Object keyObject = null;
        Method keyGetterMethod = null;
        Class[] clsParms = new Class[0];
        Object[] objParms = new Object[0];

        for (int i = 0; i < vector.size(); i++) {
            vectorElement = vector.elementAt(i);
            keyGetterMethod = vectorElement.getClass().getMethod(keyGetter, clsParms);
            keyObject = keyGetterMethod.invoke(vectorElement, objParms);

            hashtable.put(keyObject, vectorElement);
        }

        return hashtable;
    }

    /**
     * Method getHashMapFromVector.
     * @param vector Vector
     * @return HashMap
     */
    public static HashMap getHashMapFromVector(Vector vector) {
        HashMap hashMap = new HashMap(vector.size());

        for (int i = 0; i < vector.size(); i++) {
            hashMap.put(vector.elementAt(i), vector.elementAt(i));
        }

        return hashMap;
    }

    /**
     * Method getHashMapFromVector.
     * @param vector Vector
     * @param keyGetter String
     * @return HashMap
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static HashMap getHashMapFromVector(Vector vector, String keyGetter) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap hashMap = new HashMap(vector.size());

        Object vectorElement = null;
        Object keyObject = null;
        Method keyGetterMethod = null;
        Class[] clsParms = new Class[0];
        Object[] objParms = new Object[0];

        for (int i = 0; i < vector.size(); i++) {
            vectorElement = vector.elementAt(i);

            if (vectorElement != null) {
                keyGetterMethod = vectorElement.getClass().getMethod(keyGetter, clsParms);
                keyObject = keyGetterMethod.invoke(vectorElement, objParms);
            } else {
                keyObject = null;
            }

            hashMap.put(keyObject, vectorElement);
        }

        return hashMap;
    }

    /**
     * Method getVectorFromObjectArray.
     * @param arr Object[]
     * @return Vector
     */
    public static Vector getVectorFromObjectArray(Object[] arr){
            Vector resVector = new Vector();
            for (int i = 0; i < arr.length; i++) {
                resVector.add(arr[i]);
            }
            return resVector;
        }
    
    public static Vector setToVector(Set set){
    	Vector setVector = new Vector();
    	if(set != null){
	    	for(Iterator i = set.iterator(); i.hasNext();){
	    		setVector.add(i.next());
	    	}
    	}
    	return setVector;
    }
    
    /**
	 * @param string - this is the current value, e.g. itemNo
	 * @param list - list of strings, e.g. list of products
	 * @return string value
	 * method = getNextInList
	 * Send in value and a list of values, returns the next value from the list.
	 * returns null if the value sent in is the last value in the list.
	 * 
	 */
	public static String getNextInList(String string, List list) {
		String nextString = null;
		int i = list.indexOf(string);
		int size = list.size()-1;
		if (i==size){//this is the last item/value  in the list
			System.out.println("this is the last item/value in the list");
			return nextString;
		}else{
			nextString = list.get(i+1).toString();
			System.out.println("what is next item/value= " + nextString);
		}
		return nextString;
	}

	/**
	 * @param string - this is the current value, e.g. itemNo
	 * @param list - list of strings, e.g. list of products
	 * @return string value
	 * method = getPreviousInList
	 * Send in value and a list of values, returns the previous value from the list.
	 * returns null if the value sent in is the first value in the list.
	 * 
	 */
	public static String getPreviousInList(String string, List list) {
		String previousString = null;
		int i = list.indexOf(string);
		if (i== 0){//this is the first value in the list
			System.out.println("this is the first item/string value in the list");
			return previousString;
		}else{
			previousString = list.get(i-1).toString();
			System.out.println("what is previous item/string value= " + previousString);
		}
		return previousString;
	}
    
}
