package com.u1.uclibrary.idcardcamera.global;



import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.u1.uclibrary.idcardcamera.utils.FileUtils;

import java.io.File;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Date         2018/6/10
 * Desc	        ${常量}
 */
public class Constant {
    public static final String APP_NAME = "GoCashCamera";//app名称
    public static  String BASE_DIR = APP_NAME + File.separator;//WildmaIDCardCamera/
    public static  String DIR_ROOT = FileUtils.getRootPath() + File.separator + Constant.BASE_DIR;//文件夹根目录 /storage/emulated/0/WildmaIDCardCamera/


    /**
     * 在Android11版本后重置文件路径
     * @param context
     */
    public static void resetFileRootPath(Context context) {
        if (Build.VERSION.SDK_INT >= 30) {
            DIR_ROOT = getCacheDir(context);
        }
    }

    public static String getCacheDir(Context context) {
        File file;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            file = context.getCacheDir();
        }
        String path = file.getPath() + "/cache";
        File cachePath = new File(path);
        if (!cachePath.exists())
            cachePath.mkdir();
        return path;
    }
}