package com.okawa.pedro.galleryapp.util;

import com.okawa.pedro.galleryapp.model.Data;

import java.util.List;

/**
 * Created by pokawa on 20/11/15.
 */
public interface OnDataRequest {

    void onDataLoaded(List<Data> dataList);
    void onDataError(String message);
    void onCompleted();

}
