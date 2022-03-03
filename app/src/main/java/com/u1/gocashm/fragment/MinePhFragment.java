package com.u1.gocashm.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.u1.gocashm.R;
import com.u1.gocashm.activity.LuckLotteryActivity;
import com.u1.gocashm.activity.WebPhActivity;
import com.u1.gocashm.activity.mine.AboutPhActivity;
import com.u1.gocashm.activity.mine.AddContactActivity;
import com.u1.gocashm.activity.mine.LoginPhActivity;
import com.u1.gocashm.activity.mine.MessageListActivity;
import com.u1.gocashm.activity.mine.OrderListPhActivity;
import com.u1.gocashm.activity.mine.UserInfoActivity;
import com.u1.gocashm.activity.mine.UserLevelDetailActivity;
import com.u1.gocashm.activity.mine.coupon.FreeInterestActivity;
import com.u1.gocashm.activity.mine.coupon.InviteFriendsActivity;
import com.u1.gocashm.activity.mine.coupon.PromotionActivity;
import com.u1.gocashm.fragment.base.BasePhFragment;
import com.u1.gocashm.model.request.BehaviorRecordReqModel;
import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.InitUserModel;
import com.u1.gocashm.model.response.User;
import com.u1.gocashm.model.response.UserInfoModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.AntiShakeThUtils;
import com.u1.gocashm.util.BehaviorPhUtils;
import com.u1.gocashm.util.LiveDataBus;
import com.u1.gocashm.util.NoticeMananger;
import com.u1.gocashm.util.PhUtils;
import com.u1.gocashm.util.SharedPreferencesPhUtil;
import com.u1.gocashm.util.TokenUtils;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.constant.PhConstants;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.view.dialog.UpdateUserLevelDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;


public class MinePhFragment extends BasePhFragment {

    private static final int DIAMOND = 4;
    private static final int GOLD = 3;
    private static final int SILVER = 2;
    private static final int BRONZE = 1;

    @BindView(R.id.tv_mine_login)
    TextView tvPhLogin;
    @BindView(R.id.tv_mine_name)
    TextView tvPhName;
    @BindView(R.id.tv_mine_phone)
    TextView tvPhPhone;
    @BindView(R.id.tv_mine_version)
    TextView tvPhVersion;
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.id_unread_tv)
    TextView id_unread_tv;
    @BindView(R.id.ll_user_info)
    LinearLayout llUserInfo;
    @BindView(R.id.ll_login_info)
    LinearLayout llLoginInfo;

    Unbinder unbinder;
    private View view;
    private ViewGroup parent;
    private boolean isLogin;
    private int userLevel;
    private SharedPreferencesPhUtil spUtils;
    private BehaviorRecordReqModel mBehaviorRecord;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, null);
            unbinder = ButterKnife.bind(this, view);
            initView();
        } else {
            parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        LiveDataBus.get().with("Privacy_Status", Integer.class).observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer index) {
//                Log.e("zhangs", "onChanged: " + index);
//                if (index == 10) {
//                    String privacyText = getString(R.string.policy_privacy);
//                    Intent intent = new Intent(getContext(), WebPhActivity.class);
//                    intent.putExtra(PhConstants.WEB_TITLE, privacyText);
//                    intent.putExtra(PhConstants.WEB_URL, PhConstants.URL_POLICY);
//                    startActivity(intent);
//                } else if (index == 1) {
//
//                }
//            }
//        });
        loginView();
    }

    private void loginView() {
        isLogin = TokenUtils.TokenCheck(getActivity());
        tvPhLogin.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        llLogout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        llUserInfo.setVisibility(isLogin ? View.VISIBLE : View.GONE);

        //todo
        tvPhPhone.setText("+2 " + TokenUtils.getPhone(getActivity()));
        if (!isLogin) {
            llLoginInfo.setBackgroundResource(R.color.grey_2);
            ivUserIcon.setImageResource(R.drawable.ic_header);
        }
    }

    private void initView() {
        spUtils = SharedPreferencesPhUtil.getInstance(getActivity());
        tvPhVersion.setText("APP v " + PhUtils.getAppVersionName(getActivity()));
        NetworkPhRequest.getUserInfo(TokenUtils.getToken(getActivity()), new PhSubscriber<UserInfoModel>() {
            @Override
            public void onNext(UserInfoModel userInfoModel) {
                super.onNext(userInfoModel);
                String code = userInfoModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    UserInfoModel.UserInfo data = userInfoModel.getData();
                    User user = data.getUser();
                    if (user != null) {
                        tvPhName.setText(user.getFullname());
                    }
                }
            }
        });
        refreshUserLevel();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden && mBehaviorRecord != null) {
            BehaviorPhUtils.setDestroyModifyV2(mBehaviorRecord, "P10_Leave");
            BehaviorPhUtils.saveBehaviorReqModelV2(mBehaviorRecord);
        } else {
            mBehaviorRecord = BehaviorPhUtils.setEnterPageModifyV2("P10_Enter", getContext());
        }
        if (!hidden && isLogin) {
            refreshUserLevel();
            NoticeMananger.refreshNotices(getContext());
            //setUnreadToView(NoticeMananger.notices != null ? NoticeMananger.notices.getUnread() : 0);
        }
    }

    private void refreshUserLevel() {
        NetworkPhRequest.getUserLevel(TokenUtils.getToken(getActivity()), new PhSubscriber<InitUserModel>() {
            @Override
            public void onNext(InitUserModel userLevelModel) {
                super.onNext(userLevelModel);
                if (CODE_SUCCESS.equals(userLevelModel.getCode())) {
                    userLevel = convertLevel(userLevelModel.getData().getLevel());
                    updateUserLevelView();
                }
            }
        });

    }

    private int convertLevel(String levelName) {
        int level = -1;
        if (TextUtils.isEmpty(levelName)) {
            return level;
        }
        if ("BRONZE".equals(levelName)) {
            level = 1;
        } else if ("SILVER".equals(levelName)) {
            level = 2;
        } else if ("GOLD".equals(levelName)) {
            level = 3;
        } else if ("DIAMOND".equals(levelName)) {
            level = 4;
        }
        return level;
    }

    private void showUpLevelDialog() {
        UpdateUserLevelDialog levelDialog = new UpdateUserLevelDialog();
        levelDialog.setData(userLevel);
        levelDialog.setConfirmListener(() -> {
        });
        levelDialog.show(getFragmentManager(), "1");
    }

    private void updateUserLevelView() {
        switch (userLevel) {
            case 1:
                llLoginInfo.setBackgroundResource(R.drawable.bronze_bg);
                ivUserIcon.setImageResource(R.drawable.bronze_big_img);
                tvUserLevel.setText("(Bronze-level)");

                break;
            case 2:
                llLoginInfo.setBackgroundResource(R.drawable.silver_bg);
                ivUserIcon.setImageResource(R.drawable.silver_big_img);
                tvUserLevel.setText("(Silver-level)");

                break;
            case 3:
                llLoginInfo.setBackgroundResource(R.drawable.gold_bg);
                ivUserIcon.setImageResource(R.drawable.gold_big_img);
                tvUserLevel.setText("(Gold-level)");

                break;
            case 4:
                llLoginInfo.setBackgroundResource(R.drawable.diamond_bg);
                ivUserIcon.setImageResource(R.drawable.diamond_big_img);
                tvUserLevel.setText("(Diamond-level)");

                break;
            default:
                break;
        }
    }

    @OnClick({R.id.tv_mine_login, R.id.ll_mine_info, R.id.ll_mine_order, R.id.ll_mine_about, R.id.tv_user_level_img,
            R.id.ll_mine_feedback, R.id.bt_mine_logout, R.id.iv_mine_message, R.id.tv_user_level, R.id.tv_get_level_detail, R.id.ll_lucky_draw})
    public void onViewClicked(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_mine_login:
                startActivity(new Intent(getActivity(), LoginPhActivity.class));
                break;
            case R.id.ll_mine_info:
                if (TokenUtils.TokenCheck(getActivity()))
                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                else
                    startActivity(new Intent(getActivity(), LoginPhActivity.class));
                break;
            case R.id.ll_mine_order:
                BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P10_C_LoanHistory");
                if (TokenUtils.TokenCheck(getActivity()))
                    startActivity(new Intent(getActivity(), OrderListPhActivity.class));
                else
                    startActivity(new Intent(getActivity(), LoginPhActivity.class));
                break;
            case R.id.ll_mine_about:
                BehaviorPhUtils.setClickModifyV2(mBehaviorRecord, "P10_C_AboutUs");
                Intent intent = new Intent(getActivity(), AboutPhActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_mine_feedback:
                if (TokenUtils.TokenCheck(getActivity())) {
                    SharedPreferencesPhUtil preferencesUtil = SharedPreferencesPhUtil.getInstance(getActivity());
                    Intent intent1 = new Intent(getActivity(), WebPhActivity.class);
                    intent1.putExtra(PhConstants.WEB_TITLE, getActivity().getString(R.string.feed_back));
                    intent1.putExtra(PhConstants.WEB_URL, PhUtils.getFeedbackUrl(preferencesUtil.getString(PhConstants.PhUser.USER_NAME), preferencesUtil.getString(PhConstants.PhUser.USER_ID_CARD)));
                    startActivity(intent1);
                } else {
                    startActivity(new Intent(getActivity(), LoginPhActivity.class));
                }
                break;
            case R.id.bt_mine_logout:
                logoutPh();
                break;
            case R.id.iv_mine_message:
                toNoticeMessage();
                break;
            case R.id.tv_user_level:
            case R.id.tv_user_level_img:
                showUpLevelDialog();
                break;
            case R.id.tv_get_level_detail:
                toUserLevelDetail();
                break;
            case R.id.ll_lucky_draw:
                startActivity(new Intent(getActivity(), LuckLotteryActivity.class));
                break;
            default:
                break;
        }
    }

    private void toNoticeMessage() {
        startActivity(new Intent(getActivity(), MessageListActivity.class));
    }

    private void toUserLevelDetail() {
        Intent intent = new Intent(getActivity(), UserLevelDetailActivity.class);
        intent.putExtra(PhConstants.USER_LEVEL, userLevel);
        startActivity(intent);
    }

    private void logoutPh() {
        NetworkPhRequest.logout(TokenUtils.getToken(getActivity()), new PhSubscriber<BasePhModel>() {
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
                    SharedPreferencesPhUtil.getInstance(getActivity()).logout();
                    SharedPreferencesPhUtil.getInstance(getActivity()).clearApplyData();
                    UpdateUserLevelDialog.isShowUpgradeLevelDialog = true;
                    loginView();
                    RxBus.getDefault().post(new TagEvent(EventTagPh.LOGOUT));
                } else {
                    ToastUtils.showShort(basePhModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                closeLoadingDialog();
                ToastUtils.showShort(R.string.error_request_fail);
            }
        });
    }

    @OnClick({R.id.ll_mine_help, R.id.ll_mine_invite, R.id.ll_mine_offer, R.id.ll_mine_promotion, R.id.ll_add_contact})
    public void onViewClick(View view) {
        if (AntiShakeThUtils.isInvalidClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_mine_help:
                toHelp();
                break;
            case R.id.ll_mine_invite:
                toInviteFriend();
                break;
            case R.id.ll_mine_offer:
                toOffer();
                break;
            case R.id.ll_mine_promotion:
                toPromotion();
                break;
            case R.id.ll_add_contact:
                toAddContact();
                break;
        }
    }

    private void toHelp() {
        Intent intent = new Intent(getActivity(), WebPhActivity.class);
        intent.putExtra(PhConstants.WEB_URL, PhConstants.HELP_URL);
        intent.putExtra(PhConstants.WEB_TITLE, getString(R.string.mine_help));
        startActivity(intent);
    }

    private void toInviteFriend() {
        if (!TokenUtils.TokenCheck(getActivity())) {
            ToastUtils.showShort(R.string.error_has_not_login);
            return;
        }
        Intent intent = new Intent(getActivity(), InviteFriendsActivity.class);
        startActivity(intent);
    }

    private void toOffer() {
        if (!TokenUtils.TokenCheck(getActivity())) {
            ToastUtils.showShort(R.string.error_has_not_login);
            return;
        }
        Intent intent = new Intent(getActivity(), FreeInterestActivity.class);
        startActivity(intent);
    }

    private void toPromotion() {
        if (!TokenUtils.TokenCheck(getActivity())) {
            ToastUtils.showShort(R.string.error_has_not_login);
            return;
        }
        Intent intent = new Intent(getActivity(), PromotionActivity.class);
        startActivity(intent);
    }

    private void toAddContact() {
        if (!TokenUtils.TokenCheck(getActivity())) {
            ToastUtils.showShort(R.string.error_has_not_login);
            return;
        }
        Intent intent = new Intent(getActivity(), AddContactActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setUnreadToView(int unread) {
        if (id_unread_tv != null) {
            id_unread_tv.setText(String.valueOf(unread >= 99 ? 99 : unread));
            id_unread_tv.setVisibility(unread > 0 ? View.VISIBLE : View.GONE);
        }
    }
}
