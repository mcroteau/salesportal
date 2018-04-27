package com.randr.webdw.mvcAbstract;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

//import com.randr.orderportal.product.ProductDetails;
//import com.randr.orderportal.productGroup.ProductGroupDetails;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.util.CollectionUtilities;


/**
 */
public abstract class AbstractView extends AbstractConnection implements Cloneable {
    protected boolean hasMoreRows = false;
    protected AbstractDetails current;
    protected Vector theCollection;
    protected int updateCount;
    protected int start = 0;
    protected int startRow = 0;
    protected int maxRows = 0;
    protected String action = null;
    protected BigDecimal fillType = null;
    protected String beanClassName;
    protected String detailClassName;
    private String orderBy;
    private boolean caseSensitiveSearch = false;
    private String hashKeyGetter;
    private Hashtable primaryKeyCollectionHashtable;
    private String searchKeyGetter;
    private Hashtable searchKeyCollectionHashtable;

    /**
     * Constructor for AbstractView.
     */
    public AbstractView() {
        this.setBeanClassName();
        this.setDetailClassName();
    }

    /**
     * Constructor for AbstractView.
     * @param connection Connection
     */
    public AbstractView(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method setAction.
     * @param value String
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Method getAction.
     * @return String
     */
    public String getAction() {
        return this.action;
    }

    /**
     * Method setCurrent.
     * @param current AbstractDetails
     */
    public void setCurrent(AbstractDetails current) {
        this.current = current;
    }

    /**
     * Method getCurrent.
     * @return AbstractDetails
     */
    public AbstractDetails getCurrent() {
        return this.current;
    }

    /**
     * Method doAction.
     * @param connection Connection
     * @param action String
     * @param fillType BigDecimal
     * @param details AbstractDetails
     * @return boolean
     * @throws Exception
     */
    public boolean doAction(Connection connection, String action, BigDecimal fillType, AbstractDetails details) throws Exception {
        this.connection = connection;

        return doAction(action, fillType, details);
    }

    /**
     * Method doAction.
     * @param action String
     * @param fillType BigDecimal
     * @param details AbstractDetails
     * @return boolean
     * @throws Exception
     */
    public boolean doAction(String action, BigDecimal fillType, AbstractDetails details) throws Exception {
        this.action = action;
        this.fillType = fillType;
        this.current = details;

        return this.doAction();
    }

    /**
     * Method doAction.
     * @return boolean
     * @throws Exception
     */
    public boolean doAction() throws Exception {
        try {
            Class[] cls = new Class[0];
            Object[] obj = new Object[0];
            AbstractDBBean bean = (AbstractDBBean) Class.forName(this.beanClassName).getConstructor(cls).newInstance(obj);

            bean.setConnection(this.connection);

            Class[] clsParms = new Class[2];

            clsParms[0] = Class.forName("java.math.BigDecimal");
            clsParms[1] = Class.forName(this.detailClassName);

            Object[] objParms = new Object[2];

            objParms[0] = this.fillType;
            objParms[1] = this.current;

            if (action.equals("INSERT")) {
                this.current = (AbstractDetails) Class.forName(this.beanClassName).getDeclaredMethod("insert", clsParms).invoke(bean, objParms);

                return true;
            } else if (action.equals("DELETE")) {
                Boolean bDeleted = (Boolean) Class.forName(this.beanClassName).getDeclaredMethod("delete", clsParms).invoke(bean, objParms);

                this.updateCount = bean.getUpdateCount();

                return bDeleted.booleanValue();
            } else if (action.equals("UPDATE")) {
                this.current = (AbstractDetails) Class.forName(this.beanClassName).getDeclaredMethod("update", clsParms).invoke(bean, objParms);
                this.updateCount = bean.getUpdateCount();

                return true;
            }
        } catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                throw (Exception) ((InvocationTargetException) e).getTargetException();
            } else {
                throw e;
            }
        }
        return false;
    }

    /**
     * Method getUpdateCount.
     * @return int
     */
    public int getUpdateCount() {
        return this.updateCount;
    }

    /**
     * Method getElements.
     * @return Vector
     * @throws Exception
     */
    public Vector getElements() throws Exception {
        if (this.theCollection == null) {
            this.theCollection = new Vector();
        }

        return this.theCollection;
    }

    /**
     * Method fillWithElements.
     * @param connection Connection
     * @param fillType BigDecimal
     * @param details AbstractDetails
     * @throws Exception
     */
    public void fillWithElements(Connection connection, BigDecimal fillType, AbstractDetails details) throws Exception {
        this.connection = connection;
        this.fillWithElements(fillType, details);
    }

    /**
     * Method fillWithElements.
     * @param fillType BigDecimal
     * @param details AbstractDetails
     * @throws Exception
     */
    public void fillWithElements(BigDecimal fillType, AbstractDetails details) throws Exception {
        this.fillType = fillType;
        this.current = details;

        this.fillWithElements();
    }

    /**
     * Method fillWithElements.
     * @throws Exception
     */
    public void fillWithElements() throws Exception {
        try {
            Class[] cls = new Class[0];
            Object[] obj = new Object[0];
            AbstractDBBean bean = (AbstractDBBean) Class.forName(this.beanClassName).getConstructor(cls).newInstance(obj);

            bean.setConnection(this.connection);

            if (this.current == null) {
                this.current = (AbstractDetails) Class.forName(this.detailClassName).getConstructor(cls).newInstance(obj);
            }

            Class[] clsParms = new Class[2];

            clsParms[0] = Class.forName("java.math.BigDecimal");
            clsParms[1] = Class.forName(this.detailClassName);

            Object[] objParms = new Object[2];

            objParms[0] = this.fillType;
            objParms[1] = this.current;

            bean.setStartRow(this.startRow);
            bean.setMaxRows(this.maxRows);
            bean.setOrderBy(this.orderBy);
            bean.setCaseSensitiveSearch(this.caseSensitiveSearch);

            this.theCollection = (java.util.Vector) Class.forName(this.beanClassName).getDeclaredMethod("loadElements", clsParms).invoke(bean, objParms);
            this.hasMoreRows = bean.hasMoreRows();
        } catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                throw (Exception) ((InvocationTargetException) e).getTargetException();
            } else {
                throw e;
            }
        }
    }

    /**
     * Method setFillType.
     * @param fillType BigDecimal
     */
    public void setFillType(BigDecimal fillType) {
        this.fillType = fillType;
    }

    /**
     * Method hasMoreRows.
     * @return boolean
     */
    public boolean hasMoreRows() {
        return this.hasMoreRows;
    }

    /**
     * Method setStartRow.
     * @param startRow int
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * Method setMaxRows.
     * @param maxRows int
     */
    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    /**
     * Method setBeanClassName.
     */
    protected abstract void setBeanClassName();

    /**
     * Method setDetailClassName.
     */
    protected abstract void setDetailClassName();

    /**
     * Method setOrderBy.
     * @param orderBy String
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * Method getOrderBy.
     * @return String
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Method isCaseSensitiveSearch.
     * @return boolean
     */
    public boolean isCaseSensitiveSearch() {
        return caseSensitiveSearch;
    }

    /**
     * Method setCaseSensitiveSearch.
     * @param caseSensitiveSearch boolean
     */
    public void setCaseSensitiveSearch(boolean caseSensitiveSearch) {
        this.caseSensitiveSearch = caseSensitiveSearch;
    }

    /**
     * Method createUniqueKeyCollectionHashtable.
     * @param hashKeyGetter String
     * @throws Exception
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void createUniqueKeyCollectionHashtable(String hashKeyGetter) throws Exception, IllegalAccessException, InvocationTargetException {
        if (this.theCollection == null) {
            this.theCollection = new Vector();
        }

        this.hashKeyGetter = hashKeyGetter;

        this.primaryKeyCollectionHashtable = new Hashtable(this.theCollection.size());
        for (int i = 0; i < this.theCollection.size(); i++) {
            addToUniqueKeyCollectionHashtable((AbstractDetails) this.theCollection.elementAt(i));
        }

        if (this.searchKeyCollectionHashtable != null) {
            createSearchKeyCollectionHashtable(this.hashKeyGetter);
        }
    }
    
//    public Set createUniqueSearchSet(AbstractView abstractView){
//    	System.out.println("inside new method");
//		Set set = new HashSet();
//	    ProspectDetails prospectDetails;
//	    try {
//			for(int i = 0; i < abstractView.getElements().size();i++) {
//				prospectDetails = (ProspectDetails)abstractView.getElement(i);
//				set.add(prospectDetails.getProspectId());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return set;
//	}

    /**
     * Method createSearchKeyCollectionHashtable.
     * @param searchKeyGetter String
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws Exception
     */
    public void createSearchKeyCollectionHashtable(String searchKeyGetter) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, Exception {
        if (this.primaryKeyCollectionHashtable == null) {
            throw new Exception("primary key collection must be created");
        }

        this.searchKeyGetter = searchKeyGetter;
        this.searchKeyCollectionHashtable = new Hashtable();

        for (int i = 0; i < this.theCollection.size(); i++) {
            addToSearchKeyCollectionHashtable((AbstractDetails) this.theCollection.elementAt(i));
        }
    }

    /**
     * Method getElement.
     * @param index int
     * @return AbstractDetails
     */
    public AbstractDetails getElement(int index) {
        return (AbstractDetails) this.theCollection.elementAt(index);
    }

    /**
     * Method getElement.
     * @param primaryKey Object
     * @return AbstractDetails
     */
    public AbstractDetails getElement(Object primaryKey) {
        return (AbstractDetails) this.primaryKeyCollectionHashtable.get(primaryKey);
    }

    /**
     * Method getElementIndex.
     * @param primaryKey Object
     * @return int
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public int getElementIndex(Object primaryKey)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        if (primaryKey != null) {

            if ((AbstractDetails) this.primaryKeyCollectionHashtable.get(primaryKey) != null) {

                for (int i = 0; i < this.theCollection.size(); i++) {
                    AbstractDetails details = (AbstractDetails) this.theCollection.elementAt(i);
                    Object currentPrimaryKey = getDetailPropertyValue(details, this.hashKeyGetter);

                    if (currentPrimaryKey.equals(primaryKey)) {

                        return i;
                    }
                }
            }
        }

        return -1;
    }

    /**
     * Method getElements.
     * @param key Object
     * @return Vector
     */
    public Vector getElements(Object key) {
        Vector primaryKeysList = (Vector) this.searchKeyCollectionHashtable.get(key);

        if (primaryKeysList != null) {
            Vector keyElements = new Vector(primaryKeysList.size());
            Object primaryKey = null;

            for (int i = 0; i < primaryKeysList.size(); i++) {
                primaryKey = primaryKeysList.elementAt(i);
                keyElements.add(getElement(primaryKey));
            }

            return keyElements;
        } else {
            return new Vector();
        }
    }

    /**
     * Method addElement.
     * @param details AbstractDetails
     */
    public void addElement(AbstractDetails details) {
        addElement(details, "INSERT");
    }

    /**
     * Method addElement.
     * @param details AbstractDetails
     * @param actionFlag String
     */
    public void addElement(AbstractDetails details, String actionFlag) {
        if (this.theCollection == null) {
            this.theCollection = new Vector();
        }

        details.setActionFlag(actionFlag);
        this.theCollection.add(details);

        if (this.primaryKeyCollectionHashtable != null) {
            try {
                addToUniqueKeyCollectionHashtable(details);
            } catch (Exception e) {
            }
        }

        if (this.searchKeyCollectionHashtable != null) {
            try {
                addToSearchKeyCollectionHashtable(details);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Method changeElement.
     * @param index int
     * @param newDetails AbstractDetails
     */
    public void changeElement(int index, AbstractDetails newDetails) {
        newDetails.setChanged(true);

        if (this.primaryKeyCollectionHashtable != null) {
            try {
                AbstractDetails oldDetails = (AbstractDetails) this.theCollection.elementAt(index);
                Object primaryKey = getDetailPropertyValue(oldDetails, this.hashKeyGetter);
                //this.primaryKeyCollectionHashtable.put(key, newDetails);
                changeElement(primaryKey, newDetails, false);
            } catch (Exception e) {
            }
        }

        this.theCollection.setElementAt(newDetails, index);
    }

    /**
     * Method changeElement.
     * @param primaryKey Object
     * @param newDetails AbstractDetails
     */
    public void changeElement(Object primaryKey, AbstractDetails newDetails) {
        changeElement(primaryKey, newDetails, true);
    }

    /**
     * Method changeElement.
     * @param uniqueKey Object
     * @param newDetails AbstractDetails
     * @param updateCollection boolean
     */
    private void changeElement(Object uniqueKey, AbstractDetails newDetails, boolean updateCollection) {
        newDetails.setChanged(true);

        AbstractDetails oldDetails = (AbstractDetails) this.primaryKeyCollectionHashtable.get(uniqueKey);

        if (updateCollection) {
            int elementIndex = this.theCollection.indexOf(oldDetails);
            this.theCollection.setElementAt(newDetails, elementIndex);
        }

        this.primaryKeyCollectionHashtable.put(uniqueKey, newDetails);

        if (this.searchKeyCollectionHashtable != null) {
            try {
                if (!getDetailPropertyValue(oldDetails, this.searchKeyGetter).equals(getDetailPropertyValue(newDetails, this.searchKeyGetter))) {
                    removeFromSearchKeyCollection(getDetailPropertyValue(oldDetails, this.searchKeyGetter), getDetailPropertyValue(oldDetails, this.hashKeyGetter));
                    addToSearchKeyCollectionHashtable(newDetails);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * Method removeElement.
     * @param index int
     */
    public void removeElement(int index) {
        if (this.primaryKeyCollectionHashtable != null) {
            try {
                AbstractDetails oldDetails = (AbstractDetails) this.theCollection.elementAt(index);
                Object primaryKey = getDetailPropertyValue(oldDetails, this.hashKeyGetter);
                //this.primaryKeyCollectionHashtable.remove(primaryKey);
                removeElement(primaryKey, false);

            } catch (Exception e) {
            }
        }

        this.theCollection.removeElementAt(index);
    }

    /**
     * Method removeElement.
     * @param primaryKey Object
     */
    public void removeElement(Object primaryKey) {
        removeElement(primaryKey, true);
    }

    /**
     * Method removeElements.
     * @param searchKey Object
     */
    public void removeElements(Object searchKey) {

    }

    /**
     * Method removeElement.
     * @param uniqueKey Object
     * @param removeFromCollection boolean
     */
    private void removeElement(Object uniqueKey, boolean removeFromCollection) {
        AbstractDetails oldDetails = (AbstractDetails) this.primaryKeyCollectionHashtable.get(uniqueKey);

        if (removeFromCollection) {
            int elementIndex = this.theCollection.indexOf(oldDetails);
            if (elementIndex > -1) {
                this.theCollection.removeElementAt(elementIndex);
            }
        }

        this.primaryKeyCollectionHashtable.remove(uniqueKey);

        if (this.searchKeyCollectionHashtable != null) {
            try {
                removeFromSearchKeyCollection(getDetailPropertyValue(oldDetails, this.searchKeyGetter), getDetailPropertyValue(oldDetails, this.hashKeyGetter));
            } catch (Exception e) {
            }
        }
    }

    /**
     * Method removeFromSearchKeyCollection.
     * @param key Object
     * @param primaryKey Object
     */
    private void removeFromSearchKeyCollection(Object key, Object primaryKey) {
        Vector primaryKeyList = (Vector) this.searchKeyCollectionHashtable.get(key);
        if (primaryKeyList != null) {
            primaryKeyList.removeElement(primaryKey);
        }
        this.searchKeyCollectionHashtable.put(key, primaryKeyList);
    }

    /**
     * Method addToUniqueKeyCollectionHashtable.
     * @param details AbstractDetails
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void addToUniqueKeyCollectionHashtable(AbstractDetails details) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object primaryKey = getDetailPropertyValue(details, this.hashKeyGetter);
        this.primaryKeyCollectionHashtable.put(primaryKey, details);
    }

    /**
     * Method addToSearchKeyCollectionHashtable.
     * @param details AbstractDetails
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private void addToSearchKeyCollectionHashtable(AbstractDetails details) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object key = getDetailPropertyValue(details, this.searchKeyGetter);
        Vector primaryKeyList = (Vector) searchKeyCollectionHashtable.get(key);

        if (primaryKeyList == null) {
            primaryKeyList = new Vector();
        }

        primaryKeyList.add(getDetailPropertyValue(details, this.hashKeyGetter));
        this.searchKeyCollectionHashtable.put(key, primaryKeyList);
    }

    /**
     * Method getDetailPropertyValue.
     * @param details AbstractDetails
     * @param getter String
     * @return Object
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Object getDetailPropertyValue(AbstractDetails details, String getter) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class[] clsParms = new Class[0];
        Object[] objParms = new Object[0];
        Method getterMethod = details.getClass().getMethod(getter, clsParms);

        return getterMethod.invoke(details, objParms);
    }

    /**
     * Method sortElements.
     * @param getter String
     * @throws Exception
     */
    public void sortElements(String getter) throws Exception {
        CollectionUtilities.sortVector(this.theCollection, getter);
    }

    /**
     * Method sortElements.
     * @param getter String
     * @param sortDirection int
     * @throws Exception
     */
    public void sortElements(String getter, int sortDirection) throws Exception {
        CollectionUtilities.sortVector(this.theCollection, getter, sortDirection);
    }

    /**
     * Method sortElements.
     * @param getter String
     * @param sortDirection int
     * @param caseSensitiveSort boolean
     * @throws Exception
     */
    public void sortElements(String getter, int sortDirection, boolean caseSensitiveSort) throws Exception {
        CollectionUtilities.sortVector(this.theCollection, getter, sortDirection, caseSensitiveSort);
    }

    /**
     * Method addElements.
     * @param elements Vector
     */
    public void addElements(Vector elements) {
        for (int i = 0; i < elements.size(); i++) {
            addElement((AbstractDetails) elements.elementAt(i));
        }
    }

    /**
     * Method addCloneElements.
     * @param elements Vector
     * @throws CloneNotSupportedException
     */
    public void addCloneElements(Vector elements) throws CloneNotSupportedException {
        AbstractDetails details = null;
        for (int i = 0; i < elements.size(); i++) {
            details = (AbstractDetails) elements.elementAt(i);
            addCloneElement(details);
        }
    }

    /**
     * Method addCloneElement.
     * @param details AbstractDetails
     * @throws CloneNotSupportedException
     */
    private void addCloneElement(AbstractDetails details) throws CloneNotSupportedException {
        details = (AbstractDetails) details.clone();
        addElement(details, details.getActionFlag());
    }

    /**
     * Method clone.
     * @return Object
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    /**
     * Method toString.
     * @return String
     */
    public String toString() {
        return "AbstractView{" +
                " theCollection=" + theCollection +
                "}";
    }
}

