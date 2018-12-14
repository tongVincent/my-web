package com.twx.java.math;

import com.twx.BaseTest;
import com.twx.core.util.BigDecimalUtil;
import com.twx.core.util.MessageUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class BigDecimalTest extends BaseTest {

    /**
     * MathContext是用来保留有效数字的，MathContext.DECIMAL32是7位有效数字，但位数超过7位的时候，会舍入到7位。
     * 注意：7位有效数字，不是指小数位数为7位，而是也包括整数的位数。当整数位数超过7位的时候，就会表示成科学计数法。
     */
    @Test
    public void test001() {
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 5, MathContext.DECIMAL32));
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 6, MathContext.DECIMAL32));
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 7, MathContext.DECIMAL32));
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 8, MathContext.DECIMAL32));
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 8, MathContext.DECIMAL64));
        MessageUtil.onTime(new BigDecimal(new BigInteger("331321321323213"), 8));
    }

    /**
     * 除法的时候，一定要有舍入的mode，否则当除不尽的时候会抛出java.lang.ArithmeticException
     * 另外还需要指定精度，否则按被除数的精度来作为舍入的位置
     */
    @Test
    public void test002() {
        MessageUtil.onTime(new BigDecimal("10").divide(new BigDecimal("3"), 8, RoundingMode.HALF_UP));
    }

    @Test
    public void test003() {
        BigDecimal left = BigDecimal.TEN;
        BigDecimal right = left;
        right = BigDecimalUtil.subtract(right, BigDecimal.ONE);
        MessageUtil.onTime(left);
        MessageUtil.onTime(right);
    }

    @Test
    public void test004() {
        MessageUtil.onTime(BigDecimal.ONE.divide(new BigDecimal(100), RoundingMode.HALF_UP));
        MessageUtil.onTime(BigDecimal.ONE.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
    }
}
