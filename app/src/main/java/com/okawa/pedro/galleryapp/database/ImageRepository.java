package com.okawa.pedro.galleryapp.database;

import java.util.List;

import greendao.DaoSession;
import greendao.ImageData;

/**
 * Created by pokawa on 21/11/15.
 */
public class ImageRepository {
    private static int SELECT_LIMIT = 20;
    private DaoSession mDaoSession;

    public ImageRepository(DaoSession daoSession) {
        this.mDaoSession = daoSession;
    }

    public void insertInTx(List<ImageData> ImageData) {
        mDaoSession.getImageDataDao().insertInTx(ImageData);
    }

    public void insertInTx(ImageData... ImageData) {
        mDaoSession.getImageDataDao().insertInTx(ImageData);
    }

    public void insertOrUpdate(ImageData ImageData) {
        mDaoSession.getImageDataDao().insertOrReplace(ImageData);
    }

    public void clearImageData() {
        mDaoSession.getImageDataDao().deleteAll();
    }

    public List<ImageData> getPagedImageData(int offset) {
        return mDaoSession.getImageDataDao().queryBuilder().limit(SELECT_LIMIT).offset(offset).list();
    }

    public List<ImageData> getAllImageData() {
        return mDaoSession.getImageDataDao().loadAll();
    }

    public ImageData getImageDataForId(long id) {
        return mDaoSession.getImageDataDao().load(id);
    }
}
