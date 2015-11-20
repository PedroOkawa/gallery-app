package com.okawa.pedro.galleryapp.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by pokawa on 19/11/15.
 */
public class Data extends RealmObject {
    @Expose private long id;
    @Expose private Assets assets;
    @Expose private RealmList<Categories> categories;
    @Expose private Contributor contributor;
    @Expose private String imageType;

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

    public RealmList<Categories> getCategories() {
        return categories;
    }

    public void setCategories(RealmList<Categories> categories) {
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
