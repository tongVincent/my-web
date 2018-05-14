package com.twx;

import com.twx.core.util.MessageUtil;
import org.junit.Test;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class PrimitiveTest extends BaseTest {

    @Test
    public void test001() {
        MessageUtil.onTime(Integer.class.isPrimitive());
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
}
