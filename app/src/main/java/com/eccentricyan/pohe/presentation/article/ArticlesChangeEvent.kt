package com.eccentricyan.pohe.presentation.article

import com.eccentricyan.pohe.domain.entity.Article

import java.util.HashMap

/**
 * Created by shiyanhui on 2017/04/15.
 */

class ArticlesChangeEvent(articles: List<Article>, val refresh: Boolean, category: String) {
    val map = HashMap<String, List<Article>>()

    init {
        this.map[category] = articles
    }
}
