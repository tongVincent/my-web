package com.twx.core.util;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by vincent.tong on 2017/2/28.
 */
public abstract class ObjectUtil {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            return !StringUtils.hasText((String) obj);
        }

        if (obj instanceof Object[]) {
            return ((Object[]) obj).length == 0;
        }

        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        return obj instanceof Collection && ((Collection) obj).isEmpty();
    }
}
