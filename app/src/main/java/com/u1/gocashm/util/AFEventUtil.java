package com.u1.gocashm.util;

import android.app.Activity;

import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;


public class AFEventUtil {

    public static void afEvent(String eventName, Activity activity, Integer orderId) {
        Map<String, Object> eventValues = new HashMap<>();
        eventValues.put(eventName, TokenUtils.getPhone(activity) + "##" + orderId);
        AppsFlyerLib.getInstance().logEvent(activity, eventName, eventValues);
    }

    public static void afEvent(String eventName, Activity activity, String phone, String orderId) {
        Map<String, Object> eventValues = new HashMap<>();
        eventValues.put(eventName, phone);
        eventValues.put(eventName, orderId);
        AppsFlyerLib.getInstance().logEvent(activity, eventName, eventValues);
    }
}
