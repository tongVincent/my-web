package com.twx;

import com.twx.test.util.MessageUtil;
import org.junit.Test;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class GrammarTest extends BaseTest {

    /**
     * switch里的表达式的值不能是null
     */
    @Test
    public void test001() {
        thrown.expect(NullPointerException.class);
        String i = null;
        switch (i) {
            case "1":
                MessageUtil.onTime("1");
            default:
                MessageUtil.onTime("null");
        }
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
    public void test002() {
        B b = new B();
        b.print();
    }

    // 当构造函数抛出异常，那么变量不会引用到该抛出异常的对象上，对象构造不成功。
    // 另外，如果声明一个变量的时候，没有赋值，那么在运行的时候，是不能找到该变量的，只有初始化后的变量才能找到。
    // 对于java编程思想--异常--构造函数--中说的处理方法，也可以用下面的方式来处理，省去一个try
    @Test
    public void test003() {
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

    // 从下面测试可以看到，在类中用this和类.this是一样的
    @Test
    public void test004() {
        MessageUtil.onTime(this);
        MessageUtil.onTime(GrammarTest.this);
        MessageUtil.onTime(GrammarTest.class);
        MessageUtil.onTime(GrammarTest.B.class);
        MessageUtil.onTime(super.toString());
        MessageUtil.onTime(GrammarTest.super.toString());
    }

    interface Function {
        void apply();
    }

    private void testFunction(Function f) {
        f.apply();
    }

    @Test
    public void test005() {
        testFunction(new Function() {
            @Override
            public void apply() {
                MessageUtil.onTime(this);
            }
        });
    }
}
