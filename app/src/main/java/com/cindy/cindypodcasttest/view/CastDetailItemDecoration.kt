package com.cindy.cindypodcasttest.view

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class CastDetailItemDecoration(private val mContext: Context,
                               private val mOrientation: Int,
                               private val mSpaceLeft: Int = -1,
                               private val mSpaceTop: Int = -1,
                               private val mSpaceRight: Int = -1,
                               private val mSpaceBottom: Int = -1): DividerItemDecoration(mContext, mOrientation) {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        if(mSpaceLeft!=-1) outRect.left = mSpaceLeft
        if(mSpaceTop!=-1) outRect.top = mSpaceTop
        if(mSpaceRight!=-1) outRect.right = mSpaceRight
        if(mSpaceBottom!=-1) outRect.bottom = mSpaceBottom

        if(parent.getChildLayoutPosition(view)==0){
            outRect.top = 0
        }

    }
}