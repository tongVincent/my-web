package com.twx.core.util;

import com.twx.test.util.MessageUtil;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by vincent.tong on 2016/7/22.
 */
public class StringUtilTest extends TestCase {

    public void testSplitByByteSizeOnUTF8() throws Exception {
        String str = "a中国大方款收到上了\uD842\uDFB7对方空间&a9d0安抚\\(^o^)/~";
        print(str, 10);
        print(str, 1);
        print(str, 2);
        print(str, 3);
        print(str, 4);
        print(str, 5);
        print(str, 6);
    }

    private void print(String str, int byteSize) {
        MessageUtil.onTime(byteSize + "字节");
        StringUtil.splitByByteSizeOnUTF8(str, byteSize).forEach(MessageUtil::onTime);
    }

    public void testSplitToWords() throws Exception {
        String text = "a-b-c-d ab dd";
        Set<String> words = StringUtil.splitToWords(text);
        MessageUtil.onTime(words);
        Assert.assertEquals(3, words.size());
    }

    /**
     * 最后一个,后面没有东西，则不算进去
     */
    public void testSplitBySeparator() {
        List<String> list = StringUtil.splitBySeparator(",1,,2,3,", ",");
        Assert.assertEquals(5, list.size());
        Assert.assertEquals("1", list.get(1));
    }

    public void testFormat() {
        String format = StringUtil.format("数值：{}，字符串：{}", 123, "kankan");
        Assert.assertEquals("数值：123，字符串：kankan", format);
        format = StringUtil.format("数值：{}，字符串：{}", 123);
        Assert.assertEquals("数值：123，字符串：{}", format);
    }

    public void testHideMobile() {
        String mobile = "12345678900";
        StringUtil.hideMobile(mobile);
        Assert.assertEquals("123****8900", StringUtil.hideMobile("12345678900"));
    }

    public void testGenerateOrderNo() {
        MessageUtil.onTime(StringUtil.generateOrderNo(123L, 9));
        MessageUtil.onTime(StringUtil.generateOrderNo(123L, 123));
        MessageUtil.onTime(StringUtil.generateOrderNo(58888889L, 56));
    }

    public void testTruncate() {
        MessageUtil.onTime(StringUtil.truncate("asdasdfljsad;lfkjkssdsdfasdadl", 20));
        MessageUtil.onTime(StringUtil.truncate("阿克苏l发动机克撒的了放假了可见", 20));
        MessageUtil.onTime(StringUtil.truncate("阿克苏l发动机克撒的1333", 20));
        MessageUtil.onTime(StringUtil.truncate("谁看懂了就", 20));
        MessageUtil.onTime(StringUtil.truncate("撒看到了伐", 20));
    }

    public void testTruncateByUTF8() {
        MessageUtil.onTime(StringUtil.truncateByUTF8("asdasdfljsad;lfkjkssdsdfasdadl", 20));
        MessageUtil.onTime(StringUtil.truncateByUTF8("阿克苏l发动机克撒的了放假了可见", 20));
        MessageUtil.onTime(StringUtil.truncateByUTF8("阿克苏l发动机克撒的1333", 20));
        MessageUtil.onTime(StringUtil.truncateByUTF8("阿克苏l发动机克撒\uD842\uDFB7123", 20));
        MessageUtil.onTime(StringUtil.truncateByUTF8("谁看懂了就", 20));
        MessageUtil.onTime(StringUtil.truncateByUTF8("撒看到了伐", 20));
        MessageUtil.onTime(StringUtil.truncateByUTF8("撒看到了伐", 20));
    }

    public void testJoinBySeparator() {
        MessageUtil.onTime(StringUtil.joinBySeparator(Arrays.asList("dd", "aa"), "、"));
        MessageUtil.onTime(StringUtil.joinBySeparator(Arrays.asList("dd", "aa"), ""));
        MessageUtil.onTime(StringUtil.joinBySeparator(Arrays.asList("dd", "aa"), null));
        MessageUtil.onTime(StringUtil.joinBySeparator(Arrays.asList(" ", " "), "、"));
    }


}