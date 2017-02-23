package com.twx.core.util;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vincent.tong on 2016/6/30.
 */
public abstract class StringUtil {

    /**
     * 一个中文，可能占2个字符（4个字节）
     * 根据UTF-8的编码规则，可以知道如果字节以10开头的是多字节的中间字节
     */
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

    public static String format(String pattern, Object... arguments) {
        return MessageFormatter.arrayFormat(pattern, arguments).getMessage();
    }
}
