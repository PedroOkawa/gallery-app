package com.okawa.pedro.galleryapp.util.listener;

import com.okawa.pedro.galleryapp.model.Data;

import java.util.List;

/**
 * Created by pokawa on 20/11/15.
 */
public interface OnDataRequestListener {

    void onDataLoaded(List<Data> dataList);
    void onDataError(String message);
    void onCompleted();

}
