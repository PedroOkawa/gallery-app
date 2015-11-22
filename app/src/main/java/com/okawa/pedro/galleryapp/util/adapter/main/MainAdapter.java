package com.okawa.pedro.galleryapp.util.adapter.main;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.databinding.AdapterMainBinding;
import com.okawa.pedro.galleryapp.util.adapter.commun.SimpleAdapter;

import java.util.List;

import greendao.ImageData;

/**
 * Created by pokawa on 22/11/15.
 */
public class MainAdapter extends SimpleAdapter<ImageData, AdapterMainBinding> {

    private Context mContext;

    public MainAdapter(Context context, List<ImageData> data) {
        super(data);
        this.mContext = context;
    }

    @Override
    public int layoutToInflate() {
        return R.layout.adapter_main;
    }

    @Override
    protected void doOnBindViewHolder(SimpleViewHolder holder,
                                      AdapterMainBinding binding,
                                      int position, ImageData item) {
        Glide.with(mContext)
                .load(item.getImageURL())
                .thumbnail(Glide.with(mContext).load(item.getImageURL()).centerCrop())
                .dontAnimate()
                .centerCrop()
                .into(binding.ivAdapterMainImage);
    }
}
