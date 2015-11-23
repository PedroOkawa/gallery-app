package com.okawa.pedro.galleryapp.ui.details;

import greendao.ImageData;

/**
 * Created by pokawa on 23/11/15.
 */
public interface DetailsView {

    void showProgress();
    void hideProgress();
    void loadData(ImageData imageData);

}
