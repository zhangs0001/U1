package com.u1.gocashm.activity.mine;

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
import com.u1.gocashm.adapter.OrderListPhAdapter;
import com.u1.gocashm.model.response.OrderListModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * Created by jishudiannao on 2018/9/10.
 */

public class OrderListPhActivity extends BasePhTitleBarActivity {

    @BindView(R.id.rv_orderlist)
    RecyclerView recyclerView;
    private OrderListPhAdapter adapter;
    private Disposable disposable;

    private Activity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        ButterKnife.bind(this);
        activity = this;
        initView();
        initData();
    }

    private void initData() {
        disposable = RxBus.getDefault().toDefaultFlowable(TagEvent.class, new Consumer<TagEvent>() {
            @Override
            public void accept(TagEvent tagEvent) throws Exception {
                if (tagEvent.getTag().equals(EventTagPh.RE_APPLY)) {
                    finish();
                }
            }
        });
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getContractList();
    }

    private void getContractList() {
        NetworkPhRequest.getOrderList(TokenUtils.getToken(activity), "all", new PhSubscriber<OrderListModel>() {
            @Override
            public void onNext(OrderListModel orderListModel) {
                super.onNext(orderListModel);
                if (orderListModel.getCode().equals(CODE_SUCCESS)) {
                    OrderListModel.OrderList data = orderListModel.getData();
                    if (adapter == null) {
                        adapter = new OrderListPhAdapter(data.getData());
                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(OrderListPhActivity.this, OrderDetailPhActivity.class);
                                intent.putExtra(PhConstants.CONTRACTNO, String.valueOf(data.getData().get(position).getId()));
                                if (position == 0) {
                                    intent.putExtra(PhConstants.CONTRACTSHOWBTN, true);
                                }
                                startActivity(intent);
                                BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P12_C_CheckDetail");
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.setNewData(data.getData());
                    }
                } else {
                    showToast(orderListModel.getMsg());
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                closeLoadingDialog();
            }
        });
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.order_list);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    @Override
    protected void onDestroy() {
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    protected boolean uploadRecordsEnable() {
        return true;
    }

    @Override
    protected String getRecordEnterKey() {
        return "P12_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P12_Leave";
    }
    @Override
    protected String getRecordBackKey() {
        return "P12_C_Back";
    }
}
