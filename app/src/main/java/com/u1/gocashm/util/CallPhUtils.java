package com.u1.gocashm.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import androidx.core.app.ActivityCompat;

import com.u1.gocashm.PhApplication;
import com.u1.gocashm.util.constant.TimeFormatConfigs;
import com.u1.gocashm.util.model.CallInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallPhUtils {
    public static List<CallInfo> getCalls() {
        List<CallInfo> infoList = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(PhApplication.getInstance(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return infoList;
        }

        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection = new String[]{
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
        };

        Cursor cursor = PhApplication.getInstance().getContentResolver().query(uri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER + " LIMIT 1000");
        while (cursor.moveToNext()) {
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            long duration = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

            CallInfo info = new CallInfo();
            info.setCallNumber(getNumber(number));
            info.setStartime(getDate(date));
            info.setEndTime(getDate(date + duration * 1000));
            info.setCallType(getType(type));
            infoList.add(info);
        }
        cursor.close();
        return infoList;
    }

    private static String getNumber(String number) {
        return number.replaceAll(" ", "").replaceAll("-", "");
    }

    private static String getDate(long date) {
        return TimeFormatConfigs.format.format(new Date(date));
    }

    private static String getDuration(long duration) {
        long second = duration % 60;
        long minute = duration / 60;
        long hour = duration / 60 / 60;
        StringBuilder durationBuilder = new StringBuilder();
        if (hour > 0) {
            durationBuilder.append(hour).append("小时");
        }
        if (minute > 0) {
            durationBuilder.append(minute).append("分");
        }
        durationBuilder.append(second).append("秒");
        return durationBuilder.toString();
    }

    private static int getType(int type) {
        switch (type) {
            case CallLog.Calls.INCOMING_TYPE:
                return CallInfo.TYPE_RECEIVE;
            case CallLog.Calls.OUTGOING_TYPE:
                return CallInfo.TYPE_SEND;
            default:
                return CallInfo.TYPE_MISS;
        }
    }
}
