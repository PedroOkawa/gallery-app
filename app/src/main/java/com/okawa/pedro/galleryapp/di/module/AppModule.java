package com.okawa.pedro.galleryapp.di.module;

import com.okawa.pedro.galleryapp.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pokawa on 19/11/15.
 */
@Module
public class AppModule {

    private App mApp;

    public AppModule(App app) {
        this.mApp = app;
    }

    @Singleton
    @Provides
    public App provideApp() {
        return mApp;
    }
}
