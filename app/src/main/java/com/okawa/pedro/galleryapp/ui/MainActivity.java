package com.okawa.pedro.galleryapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.okawa.pedro.galleryapp.GalleryApp;
import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.model.Response;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.util.OnViewTouched;
import com.okawa.pedro.galleryapp.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by pokawa on 19/11/15.
 */
public class MainActivity extends BaseActivity implements OnViewTouched {

    private ActivityMainBinding mBinding;

    @Inject
    ShutterStockInterface mShutterStockInterface;

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_main;
    }

    @Override
    protected void doOnCreated(Bundle saveInstanceState) {
        mBinding = (ActivityMainBinding) getDataBinding();
        mBinding.setTouchListener(this);

        ((GalleryApp)getApplication()).getShutterStockComponent().inject(this);

        Map<String, String> parameters = new HashMap<>();

        parameters.put(ShutterStockInterface.PARAMETER_PAGE, "1");

        mShutterStockInterface
                .imageList(parameters)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response>() {
                    @Override
                    public void call(Response response) {
                        int index = 0;
                        for(int i = 0; i < response.getData().size(); i++) {
                            Log.d("GALLERY TEST", "IMAGE (" + (index++) + "): " + response.getData().get(i).getAssets().getPreview().getUrl());
                        }
                    }
                });
    }

    @Override
    public void onViewTouched(View view) {

    }
}
