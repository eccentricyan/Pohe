package com.eccentricyan.pohe.presentation.article;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eccentricyan.pohe.R;
import com.eccentricyan.pohe.common.Bundler.ParcelBundler;
import com.eccentricyan.pohe.databinding.FragmentArticleListBinding;
import com.eccentricyan.pohe.domain.entity.Article;
import com.eccentricyan.pohe.presentation.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import icepick.Icepick;
import icepick.State;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class ArticleListFragment extends BaseFragment {
    private FragmentArticleListBinding binding;
    private ArticleListViewModel viewModel;
    private static final String ARGS_NAME = "topics";
    private String topicId;
    boolean isLoad = false;
    @State(ParcelBundler.class)
    List<Article> articles;
    boolean refresh;

    public static ArticleListFragment newInstance(String topicId) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_NAME, topicId);
        fragment.setArguments(args);

        return fragment;
    }
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Icepick.restoreInstanceState(this, savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_list, null, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        topicId = args.getString(ARGS_NAME);
        viewModel = new ArticleListViewModel(component, topicId);
//        binding.recyclerViewRepos.scrollEvents(this)
//        RxRecyclerView.scrollEvents(binding.recyclerViewRepos)
//        Icepick.restoreInstanceState(viewModel, savedInstanceState);

        binding.setViewModel(viewModel);

        setupRecyclerView(binding.recyclerViewRepos);
    }

    @Override
    public void onStart() {
        super.onStart();
        eventBus.register(this);
    }

    @Override
    public void onStop() {
        eventBus.unregister(this);
        super.onStop();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        ArticleListAdapter adapter = new ArticleListAdapter(component);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setSwipeRefreshLayout();
        setupAdapterRepositories(adapter);
    }

    private void setupAdapterRepositories(ArticleListAdapter adapter) {
        if (articles != null) {
            adapter.setArticles(articles, refresh);
            adapter.notifyDataSetChanged();
        }
    }

    private void setSwipeRefreshLayout() {
        //设置首次运行进度条刷新
        binding.swipeRefreshLayout.setProgressViewOffset(false,
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        24,
                        getResources().getDisplayMetrics()));
        //设置进度条颜色
        binding.swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoad) {
            isLoad = true;
            viewModel = binding.getViewModel();
            if (viewModel != null) viewModel.onRefreshCommand.execute();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeArticles(ArticlesChangeEvent event) {
        this.articles = event.map.get(topicId);
        this.refresh = event.refresh;
        ArticleListAdapter adapter = (ArticleListAdapter) binding.recyclerViewRepos.getAdapter();
        setupAdapterRepositories(adapter);
    }

}