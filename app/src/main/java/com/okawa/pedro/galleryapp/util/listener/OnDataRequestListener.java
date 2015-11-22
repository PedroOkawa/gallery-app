package com.okawa.pedro.galleryapp.util.listener;

import java.util.List;

import greendao.ImageData;

/**
 * Created by pokawa on 20/11/15.
 */
public interface OnDataRequestListener {

    void onDataLoaded(List<ImageData> dataSet);
    void onDataError(String message);
    void onCompleted();

}
