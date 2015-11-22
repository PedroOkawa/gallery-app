package com.okawa.pedro.galleryapp.di.module;

import android.support.v7.widget.RecyclerView;

import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.presenter.main.MainPresenter;
import com.okawa.pedro.galleryapp.presenter.main.MainPresenterImpl;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.ui.main.MainView;
import com.okawa.pedro.galleryapp.util.listener.OnRecyclerViewThresholdListener;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pokawa on 20/11/15.
 */
@Module
public class MainModule {
    private MainView mMainView;
    private RecyclerView mRecyclerView;

    public MainModule(MainView mainView, RecyclerView recyclerView) {
        this.mMainView = mainView;
        this.mRecyclerView = recyclerView;
    }

    @Provides
    public MainView provideMainView() {
        return mMainView;
    }

    @Provides
    public RecyclerView provideRecyclerView() {
        return mRecyclerView;
    }

    @Provides
    public MainPresenter provideMainPresenter(MainView mainView,
                                              ImageRepository imageRepository,
                                              ShutterStockPresenter shutterStockPresenter,
                                              RecyclerView recyclerView) {
        return new MainPresenterImpl(mainView,
                imageRepository,
                shutterStockPresenter,
                recyclerView);
    }
}
