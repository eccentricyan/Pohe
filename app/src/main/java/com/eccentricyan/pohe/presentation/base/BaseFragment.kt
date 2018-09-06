package com.eccentricyan.pohe.presentation.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment

import com.eccentricyan.pohe.di.component.ActivityComponent

import org.greenrobot.eventbus.EventBus

import javax.inject.Inject

import io.realm.Realm

/**
 * Created by shiyanhui on 2017/04/15.
 */

open class BaseFragment : Fragment() {
    protected var component: ActivityComponent? = null

    @JvmField
    @Inject
    var eventBus: EventBus? = null

    @JvmField
    @Inject
    var realm: Realm? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.component = (activity as BaseActivity).component
        this.component!!.inject(this)
    }

}