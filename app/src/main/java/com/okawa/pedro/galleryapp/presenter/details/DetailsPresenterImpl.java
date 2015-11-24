package com.okawa.pedro.galleryapp.presenter.details;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Build;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.databinding.ActivityDetailsBinding;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.ui.details.DetailsView;
import com.okawa.pedro.galleryapp.util.listener.OnDataRequestListener;

import greendao.ImageData;

/**
 * Created by pokawa on 23/11/15.
 */
public class DetailsPresenterImpl implements DetailsPresenter, OnDataRequestListener {

    private ImageData mImageData;

    private DetailsView mDetailsView;
    private ImageRepository mImageRepository;
    private ShutterStockPresenter mShutterStockPresenter;

    private ActivityDetailsBinding mActivityDetailsBinding;

    private int mFlexibleSpaceHeight;
    private int mStatusBarColor;
    private int mBaseColor;

    public DetailsPresenterImpl(DetailsView detailsView, ImageRepository imageRepository,
                                ShutterStockPresenter shutterStockPresenter) {

        this.mDetailsView = detailsView;
        this.mImageRepository = imageRepository;
        this.mShutterStockPresenter = shutterStockPresenter;
    }

    @Override
    public void defineViewsBehaviour(Context context, ViewDataBinding viewDataBinding, long imageId) {
        mDetailsView.showProgress();

        /* DEFINES DATA BINDING */
        mActivityDetailsBinding = (ActivityDetailsBinding) viewDataBinding;

        /* RETRIEVES AND LOAD INTO THE LAYOUT THE IMAGE DATA */
        mImageData = mImageRepository.getImageDataById(imageId);
        mDetailsView.loadImageData(mImageData);

        /* DEFINES VARIABLES USED TO CREATE TOOLBAR ANIMATION ON SCROLL */
        mFlexibleSpaceHeight = context.getResources()
                .getDimensionPixelSize(R.dimen.view_image_card_height);
        mBaseColor = context.getResources().getColor(R.color.color_primary);
        mStatusBarColor = context.getResources().getColor(R.color.black);

        /*
         ADD LISTENER TO THE SCROLLVIEW AND LAYOUT LISTENER
         TO UPDATE TOOLBAR IN ONE OF THESE CASES
         */
        mActivityDetailsBinding.svActivityDetails
                .setScrollViewCallbacks(new ObservableScrollListener());
        ScrollUtils.addOnGlobalLayoutListener(mActivityDetailsBinding.getRoot(),
                new GlobalLayoutListener());

        /* CHECK IF IS NECESSARY TO MAKE A NEW REQUEST ON SERVER */
        if(mImageData.getContributor() == null || mImageData.getContributor().isEmpty()) {
            requestData();
        } else {
            onCompleted();
        }
    }

    @Override
    public void onDataError(String message) {
        mDetailsView.onError(message);
    }

    @Override
    public void requestData() {
        mShutterStockPresenter.loadContributorData(this,
                mImageData.getImageId(), mImageData.getContributorId());
    }

    @Override
    public void onCompleted() {
        mDetailsView.loadContributorsData(mImageData.getContributor());
        mDetailsView.hideProgress();
    }

    /* TOOLBAR ANIMATIONS */
    private void updateScroll(int scrollY) {
        float alpha = Math.min(1, (float) scrollY /
                (mFlexibleSpaceHeight - mActivityDetailsBinding.rlImageInfo.getHeight() - 25));

        mActivityDetailsBinding.toolbar
                .setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, mBaseColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDetailsView.changeStatusBarColor(ScrollUtils.getColorWithAlpha(alpha / 4, mStatusBarColor));
        }

        mActivityDetailsBinding.rlImageInfo
                .setAlpha(1 - ((float) scrollY / (mFlexibleSpaceHeight / 3)));
    }

    public class GlobalLayoutListener implements Runnable {
        @Override
        public void run() {
            updateScroll(mActivityDetailsBinding.svActivityDetails.getCurrentScrollY());
        }
    }

    public class ObservableScrollListener implements ObservableScrollViewCallbacks {
        @Override
        public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
            updateScroll(scrollY);
        }

        @Override
        public void onDownMotionEvent() {
        }

        @Override
        public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        }
    }
}
