package com.xcoder.lib.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * 万能的ViewHolder
 * 适用用listview
 * Created by xz on 2016/8/4 0004.
 */
public class ListViewHolder {

    /**
     * @param view 所有缓存View的根View
     * @param id   缓存View的唯一标识
     * @return
     */
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        //如果根view没有用来缓存View的集合
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);//创建集合和根View关联
        }
        View chidlView = viewHolder.get(id);//获取根View储存在集合中的孩纸
        if (chidlView == null) {//如果没有改孩纸
            //找到该孩纸
            chidlView = view.findViewById(id);
            viewHolder.put(id, chidlView);//保存到集合
        }
        return (T) chidlView;
    }
    /*
    //使用说明
    @Override
public View getView(int position, View convertView, ViewGroup parent) {

    if (convertView == null) {
        convertView = LayoutInflater.from(context)
          .inflate(R.layout.banana_phone, parent, false);
    }

    ImageView bananaView = ListViewHolder.get(convertView, R.id.banana);
    TextView phoneView = ListViewHolder.get(convertView, R.id.phone);

    BananaPhone bananaPhone = getItem(position);
    phoneView.setText(bananaPhone.getPhone());
    bananaView.setImageResource(bananaPhone.getBanana());

    return convertView;
}
     */

}
