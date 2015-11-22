package com.okawa.pedro.galleryapp.presenter.shutterstock;

import com.okawa.pedro.galleryapp.database.CategoryRepository;
import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.model.Categories;
import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.model.Response;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.util.listener.OnDataRequestListener;

import java.util.ArrayList;
import java.util.List;

import greendao.CategoryData;
import greendao.ImageData;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by pokawa on 20/11/15.
 */
public class ShutterStockPresenterImpl implements ShutterStockPresenter {

    private static final int TOTAL_RETRIES = 3;

    private ShutterStockInterface mShutterStockInterface;
    private ImageRepository mImageRepository;
    private CategoryRepository mCategoryRepository;

    public ShutterStockPresenterImpl(ShutterStockInterface shutterStockInterface,
                                     ImageRepository imageRepository,
                                     CategoryRepository categoryRepository) {
        this.mShutterStockInterface = shutterStockInterface;
        this.mImageRepository = imageRepository;
        this.mCategoryRepository = categoryRepository;
    }

    @Override
    public void loadData(final OnDataRequestListener onDataRequestListener, long page, long categoryId) {
        /* REQUESTS THE IMAGE'S DATA */
        mShutterStockInterface
                .imageList(ShutterStockInterface.PARAMETER_FULL_VALUE, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                /* SEPARATES THE IMAGES LIST FROM THE RESPONSE */
                .flatMap(new Func1<Response, Observable<Data>>() {
                    @Override
                    public Observable<Data> call(Response responseParser) {
                        return Observable.from(responseParser.getDataParser());
                    }
                })
                /* TRANSFORMS THE DATA INTO AN IMAGE DATA OBSERVABLE */
                .flatMap(new Func1<Data, Observable<ImageData>>() {
                    @Override
                    public Observable<ImageData> call(Data data) {
                        return Observable.just(parseData(data));
                    }
                })
                /* DEFINE RETRY (3 TIMES) */
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer attempts, Throwable throwable) {
                        return attempts <= TOTAL_RETRIES;
                    }
                })
                /*
                 TRANSFORMS THE DATA INTO A LIST TO RECEIVE THE ANSWER JUST WHEN THE ENTIRE LIST
                 ARE LOADED
                 */
                .toList()
                /* SENDS THE DATA TO THE MAIN PRESENTER */
                .subscribe(new Observer<List<ImageData>>() {
                    @Override
                    public void onCompleted() {
                        onDataRequestListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDataRequestListener.onDataError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ImageData> imageDataSet) {
                        onDataRequestListener.onDataLoaded(imageDataSet);
                        mImageRepository.insertOrReplaceInTx(imageDataSet);
                    }
                });
    }

    /* PARSE DATA AND PERSIST THE CATEGORY ON DATABASE */
    private ImageData parseData(Data data) {
        /* PERSIST CATEGORY ON DATABASE */
        new Thread(new CategoryPersistence(mCategoryRepository, data)).start();

        /* PARSE DATA RETRIEVED TO IMAGE DATA */
        ImageData imageData = new ImageData();

        imageData.setImageID(data.getId());
        imageData.setImageType(data.getImage_type());
        imageData.setContributor(data.getContributor().getDisplay_name());
        imageData.setImageURL(data.getAssets().getPreview().getUrl());

        return imageData;
    }

    /*
     THREAD CREATED TO SAVE THE IMAGE'S CATEGORIES INTO THE DATABASE
     */
    public class CategoryPersistence implements Runnable {

        private CategoryRepository mCategoryRepository;
        private Data mData;

        public CategoryPersistence(CategoryRepository categoryRepository, Data data) {
            this.mCategoryRepository = categoryRepository;
            this.mData = data;
        }

        @Override
        public void run() {
                List<CategoryData> categories = new ArrayList<>();
                for(Categories category : mData.getCategories()) {
                    CategoryData categoryData = new CategoryData();
                    categoryData.setCategoryId(category.getId());
                    categoryData.setName(category.getName());
                    categoryData.setImageId(mData.getId());
                    categories.add(categoryData);
                }

                mCategoryRepository.insertOrReplaceInTx(categories);
        }
    }
}
