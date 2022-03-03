package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

/**
 * @author hewei
 * @date 2019/12/26 0026 下午 4:12
 */
public class UpdateUserLevelDialog extends AbsBaseCircleDialog {
    public static boolean isShowUpgradeLevelDialog = true;

    private static final int DIAMOND = 4;
    private static final int GOLD = 3;
    private static final int SILVER = 2;
    private static final int BRONZE = 1;

    private ImageView ivLevelIcon;
    private TextView tvLevelContent;
    private TextView tvConfirm;

    private int level;

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_update_userlever, container);
        initView(view);
        initListener();
        initData();
        return view;
    }

    public void setData(int level) {
        this.level = level;
    }

    private void initView(View view) {
        ivLevelIcon = view.findViewById(R.id.iv_level_icon);
        tvLevelContent = view.findViewById(R.id.tv_level_content);
        tvConfirm = view.findViewById(R.id.tv_dialog_confirm);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setCanceledBack(false);
    }

    private void initListener() {
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmListener.OnConfirmClick();
                dismiss();
            }
        });
    }

    private void initData() {
        String str = "";
        tvLevelContent.setTextColor(Color.WHITE);
        switch (level) {
            case BRONZE:
                ivLevelIcon.setImageResource(R.drawable.bronze_hint_img);
                tvLevelContent.setText(R.string.user_level_bronze_hint);
                tvConfirm.setBackgroundResource(R.drawable.btn_silver_bg);
                break;
            case SILVER:
                ivLevelIcon.setImageResource(R.drawable.silver_hint_img);
                str = "Congratulations, your loyalty is rewarded. You reached the silver level. The discount you can get is <b><font color='#A1A1A1'>3%</font></b> off the service fee.Keep on partenering with us to reach a higher level and get more discount.";
                tvConfirm.setBackgroundResource(R.drawable.btn_silver_bg);
                break;
            case GOLD:
                ivLevelIcon.setImageResource(R.drawable.gold_hint_img);
                str = "Congratulations, your loyalty is rewarded. You reached the gold level. The discount you can get is <b><font color='#B59900'>6%</font></b> off the service fee.Keep on partenering with us to reach a higher level and get more discount.";
                tvConfirm.setBackgroundResource(R.drawable.btn_gold_bg);
                break;
            case DIAMOND:
                ivLevelIcon.setImageResource(R.drawable.diamond_hint_img);
                str = "Congratulations, your loyalty is rewarded. You reached the diamond level. The discount you can get is <b><font color='#7699C8'>8%-10%</font></b> off the service fee.Keep on partenering with us to reach a higher level and get more discount.";
                tvConfirm.setBackgroundResource(R.drawable.btn_diamond_bg);
                break;
            default:
                break;
        }
        if (level != BRONZE) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                tvLevelContent.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY));
            } else {
                tvLevelContent.setText(Html.fromHtml(str));
            }
        }

    }

    /**
     * 定义一个接口对象listener
     */
    private OnConfirmClick confirmListener;

    /**
     * 定义一个接口对象
     */
    public interface OnConfirmClick {
        void OnConfirmClick();
    }


    public void setConfirmListener(OnConfirmClick confirmListener) {
        this.confirmListener = confirmListener;
    }

    public static int convertLevel(String levelName){
        int level=-1;
        if (TextUtils.isEmpty(levelName)){
            return level;
        }
        if ("BRONZE".equals(levelName)){
            level=1;
        }else if ("SILVER".equals(levelName)){
            level=2;
        }else if ("GOLD".equals(levelName)){
            level=3;
        }else if ("DIAMOND".equals(levelName)){
            level=4;
        }
        return level;
    }

}
