package com.twx.java.lang;

import com.twx.BaseTest;
import com.twx.core.util.MessageUtil;
import org.junit.Test;

public class LongTest extends BaseTest {

    /**
     * highestOneBit返回参数的二进制码中保留最高位1的值
     * lowestOneBit返回参数的二进制码中保留最低位1的值
     */
    @Test
    public void test001() {
        MessageUtil.onTime(Integer.highestOneBit(1));
    }

}
