package com.okawa.pedro.galleryapp.di.module;

import android.databinding.ViewDataBinding;

import com.okawa.pedro.galleryapp.database.CategoryRepository;
import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.presenter.main.MainPresenter;
import com.okawa.pedro.galleryapp.presenter.main.MainPresenterImpl;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.ui.main.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pokawa on 20/11/15.
 */
@Module
public class MainModule {
    private MainView mMainView;
    private ViewDataBinding mViewDataBinding;

    public MainModule(MainView mainView, ViewDataBinding activityMainBinding) {
        this.mMainView = mainView;
        this.mViewDataBinding = activityMainBinding;
    }

    @Provides
    public MainView provideMainView() {
        return mMainView;
    }

    @Provides
    public ViewDataBinding provideActivityMainBinding() {
        return mViewDataBinding;
    }

    @Provides
    public MainPresenter provideMainPresenter(MainView mainView,
                                              ImageRepository imageRepository,
                                              CategoryRepository categoryRepository,
                                              ShutterStockPresenter shutterStockPresenter,
                                              ViewDataBinding viewDataBinding) {
        return new MainPresenterImpl(mainView,
                imageRepository,
                categoryRepository,
                shutterStockPresenter,
                viewDataBinding);
    }
}
