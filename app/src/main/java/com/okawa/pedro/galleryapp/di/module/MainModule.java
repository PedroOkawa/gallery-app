package com.okawa.pedro.galleryapp.di.module;

import com.okawa.pedro.galleryapp.database.CategoryRepository;
import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.presenter.main.MainPresenter;
import com.okawa.pedro.galleryapp.presenter.main.MainPresenterImpl;
import com.okawa.pedro.galleryapp.ui.main.MainView;
import com.okawa.pedro.galleryapp.util.manager.ShutterStockManager;

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
    public MainView provideMainView() {
        return mMainView;
    }

    @Provides
    public MainPresenter provideMainPresenter(MainView mainView,
                                              ImageRepository imageRepository,
                                              CategoryRepository categoryRepository,
                                              ShutterStockManager shutterStockManager) {
        return new MainPresenterImpl(mainView,
                imageRepository,
                categoryRepository,
                shutterStockManager);
    }
}
