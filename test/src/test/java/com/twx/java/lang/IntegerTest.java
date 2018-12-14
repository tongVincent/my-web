package com.twx.java.lang;

import com.twx.BaseTest;
import com.twx.core.util.MessageUtil;
import org.junit.Test;

public class IntegerTest extends BaseTest {

    /**
     * highestOneBit返回参数的二进制码中保留最高位1的值
     * lowestOneBit返回参数的二进制码中保留最低位1的值
     */
    @Test
    public void test001() {
        MessageUtil.onTime(Integer.highestOneBit(1));
        MessageUtil.onTime(Integer.highestOneBit(2));
        MessageUtil.onTime(Integer.highestOneBit(3));
        MessageUtil.onTime(Integer.highestOneBit(4));
        MessageUtil.onTime(Integer.highestOneBit(5));
        MessageUtil.onTime(Integer.highestOneBit(6));
        MessageUtil.onTime(Integer.highestOneBit(7));
        MessageUtil.onTime(Integer.highestOneBit(8));
        MessageUtil.onTime(Integer.lowestOneBit(1));
        MessageUtil.onTime(Integer.lowestOneBit(2));
        MessageUtil.onTime(Integer.lowestOneBit(3));
        MessageUtil.onTime(Integer.lowestOneBit(4));
        MessageUtil.onTime(Integer.lowestOneBit(5));
        MessageUtil.onTime(Integer.lowestOneBit(6));
        MessageUtil.onTime(Integer.lowestOneBit(7));
        MessageUtil.onTime(Integer.lowestOneBit(8));

        MessageUtil.onTime("-------");

        MessageUtil.onTime(Integer.highestOneBit(-1));
        MessageUtil.onTime(Integer.highestOneBit(-2));
        MessageUtil.onTime(Integer.highestOneBit(-3));
        MessageUtil.onTime(Integer.highestOneBit(-4));
        MessageUtil.onTime(Integer.highestOneBit(-5));
        MessageUtil.onTime(Integer.highestOneBit(-6));
        MessageUtil.onTime(Integer.highestOneBit(-7));
        MessageUtil.onTime(Integer.highestOneBit(-8));
        MessageUtil.onTime(Integer.lowestOneBit(-1));
        MessageUtil.onTime(Integer.lowestOneBit(-2));
        MessageUtil.onTime(Integer.lowestOneBit(-3));
        MessageUtil.onTime(Integer.lowestOneBit(-4));
        MessageUtil.onTime(Integer.lowestOneBit(-5));
        MessageUtil.onTime(Integer.lowestOneBit(-6));
        MessageUtil.onTime(Integer.lowestOneBit(-7));
        MessageUtil.onTime(Integer.lowestOneBit(-8));
    }

    /**
     * 对二进制补码转换为16进制表示
     */
    @Test
    public void test002() {
        MessageUtil.onTime(Integer.toHexString(1));
        MessageUtil.onTime(Integer.toHexString(-1));
    }

    /**
     * 前导0的个数
     */
    @Test
    public void test003() {
        MessageUtil.onTime(Integer.numberOfLeadingZeros(1));
        MessageUtil.onTime(Integer.numberOfLeadingZeros(-1));
    }

    /**
     * 后缀0的个数
     */
    @Test
    public void test004() {
        MessageUtil.onTime(Integer.numberOfTrailingZeros(2));
        MessageUtil.onTime(Integer.numberOfTrailingZeros(-2));
    }

    /**
     * 二进制补码中1的个数
     */
    @Test
    public void test005() {
        MessageUtil.onTime(Integer.bitCount(1));
        MessageUtil.onTime(Integer.bitCount(-1));
    }

    /**
     * 如果使用强转，int类型转换成long类型，值是不变的，
     * 如果是long类型转换为int类型，则只是把long类型的低32位给int类型变量，这样就可能变成负数。
     * <p>
     * 使用toUnsignedLong方法，可以转换为无符号的Long类型，即直接把int的32位赋给long类型，并且long类型的高32位补0
     */
    @Test
    public void test006() {
        MessageUtil.onTime((long) -1); // -1
        MessageUtil.onTime(Integer.toUnsignedLong(-1)); // 4294967295
        MessageUtil.onTime((int) (4294967295L)); // -1
    }

    /**
     * reverse 反转的是位的顺序，如1反转成: 10000000 00000000 00000000 00000000
     * reverseBytes 反转的是字节的顺序，如1字节反转成: 00000001 00000000 00000000 00000000
     */
    @Test
    public void test007() {
        MessageUtil.onTime(Integer.reverse(1));
        MessageUtil.onTime(Integer.reverse(-1));
        MessageUtil.onTime(Integer.reverseBytes(1));
        MessageUtil.onTime(Integer.reverseBytes(-1));
    }

    /**
     * parseUnsignedInt 根据字符串转换成long类型，然后直接强制转换成int
     * toUnsignedString 把int类型转换成无符号的字符串
     * compareUnsigned 把比较的值，当成无符号来比较，所以对于int，负数永远是大于整数的
     */
    @Test
    public void test008() {
        MessageUtil.onTime(Integer.parseUnsignedInt("2147483648", 10)); // -2147483648
        MessageUtil.onTime(Integer.parseUnsignedInt("4294967295", 10)); // -1
        MessageUtil.onTime(Integer.toUnsignedString(-1)); // 4294967295
        MessageUtil.onTime(Integer.compareUnsigned(-1, 1)); // 1
    }


}
