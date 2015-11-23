package com.okawa.pedro.galleryapp.ui.main;

import android.content.Intent;
import android.support.v4.util.Pair;
import android.view.View;

/**
 * Created by pokawa on 20/11/15.
 */
public interface MainView {

    void showProgress();
    void hideProgress();
    void closeNavigation();
    void openDetails(long imageId, Pair<View, String>... params);
    void openGithub(Intent intent);

}
