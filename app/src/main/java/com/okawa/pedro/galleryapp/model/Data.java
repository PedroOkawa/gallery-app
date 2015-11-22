package com.okawa.pedro.galleryapp.model;

import java.util.List;

/**
 * Created by pokawa on 19/11/15.
 */
public class Data {
    private long id;
    private Assets assets;
    private List<Categories> categories;
    private Contributor contributor;
    private String image_type;

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

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }
}
