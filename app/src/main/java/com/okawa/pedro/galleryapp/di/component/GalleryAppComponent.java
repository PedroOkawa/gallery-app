package com.okawa.pedro.galleryapp.di.component;

import com.okawa.pedro.galleryapp.di.module.GalleryAppModule;
import com.okawa.pedro.galleryapp.ui.BaseActivity;

import dagger.Component;

/**
 * Created by pokawa on 19/11/15.
 */
@Component(modules = GalleryAppModule.class)
public interface GalleryAppComponent {

    void inject(BaseActivity baseActivity);

}
