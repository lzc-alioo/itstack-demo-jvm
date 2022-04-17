package com.lzc.wuxin.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期组件
 *
 * @author 悟心
 */
public class DateTimeUtil {

    private static final int NUMBER_TEN = 10;

    /**
     * 一个月天数（30天）
     */
    private static final int ONE_MOUTH_DAYS_30 = 30;

    /**
     * 时间值（单位：ms）
     */
    public static final int ONE_SECOND_MS = 1000;

    /**
     * 时间值（单位：s）
     */
    public static final int ONE_MINUTE_S = 60;
    public static final int ONE_HOUR_S = 60 * ONE_MINUTE_S;
    public static final int ONE_DAY_S = 24 * ONE_HOUR_S;
    public static final int ONE_WEEK_S = 7 * ONE_DAY_S;
    public static final int ONE_YEAR_S = 365 * ONE_DAY_S;

    /**
     * 常见格式
     */
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";



    /**
     * 获取两个日期对象相差天数
     *
     * @param date1 日期对象
     * @param date2 日期对象
     * @return int 日差值
     */
    public static int compareDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }

        long time1 = date1.getTime();
        long time2 = date2.getTime();

        long margin = time1 - time2;

        /* 转化成天数,向上取整 */
        int ret = (int) Math.ceil((double) margin / (1000 * 60 * 60 * 24));

        return ret;
    }

    /**
     * 获取两个日期对象相差分钟数
     *
     * @param date1 日期对象
     * @param date2 日期对象
     * @return int 日差值
     */
    public static int compareMinutes(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }

        long time1 = date1.getTime();
        long time2 = date2.getTime();

        long margin = time1 - time2;

        /* 转化成天数 */
        int ret = (int) Math.floor((double) margin / (1000 * 60));

        return ret;
    }

    /**
     * date1 是否早于date2
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean before(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("日期不能为空");
        }
        return date2.getTime() - date1.getTime() >= 0;
    }

    /**
     * 获取两个日期对象相差秒数
     *
     * @param date1 日期对象
     * @param date2 日期对象
     * @return int 相差秒数
     */
    public static int compareSecond(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        long time1 = date1.getTime();
        long time2 = date2.getTime();

        long margin = time1 - time2;

        // Long longValue = new Long(margin / (1000));
        Long longValue = Long.valueOf(margin / (1000));

        return longValue.intValue();
    }


    /**
     * 日期字符串转时间戳
     *
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static Long string2Millis(String dateStr, String formatStr) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
            return simpleDateFormat.parse(dateStr).getTime();
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 获取当前时
     *
     * @return 当前时间，如:23点,0点,1点等
     */
    public static int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return hour;
    }

    /**
     * 获取当前分
     *
     * @return 当前分
     */
    public static int getCurrentMinute() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.MINUTE);

        return hour;
    }

    /**
     * 获取当前月份
     *
     * @return 月份
     */
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前时间的星期数:星期日=7;星期一=1;星期二=2;星期三=3;星期四=4;星期五=5;星期六=6;
     *
     * @return 周数值
     */
    public static int getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        week = week - 1;
        if (week == 0) {
            week = 7;
        }
        return week;
    }

    /**
     * 获取指定日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) {
            week = 0;
        }
        return weekDays[week];
    }

    /**
     * 获取当前年
     *
     * @return 当前年
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 取得当前的日期时间字符串YYYY-MM-DD
     *
     * @return String 取得当前的日期时间字符串
     */
    public static String getDateString() {
        String format = "yyyy-MM-dd";
        return getDateTimeString(format);
    }

    /**
     * 取得当前的日期时间字符串YYYY/MM/DD (移动)
     *
     * @return String 取得当前的日期时间字符串YYYY/MM/DD
     */
    public static String getDateString2() {
        String format = "yyyy/MM/dd";
        return getDateTimeString(format);
    }

    /**
     * 取得当前的日期时间字符串YYMMDDHHMISS
     *
     * @return String 取得当前的日期时间字符串YYMMDDHHMISS
     */
    public static String getDateTime12String() {
        String format = "yyMMddHHmmss";
        return getDateTimeString(format);
    }

    /**
     * 取得当前的日期时间字符串YYYYMMDDHHMISS
     *
     * @return String 取得当前的日期时间字符串YYYYMMDDHHMISS
     */
    public static String getDateTime14String() {
        String format = "yyyyMMddHHmmss";
        return getDateTimeString(format);
    }

    /**
     * 取得当前的日期时间字符串yyyymmddhhmmssSSS
     *
     * @return String 取得当前的日期时间字符串yyyymmddhhmmssSSS
     */
    public static String getDateTime17String() {
        String format = "yyyymmddhhmmssSSS";
        return getDateTimeString(format);
    }

    /**
     * 取得当前的日期时间字符串YYYYMM
     *
     * @return String 取得当前的日期时间字符串
     */
    public static String getDateTime6String() {
        String format = "yyyyMM";
        return getDateTimeString(format);
    }

    /**
     * 取得当前的日期时间字符串YYYYMMDD
     *
     * @return String 取得当前的日期时间字符串
     */
    public static String getDateTime8String() {
        String format = "yyyyMMdd";
        return getDateTimeString(format);
    }

    /**
     * 取得当前的日期整型数组共7项,分别为年,月,日,时,分,秒,毫秒
     *
     * @return int[] 共7项,分别为年,月,日,时,分,秒,毫秒
     */
    public static int[] getDateTimes() {
        int[] dates = new int[7];
        Calendar calendar = Calendar.getInstance();
        dates[0] = calendar.get(Calendar.YEAR);
        dates[1] = calendar.get(Calendar.MONTH) + 1;
        dates[2] = calendar.get(Calendar.DAY_OF_MONTH);
        dates[3] = calendar.get(Calendar.HOUR_OF_DAY);
        dates[4] = calendar.get(Calendar.MINUTE);
        dates[5] = calendar.get(Calendar.SECOND);
        dates[6] = calendar.get(Calendar.MILLISECOND);
        return dates;
    }

    /**
     * 取得当前的日期时间字符串YYYY-MM-DD HH:mm:ss
     *
     * @return String 取得当前的日期时间字符串YYYY-MM-DD HH:mm:ss
     */
    public static String getDateTimeString() {
        String format = "yyyy-MM-dd HH:mm:ss";
        return getDateTimeString(format);
    }

    /**
     * 取得当前的日期时间字符串
     *
     * @param format 格式,如String format = "yyyy-MM-dd HH:mm:ss";
     * @return String 取得当前的日期时间字符串
     */
    public static String getDateTimeString(String format) {
        return toDateTimeString(new Date(), format);
    }

    /**
     * 获取指定时间所在周的第一天的时间
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 年、月、日数组
     */
    public static int[] getDayOfWeek(int year, int month, int day) {
        int[] rtn = new int[6];
        int week = 0;
        long longDate = 0;

        Date date = null;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);

        week = calendar.get(Calendar.DAY_OF_WEEK);
        // longDate = (calendar.getTime()).getTime() - 60 * 1000 * 60 * 24 *
        // (week - 1);
        longDate = (calendar.getTime()).getTime() - 60L * 1000 * 60 * 24 * (week - 1);
        date = new Date(longDate);
        calendar1.setTime(date);

        rtn[0] = calendar1.get(Calendar.YEAR);
        rtn[1] = calendar1.get(Calendar.MONTH) + 1;
        rtn[2] = calendar1.get(Calendar.DATE);

        // longDate = (calendar.getTime()).getTime() + (60 * 1000 * 60 * 24 * (7
        // - week));
        longDate = (calendar.getTime()).getTime() + (60L * 1000 * 60 * 24 * (7 - week));
        date = new Date(longDate);
        calendar2.setTime(date);
        rtn[3] = calendar2.get(Calendar.YEAR);
        rtn[4] = calendar2.get(Calendar.MONTH) + 1;
        rtn[5] = calendar2.get(Calendar.DATE);

        return rtn;
    }

    /**
     * 根据传入的日期得到是每周的周几
     *
     * @param sendDate 日期格式：yyyy-MM-dd
     * @return
     */
    public static String getDayOfWeek(String sendDate) {
        String[] dayName = {"0", "7", "1", "2", "3", "4", "5", "6"};

        Calendar c = toCalendar(sendDate + " 00:00:00");
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        return dayName[dayOfWeek];
    }

    /**
     * 获取后几天对应的当前时间
     *
     * @param format 格式化如 yyyy-MM-dd
     * @return String
     */
    public static String getNextDateString(int days, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return toDateTimeString(calendar.getTime(), format);
    }

    /**
     * 获取指定日期后几天对应的当前时间
     *
     * @param format 格式化如 yyyy-MM-dd
     * @return String
     */
    public static String getNextDateString(Date nowDate, int days, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return toDateTimeString(calendar.getTime(), format);
    }

    public static String getDateFixTime(Date date, int hour, int minute, int second, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);

        return toDateTimeString(cal.getTime(), format);
    }

    public static Date getDateFixTime(Date date, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0); //bugfix,毫秒数清零

        return cal.getTime();
    }

    /**
     * 获取指定时间的后一天的Date String
     *
     * @param currentDate 格式化如 yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String getNextDateString(String currentDate) {
        Calendar calendar = toCalendar(currentDate + " 00:00:01");
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return toDateString(calendar);
    }

    /**
     * 获取后几秒对应的当前时间
     *
     * @param seconds
     * @return String  格式化如 yyyy-MM-dd
     */
    public static String getNextDateStringBySecond(Date nowDate, int seconds, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.SECOND, seconds);

        return toDateTimeString(calendar.getTime(), format);
    }

    /**
     * 获取指定日期后几秒对应的当前时间
     *
     * @param startDate
     * @param seconds
     * @return String  格式化如 yyyy-MM-dd
     */
    public static Date getNextDateTimeBySecond(Date startDate, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.SECOND, seconds);

        return calendar.getTime();
    }

    /**
     * 取得当前的日期时间字符串HH:mm:ss
     *
     * @return String 取得当前的日期时间字符串
     */
    public static String getTimeString() {
        String format = "HH:mm:ss";
        return getDateTimeString(format);
    }

    /**
     * 日期字符串校验，是否与指定的 format格式一致.
     *
     * @param dateStr
     * @param format  格式字符串，比如 yyyy-MM-dd
     * @return
     */
    public static boolean isDate(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 返回当前时间的Date
     */
    public static Date nowDate() {
        return new Date();
    }


    /**
     * 通过String,组织一个日历
     *
     * @param datetime
     * @return 通过整型数组, 返回一个日历
     */
    public static Calendar toCalendar(String datetime) {
        Date date = toDateFromStr(datetime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 取得当前的日期时间 按默认格式YYYY-MM-DD HH:mm:ss不对则返回null
     *
     * @param datestr 字符串
     * @return 取得当前的日期时间 按默认格式不对则返回null
     */
    public static Date toDateFromStr(String datestr) {
        try {
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(datestr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取得当前的日期时间
     *
     * @param str    字符串
     * @param format 格式
     * @return 取得当前的日期时间 如果格式不对则返回null
     */
    public static Date toDateFromStr(String str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取得给定日历,给定格式的日期字符串
     *
     * @param calendar 日历,给定一个日历
     * @return String 取得默认的日期时间字符串"yyyy-MM-dd"
     */
    public static String toDateString(Calendar calendar) {
        String format = "yyyy-MM-dd";
        return toDateTimeString(calendar.getTime(), format);
    }

    /** ****************************************************** */
    // 以下为数据库使用的日期方法,Timestamp ,java.sql.Date
    /** ****************************************************** */

    /**
     * 生成标准日期,格式为 YYYY-MM-DD
     *
     * @param date The Date
     * @return 生成日期, 格式为 YYYY-MM-DD
     */
    public static String toDateString(Date date) {
        return toDateString(date, "-");
    }

    /**
     * 生成标准日期,格式为 YYYY+spe+MM+spe+DD
     *
     * @param date The Date
     * @return 生成日期, 格式为 YYYY+spe+MM+spe+DD
     */
    public static String toDateString(Date date, String spe) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        String monthStr = "" + month;
        String dayStr = "" + day;
        String yearStr = "" + year;

        if (month < NUMBER_TEN) {
            monthStr = "0" + month;
        }
        if (day < NUMBER_TEN) {
            dayStr = "0" + day;
        }
        return yearStr + spe + monthStr + spe + dayStr;
    }

    /**
     * 取得给定日历,给定格式的日期时间字符串
     *
     * @param calendar 日历,给定一个日历
     * @return String 取得默认的日期时间字符串"yyyy-MM-dd HH:mm:ss"
     */
    public static String toDateTimeString(Calendar calendar) {
        String format = "yyyy-MM-dd HH:mm:ss";
        return toDateTimeString(calendar.getTime(), format);
    }

    /**
     * 取得给定日历,给定格式的日期时间字符串
     *
     * @param calendar 日历,给定一个日历
     * @param format   格式,如String format = "yyyy-MM-dd HH:mm:ss";
     * @return String 取得给定日历,给定格式的日期时间字符串
     */
    public static String toDateTimeString(Calendar calendar, String format) {
        return toDateTimeString(calendar.getTime(), format);
    }

    /**
     * 生成标准格式的字符串 格式为: "MM-DD-YYYY HH:MM:SS"
     *
     * @param date The Date
     * @return 生成默认格式的字符串 格式为: "MM-DD-YYYY HH:MM:SS"
     */
    public static String toDateTimeString(Date date) {
        if (date == null) {
            return "";
        }
        String dateString = toDateString(date);
        String timeString = toTimeString(date);

        if (dateString == null || timeString == null) {
            return "";
        }
        return dateString + " " + timeString;
    }

    /**
     * 取得给定时间,给定格式的日期时间字符串
     *
     * @param date   日期,给定一个时间
     * @param format 格式,如String format = "yyyy-MM-dd HH:mm:ss";
     * @return String 取得给定时间,给定格式的日期时间字符串
     */
    public static String toDateTimeString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 取得给定时间,给定格式的日期时间字符串,标准格式:"yyyy-MM-dd HH:mm:ss";
     *
     * @param datetime 日期,给定一个时间的毫秒数
     * @return String 取得给定时间,给定格式的日期时间字符串
     */
    public static String toDateTimeString(long datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(datetime));
    }

    /**
     * 取得给定时间,给定格式的日期时间字符串
     *
     * @param datetime 日期,给定一个时间的毫秒数
     * @param format   格式,如String format = "yyyy-MM-dd HH:mm:ss";
     * @return String 取得给定时间,给定格式的日期时间字符串
     */
    public static String toDateTimeString(long datetime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(datetime));
    }

    /**
     * 根据输入的时,分,秒,生成时间格式 HH:MM:SS
     *
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return 生成时间格式为 HH:MM:SS
     */
    public static String toTimeString(int hour, int minute, int second) {
        String hourStr = "" + hour;
        String minuteStr = "" + minute;
        String secondStr = "" + second;

        if (hour < NUMBER_TEN) {
            hourStr = "0" + hour;
        }
        if (minute < NUMBER_TEN) {
            minuteStr = "0" + minute;
        }
        if (second < NUMBER_TEN) {
            secondStr = "0" + second;
        }
        return hourStr + ":" + minuteStr + ":" + secondStr;
    }

    /**
     * 根据输入的时间,生成时间格式 HH:MM:SS
     *
     * @param date java.util.Date
     * @return 生成时间格式为 HH:MM:SS
     */
    public static String toTimeString(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        return toTimeString(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    /**
     * 获得某天最大时间 2020-07-10 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得某天最小时间 2020-07-10 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getStartDayOfMonth() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime firstDay = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime startOfDay = firstDay.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getEndDayOfMonth() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime firstDay = date.with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime startOfDay = firstDay.with(LocalTime.MAX);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 判断时间是不是今天
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    public static boolean isToday(Date date) {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String nowDay = sf.format(now);
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     * 获取当前时间到当晚24点的秒数
     *
     * @return
     */
    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    /**
     * 获取当前时间的前几秒
     *
     * @param date
     * @param secends
     * @return
     */
    public static Date getDateFromNowOnBySeconds(Date date, int secends) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, secends);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取当前时间的前几分钟
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date getDateFromNowOnByMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取当前时间的后N天
     *
     * @param date
     * @param days
     * @return
     */
    public static Date getDateFromNowOnByDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取当前时间30天后的date
     *
     * @return 往后推30天的时间
     */
    public static Date getAfter30DayDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, ONE_MOUTH_DAYS_30);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取当前时间的后N天的23:59:59
     *
     * @param date
     * @param days
     * @return
     */
    public static Date getMaxDateFromNowOnByDays(Date date, int days) {
        return getEndOfDay(getDateFromNowOnByDays(date, days));
    }

    /**
     * 获取当前时间的前N天的00:00:00
     *
     * @param date
     * @param days
     * @return
     */
    public static Date getMinDateFromNowOnByDays(Date date, int days) {
        return getStartOfDay(getDateFromNowOnByDays(date, days));
    }

    /**
     * 获取X天相差秒数
     *
     * @param date 当前时间
     * @param days 天数
     * @return
     */
    public static int getCompareSecond(Date date, int days) {
        if(days == 0){
            return 5;
        }
        Date endOfDay = getMaxDateFromNowOnByDays(date, days);
        return compareSecond(endOfDay, date);
    }


}
