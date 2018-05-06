package com.tanavota.tanavota.view.common

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View


class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable?

    init {
        val attrs = context.obtainStyledAttributes(ATTRS)
        mDivider = attrs.getDrawable(0)
        attrs.recycle()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawVertical(canvas, parent)
    }

    fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(canvas)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
    }

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}