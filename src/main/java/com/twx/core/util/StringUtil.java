package com.twx.core.util;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by vincent.tong on 2016/6/30.
 */
public abstract class StringUtil {

    public static final String UNDER_LINE = "_";
    static final String REG_PAT = "\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%amp;:/~\\+#]*[\\w\\-\\@?^=%amp;/~\\+#])?";
    static final String WORD_PAT = "\\w+(-\\w+)*";

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

    /**
     * 把下划线的字符串转换成驼峰式
     *
     * @param underlineStr,   下划线分隔的字符串
     * @param initialIsUpper, 最后结果的首字母是否大写
     * @return
     */
    public static String underline2camel(String underlineStr, boolean initialIsUpper) {
        if (isBlank(underlineStr) || !underlineStr.contains(UNDER_LINE)) {
            return initialIsUpper ? initial2Upper(underlineStr) : initial2Lower(underlineStr);
        }

        String result = Stream.of(underlineStr.split(UNDER_LINE))
            .map(String::toLowerCase)
            .map(StringUtil::initial2Upper)
            .reduce("", String::concat);

        return initialIsUpper ? result : initial2Lower(result);
    }

    public static String underline2camel(String underlineStr) {
        return underline2camel(underlineStr, false);
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isBlank(String str) {
        return !hasText(str);
    }

    public static boolean hasText(String str) {
        return StringUtils.hasText(str);
    }

    public static String initial2Upper(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char[] cs = str.toCharArray();
        if (cs[0] >= 'a' && cs[0] <= 'z') {
            cs[0] -= 32;
            return String.valueOf(cs);
        } else {
            return str;
        }
    }

    public static String initial2Lower(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char[] cs = str.toCharArray();
        if (cs[0] >= 'A' && cs[0] <= 'Z') {
            cs[0] += 32;
            return String.valueOf(cs);
        } else {
            return str;
        }
    }

    public static Set<String> splitToWords(String text) {
        Set<String> s = new HashSet<>();
        Matcher matcher = Pattern.compile(WORD_PAT).matcher(text);
        while (matcher.find()) {
            if (!s.contains(matcher.group())) {
                s.add(matcher.group());
            }
        }
        return s;
    }

    public static List<String> splitBySeparator(String text, String regex) {
        if (hasText(text)) {
            return Arrays.asList(text.split(regex));
        } else {
            return Collections.emptyList();
        }
    }

    public static String hideMobile(String mobile) {
        if (isBlank(mobile)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static String generateOrderNo(Long CustomerId, int num) {
        return Convert.toString(new Date(), "yyyyMMddHHmmss") + String.format("%02d", num) + String.format("%06d", CustomerId);
    }

    /**
     * 从builder中把最后面的count个字符去掉
     * @param builder
     * @param count 截取的个数
     * @return
     */
    public static String substring(StringBuilder builder, int count) {
        if (builder.length() < count) {
            return builder.toString();
        }

        return builder.substring(0, builder.length() - count);
    }

    /**
     * 截取字符串
     * @param str
     * @param size 字符数
     * @return
     */
    public static String truncate(String str, int size) {
        if (isBlank(str) || str.getBytes(CharacterEncodings.CHARSET_UTF_8).length < size) {
            return str;
        }

        return truncate(str.substring(0, str.length() - 1), size);
    }

    /**
     * 截取字符串，按中文，一个中文算2个，非中文算一个
     * @param str
     * @param size 字符数
     * @return
     */
    public static String truncateByUTF8(String str, int size) {
        if (isEmpty(str) || size <= 0) {
            return str;
        }

        byte[] bytes = str.getBytes(CharacterEncodings.CHARSET_UTF_8);

        int length = 0, count = 0;
        for (byte b : bytes) {
            if ((b & (byte) 0xc0) == (byte) 0x80) { // 中间字符
                length++;
                continue;
            }

            if ((b & (byte) 0x80) == (byte) 0x00) { // ascii码
                count++;
            } else { // 其他字符算2个
                count += 2;
            }

            if (count > size) {
                break;
            }

            length++;
        }

        return new String(bytes, 0, length, CharacterEncodings.CHARSET_UTF_8);
    }

    public static String joinBySeparator(List<String> text, String regex) {
        if (CollectionUtil.isEmpty(text)) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (String str: text) {
            if (isBlank(str)) {
                continue;
            }
            builder.append(str);

            if (!isEmpty(regex)) {
                builder.append(regex);
            }
        }

        return substring(builder, isEmpty(regex) ? 0 : regex.length());
    }

}
