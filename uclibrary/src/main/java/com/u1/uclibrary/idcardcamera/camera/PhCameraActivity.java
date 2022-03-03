package com.u1.uclibrary.idcardcamera.camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.exifinterface.media.ExifInterface;

import com.blankj.utilcode.util.GsonUtils;
import com.firstinvest.chlibrary.R;
import com.google.gson.Gson;
import com.u1.uclibrary.idcardcamera.cropper.CropImageView;
import com.u1.uclibrary.idcardcamera.cropper.CropListener;
import com.u1.uclibrary.idcardcamera.global.Constant;
import com.u1.uclibrary.idcardcamera.global.ExifInfo;
import com.u1.uclibrary.idcardcamera.global.ImageInfo;
import com.u1.uclibrary.idcardcamera.utils.FileUtils;
import com.u1.uclibrary.idcardcamera.utils.ImageUtils;
import com.u1.uclibrary.idcardcamera.utils.PermissionUtils;

import java.io.IOException;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Date         2018/6/24
 * Desc	        ${拍照界面}
 */
public class PhCameraActivity extends Activity implements View.OnClickListener {

    public final static int TYPE_IDCARD_FRONT = 1;//身份证正面
    public final static int TYPE_IDCARD_BACK = 2;//身份证反面
    public final static int TYPE_WORK = 3;//身份证反面
    public final static int REQUEST_CODE = 0X11;//请求码
    public final static int RESULT_CODE = 0X12;//结果码
    public final static int PERMISSION_CODE_FIRST = 0x13;//权限请求码
    public final static String TAKE_TYPE = "take_type";//拍摄类型标记
    public final static String IMAGE_PATH = "image_path";//图片路径标记
    public final static String IMAGE_EXIF = "image_exif";//图片路径标记
    public final static String CARD_TYPE = "card_type";
    private static final String TAG = PhCameraActivity.class.getSimpleName();
    public static int mType;//拍摄类型
    public static Activity mActivity;
    private boolean isToast = true;//是否弹吐司，为了保证for循环只弹一次

    private CropImageView mCropImageView;
    private Bitmap mCropBitmap;
    private CameraPreview mCameraPreview;
    private View mLlCameraCropContainer;
    private ImageView mIvCameraCrop;
    private ImageView mIvCameraFlash;
    private ImageView mIvCameraChange;
    private View mLlCameraOption;
    private View mLlCameraResult;
    private TextView mViewCameraCropBottom;
    private String cardType;
    private ExifInterface exifInterface;
    private ImageInfo imageInfo;

    /**
     * 跳转到拍照界面
     *
     * @param activity
     * @param type     拍摄类型
     */
    public static void toCameraActivity(Activity activity, int type) {
        Intent intent = new Intent(activity, PhCameraActivity.class);
        intent.putExtra(TAKE_TYPE, type);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * 获取图片路径
     *
     * @param data
     * @return
     */
    public static String getImagePath(Intent data) {
        if (data != null) {
            return data.getStringExtra(IMAGE_PATH);
        }
        return "";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*动态请求需要的权限*/
        boolean checkPermissionFirst = PermissionUtils.checkPermissionFirst(this, PERMISSION_CODE_FIRST,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
        if (checkPermissionFirst) {
            init();
        }
    }

    /**
     * 处理请求权限的响应
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 请求权限结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPermissions = true;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults.length > i && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isPermissions = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) { //用户选择了"不再询问"
                    if (isToast) {
                        Toast.makeText(this, "Please manually open the permissions required by the app", Toast.LENGTH_SHORT).show();
                        isToast = false;
                    }
                }
            }
        }
        isToast = true;
        if (isPermissions) {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "允许所有权限");
            init();
        } else {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "有权限不允许");
            finish();
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void init() {
        Constant.resetFileRootPath(getApplicationContext());
        setContentView(R.layout.activity_camera);
        mType = getIntent().getIntExtra(TAKE_TYPE, 0);
        cardType = getIntent().getStringExtra(CARD_TYPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initView();
        initListener();
    }

    private void initView() {
        mCameraPreview = findViewById(R.id.camera_preview);
        mLlCameraCropContainer = findViewById(R.id.ll_camera_crop_container);
        mIvCameraCrop = findViewById(R.id.iv_camera_crop);
        mIvCameraFlash = (ImageView) findViewById(R.id.iv_camera_flash);
        mIvCameraChange = (ImageView) findViewById(R.id.iv_camera_change);
        mLlCameraOption = findViewById(R.id.ll_camera_option);
        mLlCameraResult = findViewById(R.id.ll_camera_result);
        mCropImageView = findViewById(R.id.crop_image_view);
        mViewCameraCropBottom = (TextView) findViewById(R.id.view_camera_crop_bottom);

        //获取屏幕最小边，设置为cameraPreview较窄的一边
        float screenMinSize = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        //根据screenMinSize，计算出cameraPreview的较宽的一边，长宽比为标准的16:9
        float maxSize = screenMinSize / 9.0f * 16.0f;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) maxSize, (int) screenMinSize);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mCameraPreview.setLayoutParams(layoutParams);

        float height = (int) (screenMinSize * 0.75);
        float width = (int) (height * 75.0f / 47.0f);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
        mLlCameraCropContainer.setLayoutParams(containerParams);
        mIvCameraCrop.setLayoutParams(cropParams);

        switch (cardType) {
            case "Driver’s License ":
                mIvCameraCrop.setImageResource(R.drawable.drviver_bg);
                break;
            case "SSS card ":
                mIvCameraCrop.setImageResource(R.drawable.sss_bg);
                break;
            case "UMID ":
                mIvCameraCrop.setImageResource(R.drawable.umid_bg);
                break;
            default:
                mIvCameraChange.setVisibility(View.VISIBLE);
//                mIvCameraCrop.setVisibility(View.GONE);
//                mViewCameraCropBottom.setVisibility(View.GONE);
                break;
        }

        /*增加0.5秒过渡界面，解决个别手机首次申请权限导致预览界面启动慢的问题*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCameraPreview.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 500);
    }

    private void initListener() {
        mCameraPreview.setOnClickListener(this);
        mIvCameraFlash.setOnClickListener(this);
        mIvCameraChange.setOnClickListener(this);
        findViewById(R.id.iv_camera_close).setOnClickListener(this);
        findViewById(R.id.iv_camera_take).setOnClickListener(this);
        findViewById(R.id.iv_camera_result_ok).setOnClickListener(this);
        findViewById(R.id.iv_camera_result_cancel).setOnClickListener(this);
        if (cardType.equals("hold")) {
            mIvCameraFlash.setVisibility(View.GONE);
            mIvCameraChange.setVisibility(View.VISIBLE);
            mCameraPreview.switchFontCamera();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.camera_preview) {
            mCameraPreview.focus();
        } else if (id == R.id.iv_camera_close) {
            finish();
        } else if (id == R.id.iv_camera_take) {
            takePhoto();
        } else if (id == R.id.iv_camera_flash) {
            boolean isFlashOn = mCameraPreview.switchFlashLight();
            mIvCameraFlash.setImageResource(isFlashOn ? R.drawable.camera_flash_on : R.drawable.camera_flash_off);
        } else if (id == R.id.iv_camera_result_ok) {
            confirm();
        } else if (id == R.id.iv_camera_result_cancel) {
            mCameraPreview.setEnabled(true);
            mCameraPreview.startPreview();
            mIvCameraFlash.setImageResource(R.drawable.camera_flash_off);
            setTakePhotoLayout();
        } else if (id == R.id.iv_camera_change) {
            mCameraPreview.onChange();
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        mCameraPreview.setEnabled(false);
        mCameraPreview.takePhoto(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(final byte[] data, Camera camera) {
                camera.stopPreview();
                //子线程处理图片，防止ANR
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder builder = new StringBuilder();
                        String imagePath = "";
                        imageInfo = new ImageInfo();
                        if (mType == TYPE_IDCARD_FRONT) {
                            imagePath = builder.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append("_").append("idCardFrontCrop.jpg").toString();
                            imageInfo.setImage_name("idCardFrontCrop.jpg");
                            imageInfo.setImage_type("id_front");
                        } else if (mType == TYPE_IDCARD_BACK) {
                            imagePath = builder.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append("_").append("idCardHold.jpg").toString();
                            imageInfo.setImage_name("idCardHold.jpg");
                            imageInfo.setImage_type("selfie");
                        } else if (mType == TYPE_WORK) {
                            imagePath = builder.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append("_").append("work.jpg").toString();
                        }
                        FileUtils.writeFile(imagePath, data);
//                        File file = new File(imagePath);
//                        imagePath = Constant.DIR_ROOT + "123.jpg";
                        try {
                            exifInterface = new ExifInterface(imagePath);
                            imageInfo.setMake(exifInterface.getAttribute(ExifInterface.TAG_MAKE));
                            imageInfo.setModel(exifInterface.getAttribute(ExifInterface.TAG_MODEL));
                            imageInfo.setSofeware(exifInterface.getAttribute(ExifInterface.TAG_SOFTWARE));
                            imageInfo.setDatetime(exifInterface.getAttribute(ExifInterface.TAG_DATETIME));
                            imageInfo.setDatetimeoriginal(exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL));
                            imageInfo.setIMAGEWIDTH(exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL));
                            imageInfo.setIMAGEHEIGHT(exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL));
                            imageInfo.setPIXELXDIMENSION(exifInterface.getAttributeInt(ExifInterface.TAG_PIXEL_X_DIMENSION, ExifInterface.ORIENTATION_NORMAL));
                            imageInfo.setPIXELYDIMENSION(exifInterface.getAttributeInt(ExifInterface.TAG_PIXEL_Y_DIMENSION, ExifInterface.ORIENTATION_NORMAL));
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
                            e.printStackTrace();
                        }
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                        /*计算裁剪位置*/
//                        float left, top, right, bottom;
//                        left = ((float) mLlCameraCropContainer.getLeft() - (float) mCameraPreview.getLeft()) / (float) mCameraPreview.getWidth();
//                        top = (float) mIvCameraCrop.getTop() / (float) mCameraPreview.getHeight();
//                        right = (float) mLlCameraCropContainer.getRight() / (float) mCameraPreview.getWidth();
//                        bottom = (float) mIvCameraCrop.getBottom() / (float) mCameraPreview.getHeight();
//
//                        /*自动裁剪*/
//                        mCropBitmap = Bitmap.createBitmap(bitmap,
//                                (int) (left * (float) bitmap.getWidth()),
//                                (int) (top * (float) bitmap.getHeight()),
//                                (int) ((right - left) * (float) bitmap.getWidth()),
//                                (int) ((bottom - top) * (float) bitmap.getHeight()));

                        /*手动裁剪*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //将裁剪区域设置成与扫描框一样大
//                                mCropImageView.setLayoutParams(new LinearLayout.LayoutParams(mIvCameraCrop.getWidth(), bitmap.getHeight()));
                                setCropLayout();
                                mCropImageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    /**
     * 设置裁剪布局
     */
    private void setCropLayout() {
        mIvCameraCrop.setVisibility(View.GONE);
        mCameraPreview.setVisibility(View.GONE);
        mLlCameraOption.setVisibility(View.GONE);
        mCropImageView.setVisibility(View.VISIBLE);
        mLlCameraResult.setVisibility(View.VISIBLE);
        mViewCameraCropBottom.setText("");
    }

    /**
     * 设置拍照布局
     */
    private void setTakePhotoLayout() {
        mIvCameraCrop.setVisibility(View.VISIBLE);
        mCameraPreview.setVisibility(View.VISIBLE);
        mLlCameraOption.setVisibility(View.VISIBLE);
        mCropImageView.setVisibility(View.GONE);
        mLlCameraResult.setVisibility(View.GONE);
        mViewCameraCropBottom.setText(getString(R.string.touch_to_focus));

        mCameraPreview.focus();
    }

    /**
     * 点击确认，返回图片路径
     */
    private void confirm() {
        /*裁剪图片*/
        mCropImageView.crop(new CropListener() {
            @Override
            public void onFinish(Bitmap bitmap) {
                if (bitmap == null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.crop_fail), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    /*保存图片到sdcard并返回图片路径*/
                    if (FileUtils.createOrExistsDir(Constant.DIR_ROOT)) {
                        StringBuilder builder = new StringBuilder();
                        String imagePath = "";
                        if (mType == TYPE_IDCARD_FRONT) {
                            imagePath = builder.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("idCardFrontCrop.jpg").toString();
                        } else if (mType == TYPE_IDCARD_BACK) {
                            imagePath = builder.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("idCardHold.jpg").toString();
                        } else if (mType == TYPE_WORK) {
                            imagePath = builder.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("work.jpg").toString();
                        }

                        if (ImageUtils.save(bitmap, imagePath, Bitmap.CompressFormat.JPEG)) {
                            Intent intent = new Intent();
                            intent.putExtra(PhCameraActivity.IMAGE_PATH, imagePath);
                            intent.putExtra(PhCameraActivity.IMAGE_EXIF, GsonUtils.toJson(imageInfo));
                            setResult(RESULT_CODE, intent);
                            finish();
                        }
                    }
                }
            }
        }, true);
    }

    private String getExifInfoBean(androidx.exifinterface.media.ExifInterface exifInterface) {
        ExifInfo exifInfo = new ExifInfo();
        ExifInfo.ThumbnailBean thumbnail = new ExifInfo.ThumbnailBean();
        if (exifInterface != null) {
            try {
                exifInfo.setMake(exifInterface.getAttribute(ExifInterface.TAG_MAKE));
                exifInfo.setModel(exifInterface.getAttribute(ExifInterface.TAG_MODEL));
                exifInfo.setXResolution(exifInterface.getAttributeInt(ExifInterface.TAG_X_RESOLUTION, 0));
                exifInfo.setYResolution(exifInterface.getAttributeInt(ExifInterface.TAG_Y_RESOLUTION, 0));
                exifInfo.setResolutionUnit(exifInterface.getAttributeInt(ExifInterface.TAG_RESOLUTION_UNIT, 0));
                exifInfo.setSoftware(exifInterface.getAttribute(ExifInterface.TAG_SOFTWARE));
                exifInfo.setDateTime(exifInterface.getAttribute(ExifInterface.TAG_DATETIME));
                exifInfo.setYCbCrPositioning(exifInterface.getAttributeInt(ExifInterface.TAG_Y_CB_CR_POSITIONING, 0));
                exifInfo.setExposureTime(exifInterface.getAttributeDouble(ExifInterface.TAG_EXPOSURE_TIME, 0.0d));
                exifInfo.setFNumber(exifInterface.getAttributeDouble(ExifInterface.TAG_F_NUMBER, 0.0d));
                exifInfo.setExposureProgram(exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_PROGRAM));
                exifInfo.setISOSpeedRatings(exifInterface.getAttributeInt(ExifInterface.TAG_ISO_SPEED_RATINGS, 0));
                exifInfo.setExifVersion(exifInterface.getAttribute(ExifInterface.TAG_EXIF_VERSION));
                exifInfo.setDateTimeOriginal(exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL));
                exifInfo.setDateTimeDigitized(exifInterface.getAttribute(ExifInterface.TAG_DATETIME_DIGITIZED));
                exifInfo.setComponentsConfiguration(exifInterface.getAttribute(ExifInterface.TAG_COMPONENTS_CONFIGURATION));
                exifInfo.setShutterSpeedValue(exifInterface.getAttributeDouble(ExifInterface.TAG_SHUTTER_SPEED_VALUE, 0.0d));
                exifInfo.setApertureValue(exifInterface.getAttributeDouble(ExifInterface.TAG_APERTURE_VALUE, 0.0d));
                exifInfo.setBrightnessValue(exifInterface.getAttributeInt(ExifInterface.TAG_BRIGHTNESS_VALUE, 0));
                exifInfo.setMeteringMode(exifInterface.getAttribute(ExifInterface.TAG_METERING_MODE));
                exifInfo.setFlash(exifInterface.getAttribute(ExifInterface.TAG_FLASH));
                exifInfo.setFocalLength(exifInterface.getAttributeDouble(ExifInterface.TAG_FOCAL_LENGTH, 0D));
                exifInfo.setUserComment(exifInterface.getAttribute(ExifInterface.TAG_USER_COMMENT));
                exifInfo.setSubsecTime(exifInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME));
                exifInfo.setSubsecTimeOriginal(exifInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME_ORIGINAL));
                exifInfo.setSubsecTimeDigitized(exifInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME_DIGITIZED));
                exifInfo.setFlashpixVersion(exifInterface.getAttribute(ExifInterface.TAG_FLASHPIX_VERSION));
                exifInfo.setColorSpace(exifInterface.getAttributeInt(ExifInterface.TAG_COLOR_SPACE, 0));
                exifInfo.setPixelXDimension(exifInterface.getAttributeInt(ExifInterface.TAG_PIXEL_X_DIMENSION, 0));
                exifInfo.setPixelYDimension(exifInterface.getAttributeInt(ExifInterface.TAG_PIXEL_Y_DIMENSION, 0));
                exifInfo.setInteroperabilityIFDPointer(exifInterface.getAttributeInt(ExifInterface.TAG_INTEROPERABILITY_INDEX, 0));
                exifInfo.setSensingMethod(exifInterface.getAttribute(ExifInterface.TAG_SENSING_METHOD));
                exifInfo.setSceneType(exifInterface.getAttribute(ExifInterface.TAG_SCENE_TYPE));
                exifInfo.setExposureMode(exifInterface.getAttributeInt(ExifInterface.TAG_EXPOSURE_MODE, 0));
                exifInfo.setWhiteBalance(exifInterface.getAttributeInt(ExifInterface.TAG_WHITE_BALANCE, 0));
                exifInfo.setFocalLengthIn35mmFilm(exifInterface.getAttributeInt(ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM, 0));
                exifInfo.setSceneCaptureType(exifInterface.getAttributeInt(ExifInterface.TAG_SCENE_CAPTURE_TYPE, 0));
                exifInfo.setGPSAltitudeRef(exifInterface.getAttributeInt(ExifInterface.TAG_GPS_ALTITUDE_REF, 0));
                exifInfo.setGPSLatitudeRef(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
                exifInfo.setGPSLongitudeRef(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));
                exifInfo.setGPSDateStamp(exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP));
                exifInfo.setGPSProcessingMethod(exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD));
                exifInfo.setGPSAltitude(exifInterface.getAltitude(0.0d));
                exifInfo.setGPSLongitude(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
                exifInfo.setGPSLatitude(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
                exifInfo.setImageWidth(exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0));
                exifInfo.setImageHeight(exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0));
                exifInfo.setOrientation(exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0));
                exifInfo.setCompressedBitsPerPixel(exifInterface.getAttributeDouble(ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL, 0.0d));
                exifInfo.setExposureBias(exifInterface.getAttributeInt(ExifInterface.TAG_EXPOSURE_BIAS_VALUE, 0));
                exifInfo.setMaxApertureValue(exifInterface.getAttributeDouble(ExifInterface.TAG_MAX_APERTURE_VALUE, 0.0d));
                exifInfo.setLightSource(exifInterface.getAttribute(ExifInterface.TAG_LIGHT_SOURCE));
                exifInfo.setFileSource(exifInterface.getAttribute(ExifInterface.TAG_FILE_SOURCE));
                exifInfo.setCustomRendered(exifInterface.getAttribute(ExifInterface.TAG_CUSTOM_RENDERED));
                exifInfo.setDigitalZoomRation(exifInterface.getAttributeInt(ExifInterface.TAG_DIGITAL_ZOOM_RATIO, 0));
                exifInfo.setGainControl(exifInterface.getAttribute(ExifInterface.TAG_GAIN_CONTROL));
                exifInfo.setContrast(exifInterface.getAttribute(ExifInterface.TAG_CONTRAST));
                exifInfo.setSaturation(exifInterface.getAttribute(ExifInterface.TAG_SATURATION));
                exifInfo.setSharpness(exifInterface.getAttribute(ExifInterface.TAG_SHARPNESS));
                exifInfo.setSubjectDistanceRange(exifInterface.getAttribute(ExifInterface.TAG_SUBJECT_DISTANCE_RANGE));
                exifInfo.setBitsPerSample(exifInterface.getAttributeInt(ExifInterface.TAG_BITS_PER_SAMPLE, 0));
                exifInfo.setMakerNote(exifInterface.getAttribute(ExifInterface.TAG_MAKER_NOTE));
                if (exifInterface.hasThumbnail()) {
                    thumbnail.setCompression(exifInterface.getAttributeInt(ExifInterface.TAG_COMPRESSION, 0));
                    thumbnail.setXResolution(exifInterface.getAttributeInt(ExifInterface.TAG_X_RESOLUTION, 0));
                    thumbnail.setYResolution(exifInterface.getAttributeInt(ExifInterface.TAG_Y_RESOLUTION, 0));
                    thumbnail.setResolutionUnit(exifInterface.getAttributeInt(ExifInterface.TAG_RESOLUTION_UNIT, 0));
                    thumbnail.setJpegIFOffset(exifInterface.getAttributeInt("ThumbnailOffset", 0));
                    thumbnail.setJpegIFByteCount(exifInterface.getAttributeInt(ExifInterface.TAG_STRIP_BYTE_COUNTS, 0));
                    thumbnail.setImageHeight(exifInterface.getAttributeInt(ExifInterface.TAG_THUMBNAIL_IMAGE_WIDTH, 0));
                    thumbnail.setImageWidth(exifInterface.getAttributeInt(ExifInterface.TAG_THUMBNAIL_IMAGE_LENGTH, 0));
                    exifInfo.setThumbnail(thumbnail);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Gson().toJson(exifInfo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCameraPreview != null) {
            mCameraPreview.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCameraPreview != null) {
            mCameraPreview.onStop();
        }
    }
}