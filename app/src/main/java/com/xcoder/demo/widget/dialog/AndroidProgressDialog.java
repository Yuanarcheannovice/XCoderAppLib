package com.xcoder.demo.widget.dialog;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import com.xcoder.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2017/7/11 0011.
 * 官方dialogUpdate
 */

public class AndroidProgressDialog extends ProgressDialog {
    private static List<AndroidProgressDialog> mListDialog;

    public AndroidProgressDialog(Context context) {
        super(context);
    }

    public AndroidProgressDialog(Context context, int theme) {
        super(context, theme);
    }


    public static ProgressDialog show(Context context) {
        return show(context, "正在加载...");
    }

    public static AndroidProgressDialog show(Context context, CharSequence message) {
        AndroidProgressDialog progressDialog = show(context, null, message, true, true, new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mListDialog != null)
                    mListDialog.clear();
            }
        });
        //只允许一个弹出框存在
        if (mListDialog == null)
            mListDialog = new ArrayList<>();
        else
            onDissmiss();
        mListDialog.add(progressDialog);
        return progressDialog;
    }

    /**
     * @param context        上下文
     * @param title          标题
     * @param message        内容
     * @param indeterminate  是否模糊
     * @param cancelable     是否后退键
     * @param cancelListener 是否监听dialog消失
     */
    public static AndroidProgressDialog show(Context context, CharSequence title,
                                         CharSequence message, boolean indeterminate,
                                         boolean cancelable, OnCancelListener cancelListener) {
        //做小于21的兼容，保证dialog不会变形
        AndroidProgressDialog dialog;
        if (Build.VERSION.SDK_INT >= 21)
            dialog = new AndroidProgressDialog(context, R.style.AppProgressDialog);
        else
            dialog = new AndroidProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        //排列为横着的,用来做上传进度条的
//        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        //屏幕外点击消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    public static void onDissmiss() {
        if (mListDialog != null) {
            for (AndroidProgressDialog progressDialog : mListDialog) {
                if (progressDialog != null)
                    progressDialog.dismiss();
            }
            mListDialog.clear();
        }
    }
}
