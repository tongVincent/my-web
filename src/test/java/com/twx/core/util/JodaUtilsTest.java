package com.twx.core.util;

import com.twx.BaseTest;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class JodaUtilsTest extends BaseTest {

    @Test
    public void testMinutesBetween() throws Exception {
        Date left = new Date();
        Date right = new DateTime(left).plusMinutes(30).toDate();

        Assert.assertEquals(30, JodaUtils.minutesBetween(left, right));
        Assert.assertEquals(-30, JodaUtils.minutesBetween(right, left));
    }
}