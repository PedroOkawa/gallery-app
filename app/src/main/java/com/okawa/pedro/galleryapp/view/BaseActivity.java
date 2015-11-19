package com.okawa.pedro.galleryapp.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pokawa on 19/11/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, layoutToInflate());
        doOnCreated(savedInstanceState);
    }

    protected abstract int layoutToInflate();
    protected abstract void doOnCreated(Bundle saveInstanceState);

    protected ViewDataBinding getDataBinding() {
        return binding;
    }
}
