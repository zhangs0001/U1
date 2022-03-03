package com.u1.gocashm.view.dialog;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.u1.gocashm.R;
import com.u1.gocashm.model.response.RenewaInfoPhModel;
import com.u1.gocashm.util.DateTimePhUtil;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.mylhyl.circledialog.AbsBaseCircleDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hewei
 * @date 2020/2/25 0025 下午 3:57
 */
public class ExtendSuccessfulDialog extends AbsBaseCircleDialog {

    @BindView(R.id.id_extend_mini_repayment)
    TextView id_extend_mini_repayment;
    @BindView(R.id.id_extend_hint)
    TextView id_extend_hint;

    RenewaInfoPhModel.RenewaInfo renewaInfo;

    public ExtendSuccessfulDialog(RenewaInfoPhModel.RenewaInfo renewaInfo) {
        this.renewaInfo = renewaInfo;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_extend_successful, container);
        ButterKnife.bind(this, view);

        id_extend_mini_repayment.setText(String.format("EGP %1$s", renewaInfo.getRenewalPreInfo().getMin_repay_amount()));
        String html = "Extension submission is successful, pls confirm minimun repayment amount that need to make one-off payment on <b><font color='#F50B0B'>" + DateTimePhUtil.changeYMDtoEn2(renewaInfo.getRenewalPreInfo().getAppointment_paid_time()) + "</font></b> to complete extension process, otherwise it will fail.";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            id_extend_hint.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            id_extend_hint.setText(Html.fromHtml(html));
        }
        return view;
    }


    @OnClick({R.id.tv_repayment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_repayment:
                RxBus.getDefault().post(new TagEvent(EventTagPh.REPAYMENT));
                dismiss();
                break;
        }
    }
}
