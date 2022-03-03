package com.u1.gocashm.activity.mine;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.PhApplication;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.adapter.NoticeAdapter;
import com.u1.gocashm.model.response.NoticeListModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.NoticeMananger;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.TokenUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

public class MessageListActivity extends BasePhTitleBarActivity {

    @BindView(R.id.message_list)
    RecyclerView messageList;

    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);
        activity = this;
        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        messageList.setLayoutManager(layoutManager);
    }

    private void initData() {
        NetworkPhRequest.getMessageList(1,1000,TokenUtils.getToken(activity),new PhSubscriber<NoticeListModel>() {
            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onNext(NoticeListModel noticeListModel) {
                super.onNext(noticeListModel);
                closeLoadingDialog();
                String code = noticeListModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    NoticeListModel data = noticeListModel.getData();

                    if (data.getUnread() > 0 && !data.getItems().isEmpty()) {
                        ArrayList<NoticeListModel.Notice> items = new ArrayList<>();
                        for (NoticeListModel.Notice notice : data.getItems()) {
                            if (!notice.isUrgent() && !notice.isRead()) {
                                items.add(notice);
                                notice.setIs_read(1);
                            }
                        }

                        NoticeMananger.resetNoticeReadStatus(PhApplication.getContext(),items);
                        NoticeMananger.notifyMessageTagEvent(data.getUnread() - items.size());
                    }

                    NoticeAdapter adapter = new NoticeAdapter(data.getItems());
                    messageList.setAdapter(adapter);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                ToastUtils.showShort(e.getMessage());
            }
        });
    }


    @Override
    public void initTitleBar() {
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
        setTitleBack(getString(R.string.a17));
    }

    @Override
    protected boolean uploadRecordsEnable() {
        return true;
    }

    @Override
    protected String getRecordEnterKey() {
        return "P19_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P19_Leave";
    }
    @Override
    protected String getRecordBackKey() {
        return "P19_C_Back";
    }
}
