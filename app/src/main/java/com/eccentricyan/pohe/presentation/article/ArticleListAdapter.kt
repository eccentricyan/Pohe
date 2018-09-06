package com.eccentricyan.pohe.presentation.article

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.eccentricyan.pohe.R
import com.eccentricyan.pohe.databinding.ArticleBinding
import com.eccentricyan.pohe.di.component.ActivityComponent
import com.eccentricyan.pohe.domain.entity.Article

import java.util.Collections

/**
 * Created by shiyanhui on 2017/04/15.
 */

class ArticleListAdapter(private val component: ActivityComponent) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
    var articles: MutableList<Article>? = null

    init {
        this.articles = ArrayList()
    }

    fun setArticles(articles: MutableList<Article>, refresh: Boolean) {
        if (refresh || this.articles!!.size <= 0) {
            this.articles = articles
        } else {
            this.articles!!.addAll(articles)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ArticleBinding>(
                LayoutInflater.from(parent.context),
                R.layout.article,
                parent,
                false
        )
        return ArticleListAdapter.ViewHolder(binding, component)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles!![position])
    }

    override fun getItemCount(): Int {
        return articles!!.size
    }

    class ViewHolder(val binding: ArticleBinding, val component: ActivityComponent) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            if (binding.viewModel == null) {
                binding.viewModel = ArticleViewModel(component, article)
            } else {
                binding.viewModel.setArticle(article)
            }
            binding.executePendingBindings()
        }
    }
}