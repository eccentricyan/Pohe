package com.eccentricyan.pohe.di.module

import android.content.Context

import com.eccentricyan.pohe.di.scope.ActivityScope
import com.eccentricyan.pohe.presentation.base.BaseActivity
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @ActivityScope
    fun activityContext(): Context {
        return this.activity
    }

    @Provides
    @ActivityScope
    fun lifecycleProvider(): LifecycleProvider<ActivityEvent> {
        return activity
    }

}