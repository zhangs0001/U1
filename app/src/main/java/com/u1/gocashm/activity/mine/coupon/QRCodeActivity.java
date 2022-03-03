package com.u1.gocashm.activity.mine.coupon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.util.QRCodeUtil;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.constant.PhConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/12 0012.
 */

public class QRCodeActivity extends BasePhTitleBarActivity {
    @BindView(R.id.qrcode_iv)
    ImageView qrCodeImg;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        initViews();
        initData();
    }

    protected void initData() {
        url = getIntent().getStringExtra(PhConstants.INVITE_URL);
        Bitmap qrBitmap = QRCodeUtil.createQRCodeBitmap(url, getScreenWidth() * 2 / 3, getScreenWidth() * 2 / 3);
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qr_logo);
        Bitmap bitmap = QRCodeUtil.addLogo(qrBitmap, logoBitmap);
        qrCodeImg.setImageBitmap(bitmap);
    }

    protected void initViews() {
        setTitle(R.string.qrcode);
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.qrcode);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }


    /**
     * 获取屏幕宽高
     */
    public int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
