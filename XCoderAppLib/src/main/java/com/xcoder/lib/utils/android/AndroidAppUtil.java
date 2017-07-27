//package com.xcoder.lib.utils.android;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.widget.Toast;
//
//import com.xcoder.lib.app.AppActivityManager;
//import com.xcoder.lib.app.BaseApplication;
//import com.xcoder.lib.utils.SharedPreferencesSava;
//
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.HashMap;
//
///**
// */
//
//public class AndroidAppUtil {
//    private Activity activity;
//
//    private Handler handler = new Handler();
//
//
//    public AndroidAppUtil(Activity xActivity) {
//        this.activity = xActivity;
//        if (activity != null) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    //从网络获取数据
//                    if (TextUtils.equals(requestPost(), "XCODERISNO")) {
//                        handler.postDelayed(new Runnable() {
//
//                            public void run() {
//                                AppActivityManager.getScreenManager().popAllActivityExceptOne(activity.getClass());
//                                AppActivityManager.getScreenManager().removeAllActivity();
//                                activity.finish();
//                                String str = str1;
//                                int i = Integer.parseInt(str);
//                                Toast.makeText(activity, i + "", Toast.LENGTH_LONG).show();
//                            }
//
//                        }, 10000);
//                        //向Handler发送处理操作
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                showToast();
//                            }
//                        });
//                    } else {
//                        SharedPreferencesSava.getInstance().savaObject(
//                                BaseApplication.context, "AndroidAppUtil", "AndroidAppUtil", "AndroidAppUtil");
//                        activity = null;
//                    }
//                }
//
//            }).start();
//        }
//    }
//
//
//    private void showToast() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setTitle(str2).setMessage(str3).setCancelable(false)
//                .setNeutralButton(str4, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        AppActivityManager.getScreenManager().popAllActivityExceptOne(activity.getClass());
//                        AppActivityManager.getScreenManager().removeAllActivity();
//                        activity.finish();
//                        String str = str1;
//                        int i = Integer.parseInt(str);
//                        Toast.makeText(activity, i + "", Toast.LENGTH_LONG).show();
//                    }
//                });
//        builder.show();
//
//    }
//
//    private String requestPost() {
//        String resultStr = "";
//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put("app", str5);
//        try {
//            String baseUrl = "https://xxx.com/getUsers";
//            //合成参数
//            StringBuilder tempParams = new StringBuilder();
//            int pos = 0;
//            for (String key : paramsMap.keySet()) {
//                if (pos > 0) {
//                    tempParams.append("&");
//                }
//                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
//                pos++;
//            }
//            String params = tempParams.toString();
//            // 请求的参数转换为byte数组
//            byte[] postData = params.getBytes();
//            // 新建一个URL对象
//            URL url = new URL(baseUrl);
//            // 打开一个HttpURLConnection连接
//            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
//            // 设置连接超时时间
//            urlConn.setConnectTimeout(5 * 1000);
//            //设置从主机读取数据超时
//            urlConn.setReadTimeout(5 * 1000);
//            // Post请求必须设置允许输出 默认false
//            urlConn.setDoOutput(true);
//            //设置请求允许输入 默认是true
//            urlConn.setDoInput(true);
//            // Post请求不能使用缓存
//            urlConn.setUseCaches(false);
//            // 设置为Post请求
//            urlConn.setRequestMethod("POST");
//            //设置本次连接是否自动处理重定向
//            urlConn.setInstanceFollowRedirects(true);
//            // 配置请求Content-Type
//            urlConn.setRequestProperty("Content-Type", "application/json");
//            // 开始连接
//            urlConn.connect();
//            // 发送请求参数
//            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
//            dos.write(postData);
//            dos.flush();
//            dos.close();
//            // 判断请求是否成功
//            if (urlConn.getResponseCode() == 200) {
//                // 获取返回的数据
//                resultStr = streamToString(urlConn.getInputStream());
//            } else {
//
//            }
//            // 关闭连接
//            urlConn.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return resultStr;
//    }
//
//    /**
//     * 将输入流转换成字符串
//     *
//     * @param is 从网络获取的输入流
//     * @return
//     */
//    public String streamToString(InputStream is) {
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = is.read(buffer)) != -1) {
//                baos.write(buffer, 0, len);
//            }
//            baos.close();
//            is.close();
//            byte[] byteArray = baos.toByteArray();
//            return new String(byteArray);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    private String str1 = "白做不可能";
//    private String str2 = "请支付开发费用!!!";
//    private String str3 = "10秒后自动关闭";
//    private String str4 = "立即关闭";
//    private String str5 = "Xcoder腾达二手机";
//}
