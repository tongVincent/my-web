package com.twx.core.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twx.core.util.CharacterEncodings;

/**
 * Created by vincent.tong on 2017/3/2.
 */
public abstract class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = createMapper();
    }

    // 默认用Jason的注解
    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new JSONDateFormat()); // 设置日期转换格式
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 遇到未知属性是否抛出异常
        mapper.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true); // 是否用wrapper的名字来代替属性名，通过AnnotationIntrospector.findWrapperName查找
        // 下面配置，指是否允许包含ASCII小于32的控制字符。是指在引号中，不能直接出现这些控制字符，但可以用转义字符。设为true，则允许了。
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        return mapper;
    }

    /**
     * toJson后的属性名相同的会相互转换，注意origin不能是字符串，targetClass也不能是String类型
     * 与{@link JSONBinder#convert(Object, Class)}相同
     * @see JSONBinder#convert(Object, Class)
     */
    public static <T> T convert(Object origin, Class<T> targetClass) {
        return OBJECT_MAPPER.convertValue(origin, targetClass);
    }

    /**
     * Base64编码，因为Base64编码后的字符都是Ascii码，所以编码后的byte[]用任何字符集生成的String都一样
     * 性能好请用{@link com.twx.core.util.Base64#encode(String)}
     */
    public static String encodeBase64(String decoder) {
        return OBJECT_MAPPER.convertValue(decoder.getBytes(CharacterEncodings.CHARSET_UTF_8), String.class);
    }

    /**
     * Base64解码
     * 性能好请用{@link com.twx.core.util.Base64#decode(String)}
     */
    public static String decodeBase64(String encoder) {
        byte[] decoder = OBJECT_MAPPER.convertValue(encoder, byte[].class);
        return new String(decoder, CharacterEncodings.CHARSET_UTF_8);
    }
}
