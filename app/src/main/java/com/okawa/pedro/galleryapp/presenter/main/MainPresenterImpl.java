package com.okawa.pedro.galleryapp.presenter.main;

import android.util.Log;

import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.ui.main.MainView;
import com.okawa.pedro.galleryapp.util.OnDataRequest;

import java.util.List;

/**
 * Created by pokawa on 20/11/15.
 */
public class MainPresenterImpl implements MainPresenter, OnDataRequest {

    private MainView mMainView;
    private ShutterStockPresenter mShutterStockPresenter;

    public MainPresenterImpl(MainView mainView, ShutterStockPresenter shutterStockPresenter) {
        this.mMainView = mainView;
        this.mShutterStockPresenter = shutterStockPresenter;
    }

    @Override
    public void reload() {
        mShutterStockPresenter.loadData(this, 1, "");
    }

    @Override
    public void onResume() {
        mMainView.showProgress();
    }

    @Override
    public void onDataLoaded(List<Data> dataList) {
        mMainView.loadData(dataList);
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