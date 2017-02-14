package com.twx;

import com.twx.test.util.MessageUtil;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class StringTest extends BaseTest {

    @Test
    public void test001() {
        MessageUtil.onTime("".substring(0, 0));
    }

    @Test
    public void test002() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        System.out.println(map);
        System.out.println(map + "a");
    }

    @Test
    public void test003() {
        String str = "a严";
        MessageUtil.onTime(str.length());
        for (byte b : str.getBytes(Charset.forName("UTF-8"))) {
            MessageUtil.onTime(Integer.toBinaryString(b));
        }
    }

    @Test
    public void test004() {
        MessageUtil.onTime("" + null);
    }

    // 从下面的测试可以知道，如果计算字的个数，可以根据codePointAt的值>0xFFFF来判断这个字占2个字符（4字节）。
    @Test
    public void test005() {
        String str = "\uD842\uDFB7a"; //𠮷,这个字是2个字符的
        MessageUtil.onTime(str.length());
        MessageUtil.onTime(str.codePointAt(0)); // 返回的是𠮷的unicode值
        MessageUtil.onTime(str.codePointAt(1)); // 返回的是𠮷的第二个字符的unicode值
        MessageUtil.onTime(str.codePointAt(2)); // 返回a的unicode值
        MessageUtil.onTime(str.charAt(0));
        MessageUtil.onTime(str.charAt(1));
        MessageUtil.onTime(str.charAt(2));
        MessageUtil.onTime((int)str.charAt(0));
        MessageUtil.onTime((int)str.charAt(1));
        MessageUtil.onTime((int)str.charAt(2));
    }
}
