package com.tanavota.tanavota.util

import android.support.v7.widget.RecyclerView
import com.tanavota.tanavota.viewmodel.common.DataLoadingState
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.GridLayoutManager



class RecyclerViewScrollListenerDelegate {
    fun handleOnScrollEvent(state: DataLoadingState, recyclerView: RecyclerView, dx: Int, dy: Int, onLoadMore: ()->Unit) {
        if (state != DataLoadingState.Completed) {
            return
        }

        val totalItemCount = recyclerView.adapter.itemCount
        val visibleItemCount = recyclerView.childCount

        val layoutManager = recyclerView.layoutManager

        if (layoutManager is GridLayoutManager) { // GridLayoutManager
            val firstPosition = layoutManager.findFirstVisibleItemPosition() // RecyclerViewに表示されている一番上のアイテムポジション
            if (totalItemCount - 10 < visibleItemCount + firstPosition) {
                // ページング処理
                onLoadMore()
            }
        } else if (layoutManager is LinearLayoutManager) { // LinearLayoutManager
            val firstPosition = layoutManager.findFirstVisibleItemPosition() // RecyclerViewの一番上に表示されているアイテムのポジション
            if (totalItemCount - 10 < visibleItemCount + firstPosition) {
                // ページング処理
                onLoadMore()
            }
        }
    }
}