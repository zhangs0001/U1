package com.u1.gocashm.util;


import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FireBaseUtil {

    public static void fireBaseEvent(Activity activity, String name, long id) {
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity);
        Bundle bundle = new Bundle();
        bundle.putString("apply_id", String.valueOf(id));
        mFirebaseAnalytics.logEvent(name, bundle);
    }
}
