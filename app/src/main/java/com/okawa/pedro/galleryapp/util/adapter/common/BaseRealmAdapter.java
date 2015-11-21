package com.okawa.pedro.galleryapp.util.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by pokawa on 21/11/15.
 */
public abstract class BaseRealmAdapter<T extends RealmObject> extends RecyclerView.Adapter {

    LayoutInflater mInflater;
    RealmResults<T> mRealmResults;
    Context mContext;
    final RealmChangeListener mListener;

    public BaseRealmAdapter(
            Context context,
            RealmResults<T> realmResults,
            boolean automaticUpdate) {

        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.mContext = context;
        this.mRealmResults = realmResults;
        this.mInflater = LayoutInflater.from(context);
        this.mListener = (!automaticUpdate) ? null : new RealmChangeListener() {
            @Override
            public void onChange() {
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        if (mRealmResults == null) {
            return 0;
        }
        return mRealmResults.size();
    }

    public T getItem(int position) {
        if (mRealmResults == null) {
            return null;
        }
        return mRealmResults.get(position);
    }

    public void updateRealmResults(RealmResults<T> queryResults) {
        if (mListener != null) {
            if (this.mRealmResults != null) {
                Realm.getDefaultInstance().removeChangeListener(mListener);
            }
            if (queryResults != null) {
                Realm.getDefaultInstance().addChangeListener(mListener);
            }
        }

        this.mRealmResults = queryResults;
        notifyDataSetChanged();
    }

    public void addDataSet(RealmResults<T> realmResults) {
        this.mRealmResults = realmResults;
        notifyDataSetChanged();
    }

    @Deprecated
    public void add(int position, T item) {
        mRealmResults.add(position, item);
        notifyItemInserted(position);
    }

    public int getItemPosition(T itemToFind) {
        for (int i = 0; i < mRealmResults.size(); ++i) {
            T item = mRealmResults.get(i);
            if (itemToFind.equals(item)) {
                return i;
            }
        }

        return -1;
    }

    public void moveEntity(int fromPosition, int toPosition) {
        move(mRealmResults, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void move(List<T> data, int a, int b) {
        T temp = data.remove(a);
        data.add(b, temp);
    }
}