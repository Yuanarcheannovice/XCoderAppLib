package com.xcoder.demo.app;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2017/3/24 0024.
 * 安卓运行需要的权限
 */

public class AppRuntimePermission {
    /**
     * group:android.permission-group.CONTACTS//联系人
     * permission:android.permission.WRITE_CONTACTS//允许一个应用程序编写用户的联系人数据。
     * permission:android.permission.GET_ACCOUNTS//允许访问的帐户的帐户列表服务。
     * permission:android.permission.READ_CONTACTS//允许应用程序读取用户的联系人数据。
     * <p>
     * group:android.permission-group.PHONE//通话记录
     * permission:android.permission.READ_CALL_LOG//允许应用程序读取用户的通话记录。
     * permission:android.permission.READ_PHONE_STATE//允许只读访问手机状态,包括设备的电话号码、当前的蜂窝网络信息,任何正在进行的调用的状态,任何PhoneAccounts注册设备的列表。
     * permission:android.permission.CALL_PHONE//允许应用程序启动一个电话不经过用户确认的拨号器的用户界面。
     * permission:android.permission.WRITE_CALL_LOG//允许一个应用程序编写(但不是读)用户的通话记录数据。
     * permission:android.permission.USE_SIP//允许一个应用程序使用SIP服务。
     * permission:android.permission.PROCESS_OUTGOING_CALLS//允许应用程序看到的数量被打在一个外向与选择的电话呼叫重定向到一个完全不同的号码或终止调用。
     * permission:com.android.voicemail.permission.ADD_VOICEMAIL//允许一个应用程序添加到系统的语音邮件。
     * <p>
     * group:android.permission-group.CALENDAR//日历
     * permission:android.permission.READ_CALENDAR//允许应用程序读取用户的日历数据。
     * permission:android.permission.WRITE_CALENDAR//允许一个应用程序编写用户的日历数据。
     * <p>
     * group:android.permission-group.CAMERA
     * permission:android.permission.CAMERA//需要能够访问摄像头设备。
     * <p>
     * group:android.permission-group.SENSORS
     * permission:android.permission.BODY_SENSORS//允许应用程序访问数据从用户使用的传感器,用于测量发生了什么他/她的体内,如心率。
     * <p>
     * group:android.permission-group.LOCATION//位置
     * permission:android.permission.ACCESS_FINE_LOCATION//允许应用程序访问的精确位置。
     * permission:android.permission.ACCESS_COARSE_LOCATION//允许应用程序访问近似位置。
     * <p>
     * group:android.permission-group.STORAGE//sd卡
     * permission:android.permission.READ_EXTERNAL_STORAGE//允许一个应用程序从外部存储器读取数据。
     * permission:android.permission.WRITE_EXTERNAL_STORAGE//允许一个应用程序编写外部存储器。
     * <p>
     * group:android.permission-group.MICROPHONE
     * permission:android.permission.RECORD_AUDIO//允许一个应用程序来记录音频。
     * <p>
     * group:android.permission-group.SMS//短信息块
     * permission:android.permission.READ_SMS//允许应用程序读取短信。
     * permission:android.permission.RECEIVE_WAP_PUSH//允许一个应用程序接收WAP推送消息。
     * permission:android.permission.RECEIVE_MMS//允许应用程序监控传入的MMS消息。
     * permission:android.permission.RECEIVE_SMS//允许一个应用程序接收短信。
     * permission:android.permission.SEND_SMS//允许一个应用程序发送SMS消息。
     * permission:android.permission.READ_CELL_BROADCASTS ???
     **/
    public final static String CONTACTS = Manifest.permission.WRITE_CONTACTS;//联系人权限
    public final static String PHONE = Manifest.permission.READ_CALL_LOG;//通话记录权限
    public final static String CALENDAR = Manifest.permission.READ_CALENDAR;//日历
    public final static String CAMERA = Manifest.permission.CAMERA;//相机权限
    public final static String SENSORS = Manifest.permission.BODY_SENSORS;//传感器
    public final static String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;//位置权限
    public final static String STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;//sd卡权限
    public final static String MICROPHONE = Manifest.permission.RECORD_AUDIO;//音频权限
    public final static String SMS = Manifest.permission.READ_SMS;//短信息权限
    public final static int MY_PERMISSIONS_CODE = 1213;//code码
    private String[] permission = {CONTACTS, PHONE, CALENDAR, CAMERA, SENSORS, LOCATION, STORAGE, MICROPHONE, SMS};
    private Activity activity;

    /**
     * @param activity 传入权限组
     */
    public AppRuntimePermission(Activity activity) {
        this.activity = activity;
    }

    /**
     * 检查权限
     */
    public void inspectPer(String[] strPermissions) {
        if (Build.VERSION.SDK_INT < 23) {
            appPerInterface.appPerObtain(true, "不需要获取权限", strPermissions);
            return;
        }
        List<String> listPermission = new ArrayList<String>();//得到用户没有授权的权限
        for (String per : strPermissions) {
            // 检查权限，主要用于检测某个权限是否已经被授予，为PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED。当返回DENIED就需要进行申请授权了。
            if (ContextCompat.checkSelfPermission(activity, per) != PackageManager.PERMISSION_GRANTED) {
                //得到需要进行授权的
                listPermission.add(per);
            }
        }
        if (listPermission.size() > 0) {
            //提示用户需要授权的权限
            String[] goPermission = new String[listPermission.size()];
            listPermission.toArray(goPermission);
            //展出提示框，授权
            ActivityCompat.requestPermissions(activity, goPermission, AppRuntimePermission.MY_PERMISSIONS_CODE);
        } else {
            appPerInterface.appPerObtain(true, "没有需要获取的权限", strPermissions);
        }
    }

    /**
     * @param requestCode:code
     * @param permissions：权限
     * @param grantResults:权限验证结果(-1,禁止（无论用户是否点击不再提示）)
     */
    public void MyRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            if (requestCode == AppRuntimePermission.MY_PERMISSIONS_CODE) {
                if (grantResults == null || grantResults.length <= 0) {
                    return;
                }
                //如果请求被取消了,结果数组是空的。
                if (permissions.length > 0 && grantResults.length > 0) {
                    //循环判断，用户禁止了的权限
                    List<String> strPer = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == -1) {
                            strPer.add(permissions[i]);
                        }
                    }
                    //如果用户禁止某些权限
                    if (strPer.size() > 0) {
                        //这里需要返回的是 被用户禁止的权限
                        String[] goPermission = new String[strPer.size()];
                        strPer.toArray(goPermission);
                        appPerInterface.appPerObtain(false, "部分权限获取失败", goPermission);
                    } else {
                        appPerInterface.appPerObtain(true, "权限获取正常", permissions);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * * 这里先提示用户为什么需要此权限，
     * 然后打开权限设置页面，去设置权限
     *
     * @param strPer          *需要判断的权限
     * @param ifCloseActivity 是否需要关闭activity
     */
    public void setUserPermission(String[] strPer, final Boolean ifCloseActivity) {
        List<String> listPermission = new ArrayList<String>();//得到用户没有授权的权限
        for (String per : strPer) {
            // 检查权限，主要用于检测某个权限是否已经被授予，为PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED。当返回DENIED就需要进行申请授权了。
            if (ContextCompat.checkSelfPermission(activity, per) != PackageManager.PERMISSION_GRANTED) {
                //得到需要进行授权的
                listPermission.add(per);
            }
        }
        if (listPermission.size() > 0) {
            String strMessage = "App需要";
            for (String strP : listPermission) {
                strMessage += getPermissionName(strP) + "，";
            }
            strMessage += "如果禁止，可能会影响app的使用！！！";
            new AlertDialog.Builder(activity).setTitle("权限警告").
                    setMessage(strMessage)
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            String pkg = "com.android.settings";
                            String cls = "com.android.settings.applications.InstalledAppDetails";
                            i.setComponent(new ComponentName(pkg, cls));
                            i.setData(Uri.parse("package:" + activity.getPackageName()));
                            activity.startActivity(i);
                            if (ifCloseActivity) {
                                activity.finish();
                            }
                        }
                    }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (ifCloseActivity) {
                        activity.finish();
                    }
                }
            }).create().show();
        }
    }


    /**
     *
     */
    private AppPerInterface appPerInterface;

    public void setAppPerInterface(AppPerInterface appPerInterface) {
        this.appPerInterface = appPerInterface;
    }

    public interface AppPerInterface {
        public void appPerObtain(Boolean isObtain, String context, String[] strPer);
    }


    private String getPermissionName(String strPermission) {
        if (strPermission.equals(CONTACTS))
            return "联系人权限";
        else if (strPermission.equals(PHONE))
            return "通话记录权限";
        else if (strPermission.equals(CALENDAR))
            return "日历权限";
        else if (strPermission.equals(CAMERA))
            return "相机权限";
        else if (strPermission.equals(SENSORS))
            return "传感器权限";
        else if (strPermission.equals(LOCATION))
            return "位置权限";
        else if (strPermission.equals(STORAGE))
            return "sd卡权限";
        else if (strPermission.equals(MICROPHONE))
            return "音频权限";
        else if (strPermission.equals(SMS))
            return "短信息权限";
        else
            return "";
    }



}
