package com.okawa.pedro.galleryapp.util.manager;

import com.okawa.pedro.galleryapp.model.Categories;
import com.okawa.pedro.galleryapp.model.Data;

import java.util.ArrayList;
import java.util.List;

import greendao.CategoryData;
import greendao.ImageData;

/**
 * Created by pokawa on 24/11/15.
 */
public class ParserManager {

    /* PARSE DATA AND PERSIST THE CATEGORY ON DATABASE */
    public ImageData parseData(Data data) {
        /* PARSE DATA RETRIEVED TO IMAGE DATA */
        ImageData imageData = new ImageData();

        imageData.setImageId(data.getId());
        imageData.setDescription(data.getDescription());
        imageData.setImageType(convertType(data.getImage_type()));
        imageData.setContributorId(data.getContributor().getId());
        imageData.setImageURL(data.getAssets().getPreview().getUrl());

        return imageData;
    }

    public List<CategoryData> parseCategories(Data data) {
        List<CategoryData> categories = new ArrayList<>();
        for(Categories category : data.getCategories()) {
            CategoryData categoryData = new CategoryData();
            categoryData.setCategoryId(category.getId());
            categoryData.setName(category.getName());
            categoryData.setImageId(data.getId());
            categories.add(categoryData);
        }
        return categories;
    }

    private int convertType(String type) {
        switch(type) {
            case ImageData.TYPE_PHOTO:
                return ImageData.TYPE_PHOTO_ID;
            case ImageData.TYPE_ILLUSTRATION:
                return ImageData.TYPE_ILLUSTRATION_ID;
            default:
                return ImageData.TYPE_VECTOR_ID;
        }
    }

}
