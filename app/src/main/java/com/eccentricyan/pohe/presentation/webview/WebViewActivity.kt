package com.eccentricyan.pohe.presentation.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient

import com.eccentricyan.pohe.R
import com.eccentricyan.pohe.domain.entity.Article
import com.eccentricyan.pohe.presentation.base.BaseActivity

import org.parceler.Parcels

/**
 * Created by shiyanhui on 2017/04/15.
 */

class WebViewActivity : BaseActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        initToolBar()
        val article = Parcels.unwrap<Article>(intent.getParcelableExtra<Parcelable>(EXTRA_ARTICLE))
        supportActionBar!!.setTitle(article.page!!.title)
        //レイアウトで指定したWebViewのIDを指定する。
        val myWebView = findViewById<View>(R.id.webview) as WebView

        //リンクをタップしたときに標準ブラウザを起動させない
        myWebView.webViewClient = WebViewClient()
        //最初にgoogleのページを表示する。
        myWebView.loadUrl(article.page!!.url)
        //jacascriptを許可する
        myWebView.settings.javaScriptEnabled = true
    }

    companion object {
        val EXTRA_ARTICLE = "article"
    }


}