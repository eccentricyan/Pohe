package com.eccentricyan.pohe.di.component

import com.eccentricyan.pohe.di.module.ActivityModule
import com.eccentricyan.pohe.di.module.ApplicationModule
import com.eccentricyan.pohe.di.scope.ApplicationScope

import dagger.Component

/**
 * Created by shiyanhui on 2017/04/15.
 */

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun activityComponent(module: ActivityModule): ActivityComponent
}