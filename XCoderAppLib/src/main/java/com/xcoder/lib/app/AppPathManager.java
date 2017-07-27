package com.xcoder.lib.app;

import android.os.Environment;


import com.xcoder.lib.utils.DateTime;
import com.xcoder.lib.utils.Utils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * app的文件处理
 *
 * @作者:Administrator
 * @修改人:
 * @修改时间:
 * @修改备注:所有的安卓开发者都应该按照这个方向走，不要再随意地再SD卡里建立各式各样的文件夹， 而应该将所有应用数据都在/Android/data/内进行读写
 * 。统一的规范并不违背开源的初衷，
 * 甚至有利于整个生态圈的有序发展。
 * @版本:
 */
public class AppPathManager {
    private static int i = 0;
    public static String appFolderName = "";//app文件的名字

    /**
     * 初始话文件管理类
     *
     * @param str：文件目录名称
     */
    public static void initPathManager(String str) {
        appFolderName = str;
        //设置app默认文件路径
        AppPathManager.setAppPath();
        // 删除七天以前的图片缓存
        AppPathManager.delImgCacheFileBeforeWeek();
    }

    /**
     * @方法说明:返回多个sd卡的该应用私有数据区的files目录
     * @方法名称:getExternalRootFilesCachePath
     * @return：/storage/sdcard0 or sdcard1/Android/data/<包名>/files
     * @返回值:StringBuffer
     */
    public static StringBuffer getExternalRootFilesCachePath() {
        if (BaseApplication.context.getExternalCacheDir() == null) {
            return new StringBuffer(BaseApplication.context.getCacheDir()
                    .getAbsolutePath()).append("/");
        }
        return new StringBuffer(BaseApplication.context.getExternalFilesDir(
                Environment.MEDIA_MOUNTED).getAbsolutePath()).append("/");
    }

    /**
     * @方法说明:返回多个sd卡下该应用私有数据库的缓存目录
     * @方法名称:getExternalRootCachePath
     * @return：/storage/sdcard0 or sdcard1/Android/data/<包名>/caches
     * @返回值:StringBuffer
     */
    public static StringBuffer getExternalRootCachePath() {
        if (BaseApplication.context.getExternalCacheDir() == null) {
            return new StringBuffer(BaseApplication.context.getCacheDir()
                    .getAbsolutePath()).append("/");
        }
        return new StringBuffer(BaseApplication.context.getExternalCacheDir()
                .getAbsolutePath()).append("/");
    }


    /**
     * 设置app的初始目录
     */
    public static void setAppPath() {
        File fileAppPath = null;
        try {
            fileAppPath = new File(getExternalRootFilesCachePath().toString());
            if (!fileAppPath.exists()) {
                fileAppPath.mkdirs();
            }
            fileAppPath = new File(getExternalRootCachePath().toString());
            if (!fileAppPath.exists()) {
                fileAppPath.mkdirs();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return
     * @方法说明:图片保存路径(统一默认为：yyyy-MM-dd_HH:mm:ss.jpg, 方便后面自动清除)
     * @方法名称:getDownloadImagePath
     * @返回值:String
     */
    public static String getDownloadImagePath() {

        String path = getExternalRootCachePath()
                .append("image/")
                .append(DateTime.formatDate(new Date(),
                        DateTime.GROUP_BY_EACH_DAYSM)).append(i).append(".jpg")
                .toString();
        i++;
        return path;
    }

    /**
     * @方法说明:清理掉十天前一天的缓存图片数据
     * @方法名称:delImgCacheFileBeforeWeek
     * @返回值:void
     */
    public static void delImgCacheFileBeforeWeek() {
        String needDelField = DateTime.formatDate(getDateBefore(10),
                DateTime.DATE_PATTERN_2);
        File file = new File(getExternalRootCachePath().toString());
        if (file.exists() && file.isDirectory()) {
            File[] list = file.listFiles();
            for (File file2 : list) {
                String name = file2.getName();
                if (name.contains(needDelField)) {
                    file2.delete();
                }
            }
        }
    }

    /**
     * @param spanDay
     * @return
     * @方法说明:获取以前的时间
     * @方法名称:getDateBefore
     * @返回值:Date
     */
    private static Date getDateBefore(int spanDay) {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - spanDay);
        return now.getTime();
    }

    /**
     * @return
     * @方法说明:清除缓存
     * @方法名称:ManualClearCache
     * @返回值:boolean
     */
    public static void ManualClearCache() {
        File file = new File(getExternalRootCachePath().toString());
        DeleteFile(file);
    }

    /**
     * @方法说明:清除appfiles
     * @方法名称:ManualClearFiles
     * @返回值:void
     */
    public static void ManualClearFiles() {
        File file = new File(getExternalRootFilesCachePath().toString());
        DeleteFile(file);
    }

    /**
     * @方法说明:递归删除文件和文件夹
     * @方法名称:DeleteFile
     * @返回值:void
     */
    public static void DeleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    DeleteFile(f);
                }
                file.delete();
            }
        }
    }



    /**
     * @方法说明：得到主目录文件的路径
     * @方法名称：getAPPPath
     * @返回值：void
     */
    public static StringBuffer getAPPPath() {
        String path = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File sd = Environment.getExternalStorageDirectory();
            path = sd.getPath() + "/" + appFolderName + "/";
        }
        return new StringBuffer(path);
    }

    /**
     * 判断文件夹是否存在，
     * 不存在则创建
     * @return
     */
    public static Boolean ifFolderExit(String filePath){
        File fileAppPath = null;
        try {
            fileAppPath = new File(filePath);
            if (!fileAppPath.exists()) {
                fileAppPath.mkdirs();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * @return
     * @方法说明：生成错误log文件
     * @方法名称：getSaveLogPath()
     * @返回值：String
     */
    public static String getSaveLogPath() {
        if (Utils.isEmpty(getAPPPath().toString())) {
            return "";
        }
        return getAPPPath().append("app_errLog/").toString();
    }

    /**
     * @return
     * @方法说明：apk保存的目录
     * @方法名称：getSaveLogPath()
     * @返回值：String
     */
    public static String getSaveApkPath() {
        if (Utils.isEmpty(getAPPPath().toString())) {
            return "";
        }
        return getAPPPath().append("appApk/").toString();
    }

    //得到日志文件名
    public static String getLogName() {
        return DateTime.formatDate(new Date(), DateTime.GROUP_BY_EACH_DAYSM) + ".txt";
    }


    //得到图片名
    public static String getImageName() {
        return DateTime.formatDate(new Date(), DateTime.GROUP_BY_EACH_DAYSM) + ".jpg";
    }

    /**
     * @return
     * @方法说明：拍照地址
     * @方法名称：getCameraPath
     * @返回值：String
     */
    public static String getCameraPath() {
        if (Utils.isEmpty(getAPPPath().toString())) {
            return "";
        }
        return getAPPPath()
                .append("save_image")
                .append(DateTime.formatDate(new Date(),
                        DateTime.GROUP_BY_EACH_DAYSM)).append(".jpg")
                .toString();
    }

}
