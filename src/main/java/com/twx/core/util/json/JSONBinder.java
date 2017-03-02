package com.twx.core.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

import java.io.IOException;

/**
 * Created by vincent.tong on 2016/10/12.
 */
public final class JSONBinder<T> {
    private static final ObjectMapper DEFAULT_OBJECT_MAPPER;

    static {
        DEFAULT_OBJECT_MAPPER = createMapper();
    }

    public static <T> JSONBinder<T> binder(Class<T> beanClass) {
        return new JSONBinder<>(beanClass);
    }

    /**
     * toJson后的属性名相同的会相互转换，注意origin不能是字符串，targetClass也不能是String类型，
     * 与{@link JsonUtil#convert(Object, Class)}相同
     * @see JsonUtil#convert(Object, Class)
     */
    public static <T> T convert(Object fromValue, Class<T> toValueType) {
        return DEFAULT_OBJECT_MAPPER.convertValue(fromValue, toValueType);
    }

    public static ObjectMapper getObjectMapper() {
        return DEFAULT_OBJECT_MAPPER;
    }

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new JSONDateFormat()); // 设置日期转换格式
        AnnotationIntrospector primary = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
        mapper.setAnnotationIntrospector(AnnotationIntrospector.pair(primary, secondary)); // 设置注解内省方式，优先使用Jaxb注解，然后Jackson注解
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 遇到未知属性是否抛出异常
        mapper.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true); // 是否用wrapper的名字来代替属性名，通过AnnotationIntrospector.findWrapperName查找
        // 下面配置，指是否允许包含ASCII小于32的控制字符。是指在引号中，不能直接出现这些控制字符，但可以用转义字符。设为true，则允许了。
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        return mapper;
    }

    private final Class<T> beanClass;
    ObjectMapper objectMapper;

    private JSONBinder(Class<T> beanClass) {
        this.beanClass = beanClass;
        this.objectMapper = DEFAULT_OBJECT_MAPPER;
    }

    public T fromJson(String json) {
        try {
            return objectMapper.readValue(json, beanClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 实际应该这样定义方法的：public <P> P fromJson(String json, Class<? extends P> parametrized)
    // 之所以定义成下面的，就是为了避免调用时的编译时checked检查
    public <P> P fromJson(String json, Class<?> parametrized) {
        try {
            JavaType collectionType = objectMapper.getTypeFactory().constructParametricType(parametrized, beanClass);
            return objectMapper.readValue(json, collectionType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // json输出是有缩进的漂亮输出
    public JSONBinder<T> indentOutput() {
        if (DEFAULT_OBJECT_MAPPER.equals(objectMapper)) {
            objectMapper = createMapper();
        }
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return this;
    }
}
