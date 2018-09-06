package com.eccentricyan.pohe.presentation.article

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eccentricyan.pohe.R
import com.eccentricyan.pohe.common.Bundler.ParcelBundler
import com.eccentricyan.pohe.databinding.FragmentArticleListBinding
import com.eccentricyan.pohe.domain.entity.Article
import com.eccentricyan.pohe.presentation.base.BaseFragment
import com.github.yamamotoj.pikkel.Pikkel
import com.github.yamamotoj.pikkel.PikkelDelegate

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import icepick.Icepick
import icepick.State

/**
 * Created by shiyanhui on 2017/04/15.
 */

class ArticleListFragment : BaseFragment(), Pikkel by PikkelDelegate() {
    private var binding: FragmentArticleListBinding? = null
    private var viewModel: ArticleListViewModel? = null
    private var topicId: String? = null
    internal var isLoad = false
//    @State(ParcelBundler::class)
    var articles: MutableList<Article>? = null
    internal var refresh: Boolean = false
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreInstanceState(savedInstanceState)
        //        Icepick.restoreInstanceState(this, savedInstanceState);
        if (arguments != null) {
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_list, null, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = arguments
        topicId = args!!.getString(ARGS_NAME)
        viewModel = ArticleListViewModel(component!!, topicId!!)

        binding!!.viewModel = viewModel

        setupRecyclerView(binding!!.recyclerViewRepos)
    }

    override fun onStart() {
        super.onStart()
        eventBus!!.register(this)
    }

    override fun onStop() {
        eventBus!!.unregister(this)
        super.onStop()
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val adapter = ArticleListAdapter(component!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        setSwipeRefreshLayout()
        setupAdapterRepositories(adapter)
    }

    private fun setupAdapterRepositories(adapter: ArticleListAdapter) {
        if (articles != null) {
            adapter.setArticles(articles!!, refresh)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setSwipeRefreshLayout() {
        //设置首次运行进度条刷新
        binding!!.swipeRefreshLayout.setProgressViewOffset(false,
                0,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        24f,
                        resources.displayMetrics).toInt())
        //设置进度条颜色
        binding!!.swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark)
    }

    override fun onResume() {
        super.onResume()
        if (!isLoad) {
            isLoad = true
            viewModel = binding!!.viewModel
            if (viewModel != null) viewModel!!.onRefreshCommand.execute()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onChangeArticles(event: ArticlesChangeEvent) {
        this.articles = event.map[topicId]?.toMutableList()
        this.refresh = event.refresh
        val adapter = binding!!.recyclerViewRepos.adapter as ArticleListAdapter
        setupAdapterRepositories(adapter)
    }

    companion object {
        private val ARGS_NAME = "topics"

        fun newInstance(topicId: String): ArticleListFragment {
            val fragment = ArticleListFragment()
            val args = Bundle()
            args.putString(ARGS_NAME, topicId)
            fragment.arguments = args

            return fragment
        }
    }

}