package com.twx.core.util;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author wenxin.tong
 * @since 2017/5/10
 */
public abstract class JodaUtils {

    /**
     * 时间差是： right - left
     */
    public static int minutesBetween(Date left, Date right) {
        return Minutes.minutesBetween(new DateTime(left), new DateTime(right)).getMinutes();
    }

    /**
     * 时间差是： right - left
     */
    public static int secondsBetween(Date left, Date right) {
        return Seconds.secondsBetween(new DateTime(left), new DateTime(right)).getSeconds();
    }

    /**
     * 判断现在是否在给定的时间的范围内：left <= now < right
     * @param left
     * @param right
     * @return
     */
    public static boolean isNowBetween(Date left, Date right) {
        if (left == null || right == null) {
            return false;
        }
        Date now = new Date();
        return !left.after(now) && right.after(now);
    }

    /**
     * 加上多少分钟
     * @param now
     * @param minutes
     * @return
     */
    public static Date plusMinutes(Date now, int minutes) {
        Assert.notNull(now, "时间不能为null");
        return new DateTime(now).plusMinutes(minutes).toDate();
    }
}
