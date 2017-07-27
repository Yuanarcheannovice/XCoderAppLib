package com.xcoder.demo.function.main.view;

import android.os.Bundle;

import com.xcoder.demo.R;
import com.xcoder.demo.app.base.BaseActivity;
import com.xcoder.demo.widget.dialog.AndroidProgressDialog;
import com.xcoder.lib.annotation.ContentView;

/**
 * Created by Administrator on 2017/7/5 0005.
 */
@ContentView(R.layout.activity_view)
public class ViewActivity extends BaseActivity {
    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        for (int i = 0; i < 10; i++) {
            AndroidProgressDialog.show(this);
        }
    }

    @Override
    public Object closeActivity() {
        return "ViewActivity ";
    }
}
