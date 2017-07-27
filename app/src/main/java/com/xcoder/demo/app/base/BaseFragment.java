package com.xcoder.demo.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.http.okhttp.OkHttpUtils;


/**
 * @类名:BaseFragment
 * @类描述:碎片基类，所有fragment子类需继承此类
 * @作者:Administrator
 * @修改人:
 * @修改时间:
 * @修改备注:
 * @版本:
 */
public abstract class BaseFragment extends Fragment {
    //private Context context;// 上下文

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initLayout(inflater);// 布局;
    }

//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		context = activity;
//	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//		if (savedInstanceState == null) {// 数据初始化
//			dataInit();
//		} else {// 数据恢复
//			dataRestore(savedInstanceState);
//		}
        initData();// 事件
    }

    /**
     * @param inflater
     * @return
     * @方法说明:布局
     * @方法名称:layout
     * @返回值:View
     */
    public abstract View initLayout(LayoutInflater inflater);


    /**
     * @方法说明:处理
     * @方法名称:eventDispose
     * @返回值:void
     */
    public abstract void initData();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //判断子类传过来的是什么再做判断
        Object closeO = closeFragment();
        if (closeO instanceof String) {
            //一般返如果是string，那就是关闭Okhttp
            OkHttpUtils.getInstance().cancelTag(closeO);
        } else if (closeO instanceof Fragment) {
            //未处理.v4的fragment
        }
    }

    /**
     * @方法说明:手动释放内存
     * @方法名称:releaseMemory
     * @返回值:void
     */
    public abstract Object closeFragment();

    /**
     * @param pClass
     * @方法说明:启动指定activity
     * @方法名称:openActivity
     * @返回值:void
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * @param pClass
     * @param pBundle
     * @方法说明:启动到指定activity，Bundle传递对象（作用于2个界面之间传递数据）
     * @方法名称:openActivity
     * @返回值:void
     */
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this.getContext(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * @param pAction
     * @方法说明:根据界面name启动到指定界面
     * @方法名称:openActivity
     * @返回值:void
     */
    public void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * @param pAction
     * @param pBundle
     * @方法说明:根据界面name启动到指定界面，Bundle传递对象（作用于2个界面之间传递数据）
     * @方法名称:openActivity
     * @返回值:void
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
     * @返回值:void
     */
    public void openActivityResult(Class<?> pClass) {
        openActivityResult(pClass, null);
    }

    /**
     * @param pClass
     * @param pBundle
     * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作， 而B-Activity又要将，执行操作数据的结果返回给A-Activity
     * ， Bundle传递对象（作用于2个界面之间传递数据）
     * @方法名称:openActivityResult
     * @返回值:void
     */
    public void openActivityResult(Class<?> pClass, Bundle pBundle) {
        openActivityResult(pClass, pBundle, 0);
    }

    /**
     * @param pClass
     * @param pBundle
     * @param requestCode
     * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作， 而B-Activity又要将，执行操作数据的结果返回给A-Activity
     * ， Bundle传递对象（作用于2个界面之间传递数据）
     * @方法名称:openActivityResult
     * @返回值:void
     */
    public void openActivityResult(Class<?> pClass, Bundle pBundle,
                                   int requestCode) {
        Intent intent = new Intent(this.getContext(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivityForResult(intent, requestCode);
    }


}
