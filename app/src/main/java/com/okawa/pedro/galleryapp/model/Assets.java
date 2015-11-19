package com.okawa.pedro.galleryapp.model;

/**
 * Created by pokawa on 19/11/15.
 */
public class Assets {
    private Image preview;
    private Image smallThumb;
    private Image largeThumb;

    public Image getPreview() {
        return preview;
    }

    public void setPreview(Image preview) {
        this.preview = preview;
    }

    public Image getSmallThumb() {
        return smallThumb;
    }

    public void setSmallThumb(Image smallThumb) {
        this.smallThumb = smallThumb;
    }

    public Image getLargeThumb() {
        return largeThumb;
    }

    public void setLargeThumb(Image largeThumb) {
        this.largeThumb = largeThumb;
    }
}
