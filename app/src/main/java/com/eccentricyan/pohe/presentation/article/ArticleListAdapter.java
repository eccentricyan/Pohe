package com.eccentricyan.pohe.presentation.article;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.eccentricyan.pohe.R;
import com.eccentricyan.pohe.databinding.ArticleBinding;
import com.eccentricyan.pohe.di.component.ActivityComponent;
import com.eccentricyan.pohe.domain.entity.Article;

import java.util.Collections;
import java.util.List;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {
    private ActivityComponent component;
    private List<Article> articles;

    public ArticleListAdapter(ActivityComponent component) {
        this.component = component;
        this.articles = Collections.emptyList();
    }

    public void setArticles(List<Article> articles, boolean refresh) {
        if (refresh || this.articles.size() <= 0) {
            this.articles = articles;
        } else {
            this.articles.addAll(articles);
        }
    }

    @Override
    public ArticleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ArticleBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.article,
                parent,
                false
        );
        return new ArticleListAdapter.ViewHolder(binding, component);
    }

    @Override
    public void onBindViewHolder(ArticleListAdapter.ViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ArticleBinding binding;
        final ActivityComponent component;

        ViewHolder(ArticleBinding binding, ActivityComponent component) {
            super(binding.getRoot());
            this.binding = binding;
            this.component = component;
        }

        void bind(Article article) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ArticleViewModel(component, article));
            } else {
                binding.getViewModel().setArticle(article);
            }
            binding.executePendingBindings();
        }
    }
}