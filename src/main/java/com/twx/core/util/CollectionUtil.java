package com.twx.core.util;

import java.util.Collection;
import java.util.List;

/**
 * @author wenxin.tong
 * @since 2017/4/25
 */
public abstract class CollectionUtil {

    public static <E> List<E> subLastBySize(List<E> list, int size) {
        if (isEmpty(list)) {
            return list;
        }

        if (list.size() > size) {
            return list.subList(list.size() - size, list.size());
        }

        return list;
    }

    public static <E> boolean isEmpty(Collection<E> c) {
        return c == null || c.isEmpty();
    }
}
