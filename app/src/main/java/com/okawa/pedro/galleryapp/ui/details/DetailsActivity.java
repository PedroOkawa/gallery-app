package com.okawa.pedro.galleryapp.ui.details;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.view.ViewCompat;
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

        mDetailsPresenter.defineViewsBehaviour(imageId);
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
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void loadImageData(ImageData imageData) {
        /* IMAGE */
        Glide.with(this)
                .load(imageData.getImageURL())
                .thumbnail(Glide.with(this).load(imageData.getImageURL()).centerCrop().dontAnimate())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .centerCrop()
                .into(mBinding.viewImageDetails.ivViewImageCard);

        /* TYPE */
        @IntegerRes int iconResource = imageData.getImageType().equals(ImageData.TYPE_PHOTO) ?
                R.mipmap.ic_photo :
                imageData.getImageType().equals(ImageData.TYPE_ILLUSTRATION) ?
                        R.mipmap.ic_illustration:
                        R.mipmap.ic_vector;

        Glide.with(this)
                .load(iconResource)
                .thumbnail(Glide.with(this).load(iconResource).fitCenter())
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .into(mBinding.viewImageDetails.ivViewImageType);

        /* ID */
        StringBuffer imageIdBuffer = new StringBuffer()
                .append(getString(R.string.id_label))
                .append(" ").append(imageData.getImageId());
        mBinding.viewImageDetails.tvViewImageId.setText(imageIdBuffer.toString());

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
    }

    @Override
    public void loadContributorsData(String contributor) {
        /* CONTRIBUTOR */
        StringBuffer contributorBuffer = new StringBuffer()
                .append(getString(R.string.contributor_label))
                .append(" ").append(contributor);
        mBinding.tvActivityDetailsContributor.setText(contributorBuffer.toString());
    }
}
