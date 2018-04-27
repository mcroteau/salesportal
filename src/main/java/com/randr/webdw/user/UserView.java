package com.randr.webdw.user;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 */
public class UserView extends AbstractView {
    public static final BigDecimal FILL_TYPE_LOGIN = new BigDecimal(1);
    public static final BigDecimal FILL_TYPE_SALESMAN = new BigDecimal(2);
    public static final BigDecimal FILL_TYPE_SALESMAN_REPORT = new BigDecimal(3);
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(4);
    public static final BigDecimal FILL_TYPE_ACCOUNT = new BigDecimal(5);
    public static final BigDecimal FILL_TYPE_QUERY_AUTHORIZED_USERS = new BigDecimal(6);

    /**
     * Constructor for UserView.
     */
    public UserView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.user.UserBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.user.UserDetails";
    }

}


