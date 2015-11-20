package com.okawa.pedro.galleryapp.presenter;

import android.util.Log;

import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.model.Response;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.ui.main.MainView;

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
public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;
    private Realm mRealm;
    private ShutterStockInterface mShutterStockInterface;

    public MainPresenterImpl(
            MainView mainView,
            Realm realm,
            ShutterStockInterface shutterStockInterface) {
        this.mMainView = mainView;
        this.mRealm = realm;
        this.mShutterStockInterface = shutterStockInterface;
    }

    @Override
    public void onResume() {
        mMainView.showProgress();
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
                        Log.d("GALLERY_APP", "COMPLETED");
                        mMainView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*
                            INCLUDE CRASHLYTICS (FABRIC)
                         */
                        Log.d("GALLERY_APP", "ERROR: " + e.getMessage());
                        mMainView.hideProgress();
                    }

                    @Override
                    public void onNext(List<Data> data) {
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(data);
                        mRealm.commitTransaction();

                        Log.d("GALLERY_APP", "REALM: " + mRealm.where(Data.class).findAll().size());

                        mRealm.close();

                        mMainView.loadData(data);
                    }
                });
    }
}