package com.okawa.pedro.galleryapp.di.module;

import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.presenter.MainPresenter;
import com.okawa.pedro.galleryapp.presenter.MainPresenterImpl;
import com.okawa.pedro.galleryapp.ui.main.MainView;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

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
                                              Realm realm,
                                              ShutterStockInterface shutterStockInterface) {
        return new MainPresenterImpl(mainView, realm, shutterStockInterface);
    }

}
