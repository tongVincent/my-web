package com.twx.core.util;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.Date;

/**
 * @author wenxin.tong
 * @since 2017/5/10
 */
public abstract class JodaUtils {

    // 时间差是： right - left
    public static int minutesBetween(Date left, Date right) {
        return Minutes.minutesBetween(new DateTime(left), new DateTime(right)).getMinutes();
    }
}
