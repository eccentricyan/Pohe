package com.eccentricyan.pohe.di.component;

import com.eccentricyan.pohe.di.module.ActivityModule;
import com.eccentricyan.pohe.di.scope.ActivityScope;
import com.eccentricyan.pohe.presentation.base.BaseActivity;
import com.eccentricyan.pohe.presentation.base.BaseFragment;
import com.eccentricyan.pohe.presentation.base.BaseViewModel;

import dagger.Subcomponent;

/**
 * Created by shiyanhui on 2017/04/15.
 */

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(BaseActivity viewModelActivity);
    void inject(BaseViewModel viewModel);
    void inject(BaseFragment fragment);
}