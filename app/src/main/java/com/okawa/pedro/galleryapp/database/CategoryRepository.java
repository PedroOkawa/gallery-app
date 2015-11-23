package com.okawa.pedro.galleryapp.database;

import java.util.List;

import greendao.CategoryData;
import greendao.CategoryDataDao;
import greendao.DaoSession;

/**
 * Created by pokawa on 21/11/15.
 */
public class CategoryRepository {
    private static int SELECT_LIMIT = 20;
    private DaoSession mDaoSession;

    public CategoryRepository(DaoSession daoSession) {
        this.mDaoSession = daoSession;
    }

    public void insertOrReplaceInTx(List<CategoryData> category) {
        mDaoSession.getCategoryDataDao().insertOrReplaceInTx(category);
    }

    public void clearCategoryData() {
        mDaoSession.getCategoryDataDao().deleteAll();
    }

    public void deleteCategoryDataForImageId(long imageId) {
        mDaoSession.getCategoryDataDao()
                .deleteInTx(mDaoSession.getCategoryDataDao().queryBuilder()
                        .where(CategoryDataDao.Properties.ImageId.eq(imageId)).list());
    }
}
