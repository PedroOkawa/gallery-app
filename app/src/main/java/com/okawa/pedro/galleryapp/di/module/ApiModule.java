package com.okawa.pedro.galleryapp.di.module;

import android.util.Base64;
import android.util.Log;

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

    private String mUsername;
    private String mPassword;

    public ApiModule(String username, String password) {
        this.mUsername = username;
        this.mPassword = password;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();

        client.setConnectTimeout(0, TimeUnit.MILLISECONDS);
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String credentials = (mUsername).concat(":").concat(mPassword);
                String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

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

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit
                .Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }
}
