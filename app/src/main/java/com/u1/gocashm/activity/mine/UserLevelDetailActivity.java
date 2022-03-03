package com.u1.gocashm.activity.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.activity.base.BasePhTitleBarActivity;
import com.u1.gocashm.util.StatusPhBarUtil;
import com.u1.gocashm.util.constant.PhConstants;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hewei
 * @date 2019/12/23 0023 下午 3:14
 */
public class UserLevelDetailActivity extends BasePhTitleBarActivity implements View.OnClickListener {

    private static final int DIAMOND = 4;
    private static final int GOLD = 3;
    private static final int SILVER = 2;
    private static final int BRONZE = 1;

    @BindView(R.id.iv_bronze_level)
    ImageView ivBronzeLevel;
    @BindView(R.id.iv_silver_level)
    ImageView ivSilverLevel;
    @BindView(R.id.iv_gold_level)
    ImageView ivGoldLevel;
    @BindView(R.id.iv_diamond_level)
    ImageView ivDiamondLevel;
    @BindView(R.id.ll_user_level_info)
    LinearLayout llUserLevelInfo;
    @BindView(R.id.ll_bronze)
    LinearLayout llBronze;
    @BindView(R.id.ll_silver)
    LinearLayout llSilver;
    @BindView(R.id.ll_gold)
    LinearLayout llGold;
    @BindView(R.id.ll_diamond)
    LinearLayout llDiamond;
    @BindView(R.id.tv_bronze_name)
    TextView tvBronzeName;
    @BindView(R.id.tv_bronze_amount)
    TextView tvBronzeAmount;
    @BindView(R.id.tv_silver_name)
    TextView tvSilverName;
    @BindView(R.id.tv_silver_amount)
    TextView tvSilverAmount;
    @BindView(R.id.tv_gold_name)
    TextView tvGoldName;
    @BindView(R.id.tv_gold_amount)
    TextView tvGoldAmount;
    @BindView(R.id.tv_diamond_name)
    TextView tvDiamondName;
    @BindView(R.id.tv_diamond_amount)
    TextView tvDiamondAmount;

    private int userLevel;
    private Activity activity;

    private LinearLayout tabBronze;
    private LinearLayout tabSilver;
    private LinearLayout tabGold;
    private LinearLayout tabDiamond;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_level_detail);
        ButterKnife.bind(this);
        activity = this;
        initData();
        initView();
    }

    private void initData() {
        userLevel = getIntent().getIntExtra(PhConstants.USER_LEVEL,1);
    }

    private void initView() {
        tabBronze=findViewById(R.id.tab_bronze);
        tabBronze.setOnClickListener(this);
        tabSilver=findViewById(R.id.tab_silver);
        tabSilver.setOnClickListener(this);
        tabGold=findViewById(R.id.tab_gold);
        tabGold.setOnClickListener(this);
        tabDiamond=findViewById(R.id.tab_diamond);
        tabDiamond.setOnClickListener(this);

        updateView();
    }

    private void updateView() {
        switch (userLevel) {
            case BRONZE:
                ivBronzeLevel.setImageResource(R.drawable.bronze_big_img);
                llUserLevelInfo.setBackgroundResource(R.drawable.bronze_bg);
                tvBronzeName.setText("Bronze-level");
                tvBronzeAmount.setText(getString(R.string.user_level_amount, 4000));
                tvBronzeName.setVisibility(View.VISIBLE);
                tvBronzeAmount.setVisibility(View.VISIBLE);
                break;
            case SILVER:
                ivSilverLevel.setImageResource(R.drawable.silver_big_img);
                llUserLevelInfo.setBackgroundResource(R.drawable.silver_bg);
                tvSilverName.setText("Silver-level");
                tvSilverAmount.setText(getString(R.string.user_level_amount, 6000));
                tvSilverName.setVisibility(View.VISIBLE);
                tvSilverAmount.setVisibility(View.VISIBLE);
                break;
            case GOLD:
                ivGoldLevel.setImageResource(R.drawable.gold_big_img);
                llUserLevelInfo.setBackgroundResource(R.drawable.gold_bg);
                tvGoldName.setText("Gold-level");
                tvGoldAmount.setText(getString(R.string.user_level_amount, 12000));
                tvGoldName.setVisibility(View.VISIBLE);
                tvGoldAmount.setVisibility(View.VISIBLE);
                break;
            case DIAMOND:
                ivDiamondLevel.setImageResource(R.drawable.diamond_big_img);
                llUserLevelInfo.setBackgroundResource(R.drawable.diamond_bg);
                tvDiamondName.setText("Diamond-level");
                tvDiamondAmount.setText(getString(R.string.user_level_amount, 12000)+"+");
                tvDiamondName.setVisibility(View.VISIBLE);
                tvDiamondAmount.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
    private void reinit() {
        ivBronzeLevel.setImageResource(R.drawable.bronze_img);
        tvBronzeName.setVisibility(View.GONE);
        tvBronzeAmount.setVisibility(View.GONE);

        ivSilverLevel.setImageResource(R.drawable.silver_img);
        tvSilverName.setVisibility(View.GONE);
        tvSilverAmount.setVisibility(View.GONE);

        ivGoldLevel.setImageResource(R.drawable.gold_img);
        tvGoldName.setVisibility(View.GONE);
        tvGoldAmount.setVisibility(View.GONE);

        ivDiamondLevel.setImageResource(R.drawable.diamond_img);
        tvDiamondName.setVisibility(View.GONE);
        tvDiamondAmount.setVisibility(View.GONE);
    }

    @Override
    public void initTitleBar() {
        setTitleBack(getString(R.string.a20));
        StatusPhBarUtil.setColor(this, getResources().getColor(R.color.blue));
    }

    private void setLevel(int level) {
        userLevel = level;
        reinit();
        updateView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_bronze:
                setLevel(1);
                break;
            case R.id.tab_silver:
                setLevel(2);
                break;
            case R.id.tab_gold:
                setLevel(3);
                break;
            case R.id.tab_diamond:
                setLevel(4);
                break;
        }
    }

    @Override
    protected boolean uploadRecordsEnable() {
        return true;
    }

    @Override
    protected String getRecordEnterKey() {
        return "P20_Enter";
    }

    @Override
    protected String getRecordLeaveKey() {
        return "P20_Leave";
    }

    @Override
    protected String getRecordBackKey() {
        return "P20_C_Back";
    }
}
