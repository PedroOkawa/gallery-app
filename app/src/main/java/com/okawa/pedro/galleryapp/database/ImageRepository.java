package com.okawa.pedro.galleryapp.database;

import java.util.List;

import greendao.DaoSession;
import greendao.ImageData;
import greendao.ImageDataDao;

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

    public ImageData getImageDataById(long imageId) {
        return mDaoSession.getImageDataDao()
                /*

                SHUTTER STOCK API IS RETURNING REPEATED IMAGE IDS:
                E.G.: PAGED SEARCH 3 & 4 RETURNS THE SAME ID: 323097338

                https://api.shutterstock.com/v2/images/search?page=3
                https://api.shutterstock.com/v2/images/search?page=4

                */
                .queryBuilder().where(ImageDataDao.Properties.ImageId.eq(imageId)).unique();
    }

    public List<ImageData> getPagedImageData(int offset, String type) {
        if(!type.equals(ImageData.TYPE_ALL)) {
            return mDaoSession.getImageDataDao()
                    .queryBuilder().where(ImageDataDao.Properties.ImageType.eq(type))
                    .limit(SELECT_LIMIT).offset(offset).list();
        }
        return mDaoSession.getImageDataDao()
                .queryBuilder().limit(SELECT_LIMIT).offset(offset).list();
    }

    public long countImageData() {
        return mDaoSession.getImageDataDao().count();
    }

    public long countImageDataByType(String type) {
        if(type.equals(ImageData.TYPE_ALL)) {
            return mDaoSession.getImageDataDao().count();
        }
        return mDaoSession.getImageDataDao()
                .queryBuilder().where(ImageDataDao.Properties.ImageType.eq(type)).count();
    }

    public void clearImageData() {
        mDaoSession.getImageDataDao().deleteAll();
    }

    public long getCurrentPage(String type) {
        long total = countImageDataByType(type);
        if (total == 0) return 1;
        return (long) (Math.ceil(total / SELECT_LIMIT) + 1);
    }
}
