package com.twx.java.util;

import com.twx.BaseTest;
import com.twx.core.util.MessageUtil;
import org.junit.Test;

import java.util.ResourceBundle;

public class ResourceBundleTest extends BaseTest {

    /**
     * 通过测试，可以知道读取的资源为第一个满足要求的，如果后面还有其他同名的资源都将忽略。
     * 而且读取顺序为classpath里指定的类路径的顺序来读取的。
     */
    @Test
    public void test001() {
        ResourceBundle bundle = ResourceBundle.getBundle("test");
        MessageUtil.onTime(bundle.getString("test"));
    }
}
