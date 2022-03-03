package com.u1.gocashm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.multidex.MultiDex;

import ai.advance.liveness.lib.GuardianLivenessDetectionSDK;
import ai.advance.liveness.lib.Market;
import cn.shuzilm.core.Main;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

import com.appsflyer.AppsFlyerLib;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.facebook.FacebookSdk;
import com.u1.gocashm.activity.MainPhActivity;

import java.util.HashSet;
import java.util.Set;

public class PhApplication extends Application {

    public static PhApplication instance;

    public Set<Activity> activities;

    private final Object lock = new Object();

    private static Context mContext;

    private static final String AF_DEV_KEY = "apnstmjnXZs3XNASX76p5a";
    public static final String MAIN_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANutqaItSL8wakExFdd4qjzL6tT0304Im5nvbuwbvQCk+MqXYL5m4SnXZvF3hqY1rjhM5p2zspP7xVK8VCGz3isCAwEAAQ==";


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        instance = this;
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("1111", throwable.getMessage());
            }
        });
        FacebookSdk.sdkInitialize(instance);
        Utils.init(instance);
        LogUtils.getConfig().setGlobalTag("GoCash");
        // todo okgo网络框架
        //OkGo必须调用初始化
       // OkGo.getInstance().init(this);
//        Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程  以下用来捕获程序崩溃异常
        AppsFlyerLib.getInstance().start(instance, AF_DEV_KEY);
        AppsFlyerLib.getInstance().setDebugLog(BuildConfig.DEBUG);
//        JPushInterface.setDebugMode(BuildConfig.DEBUG);    // 设置开启日志,发布时请关闭日志
//        JCoreInterface.testCountryCode("ph");
//        JPushInterface.init(this);            // 初始化 JPush
        try {
            Main.init(instance, MAIN_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GuardianLivenessDetectionSDK.init(instance, BuildConfig.LIVENESS_KEY, BuildConfig.LIVENESS_SECRET, Market.Philippines);
        GuardianLivenessDetectionSDK.setLogEnable(BuildConfig.DEBUG);
    }

    public static PhApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

    public void addActivityToSet(Activity activity) {
        synchronized (lock) {
            if (activity == null) {
                throw new NullPointerException("activity is null");
            }
            if (activities == null) {
                activities = new HashSet<>();
            }
            activities.add(activity);
        }
    }

    public void removeActivityFromSet(Activity activity) {
        if (activities != null) {
            activities.remove(activity);
        }
    }

    public void exitApplication() {
        if (activities != null) {
            synchronized (lock) {
                for (Activity activity : activities) {
                    activity.finish();
                }
            }
            activities.clear();
        }
    }


    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
//            restartUCashApp();//发生崩溃异常时,重启应用
        }
    };

    public void restartUCashApp() {
        Intent intent = new Intent(this, MainPhActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}