package com.u1.gocashm.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.util.Log;

import com.u1.gocashm.model.request.AuthInfoPhReqModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.util.constant.PhConstants;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;


/**
 * Created by KAEDE on 2018/1/2.
 */
@SuppressLint("CheckResult")
public class PermissionPhUtils {
    public static final int PERMISSION = 100;
    public static final int WRITE_EXTERNAL_STORAGE = 101;
    public static final int READ_PHONE_STATE = 102;
    public static final int CAMERA = 103;
    public static final int ACCESS_FINE_LOCATION = 104;
    public static final int READ_CONTACTS = 105;
    public static final int READ_CALL_LOG = 106;
    public static final int READ_SMS = 107;
    private static final String TAG = PermissionPhUtils.class.getSimpleName();


    public static void onRequestPermissionsResult(final Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults, int requestCode) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE:
                permissionCallBack(activity, permissions, grantResults, "WRITE_EXTERNAL_STORAGE", requestCode);
                break;
            case READ_PHONE_STATE:
                permissionCallBack(activity, permissions, grantResults, "READ_PHONE_STATE", requestCode);
                break;
            case ACCESS_FINE_LOCATION:
                permissionCallBack(activity, permissions, grantResults, "ACCESS_FINE_LOCATION", requestCode);
                break;
            default:
                break;
        }
    }

    private static void permissionCallBack(Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults, String permission, int requestCode) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE:
                getResult(activity, permissions, grantResults, permission, requestCode);
                break;
            case READ_PHONE_STATE:
                getResult(activity, permissions, grantResults, permission, requestCode);
                break;
            case ACCESS_FINE_LOCATION:
                getResult(activity, permissions, grantResults, permission, requestCode);
                break;
            default:
                break;
        }

    }

    private static void getResult(Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults, String permission, int requestCode) {
        if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "拒绝了" + permission + "权限");
            uploadDeviceInfo(activity, false, permission);
        } else {
            Log.e(TAG, "同意了" + permission + "权限");
            uploadDeviceInfo(activity, true, permission);
        }
    }

    public static void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
    }

    public static void requestPermission1(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG}, READ_PHONE_STATE);
        }

    }

    public static void requestStoragePermission(final Activity activity) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            uploadDeviceInfo(activity, true, "WRITE_EXTERNAL_STORAGE");
                        } else {
                            uploadDeviceInfo(activity, false, "WRITE_EXTERNAL_STORAGE");
                        }
                    }
                });

    }

    public static void requestPhoneStatePermission(final Activity activity) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            NetworkPhRequest.appIndex(new PhSubscriber<BasePhModel>() {
                                @Override
                                public void onNext(BasePhModel baseYnModel) {
                                    super.onNext(baseYnModel);
                                }
                            });
                            uploadDeviceInfo(activity, true, "READ_PHONE_STATE");
                        } else {
                            uploadDeviceInfo(activity, false, "READ_PHONE_STATE");
                        }
                    }
                });

    }

    public static void requestReadSmsPermission(final Activity activity) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.READ_SMS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            uploadDeviceInfo(activity, true, "READ_SMS");
                        } else {
                            uploadDeviceInfo(activity, false, "READ_SMS");
                        }
                    }
                });

    }

    public static void requestLocationPermission(final Activity activity) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            saveGpsInfo(activity);
                            uploadDeviceInfo(activity, true, "ACCESS_FINE_LOCATION");
                        } else {
                            uploadDeviceInfo(activity, false, "ACCESS_FINE_LOCATION");
                        }
                    }
                });

    }

    public static void requestPermission(final Activity activity, final String[] reqPermissions) {
        new RxPermissions(activity).requestEach(reqPermissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        switch (permission.name) {
                            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                                if (permission.granted) {
                                    uploadDeviceInfo(activity, true, "WRITE_EXTERNAL_STORAGE");
                                } else {
                                    uploadDeviceInfo(activity, false, "WRITE_EXTERNAL_STORAGE");
                                }
                                break;
                            case Manifest.permission.READ_PHONE_STATE:
                                if (permission.granted) {
                                    uploadDeviceInfo(activity, true, "READ_PHONE_STATE");
                                } else {
                                    uploadDeviceInfo(activity, false, "READ_PHONE_STATE");
                                }
                                break;
                            case Manifest.permission.ACCESS_FINE_LOCATION:
                                if (permission.granted) {
                                    uploadDeviceInfo(activity, true, "ACCESS_FINE_LOCATION");
                                } else {
                                    uploadDeviceInfo(activity, false, "ACCESS_FINE_LOCATION");
                                }
                                break;
                            case Manifest.permission.CAMERA:
                                if (permission.granted) {
                                    uploadDeviceInfo(activity, true, "CAMERA");
                                } else {
                                    uploadDeviceInfo(activity, false, "CAMERA");
                                }
                                break;
                            case Manifest.permission.READ_SMS:
                                if (permission.granted) {
                                    uploadDeviceInfo(activity, true, "READ_SMS");
                                } else {
                                    uploadDeviceInfo(activity, false, "READ_SMS");
                                }
                                break;
                            case Manifest.permission.READ_CONTACTS:
                                if (permission.granted) {
                                    uploadDeviceInfo(activity, true, "READ_CONTACTS");
                                } else {
                                    uploadDeviceInfo(activity, false, "READ_CONTACTS");
                                }
                                break;
                        }
                    }
                });

    }

    public static void uploadDeviceInfo(Activity activity, boolean isAgree, String authType) {
        final SharedPreferencesPhUtil manager = SharedPreferencesPhUtil.getInstance(activity);
        AuthInfoPhReqModel deviceInfo = new AuthInfoPhReqModel();
        deviceInfo.setAuthId(manager.getString(PhConstants.AUTH_ID));
        deviceInfo.setIsAuth(isAgree ? "Y" : "N");
        deviceInfo.setAuthType(authType);
        deviceInfo.setDeviceId(DevicePhUtil.getDeviceUnionID(activity));
        deviceInfo.setPhone(manager.getString(PhConstants.PhUser.USER_PHONE));
        deviceInfo.setIdcard(manager.getString(PhConstants.PhUser.USER_ID_CARD));
//        NetworkPhRequest.uploadDeviceInfo(deviceInfo, new PhSubscriber<AuthInfoPhModel>() {
//            @Override
//            public void onNext(AuthInfoPhModel authInfoPhModel) {
//                super.onNext(authInfoPhModel);
//                Status status = authInfoPhModel.getStatus();
//                if (status != null && "000".equals(status.getCode())) {
//                    manager.saveString(PhConstants.AUTH_ID, authInfoPhModel.getBody().getAuthId());
//                }
//            }
//        });
    }

    private static void saveGpsInfo(Activity activity) {
        String gpsInfo;
        try {
            Location gps = GpsPhUtils.getGPSLocation(activity);
            if (gps == null) {
                Location net = GpsPhUtils.getNetWorkLocation(activity);
                if (net != null) {
                    gpsInfo = net.getLatitude() + "," + net.getLongitude();
                } else {
                    gpsInfo = DevicePhUtil.getCellInfoofLAC(activity) + "," + DevicePhUtil.getCellInfoofCID(activity);
                }
            } else {
                gpsInfo = gps.getLatitude() + "," + gps.getLongitude();
            }
        } catch (Exception e) {
            gpsInfo = "0,0";
        }
        SharedPreferencesPhUtil.getInstance(activity).saveString(PhConstants.GPS, gpsInfo);
    }
}
