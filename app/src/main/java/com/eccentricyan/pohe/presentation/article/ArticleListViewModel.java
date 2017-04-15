package com.eccentricyan.pohe.presentation.article;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.eccentricyan.pohe.common.command.ReplyCommand;
import com.eccentricyan.pohe.di.component.ActivityComponent;
import com.eccentricyan.pohe.domain.entity.Article;
import com.eccentricyan.pohe.presentation.base.BaseViewModel;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import icepick.State;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class ArticleListViewModel extends BaseViewModel {
    @State
    public int offset;
    @State
    public String category;
    @State
    public ObservableBoolean isRefreshing = new ObservableBoolean(false);
    @State
    public ObservableInt progressVisibility;
    @State
    public ObservableInt recyclerViewVisibility;
    @State
    public ObservableInt infoTextVisibility;


    public ArticleListViewModel(ActivityComponent component, String category) {
        super(component);
        progressVisibility = new ObservableInt(View.INVISIBLE);
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        infoTextVisibility = new ObservableInt(View.VISIBLE);
        this.category = category;
        loadArticles(false);
    }

    private void loadArticles(Boolean refresh) {
        isRefreshing.set(true);
        progressVisibility.set(View.VISIBLE);
        recyclerViewVisibility.set(View.INVISIBLE);
        infoTextVisibility.set(View.INVISIBLE);
        Observable<JsonObject> observable = api.articles(category, offset);
        compositeDisposable.add(
                observable
                        .map(jsonObject -> (List<Article>)gson.fromJson(jsonObject.get("items"), new TypeToken<List<Article>>(){}.getType()))
                        .compose(lifecycleProvider.bindToLifecycle())
                        .observeOn(Schedulers.io())
                        .doAfterTerminate(() -> progressVisibility.set(View.INVISIBLE))
                        .subscribeOn(subscribeScheduler)
                        .subscribe(articles -> {
                                    eventBus.post(new ArticlesChangeEvent(articles, refresh, category));
                                    if (!articles.isEmpty()) {
                                        recyclerViewVisibility.set(View.VISIBLE);
                                    } else {

                                    }
                                    offset += articles.size();
                                },
                                error -> {
                                    Log.d("onError: ",  error.toString());
                                    isRefreshing.set(false);
                                },
                                () -> {
                                    isRefreshing.set(false);
                                }));
    }

    public final ReplyCommand<Integer> onLoadMoreCommand = new ReplyCommand(new Action(){
        @Override
        public void run() {
            Log.e("replycommand", "load");
            if (isRefreshing.get()) return;
            loadArticles(false);
        }
    });

    public final ReplyCommand<Integer> onRefreshCommand = new ReplyCommand(new Action(){
        @Override
        public void run() {
            Log.e("replycommand", "refresh");
            if (isRefreshing.get()) return;
            offset = 0;
            loadArticles(true);
        }
    });
}