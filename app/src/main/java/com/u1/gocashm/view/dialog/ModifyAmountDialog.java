package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.WebPhActivity;
import com.u1.gocashm.adapter.LoanTermAdapter;
import com.u1.gocashm.model.request.ScheduleCalcPhReqModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.ProductPhModel;
import com.u1.gocashm.model.response.ScheduleCalcPhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.PhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.constant.PhConstants;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

import static android.view.View.VISIBLE;
import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;


/**
 * @author hewei
 * @date 2019/9/18 0018 上午 10:31
 */
public class ModifyAmountDialog extends AbsBaseCircleDialog {

    public static final String UP_AMOUNT = "up_amount";
    public static final String DOWN_AMOUNT = "down_amount";


    private TextView tvDialogContent;
    private SeekBar amountSeekBar;
    private TextView tvLoanAmount;
    private TextView tvLoanDate;
    private TextView tvLoanPayment;
    private TextView tvDialogCancel;
    private TextView tvDialogConfirm;
    private TextView tvAmountMessage;
    private TextView tvLoanAmountMin;
    private TextView tvLoanAmountMax;
    private CheckBox checkboxAgreement;
    private TextView tvAgreementHint;
    private TextView tvAgreement;
    private RecyclerView loanTermRecyclerView;
    private TextView tvApplyDate;
    private TextView tvRepayLoanAmount;
    private TextView tvFirstPeriodDate;
    private TextView tvFirstPeriodAmount;
    private TextView tvSecondPeriodDate;
    private TextView tvSecondPeriodAmount;
    private LinearLayout llStaging;
    private LinearLayout amountLayout;

    private ProductPhModel.Product product;
    private String dialogType;
    private Integer amount;
    private Integer term;
    private Integer maxAmount;
    private Integer amountStep;
    private Integer termStep;
    private String productCode;
    private LoanTermAdapter termAdapter;

    //定义一个接口对象listener
    private OnConfirmClick confirmListener;
    private OnCancelClick cancelListener;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_modify_amount, container);
        initView(view);
        initListener();
        initData();
        return view;
    }

    private void initView(View view) {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setCanceledBack(false);
        tvDialogContent = view.findViewById(R.id.tv_dialog_content);
        amountSeekBar = view.findViewById(R.id.sb_loan_balance);
        tvAmountMessage = view.findViewById(R.id.tv_loan_messages);
        tvLoanAmount = view.findViewById(R.id.tv_loan_amount);
        tvLoanDate = view.findViewById(R.id.tv_loan_duedate);
        tvLoanPayment = view.findViewById(R.id.tv_loan_totalamount);
        tvDialogCancel = view.findViewById(R.id.tv_dialog_cancel);
        tvDialogConfirm = view.findViewById(R.id.tv_dialog_confirm);
        tvLoanAmountMin = view.findViewById(R.id.tv_loan_amount_min);
        tvLoanAmountMax = view.findViewById(R.id.tv_loan_amount_max);
        checkboxAgreement = view.findViewById(R.id.checkbox_agreement);
        tvAgreementHint = view.findViewById(R.id.tv_agreement_hint);
        tvAgreement = view.findViewById(R.id.tv_agreement);
        loanTermRecyclerView = view.findViewById(R.id.loan_term_recyclerView);
        amountLayout = view.findViewById(R.id.ll_repay_info);
        llStaging = view.findViewById(R.id.ll_staging);
        tvRepayLoanAmount = view.findViewById(R.id.tv_loan_repay_amount);
        tvApplyDate = view.findViewById(R.id.tv_apply_date);
        tvFirstPeriodDate = view.findViewById(R.id.tv_first_period_date);
        tvFirstPeriodAmount = view.findViewById(R.id.tv_first_period_amount);
        tvSecondPeriodDate = view.findViewById(R.id.tv_second_period_date);
        tvSecondPeriodAmount = view.findViewById(R.id.tv_second_period_amount);
        checkboxAgreement.setChecked(true);
        initSeekBar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        loanTermRecyclerView.setLayoutManager(layoutManager);
    }

    private void setRecyclerView() {
        product.getLoanTerms().get(0).setSelected(true);
        product.getLoanTerms().get(1).setSelected(false);
        termAdapter = new LoanTermAdapter(R.layout.item_loan_term_layout, product.getLoanTerms());
        termAdapter.setOnItemClick(new LoanTermAdapter.ItemClick() {
            @Override
            public void ItemClick(int loanTerm) {
                term = loanTerm;
                scheduleCalc();
            }
        });
        loanTermRecyclerView.setAdapter(termAdapter);
        term = termAdapter.getLoanTerm();
    }

    private void initData() {
        showView();
        setRecyclerView();
        if (UP_AMOUNT.equals(dialogType)) {
            tvDialogContent.setText(	ActivityUtils.getTopActivity().getResources().getString(R.string.a31));
        } else {
            tvDialogCancel.setVisibility(View.GONE);
            tvDialogContent.setText(	ActivityUtils.getTopActivity().getResources().getString(R.string.a32));
        }
        amount = Integer.parseInt(product.maxAmount);
//        term = product.maxLoanTerms;
        maxAmount = Integer.parseInt(product.maxAmount);
        productCode = product.productCode;
        scheduleCalc();
    }

    private void initSeekBar() {
        Resources res = getResources();
        Drawable thumb = res.getDrawable(R.drawable.seekbar_btn);
        int h = PhUtils.dip2px(getActivity(), 16) * 2;
        int w = h;
        Bitmap bmpOrg = ((BitmapDrawable) thumb).getBitmap();
        Bitmap bmpScaled = Bitmap.createScaledBitmap(bmpOrg, w, h, true);
        Drawable newThumb = new BitmapDrawable(res, bmpScaled);
        newThumb.setBounds(0, 0, newThumb.getIntrinsicWidth(), newThumb.getIntrinsicHeight());
        Drawable newThumb2 = new BitmapDrawable(res, bmpScaled);
        newThumb2.setBounds(0, 0, newThumb2.getIntrinsicWidth(), newThumb2.getIntrinsicHeight());
        amountSeekBar.setThumb(newThumb);
    }

    private void showView() {
        tvLoanAmountMin.setText(PhUtils.setMoney(product.minAmount));
        tvLoanAmountMax.setText(PhUtils.setMoney(product.getMaxViewAmount()));
        amountSeekBar.setMax((Integer.parseInt(product.getMaxViewAmount()) - Integer.parseInt(product.minAmount)) / amountStep);
        amountSeekBar.setProgress((Integer.parseInt(product.maxAmount) - Integer.parseInt(product.minAmount)) / amountStep);
    }

    private void initListener() {
        amountStep = Integer.parseInt(product.getAmountStep());
        termStep = product.getLoanStep();
        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelListener.OnCancelClick();
                dismiss();
            }
        });

        tvDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkboxAgreement.isChecked()) {
                    tvAgreementHint.setVisibility(VISIBLE);
                    return;
                }
                if (amount > Integer.parseInt(product.maxAmount)) {
                    amountSeekBar.setProgress((Integer.parseInt(product.maxAmount) - Integer.parseInt(product.minAmount)) / amountStep);
                    amount = Integer.parseInt(product.maxAmount);
                }
                confirmListener.OnConfirmClick();
            }
        });
        amountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 更改贷款金额
                if (product != null) {
                    amount = Integer.parseInt(product.minAmount) + amountStep * progress;
                    tvLoanAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(amount)));
                    Rect bounds = amountSeekBar.getProgressDrawable().getBounds();
                    if (amount <= Integer.parseInt(product.maxAmount)) {
                        amountSeekBar.setSecondaryProgress(0);
                        amountSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar));
                        tvAmountMessage.setVisibility(View.INVISIBLE);
                    } else {
                        amountSeekBar.setSecondaryProgress((Integer.parseInt(product.maxAmount) - Integer.parseInt(product.minAmount)) / amountStep);
                        amountSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_secondary));
                        tvAmountMessage.setText(PhUtils.format(getString(R.string.loan_amount_message), (PhUtils.setMoney(product.maxAmount))));
                        tvAmountMessage.setVisibility(VISIBLE);
                    }
                    amountSeekBar.getProgressDrawable().setBounds(bounds);
                    if (term != null && term != 0) {
                        scheduleCalc();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tvAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAgreement();
            }
        });
        checkboxAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tvAgreementHint.setVisibility(View.GONE);
                }
            }
        });
    }

    private void toAgreement() {
        SharedPreferencesPhUtil spU2Util = SharedPreferencesPhUtil.getInstance(getActivity());
        String accessToken = spU2Util.getString(PhConstants.PhUser.ACCESS_TOKEN);
        long applyId = spU2Util.getLong(PhConstants.PhUser.APPLY_ID, 0L);
        if (applyId == 0) {
            return;
        }
        NetworkPhRequest.getContractPreview(String.valueOf(term), String.valueOf(amount), accessToken, new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtils.showShort(getString(R.string.error_request_fail));
            }

            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                String code = basePhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    Intent intent = new Intent(getActivity(), WebPhActivity.class);
                    intent.putExtra(PhConstants.WEB_URL, basePhModel.getData().toString());
                    intent.putExtra(PhConstants.WEB_TITLE, getString(R.string.click_agreement_contract));
                    startActivity(intent);
                } else {
                    ToastUtils.showShort(getString(R.string.error_request_fail));
                }
            }
        });
    }


    private void scheduleCalc() {
        if (term == null) {
            return;
        }
        ScheduleCalcPhReqModel model = new ScheduleCalcPhReqModel();
//        model.loanAmt = String.valueOf(amount);
//        model.loanCode = product.loanCode;
//        model.loanDay = String.valueOf(term);
//        model.productCode = product.productCode;
        NetworkPhRequest.scheduleCalc(model, new PhSubscriber<ScheduleCalcPhModel>() {
            @Override
            public void onNext(ScheduleCalcPhModel calcPhModel) {
                super.onNext(calcPhModel);
                String code = calcPhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
//                    ScheduleCalcPhModel.ScheduleCalc calc = calcPhModel.getData();
//                    List<ScheduleCalcPhModel.ScheduleCalc.Schedule> schedules = calc.schedules;
//                    tvLoanAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(calcPhModel.getData().actualAmount)));
//                    if (schedules != null && schedules.size() != 0) {
//                        if (schedules.size() > 1) {
//                            ScheduleCalcPhModel.ScheduleCalc.Schedule schedule1 = schedules.get(0);
//                            ScheduleCalcPhModel.ScheduleCalc.Schedule schedule2 = schedules.get(1);
//                            llStaging.setVisibility(VISIBLE);
//                            amountLayout.setVisibility(View.GONE);
//                            tvApplyDate.setText(calc.applyDate);
//                            tvRepayLoanAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(calc.actualAmount)));
//                            tvFirstPeriodAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(schedule1.totalAmt)));
//                            tvFirstPeriodDate.setText(schedule1.loanPmtDueDate);
//                            tvSecondPeriodAmount.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(schedule2.totalAmt)));
//                            tvSecondPeriodDate.setText(schedule2.loanPmtDueDate);
//                        } else {
//                            llStaging.setVisibility(View.GONE);
//                            amountLayout.setVisibility(VISIBLE);
//                            tvLoanDate.setText(calcPhModel.getData().loanPmtDueDate);
//                            tvLoanPayment.setText(String.format(getString(R.string.loan_how_much), PhUtils.setMoney(calcPhModel.getData().totalAmt)));
//                        }
//                    }


                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtils.showShort(getString(R.string.error_request_fail));
            }
        });
    }

    public void setData(ProductPhModel.Product product, String dialogType) {
        this.product = product;
        this.dialogType = dialogType;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getTerm() {
        return term;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public String getProductCode() {
        return productCode;
    }

    //定义一个接口
    public interface OnConfirmClick {
        void OnConfirmClick();

    }

    public void setConfirmListener(OnConfirmClick confirmListener) {
        this.confirmListener = confirmListener;
    }

    //定义一个接口
    public interface OnCancelClick {
        void OnCancelClick();

    }

    public void setCancelListener(OnCancelClick cancelListener) {
        this.cancelListener = cancelListener;
    }
}
