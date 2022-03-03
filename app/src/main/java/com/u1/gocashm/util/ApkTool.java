package com.u1.gocashm.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.u1.gocashm.model.request.AppPhInfo;

import java.util.ArrayList;
import java.util.List;

public class ApkTool {

    /**
     *
     * @param packageManager
     * @param context
     * @return
     */

    public static List<AppPhInfo> getInstallAppList(PackageManager packageManager, Context context) {
        List<AppPhInfo> myAppInfos = new ArrayList<>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            if (packageInfos != null) {
                for (int i = 0; i < packageInfos.size(); i++) {
                    PackageInfo packageInfo = packageInfos.get(i);
                    //如下可获得更多信息
                    AppPhInfo myAppInfo = new AppPhInfo();
                    try {
                        myAppInfo.setPkgName(packageInfo.packageName);
                        myAppInfo.setAppName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
                        myAppInfo.setVersionCode(packageInfo.versionCode);
                        myAppInfo.setVersionName(packageInfo.versionName);
                        myAppInfo.setFirstInstallTime(String.valueOf(packageInfo.firstInstallTime));
                        //myAppInfo.setIsAppActive(isBackground(context, packageInfo.packageName) ? "Y" : "N");
                        myAppInfo.setLastUpdateTime(String.valueOf(packageInfo.lastUpdateTime));
                    } catch (Exception e){
                    }
                    myAppInfos.add(myAppInfo);
                }
            }



        } catch (Exception e) {
            Log.e("Steven", "===============获取应用包信息失败");
        }
        return myAppInfos;
    }

    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */

    public static boolean isBackground(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)) {
                return appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }
}
