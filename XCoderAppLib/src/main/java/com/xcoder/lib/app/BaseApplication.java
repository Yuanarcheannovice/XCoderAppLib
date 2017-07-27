package com.xcoder.lib.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by xz on 2016/12/3 0003.
 */

public class BaseApplication extends Application {
    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }
}
