package com.okawa.pedro.galleryapp.presenter.details;

import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.presenter.shutterstock.ShutterStockPresenter;
import com.okawa.pedro.galleryapp.ui.details.DetailsView;
import com.okawa.pedro.galleryapp.util.listener.OnDataRequestListener;

import greendao.ImageData;

/**
 * Created by pokawa on 23/11/15.
 */
public class DetailsPresenterImpl implements DetailsPresenter, OnDataRequestListener {

    private ImageData mImageData;

    private DetailsView mDetailsView;
    private ImageRepository mImageRepository;
    private ShutterStockPresenter mShutterStockPresenter;

    public DetailsPresenterImpl(DetailsView detailsView, ImageRepository imageRepository,
                                ShutterStockPresenter shutterStockPresenter) {

        this.mDetailsView = detailsView;
        this.mImageRepository = imageRepository;
        this.mShutterStockPresenter = shutterStockPresenter;
    }

    @Override
    public void defineViewsBehaviour(long imageId) {
        mDetailsView.showProgress();
        mImageData = mImageRepository.getImageDataById(imageId);

        mDetailsView.loadImageData(mImageData);
        requestData();
    }

    @Override
    public void onDataError(String message) {
        mDetailsView.onError(message);
    }

    @Override
    public void requestData() {
        mShutterStockPresenter.loadContributorData(this,
                mImageData.getImageId(), mImageData.getContributorId());
    }

    @Override
    public void onCompleted() {
        mDetailsView.loadContributorsData(mImageData.getContributor());
        mDetailsView.hideProgress();
    }
}
