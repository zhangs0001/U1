package com.u1.gocashm.util.constant;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimeFormatConfigs {
    public final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public final static SimpleDateFormat formatYear = new SimpleDateFormat("yyyy", Locale.getDefault());
    public final static DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##0");
}