package com.eccentricyan.pohe.presentation.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import java.util.ArrayList

/**
 * Created by shiyanhui on 2017/04/15.
 */

class ViewPagerAdapter(manager: FragmentManager?) : FragmentStatePagerAdapter(manager) {

    private var mFragmentList: MutableList<Fragment> = ArrayList()
    private var mFragmentTitleList: MutableList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFrag(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    fun clearFrag() {
        mFragmentList = ArrayList()
        mFragmentTitleList = ArrayList()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }
}