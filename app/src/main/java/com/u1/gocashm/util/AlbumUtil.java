package com.u1.gocashm.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AlbumUtil {

    public static JSONArray getPhoneAlbum(Context context){
        Cursor photoCursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);

        if(null==photoCursor){
            return null;
        }
        JSONArray array = new JSONArray();
        while (photoCursor.moveToNext()) {
           // ContactsContract.Contacts.Photo photo = new ContactsContract.Contacts.Photo();
            //照片路径
            String photoPath = photoCursor.getString(photoCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            //照片日期
            long photoDate = photoCursor.getLong(photoCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN));
            //照片标题
            String photoTitle = photoCursor.getString(photoCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            String latitude = photoCursor.getString(photoCursor.getColumnIndex(MediaStore.Images.Media.LATITUDE));
            String longitude = photoCursor.getString(photoCursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoPath, options);
            //照片类型
            String photoType = options.outMimeType;
            //照片长度
            String photoLength = String.valueOf(options.outHeight);
            //照片宽度
            String photoWidth = String.valueOf(options.outWidth);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("date_time",""+photoDate);
                jsonObject.put("image_length",""+photoLength);
                jsonObject.put("name",""+photoTitle);
                jsonObject.put("latitude",""+latitude);
                jsonObject.put("longitude",""+longitude);
                jsonObject.put("image_width",""+photoWidth);
                array.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        photoCursor.close();
        return array;
    }

}
