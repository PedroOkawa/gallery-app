package com.okawa.pedro.galleryapp.util.adapter.main;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.view.View;

import com.bumptech.glide.Glide;
import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.databinding.AdapterTypeBinding;
import com.okawa.pedro.galleryapp.util.adapter.commun.SimpleAdapter;
import com.okawa.pedro.galleryapp.util.listener.OnTypeTouchListener;

import java.util.List;

import greendao.ImageData;

/**
 * Created by pokawa on 23/11/15.
 */
public class TypeAdapter extends SimpleAdapter<String, AdapterTypeBinding> {

    private Context mContext;
    private OnTypeTouchListener mOnTypeTouchListener;

    public TypeAdapter(Context context, List<String> data) {
        super(data);
        this.mContext = context;
    }

    public void setOnTypeTouchListener(OnTypeTouchListener onTypeTouchListener) {
        this.mOnTypeTouchListener = onTypeTouchListener;
    }

    @Override
    public int layoutToInflate() {
        return R.layout.adapter_type;
    }

    @Override
    protected void doOnBindViewHolder(SimpleViewHolder holder, AdapterTypeBinding binding, int position, final String type) {
        if(mOnTypeTouchListener != null) {
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnTypeTouchListener.onTypeTouched(type);
                }
            });
        }

        binding.tvAdapterType.setText(type);

        /* TYPE */
        @IntegerRes int iconResource = type.equals(ImageData.TYPE_PHOTO) ?
                R.mipmap.ic_photo :
                type.equals(ImageData.TYPE_ILLUSTRATION) ?
                        R.mipmap.ic_illustration :
                        type.equals(ImageData.TYPE_VECTOR) ?
                                R.mipmap.ic_vector :
                                R.mipmap.ic_all_types;

        Glide.with(mContext)
                .load(iconResource)
                .thumbnail(Glide.with(mContext).load(iconResource).fitCenter())
                .dontAnimate()
                .fitCenter()
                .into(binding.ivViewImageType);
    }
}
