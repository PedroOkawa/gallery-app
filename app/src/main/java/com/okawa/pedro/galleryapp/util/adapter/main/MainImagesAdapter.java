package com.okawa.pedro.galleryapp.util.adapter.main;

import android.content.Context;

import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.databinding.AdapterMainImagesBinding;
import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.util.adapter.common.SimpleRealmAdapter;

import io.realm.RealmResults;

/**
 * Created by pokawa on 21/11/15.
 */
public class MainImagesAdapter extends SimpleRealmAdapter<Data, AdapterMainImagesBinding> {

    public MainImagesAdapter(
            Context context,
            RealmResults<Data> realmResults,
            boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public int layoutToInflate() {
        return R.layout.adapter_main_images;
    }

    @Override
    protected void doOnBindViewHolder(
            ViewHolder holder,
            AdapterMainImagesBinding binding,
            int position,
            Data item) {

    }
}
