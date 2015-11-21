package com.okawa.pedro.galleryapp.ui.main;

import android.os.Bundle;
import android.view.View;

import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.di.component.AppComponent;
import com.okawa.pedro.galleryapp.di.component.DaggerMainComponent;
import com.okawa.pedro.galleryapp.di.module.MainModule;
import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.presenter.main.MainPresenter;
import com.okawa.pedro.galleryapp.databinding.ActivityMainBinding;
import com.okawa.pedro.galleryapp.ui.common.BaseActivity;
import com.okawa.pedro.galleryapp.util.listener.OnViewTouchListener;
import com.okawa.pedro.galleryapp.util.adapter.main.MainImagesAdapter;
import com.okawa.pedro.galleryapp.util.manager.AutoGridLayoutManager;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by pokawa on 19/11/15.
 */
public class MainActivity extends BaseActivity implements OnViewTouchListener, MainView {

    private ActivityMainBinding mBinding;

    private MainImagesAdapter mMainImagesAdapter;

    @Inject
    MainPresenter mMainPresenter;

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize(AppComponent appComponent) {
        mBinding = (ActivityMainBinding) getDataBinding();

        Realm realm = Realm.getDefaultInstance();

        mMainImagesAdapter = new MainImagesAdapter(this, realm.where(Data.class).findAll());

        mBinding.rvMainActivityImages.setLayoutManager(new AutoGridLayoutManager(this));
        mBinding.rvMainActivityImages.setAdapter(mMainImagesAdapter);

        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this, mBinding.rvMainActivityImages))
                .build()
                .inject(this);
    }

    @Override
    protected void doOnCreated(Bundle saveInstanceState) {
        mMainPresenter.reload();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.onResume();
    }

    @Override
    public void onViewTouched(View view) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loadData() {

    }
}
