package com.eccentricyan.pohe.presentation.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View

import com.eccentricyan.pohe.Application
import com.eccentricyan.pohe.R
import com.eccentricyan.pohe.di.component.ActivityComponent
import com.eccentricyan.pohe.di.module.ActivityModule
import com.eccentricyan.pohe.infra.api.RestfulApi
import com.google.gson.Gson
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.twitter.sdk.android.core.models.User

import org.greenrobot.eventbus.EventBus

import javax.inject.Inject

import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm

/**
 * Created by shiyanhui on 2017/04/15.
 */

open class BaseActivity : RxAppCompatActivity() {
    var component: ActivityComponent? = null
        protected set
    protected var toolbar: Toolbar? = null
    @JvmField
    @Inject
    var eventBus: EventBus? = null
    @JvmField
    @Inject
    var gson: Gson? = null
    @JvmField
    @Inject
    var realm: Realm? = null
    @JvmField
    @Inject
    internal var compositeDisposable: CompositeDisposable? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.component = Application.getComponent(this)!!.activityComponent(ActivityModule(this))
        this.component!!.inject(this)

    }

    protected fun initToolBar() {
        val mCollapsingToolbarLayout = findViewById<View>(R.id.collapsing) as CollapsingToolbarLayout
        mCollapsingToolbarLayout.isTitleEnabled = false
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // add back arrow to toolbar
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        var result = true

        when (id) {
            android.R.id.home -> finish()
            else -> result = super.onOptionsItemSelected(item)
        }

        return result
    }
}
