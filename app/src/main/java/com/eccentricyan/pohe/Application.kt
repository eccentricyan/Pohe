package com.eccentricyan.pohe

import android.content.Context
import android.support.annotation.VisibleForTesting

import com.eccentricyan.pohe.di.component.ApplicationComponent
import com.eccentricyan.pohe.di.component.DaggerApplicationComponent
import com.eccentricyan.pohe.di.module.ApplicationModule

/**
 * Created by shiyanhui on 2017/04/15.
 */

open class Application : android.app.Application() {
    private var component: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        this.component = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule())
                .build()
        instance = this
    }

    @VisibleForTesting
    fun setComponent(appComponent: ApplicationComponent) {
        this.component = appComponent
    }

    companion object {
        var instance: Application? = null
            private set

        fun getComponent(context: Context): ApplicationComponent? {
            return (context.applicationContext as Application).component
        }
    }
}