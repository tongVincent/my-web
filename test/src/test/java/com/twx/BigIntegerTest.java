package com.twx;

import com.twx.core.util.MessageUtil;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class BigIntegerTest extends BaseTest {

    @Test
    public void test001() {
        MessageUtil.onTime(new BigInteger("5").equals(new BigInteger("5")));
    }
}
