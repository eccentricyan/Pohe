package com.eccentricyan.pohe.presentation.base

import android.content.Context
import android.databinding.BaseObservable

import com.eccentricyan.pohe.di.component.ActivityComponent
import com.eccentricyan.pohe.infra.api.RestfulApi
import com.google.gson.Gson
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

import org.greenrobot.eventbus.EventBus

import javax.inject.Inject

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm

/**
 * Created by shiyanhui on 2017/04/15.
 */

open class BaseViewModel : BaseObservable {
    @JvmField
    @Inject
    var context: Context? = null
    @JvmField
    @Inject
    var lifecycleProvider: LifecycleProvider<ActivityEvent>? = null
    @JvmField
    @Inject
    var api: RestfulApi? = null
    @JvmField
    @Inject
    var compositeDisposable: CompositeDisposable? = null
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
    var subscribeScheduler: Scheduler? = null


    constructor(component: ActivityComponent) {
        component.inject(this)
    }

    constructor() {

    }

}