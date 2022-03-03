package com.u1.gocashm.activity.mine;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.blankj.utilcode.util.RegexUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhActivity;
import com.u1.gocashm.util.constant.PhConstants;


public class GooglePlayPhActivity extends BasePhActivity {
    private WebView webView;
    private String title;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initViews();
        setOnListener();
        initData();
    }

    protected void initViews() {
        webView = (WebView) findViewById(R.id.web_view);
    }

    protected void setOnListener() {
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        //点击后退按钮,让WebView后退一页
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            //当WebView进度改变时更新窗口进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                setProgress(newProgress * 100);
            }
        });
    }

    protected void initData() {
        title = getIntent().getStringExtra(PhConstants.WEB_TITLE);
        url = getIntent().getStringExtra(PhConstants.WEB_URL);
        if (RegexUtils.isURL(url)) {
            webView.loadUrl(url);
        } else {
            showToast(getString(R.string.error_url_invalid));
        }
    }
}
