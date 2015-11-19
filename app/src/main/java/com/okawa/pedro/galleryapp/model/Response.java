package com.okawa.pedro.galleryapp.model;

import java.util.List;

/**
 * Created by pokawa on 19/11/15.
 */
public class Response {
    private long id;
    private Assets assets;
    private List<Categories> categories;
    private Contributor contributor;
    private String imageType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
