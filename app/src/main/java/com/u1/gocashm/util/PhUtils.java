package com.u1.gocashm.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.u1.gocashm.BuildConfig;
import com.u1.gocashm.util.constant.PhConstants;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhUtils {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;
    private static NumberFormat nf = new DecimalFormat("#,###");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static String regex = "(<[^>]*>)";

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    public static void showLongToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_LONG).show();
    }

    public static String formatTimeFromLong(long dateTime) {
        return sdf.format(new Date(dateTime));
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static String formatAmount(int d) {
        return nf.format(d).replace(",", ".");
    }

    public static String formatAmount(long d) {
        return nf.format(d).replace(",", ".");
    }

    public static String formatAmount(double d) {
        return nf.format(d).replace(",", ".");
    }

    public static String formatAmount(BigDecimal d) {
        return nf.format(d).replace(",", ".");
    }

    public static String setMoney(Object money) {
        try {
            double number = Double.parseDouble(money.toString());
            money = decimalFormat.format(number).replaceAll(",", ",");
        } catch (Exception e) {

        }
        return money.toString();
    }

    public final static DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##0.00");

    public static void showShortToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "v1.0.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * 验证是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidPassword(String password) {
        if (!TextUtils.isEmpty(password) && password.length() >= 6 && password.length() <= 16 && okPassword(password)) {
            return true;
        }
        return false;
    }

    // 密码验证的正则表达式:由数字和字母组成，并且要同时含有数字和字母，且长度要在6-16位之间
    private static boolean okPassword(String password) {
        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        Matcher match = pattern.matcher(password);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }


    public static String format(String text, Object... objects) {
        return String.format(text, objects);
    }

    public static String getFeedbackUrl(String name, String idNo) {
        if (BuildConfig.DEBUG)
            return "http://119.23.228.125/order/#/feedback/index?lang=vn&channelId=UCASH&name=" + name + "&idNo=" + idNo;
        else
            return "http://udong.ucash.vn/#/feedback/index?lang=vn&channelId=UCASH&name=" + name + "&idNo=" + idNo;
    }

    //这个方法是为了生成标准的可用于跳转的Facebook url
    public static String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            return "fb://page/" + PhConstants.FACEBOOK_CODE;
        } catch (PackageManager.NameNotFoundException e) {
            return PhConstants.FACEBOOK_URL; //要是没有安装就用普通的url
        }
    }
}