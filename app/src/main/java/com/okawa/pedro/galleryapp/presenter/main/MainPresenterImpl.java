package com.okawa.pedro.galleryapp.presenter.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.ui.main.MainView;
import com.okawa.pedro.galleryapp.util.adapter.main.MainAdapter;
import com.okawa.pedro.galleryapp.util.listener.OnDataRequestListener;
import com.okawa.pedro.galleryapp.util.listener.OnRecyclerViewThresholdListener;

import java.util.List;

import greendao.ImageData;

/**
 * Created by pokawa on 20/11/15.
 */
public class MainPresenterImpl implements MainPresenter, OnDataRequestListener {

    private MainView mMainView;
    private ImageRepository mImageRepository;
    private ShutterStockPresenter mShutterStockPresenter;
    private MainAdapter mMainAdapter;

    public MainPresenterImpl(MainView mainView,
                             ImageRepository imageRepository,
                             ShutterStockPresenter shutterStockPresenter,
                             RecyclerView recyclerView) {
        this.mMainView = mainView;
        this.mImageRepository = imageRepository;
        this.mShutterStockPresenter = shutterStockPresenter;
        defineRecyclerView(recyclerView);
    }

    private void defineRecyclerView(RecyclerView recyclerView) {
        mMainAdapter = (MainAdapter) recyclerView.getAdapter();
        recyclerView.addOnScrollListener(
                new OnMainRecyclerViewListener(
                        (GridLayoutManager) recyclerView.getLayoutManager()));
    }

    @Override
    public void reload() {
        if(mMainAdapter.getItemCount() <
                (mImageRepository.countImageData() - OnMainRecyclerViewListener.LIST_THRESHOLD)) {
            loadData(mImageRepository.getPagedImageData(mMainAdapter.getItemCount()));
        } else {
            mShutterStockPresenter.loadData(this,
                    mImageRepository.getCurrentPage(mMainAdapter.getItemCount()),
                    ShutterStockInterface.PARAMETER_CATEGORY_ALL);
        }
    }

    @Override
    public void onResume() {
        mMainView.showProgress();
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