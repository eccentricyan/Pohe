package com.eccentricyan.pohe.presentation.article;

import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.eccentricyan.pohe.common.command.ReplyCommand;
import com.eccentricyan.pohe.di.component.ActivityComponent;
import com.eccentricyan.pohe.domain.entity.Article;
import com.eccentricyan.pohe.presentation.base.BaseViewModel;
import com.eccentricyan.pohe.presentation.webview.WebViewActivity;

import org.parceler.Parcels;

import icepick.State;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class ArticleViewModel extends BaseViewModel {
    private Article article;
    @State
    public ObservableInt progressVisibility;
    @State
    public ObservableInt recyclerViewVisibility;
    @State
    public ObservableInt infoTextVisibility;

    public ArticleViewModel(ActivityComponent component, Article article) {
        super(component);
        progressVisibility = new ObservableInt(View.INVISIBLE);
        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        infoTextVisibility = new ObservableInt(View.VISIBLE);
        this.article = article;
    }

    @Bindable
    public String getTitle() {
        return article.page.title;
    }

    @Bindable
    public String getDescription() {
        return article.page.description;
    }

    @Bindable
    public String getThumbnail() {
        return article.page.thumbnail;
    }

    @Bindable
    public String getUrl() {
        return article.page.url;
    }

    public void setArticle(Article article) {
        this.article = article;
        super.notifyChange();
    }

    //command
    public ReplyCommand itemClickCommand = new ReplyCommand(() -> {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_ARTICLE, Parcels.wrap(article));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    });

}