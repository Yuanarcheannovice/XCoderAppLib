package com.xcoder.demo.function.main.controller;


import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.xcoder.demo.function.main.adapter.ListAdapter;
import com.xcoder.demo.function.main.model.MainModel;
import com.xcoder.demo.function.main.view.MainActivity;
import com.xcoder.lib.annotation.Injection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2017/5/2 0002.
 * 逻辑处理层
 */

public class MainController {

    @Injection
    public MainModel mm;
    private MainActivity mActivity;
    public ListAdapter mAdapter;
    private List<String> strList = new ArrayList<>();
    private int ind = 1;

    public void init(MainActivity mActivity) {
        this.mActivity = mActivity;
        initView();
        // / mm.a_m_tv.setTextColor(colorAccent);

    }

    private void initView() {
        mm.rv.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ListAdapter(mActivity);
        TextView textView = new TextView(mActivity);
        textView.setText("head1");
        TextView textView2 = new TextView(mActivity);
        textView2.setText("head2");
        mAdapter.addHeaderView(textView);
        mAdapter.addHeaderView(textView2);
        TextView textView3 = new TextView(mActivity);
        textView3.setText("foot1");
        TextView textView4 = new TextView(mActivity);
        textView4.setText("foot2");
        mAdapter.addFootView(textView3);
        mAdapter.addFootView(textView4);
        mm.rv.setAdapter(mAdapter);


        initData();

        mm.srl.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                mm.rv.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 50; i++) {
                            list.add(ind + "-" + i + (i * 2) + (i + 3));
                            ind++;
                        }

                        mAdapter.setDatas(list, true);
                        mm.srl.finishLoadmore(500);
                    }
                }, 3000);
            }
        });


    }


    private void initData() {
        mm.rv.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    strList.add(ind + "-" + i + (i * 2) + (i + 3));
                    ind++;
                }
                mAdapter.setDatas(strList, true);
            }
        },3000);
    }




}
