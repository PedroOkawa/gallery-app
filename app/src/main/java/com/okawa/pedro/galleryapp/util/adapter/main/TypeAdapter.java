package com.okawa.pedro.galleryapp.util.adapter.main;

import android.view.View;

import com.okawa.pedro.galleryapp.R;
import com.okawa.pedro.galleryapp.databinding.AdapterTypeBinding;
import com.okawa.pedro.galleryapp.util.adapter.commun.SimpleAdapter;
import com.okawa.pedro.galleryapp.util.listener.OnTypeTouchListener;

import java.util.List;

/**
 * Created by pokawa on 23/11/15.
 */
public class TypeAdapter extends SimpleAdapter<String, AdapterTypeBinding> {

    private OnTypeTouchListener mOnTypeTouchListener;

    public TypeAdapter(List<String> data) {
        super(data);
    }

    public void setOnTypeTouchListener(OnTypeTouchListener onTypeTouchListener) {
        this.mOnTypeTouchListener = onTypeTouchListener;
    }

    @Override
    public int layoutToInflate() {
        return R.layout.adapter_type;
    }

    @Override
    protected void doOnBindViewHolder(SimpleViewHolder holder,
                                      AdapterTypeBinding binding, final int position, String type) {
        if(mOnTypeTouchListener != null) {
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnTypeTouchListener.onTypeTouched(position);
                }
            });
        }

        binding.tvAdapterType.setText(type);
        binding.setType(position);
    }
}
