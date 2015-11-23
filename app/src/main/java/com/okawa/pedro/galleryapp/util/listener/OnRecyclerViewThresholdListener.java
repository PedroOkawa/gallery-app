package com.okawa.pedro.galleryapp.util.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by pokawa on 22/11/15.
 */
public abstract class OnRecyclerViewThresholdListener extends RecyclerView.OnScrollListener {
    public static final int LIST_THRESHOLD = 5;

    private int mPreviousTotal = 0;
    private boolean mLoading = true;
    private int mFirstVisibleItem;
    private int mVisibleItemCount;
    private int mTotalItemCount;
    private GridLayoutManager mGridLayoutManager;

    public abstract void onVisibleThreshold();

    public OnRecyclerViewThresholdListener(GridLayoutManager gridLayoutManager) {
        this.mGridLayoutManager = gridLayoutManager;
        if(mGridLayoutManager.getChildCount() == 0) {
            onVisibleThreshold();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mVisibleItemCount = recyclerView.getChildCount();
        mTotalItemCount = mGridLayoutManager.getItemCount();
        mFirstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

        if (mLoading) {
            if (mTotalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = mTotalItemCount;
            }
        }

        if (!mLoading &&
            (mTotalItemCount - mVisibleItemCount) <= (mFirstVisibleItem + LIST_THRESHOLD)) {
            onVisibleThreshold();
            mLoading = true;
        }
    }

    public void reset() {
        mVisibleItemCount = 0;
        mTotalItemCount = 0;
        mPreviousTotal = 0;
    }
}
