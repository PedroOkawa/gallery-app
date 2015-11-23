package com.okawa.pedro.galleryapp.presenter.details;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.IntegerRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.database.CategoryRepository;
import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.databinding.ActivityDetailsBinding;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.ui.details.DetailsView;

import greendao.ImageData;

/**
 * Created by pokawa on 23/11/15.
 */
public class DetailsPresenterImpl implements DetailsPresenter {

    private DetailsView mDetailsView;
    private ImageRepository mImageRepository;
    private CategoryRepository mCategoryRepository;
    private ShutterStockPresenter mShutterStockPresenter;

    public DetailsPresenterImpl(DetailsView detailsView, ImageRepository imageRepository,
                                CategoryRepository categoryRepository,
                                ShutterStockPresenter shutterStockPresenter) {

        this.mDetailsView = detailsView;
        this.mImageRepository = imageRepository;
        this.mCategoryRepository = categoryRepository;
        this.mShutterStockPresenter = shutterStockPresenter;
    }

    @Override
    public void defineViewsBehaviour(Context context, long imageId, ViewDataBinding viewDataBinding) {
        ActivityDetailsBinding activityDetailsBinding = (ActivityDetailsBinding) viewDataBinding;

        ImageData imageData = mImageRepository.getImageDataById(imageId);

        /* IMAGE */
        Glide.with(context)
                .load(imageData.getImageURL())
                .thumbnail(Glide.with(context).load(imageData.getImageURL()).centerCrop().dontAnimate())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .centerCrop()
                .into(activityDetailsBinding.viewImageDetails.ivViewImageCard);

        /* TYPE */
        @IntegerRes int iconResource = imageData.getImageType().equals(ImageData.TYPE_PHOTO) ?
                R.mipmap.ic_photo :
                imageData.getImageType().equals(ImageData.TYPE_ILLUSTRATION) ?
                        R.mipmap.ic_illustration:
                        R.mipmap.ic_vector;

        Glide.with(context)
                .load(iconResource)
                .thumbnail(Glide.with(context).load(iconResource).fitCenter())
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .into(activityDetailsBinding.viewImageDetails.ivViewImageType);

        /* ID */
        StringBuffer stringBuffer = new StringBuffer().append("ID: ").append(imageData.getImageId());
        activityDetailsBinding.viewImageDetails.tvViewImageId.setText(stringBuffer.toString());

        activityDetailsBinding.setImageData(imageData);
    }
}
