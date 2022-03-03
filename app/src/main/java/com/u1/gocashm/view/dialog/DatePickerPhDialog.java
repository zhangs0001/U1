package com.u1.gocashm.view.dialog;

import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.View;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelMonthPicker;
import com.aigestudio.wheelpicker.widgets.WheelYearPicker;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.view.listener.OnCreateBodyViewListener;
import com.u1.gocashm.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * @author hewei
 * @date 2018/9/7 0007 上午 11:01
 */
public class DatePickerPhDialog implements WheelMonthPicker.OnItemSelectedListener {

    private static final String TAG = DatePickerPhDialog.class.getSimpleName();
    private WheelYearPicker dateYear;
    private WheelPicker dateMonth;
    private WheelPicker dateDay;

    private CircleDialog.Builder builder;
    private String date;
    private FragmentActivity activity;

    private int year;
    private String month;
    private String day;

    private int type;
    public static final int NEXTINCOMEDATE = 1;

    private static final HashMap<String, String> M1 = new HashMap<String, String>(){{
        put("01", "Jan");
        put("02", "Feb");
        put("03", "Mar");
        put("04", "Apr");
        put("05", "May");
        put("06", "Jun");
        put("07", "Jul");
        put("08", "Aug");
        put("09", "Sept");
        put("10", "Oct");
        put("11", "Nov");
        put("12", "Dec");
    }};

    private static final HashMap<String, String> M2 = new HashMap<String, String>(){{
        put("Jan","01");
        put("Feb","02");
        put("Mar","03");
        put("Apr","04");
        put("May","05");
        put("Jun","06");
        put("Jul","07");
        put("Aug","08");
        put("Sept","09");
        put("Oct","10");
        put("Nov","11");
        put("Dec","12");
    }};

    public DatePickerPhDialog(FragmentActivity activity,final String nowDate) {
        this.activity = activity;
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        builder = new CircleDialog.Builder();
        CircleDialog.Builder builder = new CircleDialog.Builder();
        ConfigButton button = new ConfigButton() {
            @Override
            public void onConfig(ButtonParams params) {
                params.textColor = 0xFFFF8010;
                params.textSize = 20;
            }
        };
        builder.setTitle(activity.getString(R.string.date_picker_hint))
                .setBodyView(R.layout.date_picker_dialog, new OnCreateBodyViewListener() {
                    @Override
                    public void onCreateBodyView(View view) {
                        Calendar now = Calendar.getInstance();
                        dateYear = view.findViewById(R.id.date_year);
                        dateMonth = view.findViewById(R.id.date_month);
                        dateDay = view.findViewById(R.id.date_day);
                        //dateYear.setYearStart(year - 18);
                        dateYear.setYearStart(1960);
                        if (type == NEXTINCOMEDATE) {
                            dateYear.setYearFrame(year, year + 1);
                        } else {
                            dateYear.setYearEnd(year - 18);
                            //dateYear.setYearEnd(now.get(Calendar.YEAR));
                        }
                        dateMonth.setData(getMonthsData(year));
//                        if (TextUtils.isEmpty(nowDate)) {
                        dateYear.setSelectedYear(year);
                        month = dateMonth.getData().get(dateMonth.getCurrentItemPosition()).toString();
                        dateDay.setData(getDaysData(year, month));
//                        } else {
//                            int nowYear = Integer.parseInt(nowDate.substring(nowDate.length() - 4, nowDate.length()));
//                            String month = nowDate.substring(0, 2);
//                            day = nowDate.substring(3, 5);
//                            dateYear.setSelectedYear(nowYear);
//                            dateDay.setData(getDaysData(year, Integer.parseInt(month)));
//                            dateDay.setSelectedItemPosition(dateDay.getData().indexOf(day));
//                            dateMonth.setSelectedItemPosition(dateMonth.getData().indexOf(month));
//                        }
                        dateYear.setOnItemSelectedListener(DatePickerPhDialog.this);
                        dateMonth.setOnItemSelectedListener(DatePickerPhDialog.this);
                    }
                })
                .configPositive(button)
                .setPositive(activity.getString(R.string.confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        date = String.valueOf(dateYear.getCurrentYear() + "-" + M2.get(dateMonth.getData().get(dateMonth.getCurrentItemPosition())) + "-" + dateDay.getData().get(dateDay.getCurrentItemPosition()));
                        listener.onItemSelect(date);
                    }
                }).show(activity.getSupportFragmentManager());
    }

    public String getDate() {
        return date;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        try {
            switch (picker.getId()) {
                case R.id.date_year:
                    year = Integer.parseInt(data.toString());
                    if (type == NEXTINCOMEDATE)
                        dateMonth.setData(getMonthsData(year));
                    dateDay.setData(getDaysData(year, month));
                    break;
                case R.id.date_month:
                    month = data.toString();
                    dateDay.setData(getDaysData(year, month));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public void show() {
        builder.show(activity.getSupportFragmentManager());
    }


    private List<String> getDaysData(int year, String month) {
        List<String> list = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        int nm = Integer.parseInt(M2.get(month));
        int months = getDaysByYearMonth(year, nm);
        if (type == NEXTINCOMEDATE && year == now.get(Calendar.YEAR) && nm == now.get(Calendar.MONTH) + 1) {
            int day = now.get(Calendar.DAY_OF_MONTH);
            for (int i = day; i <= months; i++) {
                if (i < 10) {
                    list.add("0" + i);
                } else {
                    list.add(i + "");
                }
            }
        } else {
            for (int i = 1; i <= months; i++) {
                if (i < 10) {
                    list.add("0" + i);
                } else {
                    list.add(i + "");
                }
            }
        }
        return list;
    }

    private List<String> getMonthsData(int year) {
        List<String> list = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        if (type == NEXTINCOMEDATE && year == now.get(Calendar.YEAR)) {
            int m = now.get(Calendar.MONTH) + 1;
            for (int i = m; i <= 12; i++) {
                if (i < 10) {
                    list.add("0" + i);
                } else {
                    list.add(i + "");
                }
            }
        } else {
            for (int i = 1; i <= 12; i++)
                if (i < 10) {
                    list.add("0" + i);
                } else {
                    list.add(i + "");
                }
        }

        for (int i = 0; i < list.size(); i++) {
            list.set(i, M1.get(list.get(i)));
        }
        return list;
    }

    /**
     * 根据 年、月 获取对应的月份 的 天数
     */
    private int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    private OnDateSelectListener listener;

    public void setOnDateSelectListener(OnDateSelectListener listener) {
        this.listener = listener;
    }

    public interface OnDateSelectListener {
        void onItemSelect(String date);
    }

}
