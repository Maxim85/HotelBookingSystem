package com.booking.service;

import java.util.ResourceBundle;

/**
 * @author Maksym.
 */
public class ConfigurationManager {

    private static ConfigurationManager instance;
    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "config";
//    public static final String DATABASE_DRIVER_NAME = "DATABASE_DRIVER_NAME";
//    public static final String DATABASE_URL = "DATABASE_URL";
    public static final String ERROR_PAGE_PATH = "ERROR_PAGE_PATH";
    public static final String LOGIN_PAGE_PATH = "LOGIN_PAGE_PATH";
    public static final String MAIN_PAGE_PATH = "MAIN_PAGE_PATH";
    public static final String MY_BID_PATH = "MY_BID_PATH";
    public static final String CLOSE_PAGE_PATH = "CLOSE_PAGE_PATH";
    public static final String FORMING_PAGE_PATH = "FORMING_PAGE_PATH";
    public static final String EDIT_BILLS_PATH = "EDIT_BILLS_PATH";
    public static final String EDIT_BIDS_PATH = "EDIT_BIDS_PATH";
    public static final String EDIT_APARTMENTS_PATH = "EDIT_APARTMENTS_PATH";
    public static final String EDIT_USERS_PATH = "EDIT_USERS_PATH";

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
            instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }

}
