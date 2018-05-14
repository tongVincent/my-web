package com.twx;

import com.twx.core.util.MessageUtil;
import org.junit.Test;

import java.util.Date;

/**
 * @author wenxin.tong
 * @since 2017/9/20
 */
public class DateTest extends BaseTest {

    @Test
    public void test001() throws Exception {
        MessageUtil.onTime(new Date(1505836800000L));
    }
}
