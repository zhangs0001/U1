package com.u1.gocashm.util;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.PermissionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class DevicePhUtil {
    private static final String TAG = "DevicePhUtil";

    public static String createSessionID(Context context) {
        String imei = getDeviceIMEI(context);
        String time = String.valueOf(System.currentTimeMillis());
        String data = imei + time;
        String sessionID = FilePhUtil.calculateMd5(data);
        return sessionID;
    }

    public static boolean isSimulator(Context context) {
        return "000000000000000".equals(getDeviceIMEI(context));
    }

    public static String getNetworkTypeWIFI2G3G(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            String type = "";
            if (ni != null && ni.getTypeName() != null) {
                type = ni.getTypeName().toLowerCase(Locale.US);
                if (!type.equals("wifi")) {
                    type = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                            .getExtraInfo();
                }
            }
            return type;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getWifiMac(Context context) {
        try {
            if (!PermissionUtils.isGranted(Manifest.permission.ACCESS_WIFI_STATE)) {
                Log.e(TAG, "ACCESS_WIFI_STATE permission should be added into AndroidManifest.xml.");
                return "";
            }
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wi = wifiManager.getConnectionInfo();
            String result = wi.getMacAddress();
            if (result == null) {
                result = "";
            }
            return result;
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
            return "";
        }
    }


    //IMEI 仅仅只对Android手机有效
    public static String getDeviceIMEI(Context context) {
        String result = null;
        try {
            if (!PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE)) {
                Log.e(TAG, "READ_PHONE_STATE permission should be added into AndroidManifest.xml.");
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) (context
                        .getSystemService(Context.TELEPHONY_SERVICE));
                result = telephonyManager.getDeviceId();
            }
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
        }
        if (null == result) {
            result = "";
        }
        return result;
    }

    //Pseudo-Unique ID, 这个在任何Android手机中都有效
    public static String getUniqueID() {
        String m_szDevIDShort = "35"; //we make this look like a valid IMEI
        return new StringBuilder(m_szDevIDShort)
                .append(Build.BOARD.length() % 10)
                .append(Build.BRAND.length() % 10)
                .append(Build.CPU_ABI.length() % 10)
                .append(Build.DEVICE.length() % 10)
                .append(Build.DISPLAY.length() % 10)
                .append(Build.HOST.length() % 10)
                .append(Build.ID.length() % 10)
                .append(Build.MANUFACTURER.length() % 10)
                .append(Build.MODEL.length() % 10)
                .append(Build.PRODUCT.length() % 10)
                .append(Build.TAGS.length() % 10)
                .append(Build.TYPE.length() % 10)
                .append(Build.USER.length() % 10)
                .toString();
    }

    // The Android ID, 通常被认为不可信，因为它有时为null
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static int getCellInfoofLAC(Context context) {
        if (!PermissionUtils.isGranted(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Log.e(TAG, "ACCESS_COARSE_LOCATION permission should be added into AndroidManifest.xml.");
            return 0;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) (context
                    .getSystemService(Context.TELEPHONY_SERVICE));
            CellLocation cl = telephonyManager.getCellLocation();
            if (cl instanceof GsmCellLocation) {
                GsmCellLocation location = (GsmCellLocation) cl;
                return location.getLac();
            } else if (cl instanceof CdmaCellLocation) {
                CdmaCellLocation cdma = (CdmaCellLocation) cl;
                return cdma.getNetworkId();
            }
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
        }
        return 0;
    }

    public static int getCellInfoofCID(Context context) {
        if (!PermissionUtils.isGranted(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Log.e(TAG, "ACCESS_COARSE_LOCATION permission should be added into AndroidManifest.xml.");
            return 0;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) (context
                    .getSystemService(Context.TELEPHONY_SERVICE));
            CellLocation cl = telephonyManager.getCellLocation();
            if (cl instanceof GsmCellLocation) {
                GsmCellLocation location = (GsmCellLocation) cl;
                return location.getCid();
            } else if (cl instanceof CdmaCellLocation) {
                CdmaCellLocation cdma = (CdmaCellLocation) cl;
                return cdma.getBaseStationId();
            }
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
        }
        return 0;
    }

    public static String getResolution(final Context context) {
        String resolution = "";
        try {
            final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            final Display display = wm.getDefaultDisplay();
            final DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            resolution = metrics.widthPixels + "x" + metrics.heightPixels;
        } catch (Throwable t) {
            Log.w(TAG, "Device resolution cannot be determined");
        }
        return resolution;
    }

    public static String getDensity(final Context context) {
        String densityStr = "";
        final int density = context.getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                densityStr = "LDPI";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                densityStr = "MDPI";
                break;
            case DisplayMetrics.DENSITY_TV:
                densityStr = "TVDPI";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                densityStr = "HDPI";
                break;
            //todo uncomment in android sdk 25
            //case DisplayMetrics.DENSITY_260:
            //    densityStr = "XHDPI";
            //    break;
            case DisplayMetrics.DENSITY_280:
                densityStr = "XHDPI";
                break;
            //todo uncomment in android sdk 25
            //case DisplayMetrics.DENSITY_300:
            //    densityStr = "XHDPI";
            //    break;
            case DisplayMetrics.DENSITY_XHIGH:
                densityStr = "XHDPI";
                break;
            //todo uncomment in android sdk 25
            //case DisplayMetrics.DENSITY_340:
            //    densityStr = "XXHDPI";
            //    break;
            case DisplayMetrics.DENSITY_360:
                densityStr = "XXHDPI";
                break;
            case DisplayMetrics.DENSITY_400:
                densityStr = "XXHDPI";
                break;
            case DisplayMetrics.DENSITY_420:
                densityStr = "XXHDPI";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                densityStr = "XXHDPI";
                break;
            case DisplayMetrics.DENSITY_560:
                densityStr = "XXXHDPI";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                densityStr = "XXXHDPI";
                break;
            default:
                densityStr = "other";
                break;
        }
        return densityStr;
    }

    public static int getTimezoneOffset() {
        return TimeZone.getDefault().getOffset(new Date().getTime()) / 60000;
    }

    public static String getLocale() {
        final Locale locale = Locale.getDefault();
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    /**
     * 获取设备唯一标识符
     *
     * @return
     */
    public static String getDeviceUnionID(Context context) {
        String uniqueID;
        String imei = getDeviceIMEI(context);
        if (TextUtils.isEmpty(imei)) {
            String mac = DeviceUtils.getMacAddress();
            if (TextUtils.isEmpty(mac)) {
                uniqueID = unionID(context);
            } else {
                uniqueID = mac;
            }
        } else {
            uniqueID = imei;
        }
        return FilePhUtil.calculateMd5(uniqueID);
//        return uniqueID;
    }

    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static String unionID(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists()) {
                    writeInstallationFile(installation);
                }
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}
