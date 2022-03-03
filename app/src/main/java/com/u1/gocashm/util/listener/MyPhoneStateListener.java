package com.u1.gocashm.util.listener;


import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;


import com.u1.gocashm.PhApplication;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.constant.PhConstants;

import java.lang.reflect.Method;

/**
 * @author ADMIN
 */
public class MyPhoneStateListener extends PhoneStateListener {

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        Method method1;

        try {
            method1 = signalStrength.getClass().getMethod("getDbm");
            Object dbm = method1.invoke(signalStrength);
            SharedPreferencesPhUtil.getInstance(PhApplication.getInstance()).saveString(PhConstants.PHONE_DBM, dbm.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
