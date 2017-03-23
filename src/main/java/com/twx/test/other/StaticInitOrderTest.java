package com.twx.test.other;

/**
 * 类的初始化顺序：先加载类，静态变量设为默认的0或null值。
 * 接着加载父类，
 * 最后依次按照静态变量声明的顺序初始化其设置的值（包括静态代码块）。
 *
 * 构造函数的初始化顺序：成员变量设为默认的0或null值，然后调用父类的构造函数，
 * 接着依次按照成员变量声明的顺序初始化其设置的值（包括成员代码块），
 * 最后调用构造函数体。
 * Created by Administrator on 2017/3/22.
 */
public class StaticInitOrderTest {
    public static void main(String[] args) {
        staticFunction();
        System.out.println(b);
    }

    static StaticInitOrderTest st = new StaticInitOrderTest();

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
        System.out.println(this.a);
    }

    public StaticInitOrderTest() {
        System.out.println("3");
        System.out.println("a = " + a + " b = " + b);
    }

    private static void staticFunction() {
        System.out.println("4");
    }

    int a = 110;

    static int b = 112;
}
