package com.okawa.pedro.galleryapp.presenter;

import android.util.Log;

import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.model.Response;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.ui.MainActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pokawa on 20/11/15.
 */
public class MainActivityPresenter {

    private MainActivity mMainActivity;
    private ShutterStockInterface mShutterStockInterface;

    public MainActivityPresenter(
            MainActivity mainActivity,
            ShutterStockInterface shutterStockInterface) {
        this.mMainActivity = mainActivity;
        this.mShutterStockInterface = shutterStockInterface;
    }

    public void loadData() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(ShutterStockInterface.PARAMETER_PAGE, "1");

        mShutterStockInterface
                .imageList(parameters)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Func1<Response, Iterable<Data>>() {
                    @Override
                    public Iterable<Data> call(Response response) {
                        return response.getData();
                    }
                })
                .toList()
                .subscribe(new Observer<List<Data>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("GALLERY APP", "COMPLETED");
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*
                            INCLUDE CRASHLYTICS (FABRIC)
                         */
                        Log.d("GALLERY APP", "ERROR: " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Data> dataList) {
                        for(int i = 0 ; i < dataList.size(); i++) {
                            Log.d("GALLERY APP", "PRESENTER TEST (" + i + "): " + dataList.get(i).getAssets().getPreview().getUrl());
                        }
                        mMainActivity.displayData(dataList);
                    }
                });
    }
}