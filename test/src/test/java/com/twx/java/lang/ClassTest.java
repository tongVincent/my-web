package com.twx.java.lang;

import com.twx.BaseTest;
import com.twx.test.domain.People;
import com.twx.test.domain.Student;
import com.twx.core.util.MessageUtil;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincent.tong on 2017/2/23.
 */
public class ClassTest extends BaseTest {

    /**
     * 从下面测试可以知道，class的getGenericSuperclass和getSuperclass返回值差不多，
     * 不同点：当此class的父类是泛型的时候，返回的类型不同
     */
    @Test
    public void test001() {
        MessageUtil.onTime(Integer.class.getGenericSuperclass());
        MessageUtil.onTime(Integer.class.getSuperclass());
        MessageUtil.onTime(Map.class.getGenericSuperclass());
        MessageUtil.onTime(Map.class.getSuperclass());
        MessageUtil.onTime(HashMap.class.getGenericSuperclass());
        MessageUtil.onTime(HashMap.class.getSuperclass());
        MessageUtil.onTime(Student.class.getGenericSuperclass());
        MessageUtil.onTime(Student.class.getSuperclass());
        MessageUtil.onTime(People.class.getGenericSuperclass());
        MessageUtil.onTime(People.class.getSuperclass());
        MessageUtil.onTime(Object.class.getGenericSuperclass());
        MessageUtil.onTime(Object.class.getSuperclass());
    }

    @Test
    public void test002() {
        printFields(Student.class);
    }

    private void printFields(Class<?> clz) {
        Class<?> superclass = clz.getSuperclass();
        if (superclass != null) {
            printFields(superclass);
        }

        for (Field field : clz.getDeclaredFields()) {
            MessageUtil.onTime(field.getName());
        }
    }

    public abstract class Parent<T> {

        public Class<?> getGeneric() {
            Type type = this.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type generic = ((ParameterizedType) type).getActualTypeArguments()[0];
                if (generic instanceof Class) {
                    return (Class<?>) generic;
                }
            }
            return null;
        }
    }

    @Test
    public void test003() {
        MessageUtil.onTime(new Parent<People>() { }.getGeneric());
        MessageUtil.onTime(new Parent<Student>() { }.getGeneric());
        MessageUtil.onTime(new Parent<List<People>>() { }.getGeneric());
    }

}
