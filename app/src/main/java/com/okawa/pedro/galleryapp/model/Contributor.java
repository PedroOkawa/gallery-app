package com.okawa.pedro.galleryapp.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;

/**
 * Created by pokawa on 19/11/15.
 */
public class Contributor extends RealmObject {
    @Expose private String id;
    @Expose private String display_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
