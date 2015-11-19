package com.okawa.pedro.galleryapp.module;

import com.okawa.pedro.galleryapp.model.Contributor;
import com.okawa.pedro.galleryapp.model.Response;

import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by pokawa on 19/11/15.
 */
@Module
public class ApiModule {

    private static final String PREFIX = "https://";
    private static final String URL = "api.shutterstock.com";
    private static final String VERSION = "/v2";
    private static final String BASE_URL = PREFIX.concat(URL).concat(VERSION);

    private static final String IMAGE_LIST = "/images/search";
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_CATEGORY = "category";

    private static final String CONTRIBUTOR_DETAILS = "/contributors/{contributor_id}";

    @Provides
    @Singleton
    ShutterStockService provideShutterStockService() {
        return new Retrofit
                .Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ShutterStockService.class);
    }

    public interface ShutterStockService {

        @GET(IMAGE_LIST)
        Observable<List<Response>> imageList(
                @QueryMap Map<String, String> parameters
        );

        @GET(CONTRIBUTOR_DETAILS)
        Observable<Contributor> contributorDetails(
                @Path("contributor_id") String contributor_id
        );
    }

}
