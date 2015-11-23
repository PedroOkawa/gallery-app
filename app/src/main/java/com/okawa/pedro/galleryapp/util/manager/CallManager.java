package com.okawa.pedro.galleryapp.util.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.okawa.pedro.galleryapp.ui.details.DetailsActivity;

/**
 * Created by pokawa on 23/11/15.
 */
public class CallManager {
    public static final String IMAGE = "image";
    public static final String DETAILS = "details";

    public static final String IMAGE_ID = "imageID";
    public static Intent detailsIntent(Context context, long imageId) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(IMAGE_ID, imageId);
        return intent;
    }

    public void startDetailsActivity(Activity activity,
                                     long imageId, Pair<View, String> ... params) {

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, params);
        ActivityCompat.startActivity(activity, detailsIntent(activity, imageId),
                options.toBundle());

    }
}