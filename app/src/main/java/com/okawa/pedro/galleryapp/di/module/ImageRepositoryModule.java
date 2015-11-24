package com.okawa.pedro.galleryapp.di.module;

import com.okawa.pedro.galleryapp.database.ImageRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;

/**
 * Created by pokawa on 21/11/15.
 */
@Module
public class ImageRepositoryModule {

    @Singleton
    @Provides
    public ImageRepository provideImageRepository(DaoSession daoSession) {
        return new ImageRepository(daoSession);
    }
}