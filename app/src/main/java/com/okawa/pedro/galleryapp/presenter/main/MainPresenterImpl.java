package com.okawa.pedro.galleryapp.presenter.main;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.database.CategoryRepository;
import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.databinding.ActivityMainBinding;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.ui.main.MainView;
import com.okawa.pedro.galleryapp.util.adapter.main.MainAdapter;
import com.okawa.pedro.galleryapp.util.listener.OnDataRequestListener;
import com.okawa.pedro.galleryapp.util.listener.OnRecyclerViewThresholdListener;
import com.okawa.pedro.galleryapp.util.listener.OnViewTouchListener;

import java.util.List;

import greendao.ImageData;

/**
 * Created by pokawa on 20/11/15.
 */
public class MainPresenterImpl implements MainPresenter, OnDataRequestListener, OnViewTouchListener {

    private MainView mMainView;
    private ImageRepository mImageRepository;
    private CategoryRepository mCategoryRepository;
    private ShutterStockPresenter mShutterStockPresenter;
    private MainAdapter mMainAdapter;
    private ActivityMainBinding mActivityMainBinding;
    private OnMainRecyclerViewListener mOnMainRecyclerViewListener;

    public MainPresenterImpl(MainView mainView,
                             ImageRepository imageRepository,
                             CategoryRepository categoryRepository,
                             ShutterStockPresenter shutterStockPresenter,
                             ViewDataBinding activityMainBinding) {
        this.mMainView = mainView;
        this.mImageRepository = imageRepository;
        this.mCategoryRepository = categoryRepository;
        this.mShutterStockPresenter = shutterStockPresenter;
        this.mActivityMainBinding = (ActivityMainBinding)activityMainBinding;
        defineViewsBehaviour();
    }

    private void defineViewsBehaviour() {
        mActivityMainBinding.setOnViewTouchListener(this);
        mMainAdapter = (MainAdapter) mActivityMainBinding.rvActivityMainImages.getAdapter();
        mOnMainRecyclerViewListener = new OnMainRecyclerViewListener(
                (GridLayoutManager) mActivityMainBinding
                        .rvActivityMainImages.getLayoutManager());
        mActivityMainBinding.rvActivityMainImages.addOnScrollListener(mOnMainRecyclerViewListener);

        mActivityMainBinding
                .srActivityMainImages
                .setOnRefreshListener(
                        new SwipeRefreshLayout
                                .OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                mImageRepository.clearImageData();
                                mCategoryRepository.clearCategoryData();
                                mOnMainRecyclerViewListener.reset();
                                mMainAdapter.reset();
                                reload();
                            }
                        });
    }

    @Override
    public void reload() {
        if(mMainAdapter.getItemCount() <
                (mImageRepository.countImageData() - OnMainRecyclerViewListener.LIST_THRESHOLD)) {
            loadData(mImageRepository.getPagedImageData(mMainAdapter.getItemCount()));
        } else {
            mMainView.showProgress();
            mShutterStockPresenter.loadData(this,
                    mImageRepository.getCurrentPage(mMainAdapter.getItemCount()),
                    ShutterStockInterface.PARAMETER_CATEGORY_ALL);
        }
    }

    @Override
    public void onDataLoaded(List<ImageData> imageDataList) {
        loadData(imageDataList);
    }

    @Override
    public void onDataError(String message) {
        mMainView.hideProgress();
    }

    @Override
    public void onCompleted() {

    }

    private void loadData(List<ImageData> imageDataList) {
        mMainAdapter.addDataSet(imageDataList);
        mMainAdapter.notifyDataSetChanged();
        mMainView.loadData();
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