package com.okawa.pedro.galleryapp.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;

/**
 * Created by pokawa on 19/11/15.
 */
public class Categories extends RealmObject {
    @Expose private long id;
    @Expose private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
