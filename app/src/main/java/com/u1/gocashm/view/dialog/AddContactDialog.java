package com.u1.gocashm.view.dialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.tu.loadingdialog.LoadingDailog;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.u1.gocashm.R;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.DictionaryPhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.VerifyUtil;
import com.u1.gocashm.util.model.ContactInfo;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.popupwindow.SelectListPopupWindow;
import com.mylhyl.circledialog.AbsBaseCircleDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2020/2/25 0025 下午 3:57
 */
public class AddContactDialog extends AbsBaseCircleDialog {

    private static final int PICK_CONTACT = 101;
    @BindView(R.id.input_contact_name)
    InputView inputContactName;
    @BindView(R.id.input_contact_phone)
    InputView inputContactPhone;
    @BindView(R.id.input_contact_relation)
    InputView inputContactRelation;
    @BindView(R.id.tv_dialog_title)
    TextView tvTitle;

    private String contactName;
    private String contactPhone;
    private String contactRelation;
    private String contactRelationCode;
    private SelectListPopupWindow popupWindow;
    private Cursor cursor;
    private LoadingDailog.Builder builder;
    private LoadingDailog dialog;
    private OnConfirmClick confirmListener;
    private boolean isEmergency;
    private RequestPermissionDialog permissionDialog;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_add_contact, container);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        inputContactPhone.getTv_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRelativePhone(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (isEmergency) {
            tvTitle.setText("Emergency Contact");
        } else {
            tvTitle.setText("General Contact");
        }
    }

    private void checkRelativePhone(String relativesPhone) {
        if (!VerifyUtil.checkPhoneForPh(relativesPhone)) {
            inputContactPhone.setVerify(R.string.verify_relativesphone);
        } else {
            inputContactPhone.setVerify("");
        }
    }

    @OnClick({R.id.input_contact_phone, R.id.input_contact_relation, R.id.tv_add_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.input_contact_phone:
                selectPhone();
                break;
            case R.id.input_contact_relation:
                selectRelation(isEmergency);
                break;
            case R.id.tv_add_confirm:
                confirm();
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void selectPhone() {
        permissionDialog = new RequestPermissionDialog();
        permissionDialog.setData(R.drawable.contact_img, R.string.contacts_info_title, R.string.contacts_info_content, R.string.contacts_info_hint);
        permissionDialog.setConfirmListener(new RequestPermissionDialog.OnConfirmClick() {
            @Override
            public void OnConfirmClick() {
                RxPermissions permissions = new RxPermissions(getActivity());
                permissions.requestEach(Manifest.permission.READ_CONTACTS).subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            permissionDialog.dismiss();
                            onContactApp();
                        } else if (!permission.shouldShowRequestPermissionRationale) {
                            getAppDetailSettingIntent();
                        }
                    }
                });
            }
        });
        permissionDialog.show(getFragmentManager(), "1");
    }

    private void selectRelation(boolean isEmergency) {
        String code;
        if (isEmergency) {
            code = "RELATIONSHIP";
        } else {
            code = "OTHER_RELATIONSHIP";
        }
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            NetworkPhRequest.dictionaryQuery(code, new PhSubscriber<DictionaryPhModel>() {
                @Override
                public void onNext(DictionaryPhModel dictionaryPhModel) {
                    super.onNext(dictionaryPhModel);
                    String modelCode = dictionaryPhModel.getCode();
                    if (CODE_SUCCESS.equals(modelCode)) {
                        popupWindow = new SelectListPopupWindow(getActivity(), dictionaryPhModel.getData(), new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                                if (dictionary == null) {
                                    return;
                                }
                                contactRelation = dictionary.getName();
                                contactRelationCode = dictionary.getCode();
                                inputContactRelation.setText(dictionary.getName());
                                if (popupWindow != null) popupWindow.dismiss();
                            }
                        });
                        popupWindow.showPopupWindow(inputContactRelation);
                    }
                }
            });
        }

    }

    private void confirm() {
        contactName = inputContactName.getText();
        contactPhone = inputContactPhone.getText();
        contactRelation = inputContactRelation.getText();
        if (TextUtils.isEmpty(contactName)) {
            inputContactName.setVerify(R.string.input_name);
            return;
        } else {
            inputContactName.setVerify("");
        }
        if (!VerifyUtil.checkPhoneForPh(contactPhone)) {
            inputContactPhone.setVerify(R.string.verify_relativesphone);
            return;
        } else {
            inputContactPhone.setVerify("");
        }
        if (TextUtils.isEmpty(contactRelation)) {
            inputContactRelation.setVerify(R.string.verify_relationship);
            return;
        } else {
            inputContactRelation.setVerify("");
        }
        List<ContactInfo> contactInfos = new ArrayList<>();
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setContactFullname(contactName);
        contactInfo.setContactRelationCode(contactRelationCode);
        contactInfo.setContactRelation(contactRelation);
        contactInfo.setContactTelephone(contactPhone);
        contactInfos.add(contactInfo);
        Gson gson = new Gson();
        String bodys = gson.toJson(contactInfos);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodys);
        NetworkPhRequest.addContact(body, new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                closeLoadingDialog();
                String code = basePhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    confirmListener.OnConfirmClick(contactInfo);
                    dismiss();
                } else {
                    ToastUtils.showShort(basePhModel.getMsg());
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                ToastUtils.showShort(R.string.error_request_fail);
            }
        });
    }

    private void onContactApp() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK && data != null) {
            try {
                getPhoneNum(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getPhoneNum(Intent data) {
        Uri contactData = data.getData();
        ContentResolver cr = getActivity().getContentResolver();
        cursor = cr.query(contactData, null, null, null, null);
        boolean isOpen = false;
        try {
            isOpen = cursor != null && cursor.moveToFirst();
        } catch (RuntimeException e) {
            Log.e("getPhoneNum", e.getMessage());
        }
        if (isOpen) {
            int hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            if (hasPhone > 0 && !TextUtils.isEmpty(contactId)) {
                try {
                    Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones != null && phones.moveToNext()) {
                        contactPhone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    if (null != phones && !phones.isClosed()) {
                        phones.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (!TextUtils.isEmpty(contactPhone)) {
            contactPhone = contactPhone.replaceAll("[^0-9]", "");
            // 亲属手机号
            inputContactPhone.setText(contactPhone);
        }
    }

    /**
     * 跳转到权限设置界面
     */
    private void getAppDetailSettingIntent() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
        startActivity(intent);
    }

    private void showLoadingDialog() {
        builder = new LoadingDailog.Builder(getActivity())
                .setCancelable(true)
                .setCancelOutside(true);
        dialog = builder.create();
        dialog.show();
    }

    private void closeLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    //定义一个接口
    public interface OnConfirmClick {
        void OnConfirmClick(ContactInfo contactInfo);

    }

    public void setConfirmListener(OnConfirmClick confirmListener) {
        this.confirmListener = confirmListener;
    }

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
    }
}
