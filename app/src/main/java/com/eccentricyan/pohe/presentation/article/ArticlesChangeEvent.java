package com.eccentricyan.pohe.presentation.article;

import com.eccentricyan.pohe.domain.entity.Article;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class ArticlesChangeEvent {
    public final HashMap<String, List<Article>> map = new HashMap<>();

    public final boolean refresh;
    public ArticlesChangeEvent(List<Article> articles, boolean refresh, String category) {
        this.map.put(category, articles);
        this.refresh = refresh;
    }
}
