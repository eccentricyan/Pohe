package com.eccentricyan.pohe.common.listener

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log

/**
 * Created by shiyanhui on 2017/04/15.
 */

abstract class OnRcvScrollListener : RecyclerView.OnScrollListener(), OnBottomListener {
    internal val TAG = javaClass.simpleName
    /**
     * layoutManager的类型（枚举）
     */
    protected var layoutManagerType: LAYOUT_MANAGER_TYPE? = null
    internal var canLoadMore = false//当滑动时检测可以加载更多，在手指放下时才加载
    /**
     * 最后一个的位置
     */
    private var lastPositions: IntArray? = null

    /**
     * 最后一个可见的item的位置
     */
    private var lastVisibleItemPosition: Int = 0
    /**
     * 当前滑动的状态
     */
    private var currentScrollState = 0

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        currentScrollState = newState
        val layoutManager = recyclerView!!.layoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        if (visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_DRAGGING &&
                lastVisibleItemPosition >= totalItemCount - 1 - setRestItem()) {
            canLoadMore = true
        }
        if (currentScrollState == RecyclerView.SCROLL_STATE_IDLE && canLoadMore) {
            canLoadMore = false
            onBottom()
        }
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        //        if (dy < 0) {
        //            //下拉不处理
        //            lastVisibleItemPosition = -1;
        //            canLoadMore = false;
        //            return;
        //        }
        val layoutManager = recyclerView!!.layoutManager
        if (layoutManagerType == null) {
            if (layoutManager is LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR
            } else if (layoutManager is GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID
            } else if (layoutManager is StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID
            } else {
                throw RuntimeException("Unsupported LayoutManager used. Valid ones are " + "LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
            }
        }

        when (layoutManagerType) {
            OnRcvScrollListener.LAYOUT_MANAGER_TYPE.LINEAR -> lastVisibleItemPosition = (layoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()
            OnRcvScrollListener.LAYOUT_MANAGER_TYPE.GRID -> lastVisibleItemPosition = (layoutManager as GridLayoutManager)
                    .findLastVisibleItemPosition()
            OnRcvScrollListener.LAYOUT_MANAGER_TYPE.STAGGERED_GRID -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                if (lastPositions == null) {
                    lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
                }
                staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions)
                lastVisibleItemPosition = findMax(lastPositions!!)
            }
        }
        Log.d(TAG, "onScrolled: $dx...$dy")
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    //剩余多少下加载更多，0则不预先加载
    open fun setRestItem(): Int {
        return 20
    }

    enum class LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }
}
