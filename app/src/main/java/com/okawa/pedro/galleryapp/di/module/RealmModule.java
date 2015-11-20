package com.okawa.pedro.galleryapp.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by pokawa on 20/11/15.
 */
@Module
public class RealmModule {

    private Realm mRealm;

    public RealmModule(Realm realm) {
        this.mRealm = realm;
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        return mRealm;
    }
}
