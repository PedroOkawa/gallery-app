package com.okawa.pedro.galleryapp;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.okawa.pedro.galleryapp.di.component.AppComponent;
import com.okawa.pedro.galleryapp.di.component.DaggerAppComponent;
import com.okawa.pedro.galleryapp.di.module.AppModule;
import io.fabric.sdk.android.Fabric;

/**
 * Created by pokawa on 19/11/15.
 */
public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        defineComponent();
    }

    private void defineComponent() {
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
