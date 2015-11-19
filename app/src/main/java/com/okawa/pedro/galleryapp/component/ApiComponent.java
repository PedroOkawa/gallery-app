package com.okawa.pedro.galleryapp.component;

import com.okawa.pedro.galleryapp.module.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by pokawa on 19/11/15.
 */
@Component(modules = ApiModule.class)
@Singleton
public interface ApiComponent {

    ApiModule.ShutterStockService provideShutterStockService();

}
