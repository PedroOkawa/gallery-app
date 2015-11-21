package com.okawa.pedro.galleryapp.presenter.main;

import android.support.v7.widget.RecyclerView;

import com.okawa.pedro.galleryapp.App;
import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.ui.main.MainView;
import com.okawa.pedro.galleryapp.util.listener.OnDataRequestListener;

import java.util.List;

/**
 * Created by pokawa on 20/11/15.
 */
public class MainPresenterImpl implements MainPresenter, OnDataRequestListener {

    private MainView mMainView;
    private RecyclerView mRecyclerView;
    private ShutterStockPresenter mShutterStockPresenter;

    public MainPresenterImpl(MainView mainView,
                             RecyclerView recyclerView,
                             ShutterStockPresenter shutterStockPresenter) {
        this.mMainView = mainView;
        this.mRecyclerView = recyclerView;
        this.mShutterStockPresenter = shutterStockPresenter;
    }

    @Override
    public void reload() {
        mShutterStockPresenter.loadData(this, 1, ShutterStockInterface.PARAMETER_CATEGORY_ALL);
    }

    @Override
    public void onResume() {
        mMainView.showProgress();
    }

    @Override
    public void onDataLoaded(List<Data> dataList) {
        mMainView.loadData();
        mMainView.hideProgress();
    }

    @Override
    public void onDataError(String message) {
        mMainView.showProgress();
    }

    @Override
    public void onCompleted() {

    }
}