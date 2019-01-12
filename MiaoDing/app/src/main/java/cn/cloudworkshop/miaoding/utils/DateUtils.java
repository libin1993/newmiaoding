package cn.cloudworkshop.miaoding.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Author：Libin on 2016/12/19 10:11
 * Email：1993911441@qq.com
 * Describe：时间戳
 */
public class DateUtils {

    /**
     * @param type
     * @param time
     * @return 获取日期
     */
    public static String getDate(String type, long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(type, Locale.CHINA);
        return sdf.format(new Date(time));
    }

    /**
     * @param time
     * @return 字符串转时间戳毫秒
     */
    public static long getTime(String type, String time) {

        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat(type).parse(time));
            return c.getTimeInMillis();
        } catch (ParseException e) {

        }
        return 0;
    }

    /**
     * @return 当前时间，精确都秒
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }


    /**
     * @return 时间格式转换
     */
    public static String formatTime(String type, String formatType, String time) {
        long time1 = getTime(type, time);
        return getDate(formatType, time1);
    }

}
