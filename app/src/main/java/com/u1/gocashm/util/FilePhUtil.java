package com.u1.gocashm.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FilePhUtil {
    private static final String TAG = "FilePhUtil";
    public static final String LINE_SEPARATOR = "@_@";
    public static final String JSON_TYPE_SEPARATOR = "@=@";
    public static final String CACHE_DIR = "mycache";
    private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void createCacheDir(Context context) {
        File file = new File(context.getFilesDir(), CACHE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    public static String readDataFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return "";
        }
        FileInputStream in = null;
        StringBuffer dataBuffer = new StringBuffer();

        if (rwl.readLock().tryLock()) {
            //上读锁,此时其他线程只能读，写操作必须等待读锁释放
            rwl.readLock().lock();
            try {
                in = new FileInputStream(file);
                byte[] buffer = new byte[2048];
                int readByte;
                while ((readByte = in.read(buffer)) != -1) {
                    dataBuffer.append(new String(buffer, 0, readByte));
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                //解读锁
                rwl.readLock().unlock();
                file.delete();
            }
        }
        return dataBuffer.toString();
    }
    public static boolean saveInfoToFile(Context context, String fileName, String record) {
        String dir = context.getFilesDir().getAbsolutePath();
        String fullFileName = dir + File.separator + CACHE_DIR + File.separator + fileName;
        return saveInfoToFile(fullFileName, record);
    }
    public static boolean saveInfoToFile(String fileName, String record) {
        FileWriter writer = null;
        boolean result = false;
        while (true) {
            //上写锁,此时必须等待写锁释放，其他线程才能读和写操作
            if (!rwl.writeLock().tryLock()) {
                continue;
            }
            rwl.writeLock().lock();
            try {
                writer = new FileWriter(fileName, true);
                writer.write(record);
                result = true;
            } catch (IOException e) {
                Log.w(TAG, e.getMessage());
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    Log.w(TAG, e.getMessage());
                }
                //解写锁
                rwl.writeLock().unlock();
            }
            break;
        }
        return result;
    }

    public static String calculateMd5(String str) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Exception while getting Digest", e);
            return null;
        }

        byte[] byteArray = str.getBytes();
        byte[] md5Bytes = digest.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        int size = md5Bytes.length;
        for (int i = 0; i < size; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    public static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
