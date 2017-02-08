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
}
