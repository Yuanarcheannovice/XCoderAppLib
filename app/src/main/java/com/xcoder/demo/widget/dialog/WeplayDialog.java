package com.xcoder.demo.widget.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcoder.demo.R;
import com.xcoder.lib.utils.Utils;

/**
 * Created by xz on 2017/6/26 0026.
 * 建造者模式写的dialog
 */
public class WeplayDialog implements View.OnClickListener {
    private Context context;
    public AlertDialog dialog;

    private String mTitle;
    private String mMessage;
    private String mLeftTxt;
    private String mRightTxt;


    /**
     * 建造者模式
     */

    public WeplayDialog(Context context) {
        this.context = context;
    }

    /**
     * 设置title
     */
    public WeplayDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }


    /**
     * 设置 文本
     */
    public WeplayDialog setMessage(String message) {
        this.mMessage = message;
        return this;
    }

    /**
     * 设置左边按钮的文字
     */
    public WeplayDialog setLeftTxt(String leftTxt) {
        this.mLeftTxt = leftTxt;
        return this;
    }

    /**
     * 设置右边按钮的文字
     */
    public WeplayDialog setRightTxt(String rightTxt) {
        this.mRightTxt = rightTxt;
        return this;
    }

    /**
     * 设置左边按钮的点击
     */
    public WeplayDialog setmOnLeftButtonClickListener(onLeftButtonClickListener mOnLeftButtonClickListener) {
        this.mOnLeftButtonClickListener = mOnLeftButtonClickListener;
        return this;
    }

    /**
     * 设置右边按钮的点击
     */
    public WeplayDialog setmOnRightButtonClickListener(onRightButtonClickListener mOnRightButtonClickListener) {
        this.mOnRightButtonClickListener = mOnRightButtonClickListener;
        return this;
    }


    public void showDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.app_dialog_main, null);
        TextView diaTitle = (TextView) view.findViewById(R.id.app_dialog_title);//标题
        View line1 = view.findViewById(R.id.app_dialog_linehor1);//标题分割线
        TextView diaMessage = (TextView) view.findViewById(R.id.app_dialog_message);//内容
        View line2 = view.findViewById(R.id.app_dialog_linehor2);//内容分割线
        LinearLayout buttonLin = (LinearLayout) view.findViewById(R.id.app_dialog_buttonll);//button布局
        Button leftB = (Button) view.findViewById(R.id.app_dialog_buttonleft);//左button
        leftB.setOnClickListener(this);
        View line3 = view.findViewById(R.id.app_dialog_linever);//button中间的分割线
        Button rightB = (Button) view.findViewById(R.id.app_dialog_buttonright);//右button
        rightB.setOnClickListener(this);

        if (mLeftTxt == null) {
            //左边button为null
            if (mTitle == null) {
                diaTitle.setVisibility(View.GONE);
                line1.setVisibility(View.GONE);
            } else {
                diaTitle.setText(mTitle);
            }
            diaMessage.setText(mMessage);
            rightB.setText(mRightTxt);
            line3.setVisibility(View.GONE);
            leftB.setVisibility(View.GONE);

        } else {
            if (mTitle == null) {
                diaTitle.setVisibility(View.GONE);
                line1.setVisibility(View.GONE);

            } else {
                diaTitle.setText(mTitle);
            }
            diaMessage.setText(mMessage);
            leftB.setText(mLeftTxt);
            rightB.setText(mRightTxt);
        }
///////////------initDialog-----------//////////
        // 创建对话
        dialog = new AlertDialog.Builder(context).create();
        // 设置返回键失
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        // dialog.getWindow().setType(
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        // 显示对话
        dialog.show();
        // 必须放到显示对话框下面，否则显示不出效果
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Utils.getPhoneWidth(context) * 4 / 5;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        // 加载布局组件
        dialog.getWindow().setContentView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_dialog_buttonleft:
                if (mOnLeftButtonClickListener != null)
                    mOnLeftButtonClickListener.onClick();
                dismiss();
                break;
            case R.id.app_dialog_buttonright:
                if (mOnRightButtonClickListener != null)
                    mOnRightButtonClickListener.onClick();
                dismiss();
                break;
        }


    }

    /**
     * @方法说明:让警告框消失
     * @方法名称:dismiss
     * @返回值:void
     */
    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog.cancel();
            dialog = null;
            context = null;
        }
    }


    private onLeftButtonClickListener mOnLeftButtonClickListener;

    private onRightButtonClickListener mOnRightButtonClickListener;

    /**
     * 左边按钮的点击事件
     */
    public interface onLeftButtonClickListener {
        void onClick();
    }

    /**
     * 右边按钮的点击事件
     */
    public interface onRightButtonClickListener {
        void onClick();
    }
}
