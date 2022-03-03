package com.u1.gocashm.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.LuckLotteryActivity;

public class LotterEnterView extends FrameLayout {
    private TextView textView;
    private ImageView gifView;

    public LotterEnterView(@NonNull Context context) {
        this(context, null);
    }

    public LotterEnterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LotterEnterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_lotter_enter, this);
        textView = findViewById(R.id.textView2);
        textView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        gifView = findViewById(R.id.gifView);
        Glide.with(getContext()).asGif().load(R.raw.lotter).into(gifView);
        setOnClickListener(v -> getContext().startActivity(new Intent(getContext(), LuckLotteryActivity.class)));
    }
}
