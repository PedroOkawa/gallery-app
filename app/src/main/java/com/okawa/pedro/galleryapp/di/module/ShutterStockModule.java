package com.okawa.pedro.galleryapp.di.module;

import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit.Retrofit;

/**
 * Created by pokawa on 19/11/15.
 */
@Module
public class ShutterStockModule {

    @Singleton
    @Provides
    public ShutterStockInterface provideShutterStockInterface(Retrofit retrofit) {
        return retrofit.create(ShutterStockInterface.class);
    }

    @Singleton
    @Provides
    public ShutterStockPresenter provideShutterStockPresenter(
            ShutterStockInterface shutterStockInterface,
            Realm realm) {
        return new ShutterStockPresenterImpl(shutterStockInterface, realm);
    }
}
