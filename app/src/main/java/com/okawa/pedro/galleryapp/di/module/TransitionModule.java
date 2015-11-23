package com.okawa.pedro.galleryapp.di.module;

import com.okawa.pedro.galleryapp.util.manager.CallManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pokawa on 23/11/15.
 */
@Module
public class TransitionModule {

    @Singleton
    @Provides
    public CallManager provideCallManager() {
        return new CallManager();
    }

}
