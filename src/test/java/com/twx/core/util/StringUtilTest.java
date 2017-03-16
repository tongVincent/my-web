package com.twx.core.util;

import com.twx.test.util.MessageUtil;
import junit.framework.TestCase;
import org.junit.Assert;

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
}