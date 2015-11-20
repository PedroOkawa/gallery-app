package com.okawa.pedro.galleryapp.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;

/**
 * Created by pokawa on 19/11/15.
 */
public class Contributor extends RealmObject {
    @Expose private String id;
    @Expose private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
