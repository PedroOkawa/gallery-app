package com.okawa.pedro.galleryapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.okawa.pedro.galleryapp.GalleryApp;
import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.presenter.MainActivityPresenter;
import com.okawa.pedro.galleryapp.databinding.ActivityMainBinding;
import com.okawa.pedro.galleryapp.util.OnViewTouchListener;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by pokawa on 19/11/15.
 */
public class MainActivity extends BaseActivity implements OnViewTouchListener {

    private ActivityMainBinding mBinding;
    private MainActivityPresenter mMainActivityPresenter;

    @Inject
    ShutterStockInterface mShutterStockInterface;

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_main;
    }

    @Override
    protected void doOnCreated(Bundle saveInstanceState) {
        mBinding = (ActivityMainBinding) getDataBinding();
        mBinding.setViewTouchListener(this);

        ((GalleryApp)getApplication()).getShutterStockComponent().inject(this);
        mMainActivityPresenter = new MainActivityPresenter(this, mShutterStockInterface);
        mMainActivityPresenter.loadData();
    }

    @Override
    public void onViewTouched(View view) {

    }

    public void displayData(List<Data> dataList) {
        for(int i = 0 ; i < dataList.size(); i++) {
            Log.d("GALLERY APP", "TEST (" + i + "): " + dataList.get(i).getAssets().getPreview().getUrl());
        }
    }
}
