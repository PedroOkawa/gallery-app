package com.okawa.pedro.galleryapp.di.module;

import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.presenter.details.DetailsPresenter;
import com.okawa.pedro.galleryapp.presenter.details.DetailsPresenterImpl;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.ui.details.DetailsView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pokawa on 23/11/15.
 */
@Module
public class DetailsModule {
    private DetailsView mDetailsView;

    public DetailsModule(DetailsView detailsView) {
        this.mDetailsView = detailsView;
    }

    @Provides
    public DetailsView provideMainView() {
        return mDetailsView;
    }

    @Provides
    public DetailsPresenter provideMainPresenter(DetailsView detailsView,
                                                 ImageRepository imageRepository,
                                                 ShutterStockPresenter shutterStockPresenter) {
        return new DetailsPresenterImpl(detailsView,
                imageRepository,
                shutterStockPresenter);
    }
}
