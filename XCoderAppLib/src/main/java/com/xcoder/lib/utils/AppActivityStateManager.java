package com.xcoder.lib.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

/**
 * Created by jay on 2017/7/7 下午12:12
 * 应用活动状态管理类，可监听应用前、后台状态的改变
 * @author jay
 * @version 1.0.0
 */
@RequiresApi(api = android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public final class AppActivityStateManager implements Application.ActivityLifecycleCallbacks {

    private int mActivityCount;
    private boolean isBackground;
    private boolean isForeground;

    private final ArrayList<ApplicationActivityStateCallback> mApplicationActivityStateCallbacks =
            new ArrayList<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        mActivityCount++;
        if (mActivityCount > 0 && isBackground) {
            // 应用进入前台状态
            isBackground = false;
            isForeground = true;
            for (ApplicationActivityStateCallback callback : mApplicationActivityStateCallbacks) {
                if (callback != null) {
                    callback.onApplicationEnterForeground();
                }
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        mActivityCount--;
        if (mActivityCount == 0) {
            // 应用进入后台状态
            isForeground = false;
            isBackground = true;
            for (ApplicationActivityStateCallback callback : mApplicationActivityStateCallbacks) {
                if (callback != null) {
                    callback.onApplicationEnterBackground();
                }
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private static class Build {
        static AppActivityStateManager instance = new AppActivityStateManager();
    }

    private AppActivityStateManager() {

    }

    public static AppActivityStateManager getInstance() {
        return Build.instance;
    }

    public void registerApplicationActivityStateCallbacks(Application app, ApplicationActivityStateCallback callback) {
        synchronized (mApplicationActivityStateCallbacks) {
            mApplicationActivityStateCallbacks.add(callback);
        }
    }
    public void unregisterApplicationActivityStateCallbacks(Application app, ApplicationActivityStateCallback callback) {
        synchronized (mApplicationActivityStateCallbacks) {
            mApplicationActivityStateCallbacks.remove(callback);
        }
    }

    public interface ApplicationActivityStateCallback {
        void onApplicationEnterBackground();

        void onApplicationEnterForeground();
    }
}
