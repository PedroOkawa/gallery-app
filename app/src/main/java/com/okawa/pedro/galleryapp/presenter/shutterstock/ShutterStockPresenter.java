package com.okawa.pedro.galleryapp.presenter.shutterstock;

import com.okawa.pedro.galleryapp.util.listener.OnDataRequestListener;

/**
 * Created by pokawa on 20/11/15.
 */
public interface ShutterStockPresenter {

    void loadDataFromApi(OnDataRequestListener onDataRequestListener, String type);
    void definePageSearch(String type, long page);
    void resetPageSearch();

}