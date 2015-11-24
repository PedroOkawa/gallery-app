package com.okawa.pedro.galleryapp.presenter.details;

import android.content.Context;
import android.databinding.ViewDataBinding;

/**
 * Created by pokawa on 23/11/15.
 */
public interface DetailsPresenter {

    void defineViewsBehaviour(Context context, ViewDataBinding viewDataBinding, long imageId);

}
