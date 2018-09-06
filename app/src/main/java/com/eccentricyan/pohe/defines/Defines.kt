package com.eccentricyan.pohe.defines

import com.eccentricyan.pohe.Application

import java.io.File

import okhttp3.Cache

/**
 * Created by shiyanhui on 2017/04/15.
 */

object Defines {
    val SITE_URL = "http://menthas.com"
    val BR = System.getProperty("line.separator")
    val OS_NAME = "Android"
    val SINGLE_ACCOUNT_MODE = false
    val STRICT_LOGIN_MODE = true
    /** Remove the account at the same time as uninstalling the application.  */
    val REMOVE_ACCOUNT_WITH_UNINSTALLING_APPLICATION_MODE = false
    val REQUEST_CODE_CHOOSE_ACCOUNT = 10

    val DB_VERSION = 1

    val CACHE_DIR = File(Application.instance?.cacheDir, "http.cache")
    val CACHE = Cache(CACHE_DIR, (4 * 1024 * 1024).toLong())

    val CONSUMER_KEY = "KMOOPulYr3rKPGCZC6eVMnP23"
    val CONSUMER_SECRET = "sGT58DBZBoWCpqnKbDv6KXUN5wKVTxeCJvrPKR9Y62ksmUyml0"
    val PERMISSION_REQUEST_CAMERA = 300
    val REQUEST_CODE_ACTIVITY_CAMERA = 300


}
