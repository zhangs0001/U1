package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.adapter.SurveyAdapter;
import com.u1.gocashm.model.response.SurveyModel;
import com.u1.gocashm.model.response.UserSurveyModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.TokenUtils;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;


public class SurveyDialog extends AbsBaseCircleDialog {


    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.survey_list_view)
    RecyclerView surveyListView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String step;
    private SurveyAdapter adapter;
    private String surveyContent;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_user_survey, container);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        surveyListView.setLayoutManager(layoutManager);
        NetworkPhRequest.getUserSurveyList(TokenUtils.getToken(getActivity()), step, new PhSubscriber<UserSurveyModel>() {
            private void hidenProgressBar() {
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hidenProgressBar();
            }

            @Override
            public void onNext(UserSurveyModel userSurveyModel) {
                super.onNext(userSurveyModel);
                hidenProgressBar();
                String code = userSurveyModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    UserSurveyModel.UserSurvey data = userSurveyModel.getData();
                    tvDialogTitle.setText(data.getTitle());
                    setListData(data.getItems());
                } else {
                    ToastUtils.showShort(userSurveyModel.getMsg());
                }
            }
        });
    }

    private void setListData(List<String> items) {
        List<SurveyModel> list = new ArrayList<>();
        for (String content : items) {
            SurveyModel model = new SurveyModel();
            model.setContent(content);
            list.add(model);
        }
        if (adapter == null) {
            adapter = new SurveyAdapter(list);
            adapter.setOnItemClick(new SurveyAdapter.ItemClick() {
                @Override
                public void ItemClick(String content) {
                    surveyContent = content;
                }
            });
            surveyListView.setAdapter(adapter);
        } else {
            adapter.setNewData(list);
        }
    }

    @OnClick({R.id.tv_continue, R.id.tv_leave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_continue:
                dismiss();
                break;
            case R.id.tv_leave:
                leave();
                break;
        }
    }

    private void leave() {
        leaveListener.OnLeaveClick(surveyContent);
    }

    public void setStep(String step) {
        this.step = step;
    }

    /**
     * 定义一个接口对象listener
     */
    private OnLeaveClick leaveListener;

    /**
     * 定义一个接口对象
     */
    public interface OnLeaveClick {
        void OnLeaveClick(String content);
    }


    public void setLeaveListener(OnLeaveClick leaveListener) {
        this.leaveListener = leaveListener;
    }
}
