package com.u1.gocashm.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.util.KeyBoardListener;
import com.u1.gocashm.util.LiveDataBus;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.constant.PhConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hewei
 * @date 2018/8/27 0027 下午 8:05
 */
public class WebPhActivity extends BasePhTitleBarActivity {
    public static final String ACCEPT_TYPE_VIDEO = "video/*";
    public static final String ACCEPT_TYPE_IMAGE = "image/*";
    private static final String TAG = WebPhActivity.class.getSimpleName();
    private ValueCallback<Uri> mUploadMessagePh;
    private Uri imageUriPh;
    private ValueCallback<Uri[]> mUploadCallbackAboveLPh;
    private boolean isBackFromFileChooserPh;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;

    @BindView(R.id.web_view)
    WebView webViewPh;

    private String titlePh;
    private String urlPh;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        activity = this;
        initData();
        initWeb();
    }

    private void initWeb() {
        KeyBoardListener.getInstance(this).init();
        WebSettings webPhSettings = webViewPh.getSettings();

        webPhSettings.setJavaScriptEnabled(true);
        webPhSettings.setSupportZoom(true);
        webPhSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webPhSettings.setUseWideViewPort(true);
        webPhSettings.setLoadWithOverviewMode(true);
        webViewPh.clearHistory();
        webViewPh.clearFormData();
        webPhSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webPhSettings.setBuiltInZoomControls(true);
        webPhSettings.setAllowFileAccess(true);
        webPhSettings.setAllowContentAccess(true);
        webPhSettings.setDatabaseEnabled(true);
        webPhSettings.setDomStorageEnabled(true);
        webPhSettings.setGeolocationEnabled(true);
        webPhSettings.setAppCacheEnabled(true);
        webPhSettings.setAppCachePath(getApplicationContext().getCacheDir().getPath());
        webPhSettings.setDefaultTextEncodingName("UTF-8");
        if (Build.VERSION.SDK_INT >= 17) {
            // 在sdk4.2以上的系统上继续使用addJavascriptInterface
            webViewPh.addJavascriptInterface(new JSInterface(), "AndroidWebView");
        } else {
            //4.2之前 addJavascriptInterface有安全泄漏风险
            webViewPh.removeJavascriptInterface("searchBoxJavaBridge_");
        }
//        if (RegexUtils.isURL(urlPh)) {
        webViewPh.loadUrl(urlPh);
        Log.e(TAG, "urlPh= " + urlPh);
//        } else {
//            showToast(getString(R.string.error_url_invalid));
//        }
        webViewPh.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webViewPh.canGoBack()) {
                        webViewPh.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        webViewPh.setWebViewClient(new WebViewClient() {
            //当点击链接时,希望覆盖而不是打开新窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                final SslErrorHandler mHandler ;
                mHandler= handler;
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(getString(R.string.a26));
                builder.setPositiveButton(getString(R.string.btn_continue), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHandler.proceed();
                    }
                });
                builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHandler.cancel();
                    }
                });
                builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                            mHandler.cancel();
                            dialog.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        webViewPh.setWebChromeClient(new WebChromeClient() {
            //当WebView进度改变时更新窗口进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                setProgress(newProgress * 100);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                mUploadCallbackAboveLPh = filePathCallback;
                String[] acceptTypes = fileChooserParams == null ? null : fileChooserParams.getAcceptTypes();
                if (acceptTypes != null && acceptTypes.length == 1) {
                    take(acceptTypes[0]);
                } else {
                    take("");
                }
                return true;
            }


            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessagePh = uploadMsg;
                take("");
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessagePh = uploadMsg;
                take(acceptType);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessagePh = uploadMsg;
                take(acceptType);
            }
        });
    }

    private void initData() {
        titlePh = getIntent().getStringExtra(PhConstants.WEB_TITLE);
        urlPh = getIntent().getStringExtra(PhConstants.WEB_URL);
    }

    @Override
    protected void onDestroy() {
        if (webViewPh != null) {
            webViewPh.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);//加载空地址
            webViewPh.clearCache(true);
            webViewPh.clearHistory();
            ((ViewGroup) webViewPh.getParent()).removeView(webViewPh);
            webViewPh.destroy();
            webViewPh = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (isBackFromFileChooserPh && resultCode == RESULT_OK) {
                if (null == mUploadMessagePh && null == mUploadCallbackAboveLPh) return;
                Uri result = data == null ? null : data.getData();
                if (mUploadCallbackAboveLPh != null) {
                    onActivityResultAboveL(requestCode, resultCode, data);
                } else if (mUploadMessagePh != null) {
                    if (result != null) {
                        String path = getPath(getApplicationContext(), result);
                        Uri uri = Uri.fromFile(new File(path));
                        mUploadMessagePh.onReceiveValue(uri);
                    } else {
                        mUploadMessagePh.onReceiveValue(imageUriPh);
                    }
                    mUploadMessagePh = null;
                }
            } else {
                //从文件选择页返回时，需要做如下指定返回操作，否则再次点击拍照时网页无响应
                if (mUploadCallbackAboveLPh != null) {
                    mUploadCallbackAboveLPh.onReceiveValue(new Uri[]{});
                    mUploadCallbackAboveLPh = null;
                } else if (mUploadMessagePh != null) {
                    mUploadMessagePh.onReceiveValue(null);
                    mUploadMessagePh = null;
                }
            }
            isBackFromFileChooserPh = false;
        }
    }

    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || mUploadCallbackAboveLPh == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUriPh};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveLPh.onReceiveValue(results);
            mUploadCallbackAboveLPh = null;
        } else {
            results = new Uri[]{imageUriPh};
            mUploadCallbackAboveLPh.onReceiveValue(results);
            mUploadCallbackAboveLPh = null;
        }
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private void take(String acceptType) {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUriPh = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        //为了解决部分小米机型同时添加拍照和录像功能时只显示最后一个的bug，对于acceptType进行区分
        if (acceptType.equals(ACCEPT_TYPE_VIDEO)) {
            //若为video则录像
            addCameraRecordIntent(cameraIntents);
        } else if (acceptType.equals(ACCEPT_TYPE_IMAGE)) {
            //若为image则拍照
            addCameraIntent(cameraIntents);
        } else {
            //否则同时添加两个以适配其它机型
            addCameraIntent(cameraIntents);
            addCameraRecordIntent(cameraIntents);
        }
        Intent getContentIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getContentIntent.addCategory(Intent.CATEGORY_OPENABLE);
        getContentIntent.setType(ACCEPT_TYPE_IMAGE);
        Intent chooserIntent = Intent.createChooser(getContentIntent, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        isBackFromFileChooserPh = true;
        startActivityForResult(chooserIntent, FILE_CHOOSER_RESULT_CODE);
    }

    private void addCameraRecordIntent(List<Intent> cameraIntents) {
        cameraIntents.add(new Intent(MediaStore.ACTION_VIDEO_CAPTURE));
    }

    private void addCameraIntent(List<Intent> cameraIntents) {
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriPh);
            cameraIntents.add(intent);
        }
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public void initTitleBar() {
        setTitleBack(titlePh);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    class JSInterface {
        @JavascriptInterface
        public void loan(long applyId) {
//            AdjustUtils.addAdjustEvent(AdjustConstants.LoanInfo.P1605C003E001, PhConstants.PhUser.APPLY_ID, applyId);
        }

        @JavascriptInterface
        public void initCameraPermission(String idCard, String phoneNum) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionUtils.permission(PermissionConstants.CAMERA).request();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LiveDataBus.get().with("Privacy_Status").postValue(1);
        LiveDataBus.get().with("Privacy_Status").postValue(1);
    }
}
