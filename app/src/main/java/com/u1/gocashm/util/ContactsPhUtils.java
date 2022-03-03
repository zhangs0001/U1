package com.u1.gocashm.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;

import com.u1.gocashm.PhApplication;
import com.u1.gocashm.util.model.ContactInfo;

import java.util.ArrayList;
import java.util.List;

public class ContactsPhUtils {
    public static List<ContactInfo> getContacts() {
        List<ContactInfo> infoList = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(PhApplication.getInstance(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return infoList;
        }

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED,
                ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED,
                ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP,
                ContactsContract.CommonDataKinds.Phone.LAST_TIME_USED
        };

        Cursor cursor = PhApplication.getInstance().getContentResolver().query(uri, projection, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            long lastCallTime = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED));
            int callCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED));
            long lastTimeUpdate = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP));
            long createTime = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LAST_TIME_USED));

            ContactInfo info = new ContactInfo();
            info.setContactFullname(name);
            info.setContactTelephone(getNumber(number));
            info.setContactRelation("default");
            info.setLastTimeContacted(String.valueOf(lastCallTime));
            info.setTimesContacted(String.valueOf(callCount));
            info.setContactLastUpdatedTimestamp(String.valueOf(lastTimeUpdate));
            info.setContactCreatedAt(String.valueOf(lastTimeUpdate));
            infoList.add(info);
        }
        cursor.close();
        return infoList;
    }

    private static String getNumber(String number) {
        return number.replaceAll(" ", "").replaceAll("-", "");
    }
}
