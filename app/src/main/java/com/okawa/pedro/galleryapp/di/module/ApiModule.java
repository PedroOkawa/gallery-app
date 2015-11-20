package com.okawa.pedro.galleryapp.di.module;

import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.okawa.pedro.galleryapp.App;
import com.okawa.pedro.galleryapp.BuildConfig;
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
import io.realm.RealmObject;
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

            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            /*
                INCLUDE CRASHLYTICS (FABRIC)
             */
            e.printStackTrace();
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

                Response response = chain.proceed(request);

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
    GsonConverterFactory provideGsonConverterFactory() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit
                .Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .baseUrl(BASE_URL)
                .build();
    }
}
