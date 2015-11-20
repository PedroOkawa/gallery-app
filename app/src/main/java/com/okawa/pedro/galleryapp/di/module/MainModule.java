package com.okawa.pedro.galleryapp.di.module;

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

    public MainModule(MainView mainView) {
        this.mMainView = mainView;
    }

    @Provides
    MainView provideMainView() {
        return mMainView;
    }

    @Provides
    public MainPresenter provideMainPresenter(MainView mainView,
                                              ShutterStockPresenter shutterStockPresenter) {
        return new MainPresenterImpl(mainView, shutterStockPresenter);
    }

}
