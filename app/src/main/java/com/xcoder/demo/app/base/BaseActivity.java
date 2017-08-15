package com.xcoder.demo.app.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.xcoder.lib.app.AppActivityManager;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.lib.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;


/**
 * @类名:BaseActivity
 * @类描述:activity基类,所有子类需继承此类
 * @作者:Administrator
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @版本:
 */
public abstract class BaseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //隐藏标题栏
//        getSupportActionBar().hide();
        //开启全屏模式才用这个，类似于小书阅读器
//        getWindow().
//                setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AppActivityManager.getInstance().pushActivity(this);
        ViewUtils.inject(this);
        onXCoderCreate(savedInstanceState);
    }

    /**
     * @param pClass
     * @方法说明:启动指定activity
     * @方法名称:openActivity
     * @返回void
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * @param pClass
     * @param pBundle
     * @方法说明:启动到指定activity，Bundle传递对象（作用个界面之间传递数据）
     * @方法名称:openActivity
     * @返回void
     */
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * @param pAction
     * @方法说明:根据界面name启动到指定界 * @方法名称:openActivity
     * @返回void
     */
    public void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * @param pAction
     * @param pBundle
     * @方法说明:根据界面name启动到指定界面，Bundle传递对象（作用个界面之间传递数据）
     * @方法名称:openActivity
     * @返回void
     */
    public void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * @param pClass
     * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作，而B-Activity又要将，执行操作数据的结果返回给A-Activity
     * @方法名称:openActivityResult
     * @返回void
     */
    public void openActivityResult(Class<?> pClass) {
        openActivityResult(pClass, null);
    }

    /**
     * @param pClass
     * @param pBundle
     * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作， 而B-Activity又要将，执行操作数据的结果返回给A-Activity
     * Bundle传递对象（作用个界面之间传递数据）
     * @方法名称:openActivityResult
     * @返回void
     */
    public void openActivityResult(Class<?> pClass, Bundle pBundle) {
        openActivityResult(pClass, pBundle, 0);
    }

    /**
     * @param pClass
     * @param pBundle
     * @param requestCode
     * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作， 而B-Activity又要将，执行操作数据的结果返回给A-Activity
     * Bundle传递对象（作用个界面之间传递数据）
     * @方法名称:openActivityResult
     * @返回void
     */
    public void openActivityResult(Class<?> pClass, Bundle pBundle,
                                   int requestCode) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /*
     * @重写方法 onKeyDown
     *
     * @父类:@see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     *
     * @方法说明:
     */
    @SuppressWarnings("static-access")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (Utils.backDouble(800)) {
                return super.onKeyDown(keyCode, event);
            }
            finish();

        }
        if (keyCode == event.KEYCODE_HOME) {
            //	ProgressDialogUtil.stopLoad();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @方法说明:退出栈中所有Activity
     * @方法名称:finishBase
     * @返回void
     */
    public void finishBase() {
        AppActivityManager.getInstance().finishAllActivityExceptOne(
                getClass());
        finish();
    }

    /**
     * @param context                上下 * @param isBackground是否开开启后台运 * @返回值：void
     * @param //isBackground是否开开启后台运 * @返回void
     * @方法说明:退出应用程 * @方法名称:AppExit
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context, Boolean isBackground) {
        try {
            finishBase();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
        } catch (Exception e) {
        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                System.exit(0);
            }
        }
    }

    /**
     * @重写方法onDestroy
     * @父类:@see android.app.Activity#onDestroy()
     * @方法说明:
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁当前的activity
        AppActivityManager.getInstance().finishActivity(this);
        // removeProgress();
        //判断子类传过来的是什么再做判断
        Object closeO = closeActivity();
        if(closeO  instanceof String){
            //一般返如果是string，那就是关闭Okhttp
            OkHttpUtils.getInstance().cancelTag(closeO);
        }else if(closeO instanceof  Activity){
            //未处理
        }
        // AppActivityManager.getInstance().finishActivity(this);
    }

    /**
     * @param savedInstanceState
     * @方法说明:初始化界 * @方法名称:onPreOnCreate
     * @返回void
     */
    public abstract void onXCoderCreate(Bundle savedInstanceState);

    /**
     * @方法说明:手动释放内存
     * @方法名称:releaseMemory
     * @返回void
     */
    public abstract Object closeActivity();



}
