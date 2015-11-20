package com.okawa.pedro.galleryapp.di.component;

import com.okawa.pedro.galleryapp.di.module.ApiModule;
import com.okawa.pedro.galleryapp.di.module.GalleryAppModule;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;

/**
 * Created by pokawa on 19/11/15.
 */
@Singleton
@Component(modules = {GalleryAppModule.class, ApiModule.class})
public interface ApiComponent {

    Retrofit retrofit();
    OkHttpClient okHttpClient();

}
