package com.okawa.pedro.galleryapp.ui.details;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.databinding.ActivityDetailsBinding;
import com.okawa.pedro.galleryapp.di.component.AppComponent;
import com.okawa.pedro.galleryapp.di.component.DaggerDetailsComponent;
import com.okawa.pedro.galleryapp.di.module.DetailsModule;
import com.okawa.pedro.galleryapp.presenter.details.DetailsPresenter;
import com.okawa.pedro.galleryapp.ui.common.BaseActivity;
import com.okawa.pedro.galleryapp.util.manager.CallManager;

import javax.inject.Inject;

import greendao.CategoryData;
import greendao.ImageData;

/**
 * Created by pokawa on 23/11/15.
 */
public class DetailsActivity extends BaseActivity implements DetailsView {

    private ActivityDetailsBinding mBinding;
    private ImageData mImageData;

    @Inject
    DetailsPresenter mDetailsPresenter;

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_details;
    }

    @Override
    protected void doOnCreated(AppComponent appComponent, Bundle saveInstanceState) {
        mBinding = (ActivityDetailsBinding) getDataBinding();

        /* DRAW OVER STATUS BAR */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }

        /* DEFINES TOOLBAR BACK BUTTON */

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        long imageId = getIntent().getLongExtra(CallManager.IMAGE_ID, -1);

        /* CREATES THE LINK TO THE TRANSITION ANIMATION */

        ViewCompat.setTransitionName(mBinding.ivViewImageCard, CallManager.IMAGE);
        ViewCompat.setTransitionName(mBinding.viewImageInfo.rlViewImageDetails, CallManager.DETAILS);

        /* INJECT DEPENDENCY (COMPONENT) */

        DaggerDetailsComponent
                .builder()
                .appComponent(appComponent)
                .detailsModule(new DetailsModule(this))
                .build()
                .inject(this);

        mDetailsPresenter.defineViewsBehaviour(this, mBinding, imageId);
    }

    @Override
    public void showProgress() {
        mBinding.setLoading(true);
    }

    @Override
    public void hideProgress() {
        mBinding.setLoading(false);
    }

    @Override
    public void changeStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(this, getString(R.string.connection_error_message), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void loadImageData(ImageData imageData) {
        this.mImageData = imageData;
        /* IMAGE */
        Glide.with(this)
                .load(imageData.getImageURL())
                .thumbnail(Glide.with(this).load(imageData.getImageURL()).centerCrop().dontAnimate())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .centerCrop()
                .into(mBinding.ivViewImageCard);

        /* ID */
        mBinding.viewImageInfo.tvImageId.setText(String.valueOf(imageData.getImageId()));

        /* CATEGORIES */
        String divider = "";
        StringBuffer categoriesBuffer = new StringBuffer()
                .append(getString(R.string.category_label))
                .append(" ");
        for(CategoryData categoryData : imageData.getCategoryDataList()) {
            categoriesBuffer.append(divider).append(categoryData.getName());
            divider = ", ";
        }

        mBinding.tvActivityDetailsCategories.setText(categoriesBuffer.toString());

        mBinding.setImageData(imageData);
        mBinding.viewImageInfo.setImageData(imageData);
    }

    @Override
    public void loadContributorsData(String contributor) {
        /* CONTRIBUTOR */
        StringBuffer contributorBuffer = new StringBuffer()
                .append(getString(R.string.contributor_label))
                .append(" ").append(contributor);
        mBinding.tvActivityDetailsContributor.setText(contributorBuffer.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_share:
                share();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* SHARE BUTTON ACTION */

    public void share() {
        if (mImageData != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TITLE, mImageData.getId());
            String message = mImageData.getDescription() + "\n" +
                    mImageData.getContributor() + "\n\n" + mImageData.getImageURL();
            intent.putExtra(Intent.EXTRA_TEXT, message);

            startActivity(Intent
                    .createChooser(intent, getString(R.string.activity_details_intent_share)));
        }
    }
}
