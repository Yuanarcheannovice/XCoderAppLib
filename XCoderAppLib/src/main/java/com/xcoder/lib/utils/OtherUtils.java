package com.xcoder.lib.utils;

/**
 * Created by Administrator on 2016/12/3 0003.
 */

public class OtherUtils {

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

}
