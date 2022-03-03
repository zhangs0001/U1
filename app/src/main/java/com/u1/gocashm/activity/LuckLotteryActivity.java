package com.u1.gocashm.activity;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.cretin.www.wheelsruflibrary.listener.RotateListener;
import com.cretin.www.wheelsruflibrary.view.WheelSurfView;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.model.response.LotterModel;
import com.u1.gocashm.model.response.LotterResModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.ThreadUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.view.dialog.LotterDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

public class LuckLotteryActivity extends BasePhTitleBarActivity {
    @BindView(R.id.wheelSurfView)
    WheelSurfView wheelSurfView;

    @BindView(R.id.textView)
    TextView mSpinView;


    LotterModel.Lotter mLotter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_lotter);
        ButterKnife.bind(this);

        NetworkPhRequest.getBoundsActivity(TokenUtils.getToken(getApplicationContext()), new PhSubscriber<LotterModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }

            @Override
            public void onNext(LotterModel lotterModel) {
                super.onNext(lotterModel);
                if (CODE_SUCCESS.equals(lotterModel.getCode())) {
                    if (lotterModel.getData() == null) {
                        closeLoadingDialog();
                        return;
                    }
                    downloadBitmapProcess(mLotter = lotterModel.getData());
                } else {
                    closeLoadingDialog();
                    Toast.makeText(LuckLotteryActivity.this, lotterModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        wheelSurfView.setRotateListener(new RotateListener() {
            @Override
            public void rotateEnd(int position) {
                LotterModel.Lotter.Award award = mLotter.getAwards().get(position);
                if (award.getId() == 0) {
                    LotterDialog.showNoWinLotterDialog(getSupportFragmentManager());
                } else {
                    LotterDialog.showWinLotterDialog(getSupportFragmentManager(), award.getTitle());
                }
            }

            @Override
            public void rotating(ValueAnimator valueAnimator) {

            }

            @Override
            public void rotateBefore(ImageView goImg) {
                if (mLotter == null) return;
                if (mLotter.getAwards() == null) return;

                if (mLotter.getBonus_count() <= 0) {
                    LotterDialog.showNoLotterDialog(getSupportFragmentManager());
                } else {
                    start();
                }
            }
        });
    }

    private void start() {
        NetworkPhRequest.addBounds(TokenUtils.getToken(getApplicationContext()), String.valueOf(mLotter.getId()), new PhSubscriber<LotterResModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }

            @Override
            public void onNext(LotterResModel basePhModel) {
                super.onNext(basePhModel);
                closeLoadingDialog();
                if (CODE_SUCCESS.equals(basePhModel.getCode())) {
                    mLotter.setBonus_count(mLotter.getBonus_count() - 1);
                    updateSpinCount(mLotter.getBonus_count());
                    for (int i = 0; i < mLotter.getAwards().size(); i++) {
                        if (mLotter.getAwards().get(i).getId() == basePhModel.getData().getId()) {
                            wheelSurfView.startRotate(i);
                            return;
                        }
                    }
                } else {
                    Toast.makeText(LuckLotteryActivity.this, basePhModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initTitleBar() {
        setTitleBack(getString(R.string.a21));
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    private void downloadBitmapProcess(final LotterModel.Lotter lotter) {
        ThreadUtil.submit(() -> {
            List<Bitmap> icons = new ArrayList<>();
            try {
                for (LotterModel.Lotter.Award award : lotter.getAwards()) {
                    Bitmap bitmap = drawableToBitmap(Glide.with(getApplicationContext()).load(award.getFile()).submit().get());
                    award.setBitmap(bitmap);
                    icons.add(bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (icons.size() != lotter.getAwards().size()) icons.clear();
                onFinish(lotter, icons);
            }
        });

    }

    private void onFinish(final LotterModel.Lotter lotter, List<Bitmap> icons) {
        runOnUiThread(() -> {
            closeLoadingDialog();
            if (icons.isEmpty()) {
                Toast.makeText(this, getString(R.string.a22), Toast.LENGTH_SHORT).show();
            } else {
                Integer[] colors = new Integer[lotter.getAwards().size()];
                for (int i = 0; i < lotter.getAwards().size(); i++) {
                    colors[i] = Color.parseColor(i % 2 == 0 ? "#FFCC66" : "#FFFC95");
                }

                wheelSurfView.setConfig(new WheelSurfView.Builder()
                        .setmColors(colors)
                        .setmTypeNum(lotter.getAwards().size())
                        .setmIcons(icons)
                        .build());

                updateSpinCount(mLotter.getBonus_count());
            }
        });
    }

    private void updateSpinCount(int count) {
        mSpinView.setText(getString(R.string.a23) + count + getString(R.string.a24));
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
