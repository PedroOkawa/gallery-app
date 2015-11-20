package com.okawa.pedro.galleryapp.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;

/**
 * Created by pokawa on 19/11/15.
 */
public class Assets extends RealmObject {
    @Expose private Content preview;
    @Expose private Content smallThumb;
    @Expose private Content largeThumb;

    public Content getPreview() {
        return preview;
    }

    public void setPreview(Content preview) {
        this.preview = preview;
    }

    public Content getSmallThumb() {
        return smallThumb;
    }

    public void setSmallThumb(Content smallThumb) {
        this.smallThumb = smallThumb;
    }

    public Content getLargeThumb() {
        return largeThumb;
    }

    public void setLargeThumb(Content largeThumb) {
        this.largeThumb = largeThumb;
    }
}
