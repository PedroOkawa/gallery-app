package com.okawa.pedro.galleryapp;

import android.app.Application;

import com.okawa.pedro.galleryapp.di.component.AppComponent;
import com.okawa.pedro.galleryapp.di.component.DaggerAppComponent;
import com.okawa.pedro.galleryapp.di.module.AppModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by pokawa on 19/11/15.
 */
public class App extends Application {
    private static final String DATABASE_NAME = "gallery.realm";

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        defineComponent();
        defineDataBase();
    }

    private void defineComponent() {
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);
    }

    private void defineDataBase() {
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(this);
        builder.name(DATABASE_NAME);
        if(BuildConfig.DEBUG) {
            builder.deleteRealmIfMigrationNeeded();
        }
        Realm.setDefaultConfiguration(builder.build());
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
