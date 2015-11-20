package com.okawa.pedro.galleryapp.di.module;

import com.okawa.pedro.galleryapp.GalleryApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pokawa on 19/11/15.
 */
@Module
public class GalleryAppModule {

    GalleryApp mGalleryApp;

    public GalleryAppModule(GalleryApp galleryApp) {
        this.mGalleryApp = galleryApp;
    }

    @Provides
    @Singleton
    GalleryApp provideApp() {
        return mGalleryApp;
    }
}
