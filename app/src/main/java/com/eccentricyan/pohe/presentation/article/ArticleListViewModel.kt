package com.eccentricyan.pohe.presentation.article

import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import android.util.Log
import android.view.View

import com.eccentricyan.pohe.common.command.ReplyCommand
import com.eccentricyan.pohe.di.component.ActivityComponent
import com.eccentricyan.pohe.domain.entity.Article
import com.eccentricyan.pohe.presentation.base.BaseViewModel
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

import icepick.State
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

/**
 * Created by shiyanhui on 2017/04/15.
 */

class ArticleListViewModel(component: ActivityComponent, @JvmField@field:State
var category: String) : BaseViewModel(component) {
    @JvmField
    @State
    var progressVisibility: ObservableInt
    @JvmField
    @State
    var offset: Int = 0
    @JvmField
    @State
    var isRefreshing = ObservableBoolean(false)
    @JvmField
    @State
    var recyclerViewVisibility: ObservableInt
    @JvmField
    @State
    var infoTextVisibility: ObservableInt

    val onLoadMoreCommand: ReplyCommand<Int> = ReplyCommand(Action {
        Log.e("replycommand", "load")
        if (isRefreshing.get()) return@Action
        loadArticles(false)
    })

    val onRefreshCommand: ReplyCommand<Int> = ReplyCommand(Action {
        Log.e("replycommand", "refresh")
        if (isRefreshing.get()) return@Action
        offset = 0
        loadArticles(true)
    })


    init {
        progressVisibility = ObservableInt(View.INVISIBLE)
        recyclerViewVisibility = ObservableInt(View.INVISIBLE)
        infoTextVisibility = ObservableInt(View.VISIBLE)
        loadArticles(false)
    }

    private fun loadArticles(refresh: Boolean?) {
        isRefreshing.set(true)
        progressVisibility.set(View.VISIBLE)
        recyclerViewVisibility.set(View.INVISIBLE)
        infoTextVisibility.set(View.INVISIBLE)
        val observable = api!!.articles(category, offset)
        compositeDisposable!!.add(
                observable
                        .map { jsonObject ->
                            gson!!.fromJson<Any>(jsonObject.get("items"), object : TypeToken<List<Article>>() {

                            }.type) as List<Article>?
                        }
                        .compose(lifecycleProvider!!.bindToLifecycle())
                        .observeOn(Schedulers.io())
                        .doAfterTerminate { progressVisibility.set(View.INVISIBLE) }
                        .subscribeOn(subscribeScheduler)
                        .subscribe({ articles ->
                            eventBus!!.post(ArticlesChangeEvent(articles!!, refresh!!, category))
                            if (!articles.isEmpty()) {
                                recyclerViewVisibility.set(View.VISIBLE)
                            } else {

                            }
                            offset += articles.size
                        },
                                { error ->
                                    Log.d("onError: ", error.toString())
                                    isRefreshing.set(false)
                                },
                                { isRefreshing.set(false) }))
    }
}