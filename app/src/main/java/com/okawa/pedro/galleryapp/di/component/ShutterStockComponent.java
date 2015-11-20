package com.okawa.pedro.galleryapp.di.component;

import com.okawa.pedro.galleryapp.di.module.ShutterStockModule;
import com.okawa.pedro.galleryapp.di.scope.UserScope;
import com.okawa.pedro.galleryapp.ui.MainActivity;

import dagger.Component;

/**
 * Created by pokawa on 19/11/15.
 */
@UserScope
@Component(dependencies = ApiComponent.class, modules = ShutterStockModule.class)
public interface ShutterStockComponent {

    void inject(MainActivity mainActivity);

}
