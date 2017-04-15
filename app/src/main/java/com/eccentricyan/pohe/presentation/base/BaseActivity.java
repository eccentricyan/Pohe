package com.eccentricyan.pohe.presentation.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.eccentricyan.pohe.Application;
import com.eccentricyan.pohe.R;
import com.eccentricyan.pohe.di.component.ActivityComponent;
import com.eccentricyan.pohe.di.module.ActivityModule;
import com.eccentricyan.pohe.infra.api.RestfulApi;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.twitter.sdk.android.core.models.User;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class BaseActivity extends RxAppCompatActivity {
    protected ActivityComponent component;
    protected Toolbar toolbar;

    @Inject
    protected EventBus eventBus;
    @Inject
    protected Gson gson;
    @Inject
    protected Realm realm;
    @Inject
    CompositeDisposable compositeDisposable;

    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.component = Application.getComponent(this).activityComponent(new ActivityModule(this));
        this.component.inject(this);

    }

    public ActivityComponent getComponent() {
        return component;
    }

    protected void initToolBar() {
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        mCollapsingToolbarLayout.setTitleEnabled(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        boolean result = true;

        switch (id) {
            case android.R.id.home:
                finish();
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }

        return result;
    }
}
