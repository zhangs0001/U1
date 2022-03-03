package com.u1.gocashm.util;

import com.u1.gocashm.PhApplication;
import com.u1.gocashm.util.constant.PhConstants;


public class LoanInfoPhUtils {
    public static int getAmount() {
        return SharedPreferencesPhUtil.getInstance(PhApplication.getContext()).getInt(PhConstants.LOANINFOVIEW_AMOUNT, 0);
    }

    public static String getPayment() {
        return SharedPreferencesPhUtil.getInstance(PhApplication.getContext()).getString(PhConstants.LOANINFOVIEW_PAYMENT);
    }

    public static String getDate() {
        return SharedPreferencesPhUtil.getInstance(PhApplication.getContext()).getString(PhConstants.LOANINFOVIEW_DATE);
    }

    public static long getApplyId() {
        return SharedPreferencesPhUtil.getInstance(PhApplication.getContext()).getLong(PhConstants.PhUser.APPLY_ID, 0L);
    }

    public static long getPreApplyId() {
        return SharedPreferencesPhUtil.getInstance(PhApplication.getContext()).getLong(PhConstants.PhUser.PRE_APPLY_ID, 0L);
    }

    public static int getApplyTerm() {
        return SharedPreferencesPhUtil.getInstance(PhApplication.getContext()).getInt(PhConstants.APPLYTERM, 0);
    }

    public static String getApplyProductCode() {
        return SharedPreferencesPhUtil.getInstance(PhApplication.getContext()).getString(PhConstants.APPLYPRODUCTCODE);
    }

    public static String getApplyPhone() {
        return SharedPreferencesPhUtil.getInstance(PhApplication.getContext()).getString(PhConstants.PhUser.USER_PHONE);
    }

    public static String getApplyName() {
        return SharedPreferencesPhUtil.getInstance(PhApplication.getContext()).getString(PhConstants.PhUser.USER_NAME);
    }
}
