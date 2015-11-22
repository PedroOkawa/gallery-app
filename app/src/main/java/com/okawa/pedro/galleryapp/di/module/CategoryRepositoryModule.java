package com.okawa.pedro.galleryapp.di.module;

import com.okawa.pedro.galleryapp.database.CategoryRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;

/**
 * Created by pokawa on 21/11/15.
 */
@Module
public class CategoryRepositoryModule {

    @Singleton
    @Provides
    public CategoryRepository provideCategoryRepository(DaoSession daoSession) {
        return new CategoryRepository(daoSession);
    }
}
