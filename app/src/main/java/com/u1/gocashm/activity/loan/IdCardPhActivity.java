package com.u1.gocashm.activity.loan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.appevents.AppEventsLogger;
import com.u1.uclibrary.idcardcamera.camera.PhCameraActivity;
import com.u1.uclibrary.idcardcamera.global.Constant;
import com.u1.uclibrary.idcardcamera.global.ImageInfo;
import com.u1.uclibrary.idcardcamera.utils.FileUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.BehaviorPhReqModel;
import com.u1.gocashm.model.request.BehaviorRecord;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.DictionaryPhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.AFPhListener;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.GpsPhUtils;
import com.u1.gocashm.util.GsonPhHelper;
import com.u1.gocashm.util.PermissionPhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.util.http.UploadInfoUtils;
import com.u1.gocashm.util.listener.MySensorListener;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.LoanInfoView;
import com.u1.gocashm.view.dialog.IdCardExampleDialog;
import com.u1.gocashm.view.dialog.RequestPermissionDialog;
import com.u1.gocashm.view.dialog.SurveyDialog;
import com.u1.gocashm.view.popupwindow.SelectListPopupWindow;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.view.listener.OnCreateBodyViewListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import ai.advance.liveness.lib.LivenessResult;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2018/9/4 0004 下午 2:21
 */
public class IdCardPhActivity extends IdentityInfoActivity {

    private static final int TAKE_PHOTO = 100;
    private static final int TAKE_HOLD_PHOTO = 101;
    private static final int TAKE_BACK_PHOTO = 102;
    /**
     * 请求状态码
     */
    public static final int REQUEST_CODE_LIVENESS = 1001;
    @BindView(R.id.iv_id_card_preview)
    ImageView ivIdCardPhPreview;
    @BindView(R.id.ll_info)
    LoanInfoView llPhInfo;
    @BindView(R.id.tv_next)
    TextView tvPhNext;
    @BindView(R.id.iv_take_photo)
    ImageView ivPhTakePhoto;
    @BindView(R.id.iv_hold_card_preview)
    ImageView ivHoldCardPreview;
    @BindView(R.id.input_idcard_type)
    InputView inputIdcardType;
    @BindView(R.id.iv_hold_take_photo)
    ImageView ivHoldTakePhoto;
    @BindView(R.id.input_idcard)
    InputView inputIdcard;
    @BindView(R.id.tv_hold_title)
    TextView tvHoldTitle;
    @BindView(R.id.tv_hold_hint)
    TextView tvHoldHint;
    @BindView(R.id.iv_id_card_back_preview)
    ImageView ivIdCardBackPreview;
    @BindView(R.id.iv_take_photo_back)
    ImageView ivTakePhotoBack;

    private FragmentActivity activity;
    private int idCardPhImageId;
    private int idBackPhImageId;
    private int holdIdCardImageId;
    private Disposable disposable;
    private SharedPreferencesPhUtil spPhUtils;
    private String imagePhPath;
    private String imageBackPath;
    private String holdImagePath;
    private String cardType;
    private String cardTypeCode;
    private String idCardNum;
    private String oldImageExif;
    private String newImageExif;
    private String oldBackImageExif;
    private String newBackImageExif;
    private String oldHoldImageExif;
    private String newHoldImageExif;
    // 行为数据model
    private BehaviorPhReqModel bfModel;
    private SelectListPopupWindow popupWindow;
    private RequestPermissionDialog permissionDialog;
    private SensorManager MyManage;
    private MySensorListener sensorListener;
    private String livenessId;
    private boolean isLiveness = true;
    private int livenessCount;
    private String latitude;
    private String longitude;
    private BehaviorRecord p05IFrontIDPhoto;
    private BehaviorRecord p05IBackPersonVerify;
    private BehaviorRecord p05IRealPersonVerify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card);
        activity = this;
        ButterKnife.bind(this);
        initData();
        initView();
        initListener();
        disposable = RxBus.getDefault().toDefaultFlowable(TagEvent.class, new Consumer<TagEvent>() {
            @Override
            public void accept(TagEvent tagEvent) throws Exception {
                if (EventTagPh.APPLY_SUCCESS.equals(tagEvent.getTag()) || EventTagPh.LOAN.equals(tagEvent.getTag())) {
                    finish();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // 启动居住地址
        getFocus(inputIdcard);
        // 身份证号码
        AFPhListener.getInstance().addEditTextListener(inputIdcard.getEt_content(), bfModel, "P02_C02_I_IDCARD");
    }

    private void initData() {
        Location location = GpsPhUtils.getLocation();
        if (location != null) {
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
        }
        MyManage = (SensorManager) getSystemService(SENSOR_SERVICE);    //获得系统传感器服务管理权
        sensorListener = new MySensorListener();
        MyManage.registerListener(sensorListener, MyManage.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);
        bfModel = BehaviorPhUtils.setEnterPageModify("P05_C00", getBaseContext());
        spPhUtils = SharedPreferencesPhUtil.getInstance(activity);
        livenessCount = spPhUtils.getInt(PhConstants.PhPhoto.LIVENESS_COUNT, 0);
        livenessId = spPhUtils.getString(PhConstants.PhPhoto.LIVENESS_ID);

        imagePhPath = spPhUtils.getString(PhConstants.PhPhoto.PHOTO_IMAGE_PATH);
        idCardPhImageId = spPhUtils.getInt(PhConstants.PhPhoto.PHOTO_IMAGE_ID, 0);
        idBackPhImageId = spPhUtils.getInt(PhConstants.PhPhoto.PHOTO_BACK_IMAGE_ID, 0);
        imageBackPath = spPhUtils.getString(PhConstants.PhPhoto.PHOTO_BACK_IMAGE_PATH);
        holdImagePath = spPhUtils.getString(PhConstants.PhPhoto.PHOTO_HOLD_IMAGE_PATH);
        holdIdCardImageId = spPhUtils.getInt(PhConstants.PhPhoto.PHOTO_HOLD_IMAGE_ID, 0);
        cardType = spPhUtils.getString(PhConstants.PhIdentityInfo.CARD_TYPE);
        cardTypeCode = spPhUtils.getString(PhConstants.PhIdentityInfo.CARD_TYPE_CODE);
        idCardNum = spPhUtils.getString(PhConstants.PhIdentityInfo.IDENTITY_CARD);
        if (!TextUtils.isEmpty(imagePhPath)) {
            ivIdCardPhPreview.setImageBitmap(BitmapFactory.decodeFile(imagePhPath));
        }

        if (!TextUtils.isEmpty(imageBackPath)) {
            ivHoldCardPreview.setImageBitmap(BitmapFactory.decodeFile(imageBackPath));
        }

        if (!TextUtils.isEmpty(holdImagePath)) {
            ivHoldCardPreview.setImageBitmap(BitmapFactory.decodeFile(holdImagePath));
        }
        if (!TextUtils.isEmpty(imagePhPath) && !TextUtils.isEmpty(holdImagePath)) {
            tvPhNext.setEnabled(true);
            tvPhNext.setBackgroundResource(R.drawable.shape_blue);
        }
        inputIdcardType.setText(cardType);
        inputIdcard.setText(idCardNum);
//        NetworkPhRequest.getTakeHoldType(new PhSubscriber<BasePhModel>() {
//            @Override
//            public void onNext(BasePhModel basePhModel) {
//                super.onNext(basePhModel);
//                String code = basePhModel.getCode();
//                if (CODE_SUCCESS.equals(code)) {
//                    String type = (String) basePhModel.getData();
//                    isLiveness = "Y".equals(type);
//                    if (isLiveness) {
//                        tvHoldHint.setVisibility(View.VISIBLE);
//                        tvHoldTitle.setText("Live Face Identification");
//                        if (!TextUtils.isEmpty(livenessId)) {
//                            ivHoldTakePhoto.setImageResource(R.drawable.liveness_success_img);
//                        }
//                    } else {
//                        tvHoldHint.setVisibility(View.GONE);
//                        tvHoldTitle.setText(R.string.id_card_title2);
//                    }
//                }
//            }
//
//            @Override
//            public void onCompleted() {
//                super.onCompleted();
//                showExampleDialog();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                isLiveness = true;
//                tvHoldHint.setVisibility(View.GONE);
//                tvHoldTitle.setText(R.string.id_card_title2);
//
//            }
//        });
    }


    private void initView() {
        if (!TextUtils.isEmpty(cardType)) {
            inputIdcard.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(idCardNum)) {
            inputIdcard.setText(idCardNum);
            inputIdcard.getEt_content().setSelection(idCardNum.length());
        }
        if (!TextUtils.isEmpty(livenessId)) {
            ivHoldTakePhoto.setImageResource(R.drawable.liveness_success_img);
        }
    }

    public void getFocus(final InputView input) {
        input.getEt_content().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                input.getFocus();
                return false;
            }
        });
    }

    private void initListener() {
        inputIdcard.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) {
                    return;
                }
                checkIDCard(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        inputIdcard.inputClearFocus();
        MyManage.unregisterListener(sensorListener);
        saveData();
        uploadBehaviorRecords(
                inputIdcardType,
                inputIdcard);
        BehaviorPhUtils.setDestroyModify(bfModel, "P05_C99");
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.id_card_title);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
        llPhInfo.setStatus(4);
    }

    @OnClick({R.id.iv_take_photo,R.id.iv_take_photo_back, R.id.tv_next, R.id.tv_example, R.id.iv_hold_take_photo, R.id.input_idcard_type})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_take_photo:
                BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P05_I_Selfie", true);
                takePhoto();
                break;
            case R.id.iv_take_photo_back:
                BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P05_I_Selfie", true);
                if (!TextUtils.isEmpty(livenessId)) {
                    return;
                }
                p05IBackPersonVerify = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P05_I_BackPersonVerify",true);
                if (isLiveness && livenessCount < 2) {
                    if (!PermissionUtils.isGranted(Manifest.permission.CAMERA)) {
                        showCameraDialog();
                    } else {
                        takeBackPhoto();
                    }
                } else {
                    takeBackPhoto();
                }
                //takeBackPhoto();
                break;
            case R.id.tv_next:
                next();
                break;
            case R.id.tv_example:
                toExample();
                break;
            case R.id.iv_hold_take_photo:
                BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P05_I_Selfie", true);
                takeLiveness();
                break;
            case R.id.input_idcard_type:
                selectCardType();
                break;
        }
        inputIdcard.inputClearFocus();
    }

    private void takeLiveness() {
        if (!TextUtils.isEmpty(livenessId)) {
            return;
        }
        p05IRealPersonVerify = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P05_I_RealPersonVerify",true);
        if (isLiveness && livenessCount < 2) {
            startLivenessActivity();
        } else {
            takeHoldPhoto();
        }
    }

    private void selectCardType() {
        showPopupWindow("PRIMAARYID ", "cardType", inputIdcardType.getTv_content());
    }

    private void showPopupWindow(String code, final String value, final TextView textView) {
        NetworkPhRequest.dictionaryQuery(code, new PhSubscriber<DictionaryPhModel>() {
            @Override
            public void onNext(DictionaryPhModel dictionaryPhModel) {
                super.onNext(dictionaryPhModel);
                String modelCode = dictionaryPhModel.getCode();
                if (CODE_SUCCESS.equals(modelCode)) {
                    popupWindow = new SelectListPopupWindow(activity, dictionaryPhModel.getData(), new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                            if (dictionary == null) {
                                return;
                            }
                            switch (value) {
                                case "cardType":
                                    // 证件类型
                                    BehaviorPhUtils.setChangeModify(bfModel, "P02_C07_R_MARITAL", cardType, dictionary.getName());
                                    cardType = dictionary.getName();
                                    cardTypeCode = dictionary.getCode();
                                    if (!TextUtils.isEmpty(cardType)) {
                                        inputIdcard.setVisibility(View.VISIBLE);
                                    }
                                    idCardNum = inputIdcard.getText();
                                    if (!TextUtils.isEmpty(idCardNum)) {
                                        checkIDCard(idCardNum);
                                    }
                                    break;
                            }
                            clear();
                            textView.setText(dictionary.getName());
                            if (popupWindow != null) popupWindow.dismiss();
                        }
                    });
                    if (popupWindow.isShowing())
                        popupWindow.dismiss();
                    else {
                        popupWindow.showPopupWindow(textView);
                    }
                }
            }
        });

    }

    private void clear() {
        imagePhPath = null;
        holdImagePath = null;
        tvPhNext.setEnabled(false);
        tvPhNext.setBackgroundResource(R.drawable.shape_grey_solid);
        inputIdcard.getEt_content().setText(null);
        ivIdCardPhPreview.setImageBitmap(null);
        ivHoldCardPreview.setImageBitmap(null);
    }

    private void getCardImageDialog(int titleRes, final int imageRes) {
        CircleDialog.Builder builder = new CircleDialog.Builder();
        ConfigButton button = new ConfigButton() {
            @Override
            public void onConfig(ButtonParams params) {
                params.textColor = 0xFFFF8010;
                params.textSize = 20;
            }
        };
        builder.setTitle(getString(titleRes))
                .setPositive(getString(R.string.understood), null)
                .setBodyView(R.layout.item_card_example, new OnCreateBodyViewListener() {
                    @Override
                    public void onCreateBodyView(View view) {
                        ImageView imageView = view.findViewById(R.id.card_image);
                        imageView.setImageResource(imageRes);
                    }
                })
                .configPositive(button)
                .show(getSupportFragmentManager());
    }

    /**
     * 启动活体检测
     */
    private void startLivenessActivity() {
        if (!PermissionUtils.isGranted(Manifest.permission.CAMERA)) {
            showCameraDialog();
        } else {
            takeHoldPhoto();
            //skipFilmingFace();
        }
    }

    private void skipFilmingFace() {
//            Intent intent = new Intent(activity, LivenessActivity.class);
//            startActivityForResult(intent, REQUEST_CODE_LIVENESS);

        //前置摄像头拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        activity.startActivityForResult(intent, TAKE_PHOTO);
    }


    @SuppressLint("CheckResult")
    private void showCameraDialog() {
        permissionDialog = new RequestPermissionDialog();
        permissionDialog.setData(R.drawable.camera_img, R.string.camera_info_title, R.string.camera_info_content, R.string.camera_info_hint);
        permissionDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
            @Override
            public void OnConfirmClick() {
                RxPermissions rxPermissions = new RxPermissions(activity);
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    skipFilmingFace();
                                } else {
                                    PermissionPhUtils.uploadDeviceInfo(activity, false, "CAMERA");
                                }
                            }
                        });

            }
        });
        permissionDialog.show(getSupportFragmentManager(), "2");
    }

    private void toExample() {
        showExampleDialog();
    }

    private void showExampleDialog() {
        IdCardExampleDialog dialog = new IdCardExampleDialog();
        dialog.setLiveness(isLiveness);
        dialog.show(getSupportFragmentManager(), "1");
    }

    /**
     * 跳转到拍照界面
     */
    private void takePhoto() {
        idCardNum = inputIdcard.getText();
        if (TextUtils.isEmpty(cardType)) {
            showToast(R.string.please_select);
            return;
        }
        if (!checkIDCard(idCardNum)) {
            return;
        }
        if (!PermissionUtils.isGranted(Manifest.permission.CAMERA) || !PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            RequestPermissionDialog photoDialog = new RequestPermissionDialog();
            photoDialog.setData(R.drawable.camera_img, R.string.camera_info_title, R.string.camera_info_content, R.string.camera_info_hint);
            photoDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
                @SuppressLint("CheckResult")
                @Override
                public void OnConfirmClick() {
                    new RxPermissions(activity).requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
                                photoDialog.dismiss();
                                //点击按钮
                                PermissionPhUtils.uploadDeviceInfo(activity, true, "CAMERA");
                                //点击按钮
                                p05IFrontIDPhoto = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P05_I_FrontIDPhoto",true);
                                BehaviorPhUtils.setClickModify(bfModel, "P05_C01_B_TAKEPHOTO");
                                Intent intent = new Intent(activity, PhCameraActivity.class);
                                intent.putExtra(PhCameraActivity.TAKE_TYPE, 1);
                                intent.putExtra(PhCameraActivity.CARD_TYPE, cardType);
                                activity.startActivityForResult(intent, TAKE_PHOTO);
                            } else if (!permission.shouldShowRequestPermissionRationale) {
                                getAppDetailSettingIntent();
                            }
                        }
                    });
                }
            });
            photoDialog.show(getSupportFragmentManager(), "1");
        } else {
            p05IFrontIDPhoto = BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P05_I_FrontIDPhoto",true);
            //点击按钮
            BehaviorPhUtils.setClickModify(bfModel, "P05_C01_B_TAKEPHOTO");
            Intent intent = new Intent(activity, PhCameraActivity.class);
            intent.putExtra(PhCameraActivity.TAKE_TYPE, 1);
            intent.putExtra(PhCameraActivity.CARD_TYPE, cardType);
            activity.startActivityForResult(intent, TAKE_PHOTO);
        }

    }

    private void takeBackPhoto() {
        if (!PermissionUtils.isGranted(Manifest.permission.CAMERA) || !PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            RequestPermissionDialog cameraDialog = new RequestPermissionDialog();
            cameraDialog.setData(R.drawable.camera_img, R.string.camera_info_title, R.string.camera_info_content, R.string.camera_info_hint);
            cameraDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
                @SuppressLint("CheckResult")
                @Override
                public void OnConfirmClick() {
                    new RxPermissions(activity).requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
                                cameraDialog.dismiss();
                                Intent intent = new Intent(activity, PhCameraActivity.class);
                                intent.putExtra(PhCameraActivity.TAKE_TYPE, 3);
                                intent.putExtra(PhCameraActivity.CARD_TYPE, "back");
                                activity.startActivityForResult(intent, TAKE_BACK_PHOTO);
                                //权限被用户通过
                            } else if (!permission.shouldShowRequestPermissionRationale) {
                                getAppDetailSettingIntent();
                            }
                        }
                    });

                }
            });
            cameraDialog.show(getSupportFragmentManager(), "3");
        } else {
            Intent intent = new Intent(activity, PhCameraActivity.class);
            intent.putExtra(PhCameraActivity.TAKE_TYPE, 3);
            intent.putExtra(PhCameraActivity.CARD_TYPE, "back");
            activity.startActivityForResult(intent, TAKE_BACK_PHOTO);
        }
    }

    private void takeHoldPhoto() {
        if (!PermissionUtils.isGranted(Manifest.permission.CAMERA) || !PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            RequestPermissionDialog cameraDialog = new RequestPermissionDialog();
            cameraDialog.setData(R.drawable.camera_img, R.string.camera_info_title, R.string.camera_info_content, R.string.camera_info_hint);
            cameraDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
                @SuppressLint("CheckResult")
                @Override
                public void OnConfirmClick() {
                    new RxPermissions(activity).requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
                                cameraDialog.dismiss();
                                Intent intent = new Intent(activity, PhCameraActivity.class);
                                intent.putExtra(PhCameraActivity.TAKE_TYPE, 2);
                                intent.putExtra(PhCameraActivity.CARD_TYPE, "hold");
                                activity.startActivityForResult(intent, TAKE_HOLD_PHOTO);
                                //权限被用户通过
                            } else if (!permission.shouldShowRequestPermissionRationale) {
                                getAppDetailSettingIntent();
                            }
                        }
                    });

                }
            });
            cameraDialog.show(getSupportFragmentManager(), "2");
        } else {
            Intent intent = new Intent(activity, PhCameraActivity.class);
            intent.putExtra(PhCameraActivity.TAKE_TYPE, 2);
            intent.putExtra(PhCameraActivity.CARD_TYPE, "hold");
            activity.startActivityForResult(intent, TAKE_HOLD_PHOTO);
        }
    }

    private void next() {
        idCardNum = inputIdcard.getText();
        if (!checkIDCardEmpty(idCardNum)) {
            return;
        }
        if (!checkCardTypeCodeEmpty()) {
            return;
        }

        //点击按钮
        BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P05_C_Next",true);
        BehaviorPhUtils.setClickModify(bfModel, "P05_C01_B_NEXT");
        BehaviorPhUtils.setSensorValue(bfModel, "P05_C20_SENSOR", sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez());
        showLoadingDialog();
        ApplyReqPhModel applyReqPhModel = new ApplyReqPhModel();
//        List<Integer> list = new ArrayList<>();
//        list.add(idCardPhImageId);
//        list.add(holdIdCardImageId);
//        applyReqPhModel.setImages(list);
        applyReqPhModel.setCard_type_code(cardTypeCode);
        applyReqPhModel.setCard_num(idCardNum);
        applyReqPhModel.setIdCardImagePath(imagePhPath);
        applyReqPhModel.setBackPhotoImagePath(imageBackPath);
        applyReqPhModel.setHoldImagePath(holdImagePath);
        if (isLiveness) {
            applyReqPhModel.setLivenessId(livenessId);
        }
        applyReqPhModel.setToken(TokenUtils.getToken(activity));
        applyReqPhModel.setStep_name("photoInfo");
        NetworkPhRequest.savePhoto(applyReqPhModel, new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                closeLoadingDialog();
                String code = basePhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    AppEventsLogger.newLogger(activity).logEvent(PhConstants.FaceBookEvent.EVENT_ID_CARD);
                    // 保存行为数据
                    BehaviorPhUtils.saveBehaviorReqModel(bfModel);
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_ID_CARD, activity, TokenUtils.getOrderId(activity));
                    NetworkPhRequest.getUserStep(applyReqPhModel, new PhSubscriber<>());
                    saveData();
                    switchStep(PayInfoPhActivity.class);
                    finish();
                } else {
                    showToast(basePhModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                showToast(R.string.error_request_fail);
            }
        });
    }

    private void saveData() {
        spPhUtils.saveString(PhConstants.PhIdentityInfo.IDENTITY_CARD, idCardNum);
        spPhUtils.saveString(PhConstants.PhIdentityInfo.CARD_TYPE, cardType);
        spPhUtils.saveString(PhConstants.PhIdentityInfo.CARD_TYPE_CODE, cardTypeCode);
        spPhUtils.saveString(PhConstants.PhPhoto.PHOTO_IMAGE_PATH, imagePhPath);
        spPhUtils.saveInt(PhConstants.PhPhoto.PHOTO_IMAGE_ID, idCardPhImageId);
        spPhUtils.saveString(PhConstants.PhPhoto.PHOTO_HOLD_IMAGE_PATH, holdImagePath);
        spPhUtils.saveInt(PhConstants.PhPhoto.PHOTO_HOLD_IMAGE_ID, holdIdCardImageId);
        spPhUtils.saveInt(PhConstants.PhPhoto.LIVENESS_COUNT, livenessCount);
        spPhUtils.saveString(PhConstants.PhPhoto.LIVENESS_ID, livenessId);
        spPhUtils.saveBoolean(PhConstants.FINISH_STATUS.PHOTO_INFO, true);
    }

    private boolean checkIDCard(String card) {
        /*if ("Driver’s License".equals(cardType)) {
            if (!VerifyUtil.checkIdCardForDriver(card)) {
                inputIdcard.setVerify(R.string.driver_card_type_hint);
                return false;
            } else {
                inputIdcard.setVerify("");
                return true;
            }
        } else if ("SSS card".equals(cardType)) {
            if (!VerifyUtil.checkIdCardForSSS(card)) {
                inputIdcard.setVerify(R.string.sss_card_type_hint);
                return false;
            } else {
                inputIdcard.setVerify("");
                return true;
            }
        } else if ("UMID".equals(cardType)) {
            if (!VerifyUtil.checkIdCardForUMID(card)) {
                inputIdcard.setVerify(R.string.umid_type_hint);
                return false;
            } else {
                inputIdcard.setVerify("");
                return true;
            }
        } else if ("TIN".equals(cardType)) {
            if (!VerifyUtil.checkIdCardForTIN(card)) {
                inputIdcard.setVerify(R.string.tin_type_hint);
                return false;
            } else {
                inputIdcard.setVerify("");
                return true;
            }
        } else if ("PRC ID".equals(cardType)) {
            if (!VerifyUtil.checkIdCardForPRC(card)) {
                inputIdcard.setVerify(R.string.prc_type_hint);
                return false;
            } else {
                inputIdcard.setVerify("");
                return true;
            }
        } else if ("PASSPORT".equals(cardType)) {
            if (!VerifyUtil.checkIdCardForPassport(card)) {
                inputIdcard.setVerify(R.string.passport_type_hint);
                return false;
            } else {
                inputIdcard.setVerify("");
                return true;
            }
        } else {
            return true;
        }*/

        return true;
    }

    private boolean checkIDCardEmpty(String card) {
        if (TextUtils.isEmpty(card)) {
            inputIdcard.setVerify(R.string.verify_idcard_type);
            return false;
        } else {
            inputIdcard.setVerify("");
            return true;
        }
    }

    private boolean checkCardTypeCodeEmpty() {
        if (TextUtils.isEmpty(cardTypeCode)) {
            inputIdcardType.setVerify(R.string.verify_idcard_type);
            return false;
        } else {
            inputIdcardType.setVerify("");
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO && resultCode == PhCameraActivity.RESULT_CODE && data != null) {
            p05IFrontIDPhoto.setEndTime();
            p05IFrontIDPhoto.setOldValue(imagePhPath);
            imagePhPath = data.getStringExtra(PhCameraActivity.IMAGE_PATH);
            p05IFrontIDPhoto.setNewValue(imagePhPath);
            newImageExif = data.getStringExtra(PhCameraActivity.IMAGE_EXIF);
            Log.e("111",newImageExif);
            ImageInfo imageInfo = GsonUtils.fromJson(newImageExif, ImageInfo.class);
            imageInfo.setLatitude(latitude);
            imageInfo.setLongitude(longitude);
            UploadInfoUtils.uploadPhotoInfo(TokenUtils.getToken(activity), GsonPhHelper.getGson().toJson(imageInfo));
            Bitmap bitmap = BitmapFactory.decodeFile(imagePhPath);
            Glide.with(activity).load(bitmap).into(ivIdCardPhPreview);
            if (!TextUtils.isEmpty(imagePhPath) && !TextUtils.isEmpty(holdImagePath) && !TextUtils.isEmpty(imageBackPath)) {
                tvPhNext.setEnabled(true);
                tvPhNext.setBackgroundResource(R.drawable.shape_blue);
            }

        }else if (requestCode == TAKE_BACK_PHOTO && resultCode == PhCameraActivity.RESULT_CODE && data != null) {
            p05IBackPersonVerify.setEndTime();
            p05IBackPersonVerify.setOldValue(imageBackPath);
            imageBackPath = data.getStringExtra(PhCameraActivity.IMAGE_PATH);
            p05IBackPersonVerify.setNewValue(imageBackPath);
            newBackImageExif = data.getStringExtra(PhCameraActivity.IMAGE_EXIF);
            ImageInfo imageInfo = GsonUtils.fromJson(newBackImageExif, ImageInfo.class);
            imageInfo.setLatitude(latitude);
            imageInfo.setLongitude(longitude);
            UploadInfoUtils.uploadPhotoInfo(TokenUtils.getToken(activity), GsonPhHelper.getGson().toJson(imageInfo));
            Bitmap bitmap = BitmapFactory.decodeFile(imageBackPath);
            Glide.with(activity).load(bitmap).into(ivIdCardBackPreview);
            if (!TextUtils.isEmpty(imagePhPath) && !TextUtils.isEmpty(holdImagePath)&& !TextUtils.isEmpty(imageBackPath)) {
                tvPhNext.setEnabled(true);
                tvPhNext.setBackgroundResource(R.drawable.shape_blue);
            }
        } else if (requestCode == TAKE_HOLD_PHOTO && resultCode == PhCameraActivity.RESULT_CODE && data != null) {
            p05IRealPersonVerify.setEndTime();
            p05IRealPersonVerify.setOldValue(holdImagePath);
            holdImagePath = data.getStringExtra(PhCameraActivity.IMAGE_PATH);
            p05IRealPersonVerify.setNewValue(holdImagePath);
            newHoldImageExif = data.getStringExtra(PhCameraActivity.IMAGE_EXIF);
            ImageInfo imageInfo = GsonUtils.fromJson(newHoldImageExif, ImageInfo.class);
            imageInfo.setLatitude(latitude);
            imageInfo.setLongitude(longitude);
            UploadInfoUtils.uploadPhotoInfo(TokenUtils.getToken(activity), GsonPhHelper.getGson().toJson(imageInfo));
            Bitmap bitmap = BitmapFactory.decodeFile(holdImagePath);
            Glide.with(activity).load(bitmap).into(ivHoldCardPreview);
            if (!TextUtils.isEmpty(imagePhPath) && !TextUtils.isEmpty(holdImagePath)&& !TextUtils.isEmpty(imageBackPath)) {
                tvPhNext.setEnabled(true);
                tvPhNext.setBackgroundResource(R.drawable.shape_blue);
            }
        } else if (requestCode == REQUEST_CODE_LIVENESS && resultCode == RESULT_OK) {
            if (LivenessResult.isSuccess()) { // 活体检测成功
                p05IRealPersonVerify.setEndTime();
                p05IRealPersonVerify.setOldValue(holdImagePath);
                livenessCount = livenessCount + 1;
//                showLoadingDialog();
                // 本次活体id
                livenessId = LivenessResult.getLivenessId();
                Bitmap livenessBitmap = LivenessResult.getLivenessBitmap();// 本次活体图片
                StringBuilder builder = new StringBuilder();
                if (FileUtils.createOrExistsDir(Constant.DIR_ROOT)) {
                    holdImagePath = builder.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("liveness.jpg").toString();
                    ImageUtils.save(livenessBitmap, holdImagePath, Bitmap.CompressFormat.JPEG);
                }
                p05IRealPersonVerify.setNewValue(holdImagePath);
                Glide.with(activity).load(livenessBitmap).into(ivHoldCardPreview);
                if (!TextUtils.isEmpty(imagePhPath) && !TextUtils.isEmpty(holdImagePath)) {
                    tvPhNext.setEnabled(true);
                    tvPhNext.setBackgroundResource(R.drawable.shape_blue);
                }
            } else {// 活体检测失败
                livenessCount = livenessCount + 1;
                if (livenessCount == 2) {
                    ivHoldTakePhoto.setImageResource(R.drawable.ic_camera);
                }
                String errorMsg = LivenessResult.getErrorMsg();// 失败原因
                showToast(errorMsg);
            }
        }

        handleFace(requestCode, resultCode, data);
    }

    private void handleFace(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO && data != null) {
                Bundle bundle = data.getExtras();
                //这里就是得到的缩略图。因为是intent传递的，放置oom，android已经处理成了缩略图，你会发现缩略图确实是很模糊的
                Bitmap bitmap = (Bitmap) bundle.get("data");
                StringBuilder builder = new StringBuilder();
                if (FileUtils.createOrExistsDir(Constant.DIR_ROOT)) {
                    holdImagePath = builder.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("liveness.jpg").toString();
                    ImageUtils.save(bitmap, holdImagePath, Bitmap.CompressFormat.JPEG);
                }
                Glide.with(activity).load(bitmap).into(ivHoldCardPreview);
                if (!TextUtils.isEmpty(imagePhPath) && !TextUtils.isEmpty(holdImagePath)) {
                    tvPhNext.setEnabled(true);
                    tvPhNext.setBackgroundResource(R.drawable.shape_blue);
                }
            }
        }
    }

    /**
     * 跳转到权限设置界面
     */
    private void getAppDetailSettingIntent() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        showSurveyDialog();
    }

    private void showSurveyDialog() {
        SurveyDialog dialog = new SurveyDialog();
        dialog.setStep("4");
        dialog.setLeaveListener(new SurveyDialog.OnLeaveClick() {
            @Override
            public void OnLeaveClick(String content) {
                if (TextUtils.isEmpty(content)) {
                    showToast(getString(R.string.a2));
                    return;
                }
                NetworkPhRequest.submitUserSurvey(TokenUtils.getToken(activity), "4",
                        content, new PhSubscriber<BasePhModel>() {
                            @Override
                            public void onStart() {
                                super.onStart();
                                showLoadingDialog();
                            }

                            @Override
                            public void onNext(BasePhModel basePhModel) {
                                super.onNext(basePhModel);
                                closeLoadingDialog();
                                String code = basePhModel.getCode();
                                if (CODE_SUCCESS.equals(code)) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                closeLoadingDialog();
                            }
                        });
            }
        });
        dialog.show(getSupportFragmentManager(), "1");
    }

}
