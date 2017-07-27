package com.xcoder.demo.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xcoder.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xz on 2017/7/12 0012.
 */

public class AppProgressDialog extends AlertDialog {
    private static List<AppProgressDialog> mListDialog;
    private TextView mMessageView;
    private String mMessage;

    protected AppProgressDialog(Context context) {
        super(context);
    }


    protected AppProgressDialog(Context context,int themeResId) {
        super(context, themeResId);

    }


    public static AppProgressDialog show(Context context) {
        return show(context, "正在加载...");
    }


    public static AppProgressDialog show(Context context, CharSequence message) {
        return show(context, message, true, false);
    }


    /**
     * @param context                上下文
     * @param message                消息
     * @param cancelable             是否点击返回键消失
     * @param canceledOnTouchOutside 是否点击dialog外面消失
     */
    public static AppProgressDialog show(Context context, CharSequence message, boolean cancelable, boolean canceledOnTouchOutside) {
        AppProgressDialog dialog;
        if (Build.VERSION.SDK_INT >= 17)
            dialog = new AppProgressDialog(context, R.style.AppProgressDialog);
        else
            dialog = new AppProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        //只允许一个弹出框存在
        if (mListDialog == null)
            mListDialog = new ArrayList<>();
        else
            onDissmiss();
        mListDialog.add(dialog);

        //排列为横着的
        dialog.setTitle(null);
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);
        //屏幕外点击消失
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mListDialog != null)
                    mListDialog.clear();
            }
        });
        dialog.show();
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.app_progress_dialog, null);
        mMessageView = (TextView) view.findViewById(R.id.message);
        setView(view);
        if (mMessage != null) {
            setMessage(mMessage);
        }
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setMessage(CharSequence message) {
        if (mMessageView != null) {
            mMessageView.setText(message);
        } else {

        }
    }

    public static void onDissmiss() {
        if (mListDialog != null) {
            for (AppProgressDialog progressDialog : mListDialog) {
                if (progressDialog != null)
                    progressDialog.dismiss();
            }
            mListDialog.clear();
        }
    }


}

