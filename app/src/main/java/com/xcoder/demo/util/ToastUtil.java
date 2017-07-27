package com.xcoder.demo.util;

import android.widget.Toast;

import com.xcoder.demo.app.DemoApplication;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class ToastUtil {

    public static void show(String str){
        Toast.makeText(DemoApplication.context,str,Toast.LENGTH_SHORT).show();
    }
}
