package com.u1.gocashm.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhActivity;
import com.u1.gocashm.activity.mine.RegisterActivity;
import com.u1.gocashm.util.AFEventUtil;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.view.CustomScrollView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PolicyActivity extends BasePhActivity {

    @BindView(R.id.tv_policy_text)
    TextView tvPolicyYnText;
    @BindView(R.id.my_scrollView)
    CustomScrollView myScrollView;
    @BindView(R.id.tv_disagree)
    TextView tvDisagree;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    @BindView(R.id.checkbox_policy)
    CheckBox checkboxPolicy;

    private Long initTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        ButterKnife.bind(this);
        initView();
        initTime = System.currentTimeMillis();
    }

    private void initView() {
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.white));
        setTvUpdatePhoneStyle();
        myScrollView.setOnScrollChangeListener(new CustomScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollToStart() {

            }

            @Override
            public void onScrollToEnd() {
                tvAgree.setEnabled(true);
                tvDisagree.setEnabled(true);
                tvAgree.setBackgroundResource(R.drawable.shape_agree_btn_able);
                tvDisagree.setBackgroundResource(R.drawable.shape_disagree_btn_able);
                tvDisagree.setTextColor(getResources().getColor(R.color.black_1));
            }
        });
        checkboxPolicy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tvAgree.setEnabled(true);
                    tvDisagree.setEnabled(true);
                    tvAgree.setBackgroundResource(R.drawable.shape_agree_btn_able);
                    tvDisagree.setBackgroundResource(R.drawable.shape_disagree_btn_able);
                    tvDisagree.setTextColor(getResources().getColor(R.color.black_1));
                } else {
                    tvAgree.setEnabled(false);
                    tvDisagree.setEnabled(false);
                    tvAgree.setBackgroundResource(R.drawable.shape_agree_btn_enable);
                    tvDisagree.setBackgroundResource(R.drawable.shape_disagree_btn_enable);
                    tvDisagree.setTextColor(getResources().getColor(R.color.grey_5));
                }
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
                Intent intent = new Intent(PolicyActivity.this, WebPhActivity.class);
                intent.putExtra(PhConstants.WEB_TITLE, privacyText);
                intent.putExtra(PhConstants.WEB_URL, PhConstants.URL_POLICY);
                startActivity(intent);
            }
        }, start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new AbsoluteSizeSpan(16, true), start1, end1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spanText.setSpan(new ForegroundColorSpan(Color.parseColor("#266BB7")), start1, end1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
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
                Intent intent = new Intent(PolicyActivity.this, WebPhActivity.class);
                intent.putExtra(PhConstants.WEB_TITLE, agreeText);
                intent.putExtra(PhConstants.WEB_URL, PhConstants.URL_AGREEMENT);
                startActivity(intent);
            }
        }, start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new AbsoluteSizeSpan(16, true), start2, end2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spanText.setSpan(new ForegroundColorSpan(Color.parseColor("#266BB7")), start2, end2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);


        /*int start3 = text.indexOf(consendText);
        int end3 = text.indexOf(consendText) + consendText.length();
        spanText.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(@NotNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.blue)); //设置文件颜色
            }

            @Override
            public void onClick(@NotNull View view) {
                Intent intent = new Intent(PolicyActivity.this, WebPhActivity.class);
                intent.putExtra(PhConstants.WEB_TITLE, consendText);
                intent.putExtra(PhConstants.WEB_URL, PhConstants.URL_CONSENT_FORM);
                startActivity(intent);
            }
        }, start3, end3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/


        tvPolicyYnText.setHighlightColor(Color.TRANSPARENT);
        tvPolicyYnText.setText(spanText);
        tvPolicyYnText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick({R.id.tv_disagree, R.id.tv_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_disagree:
                toReject();
                break;
            case R.id.tv_agree:
                toMain();
                break;
        }
    }

    private void toReject() {
        startActivity(new Intent(this, AgreementRejectActivity.class));
    }

    private void toMain() {
        if (!checkboxPolicy.isChecked()) {
            showToast(getString(R.string.a25));
            return;
        }
        SharedPreferencesPhUtil preferencesUtil = SharedPreferencesPhUtil.getInstance(PolicyActivity.this);
        preferencesUtil.saveLong(PhConstants.AGREEMENT_END_TIME, System.currentTimeMillis());
        preferencesUtil.saveLong(PhConstants.AGREEMENT_INIT_TIME, initTime);
        preferencesUtil.saveBoolean(PhConstants.FIRST_RUN_DIALOG, true);
        AFEventUtil.afEvent(PhConstants.FaceBookEvent.EVENT_INIT_AGREEMENT, this, null);
        Intent intent = new Intent();
        intent.setClass(PolicyActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
