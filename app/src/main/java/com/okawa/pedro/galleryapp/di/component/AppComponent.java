package com.okawa.pedro.galleryapp.di.component;

import com.okawa.pedro.galleryapp.App;
import com.okawa.pedro.galleryapp.database.CategoryRepository;
import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.di.module.CategoryRepositoryModule;
import com.okawa.pedro.galleryapp.di.module.ImageRepositoryModule;
import com.okawa.pedro.galleryapp.di.module.ApiModule;
import com.okawa.pedro.galleryapp.di.module.AppModule;
import com.okawa.pedro.galleryapp.di.module.DatabaseModule;
import com.okawa.pedro.galleryapp.di.module.TransitionModule;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.util.manager.CallManager;
import com.okawa.pedro.galleryapp.util.manager.ParserManager;

import javax.inject.Singleton;

import dagger.Component;
import greendao.DaoSession;
import retrofit.Retrofit;

/**
 * Created by pokawa on 19/11/15.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        TransitionModule.class,
        DatabaseModule.class,
        ImageRepositoryModule.class,
        CategoryRepositoryModule.class
})
public interface AppComponent {
    void inject(App app);

    App provideApp();
    CallManager provideCallManager();
    Retrofit provideRetrofit();
    ShutterStockInterface provideShutterStockInterface();
    ShutterStockPresenter provideShutterStockPresenter();
    ParserManager provideParserManager();
    DaoSession provideDaoSession();
    ImageRepository provideImageRepository();
    CategoryRepository provideCategoryRepository();

}
