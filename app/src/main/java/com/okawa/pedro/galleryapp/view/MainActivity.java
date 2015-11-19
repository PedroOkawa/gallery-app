package com.okawa.pedro.galleryapp.view;

import android.os.Bundle;
import android.view.View;

import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.component.ApiComponent;
import com.okawa.pedro.galleryapp.component.DaggerApiComponent;
import com.okawa.pedro.galleryapp.util.OnViewTouched;
import com.okawa.pedro.galleryapp.databinding.ActivityMainBinding;
import com.okawa.pedro.galleryapp.module.ApiModule;


/**
 * Created by pokawa on 19/11/15.
 */
public class MainActivity extends BaseActivity implements OnViewTouched {

    private ApiComponent component;
    private ActivityMainBinding binding;

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_main;
    }

    @Override
    protected void doOnCreated(Bundle saveInstanceState) {
        component = DaggerApiComponent.builder().apiModule(new ApiModule()).build();
        binding = (ActivityMainBinding) getDataBinding();
        binding.setTouchListener(this);
    }

    @Override
    public void onViewTouched(View view) {

    }
}
