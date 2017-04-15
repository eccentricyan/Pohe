package com.eccentricyan.pohe.presentation.category;

import com.eccentricyan.pohe.di.component.ActivityComponent;
import com.eccentricyan.pohe.domain.entity.Category;
import com.eccentricyan.pohe.presentation.base.BaseViewModel;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class CategoryViewModel extends BaseViewModel {
    public CategoryViewModel(ActivityComponent component) {
        super(component);
    }

    public Single<Category> category() {
        return api.categories()
                .map(jsonObject -> gson.fromJson(jsonObject.get("category"), Category.class))
                .compose(lifecycleProvider.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(subscribeScheduler);
    }
}
