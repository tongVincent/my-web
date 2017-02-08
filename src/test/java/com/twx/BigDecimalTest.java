package com.twx;

import com.twx.test.util.MessageUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

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
}
