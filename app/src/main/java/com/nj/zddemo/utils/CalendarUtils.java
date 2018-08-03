package com.nj.zddemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2018-08-03.
 */

public class CalendarUtils {

    public static String getToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        return format.format(new Date());
    }
}
