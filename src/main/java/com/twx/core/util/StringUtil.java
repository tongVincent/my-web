package com.twx.core.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vincent.tong on 2016/6/30.
 */
public abstract class StringUtil {
    public static List<String> splitByByteSizeOnUTF8(String str, int byteSize) {
        if (!StringUtils.hasText(str) || byteSize <= 0) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        byte[] bytes = str.getBytes(CharacterEncodings.CHARSET_UTF_8);

        for (int begin = 0, end = byteSize; begin < bytes.length; begin = end, end += byteSize) {
            if (end >= bytes.length) {
                end = bytes.length;
            } else {
                while (end > begin) {
                    if ((bytes[end] & (byte) 0xc0) != (byte) 0x80) {
                        break;
                    }
                    end--;
                }
            }

            if (end <= begin) {
                break;
            }

            result.add(new String(bytes, begin, end - begin, CharacterEncodings.CHARSET_UTF_8));
        }
        return result;
    }
}
