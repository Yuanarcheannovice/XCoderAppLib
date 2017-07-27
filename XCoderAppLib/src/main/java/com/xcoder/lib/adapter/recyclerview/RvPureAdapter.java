package com.xcoder.lib.adapter.recyclerview;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.xcoder.lib.adapter.recyclerview.util.RvViewHolder;


/**
 * 一个简单封装了，Rl点击事件的类，
 * Created by xz on 2016/8/15 0015.
 * <p>
 * 纯净版的adapter(没有head，footer，多layout控制，只有数据的控制)
 */
public abstract class RvPureAdapter<T> extends RvDataAdapter<T> {


    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RvViewHolder viewHolder = RvViewHolder.createViewHolder(parent.getContext(), parent, getItemLayout(viewType));

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition() - getHeadersCount();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    int position = viewHolder.getAdapterPosition() - getHeadersCount();
                    return mOnItemLongClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
        return viewHolder;
    }


    @LayoutRes
    public abstract int getItemLayout(int viewType);


    //把RvData一些公用的方法重写，转成私有，防止调用无效

    /**
     * 一个假的添加HeaderView
     */
    public void addHeaderView(View view) {
    }

    /**
     * 一个假的添加FootView
     */
    public void addFootView(View view) {
    }


}
