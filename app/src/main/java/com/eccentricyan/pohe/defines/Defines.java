package com.eccentricyan.pohe.defines;

import com.eccentricyan.pohe.Application;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class Defines {
    public static final String SITE_URL = "http://menthas.com";
    public static final String BR = System.getProperty("line.separator");
    public static final String OS_NAME = "Android";
    public static final boolean SINGLE_ACCOUNT_MODE = false;
    public static final boolean STRICT_LOGIN_MODE = true;
    /** Remove the account at the same time as uninstalling the application. **/
    public static final boolean REMOVE_ACCOUNT_WITH_UNINSTALLING_APPLICATION_MODE = false;
    public static final int REQUEST_CODE_CHOOSE_ACCOUNT = 10;

    public static final int DB_VERSION = 1;

    public static final File CACHE_DIR = new File(Application.getInstance().getCacheDir(), "http.cache");
    public static final Cache CACHE = new Cache(CACHE_DIR, 4 * 1024 * 1024);

    public static final String CONSUMER_KEY = "KMOOPulYr3rKPGCZC6eVMnP23";
    public static final String CONSUMER_SECRET = "sGT58DBZBoWCpqnKbDv6KXUN5wKVTxeCJvrPKR9Y62ksmUyml0";
    public static final int PERMISSION_REQUEST_CAMERA = 300;
    public static final int REQUEST_CODE_ACTIVITY_CAMERA = 300;


}
