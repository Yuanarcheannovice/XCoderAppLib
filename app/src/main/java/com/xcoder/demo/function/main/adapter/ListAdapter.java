package com.xcoder.demo.function.main.adapter;


import android.content.Context;

import com.xcoder.demo.R;
import com.xz.xadapter.XRvCommonAdapter;
import com.xz.xadapter.xutil.XRvViewHolder;

/**
 * Created by xz on 2017/6/14 0014.
 */

public class ListAdapter extends XRvCommonAdapter<String> {


    public ListAdapter(Context context) {
        super(context, R.layout.item_str);
    }



    @Override
    protected void convert(XRvViewHolder xRvViewHolder, String s, int position) {
        xRvViewHolder.setText(R.id.i_t_tv, mDatas.get(position));

    }
}
