package com.okawa.pedro.galleryapp.presenter.shutterstock;

import android.util.Log;

import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.model.Response;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.util.OnDataRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pokawa on 20/11/15.
 */
public class ShutterStockPresenterImpl implements ShutterStockPresenter {

    private ShutterStockInterface mShutterStockInterface;
    private Realm mRealm;

    public ShutterStockPresenterImpl(
            ShutterStockInterface shutterStockInterface,
            Realm realm) {
        this.mShutterStockInterface = shutterStockInterface;
        this.mRealm = realm;
    }

    @Override
    public void loadData(final OnDataRequest onDataRequest) {
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
                        Log.d("GALLERY_APP", "COMPLETED");
                        onDataRequest.onDataCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*
                            INCLUDE CRASHLYTICS (FABRIC)
                         */
                        Log.d("GALLERY_APP", "ERROR: " + e.getMessage());
                        onDataRequest.onDataError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Data> data) {
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(data);
                        mRealm.commitTransaction();

                        Log.d("GALLERY_APP", "REALM: " + mRealm.where(Data.class).findAll().size());

                        mRealm.close();

                        onDataRequest.onDataLoaded(data);
                    }
                });
    }
}
