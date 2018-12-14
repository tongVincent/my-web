package com.twx;

import com.twx.core.util.MessageUtil;
import org.junit.Test;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class PrimitiveTest extends BaseTest {

    /**
     * 从下面的测试中可以知道，Integer.class不是基本类型，而Integer.TYPE 才表示基本类型
     */
    @Test
    public void test001() {
        MessageUtil.onTime(Integer.class.isPrimitive());
        MessageUtil.onTime(Integer.TYPE.isPrimitive());
    }

    @Test
    public void test002() {
        Object a = true;
        MessageUtil.onTime(a.equals(Boolean.TRUE));
    }

    @Test
    public void test003() {
        Object a = 5;
        MessageUtil.onTime(a instanceof Number);
    }

    @Test
    public void test004() {
        Integer a = 5;
        MessageUtil.onTime(a.equals(new Short("5")));
    }

    @Test
    public void test005() {
        byte b = (byte) 0x90;
        MessageUtil.onTime((b & (byte) 0xc0) == (byte) 0x80);
    }

    @Test
    public void test006() {
        MessageUtil.onTime(1 % 6);
        MessageUtil.onTime(2 % 6);
        MessageUtil.onTime(3 % 6);
        MessageUtil.onTime(4 % 6);
        MessageUtil.onTime(5 % 6);
        MessageUtil.onTime(6 % 6);
        MessageUtil.onTime(-1 % 6);
        MessageUtil.onTime(-2 % 6);
        MessageUtil.onTime(-3 % 6);
        MessageUtil.onTime(-4 % 6);
        MessageUtil.onTime(-5 % 6);
        MessageUtil.onTime(-6 % 6);
        MessageUtil.onTime(7 / 6);
        MessageUtil.onTime(2 / 6);
        MessageUtil.onTime(3 / 6);
        MessageUtil.onTime(4 / 6);
        MessageUtil.onTime(11 / 6);
        MessageUtil.onTime(6 / 6);
        MessageUtil.onTime(-7 / 6);
        MessageUtil.onTime(-2 / 6);
        MessageUtil.onTime(-3 / 6);
        MessageUtil.onTime(-4 / 6);
        MessageUtil.onTime(-11 / 6);
        MessageUtil.onTime(-6 / 6);
    }
}
