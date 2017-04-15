package com.eccentricyan.pohe.presentation.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eccentricyan.pohe.R;
import com.eccentricyan.pohe.common.utils.NetUtils;
import com.eccentricyan.pohe.databinding.FragmentMainBinding;
import com.eccentricyan.pohe.presentation.article.ArticleListFragment;
import com.eccentricyan.pohe.presentation.base.BaseFragment;
import com.eccentricyan.pohe.presentation.category.CategoryViewModel;

import icepick.Icepick;

/**
 * Created by shiyanhui on 2017/04/15.
 */

public class MainFragment extends BaseFragment {
    private FragmentMainBinding binding;
    private OnFragmentInteractionListener mListener;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private String[] categories;
    private CategoryViewModel categoryViewModel;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        if (!NetUtils.checkNet(this.getContext())) {
            Toast.makeText(this.getContext(), "ネットに繋がっていません", Toast.LENGTH_LONG).show();
        }
        adapter = new ViewPagerAdapter(getFragmentManager());
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tabLayout = binding.topicTabs;
        mViewPager = binding.topicContainer;
        categoryViewModel = new CategoryViewModel(component);
        categoryViewModel.category().subscribe(category -> {
            setupViewPager(mViewPager, category.categories);
            tabLayout.setupWithViewPager(mViewPager);
        });
    }

    private void setupViewPager(ViewPager viewPager, String[] categories) {
        for(int i = 0; i < categories.length; i++) {
            adapter.addFrag(ArticleListFragment.newInstance(categories[i]), categories[i]);
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}