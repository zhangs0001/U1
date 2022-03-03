package com.u1.gocashm.activity.loan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.PermissionUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.appevents.AppEventsLogger;
import com.u1.gocashm.R;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.request.AuthInfoPhReqModel;
import com.u1.gocashm.model.request.BehaviorPhReqModel;
import com.u1.gocashm.model.response.AuthInfoPhModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.DictionaryPhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.AFPhListener;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.AppPhUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.DevicePhUtil;
import com.u1.gocashm.util.InputPhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.VerifyUtil;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.util.listener.MySensorListener;
import com.u1.gocashm.util.model.ContactInfo;
import com.u1.gocashm.view.InputView;
import com.u1.gocashm.view.LoanInfoView;
import com.u1.gocashm.view.dialog.RequestPermissionDialog2;
import com.u1.gocashm.view.dialog.SurveyDialog;
import com.u1.gocashm.view.popupwindow.SelectListPopupWindow;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @author hewei
 * @date 2018/9/3 0003 下午 7:04
 */
public class JobInfoPhActivity extends IdentityInfoActivity {

    private static final int PICK_CONTACT = 100;
    private static final int TAG_ONE = 1;
    private static final int TAG_TWO = 2;
    private static final int TAG_THREE = 3;
    private static final String TAG = JobInfoPhActivity.class.getSimpleName();

    @BindView(R.id.ll_info)
    LoanInfoView llPhInfo;
    @BindView(R.id.tv_next)
    TextView tvPhNext;
    @BindView(R.id.job_layout)
    LinearLayout jobPhLayout;
    @BindView(R.id.input_intent)
    InputView input_intentPh;
    @BindView(R.id.input_profession)
    InputView input_professionPh;
    @BindView(R.id.input_working_industry)
    InputView input_working_industryPh;
    @BindView(R.id.input_company_name)
    InputView input_company_namePh;
    @BindView(R.id.input_job_position)
    InputView input_job_positionPh;
    @BindView(R.id.input_monthly_income)
    InputView input_monthly_incomePh;
    @BindView(R.id.input_working_time)
    InputView input_working_timePh;
    @BindView(R.id.input_office_phone)
    InputView input_office_phonePh;
    @BindView(R.id.input_relatives_name)
    InputView input_relatives_namePh;
    @BindView(R.id.input_relatives_phone)
    InputView input_relatives_phonePh;
    @BindView(R.id.input_relationship)
    InputView input_relationshipPh;
    @BindView(R.id.input_relatives_name2)
    InputView inputRelativesName2;
    @BindView(R.id.input_relatives_phone2)
    InputView inputRelativesPhone2;
    @BindView(R.id.input_relationship2)
    InputView inputRelationship2;
    @BindView(R.id.input_relatives_name3)
    InputView inputRelativesName3;
    @BindView(R.id.input_relatives_phone3)
    InputView inputRelativesPhone3;
    @BindView(R.id.input_relationship3)
    InputView inputRelationship3;

    private FragmentActivity activity;
    private SharedPreferencesPhUtil spPhUtil;

    private String loanPhIntent;
    private String professionPh;
    private String workPhIndustry;
    private String companyPhName;
    private String jobPositionPh;
    private String monthlyIncomePh;
    private String monthlyIncomeName;
    private String workTimePh;
    private String officePhPhone;
    private String relativesName;
    private String relativesPhone;
    private String relationship;
    private String relationshipCode;
    private String relativesName2;
    private String relativesPhone2;
    private String relationship2;
    private String relationshipCode2;
    private String relativesName3;
    private String relativesPhone3;
    private String relationship3;
    private String relationshipCode3;

    private String loanIntentPhCode;
    private String professionPhCode;
    private String workIndustryPhCode;
    private String workTimePhCode;

    private Cursor cursor;

    private SelectListPopupWindow popupWindow;
    private Disposable disposable;
    private String isShowJobLayout;
    // 行为数据model
    private BehaviorPhReqModel bfModel;
    private RequestPermissionDialog2 permissionDialog;
    private SensorManager MyManage;
    private MySensorListener sensorListener;
    private List<ContactInfo> contactInfoList;
    private Integer relationTag;
    private ContactInfo contactInfo;
    private String applyFlag;
    private String showcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_info);
        activity = this;
        ButterKnife.bind(this);
        initData();
        initView();
        initTitleBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 启动工作信息
        initListener();
    }

    private void initData() {
        MyManage = (SensorManager) getSystemService(SENSOR_SERVICE);    //获得系统传感器服务管理权
        sensorListener = new MySensorListener();
        MyManage.registerListener(sensorListener, MyManage.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);
        bfModel = BehaviorPhUtils.setEnterPageModify("P04_C00", getBaseContext());
        spPhUtil = SharedPreferencesPhUtil.getInstance(activity);
        disposable = RxBus.getDefault().toDefaultFlowable(TagEvent.class, new Consumer<TagEvent>() {
            @Override
            public void accept(TagEvent tagEvent) throws Exception {
                if (EventTagPh.APPLY_SUCCESS.equals(tagEvent.getTag()) || EventTagPh.LOAN.equals(tagEvent.getTag())) {
                    finish();
                }
            }
        });
        applyFlag = getIntent().getStringExtra(PhConstants.APPLY_FLAG);
        loanPhIntent = spPhUtil.getString(PhConstants.PhJobInfo.LOAN_INTENT);
        professionPh = spPhUtil.getString(PhConstants.PhJobInfo.PROFESSION);
        workPhIndustry = spPhUtil.getString(PhConstants.PhJobInfo.WORK_INDUSTRY);
        companyPhName = spPhUtil.getString(PhConstants.PhJobInfo.COMPANY_NAME);
        jobPositionPh = spPhUtil.getString(PhConstants.PhJobInfo.JOB_POSITION);
        monthlyIncomePh = spPhUtil.getString(PhConstants.PhJobInfo.MONTHLY_INCOME);
        monthlyIncomeName = spPhUtil.getString(PhConstants.PhJobInfo.MONTHLY_INCOME_NAME);
        workTimePh = spPhUtil.getString(PhConstants.PhJobInfo.WORK_TIME);
        officePhPhone = spPhUtil.getString(PhConstants.PhJobInfo.OFFICE_PHONE);
        loanIntentPhCode = spPhUtil.getString(PhConstants.PhJobInfo.LOANINTENT_CODE);
        professionPhCode = spPhUtil.getString(PhConstants.PhJobInfo.PROFESSION_CODE);
        Log.e(TAG, "initData: " + spPhUtil.getString(PhConstants.PhJobInfo.PROFESSION_CODE));
        workIndustryPhCode = spPhUtil.getString(PhConstants.PhJobInfo.WORKINDUSTRY_CODE);
        workTimePhCode = spPhUtil.getString(PhConstants.PhJobInfo.WORKTIME_CODE);
        contactInfoList = spPhUtil.getContactList();
        if (contactInfoList != null) {
            if (contactInfoList.get(0) != null) {
                relationshipCode = contactInfoList.get(0).getContactRelationCode();
                relativesName = contactInfoList.get(0).getContactFullname();
                relativesPhone = contactInfoList.get(0).getContactTelephone();
                relationship = contactInfoList.get(0).getContactRelation();
            }
            if (contactInfoList.get(1) != null) {
                relationshipCode2 = contactInfoList.get(1).getContactRelationCode();
                relativesName2 = contactInfoList.get(1).getContactFullname();
                relativesPhone2 = contactInfoList.get(1).getContactTelephone();
                relationship2 = contactInfoList.get(1).getContactRelation();
            }
            if (contactInfoList.get(2) != null) {
                relationshipCode3 = contactInfoList.get(2).getContactRelationCode();
                relativesName3 = contactInfoList.get(2).getContactFullname();
                relativesPhone3 = contactInfoList.get(2).getContactTelephone();
                relationship3 = contactInfoList.get(2).getContactRelation();
            }
        }
    }

    private void getJobLayoutVisible() {
        NetworkPhRequest.dictionaryQuery("SOCIAL_STATUS", new PhSubscriber<DictionaryPhModel>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onNext(DictionaryPhModel dictionaryPhModel) {
                Log.e(TAG, "onNext: 1 " + isShowJobLayout);
                super.onNext(dictionaryPhModel);
                showcode = dictionaryPhModel.getCode();
                Log.e(TAG, "onNext: 2 " + showcode);
                if (CODE_SUCCESS.equals(showcode)) {
                    Log.e(TAG, "onNext: success");
//                    List<DictionaryPhModel.Dictionary> body = dictionaryPhModel.getData();
//                    for (DictionaryPhModel.Dictionary dictionary : body) {
//                        if (dictionary.getCode().equals(professionPhCode)) {
//                            Log.e(TAG, "onNext: 3 " + dictionary.getAttr1());
//                            isShowJobLayout = dictionary.getAttr1();
//                            Log.e(TAG, "onNext: 4 " + isShowJobLayout);
////                            if ("N".equals(isShowJobLayout)) {
////                                jobPhLayout.setVisibility(View.VISIBLE);
////                            } else {
////                                jobPhLayout.setVisibility(View.GONE);
////                            }
//
//                            if ("SOCIAL_STATUS_01".equals(professionPhCode)) {
//                                jobPhLayout.setVisibility(View.GONE);
//                            } else if ("SOCIAL_STATUS_02".equals(professionPhCode)) {
//                                jobPhLayout.setVisibility(View.GONE);
//                            } else if ("SOCIAL_STATUS_03".equals(professionPhCode)) {
//                                jobPhLayout.setVisibility(View.GONE);
//                            } else if ("SOCIAL_STATUS_04".equals(professionPhCode)) {
//                                jobPhLayout.setVisibility(View.GONE);
//                            } else {
//                                jobPhLayout.setVisibility(View.VISIBLE);
//                            }
//                        }
//                    }
                }
            }
        });
    }

    private void initView() {
        input_intentPh.setText(loanPhIntent);
        input_professionPh.setText(professionPh);
        input_working_industryPh.setText(workPhIndustry);
        input_company_namePh.setText(companyPhName);
        input_job_positionPh.setText(jobPositionPh);
        input_monthly_incomePh.setText(monthlyIncomeName);
        input_working_timePh.setText(workTimePh);
        input_office_phonePh.setText(officePhPhone);
        input_relatives_namePh.setText(relativesName);
        input_relatives_phonePh.setText(relativesPhone);
        input_relationshipPh.setText(relationship);
        inputRelativesName2.setText(relativesName2);
        inputRelativesPhone2.setText(relativesPhone2);
        inputRelationship2.setText(relationship2);
        inputRelativesName3.setText(relativesName3);
        inputRelativesPhone3.setText(relativesPhone3);
        inputRelationship3.setText(relationship3);
        llPhInfo.setStatus(3);
        getJobLayoutVisible();

        input_monthly_incomePh.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) {
                    return;
                }
                if (count != before) {
                    StringBuilder sss = new StringBuilder();
                    String string = s.toString().replace(",", "");
                    int b = string.length() / 3;
                    if (string.length() >= 3) {
                        int yushu = string.length() % 3;
                        if (yushu == 0) {
                            b = string.length() / 3 - 1;
                            yushu = 3;
                        }
                        for (int i = 0; i < b; i++) {
                            sss.append(string.substring(0, yushu)).append(",").append(string.substring(yushu, 3));
                            string = string.substring(3, string.length());
                        }
                        sss.append(string);
                        input_monthly_incomePh.getEt_content().setText(sss.toString());
                    }
                }
                input_monthly_incomePh.getEt_content().setSelection(input_monthly_incomePh.getEt_content().getText().length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        InputPhUtils.setBlankToEditText(input_office_phonePh.getEt_content());
        addRuntimeEvent();
    }

    private void addRuntimeEvent() {
        input_relatives_phonePh.getTv_content().addTextChangedListener(new TextWatcher() {
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
        inputRelativesPhone2.getTv_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRelativePhone2(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputRelativesPhone3.getTv_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRelativePhone3(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input_relatives_namePh.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRelativeName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputRelativesName2.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRelativeName2(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputRelativesName3.getEt_content().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRelativeName3(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initListener() {
        // 公司名称
        AFPhListener.getInstance().addEditTextListener(input_company_namePh.getEt_content(), bfModel, "P04_C01_I_COMPANY");
        // 工作职位
        AFPhListener.getInstance().addEditTextListener(input_job_positionPh.getEt_content(), bfModel, "P04_C09_I_JOBPOSITION");
        // 月收入
        AFPhListener.getInstance().addEditTextListener(input_monthly_incomePh.getEt_content(), bfModel, "P04_C05_S_MONTHLYINCOME");
        // 公司电话
        AFPhListener.getInstance().addEditTextListener(input_office_phonePh.getEt_content(), bfModel, "P04_C04_I_COMPANYPHONE");

        getFocus(input_company_namePh);
        getFocus(input_job_positionPh);
        getFocus(input_monthly_incomePh);
        getFocus(input_office_phonePh);
        getFocus(input_relatives_namePh);
        getFocus(inputRelativesName2);
        getFocus(inputRelativesName3);
    }

    @Override
    public void initTitleBar() {
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
        setTitleBack(R.string.job_info_title);
    }

    @OnClick({R.id.input_intent, R.id.input_profession, R.id.input_working_industry, R.id.input_working_time, R.id.input_relatives_name,
            R.id.input_relatives_phone, R.id.input_relationship, R.id.input_relatives_name2, R.id.input_relatives_phone2,
            R.id.input_relationship2, R.id.input_relatives_name3, R.id.input_relatives_phone3, R.id.input_relationship3, R.id.tv_next, R.id.input_monthly_income})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.input_intent:
                selectLoanIntent();
                break;
            case R.id.input_profession:
                selectProfession();
                break;
            case R.id.input_working_industry:
                selectWorkingIndustry();
                break;
            case R.id.input_working_time:
                selectWorkingTime();
                break;
            case R.id.input_relatives_phone:
            case R.id.input_relatives_name:
                relationTag = TAG_ONE;
                selectRelativesPhone();
                break;
            case R.id.input_relationship:
                relationTag = TAG_ONE;
                selectRelationship();
                break;
            case R.id.input_relatives_name2:
            case R.id.input_relatives_phone2:
                relationTag = TAG_TWO;
                selectRelativesPhone();
                break;
            case R.id.input_relationship2:
                relationTag = TAG_TWO;
                selectRelationship();
                break;
            case R.id.input_relatives_name3:
            case R.id.input_relatives_phone3:
                relationTag = TAG_THREE;
                selectRelativesPhone();
                break;
            case R.id.input_relationship3:
                relationTag = TAG_THREE;
                selectRelationship();
                break;
            case R.id.tv_next:
                next();
                break;
            case R.id.input_monthly_income:
                selectMonthlyIncome();
                break;
        }
        input_company_namePh.inputClearFocus();
        input_job_positionPh.inputClearFocus();
        input_monthly_incomePh.inputClearFocus();
        input_office_phonePh.inputClearFocus();
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

    private void showPopupWindow(String code, final String value, final TextView textView) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            NetworkPhRequest.dictionaryQuery(code, new PhSubscriber<DictionaryPhModel>() {
                @Override
                public void onNext(DictionaryPhModel dictionaryPhModel) {
                    super.onNext(dictionaryPhModel);
                    String modelCode = dictionaryPhModel.getCode();

                    Log.e(TAG, "onNext: code  " + dictionaryPhModel.getData().get(0).getCode());
                    Log.e(TAG, "onNext: name  " + dictionaryPhModel.getData().get(0).getName());
                    if (CODE_SUCCESS.equals(modelCode)) {
                        popupWindow = new SelectListPopupWindow(activity, dictionaryPhModel.getData(), new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                DictionaryPhModel.Dictionary dictionary = (DictionaryPhModel.Dictionary) adapter.getItem(position);
                                if (dictionary == null) {
                                    return;
                                }
                                switch (value) {
                                    case "loanPhIntent":
                                        // 贷款目的
                                        BehaviorPhUtils.setChangeModify(bfModel, "P04_C08_S_LOANPURPOSE", loanIntentPhCode, dictionary.getCode());
                                        loanPhIntent = dictionary.getName();
                                        loanIntentPhCode = dictionary.getCode();
                                        break;
                                    case "professionPh":
                                        // 职业
                                        BehaviorPhUtils.setChangeModify(bfModel, "P04_C03_S_JOBTYPE", professionPhCode, dictionary.getCode());
                                        professionPh = dictionary.getName();
                                        professionPhCode = dictionary.getCode();
                                        Log.e(TAG, "onItemClick: professionPhCode: " + professionPhCode);
                                        isShowJobLayout = dictionary.getAttr1();
//                                        if ("N".equals(isShowJobLayout)) {
//                                            jobPhLayout.setVisibility(View.VISIBLE);
//                                        } else {
//                                            jobPhLayout.setVisibility(View.GONE);
//                                        }
                                        if ("SOCIAL_STATUS_01".equals(professionPhCode)) {
                                            jobPhLayout.setVisibility(View.GONE);
                                        } else if ("SOCIAL_STATUS_02".equals(professionPhCode)) {
                                            jobPhLayout.setVisibility(View.GONE);
                                        } else if ("SOCIAL_STATUS_03".equals(professionPhCode)) {
                                            jobPhLayout.setVisibility(View.GONE);
                                        } else if ("SOCIAL_STATUS_04".equals(professionPhCode)) {
                                            jobPhLayout.setVisibility(View.GONE);
                                        } else {
                                            jobPhLayout.setVisibility(View.VISIBLE);
                                        }
                                        break;
                                    case "workPhIndustry":
                                        // 行业
                                        BehaviorPhUtils.setChangeModify(bfModel, "P04_C02_S_INDUSTRY", workIndustryPhCode, dictionary.getCode());
                                        workPhIndustry = dictionary.getName();
                                        workIndustryPhCode = dictionary.getCode();
                                        break;
                                    case "workTimePh":
                                        // 工作时长
                                        BehaviorPhUtils.setChangeModify(bfModel, "P04_C12_S_INCUMBENCY", workTimePhCode, dictionary.getCode());
                                        workTimePh = dictionary.getName();
                                        workTimePhCode = dictionary.getCode();
                                        break;
                                    case "monthlyIncome":
                                        // 月收入
                                        //BehaviorPhUtils.setChangeModify(bfModel, "P04_C12_S_INCUMBENCY", workTimePhCode, dictionary.getCode());
                                        monthlyIncomeName = dictionary.getName();
                                        monthlyIncomePh = dictionary.getCode();
                                        break;
                                    case "relationshipPh":
                                        if (relationTag == TAG_ONE) {
                                            relationship = dictionary.getName();
                                            relationshipCode = dictionary.getCode();
                                        } else if (relationTag == TAG_TWO) {
                                            relationship2 = dictionary.getName();
                                            relationshipCode2 = dictionary.getCode();
                                        } else {
                                            relationship3 = dictionary.getName();
                                            relationshipCode3 = dictionary.getCode();
                                        }
                                        break;
                                }
                                textView.setText(dictionary.getName());
                                if (popupWindow != null) popupWindow.dismiss();
                            }
                        });
                        popupWindow.showPopupWindow(textView);
                    }
                }
            });
        }

    }

    private void selectLoanIntent() {
        showPopupWindow("LOAN_PURPOSE", "loanPhIntent", input_intentPh.getTv_content());
    }

    private void selectProfession() {
        showPopupWindow("SOCIAL_STATUS", "professionPh", input_professionPh.getTv_content());
    }

    private void selectWorkingIndustry() {
        showPopupWindow("INDUSTRY", "workPhIndustry", input_working_industryPh.getTv_content());
    }

    private void selectWorkingTime() {
        showPopupWindow("INCUMBENCY", "workTimePh", input_working_timePh.getTv_content());
    }

    private void selectMonthlyIncome() {
        showPopupWindow("MONTHLY_INCOME", "monthlyIncome", input_monthly_incomePh.getTv_content());
    }

    private void selectRelativesPhone() {
        if (!PermissionUtils.isGranted(Manifest.permission.READ_CONTACTS)) {
            permissionDialog = new RequestPermissionDialog2();
            permissionDialog.setData(R.drawable.contact_img, R.string.contacts_info_title, R.string.contacts_info_content, R.string.contacts_info_hint);
            permissionDialog.setConfirmListener(new RequestPermissionDialog2.OnConfirmClick() {
                @SuppressLint("CheckResult")
                @Override
                public void OnConfirmClick() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        new RxPermissions(activity).requestEach(Manifest.permission.READ_CONTACTS).subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                if (permission.granted) {
                                    permissionDialog.dismiss();
//                                    uploadDeviceInfo(activity, true, "READ_CONTACTS");
                                    onContactApp();
                                } else if (!permission.shouldShowRequestPermissionRationale) {
                                    getAppDetailSettingIntent();
                                }
                            }
                        });
                    } else {
                        onContactApp();
                    }
                }
            });
            permissionDialog.show(getSupportFragmentManager(), "2");
        } else {
            onContactApp();
        }
    }

    private void onContactApp() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectRelationship() {
        if (relationTag == TAG_ONE) {
            showPopupWindow("RELATIONSHIP", "relationshipPh", input_relationshipPh.getTv_content());
        } else if (relationTag == TAG_TWO) {
            showPopupWindow("RELATIONSHIP", "relationshipPh", inputRelationship2.getTv_content());
        } else {
            showPopupWindow("RELATIONSHIP", "relationshipPh", inputRelationship3.getTv_content());
        }
    }

    private void next() {
        //点击按钮
        BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P04_C_Next");
        BehaviorPhUtils.setClickModify(bfModel, "P04_C21_B_NEXT");
        companyPhName = input_company_namePh.getText();
        jobPositionPh = input_job_positionPh.getText();
        //monthlyIncomePh = input_monthly_incomePh.getText();
        officePhPhone = input_office_phonePh.getText().replace(" ", "");
        relativesName = input_relatives_namePh.getText();
        relativesName2 = inputRelativesName2.getText();
        relativesName3 = inputRelativesName3.getText();
        if (TextUtils.isEmpty(loanPhIntent)) {
            input_intentPh.setVerify(R.string.verify_intent);
            return;
        } else {
            input_intentPh.setVerify("");
        }
        if (TextUtils.isEmpty(professionPh)) {
            input_professionPh.setVerify(R.string.verify_profession);
            return;
        } else {
            input_professionPh.setVerify("");
        }
//        todo
        if ("SOCIAL_STATUS_01".equals(professionPhCode)) {
        } else if ("SOCIAL_STATUS_02".equals(professionPhCode)) {
        } else if ("SOCIAL_STATUS_03".equals(professionPhCode)) {
        } else if ("SOCIAL_STATUS_04".equals(professionPhCode)) {
        } else {
            if (TextUtils.isEmpty(workPhIndustry)) {
                input_working_industryPh.setVerify(R.string.verify_workindustry);
                return;
            } else {
                input_working_industryPh.setVerify("");
            }
            if (TextUtils.isEmpty(companyPhName)) {
                input_company_namePh.setVerify(R.string.verify_companyname);
                return;
            } else {
                input_company_namePh.setVerify("");
            }
            if (TextUtils.isEmpty(jobPositionPh)) {
                input_job_positionPh.setVerify(R.string.verify_jobposition);
                return;
            } else {
                input_job_positionPh.setVerify("");
            }
            if (TextUtils.isEmpty(monthlyIncomePh)) {
                input_monthly_incomePh.setVerify(R.string.verify_monthlyincome);
                return;
            } else {
                input_monthly_incomePh.setVerify("");
            }
            if (TextUtils.isEmpty(workTimePh)) {
                input_working_timePh.setVerify(R.string.verify_worktime);
                return;
            } else {
                input_working_timePh.setVerify("");
            }
            if (TextUtils.isEmpty(officePhPhone)) {
                input_office_phonePh.setVerify(R.string.verify_officephone);
                return;
            } else {
                input_office_phonePh.setVerify("");
            }
            if (!VerifyUtil.checkPhoneLength(officePhPhone)) {
                input_office_phonePh.setVerify(R.string.verify_officephoneerror);
                return;
            } else {
                input_office_phonePh.setVerify("");
            }
        }
        contactInfoList = new ArrayList<>();
        contactInfo = new ContactInfo();
        if (TextUtils.isEmpty(relativesName)) {
            input_relatives_namePh.setVerify(R.string.verify_relativesname);
            return;
        } else {
            input_relatives_namePh.setVerify("");
        }

        if (relativesName.length() <= 2) {
            input_relatives_namePh.setVerify(R.string.verify_relativesname_length);
            return;
        } else {
            input_relatives_namePh.setVerify("");
        }

        contactInfo.setContactFullname(relativesName);
        if (checkRelativePhone(relativesPhone)) {
            return;
        }
        contactInfo.setContactTelephone(relativesPhone);
        if (TextUtils.isEmpty(relationship)) {
            input_relationshipPh.setVerify(R.string.verify_relationship);
            return;
        } else {
            input_relationshipPh.setVerify("");
        }
        contactInfo.setContactRelationCode(relationshipCode);
        contactInfo.setContactRelation(relationship);
        contactInfoList.add(contactInfo);
        contactInfo = new ContactInfo();
        if (TextUtils.isEmpty(relativesName2)) {
            inputRelativesName2.setVerify(R.string.verify_relativesname);
            return;
        } else {
            inputRelativesName2.setVerify("");
        }

        if (relativesName2.length() <= 2) {
            inputRelativesName2.setVerify(R.string.verify_relativesname_length);
            return;
        } else {
            inputRelativesName2.setVerify("");
        }

        contactInfo.setContactFullname(relativesName2);
        if (checkRelativePhone2(relativesPhone2)) {
            return;
        }
        contactInfo.setContactTelephone(relativesPhone2);
        if (TextUtils.isEmpty(relationship2)) {
            inputRelationship2.setVerify(R.string.verify_relationship);
            return;
        } else {
            inputRelationship2.setVerify("");
        }
        contactInfo.setContactRelationCode(relationshipCode2);
        contactInfo.setContactRelation(relationship2);
        contactInfoList.add(contactInfo);
        contactInfo = new ContactInfo();
        if (TextUtils.isEmpty(relativesName3)) {
            inputRelativesName3.setVerify(R.string.verify_relativesname);
            return;
        } else {
            inputRelativesName3.setVerify("");
        }
        if (relativesName3.length() <= 2) {
            inputRelativesName3.setVerify(R.string.verify_relativesname_length);
            return;
        } else {
            inputRelativesName3.setVerify("");
        }

        contactInfo.setContactFullname(relativesName3);
        if (checkRelativePhone3(relativesPhone3)) {
            return;
        }
        contactInfo.setContactTelephone(relativesPhone3);
        if (TextUtils.isEmpty(relationship3)) {
            inputRelationship3.setVerify(R.string.verify_relationship);
            return;
        } else {
            inputRelationship3.setVerify("");
        }
        contactInfo.setContactRelationCode(relationshipCode3);
        contactInfo.setContactRelation(relationship3);
        contactInfoList.add(contactInfo);
        BehaviorPhUtils.setSensorValue(bfModel, "P04_C20_SENSOR", sensorListener.getAnglex(), sensorListener.getAngley(), sensorListener.getAnglez());
        showLoadingDialog();
        ApplyReqPhModel apply = new ApplyReqPhModel();
        apply.setLoan_reason(loanIntentPhCode);
        apply.setEmployment_type(professionPhCode);
        if ("SOCIAL_STATUS_01".equals(professionPhCode)) {
        } else if ("SOCIAL_STATUS_02".equals(professionPhCode)) {
        } else if ("SOCIAL_STATUS_03".equals(professionPhCode)) {
        } else if ("SOCIAL_STATUS_04".equals(professionPhCode)) {
        } else {
            apply.setIndustry(workIndustryPhCode);
            apply.setCompany(companyPhName);
            apply.setJobPosition(jobPositionPh);
            apply.setSalary(monthlyIncomePh);
            apply.setWorking_time_type(workTimePhCode);
            apply.setWork_phone(officePhPhone);
        }
        apply.setToken(TokenUtils.getToken(activity));
        apply.setContact_fullname1(relativesName);
        apply.setContact_telephone1(relativesPhone);
        apply.setRelation1(relationship);
        apply.setContact_fullname2(relativesName2);
        apply.setContact_telephone2(relativesPhone2);
        apply.setRelation2(relationship2);
        apply.setContact_fullname3(relativesName3);
        apply.setContact_telephone3(relativesPhone3);
        apply.setRelation3(relationship3);
        apply.setToken(TokenUtils.getToken(activity));
        if (!TextUtils.isEmpty(applyFlag)) {
            apply.setStep_name("photoInfo");
        } else {
            apply.setStep_name("jobInfo");
        }
        Log.e(TAG, "next: " + apply.toString());
        NetworkPhRequest.saveJobInfo(apply, new PhSubscriber<BasePhModel>() {
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                closeLoadingDialog();
                String code = basePhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    saveJobInfo();
                    AppEventsLogger.newLogger(activity).logEvent(PhConstants.FaceBookEvent.EVENT_JOB_INFO);
                    // 保存行为数据
                    BehaviorPhUtils.saveBehaviorReqModel(bfModel);
                    AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_JOB_INFO, activity, TokenUtils.getOrderId(activity));
                    NetworkPhRequest.getUserStep(apply, new PhSubscriber<>());
                    AppPhUtils.updateContactList(TokenUtils.getToken(activity));
                    Intent intent;
                    Class target = IdCardPhActivity.class;
                    if (!TextUtils.isEmpty(applyFlag)) {
                        target = PayInfoPhActivity.class;
                    }
                    switchStep(target);
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

    private boolean checkRelativePhone(String relativesPhone) {
        if (TextUtils.isEmpty(relativesPhone)) {
            input_relatives_phonePh.setVerify(R.string.verify_relativesphone);
            return true;
        } else {
            input_relatives_phonePh.setVerify("");
//            if (!VerifyUtil.checkPhoneForPh(relativesPhone)) {
//                input_relatives_phonePh.setVerify(R.string.error_phone_no);
//                return true;
//            } else {
//                input_relatives_phonePh.setVerify("");
//                return false;
//            }
            return false;
        }
    }

    private boolean checkRelativePhone2(String relativesPhone) {
        if (TextUtils.isEmpty(relativesPhone)) {
            inputRelativesPhone2.setVerify(R.string.verify_relativesphone);
            return true;
        } else {
            inputRelativesPhone2.setVerify("");
            return false;
        }
    }

    private boolean checkRelativePhone3(String relativesPhone) {
        if (TextUtils.isEmpty(relativesPhone)) {
            inputRelativesPhone3.setVerify(R.string.verify_relativesphone);
            return true;
        } else {
            inputRelativesPhone3.setVerify("");
            return false;
        }
    }

    private boolean checkRelativeName(String name) {
        if (TextUtils.isEmpty(name)) {
            input_relatives_namePh.setVerify(R.string.verify_relativesname);
            return true;
        } else {
            input_relatives_namePh.setVerify("");
            return false;
        }
    }

    private boolean checkRelativeName2(String name) {
        if (TextUtils.isEmpty(name)) {
            inputRelativesName2.setVerify(R.string.verify_relativesname);
            return true;
        } else {
            inputRelativesName2.setVerify("");
            return false;
        }
    }

    private boolean checkRelativeName3(String name) {
        if (TextUtils.isEmpty(name)) {
            inputRelativesName3.setVerify(R.string.verify_relativesname);
            return true;
        } else {
            inputRelativesName3.setVerify("");
            return false;
        }
    }

    private void saveJobInfo() {
        spPhUtil.saveString(PhConstants.PhJobInfo.LOAN_INTENT, loanPhIntent);
        spPhUtil.saveString(PhConstants.PhJobInfo.PROFESSION, professionPh);
        spPhUtil.saveString(PhConstants.PhJobInfo.WORK_INDUSTRY, workPhIndustry);
        spPhUtil.saveString(PhConstants.PhJobInfo.COMPANY_NAME, companyPhName);
        spPhUtil.saveString(PhConstants.PhJobInfo.JOB_POSITION, jobPositionPh);
        spPhUtil.saveString(PhConstants.PhJobInfo.MONTHLY_INCOME, monthlyIncomePh);
        spPhUtil.saveString(PhConstants.PhJobInfo.MONTHLY_INCOME_NAME, monthlyIncomePh);
        spPhUtil.saveString(PhConstants.PhJobInfo.WORK_TIME, workTimePh);
        spPhUtil.saveString(PhConstants.PhJobInfo.OFFICE_PHONE, officePhPhone);
        spPhUtil.saveString(PhConstants.PhJobInfo.LOANINTENT_CODE, loanIntentPhCode);
        spPhUtil.saveString(PhConstants.PhJobInfo.PROFESSION_CODE, professionPhCode);
        spPhUtil.saveString(PhConstants.PhJobInfo.WORKINDUSTRY_CODE, workIndustryPhCode);
        spPhUtil.saveString(PhConstants.PhJobInfo.WORKTIME_CODE, workTimePhCode);
        spPhUtil.setContactList(contactInfoList);
        spPhUtil.saveBoolean(PhConstants.FINISH_STATUS.JOB_INFO, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        cursor = managedQuery(contactData, null, null, null, null);
        boolean isOpen = false;
        try {
            isOpen = cursor != null && cursor.moveToFirst();
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage());
        }
        if (isOpen) {
            int hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            if (hasPhone > 0 && !TextUtils.isEmpty(contactId)) {
                try {
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones != null && phones.moveToNext()) {
                        if (relationTag == TAG_ONE) {
                            relativesPhone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            relativesName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                            if (TextUtils.isEmpty(relativesName)) {
//                                input_relatives_namePh.setVerify(R.string.verify_relativesname);
//                                return;
//                            } else {
//                                input_relatives_namePh.setVerify("");
//                            }
                        } else if (relationTag == TAG_TWO) {
                            relativesPhone2 = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            relativesName2 = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                            if (TextUtils.isEmpty(relativesName2)) {
//                                inputRelativesName2.setVerify(R.string.verify_relativesname);
//                                return;
//                            } else {
//                                inputRelativesName2.setVerify("");
//                            }
                        } else {
                            relativesPhone3 = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            relativesName3 = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                            if (TextUtils.isEmpty(relativesName3)) {
//                                inputRelativesName3.setVerify(R.string.verify_relativesname);
//                                return;
//                            } else {
//                                inputRelativesName3.setVerify("");
//                            }
                        }

                    }
                    if (null != phones && !phones.isClosed()) {
                        phones.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (relationTag == TAG_ONE) {
            if (!TextUtils.isEmpty(relativesPhone)) {
                relativesPhone = relativesPhone.replaceAll("[^0-9]", "");
                input_relatives_phonePh.setText(relativesPhone);
//                input_relatives_namePh.setText(relativesName);
            }
        } else if (relationTag == TAG_TWO) {
            if (!TextUtils.isEmpty(relativesPhone2)) {
                relativesPhone2 = relativesPhone2.replaceAll("[^0-9]", "");
                inputRelativesPhone2.setText(relativesPhone2);
//                inputRelativesName2.setText(relativesName2);
            }
        } else {
            if (!TextUtils.isEmpty(relativesPhone3)) {
                relativesPhone3 = relativesPhone3.replaceAll("[^0-9]", "");
                inputRelativesPhone3.setText(relativesPhone3);
//                inputRelativesName3.setText(relativesName3);
            }
        }
    }

    private SpannableString getFirstWordRed(String text) {
        SpannableString style = new SpannableString(text);
        style.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色
        return style;
    }

    @Override
    protected void onDestroy() {
        input_company_namePh.inputClearFocus();
        input_job_positionPh.inputClearFocus();
        input_monthly_incomePh.inputClearFocus();
        input_office_phonePh.inputClearFocus();
        uploadBehaviorRecords(input_intentPh,
                input_professionPh,
                input_working_industryPh,
                input_company_namePh,
                input_job_positionPh,
                input_monthly_incomePh,
                input_working_timePh,
                input_office_phonePh,
                input_relatives_namePh,
                input_relatives_phonePh,
                input_relationshipPh,
                inputRelativesName2,
                inputRelativesPhone2,
                inputRelationship2,
                inputRelativesName3,
                inputRelativesPhone3,
                inputRelationship3);
        BehaviorPhUtils.setDestroyModify(bfModel, "P04_C99");
        MyManage.unregisterListener(sensorListener);
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
        try {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
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
        NetworkPhRequest.uploadDeviceInfo(deviceInfo, new PhSubscriber<AuthInfoPhModel>() {
            @Override
            public void onNext(AuthInfoPhModel authInfoVnModel) {
                super.onNext(authInfoVnModel);
                String code = authInfoVnModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    manager.saveString(PhConstants.AUTH_ID, authInfoVnModel.getData().getAuthId());
                }
            }
        });
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
        dialog.setStep("3");
        dialog.setLeaveListener(new SurveyDialog.OnLeaveClick() {
            @Override
            public void OnLeaveClick(String content) {
                if (TextUtils.isEmpty(content)) {
                    showToast("Please select");
                    return;
                }
                NetworkPhRequest.submitUserSurvey(TokenUtils.getToken(activity), "3",
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
