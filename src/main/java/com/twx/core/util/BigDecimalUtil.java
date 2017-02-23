package com.twx.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Created by vincent.tong on 2016/7/21.
 * 当把double类型为BigDecimal时，
 * 正确的做法是：先把double转为为字符串（调用String.valueOf()或Double.toString()），然后再用字符串创建BigDecimal。
 * 这是因为直接用double类型转换（直接调用BigDecimal构造函数），可能会出现不精确的情况。如：1.2可能转换成BigDecimal的时候变成了1.1999999。
 */
public abstract class BigDecimalUtil {
    public static BigDecimal getOrElse(Double d) {
        return getOrElse(d, BigDecimal.ZERO);
    }

    public static BigDecimal getOrElse(BigDecimal decimal) {
        return getOrElse(decimal, BigDecimal.ZERO);
    }

    public static BigDecimal getOrElse(Double d, BigDecimal other) {
        return d == null ? other : new BigDecimal(d.toString());
    }

    public static BigDecimal getOrElse(BigDecimal decimal, BigDecimal other) {
        return decimal == null ? other : decimal;
    }

    public static BigDecimal add(BigDecimal left, BigDecimal right) {
        if (left == null && right == null) {
            return null;
        }
        return getOrElse(left).add(getOrElse(right));
    }

    public static BigDecimal subtract(BigDecimal left, BigDecimal right) {
        if (left == null && right == null) {
            return null;
        }
        return getOrElse(left).subtract(getOrElse(right));
    }

    public static BigDecimal multiply(BigDecimal left, BigDecimal right) {
        if (left == null && right == null) {
            return null;
        }
        return getOrElse(left).multiply(getOrElse(right));
    }

    // 指定精度的误差内，2个值是否相同。默认精度是6位小数
    public static boolean equals(BigDecimal left, BigDecimal right) {
        return equals(left, right, 6);
    }

    // 指定精度的误差内，2个值是否相同。
    public static boolean equals(BigDecimal left, BigDecimal right, int scale) {
        if (left != null && right != null) {
            BigDecimal l = round(left, scale);
            return l.compareTo(round(right, scale)) == 0;
        }
        return Objects.equals(left, right);
    }

    public static BigDecimal round(BigDecimal value, int scale) {
        if (value == null) {
            return null;
        }
        return value.setScale(scale, RoundingMode.HALF_UP);
    }
}
