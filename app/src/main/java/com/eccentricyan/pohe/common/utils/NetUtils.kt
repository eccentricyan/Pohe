package com.eccentricyan.pohe.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by shiyanhui on 2017/04/15.
 */

object NetUtils {
    fun checkNet(context: Context): Boolean {
        val mWifiConnected = isWifiConnected(context)

        val mMobileConnected = isMobileConnected(context)

        return if (mWifiConnected == false && mMobileConnected == false) {

            false
        } else true
    }

    private fun isWifiConnected(context: Context): Boolean {
        val manager = context.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return if (info != null && info.isConnected) {
            true
        } else false

    }

    private fun isMobileConnected(context: Context): Boolean {
        val manager = context.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return if (info != null && info.isConnected) {
            true
        } else false

    }

}
