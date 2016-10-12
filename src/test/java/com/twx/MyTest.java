package com.twx;

import com.twx.test.util.MessageUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vincent.tong on 2016/7/14.
 */
public class MyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    /**
     * switch里的表达式的值不能是null
     */
    @Test
    public void test011() {
        thrown.expect(NullPointerException.class);
        String i = null;
        switch (i) {
            case "1":
                MessageUtil.onTime("1");
            default:
                MessageUtil.onTime("null");
        }
    }

    @Test
    public void test012() {
        MessageUtil.onTime("".substring(0, 0));
    }

    @Test
    public void test013() {
        String str = null;
        MessageUtil.onTime(Collections.singletonList("df"));
        MessageUtil.onTime(Collections.singletonList(null).isEmpty());
    }

    // 列出指定目录下的所有子目录
    @Test
    public void test015() throws IOException {
        String directory = "D:\\fuzhou";
        Files.list(Paths.get(directory))
            .filter(Files::isDirectory)
            .map(path -> path.getName(path.getNameCount() - 1))
            .forEach(pathName -> System.out.println("cd " + pathName + "\n" + "git pull\ncd ..\n"));
    }

    @Test
    public void test016() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        System.out.println(map);
        System.out.println(map + "a");
    }


    class A {
        int day;
        int year;

        public A() {
        }

        public A(int day) {
            this.day = day;
        }

        public A(int day, int year) {
            this(day);
            this.year = year;
        }

        public void print() {
            MessageUtil.onTime(day);
            MessageUtil.onTime(year);
        }
    }

    class B extends A {

    }

    class C extends A {
        public C() {
            throw new RuntimeException("调试");
        }
    }

    @Test
    public void test017() {
        B b = new B();
        b.print();
    }

    // 当构造函数抛出异常，那么变量不会引用到该抛出异常的对象上，对象构造不成功。
    // 另外，如果声明一个变量的时候，没有赋值，那么在运行的时候，是不能找到该变量的，只有初始化后的变量才能找到。
    // 对于java编程思想--异常--构造函数--中说的处理方法，也可以用下面的方式来处理，省去一个try
    @Test
    public void test018() {
        C c = null;
        try {
            c = new C();
            c.print();
        } catch (Exception e) {
            if (c == null) {
                System.out.println("c is null");
            }
        }
    }

    interface Function {
        void apply();
    }

    private void testFunction(Function f) {
        f.apply();
    }

    @Test
    public void test019() {
        testFunction(new Function() {
            @Override
            public void apply() {
                MessageUtil.onTime(this);
            }
        });
    }

    // 从下面测试可以看到，在类中用this和类.this是一样的
    @Test
    public void test020() {
        MessageUtil.onTime(this);
        MessageUtil.onTime(MyTest.this);
        MessageUtil.onTime(MyTest.class);
        MessageUtil.onTime(MyTest.B.class);
        MessageUtil.onTime(super.toString());
        MessageUtil.onTime(MyTest.super.toString());
    }

    @Override
    public String toString() {
        return "MyTest{"
            + "thrown=" + thrown
            + '}';
    }
}



