package com.u1.gocashm.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.BuildConfig;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.WebPhActivity;
import com.u1.gocashm.activity.loan.ConfirmLoanActivity;
import com.u1.gocashm.activity.loan.IdCardPhActivity;
import com.u1.gocashm.activity.loan.IdentityInfoActivity;
import com.u1.gocashm.activity.loan.JobInfoPhActivity;
import com.u1.gocashm.activity.loan.LoanAddressPhActivity;
import com.u1.gocashm.activity.loan.LoanIdentityPhActivity;
import com.u1.gocashm.activity.loan.PayInfoPhActivity;
import com.u1.gocashm.fragment.base.BasePhFragment;
import com.u1.gocashm.model.request.ApplyReqPhModel;
import com.u1.gocashm.model.response.ApplyRespModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.OrderListModel;
import com.u1.gocashm.model.response.StepModel;
import com.u1.gocashm.model.response.User;
import com.u1.gocashm.model.response.UserInfoModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.GpsPhUtils;
import com.u1.gocashm.util.GsonPhHelper;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.model.LocationInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;


/**
 * @author hewei
 * @date 2019/11/16 0016 下午 6:05
 */
public class PreViewFragment extends BasePhFragment {

    Unbinder unbinder;
    private View view;
    private ViewGroup parent;
    private int quality;
    private String contactsStatus;
    private Integer orderId;


    @BindView(R.id.tv_policy_text)
    TextView tvPolicyYnText;
    @BindView(R.id.checkbox_policy)
    CheckBox checkboxPolicy;

    private ArrayList<Integer> steps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("zhangs", "onCreateView: PreViewFragment");
        // 启动首页
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_loan, null);
            unbinder = ButterKnife.bind(this, view);
            initData();
            initView();
        } else {
            parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initView();
        }
    }

    private void initView() {
        steps.clear();
        setTvUpdatePhoneStyle();
        SharedPreferencesPhUtil.getInstance(getActivity()).saveBoolean(PhConstants.IS_CONTINUE, false);
        NetworkPhRequest.getUserInfo(TokenUtils.getToken(getActivity()), new PhSubscriber<UserInfoModel>() {
            @Override
            public void onStart() {
                super.onStart();
               // showLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //closeLoadingDialog();
            }

            @Override
            public void onNext(UserInfoModel userInfoModel) {
                super.onNext(userInfoModel);
//                closeLoadingDialog();
                String code = userInfoModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    UserInfoModel.UserInfo data = userInfoModel.getData();
                    User user = data.getUser();
                    if (user != null) {
                        quality = user.getQuality();
                        contactsStatus = user.getContactsStatus();
                        OrderListModel.OrderList.Order order = user.getOrder();
                        if (order != null) {
                            orderId = order.getId();
                            TokenUtils.setOrderId(getActivity(), orderId);
                        }
                        if (user.basicDetailStatus == 2) {
                            steps.add(1);
                        }

                        if ("2".equals(user.contactsStatus)) {
                            steps.add(3);
                        }

                        if (user.idCardStatus == 2) {
                            steps.add(4);
                        }

                        if (user.bankCardStatus == 2) {
                            steps.add(5);
                        }

                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
//            unbinder = null;
            unbinder.unbind();
        }

    }

    @OnClick({R.id.bt_loan_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_loan_apply:
                if (!checkboxPolicy.isChecked()) {
                    ToastUtils.showShort(getString(R.string.a25));
                    return;
                }
                apply();
                break;
        }
    }

    private void apply() {
        if (quality == 1) {
            initApply(true);
        } else {
            ApplyReqPhModel reqPhModel = new ApplyReqPhModel();
            reqPhModel.setToken(TokenUtils.getToken(getActivity()));
            NetworkPhRequest.getUserStep(reqPhModel, new PhSubscriber<StepModel>() {
                @Override
                public void onStart() {
                    super.onStart();
                    showLoadingDialog();
                }

                @Override
                public void onNext(StepModel stepModel) {
                    super.onNext(stepModel);
                    closeLoadingDialog();
                    String code = stepModel.getCode();
                    if (CODE_SUCCESS.equals(code)) {
                        StepModel.Step data = stepModel.getData();
                        if (data != null) {
                            String step_name = data.getStep_name();
                            if (TextUtils.isEmpty(step_name)) {
                                initApply(false);
                            } else {
                                toStep(step_name);
                            }
                        } else {
                            initApply(false);
                        }
                    } else {
                        ToastUtils.showShort(stepModel.getMsg());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    ToastUtils.showShort(R.string.error_request_fail);
                }
            });
        }
    }

    private void toStep(String step_name) {
        Intent intent = new Intent();
        switch (step_name) {
            case "baseInfo":
                intent.setClass(getActivity(), LoanAddressPhActivity.class);
                break;
            case "addressInfo":
                intent.setClass(getActivity(), JobInfoPhActivity.class);
                break;
            case "jobInfo":
                intent.setClass(getActivity(), IdCardPhActivity.class);
                break;
            case "photoInfo":
                intent.setClass(getActivity(), PayInfoPhActivity.class);
                break;
            case "payInfo":
                intent.setClass(getActivity(), ConfirmLoanActivity.class);
                break;
            default:
                intent.setClass(getActivity(), LoanIdentityPhActivity.class);
                break;
        }
        startActivity(intent);
    }

    private void initApply(boolean isContinue) {
        ApplyReqPhModel reqPhModel = new ApplyReqPhModel();
        LocationInfo locationInfo = new LocationInfo();
        Location location = GpsPhUtils.getLocation();
        if (location != null) {
            if (BuildConfig.DEBUG) {
                locationInfo.setLatitude(String.valueOf(17.394144));
                locationInfo.setLongitude(String.valueOf(78.471902));
            } else {
                locationInfo.setLatitude(String.valueOf(location.getLatitude()));
                locationInfo.setLongitude(String.valueOf(location.getLongitude()));
            }
        }
        reqPhModel.setToken(TokenUtils.getToken(getActivity()));
        reqPhModel.setPosition(GsonPhHelper.getGson().toJson(locationInfo));
        NetworkPhRequest.getLocation(reqPhModel, new PhSubscriber<>());
        reqPhModel.setClient_id("android");
        reqPhModel.setIt_service_need(1);
        NetworkPhRequest.createOrder(reqPhModel, new PhSubscriber<BasePhModel>() {
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
                    try {
                        String json = GsonPhHelper.getGson().toJson(basePhModel.getData());
                        ApplyRespModel.Apply apply = GsonPhHelper.getGson().fromJson(json, ApplyRespModel.Apply.class);
                        if (apply != null) {
                            orderId = apply.getId();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (isContinue) {
                        int currentStep;
                        Class target = PayInfoPhActivity.class;
                        if (!steps.isEmpty()) {
                            target = IdentityInfoActivity.getStepByClass(currentStep = steps.get(0));
                            steps.clear();
                            for (int i = currentStep; i <= 5; i++) {
                                steps.add(i);
                            }
                        }
                        Intent intent = new Intent(getActivity(), target);
                        if (JobInfoPhActivity.class.equals(target)) {
                            intent.putExtra(PhConstants.APPLY_FLAG, "SETTLE");
                        }
                        intent.putIntegerArrayListExtra("steps", steps);
                        startActivity(intent);


                        SharedPreferencesPhUtil.getInstance(getActivity()).saveBoolean(PhConstants.IS_CONTINUE, true);
                        AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_LOAN_CONTINUE, getActivity(), orderId);
                    } else {
                        startActivity(new Intent(getActivity(), LoanIdentityPhActivity.class));
                        AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_LOAN_APPLY, getActivity(), orderId);
                    }
                    TokenUtils.setOrderId(getActivity(), orderId);
                } else {
                    ToastUtils.showShort(basePhModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                closeLoadingDialog();
                super.onError(e);
            }
        });
    }

    private void setTvUpdatePhoneStyle() {
        final String text = getString(R.string.policy_text);
        SpannableString spanText = new SpannableString(text);
        final String privacyText = getString(R.string.policy_privacy);
        final String agreeText = getString(R.string.policy_agree);
        //final String consendText = "Consent Form";
        int start1 = text.indexOf(privacyText);
        int end1 = text.indexOf(privacyText) + privacyText.length();
        spanText.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(@NotNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.blue));
            }

            @Override
            public void onClick(@NotNull View view) {
                Intent intent = new Intent(getContext(), WebPhActivity.class);
                intent.putExtra(PhConstants.WEB_TITLE, privacyText);
                intent.putExtra(PhConstants.WEB_URL, PhConstants.URL_POLICY);
                startActivity(intent);
            }
        }, start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new AbsoluteSizeSpan(16, true), start1, end1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spanText.setSpan(new ForegroundColorSpan(Color.parseColor("#085F06")), start1, end1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        int start2 = text.indexOf(agreeText);
        int end2 = text.indexOf(agreeText) + agreeText.length();
        spanText.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(@NotNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.blue)); //设置文件颜色
            }

            @Override
            public void onClick(@NotNull View view) {
                Intent intent = new Intent(getContext(), WebPhActivity.class);
                intent.putExtra(PhConstants.WEB_TITLE, agreeText);
                intent.putExtra(PhConstants.WEB_URL, PhConstants.URL_AGREEMENT);
                startActivity(intent);
            }
        }, start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new AbsoluteSizeSpan(16, true), start2, end2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spanText.setSpan(new ForegroundColorSpan(Color.parseColor("#085F06")), start2, end2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        tvPolicyYnText.setHighlightColor(Color.TRANSPARENT);
        tvPolicyYnText.setText(spanText);
        tvPolicyYnText.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
