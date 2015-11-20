package com.okawa.pedro.galleryapp.presenter.main;

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
        mShutterStockPresenter.loadData(this);
    }

    @Override
    public void onResume() {
        mMainView.showProgress();
    }

    @Override
    public void onDataLoaded(List<Data> data) {
        mMainView.loadData(data);
    }

    @Override
    public void onDataError(String message) {
        mMainView.showProgress();
    }

    @Override
    public void onDataCompleted() {
        mMainView.showProgress();
    }
}