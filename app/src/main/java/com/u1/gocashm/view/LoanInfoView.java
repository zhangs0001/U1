package com.u1.gocashm.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.model.response.ScheduleCalcPhModel;
import com.u1.gocashm.util.TokenUtils;

/**
 * Created by jishudiannao on 2018/9/5.
 */

public class LoanInfoView extends LinearLayout {

    private View layout, view_2, view_3, view_4, view_5;
    private TextView tv_amount, tv_date, tv_payment, tv_1, tv_2, tv_3, tv_4, tv_5;
    private SeekBar sb_pro;
    private Context mContext;
    private LinearLayout scheduleLayout;
    private TextView tvLoanAmount;
    private TextView tvApplyDate;
    private TextView tvFirstPeriodDate;
    private TextView tvFirstPeriodAmount;
    private TextView tvSecondPeriodDate;
    private TextView tvSecondPeriodAmount;
    private LinearLayout amountLayout;
    private LinearLayout llStag;

    public LoanInfoView(Context context) {
        super(context);
        init(context);
    }

    public LoanInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setOrientation(LinearLayout.VERTICAL);
        layout = inflater.inflate(R.layout.layout_loan_info, null);
        addView(layout);
        mContext = context;
        initView();
    }

    private void initView() {
        ScheduleCalcPhModel.ScheduleCalc calcModel = TokenUtils.getCalcModel();
        tv_amount = layout.findViewById(R.id.tv_loan_amount);
        tv_date = layout.findViewById(R.id.tv_loan_date);
        tv_payment = layout.findViewById(R.id.tv_loan_payment);
        tv_1 = layout.findViewById(R.id.tv_loan_info_1);
        tv_2 = layout.findViewById(R.id.tv_loan_info_2);
        tv_3 = layout.findViewById(R.id.tv_loan_info_3);
        tv_4 = layout.findViewById(R.id.tv_loan_info_4);
        tv_5 = layout.findViewById(R.id.tv_loan_info_5);
        view_2 = layout.findViewById(R.id.view_loan_info_2);
        view_3 = layout.findViewById(R.id.view_loan_info_3);
        view_4 = layout.findViewById(R.id.view_loan_info_4);
        view_5 = layout.findViewById(R.id.view_loan_info_5);
        sb_pro = layout.findViewById(R.id.sb_loanid_pro);
        scheduleLayout = layout.findViewById(R.id.schedule_layout);
        amountLayout = layout.findViewById(R.id.ll_amount_layout);
        llStag = layout.findViewById(R.id.ll_staging);
        tvLoanAmount = layout.findViewById(R.id.tv_loan_total_amount);
        tvApplyDate = layout.findViewById(R.id.tv_apply_date);
        tvFirstPeriodDate = layout.findViewById(R.id.tv_first_period_date);
        tvFirstPeriodAmount = layout.findViewById(R.id.tv_first_period_amount);
        tvSecondPeriodDate = layout.findViewById(R.id.tv_second_period_date);
        tvSecondPeriodAmount = layout.findViewById(R.id.tv_second_period_amount);
        sb_pro.setClickable(false);
        sb_pro.setEnabled(false);
        sb_pro.setSelected(false);
        sb_pro.setFocusable(false);
        if (calcModel != null) {
//            if (calcModel.schedules != null) {
//                if (calcModel.schedules.size() > 1) {
//                    List<ScheduleCalcPhModel.ScheduleCalc.Schedule> scheduleList = calcModel.schedules;
//                    ScheduleCalcPhModel.ScheduleCalc.Schedule schedule1 = scheduleList.get(0);
//                    ScheduleCalcPhModel.ScheduleCalc.Schedule schedule2 = scheduleList.get(1);
//                    llStag.setVisibility(GONE);
//                    amountLayout.setVisibility(GONE);
//                    tvApplyDate.setText(calcModel.applyDate);
//                    tvLoanAmount.setText(String.format(mContext.getString(R.string.loan_how_much), PhUtils.setMoney(calcModel.actualAmount)));
//                    tvFirstPeriodAmount.setText(String.format(mContext.getString(R.string.loan_how_much), PhUtils.setMoney(schedule1.totalAmt)));
//                    tvFirstPeriodDate.setText(schedule1.loanPmtDueDate);
//                    tvSecondPeriodAmount.setText(String.format(mContext.getString(R.string.loan_how_much), PhUtils.setMoney(schedule2.totalAmt)));
//                    tvSecondPeriodDate.setText(schedule2.loanPmtDueDate);
//                } else {
//                    llStag.setVisibility(GONE);
//                    amountLayout.setVisibility(VISIBLE);
//                    tv_amount.setText(String.format(mContext.getString(R.string.loan_how_much), PhUtils.setMoney(calcModel.actualAmount)));
//                    tv_date.setText(calcModel.loanPmtDueDate);
//                    tv_payment.setText(String.format(mContext.getString(R.string.loan_how_much), PhUtils.setMoney(calcModel.totalAmt)));
//                }
//            }
        }
    }

    public void setStatus(int status) {
        if (sb_pro.getProgress() != 0) return;
        Drawable thumb = null;
        if (status >= 1) {
            llStag.setVisibility(GONE);
            amountLayout.setVisibility(GONE);
            setColor(tv_1);
            sb_pro.setProgress(2);
            thumb = getResources().getDrawable(R.drawable.ic_seekhumb1);
        }
        if (status >= 2) {
            llStag.setVisibility(GONE);
            amountLayout.setVisibility(GONE);
            setColor(tv_2);
            view_2.setBackgroundColor(getResources().getColor(R.color.blue_1));
            sb_pro.setProgress(25);
            thumb = getResources().getDrawable(R.drawable.ic_seekhumb2);
        }
        if (status >= 3) {
            llStag.setVisibility(GONE);
            amountLayout.setVisibility(GONE);
            setColor(tv_3);
            view_3.setBackgroundColor(getResources().getColor(R.color.blue_1));
            sb_pro.setProgress(50);
            thumb = getResources().getDrawable(R.drawable.ic_seekhumb3);
        }
        if (status >= 4) {
            setColor(tv_4);
            llStag.setVisibility(GONE);
            amountLayout.setVisibility(GONE);
            view_4.setBackgroundColor(getResources().getColor(R.color.blue_1));
            sb_pro.setProgress(75);
            thumb = getResources().getDrawable(R.drawable.ic_seekhumb4);
        }
        if (status >= 5) {
            setColor(tv_5);
            llStag.setVisibility(GONE);
            amountLayout.setVisibility(GONE);
            view_5.setBackgroundColor(getResources().getColor(R.color.blue_1));
            sb_pro.setProgress(100);
            thumb = getResources().getDrawable(R.drawable.ic_seekhumb5);
        }
        sb_pro.setThumb(thumb);
        sb_pro.setThumbOffset(0);
    }

    private void setColor(TextView tv) {
        tv.setTextColor(getResources().getColor(R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tv.setBackground(getResources().getDrawable(R.drawable.shape_oval_blue));
        } else {
            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_oval_blue));
        }
    }

    public void setScheduleLayoutVisible(boolean visible) {
        llStag.setVisibility(GONE);
        amountLayout.setVisibility(VISIBLE);
        scheduleLayout.setVisibility(visible ? VISIBLE : GONE);
    }
}