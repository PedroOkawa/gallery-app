package com.okawa.pedro.galleryapp.util.adapter.main;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.util.Log;

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
                                      int position, ImageData imageData) {
        Glide.with(mContext)
                .load(imageData.getImageURL())
                .thumbnail(Glide.with(mContext).load(imageData.getImageURL()).centerCrop())
                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate()
                .centerCrop()
                .into(binding.ivAdapterMainImage);

        @IntegerRes int iconResource = imageData.getImageType().equals(ImageData.TYPE_PHOTO) ?
                R.mipmap.ic_photo :
                imageData.getImageType().equals(ImageData.TYPE_ILLUSTRATION) ?
                        R.mipmap.ic_illustration:
                        R.mipmap.ic_vector;

        Glide.with(mContext)
                .load(iconResource)
                .thumbnail(Glide.with(mContext).load(iconResource).centerCrop())
                .dontAnimate()
                .centerCrop()
                .into(binding.ivAdapterMainType);

        binding.setImageData(imageData);
    }
}
