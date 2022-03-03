package com.u1.gocashm.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.u1.gocashm.model.response.BasePhModel;
import com.u1.gocashm.model.response.NoticeListModel;
import com.u1.gocashm.network.NetworkPhRequest;
import com.u1.gocashm.network.PhSubscriber;
import com.u1.gocashm.util.constant.EventTagPh;
import com.u1.gocashm.util.event.RxBus;
import com.u1.gocashm.util.event.TagEvent;
import com.u1.gocashm.view.dialog.VirusDialog;

import java.util.ArrayList;
import java.util.List;

import static com.u1.gocashm.util.constant.PhConstants.CODE_SUCCESS;

/**
 * @program: peranyo
 * @description:
 * @author: black.cube
 * @create: 2021-06-03 14:54
 **/
public class NoticeMananger {
    public static NoticeListModel notices;
    static StringBuilder sb = new StringBuilder();

    /**
     * 获取最新的紧急通知
     */
    public static void pullUrgentNotices(final Context context) {

        NetworkPhRequest.getMessageList(1,10,TokenUtils.getToken(context),new PhSubscriber<NoticeListModel>() {
            @Override
            public void onNext(NoticeListModel noticeListModel) {
                super.onNext(noticeListModel);
                String code = noticeListModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    NoticeListModel data = notices = noticeListModel.getData();
                    for (final NoticeListModel.Notice item : data.getItems()) {
                        if (item.isUrgent() && !item.isRead()) {
                            VirusDialog dialog = new VirusDialog();
                            dialog.setCancelable2(false);
                            dialog.setTitle(item.getTitle());
                            dialog.setContent(formatNoticeContent(context,item));
                            dialog.show(((FragmentActivity) context).getSupportFragmentManager(),"2");
                            resetNoticeReadStatus(context,new ArrayList<NoticeListModel.Notice>() {{
                                add(item);
                            }});
                            break;
                        }
                    }


                    notifyMessageTagEvent(data.getUnread());
                }
            }
        });

    }

    /**
     * 获取最新的紧急通知
     */
    public static void refreshNotices(final Context context) {
        NetworkPhRequest.getMessageList(1,10,TokenUtils.getToken(context),new PhSubscriber<NoticeListModel>() {
            @Override
            public void onNext(NoticeListModel noticeListModel) {
                super.onNext(noticeListModel);
                String code = noticeListModel.getCode();
                if (CODE_SUCCESS.equals(code)) {
                    NoticeListModel data = notices = noticeListModel.getData();
                    notifyMessageTagEvent(data.getUnread());
                }
            }
        });

    }
    public static void resetNoticeReadStatus(Context context,final List<NoticeListModel.Notice> items) {
        List ids = new ArrayList();
        for (int i = 0; i < items.size(); i++) {
                ids.add(items.get(i).getId());
        }

        NetworkPhRequest.setMessageRead(TokenUtils.getToken(context),ids, new PhSubscriber<BasePhModel>(){
            @Override
            public void onNext(BasePhModel basePhModel) {
                super.onNext(basePhModel);
                if (CODE_SUCCESS.equals(basePhModel.getCode())) {
                    notifyMessageTagEvent(notices == null ? 0 : notices.getUnread() - items.size());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                LogUtil.e(e.getMessage());
            }
        });
    }

    public static void notifyMessageTagEvent(int count){
        if (notices != null) {
            notices.setUnread(count);
        }
        RxBus.getDefault().post(new TagEvent(EventTagPh.MESSAGE_UNREAD_COUNT,count));
    }

    public static Spannable formatNoticeContent(final Context context, final NoticeListModel.Notice notice) {
        sb.setLength(0);
        boolean elip = false;
        if (!TextUtils.isEmpty(notice.getContent()) && notice.getContent().length() > 500) {
            sb.append(notice.getContent().substring(0, 500));
            elip = true;
        } else {
            sb.append(notice.getContent());
        }
        int start = 0, end = 0;
        if (!TextUtils.isEmpty(notice.getLink_address())) {
            start = sb.length();
            sb.append(notice.getLink_address());
            end = sb.length();
        }

        if (elip) {
            sb.append("...");
        }

        SpannableString spannableString = new SpannableString(sb.toString());
        if (start != 0 && end != 0) {
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notice.getLink_address()));
                        context.startActivity(browserIntent);
                    } catch (Exception e) {
                    }
                }
            }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4e97ff")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return spannableString;
    }
}
