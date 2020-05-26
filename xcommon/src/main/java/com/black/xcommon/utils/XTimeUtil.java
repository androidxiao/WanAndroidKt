package com.black.xcommon.utils;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.text.format.DateUtils.isToday;

/**
 * Created by chawei on 2017/4/5.
 */
public class XTimeUtil {
    private static final ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> dateFormater4 = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM月dd日");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> dateFormater5 = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };

    public static String friendly_time(Date time) {
        if(time == null) {
            return "";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        String curDate = ((SimpleDateFormat) dateFormater2.get()).format(cal.getTime());
        String paramDate = ((SimpleDateFormat) dateFormater2.get()).format(time);
        if (curDate.equals(paramDate)) {
            ftime = ((SimpleDateFormat) dateFormater5.get()).format(time);

            return ftime;
        } else {
            long lt = time.getTime() / 86400000L;
            long ct = cal.getTimeInMillis() / 86400000L;
            int days = (int) (ct - lt);
            if (days == 0) {
                ftime = ((SimpleDateFormat) dateFormater5.get()).format(time);
            } else if (days == 1) {
                ftime = "昨天";
            } else if (days == 2) {
                ftime = "前天 ";
            } else {
                ftime = ((SimpleDateFormat) dateFormater4.get()).format(time);
            }

            return ftime;
        }
    }

    public static String friendly_time_detail(long longdate) {
        if(longdate <= 0)
            return "";
        Date date = new Date(longdate);
        String res = "";
        if (date != null) {
            SimpleDateFormat format = (SimpleDateFormat) dateFormater2.get();
            if (isToday(date.getTime())) {
                format.applyPattern(isMorning(date.getTime()) ? "上午 HH:mm" : "下午 HH:mm");
                res = format.format(date);
            } else if (isYesterday(date.getTime())) {
                format.applyPattern(isMorning(date.getTime()) ? "昨天 上午 HH:mm" : "昨天 下午 HH:mm");
                res = format.format(date);
            } else if (isCurrentYear(date.getTime())) {
                format.applyPattern(isMorning(date.getTime()) ? "MM-dd 上午 HH:mm" : "MM-dd 下午 HH:mm");
                res = format.format(date);
            } else {
                format.applyPattern(isMorning(date.getTime()) ? "yyyy-MM-dd 上午 HH:mm" : "yyyy-MM-dd 下午 HH:mm");
                res = format.format(date);
            }
        }

        return res;
    }

    public static String friendly_time_list(long longdate) {
        if(longdate <= 0)
            return "";
        Date date = new Date(longdate);
        String res = "";
        if (date != null) {
            SimpleDateFormat format = (SimpleDateFormat) dateFormater2.get();
            if (isToday(date.getTime())) {
                format.applyPattern(isMorning(date.getTime()) ? "上午 HH:mm" : "下午 HH:mm");
                res = format.format(date);
            } else if (isYesterday(date.getTime())) {
                format.applyPattern(isMorning(date.getTime()) ? "昨天" : "昨天");
                res = format.format(date);
            } else if (isCurrentYear(date.getTime())) {
                format.applyPattern(isMorning(date.getTime()) ? "MM-dd" : "MM-dd");
                res = format.format(date);
            } else {
                format.applyPattern(isMorning(date.getTime()) ? "yyyy-MM-dd" : "yyyy-MM-dd");
                res = format.format(date);
            }
        }

        return res;
    }

    public static boolean isMorning(long when) {
        Time time = new Time();
        time.set(when);
        int hour = time.hour;
        return hour >= 0 && hour < 12;
    }

    public static boolean isYesterday(long when) {
        Time time = new Time();
        time.set(when);
        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;
        time.set(System.currentTimeMillis());
        return thenYear == time.year && thenMonth == time.month && time.monthDay - thenMonthDay == 1;
    }

    public static boolean isCurrentYear(long when) {
        Time time = new Time();
        time.set(when);
        int thenYear = time.year;
        time.set(System.currentTimeMillis());
        return thenYear == time.year;
    }

    public static int diffMinutes(Date date1, Date date2) {
        long l1 = date1.getTime();
        long l2 = date2.getTime();
        long cal = 0;
        if (l1 > l2) {
            cal = l1 - l2;
        } else {
            cal = l2 - l1;
        }
        int days = (int) (cal / (1000 * 60));

        return days;
    }

    public static int diffHours(Date date1, Date date2) {
        long l1 = date1.getTime();
        long l2 = date2.getTime();
        long cal = 0;
        if (l1 > l2) {
            cal = l1 - l2;
        } else {
            cal = l2 - l1;
        }
        int days = (int) (cal / (1000 * 60 * 60));

        return days;
    }

    public static int diffDays(Date date1, Date date2) {
        long l1 = date1.getTime();
        long l2 = date2.getTime();
        long cal = 0;
        if (l1 > l2) {
            cal = l1 - l2;
        } else {
            cal = l2 - l1;
        }
        int days = (int) (cal / (1000 * 60 * 60 * 24));

        return days;
    }

    public static String toMinuteSecond(int second) {
        int m = second / 60;
        int s = second % 60;
       String se=s+"";
        if(s<10)
        {
            se="0"+s;
        }
        return m + ":" + se;
    }
}
