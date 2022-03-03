package com.u1.gocashm.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.u1.gocashm.PhApplication;
import com.u1.gocashm.model.response.ScheduleCalcPhModel;
import com.u1.gocashm.util.constant.PhConstants;


public class TokenUtils {

    public static boolean TokenCheck(Context context) {
        String accessToken = SharedPreferencesPhUtil.getInstance(context).getString(PhConstants.PhUser.ACCESS_TOKEN);
        Log.e("zhangs", "TokenCheck: " + accessToken );
        if (TextUtils.isEmpty(accessToken)) {
            return false;
        }
        return true;
    }

    public static String getToken(Context context) {
        return SharedPreferencesPhUtil.getInstance(context).getString(PhConstants.PhUser.ACCESS_TOKEN);
    }

    public static void setToken(Context context, String token) {
        SharedPreferencesPhUtil.getInstance(context).saveString(PhConstants.PhUser.ACCESS_TOKEN, token);
    }

    public static String getName(Activity activity) {
        return SharedPreferencesPhUtil.getInstance(activity).getString(PhConstants.PhUser.USER_NAME);
    }

    public static void setName(Context context, String name) {
        SharedPreferencesPhUtil.getInstance(context).saveString(PhConstants.PhUser.USER_NAME, name);
    }


    public static String getFirstName(Activity activity) {
        return SharedPreferencesPhUtil.getInstance(activity).getString(PhConstants.PhUser.FIRST_NAME);
    }

    public static String getPhone(Activity activity) {
        return SharedPreferencesPhUtil.getInstance(activity).getString(PhConstants.PhUser.USER_PHONE);
    }

    public static long getApplyId(Activity activity) {
        return SharedPreferencesPhUtil.getInstance(activity).getLong(PhConstants.PhUser.APPLY_ID, 0L);
    }

    public static int getUserId(Activity activity) {
        return SharedPreferencesPhUtil.getInstance(activity).getInt(PhConstants.PhUser.USER_ID, 0);
    }

    public static ScheduleCalcPhModel.ScheduleCalc getCalcModel() {
        return SharedPreferencesPhUtil.getInstance(PhApplication.getInstance()).getCalcModel();
    }

    public static String getRepayCode(Context context) {
        return SharedPreferencesPhUtil.getInstance(context).getString(PhConstants.REPAY_CODE);
    }

    public static void setRepayCode(Context context, String repayCode) {
        SharedPreferencesPhUtil.getInstance(context).saveString(PhConstants.REPAY_CODE, repayCode);
    }

    public static Integer getOrderId(Context context) {
        return SharedPreferencesPhUtil.getInstance(context).getInt(PhConstants.ORDER_ID,0);
    }

    public static void setOrderId(Context context, Integer orderId) {
        SharedPreferencesPhUtil.getInstance(context).saveInt(PhConstants.ORDER_ID, orderId);
    }
}
