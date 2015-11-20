package com.okawa.pedro.galleryapp.ui.main;

import com.okawa.pedro.galleryapp.model.Data;

import java.util.List;

/**
 * Created by pokawa on 20/11/15.
 */
public interface MainView {

    void showProgress();
    void hideProgress();
    void loadData(List<Data> data);

}
