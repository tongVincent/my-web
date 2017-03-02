package com.twx.core.util;

/**
 * Created by vincent.tong on 2017/3/2.
 */
public abstract class Base64 {

    public static String encode(String decoder) {
        return java.util.Base64.getEncoder().encodeToString(decoder.getBytes(CharacterEncodings.CHARSET_UTF_8));
    }

    public static String decode(String encoder) {
        return new String(java.util.Base64.getDecoder().decode(encoder), CharacterEncodings.CHARSET_UTF_8);
    }
}
