package com.eccentricyan.pohe.presentation.webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.eccentricyan.pohe.R;
import com.eccentricyan.pohe.domain.entity.Article;
import com.eccentricyan.pohe.presentation.base.BaseActivity;

import org.parceler.Parcels;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class WebViewActivity extends BaseActivity {
    public static final String EXTRA_ARTICLE= "article";
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initToolBar();
        Article article = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_ARTICLE));
        getSupportActionBar().setTitle(article.page.title);
        //レイアウトで指定したWebViewのIDを指定する。
        WebView myWebView = (WebView)findViewById(R.id.webview);

        //リンクをタップしたときに標準ブラウザを起動させない
        myWebView.setWebViewClient(new WebViewClient());
        //最初にgoogleのページを表示する。
        myWebView.loadUrl(article.page.url);
        //jacascriptを許可する
        myWebView.getSettings().setJavaScriptEnabled(true);
    }


}