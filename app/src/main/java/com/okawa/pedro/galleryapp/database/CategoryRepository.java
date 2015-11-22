package com.okawa.pedro.galleryapp.database;

import java.util.List;

import greendao.CategoryData;
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

    public void insertOrUpdate(CategoryData CategoryData) {
        mDaoSession.getCategoryDataDao().insertOrReplace(CategoryData);
    }

    public void clearCategoryData() {
        mDaoSession.getCategoryDataDao().deleteAll();
    }

    public List<CategoryData> getPagedCategoryData(int offset) {
        return mDaoSession.getCategoryDataDao().queryBuilder().limit(SELECT_LIMIT).offset(offset).list();
    }

    public List<CategoryData> getAllCategoryData() {
        return mDaoSession.getCategoryDataDao().loadAll();
    }

    public List<CategoryData> getImagesForId(long id) {
        return mDaoSession.getCategoryDataDao()._queryImageData_CategoryDataList(id);
    }

    public CategoryData getCategoryDataForId(long id) {
        return mDaoSession.getCategoryDataDao().load(id);
    }
}
