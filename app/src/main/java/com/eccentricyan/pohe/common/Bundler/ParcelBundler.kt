package com.eccentricyan.pohe.common.Bundler

import android.os.Bundle
import android.os.Parcelable

import org.parceler.Parcels

import icepick.Bundler

/**
 * Created by shiyanhui on 2017/04/15.
 */

class ParcelBundler : Bundler<Any> {

    override fun put(key: String, value: Any, bundle: Bundle) {
        bundle.putParcelable(key, Parcels.wrap(value))
    }

    override fun get(key: String, bundle: Bundle): List<*>? {
        return Parcels.unwrap<List<*>>(bundle.getParcelable<Parcelable>(key))
    }
}