package com.eccentricyan.pohe.presentation.article

import android.content.Intent
import android.databinding.Bindable
import android.databinding.ObservableInt
import android.util.Log
import android.view.View

import com.eccentricyan.pohe.common.command.ReplyCommand
import com.eccentricyan.pohe.di.component.ActivityComponent
import com.eccentricyan.pohe.domain.entity.Article
import com.eccentricyan.pohe.presentation.base.BaseViewModel
import com.eccentricyan.pohe.presentation.webview.WebViewActivity

import org.parceler.Parcels

import icepick.State
import io.reactivex.functions.Action

/**
 * Created by shiyanhui on 2017/04/15.
 */

class ArticleViewModel(component: ActivityComponent, private var article: Article?) : BaseViewModel(component) {
    @JvmField
    @State
    var progressVisibility: ObservableInt
    @JvmField
    @State
    var recyclerViewVisibility: ObservableInt
    @JvmField
    @State
    var infoTextVisibility: ObservableInt

    val title: String?
        @Bindable
        get() = article!!.page!!.title

    val description: String?
        @Bindable
        get() = article!!.page!!.description

    val thumbnail: String?
        @Bindable
        get() = article!!.page!!.thumbnail

    val url: String?
        @Bindable
        get() = article!!.page!!.url

    //command
    var itemClickCommand: ReplyCommand<Int> = ReplyCommand(Action  {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra(WebViewActivity.EXTRA_ARTICLE, Parcels.wrap<Article>(article))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context!!.startActivity(intent)
    })

    init {
        progressVisibility = ObservableInt(View.INVISIBLE)
        recyclerViewVisibility = ObservableInt(View.INVISIBLE)
        infoTextVisibility = ObservableInt(View.VISIBLE)
    }

    fun setArticle(article: Article) {
        this.article = article
        super.notifyChange()
    }

}