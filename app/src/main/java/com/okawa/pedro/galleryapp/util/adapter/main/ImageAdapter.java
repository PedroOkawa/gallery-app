package com.okawa.pedro.galleryapp.util.adapter.main;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.databinding.AdapterMainBinding;
import com.okawa.pedro.galleryapp.util.adapter.commun.SimpleAdapter;
import com.okawa.pedro.galleryapp.util.listener.OnImageTouchListener;
import com.okawa.pedro.galleryapp.util.manager.CallManager;

import java.util.List;

import greendao.ImageData;

/**
 * Created by pokawa on 22/11/15.
 */
public class ImageAdapter extends SimpleAdapter<ImageData, AdapterMainBinding> {

    private Context mContext;
    private OnImageTouchListener mOnImageTouchListener;

    public ImageAdapter(Context context, List<ImageData> data) {
        super(data);
        this.mContext = context;
    }

    public void setOnImageTouchListener(OnImageTouchListener onImageTouchListener) {
        this.mOnImageTouchListener = onImageTouchListener;
    }

    @Override
    public int layoutToInflate() {
        return R.layout.adapter_main;
    }

    @Override
    protected void doOnBindViewHolder(SimpleViewHolder holder,
                                      final AdapterMainBinding binding,
                                      int position, final ImageData imageData) {

        if(mOnImageTouchListener != null) {
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* DEFINE PARAMETERS TO MAKE TRANSITION (IMAGE VIEW AND DETAILS LAYOUT) */
                    Pair<View, String> image =
                            new Pair<View, String>(binding.viewImageDetails.ivViewImageCard,
                                    CallManager.IMAGE);

                    Pair<View, String> details =
                            new Pair<View, String>(binding.viewImageDetails.llViewImageDetails,
                                    CallManager.DETAILS);

                    /* CALLS THE TOUCH LISTENER (MAIN PRESENTER) */
                    mOnImageTouchListener.onImageTouched(imageData.getImageId(), image, details);
                }
            });
        }

        /*
         LOAD DATA INSIDE THE ADAPTER (IMAGE / TYPE / ID)
         */

        /* IMAGE */
        Glide.with(mContext)
                .load(imageData.getImageURL())
                .thumbnail(Glide.with(mContext).load(imageData.getImageURL()).centerCrop())
                .placeholder(R.mipmap.ic_placeholder)
                .dontAnimate()
                .centerCrop()
                .into(binding.viewImageDetails.ivViewImageCard);

        /* TYPE */
        @IntegerRes int iconResource = imageData.getImageType().equals(ImageData.TYPE_PHOTO) ?
                R.mipmap.ic_photo :
                imageData.getImageType().equals(ImageData.TYPE_ILLUSTRATION) ?
                        R.mipmap.ic_illustration:
                        R.mipmap.ic_vector;

        Glide.with(mContext)
                .load(iconResource)
                .thumbnail(Glide.with(mContext).load(iconResource).fitCenter())
                .dontAnimate()
                .fitCenter()
                .into(binding.viewImageDetails.ivViewImageType);

        /* ID */
        StringBuffer stringBuffer = new StringBuffer().append("ID: ").append(imageData.getImageId());
        binding.viewImageDetails.tvViewImageId.setText(stringBuffer.toString());

        binding.setImageData(imageData);
    }
}