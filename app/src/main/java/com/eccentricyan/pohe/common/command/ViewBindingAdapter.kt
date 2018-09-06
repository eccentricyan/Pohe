package com.eccentricyan.pohe.common.command

import android.databinding.BindingAdapter
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.eccentricyan.pohe.common.listener.OnRcvScrollListener

/**
 * Created by shiyanhui on 2017/04/15.
 */

object ViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("clickCommand")
    fun clickCommand(view: View, clickCommand: ReplyCommand<*>?) {
        view.setOnClickListener {
            clickCommand?.execute()
        }
    }

    @JvmStatic
    @BindingAdapter("onLoadMoreCommand")
    fun onLoadMoreCommand(recyclerView: RecyclerView, onLoadMoreCommand: ReplyCommand<*>?) {
        recyclerView.addOnScrollListener(object : OnRcvScrollListener() {
            override fun onBottom() {
                onLoadMoreCommand?.execute()
            }

            override fun setRestItem(): Int {
                return 20
            }
        })
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String?) {
        if (imageUrl.isNullOrEmpty())
            Glide.with(view.context)
                        .load(imageUrl)
                        .into(view)
    }

    @JvmStatic
    @BindingAdapter("addOnOffsetChangedListener")
    fun initToolbar(appBarLayout: AppBarLayout, listener: AppBarLayout.OnOffsetChangedListener) {
        appBarLayout.addOnOffsetChangedListener(listener)
    }

    @JvmStatic
    @BindingAdapter("isRefresh")
    fun setRefresh(swipeRefreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }

    @JvmStatic
    @BindingAdapter("onRefreshCommand")
    fun onRefreshCommand(swipeRefreshLayout: SwipeRefreshLayout, onRefreshCommand: ReplyCommand<*>?) {
        swipeRefreshLayout.setOnRefreshListener {
            onRefreshCommand?.execute()
        }
    }

    @JvmStatic
    @BindingAdapter("render")
    fun loadHtml(webView: WebView, html: String) {
        if (!TextUtils.isEmpty(html)) {
            webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
        }
    }

}
