package com.eccentricyan.pohe.presentation.main

import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.eccentricyan.pohe.R
import com.eccentricyan.pohe.common.utils.NetUtils
import com.eccentricyan.pohe.databinding.FragmentMainBinding
import com.eccentricyan.pohe.presentation.article.ArticleListFragment
import com.eccentricyan.pohe.presentation.base.BaseFragment
import com.eccentricyan.pohe.presentation.category.CategoryViewModel
import com.eftimoff.viewpagertransformers.RotateDownTransformer
import com.eftimoff.viewpagertransformers.RotateUpTransformer
import com.eftimoff.viewpagertransformers.StackTransformer
import com.github.yamamotoj.pikkel.Pikkel
import com.github.yamamotoj.pikkel.PikkelDelegate

//import icepick.Icepick
//import icepick.Icepick.restoreInstanceState

/**
 * Created by shiyanhui on 2017/04/15.
 */

class MainFragment : BaseFragment(), Pikkel by PikkelDelegate() {
    private var binding: FragmentMainBinding? = null
    private var mListener: OnFragmentInteractionListener? = null
    private var mViewPager: ViewPager? = null
    private var adapter: ViewPagerAdapter? = null
    private var tabLayout: TabLayout? = null
    private val categories: Array<String>? = null
    private var categoryViewModel: CategoryViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
        if (!NetUtils.checkNet(this.context)) {
            Toast.makeText(this.context, "ネットに繋がっていません", Toast.LENGTH_LONG).show()
        }
        adapter = ViewPagerAdapter(getFragmentManager())
        restoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tabLayout = binding!!.topicTabs
        mViewPager = binding!!.topicContainer
        categoryViewModel = CategoryViewModel(component)
        categoryViewModel!!.category().subscribe { category ->
            setupViewPager(mViewPager, category.categories)
            tabLayout!!.setupWithViewPager(mViewPager)
        }
    }

    private fun setupViewPager(viewPager: ViewPager?, categories: Array<String>) {
        for (i in categories.indices) {
            adapter!!.addFrag(ArticleListFragment.newInstance(categories[i]), categories[i])
        }
        viewPager!!.adapter = adapter
        viewPager.offscreenPageLimit = adapter!!.count - 1
        viewPager.offscreenPageLimit = 3
        viewPager.setPageTransformer(true, RotateDownTransformer())
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveInstanceState(outState)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        fun newInstance(): MainFragment {
            val fragment = MainFragment()

            val args = Bundle()
            fragment.arguments = args

            return fragment
        }
    }
}