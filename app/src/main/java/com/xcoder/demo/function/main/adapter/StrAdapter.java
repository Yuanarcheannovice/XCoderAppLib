package com.xcoder.demo.function.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.xcoder.demo.R;
import com.xcoder.lib.adapter.recyclerview.util.RvViewHolder;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class StrAdapter extends RecyclerView.Adapter<RvViewHolder>{
    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RvViewHolder rvViewHolder=RvViewHolder.createViewHolder(parent.getContext(), parent, R.layout.item_str);
        return rvViewHolder;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        holder.getConvertView();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
