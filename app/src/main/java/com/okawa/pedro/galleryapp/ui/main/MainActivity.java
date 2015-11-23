package com.okawa.pedro.galleryapp.ui.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.di.component.AppComponent;
import com.okawa.pedro.galleryapp.di.component.DaggerMainComponent;
import com.okawa.pedro.galleryapp.di.module.MainModule;
import com.okawa.pedro.galleryapp.presenter.main.MainPresenter;
import com.okawa.pedro.galleryapp.databinding.ActivityMainBinding;
import com.okawa.pedro.galleryapp.ui.common.BaseActivity;
import com.okawa.pedro.galleryapp.util.adapter.main.TypeAdapter;
import com.okawa.pedro.galleryapp.util.adapter.main.ImageAdapter;
import com.okawa.pedro.galleryapp.util.manager.AutoGridLayoutManager;
import com.okawa.pedro.galleryapp.util.manager.CallManager;

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
    CallManager mCallManager;

    @Inject
    MainPresenter mMainPresenter;

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_main;
    }

    @Override
    protected void doOnCreated(AppComponent appComponent, Bundle saveInstanceState) {
        mBinding = (ActivityMainBinding) getDataBinding();

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mBinding.srActivityMainImages.setColorSchemeResources(
                R.color.color_primary,
                R.color.color_primary_dark,
                R.color.color_accent);

        mAutoGridLayoutManager = new AutoGridLayoutManager(this);
        mBinding.rvActivityMainImages.setLayoutManager(mAutoGridLayoutManager);
        mBinding.rvActivityMainImages.setAdapter(new ImageAdapter(this, new ArrayList<ImageData>()));

        ArrayList<String> types = new ArrayList<>();
        types.add(ImageData.TYPE_ALL);
        types.add(ImageData.TYPE_PHOTO);
        types.add(ImageData.TYPE_ILLUSTRATION);
        types.add(ImageData.TYPE_VECTOR);

        mBinding.nvLayout.rvNavigationView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.nvLayout.rvNavigationView.setAdapter(new TypeAdapter(this, types));

        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);

        mMainPresenter.defineViewsBehaviour(mBinding);
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
    public void closeNavigation() {
        mBinding.drawerLayout.closeDrawers();
    }

    @Override
    public void openDetails(long imageId, Pair<View, String> ... params) {
        mCallManager.startDetailsActivity(this, imageId, params);
    }

    @Override
    public void openGithub(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mAutoGridLayoutManager.changeColumnsNumber(newConfig.orientation);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            mBinding.drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
