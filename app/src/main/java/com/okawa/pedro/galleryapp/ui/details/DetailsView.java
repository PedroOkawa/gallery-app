package com.okawa.pedro.galleryapp.ui.details;

import greendao.ImageData;

/**
 * Created by pokawa on 23/11/15.
 */
public interface DetailsView {

    void showProgress();
    void hideProgress();
    void changeStatusBarColor(int color);
    void onError();
    void loadImageData(ImageData imageData);
    void loadContributorsData(String contributor);

}
