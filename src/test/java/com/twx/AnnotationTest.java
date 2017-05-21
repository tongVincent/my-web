package com.twx;

import org.junit.Test;

/**
 * @author wenxin.tong
 * @since 2017/5/8
 */
public class AnnotationTest extends BaseTest {

    public @interface Template {
        String value() default "ddd";
    }

    // 注解不能用newInstance实例化
    @Test
    public void testNewInstance() throws Exception{
        thrown.expect(InstantiationException.class);
        Template.class.newInstance();
    }
}
