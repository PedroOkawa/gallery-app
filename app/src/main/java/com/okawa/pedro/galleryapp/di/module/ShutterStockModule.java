package com.okawa.pedro.galleryapp.di.module;

import com.okawa.pedro.galleryapp.di.scope.UserScope;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * Created by pokawa on 19/11/15.
 */
@Module
public class ShutterStockModule {

    @UserScope
    @Provides
    ShutterStockInterface provideShutterStockService(Retrofit retrofit) {
        return retrofit.create(ShutterStockInterface.class);
    }
}
