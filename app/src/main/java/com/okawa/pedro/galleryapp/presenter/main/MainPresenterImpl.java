package com.okawa.pedro.galleryapp.presenter.main;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.database.CategoryRepository;
import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.databinding.ActivityMainBinding;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.ui.main.MainView;
import com.okawa.pedro.galleryapp.util.adapter.main.TypeAdapter;
import com.okawa.pedro.galleryapp.util.adapter.main.ImageAdapter;
import com.okawa.pedro.galleryapp.util.listener.OnTypeTouchListener;
import com.okawa.pedro.galleryapp.util.listener.OnDataRequestListener;
import com.okawa.pedro.galleryapp.util.listener.OnImageTouchListener;
import com.okawa.pedro.galleryapp.util.listener.OnRecyclerViewThresholdListener;
import com.okawa.pedro.galleryapp.util.listener.OnViewTouchListener;

import java.util.List;

import greendao.ImageData;

/**
 * Created by pokawa on 20/11/15.
 */
public class MainPresenterImpl implements MainPresenter, OnDataRequestListener,
        OnViewTouchListener, OnImageTouchListener, OnTypeTouchListener {

    private String mType = ImageData.TYPE_ALL;

    private MainView mMainView;
    private ImageRepository mImageRepository;
    private CategoryRepository mCategoryRepository;
    private ShutterStockPresenter mShutterStockPresenter;
    private ImageAdapter mImageAdapter;
    private OnMainRecyclerViewListener mOnMainRecyclerViewListener;

    public MainPresenterImpl(MainView mainView, ImageRepository imageRepository,
                             CategoryRepository categoryRepository,
                             ShutterStockPresenter shutterStockPresenter) {

        this.mMainView = mainView;
        this.mImageRepository = imageRepository;
        this.mCategoryRepository = categoryRepository;
        this.mShutterStockPresenter = shutterStockPresenter;
    }

    @Override
    public void defineViewsBehaviour(ViewDataBinding viewDataBinding) {
        ActivityMainBinding activityMainBinding = (ActivityMainBinding) viewDataBinding;

        /* TOUCH LISTENER BEHAVIOUR */

        activityMainBinding.setOnViewTouchListener(this);

        /* RECYCLER VIEW (ADAPTER) BEHAVIOUR */

        mImageAdapter = (ImageAdapter) activityMainBinding.rvActivityMainImages.getAdapter();
        mImageAdapter.setOnImageTouchListener(this);

        /* RECYCLER VIEW BEHAVIOUR */

        mOnMainRecyclerViewListener =
                new OnMainRecyclerViewListener(
                        (GridLayoutManager) activityMainBinding
                                .rvActivityMainImages.getLayoutManager());

        activityMainBinding.rvActivityMainImages.addOnScrollListener(mOnMainRecyclerViewListener);

        /* SWIPE TO REFRESH BEHAVIOUR */

        activityMainBinding
                .srActivityMainImages
                .setOnRefreshListener(
                        new SwipeRefreshLayout
                                .OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                mImageRepository.clearImageData();
                                mCategoryRepository.clearCategoryData();
                                mShutterStockPresenter.resetPageSearch();
                                resetAdapter();
                            }
                        });

        /* NAVIGATION VIEW BEHAVIOUR */

        ((TypeAdapter)activityMainBinding.nvLayout.rvNavigationView.getAdapter())
                .setOnTypeTouchListener(this);
    }

    @Override
    public void reload() {
        if(mImageAdapter.getItemCount() < (mImageRepository.countImageDataByType(mType))) {

                loadDataFromDB(mImageRepository.getPagedImageData(mImageAdapter.getItemCount(), mType));

        } else {

            mMainView.showProgress();
            mShutterStockPresenter.loadDataFromApi(this, mType);

        }
    }

    @Override
    public void onDataError(String message) {
        mMainView.hideProgress();
    }

    @Override
    public void onRecall() {
        reload();
    }

    @Override
    public void onCompleted() {
        loadDataFromDB(mImageRepository.getPagedImageData(mImageAdapter.getItemCount(), mType));
    }

    private void loadDataFromDB(List<ImageData> imageDataList) {
        mImageAdapter.addDataSet(imageDataList);
        mImageAdapter.notifyDataSetChanged();
        mMainView.hideProgress();
    }

    @Override
    public void onViewTouched(View view) {
        if(view.getId() == R.id.footerNavigationView) {
            Uri uri = Uri.parse(view.getResources().getString(R.string.github_uri));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mMainView.openGithub(intent);
        }
    }

    @Override
    public void onImageTouched(long imageId, Pair<View, String> ... params) {
        mMainView.openDetails(imageId, params);
    }

    @Override
    public void onTypeTouched(String type) {
        if(!this.mType.equals(type)) {
            this.mType = type;
            mShutterStockPresenter.definePageSearch(type, mImageRepository.getCurrentPage(type));
            resetAdapter();

            reload();
        }
        mMainView.closeNavigation();
    }

    /*
     CLEANS THE ADAPTER TO RELOAD DATA
     */
    private void resetAdapter() {
        mOnMainRecyclerViewListener.reset();
        mImageAdapter.reset();
    }

    public class OnMainRecyclerViewListener extends OnRecyclerViewThresholdListener {

        public OnMainRecyclerViewListener(GridLayoutManager mGridLayoutManager) {
            super(mGridLayoutManager);
        }

        @Override
        public void onVisibleThreshold() {
            reload();
        }
    }
}