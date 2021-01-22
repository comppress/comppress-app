package com.example.couscousapp.views;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLayoutManager extends LinearLayoutManager {

    private int mPendingTargetPos = -1;
    private int mPendingPosOffset = -1;

    public CustomLayoutManager(Context context) {
        super(context);
    }

    public CustomLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (mPendingTargetPos != -1 && state.getItemCount() > 0) {
            /*
            Data is present now, we can set the real scroll position
            */
            scrollToPositionWithOffset(mPendingTargetPos, mPendingPosOffset);
            mPendingTargetPos = -1;
            mPendingPosOffset = -1;
        }
        super.onLayoutChildren(recycler, state);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        /*
        May be needed depending on your implementation.

        Ignore target start position if InstanceState is available (page existed before already, keep position that user scrolled to)
         */
        mPendingTargetPos = -1;
        mPendingPosOffset = -1;
        super.onRestoreInstanceState(state);
    }

    /**
     * Sets a start position that will be used <b>as soon as data is available</b>.
     * May be used if your Adapter starts with itemCount=0 (async data loading) but you need to
     * set the start position already at this time. As soon as itemCount > 0,
     * it will set the scrollPosition, so that given itemPosition is visible.
     * @param position
     * @param offset
     */
    public void setTargetStartPos(int position, int offset) {
        mPendingTargetPos = position;
        mPendingPosOffset = offset;
    }
}