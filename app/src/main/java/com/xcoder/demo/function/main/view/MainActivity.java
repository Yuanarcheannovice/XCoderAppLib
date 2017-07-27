package com.xcoder.demo.function.main.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xcoder.demo.R;
import com.xcoder.demo.app.base.BaseActivity;
import com.xcoder.demo.function.main.controller.MainController;
import com.xcoder.demo.function.main.model.MainModel;
import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Injection
    MainController mc;
    @Injection
    public MainModel mm;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {

        mc.init(this);


    }

    @OnClick({R.id.m_bt1, R.id.m_bt2, R.id.m_bt3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_bt1:
//                new WeplayDialog(this)
//                        .setTitle("提示")
//                        .setMessage("弹出了一个Dialog")
//                        .setLeftTxt("好的")
//                        .setmOnLeftButtonClickListener(new WeplayDialog.onLeftButtonClickListener() {
//                            @Override
//                            public void onClick() {
//                                ToastUtil.show("好的");
//                            }
//                        })
//                        .setRightTxt("我知道了")
//                        .setmOnRightButtonClickListener(new WeplayDialog.onRightButtonClickListener() {
//                            @Override
//                            public void onClick() {
//                                ToastUtil.show("我知道了");
//                            }
//                        })
//                        .showDialog();


                //点击事件
                int i = mc.mm.rv.computeVerticalScrollRange();//控件高
                int i1 = mc.mm.rv.computeVerticalScrollExtent();//屏幕高
                boolean b = mc.mm.rv.canScrollVertically(1);//是否上拉
                Toast.makeText(this, b + "_" + "控件高" + i + "屏幕高度" + i1, Toast.LENGTH_SHORT).show();

                break;
            case R.id.m_bt2:
                break;
            case R.id.m_bt3:
                openActivity(ViewActivity.class);
                //   mc.listAdapter.showToast();
                break;
        }
    }
//
//    @OnLongClick({R.id.a_m_tv})
//    public boolean onLongClick(View view) {
//        switch (view.getId()) {
//            case R.id.a_m_tv:
//                //长按事件
//                break;
//        }
//
//        return false;
//    }

    /**
     * 此方法用于关闭activity时会调用
     *
     * @return
     */
    @Override
    public Object closeActivity() {
        return "MainActivity";
    }
}
