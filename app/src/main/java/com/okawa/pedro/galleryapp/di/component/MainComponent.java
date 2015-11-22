package com.okawa.pedro.galleryapp.di.component;

import android.support.v7.widget.RecyclerView;

import com.okawa.pedro.galleryapp.di.module.MainModule;
import com.okawa.pedro.galleryapp.di.scope.Activity;
import com.okawa.pedro.galleryapp.presenter.main.MainPresenter;
import com.okawa.pedro.galleryapp.ui.main.MainActivity;
import com.okawa.pedro.galleryapp.util.listener.OnRecyclerViewThresholdListener;

import dagger.Component;

/**
 * Created by pokawa on 20/11/15.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);

    MainPresenter provideMainPresenter();
    RecyclerView provideRecyclerView();

}
