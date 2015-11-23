package com.okawa.pedro.galleryapp.util.listener;

import android.support.v4.util.Pair;
import android.view.View;

/**
 * Created by pokawa on 23/11/15.
 */
public interface OnImageTouchListener {

    void onImageTouched(long imageId, Pair<View, String> ... params);

}
