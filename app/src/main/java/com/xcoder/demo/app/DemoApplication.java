package com.xcoder.demo.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xcoder.demo.R;
import com.xcoder.demo.util.MaterialHeader;
import com.xcoder.lib.app.AppPathManager;
import com.xcoder.lib.app.BaseApplication;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by xcoder on 2017/1/7 0007.
 * 示例
 */

public class DemoApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        //配置app的文件缓存目录的文件夹名称
        AppPathManager.initPathManager("demo");
//        CrashHandler.getInstance();
        initOkHttp();

        initRefresh();

    }

    private void initRefresh(){

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                MaterialHeader materialHeader = new MaterialHeader(context);
                materialHeader.setColorSchemeColors(
                        ContextCompat.getColor(context, R.color.app_main_color),
                        ContextCompat.getColor(context,R.color.app_sub_color),
                        ContextCompat.getColor(context,R.color.app_ornage_color));
                return materialHeader;
            }
        });
////设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
//            @Override
//            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
//                return new ClassicsFooter(context);//指定为经典Footer，默认是 BallPulseFooter
//            }
//        });
    }


    /**
     * ok配置
     */
    public void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
