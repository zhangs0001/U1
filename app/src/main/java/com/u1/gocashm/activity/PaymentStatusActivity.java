package com.u1.gocashm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.adapter.PaymentStatusAdapter;
import com.u1.gocashm.model.PaymentStatusBean;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.PaymentStatusModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;

import org.apache.http.params.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

public class PaymentStatusActivity extends BasePhTitleBarActivity implements BaseQuickAdapter.OnItemChildClickListener {

    public static void startAct(Activity activity){
        Intent intent = new Intent(activity,PaymentStatusActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.mRcy)
    RecyclerView mRcy;

    private PaymentStatusAdapter paymentStatusAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentstatus);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initData() {
        NetworkPhRequest.paymentList(TokenUtils.getToken(PaymentStatusActivity.this), new PhSubscriber<PaymentStatusModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(PaymentStatusModel paymentStatusModel) {
                super.onNext(paymentStatusModel);
                if (paymentStatusModel.getCode().equals(CODE_SUCCESS)) {
                    List<PaymentStatusModel.PaymentStatus> data = paymentStatusModel.getData();
                    paymentStatusAdapter.replaceData(data);
                }
                closeLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
            }
        });
    }

    private void initView() {
        paymentStatusAdapter = new PaymentStatusAdapter(R.layout.item_payment_status);
        mRcy.setLayoutManager(new LinearLayoutManager(this));
        mRcy.setAdapter(paymentStatusAdapter);
        paymentStatusAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.tv_unpaid) {
            List<PaymentStatusModel.PaymentStatus> data = adapter.getData();
            String transaction_no = data.get(position).getTransaction_no();
            repayQuery(transaction_no);
        }
    }

    //还款查询
    private void repayQuery(String transaction_no) {
//        HttpParams params = new HttpParams();
//        params.put("token", PesContext.getInstance().getAccessToken());
//        params.put("transaction_no", transaction_no);
//        AppApi.getInstance().repayQuery(params, new ResultCallback() {
//            @Override
//            public void onSuccess(Object response, String respCode) {
//                if (response instanceof ApiResult) {
//                    ApiResult result = (ApiResult) response;
//                    if (AppConst.Response.SUCCESS.equals(result.code)) {
//                        initData();
//                    }else {
//                        ToastUtil.showShortToast(result.msg);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Throwable e, String errorCode) {
//
//            }
//        });

        NetworkPhRequest.repayQuery(TokenUtils.getToken(PaymentStatusActivity.this), transaction_no, new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                if (basePhModel.getCode().equals(CODE_SUCCESS)) {
                    initData();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });


    }

    @Override
    public void initTitleBar() {
        setTitleBack(getString(R.string.repayment));
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

}
