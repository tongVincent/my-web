package com.twx.java.lang;

import com.twx.BaseTest;
import com.twx.core.util.MessageUtil;
import org.junit.Test;

/**
 * created by tongwenxin on 2018/12/14
 */
public class MathTest extends BaseTest {

    @Test
    public void testRandom() {
        double random = Math.random() * 5;
        MessageUtil.onTime(random);
        MessageUtil.onTime((int) random);
    }
}
