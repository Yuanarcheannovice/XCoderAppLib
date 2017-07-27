package com.xcoder.lib.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcoder.lib.R;


/**
 * 刷新加载
 * Created by xz on 2016/10/24 0024.
 * 此类通过SwipeRefreshLayout，和listview的head，实现了刷新和加载；
 * 此类实现了listview的拉动监听，如果你需要用到listview的拉动监听，请使用本类中的拉动监听；
 */

public class RefreshAndLoad implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private Context context;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar loadPB;//加载的进度条
    private TextView loadTV;//加载的文字
    private OnLoadListener onLoadListener;//加载
    private OnRefreshListener onRefreshListener;//刷新
    private OnScrollListener onScrollListener;//拉动加载
    private int refreshColor;//刷新的控件的颜色

    public final int LOADIS = 0;//正在加载
    public final int LOADClick = 1;//点击加载更多...
    public final int LOADNO = 2;//暂无更多数据可加载
    public final int LOADGONE = 3;//隐藏加载栏
    public final int LOADVISIBLE = 4;//显示加载栏
    public final int LOADERROR = 5;//加载错误
    public final int LOADSUCCESS = 6;//加载完成
    private boolean isLoad = false;//是否在加载   false-否；true-加载中
    private boolean isRefresh = false;//是否在刷新   false-否；true-刷新中
    private RelativeLayout listview_load;
    private View listview_line;
    private View loadView;
    private Boolean isAddFooterView = false;//作为判断，footerview是否被添加


    public void setRefreshColor(@ColorInt int refreshColor) {
        this.refreshColor = refreshColor;
    }

    /**
     * 只有加载的
     *
     * @param context
     * @param listView
     */
    public RefreshAndLoad(Context context, ListView listView) {
        init(context, listView, null);
    }

    /**
     * 只有刷新的
     *
     * @param context
     * @param swipeRefreshLayout
     */
    public RefreshAndLoad(Context context, SwipeRefreshLayout swipeRefreshLayout) {
        init(context, null, swipeRefreshLayout);
    }

    /**
     * 带加载的
     *
     * @param context
     * @param listView
     * @param swipeRefreshLayout
     */
    public RefreshAndLoad(Context context, ListView listView, SwipeRefreshLayout swipeRefreshLayout) {


        init(context, listView, swipeRefreshLayout);
    }

    /**
     * 初始化view
     */
    private void init(Context context, ListView listView, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.listView = listView;
        this.swipeRefreshLayout = swipeRefreshLayout;
        if (listView != null) {
            //加载
            loadView = LayoutInflater.from(context).inflate(
                    R.layout.xcoder_xz_listview_load, null);
            loadView.setOnClickListener(this);
            listview_load = (RelativeLayout) loadView.findViewById(R.id.listview_load);
            loadPB = (ProgressBar) loadView.findViewById(R.id.listview_load_pb);
            loadTV = (TextView) loadView.findViewById(R.id.listview_load_tv);
            loadTV.setOnClickListener(this);
            ListViewScrollListener();
            //默认是隐藏加载栏的
            loadStatus(LOADGONE);
        }
        if (swipeRefreshLayout != null) {
            //刷新
            //这里是使用theme的颜色
            int defaultColor = 0xFF000000;
            int[] attrsArray = {android.R.attr.colorAccent};
            TypedArray typedArray = context.obtainStyledAttributes(attrsArray);
            int accentColor = typedArray.getColor(0, defaultColor);
            typedArray.recycle();
            if(accentColor!=0||refreshColor!=0) {
                swipeRefreshLayout.setColorSchemeColors(refreshColor == 0 ? accentColor : refreshColor);
            }
            swipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    /**
     * 监听listview的滑动
     */
    public void ListViewScrollListener() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
                //停止滑动
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //是否显示加载
                    if (isShowLoad()) {
                        //是否是加载状态
                        if (isLoad != true) {
                            loadStatus(LOADVISIBLE);
                            onLoad();
                        } else {
                            loadStatus(LOADClick);
                        }
                    } else {
                        loadStatus(LOADGONE);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }

    /**
     * 判断是否显示加载栏
     *
     * @return
     */
    public boolean isShowLoad() {
        //判断listview的条目
        if (this.listView.getCount() == 0) {
            return false;
        }
        //判断listview是否滑到最后一个
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            //判断总条目是否大于可见条目，大于才说明，item是超过屏幕的
            if (listView.getCount() > listView.getChildCount()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 刷新的
     */
    @Override
    public void onRefresh() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        //如果加载的同时，使用刷新，需要把加载隐藏，状态回复
        //暂不做，容易导致重复加载请求，
//        if(isLoad()){
//            isLoad=false;
//            loadStatus(LOADGONE);
//        }

        //如需消耗主线程，自己做线程处理
        if (onRefreshListener != null) {
//            swipeRefreshLayout.postDelayed(new Runnable() {
//                @Override
//                public void run() {
            onRefreshListener.onRefresh();
//                }
//            },2000);
        }
        // }
        //   }, 1000);
    }

    /**
     * 加载的
     */
    public void onLoad() {
        isLoad = true;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        if (onLoadListener != null) {
            loadStatus(LOADIS);
            onLoadListener.onLoad();

        }
        // }
        // }, 1000);
    }

    /**
     * 进入app就刷新
     */
    public void immediatelyRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(true);
                    onRefresh();
                }
            }
        });
    }

    /**
     * 停止刷新，或者加载
     */
    public void stopRefreshAndLoad() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (isLoad()) {
            isLoad = false;
            loadStatus(LOADClick);
        }
    }

    /**
     * 停止刷新，或者加载，
     *
     * @param status:加载显示的状态
     */
    public void stopRefreshAndLoad(int status) {
        try {
            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            if (isLoad()) {
                isLoad = false;
                loadStatus(status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止刷新
     */
    public void stopRefresh() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 停止加载
     */
    public void stopLoad() {
        loadStatus(LOADGONE);
    }

    /**
     * int LOADIS = 0;//正在加载
     * int LOADClick = 1;//点击加载更多...
     * int LOADNO = 2;//暂无更多数据可加载
     * int LOADGONE = 3;//隐藏加载栏
     * int LOADVISIBLE = 4;//显示加载栏
     * int LOADERROR=5;//加载错误
     * int LOADSUCCESS = 6;//加载完成
     *
     * @param status:
     */
    public void loadStatus(int status) {
        switch (status) {
            case LOADIS:
                addFooterView();
                // xcoder_xz_listview_load.setVisibility(View.VISIBLE);
                loadTV.setText("正在加载...");
                loadPB.setVisibility(View.VISIBLE);
                break;
            case LOADClick:
                loadTV.setText("点击加载更多...");
                loadPB.setVisibility(View.GONE);
                break;
            case LOADNO:
                addFooterView();
                loadTV.setText("暂无更多数据可加载");
                loadPB.setVisibility(View.GONE);
                break;
            case LOADGONE:
                loadTV.setText("点击加载更多...");
                removeFooterView();
                break;
            case LOADVISIBLE:
                addFooterView();
                //  xcoder_xz_listview_load.setVisibility(View.VISIBLE);
                loadStatus(LOADClick);
                loadStatus(LOADIS);
                break;
            case LOADERROR:
                addFooterView();
                loadTV.setText("网络错误，点击重试");
                loadPB.setVisibility(View.GONE);
                break;
            case LOADSUCCESS:
                addFooterView();
                loadTV.setText("加载完成");
                loadPB.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 刷新此控件
     */
    public void onRefreshThis() {
        stopRefreshAndLoad();
        //是否显示加载
        if (isShowLoad()) {
            loadStatus(LOADClick);
        } else {
            loadStatus(LOADGONE);
        }
    }

    /**
     * 退出activity的时候，需要移除listview的footerview
     */
    public void onDestroy() {
        try {
            if (listView != null && loadView != null)
                listView.removeFooterView(loadView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载栏点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (!isLoad()) {
            onLoad();
        }
    }

    public boolean isRefresh() {
        if (swipeRefreshLayout != null) {
            return swipeRefreshLayout.isRefreshing();
        } else {
            return false;
        }
    }

//    public void setRefresh(boolean refresh) {
//        isRefresh = refresh;
//    }

    /**
     * 添加footer
     */
    public void addFooterView() {
        try {
            if (isAddFooterView == false && listView != null && loadView != null) {
                listView.addFooterView(loadView);
                isAddFooterView = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeFooterView() {
        try {
            if (isAddFooterView == true && listView != null && loadView != null) {
                listView.removeFooterView(loadView);
                isAddFooterView = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }


    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * listview的加载监听
     */
    public interface OnLoadListener {
        void onLoad();
    }

    /**
     * listview的刷新监听
     */
    public interface OnRefreshListener {
        void onRefresh();
    }

    /**
     * listview的拉动监听
     */
    public interface OnScrollListener {
        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);

        void onScrollStateChanged(AbsListView view, int scrollState);
    }


}
