package com.twx.core.util;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Created by vincent.tong on 2016/7/14.
 * 此类的目的是用来分类，每个元素可以是任意值，但在比较的时候，2个此类对象的每个元素的类型必须是一样。
 * 特别是数值的时候，建议用封装类。
 * 此处，把所有数值都转换成BigDecimal来处理
 */
public class Category {
    private Object[] elements;

    public Category(Object... elements) {
        this.elements = elements;
        handleString();
        handleNumber();
    }

    private void handleString() {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] instanceof String && !StringUtils.hasText((String) elements[i])) {
                elements[i] = null;
            }
        }
    }

    private void handleNumber() {
        for (int i = 0; i < elements.length; i++) {
            Object e = elements[i];
            if (!(e instanceof Number)) {
                continue;
            }

            if (e instanceof BigInteger) {
                elements[i] = new BigDecimal((BigInteger) e);
            }
            if (e instanceof Byte || e instanceof Short || e instanceof Integer || e instanceof Long) {
                elements[i] = new BigDecimal(((Number) e).longValue());
            }
            if (e instanceof Float || e instanceof Double) {
                elements[i] = new BigDecimal(((Number) e).doubleValue());
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Category category = (Category) o;
        return deepEquals(elements, category.elements);
    }

    private boolean deepEquals(Object[] a1, Object[] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null) {
            return false;
        }

        int length = a1.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            Object e1 = a1[i];
            Object e2 = a2[i];

            boolean eq = Objects.equals(e1, e2);
            if (!eq && e1 instanceof BigDecimal && e2 instanceof BigDecimal) {
                eq = ((BigDecimal) e1).compareTo((BigDecimal) e2) == 0;
            }

            if (!eq) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }
}
