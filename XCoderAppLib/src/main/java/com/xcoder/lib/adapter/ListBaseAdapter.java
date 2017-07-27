package com.xcoder.lib.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2017/1/1 0001.
 */

public abstract class ListBaseAdapter<D> extends BaseAdapter {

    /**
     * 获取view
     *
     * @param context
     * @param layout
     * @return
     */
    protected View getConvertView(Context context, @LayoutRes int layout) {
        return LayoutInflater.from(context).inflate(layout, null);
    }

    protected List<D> mListData = new ArrayList<>();

    public List<D> getListData() {
        return mListData;
    }

    public void setListData(List<D> listData) {
        if (mListData != null) {
            this.mListData = listData;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}

