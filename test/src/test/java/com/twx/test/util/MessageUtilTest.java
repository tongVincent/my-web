package com.twx.test.util;

import com.twx.core.util.MessageUtil;
import junit.framework.TestCase;

/**
 * Created by vincent.tong on 2016/6/30.
 */
public class MessageUtilTest extends TestCase {

    public void testOnTime() throws Exception {
        MessageUtil.onTime("测试下。");
    }
}