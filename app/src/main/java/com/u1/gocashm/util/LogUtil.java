package com.u1.gocashm.util;

import android.util.Log;

import java.util.Arrays;

/**
 * 简单日志工具
 *
 * @author LiuFeng
 * @data 2021/6/9 17:05
 */
public class LogUtil {
    public static boolean debug = true;
    public static String globalTag = "Upeso";

    public static void setDebug(boolean isDebug) {
        debug = isDebug;
    }

    public static void d(String msg) {
        log(Log.DEBUG, msg);
    }

    public static void v(String msg) {
        log(Log.VERBOSE, msg);
    }

    public static void i(String msg) {
        log(Log.INFO, msg);
    }

    public static void e(String msg) {
        log(Log.ERROR, msg);
    }
    public static void w(String msg) {
        log(Log.WARN, msg);
    }

    public static void d(String tag, String msg) {
        log(Log.DEBUG, tag + " " + msg);
    }

    public static void v(String tag, String msg) {
        log(Log.VERBOSE, tag + " " + msg);
    }

    public static void i(String tag, String msg) {
        log(Log.INFO, tag + " " + msg);
    }

    public static void e(String tag, String msg) {
        log(Log.ERROR, tag + " " + msg);
    }

    public static void w(String tag, String msg) {
        log(Log.WARN, tag + " " + msg);
    }

    public static void log(int priority, String msg) {
        if (debug) {
            print(priority, globalTag, msg);
        }
    }

    /**
     * 打印日志到控制台（解决Android控制台丢失长日志记录）
     *
     * @param priority
     * @param tag
     * @param content
     */
    public static void print(int priority, String tag, String content) {
        // 1. 测试控制台最多打印4062个字节，不同情况稍有出入（注意：这里是字节，不是字符！！）
        // 2. 字符串默认字符集编码是utf-8，它是变长编码一个字符用1~4个字节表示
        // 3. 这里字符长度小于1000，即字节长度小于4000，则直接打印，避免执行后续流程，提高性能哈
        if (content.length() < 1000) {
            Log.println(priority, tag, content);
            return;
        }

        // 一次打印的最大字节数
        int maxByteNum = 4000;

        // 字符串转字节数组
        byte[] bytes = content.getBytes();

        // 超出范围直接打印
        if (maxByteNum >= bytes.length) {
            Log.println(priority, tag, content);
            return;
        }

        // 分段打印计数
        int count = 1;

        // 在数组范围内，则循环分段
        while (maxByteNum < bytes.length) {
            // 按字节长度截取字符串
            String subStr = cutStr(bytes, maxByteNum);

            // 打印日志
            String desc = String.format("分段打印(%s):%s", count++, subStr);
            Log.println(priority, tag, desc);

            // 截取出尚未打印字节数组
            bytes = Arrays.copyOfRange(bytes, subStr.getBytes().length, bytes.length);

            // 可根据需求添加一个次数限制，避免有超长日志一直打印
            /*if (count == 10) {
                break;
            }*/
        }

        // 打印剩余部分
        Log.println(priority, tag, String.format("分段打印(%s):%s", count, new String(bytes)));
    }


    /**
     * 按字节长度截取字节数组为字符串
     *
     * @param bytes
     * @param subLength
     * @return
     */
    private static String cutStr(byte[] bytes, int subLength) {
        // 边界判断
        if (bytes == null || subLength < 1) {
            return null;
        }

        // 超出范围直接返回
        if (subLength >= bytes.length) {
            return new String(bytes);
        }

        // 复制出定长字节数组，转为字符串
        String subStr = new String(Arrays.copyOf(bytes, subLength));

        // 避免末尾字符是被拆分的，这里减1使字符串保持完整
        return subStr.substring(0, subStr.length() - 1);
    }
}
