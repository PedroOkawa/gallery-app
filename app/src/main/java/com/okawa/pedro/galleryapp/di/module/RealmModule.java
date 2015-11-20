package com.okawa.pedro.galleryapp.di.module;

import com.okawa.pedro.galleryapp.BuildConfig;
import com.okawa.pedro.galleryapp.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by pokawa on 20/11/15.
 */
@Module
public class RealmModule {

    private static final String DATABASE_NAME = "gallery.realm";

    @Singleton
    @Provides
    Realm provideRealm(App app) {
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(app);
        builder.name(DATABASE_NAME);
        if(BuildConfig.DEBUG) {
            builder.deleteRealmIfMigrationNeeded();
        }
        Realm.setDefaultConfiguration(builder.build());

        return Realm.getDefaultInstance();
    }
}
