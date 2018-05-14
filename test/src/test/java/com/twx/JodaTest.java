package com.twx;

import com.twx.core.util.MessageUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * @author wenxin.tong
 * @since 2017/7/19
 */
public class JodaTest extends BaseTest {

    @Test
    public void test001() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        MessageUtil.onTime(new DateTime().getHourOfDay());
        MessageUtil.onTime(dateTimeFormatter.parseDateTime("2017-09-15 22:22:22").getHourOfDay());
        MessageUtil.onTime(dateTimeFormatter.parseDateTime("2017-09-15 00:22:22").getHourOfDay());
        MessageUtil.onTime(dateTimeFormatter.parseDateTime("2017-09-15 00:00:00").getHourOfDay());
        MessageUtil.onTime(dateTimeFormatter.parseDateTime("2017-09-15 23:22:22").getHourOfDay());
    }

    @Test
    public void test002() {
        MessageUtil.onTime(new DateTime().withHourOfDay(11));
        MessageUtil.onTime(new LocalDateTime().withHourOfDay(11));
        MessageUtil.onTime(new LocalDate().toDateTimeAtStartOfDay().withHourOfDay(11));
    }
}
