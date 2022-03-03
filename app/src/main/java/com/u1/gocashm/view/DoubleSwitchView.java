package com.u1.gocashm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.util.DpUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 双向选择切换view
 *
 * @author LiuFeng
 * @data 2021/6/9 11:09
 */
public class DoubleSwitchView extends FrameLayout {
    private Context context;
    private TextView leftTv;
    private TextView rightTv;
    private String leftText;
    private String rightText;
    private boolean isLeftSelected;
    private float textSize;
    private int textStyle;
    private int selectedTextColor;
    private int unselectedTextColor;
    private Drawable selectedBackground;
    private Drawable unselectedBackground;
    private int backgroundResId;
    private SwitchListener listener;

    public DoubleSwitchView(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public DoubleSwitchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleSwitchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs, defStyleAttr);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DoubleSwitchView, defStyleAttr, 0);
        isLeftSelected = typedArray.getBoolean(R.styleable.DoubleSwitchView_leftSelected, true);
        leftText = typedArray.getString(R.styleable.DoubleSwitchView_leftText);
        rightText = typedArray.getString(R.styleable.DoubleSwitchView_rightText);
        textSize = typedArray.getDimension(R.styleable.DoubleSwitchView_textSize, DpUtil.dp2px(context, 18));
        textStyle = typedArray.getInteger(R.styleable.DoubleSwitchView_textStyle, 0);
        selectedTextColor = typedArray.getColor(R.styleable.DoubleSwitchView_selectedTextColor, getResources().getColor(R.color.color_7A3A10));
        unselectedTextColor = typedArray.getColor(R.styleable.DoubleSwitchView_unselectedTextColor, getResources().getColor(R.color.color_D9D3A8));
        selectedBackground = typedArray.getDrawable(R.styleable.DoubleSwitchView_selectedBackground);
        unselectedBackground = typedArray.getDrawable(R.styleable.DoubleSwitchView_unselectedBackground);
        typedArray.recycle();

        initView(context);
        initListener();
    }

    private void initView(@NonNull Context context) {
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);
//        setBackgroundResource(R.drawable.bg_orange_round_22);

        leftTv = new TextView(context);
        leftTv.setText(leftText);
        leftTv.setTextSize(DpUtil.px2dp(context, textSize));
        leftTv.setGravity(Gravity.CENTER);
        leftTv.setTypeface(Typeface.defaultFromStyle(textStyle));
        LayoutParams leftLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DpUtil.dp2px(context, 35));
        leftLp.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        leftTv.setLayoutParams(leftLp);
        addView(leftTv);

        rightTv = new TextView(context);
        rightTv.setText(rightText);
        rightTv.setTextSize(DpUtil.px2dp(context, textSize));
        rightTv.setGravity(Gravity.CENTER);
        rightTv.setTypeface(Typeface.defaultFromStyle(textStyle));
        LayoutParams rightLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DpUtil.dp2px(context, 35));
        rightLp.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        rightTv.setLayoutParams(rightLp);
        addView(rightTv);

        setSwitchStatus(isLeftSelected);
    }

    private void initListener() {
        leftTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSwitchStatus(true);
                if (listener != null) {
                    listener.onSelectedChange(true);
                }
            }
        });

        rightTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setSwitchStatus(false);
                if (listener != null) {
                    listener.onSelectedChange(false);
                }
            }
        });
    }

    public void setSwitchStatus(boolean isLeftSelected) {
        this.isLeftSelected = isLeftSelected;
        if (isLeftSelected) {
            leftTv.setTextColor(selectedTextColor);
            leftTv.setBackground(selectedBackground);
            rightTv.setTextColor(unselectedTextColor);
            rightTv.setBackground(unselectedBackground);
            LayoutParams leftLp = (LayoutParams) leftTv.getLayoutParams();
            leftLp.leftMargin = DpUtil.dp2px(context, 8);
            leftTv.setLayoutParams(leftLp);
            leftTv.setPadding(DpUtil.dp2px(context, 19), 0, DpUtil.dp2px(context, 19), 0);
            rightTv.setPadding(0, 0, DpUtil.dp2px(context, 16), 0);
        } else {
            rightTv.setTextColor(selectedTextColor);
            rightTv.setBackground(selectedBackground);
            leftTv.setTextColor(unselectedTextColor);
            leftTv.setBackground(unselectedBackground);
            LayoutParams rightLp = (LayoutParams) rightTv.getLayoutParams();
            rightLp.rightMargin = DpUtil.dp2px(context, 8);
            rightTv.setLayoutParams(rightLp);
            rightTv.setPadding(DpUtil.dp2px(context, 19), 0, DpUtil.dp2px(context, 19), 0);
            leftTv.setPadding(DpUtil.dp2px(context, 16), 0, 0, 0);
        }
    }

    public int getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public int getUnselectedTextColor() {
        return unselectedTextColor;
    }

    public void setUnselectedTextColor(int unselectedTextColor) {
        this.unselectedTextColor = unselectedTextColor;
    }

    public String getLeftText() {
        return leftText;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public Drawable getSelectedBackground() {
        return selectedBackground;
    }

    public void setSelectedBackground(Drawable selectedBackground) {
        this.selectedBackground = selectedBackground;
    }

    public Drawable getUnselectedBackground() {
        return unselectedBackground;
    }

    public void setUnselectedBackground(Drawable unselectedBackground) {
        this.unselectedBackground = unselectedBackground;
    }

    public int getBackgroundResId() {
        return backgroundResId;
    }

    public void setBackgroundResId(int backgroundResId) {
        this.backgroundResId = backgroundResId;
    }

    public void setSwitchListener(SwitchListener listener) {
        this.listener = listener;
    }

    public interface SwitchListener {
        void onSelectedChange(boolean isLeftSelected);
    }
}
