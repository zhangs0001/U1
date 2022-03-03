package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.u1.gocashm.R;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

/**
 * @author hewei
 * @date 2019/5/8 0008 下午 2:52
 */
public class LotterDialog extends AbsBaseCircleDialog {
    private ImageView imageView;
    private TextView confirmBtn;
    private TextView textView;
    private TextView prizeTv;

    private boolean win;
    private String prize;
    private String text;

    private LotterDialog(boolean win, String prize, String text) {
        this.win = win;
        this.prize = prize;
        this.text = text;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_lotter, container);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        confirmBtn = view.findViewById(R.id.tv_ok);
        textView = view.findViewById(R.id.textView);
        prizeTv = view.findViewById(R.id.prizeTv);
        imageView = view.findViewById(R.id.imageView);

        textView.setText(text);
        prizeTv.setText(prize);
        if (!win) {
            imageView.setImageResource(R.drawable.icon_not_winl);
            prizeTv.setVisibility(View.GONE);
        }
        imageView.setVisibility("hiden".equals(prize) ? View.GONE : View.VISIBLE);
    }

    private void initListener() {
        confirmBtn.setOnClickListener(view -> dismiss());

    }

    public static void showWinLotterDialog(FragmentManager fragmentManager, String prize) {
        LotterDialog lotterDialog = new LotterDialog(true, prize, ActivityUtils.getTopActivity().getResources().getString(R.string.a28));
        lotterDialog.show(fragmentManager, "1");
    }

    public static void showNoWinLotterDialog(FragmentManager fragmentManager) {
        LotterDialog lotterDialog = new LotterDialog(false, null, ActivityUtils.getTopActivity().getResources().getString(R.string.a29));
        lotterDialog.show(fragmentManager, "1");
    }

    public static void showNoLotterDialog(FragmentManager fragmentManager) {
        LotterDialog lotterDialog = new LotterDialog(false, "hiden", ActivityUtils.getTopActivity().getResources().getString(R.string.a30));
        lotterDialog.show(fragmentManager, "1");
    }


    //定义一个接口对象listener
    private OnClick listener;
    private OnCancelClick listener2;

    //获得接口对象的方法。
    public void setOnClick(OnClick listener) {
        this.listener = listener;
    }

    public void setOnCancelClick(OnCancelClick listener2) {
        this.listener2 = listener2;
    }

    //定义一个接口
    public interface OnClick {
        void OnClick();
    }

    //定义一个接口
    public interface OnCancelClick {
        void OnCancelClick();
    }
}
