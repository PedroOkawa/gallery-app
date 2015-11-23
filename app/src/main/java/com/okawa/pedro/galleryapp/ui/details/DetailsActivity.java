package com.okawa.pedro.galleryapp.ui.details;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;

import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.databinding.ActivityDetailsBinding;
import com.okawa.pedro.galleryapp.di.component.AppComponent;
import com.okawa.pedro.galleryapp.di.component.DaggerDetailsComponent;
import com.okawa.pedro.galleryapp.di.module.DetailsModule;
import com.okawa.pedro.galleryapp.presenter.details.DetailsPresenter;
import com.okawa.pedro.galleryapp.ui.common.BaseActivity;
import com.okawa.pedro.galleryapp.util.manager.CallManager;

import javax.inject.Inject;

/**
 * Created by pokawa on 23/11/15.
 */
public class DetailsActivity extends BaseActivity implements DetailsView {

    private ActivityDetailsBinding mBinding;

    @Inject
    DetailsPresenter mDetailsPresenter;

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_details;
    }

    @Override
    protected void doOnCreated(AppComponent appComponent, Bundle saveInstanceState) {
        mBinding = (ActivityDetailsBinding) getDataBinding();

        long imageId = getIntent().getLongExtra(CallManager.IMAGE_ID, -1);

        ViewCompat.setTransitionName(mBinding
                .viewImageDetails.ivViewImageCard, CallManager.IMAGE);

        ViewCompat.setTransitionName(mBinding
                .viewImageDetails.llViewImageDetails, CallManager.DETAILS);


        DaggerDetailsComponent
                .builder()
                .appComponent(appComponent)
                .detailsModule(new DetailsModule(this))
                .build()
                .inject(this);

        mDetailsPresenter.defineViewsBehaviour(this, imageId, mBinding);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loadData() {

    }
}
