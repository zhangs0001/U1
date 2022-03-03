package com.u1.gocashm.activity.mine.coupon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.adapter.PromotionAdapter;
import com.u1.gocashm.model.response.RecommendModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;


public class PromotionActivity extends BasePhTitleBarActivity {

    @BindView(R.id.invite_friend_btn)
    TextView inviteFriendBtn;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;
    @BindView(R.id.promotion_list)
    RecyclerView promotionList;

    private PromotionAdapter adapter;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        ButterKnife.bind(this);
        activity = this;
        initViews();
        initData();
    }

    protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        promotionList.setLayoutManager(layoutManager);
        promotionList.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
    }

    protected void initData() {
        NetworkPhRequest.getMyRecommendList(TokenUtils.getToken(this), new PhSubscriber<RecommendModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(RecommendModel recommendModel) {
                super.onNext(recommendModel);
                closeLoadingDialog();
                String code = recommendModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    promotionList.setVisibility(View.VISIBLE);
                    inviteFriendBtn.setVisibility(View.GONE);
                    emptyLayout.setVisibility(View.GONE);
                    List<RecommendModel.Recommend> body = recommendModel.getData();
                    if (body != null && body.size() != 0) {
                        if (adapter == null) {
                            adapter = new PromotionAdapter(R.layout.item_promotion_layout, body);
                            promotionList.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        promotionList.setVisibility(View.GONE);
                        inviteFriendBtn.setVisibility(View.VISIBLE);
                        emptyLayout.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                showToast(R.string.error_request_fail);
            }
        });
    }

    @Override
    public void initTitleBar() {
        setTitleBack(R.string.mine_promotion);
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    @OnClick(R.id.invite_friend_btn)
    public void onViewClicked() {
        startActivity(new Intent(activity, InviteFriendsActivity.class));
    }

    @Override
    protected boolean uploadRecordsEnable() {
        return true;
    }

    @Override
    protected String getRecordEnterKey() {
        return "P18_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P18_Leave";
    }
}
