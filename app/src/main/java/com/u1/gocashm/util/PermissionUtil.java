package com.u1.gocashm.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class PermissionUtil {

    private static int mRequestCode = -1;
    private static PermissionCheckCallBack mCallBack;
    private static String[] showPermissions;
    private static String[] requestPermissions;

    /**
     * 检测权限
     *
     * @return true：已授权； false：未授权；
     */
    public static boolean checkPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    /**
     * 检测多个权限
     *
     * @return 未授权的权限
     */
    public static List<String> checkMorePermissions(Context context, String[] permissions) {
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (!checkPermission(context, permissions[i]))
                permissionList.add(permissions[i]);
        }
        return permissionList;
    }

    /**
     * 请求权限
     */
    public static void requestPermission(Context context, String permission, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, requestCode);
    }

    /**
     * 请求多个权限
     */
    public static void requestMorePermissions(Context context, List permissionList, int requestCode) {
        String[] permissions = (String[]) permissionList.toArray(new String[permissionList.size()]);
        requestMorePermissions(context, permissions, requestCode);
    }

    /**
     * 请求多个权限
     */
    public static void requestMorePermissions(Context context, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
    }

    /**
     * 判断是否已拒绝过权限
     *
     * @return
     * @describe :如果应用之前请求过此权限但用户拒绝，此方法将返回 true;
     * -----------如果应用第一次请求权限或 用户在过去拒绝了权限请求，
     * -----------并在权限请求系统对话框中选择了 Don't ask again 选项，此方法将返回 false。
     */
    public static boolean judgePermission(Context context, String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission))
            return true;
        else
            return false;
    }

    /**
     * 检测权限并请求权限：如果没有权限，则请求权限
     */
    public static void checkAndRequestPermission(Context context, String permission, int requestCode) {
        if (!checkPermission(context, permission)) {
            requestPermission(context, permission, requestCode);
        }
    }

    /**
     * 检测并请求多个权限
     */
    public static void checkAndRequestMorePermissions(Context context, String[] permissions, int requestCode) {
        List<String> permissionList = checkMorePermissions(context, permissions);
        requestMorePermissions(context, permissionList, requestCode);
    }


    /**
     * 检测权限
     *
     * @describe：具体实现由回调接口决定
     */
    public static void checkPermission(Context context, String permission, PermissionCheckCallBack callBack) {
        if (checkPermission(context, permission)) { // 用户已授予权限
            callBack.onHasPermission();
        } else {
            if (judgePermission(context, permission))  // 用户之前已拒绝过权限申请
                callBack.onUserHasAlreadyTurnedDown(permission);
            else                                       // 用户之前已拒绝并勾选了不在询问、用户第一次申请权限。
                callBack.onUserHasAlreadyTurnedDownAndDontAsk(permission);
        }
    }

    /**
     * 检测多个权限
     *
     * @describe：具体实现由回调接口决定
     */
    public static void checkMorePermissions(Context context, String[] permissions, PermissionCheckCallBack callBack) {
        List<String> permissionList = checkMorePermissions(context, permissions);
        if (permissionList.size() == 0) {  // 用户已授予权限
            callBack.onHasPermission();
        } else {
            boolean isFirst = true;
            for (int i = 0; i < permissionList.size(); i++) {
                String permission = permissionList.get(i);
                if (judgePermission(context, permission)) {
                    isFirst = false;
                    break;
                }
            }
            String[] unauthorizedMorePermissions = (String[]) permissionList.toArray(new String[permissionList.size()]);
            if (isFirst)// 用户之前已拒绝过权限申请
                callBack.onUserHasAlreadyTurnedDownAndDontAsk(unauthorizedMorePermissions);
            else       // 用户之前已拒绝并勾选了不在询问、用户第一次申请权限。
                callBack.onUserHasAlreadyTurnedDown(unauthorizedMorePermissions);

        }
    }

    public static void checkMorePermissions(final Context context, final int requestCode, String[] permissionShow, final String[] permissions, PermissionCheckCallBack callBack, DisallowCallBack disallowCallBack){
        mRequestCode=requestCode;
        mCallBack=callBack;
        showPermissions=permissionShow;
        requestPermissions=permissions;
        mDisallowCallBack = disallowCallBack;
        List<String> permissionList = checkMorePermissions(context, permissions);
        if (permissionList.size() == 0) {  // 用户已授予权限
            callBack.onHasPermission();
        } else {
            boolean isFirst = true;
            for (int i = 0; i < permissionList.size(); i++) {
                String permission = permissionList.get(i);
                if (judgePermission(context, permission)) {
                    isFirst = false;
                    break;
                }
            }
            String[] unauthorizedMorePermissions = (String[]) permissionList.toArray(new String[permissionList.size()]);
            if (isFirst){
                // 用户之前已拒绝过权限申请
//                callBack.onUserHasAlreadyTurnedDownAndDontAsk(unauthorizedMorePermissions);
                PermissionUtil.requestMorePermissions(context, permissions, requestCode);
            } else {
                // 用户之前已拒绝并勾选了不在询问、用户第一次申请权限。
//                callBack.onUserHasAlreadyTurnedDown(unauthorizedMorePermissions);
                if (showPermissions!=null){
                    PermissionUtil.showExplainDialog(context,showPermissions, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PermissionUtil.requestMorePermissions(context, permissions, requestCode);
                        }
                    });
                }

            }

        }
    }

    public static void checkPermission(final Context context, final int requestCode, boolean showTips,final String permission, PermissionCheckCallBack callBack, DisallowCallBack disallowCallBack){
        mRequestCode=requestCode;
        mCallBack=callBack;
        mDisallowCallBack = disallowCallBack;
        boolean hasPermission = checkPermission(context, permission);
        if (hasPermission) {  // 用户已授予权限
            callBack.onHasPermission();
        } else {
            boolean isFirst = true;
            if (judgePermission(context, permission)){
                isFirst = false;
            }
            if (isFirst){
                // 用户之前已拒绝过权限申请
                PermissionUtil.requestPermission(context, permission, requestCode);
            } else {
                if (showTips){
                    if (disallowCallBack!=null){
                        disallowCallBack.showTips(permission);
                    }
                }
                // 用户之前已拒绝并勾选了不在询问、用户第一次申请权限。
//                if (disallowCallBack!=null){
//                    disallowCallBack.disallow();
//                }
            }

        }
    }

    public static void checkMorePermissions(final Context context, final int requestCode, String[] permissionShow, final String[] permissions, PermissionCheckCallBack callBack) {
        checkMorePermissions(context,requestCode,permissionShow,permissions,callBack,null);
    }

    public static void onRequestMorePermissionsResult(Context context, int requestCode, String[] permissions) {
        if (requestCode==mRequestCode){
            boolean isBannedPermission = false;
            List<String> permissionList = checkMorePermissions(context, permissions);
            if (permissionList.size() == 0){
//                callback.onHasPermission();
                if (mCallBack!=null){
                    mCallBack.onHasPermission();
                }
            }else {
                for (int i = 0; i < permissionList.size(); i++) {
                    if (!judgePermission(context, permissionList.get(i))) {
                        isBannedPermission = true;
                        break;
                    }
                }
                //　已禁止再次询问权限
                if (isBannedPermission){
                    if (mDisallowCallBack!=null){
                        mDisallowCallBack.banned();
                    }else {
                        //                    callback.onUserHasAlreadyTurnedDownAndDontAsk(permissions);
                        if (showPermissions!=null&&showPermissions.length>0){
                            Toast.makeText(context, "请开启"+ Arrays.toString(showPermissions), Toast.LENGTH_SHORT).show();
                        }
                        PermissionUtil.showToAppSettingDialog(context);
                    }

                } else{
                    if (mDisallowCallBack!=null){
                        mDisallowCallBack.disallow();
                    }else {
                        // 拒绝权限
//                    callback.onUserHasAlreadyTurnedDown(permissions);
                        if (showPermissions!=null&&showPermissions.length>0){
                            Toast.makeText(context, "请开启"+ Arrays.toString(showPermissions), Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        }
    }


    /**
     * 检测并申请权限
     */
    public static void checkAndRequestPermission(Context context, String permission, int requestCode, PermissionRequestSuccessCallBack callBack) {
        if (checkPermission(context, permission)) {// 用户已授予权限
            callBack.onHasPermission();
        } else {
            requestPermission(context, permission, requestCode);
        }
    }

    /**
     * 检测并申请多个权限
     */
    public static void checkAndRequestMorePermissions(Context context, String[] permissions, int requestCode, PermissionRequestSuccessCallBack callBack) {
        List<String> permissionList = checkMorePermissions(context, permissions);
        if (permissionList.size() == 0) {  // 用户已授予权限
            callBack.onHasPermission();
        } else {
            requestMorePermissions(context, permissionList, requestCode);
        }
    }

    /**
     * 判断权限是否申请成功
     */
    public static boolean isPermissionRequestSuccess(int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    /**
     * 用户申请权限返回
     */
    public static void onRequestPermissionResult(Context context, String permission, int[] grantResults, PermissionCheckCallBack callback) {
        if (PermissionUtil.isPermissionRequestSuccess(grantResults)) {
            callback.onHasPermission();
        } else {
            if (PermissionUtil.judgePermission(context, permission)) {
                callback.onUserHasAlreadyTurnedDown(permission);
            } else {
                callback.onUserHasAlreadyTurnedDownAndDontAsk(permission);
            }
        }
    }

    /**
     * 用户申请多个权限返回
     */
    public static void onRequestMorePermissionsResult(Context context, String[] permissions, PermissionCheckCallBack callback) {
        boolean isBannedPermission = false;
        List<String> permissionList = checkMorePermissions(context, permissions);
        if (permissionList.size() == 0)
            callback.onHasPermission();
        else {
            for (int i = 0; i < permissionList.size(); i++) {
                if (!judgePermission(context, permissionList.get(i))) {
                    isBannedPermission = true;
                    break;
                }
            }
            //　已禁止再次询问权限
            if (isBannedPermission)
                callback.onUserHasAlreadyTurnedDownAndDontAsk(permissions);
            else // 拒绝权限
                callback.onUserHasAlreadyTurnedDown(permissions);
        }

    }


    /**
     * 跳转到权限设置界面
     */
    public static void toAppSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }


    /**
     * 解释权限的dialog
     *
     */
    public static void showExplainDialog(Context context, String[] permission, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(context)
                .setTitle("申请权限")
                .setMessage("为了您能更好的使用应用，请您开放" + Arrays.toString(permission))
                .setPositiveButton("确定", onClickListener)
                .show();
    }

    /**
     * 显示前往应用设置Dialog
     *
     */
    public static void showToAppSettingDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle("需要权限")
                .setMessage("我们需要相关权限，才能实现功能，点击前往，将转到应用的设置界面，请开启应用的相关权限。")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtil.toAppSetting(context);
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    public interface PermissionRequestSuccessCallBack {
        /**
         * 用户已授予权限
         */
        void onHasPermission();
    }


    public interface PermissionCheckCallBack {

        /**
         * 用户已授予权限
         */
        void onHasPermission();

        /**
         * 用户已拒绝过权限
         *
         * @param permission:被拒绝的权限
         */
        void onUserHasAlreadyTurnedDown(String... permission);

        /**
         * 用户已拒绝过并且已勾选不再询问选项、用户第一次申请权限;
         *
         * @param permission:被拒绝的权限
         */
        void onUserHasAlreadyTurnedDownAndDontAsk(String... permission);
    }

    private static DisallowCallBack mDisallowCallBack;

    public static void clearDisallowCallBack(){
        mDisallowCallBack = null;
    }


    /**
     * 用户拒绝权限
     */
    public interface DisallowCallBack{
        /**拒绝权限*/
        void disallow();
        /**不再询问*/
        void banned();
        void showTips(String permission);
    }


}
