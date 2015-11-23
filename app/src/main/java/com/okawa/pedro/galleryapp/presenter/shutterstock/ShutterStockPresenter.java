package com.okawa.pedro.galleryapp.presenter.shutterstock;

import com.okawa.pedro.galleryapp.util.listener.OnDataRequestListener;

/**
 * Created by pokawa on 20/11/15.
 */
public interface ShutterStockPresenter {

    void loadImageData(OnDataRequestListener onDataRequestListener, String type);
    void loadContributorData(OnDataRequestListener onDataRequestListener,
                             long imageId, long contributorId);
    void definePageSearch(String type, long page);
    void resetPageSearch();

}