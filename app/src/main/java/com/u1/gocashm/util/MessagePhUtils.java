package com.u1.gocashm.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.Telephony;
import androidx.core.app.ActivityCompat;

import com.u1.gocashm.PhApplication;
import com.u1.gocashm.util.constant.TimeFormatConfigs;
import com.u1.gocashm.util.model.MessageInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MessagePhUtils {
    public static List<MessageInfo> getMessages() {
        List<MessageInfo> infoList = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(PhApplication.getInstance(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return infoList;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Uri uri = Telephony.Sms.CONTENT_URI;
            String[] projection = new String[]{
                    Telephony.Sms.ADDRESS,
                    Telephony.Sms.DATE,
                    Telephony.Sms.BODY,
                    Telephony.Sms.TYPE
            };
            Cursor cursor = PhApplication.getInstance().getContentResolver().query(uri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER + " LIMIT 300");
            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
                long date = cursor.getLong(cursor.getColumnIndex(Telephony.Sms.DATE));
                String content = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                int type = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.TYPE));

                MessageInfo info = new MessageInfo();
                info.setMessageNumber(getNumber(number));
                info.setMessageContent(content);
                info.setMessageTime(getDate(date));
                info.setMessageType(getType(type));
                infoList.add(info);
            }
            cursor.close();
            return infoList;
        } else {
            Uri uri = Uri.parse("content://sms/");
            String[] projection = new String[]{
                    "address",
                    "date",
                    "body",
                    "type"
            };
            Cursor cursor = PhApplication.getInstance().getContentResolver().query(uri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER + " LIMIT 300");
            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndex("address"));
                long date = cursor.getLong(cursor.getColumnIndex("date"));
                String content = cursor.getString(cursor.getColumnIndex("body"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));

                MessageInfo info = new MessageInfo();
                info.setMessageNumber(getNumber(number));
                info.setMessageContent(content);
                info.setMessageTime(getDate(date));
                info.setMessageType(getType(type));
                infoList.add(info);
            }
            cursor.close();
            return infoList;
        }
    }

    private static String getNumber(String number) {
        return number.replaceAll(" ", "").replaceAll("-", "");
    }

    private static String getDate(long date) {
        return TimeFormatConfigs.format.format(new Date(date));
    }

    private static int getType(int type) {
        switch (type) {
            case 1:
                return MessageInfo.TYPE_RECEIVE;
            default:
                return MessageInfo.TYPE_SEND;
        }
    }
}
