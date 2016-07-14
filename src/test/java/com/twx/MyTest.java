package com.twx;

import com.twx.test.util.MessageUtil;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by vincent.tong on 2016/7/14.
 */
public class MyTest {

    @Test
    public void test001() {
        MessageUtil.onTime(new BigInteger("5").equals(new BigInteger("5")));
    }

    @Test
    public void test002() {
        MessageUtil.onTime(Integer.class.isPrimitive());
    }

    @Test
    public void test003() {
        Object a = true;
        MessageUtil.onTime(a.equals(Boolean.TRUE));
    }

    @Test
    public void test005() {
        Object a = 5;
        MessageUtil.onTime(a instanceof Number);
    }

    @Test
    public void test006() {
        Integer a = 5;
        MessageUtil.onTime(a.equals(new Short("5")));
    }
}
