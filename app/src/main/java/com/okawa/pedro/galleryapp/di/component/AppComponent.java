package com.okawa.pedro.galleryapp.di.component;

import com.okawa.pedro.galleryapp.App;
import com.okawa.pedro.galleryapp.di.module.ApiModule;
import com.okawa.pedro.galleryapp.di.module.AppModule;
import com.okawa.pedro.galleryapp.di.module.ShutterStockModule;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;

/**
 * Created by pokawa on 19/11/15.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        ShutterStockModule.class
})
public interface AppComponent {
    void inject(App app);

    App provideApp();
    Retrofit provideRetrofit();
    ShutterStockInterface provideShutterStockInterface();
    ShutterStockPresenter provideShutterStockPresenter();

}
