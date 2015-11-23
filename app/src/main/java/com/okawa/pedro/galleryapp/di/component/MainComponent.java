package com.okawa.pedro.galleryapp.di.component;

import com.okawa.pedro.galleryapp.di.module.MainModule;
import com.okawa.pedro.galleryapp.di.scope.Activity;
import com.okawa.pedro.galleryapp.presenter.main.MainPresenter;
import com.okawa.pedro.galleryapp.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by pokawa on 20/11/15.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);

    MainPresenter provideMainPresenter();

}
