package com.okawa.pedro.galleryapp.di.module;

import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.crashlytics.android.Crashlytics;
import com.okawa.pedro.galleryapp.App;
import com.okawa.pedro.galleryapp.BuildConfig;
import com.okawa.pedro.galleryapp.database.CategoryRepository;
import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenterImpl;
import com.okawa.pedro.galleryapp.util.manager.ParserManager;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by pokawa on 19/11/15.
 */
@Module
public class ApiModule {
    private static final String PREFIX = "https://";
    private static final String URL = "api.shutterstock.com/";
    private static final String VERSION = "v2/";
    private static final String BASE_URL = PREFIX.concat(URL).concat(VERSION);

    @Singleton
    @Provides
    Pair<String, String> provideCredentials(App app) {
        String username = "";
        String password = "";

        Properties properties = new Properties();
        try {
            InputStream inputStream = app.getAssets().open("api.properties");
            properties.load(inputStream);

            username = new String(
                    Base64.decode(properties.getProperty("username").getBytes("UTF-8"),
                            Base64.DEFAULT), "UTF-8");
            password = new String(
                    Base64.decode(properties.getProperty("password").getBytes("UTF-8"),
                            Base64.DEFAULT), "UTF-8");
        } catch (IOException e) {
            Crashlytics.log(e.getMessage());
        }

        return new Pair<>(username, password);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(final Pair<String, String> credentials) {
        OkHttpClient client = new OkHttpClient();

        client.setConnectTimeout(0, TimeUnit.MILLISECONDS);
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String basic = "Basic " +
                        Base64
                        .encodeToString((credentials.first)
                        .concat(":")
                        .concat(credentials.second)
                        .getBytes(), Base64.NO_WRAP);

                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", basic)
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build();

                /* RETRY CALL FOR 3 TIMES */

                Response response = chain.proceed(request);
                int tryCount = 0;
                while (!response.isSuccessful() && tryCount < 3) {
                    tryCount++;
                    response = chain.proceed(request);
                }

                if (BuildConfig.DEBUG) {
                    String bodyString = response.body().string();
                    Log.d("api", String.format("Sending request %s with headers %s", original.url(), original.headers()));
                    Log.d("api", String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), bodyString, response.headers()));
                    response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();
                }

                return response;
            }
        });
        return client;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit
                .Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    @Singleton
    @Provides
    public ShutterStockInterface provideShutterStockInterface(Retrofit retrofit) {
        return retrofit.create(ShutterStockInterface.class);
    }

    @Singleton
    @Provides
    public ShutterStockPresenter provideShutterStockPresenter(
            ShutterStockInterface shutterStockInterface,
            ParserManager parserManager,
            ImageRepository imageRepository,
            CategoryRepository categoryRepository) {
        return new ShutterStockPresenterImpl(shutterStockInterface,
                parserManager,
                imageRepository,
                categoryRepository);
    }

    @Singleton
    @Provides
    public ParserManager provideParserModule() {
        return new ParserManager();
    }
}
