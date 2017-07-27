package com.xcoder.demo.function.main.model;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xcoder.demo.R;
import com.xcoder.lib.annotation.ResInject;
import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.lib.injection.ResType;

/**
 * Created by xcoder_xz on 2017/5/2 0002.
 * 初始化view层
 */

public class MainModel {
    @ViewInject(R.id.rv)
    public RecyclerView rv;

    @ViewInject(R.id.m_bt1)
    public Button m_bt1;

    @ViewInject(R.id.m_bt2)
    public Button m_bt2;

    @ViewInject(R.id.srl)
    public SmartRefreshLayout srl;

    /**
     * 资源注解
     */
    @ResInject(id = R.color.colorAccent, type = ResType.Color)
    public int colorAccent;


}
