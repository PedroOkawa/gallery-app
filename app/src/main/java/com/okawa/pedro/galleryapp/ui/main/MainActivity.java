package com.okawa.pedro.galleryapp.ui.main;

import android.content.res.Configuration;
import android.os.Bundle;

import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.di.component.AppComponent;
import com.okawa.pedro.galleryapp.di.component.DaggerMainComponent;
import com.okawa.pedro.galleryapp.di.module.MainModule;
import com.okawa.pedro.galleryapp.presenter.main.MainPresenter;
import com.okawa.pedro.galleryapp.databinding.ActivityMainBinding;
import com.okawa.pedro.galleryapp.ui.common.BaseActivity;
import com.okawa.pedro.galleryapp.util.adapter.main.MainAdapter;
import com.okawa.pedro.galleryapp.util.manager.AutoGridLayoutManager;

import java.util.ArrayList;

import javax.inject.Inject;

import greendao.ImageData;

/**
 * Created by pokawa on 19/11/15.
 */
public class MainActivity extends BaseActivity implements MainView {

    private ActivityMainBinding mBinding;
    private AutoGridLayoutManager mAutoGridLayoutManager;

    @Inject
    MainPresenter mMainPresenter;

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_main;
    }

    @Override
    protected void doOnCreated(AppComponent appComponent, Bundle saveInstanceState) {
        mBinding = (ActivityMainBinding) getDataBinding();

        mBinding.srActivityMainImages.setColorSchemeResources(
                R.color.color_primary,
                R.color.color_primary_dark,
                R.color.color_accent);

        mAutoGridLayoutManager = new AutoGridLayoutManager(this);
        mBinding.rvActivityMainImages.setLayoutManager(mAutoGridLayoutManager);
        mBinding.rvActivityMainImages.setAdapter(new MainAdapter(this, new ArrayList<ImageData>()));

        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this, mBinding.rvActivityMainImages))
                .build()
                .inject(this);
    }

    @Override
    public void showProgress() {
        mBinding.srActivityMainImages.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mBinding.srActivityMainImages.setRefreshing(false);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mAutoGridLayoutManager.changeColumnsNumber(newConfig.orientation);
        super.onConfigurationChanged(newConfig);
    }
}
