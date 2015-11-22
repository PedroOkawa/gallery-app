package com.okawa.pedro.galleryapp.ui.main;

import android.content.Intent;

/**
 * Created by pokawa on 20/11/15.
 */
public interface MainView {

    void showProgress();
    void hideProgress();
    void loadData();
    void openGithub(Intent intent);

}
