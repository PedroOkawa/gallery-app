package com.okawa.pedro.galleryapp.util.listener;

/**
 * Created by pokawa on 20/11/15.
 */
public interface OnDataRequestListener {

    void onDataError();
    void requestData();
    void onCompleted();

}
