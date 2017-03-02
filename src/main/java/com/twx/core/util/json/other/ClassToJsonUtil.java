package com.twx.core.util.json.other;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.twx.core.util.StringUtil;
import com.twx.core.util.json.JSONBinder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by vincent.tong on 2017/2/23.
 */
public abstract class ClassToJsonUtil {

    public static void toJson(Class<?> clz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

//        ObjectNode obj = mapper.createObjectNode();

        Class<?> superclass = clz.getSuperclass();
        if (superclass != null) {

        }

        Stream.of(clz.getDeclaredFields())
            .map(field -> field.getAnnotation(XmlElement.class))
            .filter(Objects::nonNull)
            .forEach(element -> {
            });

    }

    /**
     *
     * @param json
     * @param className, 以下滑线的形式
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String fromJson(String json, String className) {
        StringBuilder builder = new StringBuilder(64);
        Map<String, Object> map = JSONBinder.binder(Map.class).fromJson(json);

        boolean hasClassName = StringUtil.hasText(className);

        if (hasClassName) {
            String name = className.toLowerCase();
            builder.append("@XmlRootElement(name = \"")
                .append(name)
                .append("\")\n@XmlAccessorType(XmlAccessType.FIELD)\npublic class ")
                .append(StringUtil.underline2camel(name, true))
                .append(" {\n");
        }

        map.forEach((key, value) -> {
            builder.append("    @XmlElement(name = \"")
                .append(key)
                .append("\")\n    private ");

            String name = StringUtil.underline2camel(key);
            if (value instanceof List) {
                builder.append("List<")
                    .append(StringUtil.initial2Upper(name))
                    .append("> ");
            } else {
                builder.append("String ");
            }

            builder.append(name)
                .append(";\n");

        });

        if (hasClassName) {
            builder.append("}\n");
        }

        return builder.toString();
    }


}
