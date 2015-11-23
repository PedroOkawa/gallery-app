package com.okawa.pedro.galleryapp.presenter.shutterstock;

import com.okawa.pedro.galleryapp.database.CategoryRepository;
import com.okawa.pedro.galleryapp.database.ImageRepository;
import com.okawa.pedro.galleryapp.model.Categories;
import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.model.Response;
import com.okawa.pedro.galleryapp.network.ShutterStockInterface;
import com.okawa.pedro.galleryapp.util.listener.OnDataRequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final long INITIAL_PAGE = 1;
    private static final int TOTAL_RETRIES = 3;

    /*
     CREATED A DIFFERENT INDEX PAGE FOR ALL OBJECTS BECAUSE IT COULD NOT BE RELATED TO THE SIZE OF
     THE TABLE, BECAUSE OTHER TYPES COULD LOAD OBJECTS
     */
    private long mCurrentPageAllObjects = INITIAL_PAGE;
    private long mCurrentPage = INITIAL_PAGE;

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
    public void loadDataFromApi(final OnDataRequestListener onDataRequestListener, String type) {

        /*
         PARAMETERS USED TO LOAD IMAGES FROM SHUTTER STOCK API
         VIEW TYPE: ALWAYS FULL
         PAGE: CURRENT PAGE VARIABLE
         TYPE: PHOTO / ILLUSTRATION / PHOTO
         */
        Map<String, String> parameters = new HashMap<>();

        parameters.put(ShutterStockInterface.PARAMETER_VIEW, ShutterStockInterface.PARAMETER_FULL_VALUE);
        parameters.put(ShutterStockInterface.PARAMETER_PAGE, String.valueOf(mCurrentPage++));
        if(!type.equals(ImageData.TYPE_ALL)) {
            parameters.put(ShutterStockInterface.PARAMETER_TYPE, type);
        } else {
            /* KEEP THE ALL OBJECTS INDEX PAGE UPDATED */
            mCurrentPageAllObjects = mCurrentPage;
        }

        /* REQUESTS THE IMAGE'S DATA */
        mShutterStockInterface
                .imageList(parameters)
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
                 IS LOADED
                 */
                .toList()
                /* SENDS THE DATA TO THE MAIN PRESENTER */
                .subscribe(new Observer<List<ImageData>>() {

                    private boolean recall = false;

                    @Override
                    public void onCompleted() {
                        if(recall) {
                            /* RECALLS THE SERVICE INSIDE THE SAME THREAD */
                            onDataRequestListener.onRecall();
                        } else {
                            onDataRequestListener.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDataRequestListener.onDataError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ImageData> imageDataSet) {
                        long previousTotal = mImageRepository.countImageData();
                        /* PERSIST DATA ON DATABASE TO REQUEST AFTER COMPLETED */
                        mImageRepository.insertOrReplaceInTx(imageDataSet);
                        long currentTotal = mImageRepository.countImageData();

                        /*
                         CHECK IF WAS ADDED NEW RECORDS
                         BECAUSE IT'S POSSIBLE LOAD AN ALREADY RECORDED PAGE CONSIDERING THE FILTERS
                         IN THAT CASE MAKES ANOTHER SEARCH
                        */
                        if(previousTotal == currentTotal) {
                            recall = true;
                        }
                    }
                });
    }

    @Override
    public void definePageSearch(String type, long page) {
        if(type.equals(ImageData.TYPE_ALL)) {
            this.mCurrentPage = mCurrentPageAllObjects;
        } else {
            this.mCurrentPage = page;
        }
    }

    @Override
    public void resetPageSearch() {
        mCurrentPage = INITIAL_PAGE;
        mCurrentPageAllObjects = INITIAL_PAGE;
    }

    /* PARSE DATA AND PERSIST THE CATEGORY ON DATABASE */
    private ImageData parseData(Data data) {
        /* PERSIST CATEGORY ON DATABASE */
        new Thread(new CategoryPersistence(data)).start();

        /* PARSE DATA RETRIEVED TO IMAGE DATA */
        ImageData imageData = new ImageData();

        imageData.setImageId(data.getId());
        imageData.setDescription(data.getDescription());
        imageData.setImageType(data.getImage_type());
        imageData.setContributor(data.getContributor().getDisplay_name());
        imageData.setImageURL(data.getAssets().getPreview().getUrl());

        return imageData;
    }

    /*
     THREAD CREATED TO SAVE THE IMAGE'S CATEGORIES INTO THE DATABASE
     */
    public class CategoryPersistence implements Runnable {

        private Data mData;

        public CategoryPersistence(Data data) {
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
