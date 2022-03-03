package com.u1.gocashm.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class FilePhUtils {
    /**
     * 检查SD卡是否存在
     */
    public static boolean checkSaveLocationExists() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) ? true : false;
    }

    /**
     * 获取SD路径
     */
    public static String getSdPath(boolean onlySD) {
        boolean hasSD = checkSaveLocationExists();
        if (hasSD) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            if (!onlySD) {
                return Environment.getDownloadCacheDirectory().toString();
            }
        }
        return "";
    }

    /**
     * 获取Cache路径
     */
    public static String getSavePath(Context context, boolean onlySD) {
        boolean hasSD = checkSaveLocationExists();
        if (hasSD) {
            return context.getExternalCacheDir().toString();
        } else {
            if (!onlySD) {
                return context.getCacheDir().toString();
            }
        }
        return null;
    }

    /**
     * 检查SD卡空间是否足够(10M)
     */
    public static boolean isEnoughMem() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long memSize = availableBlocks * blockSize;

        if (memSize < 1024 * 1024 * 10) {
            return false;
        }
        return true;
    }

    /**
     * Byte[]转File
     */
    public static File byteToFile(Context context, byte[] bytes, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(getSavePath(context, false));
            if (!dir.exists() && dir.isDirectory()) { // 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(getSavePath(context, false) + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 获取文件夹大小
     */
    public static long getFileSize(String folderPath) {
        long size = 0;
        File file = new File(folderPath);
        if (file.exists()) {
            File flist[] = file.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getFileSize(flist[i].getAbsolutePath());
                } else {
                    size = size + flist[i].length();
                }
            }
        }
        return size;
    }

    /**
     * 获取文件夹大小 M
     */
    public static String getFileSize(long size) {
        return new DecimalFormat("0.##").format(size * 1.0 / (1024 * 1024))
                + "M";
    }

    /**
     * 删除文件夹
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 计算SDcard剩余大小
     */
    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (Environment.MEDIA_MOUNTED.equals(status)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize / 1024;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return freeSpace;
    }

    /**
     * 复制文件
     */
    public static void copyFile(File sourceFile, File targetFile)
            throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * 读取TXT文件
     */
    public static String readTxtFile(String filePath) {
        String text = "";
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    text += (lineTxt + "\n");
                }
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static File saveImageToFile(Context context, Bitmap mBitmap, String fileName) {
        String dir = getDir(context);
        File file = new File(dir, fileName);
        File parent = file.getParentFile();
        try {
            if (!parent.exists()) {
                parent.mkdirs();
            }
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String getDir(Context context) {
        if (isSdcardEnable()) {
            return context.getExternalFilesDir(null).getAbsolutePath() + "/image/";
//            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName() + "/files/image/";
        } else {
            return context.getFilesDir() + "/image/";
        }
    }

    public static boolean isSdcardEnable() {
        //获取内部存储状态
        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写
        return state.equals(Environment.MEDIA_MOUNTED);
    }

}
