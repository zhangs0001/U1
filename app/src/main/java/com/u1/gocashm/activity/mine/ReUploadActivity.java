package com.u1.gocashm.activity.mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.u1.uclibrary.idcardcamera.camera.PhCameraActivity;
import com.u1.uclibrary.idcardcamera.global.Constant;
import com.u1.uclibrary.idcardcamera.utils.FileUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.DictionaryPhModel;
import com.u1.gocashm.model.response.LoanResultPhModel;
import com.u1.gocashm.model.response.UserInfoModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.PermissionPhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.LoanInfoView;
import com.u1.gocashm.view.dialog.IdCardExampleDialog;
import com.u1.gocashm.view.dialog.RequestPermissionDialog;
import com.u1.gocashm.view.popupwindow.SelectListPopupWindow;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import ai.advance.liveness.lib.LivenessResult;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

public class ReUploadActivity extends BasePhTitleBarActivity {

    private static final int TAKE_PHOTO = 100;
    private static final int TAKE_HOLD_PHOTO = 101;
    public static final int REQUEST_CODE_LIVENESS = 1001;
    public static final String ALL = "NEED_APTITUDE_ALL";
    public static final String CARD = "NEED_APTITUDE_ID";
    public static final String FACE = "NEED_APTITUDE_FACE";
    @BindView(R.id.input_idcard_type)
    InputView inputIdcardType;
    @BindView(R.id.iv_id_card_preview)
    ImageView ivIdCardPreview;
    @BindView(R.id.iv_hold_card_preview)
    ImageView ivHoldCardPreview;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_hold_title)
    TextView tvHoldTitle;
    @BindView(R.id.tv_hold_hint)
    TextView tvHoldHint;
    @BindView(R.id.ll_info)
    LoanInfoView llInfo;
    @BindView(R.id.iv_hold_take_photo)
    ImageView ivTakeHold;
    @BindView(R.id.ll_id_card)
    LinearLayout llIdCard;
    @BindView(R.id.ll_face)
    LinearLayout llFace;


    private Activity activity;

    private String imagePath;
    private String holdImagePath;
    private String cardType;
    private String cardTypeCode;
    private SelectListPopupWindow popupWindow;
    private SharedPreferencesPhUtil spPhUtils;
    private RequestPermissionDialog permissionDialog;
    //    private String livenessId;
    private boolean isLiveness = true;
    private int livenessCount;
    private String applyStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card);
        ButterKnife.bind(this);
        activity = this;
        initData();
        initView();
    }

    private void initData() {
        tvNext.setText(R.string.submit);
        llInfo.setVisibility(View.GONE);
        inputIdcardType.setEnabled(false);
        tvHoldHint.setVisibility(View.GONE);
        tvHoldTitle.setText(R.string.id_card_title2);
        spPhUtils = SharedPreferencesPhUtil.getInstance(activity);
        livenessCount = spPhUtils.getInt(PhConstants.PhPhoto.LIVENESS_COUNT, 0);
//        livenessId = spPhUtils.getString(PhConstants.PhPhoto.LIVENESS_ID);
        getUserData();
    }

    private void getUserData() {
        String token = TokenUtils.getToken(activity);
        NetworkPhRequest.getUserInfo(token, new PhSubscriber<UserInfoModel>() {
            @Override
            public void onNext(UserInfoModel userInfoModel) {
                super.onNext(userInfoModel);
                String code = userInfoModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    UserInfoModel.UserInfo data = userInfoModel.getData();
                    inputIdcardType.setText(data.getUser().getCard_type());
                }
            }
        });
        ApplyReqPhModel reqPhModel = new ApplyReqPhModel();
        reqPhModel.setToken(token);
        NetworkPhRequest.getLastOrder(reqPhModel, new PhSubscriber<LoanResultPhModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(LoanResultPhModel loanResultPhModel) {
                super.onNext(loanResultPhModel);
                closeLoadingDialog();
                String code = loanResultPhModel.getCode();
                if (code.equals(CODE_SUCCESS)) {
                    LoanResultPhModel.LoanResult data = loanResultPhModel.getData();
                    applyStatus = data.getApplyStatus();
                    if (!TextUtils.isEmpty(applyStatus)) {
                        if (ALL.equals(applyStatus)) {
                            llIdCard.setVisibility(View.VISIBLE);
                            llFace.setVisibility(View.VISIBLE);
                        } else if (CARD.equals(applyStatus)) {
                            llIdCard.setVisibility(View.VISIBLE);
                            llFace.setVisibility(View.GONE);
                        } else if (FACE.equals(applyStatus)) {
                            llIdCard.setVisibility(View.GONE);
                            llFace.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }
        });
    }

    private void initView() {
//        if (!TextUtils.isEmpty(imagePath)) {
//            ivIdCardPreview.setImageBitmap(BitmapFactory.decodeFile(imagePath));
//        }
//        if (!TextUtils.isEmpty(holdImagePath)) {
//            ivHoldCardPreview.setImageBitmap(BitmapFactory.decodeFile(holdImagePath));
//        }
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.id_card_title);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue_1));
    }

    @OnClick({R.id.tv_example, R.id.iv_id_card_preview, R.id.iv_hold_card_preview, R.id.tv_next, R.id.input_idcard_type})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_example:
                toExample();
                break;
            case R.id.iv_id_card_preview:
                takePhoto();
                break;
            case R.id.iv_hold_card_preview:
                takeLiveness();
                break;
            case R.id.tv_next:
                next();
                break;
            case R.id.input_idcard_type:
                selectCardType();
                break;
        }
    }

    private void takeLiveness() {
//        if (!TextUtils.isEmpty(livenessId)) {
//            return;
//        }
        if (isLiveness && livenessCount < 2) {
            startLivenessActivity();
        } else {
            takeHoldPhoto();
        }
    }

    private void startLivenessActivity() {
        if (!PermissionUtils.isGranted(Manifest.permission.CAMERA)) {
            showCameraDialog();
        } else {
            skipFilmingFace();
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
                                    PermissionPhUtils.uploadDeviceInfo(activity, true, "CAMERA");
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
        cardType = inputIdcardType.getText();
//        if (TextUtils.isEmpty(cardType)) {
//            showToast(R.string.please_select);
//            return;
//        }
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
            //点击按钮
            Intent intent = new Intent(activity, PhCameraActivity.class);
            intent.putExtra(PhCameraActivity.TAKE_TYPE, 1);
            intent.putExtra(PhCameraActivity.CARD_TYPE, cardType);
            activity.startActivityForResult(intent, TAKE_PHOTO);
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
        showLoadingDialog();
        ApplyReqPhModel applyReq = new ApplyReqPhModel();
        applyReq.setToken(TokenUtils.getToken(activity));
        if (ALL.equals(applyStatus)) {
            applyReq.setIdCardImagePath(imagePath);
            applyReq.setHoldImagePath(holdImagePath);
        } else if (CARD.equals(applyStatus)) {
            applyReq.setIdCardImagePath(imagePath);
        } else if (FACE.equals(applyStatus)) {
            applyReq.setHoldImagePath(holdImagePath);
        }
        NetworkPhRequest.saveSupplementPhoto(applyReq, new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel baseModel) {
                super.onNext(baseModel);
                closeLoadingDialog();
                String code = baseModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    cleanData();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    showToast(baseModel.getMsg());
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
//        spPhUtils.saveInt(PhConstants.PhPhoto.LIVENESS_COUNT, livenessCount);
//        spPhUtils.saveString(PhConstants.PhPhoto.LIVENESS_ID, livenessId);
        spPhUtils.saveString(PhConstants.PhPhoto.PHOTO_IMAGE_PATH, imagePath);
        spPhUtils.saveString(PhConstants.PhPhoto.PHOTO_HOLD_IMAGE_PATH, holdImagePath);
    }

    private void cleanData() {
        spPhUtils.removeByKey(PhConstants.PhPhoto.LIVENESS_COUNT);
        spPhUtils.removeByKey(PhConstants.PhPhoto.LIVENESS_ID);
        spPhUtils.removeByKey(PhConstants.PhPhoto.PHOTO_IMAGE_PATH);
        spPhUtils.removeByKey(PhConstants.PhPhoto.PHOTO_IMAGE_ID);
        spPhUtils.removeByKey(PhConstants.PhPhoto.PHOTO_HOLD_IMAGE_PATH);
        spPhUtils.removeByKey(PhConstants.PhPhoto.PHOTO_HOLD_IMAGE_ID);
    }

    private void selectCardType() {
        showPopupWindow("PRIMAARYID ", "cardType", inputIdcardType.getTv_content());
    }

    private void showPopupWindow(String code, final String value, final TextView textView) {
        NetworkPhRequest.dictionaryQuery(code, new PhSubscriber<DictionaryPhModel>() {
            @Override
            public void onNext(DictionaryPhModel dictionaryModel) {
                super.onNext(dictionaryModel);
                String modelCode = dictionaryModel.getCode();
                if (CODE_SUCCESS.equals(modelCode)) {
                    popupWindow = new SelectListPopupWindow(activity, dictionaryModel.getData(), new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                            switch (value) {
                                case "cardType":
                                    // 证件类型
                                    cardType = dictionary.getName();
                                    cardTypeCode = dictionary.getCode();
                                    break;
                            }
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

    @Override
    protected void onDestroy() {
        saveData();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO && resultCode == PhCameraActivity.RESULT_CODE && data != null) {
            imagePath = data.getStringExtra(PhCameraActivity.IMAGE_PATH);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            ivIdCardPhPreview.setImageBitmap(bitmap);
            Glide.with(activity).load(bitmap).into(ivIdCardPreview);
        } else if (requestCode == TAKE_HOLD_PHOTO && resultCode == PhCameraActivity.RESULT_CODE && data != null) {
            holdImagePath = data.getStringExtra(PhCameraActivity.IMAGE_PATH);
            Bitmap bitmap = BitmapFactory.decodeFile(holdImagePath);
            Glide.with(activity).load(bitmap).into(ivHoldCardPreview);
        } else if (requestCode == REQUEST_CODE_LIVENESS && resultCode == RESULT_OK) {
            if (LivenessResult.isSuccess()) { // 活体检测成功
                livenessCount = livenessCount + 1;
                // 本次活体id
//                livenessId = LivenessResult.getLivenessId();
                Bitmap livenessBitmap = LivenessResult.getLivenessBitmap();// 本次活体图片
                StringBuilder builder = new StringBuilder();
                if (FileUtils.createOrExistsDir(Constant.DIR_ROOT)) {
                    holdImagePath = builder.append(Constant.DIR_ROOT).append(Constant.APP_NAME).append(".").append("liveness.jpg").toString();
                    ImageUtils.save(livenessBitmap, holdImagePath, Bitmap.CompressFormat.JPEG);
                }
                Glide.with(activity).load(livenessBitmap).into(ivHoldCardPreview);
            } else {// 活体检测失败
                livenessCount = livenessCount + 1;
//                if (livenessCount == 2) {
//                }
                ivTakeHold.setImageResource(R.drawable.ic_camera);
                String errorMsg = LivenessResult.getErrorMsg();// 失败原因
                showToast(errorMsg);
            }
        }
        if (ALL.equals(applyStatus)) {
            if (!TextUtils.isEmpty(imagePath) && !TextUtils.isEmpty(holdImagePath)) {
                tvNext.setEnabled(true);
                tvNext.setBackgroundResource(R.drawable.shape_blue);
            }
        } else if (CARD.equals(applyStatus)) {
            if (!TextUtils.isEmpty(imagePath)) {
                tvNext.setEnabled(true);
                tvNext.setBackgroundResource(R.drawable.shape_blue);
            }
        } else if (FACE.equals(applyStatus)) {
            if (!TextUtils.isEmpty(holdImagePath)) {
                tvNext.setEnabled(true);
                tvNext.setBackgroundResource(R.drawable.shape_blue);
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
}
