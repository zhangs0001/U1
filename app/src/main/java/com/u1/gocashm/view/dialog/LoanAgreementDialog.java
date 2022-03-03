package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.WebPhActivity;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.view.MyWebView;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

import org.jetbrains.annotations.NotNull;

/**
 * @author hewei
 * @date 2019/9/24 0024 下午 2:07
 */
public class LoanAgreementDialog extends AbsBaseCircleDialog {

    private MyWebView webView;
    private TextView tvDisagree;
    private TextView tvAgree;
    private TextView tv_policy_text;
    private boolean isLoadError;
    //定义一个接口对象listener
    private OnClick listener;
    private OnCancelClick cancelClick;

    private String loanContractUrl;
    private Long initTime;
    private Long endTime;

    private CheckBox checkbox_policy;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_loan_agreement, container);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        setCanceledOnTouchOutside(false);
        initTime = System.currentTimeMillis();
        checkbox_policy = view.findViewById(R.id.checkbox_policy);
        tv_policy_text = view.findViewById(R.id.tv_policy_text);
        webView = view.findViewById(R.id.dialog_webview);
        tvDisagree = view.findViewById(R.id.tv_disagree);
        tvAgree = view.findViewById(R.id.tv_agree);
        if (!TextUtils.isEmpty(loanContractUrl)) {
            webView.loadDataWithBaseURL(null, loanContractUrl, "text/html", "utf-8", null);
        }

        tvAgree.setEnabled(true);
        tvDisagree.setEnabled(true);
        tvAgree.setBackgroundResource(R.drawable.shape_agree_btn_able);
        tvDisagree.setBackgroundResource(R.drawable.shape_disagree_btn_able);
        tvDisagree.setTextColor(getResources().getColor(R.color.black_1));

//        setTvUpdatePhoneStyle();
    }

    private void setTvUpdatePhoneStyle() {
        final String text = getString(R.string.policy_text);
        SpannableString spanText = new SpannableString(text);
        final String privacyText = getString(R.string.policy_privacy);
        final String agreeText = getString(R.string.policy_agree);
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

        tv_policy_text.setHighlightColor(Color.TRANSPARENT);
        tv_policy_text.setText(spanText);
        tv_policy_text.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initListener() {
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setOnCustomScrollChangeListener(null);  // 将listener置空
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                isLoadError = true;  // webview加载失败 的flag（全局变量）
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                isLoadError = true; // webview加载失败 的flag
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isLoadError = false; // webview开始加载时的默认值
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // webview加载完成后
                /*if (webView != null) {
                    webView.setOnCustomScrollChangeListener(new MyWebView.ScrollInterface() {
                        @Override
                        public void onSChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                            if (!webView.canScrollVertically(1) && !isLoadError) {
                                tvAgree.setEnabled(true);
                                tvDisagree.setEnabled(true);
                                tvAgree.setBackgroundResource(R.drawable.shape_agree_btn_able);
                                tvDisagree.setBackgroundResource(R.drawable.shape_disagree_btn_able);
                                tvDisagree.setTextColor(getResources().getColor(R.color.black_1));
                            }
                        }
                    });
                }*/
            }
        });
        tvDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTime = System.currentTimeMillis();
                cancelClick.OnCancelClick();
                dismiss();
            }
        });
        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkbox_policy.isChecked()) {
                    ToastUtils.showShort(getString(R.string.a25));
                    return;
                }
                endTime = System.currentTimeMillis();
                listener.OnClick();
                dismiss();
            }
        });
    }

    public void setLoanContractUrl(String loanContractUrl) {
        this.loanContractUrl = loanContractUrl;
    }

    public Long getInitTime() {
        return initTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    //获得接口对象的方法。
    public void setOnClick(OnClick listener) {
        this.listener = listener;
    }

    public interface OnClick {
        void OnClick();
    }

    //获得接口对象的方法。
    public void setOnCancelClick(OnCancelClick cancelClick) {
        this.cancelClick = cancelClick;
    }

    public interface OnCancelClick {
        void OnCancelClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
