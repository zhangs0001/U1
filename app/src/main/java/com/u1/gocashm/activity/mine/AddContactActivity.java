package com.u1.gocashm.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.adapter.ContactInfoAdapter;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.model.ContactInfo;
import com.u1.gocashm.view.dialog.AddContactDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

public class AddContactActivity extends BasePhTitleBarActivity {

    @BindView(R.id.emergency_contact_list)
    RecyclerView emergencyContactList;
    @BindView(R.id.emergency_general_list)
    RecyclerView emergencyGeneralList;
    @BindView(R.id.tv_add_submit)
    TextView tvAddSubmit;

    private List<ContactInfo> emergencyList;
    private List<ContactInfo> generaList;
    private AddContactDialog dialog;
    private ContactInfoAdapter emergencyAdapter;
    private ContactInfoAdapter generaAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        emergencyContactList.setLayoutManager(layoutManager);
        emergencyGeneralList.setLayoutManager(layoutManager2);
    }

    private void initData() {
        emergencyList = new ArrayList<>();
        generaList = new ArrayList<>();
    }

    @Override
    public void initTitleBar() {
        setTitleBack(getString(R.string.a13));
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    @OnClick({R.id.iv_add_emergency, R.id.iv_add_general, R.id.tv_add_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_emergency:
                showAddDialog(true);
                break;
            case R.id.iv_add_general:
                showAddDialog(false);
                break;
            case R.id.tv_add_submit:
                confirm();
                break;
        }
    }

    private void showAddDialog(boolean isEmergency) {
        if (isEmergency) {
            dialog = new AddContactDialog();
            dialog.setEmergency(true);
            dialog.setConfirmListener(new AddContactDialog.OnConfirmClick() {
                @Override
                public void OnConfirmClick(ContactInfo contactInfo) {
                    emergencyList.add(contactInfo);
                    emergencyAdapter = new ContactInfoAdapter(R.layout.item_add_contact, emergencyList);
                    emergencyContactList.setAdapter(emergencyAdapter);
                    if (emergencyList.size() != 0) {
                        emergencyContactList.setVisibility(View.VISIBLE);
                        if (generaList.size() != 0) {
                            tvAddSubmit.setBackgroundResource(R.drawable.shape_blue);
                            tvAddSubmit.setEnabled(true);
                            tvAddSubmit.setTextColor(getResources().getColor(R.color.white));
                        }
                    }
                }
            });
            dialog.show(getSupportFragmentManager(), "1");
        } else {
            dialog = new AddContactDialog();
            dialog.setEmergency(false);
            dialog.setConfirmListener(new AddContactDialog.OnConfirmClick() {
                @Override
                public void OnConfirmClick(ContactInfo contactInfo) {
                    generaList.add(contactInfo);
                    generaAdapter = new ContactInfoAdapter(R.layout.item_add_contact, generaList);
                    emergencyGeneralList.setAdapter(generaAdapter);
                    if (generaList.size() != 0 ) {
                        emergencyGeneralList.setVisibility(View.VISIBLE);
                        if (emergencyList.size() != 0){
                            tvAddSubmit.setBackgroundResource(R.drawable.shape_blue);
                            tvAddSubmit.setEnabled(true);
                            tvAddSubmit.setTextColor(getResources().getColor(R.color.white));
                        }
                    }
                }
            });
            dialog.show(getSupportFragmentManager(), "2");
        }
    }


    private void confirm() {
        NetworkPhRequest.addContactSubmit(new PhSubscriber<BasePhModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                closeLoadingDialog();
                String code = basePhModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    finish();
                } else  {
                    showToast(basePhModel.getMsg());
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
}
