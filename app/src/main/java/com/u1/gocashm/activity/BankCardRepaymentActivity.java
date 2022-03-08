package com.u1.gocashm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.u1.gocashm.R;
import com.u1.gocashm.adapter.BankCardListAdapter;
import com.u1.gocashm.model.request.AddBankCardBody;
import com.u1.gocashm.model.request.AddBankCardModel;
import com.u1.gocashm.model.request.BankCardListModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.TokenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

public class BankCardRepaymentActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.et_CardNumber)
    EditText etCardNumber;
    @BindView(R.id.et_CardHolderName)
    EditText etCardHolderName;
    @BindView(R.id.et_Month)
    EditText etMonth;
    @BindView(R.id.et_Year)
    EditText etYear;
    @BindView(R.id.et_CVV)
    EditText etCVV;
    @BindView(R.id.bt_PAY)
    Button btPAY;
    @BindView(R.id.lin_InputBank)
    LinearLayout linInputBank;
    @BindView(R.id.recy_Bank)
    RecyclerView recyBank;
    @BindView(R.id.tv_addcard)
    TextView tvAddcard;
    @BindView(R.id.scroll_BankList)
    NestedScrollView scrollBankList;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    private String amount;
    private List<BankCardListModel.bankcardlist> data = new ArrayList<>();
    private BankCardListAdapter bankCardListAdapter;

    private int iconic;//1 关闭   2 添加银行卡

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankcardrepayment);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        Intent intent = getIntent();
        amount = intent.getStringExtra("amount");
        Log.e("zhangs", "initView: " + amount);
        btPAY.setText(getString(R.string.a289) + amount + getString(R.string.dialog_tv29));
        recyBank.setLayoutManager(new LinearLayoutManager(BankCardRepaymentActivity.this));
        bankCardListAdapter = new BankCardListAdapter(R.layout.banklist_item);
        bankCardListAdapter.setOnItemChildClickListener(this);
        //添加
        btPAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardnumber = etCardNumber.getText().toString();
                String cardholdername = etCardHolderName.getText().toString();
                String month = etMonth.getText().toString();
                String year = etYear.getText().toString();
                String cvv = etCVV.getText().toString();
                if (!cardnumber.isEmpty() && !cardholdername.isEmpty() && !month.isEmpty() && !year.isEmpty() && !cvv.isEmpty()) {
                    AddBankCardBody addBankCardBody = new AddBankCardBody();
                    addBankCardBody.setToken(TokenUtils.getToken(BankCardRepaymentActivity.this));
                    addBankCardBody.setCard_number(cardnumber);
                    addBankCardBody.setExpiry_year(year);
                    addBankCardBody.setExpiry_month(month);
                    addBankCardBody.setCvv(cvv);
                    addBankCardBody.setHolder_name(cardholdername);
                    Log.e("zhangs", "onClick: " + addBankCardBody.toString());
                    NetworkPhRequest.addBankCard(addBankCardBody, new PhSubscriber<AddBankCardModel>() {
                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        @Override
                        public void onNext(AddBankCardModel addBankCardModel) {
                            super.onNext(addBankCardModel);
                            if (!addBankCardModel.getCode().equals(CODE_SUCCESS)) {
                                ToastUtils.showLong(addBankCardModel.getMsg());
                                return;
                            }
                            Log.e("zhangs", "onNext: 保存成功");
                            initRepayMent(addBankCardModel.getData().getId());
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            ToastUtils.showLong(R.string.error_request_fail);
                        }
                    });
                } else {
                    Toast.makeText(BankCardRepaymentActivity.this, getString(R.string.a290), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iconic == 2) {
                    iconic = 1;
                    btPAY.setText(R.string.addbankcard);
                    linInputBank.setVisibility(View.GONE);
                    scrollBankList.setVisibility(View.VISIBLE);
                } else if (iconic == 3) {
                    finish();
                }
            }
        });
        tvAddcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconic = 2;
                btPAY.setText(getString(R.string.a289) + amount + getString(R.string.dialog_tv29));
                linInputBank.setVisibility(View.VISIBLE);
                scrollBankList.setVisibility(View.GONE);
            }
        });
    }

    private void initData() {
        NetworkPhRequest.getBankCardList(TokenUtils.getToken(BankCardRepaymentActivity.this), new PhSubscriber<BankCardListModel>() {
            @Override
            public void onStart() {
                super.onStart();
//                showLoadingDialog();
            }

            @Override
            public void onNext(BankCardListModel bankCardListModel) {
                super.onNext(bankCardListModel);
                String code = bankCardListModel.getCode();
                if (code.equals(CODE_SUCCESS)) {
                    List<BankCardListModel.bankcardlist> data = bankCardListModel.getData();
                    if (data.size() != 0) {
                        linInputBank.setVisibility(View.GONE);
                        scrollBankList.setVisibility(View.VISIBLE);
                        bankCardListAdapter.replaceData(data);
                        recyBank.setAdapter(bankCardListAdapter);
                    } else {
                        linInputBank.setVisibility(View.VISIBLE);
                        scrollBankList.setVisibility(View.GONE);
                    }
                    iconic = 3;
                } else {
                    ToastUtils.showLong(bankCardListModel.getMsg());
                    return;
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
//                closeLoadingDialog();
                ToastUtils.showLong(R.string.error_request_fail);
            }
        });
    }

    private void initRepayMent(int id) {
        NetworkPhRequest.useBankCardRepayMent(TokenUtils.getToken(BankCardRepaymentActivity.this), id, amount, new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
//                showLoadingDialog();
            }

            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
//                closeLoadingDialog();
                Log.e("zhangs", "onNext: 还款成功");
                finish();
                if (!basePhModel.getCode().equals(CODE_SUCCESS)) {
                    ToastUtils.showLong(basePhModel.getMsg());
                    return;
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
//                closeLoadingDialog();
                ToastUtils.showLong(R.string.error_request_fail);
            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<BankCardListModel.bankcardlist> data = adapter.getData();
        int id = data.get(position).getId();
        if (view.getId() == R.id.bt_delete) {//删除
            delete(id);
        } else if (view.getId() == R.id.bt_repayment) {
            initRepayMent(id);
        }
    }

    private void delete(int id) {
        //删除
        NetworkPhRequest.deleteBankCard(TokenUtils.getToken(BankCardRepaymentActivity.this), id, new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                if (!basePhModel.getCode().equals(CODE_SUCCESS)) {
                    ToastUtils.showLong(basePhModel.getMsg());
                    return;
                }
                initData();
                if (iconic == 2) {
                    btPAY.setText(getString(R.string.a289) + amount + getString(R.string.dialog_tv29));
                    iconic = 3;
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                ToastUtils.showLong(R.string.error_request_fail);
            }
        });
    }

//    @Override
//    public void initTitleBar() {
//        setTitleBack(getString(R.string.a283));
//        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
//    }
}