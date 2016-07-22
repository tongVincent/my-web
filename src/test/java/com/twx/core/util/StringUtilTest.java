package com.twx.core.util;

import com.twx.test.util.MessageUtil;
import junit.framework.TestCase;

/**
 * Created by vincent.tong on 2016/7/22.
 */
public class StringUtilTest extends TestCase {

    public void testSplitByByteSizeOnUTF8() throws Exception {
        String str = "a中国大方款收到上了对方空间&a9d0安抚\\(^o^)/~";
        MessageUtil.onTime("10字节");
        StringUtil.splitByByteSizeOnUTF8(str, 10).forEach(MessageUtil::onTime);
        MessageUtil.onTime("1字节");
        StringUtil.splitByByteSizeOnUTF8(str, 1).forEach(MessageUtil::onTime);
        MessageUtil.onTime("2字节");
        StringUtil.splitByByteSizeOnUTF8(str, 2).forEach(MessageUtil::onTime);
        MessageUtil.onTime("3字节");
        StringUtil.splitByByteSizeOnUTF8(str, 3).forEach(MessageUtil::onTime);
        MessageUtil.onTime("4字节");
        StringUtil.splitByByteSizeOnUTF8(str, 4).forEach(MessageUtil::onTime);
        MessageUtil.onTime("5字节");
        StringUtil.splitByByteSizeOnUTF8(str, 5).forEach(MessageUtil::onTime);
        MessageUtil.onTime("6字节");
        StringUtil.splitByByteSizeOnUTF8(str, 6).forEach(MessageUtil::onTime);
    }
}