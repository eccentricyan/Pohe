package com.eccentricyan.pohe;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.eccentricyan.pohe.di.component.ApplicationComponent;
import com.eccentricyan.pohe.di.component.DaggerApplicationComponent;
import com.eccentricyan.pohe.di.module.ApplicationModule;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class Application extends android.app.Application {
    private static Application instance = null;
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        this.component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule())
                .build();
        instance = this;
    }

    public static ApplicationComponent getComponent(Context context) {
        return ((Application) context.getApplicationContext()).component;
    }

    @VisibleForTesting
    public void setComponent(ApplicationComponent appComponent) {
        this.component = appComponent;
    }

    public static Application getInstance() {
        return instance;
    }
}