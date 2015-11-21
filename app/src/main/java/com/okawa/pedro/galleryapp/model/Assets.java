package com.okawa.pedro.galleryapp.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;

/**
 * Created by pokawa on 19/11/15.
 */
public class Assets extends RealmObject {
    @Expose private Content preview;

    public Content getPreview() {
        return preview;
    }

    public void setPreview(Content preview) {
        this.preview = preview;
    }
}
