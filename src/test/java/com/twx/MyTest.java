package com.twx;

import com.twx.test.util.MessageUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

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

    /**
     * 通过下面的测试，可以知道：
     * 1、0表示，当位数不够的时候，补0。#表示，当位数不够的时候，不管。
     * 2、"00#"与".#0"格式是不合法的，根据第一点，可以推出此不合法。
     * 3、整数的分隔符，以小数点的地方第一次出现的为准。如#,###,##是每2位分隔，#,##,###是每3位分隔。
     * 4、小数没有分隔的概念。
     */
    @Test
    public void test007() {
        DecimalFormat format = new DecimalFormat("00.00");
        MessageUtil.onTime("00.00格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

        format = new DecimalFormat("##.##");
        MessageUtil.onTime("##.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

        format = new DecimalFormat(",##.##");
        MessageUtil.onTime(",##.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

        format = new DecimalFormat("#,##.##");
        MessageUtil.onTime("#,##.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

        format = new DecimalFormat("#,###,##.##");
        MessageUtil.onTime("#,###,##.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321653334));

        format = new DecimalFormat("#,##,###.##");
        MessageUtil.onTime("#,##,###.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321653334));

        format = new DecimalFormat("###,##,#.##");
        MessageUtil.onTime("###,##,#.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321653334));

        format = new DecimalFormat("##.0#");
        MessageUtil.onTime("##.0#格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

        format = new DecimalFormat("#00");
        MessageUtil.onTime("#00格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

    }

    /**
     * MathContext是用来保留有效数字的，MathContext.DECIMAL32是7位有效数字，但位数超过7位的时候，会舍入到7位。
     * 注意：7位有效数字，不是指小数位数为7位，而是也包括整数的位数。当整数位数超过7位的时候，就会表示成科学计数法。
     */
    @Test
    public void test008() {
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 5, MathContext.DECIMAL32));
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 6, MathContext.DECIMAL32));
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 7, MathContext.DECIMAL32));
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 8, MathContext.DECIMAL32));
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 8, MathContext.DECIMAL64));
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 8));
    }

    @Test
    public void test009() {
        String str = "a严";
        MessageUtil.onTime(str.length());
        for (byte b : str.getBytes(Charset.forName("UTF-8"))) {
            MessageUtil.onTime(Integer.toBinaryString(b));
        }
    }

    @Test
    public void test010() {
        byte b = (byte) 0x90;
        MessageUtil.onTime((b & (byte) 0xc0) == (byte) 0x80);
    }
}
