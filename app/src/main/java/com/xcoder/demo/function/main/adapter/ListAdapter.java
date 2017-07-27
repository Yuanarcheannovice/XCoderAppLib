package com.xcoder.demo.function.main.adapter;


import android.content.Context;

import com.xcoder.demo.R;
import com.xcoder.lib.adapter.recyclerview.RvCommonAdapter;
import com.xcoder.lib.adapter.recyclerview.util.RvViewHolder;

/**
 * Created by xz on 2017/6/14 0014.
 */

public class ListAdapter extends RvCommonAdapter<String> {


    public ListAdapter(Context context) {
        super(context, R.layout.item_str);
    }


    @Override
    protected void convert(RvViewHolder holder, String s, int position) {
        holder.setText(R.id.i_t_tv, mDatas.get(position));
    }
}
