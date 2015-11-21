package com.okawa.pedro.galleryapp.presenter.shutterstock;

import android.util.Log;

import com.okawa.pedro.galleryapp.model.Contributor;
import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.model.Response;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.util.listener.OnDataRequestListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pokawa on 20/11/15.
 */
public class ShutterStockPresenterImpl implements ShutterStockPresenter {

    private ShutterStockInterface mShutterStockInterface;

    public ShutterStockPresenterImpl(ShutterStockInterface shutterStockInterface) {
        this.mShutterStockInterface = shutterStockInterface;
    }

    @Override
    public void loadData(final OnDataRequestListener onDataRequestListener, long page, long categoryId) {
        /*
         PARAMETERS TO REQUEST DATA
             # CURRENT PAGE: PARAMETER NAME:
                * PAGE (ShutterStockInterface.PARAMETER_PAGE)
             # CATEGORY SELECTED: PARAMETER NAME:
                * CATEGORY (ShutterStockInterface.PARAMETER_CATEGORY)
         */

        Map<String, Long> parameters = new HashMap<>();
        parameters.put(ShutterStockInterface.PARAMETER_PAGE, page);
        if(categoryId != ShutterStockInterface.PARAMETER_CATEGORY_ALL) {
            parameters.put(ShutterStockInterface.PARAMETER_CATEGORY, categoryId);
        }

        /* REQUESTS THE IMAGE'S DATA */
        mShutterStockInterface
                .imageList(parameters)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                /* SEPARATES THE IMAGES LIST FROM THE RESPONSE */
                .flatMap(new Func1<Response, Observable<Data>>() {
                    @Override
                    public Observable<Data> call(Response response) {
                        return Observable.from(response.getData());
                    }
                })
                /* COMBINES THE IMAGE'S DATA WITH CONTRIBUTOR'S DATA RETRIEVING FROM ANOTHER CALL */
                .flatMap(new Func1<Data, Observable<Data>>() {
                    @Override
                    public Observable<Data> call(final Data data) {
                        /* REQUESTS THE CONTRIBUTOR'S DATA */
                        return mShutterStockInterface
                                .contributorDetails(data.getContributor().getId())
                                .subscribeOn(Schedulers.newThread())
                                .flatMap(new Func1<Contributor, Observable<Data>>() {
                                    @Override
                                    public Observable<Data> call(Contributor contributor) {
                                        data.setContributor(contributor);
                                        return Observable.just(data);
                                    }
                                })
                                .doOnError(new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        onDataRequestListener.onDataError(throwable.getMessage());
                                    }
                                });
                    }
                })
                /*
                 TRANSFORMS THE DATA INTO A LIST TO RECEIVE THE ANSWER JUST WHEN THE ENTIRE LIST
                 ARE LOADED
                 */
                .toList()
                /* SENDS THE DATA TO THE MAIN PRESENTER */
                .subscribe(new Observer<List<Data>>() {
                    @Override
                    public void onCompleted() {
                        onDataRequestListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDataRequestListener.onDataError(e.getMessage());
                        Log.d("GALLERY_APP", "ERROR SECOND: " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Data> dataList) {
                        persistOnDatabase(dataList);
                        onDataRequestListener.onDataLoaded(dataList);
                    }
                });
    }

    private void persistOnDatabase(List<Data> dataList) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(dataList);
        realm.commitTransaction();
        realm.close();
    }
}
