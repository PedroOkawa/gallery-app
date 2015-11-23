package com.okawa.pedro.galleryapp.di.component;

import com.okawa.pedro.galleryapp.di.module.DetailsModule;
import com.okawa.pedro.galleryapp.di.scope.Activity;
import com.okawa.pedro.galleryapp.presenter.details.DetailsPresenter;
import com.okawa.pedro.galleryapp.ui.details.DetailsActivity;

import dagger.Component;

/**
 * Created by pokawa on 23/11/15.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = DetailsModule.class)
public interface DetailsComponent {

    void inject(DetailsActivity detailsActivity);

    DetailsPresenter provideDetailsPresenter();

}
