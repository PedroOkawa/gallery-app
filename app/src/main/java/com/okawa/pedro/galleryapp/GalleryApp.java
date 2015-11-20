package com.okawa.pedro.galleryapp;

import android.app.Application;

import com.okawa.pedro.galleryapp.di.component.ApiComponent;
import com.okawa.pedro.galleryapp.di.component.DaggerApiComponent;
import com.okawa.pedro.galleryapp.di.component.DaggerShutterStockComponent;
import com.okawa.pedro.galleryapp.di.component.ShutterStockComponent;
import com.okawa.pedro.galleryapp.di.module.ApiModule;
import com.okawa.pedro.galleryapp.di.module.GalleryAppModule;
import com.okawa.pedro.galleryapp.di.module.ShutterStockModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by pokawa on 19/11/15.
 */
public class GalleryApp extends Application {

    private ApiComponent mApiComponent;
    private ShutterStockComponent mShutterStockComponent;
    private String username;
    private String password;

    @Override
    public void onCreate() {
        super.onCreate();
        retrieveApiCredentials();
        defineComponents();
    }

    private void defineComponents() {
        mApiComponent = DaggerApiComponent
                .builder()
                .galleryAppModule(new GalleryAppModule(this))
                .apiModule(new ApiModule(username, password))
                .build();

        mShutterStockComponent = DaggerShutterStockComponent
                .builder()
                .apiComponent(mApiComponent)
                .shutterStockModule(new ShutterStockModule())
                .build();
    }

    private void retrieveApiCredentials() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getAssets().open("api.properties");
            properties.load(inputStream);

            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }

    public ShutterStockComponent getShutterStockComponent() {
        return mShutterStockComponent;
    }
}
