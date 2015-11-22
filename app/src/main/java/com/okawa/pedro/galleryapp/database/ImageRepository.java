package com.okawa.pedro.galleryapp.database;

import java.util.List;

import greendao.DaoSession;
import greendao.ImageData;

/**
 * Created by pokawa on 21/11/15.
 */
public class ImageRepository {
    private static final int SELECT_LIMIT = 20;

    private DaoSession mDaoSession;

    public ImageRepository(DaoSession daoSession) {
        this.mDaoSession = daoSession;
    }

    public void insertOrReplaceInTx(List<ImageData> ImageData) {
        mDaoSession.getImageDataDao().insertOrReplaceInTx(ImageData);
    }

    public void insertOrUpdate(ImageData ImageData) {
        mDaoSession.getImageDataDao().insertOrReplace(ImageData);
    }

    public List<ImageData> getPagedImageData(int offset) {
        return mDaoSession.getImageDataDao().queryBuilder().limit(SELECT_LIMIT).offset(offset).list();
    }

    public long countImageData() {
        return mDaoSession.getImageDataDao().count();
    }

    public int getCurrentPage(int itemCount) {
        if (itemCount == 0) return 1;
        return (itemCount / SELECT_LIMIT) + 1;
    }
}
