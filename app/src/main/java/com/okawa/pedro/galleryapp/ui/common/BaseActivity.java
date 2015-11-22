package com.okawa.pedro.galleryapp.ui.common;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.okawa.pedro.galleryapp.App;
import com.okawa.pedro.galleryapp.di.component.AppComponent;

/**
 * Created by pokawa on 19/11/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, layoutToInflate());
        doOnCreated(((App) getApplication()).getAppComponent(), savedInstanceState);
    }

    protected abstract @LayoutRes int layoutToInflate();
    protected abstract void doOnCreated(AppComponent appComponent, Bundle saveInstanceState);

    protected ViewDataBinding getDataBinding() {
        return binding;
    }
}
