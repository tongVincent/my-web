package com.twx.java.lang;

import com.twx.BaseTest;
import com.twx.core.util.json.GsonUtil;
import com.twx.core.util.MessageUtil;
import org.junit.Test;

import java.io.FileReader;
import java.io.FileWriter;
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
        MessageUtil.onTime((int) str.charAt(0));
        MessageUtil.onTime((int) str.charAt(1));
        MessageUtil.onTime((int) str.charAt(2));
        MessageUtil.onTime(str.getBytes());
    }

    /**
     * 从下面测试可以看出，增补区域U+D800到U+DFFF是没有分配字符的，只能由2个增补字符组成表示一个字，
     * 即高代理U+D800到U+DBFF，低代理U+DC00到U+DFFF
     * @throws Exception
     */
    @Test
    public void test006() throws Exception {
        int[] codePoints = {0xd802, 0xd802, 0xdf00, 0xdf01, 0x34};
        String str = new String(codePoints, 0, codePoints.length);
        char[] ch = str.toCharArray();
        for (char c : ch) {
            System.out.print(c + "--" + Integer.toHexString(c) + " "); //输出？？？,因为Unicode中不存在这样的char

        }

        FileWriter out = new FileWriter("aa");
        out.write(ch);
        out.close();
        System.out.print("\n***********************\n");
        FileReader in = new FileReader("aa");
        int c;
        /**
         * 对比结果发现非代理范围的字符可以正常写入与读出
         * 但是来自高代理与低代理范围的字符无法正常写入，而是被转化为0x3f，如第一个和第四个增补字符
         * 如果连续的2个增补字符可以组成一个字，则可以正确写入和读出，如第二个和第四个增补字符符合高低代理，可以组成一个字
         */
        while ((c = in.read()) != -1) {
            System.out.print(Integer.toHexString(c) + " "); //为什么是3f?
        }
        in.close();
        System.out.println(str);
    }

    /**
     * 从下面可以看出，字符串如果不是new的，则都指向同一个String对象，字符串常量池。
     *
     */
    @Test
    public void test007() {
        String a = "abc";
        String b = "abc";
        MessageUtil.onTime(a == b); // true
        MessageUtil.onTime(a.equals(b)); // true

        a = new String("abc");
        MessageUtil.onTime(a == b); // false
        b = new String("abc");
        MessageUtil.onTime(a == b); // false
        MessageUtil.onTime(a.equals(b)); // true
    }

    @Test
    public void test008() {
        MessageUtil.onTime(GsonUtil.toJson("".split("-"))); // ""
        MessageUtil.onTime(GsonUtil.toJson("-".split("-"))); // 空数组
        MessageUtil.onTime(GsonUtil.toJson("d".split("-"))); // "d"
        MessageUtil.onTime(GsonUtil.toJson("-d".split("-"))); // ""和"d"
        MessageUtil.onTime(GsonUtil.toJson("d-".split("-"))); // "d"
        MessageUtil.onTime(GsonUtil.toJson("d-s".split("-"))); // "d"和"s"
    }
}
